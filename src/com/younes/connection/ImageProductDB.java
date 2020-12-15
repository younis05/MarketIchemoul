/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.younes.connection;

import com.younes.model.ImageProduct;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author BOUKHTACHE
 */
public class ImageProductDB {
       public static int save(ImageProduct imgp,byte[] pict){	
    int st = 0;	
try {
	String sql = "INSERT INTO `productimage`(`p_id`,`path`,`picture`) VALUES (?,?,?)";
	Connection con = JavaConnected.connectionDB();
	PreparedStatement pst = (PreparedStatement) con.prepareStatement(sql);
	pst.setLong(1,imgp.getP_id());
        pst.setString(2,imgp.getPath());
        pst.setBytes(3,pict);
	st = pst.executeUpdate();	
	con.close();	
	} catch (Exception e) {
		e.printStackTrace();
	}
	    return st;
	}
/*public static int update(ImageProduct imgp,String filename){	
    int st = 0;	
    File image = new File(filename);
    ByteArrayOutputStream bos = null;
try (FileInputStream fis = new FileInputStream(image)) {
	String sql = "UPDATE `productimage` SET `p_id`=?,`path`=?,"
                + "`picture`=? WHERE `p_id`=?";
        bos = new ByteArrayOutputStream();
                byte[] buf = new byte[1024];
                for (int readNum; (readNum = fis.read(buf)) != -1;) {
                    bos.write(buf, 0, readNum);
                     }
	Connection con = JavaConnected.connectionDB();
	PreparedStatement pst = (PreparedStatement) con.prepareStatement(sql);
	pst.setLong(1, imgp.getP_id());
        pst.setString(2, imgp.getPath());
        pst.setBytes(3, bos.toByteArray());
	pst.setLong(4, imgp.getP_id());		
	st = pst.executeUpdate();	
	con.close(); bos.close();		
	} catch (Exception e) {e.printStackTrace();}
		return st;
	}*/
public static int delete(int id){	
    int st = 0;	
try {  
	String sql = "DELETE FROM `productimage` WHERE `img_id`=?";
	Connection con = JavaConnected.connectionDB();
	PreparedStatement pst = (PreparedStatement) con.prepareStatement(sql);
	pst.setLong(1, id);			
	st = pst.executeUpdate();		
	con.close();	
	} catch (Exception e) { e.printStackTrace();}
		return st;
	}
public static ImageProduct getImageProduct(int id){
    ImageProduct imgp=new ImageProduct();
    try{
       String sql = "SELECT * FROM `productimage` WHERE `img_id`=?";
	Connection con = JavaConnected.connectionDB();
	PreparedStatement pst = (PreparedStatement) con.prepareStatement(sql);
	pst.setLong(1, id);
        ResultSet rst = pst.executeQuery();
        while(rst.next()){
            imgp.setImg_id(rst.getLong(1)); imgp.setP_id(rst.getLong(2));
            imgp.setPath(rst.getString(3));imgp.setImg(rst.getBytes(4));
        }
        con.close();
    }catch (Exception e) {e.printStackTrace();}
    return imgp;
}
public static List<ImageProduct> getAllImageProduct(){
    List<ImageProduct> list=new ArrayList<ImageProduct>();
    try{
        String sql = "SELECT * FROM `productimage` WHERE 1";
	Connection con = JavaConnected.connectionDB();
	PreparedStatement pst = (PreparedStatement) con.prepareStatement(sql);
        ResultSet rst = pst.executeQuery();
        while(rst.next()){
            list.add(new ImageProduct(rst.getLong(1),rst.getLong(2),rst.getString(3),rst.getBytes(4)));
        }
        con.close();
    }catch (Exception e) {e.printStackTrace();}
    return list;
}

}
