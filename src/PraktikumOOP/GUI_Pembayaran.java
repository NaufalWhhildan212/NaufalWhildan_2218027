package PraktikumOOP;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;


public class GUI_Pembayaran extends javax.swing.JFrame {

    /**
     * Creates new form GUI_Pembayaran
     */
    public GUI_Pembayaran() {
        initComponents(); 
        tampil();
        tampilDataPembeli();
    }
    public Connection conn;
    public void clear(){
        cmb_nama.setSelectedItem(0);
            txt_diskon.setText("");
            txt_Kasir.setText("");
            txt_harga.setText("");
            cmb1.setSelectedItem(0);
    }
    public void koneksi() throws SQLException {
        try {
            conn = null;
            Class.forName("com.mysql.cj.jdbc.Driver");
            conn = DriverManager.getConnection("jdbc:mysql://localhost/project_2218027?user=root&password=");
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(GUI_Pembayaran.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException e) {
            Logger.getLogger(GUI_Pembayaran.class.getName()).log(Level.SEVERE, null, e);
        } catch (Exception es) {
            Logger.getLogger(GUI_Pembayaran.class.getName()).log(Level.SEVERE, null, es);
        }
    }
    public void tampil() {
        DefaultTableModel tabelhead = new DefaultTableModel();
        tabelhead.addColumn("Nama pembeli");
        tabelhead.addColumn("Nama Kasir");
        tabelhead.addColumn("No Telepon");
        tabelhead.addColumn("Diskon");
        tabelhead.addColumn("Harga Barang");
        tabelhead.addColumn("Total");
        tabelhead.addColumn("Pajak");
        tabelhead.addColumn("Laba");
        try {
            koneksi();
            String sql = "SELECT * FROM tb_pembayaran";
            Statement stat = conn.createStatement();
            ResultSet res = stat.executeQuery(sql);
            while (res.next()) {
                tabelhead.addRow(new Object[]{res.getString(2), res.getString(3), res.getString(4), res.getString(5), res.getString(6), res.getString(7),res.getString(8),res.getString(9)});
            }
            Table_Info.setModel(tabelhead);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "BELUM TERKONEKSI");
        }
    }
    public void tampilDataPembeli(){
        try{
            koneksi();
            String sql = "SELECT Nama_pembeli FROM tb_datapembeli order by Nama_pembeli";
            Statement stt = conn.createStatement();
            ResultSet res = stt.executeQuery(sql);
            while(res.next()){
                Object[] ob = new Object[3];
                ob[0] = res.getString(1);
                cmb_nama.addItem((String) ob[0]);
            }
            res.close();
            stt.close();
        }catch(Exception e){
            System.out.println(e.getMessage());
        }
    }
    public void refresh() {
        new GUI_Pembayaran().setVisible(true);
        this.setVisible(false);
    }
    public void insert() {
       String Namapembeli = cmb_nama.getSelectedItem().toString();
       String NamaKasir = txt_Kasir.getText();
        String metode= "";
        if (cmb1.getSelectedIndex() == 0){
            metode=("Cash");
        }else if(cmb1.getSelectedIndex()==1){
           metode=("transfer");
        }
       int Diskon = Integer.parseInt(txt_diskon.getText());
       int Harga = Integer.parseInt(txt_harga.getText());
       double total = Double.parseDouble(txt_harga.getText());
       double pajak = Double.parseDouble(txtpajak.getText());
       double laba = Double.parseDouble(txtlaba.getText());
        try {
            koneksi();
            Statement statement = conn.createStatement();
            statement.executeUpdate("INSERT INTO tb_pembayaran(Nama_Pembeli, Nama_Kasir, Metode_Pembayaran, Diskon, Harga_Barang, Total, Pajak, Laba)"
                    + "VALUES('" +Namapembeli + "','" + NamaKasir + "','" + metode + "','" + Diskon + "','" 
                    + Harga  + "','" + total +"','" + pajak +"','" + laba + "')");
            statement.close();
            JOptionPane.showMessageDialog(null, "Berhasil Memasukan Data Pembeli!") ;
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Terjadi Kesalahan Input!");
        }
        refresh();
    }
    
