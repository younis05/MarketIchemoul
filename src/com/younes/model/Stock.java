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
public class Stock {
    private long p_id;
    private long cat_id;
    private String codebar;
    private String name;
    private double price;
    private int qty;
    private double total;

    public Stock() {
    }

    public Stock(long p_id, long cat_id, String codebar, String name, double price, int qty, double total) {
        this.p_id = p_id;
        this.cat_id = cat_id;
        this.codebar = codebar;
        this.name = name;
        this.price = price;
        this.qty = qty;
        this.total = total;
    }

    public long getP_id() {
        return p_id;
    }

    public void setP_id(long p_id) {
        this.p_id = p_id;
    }

    public long getCat_id() {
        return cat_id;
    }

    public void setCat_id(long cat_id) {
        this.cat_id = cat_id;
    }

    public String getCodebar() {
        return codebar;
    }

    public void setCodebar(String codebar) {
        this.codebar = codebar;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public int getQty() {
        return qty;
    }

    public void setQty(int qty) {
        this.qty = qty;
    }

    public double getTotal() {
        return total;
    }

    public void setTotal(double total) {
        this.total = total;
    }
 
}
