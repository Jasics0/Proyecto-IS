package com.modelo;

import com.google.gson.Gson;
import java.net.HttpURLConnection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import java.net.URLConnection;
import java.net.URL;
import java.text.Normalizer;
import java.util.LinkedList;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class Documento {

    private String title;
    private float size;
    private int nlines, nlinks, lines_size[];
    private String list_links[];
    private String links_types[];
    private boolean is_form, is_login;
    private String words_concurrency[];

    public Documento(String url) {
        try {
            final Document document = Jsoup.connect(url).get();
            URLConnection con = new URL(url).openConnection();
            size = con.getContentLength() / 1024;
            title = document.title();
            lines(document.outerHtml());
            listLinks(document);
            verifyLink();
            getForms(document);
            concurrency(document);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void lines(String html) {
        String aux[] = html.split("\n");
        nlines = aux.length;
        lines_size = new int[nlines];
        for (int i = 0; i < aux.length; i++) {
            lines_size[i] = aux[i].length();
            if (aux[i].contains("<form")) {
                is_form = true;
            }
        }
    }

    private void listLinks(Document d) {
        LinkedList<String> aux = new LinkedList<>();
        Elements href = d.select("a");

        for (Element element : href) {
            if (!element.attr("abs:href").equals("") && element.attr("abs:href") != null) {
                aux.add(element.attr("abs:href"));
            }
        }
        list_links = new String[aux.size()];
        list_links = aux.toArray(list_links);
        nlinks = list_links.length;
        links_types = new String[nlinks]; //1. html - 2.pdf - 3.otro
        for (int i = 0; i < list_links.length; i++) {
            if (list_links[i].contains(".html")) {
                links_types[i] = "0-";
            } else if (list_links[i].contains(".pdf")) {
                links_types[i] = "1-";
            } else {
                links_types[i] = "2-";
            }
        }
    }

    private void getForms(Document d) {
        Elements form = d.select("form");

        for (Element element : form) {
            if (element.attr("id").toLowerCase().contains("login")
                    || element.attr("name").toLowerCase().contains("login") //
                    || element.attr("action").toLowerCase().contains("login")
                    || element.attr("onsubmit").toLowerCase().contains("login")) {//
                is_login = true;
            }
        }
    }

    private String[] filterText(String txt) {
        LinkedList<String> words = new LinkedList<>();
        txt = txt.toLowerCase();
        txt = Normalizer.normalize(txt, Normalizer.Form.NFD);
        txt = txt.replaceAll("[\\p{InCombiningDiacriticalMarks}]", "");
        String aux = "";
        for (int i = 0; i < txt.length(); i++) {
            if (((int) txt.charAt(i) >= 97 && (int) txt.charAt(i) <= 122) || (int) txt.charAt(i) == 32) {
                aux += txt.charAt(i);
            } else {
                aux += " ";
            }
        }
        String aux2 = "";
        for (int i = 0; i <= aux.length(); i++) {
            if (i == aux.length()) {
                if (!aux2.equals("")) {
                    words.add(aux2);
                }
                break;
            }
            if (aux.charAt(i) != ' ') {
                aux2 += aux.charAt(i);
            } else {
                if (!aux2.equals("")) {
                    words.add(aux2);
                }
                aux2 = "";
            }
        }

        String palabras[] = new String[words.size()];
        palabras = words.toArray(palabras);

        return palabras;
    }

    private void concurrency(Document d) {
        String palabras[] = filterText(d.text());
        LinkedList<String> words = new LinkedList<>();
        LinkedList<Integer> times = new LinkedList<>();
        int aux = 0;
        for (int i = 0; i < palabras.length; i++) {
            if (!words.contains(palabras[i])) {
                words.add(palabras[i]);
                times.add(1);
            } else {
                aux = words.indexOf(palabras[i]);
                times.set(aux, (times.get(aux) + 1));
            }
        }

        words_concurrency = new String[words.size()];

        for (int i = 0; i < words.size(); i++) {
            words_concurrency[i] = words.get(i) + "-" + times.get(i);

        }
    }

    private boolean verify(String url) {
        try {
            HttpURLConnection.setFollowRedirects(false);
            HttpURLConnection con = (HttpURLConnection) new URL(url)
                    .openConnection();
            con.setRequestMethod("HEAD");
            return (con.getResponseCode() == HttpURLConnection.HTTP_OK);
        } catch (Exception e) {
            return false;
        }
    }

    private void verifyLink() {

        for (int i = 0; i < list_links.length; i++) {

            try {
                if (verify(list_links[i])) {
                    links_types[i] += "1";

                } else {
                    links_types[i] += "0";
                }

            } catch (Exception e) {
                links_types[i] += "0";
            }
        }
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

    public int[] getLines_size() {
        return lines_size;
    }

    public void setLines_size(int[] lines_size) {
        this.lines_size = lines_size;
    }

    public String[] getList_links() {
        return list_links;
    }

    public void setList_links(String[] list_links) {
        this.list_links = list_links;
    }

    public String[] getLinks_types() {
        return links_types;
    }

    public void setLinks_types(String[] links_types) {
        this.links_types = links_types;
    }

    public String documentJSON() {
        final String json = new Gson().toJson(this);
        return json;
    }

}
