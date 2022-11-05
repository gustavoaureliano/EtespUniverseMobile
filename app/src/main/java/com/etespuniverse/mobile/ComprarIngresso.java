package com.etespuniverse.mobile;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class ComprarIngresso extends AppCompatActivity {

    private static final String ARG_PARAM_CLIENTE = "cliente";

    private MaterialToolbar topAppBar;
    private Button btnComprar, btnPlusInt, btnMinusInt, btnPlusMeia, btnMinusMeia;
    private TextView lblPreco, lblPrecoMeia, lblQtdeInt, lblQtdeMeia, lblTotal;
    private TextInputLayout txtDataLayout;
    private TextInputEditText txtData;

    private DatePickerDialog datePickerDialog;
    private Cliente cliente = FragmentsPage.cliente;
    private Ingresso ingresso = new Ingresso();
    private ItemCarrinho ingressoInt = new ItemCarrinho();
    private ItemCarrinho ingressoMeia = new ItemCarrinho();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comprar_ingresso);

        //ingresso.setIdTipoIngresso(1);
        //ingresso.setPreco(50.99);

        Intent it = getIntent();
        ingresso = (Ingresso) it.getSerializableExtra("ingresso");
        Log.d("TAG", "Ingresso got: (" + ingresso.getIdTipoIngresso() + ") " + ingresso.getDescricao());

        ingressoInt.setIdTipoIngresso(ingresso.getIdTipoIngresso());
        ingressoInt.setPreco(ingresso.getPreco());
        ingressoInt.setQtde(1);
        ingressoInt.setMeia(false);
        ingressoMeia.setIdTipoIngresso(ingresso.getIdTipoIngresso());
        ingressoMeia.setPreco(ingresso.getPreco()/2);
        ingressoMeia.setQtde(0);
        ingressoMeia.setMeia(true);

        topAppBar = findViewById(R.id.topAppBar);
        btnComprar = findViewById(R.id.btnComprar);
        btnPlusInt = findViewById(R.id.btnPlusInt);
        btnMinusInt = findViewById(R.id.btnMinusInt);
        btnPlusMeia = findViewById(R.id.btnPlusMeia);
        btnMinusMeia = findViewById(R.id.btnMinusMeia);
        lblPreco = findViewById(R.id.lblPreco);
        lblPrecoMeia = findViewById(R.id.lblPrecoMeia);
        lblQtdeInt = findViewById(R.id.lblQtdeInt);
        lblQtdeMeia = findViewById(R.id.lblQtdeMeia);
        lblTotal = findViewById(R.id.lblTotal);
        txtDataLayout = findViewById(R.id.txtDataLayout);
        txtData = findViewById(R.id.txtData);
        txtDataLayout = findViewById(R.id.txtDataLayout);
        
        initDatePicker();
        txtData.setText(getTodaysDate());

        txtData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                datePickerDialog.show();
            }
        });

        txtDataLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                datePickerDialog.show();
            }
        });

        this.updateQtde();

        DecimalFormatSymbols symbols = new DecimalFormatSymbols(new Locale("pt", "BR"));
        DecimalFormat formatter = new DecimalFormat("###,###,##0.00", symbols);
        formatter.setRoundingMode(RoundingMode.DOWN);
        String txtPreco = "R$ " + formatter.format(ingressoInt.getPreco());
        String txtPrecoMeia = "R$ " + formatter.format(ingressoMeia.getPreco());

        Log.d("TAG", "Preco: " + ingressoInt.getPreco() + "; Meia: " + ingressoMeia.getPreco() + " / mostrado: " + txtPrecoMeia);
        lblPreco.setText(txtPreco);
        lblPrecoMeia.setText(txtPrecoMeia);

        topAppBar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

        btnPlusInt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int qtde = ingressoInt.getQtde();
                qtde++;
                ingressoInt.setQtde(qtde);
                updateQtde();
            }
        });

        btnMinusInt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int qtde = ingressoInt.getQtde();
                if (qtde > 0) {
                    qtde--;
                    ingressoInt.setQtde(qtde);
                    updateQtde();
                }
            }
        });

        btnPlusMeia.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int qtde = ingressoMeia.getQtde();
                qtde++;
                ingressoMeia.setQtde(qtde);
                updateQtde();
            }
        });

        btnMinusMeia.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int qtde = ingressoMeia.getQtde();
                if (qtde > 0) {
                    qtde--;
                    ingressoMeia.setQtde(qtde);
                    updateQtde();
                }
            }
        });

        btnComprar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Log.d("TAG", "Ingresso -> " + ingresso.getIdTipoIngresso() + ": " + ingresso.getPreco() + " (" + "0" + ") - " + ingresso.isMeia());
                ArrayList<ItemCarrinho> ingressos = new ArrayList<>();
                if (ingressoInt.getQtde() > 0) {
                    ingressoInt.setDataInicio(ingresso.getDataInicio());
                    ingressoInt.setDataValidade(ingresso.getDataValidade());
                    ingressos.add(ingressoInt);
                    Log.d("TAG", "ingressoInt -> " + ingressoInt.getIdTipoIngresso() + ": " + ingressoInt.getPreco() + " (" + ingressoInt.getQtde() + ") - " + ingressoInt.isMeia());
                }
                if (ingressoMeia.getQtde() > 0) {
                    ingressoMeia.setDataInicio(ingresso.getDataInicio());
                    ingressoMeia.setDataValidade(ingresso.getDataValidade());
                    ingressos.add(ingressoMeia);
                    Log.d("TAG", "ingressoMeia -> " + ingressoMeia.getIdTipoIngresso() + ": " + ingressoMeia.getPreco() + " (" + ingressoMeia.getQtde() + ") - " + ingressoMeia.isMeia());
                }

                DBHandler db = new DBHandler(ComprarIngresso.this);
                db.addItens(ingressos);
                Intent it = new Intent(ComprarIngresso.this, Carrinho.class);
                //it.putExtra(ARG_PARAM_CLIENTE, cliente);
                startActivity(it);
            }
        });

    }

    private void updateQtde() {
        lblQtdeInt.setText(String.valueOf(ingressoInt.getQtde()));
        lblQtdeMeia.setText(String.valueOf(ingressoMeia.getQtde()));
        double totalInt = ingressoInt.getPreco() * ingressoInt.getQtde();
        double totalMeia = ingressoMeia.getPreco() * ingressoMeia.getQtde();
        double total = totalInt + totalMeia;
        DecimalFormatSymbols symbols = new DecimalFormatSymbols(new Locale("pt", "BR"));
        DecimalFormat formatter = new DecimalFormat("###,###,##0.00", symbols);
        formatter.setRoundingMode(RoundingMode.DOWN);
        String txtTotal = "R$ " + formatter.format(total);
        lblTotal.setText(txtTotal);
    }

    private String getTodaysDate() {
        Calendar cal = Calendar.getInstance();
        int year = cal.get(Calendar.YEAR);
        int month = cal.get(Calendar.MONTH);
        month++;
        int day = cal.get(Calendar.DAY_OF_MONTH);
        //cal.set(2022,1,21);
        //Date date = new Date();
        //int year = cal.get(Calendar.YEAR);
        //int month = cal.get(Calendar.MONTH);
        //month++;
        //int day = cal.get(Calendar.DAY_OF_MONTH);
        return makeDateString(cal.getTime());
        //return makeDateString(year, month, day);
    }

    private void initDatePicker() {
        DatePickerDialog.OnDateSetListener dateSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                Calendar cal = Calendar.getInstance();
                cal.set(year, month, day);
                String date = makeDateString(cal.getTime());
                ingresso.setDataInicio(cal.getTime());
                ingresso.setDataValidade(cal.getTime());
                txtData.setText(date);
            }
        };

        Calendar cal = Calendar.getInstance();
        int year = cal.get(Calendar.YEAR);
        int month = cal.get(Calendar.MONTH);
        int day = cal.get(Calendar.DAY_OF_MONTH);

        int style = AlertDialog.THEME_DEVICE_DEFAULT_LIGHT;

        datePickerDialog = new DatePickerDialog(ComprarIngresso.this, style, dateSetListener, year, month, day);

    }

    private String makeDateString(Date date) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd LLL yyyy");
        String dateString = dateFormat.format(date);
        //return day + " " + getMonthFormat(month) + " " + year;
        return dateString;
    }

    private String getMonthFormat(int month) {
        if (month == 1)
            return "JAN";
        if (month == 2)
            return "FEV";
        if (month == 3)
            return "MAR";
        if (month == 4)
            return "ABR";
        if (month == 5)
            return "MAI";
        if (month == 6)
            return "JUN";
        if (month == 7)
            return "JUL";
        if (month == 8)
            return "AGO";
        if (month == 9)
            return "SET";
        if (month == 10)
            return "OUT";
        if (month == 11)
            return "NOV";
        if (month == 12)
            return "DEZ";
        //DEFAULT - shouldn't happen
        return "JAN";
    }


}