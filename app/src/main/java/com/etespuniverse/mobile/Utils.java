package com.etespuniverse.mobile;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.JsonReader;
import android.util.JsonWriter;
import android.util.Log;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;

public class Utils {

    public Cliente getCliente(int id) {
        String status;
        //int id = -1;
        Cliente cliente = new Cliente();

        String urlData = "cliente/"+id;
        InputStream responseBody = NetworkUtils.sendRequest(urlData);
        if (responseBody != null) {
            try {
                InputStreamReader responseBodyReader = new InputStreamReader(responseBody, "UTF-8");
                JsonReader reader = new JsonReader(responseBodyReader);
                reader.beginObject();
                while (reader.hasNext()) {
                    String name = reader.nextName();
                    if (name.equals("status")) {
                        status = reader.nextString();
                    } else if (name.equals("data")){
                        reader.beginObject();
                        while (reader.hasNext()) {
                            String key = reader.nextName();
                            Log.d("TAG", "getCliente - key:" + key);
                            if (key.equals("Nome")) {
                                String nome = reader.nextString();
                                cliente.setNome(nome);
                                Log.d("TAG", "getCliente: " + nome);
                            } else if (key.equals("Sobrenome")) {
                                String sobrenome = reader.nextString();
                                cliente.setSobrenome(sobrenome);
                                Log.d("TAG", "getCliente: " + sobrenome);
                            } else if (key.equals("Email")) {
                                String email = reader.nextString();
                                cliente.setEmail(email);
                                Log.d("TAG", "getCliente: " + email);
                            } else if (key.equals("Senha")) {
                                String senha = reader.nextString();
                                cliente.setSenha(senha);
                                Log.d("TAG", "getCliente: " + senha);
                            } else if (key.equals("DataNacimento")) {
                                String dataNascimento = reader.nextString();
                                cliente.setDataNascimentoDB(dataNascimento);
                                Log.d("TAG", "getCliente: " + dataNascimento);
                            } else if (key.equals("CPF")) {
                                String cpf = reader.nextString();
                                cliente.setCpf(cpf);
                                Log.d("TAG", "getCliente: " + cpf);
                            } else if (key.equals("idCliente")) {
                                int idCliente = reader.nextInt();
                                cliente.setId(idCliente);
                                Log.d("TAG", "getCliente: " + idCliente);
                            } else {
                                reader.skipValue();
                            }
                        }
                    } else {
                        reader.skipValue();
                    }
                }
                reader.endObject();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        String url2 = SharedData.getApiUrl()+"imagecliente/"+id;

        String urlFoto = "imagecliente/"+id;
        InputStream responseBodyFoto = NetworkUtils.sendRequest(urlFoto);
        if (responseBodyFoto != null) {
            try {
                Bitmap foto = BitmapFactory.decodeStream(responseBodyFoto);
                if (foto != null) {
                    cliente.setFoto(foto);
                }
            } catch (Exception e) {
                Log.d("TAG", "Image error: " + e.getMessage());
            }
        }

        return cliente;
    }

    public int checkLogin(String apiUrl, String email, String senha) {
        String status = "oi";
        int id = -1;
        boolean check = false;
        String url = "login";
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        try {
            String urlParameters = "email="+email+"&senha="+senha;
            byte[] postData = urlParameters.getBytes(StandardCharsets.UTF_8);
            outputStream.write(postData);
        } catch (Exception e) {
            Log.d("TAG", "checkLogin write Error: " + e.getMessage());
        }

        InputStream responseBody = NetworkUtils.sendRequest(url, outputStream);
        if (responseBody != null) {
            try {
                InputStreamReader responseBodyReader = new InputStreamReader(responseBody, "UTF-8");
                JsonReader reader = new JsonReader(responseBodyReader);
                reader.beginObject();
                while (reader.hasNext()) {
                    String name = reader.nextName();
                    if (name.equals("status")) {
                        status = reader.nextString();
                    } else if (name.equals("login")){
                        id = reader.nextInt();
                    } else {
                        reader.skipValue();
                    }
                }
                reader.endObject();
            } catch (IOException e) {
                Log.d("TAG", "checkLogin read Error: " + e.getMessage());
            }
        }
        return id;
    }

    public int getIdCliente(String apiUrl, String email) {
        int id = -1;
        String status;
        String url = apiUrl+"getuserid";
        JsonReader reader = NetworkUtils.getJSONIdClient(url, email);
        try {
            reader.beginObject();
            while (reader.hasNext()) {
                String name = reader.nextName();
                if (name.equals("status")) {
                    status = reader.nextString();
                } else if (name.equals("id")){
                    id = reader.nextInt();
                } else {
                    reader.skipValue();
                }
            }
            reader.endObject();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return id;
    }

    public ArrayList<ModelAtracao> getAtracoes() {
        String requestStatus;
        ArrayList<ModelAtracao> atracoes = new ArrayList<ModelAtracao>();

        String url = "atracoes";
        InputStream responseBody = NetworkUtils.sendRequest(url);
        if (responseBody != null) {
            try {
                InputStreamReader responseBodyReader = new InputStreamReader(responseBody);
                JsonReader reader = new JsonReader(responseBodyReader);
                reader.beginObject();
                while (reader.hasNext()) {
                    String name = reader.nextName();
                    if (name.equals("status")) {
                        requestStatus = reader.nextString();
                    } else if (name.equals("atracoes")){
                        reader.beginArray();
                        while (reader.hasNext()) {
                            reader.beginObject();
                            ModelAtracao atracao = new ModelAtracao();
                            while (reader.hasNext()) {
                                String key = reader.nextName();
                                if(key.equals("Nome")) {
                                    atracao.setNome(reader.nextString());
                                } else if (key.equals("idAtracao")) {
                                    atracao.setId(reader.nextInt());
                                } else if (key.equals("Descricao")) {
                                    atracao.setDescricao(reader.nextString());
                                } else if (key.equals("DataInauguracao")) {
                                    atracao.setDataInauguracao(reader.nextString());
                                } else if (key.equals("Localizacao")) {
                                    atracao.setLocalização(reader.nextString());
                                } else if (key.equals("idStatusAtracao")) {
                                    atracao.setStatus(reader.nextInt());
                                } else {
                                    reader.skipValue();
                                }
                            }

                            if (atracao.getId() > 0) {
                                String urlFoto = "imageatracao/"+atracao.getId();
                                InputStream responseBodyFoto = NetworkUtils.sendRequest(urlFoto);
                                if (responseBodyFoto != null) {
                                    try {
                                        Bitmap foto = BitmapFactory.decodeStream(responseBodyFoto);
                                        if (foto != null) {
                                            atracao.setFoto(foto);
                                        }
                                    } catch (Exception e) {
                                        Log.d("TAG", "Image error: " + e.getMessage());
                                    }
                                }
                            }

                            atracoes.add(atracao);
                            reader.endObject();
                        }
                        reader.endArray();
                    } else {
                        reader.skipValue();
                    }
                }
                reader.endObject();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        return atracoes;
    }

    public int cadastro(String apiUrl, Cliente cliente) {
        String status = "";
        int idCliente = -1;
        String url = "cadastro";
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        try {
            String urlParameters =
                "nome="+cliente.getNome() +
                "&sobrenome="+cliente.getSobrenome() +
                "&cpf="+cliente.getCpf() +
                "&datanascimento="+cliente.getDataNascimentoDB() +
                "&email="+cliente.getEmail() +
                "&senha="+cliente.getSenha();
            byte[] postData = urlParameters.getBytes(StandardCharsets.UTF_8);
            outputStream.write(postData);
        } catch (Exception e) {
            Log.d("TAG", "cadastro write Error: " + e.getMessage());
        }

        InputStream responseBody = NetworkUtils.sendRequest(url, outputStream);
        if (responseBody != null) {
            InputStreamReader responseBodyReader = new InputStreamReader(responseBody);
            JsonReader reader = new JsonReader(responseBodyReader);
            try {
                reader.beginObject();
                while (reader.hasNext()) {
                    String name = reader.nextName();
                    if (name.equals("status")) {
                        status = reader.nextString();
                    } else if (name.equals("cadastro")){
                        idCliente = reader.nextInt();
                    } else {
                        reader.skipValue();
                    }
                }
                reader.endObject();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return idCliente;
    }

    public boolean atualizarCliente(String apiUrl, Cliente cliente) {
        String status = "";
        int atualizar = -1;
        boolean sucesso = false;
        String url = "atualizarCliente";
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        try {
            String urlParameters =
                    "idCliente="+cliente.getId() +
                    "&nome="+cliente.getNome() +
                    "&sobrenome="+cliente.getSobrenome() +
                    "&cpf="+cliente.getCpf() +
                    "&datanascimento="+cliente.getDataNascimentoDB() +
                    "&email="+cliente.getEmail() +
                    "&senha="+cliente.getSenha();
            byte[] postData = urlParameters.getBytes(StandardCharsets.UTF_8);
            outputStream.write(postData);
        } catch (Exception e) {
            Log.d("TAG", "atualizarCliente write Error: " + e.getMessage());
        }

        InputStream responseBody = NetworkUtils.sendRequest(url, outputStream);
        if (responseBody != null) {
            InputStreamReader responseBodyReader = new InputStreamReader(responseBody);
            JsonReader reader = new JsonReader(responseBodyReader);
            try {
                reader.beginObject();
                while (reader.hasNext()) {
                    String name = reader.nextName();
                    Log.d("TAG", "name: " + name);
                    if (name.equals("status")) {
                        status = reader.nextString();
                    } else if (name.equals("atualizar")) {
                        atualizar = reader.nextInt();
                        Log.d("TAG", "Atualizar (reader): " + atualizar);
                    } else {
                        reader.skipValue();
                    }
                }
                reader.endObject();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        if (atualizar > 0) {
            sucesso = true;
        } else {
            sucesso = false;
        }
        Log.d("TAG", "atualizar (int): " + atualizar);
        Log.d("TAG", "sucesso: " + sucesso);
        Log.d("TAG", "Status: " + status);
        return sucesso;
    }

    public boolean atualizarFotoCliente(String apiUrl, Cliente cliente) {
        String status = "";
        int atualizar = -1;
        boolean sucesso = false;
        String url = "atualizarFotoCliente/"+cliente.getId();

        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        Bitmap foto = cliente.getFoto();
        if (foto != null) {
            cliente.getFoto().compress(Bitmap.CompressFormat.JPEG,40,outputStream);
        }

        InputStream responseBody = NetworkUtils.sendRequest(url, outputStream);
        if (responseBody != null) {
            InputStreamReader responseBodyReader = new InputStreamReader(responseBody);
            JsonReader reader = new JsonReader(responseBodyReader);
            try {
                reader.beginObject();
                while (reader.hasNext()) {
                    String name = reader.nextName();
                    Log.d("TAG", "name: " + name);
                    if (name.equals("status")) {
                        status = reader.nextString();
                    } else if (name.equals("atualizar")){
                        atualizar = reader.nextInt();
                        Log.d("TAG", "Atualizar (reader): " + atualizar);
                    } else {
                        reader.skipValue();
                    }
                }
                reader.endObject();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        if (atualizar > 0) {
            sucesso = true;
        } else {
            sucesso = false;
        }
        Log.d("TAG", "atualizar (int): " + atualizar);
        Log.d("TAG", "sucesso: " + sucesso);
        Log.d("TAG", "Status: " + status);
        return sucesso;
    }

    public boolean comprarIngresso(String apiUrl, Pedido pedido) {
        String status = "";
        boolean check = false;
        String url = "compra";

        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();

        if (pedido.getCliente().getId() > 0 && pedido.getIngressos().size() > 0) {
            try {
                JsonWriter writerJSON = new JsonWriter(new OutputStreamWriter(outputStream, "UTF-8"));
                writerJSON.setIndent("  ");
                writerJSON.beginObject();
                writerJSON.name("idCliente").value(pedido.getCliente().getId());
                writerJSON.name("idCupom").value(pedido.getIdCupom());
                writerJSON.name("ingressos");
                writerJSON.beginArray();
                for (Ingresso ingresso : pedido.getIngressos()) {
                    writerJSON.beginObject();
                    writerJSON.name("tipo").value(ingresso.getIdTipoIngresso());
                    writerJSON.name("dataInicio").value(ingresso.getDataInicioDB());
                    writerJSON.name("dataValidade").value(ingresso.getDataValidadeDB());
                    writerJSON.name("meia").value(ingresso.isMeia());
                    writerJSON.endObject();
                }
                writerJSON.endArray();
                writerJSON.endObject();
                writerJSON.close();
            } catch (Exception e) {
                Log.d("TAG", "comprarIngresso write Error: " + e.getMessage());
            }
        }

        InputStream responseBody = NetworkUtils.sendRequest(url, outputStream);
        if (responseBody != null) {
            InputStreamReader responseBodyReader = new InputStreamReader(responseBody);
            JsonReader reader = new JsonReader(responseBodyReader);
            try {
                Log.d("TAG", "read JSON");
                //Log.d("TAG", reader.toString());
                reader.beginObject();
                while (reader.hasNext()) {
                    //Log.d("TAG", "got next");
                    String name = reader.nextName();
                    if (name.equals("status")) {
                        status = reader.nextString();
                        Log.d("TAG", status);
                    } else if (name.equals("compra")) {
                        check = reader.nextBoolean();
                        Log.d("TAG", String.valueOf(check));
                    } else {
                        Log.d("TAG", reader.nextString());
                        reader.skipValue();
                    }
                }
                reader.endObject();
            } catch (IOException e) {
                Log.d("TAG", "Error: " + e.getMessage());
            }
        }
        return check;
    }

    public ArrayList<Ingresso> getIngressos(String apiUrl, Cliente cliente) {
        String status;
        ArrayList<Ingresso> ingressos = new ArrayList<Ingresso>();

        String url = "ingressos/"+cliente.getId();
        InputStream responseBody = NetworkUtils.sendRequest(url);
        if (responseBody != null) {
            try {
                InputStreamReader responseBodyReader = new InputStreamReader(responseBody);
                JsonReader reader = new JsonReader(responseBodyReader);
                reader.beginObject();
                while (reader.hasNext()) {
                    String name = reader.nextName();
                    if (name.equals("status")) {
                        status = reader.nextString();
                        Log.d("TAG", "Status: " + status);
                    } else if (name.equals("ingressos")) {
                        reader.beginArray();
                        while (reader.hasNext()) {
                            reader.beginObject();
                            Ingresso ingresso = new Ingresso();
                            while (reader.hasNext()) {
                                String key = reader.nextName();
                                if (key.equals("idIngresso")) {
                                    ingresso.setIdIngresso(reader.nextInt());
                                    Log.d("TAG", "idIngresso: " + ingresso.getIdIngresso());
                                } else if (key.equals("idCliente")) {
                                    ingresso.setIdCliente(reader.nextInt());
                                    Log.d("TAG", "idCliente: " + ingresso.getIdCliente());
                                } else if (key.equals("idStatusIngresso")) {
                                    ingresso.setIdStatusIngresso(reader.nextInt());
                                    Log.d("TAG", "idCliente: " + ingresso.getIdCliente());
                                } else if (key.equals("idTipoIngresso")) {
                                    ingresso.setIdTipoIngresso(reader.nextInt());
                                    Log.d("TAG", "idTipoIngresso: " + ingresso.getIdTipoIngresso());
                                } else if (key.equals("DataEmissao")) {
                                    ingresso.setDataEmissao(reader.nextString());
                                    Log.d("TAG", "DataEmissao: " + ingresso.getDataEmissao());
                                } else if (key.equals("Meia")) {
                                    String meia = reader.nextString();
                                    if (meia.equals("1"))
                                        ingresso.setMeia(true);
                                    Log.d("TAG", "Meia: " + ingresso.isMeia());
                                } else if (key.equals("DataInicio")) {
                                    ingresso.setDataInicioDB(reader.nextString());
                                    Log.d("TAG", "DataInicio: " + ingresso.getDataInicio());
                                } else if (key.equals("DataValidade")) {
                                    ingresso.setDataValidadeDB(reader.nextString());
                                    Log.d("TAG", "DataValidade: " + ingresso.getDataValidade());
                                } else if (key.equals("Preco")) {
                                    Log.d("TAG", "Preco: " + ingresso.getPreco());
                                    ingresso.setPreco(reader.nextDouble());
                                } else if (key.equals("Descricao")) {
                                    ingresso.setDescricao(reader.nextString());
                                    Log.d("TAG", "Descricao: " + ingresso.getDataValidade());
                                } else {
                                    reader.skipValue();
                                }
                            }
                            ingressos.add(ingresso);
                            reader.endObject();
                        }
                        reader.endArray();
                    } else {
                        reader.skipValue();
                    }
                }
                reader.endObject();
                reader.close();
            } catch (IOException e) {
                Log.d("TAG", "ERRO: " + e.getMessage());
            }
        }
        return ingressos;
    }

    public ArrayList<Ingresso> getTiposIngresso(String apiUrl, Cliente cliente) {
        String status;
        ArrayList<Ingresso> ingressos = new ArrayList<Ingresso>();

        String url = "tiposingresso";
        InputStream responseBody = NetworkUtils.sendRequest(url);
        if (responseBody != null) {
            try {
                InputStreamReader responsebodyReader = new InputStreamReader(responseBody);
                JsonReader reader = new JsonReader(responsebodyReader);
                reader.beginObject();
                while (reader.hasNext()) {
                    String name = reader.nextName();
                    if (name.equals("status")) {
                        status = reader.nextString();
                        Log.d("TAG", "Status: " + status);
                    } else if (name.equals("ingressos")) {
                        reader.beginArray();
                        while (reader.hasNext()) {
                            reader.beginObject();
                            Ingresso ingresso = new Ingresso();
                            while (reader.hasNext()) {
                                String key = reader.nextName();
                                if (key.equals("idTipoIngresso")) {
                                    ingresso.setIdTipoIngresso(reader.nextInt());
                                    Log.d("TAG", "idTipoIngresso: " + ingresso.getIdTipoIngresso());
                                } else if (key.equals("Preco")) {
                                    Log.d("TAG", "Preco: " + ingresso.getPreco());
                                    ingresso.setPreco(reader.nextDouble());
                                } else if (key.equals("Descricao")) {
                                    ingresso.setDescricao(reader.nextString());
                                    Log.d("TAG", "Descricao: " + ingresso.getDataValidade());
                                } else {
                                    reader.skipValue();
                                }
                            }
                            ingressos.add(ingresso);
                            reader.endObject();
                        }
                        reader.endArray();
                    } else {
                        reader.skipValue();
                    }
                }
                reader.endObject();
                reader.close();
            } catch (IOException e) {
                Log.d("TAG", "ERRO: " + e.getMessage());
            }
        }
        return ingressos;
    }

    public ArrayList<Cupom> getCuponsCliente(String apiUrl, Cliente cliente) {
        String status;
        ArrayList<Cupom> cupons = new ArrayList<Cupom>();

        String url = "cupom/"+cliente.getId();

        InputStream responseBody = NetworkUtils.sendRequest(url);
        if (responseBody != null) {
            try {
                InputStreamReader responseBodyReader = new InputStreamReader(responseBody);
                JsonReader reader = new JsonReader(responseBodyReader);
                reader.beginObject();
                while (reader.hasNext()) {
                    String name = reader.nextName();
                    if (name.equals("status")) {
                        status = reader.nextString();
                        Log.d("TAG", "Status: " + status);
                    } else if (name.equals("cupons")) {
                        reader.beginArray();
                        while (reader.hasNext()) {
                            reader.beginObject();
                            Cupom cupom = new Cupom();
                            while (reader.hasNext()) {
                                String key = reader.nextName();
                                if (key.equals("idCupom")) {
                                    cupom.setIdCupom(reader.nextInt());
                                    //Log.d("TAG", "idIngresso: " + ingresso.getIdIngresso());
                                } else if (key.equals("idTipoCupom")) {
                                    cupom.setIdTipoCupom(reader.nextInt());
                                    //Log.d("TAG", "idCliente: " + ingresso.getIdCliente());
                                } else if (key.equals("Nome")) {
                                    cupom.setNome(reader.nextString());
                                    //Log.d("TAG", "idTipoIngresso: " + ingresso.getIdTipoIngresso());
                                } else if (key.equals("Descricao")) {
                                    cupom.setDescricao(reader.nextString());
                                    //Log.d("TAG", "DataEmissao: " + ingresso.getDataEmissao());
                                } else if (key.equals("DataInicio")) {
                                    cupom.setDataInicio(reader.nextString());
                                    //Log.d("TAG", "DataInicio: " + ingresso.getDataInicio());
                                } else if (key.equals("DataValidade")) {
                                    cupom.setDataValidade(reader.nextString());
                                    //Log.d("TAG", "DataValidade: " + ingresso.getDataValidade());
                                } else if (key.equals("Desconto")) {
                                    cupom.setDesconto(reader.nextDouble());
                                    //Log.d("TAG", "DataValidade: " + ingresso.getDataValidade());
                                } else {
                                    reader.skipValue();
                                }
                            }
                            cupons.add(cupom);
                            reader.endObject();
                        }
                        reader.endArray();
                    } else {
                        reader.skipValue();
                    }
                }
                reader.endObject();
                reader.close();
            } catch (IOException e) {
                Log.d("TAG", "ERRO: " + e.getMessage());
            }
        }
        return cupons;
    }

    public Bitmap getImageCliente(String apiUrl, Cliente cliente) {
        Bitmap image = null;

        String url = "imagecliente/"+cliente.getId();
        InputStream responseBody = NetworkUtils.sendRequest(url);
        if (responseBody != null) {
            try {
                image = BitmapFactory.decodeStream(responseBody);
            } catch (Exception e) {
                Log.d("TAG", "read Image Error: " + e.getMessage());
            }
        }
        return image;
    }

    public Bitmap getImageAtracao(ModelAtracao atracao) {
        Bitmap image = null;

        String url = "imageatracao/"+atracao.getId();
        InputStream responseBody = NetworkUtils.sendRequest(url);
        if (responseBody != null) {
            try {
                image = BitmapFactory.decodeStream(responseBody);
            } catch (Exception e) {
                Log.d("TAG", "read Image Error: " + e.getMessage());
            }
        }
        return image;
    }

}
