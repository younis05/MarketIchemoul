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
import java.io.File;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Calendar;
import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.swing.plaf.basic.BasicInternalFrameUI;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumn;

/**
 *
 * @author BOUKHTACHE
 */
public class InvoiceF extends javax.swing.JInternalFrame {

    /**
     * Creates new form HomeF
     */
      ResultSet rst=null;PreparedStatement pst=null;
    Connection conn=null;Calendar timer=Calendar.getInstance();
   private JFrame frame;
   Customer cus=null;
  TexttableCenter tblcenter=new TexttableCenter();

  
    public InvoiceF() {
        initComponents();
        conn=JavaConnected.connectionDB();
        applyComponentOrientation(ComponentOrientation.RIGHT_TO_LEFT);
        this.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));
        BasicInternalFrameUI bi=(BasicInternalFrameUI)this.getUI();
        bi.setNorthPane(null);
        showInvoices();
       SwingUtilities.invokeLater(new Runnable(){
            @Override
            public void run() {
               jtxtsearch.requestFocus();
            } 
        });
       
        
    }

private void showInvoices(){
        DefaultTableModel table=new DefaultTableModel();
        table.addColumn("الرقم");table.addColumn("رقم الفاتورة");
        table.addColumn("المبلغ"); table.addColumn("الدفع");
        table.addColumn("الباقي");table.addColumn("الرابط");
        table.addColumn("التاريخ");
        String sql="SELECT * FROM `invoices`";
        try {
            pst=conn.prepareStatement(sql);
            rst=pst.executeQuery();
            while(rst.next()){
                table.addRow(new Object[]{rst.getString(1),rst.getString(2),
                    rst.getString(3), rst.getString(4),rst.getString(5),
                    rst.getString(6),rst.getString(7)});
            }
            jtableinv.setModel(table);
            jtableinv.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
            jtableinv.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
            for(int i=0;i<jtableinv.getColumnCount();i++){
                 jtableinv.getColumnModel().getColumn(i).setCellRenderer(tblcenter);
                
             }
       /*  for(int culomn=0;culomn<jtableinv.getColumnCount();culomn++){
             TableColumn tableColumn=jtableinv.getColumnModel().getColumn(culomn);
             int preferedWidth=tableColumn.getMinWidth();
             int maxWidth=tableColumn.getMaxWidth();
             for(int row=0;row<jtableinv.getColumnCount();row++){
                 TableCellRenderer cellRenderer=jtableinv.getCellRenderer(row, culomn);
                 Component c=jtableinv.prepareRenderer(cellRenderer, row, culomn);
                 int width=c.getPreferredSize().width+jtableinv.getIntercellSpacing().width;
                 preferedWidth=Math.max(preferedWidth, width);
                 
                 if(preferedWidth>=maxWidth){
                     preferedWidth=maxWidth;
                     break;
                 }
             }
             tableColumn.setPreferredWidth(preferedWidth);
         }  */
            
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
private void searchInvoices(){
     String txt=jtxtsearch.getText();
        DefaultTableModel table=new DefaultTableModel();
        table.addColumn("الرقم");table.addColumn("رقم الفاتورة");
        table.addColumn("المبلغ"); table.addColumn("الدفع");
        table.addColumn("الباقي");table.addColumn("الرابط");
        table.addColumn("التاريخ");
        String sql="SELECT * FROM `invoices` where concat(inv_id, n_inv) LIKE '%"+txt+"%'"; //||n_inv||datetime
        try {
            pst=conn.prepareStatement(sql);
            rst=pst.executeQuery();
            while(rst.next()){
                table.addRow(new Object[]{rst.getString(1),rst.getString(2),
                    rst.getString(3), rst.getString(4),rst.getString(5),
                    rst.getString(6),rst.getString(7)});
            }
            jtableinv.setModel(table);
            jtableinv.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
            jtableinv.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
            for(int i=0;i<7;i++){
                 jtableinv.getColumnModel().getColumn(i).setCellRenderer(tblcenter);
             }
        } catch (Exception ex) {
           ex.printStackTrace();
        }  
       
    } 
private void searchInvoicesDates(){
    String dt1=((JTextField)jdate1.getDateEditor().getUiComponent()).getText();
    String dt2=((JTextField)jdate2.getDateEditor().getUiComponent()).getText();
    DefaultTableModel table=new DefaultTableModel();
        table.addColumn("الرقم");table.addColumn("رقم الفاتورة");
        table.addColumn("المبلغ"); table.addColumn("الدفع");
        table.addColumn("الباقي");table.addColumn("الرابط");
        table.addColumn("التاريخ");
        String sql="SELECT * FROM `invoices` WHERE `datetime` BETWEEN'"+dt1+"'and'"+dt2+"'";
        try {
            pst=conn.prepareStatement(sql);
            rst=pst.executeQuery();
            while(rst.next()){
                table.addRow(new Object[]{rst.getString(1),rst.getString(2),
                    rst.getString(3), rst.getString(4),rst.getString(5),
                    rst.getString(6),rst.getString(7)});
            }
            jtableinv.setModel(table);
            jtableinv.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
            jtableinv.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
            for(int i=0;i<7;i++){
                 jtableinv.getColumnModel().getColumn(i).setCellRenderer(tblcenter);
             }
        } catch (Exception ex) {
           ex.printStackTrace();
        } 
    
    
} 
private void DeleteFile(){
           try{
            int row=jtableinv.getSelectedRow();
            String path=(jtableinv.getModel().getValueAt(row, 6)).toString();
          File fol=new File(path);//System.getProperty("user.home")+
           if(fol.exists()){
               if(fol.delete()){
                  JOptionPane.showMessageDialog(null, "حذف الفاتورة");
               }else{
                   JOptionPane.showMessageDialog(null, "خطأ في الحذف!");
               }
           }
          
         }catch(Exception ex){
               JOptionPane.showMessageDialog(null, ex.getMessage());
           }
     }
private void DeleteAllFile(){
           try{ 
             File fol=new File(System.getProperty("user.home")+"\\FactSells\\FactTxt");
               if(fol.isDirectory()==false){
                    JOptionPane.showMessageDialog(null, "لاتوجد فواتير!");
                   return; 
               }
            File[] listfiles=fol.listFiles();
            for(File f:listfiles){
                System.out.println("File !:"+f.getName());
                f.delete();
            }
             JOptionPane.showMessageDialog(null, "حذفت كل الفواتير!"); 
           }catch(Exception ex){
              ex.printStackTrace();
           }
     }
private void searchInvoicesBarCode(){
     String txt0=jtxtsearch1.getText().trim();
     String txt1=txt0.substring(1,txt0.length());
     String txt=txt1.substring(0, txt1.length()-1);
     jtxtsearch.setText(txt);
        DefaultTableModel table=new DefaultTableModel();
        table.addColumn("الرقم");table.addColumn("رقم الفاتورة");
        table.addColumn("المبلغ"); table.addColumn("الدفع");
        table.addColumn("الباقي");table.addColumn("الرابط");
        table.addColumn("التاريخ");
        String sql="SELECT * FROM `invoices` WHERE  `n_inv`=?"; //||n_inv||datetime
        try {
            pst=conn.prepareStatement(sql);
            pst.setString(1, txt);
            rst=pst.executeQuery();
            while(rst.next()){
                table.addRow(new Object[]{rst.getString(1),rst.getString(2),
                    rst.getString(3), rst.getString(4),rst.getString(5),
                    rst.getString(6),rst.getString(7)});
            }
            jtableinv.setModel(table);
            jtableinv.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
            
            jtableinv.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
            for(int i=0;i<7;i++){
                 jtableinv.getColumnModel().getColumn(i).setCellRenderer(tblcenter);
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
        jScrollPane1 = new javax.swing.JScrollPane();
        jtableinv = new javax.swing.JTable();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jid = new javax.swing.JTextField();
        jtxtsearch = new javax.swing.JTextField();
        jdeleteall = new javax.swing.JButton();
        jsearch = new javax.swing.JButton();
        jbdelete = new javax.swing.JButton();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jdate1 = new com.toedter.calendar.JDateChooser();
        jdate2 = new com.toedter.calendar.JDateChooser();
        jpic = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jtxtsearch1 = new javax.swing.JTextField();
        jpic1 = new javax.swing.JLabel();

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
        jLabel4.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Pictures/icons8_file_invoice_dollar_32px.png"))); // NOI18N
        jLabel4.setText("الفواتير");
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

        jtableinv.setBackground(new java.awt.Color(255, 255, 255));
        jtableinv.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        jtableinv.setFont(new java.awt.Font("Dialog", 1, 12)); // NOI18N
        jtableinv.setForeground(new java.awt.Color(0, 0, 0));
        jtableinv.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4", "Title 5", "Title 6", "Title 7"
            }
        ));
        jtableinv.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jtableinv.setPreferredSize(new java.awt.Dimension(800, 600));
        jtableinv.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jtableinvMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(jtableinv);

        jPanel1.add(jScrollPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 14, 480, 375));

        jLabel2.setBackground(new java.awt.Color(204, 204, 204));
        jLabel2.setFont(new java.awt.Font("Dialog", 1, 18)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(0, 0, 0));
        jLabel2.setText("التاريخ2:");
        jLabel2.setOpaque(true);
        jPanel1.add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(680, 180, 60, 30));

        jLabel3.setBackground(new java.awt.Color(204, 204, 204));
        jLabel3.setFont(new java.awt.Font("Dialog", 1, 18)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(0, 0, 0));
        jLabel3.setText("البحث:");
        jLabel3.setOpaque(true);
        jPanel1.add(jLabel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(680, 80, 50, 30));

        jid.setEditable(false);
        jid.setBackground(new java.awt.Color(204, 204, 204));
        jid.setFont(new java.awt.Font("Dialog", 0, 18)); // NOI18N
        jid.setForeground(new java.awt.Color(0, 0, 0));
        jid.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 1, 0, new java.awt.Color(0, 0, 0)));
        jid.setCaretColor(new java.awt.Color(255, 255, 255));
        jPanel1.add(jid, new org.netbeans.lib.awtextra.AbsoluteConstraints(540, 40, 140, 30));

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
        jPanel1.add(jtxtsearch, new org.netbeans.lib.awtextra.AbsoluteConstraints(540, 80, 140, 30));

        jdeleteall.setFont(new java.awt.Font("Dialog", 1, 18)); // NOI18N
        jdeleteall.setForeground(new java.awt.Color(0, 0, 0));
        jdeleteall.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Pictures/trash.png"))); // NOI18N
        jdeleteall.setText("حذف الكل");
        jdeleteall.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jdeleteall.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jdeleteallActionPerformed(evt);
            }
        });
        jPanel1.add(jdeleteall, new org.netbeans.lib.awtextra.AbsoluteConstraints(500, 350, -1, 40));

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
        jPanel1.add(jsearch, new org.netbeans.lib.awtextra.AbsoluteConstraints(570, 240, -1, 40));

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
        jPanel1.add(jbdelete, new org.netbeans.lib.awtextra.AbsoluteConstraints(650, 350, -1, 40));

        jLabel5.setBackground(new java.awt.Color(204, 204, 204));
        jLabel5.setFont(new java.awt.Font("Dialog", 1, 18)); // NOI18N
        jLabel5.setForeground(new java.awt.Color(0, 0, 0));
        jLabel5.setText("رقم:");
        jLabel5.setOpaque(true);
        jPanel1.add(jLabel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(680, 40, 50, 30));

        jLabel6.setBackground(new java.awt.Color(204, 204, 204));
        jLabel6.setFont(new java.awt.Font("Dialog", 1, 18)); // NOI18N
        jLabel6.setForeground(new java.awt.Color(0, 0, 0));
        jLabel6.setText("التاريخ1:");
        jLabel6.setOpaque(true);
        jPanel1.add(jLabel6, new org.netbeans.lib.awtextra.AbsoluteConstraints(680, 130, 60, 30));

        jdate1.setForeground(new java.awt.Color(0, 0, 0));
        jdate1.setDateFormatString("yyyy-MM-dd");
        jdate1.setFont(new java.awt.Font("Dialog", 0, 14)); // NOI18N
        jPanel1.add(jdate1, new org.netbeans.lib.awtextra.AbsoluteConstraints(520, 130, 150, 30));

        jdate2.setForeground(new java.awt.Color(0, 0, 0));
        jdate2.setDateFormatString("yyyy-MM-dd");
        jdate2.setFont(new java.awt.Font("Dialog", 0, 14)); // NOI18N
        jPanel1.add(jdate2, new org.netbeans.lib.awtextra.AbsoluteConstraints(520, 180, 150, 30));

        jpic.setBackground(new java.awt.Color(204, 204, 204));
        jpic.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jpic.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Pictures/icons8_search_property_32px_1.png"))); // NOI18N
        jpic.setOpaque(true);
        jPanel1.add(jpic, new org.netbeans.lib.awtextra.AbsoluteConstraints(500, 80, 40, 30));

        jLabel7.setBackground(new java.awt.Color(204, 204, 204));
        jLabel7.setFont(new java.awt.Font("Dialog", 1, 18)); // NOI18N
        jLabel7.setForeground(new java.awt.Color(0, 0, 0));
        jLabel7.setText("رقم باركود الفاتورة:");
        jLabel7.setOpaque(true);
        jPanel1.add(jLabel7, new org.netbeans.lib.awtextra.AbsoluteConstraints(380, 410, 130, 30));

        jtxtsearch1.setBackground(new java.awt.Color(204, 204, 204));
        jtxtsearch1.setFont(new java.awt.Font("Dialog", 0, 18)); // NOI18N
        jtxtsearch1.setForeground(new java.awt.Color(0, 0, 0));
        jtxtsearch1.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 1, 0, new java.awt.Color(0, 0, 0)));
        jtxtsearch1.setCaretColor(new java.awt.Color(0, 0, 0));
        jtxtsearch1.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                jtxtsearch1KeyReleased(evt);
            }
        });
        jPanel1.add(jtxtsearch1, new org.netbeans.lib.awtextra.AbsoluteConstraints(240, 410, 140, 30));

        jpic1.setBackground(new java.awt.Color(204, 204, 204));
        jpic1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jpic1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Pictures/icons8_search_property_32px_1.png"))); // NOI18N
        jpic1.setOpaque(true);
        jPanel1.add(jpic1, new org.netbeans.lib.awtextra.AbsoluteConstraints(200, 410, 40, 30));

        jpanelcontent.add(jPanel1, java.awt.BorderLayout.CENTER);

        getContentPane().add(jpanelcontent, java.awt.BorderLayout.CENTER);

        setBounds(0, 0, 750, 600);
    }// </editor-fold>//GEN-END:initComponents

    private void jsearchActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jsearchActionPerformed
       searchInvoicesDates();
       
    }//GEN-LAST:event_jsearchActionPerformed

    private void jbdeleteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbdeleteActionPerformed
       int msg=JOptionPane.showConfirmDialog(null, "هل تريد حذف الفاتورة!","حذف!",JOptionPane.WARNING_MESSAGE);
        if(msg==0){
            String inv_id=jid.getText();
            String sql="UPDATE `invoices` SET `path`=? WHERE `inv_id`=?";
            try {
                pst=conn.prepareStatement(sql);
                pst.setString(1,"");
                pst.setString(2,inv_id);
                pst.executeUpdate();
                DeleteFile();
                System.out.println("delete success!");
            } catch (Exception ex) {
                ex.printStackTrace();
            } showInvoices();
        }
    }//GEN-LAST:event_jbdeleteActionPerformed

    private void jdeleteallActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jdeleteallActionPerformed
       int msg=JOptionPane.showConfirmDialog(null, "هل تريد حذف الفاتورة!","حذف!",JOptionPane.WARNING_MESSAGE);
        if(msg==0){
            String sql="UPDATE `invoices` SET `path`=?";
            try {
                pst=conn.prepareStatement(sql);
                pst.setString(1,"");
                pst.executeUpdate();
                DeleteAllFile();
                System.out.println("delete success!");
            } catch (Exception ex) {
                ex.printStackTrace();
            } showInvoices();
        }
    }//GEN-LAST:event_jdeleteallActionPerformed

    private void jtableinvMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jtableinvMouseClicked
         int row=jtableinv.getSelectedRow();
        if(row!=-1){
        try{
            
            jid.setText((jtableinv.getModel().getValueAt(row, 0)).toString());
            String Table_Click=(jtableinv.getModel().getValueAt(row, 5)).toString();
            if(Table_Click.trim().isEmpty()==true||Table_Click.equals("")||
                    Table_Click.equals(null)){
                System.out.println("inv is null..!");
            }else if(Table_Click.trim().isEmpty()==false||!Table_Click.equals("")||
                    !Table_Click.equals(null)){
                Runtime.getRuntime().exec("rundll32 url.dll,FileProtocolHandler "+Table_Click);
            }
        }catch(Exception ex){
            ex.printStackTrace();
        }
         }else{
             JOptionPane.showMessageDialog(null,"إختر العمود المناسب");
        }
    }//GEN-LAST:event_jtableinvMouseClicked

    private void jtxtsearchKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jtxtsearchKeyReleased
        if(jtxtsearch.getText().trim().isEmpty()==true||jtxtsearch.getText().equals("")){
            jpic.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Pictures/icons8_search_property_32px_1.png")));
            showInvoices();
        }else{
             searchInvoices();
            jpic.setIcon(null);
        }
       
    }//GEN-LAST:event_jtxtsearchKeyReleased

    private void jtxtsearch1KeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jtxtsearch1KeyReleased
        if(jtxtsearch1.getText().trim().isEmpty()==true||jtxtsearch1.getText().equals("")){
            jpic1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Pictures/icons8_search_property_32px_1.png")));
            showInvoices();
        }else{
             searchInvoicesBarCode();
            jpic1.setIcon(null);
        }
    }//GEN-LAST:event_jtxtsearch1KeyReleased


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JButton jbdelete;
    private com.toedter.calendar.JDateChooser jdate1;
    private com.toedter.calendar.JDateChooser jdate2;
    private javax.swing.JButton jdeleteall;
    private javax.swing.JTextField jid;
    private javax.swing.JPanel jpanelcontent;
    private javax.swing.JPanel jpanelfooter;
    private javax.swing.JPanel jpanelheader;
    private javax.swing.JLabel jpic;
    private javax.swing.JLabel jpic1;
    private javax.swing.JButton jsearch;
    private javax.swing.JTable jtableinv;
    private javax.swing.JTextField jtxtsearch;
    private javax.swing.JTextField jtxtsearch1;
    // End of variables declaration//GEN-END:variables
}
