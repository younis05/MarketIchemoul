package com.younes;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */



import com.younes.connection.CategoreDB;
import com.younes.connection.ImageProductDB;
import com.younes.connection.JavaConnected;
import com.younes.connection.StockDB;
import com.younes.model.Categore;
import com.younes.model.Customer;
import com.younes.model.ImageProduct;
import com.younes.model.Stock;
import com.younes.util.TexttableCenter;
import java.awt.Color;
import java.awt.Component;
import java.awt.ComponentOrientation;
import java.awt.Image;
import java.awt.event.KeyEvent;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;
import java.util.Vector;
import javax.swing.BorderFactory;
import javax.swing.DefaultComboBoxModel;
import javax.swing.ImageIcon;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.SwingUtilities;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.plaf.basic.BasicInternalFrameUI;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author BOUKHTACHE
 */
public class StockF extends javax.swing.JInternalFrame {

    /**
     * Creates new form HomeF
     */
     ResultSet rst,rst1=null;PreparedStatement pst,pst1=null;
      Connection conn,conn1=null;
   private JFrame frame;
   Customer cus=null;
  TexttableCenter tblcenter=new TexttableCenter();
  Calendar timer = Calendar.getInstance();
    private Map<Integer, String> ctmap = new HashMap();
    private int cat_id = 0;
    String filename = null;
    byte[] pict = null;
    private ImageIcon format = null;
  
