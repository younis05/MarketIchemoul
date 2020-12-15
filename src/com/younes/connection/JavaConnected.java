/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.younes.connection;

import java.sql.Connection;
import java.sql.DriverManager;

/**
 *
 * @author BOUKHTACHE
 */
public class JavaConnected {
        static String root="root";
        private static final String user="root";   private static final String pass="";
       private static final String url ="jdbc:mysql://localhost/marketbenackcha?useUnicode=yes&characterEncoding=UTF-8"; 
      public static  Connection connectionDB() {
      try{    
	        Connection conn=null;
                Class.forName("com.mysql.jdbc.Driver");
                conn = DriverManager.getConnection(url,user,pass);
		  return conn;
	  }catch(Exception ex){
            ex.printStackTrace();
		  return null;
	  }
  }
}
