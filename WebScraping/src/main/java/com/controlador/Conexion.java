/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.controlador;

import com.modelo.Documento;
import java.sql.*;
import org.jsoup.nodes.Document;

public class Conexion {

    public static void insertarPage() {
        Documento d = new Documento("xd");
        Connection conn = null;
        Statement stmt = null;
        try {
            try {
                Class.forName("com.mysql.jdbc.Driver");
            } catch (Exception e) {
                System.out.println(e);
            }
            conn = (Connection) DriverManager.getConnection("jdbc:mysql://localhost:3306/webscrapping", "henry", "12345");
            System.out.println("Connection is created successfully:");
            stmt = (Statement) conn.createStatement();
            String query1 = "INSERT INTO pages " + "VALUES ('http://pagina.com', 'Pagina juasjuas', '231', '34', '123', 1, 0)";
            stmt.executeUpdate(query1);
            System.out.println("Record is inserted in the table successfully..................");
        } catch (SQLException excep) {
        } catch (Exception excep) {
        } finally {
            try {
                if (stmt != null) {
                    conn.close();
                }
            } catch (SQLException se) {
            }
            try {
                if (conn != null) {
                    conn.close();
                }
            } catch (SQLException se) {
            }
        }
        System.out.println("Please check it in the MySQL Table......... ……..");
    }

    public static void main(String args[]) {
        insertarPage();
    }
}