    public StockF() {
        initComponents();
        jname.requestFocus();
        conn=JavaConnected.connectionDB();
        applyComponentOrientation(ComponentOrientation.RIGHT_TO_LEFT);
        this.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));
        BasicInternalFrameUI bi=(BasicInternalFrameUI)this.getUI();
        bi.setNorthPane(null);
        StockDB.getPID(jp_id);
        showTableCategore();
        showTableStock();
        fillcomboboxCategories();
        jcat_id.setModel(new DefaultComboBoxModel(fillcomboboxCategories()));
        SwingUtilities.invokeLater(new Runnable(){
            @Override
            public void run() {
               jbarcode.requestFocus();
            } 
        });
        
    }
  /*********Stock**********/
    
    
    private void showTableStock(){
        try{
            DefaultTableModel model = new DefaultTableModel();
            model.addColumn("الرقم");
            model.addColumn("رقم النوع");
            model.addColumn("الباركود");
            model.addColumn("الإسم");
            model.addColumn("السعر");
            model.addColumn("الكمية");
            model.addColumn("إجمالي");
            for(Stock stk:StockDB.getAllStock()){
                model.addRow(new Object[]{ stk.getP_id(),stk.getCat_id(),
                stk.getCodebar(),stk.getName(),stk.getPrice(),stk.getQty(),
                stk.getTotal()});
            }
             jtable.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
	    // jtable.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
             jtable.setRowHeight(20);
             jtable.setModel(model);
             
             for(int i=0;i<7;i++){
                 jtable.getColumnModel().getColumn(i).setCellRenderer(tblcenter);
             }
        }catch(Exception ex){ex.printStackTrace();   }
    }
    private void clearTableStock(){
        jp_id.setText("");jname.setText("");jbarcode.setText("");
        jprice.setText("");jqty.setText("");jtotal.setText("");
        jpath.setText("");jcat_id.setSelectedIndex(0);
        jpicture.setIcon(null);StockDB.getPID(jp_id);
        jtxtsearch.setText("");
        jpicsearch.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Pictures/icons8_search_32px.png")));
        showTableStock();
    }
    private boolean verifyFieldStock(){
        boolean calm=false;
        if(jname.getText().trim().isEmpty()||jbarcode.getText().trim().isEmpty()
           ||jprice.getText().trim().isEmpty()||jqty.getText().trim().isEmpty()
           ||jcat_id.getSelectedIndex()==0||cat_id==0){
          return calm=true;  
        }
        return calm;
    }
    private void showInfoStock(){
        int row=jtable.getSelectedRow();
        if(row==-1){
         JOptionPane.showMessageDialog(null,"إختر العمود المناسب");
        }else{
        String Table_Click=(jtable.getModel().getValueAt(row, 0)).toString();
        String catid = jtable.getValueAt(row, 1).toString();
        int id=Integer.parseInt(Table_Click);
        Stock stk=StockDB.getStock(id);
        jp_id.setText(String.valueOf(stk.getP_id()));
        jname.setText(stk.getName());
        jbarcode.setText(stk.getCodebar());
        jprice.setText(String.valueOf(stk.getPrice()));
        jqty.setText(String.valueOf(stk.getQty()));
        jtotal.setText(String.valueOf(stk.getTotal()));
        try {
            String sql = "SELECT * FROM `categore` WHERE `cat_id`=?";
            pst = conn.prepareStatement(sql);
            pst.setString(1, catid);
            rst = pst.executeQuery();
            while (rst.next()) {
                String cat = rst.getString("catname");
                jcat_id.setSelectedItem(cat);
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, ex);
        }
        }
    }  
    private void showInfoStockImage(){
        int row=jtable.getSelectedRow();
        if(row==-1){
         JOptionPane.showMessageDialog(null,"إختر العمود المناسب");
        }else{
        String p_id=(jtable.getModel().getValueAt(row, 0)).toString();

        try {
            String sql1 = "SELECT * FROM `productimage` WHERE `p_id`=?";
            pst1 = conn.prepareStatement(sql1);
            pst1.setString(1, p_id);
            rst1 = pst1.executeQuery();
            if (rst1.next() == true) {
                byte[] img_data = rst1.getBytes("picture");
                format = new ImageIcon(new ImageIcon(img_data).getImage()
                    .getScaledInstance(jpicture.getWidth(),
                        jpicture.getHeight(), Image.SCALE_SMOOTH));
                jpicture.setIcon(format);
               // jpath.setText(rst1.getString("path"));
            }
        } catch (Exception ex) {
           // JOptionPane.showMessageDialog(null, ex);
           jpicture.setIcon(null);
        }
        }
    } 
    private Vector fillcomboboxCategories() {
        Vector v = new Vector();
        v.add("إختر النوع");
         jcat_id.revalidate();
        try {
            String sql = "SELECT * FROM `categore`";
            pst = conn.prepareStatement(sql);
            rst = pst.executeQuery();
            while (rst.next()) {
                v.add(rst.getString("catname"));
                ctmap.put(rst.getInt("cat_id"), rst.getString("catname"));
                //  jcombcat.addItem(cat);
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, ex);
        } 
        return v;
    }
    public boolean AlertInputNamesAndBarcodes(){
       boolean pr_exists=false;
        try {
            String namep="";//String barcodep="";
            String name=jname.getText();
            String barcode=jbarcode.getText();       
            String sql = "SELECT * FROM `stock` WHERE `barcode`=? or `p_name`=?";
            pst = conn.prepareStatement(sql);
            pst.setString(1, barcode);
            pst.setString(2, name);
            rst = pst.executeQuery();
             while(rst.next()){
                 pr_exists=true;
                   namep=rst.getString("p_name");
                  // barcodep=rst.getString("barcode");
   JOptionPane.showMessageDialog(null,"هذا الإسم("+namep+") أو الباركود"
  + "موجود إختر منتوج آخر","تنبيه!",JOptionPane.WARNING_MESSAGE); 
   jname.setText(""); jbarcode.setText("");
               }
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(null, ex);
            }
         return pr_exists;
    }
    private void getTotal() {
        if (jprice.getText().equals("") && jqty.getText().equals("")) {
            JOptionPane.showMessageDialog(null, "أدخل البيانات!", "خطأ", JOptionPane.WARNING_MESSAGE);
            return;
        } else {
            double a = Double.parseDouble(jqty.getText());
            double b = Double.parseDouble(jprice.getText());
            double c = a * b;
            String res = String.valueOf(c);
            jtotal.setText(res);
            showTableStock();
        }
    }
    private int UpdatePicture() {
     String path=jpath.getText(); String id=jp_id.getText();
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
	pst.setLong(1,Long.parseLong(id) );
        pst.setString(2, path);
        pst.setBytes(3, bos.toByteArray());
	pst.setLong(4, Long.parseLong(id));		
	st = pst.executeUpdate();	
	con.close(); bos.close();		
	} catch (Exception e) {e.printStackTrace();}
		return st; 
    }
    private void showTableStockSerch(){
        try{
            DefaultTableModel model = new DefaultTableModel();
            model.addColumn("الرقم");
            model.addColumn("رقم النوع");
            model.addColumn("الباركود");
            model.addColumn("الإسم");
            model.addColumn("السعر");
            model.addColumn("الكمية");
            model.addColumn("إجمالي");
            for(Stock stk:StockDB.getAllStock(jtxtsearch.getText())){
                model.addRow(new Object[]{ stk.getP_id(),stk.getCat_id(),
                stk.getCodebar(),stk.getName(),stk.getPrice(),stk.getQty(),
                stk.getTotal()});
            }
             jtable.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
	    // jtable.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
             jtable.setRowHeight(20);
             jtable.setModel(model);
             
             for(int i=0;i<7;i++){
                 jtable.getColumnModel().getColumn(i).setCellRenderer(tblcenter);
             }
        }catch(Exception ex){ex.printStackTrace();   }
    }
    private void AlertQty(){
     try{
        String sql0="SELECT `alqty` FROM `info_products` ";
          pst1=conn.prepareStatement(sql0);
          rst1=pst1.executeQuery();
          String sql="SELECT `qty` FROM `stock`";
          pst=conn.prepareStatement(sql);
          rst=pst.executeQuery();
         while(rst.next()){
             String qty0=rst.getString("qty");
             if(rst1.next()==true){
            String qty1=rst1.getString("alqty");  
            if(qty0.equals(qty1)) {
                    jtable.setDefaultRenderer(Object.class, new DefaultTableCellRenderer()
           { @Override
        public Component getTableCellRendererComponent(JTable table, Object value, 
            boolean isSelected, boolean hasFocus, int row, int column) {
        JLabel label=(JLabel)super.getTableCellRendererComponent(table, value, isSelected, hasFocus,row, column); 
        if(value instanceof String){
            String jl=(String)value;
            if(jl.equals(qty0)&& column==6){
             label.setBackground(Color.red);
             label.setForeground(Color.GREEN);
            }else{
              label.setBackground(Color.WHITE);
              label.setForeground(Color.BLACK);  
            }
            if(isSelected){
                label.setBackground(new Color(0,120,218));
            }
        }
        return label;
    }   
      } );   
            }    
        
             }
          } 
	  }catch(Exception ex){
		JOptionPane.showMessageDialog(null, ex.getMessage());  
	  }  
}
    
  /*********Categore**********/  
    
   
    private void showTableCategore(){
        
        try{
            DefaultTableModel model = new DefaultTableModel();
            model.addColumn("الرقم");
            model.addColumn("الإسم");
            for(Categore cat:CategoreDB.getAllCategore()){
                model.addRow(new Object[]{ cat.getCat_id(),cat.getCat_name()});
            }
             jtablecat.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
	     jtablecat.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
             jtablecat.setRowHeight(20);
             jtablecat.setModel(model);
             for(int i=0;i<2;i++){
                 jtablecat.getColumnModel().getColumn(i).setCellRenderer(tblcenter);
             }
        }catch(Exception ex){ex.printStackTrace();   }
    }
    private void clearTableCategore(){
        jcat_pid.setText("");jcatname.setText("");
    }
    private boolean verifyFieldCategore(){
        boolean calm=false;
        if(jcatname.getText().trim().isEmpty()){
          return calm=true;  
        }
        return calm;
    }
    private void showInfoCategore(){
        int row=jtablecat.getSelectedRow();
        if(row==-1){
         JOptionPane.showMessageDialog(null,"إختر العمود المناسب");
        }else{
        String Table_Click=(jtablecat.getModel().getValueAt(row, 0)).toString();
        int id=Integer.parseInt(Table_Click);
        Categore cat=CategoreDB.getCategore(id);
        jcat_pid.setText(String.valueOf(cat.getCat_id()));
        jcatname.setText(cat.getCat_name());
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
        jpcontent = new javax.swing.JPanel();
        jTabbedPane1 = new javax.swing.JTabbedPane();
        jtabstock = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jtable = new javax.swing.JTable();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jbarcode = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        jp_id = new javax.swing.JTextField();
        jname = new javax.swing.JTextField();
        jbclear = new javax.swing.JButton();
        jbupdate = new javax.swing.JButton();
        jimg = new javax.swing.JButton();
        jbdelete = new javax.swing.JButton();
        jLabel5 = new javax.swing.JLabel();
        jprice = new javax.swing.JTextField();
        jqty = new javax.swing.JTextField();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jcat_id = new javax.swing.JComboBox<>();
        jLabel8 = new javax.swing.JLabel();
        jtotal = new javax.swing.JTextField();
        jpath = new javax.swing.JTextField();
        jLabel9 = new javax.swing.JLabel();
        jpicture = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        jbsave = new javax.swing.JButton();
        jpicsearch = new javax.swing.JLabel();
        jLabel15 = new javax.swing.JLabel();
        jtxtsearch = new javax.swing.JTextField();
        jtabcategore = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        jtablecat = new javax.swing.JTable();
        jLabel12 = new javax.swing.JLabel();
        jLabel13 = new javax.swing.JLabel();
        jcat_pid = new javax.swing.JTextField();
        jcatname = new javax.swing.JTextField();
        jbclearcat = new javax.swing.JButton();
        jbupdatecat = new javax.swing.JButton();
        jbsavecat = new javax.swing.JButton();
        jbdeletecat = new javax.swing.JButton();
        jLabel14 = new javax.swing.JLabel();
        jcatname1 = new javax.swing.JTextField();

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
        jLabel4.setText("إدارة المخزون");
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

        jpcontent.setBackground(new java.awt.Color(204, 204, 204));
        jpcontent.setLayout(new java.awt.BorderLayout());

        jTabbedPane1.setBackground(new java.awt.Color(204, 204, 204));
        jTabbedPane1.setForeground(new java.awt.Color(0, 0, 0));
        jTabbedPane1.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jTabbedPane1.setFont(new java.awt.Font("Dialog", 1, 18)); // NOI18N
        jTabbedPane1.setOpaque(true);
        jTabbedPane1.setPreferredSize(new java.awt.Dimension(750, 500));

        jtabstock.setBackground(new java.awt.Color(204, 204, 204));
        jtabstock.setForeground(new java.awt.Color(0, 0, 0));
        jtabstock.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        jtabstock.setFont(new java.awt.Font("Dialog", 1, 18)); // NOI18N
        jtabstock.setPreferredSize(new java.awt.Dimension(750, 500));
        jtabstock.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jtable.setBackground(new java.awt.Color(255, 255, 255));
        jtable.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        jtable.setFont(new java.awt.Font("Dialog", 1, 12)); // NOI18N
        jtable.setForeground(new java.awt.Color(0, 0, 0));
        jtable.setModel(new javax.swing.table.DefaultTableModel(
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
        jtable.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jtable.setPreferredSize(new java.awt.Dimension(400, 400));
        jtable.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jtableMouseClicked(evt);
            }
        });
        jtable.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jtableKeyPressed(evt);
            }
            public void keyReleased(java.awt.event.KeyEvent evt) {
                jtableKeyReleased(evt);
            }
        });
        jScrollPane1.setViewportView(jtable);

        jtabstock.add(jScrollPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 10, 470, 300));

        jLabel1.setBackground(new java.awt.Color(204, 204, 204));
        jLabel1.setFont(new java.awt.Font("Dialog", 1, 18)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(0, 0, 0));
        jLabel1.setText("الباركود:");
        jLabel1.setOpaque(true);
        jtabstock.add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(660, 90, 60, 30));

        jLabel2.setBackground(new java.awt.Color(204, 204, 204));
        jLabel2.setFont(new java.awt.Font("Dialog", 1, 18)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(0, 0, 0));
        jLabel2.setText("رقم:");
        jLabel2.setOpaque(true);
        jtabstock.add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(660, 20, 50, 30));

        jbarcode.setBackground(new java.awt.Color(204, 204, 204));
        jbarcode.setFont(new java.awt.Font("Dialog", 0, 18)); // NOI18N
        jbarcode.setForeground(new java.awt.Color(0, 0, 0));
        jbarcode.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 1, 0, new java.awt.Color(0, 0, 0)));
        jbarcode.setCaretColor(new java.awt.Color(0, 0, 0));
        jbarcode.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                jbarcodeKeyTyped(evt);
            }
        });
        jtabstock.add(jbarcode, new org.netbeans.lib.awtextra.AbsoluteConstraints(510, 80, 140, 30));

        jLabel3.setBackground(new java.awt.Color(204, 204, 204));
        jLabel3.setFont(new java.awt.Font("Dialog", 1, 18)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(0, 0, 0));
        jLabel3.setText("النوع:");
        jLabel3.setOpaque(true);
        jtabstock.add(jLabel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(660, 50, 50, 30));

        jp_id.setEditable(false);
        jp_id.setBackground(new java.awt.Color(204, 204, 204));
        jp_id.setFont(new java.awt.Font("Dialog", 0, 18)); // NOI18N
        jp_id.setForeground(new java.awt.Color(0, 0, 0));
        jp_id.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 1, 0, new java.awt.Color(0, 0, 0)));
        jp_id.setCaretColor(new java.awt.Color(255, 255, 255));
        jtabstock.add(jp_id, new org.netbeans.lib.awtextra.AbsoluteConstraints(510, 10, 140, 30));

        jname.setBackground(new java.awt.Color(204, 204, 204));
        jname.setFont(new java.awt.Font("Dialog", 0, 18)); // NOI18N
        jname.setForeground(new java.awt.Color(0, 0, 0));
        jname.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 1, 0, new java.awt.Color(0, 0, 0)));
        jname.setCaretColor(new java.awt.Color(0, 0, 0));
        jtabstock.add(jname, new org.netbeans.lib.awtextra.AbsoluteConstraints(510, 120, 140, 30));

        jbclear.setFont(new java.awt.Font("Dialog", 1, 18)); // NOI18N
        jbclear.setForeground(new java.awt.Color(0, 0, 0));
        jbclear.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Pictures/icons8_broom_32px.png"))); // NOI18N
        jbclear.setText("تنظيف");
        jbclear.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jbclear.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jbclearActionPerformed(evt);
            }
        });
        jtabstock.add(jbclear, new org.netbeans.lib.awtextra.AbsoluteConstraints(490, 390, -1, 40));

        jbupdate.setFont(new java.awt.Font("Dialog", 1, 18)); // NOI18N
        jbupdate.setForeground(new java.awt.Color(0, 0, 0));
        jbupdate.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Pictures/icons8_refresh_32px.png"))); // NOI18N
        jbupdate.setText("تحديث");
        jbupdate.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jbupdate.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jbupdateActionPerformed(evt);
            }
        });
        jtabstock.add(jbupdate, new org.netbeans.lib.awtextra.AbsoluteConstraints(490, 330, -1, 40));

        jimg.setFont(new java.awt.Font("Dialog", 1, 18)); // NOI18N
        jimg.setForeground(new java.awt.Color(0, 0, 0));
        jimg.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Pictures/icons8_picture_32px.png"))); // NOI18N
        jimg.setText("إختر الصورة");
        jimg.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jimg.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jimgActionPerformed(evt);
            }
        });
        jtabstock.add(jimg, new org.netbeans.lib.awtextra.AbsoluteConstraints(220, 330, 160, 40));

        jbdelete.setFont(new java.awt.Font("Dialog", 1, 18)); // NOI18N
        jbdelete.setForeground(new java.awt.Color(0, 0, 0));
        jbdelete.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Pictures/icons8_waste_32px.png"))); // NOI18N
        jbdelete.setText("حذف");
        jbdelete.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jbdelete.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jbdeleteActionPerformed(evt);
            }
        });
        jtabstock.add(jbdelete, new org.netbeans.lib.awtextra.AbsoluteConstraints(630, 390, -1, 40));

        jLabel5.setBackground(new java.awt.Color(204, 204, 204));
        jLabel5.setFont(new java.awt.Font("Dialog", 1, 18)); // NOI18N
        jLabel5.setForeground(new java.awt.Color(0, 0, 0));
        jLabel5.setText("السعر:");
        jLabel5.setOpaque(true);
        jtabstock.add(jLabel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(660, 170, 60, 30));

        jprice.setBackground(new java.awt.Color(204, 204, 204));
        jprice.setFont(new java.awt.Font("Dialog", 0, 18)); // NOI18N
        jprice.setForeground(new java.awt.Color(0, 0, 0));
        jprice.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 1, 0, new java.awt.Color(0, 0, 0)));
        jprice.setCaretColor(new java.awt.Color(0, 0, 0));
        jprice.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                jpriceKeyTyped(evt);
            }
        });
        jtabstock.add(jprice, new org.netbeans.lib.awtextra.AbsoluteConstraints(510, 160, 140, 30));

        jqty.setBackground(new java.awt.Color(204, 204, 204));
        jqty.setFont(new java.awt.Font("Dialog", 0, 18)); // NOI18N
        jqty.setForeground(new java.awt.Color(0, 0, 0));
        jqty.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 1, 0, new java.awt.Color(0, 0, 0)));
        jqty.setCaretColor(new java.awt.Color(0, 0, 0));
        jqty.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                jqtyKeyReleased(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                jqtyKeyTyped(evt);
            }
        });
        jtabstock.add(jqty, new org.netbeans.lib.awtextra.AbsoluteConstraints(510, 200, 140, 30));

        jLabel6.setBackground(new java.awt.Color(204, 204, 204));
        jLabel6.setFont(new java.awt.Font("Dialog", 1, 18)); // NOI18N
        jLabel6.setForeground(new java.awt.Color(0, 0, 0));
        jLabel6.setText("الكمية:");
        jLabel6.setOpaque(true);
        jtabstock.add(jLabel6, new org.netbeans.lib.awtextra.AbsoluteConstraints(660, 210, 60, 30));

        jLabel7.setBackground(new java.awt.Color(204, 204, 204));
        jLabel7.setFont(new java.awt.Font("Dialog", 1, 18)); // NOI18N
        jLabel7.setForeground(new java.awt.Color(0, 0, 0));
        jLabel7.setText("الإسم:");
        jLabel7.setOpaque(true);
        jtabstock.add(jLabel7, new org.netbeans.lib.awtextra.AbsoluteConstraints(660, 130, 50, 30));

        jcat_id.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        jcat_id.setForeground(new java.awt.Color(0, 0, 0));
        jcat_id.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jcat_id.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jcat_idActionPerformed(evt);
            }
        });
        jtabstock.add(jcat_id, new org.netbeans.lib.awtextra.AbsoluteConstraints(510, 50, 140, 30));

        jLabel8.setBackground(new java.awt.Color(204, 204, 204));
        jLabel8.setFont(new java.awt.Font("Dialog", 1, 18)); // NOI18N
        jLabel8.setForeground(new java.awt.Color(0, 0, 0));
        jLabel8.setText("إجمالي:");
        jLabel8.setOpaque(true);
        jtabstock.add(jLabel8, new org.netbeans.lib.awtextra.AbsoluteConstraints(660, 250, 60, 30));

        jtotal.setEditable(false);
        jtotal.setBackground(new java.awt.Color(204, 204, 204));
        jtotal.setFont(new java.awt.Font("Dialog", 0, 18)); // NOI18N
        jtotal.setForeground(new java.awt.Color(0, 0, 0));
        jtotal.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 1, 0, new java.awt.Color(0, 0, 0)));
        jtotal.setCaretColor(new java.awt.Color(0, 0, 0));
        jtabstock.add(jtotal, new org.netbeans.lib.awtextra.AbsoluteConstraints(510, 240, 140, 30));

        jpath.setEditable(false);
        jpath.setBackground(new java.awt.Color(204, 204, 204));
        jpath.setFont(new java.awt.Font("Dialog", 0, 18)); // NOI18N
        jpath.setForeground(new java.awt.Color(0, 0, 0));
        jpath.setBorder(null);
        jpath.setCaretColor(new java.awt.Color(0, 0, 0));
        jtabstock.add(jpath, new org.netbeans.lib.awtextra.AbsoluteConstraints(510, 280, 140, 30));

        jLabel9.setBackground(new java.awt.Color(204, 204, 204));
        jLabel9.setFont(new java.awt.Font("Dialog", 1, 18)); // NOI18N
        jLabel9.setForeground(new java.awt.Color(0, 0, 0));
        jLabel9.setText("البحث:");
        jLabel9.setOpaque(true);
        jtabstock.add(jLabel9, new org.netbeans.lib.awtextra.AbsoluteConstraints(410, 390, 50, 30));
        jtabstock.add(jpicture, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 320, 130, 100));

        jLabel11.setBackground(new java.awt.Color(204, 204, 204));
        jLabel11.setFont(new java.awt.Font("Dialog", 1, 18)); // NOI18N
        jLabel11.setForeground(new java.awt.Color(0, 0, 0));
        jLabel11.setText("الرابط:");
        jLabel11.setOpaque(true);
        jtabstock.add(jLabel11, new org.netbeans.lib.awtextra.AbsoluteConstraints(660, 290, 60, 30));

        jbsave.setFont(new java.awt.Font("Dialog", 1, 18)); // NOI18N
        jbsave.setForeground(new java.awt.Color(0, 0, 0));
        jbsave.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Pictures/icons8_save_32px_1.png"))); // NOI18N
        jbsave.setText("حفظ");
        jbsave.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jbsave.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jbsaveActionPerformed(evt);
            }
        });
        jtabstock.add(jbsave, new org.netbeans.lib.awtextra.AbsoluteConstraints(630, 330, -1, 40));

        jpicsearch.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jpicsearch.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Pictures/icons8_search_32px.png"))); // NOI18N
        jtabstock.add(jpicsearch, new org.netbeans.lib.awtextra.AbsoluteConstraints(150, 390, 40, 30));

        jLabel15.setBackground(new java.awt.Color(204, 204, 204));
        jLabel15.setFont(new java.awt.Font("Dialog", 1, 18)); // NOI18N
        jLabel15.setForeground(new java.awt.Color(0, 0, 0));
        jLabel15.setText("الصورة:");
        jLabel15.setOpaque(true);
        jtabstock.add(jLabel15, new org.netbeans.lib.awtextra.AbsoluteConstraints(390, 330, 60, 30));

        jtxtsearch.setBackground(new java.awt.Color(204, 204, 204));
        jtxtsearch.setFont(new java.awt.Font("Dialog", 0, 18)); // NOI18N
        jtxtsearch.setForeground(new java.awt.Color(0, 0, 0));
        jtxtsearch.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 1, 0, new java.awt.Color(0, 0, 0)));
        jtxtsearch.setCaretColor(new java.awt.Color(0, 0, 0));
        jtxtsearch.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                jtxtsearchKeyReleased(evt);
            }
        });
        jtabstock.add(jtxtsearch, new org.netbeans.lib.awtextra.AbsoluteConstraints(193, 390, 210, 30));

        jTabbedPane1.addTab("المخزن", new javax.swing.ImageIcon(getClass().getResource("/Pictures/small_business_32px_1.png")), jtabstock); // NOI18N

        jtabcategore.setBackground(new java.awt.Color(204, 204, 204));
        jtabcategore.setForeground(new java.awt.Color(0, 0, 0));
        jtabcategore.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        jtabcategore.setPreferredSize(new java.awt.Dimension(750, 500));
        jtabcategore.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jtablecat.setBackground(new java.awt.Color(255, 255, 255));
        jtablecat.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        jtablecat.setFont(new java.awt.Font("Dialog", 1, 12)); // NOI18N
        jtablecat.setForeground(new java.awt.Color(0, 0, 0));
        jtablecat.setModel(new javax.swing.table.DefaultTableModel(
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
        jtablecat.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jtablecat.setPreferredSize(new java.awt.Dimension(400, 400));
        jtablecat.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jtablecatMouseClicked(evt);
            }
        });
        jtablecat.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jtablecatKeyPressed(evt);
            }
            public void keyReleased(java.awt.event.KeyEvent evt) {
                jtablecatKeyReleased(evt);
            }
        });
        jScrollPane2.setViewportView(jtablecat);

        jtabcategore.add(jScrollPane2, new org.netbeans.lib.awtextra.AbsoluteConstraints(26, 14, -1, 375));

        jLabel12.setBackground(new java.awt.Color(204, 204, 204));
        jLabel12.setFont(new java.awt.Font("Dialog", 1, 18)); // NOI18N
        jLabel12.setForeground(new java.awt.Color(0, 0, 0));
        jLabel12.setText("رقم:");
        jLabel12.setOpaque(true);
        jtabcategore.add(jLabel12, new org.netbeans.lib.awtextra.AbsoluteConstraints(660, 50, 50, 30));

        jLabel13.setBackground(new java.awt.Color(204, 204, 204));
        jLabel13.setFont(new java.awt.Font("Dialog", 1, 18)); // NOI18N
        jLabel13.setForeground(new java.awt.Color(0, 0, 0));
        jLabel13.setText("الإسم:");
        jLabel13.setOpaque(true);
        jtabcategore.add(jLabel13, new org.netbeans.lib.awtextra.AbsoluteConstraints(660, 90, 50, 30));

        jcat_pid.setEditable(false);
        jcat_pid.setBackground(new java.awt.Color(204, 204, 204));
        jcat_pid.setFont(new java.awt.Font("Dialog", 0, 18)); // NOI18N
        jcat_pid.setForeground(new java.awt.Color(0, 0, 0));
        jcat_pid.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 1, 0, new java.awt.Color(0, 0, 0)));
        jcat_pid.setCaretColor(new java.awt.Color(255, 255, 255));
        jtabcategore.add(jcat_pid, new org.netbeans.lib.awtextra.AbsoluteConstraints(510, 45, 140, 30));

        jcatname.setBackground(new java.awt.Color(204, 204, 204));
        jcatname.setFont(new java.awt.Font("Dialog", 0, 18)); // NOI18N
        jcatname.setForeground(new java.awt.Color(0, 0, 0));
        jcatname.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 1, 0, new java.awt.Color(0, 0, 0)));
        jcatname.setCaretColor(new java.awt.Color(0, 0, 0));
        jtabcategore.add(jcatname, new org.netbeans.lib.awtextra.AbsoluteConstraints(510, 85, 140, 30));

        jbclearcat.setFont(new java.awt.Font("Dialog", 1, 18)); // NOI18N
        jbclearcat.setForeground(new java.awt.Color(0, 0, 0));
        jbclearcat.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Pictures/icons8_broom_32px.png"))); // NOI18N
        jbclearcat.setText("تنظيف");
        jbclearcat.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jbclearcat.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jbclearcatActionPerformed(evt);
            }
        });
        jtabcategore.add(jbclearcat, new org.netbeans.lib.awtextra.AbsoluteConstraints(500, 240, -1, 40));

        jbupdatecat.setFont(new java.awt.Font("Dialog", 1, 18)); // NOI18N
        jbupdatecat.setForeground(new java.awt.Color(0, 0, 0));
        jbupdatecat.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Pictures/icons8_refresh_32px.png"))); // NOI18N
        jbupdatecat.setText("تحديث");
        jbupdatecat.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jbupdatecat.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jbupdatecatActionPerformed(evt);
            }
        });
        jtabcategore.add(jbupdatecat, new org.netbeans.lib.awtextra.AbsoluteConstraints(500, 180, -1, 40));

        jbsavecat.setFont(new java.awt.Font("Dialog", 1, 18)); // NOI18N
        jbsavecat.setForeground(new java.awt.Color(0, 0, 0));
        jbsavecat.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Pictures/icons8_save_32px_1.png"))); // NOI18N
        jbsavecat.setText("حفظ");
        jbsavecat.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jbsavecat.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jbsavecatActionPerformed(evt);
            }
        });
        jtabcategore.add(jbsavecat, new org.netbeans.lib.awtextra.AbsoluteConstraints(640, 180, -1, 40));

        jbdeletecat.setFont(new java.awt.Font("Dialog", 1, 18)); // NOI18N
        jbdeletecat.setForeground(new java.awt.Color(0, 0, 0));
        jbdeletecat.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Pictures/icons8_waste_32px.png"))); // NOI18N
        jbdeletecat.setText("حذف");
        jbdeletecat.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jbdeletecat.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jbdeletecatActionPerformed(evt);
            }
        });
        jtabcategore.add(jbdeletecat, new org.netbeans.lib.awtextra.AbsoluteConstraints(640, 240, -1, 40));

        jLabel14.setBackground(new java.awt.Color(204, 204, 204));
        jLabel14.setFont(new java.awt.Font("Dialog", 1, 18)); // NOI18N
        jLabel14.setForeground(new java.awt.Color(0, 0, 0));
        jLabel14.setText("الإسم:");
        jLabel14.setOpaque(true);
        jtabcategore.add(jLabel14, new org.netbeans.lib.awtextra.AbsoluteConstraints(660, 90, 50, 30));

        jcatname1.setBackground(new java.awt.Color(204, 204, 204));
        jcatname1.setFont(new java.awt.Font("Dialog", 0, 18)); // NOI18N
        jcatname1.setForeground(new java.awt.Color(0, 0, 0));
        jcatname1.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 1, 0, new java.awt.Color(0, 0, 0)));
        jcatname1.setCaretColor(new java.awt.Color(0, 0, 0));
        jtabcategore.add(jcatname1, new org.netbeans.lib.awtextra.AbsoluteConstraints(510, 85, 140, 30));

        jTabbedPane1.addTab("النوع", new javax.swing.ImageIcon(getClass().getResource("/Pictures/shopping_bag_32px_1.png")), jtabcategore); // NOI18N

        jpcontent.add(jTabbedPane1, java.awt.BorderLayout.CENTER);

        getContentPane().add(jpcontent, java.awt.BorderLayout.CENTER);

        setBounds(0, 0, 750, 600);
    }// </editor-fold>//GEN-END:initComponents

    private void jimgActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jimgActionPerformed
    JFileChooser chooser = new JFileChooser();
        FileNameExtensionFilter filter = new FileNameExtensionFilter("*.Images", "jpg", "gif", "png");
        chooser.addChoosableFileFilter(filter);
        int ap = chooser.showOpenDialog(null);
        if (ap == JFileChooser.APPROVE_OPTION) {
            filename = chooser.getSelectedFile().getAbsolutePath();
            ImageIcon imageIcon = new ImageIcon(new ImageIcon(filename).getImage()
                .getScaledInstance(jpicture.getWidth(),
                jpicture.getHeight(), Image.SCALE_SMOOTH));
                jpicture.setIcon(imageIcon);
        }
        try {
            if(filename==null){
                JOptionPane.showMessageDialog(null, "Fill Image!");
            }else{
                File image = new File(filename);
                ByteArrayOutputStream bos;
                try (FileInputStream fis = new FileInputStream(image)) {
                    bos = new ByteArrayOutputStream();
                    byte[] buf = new byte[1024];
                    for (int readNum; (readNum = fis.read(buf)) != -1;) {
                        bos.write(buf, 0, readNum);
                    }
                }
                pict = bos.toByteArray();
                bos.close();
                jpath.setText(filename);
            }
        } catch (IOException ex) {
            JOptionPane.showMessageDialog(null, ex);
        }       
       
    }//GEN-LAST:event_jimgActionPerformed

    private void jbdeleteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbdeleteActionPerformed
       String idcus=jp_id.getText();
       if(!idcus.trim().isEmpty()){
       int msg=JOptionPane.showConfirmDialog(null, "هل تريد حذف المعلومات..!","حذف",JOptionPane.YES_NO_OPTION);
      if(msg==0){
        int id=Integer.parseInt(jp_id.getText());
        int status=StockDB.delete(id);
            if(status>0){
                showTableStock();
                JOptionPane.showMessageDialog(null,"نجاح الحذف");
                clearTableStock();
            }else{
               JOptionPane.showMessageDialog(null,"خطأ إدخال البينات"); 
            }
        } }else{
         JOptionPane.showMessageDialog(null,"أدخل البيانات...!");   
       }
    }//GEN-LAST:event_jbdeleteActionPerformed

    private void jbupdateActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbupdateActionPerformed
      String name=jname.getText(); String barcode=jbarcode.getText();
      String price=jprice.getText(); String qty=jqty.getText();
      String total=jtotal.getText();  String path=jpath.getText();
       String id=jp_id.getText();
       if(!id.trim().isEmpty()){
       if(!verifyFieldStock()){
           if(path.trim().isEmpty()==true||path.equals("")){
               Stock stk=new Stock();
            stk.setP_id(Long.parseLong(id));
            stk.setCat_id(cat_id);
            stk.setCodebar(barcode);
            stk.setName(name);
            stk.setPrice(Double.parseDouble(price));
            stk.setQty(Integer.parseInt(qty));
            stk.setTotal(Double.parseDouble(total));
            int status1=StockDB.update(stk);
            if(status1>0){
                showTableStock();
                JOptionPane.showMessageDialog(null,"نجاح التحديث");
                clearTableStock();
            }else{
               JOptionPane.showMessageDialog(null,"خطأ إدخال البينات"); 
            }
           }else{
               Stock stk=new Stock();
            stk.setP_id(Long.parseLong(id));
            stk.setCat_id(cat_id);
            stk.setCodebar(barcode);
            stk.setName(name);
            stk.setPrice(Double.parseDouble(price));
            stk.setQty(Integer.parseInt(qty));
            stk.setTotal(Double.parseDouble(total));
            int status1=StockDB.update(stk);
            UpdatePicture();
            if(status1>0){
                showTableStock();
                JOptionPane.showMessageDialog(null,"نجاح التحديث");
                clearTableStock();
            }else{
               JOptionPane.showMessageDialog(null,"خطأ إدخال البينات"); 
            }
           }
            
       }else{
         JOptionPane.showMessageDialog(null,"أدخل البيانات...!");   
       } }else{
         JOptionPane.showMessageDialog(null,"أدخل البيانات...!");   
       }
        
    }//GEN-LAST:event_jbupdateActionPerformed

    private void jbclearActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbclearActionPerformed
        clearTableStock();
    }//GEN-LAST:event_jbclearActionPerformed

    private void jtableMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jtableMouseClicked
           showInfoStock(); showInfoStockImage();
    }//GEN-LAST:event_jtableMouseClicked

    private void jtableKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jtableKeyPressed
       if(evt.getKeyCode()==KeyEvent.VK_UP||evt.getKeyCode()==KeyEvent.VK_DOWN){
          showInfoStock(); showInfoStockImage();
       }
    }//GEN-LAST:event_jtableKeyPressed

    private void jtableKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jtableKeyReleased
        if(evt.getKeyCode()==KeyEvent.VK_UP||evt.getKeyCode()==KeyEvent.VK_DOWN){
          showInfoStock(); showInfoStockImage();
        }
    }//GEN-LAST:event_jtableKeyReleased

    private void jbsaveActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbsaveActionPerformed
      String name=jname.getText(); String barcode=jbarcode.getText();
      String price=jprice.getText(); String qty=jqty.getText();
      String total=jtotal.getText();  String path=jpath.getText();
      String pid=jp_id.getText();
       if(verifyFieldStock()){
          JOptionPane.showMessageDialog(null,"أدخل البيانات...!");
       }else if(AlertInputNamesAndBarcodes()){
               
       }else{
               Stock stk=new Stock();
            stk.setP_id(Long.parseLong(pid));
            stk.setCat_id(cat_id);
            stk.setCodebar(barcode);
            stk.setName(name);
            stk.setPrice(Double.parseDouble(price));
            stk.setQty(Integer.parseInt(qty));
            stk.setTotal(Double.parseDouble(total));
            int status1=StockDB.save(stk);
            
            ImageProduct imgp=new ImageProduct();
            imgp.setP_id(Long.parseLong(pid));
            imgp.setPath(path);
            imgp.setImg(pict);
            int status=ImageProductDB.save(imgp,pict);
            if(status>0||status1>0){
                showTableStock();
                JOptionPane.showMessageDialog(null,"نجاح اخال المعلومات");
                clearTableStock();
            }else{
               JOptionPane.showMessageDialog(null,"خطأ إدخال البينات"); 
            }
            }
    }//GEN-LAST:event_jbsaveActionPerformed

    private void jqtyKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jqtyKeyTyped
        char inumber = evt.getKeyChar();
        if (!(Character.isDigit(inumber))
            || (inumber == KeyEvent.VK_BACK_SPACE)
            || (inumber == KeyEvent.VK_DELETE)) {
            evt.consume();
        }
    }//GEN-LAST:event_jqtyKeyTyped

    private void jtablecatMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jtablecatMouseClicked
        showInfoCategore();
    }//GEN-LAST:event_jtablecatMouseClicked

    private void jtablecatKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jtablecatKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_UP||evt.getKeyCode()==KeyEvent.VK_DOWN){
            showInfoCategore();
        }
    }//GEN-LAST:event_jtablecatKeyPressed

    private void jtablecatKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jtablecatKeyReleased
        if(evt.getKeyCode()==KeyEvent.VK_UP||evt.getKeyCode()==KeyEvent.VK_DOWN){
            showInfoCategore();
        }
    }//GEN-LAST:event_jtablecatKeyReleased

    private void jbclearcatActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbclearcatActionPerformed
        clearTableCategore();
    }//GEN-LAST:event_jbclearcatActionPerformed

    private void jbupdatecatActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbupdatecatActionPerformed
        String catname=jcatname.getText();
        String id=jcat_pid.getText();
        if(!id.trim().isEmpty()){
            if(!verifyFieldCategore()){
              Categore cat=new Categore();
                cat.setCat_id(Long.parseLong(id));
                cat.setCat_name(catname);
                int status=CategoreDB.update(cat);
                if(status>0){
                    showTableCategore();
                    JOptionPane.showMessageDialog(null,"نجاح التحديث");
                    clearTableCategore();
                    fillcomboboxCategories();
                    jcat_id.setModel(new DefaultComboBoxModel(fillcomboboxCategories()));
                }else{
                    JOptionPane.showMessageDialog(null,"خطأ إدخال البينات");
                }
            }else{
                JOptionPane.showMessageDialog(null,"أدخل البيانات...!");
            } }else{
                JOptionPane.showMessageDialog(null,"أدخل البيانات...!");
            }

    }//GEN-LAST:event_jbupdatecatActionPerformed

    private void jbsavecatActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbsavecatActionPerformed
        String catname=jcatname.getText();
        if(!verifyFieldCategore()){
           Categore cat=new Categore();
           cat.setCat_name(catname);
            int status=CategoreDB.save(cat);
            if(status>0){
                showTableCategore();
                JOptionPane.showMessageDialog(null,"نجاح اخال المعلومات");
                clearTableCategore();
               fillcomboboxCategories();
               jcat_id.setModel(new DefaultComboBoxModel(fillcomboboxCategories()));
            }else{
                JOptionPane.showMessageDialog(null,"خطأ إدخال البينات");
            }
        }else{
            JOptionPane.showMessageDialog(null,"أدخل البيانات...!");
        }

    }//GEN-LAST:event_jbsavecatActionPerformed

    private void jbdeletecatActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbdeletecatActionPerformed
        String iduser=jcat_pid.getText();
        if(!iduser.trim().isEmpty()){
            int msg=JOptionPane.showConfirmDialog(null, "هل تريد حذف المعلومات..!","حذف",JOptionPane.YES_NO_OPTION);
            if(msg==0){
                int id=Integer.parseInt(jcat_pid.getText());
                int status=CategoreDB.delete(id);
                if(status>0){
                    showTableCategore();
                    JOptionPane.showMessageDialog(null,"نجاح الحذف");
                    clearTableCategore();
                   fillcomboboxCategories();
                   jcat_id.setModel(new DefaultComboBoxModel(fillcomboboxCategories()));
                }else{
                    JOptionPane.showMessageDialog(null,"خطأ إدخال البينات");
                }
            } }else{
                JOptionPane.showMessageDialog(null,"أدخل البيانات...!");
            }
    }//GEN-LAST:event_jbdeletecatActionPerformed

    private void jcat_idActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jcat_idActionPerformed
         for (Map.Entry<Integer, String> e : ctmap.entrySet()) {
            if (jcat_id.getSelectedItem().toString().equals(e.getValue())) {
                cat_id = e.getKey();
            }
        }
          
    }//GEN-LAST:event_jcat_idActionPerformed

    private void jqtyKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jqtyKeyReleased
       if (jqty.getText().equals("") || jqty.getText().equals("0")) {
            jtotal.setText("");
        } else {
            getTotal();
        }
    }//GEN-LAST:event_jqtyKeyReleased

    private void jpriceKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jpriceKeyTyped
       char inumber = evt.getKeyChar();
        if (!(inumber == KeyEvent.VK_PERIOD) && !(Character.isDigit(inumber))
            || (inumber == KeyEvent.VK_BACK_SPACE)
            || (inumber == KeyEvent.VK_DELETE)) {
            evt.consume();
        }
    }//GEN-LAST:event_jpriceKeyTyped

    private void jbarcodeKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jbarcodeKeyTyped
        char inumber = evt.getKeyChar();
        if (!(Character.isDigit(inumber))
            || (inumber == KeyEvent.VK_BACK_SPACE)
            || (inumber == KeyEvent.VK_DELETE)) {
            evt.consume();
        }
    }//GEN-LAST:event_jbarcodeKeyTyped

    private void jtxtsearchKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jtxtsearchKeyReleased
        if(jtxtsearch.getText().trim().isEmpty()==true||jtxtsearch.getText().equals("")){
            jpicsearch.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Pictures/icons8_search_32px.png")));
            showTableStock();
        }else{
            showTableStockSerch();jpicsearch.setIcon(null);
        }
        
    }//GEN-LAST:event_jtxtsearchKeyReleased


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel jLabel1;
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
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTabbedPane jTabbedPane1;
    private javax.swing.JTextField jbarcode;
    private javax.swing.JButton jbclear;
    private javax.swing.JButton jbclearcat;
    private javax.swing.JButton jbdelete;
    private javax.swing.JButton jbdeletecat;
    private javax.swing.JButton jbsave;
    private javax.swing.JButton jbsavecat;
    private javax.swing.JButton jbupdate;
    private javax.swing.JButton jbupdatecat;
    private javax.swing.JComboBox<String> jcat_id;
    private javax.swing.JTextField jcat_pid;
    private javax.swing.JTextField jcatname;
    private javax.swing.JTextField jcatname1;
    private javax.swing.JButton jimg;
    private javax.swing.JTextField jname;
    private javax.swing.JTextField jp_id;
    private javax.swing.JPanel jpanelfooter;
    private javax.swing.JPanel jpanelheader;
    private javax.swing.JTextField jpath;
    private javax.swing.JPanel jpcontent;
    private javax.swing.JLabel jpicsearch;
    private javax.swing.JLabel jpicture;
    private javax.swing.JTextField jprice;
    private javax.swing.JTextField jqty;
    private javax.swing.JPanel jtabcategore;
    private javax.swing.JTable jtable;
    private javax.swing.JTable jtablecat;
    private javax.swing.JPanel jtabstock;
    private javax.swing.JTextField jtotal;
    private javax.swing.JTextField jtxtsearch;
    // End of variables declaration//GEN-END:variables
}
