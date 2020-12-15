/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.younes.connection;


import com.younes.model.Customer;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author BOUKHTACHE
 */
public class CustomerDB {
    
public static int save(Customer cus){	
    int st = 0;	
try {
	String sql = "INSERT INTO `customer`(`firstname`, `lastname`,"
                + "`phone`,`address`) VALUES (?,?,?,?)";
	Connection con = JavaConnected.connectionDB();
	PreparedStatement pst = (PreparedStatement) con.prepareStatement(sql);
	pst.setString(1, cus.getFirstname());
	pst.setString(2, cus.getLastname());	
        pst.setString(3, cus.getPhone());
        pst.setString(4, cus.getAddress());
	st = pst.executeUpdate();	
	con.close();	
	} catch (Exception e) {
		e.printStackTrace();
	}
	    return st;
	}
public static int update(Customer cus){	
    int st = 0;	
try {
	String sql = "UPDATE `customer` SET `firstname`=?,`lastname`=?,`phone`=?"
                + ",`address`=? WHERE `cus_id`=?";
	Connection con = JavaConnected.connectionDB();
	PreparedStatement pst = (PreparedStatement) con.prepareStatement(sql);
	pst.setString(1, cus.getFirstname());
	pst.setString(2, cus.getLastname());		
	pst.setString(3, cus.getPhone());
        pst.setString(4, cus.getAddress());
        pst.setLong(5, cus.getCus_id());
	st = pst.executeUpdate();	
	con.close();		
	} catch (Exception e) {e.printStackTrace();}
		return st;
	}
public static int delete(int id){	
    int st = 0;	
try {  
	String sql = "DELETE FROM `customer` WHERE `cus_id`=?";
	Connection con = JavaConnected.connectionDB();
	PreparedStatement pst = (PreparedStatement) con.prepareStatement(sql);
	pst.setLong(1, id);			
	st = pst.executeUpdate();
			
	con.close();
			
	} catch (Exception e) { e.printStackTrace();}
		return st;
	}
public static Customer getCustomer(int id){
    Customer cus=new Customer();
    try{
       String sql = "SELECT * FROM `customer` WHERE `cus_id`=?";
	Connection con = JavaConnected.connectionDB();
	PreparedStatement pst = (PreparedStatement) con.prepareStatement(sql);
	pst.setLong(1, id);
        ResultSet rst = pst.executeQuery();
        while(rst.next()){
            cus.setCus_id(rst.getLong(1));
            cus.setFirstname(rst.getString(2));
            cus.setLastname(rst.getString(3));
            cus.setPhone(rst.getString(4));
            cus.setAddress(rst.getString(5));
        }
        con.close();
    }catch (Exception e) {e.printStackTrace();}
    return cus;
}
public static List<Customer> getAllCustomer(){
    List<Customer> list=new ArrayList<Customer>();
    try{
        String sql = "SELECT * FROM `customer` WHERE 1";
	Connection con = JavaConnected.connectionDB();
	PreparedStatement pst = (PreparedStatement) con.prepareStatement(sql);
        ResultSet rst = pst.executeQuery();
        while(rst.next()){
            list.add(new Customer(rst.getLong(1),rst.getString(2),rst.getString(3),
            rst.getString(4),rst.getString(5)));
        }
        con.close();
    }catch (Exception e) {e.printStackTrace();}
    return list;
}
//  String sql="select * from invoices where concat(inv_id, n_inv) LIKE '%"+txt+"%'"; //||n_inv||datetime
// String sql="select * from invoices where datetime between'"+dt1+"'and'"+dt2+"'"; 

}
