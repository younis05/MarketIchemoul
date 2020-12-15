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
public class CreateCustomerDB {
    public static Connection createCustomers(Connection conn3,Statement st3){

        try {
     conn3 = JavaConnected.connectionDB();
       st3 = conn3.createStatement();
String sql= "CREATE TABLE IF NOT EXISTS `customer` (`cus_id` bigint(20) NOT NULL,"
        + "`firstname` varchar(255) NOT NULL,`lastname` varchar(255) NOT NULL,"
        + "`phone` varchar(255) NOT NULL,`address` text NOT NULL) ";
String sql1="ALTER TABLE `customer` ADD PRIMARY KEY (`cus_id`)" ;
String sql2="ALTER TABLE `customer` MODIFY `cus_id` bigint(20) NOT NULL AUTO_INCREMENT=1";
String sql0="INSERT INTO `customer`(`cus_id`,`firstname`, `lastname`, `phone`, `address`) VALUES "
   + "1,'test','test','123456','test'";
st3.executeUpdate(sql);st3.executeUpdate(sql0);
st3.executeUpdate(sql1);st3.executeUpdate(sql2);

System.out.println(" create customers success!");             
          return conn3;   
            } catch (Exception ex) {
                 System.out.println(" customers exist!");
            return null;
            }finally {
            try {
               st3.close(); conn3.close();
            } catch (Exception ex) { }
    }
   }

}
