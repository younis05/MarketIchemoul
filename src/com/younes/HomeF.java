package com.younes;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


import com.younes.connection.JavaConnected;
import java.awt.ComponentOrientation;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.plaf.basic.BasicInternalFrameUI;

/**
 *
 * @author BOUKHTACHE
 */
public class HomeF extends javax.swing.JInternalFrame {

    /**
     * Creates new form HomeF
     */
        ResultSet rst,rst1=null;PreparedStatement pst,pst1=null;
      Connection conn=null;
   private JFrame frame;
   Calendar timer = Calendar.getInstance();
    SimpleDateFormat formt = new SimpleDateFormat("yyyy-MM-dd");
    Date todayt, todayNow = null;
    Date dtexpre, dtexpreN = null;
    public HomeF() {
        initComponents();
         conn=JavaConnected.connectionDB();
         applyComponentOrientation(ComponentOrientation.RIGHT_TO_LEFT);
        this.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));
        BasicInternalFrameUI bi=(BasicInternalFrameUI)this.getUI();
        bi.setNorthPane(null);
        MaxTotalSales();
        MaxCustomers();
        getNotification();
        MaxQty();
    }
private void MaxTotalSales(){
          try{
            String sql="select sum(total) from backup_sales";
            pst=conn.prepareStatement(sql);
            rst=pst.executeQuery();
           while(rst.next()){
                     String nto=rst.getString("sum(total)"); 
                      if(nto==null){
               
                jsales.setText("0.00");
                }else{
                 double c=Double.parseDouble(nto);
                 
                 jsales.setText(String.format("%,.2f دج", c));
                }
                }
         }catch(Exception ex){
//            JOptionPane.showMessageDialog(null, ex);
        }
     }
private void MaxCustomers(){
          try{
            String sql="select count(cus_id) from customer";
            pst=conn.prepareStatement(sql);
            rst=pst.executeQuery();
           while(rst.next()){
                     String nto=rst.getString("count(cus_id)"); 
                      if(nto==null){
               
                jcustomer.setText("0");
                }else{
                 int c=Integer.parseInt(nto);
                 
                  jcustomer.setText(String.valueOf(c));
                }
                }
         }catch(Exception ex){
//            JOptionPane.showMessageDialog(null, ex);
        }
     }     
private void getNotification() {
        try {
            Date dt0 = new Date();
            timer.setTime(dt0);
            String dtoday = formt.format(timer.getTime());
            String sql = "select * from info_products "
                    + " ORDER BY dateexp ASC ";
            pst = conn.prepareStatement(sql);
            rst = pst.executeQuery();
            while (rst.next()) {
                String dtexp = rst.getString("dateexp");
                todayt = formt.parse(dtoday);
                dtexpre = formt.parse(dtexp);
                System.out.println("exp:" + dtexp);
                if (dtexpre.equals(todayt) || dtexpre.before(todayt)) {
                   
                   jnotification.setText("1");
                  //  jnotification.setForeground(new Color(0, 191, 0));
                    break;
                } else if (!dtexpre.equals(todayt) || !dtexpre.before(todayt)) {
                    jnotification.setText("0");
                   // jnotification.setForeground(Color.WHITE);
                }

            }
        } catch (Exception ex) {
            // JOptionPane.showMessageDialog(null, ex.getMessage());
        }
    }   
