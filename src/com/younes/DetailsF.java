package com.younes;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */




import com.younes.connection.JavaConnected;
import com.younes.model.Customer;
import com.younes.util.TexttableCenter;
import java.awt.ComponentOrientation;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Calendar;
import java.util.HashMap;
import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.swing.plaf.basic.BasicInternalFrameUI;
import javax.swing.table.DefaultTableModel;
import net.proteanit.sql.DbUtils;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.design.JasperDesign;
import net.sf.jasperreports.engine.xml.JRXmlLoader;
import net.sf.jasperreports.view.JasperViewer;

/**
 *
 * @author BOUKHTACHE
 */
public class DetailsF extends javax.swing.JInternalFrame {

    /**
     * Creates new form HomeF
     */
      ResultSet rst=null;PreparedStatement pst=null;
    Connection conn=null;Calendar timer=Calendar.getInstance();
   private JFrame frame;
   Customer cus=null;
  TexttableCenter tblcenter=new TexttableCenter();

  
    public DetailsF() {
        initComponents();
        conn=JavaConnected.connectionDB();
        applyComponentOrientation(ComponentOrientation.RIGHT_TO_LEFT);
        this.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));
        BasicInternalFrameUI bi=(BasicInternalFrameUI)this.getUI();
        bi.setNorthPane(null);
        showInfoSales();
        loadNameSales();
        MaxTotalSales();
       SwingUtilities.invokeLater(new Runnable(){
            @Override
            public void run() {
               jtotal.requestFocus();
            } 
        });   
    }
 private void showInfoSales(){
        
        String sql="select name as 'المنتوج',total as 'المبلغ',datetime as 'التاريخ' from backup_sales ORDER BY datetime ASC"; 
        try {
            pst=conn.prepareStatement(sql);
            rst=pst.executeQuery();
            jtableinfo.setModel(DbUtils.resultSetToTableModel(rst));
            jtableinfo.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
            jtableinfo.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
            for(int i=0;i<jtableinfo.getColumnCount();i++){
                 jtableinfo.getColumnModel().getColumn(i).setCellRenderer(tblcenter);
             }
        } catch (Exception ex) {
           ex.printStackTrace();
        }finally{
          try {
           pst.close();rst.close();
          } catch (Exception ex) {
           ex.printStackTrace();
        }  
        }
    }
 private void searchSalesDates(){
      
      String dt1=((JTextField)jdate1.getDateEditor().getUiComponent()).getText();
      String dt2=((JTextField)jdate2.getDateEditor().getUiComponent()).getText();

       DefaultTableModel table=new DefaultTableModel();
        table.addColumn("المنتوج ");table.addColumn("المبلغ ");
        table.addColumn("التاريخ");
        String sql="select * from backup_sales where datetime between'"+dt1+"'and'"+dt2+"' ORDER BY datetime ASC"; 
        try {
            pst=conn.prepareStatement(sql);
            rst=pst.executeQuery();
            while(rst.next()){
                table.addRow(new Object[]{rst.getString(7),rst.getString(10),
                    rst.getString(12)});
            }
            jtableinfo.setModel(table);
            jtableinfo.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
            jtableinfo.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
            for(int i=0;i<jtableinfo.getColumnCount();i++){
                 jtableinfo.getColumnModel().getColumn(i).setCellRenderer(tblcenter);
             }
        } catch (Exception ex) {
           ex.printStackTrace();
        }
    }
 private void MaxTotalSales(){
          try{
            String sql="select sum(total) from backup_sales";
            pst=conn.prepareStatement(sql);
            rst=pst.executeQuery();
           while(rst.next()){
                     String nto=rst.getString("sum(total)"); 
                      if(nto==null){
               
                jgtotal.setText("0.00");
                }else{
                 double c=Double.parseDouble(nto);
                 
                 jgtotal.setText(String.format("%,.2f دج", c));
                }
                }
         }catch(Exception ex){
            JOptionPane.showMessageDialog(null, ex);
        }
     }
 private void searchTotalSalesDates(){
       String dt1=((JTextField)jdate1.getDateEditor().getUiComponent()).getText();
      String dt2=((JTextField)jdate2.getDateEditor().getUiComponent()).getText();  
    try{
            String sql="select sum(total) from backup_sales where datetime between'"+dt1+"'and'"+dt2+"' ORDER BY datetime ASC";
            pst=conn.prepareStatement(sql);
            rst=pst.executeQuery();
           while(rst.next()){
                     String nto=rst.getString("sum(total)"); 
                      if(nto==null){
               
                jtotal.setText("0.00");
                }else{
                 double c=Double.parseDouble(nto);
                 
                 jtotal.setText(String.format("%,.2f", c));
                }
                }
         }catch(Exception ex){
            JOptionPane.showMessageDialog(null, ex);
        }
     }
 private void loadNameSales(){
        try {
            String sql="select p_name from stock  ORDER BY p_name ASC";
            pst = conn.prepareStatement(sql);
            rst = pst.executeQuery();
            jname.removeAllItems();
            while (rst.next()) {
                   String name=rst.getString("p_name");
                   jname.addItem(name);
              }
           
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
 private void searchTotalSalesNames(){
       String dt1=((JTextField)jdate1.getDateEditor().getUiComponent()).getText();
      String dt2=((JTextField)jdate2.getDateEditor().getUiComponent()).getText();  
    try{
            String sql="select sum(total) from backup_sales "
     + "where name=? and datetime between'"+dt1+"'and'"+dt2+"' ORDER BY datetime ASC";
            pst=conn.prepareStatement(sql);
            pst.setString(1, jname.getSelectedItem().toString());
            rst=pst.executeQuery();
           while(rst.next()){
                     String nto=rst.getString("sum(total)"); 
                      if(nto==null){
               
                jtotal.setText("0.00");
                }else{
                 double c=Double.parseDouble(nto);
                 
                 jtotal.setText(String.format("%,.2f", c));
                }
                }
         }catch(Exception ex){
            JOptionPane.showMessageDialog(null, ex);
        }
     }
 private void searchSalesByNames(){
      
      String dt1=((JTextField)jdate1.getDateEditor().getUiComponent()).getText();
      String dt2=((JTextField)jdate2.getDateEditor().getUiComponent()).getText();
      
       DefaultTableModel table=new DefaultTableModel();
        table.addColumn("المنتوج ");table.addColumn("المبلغ");
        table.addColumn("التاريخ");
        String sql="select * from backup_sales "
          + "where name=? and datetime between'"+dt1+"'and'"+dt2+"' ORDER BY datetime ASC"; 
        try {
            pst=conn.prepareStatement(sql);
            pst.setString(1, jname.getSelectedItem().toString());
            rst=pst.executeQuery();
            while(rst.next()){
                table.addRow(new Object[]{rst.getString(7),rst.getString(10),
                    rst.getString(12)});
            }
            jtableinfo.setModel(table);
            jtableinfo.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
            jtableinfo.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
            for(int i=0;i<jtableinfo.getColumnCount();i++){
                 jtableinfo.getColumnModel().getColumn(i).setCellRenderer(tblcenter);
             }
        } catch (Exception ex) {
          ex.printStackTrace();
        }
    }
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jpanelheader = new javax.swing.JPanel();
        jPanel2 = new javax.swing.JPanel();
        jPanel3 = new javax.swing.JPanel();
        jPanel4 = new javax.swing.JPanel();
        jLabel4 = new javax.swing.JLabel();
        jpanelfooter = new javax.swing.JPanel();
        jpanelcontent = new javax.swing.JPanel();
        jPanel1 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jtotal = new javax.swing.JTextField();
        jsearch = new javax.swing.JButton();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jdate1 = new com.toedter.calendar.JDateChooser();
        jdate2 = new com.toedter.calendar.JDateChooser();
        jname = new javax.swing.JComboBox<>();
        jrefresh = new javax.swing.JButton();
        jsearchname = new javax.swing.JButton();
        jptable = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jtableinfo = new javax.swing.JTable();
        jgtotal = new javax.swing.JLabel();
        jdetailsales1 = new javax.swing.JButton();
        jdetailsales2 = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();

        setPreferredSize(new java.awt.Dimension(750, 600));

        jpanelheader.setBackground(new java.awt.Color(57, 59, 156));
        jpanelheader.setPreferredSize(new java.awt.Dimension(750, 50));
        jpanelheader.setLayout(new java.awt.BorderLayout());

        jPanel2.setBackground(new java.awt.Color(57, 59, 156));
        jPanel2.setPreferredSize(new java.awt.Dimension(200, 50));

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 200, Short.MAX_VALUE)
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 50, Short.MAX_VALUE)
        );

        jpanelheader.add(jPanel2, java.awt.BorderLayout.LINE_END);

        jPanel3.setBackground(new java.awt.Color(57, 59, 156));
        jPanel3.setPreferredSize(new java.awt.Dimension(200, 50));

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 200, Short.MAX_VALUE)
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 50, Short.MAX_VALUE)
        );

        jpanelheader.add(jPanel3, java.awt.BorderLayout.LINE_START);

        jPanel4.setBackground(new java.awt.Color(57, 59, 156));
        jPanel4.setPreferredSize(new java.awt.Dimension(350, 50));
        jPanel4.setLayout(new java.awt.BorderLayout());

        jLabel4.setBackground(new java.awt.Color(57, 59, 156));
        jLabel4.setFont(new java.awt.Font("Dialog", 1, 24)); // NOI18N
        jLabel4.setForeground(new java.awt.Color(255, 255, 255));
        jLabel4.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel4.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Pictures/icons8_money_32px.png"))); // NOI18N
        jLabel4.setText("بيانات المبيعات");
        jLabel4.setOpaque(true);
        jPanel4.add(jLabel4, java.awt.BorderLayout.CENTER);

        jpanelheader.add(jPanel4, java.awt.BorderLayout.CENTER);

        getContentPane().add(jpanelheader, java.awt.BorderLayout.PAGE_START);

        jpanelfooter.setBackground(new java.awt.Color(204, 204, 204));
        jpanelfooter.setPreferredSize(new java.awt.Dimension(750, 50));

        javax.swing.GroupLayout jpanelfooterLayout = new javax.swing.GroupLayout(jpanelfooter);
        jpanelfooter.setLayout(jpanelfooterLayout);
        jpanelfooterLayout.setHorizontalGroup(
            jpanelfooterLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 748, Short.MAX_VALUE)
        );
        jpanelfooterLayout.setVerticalGroup(
            jpanelfooterLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 50, Short.MAX_VALUE)
        );

        getContentPane().add(jpanelfooter, java.awt.BorderLayout.PAGE_END);

        jpanelcontent.setBackground(new java.awt.Color(204, 204, 204));
        jpanelcontent.setPreferredSize(new java.awt.Dimension(750, 450));
        jpanelcontent.setLayout(new java.awt.BorderLayout());

        jPanel1.setBackground(new java.awt.Color(204, 204, 204));
        jPanel1.setPreferredSize(new java.awt.Dimension(750, 450));
        jPanel1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel2.setBackground(new java.awt.Color(204, 204, 204));
        jLabel2.setFont(new java.awt.Font("Dialog", 1, 18)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(0, 0, 0));
        jLabel2.setText("التاريخ2:");
        jLabel2.setOpaque(true);
        jPanel1.add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(670, 70, 60, 30));

        jLabel3.setBackground(new java.awt.Color(204, 204, 204));
        jLabel3.setFont(new java.awt.Font("Dialog", 1, 18)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(0, 0, 0));
        jLabel3.setText("المبلغ:");
        jLabel3.setOpaque(true);
        jPanel1.add(jLabel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(670, 170, 50, 30));

        jtotal.setEditable(false);
        jtotal.setBackground(new java.awt.Color(204, 204, 204));
        jtotal.setFont(new java.awt.Font("Dialog", 0, 18)); // NOI18N
        jtotal.setForeground(new java.awt.Color(0, 0, 0));
        jtotal.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 1, 0, new java.awt.Color(0, 0, 0)));
        jtotal.setCaretColor(new java.awt.Color(255, 255, 255));
        jPanel1.add(jtotal, new org.netbeans.lib.awtextra.AbsoluteConstraints(510, 170, 160, 30));

        jsearch.setFont(new java.awt.Font("Dialog", 1, 18)); // NOI18N
        jsearch.setForeground(new java.awt.Color(0, 0, 0));
        jsearch.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Pictures/icons8_search_32px.png"))); // NOI18N
        jsearch.setText("بحث");
        jsearch.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jsearch.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jsearchActionPerformed(evt);
            }
        });
        jPanel1.add(jsearch, new org.netbeans.lib.awtextra.AbsoluteConstraints(510, 120, -1, 40));

        jLabel5.setBackground(new java.awt.Color(204, 204, 204));
        jLabel5.setFont(new java.awt.Font("Dialog", 1, 18)); // NOI18N
        jLabel5.setForeground(new java.awt.Color(0, 0, 0));
        jLabel5.setText("رقم:");
        jLabel5.setOpaque(true);
        jPanel1.add(jLabel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(680, 220, 50, 30));

        jLabel6.setBackground(new java.awt.Color(204, 204, 204));
        jLabel6.setFont(new java.awt.Font("Dialog", 1, 18)); // NOI18N
        jLabel6.setForeground(new java.awt.Color(0, 0, 0));
        jLabel6.setText("التاريخ1:");
        jLabel6.setOpaque(true);
        jPanel1.add(jLabel6, new org.netbeans.lib.awtextra.AbsoluteConstraints(670, 20, 60, 30));

        jdate1.setForeground(new java.awt.Color(0, 0, 0));
        jdate1.setDateFormatString("yyyy-MM-dd");
        jdate1.setFont(new java.awt.Font("Dialog", 0, 14)); // NOI18N
        jPanel1.add(jdate1, new org.netbeans.lib.awtextra.AbsoluteConstraints(510, 20, 150, 30));

        jdate2.setForeground(new java.awt.Color(0, 0, 0));
        jdate2.setDateFormatString("yyyy-MM-dd");
        jdate2.setFont(new java.awt.Font("Dialog", 0, 14)); // NOI18N
        jPanel1.add(jdate2, new org.netbeans.lib.awtextra.AbsoluteConstraints(510, 70, 150, 30));

        jname.setFont(new java.awt.Font("Dialog", 0, 14)); // NOI18N
        jname.setForeground(new java.awt.Color(0, 0, 0));
        jname.setBorder(new javax.swing.border.MatteBorder(null));
        jname.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jPanel1.add(jname, new org.netbeans.lib.awtextra.AbsoluteConstraints(510, 220, 160, 30));

        jrefresh.setFont(new java.awt.Font("Dialog", 1, 18)); // NOI18N
        jrefresh.setForeground(new java.awt.Color(0, 0, 0));
        jrefresh.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Pictures/icons8_refresh_32px.png"))); // NOI18N
        jrefresh.setText("تحديث ");
        jrefresh.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jrefresh.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jrefreshActionPerformed(evt);
            }
        });
        jPanel1.add(jrefresh, new org.netbeans.lib.awtextra.AbsoluteConstraints(620, 120, -1, 40));

        jsearchname.setFont(new java.awt.Font("Dialog", 1, 18)); // NOI18N
        jsearchname.setForeground(new java.awt.Color(0, 0, 0));
        jsearchname.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Pictures/icons8_search_32px.png"))); // NOI18N
        jsearchname.setText("بحث");
        jsearchname.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jsearchname.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jsearchnameActionPerformed(evt);
            }
        });
        jPanel1.add(jsearchname, new org.netbeans.lib.awtextra.AbsoluteConstraints(570, 270, -1, 40));

        jptable.setBackground(new java.awt.Color(204, 204, 204));

        jtableinfo.setBackground(new java.awt.Color(255, 255, 255));
        jtableinfo.setFont(new java.awt.Font("Dialog", 1, 12)); // NOI18N
        jtableinfo.setForeground(new java.awt.Color(0, 0, 0));
        jtableinfo.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Title 1", "Title 2", "Title 3"
            }
        ));
        jtableinfo.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jtableinfo.setPreferredSize(new java.awt.Dimension(800, 600));
        jScrollPane1.setViewportView(jtableinfo);

        javax.swing.GroupLayout jptableLayout = new javax.swing.GroupLayout(jptable);
        jptable.setLayout(jptableLayout);
        jptableLayout.setHorizontalGroup(
            jptableLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jptableLayout.createSequentialGroup()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 474, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 16, Short.MAX_VALUE))
        );
        jptableLayout.setVerticalGroup(
            jptableLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 360, javax.swing.GroupLayout.PREFERRED_SIZE)
        );

        jPanel1.add(jptable, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 30, 490, 370));

        jgtotal.setBackground(new java.awt.Color(204, 204, 204));
        jgtotal.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        jgtotal.setForeground(new java.awt.Color(0, 0, 0));
        jgtotal.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jgtotal.setText("Total");
        jgtotal.setOpaque(true);
        jPanel1.add(jgtotal, new org.netbeans.lib.awtextra.AbsoluteConstraints(71, 430, 420, 40));

        jdetailsales1.setFont(new java.awt.Font("Dialog", 1, 18)); // NOI18N
        jdetailsales1.setForeground(new java.awt.Color(0, 0, 0));
        jdetailsales1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Pictures/icons8_combo_chart_32px.png"))); // NOI18N
        jdetailsales1.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jdetailsales1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jdetailsales1ActionPerformed(evt);
            }
        });
        jPanel1.add(jdetailsales1, new org.netbeans.lib.awtextra.AbsoluteConstraints(530, 400, -1, 40));

        jdetailsales2.setFont(new java.awt.Font("Dialog", 1, 18)); // NOI18N
        jdetailsales2.setForeground(new java.awt.Color(0, 0, 0));
        jdetailsales2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Pictures/icons8_pie_chart_32px_1.png"))); // NOI18N
        jdetailsales2.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jdetailsales2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jdetailsales2ActionPerformed(evt);
            }
        });
        jPanel1.add(jdetailsales2, new org.netbeans.lib.awtextra.AbsoluteConstraints(630, 400, -1, 40));

        jLabel1.setBackground(new java.awt.Color(204, 204, 204));
        jLabel1.setFont(new java.awt.Font("Dialog", 1, 18)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(0, 0, 0));
        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1.setText("بيانات كلية للمبيعات");
        jLabel1.setOpaque(true);
        jPanel1.add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(521, 350, 170, 30));

        jpanelcontent.add(jPanel1, java.awt.BorderLayout.CENTER);

        getContentPane().add(jpanelcontent, java.awt.BorderLayout.CENTER);

        setBounds(0, 0, 750, 600);
    }// </editor-fold>//GEN-END:initComponents

    private void jsearchActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jsearchActionPerformed
        searchSalesDates();
        searchTotalSalesDates();     
       
    }//GEN-LAST:event_jsearchActionPerformed

    private void jrefreshActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jrefreshActionPerformed
       showInfoSales(); jtotal.setText("0.00");
        jdate1.setDate(null);jdate2.setDate(null);
    }//GEN-LAST:event_jrefreshActionPerformed

    private void jsearchnameActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jsearchnameActionPerformed
            searchSalesByNames();
            searchTotalSalesNames();
    }//GEN-LAST:event_jsearchnameActionPerformed

    private void jdetailsales1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jdetailsales1ActionPerformed
     String dt1=((JTextField)jdate1.getDateEditor().getUiComponent()).getText();
        String dt2=((JTextField)jdate2.getDateEditor().getUiComponent()).getText();
        if(dt1.trim().isEmpty()||dt2.trim().isEmpty()){
            JOptionPane.showMessageDialog(null, "أدخل بيانات التاريخ!","تنبيه!",JOptionPane.CLOSED_OPTION);
        }else{
            try{
                String Dir=System.getProperty("user.dir");
                JasperDesign jd=JRXmlLoader.load(Dir+"/BarSalesReport.jrxml");  
//                JasperDesign jd=JRXmlLoader.load("BarSales.jrxml");  
                HashMap ar=new HashMap();
                ar.put("dt1",dt1);
                ar.put("dt2",dt2);
                JasperReport jr=JasperCompileManager.compileReport(jd);
                JasperPrint jp=JasperFillManager.fillReport(jr,ar,conn);
                JasperViewer.viewReport(jp,false);
            }catch(Exception ex){
                ex.printStackTrace();
            }
        }        
    }//GEN-LAST:event_jdetailsales1ActionPerformed

    private void jdetailsales2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jdetailsales2ActionPerformed
            String dt1=((JTextField)jdate1.getDateEditor().getUiComponent()).getText();
        String dt2=((JTextField)jdate2.getDateEditor().getUiComponent()).getText();
        if(dt1.trim().isEmpty()||dt2.trim().isEmpty()){
            JOptionPane.showMessageDialog(null, "أدخل بيانات التاريخ!","تنبيه!",JOptionPane.CLOSED_OPTION);
        }else{
            try{
                String Dir=System.getProperty("user.dir");
                JasperDesign jd=JRXmlLoader.load(Dir+"/BPieChartSales.jrxml"); 
//                JasperDesign jd=JRXmlLoader.load("PieChartSales.jrxml"); 
                HashMap ar=new HashMap();
                ar.put("dt1",dt1);
                ar.put("dt2",dt2);
                JasperReport jr=JasperCompileManager.compileReport(jd);
                JasperPrint jp=JasperFillManager.fillReport(jr,ar,conn);
                JasperViewer.viewReport(jp,false);
            }catch(Exception ex){
                ex.printStackTrace();
            }
        }
    }//GEN-LAST:event_jdetailsales2ActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JScrollPane jScrollPane1;
    private com.toedter.calendar.JDateChooser jdate1;
    private com.toedter.calendar.JDateChooser jdate2;
    private javax.swing.JButton jdetailsales1;
    private javax.swing.JButton jdetailsales2;
    private javax.swing.JLabel jgtotal;
    private javax.swing.JComboBox<String> jname;
    private javax.swing.JPanel jpanelcontent;
    private javax.swing.JPanel jpanelfooter;
    private javax.swing.JPanel jpanelheader;
    private javax.swing.JPanel jptable;
    private javax.swing.JButton jrefresh;
    private javax.swing.JButton jsearch;
    private javax.swing.JButton jsearchname;
    private javax.swing.JTable jtableinfo;
    private javax.swing.JTextField jtotal;
    // End of variables declaration//GEN-END:variables
}
