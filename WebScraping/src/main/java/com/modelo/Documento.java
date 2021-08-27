package com.modelo;

import com.google.gson.Gson;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import java.net.URLConnection;
import java.net.URL;
import java.util.ArrayList;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

/**
 *
 * @author Javier Charry
 */
public class Documento {

    private String title;
    private float size;
    private int nlines;
    private int nlinks;
    private int lines_size[];
    private ArrayList<String> list_links;
    public Documento(String url) {
        try {
            final Document document = Jsoup.connect(url).get();
            URLConnection con = new URL(url).openConnection();
            size = con.getContentLength() / 1024;
            title = document.title();
            lines(document.outerHtml());
            listLinks(document);            
            //listLinks(document.outerHtml());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void lines(String html) {
        String aux[]=html.split("\n");
        nlines=aux.length;
        lines_size= new int[nlines];
        for (int i = 0; i < aux.length; i++) {
            lines_size[i]=aux[i].length();
        }
 
    }

    private void listLinks(Document d) {
        list_links = new ArrayList<>();
        Elements href = d.select("a");

        for (Element element : href) {
            if (!element.attr("abs:href").equals("") && element.attr("abs:href") != null) {
                list_links.add(element.attr("abs:href"));            }
        }
        nlinks = list_links.size();
    }

    public int getNlines() {
        return nlines;
    }

    public void setNlines(int nlines) {
        this.nlines = nlines;
    }

    public int getNlinks() {
        return nlinks;
    }

    public void setNlinks(int nlinks) {
        this.nlinks = nlinks;
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