    public void update() {
        String Namapembeli = cmb_nama.getSelectedItem().toString();
       String NamaKasir = txt_Kasir.getText();
        String metode= "";
        if (cmb1.getSelectedIndex()==0){
            metode=("Cash");
        }else if(cmb1.getSelectedIndex()==1){
           metode=("transfer");
        }
       int Diskon = Integer.parseInt(txt_diskon.getText());
       int Harga = Integer.parseInt(txt_harga.getText());
       double Total = Double.parseDouble(txt_harga.getText());
       double Pajak = Double.parseDouble(txtpajak.getText());
       double Laba = Double.parseDouble(txtlaba.getText());
        String NamaLama = Namapembeli1;
        try {
            Statement statement = conn.createStatement();
            statement.executeUpdate("UPDATE tb_pembayaran SET Nama_Pembeli='" + Namapembeli + "'," + "Nama_Kasir='" + NamaKasir + "',"
                    + "Metode_Pembayaran='" + metode + "'" + ",Diskon='" + Diskon + "',Harga_Barang='" + Harga  + "',Total='" + Total  +"',Pajak='" + Pajak  +"',Laba='" + Laba  
                    + "' WHERE Nama_Pembeli = '" + NamaLama + "'");
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
                String sql = "DELETE FROM tb_datapembeli WHERE Nama_Pembeli='" + cmb_nama.getSelectedItem() + "'";
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
                String sql = "SELECT * FROM tb_pembayaran WHERE `Nama_Pembeli`  LIKE '%" + txt_search.getText() + "%'";
                ResultSet rs = statement.executeQuery(sql); //menampilkan data dari sql query
                if (rs.next()) // .next() = scanner method
                {
                    cmb_nama.setSelectedItem(rs.getString(2));
                    txt_Kasir.setText(rs.getString(3));
                    txt_diskon.setText(rs.getString(5));
                    String metode = rs.getString(4);
                    if (cmb1.getSelectedItem().equals("Cash")){
                        cmb1.setSelectedItem(true);
                    }else if(cmb1.getSelectedItem().equals("transfer")){
                        cmb1.setSelectedItem(true);
                    }
                    txt_harga.setText(rs.getString(6));
                    txttotal.setText(rs.getString(7));
                    txtpajak.setText(rs.getString(8));
                    txtlaba.setText(rs.getString(9));
                } else {
                    JOptionPane.showMessageDialog(null, "Data yang Anda cari tidak ada");
                }
            }
        } catch (Exception ex) {
            System.out.println("Error." + ex);
        }    
    }
        String Namapembeli1,NamaKasir,diskon,metode,harga;
        public void itempilih() {
        cmb_nama.setSelectedItem(Namapembeli1);
        txt_Kasir.setText(NamaKasir);
        txt_diskon.setText(diskon);
        cmb1.setSelectedItem(metode);
        txt_harga.setText(harga);
    }
        public double hitTotal(){
            double total, harga, diskon;
            harga = Double.parseDouble(txt_harga.getText());
            diskon = Double.parseDouble(txt_diskon.getText());
            total = harga - diskon;
            txttotal.setText(Double.toString(total));
            return total;
            
        }
        
        public double hitPajak(){
            double pajak, harga;
            harga = Double.parseDouble(txt_harga.getText());
            pajak = harga *0.1;
            txtpajak.setText(Double.toString(pajak));
            return pajak;
            
        }
        
        public double hitLaba(){
            double laba, harga;
            harga = Double.parseDouble(txt_harga.getText());
            laba = harga * 0.2;
            txtlaba.setText(Double.toString(laba));
            return laba;
            
        }
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        txt_total3 = new javax.swing.JTextField();
        jLabel1 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        txt_diskon = new javax.swing.JTextField();
        cmb1 = new javax.swing.JComboBox<>();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        txt_Kasir = new javax.swing.JTextField();
        jLabel6 = new javax.swing.JLabel();
        txt_harga = new javax.swing.JTextField();
        jScrollPane2 = new javax.swing.JScrollPane();
        Table_Info = new javax.swing.JTable();
        btn_Simpan = new javax.swing.JToggleButton();
        btn_hapus = new javax.swing.JToggleButton();
        btn_batal = new javax.swing.JToggleButton();
        btn_keluar = new javax.swing.JToggleButton();
        txt_search = new javax.swing.JTextField();
        btnUpdate = new javax.swing.JToggleButton();
        cmb_nama = new javax.swing.JComboBox<>();
        jButton1 = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();
        btnproses = new javax.swing.JButton();
        txttotal = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        txtpajak = new javax.swing.JTextField();
        jLabel8 = new javax.swing.JLabel();
        txtlaba = new javax.swing.JTextField();

        txt_total3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txt_total3ActionPerformed(evt);
            }
        });

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jLabel1.setText("Metode Pembayaran Bahan Bangunan ");

        jLabel3.setText("Nama Kasir");

        txt_diskon.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txt_diskonActionPerformed(evt);
            }
        });

        cmb1.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Cash", "Transfer" }));

        jLabel4.setText("Metode Pembayaran");

        jLabel5.setText("Harga Barang");

        jLabel6.setText("Diskon");

        txt_harga.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txt_hargaActionPerformed(evt);
            }
        });

        Table_Info.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null}
            },
            new String [] {
                "Nama Pembeli", "Nama Kasir", "Metode", "Diskon", "Harga Barang", "Total", "Pajak", "Laba"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.String.class, java.lang.String.class, java.lang.Object.class, java.lang.Integer.class, java.lang.Integer.class, java.lang.Integer.class, java.lang.Integer.class, java.lang.Integer.class
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }
        });
        Table_Info.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                Table_InfoMouseClicked(evt);
            }
        });
        jScrollPane2.setViewportView(Table_Info);

        btn_Simpan.setText("Simpan");
        btn_Simpan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_SimpanActionPerformed(evt);
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

        txt_search.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txt_searchActionPerformed(evt);
            }
        });

        btnUpdate.setText("Update");
        btnUpdate.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnUpdateActionPerformed(evt);
            }
        });

        cmb_nama.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Nama Pembeli" }));

        jButton1.setText("Nama Pembeli");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jButton2.setText("Search");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        btnproses.setText("Proses");
        btnproses.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnprosesActionPerformed(evt);
            }
        });

        jLabel2.setText("Total");

        jLabel7.setText("Pajak");

        txtpajak.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtpajakActionPerformed(evt);
            }
        });

        jLabel8.setText("Laba");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel4)
                                    .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, 78, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jButton1)
                                    .addComponent(jLabel3))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                            .addGroup(layout.createSequentialGroup()
                                .addContainerGap()
                                .addComponent(jLabel5)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(txt_Kasir, javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(cmb1, javax.swing.GroupLayout.PREFERRED_SIZE, 105, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(0, 0, Short.MAX_VALUE))
                            .addComponent(cmb_nama, 0, 1, Short.MAX_VALUE)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(txt_diskon, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 105, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(txt_harga, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 105, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jLabel2)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(txttotal, javax.swing.GroupLayout.PREFERRED_SIZE, 108, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(btnproses, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                .addComponent(jLabel8)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(txtlaba, javax.swing.GroupLayout.PREFERRED_SIZE, 108, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                .addComponent(jLabel7)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(txtpajak, javax.swing.GroupLayout.PREFERRED_SIZE, 108, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 586, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addContainerGap(24, Short.MAX_VALUE))
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 265, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(102, 102, 102)
                                .addComponent(jButton2)
                                .addGap(18, 18, 18)
                                .addComponent(txt_search, javax.swing.GroupLayout.PREFERRED_SIZE, 86, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(0, 0, Short.MAX_VALUE))))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(btn_Simpan)
                        .addGap(18, 18, 18)
                        .addComponent(btn_batal, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(btn_hapus, javax.swing.GroupLayout.PREFERRED_SIZE, 85, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(btn_keluar)
                        .addGap(18, 18, 18)
                        .addComponent(btnUpdate)
                        .addGap(0, 0, Short.MAX_VALUE))))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jButton2)
                            .addComponent(txt_search, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)))
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(cmb_nama, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jButton1))
                        .addGap(18, 18, Short.MAX_VALUE)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel3)
                            .addComponent(txt_Kasir, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel4)
                            .addComponent(cmb1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel6)
                            .addComponent(txt_diskon, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel5)
                            .addComponent(txt_harga, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addComponent(btnproses)
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel2)
                            .addComponent(txttotal, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(txtpajak, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel7)))
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtlaba, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel8)
                    .addComponent(btn_Simpan)
                    .addComponent(btn_batal)
                    .addComponent(btn_hapus)
                    .addComponent(btn_keluar)
                    .addComponent(btnUpdate))
                .addGap(115, 115, 115))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void txt_hargaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txt_hargaActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txt_hargaActionPerformed

    private void btn_keluarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_keluarActionPerformed
        // TODO add your handling code here:
        dispose();
    }//GEN-LAST:event_btn_keluarActionPerformed

    private void txt_searchActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txt_searchActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txt_searchActionPerformed

    private void btn_SimpanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_SimpanActionPerformed
       insert();
    }//GEN-LAST:event_btn_SimpanActionPerformed

    private void btn_hapusActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_hapusActionPerformed
        delete();
    }//GEN-LAST:event_btn_hapusActionPerformed

    private void btn_batalActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_batalActionPerformed
        // TODO add your handling code here:
        clear();
    }//GEN-LAST:event_btn_batalActionPerformed

    private void txt_total3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txt_total3ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txt_total3ActionPerformed

    private void btnUpdateActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnUpdateActionPerformed
        // TODO add your handling code here:
        update();
    }//GEN-LAST:event_btnUpdateActionPerformed

    private void Table_InfoMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_Table_InfoMouseClicked
        // TODO add your handling code here:
        int tabel = Table_Info.getSelectedRow();
        Namapembeli1 = Table_Info.getValueAt(tabel, 0).toString();
        NamaKasir = Table_Info.getValueAt(tabel, 1).toString();
        metode = Table_Info.getValueAt(tabel, 2).toString();
        diskon = Table_Info.getValueAt(tabel, 3).toString();
        harga = Table_Info.getValueAt(tabel, 4).toString();
        itempilih();
    }//GEN-LAST:event_Table_InfoMouseClicked

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        // TODO add your handling code here:
        new GUI_DataPembeli().setVisible(true);
    }//GEN-LAST:event_jButton1ActionPerformed

    private void txt_diskonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txt_diskonActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txt_diskonActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jButton2ActionPerformed

    private void txtpajakActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtpajakActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtpajakActionPerformed

    private void btnprosesActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnprosesActionPerformed
        // TODO add your handling code here:
        hitTotal();
        hitPajak();
        hitLaba();
    }//GEN-LAST:event_btnprosesActionPerformed

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
            java.util.logging.Logger.getLogger(GUI_Pembayaran.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(GUI_Pembayaran.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(GUI_Pembayaran.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(GUI_Pembayaran.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new GUI_Pembayaran().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTable Table_Info;
    private javax.swing.JToggleButton btnUpdate;
    private javax.swing.JToggleButton btn_Simpan;
    private javax.swing.JToggleButton btn_batal;
    private javax.swing.JToggleButton btn_hapus;
    private javax.swing.JToggleButton btn_keluar;
    private javax.swing.JButton btnproses;
    private javax.swing.JComboBox<String> cmb1;
    private javax.swing.JComboBox<String> cmb_nama;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTextField txt_Kasir;
    private javax.swing.JTextField txt_diskon;
    private javax.swing.JTextField txt_harga;
    private javax.swing.JTextField txt_search;
    private javax.swing.JTextField txt_total3;
    private javax.swing.JTextField txtlaba;
    private javax.swing.JTextField txtpajak;
    private javax.swing.JTextField txttotal;
    // End of variables declaration//GEN-END:variables
}
