package com.controlador;

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet(name = "AjaxServlet", urlPatterns = {"/AjaxServlet"})
public class AjaxServlet extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        try ( PrintWriter out = response.getWriter()) {
            /* TODO output your page here. You may use following sample code. */
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Servlet AjaxServlet</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet AjaxServlet at " + request.getContextPath() + "</h1>");
            out.println("</body>");
            out.println("</html>");
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String link = request.getParameter("link");
        if (link != null) {
            String links[] = link.split(",");
            Request n[] = new Request[links.length];

            for (int i = 0; i < n.length; i++) {
                n[i] = new Request(links[i]);
            }

            for (int i = 0; i < n.length; i++) {
                n[i].start();
            }

            boolean a = true;
            while (a) {
                for (int i = 0; i < n.length; i++) {
                    if (n[i].isAlive()) {
                        break;
                    } else if (i == n.length - 1) {
                        a = false;
                    }

                }
            }
            String json_final = "{\"Pages\":[";
            for (int i = 0; i < n.length; i++) {
                json_final += n[i].getJson() + ",";

            }
            json_final += "]}";
            response.setContentType("text/html");
            response.getWriter().write(json_final);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    @Override
    public String getServletInfo() {
        return "Short description";
    }

}
