package com.controlador;

import com.google.gson.Gson;
import com.modelo.Documento;
import java.util.Properties;

public class Request {

    public String JSONof(String url) { //Devuelve un String formateado JSON de un link proporcionado
        return new Documento(url).documentJSON();
    }

    public Documento JSONtoDocument(String json) { //Devuelve un objeto tipo documento de un String formateado JSON
        return new Gson().fromJson(json, Documento.class);
    }

    public Properties JSONPropierties(String json) { //Devuelve un objeto tipo Propierties de un String formateado JSON. Propierties sirve para extraer las propiedades de un JSON
        return new Gson().fromJson(json, Properties.class);
    }

    public static void main(String[] args) {
        Request r = new Request();
        System.out.println(r.JSONof("http://localhost:8090"));
    }
}
