package com.younes;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


import com.younes.connection.JavaConnected;
import java.awt.Color;
import java.awt.ComponentOrientation;
import java.awt.Toolkit;
import java.awt.event.KeyEvent;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import javax.swing.JOptionPane;

/**
 *
 * @author BOUKHTACHE
 */
public class LoginF extends javax.swing.JFrame {

    /**
     * Creates new form LoginF
     */
    
    ResultSet rst=null;PreparedStatement pst=null;
    Statement st;
     Connection conn;
     int d=0;
     int x,y;
    String aduser,adpass=null;
    public LoginF() {
        initComponents();
                 try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Windows".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            ex.printStackTrace();
        } catch (InstantiationException ex) {
            ex.printStackTrace();
        } catch (IllegalAccessException ex) {
            ex.printStackTrace();
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            ex.printStackTrace();
        }
         applyComponentOrientation(ComponentOrientation.RIGHT_TO_LEFT);
        juser.requestFocus();        
        conn=JavaConnected.connectionDB();
        icons();
       
       
       
    }
    private void icons(){
    setIconImage(Toolkit.getDefaultToolkit().getImage(getClass().getResource("/Pictures/imgs.png"))); 
      }  
    private boolean verifyFieldes(){
       boolean calm=false;
       aduser=juser.getText();adpass=jpass.getText();
       if(!aduser.trim().isEmpty()||!aduser.equals("")||!adpass.trim().isEmpty()||adpass.equals("")){
           calm=true;
       }else{
           calm=false;
       }
       return calm;
    }
    
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        juser = new javax.swing.JTextField();
        jenter = new javax.swing.JButton();
        jPanel2 = new javax.swing.JPanel();
        jclose = new javax.swing.JLabel();
        jButton1 = new javax.swing.JButton();
        jpass = new javax.swing.JPasswordField();
        jcheckbox = new javax.swing.JCheckBox();
        jLabel3 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setUndecorated(true);

        jPanel1.setBackground(new java.awt.Color(15, 19, 52));
        jPanel1.setPreferredSize(new java.awt.Dimension(500, 400));
        jPanel1.addMouseMotionListener(new java.awt.event.MouseMotionAdapter() {
            public void mouseDragged(java.awt.event.MouseEvent evt) {
                jPanel1MouseDragged(evt);
            }
        });
        jPanel1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                jPanel1MousePressed(evt);
            }
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                jPanel1MouseReleased(evt);
            }
        });
        jPanel1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel1.setBackground(new java.awt.Color(15, 19, 52));
        jLabel1.setFont(new java.awt.Font("Dialog", 1, 18)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(255, 255, 255));
        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1.setText("المستخدم:");
        jLabel1.setOpaque(true);
        jPanel1.add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(280, 130, 70, -1));

        jLabel2.setBackground(new java.awt.Color(15, 19, 52));
        jLabel2.setFont(new java.awt.Font("Dialog", 1, 18)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(255, 255, 255));
        jLabel2.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel2.setText("إضهار/إخفاء:");
        jLabel2.setOpaque(true);
        jPanel1.add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(300, 250, 90, -1));

        juser.setBackground(new java.awt.Color(15, 19, 52));
        juser.setFont(new java.awt.Font("Dialog", 1, 18)); // NOI18N
        juser.setForeground(new java.awt.Color(255, 255, 255));
        juser.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 1, 0, new java.awt.Color(255, 255, 255)));
        juser.setCaretColor(new java.awt.Color(255, 255, 255));
        juser.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                juserKeyPressed(evt);
            }
        });
        jPanel1.add(juser, new org.netbeans.lib.awtextra.AbsoluteConstraints(110, 130, 160, -1));

        jenter.setFont(new java.awt.Font("Dialog", 1, 18)); // NOI18N
        jenter.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Pictures/icons8_user_32px.png"))); // NOI18N
        jenter.setText("دخول");
        jenter.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jenter.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jenterActionPerformed(evt);
            }
        });
        jPanel1.add(jenter, new org.netbeans.lib.awtextra.AbsoluteConstraints(100, 250, 100, 40));

        jPanel2.setLayout(new java.awt.BorderLayout());

        jclose.setBackground(new java.awt.Color(15, 19, 52));
        jclose.setForeground(new java.awt.Color(255, 255, 255));
        jclose.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jclose.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Pictures/icons8_close_window_32px.png"))); // NOI18N
        jclose.setToolTipText("خروج");
        jclose.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jclose.setOpaque(true);
        jclose.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jcloseMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                jcloseMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                jcloseMouseExited(evt);
            }
        });
        jPanel2.add(jclose, java.awt.BorderLayout.CENTER);

        jPanel1.add(jPanel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 50, 40));

        jButton1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Pictures/icons8_broom_32px.png"))); // NOI18N
        jButton1.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });
        jPanel1.add(jButton1, new org.netbeans.lib.awtextra.AbsoluteConstraints(100, 320, -1, 40));

        jpass.setBackground(new java.awt.Color(15, 19, 52));
        jpass.setFont(new java.awt.Font("Dialog", 1, 18)); // NOI18N
        jpass.setForeground(new java.awt.Color(255, 255, 255));
        jpass.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 1, 0, new java.awt.Color(255, 255, 255)));
        jpass.setCaretColor(new java.awt.Color(255, 255, 255));
        jpass.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jpassActionPerformed(evt);
            }
        });
        jpass.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jpassKeyPressed(evt);
            }
        });
        jPanel1.add(jpass, new org.netbeans.lib.awtextra.AbsoluteConstraints(110, 170, 160, -1));

        jcheckbox.setBackground(new java.awt.Color(15, 19, 52));
        jcheckbox.setForeground(new java.awt.Color(255, 255, 255));
        jcheckbox.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jcheckbox.setMargin(new java.awt.Insets(6, 6, 6, 6));
        jcheckbox.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jcheckboxActionPerformed(evt);
            }
        });
        jPanel1.add(jcheckbox, new org.netbeans.lib.awtextra.AbsoluteConstraints(270, 250, -1, 30));

        jLabel3.setBackground(new java.awt.Color(15, 19, 52));
        jLabel3.setFont(new java.awt.Font("Dialog", 1, 18)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(255, 255, 255));
        jLabel3.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel3.setText("كلمة السر:");
        jLabel3.setOpaque(true);
        jPanel1.add(jLabel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(280, 180, 70, -1));

        getContentPane().add(jPanel1, java.awt.BorderLayout.CENTER);

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void jPanel1MousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jPanel1MousePressed
        x=evt.getX();
        y=evt.getY();
         setOpacity((float)0.7);
    }//GEN-LAST:event_jPanel1MousePressed

    private void jPanel1MouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jPanel1MouseReleased
        setOpacity((float)1.0);
    }//GEN-LAST:event_jPanel1MouseReleased

    private void jPanel1MouseDragged(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jPanel1MouseDragged
     int xx=evt.getXOnScreen();
        int yy=evt.getYOnScreen();
        this.setLocation(xx-x, yy-y);
    }//GEN-LAST:event_jPanel1MouseDragged

    private void jcloseMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jcloseMouseEntered
       jclose.setBackground(Color.RED);
    }//GEN-LAST:event_jcloseMouseEntered

    private void jcloseMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jcloseMouseExited
       jclose.setBackground(new Color(15, 19, 52));
    }//GEN-LAST:event_jcloseMouseExited

    private void jcloseMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jcloseMouseClicked
        System.exit(0);
    }//GEN-LAST:event_jcloseMouseClicked

    private void jenterActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jenterActionPerformed
         aduser=juser.getText(); 
         adpass=String.valueOf(jpass.getText()) ;
        if(verifyFieldes()){
         /*  List<Admin> list=AdminDB.getAllAdmin();
           Map<String, String> map = new HashMap<String, String>();
           for(Admin a:list){
               map.put(a.getUsername(), a.getPassword());
           }
           if (map.containsKey(juser.getText())){
               String val2=map.get(juser.getText());
               if(val2.equals(jpass.getText())){
                    this.dispose();
              Home home=new Home();
              home.setVisible(true);
               }else{
                   d++;
                JOptionPane.showMessageDialog(null,"اسم المستخدم أو كلمة السر خاطئة");
                if(d==3){ System.exit(0); }
           }
           }else{
                 d++;
                JOptionPane.showMessageDialog(null,"اسم المستخدم أو كلمة السر خاطئة");
                 if(d==3){ System.exit(0); }
           }*/
         
        try{
           String sql="select * from users where username=? and password=?";
           pst=conn.prepareStatement(sql);
           pst.setString(1, aduser);pst.setString(2, adpass);
           rst=pst.executeQuery();
           if(rst.next()){
              this.dispose();
              Home home=new Home();
              home.setVisible(true);
              System.out.println(" login success!"); 
               }else{
               d++;
                 JOptionPane.showMessageDialog(null,"اسم المستخدم أو كلمة السر خاطئة");  
                 if(d==3){
                     System.exit(0);
                 }
            } 
          pst.close();rst.close();
         } catch(Exception ex){ex.printStackTrace();}
         
       }else{
            JOptionPane.showMessageDialog(null,"أدخل البيانات...!");
       }
    }//GEN-LAST:event_jenterActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
       juser.setText(""); jpass.setText("");
    }//GEN-LAST:event_jButton1ActionPerformed

    private void juserKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_juserKeyPressed
       if(evt.getKeyCode()==KeyEvent.VK_DOWN){
           jpass.requestFocus();
       }
    }//GEN-LAST:event_juserKeyPressed

    private void jpassKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jpassKeyPressed
         if(evt.getKeyCode()==KeyEvent.VK_UP){
           juser.requestFocus();
       }
    }//GEN-LAST:event_jpassKeyPressed

    private void jcheckboxActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jcheckboxActionPerformed
       if(jcheckbox.isSelected()==false){
           jpass.setEchoChar('*');
       }else{
           jpass.setEchoChar((char)0);
       }
    }//GEN-LAST:event_jcheckboxActionPerformed

    private void jpassActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jpassActionPerformed
         aduser=juser.getText(); 
         adpass=String.valueOf(jpass.getText()) ;
        if(verifyFieldes()){
         /*  List<Admin> list=AdminDB.getAllAdmin();
           Map<String, String> map = new HashMap<String, String>();
           for(Admin a:list){
               map.put(a.getUsername(), a.getPassword());
           }
           if (map.containsKey(juser.getText())){
               String val2=map.get(juser.getText());
               if(val2.equals(jpass.getText())){
                    this.dispose();
              Home home=new Home();
              home.setVisible(true);
               }else{
                   d++;
                JOptionPane.showMessageDialog(null,"اسم المستخدم أو كلمة السر خاطئة");
                if(d==3){ System.exit(0); }
           }
           }else{
                 d++;
                JOptionPane.showMessageDialog(null,"اسم المستخدم أو كلمة السر خاطئة");
                 if(d==3){ System.exit(0); }
           }*/
         
        try{
           String sql="select * from users where username=? and password=?";
           pst=conn.prepareStatement(sql);
           pst.setString(1, aduser);pst.setString(2, adpass);
           rst=pst.executeQuery();
           if(rst.next()){
              this.dispose();
              Home home=new Home();
              home.setVisible(true);
              System.out.println(" login success!"); 
               }else{
               d++;
                 JOptionPane.showMessageDialog(null,"اسم المستخدم أو كلمة السر خاطئة");  
                 if(d==3){
                     System.exit(0);
                 }
            } 
          pst.close();rst.close();
         } catch(Exception ex){ex.printStackTrace();}
         
       }else{
            JOptionPane.showMessageDialog(null,"أدخل البيانات...!");
       }
    }//GEN-LAST:event_jpassActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Windows".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(LoginF.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(LoginF.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(LoginF.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(LoginF.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new LoginF().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JCheckBox jcheckbox;
    private javax.swing.JLabel jclose;
    private javax.swing.JButton jenter;
    private javax.swing.JPasswordField jpass;
    private javax.swing.JTextField juser;
    // End of variables declaration//GEN-END:variables
}
