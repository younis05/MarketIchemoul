package com.younes;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


import com.younes.connection.JavaConnected;
import com.younes.connection.JavaCreateDBS;
import com.younes.connection.LockApp;
import com.younes.connection.UserDB;
import java.awt.Desktop;
import java.awt.Toolkit;
import java.awt.event.WindowEvent;
import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

/**
 *
 * @author BOUKHTACHE
 */
public class SplashF extends javax.swing.JFrame {

    /**
     * Creates new form TestProgress
     */
       private static  ResultSet rst=null;private static PreparedStatement pst=null;
      private static Statement st;
       private static  Connection conn,conn1,conn2=null;
     private  static LockApp lk=new LockApp("MarketBenackcha");  
    int x,y;
    WindowEvent winClosingEvent;
    public SplashF() {
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
        CreateFolders();
        lk.appClosable();
        icons();
     conn=JavaCreateDBS.connectionDBS();
     conn1=JavaConnected.connectionDB();
     conn2=UserDB.createUser(conn,st);                 
               
                         
    }
     private  void close(){
     try{
          winClosingEvent=new WindowEvent(this,WindowEvent.WINDOW_CLOSING);
           Toolkit.getDefaultToolkit().getSystemEventQueue().postEvent(winClosingEvent);
         }catch(Exception ex){ex.printStackTrace();}
    }
    private void icons(){
          setIconImage(Toolkit.getDefaultToolkit().getImage(getClass().getResource("/Pictures/imgs.png")));
      } 
private static void connectToXampp(){
    
        try {
                   Desktop.getDesktop().open(new File("C:\\xampp\\xampp-control.exe"));
                 //  JOptionPane.showMessageDialog(null,"You need connect DataBase");
                    //System.exit(0);
               } catch (IOException ex1) {
                ex1.printStackTrace();
               }  
      
    }
        private static void CreateFolders() {
        try {
            File fol0 = new File(System.getProperty("user.home") + "\\FactSells");
            if (!fol0.exists()) {
                if (fol0.mkdir()) {
                  //  JOptionPane.showMessageDialog(null, "Folder created!");
                } else {
                  //  JOptionPane.showMessageDialog(null, "Folder No created!");
                }
            }
            File fol = new File(System.getProperty("user.home") + "\\FactSells\\FactTxt");
            if (!fol.exists()) {
                if (fol.mkdir()) {
                   // JOptionPane.showMessageDialog(null, "Folder created!");
                } else {
                 //   JOptionPane.showMessageDialog(null, "Folder No created!");
                }
            }
           File fol1 = new File(System.getProperty("user.home") + "\\FactSells\\Pictures");
            if (!fol1.exists()) {
                if (fol1.mkdir()) {
                  //  JOptionPane.showMessageDialog(null, "Folder created!");
                } else {
                   // JOptionPane.showMessageDialog(null, "Folder No created!");
                }
            } 
            File fol2 = new File(System.getProperty("user.home") + "\\FactSells\\CSV");
            if (!fol2.exists()) {
                if (fol2.mkdir()) {
                   // JOptionPane.showMessageDialog(null, "Folder created!");
                } else {
                  //  JOptionPane.showMessageDialog(null, "Folder No created!");
                }
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

        jPanel1 = new javax.swing.JPanel();
        jPanel2 = new javax.swing.JPanel();
        jbar = new javax.swing.JProgressBar();
        jLabel1 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setUndecorated(true);

        jPanel1.setBackground(new java.awt.Color(17, 17, 50));
        jPanel1.setPreferredSize(new java.awt.Dimension(400, 50));
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

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 400, Short.MAX_VALUE)
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 50, Short.MAX_VALUE)
        );

        getContentPane().add(jPanel1, java.awt.BorderLayout.PAGE_START);

        jPanel2.setBackground(new java.awt.Color(17, 17, 50));
        jPanel2.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jbar.setBackground(new java.awt.Color(17, 17, 50));
        jbar.setForeground(new java.awt.Color(255, 102, 0));
        jbar.setStringPainted(true);
        jPanel2.add(jbar, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 120, 270, 30));

        jLabel1.setBackground(new java.awt.Color(17, 17, 50));
        jLabel1.setFont(new java.awt.Font("Dialog", 1, 18)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(255, 255, 255));
        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1.setText("جاري التحميل.............");
        jLabel1.setOpaque(true);
        jPanel2.add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(110, 30, 170, 50));

        getContentPane().add(jPanel2, java.awt.BorderLayout.CENTER);

        setSize(new java.awt.Dimension(400, 300));
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void jPanel1MousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jPanel1MousePressed
         x = evt.getX();
        y = evt.getY();
        setOpacity((float) 0.7);
    }//GEN-LAST:event_jPanel1MousePressed

    private void jPanel1MouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jPanel1MouseReleased
        setOpacity((float) 1.0);
    }//GEN-LAST:event_jPanel1MouseReleased

    private void jPanel1MouseDragged(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jPanel1MouseDragged
        int xx = evt.getXOnScreen();
        int yy = evt.getYOnScreen();
        this.setLocation(xx - x, yy - y);
    }//GEN-LAST:event_jPanel1MouseDragged

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
          SplashF s= new SplashF();
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
            java.util.logging.Logger.getLogger(SplashF.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(SplashF.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(SplashF.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(SplashF.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                s.setVisible(true);
            }
        });
        LoginF l = new LoginF();
        if(conn1==null){
            connectToXampp(); 
            System.exit(0);
             
        }else if(conn1!=null){
            try {
            for(int i=0;i<=100;i++){
                Thread.sleep(100);
                s.jbar.setValue(i);
                if(i==100){
                   s.setVisible(false);
                   s.dispose();
                }
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
            l.setVisible(true);
            s.dispose();
        }
        
        
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel jLabel1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JProgressBar jbar;
    // End of variables declaration//GEN-END:variables
}
