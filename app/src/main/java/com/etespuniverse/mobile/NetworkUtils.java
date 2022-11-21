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

}
