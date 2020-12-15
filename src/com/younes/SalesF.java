package com.younes;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


import com.younes.connection.JavaConnected;
import com.younes.util.TexttableCenter;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.ComponentOrientation;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.KeyEvent;
import java.io.FileWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Vector;
import javax.swing.DefaultComboBoxModel;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.SwingUtilities;
import javax.swing.table.DefaultTableModel;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperPrintManager;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.design.JasperDesign;
import net.sf.jasperreports.engine.xml.JRXmlLoader;
import net.sf.jasperreports.view.JRViewer;
import net.sf.jasperreports.view.JasperViewer;
/**
 *
 * @author BOUKHTACHE
 */
public class SalesF extends javax.swing.JFrame {

    /**
     * Creates new form SalesF
     */
     ResultSet rst,rst1,rst2,rst3=null;
     PreparedStatement pst,pst1,pst2,pst3=null;
     Connection conn,conn2=null; 
     Calendar timer=Calendar.getInstance();
     SimpleDateFormat tim=new SimpleDateFormat("yyyy-MM-dd");
     FileWriter fout=null;
     private ImageIcon format = null;
     private Map<Integer, String> cusmap = new HashMap();
     private int cus_id = 0; 
     int qty;double total;
     JFrame frame ;
     int x,y;
     DefaultTableModel table ;  
    TexttableCenter tblcenter=new TexttableCenter();
    public SalesF() {
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
        icons();      
        conn=JavaConnected.connectionDB();
        conn2=JavaConnected.connectionDB();
        showTableSales();
       FillNINVOICE(); FillsalID();
       jname.setModel(new DefaultComboBoxModel(loadNameProducts()));
       jcus_id.setModel(new DefaultComboBoxModel(FillCustumer()));
       SwingUtilities.invokeLater(new Runnable(){
            @Override
            public void run() {
              jbarcode.requestFocus(); 
            } 
        });
       
       jdeleteall.setEnabled(false);
        
    }
 /******info *******/
    private void icons(){
          setIconImage(Toolkit.getDefaultToolkit().getImage(getClass().getResource("/Pictures/imgs.png")));
      } 
    private void setActuelColor(JLabel jtext) {
        jtext.setBackground(new Color(254, 0, 72));
    }

    private void setActuelColor2(JLabel jtext) {
        jtext.setBackground(new Color(94, 94, 94));
    }

    private void setResetColor2(JLabel jtext) {
        jtext.setBackground(new Color(17,17,50));
    }
    
/********* SALES *********/  

