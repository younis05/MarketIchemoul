/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.younes.model;

/**
 *
 * @author BOUKHTACHE
 */
public class ImageProduct {
    private long img_id;
    private long p_id;
    private String path;
    private byte[] img;

    public ImageProduct() {
        
    }

    public ImageProduct(long img_id, long p_id, String path, byte[] img) {
        this.img_id = img_id;
        this.p_id = p_id;
        this.path = path;
        this.img = img;
    }

    public long getImg_id() {
        return img_id;
    }

    public void setImg_id(long img_id) {
        this.img_id = img_id;
    }

    public long getP_id() {
        return p_id;
    }

    public void setP_id(long p_id) {
        this.p_id = p_id;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public byte[] getImg() {
        return img;
    }

    public void setImg(byte[] img) {
        this.img = img;
    }
    
}
