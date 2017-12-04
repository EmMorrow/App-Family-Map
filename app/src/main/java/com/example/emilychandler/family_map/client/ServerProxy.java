package com.example.emilychandler.family_map.client;

import android.util.Log;

import com.example.emilychandler.family_map.data.EventResult;
import com.example.emilychandler.family_map.data.LoginRequest;
import com.example.emilychandler.family_map.data.LoginResult;
import com.example.emilychandler.family_map.data.Person;
import com.example.emilychandler.family_map.data.PersonResult;
import com.example.emilychandler.family_map.data.RegisterResult;
import com.example.emilychandler.family_map.data.User;
import com.google.gson.Gson;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by emilychandler on 11/11/17.
 */

public class ServerProxy {
    private String serverHost, serverPort;

    public ServerProxy(String serverHost, String serverPort){
        this.serverHost = serverHost;
        this.serverPort = serverPort;
    }

    public LoginResult login(LoginRequest request) {
        try {
            URL url = new URL("http://" + serverHost + ":" + serverPort + "/user/login");

            Gson gson = new Gson();
            String reqData = gson.toJson(request);

            String response = send(reqData, "POST", null, url);

            return gson.fromJson(response, LoginResult.class);
        }
        catch(IOException e) {
            Log.e("HttpClient",e.getMessage(),e);
        }
        return null;
    }

    public RegisterResult register(User request) {
        try {
            URL url = new URL("http://" + serverHost + ":" + serverPort + "/user/register");

            Gson gson = new Gson();
            String reqData = gson.toJson(request);

            String response = send(reqData, "POST", null, url);

            return gson.fromJson(response, RegisterResult.class);
        }
        catch(IOException e) {
            Log.e("HttpClient",e.getMessage(),e);
        }
        return null;
    }

    public PersonResult getPeople(String auth) {
        try {
            URL url = new URL("http://" + serverHost + ":" + serverPort + "/person");

            String response = send(null, "GET", auth, url);

            Gson gson = new Gson();
            return gson.fromJson(response, PersonResult.class);
        }
        catch(IOException e) {
            Log.e("HttpClient",e.getMessage(),e);
        }
        return null;
    }

    public EventResult getEvents(String auth) {
        try {
            URL url = new URL("http://" + serverHost + ":" + serverPort + "/event");

            String response = send(null, "GET", auth, url);

            Gson gson = new Gson();
            return gson.fromJson(response, EventResult.class);
        }
        catch(IOException e) {
            Log.e("HttpClient",e.getMessage(),e);
        }
        return null;
    }

    private String send(String body, String method, String header, URL url){
        try {
            HttpURLConnection http = (HttpURLConnection) url.openConnection();
            http.setRequestMethod(method);
            if(method.equals("GET")) {
                http.setDoOutput(false);
                http.addRequestProperty("Authorization",header);
            }
//            if(header != null) {
//                System.out.println("UGH");
//                http.setRequestProperty("Authorization",header);
//            }
            http.connect();
            System.out.println("HERE " + method);
            if (method.equals("POST")){
                System.out.println("also");
                OutputStream reqBody = http.getOutputStream();
                writeString(body, reqBody);
                reqBody.close();
             }

            if (http.getResponseCode() == HttpURLConnection.HTTP_OK) {
                System.out.println("Login Successful");
                InputStream responseBody = http.getInputStream();

                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                byte[] buffer = new byte[1024];
                int length = 0;
                while ((length = responseBody.read(buffer)) != -1) {
                    baos.write(buffer, 0, length);
                }

                String responseBodyData = baos.toString();
                return responseBodyData;
            }
            else {
                System.out.println("error");
                return null;
            }
        }
        catch(IOException e) {
            Log.e("ServerProxy", e.getMessage(),e);
        }
        return null;
    }

    private static void writeString(String str, OutputStream os) throws IOException {
        OutputStreamWriter sw = new OutputStreamWriter(os);
        sw.write(str);
        sw.flush();
    }
}
