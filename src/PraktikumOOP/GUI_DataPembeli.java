/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package PraktikumOOP;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import java.util.ArrayList;
import java.util.List;

public class GUI_DataPembeli extends javax.swing.JFrame {

    
    public GUI_DataPembeli() {
        initComponents();
        tampil();
    }
    public Connection conn;
public void clear(){
            txt_namapembeli.setText("");
            txt_no.setText("");
            txt_Alamat.setText("");
            txt_total.setText("");
            txt_jumlah.setText("");
            txt_namabahan.setText("");
            Cmb_jenis.setSelectedIndex(0);
    }

public void koneksi() throws SQLException{
        try {
            conn = null;
            Class.forName("com.mysql.cj.jdbc.Driver");
            conn = DriverManager.getConnection("jdbc:mysql://localhost/project_2218027?user=root&password=");
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(GUI_DataPembeli.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException e) {
            Logger.getLogger(GUI_DataPembeli.class.getName()).log(Level.SEVERE, null, e);
        } catch (Exception es) {
            Logger.getLogger(GUI_DataPembeli.class.getName()).log(Level.SEVERE, null, es);
        }
    }
public void tampil() {
        DefaultTableModel tabelhead = new DefaultTableModel();
        tabelhead.addColumn("Namapembeli");
        tabelhead.addColumn("Alamat");
        tabelhead.addColumn("No Telepon");
        tabelhead.addColumn("JenisBahan");
        tabelhead.addColumn("NamaBahan");
        tabelhead.addColumn("JumlahPembelian");
        tabelhead.addColumn("Total Pembayaran");
        try {
            koneksi();
            String sql = "SELECT * FROM tb_datapembeli";
            Statement stat = conn.createStatement();
            ResultSet res = stat.executeQuery(sql);
            while (res.next()) {
                tabelhead.addRow(new Object[]{res.getString(2), res.getString(3), res.getString(4), res.getString(5), res.getString(6), res.getString(7),res.getString(8)});
            }
            Table_Hasil.setModel(tabelhead);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "BELUM TERKONEKSI");
        }
    }
    public void refresh() {
        new GUI_DataPembeli().setVisible(true);
        this.setVisible(false);
    }
    public void insert() {
        String Namapembeli = txt_namapembeli.getText();
        String Alamat = txt_Alamat.getText();
        int Notelepon = Integer.parseInt(txt_no.getText());
        
        String jenisbahan= "";
        if (Cmb_jenis.getSelectedIndex()==0){
            jenisbahan=("Perekat");
        }
        else if(Cmb_jenis.getSelectedIndex()==1){
           jenisbahan=("Instalasi");
        }
        else if(Cmb_jenis.getSelectedIndex()==2){
            jenisbahan=("Bangunan");
        }
        else if(Cmb_jenis.getSelectedIndex()==3){
            jenisbahan=("Atap");
        }
        else if(Cmb_jenis.getSelectedIndex()==4){
            jenisbahan=("Pelapis");
        }
        String Namabahan = txt_namabahan.getText();
        int Jumlah = Integer.parseInt(txt_jumlah.getText());
        int Total = Integer.parseInt (txt_total.getText());
        try {
            koneksi();
            Statement statement = conn.createStatement();
            statement.executeUpdate("INSERT INTO tb_datapembeli (Nama_Pembeli,Alamat,No_telepon,Jenis_bahan,Nama_bahan,Jumlah_pembelian,Total_pembelian)"
                    + "VALUES('" + Namapembeli + "','" + Alamat + "','" + Notelepon + "','" + jenisbahan + "','" + Namabahan + "','" + Jumlah + "','"+Total+"')");
            statement.close();
            JOptionPane.showMessageDialog(null, "Berhasil Memasukan Data Pembeli! \n") ;
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Terjadi Kesalahan Input!");
        }
        refresh();
    }
String Namapembeli1,Alamat1,Notelepon1,jenisbahan1,Namabahan1,Jumlah1,Total1,stok1;
public void update() {
       String Namapembeli = txt_namapembeli.getText();
       String Alamat = txt_Alamat.getText();
       int Notelepon = Integer.parseInt(txt_no.getText());
        
        String jenisbahan= "";
        if (Cmb_jenis.getSelectedIndex()==0){
            jenisbahan=("Perekat");
        }
        else if(Cmb_jenis.getSelectedIndex()==1){
           jenisbahan=("Instalasi");
        }
        else if(Cmb_jenis.getSelectedIndex()==2){
            jenisbahan=("Bangunan");
        }
        else if(Cmb_jenis.getSelectedIndex()==3){
            jenisbahan=("Atap");
        }
        else if(Cmb_jenis.getSelectedIndex()==4){
            jenisbahan=("Pelapis");
        }
        String Namabahan = txt_namabahan.getText();
        int Jumlah = Integer.parseInt(txt_jumlah.getText());
        int Total = Integer.parseInt (txt_total.getText());
        String NamaLama=Namapembeli1;
        try {
            Statement statement = conn.createStatement();
            statement.executeUpdate("UPDATE tb_datapembeli SET Nama_Pembeli='" + Namapembeli + "'," + "Alamat='" + Alamat + "',"
                    + "No_telepon='" + Notelepon + "'" + ",Jenis_bahan='" + jenisbahan + "',Nama_bahan='" + Namabahan + "',Jumlah_pembelian='"
                    + Jumlah +"',Total_pembelian='" + Total + "' WHERE Nama_pembeli = '" + NamaLama + "'");
            statement.close();
            conn.close();
            JOptionPane.showMessageDialog(null, "Update Data Pembelian Berhasil!");
        } catch (Exception e) {
            System.out.println("Error : " + e);
        }
        refresh();
    }
public void delete() {
        int ok = JOptionPane.showConfirmDialog(null, "Apakah Anda yakin akan menghapus data ?", "Konfirmasi", JOptionPane.YES_NO_OPTION);
        if (ok == 0) {
            try {
                String sql = "DELETE FROM tb_datapembeli WHERE Nama_pembeli='" + txt_namapembeli.getText() + "'";
                java.sql.PreparedStatement stmt = conn.prepareStatement(sql);
                stmt.executeUpdate();
                JOptionPane.showMessageDialog(null, "Data Berhasil di hapus");
                clear();
            } catch (Exception e) {
                JOptionPane.showMessageDialog(null, "Data gagal di hapus");
            }
        }
        refresh();
    }
public void cari() {
        try {
            try ( Statement statement = conn.createStatement()) {
                String sql = "SELECT * FROM tb_datapembeli WHERE `Nama_pembeli`  LIKE '%" + txt_cari.getText() + "%'";
                ResultSet rs = statement.executeQuery(sql); //menampilkan data dari sql query
                if (rs.next()) // .next() = scanner method
                {
                    txt_namapembeli.setText(rs.getString(2));
                    txt_Alamat.setText(rs.getString(3));
                    txt_no.setText(rs.getString(4));
                    String jenisbahan= rs.getString(5);
                    if (Cmb_jenis.getSelectedIndex()==0){
                    jenisbahan=("Perekat");
                }
                    else if(Cmb_jenis.getSelectedIndex()==1){
                    jenisbahan=("Instalasi");
                 }
                    else if(Cmb_jenis.getSelectedIndex()==2){
                    jenisbahan=("Bangunan");
                }
                    else if(Cmb_jenis.getSelectedIndex()==3){
                    jenisbahan=("Atap");
                    }
                    else if(Cmb_jenis.getSelectedIndex()==4){
                    jenisbahan=("Pelapis");
                    }
                    txt_namabahan.setText(rs.getString(6));
                    txt_jumlah.setText(rs.getString(7));
                    txt_total.setText(rs.getString(8));
                } else {
                    JOptionPane.showMessageDialog(null, "Data yang Anda cari tidak ada");
                }
            }
        } catch (Exception ex) {
            System.out.println("Error." + ex);
        }    }
        
