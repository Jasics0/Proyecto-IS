package com.modelo;

import com.google.gson.Gson;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import java.net.URLConnection;
import java.net.URL;

/**
 *
 * @author Javier Charry
 */
public class Documento {

    private String title;
    private float size;

    public Documento(String url) {
        try {
            final Document document = Jsoup.connect(url).get();
            URLConnection con = new URL(url).openConnection();
            size = con.getContentLength() / 1024;
            title = document.title();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public float getSize() {
        return size;
    }

    public void setSize(float size) {
        this.size = size;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String documentJSON() {
        final String json = new Gson().toJson(this);
        return json;
    }
    

}
