package com.controlador;

import com.google.gson.Gson;
import com.modelo.Documento;

public class Request extends Thread {

    private String json;
    private String url;

    public Request(String url) {
        this.url = url;
    }

    @Override
    public void run() {
        json = JSONof(url);
    }

    public String JSONof(String url) { //Devuelve un String formateado JSON de un link proporcionado
        return new Documento(url).documentJSON();
    }

    public Documento JSONtoDocument(String json) { //Devuelve un objeto tipo documento de un String formateado JSON
        return new Gson().fromJson(json, Documento.class);
    }

    public String getJson() {
        return json;
    }
}
