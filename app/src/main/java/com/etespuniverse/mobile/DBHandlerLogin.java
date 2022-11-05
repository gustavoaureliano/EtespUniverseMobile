package com.etespuniverse.mobile;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;

import androidx.annotation.Nullable;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;

public class DBHandlerLogin extends SQLiteOpenHelper {

    private static final String DB_NAME = "cliente";

    private static final int DB_VERSION = 1;

    private static final String TABLE_NAME = "Cliente";

    private static final String ID_COL = "idIngresso";
    private static final String ID_CLIENTE_COL = "idCliente";
    private static final String CPF_COL = "idStatusIngresso";
    private static final String NOME_COL = "idTipoIngresso";
    private static final String SOBRENOME_COL = "dataEmissao";
    private static final String DATA_NASCIMENTO_COL = "meia";
    private static final String EMAIL_COL = "dataInicio";
    private static final String FOTO_COL = "dataValidade";
    private static final String ACESSOS_COL = "idEvento";
    private static final String SQL_DELETE_ENTRIES = "DROP TABLE IF EXISTS " + TABLE_NAME;

    //private static final

    public DBHandlerLogin(@Nullable Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String query = "CREATE TABLE " + TABLE_NAME + " ("
                + ID_COL + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + ID_CLIENTE_COL + " INTEGER,"
                + CPF_COL + " TEXT,"
                + NOME_COL + " TEXT,"
                + SOBRENOME_COL + " TEXT,"
                + DATA_NASCIMENTO_COL + " TEXT,"
                + EMAIL_COL + " TEXT,"
                + FOTO_COL + " BLOB,"
                + ACESSOS_COL + " INTEGER)";
        sqLiteDatabase.execSQL(query);
    }

    public void setCliente(Cliente cliente) {

        SQLiteDatabase dbRead = getReadableDatabase();

        String selection = ID_CLIENTE_COL + " = ?";
        String[] selectionArgs = { String.valueOf(cliente.getId()) };

        long qtde = DatabaseUtils.queryNumEntries(
                dbRead,
                TABLE_NAME,
                selection,
                selectionArgs
        );
        dbRead.close();

        Log.d("TAG", "setCliente: " + cliente.toString());
        Log.d("TAG", "qtde: " + qtde);

        ContentValues values = new ContentValues();
        values.put(ID_CLIENTE_COL, cliente.getId());
        values.put(CPF_COL, cliente.getCpf());
        values.put(NOME_COL, cliente.getNome());
        values.put(SOBRENOME_COL, cliente.getSobrenome());
        values.put(DATA_NASCIMENTO_COL, cliente.getDataNascimentoDB());
        values.put(EMAIL_COL, cliente.getEmail());

        if (cliente.getFoto() != null) {
            Bitmap foto = cliente.getFoto();
            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            foto.compress(Bitmap.CompressFormat.JPEG,40,outputStream);
            byte[] byteImage_photo = outputStream.toByteArray();

            values.put(FOTO_COL, byteImage_photo);
        }

        values.put(ACESSOS_COL, cliente.getAcessos());

        if (qtde > 0) {
            Log.d("TAG", "old cliente");
            SQLiteDatabase dbUpdate = this.getWritableDatabase();
            dbUpdate.update(TABLE_NAME, values, selection, selectionArgs);
            dbUpdate.close();
        } else {
            Log.d("TAG", "new cliente");
            SQLiteDatabase dbWrite = this.getWritableDatabase();
            dbWrite.insert(TABLE_NAME, null, values);
            dbWrite.close();
        }


    }

    public void deleteCliente(Cliente cliente) {

        SQLiteDatabase db = this.getWritableDatabase();

        String selection = ID_CLIENTE_COL + " = ?";
        String[] selectionArgs = { String.valueOf(cliente.getId()) };

        db.delete(TABLE_NAME, selection, selectionArgs);

    }

    public void clearClientes() {

        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_NAME, null, null);
    }

    public Cliente getCliente() {

        SQLiteDatabase db = this.getReadableDatabase();

        String[] projection = {
                ID_COL,
                ID_CLIENTE_COL,
                CPF_COL,
                NOME_COL,
                SOBRENOME_COL,
                DATA_NASCIMENTO_COL,
                EMAIL_COL,
                FOTO_COL,
                ACESSOS_COL
        };

        Cursor cursor = db.query(
                TABLE_NAME,   // The table to query
                projection,             // The array of columns to return (pass null to get all)
                null,              // The columns for the WHERE clause
                null,          // The values for the WHERE clause
                null,                   // don't group the rows
                null,                   // don't filter by row groups
                null               // The sort order
        );

        Cliente cliente = new Cliente();

        if (cursor.moveToFirst()) {
            Log.d("TAG", "Found cliente");
            //int idItem = cursor.getInt(cursor.getColumnIndexOrThrow(ID_COL));
            int idCliente = cursor.getInt(cursor.getColumnIndexOrThrow(ID_CLIENTE_COL));
            String cpf = cursor.getString(cursor.getColumnIndexOrThrow(CPF_COL));
            String nome = cursor.getString(cursor.getColumnIndexOrThrow(NOME_COL));
            String sobrenome = cursor.getString(cursor.getColumnIndexOrThrow(SOBRENOME_COL));
            String dataNascimento = cursor.getString(cursor.getColumnIndexOrThrow(DATA_NASCIMENTO_COL));
            String email = cursor.getString(cursor.getColumnIndexOrThrow(EMAIL_COL));
            Bitmap foto = null;
            try {
                byte[] image = cursor.getBlob(cursor.getColumnIndexOrThrow(FOTO_COL));
                ByteArrayInputStream inputStream = new ByteArrayInputStream(image);
                foto = BitmapFactory.decodeStream(inputStream);
            } catch (Exception e) {
                Log.d("TAG", e.getMessage());
            }

            cliente.setId(idCliente);
            cliente.setCpf(cpf);
            cliente.setNome(nome);
            cliente.setSobrenome(sobrenome);
            cliente.setDataNascimentoDB(dataNascimento);
            cliente.setEmail(email);
            if (foto != null)
                cliente.setFoto(foto);
        } else {
            Log.d("TAG", "not Found cliente");
        }
        Log.d("TAG", "Cliente found: " + cliente.toString());
        return cliente;
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int oldVersion, int newVersion) {
        // This database is only a cache for online data, so its upgrade policy is
        // to simply to discard the data and start over
        sqLiteDatabase.execSQL(SQL_DELETE_ENTRIES);
        onCreate(sqLiteDatabase);
    }
}
