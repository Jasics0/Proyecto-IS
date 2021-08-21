package com.modelo;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import java.net.URLConnection;
import java.net.URL;

/**
 *
 * @author Javier Charry
 */
public class Documento {

    private float size;
    private String tittle;

    public Documento(String url) {
        try {
            final Document document=Jsoup.connect(url).get();
            URLConnection con = new URL(url).openConnection();
            size = con.getContentLength();
            tittle=document.title();
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

    public String getTittle() {
        return tittle;
    }

    public void setTittle(String tittle) {
        this.tittle = tittle;
    }
    
    

}
