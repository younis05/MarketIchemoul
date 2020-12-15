/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.younes.connection;

import com.younes.model.Categore;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author BOUKHTACHE
 */
 
public class CategoreDB {
    public static int save(Categore cat){	
    int st = 0;	
try {
	String sql = "INSERT INTO `categore`(`catname`) VALUES (?)";
	Connection con = JavaConnected.connectionDB();
	PreparedStatement pst = (PreparedStatement) con.prepareStatement(sql);
	pst.setString(1, cat.getCat_name());
	st = pst.executeUpdate();	
	con.close();	
	} catch (Exception e) {
		e.printStackTrace();
	}
	    return st;
	}
public static int update(Categore cat){	
    int st = 0;	
try {
	String sql = "UPDATE `categore` SET `catname`=? WHERE `cat_id`=?";
	Connection con = JavaConnected.connectionDB();
	PreparedStatement pst = (PreparedStatement) con.prepareStatement(sql);
	pst.setString(1, cat.getCat_name());
	pst.setLong(2, cat.getCat_id());		
	st = pst.executeUpdate();	
	con.close();		
	} catch (Exception e) {e.printStackTrace();}
		return st;
	}
public static int delete(int id){	
    int st = 0;	
try {  
	String sql = "DELETE FROM `categore` WHERE `cat_id`=?";
	Connection con = JavaConnected.connectionDB();
	PreparedStatement pst = (PreparedStatement) con.prepareStatement(sql);
	pst.setLong(1, id);			
	st = pst.executeUpdate();		
	con.close();	
	} catch (Exception e) { e.printStackTrace();}
		return st;
	}
public static Categore getCategore(int id){
    Categore cat=new Categore();
    try{
       String sql = "SELECT * FROM `categore` WHERE `cat_id`=?";
	Connection con = JavaConnected.connectionDB();
	PreparedStatement pst = (PreparedStatement) con.prepareStatement(sql);
	pst.setLong(1, id);
        ResultSet rst = pst.executeQuery();
        while(rst.next()){
            cat.setCat_id(rst.getLong(1));
            cat.setCat_name(rst.getString(2));
        }
        con.close();
    }catch (Exception e) {e.printStackTrace();}
    return cat;
}
public static List<Categore> getAllCategore(){
    List<Categore> list=new ArrayList<Categore>();
    try{
        String sql = "SELECT * FROM `categore` WHERE 1";
	Connection con = JavaConnected.connectionDB();
	PreparedStatement pst = (PreparedStatement) con.prepareStatement(sql);
        ResultSet rst = pst.executeQuery();
        while(rst.next()){
            list.add(new Categore(rst.getLong(1),rst.getString(2)));
        }
        con.close();
    }catch (Exception e) {e.printStackTrace();}
    return list;
}



}