        public void itempilih() {
        txt_namapembeli.setText(Namapembeli1);
        txt_Alamat.setText(Alamat1);
        txt_no.setText(Notelepon1);
        Cmb_jenis.setSelectedItem(jenisbahan1);
        txt_namabahan.setText(Namabahan1);
        txt_jumlah.setText(Jumlah1);
        txt_total.setText(Total1);
    }
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        txt_namapembeli = new javax.swing.JTextField();
        txt_Alamat = new javax.swing.JTextField();
        txt_no = new javax.swing.JTextField();
        Cmb_jenis = new javax.swing.JComboBox<>();
        jLabel6 = new javax.swing.JLabel();
        txt_jumlah = new javax.swing.JTextField();
        jLabel7 = new javax.swing.JLabel();
        txt_total = new javax.swing.JTextField();
        jLabel8 = new javax.swing.JLabel();
        txt_namabahan = new javax.swing.JTextField();
        jScrollPane2 = new javax.swing.JScrollPane();
        Table_Hasil = new javax.swing.JTable();
        btn_simpan = new javax.swing.JToggleButton();
        btn_hapus = new javax.swing.JToggleButton();
        btn_batal = new javax.swing.JToggleButton();
        btn_keluar = new javax.swing.JToggleButton();
        Form = new javax.swing.JToggleButton();
        btn_Update = new javax.swing.JToggleButton();
        txt_cari = new javax.swing.JTextField();
        jLabel9 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jLabel1.setFont(new java.awt.Font("Yu Gothic UI Semibold", 0, 18)); // NOI18N
        jLabel1.setText("Data Pembeli");

