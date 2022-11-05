package com.etespuniverse.mobile;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.JsonReader;
import android.util.JsonWriter;
import android.util.Log;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;

public class NetworkUtils {

    public static InputStream sendRequest(String urlData) {
        try {
            String url = SharedData.getApiUrl()+urlData;
            URL apiEnd = new URL(url);
            Log.d("TAG", "url given: " + url);
            int codigoRsposta;
            HttpURLConnection conn;
            InputStream responseBody = null;
            InputStreamReader responseBodyReader;
            //create conection
            conn = (HttpURLConnection) apiEnd.openConnection();
            conn.setRequestProperty("User-Agent", "my-rest-app-v0.1"); //add headers
            conn.setReadTimeout(15000);
            conn.setConnectTimeout(15000);
            conn.setRequestMethod("GET");
            //conn.setDoOutput(true);
            conn.connect();

            codigoRsposta = conn.getResponseCode();
            if (codigoRsposta < HttpURLConnection.HTTP_BAD_REQUEST) {
                Log.d("TAG", "connection ok: " + codigoRsposta);
                responseBody = conn.getInputStream();
                // Execute the request in the client
                // Grab the response
                //BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream(), "UTF-8"));
                //String txt = reader.readLine();
                //Log.d("TAG", "getJSONCliente: " + txt);
            } else {
                Log.d("TAG", "bad request: " + codigoRsposta);
                responseBody = conn.getErrorStream();
            }
            return responseBody;
        } catch (IOException e) {
            Log.d("TAG", "NetworkUtils Erro: " + e.getMessage());
        }
        return null;
    }

    public static InputStream sendRequest(String urlData, ByteArrayOutputStream outputStream) {
        try {
            String url = SharedData.getApiUrl()+urlData;
            URL apiEnd = new URL(url);
            Log.d("TAG", "url given: " + url);
            int codigoRsposta;
            HttpURLConnection conn;
            InputStream responseBody = null;
            InputStreamReader responseBodyReader;
            //create conection
            conn = (HttpURLConnection) apiEnd.openConnection();
            conn.setRequestProperty("User-Agent", "my-rest-app-v0.1"); //add headers
            conn.setReadTimeout(15000);
            conn.setConnectTimeout(15000);
            conn.setRequestMethod("POST");
            conn.setDoOutput(true);

            outputStream.writeTo(conn.getOutputStream()); //write data sent to the connection outputStream

            conn.connect();

            codigoRsposta = conn.getResponseCode();
            if (codigoRsposta < HttpURLConnection.HTTP_BAD_REQUEST) {
                Log.d("TAG", "connection ok: " + codigoRsposta);
                responseBody = conn.getInputStream();
                // Execute the request in the client
                // Grab the response
                //BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream(), "UTF-8"));
                //String txt = reader.readLine();
                //Log.d("TAG", "getJSONCliente: " + txt);
            } else {
                Log.d("TAG", "bad request: " + codigoRsposta);
                responseBody = conn.getErrorStream();
            }
            return responseBody;
        } catch (IOException e) {
            Log.d("TAG", "NetworkUtils Erro: " + e.getMessage());
        }
        return null;
    }

    public static InputStream sendRequestParams(String urlData, String urlParams) {
        try {
            String url = SharedData.getApiUrl()+urlData;
            URL apiEnd = new URL(url);
            Log.d("TAG", "url given: " + url);
            int codigoRsposta;
            HttpURLConnection conn;
            InputStream responseBody = null;
            InputStreamReader responseBodyReader;
            //create conection
            conn = (HttpURLConnection) apiEnd.openConnection();
            conn.setRequestProperty("User-Agent", "my-rest-app-v0.1"); //add headers
            conn.setReadTimeout(15000);
            conn.setConnectTimeout(15000);

            byte[] postData = urlParams.getBytes(StandardCharsets.UTF_8);

            try (DataOutputStream writer = new DataOutputStream( conn.getOutputStream())) {
                writer.write(postData);
            }

            conn.connect();

            codigoRsposta = conn.getResponseCode();
            if (codigoRsposta < HttpURLConnection.HTTP_BAD_REQUEST) {
                Log.d("TAG", "connection ok: " + codigoRsposta);
                responseBody = conn.getInputStream();
                // Execute the request in the client
                // Grab the response
                //BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream(), "UTF-8"));
                //String txt = reader.readLine();
                //Log.d("TAG", "getJSONCliente: " + txt);
            } else {
                Log.d("TAG", "bad request: " + codigoRsposta);
                responseBody = conn.getErrorStream();
            }
            return responseBody;
        } catch (IOException e) {
            Log.d("TAG", "NetworkUtils Erro: " + e.getMessage());
        }
        return null;
    }

