package com.etespuniverse.mobile;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import androidx.annotation.Nullable;

import java.util.ArrayList;

public class DBHandler extends SQLiteOpenHelper {

    private static final String DB_NAME = "carrinho";

    private static final int DB_VERSION = 1;

    private static final String TABLE_NAME = "itens";

    private static final String ID_COL = "idIngresso";
    private static final String ID_CLIENTE_COL = "idCliente";
    private static final String ID_STATUS_INGRESSO_COL = "idStatusIngresso";
    private static final String ID_TIPO_INGRESSO_COL = "idTipoIngresso";
    private static final String DATA_EMISSAO_COL = "dataEmissao";
    private static final String MEIA_COL = "meia";
    private static final String DATA_INICIO_COL = "dataInicio";
    private static final String DATA_VALIDADE_COL = "dataValidade";
    private static final String ID_EVENTO_COL = "idEvento";
    private static final String PRECO_COL = "preco";
    private static final String DESCRICAO_COL = "descricao";
    private static final String QTDE_COL = "qtde";
    private static final String SQL_DELETE_ENTRIES = "DROP TABLE IF EXISTS " + TABLE_NAME;

    //private static final

    public DBHandler(@Nullable Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String query = "CREATE TABLE " + TABLE_NAME + " ("
                + ID_COL + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + ID_CLIENTE_COL + " INTEGER,"
                + ID_STATUS_INGRESSO_COL + " INTEGER,"
                + ID_TIPO_INGRESSO_COL + " INTEGER,"
                + DATA_EMISSAO_COL + " TEXT,"
                + MEIA_COL + " INTEGER,"
                + DATA_INICIO_COL + " TEXT,"
                + DATA_VALIDADE_COL + " TEXT,"
                + ID_EVENTO_COL + " INTEGER,"
                + PRECO_COL + " REAL,"
                + DESCRICAO_COL + " TEXT,"
                + QTDE_COL + " INTEGER)";
        sqLiteDatabase.execSQL(query);
    }

    public void addItem(ItemCarrinho item) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(ID_CLIENTE_COL, item.getIdCliente());
        values.put(ID_STATUS_INGRESSO_COL, item.getIdStatusIngresso());
        values.put(ID_TIPO_INGRESSO_COL, item.getIdTipoIngresso());
        values.put(DATA_EMISSAO_COL, item.getDataEmissao());
        values.put(MEIA_COL, item.isMeia());
        values.put(DATA_INICIO_COL, item.getDataInicioDB());
        values.put(DATA_VALIDADE_COL, item.getDataValidadeDB());
        values.put(ID_EVENTO_COL, item.getIdEvento());
        values.put(PRECO_COL, item.getPreco());
        values.put(DESCRICAO_COL, item.getDescricao());
        values.put(QTDE_COL, item.getQtde());