private void AlertExp(){
     try {
            Date dt0 = new Date();
            timer.setTime(dt0);
            String dtoday = formt.format(timer.getTime());

            String sql = "select dateexp from info_products ";
            pst = conn.prepareStatement(sql);
            rst = pst.executeQuery();

            while (rst.next()) {
                String dtexp = rst.getString("dateexp");
                todayt = formt.parse(dtoday);
                dtexpre = formt.parse(dtexp);
                if (dtexpre.equals(todayt) == true || dtexpre.before(todayt) == true) {
                    JOptionPane.showMessageDialog(null, "هناك منتوجات إنتهت صلاحيتها!", "تنبيه!", JOptionPane.WARNING_MESSAGE);

                    break;
                }

            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, ex.getMessage());
        }
}
private void MaxQty(){
              try{
            String sql="select sum(qty) from stock";
            pst=conn.prepareStatement(sql);
            rst=pst.executeQuery();
           while(rst.next()){
                     String nto=rst.getString("sum(qty)"); 
                      if(nto==null){
                jqty.setText("0");
                }else{
                 int c=Integer.parseInt(nto);
                 jqty.setText(String.valueOf(c));
                }
                }
         }catch(Exception ex){
//            JOptionPane.showMessageDialog(null, ex);
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
        jPanel1 = new javax.swing.JPanel();
        jPanel2 = new javax.swing.JPanel();
        jPanel3 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jpanelfooter = new javax.swing.JPanel();
        jpanelcontent = new javax.swing.JPanel();
        jpsales = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jsales = new javax.swing.JLabel();
        jpcustomer = new javax.swing.JPanel();
        jLabel7 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        jcustomer = new javax.swing.JLabel();
        jpnotification = new javax.swing.JPanel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jnotification = new javax.swing.JLabel();
        jpstock = new javax.swing.JPanel();
        jLabel6 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        jqty = new javax.swing.JLabel();

        setPreferredSize(new java.awt.Dimension(750, 600));

        jpanelheader.setBackground(new java.awt.Color(57, 59, 156));
        jpanelheader.setPreferredSize(new java.awt.Dimension(750, 50));
        jpanelheader.setLayout(new java.awt.BorderLayout());

        jPanel1.setBackground(new java.awt.Color(57, 59, 156));
        jPanel1.setPreferredSize(new java.awt.Dimension(200, 100));

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 200, Short.MAX_VALUE)
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 50, Short.MAX_VALUE)
        );

        jpanelheader.add(jPanel1, java.awt.BorderLayout.LINE_END);

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

        jpanelheader.add(jPanel2, java.awt.BorderLayout.LINE_START);

        jPanel3.setBackground(new java.awt.Color(57, 59, 156));
        jPanel3.setPreferredSize(new java.awt.Dimension(350, 50));
        jPanel3.setLayout(new java.awt.BorderLayout());

        jLabel1.setBackground(new java.awt.Color(57, 59, 156));
        jLabel1.setFont(new java.awt.Font("Dialog", 1, 24)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(255, 255, 255));
        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1.setText("الرئيسية");
        jLabel1.setOpaque(true);
        jPanel3.add(jLabel1, java.awt.BorderLayout.CENTER);

        jpanelheader.add(jPanel3, java.awt.BorderLayout.CENTER);

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
        jpanelcontent.setPreferredSize(new java.awt.Dimension(750, 500));
        jpanelcontent.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jpsales.setBackground(new java.awt.Color(254, 0, 25));
        jpsales.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        jpsales.setPreferredSize(new java.awt.Dimension(260, 130));

        jLabel2.setBackground(new java.awt.Color(254, 0, 25));
        jLabel2.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Pictures/icons8_money_32px.png"))); // NOI18N
        jLabel2.setOpaque(true);

        jLabel3.setBackground(new java.awt.Color(254, 0, 25));
        jLabel3.setFont(new java.awt.Font("Dialog", 1, 24)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(255, 255, 255));
        jLabel3.setText("المبيعات");
        jLabel3.setOpaque(true);

        jsales.setBackground(new java.awt.Color(254, 0, 25));
        jsales.setFont(new java.awt.Font("Dialog", 1, 18)); // NOI18N
        jsales.setForeground(new java.awt.Color(255, 255, 255));
        jsales.setText("0.00");
        jsales.setOpaque(true);

        javax.swing.GroupLayout jpsalesLayout = new javax.swing.GroupLayout(jpsales);
        jpsales.setLayout(jpsalesLayout);
        jpsalesLayout.setHorizontalGroup(
            jpsalesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jpsalesLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 48, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jpsalesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jpsalesLayout.createSequentialGroup()
                        .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 77, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 107, Short.MAX_VALUE))
                    .addComponent(jsales, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        jpsalesLayout.setVerticalGroup(
            jpsalesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jpsalesLayout.createSequentialGroup()
                .addGap(30, 30, 30)
                .addGroup(jpsalesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(jpsalesLayout.createSequentialGroup()
                        .addComponent(jLabel3)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jsales, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 53, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(28, Short.MAX_VALUE))
        );

        jpanelcontent.add(jpsales, new org.netbeans.lib.awtextra.AbsoluteConstraints(81, 96, -1, -1));

        jpcustomer.setBackground(new java.awt.Color(57, 59, 156));
        jpcustomer.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        jpcustomer.setPreferredSize(new java.awt.Dimension(260, 130));

        jLabel7.setBackground(new java.awt.Color(57, 59, 156));
        jLabel7.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel7.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Pictures/icons8_user_tag_32px.png"))); // NOI18N
        jLabel7.setOpaque(true);

        jLabel8.setBackground(new java.awt.Color(57, 59, 156));
        jLabel8.setFont(new java.awt.Font("Dialog", 1, 24)); // NOI18N
        jLabel8.setForeground(new java.awt.Color(255, 255, 255));
        jLabel8.setText("الزبائن");
        jLabel8.setOpaque(true);

        jcustomer.setBackground(new java.awt.Color(57, 59, 156));
        jcustomer.setFont(new java.awt.Font("Dialog", 1, 18)); // NOI18N
        jcustomer.setForeground(new java.awt.Color(255, 255, 255));
        jcustomer.setText("0");
        jcustomer.setOpaque(true);

        javax.swing.GroupLayout jpcustomerLayout = new javax.swing.GroupLayout(jpcustomer);
        jpcustomer.setLayout(jpcustomerLayout);
        jpcustomerLayout.setHorizontalGroup(
            jpcustomerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jpcustomerLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel7, javax.swing.GroupLayout.PREFERRED_SIZE, 48, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jpcustomerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jpcustomerLayout.createSequentialGroup()
                        .addComponent(jLabel8, javax.swing.GroupLayout.PREFERRED_SIZE, 77, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addComponent(jcustomer, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        jpcustomerLayout.setVerticalGroup(
            jpcustomerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jpcustomerLayout.createSequentialGroup()
                .addGap(30, 30, 30)
                .addGroup(jpcustomerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(jpcustomerLayout.createSequentialGroup()
                        .addComponent(jLabel8)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jcustomer, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addComponent(jLabel7, javax.swing.GroupLayout.PREFERRED_SIZE, 53, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jpanelcontent.add(jpcustomer, new org.netbeans.lib.awtextra.AbsoluteConstraints(440, 100, -1, -1));

        jpnotification.setBackground(new java.awt.Color(254, 87, 0));
        jpnotification.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        jpnotification.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jpnotification.setPreferredSize(new java.awt.Dimension(260, 130));
        jpnotification.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jpnotificationMouseClicked(evt);
            }
        });

        jLabel4.setBackground(new java.awt.Color(254, 87, 0));
        jLabel4.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel4.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Pictures/icons8_google_alerts_32px.png"))); // NOI18N
        jLabel4.setOpaque(true);

        jLabel5.setBackground(new java.awt.Color(254, 87, 0));
        jLabel5.setFont(new java.awt.Font("Dialog", 1, 24)); // NOI18N
        jLabel5.setForeground(new java.awt.Color(255, 255, 255));
        jLabel5.setText("التنبيهات");
        jLabel5.setOpaque(true);

        jnotification.setBackground(new java.awt.Color(254, 87, 0));
        jnotification.setFont(new java.awt.Font("Dialog", 1, 18)); // NOI18N
        jnotification.setForeground(new java.awt.Color(255, 255, 255));
        jnotification.setText("0");
        jnotification.setOpaque(true);

        javax.swing.GroupLayout jpnotificationLayout = new javax.swing.GroupLayout(jpnotification);
        jpnotification.setLayout(jpnotificationLayout);
        jpnotificationLayout.setHorizontalGroup(
            jpnotificationLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jpnotificationLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 48, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jpnotificationLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jpnotificationLayout.createSequentialGroup()
                        .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 77, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 107, Short.MAX_VALUE))
                    .addComponent(jnotification, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        jpnotificationLayout.setVerticalGroup(
            jpnotificationLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jpnotificationLayout.createSequentialGroup()
                .addGap(30, 30, 30)
                .addGroup(jpnotificationLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jpnotificationLayout.createSequentialGroup()
                        .addComponent(jLabel5)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jnotification, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 53, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(30, Short.MAX_VALUE))
        );

        jpanelcontent.add(jpnotification, new org.netbeans.lib.awtextra.AbsoluteConstraints(440, 270, -1, -1));

        jpstock.setBackground(new java.awt.Color(22, 125, 46));
        jpstock.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        jpstock.setPreferredSize(new java.awt.Dimension(260, 130));

        jLabel6.setBackground(new java.awt.Color(22, 125, 46));
        jLabel6.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel6.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Pictures/icons8_shopping_cart_32px_1.png"))); // NOI18N
        jLabel6.setOpaque(true);

        jLabel9.setBackground(new java.awt.Color(22, 125, 46));
        jLabel9.setFont(new java.awt.Font("Dialog", 1, 24)); // NOI18N
        jLabel9.setForeground(new java.awt.Color(255, 255, 255));
        jLabel9.setText("المخزن");
        jLabel9.setOpaque(true);

        jqty.setBackground(new java.awt.Color(22, 125, 46));
        jqty.setFont(new java.awt.Font("Dialog", 1, 18)); // NOI18N
        jqty.setForeground(new java.awt.Color(255, 255, 255));
        jqty.setText("0");
        jqty.setOpaque(true);

        javax.swing.GroupLayout jpstockLayout = new javax.swing.GroupLayout(jpstock);
        jpstock.setLayout(jpstockLayout);
        jpstockLayout.setHorizontalGroup(
            jpstockLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jpstockLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, 48, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jpstockLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jpstockLayout.createSequentialGroup()
                        .addComponent(jLabel9, javax.swing.GroupLayout.PREFERRED_SIZE, 77, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 107, Short.MAX_VALUE))
                    .addComponent(jqty, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        jpstockLayout.setVerticalGroup(
            jpstockLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jpstockLayout.createSequentialGroup()
                .addGap(30, 30, 30)
                .addGroup(jpstockLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(jpstockLayout.createSequentialGroup()
                        .addComponent(jLabel9)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jqty, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, 53, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(28, Short.MAX_VALUE))
        );

        jpanelcontent.add(jpstock, new org.netbeans.lib.awtextra.AbsoluteConstraints(80, 270, -1, -1));

        getContentPane().add(jpanelcontent, java.awt.BorderLayout.CENTER);

        setBounds(0, 0, 750, 600);
    }// </editor-fold>//GEN-END:initComponents

    private void jpnotificationMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jpnotificationMouseClicked
      AlertExp();
    }//GEN-LAST:event_jpnotificationMouseClicked


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JLabel jcustomer;
    private javax.swing.JLabel jnotification;
    private javax.swing.JPanel jpanelcontent;
    private javax.swing.JPanel jpanelfooter;
    private javax.swing.JPanel jpanelheader;
    private javax.swing.JPanel jpcustomer;
    private javax.swing.JPanel jpnotification;
    private javax.swing.JPanel jpsales;
    private javax.swing.JPanel jpstock;
    private javax.swing.JLabel jqty;
    private javax.swing.JLabel jsales;
    // End of variables declaration//GEN-END:variables
}
