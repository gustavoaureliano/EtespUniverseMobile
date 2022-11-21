package com.etespuniverse.mobile;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.android.material.snackbar.Snackbar;

import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.ArrayList;
import java.util.Locale;

public class Carrinho extends AppCompatActivity {

    private static final String ARG_PARAM_CLIENTE = "cliente";

    private MaterialToolbar topAppBar;
    private Button btnPagar, btnItens, btnAddItens;
    private Cliente cliente = SharedData.getCliente();
    private AutoCompleteTextView listCupons;
    private RecyclerView rvItens;
    private TextView lblTotal;
    ArrayList<ItemCarrinho> itens = new ArrayList<>();
    ArrayList<Cupom> cupons = new ArrayList<>();
    Cupom selectedCupom = new Cupom();

    private ProgressDialog load;
    private String apiUrl = SharedData.getApiUrl();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_carrinho);

        //Intent it = getIntent();
        //cliente = (Cliente) it.getSerializableExtra(ARG_PARAM_CLIENTE);

        topAppBar = findViewById(R.id.topAppBar);
        btnPagar = findViewById(R.id.btnPagar);
        listCupons = findViewById(R.id.listCupons);
        rvItens = findViewById(R.id.rvItens);
        //btnItens = findViewById(R.id.btnItens);
        //btnAddItens = findViewById(R.id.btnAddItens);
        lblTotal = findViewById(R.id.lblTotal);

        topAppBar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

        btnPagar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (SharedData.getCliente().getId() > 0) {
                    Pedido pedido = new Pedido();
                    pedido.setCliente(SharedData.getCliente());
                    ArrayList<Ingresso> ingressos = new ArrayList<Ingresso>();
                    for (ItemCarrinho item: itens) {
                        for (int i = 0; i < item.getQtde(); i++) {
                            ingressos.add(item);
                        }
                    }
                    pedido.setIngressos(ingressos);
                    pedido.setIdCupom(selectedCupom.getIdCupom());
                    ComprarTask task = new ComprarTask();
                    //Toast.makeText(Carrinho.this, pedido.toString(), Toast.LENGTH_SHORT).show();
                    task.execute(pedido);
                } else {
                    Snackbar.make(btnPagar, "É preciso estar logado para realizar a compra.", Snackbar.LENGTH_SHORT)
                            .setAction("Login", new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                    Intent it = new Intent(Carrinho.this, Login.class);
                                    startActivity(it);
                                }
                            })
                            .show();
                }
            }
        });

        Log.d("TAG", "start get itens ");
        DBHandler dbHandler = new DBHandler(Carrinho.this);
        itens = dbHandler.getItens();

        atualizarSubTotal();

        ItensCarrinhoAdapter adapter = new ItensCarrinhoAdapter(Carrinho.this, itens);
        OnCartItemClickInterface clickInterface = new OnCartItemClickInterface() {
            @Override
            public void clickPlus(int idItem, int index) {
                DBHandler db = new DBHandler(Carrinho.this);
                int qtde = db.itemPlus(idItem);
                ItemCarrinho item = itens.get(index);
                item.setQtde(qtde);
                itens.set(index, item);
                adapter.notifyItemChanged(index);
                atualizarSubTotal();
            }

            @Override
            public void clickMinus(int idItem, int index) {
                ItemCarrinho item = itens.get(index);
                DBHandler db = new DBHandler(Carrinho.this);
                if (item.getQtde() > 1) {
                    int qtde = db.itemMinus(idItem);
                    item.setQtde(qtde);
                    itens.set(index, item);
                    adapter.notifyItemChanged(index);
                } else {
                    db.deleteItem(idItem);
                    itens.remove(index);
                    adapter.notifyItemRemoved(index);
                }
                atualizarSubTotal();
            }
        };
        adapter.setOnClickInterface(clickInterface);
        Log.d("TAG", "Item count: " + adapter.getItemCount());
        rvItens.setAdapter(adapter);
        rvItens.setLayoutManager(new LinearLayoutManager(Carrinho.this, LinearLayoutManager.VERTICAL, false));
        rvItens.setNestedScrollingEnabled(false); //dá pra fazer no xml direto também
        /*
        btnAddItens.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ItemCarrinho itemCarrinho = new ItemCarrinho();
                ItemCarrinho itemCarrinho2 = new ItemCarrinho();
                itemCarrinho.setIdTipoIngresso(1);
                //itemCarrinho.setDataInicio("2022-10-02");
                //itemCarrinho.setDataValidade("2022-10-02");
                itemCarrinho.setMeia(false);
                itemCarrinho.setPreco(56.91);
                itemCarrinho.setQtde(1);
                itemCarrinho2.setIdTipoIngresso(1);
                //itemCarrinho2.setDataInicio("2022-10-02");
                //itemCarrinho2.setDataValidade("2022-10-02");
                itemCarrinho2.setMeia(true);
                itemCarrinho2.setPreco(49.99);
                itemCarrinho2.setQtde(2);
                Log.d("TAG", "Carrinho - Item -> " + itemCarrinho.getIdTipoIngresso() + ": " + itemCarrinho.getPreco() + " (" + itemCarrinho.getQtde() + ") - " + itemCarrinho.isMeia());
                Log.d("TAG", "Carrinho - Item -> " + itemCarrinho2.getIdTipoIngresso() + ": " + itemCarrinho2.getPreco() + " (" + itemCarrinho2.getQtde() + ") - " + itemCarrinho2.isMeia());

                DBHandler dbHandler = new DBHandler(Carrinho.this);
                dbHandler.addItem(itemCarrinho2);
                dbHandler.addItem(itemCarrinho);

                ArrayList<ItemCarrinho> itensTemp = dbHandler.getItens();
                itens.clear();
                itens.addAll(itensTemp);
                adapter.notifyDataSetChanged();
                atualizarSubTotal();
            }
        });
        */
        /*
        btnItens.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d("TAG", "start get itens ");
                DBHandler dbHandler = new DBHandler(Carrinho.this);
                ArrayList<ItemCarrinho> itens = dbHandler.getItens();


                ItensCarrinhoAdapter adapter = new ItensCarrinhoAdapter(Carrinho.this, itens);
                OnCartItemClickInterface clickInterface = new OnCartItemClickInterface() {
                    @Override
                    public void clickPlus(int idItem, int index) {
                        DBHandler db = new DBHandler(Carrinho.this);
                        int qtde = db.itemPlus(idItem);
                        ItemCarrinho item = itens.get(idItem);
                        item.setQtde(qtde);
                        itens.set(idItem, item);
                        adapter.notifyItemChanged(idItem);
                    }

                    @Override
                    public void clickMinus(int idItem, int index) {
                        DBHandler db = new DBHandler(Carrinho.this);
                        int qtde = db.itemMinus(idItem);
                        ItemCarrinho item = itens.get(idItem);
                        item.setQtde(qtde);
                        itens.set(idItem, item);
                        adapter.notifyItemChanged(idItem);
                    }
                };
                adapter.setOnClickInterface(clickInterface);
                Log.d("TAG", "Item count: " + adapter.getItemCount());
                rvItens.setAdapter(adapter);
                rvItens.setLayoutManager(new LinearLayoutManager(Carrinho.this, LinearLayoutManager.VERTICAL, false));
            }
        });
        */
        //Ingresso ingresso = new Ingresso();
        //Ingresso ingresso2 = new Ingresso();
        //ingresso.setIdTipoIngresso(1);
        //ingresso.setMeia(false);
        //ingresso.setPreco(56.96);
        //ingresso2.setIdTipoIngresso(2);
        //ingresso2.setMeia(true);
        //ingresso2.setPreco(49.99);
        //ItemCarrinho itemCarrinho = new ItemCarrinho(ingresso);
        //ItemCarrinho itemCarrinho2 = new ItemCarrinho(ingresso2);
        //Log.d("TAG", "Carrinho - Item -> " + itemCarrinho.getIdTipoIngresso() + ": " + itemCarrinho.getPreco() + " (" + itemCarrinho.getQtde() + ")");
        //Log.d("TAG", "Carrinho - Item -> " + itemCarrinho2.getIdTipoIngresso() + ": " + itemCarrinho2.getPreco() + " (" + itemCarrinho2.getQtde() + ")");
        //itemCarrinho.setQtde(1);
        //itemCarrinho2.setQtde(2);
        //
        //DBHandler dbHandler = new DBHandler(Carrinho.this);
        //dbHandler.addItem(itemCarrinho);
        //dbHandler.addItem(itemCarrinho2);

        //IngressosTask ingressosTask = new IngressosTask();
        //ingressosTask.execute();

        CuponsTask cuponsTask = new CuponsTask();
        cuponsTask.execute();

    }

    public void atualizarSubTotal() {
        double total = 0;

        for (ItemCarrinho item: itens) {
            double preco = item.getPreco();
            int qtde = item.getQtde();
            total += preco * qtde;
        }
        if (selectedCupom.getIdCupom() > 0) {
            total -= total*selectedCupom.getDesconto()/100;
        }

        DecimalFormatSymbols symbols = new DecimalFormatSymbols(new Locale("pt", "BR"));
        DecimalFormat formatter = new DecimalFormat("###,###,##0.00", symbols);
        formatter.setRoundingMode(RoundingMode.DOWN);
        String txtTotal = "R$ " + formatter.format(total);
        lblTotal.setText(txtTotal);
    }

    private class ComprarTask extends AsyncTask<Pedido, Void, Boolean> {

        @Override
        protected void onPreExecute() {
            load = ProgressDialog.show(Carrinho.this,"", "");
        }

        @Override
        protected Boolean doInBackground(Pedido... pedidos) {
            Pedido[] tests = pedidos.clone();
            Pedido pedido = tests[0];
            ArrayList<Ingresso> ingressos = pedido.getIngressos();
            for (Ingresso ingresso: ingressos) {
                Log.d("TAG", "Ingresso: " + ingresso.getIdTipoIngresso() + " - " + ingresso.isMeia() + " - inicio/validade: " + ingresso.getDataInicio() + " / " + ingresso.getDataValidade());
            }
            Utils util = new Utils();
            boolean compra = util.comprarIngresso(apiUrl, pedido);
            return compra;
        }

        @Override
        protected void onPostExecute(Boolean compra) {
            super.onPostExecute(compra);
            load.dismiss();
            if (compra) {
                DBHandler db = new DBHandler(Carrinho.this);
                db.clearCart();
                new MaterialAlertDialogBuilder(Carrinho.this)
                        .setTitle("Pagamento concluido")
                        .setIcon(R.drawable.ic_round_check_circle_24)
                        .setMessage("O pagamento foi concluido com sucesso. Verifique a página de ingressos para usá-lo quando necessário.")
                        .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                Intent it = new Intent(Carrinho.this, FragmentsPage.class);
                                it.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                startActivity(it);
                            }
                        })
                        .show();
            } else {
                new MaterialAlertDialogBuilder(Carrinho.this)
                        .setTitle("Pagamento não concluido")
                        .setIcon(R.drawable.ic_baseline_error_24)
                        .setMessage("O pagamento não foi concluido. Verifique os dados de pagamento e tente novamente")
                        .show();
            }
        }

    }

    private class CuponsTask extends AsyncTask<Void, Void, ArrayList<Cupom>> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            //Toast.makeText(Perfil.this, "Got here", Toast.LENGTH_SHORT).show();
            //Toast.makeText(Perfil.this, cliente.toString(), Toast.LENGTH_SHORT).show();
        }

        @Override
        protected ArrayList<Cupom> doInBackground(Void... voids) {
            Utils util = new Utils();
            cupons = util.getCuponsCliente(apiUrl, cliente);
            Log.d("TAG", "count cupons: " + cupons.size());
            return cupons;
        }

        @Override
        protected void onPostExecute(ArrayList<Cupom> cupons) {
            super.onPostExecute(cupons);
            ArrayAdapter<Cupom> adapter = new ArrayAdapter<Cupom>(Carrinho.this,
                    android.R.layout.simple_dropdown_item_1line, cupons);
            listCupons.setAdapter(adapter);
            listCupons.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {//selectedCupom = (Cupom) adapterView.getSelectedItem();
                    selectedCupom = (Cupom) adapterView.getItemAtPosition(position);
                    atualizarSubTotal();
                    Toast.makeText(Carrinho.this, "ItemClicked(pos: "+ position +", id: "+ id +"): " + selectedCupom.getDesconto() + "%", Toast.LENGTH_SHORT).show();
                }
            });
        }

    }

}