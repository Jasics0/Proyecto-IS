package com.modelo;

import com.google.gson.Gson;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import java.net.URLConnection;
import java.net.URL;
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
    private String list_links[];

    public Documento(String url) {
        try {
            final Document document = Jsoup.connect(url).get();
            URLConnection con = new URL(url).openConnection();
            size = con.getContentLength() / 1024;
            title = document.title();
            nlines = lines(document.outerHtml());
            nlinks = links(document);
            list_links = new String[nlinks];
            listLinks(document);
            //listLinks(document.outerHtml());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private int lines(String html) {
        return html.split("\n").length;
    }

    private void listLinks(Document d) {
        Elements href = d.select("a");
        int i = 0;
        for (Element element : href) {
            list_links[i] = element.attr("abs:href");
            i++;
        }

//        int count = 0;
//        String h[] = html.split("\n");
//        String aux = "";
//        for (int i = 0; i < h.length; i++) {
//            if (h[i].contains("href")) {
//                list_links[count] = "";
//                for (int j = 0; j < h[i].length(); j++) {
//                    aux = "" + h[i].charAt(j) + h[i].charAt(j + 1) + h[i].charAt(j + 2) + h[i].charAt(j + 3);
//
//                    if (aux.equals("href")) {
//
//                        for (int k = (j + 6); k < h[i].length(); k++) {
//                            if (h[i].charAt(k) != '"') {
//                                list_links[count] += h[i].charAt(k);
//                            } else {
//                                k = h[i].length();
//                            }
//                        }
//                        j = h[i].length();
//                    }
//
//                    aux = "";
//                }
//                count++;
//            }
//        }
    }

    private int links(Document d) {
        return d.select("a").size();
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