    private Vector loadNameProducts(){
        Vector v=new Vector();
         v.add("إختر المنتوج");
     try {
            String sql="SELECT `p_name` FROM `stock`";
            pst = conn.prepareStatement(sql);
            rst = pst.executeQuery();
            jname.removeAllItems();
            while (rst.next()) {
                   String name=rst.getString("p_name");
                   v.add(name);
                  // jname.addItem(name);
              }
           
        } catch (Exception ex) {
           ex.printStackTrace();
        }
        return v;
    }
    private void InputNameProducts(){
         String namep=jname.getSelectedItem().toString();
        try {
            String sql="SELECT * FROM `stock` WHERE `p_name`=?";
            pst = conn.prepareStatement(sql);
            pst.setString(1, namep);
            rst = pst.executeQuery();
            while (rst.next()) {  
                   String pid=rst.getString("p_id");
                   String name=rst.getString("p_name");
                   String price=rst.getString("price");
                   String cdbr=rst.getString("barcode");
                   jp_id.setText(pid);
                   jbarcode.setText(cdbr);
                   jname.setSelectedItem(name);
                   jprice.setText(price.trim());
                   jqty.setText("1");
                   getTotal();
                   jqty.requestFocus();
              String sql1 = "SELECT * FROM `productimage` WHERE `p_id`=?";
            pst1 = conn2.prepareStatement(sql1);
            pst1.setString(1, pid);
            rst1 = pst1.executeQuery();
            while  (rst1.next()) {
                byte[] img_data = rst1.getBytes("picture");
                if(img_data==null){
                    format.equals(null);jpicture.setIcon(null); 
                }else{
                   format = new ImageIcon(new ImageIcon(img_data).getImage()
                        .getScaledInstance(jpicture.getWidth(),
                         jpicture.getHeight(), Image.SCALE_SMOOTH)); 
                         jpicture.setIcon(format); 
                }
                   
            }
            }
        //  pst.close();rst.close();pst1.close();rst1.close();
        } catch (Exception ex) {
           ex.printStackTrace();
        } 
    }
    private void Inputproducts(){
        String codb=jbarcode.getText();
      try {
            String sql="SELECT * FROM `stock` WHERE `barcode`=?";
            pst = conn.prepareStatement(sql);
            pst.setString(1, codb);
            rst = pst.executeQuery();
            while(rst.next()==true) {
                   String pid=rst.getString("p_id");
                   String name=rst.getString("p_name");
                   String price=rst.getString("price");
                   String bc=rst.getString("barcode");
                   if(codb.equals(bc)==true){ 
                   jp_id.setText(pid);
                   jname.setSelectedItem(name.trim());
                   jprice.setText(price.trim());
                   jqty.setText("1");
                   getTotal();
                  // jbarcode.setText("");
                 //  jbarcode.requestFocus();
                 jqty.requestFocus();
             String sql1 = "SELECT * FROM `productimage` WHERE `p_id`=?";
            pst1 = conn2.prepareStatement(sql1);
            pst1.setString(1, pid);
            rst1 = pst1.executeQuery();
           if (rst1.next() == true) {
                byte[] img_data = rst1.getBytes("picture");
                if(img_data==null){
                    format.equals(null);jpicture.setIcon(null); 
                }else{
                   format = new ImageIcon(new ImageIcon(img_data).getImage()
                        .getScaledInstance(jpicture.getWidth(),
                         jpicture.getHeight(), Image.SCALE_SMOOTH)); 
                         jpicture.setIcon(format); 
                }
            }    
                   }else{
                    //  JOptionPane.showMessageDialog(null,"Failed product");  
                   }
               }
        } catch (Exception ex) {
         ex.printStackTrace();
        } 
     }
    private void showTableSales(){
         table=new DefaultTableModel();
        table.addColumn("SAL_ID"); table.addColumn("P_ID");
        table.addColumn("CUS_ID");table.addColumn("INV_ID");
        table.addColumn("BARCODE"); table.addColumn("NAME");
        table.addColumn("PRICE");table.addColumn("QTY");table.addColumn("TOTAL");
        table.addColumn("DISCOUNT");table.addColumn("DATE TIME");
        String sql="SELECT * FROM `sales`"; 
        try {
            pst=conn.prepareStatement(sql);
            rst=pst.executeQuery();
            while(rst.next()){
                table.addRow(new Object[]{rst.getString(1),rst.getString(2),
                    rst.getString(3), rst.getString(4),rst.getString(5),
                    rst.getString(6),rst.getString(7),rst.getString(8),
                    rst.getString(9),rst.getString(10),rst.getString(11)});
            }
             jtablesale.setModel(table);
             jtablesale.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
             jtablesale.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
              for(int i=0;i<11;i++){
                 jtablesale.getColumnModel().getColumn(i).setCellRenderer(tblcenter);
             }
             
        } catch (Exception ex) {
           ex.printStackTrace();
        }
    }
    private void ClearFields(){
        jbarcode.setText(""); jprice.setText(""); 
        jqty.setText("");jtotal.setText("");
        jbarcode.requestFocus();jqty.setBackground(Color.BLACK);
        jpicture.setIcon(null);
        jname.setModel(new DefaultComboBoxModel(loadNameProducts()));
        jdiscount.setSelectedIndex(0);
        jtax.setText("");
        jbalance.setText("");
    }
    private void UpdateStock(){
        try {
  String sql="INSERT INTO `backup_sales`(`sal_id`,`p_id`,`cus_id`,`inv_id`,"
         + "`barcode`,`name`,`price`,`qty`,`total`,`discount`,`datetime`)values(?,?,?,?,?,?,?,?,?,?,?)";  
             for(int i=0;i<jtablesale.getRowCount();i++){
                 Long sal_id0=Long.parseLong(jsal_id.getText());
                 Long p_id0=Long.parseLong(jtablesale.getValueAt(i, 1).toString());
                 Long cus_id0=Long.parseLong(jtablesale.getValueAt(i, 2).toString());
                 String inv_id0=jtablesale.getValueAt(i, 3).toString();
                 String barcode0=jtablesale.getValueAt(i, 4).toString();
                 String name0=jtablesale.getValueAt(i, 5).toString();
                 Double price0=Double.parseDouble(jtablesale.getValueAt(i,6).toString());
                 int qty0=Integer.parseInt(jtablesale.getValueAt(i, 7).toString());
                 Double total0=Double.parseDouble(jtablesale.getValueAt(i,8).toString());
                 int discount0=Integer.parseInt(jtablesale.getValueAt(i, 9).toString());
                  String date0=jtablesale.getValueAt(i, 10).toString();
            pst=conn.prepareStatement(sql);
            pst.setLong(1,sal_id0);
            pst.setLong(2,p_id0);
            pst.setLong(3,cus_id0);
            pst.setString(4,inv_id0);
            pst.setString(5,barcode0);
            pst.setString(6,name0);
            pst.setDouble(7,price0);
            pst.setInt(8,qty0);
            pst.setDouble(9,total0);
            pst.setInt(10,discount0);
            pst.setString(11,date0);
            pst.executeUpdate();
           String sql1="SELECT `qty`,`total` FROM `stock` WHERE `p_id`=?"; 
           pst1=conn.prepareStatement(sql1);
           pst1.setLong(1,  p_id0);
           rst1=pst1.executeQuery();
           if(rst1.next()){
              qty= rst1.getInt("qty");
              total=rst1.getDouble("total");
              qty-=qty0; //qty=qty-qty0;
              total-=total0;
           }
            String sql2="UPDATE `stock` SET `qty`=?,`total`=? WHERE `p_id`=?";
            pst2=conn.prepareStatement(sql2);
             pst2.setInt(1,qty);
             pst2.setDouble(2,total);
             pst2.setLong(3,p_id0);
             pst2.executeUpdate();
             pst.addBatch();
             pst1.addBatch();
             pst2.addBatch();
             }  
             System.out.println("Update succes!"); 
            
        } catch (Exception ex) {
           ex.printStackTrace();
        }
    }
    private void getTotalCoast(){
        if (jprice.getText().equals("")||jqty.getText().equals("")){
           JOptionPane.showMessageDialog(null, "أدخل البيانات!", "خطأ", JOptionPane.WARNING_MESSAGE);
           return;
        }else{
        SimpleDateFormat time=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
         Date dt=new Date(); timer.setTime(dt);
         String dtm=time.format(timer.getTime());
        if(jqty.getText().equals("")||jqty.getText().equals("0")){
           JOptionPane.showMessageDialog(null, "أدخل البيانات!", "خطأ", JOptionPane.WARNING_MESSAGE);
           return;  
        }
         DefaultTableModel table= (DefaultTableModel)jtablesale.getModel();
         table.addRow(new Object[]{jsal_id.getText(),jp_id.getText(),
         cus_id,
         jinv_id.getText(),jbarcode.getText(),jname.getSelectedItem().toString(),
         jprice.getText(),jqty.getText(),jtotal.getText(),jdiscount.getSelectedItem().toString(),
         dtm});
         double sum=0;
   for(int i=0;i<jtablesale.getRowCount();i++){
       sum+=Double.parseDouble(jtablesale.getValueAt(i,8).toString()); //sum=sum+.....
   }
   jpt.setText(String.format("%,.2f",sum));
         }
    }
    private void getTotal(){
     AlertQty();
     double price=Double.parseDouble(jprice.getText());
     double qty=Double.parseDouble(jqty.getText());
     double c=price*qty;
     String res=String.valueOf(c);
     jtotal.setText(res);
     
 }
    private void Discount(){
         double p=Double.parseDouble(jdiscount.getSelectedItem().toString());
         double t=Double.parseDouble(jprice.getText());
         double dis=(t*p)/100;double rs=t-dis;
         double a=Double.parseDouble(jqty.getText());
         double c=a*rs;
         jtotal.setText(String.valueOf(c));
     }
    private void FillNINVOICE(){
          try{
            String sql="SELECT max(n_inv) FROM `invoices`";
            pst=conn.prepareStatement(sql);
            rst=pst.executeQuery();
           while(rst.next()){
                     String ninv=rst.getString("max(n_inv)"); 
                      if(ninv==null){
               
                jinv_id.setText("00000000001");
                }else{
                   long nv=Long.parseLong(ninv); 
                    nv++;
                  jinv_id.setText(String.format("%011d",nv));
                }
                }
         }catch(Exception ex){
          ex.printStackTrace();
        }
     }
    private void FillsalID(){
          try{
            String sql="SELECT max(inv_id) FROM `invoices`";
            pst=conn.prepareStatement(sql);
            rst=pst.executeQuery();
           while(rst.next()){
                     String nid=rst.getString("max(inv_id)"); 
                      if(nid==null){
                   jsal_id.setText("1"); 
                }else{
                   int nv=Integer.parseInt(nid);
                    nv++;
                    jsal_id.setText(String.valueOf(nv)); 
                }
                }
         }catch(Exception ex){
           ex.printStackTrace();
        }
     } 
    private Vector FillCustumer(){
           Vector v=new Vector();
             v.add("إختر الزبون");
            try{
            String sql="SELECT * FROM `customer`";
            pst=conn.prepareStatement(sql);
            rst=pst.executeQuery();
           // jname.removeAllItems();
           while(rst.next()){
               cusmap.put(rst.getInt("cus_id"), rst.getString("lastname"));
                String cus = rst.getString("lastname");
                v.add(cus);
               // jcus_id.addItem(cus);
                }
         }catch(Exception ex){
           ex.printStackTrace();
        }
          return v;
     } 
    private void saveSaleProduct(){
      try{ 
               double sum=0;
   for(int i=0;i<jtablesale.getRowCount();i++){
       sum+=Double.parseDouble(jtablesale.getValueAt(i,8).toString()); //sum=sum+.....
   }
        SimpleDateFormat time=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date dt=new Date(); timer.setTime(dt);
        String dtm=time.format(timer.getTime());
        String sql="INSERT INTO `invoices`(`inv_id`,`n_inv`,`total`,`pay`,`balance`,`path`,`datetime`)values(?,?,?,?,?,?,?)";
           pst=conn.prepareStatement(sql);
           pst.setString(1,jsal_id.getText());  
           pst.setString(2,String.format("%011d",Integer.parseInt(jinv_id.getText())));
           pst.setString(3,String.format("%.2f",sum));
           pst.setString(4,jtax.getText());
           pst.setString(5,jbalance.getText());
           pst.setString(6, "");
           pst.setString(7,dtm);
           pst.executeUpdate();
           
         //  JOptionPane.showMessageDialog(null, "sale products save success!");  
         System.out.println("sale products save success!");
      }catch(Exception ex){
             ex.printStackTrace();
           }
  }   
    private void AlertQty(){
      
     try{
        String sql0="SELECT `alqty` FROM `info_products` WHERE `p_id`=?";
          pst2=conn.prepareStatement(sql0);
          pst2.setString(1,jp_id.getText());
          rst2=pst2.executeQuery();
          String sql="SELECT `qty` FROM `stock` WHERE `p_id`=?";
          pst3=conn.prepareStatement(sql);
          pst3.setString(1,jp_id.getText());
          rst3=pst3.executeQuery();
         while(rst3.next()){
             int qty0=Integer.parseInt(rst3.getString("qty"));
            
             while(rst2.next()){
            int qty1=Integer.parseInt(rst2.getString("alqty"));
            if(qty0<=qty1||qty0==0) { 
               jqty.setBackground(Color.red);
                 }else if(qty0>qty1){
               jqty.setBackground(Color.BLACK);
             }    
              }
          } 
	  }catch(Exception ex){
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

        jpheader = new javax.swing.JPanel();
        jpanelclose = new javax.swing.JPanel();
        jPanel4 = new javax.swing.JPanel();
        jhide = new javax.swing.JLabel();
        jPanel3 = new javax.swing.JPanel();
        jmax = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        jexit = new javax.swing.JLabel();
        jpanellogo = new javax.swing.JPanel();
        jpanelside1 = new javax.swing.JPanel();
        jPanel5 = new javax.swing.JPanel();
        jback = new javax.swing.JLabel();
        jpanelside2 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jpfooter = new javax.swing.JPanel();
        jpcontent = new javax.swing.JPanel();
        jPanel1 = new javax.swing.JPanel();
        jpinfo = new javax.swing.JPanel();
        jptable = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jtablesale = new javax.swing.JTable();
        jPanel6 = new javax.swing.JPanel();
        jppicture = new javax.swing.JPanel();
        jpicture = new javax.swing.JLabel();
        jpbottons = new javax.swing.JPanel();
        jprintcolor = new javax.swing.JButton();
        jDelete = new javax.swing.JButton();
        jdeleteall = new javax.swing.JButton();
        jsave = new javax.swing.JButton();
        jsavepdf = new javax.swing.JButton();
        printtxt = new javax.swing.JButton();
        jButton1 = new javax.swing.JButton();
        jGlobalTotal = new javax.swing.JButton();
        jptextinputinfo = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jsal_id = new javax.swing.JTextField();
        jbarcode = new javax.swing.JTextField();
        jprice = new javax.swing.JTextField();
        jqty = new javax.swing.JTextField();
        jLabel8 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        jtotal = new javax.swing.JTextField();
        jLabel10 = new javax.swing.JLabel();
        jp_id = new javax.swing.JTextField();
        jLabel11 = new javax.swing.JLabel();
        jLabel12 = new javax.swing.JLabel();
        jpt = new javax.swing.JLabel();
        jdiscount = new javax.swing.JComboBox<>();
        jLabel4 = new javax.swing.JLabel();
        jcus_id = new javax.swing.JComboBox<>();
        jinv_id = new javax.swing.JTextField();
        jname = new javax.swing.JComboBox<>();
        jLabel13 = new javax.swing.JLabel();
        jbalance = new javax.swing.JTextField();
        jLabel14 = new javax.swing.JLabel();
        jtax = new javax.swing.JTextField();
        jLabel15 = new javax.swing.JLabel();
        jpfactur = new javax.swing.JPanel();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setBackground(new java.awt.Color(204, 204, 204));
        setUndecorated(true);

        jpheader.setPreferredSize(new java.awt.Dimension(1200, 50));
        jpheader.addMouseMotionListener(new java.awt.event.MouseMotionAdapter() {
            public void mouseDragged(java.awt.event.MouseEvent evt) {
                jpheaderMouseDragged(evt);
            }
        });
        jpheader.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                jpheaderMousePressed(evt);
            }
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                jpheaderMouseReleased(evt);
            }
        });
        jpheader.setLayout(new java.awt.BorderLayout());

        jpanelclose.setBackground(new java.awt.Color(17, 17, 50));
        jpanelclose.setPreferredSize(new java.awt.Dimension(150, 50));

        jPanel4.setBackground(new java.awt.Color(17, 17, 50));
        jPanel4.setPreferredSize(new java.awt.Dimension(50, 50));
        jPanel4.setLayout(new java.awt.BorderLayout());

        jhide.setBackground(new java.awt.Color(17, 17, 50));
        jhide.setForeground(new java.awt.Color(255, 255, 255));
        jhide.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jhide.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Pictures/icons8_minus_24px_1.png"))); // NOI18N
        jhide.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jhide.setOpaque(true);
        jhide.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jhideMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                jhideMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                jhideMouseExited(evt);
            }
        });
        jPanel4.add(jhide, java.awt.BorderLayout.CENTER);

        jPanel3.setBackground(new java.awt.Color(17, 17, 50));
        jPanel3.setPreferredSize(new java.awt.Dimension(50, 50));
        jPanel3.setLayout(new java.awt.BorderLayout());

        jmax.setBackground(new java.awt.Color(17, 17, 50));
        jmax.setForeground(new java.awt.Color(255, 255, 255));
        jmax.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jmax.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Pictures/icons8_unchecked_checkbox_32px_7.png"))); // NOI18N
        jmax.setToolTipText("");
        jmax.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jmax.setOpaque(true);
        jmax.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jmaxMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                jmaxMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                jmaxMouseExited(evt);
            }
        });
        jPanel3.add(jmax, java.awt.BorderLayout.CENTER);

        jPanel2.setBackground(new java.awt.Color(17, 17, 50));
        jPanel2.setPreferredSize(new java.awt.Dimension(50, 50));
        jPanel2.setLayout(new java.awt.BorderLayout());

        jexit.setBackground(new java.awt.Color(17, 17, 50));
        jexit.setForeground(new java.awt.Color(255, 255, 255));
        jexit.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jexit.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Pictures/icons8_close_window_32px.png"))); // NOI18N
        jexit.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jexit.setOpaque(true);
        jexit.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jexitMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                jexitMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                jexitMouseExited(evt);
            }
        });
        jPanel2.add(jexit, java.awt.BorderLayout.CENTER);

        javax.swing.GroupLayout jpanelcloseLayout = new javax.swing.GroupLayout(jpanelclose);
        jpanelclose.setLayout(jpanelcloseLayout);
        jpanelcloseLayout.setHorizontalGroup(
            jpanelcloseLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jpanelcloseLayout.createSequentialGroup()
                .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0)
                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0)
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );
        jpanelcloseLayout.setVerticalGroup(
            jpanelcloseLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jpanelcloseLayout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addGroup(jpanelcloseLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
        );

        jpheader.add(jpanelclose, java.awt.BorderLayout.LINE_END);

        jpanellogo.setBackground(new java.awt.Color(17, 17, 50));
        jpanellogo.setPreferredSize(new java.awt.Dimension(850, 50));

        jpanelside1.setBackground(new java.awt.Color(17, 17, 50));
        jpanelside1.setPreferredSize(new java.awt.Dimension(150, 50));

        jPanel5.setBackground(new java.awt.Color(17, 17, 50));
        jPanel5.setPreferredSize(new java.awt.Dimension(50, 50));
        jPanel5.setLayout(new java.awt.BorderLayout());

        jback.setBackground(new java.awt.Color(17, 17, 50));
        jback.setForeground(new java.awt.Color(255, 255, 255));
        jback.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jback.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Pictures/icons8_forward_32px.png"))); // NOI18N
        jback.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jback.setOpaque(true);
        jback.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jbackMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                jbackMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                jbackMouseExited(evt);
            }
        });
        jPanel5.add(jback, java.awt.BorderLayout.CENTER);

        javax.swing.GroupLayout jpanelside1Layout = new javax.swing.GroupLayout(jpanelside1);
        jpanelside1.setLayout(jpanelside1Layout);
        jpanelside1Layout.setHorizontalGroup(
            jpanelside1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 300, Short.MAX_VALUE)
            .addGroup(jpanelside1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jpanelside1Layout.createSequentialGroup()
                    .addComponent(jPanel5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGap(0, 250, Short.MAX_VALUE)))
        );
        jpanelside1Layout.setVerticalGroup(
            jpanelside1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 50, Short.MAX_VALUE)
            .addGroup(jpanelside1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addComponent(jPanel5, javax.swing.GroupLayout.DEFAULT_SIZE, 50, Short.MAX_VALUE))
        );

        jpanelside2.setBackground(new java.awt.Color(17, 17, 50));
        jpanelside2.setPreferredSize(new java.awt.Dimension(150, 50));
        jpanelside2.setLayout(new java.awt.BorderLayout());

        jLabel1.setBackground(new java.awt.Color(17, 17, 50));
        jLabel1.setFont(new java.awt.Font("Dialog", 1, 18)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(255, 255, 255));
        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Pictures/icons8_shopping_cart_32px_1.png"))); // NOI18N
        jLabel1.setText("إدارة المبيعات");
        jLabel1.setOpaque(true);
        jpanelside2.add(jLabel1, java.awt.BorderLayout.CENTER);

        javax.swing.GroupLayout jpanellogoLayout = new javax.swing.GroupLayout(jpanellogo);
        jpanellogo.setLayout(jpanellogoLayout);
        jpanellogoLayout.setHorizontalGroup(
            jpanellogoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jpanellogoLayout.createSequentialGroup()
                .addComponent(jpanelside1, javax.swing.GroupLayout.PREFERRED_SIZE, 300, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jpanelside2, javax.swing.GroupLayout.DEFAULT_SIZE, 856, Short.MAX_VALUE)
                .addContainerGap())
        );
        jpanellogoLayout.setVerticalGroup(
            jpanellogoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jpanellogoLayout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addGroup(jpanellogoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jpanelside1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jpanelside2, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
        );

        jpheader.add(jpanellogo, java.awt.BorderLayout.CENTER);

        getContentPane().add(jpheader, java.awt.BorderLayout.PAGE_START);

        jpfooter.setBackground(new java.awt.Color(17, 17, 50));
        jpfooter.setMinimumSize(new java.awt.Dimension(100, 50));
        jpfooter.setPreferredSize(new java.awt.Dimension(1200, 50));

        javax.swing.GroupLayout jpfooterLayout = new javax.swing.GroupLayout(jpfooter);
        jpfooter.setLayout(jpfooterLayout);
        jpfooterLayout.setHorizontalGroup(
            jpfooterLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 1318, Short.MAX_VALUE)
        );
        jpfooterLayout.setVerticalGroup(
            jpfooterLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 50, Short.MAX_VALUE)
        );

        getContentPane().add(jpfooter, java.awt.BorderLayout.PAGE_END);

        jpcontent.setBackground(new java.awt.Color(204, 204, 204));
        jpcontent.setPreferredSize(new java.awt.Dimension(1200, 600));
        jpcontent.setLayout(new java.awt.BorderLayout());

        jPanel1.setBackground(new java.awt.Color(204, 204, 204));
        jPanel1.setPreferredSize(new java.awt.Dimension(1200, 600));
        jPanel1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jpinfo.setBackground(new java.awt.Color(204, 204, 204));
        jpinfo.setPreferredSize(new java.awt.Dimension(757, 600));
        jpinfo.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jptable.setBackground(new java.awt.Color(204, 204, 204));
        jptable.setPreferredSize(new java.awt.Dimension(739, 300));

        jtablesale.setBackground(new java.awt.Color(255, 255, 255));
        jtablesale.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        jtablesale.setFont(new java.awt.Font("Dialog", 0, 14)); // NOI18N
        jtablesale.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        jtablesale.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jScrollPane1.setViewportView(jtablesale);

        javax.swing.GroupLayout jptableLayout = new javax.swing.GroupLayout(jptable);
        jptable.setLayout(jptableLayout);
        jptableLayout.setHorizontalGroup(
            jptableLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jptableLayout.createSequentialGroup()
                .addGap(33, 33, 33)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 626, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(61, Short.MAX_VALUE))
        );
        jptableLayout.setVerticalGroup(
            jptableLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jptableLayout.createSequentialGroup()
                .addGap(22, 22, 22)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 250, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(28, Short.MAX_VALUE))
        );

        jpinfo.add(jptable, new org.netbeans.lib.awtextra.AbsoluteConstraints(6, 293, 720, -1));

        jPanel6.setBackground(new java.awt.Color(204, 204, 204));
        jPanel6.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jppicture.setBackground(new java.awt.Color(204, 204, 204));
        jppicture.setLayout(new java.awt.BorderLayout());
        jppicture.add(jpicture, java.awt.BorderLayout.CENTER);

        jPanel6.add(jppicture, new org.netbeans.lib.awtextra.AbsoluteConstraints(581, 161, 120, 100));

        jpbottons.setBackground(new java.awt.Color(204, 204, 204));
        jpbottons.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jprintcolor.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        jprintcolor.setForeground(new java.awt.Color(0, 0, 0));
        jprintcolor.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Pictures/icons8_print_32px.png"))); // NOI18N
        jprintcolor.setText("طباعة ألوان");
        jprintcolor.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jprintcolor.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jprintcolorActionPerformed(evt);
            }
        });
        jpbottons.add(jprintcolor, new org.netbeans.lib.awtextra.AbsoluteConstraints(359, 65, -1, 38));

        jDelete.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        jDelete.setForeground(new java.awt.Color(0, 0, 0));
        jDelete.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Pictures/icons8_waste_32px.png"))); // NOI18N
        jDelete.setText("حذف");
        jDelete.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jDelete.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jDeleteActionPerformed(evt);
            }
        });
        jpbottons.add(jDelete, new org.netbeans.lib.awtextra.AbsoluteConstraints(6, 65, -1, 38));

        jdeleteall.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        jdeleteall.setForeground(new java.awt.Color(0, 0, 0));
        jdeleteall.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Pictures/trash.png"))); // NOI18N
        jdeleteall.setText("حذف كل");
        jdeleteall.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jdeleteall.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jdeleteallActionPerformed(evt);
            }
        });
        jpbottons.add(jdeleteall, new org.netbeans.lib.awtextra.AbsoluteConstraints(243, 6, -1, 40));

        jsave.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        jsave.setForeground(new java.awt.Color(0, 0, 0));
        jsave.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Pictures/icons8_save_32px_1.png"))); // NOI18N
        jsave.setText("حفظ");
        jsave.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jsave.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jsaveActionPerformed(evt);
            }
        });
        jpbottons.add(jsave, new org.netbeans.lib.awtextra.AbsoluteConstraints(6, 7, -1, 38));

        jsavepdf.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        jsavepdf.setForeground(new java.awt.Color(0, 0, 0));
        jsavepdf.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Pictures/icons8_pdf_32px_1.png"))); // NOI18N
        jsavepdf.setText("حفظ فاتورة");
        jsavepdf.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jsavepdf.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jsavepdfActionPerformed(evt);
            }
        });
        jpbottons.add(jsavepdf, new org.netbeans.lib.awtextra.AbsoluteConstraints(359, 7, -1, 39));

        printtxt.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        printtxt.setForeground(new java.awt.Color(0, 0, 0));
        printtxt.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Pictures/icons8_print_32px.png"))); // NOI18N
        printtxt.setText("طباعة");
        printtxt.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        printtxt.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                printtxtActionPerformed(evt);
            }
        });
        jpbottons.add(printtxt, new org.netbeans.lib.awtextra.AbsoluteConstraints(253, 65, -1, 39));

        jButton1.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        jButton1.setForeground(new java.awt.Color(0, 0, 0));
        jButton1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Pictures/icons8_broom_32px.png"))); // NOI18N
        jButton1.setText("تنظيف");
        jButton1.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });
        jpbottons.add(jButton1, new org.netbeans.lib.awtextra.AbsoluteConstraints(113, 65, -1, 38));

        jGlobalTotal.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        jGlobalTotal.setForeground(new java.awt.Color(0, 0, 0));
        jGlobalTotal.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Pictures/icons8_money_bag_32px.png"))); // NOI18N
        jGlobalTotal.setText("المبلغ");
        jGlobalTotal.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jGlobalTotal.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jGlobalTotalActionPerformed(evt);
            }
        });
        jpbottons.add(jGlobalTotal, new org.netbeans.lib.awtextra.AbsoluteConstraints(109, 7, -1, 39));

        jPanel6.add(jpbottons, new org.netbeans.lib.awtextra.AbsoluteConstraints(6, 160, 500, 120));

        jptextinputinfo.setBackground(new java.awt.Color(204, 204, 204));
        jptextinputinfo.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel2.setBackground(new java.awt.Color(204, 204, 204));
        jLabel2.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(0, 0, 0));
        jLabel2.setText("رقم:");
        jLabel2.setOpaque(true);
        jLabel2.setPreferredSize(new java.awt.Dimension(50, 25));
        jptextinputinfo.add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(680, 10, 20, -1));

        jLabel3.setBackground(new java.awt.Color(204, 204, 204));
        jLabel3.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(0, 0, 0));
        jLabel3.setText("المستهلك:");
        jLabel3.setOpaque(true);
        jLabel3.setPreferredSize(new java.awt.Dimension(50, 25));
        jptextinputinfo.add(jLabel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(530, 10, -1, -1));

        jLabel5.setBackground(new java.awt.Color(204, 204, 204));
        jLabel5.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        jLabel5.setForeground(new java.awt.Color(0, 0, 0));
        jLabel5.setText("الباركود:");
        jLabel5.setOpaque(true);
        jLabel5.setPreferredSize(new java.awt.Dimension(50, 25));
        jptextinputinfo.add(jLabel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(160, 10, 40, 30));

        jLabel6.setBackground(new java.awt.Color(204, 204, 204));
        jLabel6.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        jLabel6.setForeground(new java.awt.Color(0, 0, 0));
        jLabel6.setText("المنتج:");
        jLabel6.setOpaque(true);
        jLabel6.setPreferredSize(new java.awt.Dimension(50, 25));
        jptextinputinfo.add(jLabel6, new org.netbeans.lib.awtextra.AbsoluteConstraints(160, 40, 40, -1));

        jLabel7.setBackground(new java.awt.Color(204, 204, 204));
        jLabel7.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        jLabel7.setForeground(new java.awt.Color(0, 0, 0));
        jLabel7.setText("الثمن:");
        jLabel7.setOpaque(true);
        jLabel7.setPreferredSize(new java.awt.Dimension(50, 25));
        jptextinputinfo.add(jLabel7, new org.netbeans.lib.awtextra.AbsoluteConstraints(340, 50, -1, -1));

        jsal_id.setEditable(false);
        jsal_id.setBackground(new java.awt.Color(204, 204, 204));
        jsal_id.setFont(new java.awt.Font("Dialog", 0, 14)); // NOI18N
        jsal_id.setForeground(new java.awt.Color(0, 0, 0));
        jsal_id.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 1, 0, new java.awt.Color(0, 0, 0)));
        jsal_id.setPreferredSize(new java.awt.Dimension(0, 25));
        jptextinputinfo.add(jsal_id, new org.netbeans.lib.awtextra.AbsoluteConstraints(600, 0, 70, 30));

        jbarcode.setBackground(new java.awt.Color(204, 204, 204));
        jbarcode.setFont(new java.awt.Font("Dialog", 0, 14)); // NOI18N
        jbarcode.setForeground(new java.awt.Color(0, 0, 0));
        jbarcode.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 1, 0, new java.awt.Color(0, 0, 0)));
        jbarcode.setPreferredSize(new java.awt.Dimension(0, 25));
        jbarcode.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jbarcodeKeyPressed(evt);
            }
            public void keyReleased(java.awt.event.KeyEvent evt) {
                jbarcodeKeyReleased(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                jbarcodeKeyTyped(evt);
            }
        });
        jptextinputinfo.add(jbarcode, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 0, 137, 30));

        jprice.setBackground(new java.awt.Color(204, 204, 204));
        jprice.setFont(new java.awt.Font("Dialog", 0, 14)); // NOI18N
        jprice.setForeground(new java.awt.Color(0, 0, 0));
        jprice.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 1, 0, new java.awt.Color(0, 0, 0)));
        jprice.setPreferredSize(new java.awt.Dimension(0, 25));
        jptextinputinfo.add(jprice, new org.netbeans.lib.awtextra.AbsoluteConstraints(210, 40, 118, 30));

        jqty.setBackground(new java.awt.Color(204, 204, 204));
        jqty.setFont(new java.awt.Font("Dialog", 0, 14)); // NOI18N
        jqty.setForeground(new java.awt.Color(0, 255, 0));
        jqty.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        jqty.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 1, 0, new java.awt.Color(0, 0, 0)));
        jqty.setCaretColor(new java.awt.Color(0, 255, 0));
        jqty.setPreferredSize(new java.awt.Dimension(0, 25));
        jqty.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jqtyKeyPressed(evt);
            }
            public void keyReleased(java.awt.event.KeyEvent evt) {
                jqtyKeyReleased(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                jqtyKeyTyped(evt);
            }
        });
        jptextinputinfo.add(jqty, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 80, 137, 30));

        jLabel8.setBackground(new java.awt.Color(204, 204, 204));
        jLabel8.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        jLabel8.setForeground(new java.awt.Color(0, 0, 0));
        jLabel8.setText("الكمية:");
        jLabel8.setOpaque(true);
        jLabel8.setPreferredSize(new java.awt.Dimension(50, 25));
        jptextinputinfo.add(jLabel8, new org.netbeans.lib.awtextra.AbsoluteConstraints(160, 90, 40, 20));

        jLabel9.setBackground(new java.awt.Color(204, 204, 204));
        jLabel9.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        jLabel9.setForeground(new java.awt.Color(0, 0, 0));
        jLabel9.setText("إجمالي:");
        jLabel9.setOpaque(true);
        jLabel9.setPreferredSize(new java.awt.Dimension(50, 25));
        jptextinputinfo.add(jLabel9, new org.netbeans.lib.awtextra.AbsoluteConstraints(340, 90, -1, 20));

        jtotal.setBackground(new java.awt.Color(204, 204, 204));
        jtotal.setFont(new java.awt.Font("Dialog", 0, 14)); // NOI18N
        jtotal.setForeground(new java.awt.Color(0, 0, 0));
        jtotal.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 1, 0, new java.awt.Color(0, 0, 0)));
        jtotal.setPreferredSize(new java.awt.Dimension(0, 25));
        jptextinputinfo.add(jtotal, new org.netbeans.lib.awtextra.AbsoluteConstraints(210, 80, 118, 30));

        jLabel10.setBackground(new java.awt.Color(204, 204, 204));
        jLabel10.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        jLabel10.setForeground(new java.awt.Color(0, 0, 0));
        jLabel10.setText("التخفيض:");
        jLabel10.setOpaque(true);
        jLabel10.setPreferredSize(new java.awt.Dimension(50, 25));
        jptextinputinfo.add(jLabel10, new org.netbeans.lib.awtextra.AbsoluteConstraints(340, 10, 50, 30));

        jp_id.setEditable(false);
        jp_id.setBackground(new java.awt.Color(204, 204, 204));
        jp_id.setFont(new java.awt.Font("Dialog", 0, 14)); // NOI18N
        jp_id.setForeground(new java.awt.Color(0, 0, 0));
        jp_id.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 1, 0, new java.awt.Color(0, 0, 0)));
        jp_id.setPreferredSize(new java.awt.Dimension(0, 25));
        jptextinputinfo.add(jp_id, new org.netbeans.lib.awtextra.AbsoluteConstraints(600, 40, 70, 30));

        jLabel11.setBackground(new java.awt.Color(204, 204, 204));
        jLabel11.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        jLabel11.setForeground(new java.awt.Color(0, 0, 0));
        jLabel11.setText("رقم م:");
        jLabel11.setOpaque(true);
        jLabel11.setPreferredSize(new java.awt.Dimension(50, 25));
        jptextinputinfo.add(jLabel11, new org.netbeans.lib.awtextra.AbsoluteConstraints(680, 50, 30, 20));

        jLabel12.setBackground(new java.awt.Color(204, 204, 204));
        jLabel12.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        jLabel12.setForeground(new java.awt.Color(0, 0, 0));
        jLabel12.setText("المبلغ:");
        jLabel12.setOpaque(true);
        jLabel12.setPreferredSize(new java.awt.Dimension(50, 25));
        jptextinputinfo.add(jLabel12, new org.netbeans.lib.awtextra.AbsoluteConstraints(680, 100, 40, 25));

        jpt.setBackground(new java.awt.Color(0, 0, 0));
        jpt.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jpt.setForeground(new java.awt.Color(0, 204, 0));
        jpt.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Pictures/buying_32px_1.png"))); // NOI18N
        jpt.setText("0.00");
        jpt.setOpaque(true);
        jptextinputinfo.add(jpt, new org.netbeans.lib.awtextra.AbsoluteConstraints(440, 100, 230, 40));

        jdiscount.setBackground(new java.awt.Color(204, 204, 204));
        jdiscount.setFont(new java.awt.Font("Dialog", 0, 14)); // NOI18N
        jdiscount.setForeground(new java.awt.Color(0, 0, 0));
        jdiscount.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "0", "1", "2", "3", "4", "5", "10", "15", "20", "25", "30", "35", "40", "45", "50" }));
        jdiscount.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jdiscount.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jdiscountActionPerformed(evt);
            }
        });
        jptextinputinfo.add(jdiscount, new org.netbeans.lib.awtextra.AbsoluteConstraints(268, 10, 60, -1));

        jLabel4.setBackground(new java.awt.Color(204, 204, 204));
        jLabel4.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        jLabel4.setForeground(new java.awt.Color(0, 0, 0));
        jLabel4.setText("رقم فاتورة:");
        jLabel4.setOpaque(true);
        jLabel4.setPreferredSize(new java.awt.Dimension(50, 25));
        jptextinputinfo.add(jLabel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(530, 50, 60, -1));

        jcus_id.setBackground(new java.awt.Color(204, 204, 204));
        jcus_id.setFont(new java.awt.Font("Dialog", 0, 14)); // NOI18N
        jcus_id.setForeground(new java.awt.Color(0, 0, 0));
        jcus_id.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jcus_id.addPopupMenuListener(new javax.swing.event.PopupMenuListener() {
            public void popupMenuCanceled(javax.swing.event.PopupMenuEvent evt) {
            }
            public void popupMenuWillBecomeInvisible(javax.swing.event.PopupMenuEvent evt) {
                jcus_idPopupMenuWillBecomeInvisible(evt);
            }
            public void popupMenuWillBecomeVisible(javax.swing.event.PopupMenuEvent evt) {
            }
        });
        jcus_id.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jcus_idActionPerformed(evt);
            }
        });
        jptextinputinfo.add(jcus_id, new org.netbeans.lib.awtextra.AbsoluteConstraints(400, 10, 118, 30));

        jinv_id.setEditable(false);
        jinv_id.setBackground(new java.awt.Color(204, 204, 204));
        jinv_id.setFont(new java.awt.Font("Dialog", 0, 14)); // NOI18N
        jinv_id.setForeground(new java.awt.Color(0, 0, 0));
        jinv_id.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 1, 0, new java.awt.Color(0, 0, 0)));
        jinv_id.setPreferredSize(new java.awt.Dimension(0, 25));
        jptextinputinfo.add(jinv_id, new org.netbeans.lib.awtextra.AbsoluteConstraints(400, 40, 118, 30));

        jname.setBackground(new java.awt.Color(204, 204, 204));
        jname.setFont(new java.awt.Font("Dialog", 0, 14)); // NOI18N
        jname.setForeground(new java.awt.Color(0, 0, 0));
        jname.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jname.addPopupMenuListener(new javax.swing.event.PopupMenuListener() {
            public void popupMenuCanceled(javax.swing.event.PopupMenuEvent evt) {
            }
            public void popupMenuWillBecomeInvisible(javax.swing.event.PopupMenuEvent evt) {
                jnamePopupMenuWillBecomeInvisible(evt);
            }
            public void popupMenuWillBecomeVisible(javax.swing.event.PopupMenuEvent evt) {
            }
        });
        jptextinputinfo.add(jname, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 40, 137, 30));

        jLabel13.setBackground(new java.awt.Color(204, 204, 204));
        jLabel13.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        jLabel13.setForeground(new java.awt.Color(0, 0, 0));
        jLabel13.setText("المتبقي:");
        jLabel13.setOpaque(true);
        jLabel13.setPreferredSize(new java.awt.Dimension(50, 25));
        jptextinputinfo.add(jLabel13, new org.netbeans.lib.awtextra.AbsoluteConstraints(360, 120, -1, -1));

        jbalance.setBackground(new java.awt.Color(204, 204, 204));
        jbalance.setFont(new java.awt.Font("Dialog", 0, 14)); // NOI18N
        jbalance.setForeground(new java.awt.Color(0, 0, 0));
        jbalance.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 1, 0, new java.awt.Color(0, 0, 0)));
        jbalance.setCaretColor(new java.awt.Color(0, 0, 0));
        jbalance.setPreferredSize(new java.awt.Dimension(0, 25));
        jptextinputinfo.add(jbalance, new org.netbeans.lib.awtextra.AbsoluteConstraints(210, 120, 140, -1));

        jLabel14.setBackground(new java.awt.Color(204, 204, 204));
        jLabel14.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        jLabel14.setForeground(new java.awt.Color(0, 0, 0));
        jLabel14.setText("دفع:");
        jLabel14.setOpaque(true);
        jLabel14.setPreferredSize(new java.awt.Dimension(50, 25));
        jptextinputinfo.add(jLabel14, new org.netbeans.lib.awtextra.AbsoluteConstraints(160, 130, 30, -1));

        jtax.setBackground(new java.awt.Color(204, 204, 204));
        jtax.setFont(new java.awt.Font("Dialog", 0, 14)); // NOI18N
        jtax.setForeground(new java.awt.Color(0, 0, 0));
        jtax.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 1, 0, new java.awt.Color(0, 0, 0)));
        jtax.setCaretColor(new java.awt.Color(0, 0, 0));
        jtax.setPreferredSize(new java.awt.Dimension(0, 25));
        jtax.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                jtaxKeyReleased(evt);
            }
        });
        jptextinputinfo.add(jtax, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 120, 140, -1));

        jLabel15.setBackground(new java.awt.Color(204, 204, 204));
        jLabel15.setFont(new java.awt.Font("Dialog", 1, 18)); // NOI18N
        jLabel15.setForeground(new java.awt.Color(255, 0, 51));
        jLabel15.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel15.setText("%");
        jLabel15.setOpaque(true);
        jptextinputinfo.add(jLabel15, new org.netbeans.lib.awtextra.AbsoluteConstraints(250, 10, -1, -1));

        jPanel6.add(jptextinputinfo, new org.netbeans.lib.awtextra.AbsoluteConstraints(-1, 0, 720, 160));

        jpinfo.add(jPanel6, new org.netbeans.lib.awtextra.AbsoluteConstraints(6, 0, -1, 281));

        jPanel1.add(jpinfo, new org.netbeans.lib.awtextra.AbsoluteConstraints(584, 7, 730, 590));

        jpfactur.setBackground(new java.awt.Color(204, 204, 204));

        javax.swing.GroupLayout jpfacturLayout = new javax.swing.GroupLayout(jpfactur);
        jpfactur.setLayout(jpfacturLayout);
        jpfacturLayout.setHorizontalGroup(
            jpfacturLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 560, Short.MAX_VALUE)
        );
        jpfacturLayout.setVerticalGroup(
            jpfacturLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 600, Short.MAX_VALUE)
        );

        jPanel1.add(jpfactur, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 0, 560, 600));

        jpcontent.add(jPanel1, java.awt.BorderLayout.CENTER);

        getContentPane().add(jpcontent, java.awt.BorderLayout.CENTER);

        setSize(new java.awt.Dimension(1318, 700));
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void jhideMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jhideMouseClicked
        this.setExtendedState(Home.ICONIFIED);
    }//GEN-LAST:event_jhideMouseClicked

    private void jhideMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jhideMouseEntered
        setActuelColor2(jhide);
    }//GEN-LAST:event_jhideMouseEntered

    private void jhideMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jhideMouseExited
        setResetColor2(jhide);
    }//GEN-LAST:event_jhideMouseExited

    private void jmaxMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jmaxMouseClicked
        if (this.getExtendedState() != Home.MAXIMIZED_BOTH) {
            this.setExtendedState(Home.MAXIMIZED_BOTH);
            jmax.setIcon(new ImageIcon(getClass().getResource("/Pictures/icons8_unchecked_checkbox2_32px_7.png")));
        } else {
            this.setExtendedState(Home.NORMAL);
            jmax.setIcon(new ImageIcon(getClass().getResource("/Pictures/icons8_unchecked_checkbox_32px_7.png")));
        }
    }//GEN-LAST:event_jmaxMouseClicked

    private void jmaxMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jmaxMouseEntered
        setActuelColor2(jmax);
    }//GEN-LAST:event_jmaxMouseEntered

    private void jmaxMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jmaxMouseExited
        setResetColor2(jmax);
    }//GEN-LAST:event_jmaxMouseExited

    private void jexitMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jexitMouseClicked
        frame = new JFrame("Exit");
        if (JOptionPane.showConfirmDialog(frame, "هل حقا تريد الخروج....!",
            "خروج",
            JOptionPane.YES_NO_OPTION) == JOptionPane.YES_NO_OPTION) {
        System.exit(0);
        }
    }//GEN-LAST:event_jexitMouseClicked

    private void jexitMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jexitMouseEntered
        setActuelColor(jexit);
    }//GEN-LAST:event_jexitMouseEntered

    private void jexitMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jexitMouseExited
        setResetColor2(jexit);
    }//GEN-LAST:event_jexitMouseExited

    private void jbackMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jbackMouseEntered
        setActuelColor(jback);
    }//GEN-LAST:event_jbackMouseEntered

    private void jbackMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jbackMouseExited
        setResetColor2(jback);
    }//GEN-LAST:event_jbackMouseExited

    private void jbackMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jbackMouseClicked
      this.dispose();
        Home hm=new Home();
        hm.setVisible(true);
    }//GEN-LAST:event_jbackMouseClicked

    private void jpheaderMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jpheaderMousePressed
        x = evt.getX();
        y = evt.getY();
        setOpacity((float) 0.7);
    }//GEN-LAST:event_jpheaderMousePressed

    private void jpheaderMouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jpheaderMouseReleased
       setOpacity((float) 1.0);
    }//GEN-LAST:event_jpheaderMouseReleased

    private void jpheaderMouseDragged(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jpheaderMouseDragged
        int xx = evt.getXOnScreen();
        int yy = evt.getYOnScreen();
        this.setLocation(xx - x, yy - y);
    }//GEN-LAST:event_jpheaderMouseDragged

    private void jprintcolorActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jprintcolorActionPerformed
        if(jpt.getText().equals("0.00")||jpt.getText().equals("0.0")){
            JOptionPane.showMessageDialog(null, "إملأ بيانات الفاتورة!","إملأ الفاتورة",JOptionPane.CLOSED_OPTION);
        }else{
            try{
                String Dir=System.getProperty("user.dir");
                JasperDesign jd=JRXmlLoader.load(Dir+"/BTotalc.jrxml");

                // JasperDesign jd=JRXmlLoader.load("Report00.jrxml");
                HashMap ar=new HashMap();
                ar.put("cus_sal_id",jsal_id.getText());
                JasperReport jr=JasperCompileManager.compileReport(jd);
                JasperPrint jp=JasperFillManager.fillReport(jr,ar,conn);
                JasperViewer.viewReport(jp,false);
            }catch(Exception ex){
                ex.printStackTrace();
            }
        }
    }//GEN-LAST:event_jprintcolorActionPerformed

    private void jDeleteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jDeleteActionPerformed
        int msg=JOptionPane.showConfirmDialog(null, "هل تريد حذف البيانات!","حذف ",JOptionPane.WARNING_MESSAGE);
        if(msg==0){
            int row= jtablesale.getSelectedRow();
            if(row==-1){
                JOptionPane.showMessageDialog(null, "إختر الخانة المناسبة من الجدول!","حذف!",JOptionPane.ERROR_MESSAGE);
            }else{
                jp_id.getText();
                table.removeRow(row);
                double sum=0;
                for(int i=0;i<jtablesale.getRowCount();i++){
                    sum+=Double.parseDouble(jtablesale.getValueAt(i,8).toString()); //sum=sum+.....
                }
                jpt.setText(String.format("%.2f", sum));
            }
        }
    }//GEN-LAST:event_jDeleteActionPerformed

    private void jdeleteallActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jdeleteallActionPerformed
        int msg=JOptionPane.showConfirmDialog(null, "هل تريد حذف كل البيانات!","جذف الكل",JOptionPane.WARNING_MESSAGE);
        if(msg==0){
            /*  String sql="delete from sales where 1";
            try {
                pst=conn.prepareStatement(sql);
                pst.executeUpdate();
                System.out.println("delete success!");
            } catch (Exception ex) {
                ex.printStackTrace();
            }  */
            jpfactur.removeAll();
            jpfactur.repaint();
            jpfactur.revalidate();
            loadNameProducts();FillNINVOICE();FillsalID();
            showTableSales(); ClearFields();
            jpt.setText("0.00"); cus_id = 0; jcus_id.setSelectedIndex(0); //jcus_id.setSelectedItem("0");
            jdeleteall.setEnabled(false);
             jbarcode.setEnabled(true);jname.setEnabled(true);
        }
    }//GEN-LAST:event_jdeleteallActionPerformed

    private void jsaveActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jsaveActionPerformed
        if(jprice.getText().trim().isEmpty()==true|| jqty.getText().trim().isEmpty()==true||
            jtotal.getText().trim().isEmpty()==true||jqty.getText().equals("0")||
            jcus_id.getSelectedItem().equals(null)|| jcus_id.getSelectedIndex()==0){
             JOptionPane.showMessageDialog(null, "أدخل البيانات!", "خطأ", JOptionPane.WARNING_MESSAGE);
        }else{
            getTotalCoast();
            jqty.setBackground(Color.BLACK);
            ClearFields();
        }
    }//GEN-LAST:event_jsaveActionPerformed

    private void jsavepdfActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jsavepdfActionPerformed
        if(jpt.getText().equals("0.0")||jpt.getText().equals("0.00")){
            JOptionPane.showConfirmDialog(null, "إملأ بيانات الفاتورة!","إملأ الفاتورة",JOptionPane.CLOSED_OPTION);
            System.out.println("Fill invoice!");
        }else{
            try{
                double sum=0;
                for(int i=0;i<jtablesale.getRowCount();i++){
                sum+=Double.parseDouble(jtablesale.getValueAt(i,8).toString()); //sum=sum+.....
                   }
                String Dir=System.getProperty("user.dir");
                JasperDesign jd=JRXmlLoader.load(Dir+"/BTotalc.jrxml");
                // JasperDesign jd=JRXmlLoader.load("Report00.jrxml");
                JasperReport jr=JasperCompileManager.compileReport(jd);
                HashMap ar=new HashMap();
                ar.put("cus_sal_id",jsal_id.getText());

                JasperPrint jp=JasperFillManager.fillReport(jr, ar, conn);
                Date dt=new Date(); timer.setTime(dt);
                SimpleDateFormat time=new SimpleDateFormat("yyyy_MM_dd HH_mm_ss");
                String dtm=time.format(timer.getTime());
                SimpleDateFormat time1=new SimpleDateFormat("yyyy-MM-dd");
                String dtm1=time1.format(timer.getTime());
                String f=System.getProperty("user.home")+
                "\\FactSells\\FactTxt\\"+"INVOICE_SALS"+dtm+".pdf";

                JasperExportManager.exportReportToPdfFile(jp,f);

                String sql1="UPDATE `invoices` SET `path`=? WHERE `inv_id`=?";
                pst=conn.prepareStatement(sql1);
                pst.setString(1,f);
                pst.setString(2,jsal_id.getText());
                pst.executeUpdate();
                FillNINVOICE();
                JOptionPane.showMessageDialog(null, "نجاح حفظ الفاتورة!");

            }catch(Exception ex){
                ex.printStackTrace();
            }
        }
    }//GEN-LAST:event_jsavepdfActionPerformed

    private void printtxtActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_printtxtActionPerformed
        if(jpt.getText().equals("0.0")||jpt.getText().equals("0.00")){
            JOptionPane.showConfirmDialog(null, "إملأ بيانات الفاتورة!","إملأ الفاتورة",JOptionPane.CLOSED_OPTION);
            System.out.println("Fill invoice!");
        }else{
        try{
            String Dir=System.getProperty("user.dir");
            JasperDesign jd=JRXmlLoader.load(Dir+"/BTotal1.jrxml");

            // JasperDesign jd=JRXmlLoader.load("RTotal.jrxml");
            HashMap ar=new HashMap();

            ar.put("cus_sal_id",jsal_id.getText());
            JasperReport jr=JasperCompileManager.compileReport(jd);
            JasperPrint jp=JasperFillManager.fillReport(jr,ar,conn);

            // JasperViewer.viewReport(jp,false);
            // JRViewer ve=new JRViewer(jp);
            JasperPrintManager.printReport(jp, rootPaneCheckingEnabled);

        }catch(Exception ex){
            ex.printStackTrace();
        }
          }
    }//GEN-LAST:event_printtxtActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        ClearFields();
    }//GEN-LAST:event_jButton1ActionPerformed

    private void jGlobalTotalActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jGlobalTotalActionPerformed
        if(jpt.getText().equals("0.00")||jpt.getText().equals("0.00")
            ||jbalance.getText().equals("")||jbalance.getText().equals(null)){
             JOptionPane.showMessageDialog(null, "أدخل البيانات!", "خطأ", JOptionPane.WARNING_MESSAGE);
        }else{
            UpdateStock();
            saveSaleProduct();
            ClearFields();
            jdeleteall.setEnabled(true);
            try{
                String Dir=System.getProperty("user.dir");
                JasperDesign jd=JRXmlLoader.load(Dir+"/BTotal1.jrxml");
                //  JasperDesign jd=JRXmlLoader.load("RTotal.jrxml");
                HashMap ar=new HashMap();
                jpfactur.removeAll();
                jpfactur.repaint();
                jpfactur.revalidate();
                ar.put("cus_sal_id",jsal_id.getText());
                JasperReport jr=JasperCompileManager.compileReport(jd);
                JasperPrint jp=JasperFillManager.fillReport(jr,ar,conn);

                // JasperViewer.viewReport(jp,false);
                JRViewer ve=new JRViewer(jp);
                jpfactur.setLayout(new BorderLayout());
                
                jpfactur.add(ve);
                jbarcode.setEnabled(false);jname.setEnabled(false);
                
            }catch(Exception ex){
                ex.printStackTrace();
            }
        }
    }//GEN-LAST:event_jGlobalTotalActionPerformed

    private void jbarcodeKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jbarcodeKeyPressed
        int key=evt.getKeyCode();
        if(key==KeyEvent.VK_DOWN){
            jqty.requestFocus();
        }else if(key==KeyEvent.VK_UP){
            jbarcode.requestFocus();
        }
    }//GEN-LAST:event_jbarcodeKeyPressed

    private void jbarcodeKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jbarcodeKeyReleased
        jqty.setBackground(Color.BLACK);
        Inputproducts();
    }//GEN-LAST:event_jbarcodeKeyReleased

    private void jbarcodeKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jbarcodeKeyTyped
        jqty.setBackground(Color.BLACK);
        Inputproducts();
    }//GEN-LAST:event_jbarcodeKeyTyped

    private void jqtyKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jqtyKeyPressed
        int key=evt.getKeyCode();
        if(key==KeyEvent.VK_DOWN){
            jqty.requestFocus();
        }else if(key==KeyEvent.VK_UP){
            jbarcode.requestFocus();
        }
        int key1=evt.getKeyChar();
        if(key1==KeyEvent.VK_ENTER ){
            if(jprice.getText().trim().isEmpty()==true|| jqty.getText().trim().isEmpty()==true||
                jtotal.getText().trim().isEmpty()==true||jqty.getText().equals("0")||
                jcus_id.getSelectedItem().equals(null)|| jcus_id.getSelectedIndex()==0){
                 JOptionPane.showMessageDialog(null, "أدخل البيانات!", "خطأ", JOptionPane.WARNING_MESSAGE);
            }else{
                getTotalCoast();
                jqty.setBackground(Color.black);
                ClearFields();
            }
        }
    }//GEN-LAST:event_jqtyKeyPressed

    private void jqtyKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jqtyKeyReleased
        if(jqty.getText().trim().isEmpty()||jqty.getText().equals("0") ){
            jtotal.setText("0.00");
        }else{
            int dis=Integer.parseInt(jdiscount.getSelectedItem().toString());
            if(jdiscount.getSelectedIndex()==0){
                getTotal();
            }else if(dis>=1){
                Discount();
            }
        }
    }//GEN-LAST:event_jqtyKeyReleased

    private void jqtyKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jqtyKeyTyped
        char inumber=evt.getKeyChar();
        if(!(Character.isDigit(inumber))
            ||(inumber==KeyEvent.VK_BACK_SPACE)
            ||(inumber==KeyEvent.VK_DELETE)){
            evt.consume();
        }
    }//GEN-LAST:event_jqtyKeyTyped

    private void jdiscountActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jdiscountActionPerformed
        if(jqty.getText().trim().isEmpty()==true
            ||jtotal.getText().trim().isEmpty()==true){
            jdiscount.setSelectedItem("0");
        }else{
            Discount();
        }
    }//GEN-LAST:event_jdiscountActionPerformed

    private void jcus_idActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jcus_idActionPerformed
        for (Map.Entry<Integer, String> e : cusmap.entrySet()) {
            if (jcus_id.getSelectedItem().toString().equals(e.getValue())) {
                cus_id = e.getKey();
            }
        }
    }//GEN-LAST:event_jcus_idActionPerformed

    private void jnamePopupMenuWillBecomeInvisible(javax.swing.event.PopupMenuEvent evt) {//GEN-FIRST:event_jnamePopupMenuWillBecomeInvisible
        jname.repaint();
        jqty.setBackground(Color.BLACK);
      
        InputNameProducts();
         
    }//GEN-LAST:event_jnamePopupMenuWillBecomeInvisible

    private void jtaxKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jtaxKeyReleased
        double sum=0;
        for(int i=0;i<jtablesale.getRowCount();i++){
            sum+=Double.parseDouble(jtablesale.getValueAt(i,8).toString()); //sum=sum+.....
        }
        double bx=Double.parseDouble(jtax.getText());
        double bxtotal=bx-sum;
        String balance=String.valueOf(bxtotal);
        jbalance.setText(balance);
    }//GEN-LAST:event_jtaxKeyReleased

    private void jcus_idPopupMenuWillBecomeInvisible(javax.swing.event.PopupMenuEvent evt) {//GEN-FIRST:event_jcus_idPopupMenuWillBecomeInvisible
        FillCustumer();
    }//GEN-LAST:event_jcus_idPopupMenuWillBecomeInvisible

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
            java.util.logging.Logger.getLogger(SalesF.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(SalesF.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(SalesF.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(SalesF.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new SalesF().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jDelete;
    private javax.swing.JButton jGlobalTotal;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
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
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JLabel jback;
    private javax.swing.JTextField jbalance;
    private javax.swing.JTextField jbarcode;
    private javax.swing.JComboBox<String> jcus_id;
    private javax.swing.JButton jdeleteall;
    private javax.swing.JComboBox<String> jdiscount;
    private javax.swing.JLabel jexit;
    private javax.swing.JLabel jhide;
    private javax.swing.JTextField jinv_id;
    private javax.swing.JLabel jmax;
    private javax.swing.JComboBox<String> jname;
    private javax.swing.JTextField jp_id;
    private javax.swing.JPanel jpanelclose;
    private javax.swing.JPanel jpanellogo;
    private javax.swing.JPanel jpanelside1;
    private javax.swing.JPanel jpanelside2;
    private javax.swing.JPanel jpbottons;
    private javax.swing.JPanel jpcontent;
    private javax.swing.JPanel jpfactur;
    private javax.swing.JPanel jpfooter;
    private javax.swing.JPanel jpheader;
    private javax.swing.JLabel jpicture;
    private javax.swing.JPanel jpinfo;
    private javax.swing.JPanel jppicture;
    private javax.swing.JTextField jprice;
    private javax.swing.JButton jprintcolor;
    private javax.swing.JLabel jpt;
    private javax.swing.JPanel jptable;
    private javax.swing.JPanel jptextinputinfo;
    private javax.swing.JTextField jqty;
    private javax.swing.JTextField jsal_id;
    private javax.swing.JButton jsave;
    private javax.swing.JButton jsavepdf;
    private javax.swing.JTable jtablesale;
    private javax.swing.JTextField jtax;
    private javax.swing.JTextField jtotal;
    private javax.swing.JButton printtxt;
    // End of variables declaration//GEN-END:variables
}
