/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.younes.connection;

import com.younes.model.Admin;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author BOUKHTACHE
 */
public class AdminDB {
    
public static int save(Admin adm){	
    int st = 0;	
try {
	String sql = "INSERT INTO `users`(`username`, `password`) VALUES (?,?)";
	Connection con = JavaConnected.connectionDB();
	PreparedStatement pst = (PreparedStatement) con.prepareStatement(sql);
	pst.setString(1, adm.getUsername());
	pst.setString(2, adm.getPassword());		
	st = pst.executeUpdate();	
	con.close();	
	} catch (Exception e) {
		e.printStackTrace();
	}
	    return st;
	}
public static int update(Admin adm){	
    int st = 0;	
try {
	String sql = "UPDATE `users` SET `username`=?,`password`=? WHERE `id`=?";
	Connection con = JavaConnected.connectionDB();
	PreparedStatement pst = (PreparedStatement) con.prepareStatement(sql);
	pst.setString(1, adm.getUsername());
	pst.setString(2, adm.getPassword());		
	pst.setLong(3, adm.getId());		
	st = pst.executeUpdate();	
	con.close();		
	} catch (Exception e) {e.printStackTrace();}
		return st;
	}
public static int delete(int id){	
    int st = 0;	
try {  
	String sql = "DELETE FROM `users` WHERE `id`=?";
	Connection con = JavaConnected.connectionDB();
	PreparedStatement pst = (PreparedStatement) con.prepareStatement(sql);
	pst.setLong(1, id);			
	st = pst.executeUpdate();
			
	con.close();
			
	} catch (Exception e) { e.printStackTrace();}
		return st;
	}
public static Admin getAdmin(int id){
    Admin adm=new Admin();
    try{
       String sql = "SELECT * FROM `users` WHERE `id`=?";
	Connection con = JavaConnected.connectionDB();
	PreparedStatement pst = (PreparedStatement) con.prepareStatement(sql);
	pst.setLong(1, id);
        ResultSet rst = pst.executeQuery();
        while(rst.next()){
            adm.setId(rst.getLong(1));
            adm.setUsername(rst.getString(2));
            adm.setPassword(rst.getString(3));
        }
        con.close();
    }catch (Exception e) {e.printStackTrace();}
    return adm;
}
public static List<Admin> getAllAdmin(){
    List<Admin> list=new ArrayList<Admin>();
    try{
        String sql = "SELECT * FROM `users` WHERE 1";
	Connection con = JavaConnected.connectionDB();
	PreparedStatement pst = (PreparedStatement) con.prepareStatement(sql);
        ResultSet rst = pst.executeQuery();
        while(rst.next()){
            list.add(new Admin(rst.getLong(1),rst.getString(2),rst.getString(3)));
        }
        con.close();
    }catch (Exception e) {e.printStackTrace();}
    return list;
}



//  String sql="select * from invoices where concat(inv_id, n_inv) LIKE '%"+txt+"%'"; //||n_inv||datetime
// String sql="select * from invoices where datetime between'"+dt1+"'and'"+dt2+"'"; 

}
