/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.younes.connection;

import com.younes.model.Categore;
import com.younes.model.Stock;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JTextField;

/**
 *
 * @author BOUKHTACHE
 */
public class StockDB {
    public static int save(Stock stk){	
    int st = 0;	
try {
	String sql = "INSERT INTO `stock`(`p_id`,`cat_id`,`barcode`,`p_name`,"
                + "`price`,`qty`,`total`) VALUES (?,?,?,?,?,?,?)";
	Connection con = JavaConnected.connectionDB();
	PreparedStatement pst = (PreparedStatement) con.prepareStatement(sql);
	pst.setLong(1,stk.getP_id());
        pst.setLong(2,stk.getCat_id());
        pst.setString(3,stk.getCodebar());
        pst.setString(4,stk.getName());
        pst.setDouble(5,stk.getPrice());
        pst.setInt(6,stk.getQty());
        pst.setDouble(7,stk.getTotal());
	st = pst.executeUpdate();	
	con.close();	
	} catch (Exception e) {
		e.printStackTrace();
	}
	    return st;
	}
public static int update(Stock stk){	
    int st = 0;	
try {
	String sql = "UPDATE `stock` SET `cat_id`=?,`barcode`=?,`p_name`=?,`price`=?,"
                + "`qty`=?,`total`=? WHERE `p_id`=?";
	Connection con = JavaConnected.connectionDB();
	PreparedStatement pst = (PreparedStatement) con.prepareStatement(sql);
	pst.setLong(1, stk.getCat_id());
        pst.setString(2, stk.getCodebar());
        pst.setString(3, stk.getName());
        pst.setDouble(4, stk.getPrice());
        pst.setInt(5, stk.getQty());
        pst.setDouble(6, stk.getTotal());
        pst.setLong(7, stk.getP_id());
	st = pst.executeUpdate();	
	con.close();		
	} catch (Exception e) {e.printStackTrace();}
		return st;
	}
public static int delete(int id){	
    int st = 0;	
try {  
	String sql = "DELETE FROM `stock` WHERE `p_id`=?";
	Connection con = JavaConnected.connectionDB();
	PreparedStatement pst = (PreparedStatement) con.prepareStatement(sql);
	pst.setLong(1, id);			
	st = pst.executeUpdate();		
	con.close();	
	} catch (Exception e) { e.printStackTrace();}
		return st;
	}
public static Stock getStock(int id){
    Stock stk=new Stock();
    try{
       String sql = "SELECT * FROM `stock` WHERE `p_id`=?";
	Connection con = JavaConnected.connectionDB();
	PreparedStatement pst = (PreparedStatement) con.prepareStatement(sql);
	pst.setLong(1, id);
        ResultSet rst = pst.executeQuery();
        while(rst.next()){
            stk.setP_id(rst.getLong(1));
            stk.setCat_id(rst.getLong(2));
            stk.setCodebar(rst.getString(3));
            stk.setName(rst.getString(4));
            stk.setPrice(rst.getDouble(5));
            stk.setQty(rst.getInt(6));
            stk.setTotal(rst.getDouble(7));
        }
        con.close();
    }catch (Exception e) {e.printStackTrace();}
    return stk;
}
public static List<Stock> getAllStock(){
    List<Stock> list=new ArrayList<Stock>();
    try{
        String sql = "SELECT * FROM `stock` WHERE 1";
	Connection con = JavaConnected.connectionDB();
	PreparedStatement pst = (PreparedStatement) con.prepareStatement(sql);
        ResultSet rst = pst.executeQuery();
        while(rst.next()){
            list.add(new Stock(rst.getLong(1),rst.getLong(2),rst.getString(3),
            rst.getString(4),rst.getDouble(5),rst.getInt(6),rst.getDouble(7)));
        }
        con.close();
    }catch (Exception e) {e.printStackTrace();}
    return list;
}
public static List<Stock> getAllStock(String txtsearch){ 
    List<Stock> list=new ArrayList<Stock>();
    try{  
        String sql = "SELECT * FROM `stock` WHERE concat(`p_id`,`barcode`,`p_name`) LIKE '%"+txtsearch+"%'";
	Connection con = JavaConnected.connectionDB();
	PreparedStatement pst = (PreparedStatement) con.prepareStatement(sql);
        ResultSet rst = pst.executeQuery();
        while(rst.next()){
            list.add(new Stock(rst.getLong(1),rst.getLong(2),rst.getString(3),
            rst.getString(4),rst.getDouble(5),rst.getInt(6),rst.getDouble(7)));
        }
        con.close();
    }catch (Exception e) {e.printStackTrace();}
    return list;
}
  public static void getPID(JTextField txt){
      try {
            String sql = "select max(p_id) from stock";
            Connection con = JavaConnected.connectionDB();
	  PreparedStatement pst = (PreparedStatement) con.prepareStatement(sql);
          ResultSet rst = pst.executeQuery();
            if (rst.next() == true) {
                int d=rst.getInt("max(p_id)");
                if(d==0){
                  txt.setText("1");
                }else{
                d++;
                txt.setText(String.valueOf(d));  
                }
              }
            con.close();
        } catch (Exception ex) {
           ex.printStackTrace();
        } 
  }

  
}