        jLabel2.setText("Nama Pembeli");

        jLabel3.setText("Alamat");

        jLabel4.setText("No Telepon");

        jLabel5.setText("Jenis Bahan ");

        Cmb_jenis.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Perekat", "Instalasi", "Bangunan", "Atap", "Pelapis" }));

        jLabel6.setText("Jumlah Pembelian");

        txt_jumlah.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txt_jumlahActionPerformed(evt);
            }
        });

        jLabel7.setText("Total Pembayaran");

        txt_total.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txt_totalActionPerformed(evt);
            }
        });

        jLabel8.setText("Nama Bahan");

        Table_Hasil.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null}
            },
            new String [] {
                "Nama Pembeli", "Alamat", "No Telepon", "Jenis Bahan", "Nama Bahan", "Jumlah Pembelian", "Total Pembayaran", "Stok Tersedia"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.String.class, java.lang.String.class, java.lang.Integer.class, java.lang.String.class, java.lang.String.class, java.lang.Integer.class, java.lang.Integer.class, java.lang.Integer.class
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }
        });
        Table_Hasil.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                Table_HasilMouseClicked(evt);
            }
        });
        jScrollPane2.setViewportView(Table_Hasil);

        btn_simpan.setText("Simpan");
        btn_simpan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_simpanActionPerformed(evt);
            }
        });

        btn_hapus.setText("Hapus");
        btn_hapus.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_hapusActionPerformed(evt);
            }
        });

        btn_batal.setText("Batal");
        btn_batal.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_batalActionPerformed(evt);
            }
        });

        btn_keluar.setText("Keluar");
        btn_keluar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_keluarActionPerformed(evt);
            }
        });

        Form.setText("Form Pembayaran");
        Form.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                FormActionPerformed(evt);
            }
        });

        btn_Update.setText("Update");
        btn_Update.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_UpdateActionPerformed(evt);
            }
        });

        jLabel9.setText("Search");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addGap(0, 519, Short.MAX_VALUE)
                        .addComponent(jLabel1)
                        .addGap(292, 292, 292))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jLabel7)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(txt_total, javax.swing.GroupLayout.PREFERRED_SIZE, 105, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(layout.createSequentialGroup()
                                .addGap(1, 1, 1)
                                .addComponent(jLabel6)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(txt_namabahan, javax.swing.GroupLayout.DEFAULT_SIZE, 112, Short.MAX_VALUE)
                                    .addComponent(txt_jumlah)))
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel2)
                                    .addComponent(jLabel3)
                                    .addComponent(jLabel4)
                                    .addComponent(jLabel5)
                                    .addComponent(jLabel8))
                                .addGap(28, 28, 28)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(txt_no)
                                    .addComponent(txt_Alamat)
                                    .addGroup(layout.createSequentialGroup()
                                        .addComponent(Cmb_jenis, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(0, 29, Short.MAX_VALUE))
                                    .addComponent(txt_namapembeli))))
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addGap(18, 18, 18)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jScrollPane2)
                                    .addGroup(layout.createSequentialGroup()
                                        .addComponent(btn_simpan)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                        .addComponent(btn_hapus)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                        .addComponent(btn_batal, javax.swing.GroupLayout.PREFERRED_SIZE, 67, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(btn_keluar)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                        .addComponent(btn_Update)
                                        .addGap(0, 0, Short.MAX_VALUE))))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(jLabel9)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(txt_cari, javax.swing.GroupLayout.PREFERRED_SIZE, 67, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(27, 27, 27))))))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(Form)
                .addGap(23, 23, 23))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(22, 22, 22)
                        .addComponent(jLabel1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel2)
                            .addComponent(txt_namapembeli, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel3)
                            .addComponent(txt_Alamat, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel4)
                            .addComponent(txt_no, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel5)
                            .addComponent(Cmb_jenis, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(17, 17, 17)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel8)
                            .addComponent(txt_namabahan, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(66, 66, 66)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(txt_cari, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel9))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 105, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel6)
                            .addComponent(txt_jumlah, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel7)
                            .addComponent(txt_total, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(btn_simpan)
                            .addComponent(btn_hapus)
                            .addComponent(btn_batal)
                            .addComponent(btn_keluar)
                            .addComponent(btn_Update))))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(Form)
                .addGap(31, 31, 31))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void txt_jumlahActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txt_jumlahActionPerformed
        // TODO add your handling code here:
        
    }//GEN-LAST:event_txt_jumlahActionPerformed

    private void txt_totalActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txt_totalActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txt_totalActionPerformed

    private void btn_hapusActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_hapusActionPerformed
        // TODO add your handling code here:
        delete();
    }//GEN-LAST:event_btn_hapusActionPerformed

    private void btn_simpanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_simpanActionPerformed
