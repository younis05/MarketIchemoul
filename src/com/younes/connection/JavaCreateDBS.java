/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.younes.connection;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;

/**
 *
 * @author BOUKHTACHE
 */
public class JavaCreateDBS {
     private static final String user="root";   private static final String pass="";
     private static final String url ="jdbc:mysql://localhost";
      public static Connection connectionDBS() { 
        String sqld = "CREATE DATABASE  marketbenackcha  CHARACTER SET 'utf8' COLLATE utf8_general_ci"; 
        Statement st = null;
        Connection conn = null;
        try {
            Class.forName("com.mysql.jdbc.Driver");
             conn = DriverManager.getConnection(url,user,pass);
            st = conn.createStatement();
            st.executeUpdate(sqld);
            // JOptionPane.showMessageDialog(null, "success!");
            System.out.println(" create db success!");
            return conn;
        } catch (Exception ex) {

          System.out.println("  db exist!");

            return null;
        } finally {
            try {
                st.close(); conn.close();
            } catch (Exception ex) {
            }
        }
    }
}
