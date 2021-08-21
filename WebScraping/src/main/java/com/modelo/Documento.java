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
    private String title;

    public Documento(String url) {
        try {
            final Document document=Jsoup.connect(url).get();
            URLConnection con = new URL(url).openConnection();
            size = con.getContentLength();
            title=document.title();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    public static void main(String[] args) {
        Documento d = new Documento("https://www.youtube.com/");
        System.out.println(d.getTitle());
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
    
    

}