        db.insert(TABLE_NAME, null, values);
        db.close();

    }

    public void addItens(ArrayList<ItemCarrinho> itens) {
        SQLiteDatabase db = this.getWritableDatabase();
        Log.d("TAG", "AddingItens");
        ArrayList<ItemCarrinho> itensCart = this.getItens();
        Log.d("TAG", "Cart(" + itensCart.size() + ")");
        Log.d("TAG", "Itens add(" + itens.size() + ")");
        for (ItemCarrinho item: itens) {
            Log.d("TAG", "Item -> " + item.getIdTipoIngresso() + ": " + item.getPreco() + " (" + item.getQtde() + ") - " + item.isMeia());
            boolean novo = true;
            if (itensCart.size() > 0) {
                for (ItemCarrinho itemCart: itensCart) {
                    Log.d("TAG", "ItemCart -> " + itemCart.getIdTipoIngresso() + ": " + itemCart.getPreco() + " (" + itemCart.getQtde() + ") - " + itemCart.isMeia());
                    if (item.getIdTipoIngresso() == itemCart.getIdTipoIngresso() && item.isMeia() == itemCart.isMeia()) {
                        Log.d("TAG", "Old item");
                        novo = false;
                        for (int i = 0; i < item.getQtde(); i++) {
                            this.itemPlus(itemCart.getIdItem());
                        }
                        break;
                    }
                }
            }
            if (novo) {
                Log.d("TAG", "New Item");
                Log.d("TAG", "AddItens - Item -> " + item.getIdTipoIngresso() + ": " + item.getPreco() + " (" + item.getQtde() + ") - " + item.isMeia());
                this.addItem(item);
            }

            /*
            for (int i = 0; i < item.getQtde(); i++) {

                Log.d("TAG", "AddItens - Item -> " + item.getIdTipoIngresso() + ": " + item.getPreco() + " (" + item.getQtde() + ") - " + item.isMeia());
                ContentValues values = new ContentValues();
                values.put(ID_CLIENTE_COL, item.getIdCliente());
                values.put(ID_STATUS_INGRESSO_COL, item.getIdStatusIngresso());
                values.put(ID_TIPO_INGRESSO_COL, item.getIdTipoIngresso());
                values.put(DATA_EMISSAO_COL, item.getDataEmissao());
                values.put(MEIA_COL, item.isMeia());
                values.put(DATA_INICIO_COL, item.getDataInicio());
                values.put(DATA_VALIDADE_COL, item.getDataValidade());
                values.put(ID_EVENTO_COL, item.getIdEvento());
                values.put(PRECO_COL, item.getPreco());
                values.put(DESCRICAO_COL, item.getDescricao());
                values.put(QTDE_COL, item.getQtde());

                db.insert(TABLE_NAME, null, values);
            }
            */
        }
        db.close();

    }

    public int itemPlus(int idItem) {

        SQLiteDatabase dbRead = this.getReadableDatabase();

        String[] projection = {QTDE_COL};

        // Filter results WHERE "title" = 'My Title'
        String selection = ID_COL + " = ?";
        String[] selectionArgs = { String.valueOf(idItem) };

        Cursor cursor = dbRead.query(
                TABLE_NAME,
                projection,
                selection,
                selectionArgs,
                null,
                null,
                null
        );

        int qtde = 0;
        if (cursor.moveToNext()) {
            qtde = cursor.getInt(cursor.getColumnIndexOrThrow(QTDE_COL));
        }
        dbRead.close();

        qtde++;
        SQLiteDatabase dbUpdate = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(QTDE_COL, qtde);

        dbUpdate.update(TABLE_NAME, values, selection, selectionArgs);
        dbUpdate.close();
        return qtde;
    }

    public int itemMinus(int idItem) {

        SQLiteDatabase dbRead = this.getReadableDatabase();

        String[] projection = {QTDE_COL};

        // Filter results WHERE "title" = 'My Title'
        String selection = ID_COL + " = ?";
        String[] selectionArgs = { String.valueOf(idItem) };

        Cursor cursor = dbRead.query(
                TABLE_NAME,
                projection,
                selection,
                selectionArgs,
                null,
                null,
                null
        );

        int qtde = 0;
        if (cursor.moveToNext()) {
            qtde = cursor.getInt(cursor.getColumnIndexOrThrow(QTDE_COL));
        }
        dbRead.close();

        qtde--;
        SQLiteDatabase dbUpdate = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(QTDE_COL, qtde);

        dbUpdate.update(TABLE_NAME, values, selection, selectionArgs);
        dbUpdate.close();
        return qtde;
    }

    public void deleteItem(int idItem) {

        SQLiteDatabase db = this.getWritableDatabase();

        String selection = ID_COL + " = ?";
        String[] selectionArgs = { String.valueOf(idItem) };

        db.delete(TABLE_NAME, selection, selectionArgs);

    }

    public void clearCart() {

        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_NAME, null, null);
    }

    public ArrayList<ItemCarrinho> getItens() {

        SQLiteDatabase db = this.getReadableDatabase();

        String[] projection = {
                ID_COL,
                ID_CLIENTE_COL,
                ID_STATUS_INGRESSO_COL,
                ID_TIPO_INGRESSO_COL,
                DATA_EMISSAO_COL,
                MEIA_COL,
                DATA_INICIO_COL,
                DATA_VALIDADE_COL,
                ID_EVENTO_COL,
                PRECO_COL,
                DESCRICAO_COL,
                QTDE_COL,
        };

        String sortOrder =
                PRECO_COL + " ASC";

        Cursor cursor = db.query(
                TABLE_NAME,   // The table to query
                projection,             // The array of columns to return (pass null to get all)
                null,              // The columns for the WHERE clause
                null,          // The values for the WHERE clause
                null,                   // don't group the rows
                null,                   // don't filter by row groups
                sortOrder               // The sort order
        );

        ArrayList<ItemCarrinho> itens = new ArrayList<>();
        Log.d("TAG", "getItens: ");

        while (cursor.moveToNext()) {
            int idItem = cursor.getInt(cursor.getColumnIndexOrThrow(ID_COL));
            int idCliente = cursor.getInt(cursor.getColumnIndexOrThrow(ID_CLIENTE_COL));
            int idStatusIngresso = cursor.getInt(cursor.getColumnIndexOrThrow(ID_STATUS_INGRESSO_COL));
            int idTipoIngresso = cursor.getInt(cursor.getColumnIndexOrThrow(ID_TIPO_INGRESSO_COL));
            String dataEmissao = cursor.getString(cursor.getColumnIndexOrThrow(DATA_EMISSAO_COL));
            boolean meia = cursor.getInt(cursor.getColumnIndexOrThrow(MEIA_COL)) > 0;
            String dataInicio = cursor.getString(cursor.getColumnIndexOrThrow(DATA_INICIO_COL));
            String dataValidade = cursor.getString(cursor.getColumnIndexOrThrow(DATA_VALIDADE_COL));
            int idEvento = cursor.getInt(cursor.getColumnIndexOrThrow(ID_EVENTO_COL));
            double preco = cursor.getDouble(cursor.getColumnIndexOrThrow(PRECO_COL));
            String descricao = cursor.getString(cursor.getColumnIndexOrThrow(DESCRICAO_COL));
            int qtde = cursor.getInt(cursor.getColumnIndexOrThrow(QTDE_COL));
            ItemCarrinho item = new ItemCarrinho();
            item.setIdItem(idItem);
            item.setIdCliente(idCliente);
            item.setIdStatusIngresso(idStatusIngresso);
            item.setIdTipoIngresso(idTipoIngresso);
            item.setDataEmissao(dataEmissao);
            item.setMeia(meia);
            item.setDataInicioDB(dataInicio);
            item.setDataValidadeDB(dataValidade);
            item.setIdEvento(idEvento);
            item.setPreco(preco);
            item.setDescricao(descricao);
            item.setQtde(qtde);
            itens.add(item);
            Log.d("TAG", "Item (" + item.getIdItem() + ")" + " -> " + item.getIdTipoIngresso() + ": " + item.getPreco() + " (" + item.getQtde() + ") - " + item.isMeia());
        }
        Log.d("TAG", "Count itens: " + itens.size());
        return itens;
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int oldVersion, int newVersion) {
        // This database is only a cache for online data, so its upgrade policy is
        // to simply to discard the data and start over
        sqLiteDatabase.execSQL(SQL_DELETE_ENTRIES);
        onCreate(sqLiteDatabase);
    }
}
