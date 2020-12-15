package com.younes;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


import com.younes.connection.JavaConnected;
import com.younes.util.TexttableCenter;
import java.awt.Color;
import java.awt.Component;
import java.awt.ComponentOrientation;
import java.awt.event.KeyEvent;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Vector;
import javax.swing.BorderFactory;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.swing.plaf.basic.BasicInternalFrameUI;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
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
public class InfoProductF extends javax.swing.JInternalFrame {

    /**
     * Creates new form HomeF
     */
     ResultSet rst,rst1=null;PreparedStatement pst,pst1=null;
      Connection conn=null; Calendar timer=Calendar.getInstance();
      SimpleDateFormat formt=new SimpleDateFormat("yyyy-MM-dd");
       Date todayt=null;
    Date dtexpre=null;
   private Map<Integer, String> prmap = new HashMap();
    private int pr_id = 0; 
   private JFrame frame;
  TexttableCenter tblcenter=new TexttableCenter();

    public InfoProductF() {
        initComponents();
        conn=JavaConnected.connectionDB();
        applyComponentOrientation(ComponentOrientation.RIGHT_TO_LEFT);
        this.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));
        BasicInternalFrameUI bi=(BasicInternalFrameUI)this.getUI();
        bi.setNorthPane(null);
       SwingUtilities.invokeLater(new Runnable(){
            @Override
            public void run() {
               jalqty.requestFocus();
            } 
        });
       showTableInfo();
       jp_id.setModel(new DefaultComboBoxModel(fillcomboboxProducts()));
        GetDateAlertExeperation();
        
        jScrollPane1.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
        jScrollPane1.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
    }
  
    private void showTableInfo(){
       DefaultTableModel table=new DefaultTableModel();
        table.addColumn("الرقم");table.addColumn("رقم المنتج");
        table.addColumn("الإسم");table.addColumn("تاريخ الصنع");
        table.addColumn("تاريخ النهاية");table.addColumn("تنبيه الكمية");
        String sql="SELECT * FROM `info_products`"
   + " INNER JOIN `stock` ON info_products.p_id=stock.p_id "
   + "ORDER BY dateexp ASC";
        try {
            pst=conn.prepareStatement(sql);
            rst=pst.executeQuery();
            while(rst.next()){
                table.addRow(new Object[]{rst.getString(1),rst.getString(2),
                   rst.getString(9),rst.getString(3), rst.getString(4),rst.getString(5)});
            }
              jtableinfo.setModel(table);
            //  jtableinfo.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
              //jtableinfo.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
             for(int i=0;i<6;i++){
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
    private void ClearFields(){
        je_id.setText("");jp_id.setSelectedIndex(0);
        jalqty.setText("");jdfab.setDate(null);jdexp.setDate(null);
        pr_id=0;
    showTableInfo(); fillcomboboxProducts();
    }
    private void GetDateAlertExeperation(){
      
try{
        Date dt0=new Date(); timer.setTime(dt0);
        String dtoday=formt.format(timer.getTime());   

          String sql="select * from info_products "; //ORDER BY dateexp ASC
          pst=conn.prepareStatement(sql);
          rst=pst.executeQuery();
         while(rst.next()){
              String dtexp=rst.getString("dateexp");
              todayt=formt.parse(dtoday);
              dtexpre=formt.parse(dtexp);
            // System.out.println("exp:"+dtexp);
            boolean ts =dtexpre.equals(todayt)||dtexpre.after(todayt);
            
        jtableinfo.setDefaultRenderer(Object.class, new DefaultTableCellRenderer()
{ @Override
    public Component getTableCellRendererComponent(JTable table, Object value, 
            boolean isSelected, boolean hasFocus, int row, int column) {
        JLabel label=(JLabel)super.getTableCellRendererComponent(table,value, isSelected, hasFocus, row,column); 
        
         if(value instanceof Object){
            try {  
             String dq=(String)value;
            // dq=dtoday;
           //  System.out.println(dtexp);
           if(dq.equals(dtoday) ){ 
             label.setBackground(Color.red);
             label.setForeground(Color.GREEN);
            }else{
              label.setBackground(Color.WHITE);
              label.setForeground(Color.BLACK);  
            }
            if(isSelected){
                label.setBackground(new Color(0,120,218));
            } 
                
             } catch (Exception ex) {
               ex.printStackTrace();
            }
         }        
    System.out.println("exp:"+value);
        return label;
    }   
      } );   
     
         }
	  }catch(Exception ex){
		JOptionPane.showMessageDialog(null, ex.getMessage());  
	  }finally{
        try{
		pst.close();rst.close();
	  }catch(Exception ex){
		JOptionPane.showMessageDialog(null, ex.getMessage());  
	  }
    }   
}
    private Vector fillcomboboxProducts() {
        Vector v = new Vector();
        v.add("إختر المنتوج");
        try {
            String sql = "SELECT * FROM `stock`";
            pst = conn.prepareStatement(sql);
            rst = pst.executeQuery();
            while (rst.next()) {
                v.add(rst.getString("p_name"));
                prmap.put(rst.getInt("p_id"), rst.getString("p_name"));
             }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, ex);
        } finally {
            try {
                rst.close(); pst.close();
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(null, ex);
            }
        }
        return v;
    }
    private void verifyField(){
         JOptionPane.showMessageDialog(null,"نجاح اخال المعلومات");
         JOptionPane.showMessageDialog(null,"خطأ إدخال البينات"); 
         JOptionPane.showMessageDialog(null,"أدخل البيانات...!");   
         JOptionPane.showMessageDialog(null,"نجاح التحديث");
        
        
    }
    private void showFullInfo(){
         int number=jtableinfo.getSelectedRow();
        if(number!=-1){
   
        String p_id=jtableinfo.getValueAt(number, 1).toString();
        String dtfab=jtableinfo.getValueAt(number, 3).toString();
        String dtexp=jtableinfo.getValueAt(number, 4).toString();
        SimpleDateFormat time=new SimpleDateFormat("yyyy-MM-dd");

        je_id.setText(jtableinfo.getValueAt(number, 0).toString());
        try{
            String sql="select p_name from stock where p_id=?";
            pst=conn.prepareStatement(sql);
            pst.setString(1, p_id);
            rst=pst.executeQuery();
            // jp_id.removeAllItems();
            while(rst.next()){
                String name=rst.getString("p_name");
                jp_id.setSelectedItem(name);
            }
        }catch(Exception ex){

        }
        try {
            jdfab.setDate(time.parse(dtfab));
            jdexp.setDate(time.parse(dtexp));
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        jalqty.setText(jtableinfo.getValueAt(number, 5).toString());
      }else{
             JOptionPane.showMessageDialog(null,"إختر العمود المناسب");
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
        jScrollPane1 = new javax.swing.JScrollPane();
        jtableinfo = new javax.swing.JTable();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        je_id = new javax.swing.JTextField();
        jbclear = new javax.swing.JButton();
        jbupdate = new javax.swing.JButton();
        jbsave = new javax.swing.JButton();
        jbdelete = new javax.swing.JButton();
        jLabel5 = new javax.swing.JLabel();
        jalqty = new javax.swing.JTextField();
        jLabel6 = new javax.swing.JLabel();
        jdfab = new com.toedter.calendar.JDateChooser();
        jdexp = new com.toedter.calendar.JDateChooser();
        jp_id = new javax.swing.JComboBox<>();
        dateexp = new javax.swing.JButton();

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
        jLabel4.setText("إدارة المنتجات");
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

        jScrollPane1.setHorizontalScrollBarPolicy(javax.swing.ScrollPaneConstants.HORIZONTAL_SCROLLBAR_ALWAYS);
        jScrollPane1.setVerticalScrollBarPolicy(javax.swing.ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);

        jtableinfo.setBackground(new java.awt.Color(255, 255, 255));
        jtableinfo.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        jtableinfo.setFont(new java.awt.Font("Dialog", 1, 12)); // NOI18N
        jtableinfo.setForeground(new java.awt.Color(0, 0, 0));
        jtableinfo.setModel(new javax.swing.table.DefaultTableModel(
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
        jtableinfo.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jtableinfo.setPreferredSize(new java.awt.Dimension(400, 400));
        jtableinfo.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jtableinfoMouseClicked(evt);
            }
        });
        jtableinfo.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jtableinfoKeyPressed(evt);
            }
            public void keyReleased(java.awt.event.KeyEvent evt) {
                jtableinfoKeyReleased(evt);
            }
        });
        jScrollPane1.setViewportView(jtableinfo);

        jPanel1.add(jScrollPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(9, 14, 460, 375));

        jLabel1.setBackground(new java.awt.Color(204, 204, 204));
        jLabel1.setFont(new java.awt.Font("Dialog", 1, 18)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(0, 0, 0));
        jLabel1.setText("ت/الصنع:");
        jLabel1.setOpaque(true);
        jPanel1.add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(650, 130, 60, 30));

        jLabel2.setBackground(new java.awt.Color(204, 204, 204));
        jLabel2.setFont(new java.awt.Font("Dialog", 1, 18)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(0, 0, 0));
        jLabel2.setText("رقم:");
        jLabel2.setOpaque(true);
        jPanel1.add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(650, 50, 30, 30));

        jLabel3.setBackground(new java.awt.Color(204, 204, 204));
        jLabel3.setFont(new java.awt.Font("Dialog", 1, 18)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(0, 0, 0));
        jLabel3.setText("رقم م:");
        jLabel3.setOpaque(true);
        jPanel1.add(jLabel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(650, 90, 40, 30));

        je_id.setEditable(false);
        je_id.setBackground(new java.awt.Color(204, 204, 204));
        je_id.setFont(new java.awt.Font("Dialog", 0, 18)); // NOI18N
        je_id.setForeground(new java.awt.Color(0, 0, 0));
        je_id.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 1, 0, new java.awt.Color(0, 0, 0)));
        je_id.setCaretColor(new java.awt.Color(255, 255, 255));
        jPanel1.add(je_id, new org.netbeans.lib.awtextra.AbsoluteConstraints(510, 45, 140, 30));

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
        jPanel1.add(jbclear, new org.netbeans.lib.awtextra.AbsoluteConstraints(490, 350, -1, 40));

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
        jPanel1.add(jbupdate, new org.netbeans.lib.awtextra.AbsoluteConstraints(490, 290, -1, 40));

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
        jPanel1.add(jbsave, new org.netbeans.lib.awtextra.AbsoluteConstraints(630, 290, -1, 40));

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
        jPanel1.add(jbdelete, new org.netbeans.lib.awtextra.AbsoluteConstraints(630, 350, -1, 40));

        jLabel5.setBackground(new java.awt.Color(204, 204, 204));
        jLabel5.setFont(new java.awt.Font("Dialog", 1, 18)); // NOI18N
        jLabel5.setForeground(new java.awt.Color(0, 0, 0));
        jLabel5.setText("ت/ن/الصنع:");
        jLabel5.setOpaque(true);
        jPanel1.add(jLabel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(650, 180, 80, 30));

        jalqty.setBackground(new java.awt.Color(204, 204, 204));
        jalqty.setFont(new java.awt.Font("Dialog", 0, 18)); // NOI18N
        jalqty.setForeground(new java.awt.Color(0, 0, 0));
        jalqty.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 1, 0, new java.awt.Color(0, 0, 0)));
        jalqty.setCaretColor(new java.awt.Color(0, 0, 0));
        jalqty.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                jalqtyKeyTyped(evt);
            }
        });
        jPanel1.add(jalqty, new org.netbeans.lib.awtextra.AbsoluteConstraints(510, 220, 140, 30));

        jLabel6.setBackground(new java.awt.Color(204, 204, 204));
        jLabel6.setFont(new java.awt.Font("Dialog", 1, 18)); // NOI18N
        jLabel6.setForeground(new java.awt.Color(0, 0, 0));
        jLabel6.setText("تنبيه كمية:");
        jLabel6.setOpaque(true);
        jPanel1.add(jLabel6, new org.netbeans.lib.awtextra.AbsoluteConstraints(650, 220, 70, 30));

        jdfab.setBackground(new java.awt.Color(255, 255, 255));
        jdfab.setForeground(new java.awt.Color(0, 0, 0));
        jdfab.setDateFormatString("yyyy-MM-dd");
        jdfab.setFont(new java.awt.Font("Dialog", 0, 14)); // NOI18N
        jPanel1.add(jdfab, new org.netbeans.lib.awtextra.AbsoluteConstraints(510, 130, 140, 30));

        jdexp.setBackground(new java.awt.Color(255, 255, 255));
        jdexp.setForeground(new java.awt.Color(0, 0, 0));
        jdexp.setDateFormatString("yyyy-MM-dd");
        jdexp.setFont(new java.awt.Font("Dialog", 0, 14)); // NOI18N
        jPanel1.add(jdexp, new org.netbeans.lib.awtextra.AbsoluteConstraints(510, 180, 140, 30));

        jp_id.setFont(new java.awt.Font("Dialog", 0, 14)); // NOI18N
        jp_id.setForeground(new java.awt.Color(0, 0, 0));
        jp_id.setBorder(new javax.swing.border.MatteBorder(null));
        jp_id.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jp_id.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jp_idActionPerformed(evt);
            }
        });
        jPanel1.add(jp_id, new org.netbeans.lib.awtextra.AbsoluteConstraints(510, 90, 140, -1));

        dateexp.setFont(new java.awt.Font("Dialog", 1, 18)); // NOI18N
        dateexp.setForeground(new java.awt.Color(0, 0, 0));
        dateexp.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Pictures/icons8_search_property_32px_1.png"))); // NOI18N
        dateexp.setText("بحث نهاية الصنع");
        dateexp.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        dateexp.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                dateexpActionPerformed(evt);
            }
        });
        jPanel1.add(dateexp, new org.netbeans.lib.awtextra.AbsoluteConstraints(530, 410, 170, 40));

        jpanelcontent.add(jPanel1, java.awt.BorderLayout.CENTER);

        getContentPane().add(jpanelcontent, java.awt.BorderLayout.CENTER);

        setBounds(0, 0, 750, 600);
    }// </editor-fold>//GEN-END:initComponents

    private void jbsaveActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbsaveActionPerformed
     String dtfab=((JTextField)jdfab.getDateEditor().getUiComponent()).getText();
     String dteexp=((JTextField)jdexp.getDateEditor().getUiComponent()).getText();
        if(dtfab.equals(null)||dteexp.equals(null)){
           JOptionPane.showMessageDialog(null, "أدخل البيانات!", "خطأ", JOptionPane.WARNING_MESSAGE);
        }else{
            String sql="insert into info_products (p_id,"
            + "datefab,dateexp,alqty)values(?,?,?,?)";
            try {
                pst=conn.prepareStatement(sql);
                pst.setString(1,String.valueOf(pr_id));
                pst.setString(2,dtfab);
                pst.setString(3,dteexp);
                pst.setString(4,jalqty.getText());
                pst.executeUpdate();
                System.out.println("Save success!");
            } catch (Exception ex) {
                ex.printStackTrace();
            }
            showTableInfo();ClearFields();
        }
    }//GEN-LAST:event_jbsaveActionPerformed

    private void jbdeleteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbdeleteActionPerformed
       String idcus=je_id.getText();
       if(!idcus.trim().isEmpty()){
       int msg=JOptionPane.showConfirmDialog(null, "هل تريد حذف المعلومات..!","حذف",JOptionPane.YES_NO_OPTION);
      if(msg==0){
         String sql="delete from info_products where e_id=?";
            try {
                pst=conn.prepareStatement(sql);
                pst.setString(1,je_id.getText());
                pst.executeUpdate();
                System.out.println("delete success!");
            } catch (Exception ex) {
                ex.printStackTrace();
            }
            showTableInfo();ClearFields();
            
        } }else{
         JOptionPane.showMessageDialog(null,"أدخل البيانات...!");   
       }
    }//GEN-LAST:event_jbdeleteActionPerformed

    private void jbupdateActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbupdateActionPerformed
    
       String id=je_id.getText();
        if(!id.trim().isEmpty()){
      String sql="update info_products set e_id=?,p_id=?,"
        + "datefab=?,dateexp=?,alqty=? where e_id=?";
        String dtfab=((JTextField)jdfab.getDateEditor().getUiComponent()).getText();
        String dteexp=((JTextField)jdexp.getDateEditor().getUiComponent()).getText();
        if(dtfab.equals(null)||dteexp.equals(null)){
           JOptionPane.showMessageDialog(null, "أدخل البيانات!", "خطأ", JOptionPane.WARNING_MESSAGE);
        }else{
            try {
                pst=conn.prepareStatement(sql);
                pst.setString(1,je_id.getText());
                pst.setString(2,String.valueOf(pr_id));
                pst.setString(3,dtfab);
                pst.setString(4,dteexp);
                pst.setString(5,jalqty.getText());
                pst.setString(6,je_id.getText());
                pst.executeUpdate();
                System.out.println("Update success!");
            } catch (Exception ex) {
                ex.printStackTrace();
            }
            showTableInfo();ClearFields(); 

        }      
        
        }else{
         JOptionPane.showMessageDialog(null,"أدخل البيانات...!");   
       }
        
    }//GEN-LAST:event_jbupdateActionPerformed

    private void jbclearActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbclearActionPerformed
       ClearFields();
    }//GEN-LAST:event_jbclearActionPerformed

    private void jtableinfoMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jtableinfoMouseClicked
          showFullInfo();
    }//GEN-LAST:event_jtableinfoMouseClicked

    private void jtableinfoKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jtableinfoKeyPressed
       if(evt.getKeyCode()==KeyEvent.VK_UP||evt.getKeyCode()==KeyEvent.VK_DOWN){
         showFullInfo();
       }
    }//GEN-LAST:event_jtableinfoKeyPressed

    private void jtableinfoKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jtableinfoKeyReleased
        if(evt.getKeyCode()==KeyEvent.VK_UP||evt.getKeyCode()==KeyEvent.VK_DOWN){
          showFullInfo();
        }
    }//GEN-LAST:event_jtableinfoKeyReleased

    private void jp_idActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jp_idActionPerformed
        for (Map.Entry<Integer, String> e : prmap.entrySet()) {
            if (jp_id.getSelectedItem().toString().equals(e.getValue())) {
                pr_id = e.getKey();
            }
        }
    }//GEN-LAST:event_jp_idActionPerformed

    private void dateexpActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_dateexpActionPerformed
        try{
            Date dt0=new Date(); timer.setTime(dt0);
            String dtoday=formt.format(timer.getTime());

            String sql="select dateexp from info_products Order BY dateexp ASC";
            pst=conn.prepareStatement(sql);
            rst=pst.executeQuery();
            int row=jtableinfo.getRowCount();
            while(rst.next()){
                String dtexp=rst.getString("dateexp");
                todayt=formt.parse(dtoday);
                dtexpre=formt.parse(dtexp);
                System.out.println("exp:"+dtexp);
                System.out.println("row:"+row);
                if(dtexpre.equals(todayt)||dtexpre.before(todayt)){
                    HashMap ar=new HashMap();
                    ar.put("tdexp",dtexp);
                    ar.put("ttoday",dtoday);
                    String Dir=System.getProperty("user.dir");
                    JasperDesign jd=JRXmlLoader.load(Dir+"/ExpReport1.jrxml");
                    //   JasperDesign jd=JRXmlLoader.load("ExperationProducts.jrxml");
                    JasperReport jr=JasperCompileManager.compileReport(jd);
                    JasperPrint jp=JasperFillManager.fillReport(jr, ar, conn);
                    JasperViewer.viewReport(jp,false);

                    break;
                }

            }
        }catch(Exception ex){
            JOptionPane.showMessageDialog(null, ex.getMessage());
        }
    }//GEN-LAST:event_dateexpActionPerformed

    private void jalqtyKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jalqtyKeyTyped
        char inumber=evt.getKeyChar();
        if(!(Character.isDigit(inumber))
            ||(inumber==KeyEvent.VK_BACK_SPACE)
            ||(inumber==KeyEvent.VK_DELETE)){
            evt.consume();
        }
    }//GEN-LAST:event_jalqtyKeyTyped


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton dateexp;
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
    private javax.swing.JTextField jalqty;
    private javax.swing.JButton jbclear;
    private javax.swing.JButton jbdelete;
    private javax.swing.JButton jbsave;
    private javax.swing.JButton jbupdate;
    private com.toedter.calendar.JDateChooser jdexp;
    private com.toedter.calendar.JDateChooser jdfab;
    private javax.swing.JTextField je_id;
    private javax.swing.JComboBox<String> jp_id;
    private javax.swing.JPanel jpanelcontent;
    private javax.swing.JPanel jpanelfooter;
    private javax.swing.JPanel jpanelheader;
    private javax.swing.JTable jtableinfo;
    // End of variables declaration//GEN-END:variables
}
