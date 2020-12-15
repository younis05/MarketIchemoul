/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.younes.connection;

import java.sql.Connection;
import java.sql.Statement;

/**
 *
 * @author BOUKHTACHE
 */
public class UserDB {
    public static Connection createUser(Connection conn2,Statement st2){
        try {
          
     conn2 = JavaConnected.connectionDB();
       st2 = conn2.createStatement();
                    String sql=
"CREATE TABLE IF NOT EXISTS `users` (`id` bigint(20) NOT NULL," +
"`username` varchar(50) NOT NULL DEFAULT 'admin'," +
"`password` varchar(50) NOT NULL DEFAULT 'admin') " ;
String sql0=
"INSERT INTO `users` (`id`, `username`, `password`) VALUES" +
"(1, 'admin', 'admin'),(2, 'admin', '1234'),(3, '1', '1')" ;
String sql1=
"ALTER TABLE `users` ADD PRIMARY KEY (`id`)" ;
String sql2=
"ALTER TABLE `users` MODIFY `id` bigint(20) NOT NULL AUTO_INCREMENT,AUTO_INCREMENT=3";
st2.executeUpdate(sql);st2.executeUpdate(sql0);st2.executeUpdate(sql1);
st2.executeUpdate(sql2);
//Customers
/*String sql3= "CREATE TABLE IF NOT EXISTS `customer` (`cus_id` int(20) NOT NULL,"
        + "`firstname` varchar(255) NOT NULL,`lastname` varchar(255) NOT NULL,"
        + "`phone` varchar(255) NOT NULL,`address` text NOT NULL) ";
String sql4="INSERT INTO `customer`(`cus_id`,`firstname`, `lastname`, `phone`, `address`) VALUES "
   + "1,'test','test','123456','test'";
String sql5="ALTER TABLE `customer` ADD PRIMARY KEY (`cus_id`)" ;
String sql6="ALTER TABLE `customer` MODIFY `cus_id` int(20) NOT NULL AUTO_INCREMENT=1";

st2.executeUpdate(sql3);st2.executeUpdate(sql4);st2.executeUpdate(sql5);
st2.executeUpdate(sql6);*/


System.out.println(" create user success!");             
          return conn2;   
            } catch (Exception ex) {
                 System.out.println(" user exist!");
            return null;
            }
   }

}