    public static InputStream sendRequestStream(String urlData, String urlParams) {
        try {
            String url = SharedData.getApiUrl()+urlData;
            URL apiEnd = new URL(url);
            Log.d("TAG", "url given: " + url);
            int codigoRsposta;
            HttpURLConnection conn;
            InputStream responseBody = null;
            InputStreamReader responseBodyReader;
            //create conection
            conn = (HttpURLConnection) apiEnd.openConnection();
            conn.setRequestProperty("User-Agent", "my-rest-app-v0.1"); //add headers
            conn.setReadTimeout(15000);
            conn.setConnectTimeout(15000);

            byte[] postData = urlParams.getBytes(StandardCharsets.UTF_8);

            try (DataOutputStream writer = new DataOutputStream( conn.getOutputStream())) {
                writer.write(postData);
            }

            conn.connect();

            codigoRsposta = conn.getResponseCode();
            if (codigoRsposta < HttpURLConnection.HTTP_BAD_REQUEST) {
                Log.d("TAG", "connection ok: " + codigoRsposta);
                responseBody = conn.getInputStream();
                // Execute the request in the client
                // Grab the response
                //BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream(), "UTF-8"));
                //String txt = reader.readLine();
                //Log.d("TAG", "getJSONCliente: " + txt);
            } else {
                Log.d("TAG", "bad request: " + codigoRsposta);
                responseBody = conn.getErrorStream();
            }
            return responseBody;
        } catch (IOException e) {
            Log.d("TAG", "NetworkUtils Erro: " + e.getMessage());
        }
        return null;
    }
/*
    public static JsonReader getJSONAtualizarFotoCliente(String url, Cliente cliente) {
        try {
            URL apiEnd = new URL(url);
            Log.d("TAG", "URL (network): " + apiEnd.toString());
            int codigoRsposta;
            HttpURLConnection conn;
            InputStream responseBody = null;
            InputStreamReader responseBodyReader;
            //create conection
            conn = (HttpURLConnection) apiEnd.openConnection();
            conn.setRequestProperty("User-Agent", "my-rest-app-v0.1"); //add headers
            conn.setReadTimeout(15000);
            conn.setConnectTimeout(15000);
            conn.setRequestMethod("POST");
            conn.setDoOutput(true);

            //String urlParameters =
            //        "idCliente="+cliente.getId() +
            //                "&nome="+cliente.getNome() +
            //                "&sobrenome="+cliente.getSobrenome() +
            //                "&cpf="+cliente.getCpf() +
            //                "&datanascimento="+cliente.getDataNascimentoDB() +
            //                "&email="+cliente.getEmail() +
            //                "&senha="+cliente.getSenha();
            //byte[] postData = urlParameters.getBytes(StandardCharsets.UTF_8);
//
            //try (DataOutputStream writer = new DataOutputStream( conn.getOutputStream())) {
            //    writer.write(postData);
            //}

            //ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            //foto.compress(Bitmap.CompressFormat.JPEG,40,outputStream);
            //byte[] byteImage_photo = outputStream.toByteArray();

            Bitmap foto = cliente.getFoto();

            if (foto != null)
                foto.compress(Bitmap.CompressFormat.JPEG,40,conn.getOutputStream());

            conn.connect();

            codigoRsposta = conn.getResponseCode();
            if (codigoRsposta < HttpURLConnection.HTTP_BAD_REQUEST) {
                responseBody = conn.getInputStream();
            } else {
                responseBody = conn.getErrorStream();
            }

            responseBodyReader = new InputStreamReader(responseBody, "UTF-8");
            JsonReader jsonReader = new JsonReader(responseBodyReader);
            return jsonReader;

        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
*/
    public static JsonReader getJSONCliente(String url) {
        try {
            //URL apiEnd = new URL("http://192.168.0.9/web-tcc/api/cliente/2");
            URL apiEnd = new URL(url);
            Log.d("TAG", "url given: " + url);
            Log.d("TAG", "url correct: " + "http://192.168.0.9/web-tcc/api/cliente/2");
            int codigoRsposta;
            HttpURLConnection conn;
            InputStream responseBody = null;
            InputStreamReader responseBodyReader;
            //create conection
            conn = (HttpURLConnection) apiEnd.openConnection();
            conn.setRequestProperty("User-Agent", "my-rest-app-v0.1"); //add headers
            conn.setReadTimeout(15000);
            conn.setConnectTimeout(15000);
            conn.connect();

            codigoRsposta = conn.getResponseCode();
            if (codigoRsposta < HttpURLConnection.HTTP_BAD_REQUEST) {
                Log.d("TAG", "connection ok: " + codigoRsposta);
                responseBody = conn.getInputStream();
                // Execute the request in the client
                // Grab the response
                //BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream(), "UTF-8"));
                //String txt = reader.readLine();
                //Log.d("TAG", "getJSONCliente: " + txt);
            } else {
                Log.d("TAG", "bad request: " + codigoRsposta);
                responseBody = conn.getErrorStream();
            }

            responseBodyReader = new InputStreamReader(responseBody, "UTF-8");
            JsonReader jsonReader = new JsonReader(responseBodyReader);
            return jsonReader;

        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static JsonReader getJSONLogin(String url, String email, String senha) {
        try {
            URL apiEnd = new URL(url);
            Log.d("TAG", "url given: " + url);
            int codigoRsposta;
            HttpURLConnection conn;
            InputStream responseBody = null;
            InputStreamReader responseBodyReader;
            //create conection
            conn = (HttpURLConnection) apiEnd.openConnection();
            conn.setRequestProperty("User-Agent", "my-rest-app-v0.1"); //add headers
            conn.setReadTimeout(15000);
            conn.setConnectTimeout(15000);
            conn.setRequestMethod("POST");

            Log.d("TAG", "data received: " + email + " / " + senha);

            String urlParameters = "email="+email+"&senha="+senha;
            byte[] postData = urlParameters.getBytes(StandardCharsets.UTF_8);

            try (DataOutputStream writer = new DataOutputStream( conn.getOutputStream())) {
                writer.write(postData);
            }

            conn.connect();

            codigoRsposta = conn.getResponseCode();
            if (codigoRsposta < HttpURLConnection.HTTP_BAD_REQUEST) {
                responseBody = conn.getInputStream();
            } else {
                responseBody = conn.getErrorStream();
            }

            responseBodyReader = new InputStreamReader(responseBody, "UTF-8");
            JsonReader jsonReader = new JsonReader(responseBodyReader);
            return jsonReader;

        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static JsonReader getJSONIdClient(String url, String email) {
        try {
            URL apiEnd = new URL(url);
            int codigoRsposta;
            HttpURLConnection conn;
            InputStream responseBody = null;
            InputStreamReader responseBodyReader;
            //create conection
            conn = (HttpURLConnection) apiEnd.openConnection();
            conn.setRequestProperty("User-Agent", "my-rest-app-v0.1"); //add headers
            conn.setReadTimeout(15000);
            conn.setConnectTimeout(15000);
            conn.setRequestMethod("POST");
            conn.setDoOutput(true);

            String urlParameters = "email="+email;
            byte[] postData = urlParameters.getBytes(StandardCharsets.UTF_8);

            try (DataOutputStream writer = new DataOutputStream( conn.getOutputStream())) {
                writer.write(postData);
            }

            conn.connect();

            codigoRsposta = conn.getResponseCode();
            if (codigoRsposta < HttpURLConnection.HTTP_BAD_REQUEST) {
                responseBody = conn.getInputStream();
            } else {
                responseBody = conn.getErrorStream();
            }

            responseBodyReader = new InputStreamReader(responseBody, "UTF-8");
            JsonReader jsonReader = new JsonReader(responseBodyReader);
            return jsonReader;

        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static JsonReader getJSONAtracoes(String url) {
        try {
            URL apiEnd = new URL(url);
            int codigoRsposta;
            HttpURLConnection conn;
            InputStream responseBody = null;
            InputStreamReader responseBodyReader;
            //create conection
            conn = (HttpURLConnection) apiEnd.openConnection();
            conn.setRequestProperty("User-Agent", "my-rest-app-v0.1"); //add headers
            conn.setReadTimeout(15000);
            conn.setConnectTimeout(15000);
            conn.setRequestMethod("GET");
            //conn.setDoOutput(true);

            conn.connect();

            codigoRsposta = conn.getResponseCode();
            if (codigoRsposta < HttpURLConnection.HTTP_BAD_REQUEST) {
                responseBody = conn.getInputStream();
            } else {
                responseBody = conn.getErrorStream();
            }

            responseBodyReader = new InputStreamReader(responseBody, "UTF-8");
            JsonReader jsonReader = new JsonReader(responseBodyReader);
            return jsonReader;

        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static JsonReader getJSONCadastro(String url, Cliente cliente) {
        try {
            URL apiEnd = new URL(url);
            int codigoRsposta;
            HttpURLConnection conn;
            InputStream responseBody = null;
            InputStreamReader responseBodyReader;
            //create conection
            conn = (HttpURLConnection) apiEnd.openConnection();
            conn.setRequestProperty("User-Agent", "my-rest-app-v0.1"); //add headers
            conn.setReadTimeout(15000);
            conn.setConnectTimeout(15000);
            conn.setRequestMethod("POST");
            conn.setDoOutput(true);

            String urlParameters =
                "nome="+cliente.getNome() +
                "&sobrenome="+cliente.getSobrenome() +
                "&cpf="+cliente.getCpf() +
                "&datanascimento="+cliente.getDataNascimentoDB() +
                "&email="+cliente.getEmail() +
                "&senha="+cliente.getSenha();
            byte[] postData = urlParameters.getBytes(StandardCharsets.UTF_8);

            try (DataOutputStream writer = new DataOutputStream( conn.getOutputStream())) {
                writer.write(postData);
            }

            conn.connect();

            codigoRsposta = conn.getResponseCode();
            if (codigoRsposta < HttpURLConnection.HTTP_BAD_REQUEST) {
                responseBody = conn.getInputStream();
            } else {
                responseBody = conn.getErrorStream();
            }

            responseBodyReader = new InputStreamReader(responseBody, "UTF-8");
            JsonReader jsonReader = new JsonReader(responseBodyReader);
            return jsonReader;

        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static JsonReader getJSONAtualizarCliente(String url, Cliente cliente) {
        try {
            URL apiEnd = new URL(url);
            Log.d("TAG", "URL (network): " + apiEnd.toString());
            int codigoRsposta;
            HttpURLConnection conn;
            InputStream responseBody = null;
            InputStreamReader responseBodyReader;
            //create conection
            conn = (HttpURLConnection) apiEnd.openConnection();
            conn.setRequestProperty("User-Agent", "my-rest-app-v0.1"); //add headers
            conn.setReadTimeout(15000);
            conn.setConnectTimeout(15000);
            conn.setRequestMethod("POST");
            conn.setDoOutput(true);

            String urlParameters =
                    "idCliente="+cliente.getId() +
                    "&nome="+cliente.getNome() +
                    "&sobrenome="+cliente.getSobrenome() +
                    "&cpf="+cliente.getCpf() +
                    "&datanascimento="+cliente.getDataNascimentoDB() +
                    "&email="+cliente.getEmail() +
                    "&senha="+cliente.getSenha();
            byte[] postData = urlParameters.getBytes(StandardCharsets.UTF_8);

            try (DataOutputStream writer = new DataOutputStream( conn.getOutputStream())) {
                writer.write(postData);
            }

            conn.connect();

            codigoRsposta = conn.getResponseCode();
            if (codigoRsposta < HttpURLConnection.HTTP_BAD_REQUEST) {
                responseBody = conn.getInputStream();
            } else {
                responseBody = conn.getErrorStream();
            }

            responseBodyReader = new InputStreamReader(responseBody, "UTF-8");
            JsonReader jsonReader = new JsonReader(responseBodyReader);
            return jsonReader;

        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static JsonReader getJSONAtualizarFotoCliente(String url, Cliente cliente) {
        try {
            URL apiEnd = new URL(url);
            Log.d("TAG", "URL (network): " + apiEnd.toString());
            int codigoRsposta;
            HttpURLConnection conn;
            InputStream responseBody = null;
            InputStreamReader responseBodyReader;
            //create conection
            conn = (HttpURLConnection) apiEnd.openConnection();
            conn.setRequestProperty("User-Agent", "my-rest-app-v0.1"); //add headers
            conn.setReadTimeout(15000);
            conn.setConnectTimeout(15000);
            conn.setRequestMethod("POST");
            conn.setDoOutput(true);

            //String urlParameters =
            //        "idCliente="+cliente.getId() +
            //                "&nome="+cliente.getNome() +
            //                "&sobrenome="+cliente.getSobrenome() +
            //                "&cpf="+cliente.getCpf() +
            //                "&datanascimento="+cliente.getDataNascimentoDB() +
            //                "&email="+cliente.getEmail() +
            //                "&senha="+cliente.getSenha();
            //byte[] postData = urlParameters.getBytes(StandardCharsets.UTF_8);
//
            //try (DataOutputStream writer = new DataOutputStream( conn.getOutputStream())) {
            //    writer.write(postData);
            //}



            Bitmap foto = cliente.getFoto();

            if (foto != null) {
                ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
                cliente.getFoto().compress(Bitmap.CompressFormat.JPEG,40,outputStream);
                outputStream.writeTo(conn.getOutputStream());
            }




            //if (foto != null)
            //    foto.compress(Bitmap.CompressFormat.JPEG,40,conn.getOutputStream());

            conn.connect();

            codigoRsposta = conn.getResponseCode();
            if (codigoRsposta < HttpURLConnection.HTTP_BAD_REQUEST) {
                responseBody = conn.getInputStream();
            } else {
                responseBody = conn.getErrorStream();
            }

            responseBodyReader = new InputStreamReader(responseBody, "UTF-8");
            JsonReader jsonReader = new JsonReader(responseBodyReader);
            return jsonReader;

        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static JsonReader getJSONCompra(String url, Pedido pedido) {
        try {
            Log.d("TAG", "network");
            URL apiEnd = new URL(url);
            Log.d("TAG", "URL: " + apiEnd.toString());
            int codigoRsposta;
            HttpURLConnection conn;
            InputStream responseBody = null;
            InputStreamReader responseBodyReader;
            //create conection
            conn = (HttpURLConnection) apiEnd.openConnection();
            conn.setRequestProperty("User-Agent", "my-rest-app-v0.1"); //add headers
            conn.setRequestProperty("Content-Type", "application/json");
            conn.setReadTimeout(15000);
            conn.setConnectTimeout(15000);
            conn.setRequestMethod("POST");
            conn.setDoOutput(true);

            //StringWriter sw = new StringWriter();
            //JsonWriter writerJSON = new JsonWriter(sw);
            JsonWriter writerJSON = new JsonWriter(new OutputStreamWriter(conn.getOutputStream(), "UTF-8"));
            writerJSON.setIndent("  ");
            writerJSON.beginObject();
                writerJSON.name("idCliente").value(pedido.getCliente().getId());
                writerJSON.name("idCupom").value(pedido.getIdCupom());
                writerJSON.name("ingressos");
                    writerJSON.beginArray();
                        for(Ingresso ingresso: pedido.getIngressos()) {
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
            Log.d("TAG", "write JSON");
            //Log.d("TAG", sw.toString());

            //String jsonString = sw.toString();

            conn.connect();
            Log.d("TAG", "connect");
            /*
            String urlParameters = "PostData="+jsonString;
            byte[] postData = urlParameters.getBytes(StandardCharsets.UTF_8);

            try (DataOutputStream writer = new DataOutputStream( conn.getOutputStream())) {
                writer.write(postData);
            }
            */
            codigoRsposta = conn.getResponseCode();
            if (codigoRsposta < HttpURLConnection.HTTP_BAD_REQUEST) {
                responseBody = conn.getInputStream();
                Log.d("TAG", "request ok: " + codigoRsposta);
            } else {
                responseBody = conn.getErrorStream();
                Log.d("TAG", "bad request: " + codigoRsposta);
            }

            responseBodyReader = new InputStreamReader(responseBody, "UTF-8");
            JsonReader jsonReader = new JsonReader(responseBodyReader);
            return jsonReader;

        } catch (IOException e) {
            Log.d("TAG", e.getMessage());
        }
        return null;
    }

    public static JsonReader getJSONIngressosCliente(String url) {
        try {
            URL apiEnd = new URL(url);
            int codigoRsposta;
            HttpURLConnection conn;
            InputStream responseBody = null;
            InputStreamReader responseBodyReader;
            //create conection
            conn = (HttpURLConnection) apiEnd.openConnection();
            conn.setRequestProperty("User-Agent", "my-rest-app-v0.1"); //add headers
            conn.setReadTimeout(15000);
            conn.setConnectTimeout(15000);
            conn.setRequestMethod("GET");
            //conn.setDoOutput(true);

            conn.connect();

            codigoRsposta = conn.getResponseCode();
            if (codigoRsposta < HttpURLConnection.HTTP_BAD_REQUEST) {
                responseBody = conn.getInputStream();
            } else {
                responseBody = conn.getErrorStream();
            }

            responseBodyReader = new InputStreamReader(responseBody, "UTF-8");
            JsonReader jsonReader = new JsonReader(responseBodyReader);
            return jsonReader;

        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static JsonReader getJSONTiposIngresso(String url) {
        try {
            URL apiEnd = new URL(url);
            Log.d("TAG", "url given: " + url);
            int codigoRsposta;
            HttpURLConnection conn;
            InputStream responseBody = null;
            InputStreamReader responseBodyReader;
            //create conection
            conn = (HttpURLConnection) apiEnd.openConnection();
            conn.setRequestProperty("User-Agent", "my-rest-app-v0.1"); //add headers
            conn.setReadTimeout(15000);
            conn.setConnectTimeout(15000);
            conn.setRequestMethod("GET");
            //conn.setDoOutput(true);

            conn.connect();

            codigoRsposta = conn.getResponseCode();
            if (codigoRsposta < HttpURLConnection.HTTP_BAD_REQUEST) {
                Log.d("TAG", "connection ok: " + codigoRsposta);
                responseBody = conn.getInputStream();
            } else {
                Log.d("TAG", "bad request: " + codigoRsposta);
                responseBody = conn.getErrorStream();
            }

            responseBodyReader = new InputStreamReader(responseBody, "UTF-8");
            JsonReader jsonReader = new JsonReader(responseBodyReader);
            return jsonReader;

        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static JsonReader getJSONCuponsCliente(String url) {
        try {
            URL apiEnd = new URL(url);
            int codigoRsposta;
            HttpURLConnection conn;
            InputStream responseBody = null;
            InputStreamReader responseBodyReader;
            //create conection
            conn = (HttpURLConnection) apiEnd.openConnection();
            conn.setRequestProperty("User-Agent", "my-rest-app-v0.1"); //add headers
            conn.setReadTimeout(15000);
            conn.setConnectTimeout(15000);
            conn.setRequestMethod("GET");
            //conn.setDoOutput(true);

            conn.connect();

            codigoRsposta = conn.getResponseCode();
            if (codigoRsposta < HttpURLConnection.HTTP_BAD_REQUEST) {
                responseBody = conn.getInputStream();
            } else {
                responseBody = conn.getErrorStream();
            }

            responseBodyReader = new InputStreamReader(responseBody, "UTF-8");
            JsonReader jsonReader = new JsonReader(responseBodyReader);
            return jsonReader;

        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static Bitmap getImage(String url) {
        try {
            Log.d("TAG", "network");
            URL apiEnd = new URL(url);
            Log.d("TAG", "URL: " + apiEnd.toString());
            int codigoRsposta;
            HttpURLConnection conn;
            InputStream responseBody = null;
            InputStreamReader responseBodyReader;
            //create conection
            conn = (HttpURLConnection) apiEnd.openConnection();
            conn.setRequestProperty("User-Agent", "my-rest-app-v0.1"); //add headers
            conn.setRequestProperty("Content-Type", "application/json");
            //conn.setReadTimeout(15000);
            //conn.setConnectTimeout(15000);
            conn.setRequestMethod("POST");
            conn.setDoOutput(true);

            //StringWriter sw = new StringWriter();
            Log.d("TAG", "write JSON");
            //Log.d("TAG", sw.toString());

            //String jsonString = sw.toString();

            conn.connect();
            Log.d("TAG", "connect");
            /*
            String urlParameters = "PostData="+jsonString;
            byte[] postData = urlParameters.getBytes(StandardCharsets.UTF_8);

            try (DataOutputStream writer = new DataOutputStream( conn.getOutputStream())) {
                writer.write(postData);
            }
            */
            Bitmap imageBitmap = null;
            int length = -2;
            codigoRsposta = conn.getResponseCode();
            if (codigoRsposta < HttpURLConnection.HTTP_BAD_REQUEST) {
                responseBody = conn.getInputStream();
                Log.d("TAG", "request ok: " + codigoRsposta);
                //BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream(), "UTF-8"));
                //StringBuilder sb = new StringBuilder();
                //String line = null;
                //while ((line = reader.readLine()) != null) {
                //    sb.append(line + "\n");
                //}
                //String data = sb.toString();
                ////data.getBytes(StandardCharsets.UTF_8);
                //try {
                //    byte[] imageBytes = Base64.decode(data, Base64.NO_WRAP);
                //} catch (Exception e) {
                //    Log.d("TAG", "ERROR: " + e.getMessage());
                //}
                String lenght = conn.getHeaderField("Content-Length");
                Log.d("TAG", "getImage - length: " + lenght);
                imageBitmap = BitmapFactory.decodeStream(conn.getInputStream());
                //Log.d("TAG", "input data: " + data.toString());
                //Log.d("TAG", "data lenght: " + data.length());
                //Log.d("TAG", "data bytes lenght: " + data.getBytes().length);
                //Log.d("TAG", "data bytes: " + data.getBytes().toString());
                //Log.d("TAG", "image lenght: " + imageBytes.length);
            } else {
                responseBody = conn.getErrorStream();
                Log.d("TAG", "bad request: " + codigoRsposta);
            }

            //responseBodyReader = new InputStreamReader(responseBody, "UTF-8");
            return imageBitmap;

        } catch (IOException e) {
            Log.d("TAG", e.getMessage());
        }
        return null;
    }

}