insert();     
    }//GEN-LAST:event_btn_simpanActionPerformed

    private void btn_keluarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_keluarActionPerformed
        // TODO add your handling code here:
        dispose();
    }//GEN-LAST:event_btn_keluarActionPerformed

    private void btn_batalActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_batalActionPerformed
        // TODO add your handling code here:
        clear();
    }//GEN-LAST:event_btn_batalActionPerformed

    private void btn_UpdateActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_UpdateActionPerformed
        // TODO add your handling code here:
        update();
    }//GEN-LAST:event_btn_UpdateActionPerformed

    private void Table_HasilMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_Table_HasilMouseClicked
        // TODO add your handling code here:
        int tabel = Table_Hasil.getSelectedRow();
        Namapembeli1 = Table_Hasil.getValueAt(tabel, 0).toString();
        Alamat1 = Table_Hasil.getValueAt(tabel, 1).toString();
        Notelepon1 = Table_Hasil.getValueAt(tabel, 2).toString();
        jenisbahan1 = Table_Hasil.getValueAt(tabel, 3).toString();
        Namabahan1 = Table_Hasil.getValueAt(tabel, 4).toString();
        Jumlah1 = Table_Hasil.getValueAt(tabel, 5).toString();
        Total1 = Table_Hasil.getValueAt(tabel, 6).toString();
        itempilih();

        
    }//GEN-LAST:event_Table_HasilMouseClicked

    private void FormActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_FormActionPerformed
        // TODO add your handling code here:
        new GUI_Pembayaran().setVisible(true);
    }//GEN-LAST:event_FormActionPerformed

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
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(GUI_DataPembeli.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(GUI_DataPembeli.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(GUI_DataPembeli.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(GUI_DataPembeli.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new GUI_DataPembeli().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JComboBox<String> Cmb_jenis;
    private javax.swing.JToggleButton Form;
    private javax.swing.JTable Table_Hasil;
    private javax.swing.JToggleButton btn_Update;
    private javax.swing.JToggleButton btn_batal;
    private javax.swing.JToggleButton btn_hapus;
    private javax.swing.JToggleButton btn_keluar;
    private javax.swing.JToggleButton btn_simpan;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTextField txt_Alamat;
    private javax.swing.JTextField txt_cari;
    private javax.swing.JTextField txt_jumlah;
    private javax.swing.JTextField txt_namabahan;
    private javax.swing.JTextField txt_namapembeli;
    private javax.swing.JTextField txt_no;
    private javax.swing.JTextField txt_total;
    // End of variables declaration//GEN-END:variables
}
