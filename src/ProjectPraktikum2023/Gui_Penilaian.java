/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package ProjectPraktikum2023;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.PreparedStatement;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

//masukkan import dibawah sini

/**
 *
 * @author icornermalang
 */
public class Gui_Penilaian extends javax.swing.JFrame {
    /**
     * Creates new form Gui_Penilaian
     */
    public Gui_Penilaian() {
        initComponents();
                txtKeaktifan.setEnabled(false);
        tampil();
        tampil_mhs();
        tampil_mk();
txtKeaktifan.setText(Integer.toString(0));
;
        //masuukan source code (txtKeaktifan.setEnabled(false);tampil();tampil_mhs();tampil_mk(); txtKeaktifan.setText(Integer.toString(0));)
    }
    public void batal() {
        txtNP1.setText("");
        txtNP2.setText("");
        txtNP3.setText("");
        txtPraktikum.setText("");
        txtUas.setText("");
        txtUts.setText("");
        txtKeaktifan.setText("");
    }
    //masukkan atribut dibawah sini (String nim1, kd_mk1, np1, np2, np3, UTS, UAS, PRAK, keaktifan, NA;)
    String nim1, kd_mk1, np1, np2, np3, UTS, UAS, PRAK, keaktifan, NA;
    //masukkan conection dibawah sini (public Connection conn;)
    public Connection conn;
    //masukkan method koneksi()
    public void koneksi() throws SQLException {
        try {
            conn = null;
            Class.forName("com.mysql.cj.jdbc.Driver");
            conn = DriverManager.getConnection("jdbc:mysql://localhost/OOP_2218027?user=root&password=");
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(Gui_Penilaian.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException e) {
            Logger.getLogger(Gui_Penilaian.class.getName()).log(Level.SEVERE, null, e);
        } catch (Exception es) {
            Logger.getLogger(Gui_Penilaian.class.getName()).log(Level.SEVERE, null, es);
        }
    }

    //masukkan method tampil()
public void tampil() {
        DefaultTableModel tabelhead = new DefaultTableModel();
        tabelhead.addColumn("NIM");
        tabelhead.addColumn("Kode MK");
        tabelhead.addColumn("NP1");
        tabelhead.addColumn("NP2");
        tabelhead.addColumn("UTS");
        tabelhead.addColumn("NP3");
        tabelhead.addColumn("PRAK");
        tabelhead.addColumn("UAS");
        tabelhead.addColumn("NA");
        try {
            koneksi();
            String sql = "SELECT * FROM tb_nilai";
            Statement stat = conn.createStatement();
            ResultSet res = stat.executeQuery(sql);
            while (res.next()) {
                tabelhead.addRow(new Object[]{res.getString(2), res.getString(3), res.getString(4), res.getString(5), res.getString(6), res.getString(7), res.getString(8), res.getString(9), res.getString(10),});
            }
            table_data.setModel(tabelhead);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "BELUM TERKONEKSI");
        }
    }

    //masukka method tampil_mhs() dibawah sini
public void tampil_mhs() {
        try {
            koneksi();
            String sql = "SELECT nim FROM tb_mahasiswa order by nim asc";
            Statement stt = conn.createStatement();
            ResultSet res = stt.executeQuery(sql);
            while (res.next()) {
                Object[] ob = new Object[3];
                ob[0] = res.getString(1);
                cmbNim.addItem((String) ob[0]);
            }
            res.close();
            stt.close();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    //masukkan method tampil_mk()
    public void tampil_mk() {
        try {
            koneksi();
            String sql = "SELECT kode_mk FROM tb_matkul order by kode_mk asc";
            Statement stt = conn.createStatement();
            ResultSet res = stt.executeQuery(sql);
            while (res.next()) {
                Object[] ob = new Object[3];
                ob[0] = res.getString(1);
                cmbKodeMk.addItem((String) ob[0]);
            }
            res.close();
            stt.close();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    //masukkan method refresh()
        public void refresh() {
        new Gui_Penilaian().setVisible(true);
        this.setVisible(false);
    }

    //masukkan method insert()
    public void insert() {
        String Nim = (String) cmbNim.getSelectedItem();
        String KodeMK = (String) cmbKodeMk.getSelectedItem();
        String NP1 = txtNP1.getText();
        String NP2 = txtNP2.getText();
        String UTS = txtUts.getText();
        String NP3 = txtNP3.getText();
        String PRAK = txtPraktikum.getText();
        String UAS = txtUas.getText();
        String NA = txtNA.getText();
        try {
            koneksi();
            Statement statement = conn.createStatement();
            statement.executeUpdate("INSERT INTO tb_nilai(Nim, kd_mk, NP1, NP2, UTS,NP3,prak,UAS,NA)"
                    + "VALUES('" + Nim + "','" + KodeMK + "','" + NP1 + "','" + NP2 + "','" + UTS + "','" + NP3 + "',"
                    + "'" + PRAK + "','" + UAS + "','" + NA + "')");
            statement.close();
            JOptionPane.showMessageDialog(null, "Berhasil Memasukan Data Nilai!");
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Terjadi Kesalahan Input!");
        }
        refresh();
    }

    //masukkan method update()  
    public void update() {
        String Nim = (String) cmbNim.getSelectedItem();
        String KodeMK = (String) cmbKodeMk.getSelectedItem();
        String NP1 = txtNP1.getText();
        String NP2 = txtNP2.getText();
        String UTS = txtUts.getText();
        String NP3 = txtNP3.getText();
        String PRAK = txtPraktikum.getText();
        String UAS = txtUas.getText();
        String NA = txtNA.getText();

        String nim_lama = nim1;
        String kode_lama = kd_mk1;

        try {
            Statement statement = conn.createStatement();
            statement.executeUpdate("UPDATE tb_nilai SET Nim='" + Nim + "'," + "kd_mk='" + KodeMK + "'"
                    + ",NP1='" + NP1 + "',NP2='" + NP2 + "',UTS='" + UTS + "',NP3='" + NP3 + "',prak='" + PRAK + "',UAS='" + UAS + "',NA='" + NA + "' WHERE Nim ='" + nim_lama + "' AND kd_mk='" + kode_lama + "'");
            statement.close();
            conn.close();
            JOptionPane.showMessageDialog(null, "Update Data Nilai!");
        } catch (Exception e) {
            System.out.println("Error : " + e);
        }
        
    }

    //masukkan method NilaiAkhir()
public double NilaiAkhir() {
        double NP1, NP2, NP3, NilaiPrak, UTS, UAS, nilaiProses, hasil, nilaiKeaktifan;
        NP1 = Integer.parseInt(txtNP1.getText());
        NP2 = Integer.parseInt(txtNP1.getText());
        NP3 = Integer.parseInt(txtNP1.getText());
        NilaiPrak = Integer.parseInt(txtNP1.getText());
        UTS = Integer.parseInt(txtNP1.getText());
        UAS = Integer.parseInt(txtNP1.getText());
        nilaiKeaktifan = Integer.parseInt(txtKeaktifan.getText());
        nilaiProses = (NP1 * 0.1) + (NP2 * 0.2) + (NP3 * 0.1) + (UTS * 0.2) + (NilaiPrak * 0.4);
        hasil = (nilaiKeaktifan * 0.1) + nilaiProses;
        txtNA.setText(Double.toString(hasil));
        return hasil;
    }

    //masukkan method itempilih()
    void itempilih() {
        cmbNim.setSelectedItem(nim1);
        cmbKodeMk.setSelectedItem(kd_mk1);
        txtNP1.setText(np1);
        txtNP2.setText(np2);
        txtNP3.setText(np3);
        txtPraktikum.setText(PRAK);
        txtUas.setText(UAS);
        txtUts.setText(UTS);
        txtKeaktifan.setText(keaktifan);
        txtKeaktifan.setText(Integer.toString(0));
        txtNA.setText(NA);
    }

    //masukkan method batal() 
    public void batal1() {
        txtNP1.setText("");
        txtNP2.setText("");
        txtNP3.setText("");
        txtPraktikum.setText("");
        txtUas.setText("");
        txtUts.setText("");
        txtKeaktifan.setText("");
    }

    //masukkan method delete()
public void delete() {
        int ok = JOptionPane.showConfirmDialog(null, "Apakah Anda yakin akan menghapus data ?", "Konfirmasi", JOptionPane.YES_NO_OPTION);
        if (ok == 0) {
            try {
                String sql = "DELETE FROM tb_nilai WHERE Nim='" + cmbNim.getSelectedItem() + "' AND kd_mk='" + cmbKodeMk.getSelectedItem() + "'";
                PreparedStatement stmt = conn.prepareStatement(sql);
                stmt.executeUpdate();
                JOptionPane.showMessageDialog(null, "Data Berhasil di hapus");
                batal();
            } catch (Exception e) {
                JOptionPane.showMessageDialog(null, "Data gagal di hapus");
            }
        }
        refresh();
    }

    //masukkan method cari()
public void cari() {
        try {
            try ( Statement statement = conn.createStatement()) {
                String sql = "SELECT * FROM tb_nilai WHERE `Nim` LIKE '%" + txtCari.getText() + "%'";
                ResultSet rs = statement.executeQuery(sql);
                //menampilkan data dari sql query
                if (rs.next()) // .next() = scanner method
                {
                    cmbNim.setSelectedItem(rs.getString(2));
                    cmbKodeMk.setSelectedItem(rs.getString(3));
                    txtNP1.setText(rs.getString(4));
                    txtNP2.setText(rs.getString(5));
                    txtUts.setText(rs.getString(6));
                    txtNP3.setText(rs.getString(7));
                    txtPraktikum.setText(rs.getString(8));
                    txtUas.setText(rs.getString(9));
                    txtNA.setText(rs.getString(10));

                } else {
                    JOptionPane.showMessageDialog(null, "Data yang Anda cari tidak ada");
                }
            }
        } catch (Exception ex) {
            System.out.println("Error." + ex);
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

        jLabel1 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        cmKeaktifan = new javax.swing.JCheckBox();
        txtNP1 = new javax.swing.JTextField();
        txtNP2 = new javax.swing.JTextField();
        txtUts = new javax.swing.JTextField();
        txtNP3 = new javax.swing.JTextField();
        txtPraktikum = new javax.swing.JTextField();
        txtUas = new javax.swing.JTextField();
        btnProses = new javax.swing.JButton();
        jLabel11 = new javax.swing.JLabel();
        txtKeaktifan = new javax.swing.JTextField();
        jScrollPane2 = new javax.swing.JScrollPane();
        table_data = new javax.swing.JTable();
        cmbNim = new javax.swing.JComboBox<>();
        cmbKodeMk = new javax.swing.JComboBox<>();
        txtCari = new javax.swing.JTextField();
        btnCari = new javax.swing.JButton();
        btnUbah = new javax.swing.JButton();
        btnHapus = new javax.swing.JButton();
        btnBatal = new javax.swing.JButton();
        txtNA = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        btnSimpan = new javax.swing.JButton();
        btnNim = new javax.swing.JButton();
        btnKdMk = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jLabel1.setText("Program Penilaian");

        jLabel5.setText("NP 1");

        jLabel6.setText("NP 2");

        jLabel7.setText("NP3");

        jLabel8.setText("Praktikum");

        jLabel9.setText("UTS");

        jLabel10.setText("UAS");

        cmKeaktifan.setText("Tambahkan Nilai Keaktifan");
        cmKeaktifan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cmKeaktifanActionPerformed(evt);
            }
        });

        btnProses.setText("Proses");
        btnProses.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnProsesActionPerformed(evt);
            }
        });

        jLabel11.setText("Nilai Keaktifan");

        txtKeaktifan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtKeaktifanActionPerformed(evt);
            }
        });

        table_data.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null}
            },
            new String [] {
                "Nim", "Kd MK", "NP1", "NP 2", "UTS", "NP3", "PRAK", "UAS", "AKHIR"
            }
        ));
        table_data.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                table_dataMouseClicked(evt);
            }
        });
        jScrollPane2.setViewportView(table_data);

        cmbNim.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "~NIM~" }));

        cmbKodeMk.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "~KD MK~" }));

        btnCari.setText("Cari");
        btnCari.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCariActionPerformed(evt);
            }
        });

        btnUbah.setText("Update");
        btnUbah.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnUbahActionPerformed(evt);
            }
        });

        btnHapus.setText("Delete");
        btnHapus.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnHapusActionPerformed(evt);
            }
        });

        btnBatal.setText("Batal");
        btnBatal.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnBatalActionPerformed(evt);
            }
        });

        jLabel3.setText("Nilai Akhir");

        btnSimpan.setText("Simpan");
        btnSimpan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSimpanActionPerformed(evt);
            }
        });

        btnNim.setText("Nim");
        btnNim.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnNimActionPerformed(evt);
            }
        });

        btnKdMk.setText("KD MK");
        btnKdMk.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnKdMkActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(33, 33, 33)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(104, 104, 104)
                        .addComponent(jLabel1)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(btnProses, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                                .addComponent(jLabel3)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(txtNA, javax.swing.GroupLayout.PREFERRED_SIZE, 207, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(layout.createSequentialGroup()
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addGroup(layout.createSequentialGroup()
                                                .addComponent(jLabel5)
                                                .addGap(36, 36, 36)
                                                .addComponent(txtNP1, javax.swing.GroupLayout.PREFERRED_SIZE, 74, javax.swing.GroupLayout.PREFERRED_SIZE))
                                            .addGroup(layout.createSequentialGroup()
                                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                                    .addComponent(jLabel7)
                                                    .addComponent(jLabel6))
                                                .addGap(36, 36, 36)
                                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                                    .addComponent(txtNP2, javax.swing.GroupLayout.PREFERRED_SIZE, 74, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                    .addComponent(txtNP3, javax.swing.GroupLayout.PREFERRED_SIZE, 74, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                            .addGroup(layout.createSequentialGroup()
                                                .addComponent(jLabel11)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                                .addComponent(txtKeaktifan, javax.swing.GroupLayout.PREFERRED_SIZE, 77, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(cmKeaktifan, javax.swing.GroupLayout.Alignment.TRAILING)
                                            .addGroup(layout.createSequentialGroup()
                                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                    .addComponent(jLabel8)
                                                    .addComponent(jLabel10)
                                                    .addComponent(jLabel9))
                                                .addGap(23, 23, 23)
                                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                    .addComponent(txtUts, javax.swing.GroupLayout.PREFERRED_SIZE, 74, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                                        .addComponent(txtUas)
                                                        .addComponent(txtPraktikum, javax.swing.GroupLayout.PREFERRED_SIZE, 75, javax.swing.GroupLayout.PREFERRED_SIZE))))))
                                    .addGroup(layout.createSequentialGroup()
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(btnNim)
                                            .addComponent(btnKdMk))
                                        .addGap(18, 18, 18)
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                            .addComponent(cmbNim, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                            .addComponent(cmbKodeMk, 0, 272, Short.MAX_VALUE))))
                                .addGap(0, 6, Short.MAX_VALUE))
                            .addComponent(btnSimpan, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addGap(14, 14, 14)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addGap(32, 32, 32)
                                .addComponent(btnUbah)
                                .addGap(18, 18, 18)
                                .addComponent(btnHapus)
                                .addGap(18, 18, 18)
                                .addComponent(btnBatal))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                .addComponent(txtCari, javax.swing.GroupLayout.PREFERRED_SIZE, 136, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(btnCari))
                            .addComponent(jScrollPane2, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 526, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(31, 31, 31)
                .addComponent(jLabel1)
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(cmbNim, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtCari, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnCari)
                    .addComponent(btnNim))
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(16, 16, 16)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(cmbKodeMk, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(btnKdMk))
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel5)
                            .addComponent(txtNP1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtUts, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel9))
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel6)
                            .addComponent(jLabel8)
                            .addComponent(txtNP2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtPraktikum, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel10)
                            .addComponent(txtUas, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel7)
                            .addComponent(txtNP3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(cmKeaktifan)
                            .addComponent(jLabel11)
                            .addComponent(txtKeaktifan, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(btnProses)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(txtNA, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel3)))
                    .addGroup(layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 267, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnSimpan)
                    .addComponent(btnUbah)
                    .addComponent(btnHapus)
                    .addComponent(btnBatal))
                .addContainerGap(24, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnProsesActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnProsesActionPerformed
        // TODO add your handling code here:
        //method NilaiAkhir()
        NilaiAkhir();
    }//GEN-LAST:event_btnProsesActionPerformed

    private void txtKeaktifanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtKeaktifanActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtKeaktifanActionPerformed

    private void cmKeaktifanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cmKeaktifanActionPerformed
        // TODO add your handling code here:
        if (cmKeaktifan.isSelected()){
            txtKeaktifan.setEnabled(true);
        }else{
            txtKeaktifan.setEnabled(false);
        }
    }//GEN-LAST:event_cmKeaktifanActionPerformed

    private void btnBatalActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnBatalActionPerformed
        // TODO add your handling code here:
        //masukkan method batal();
        
        
    }//GEN-LAST:event_btnBatalActionPerformed

    private void btnSimpanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSimpanActionPerformed
        // TODO add your handling code here:
        //masukkan method insert();
        insert();
    }//GEN-LAST:event_btnSimpanActionPerformed

    private void btnUbahActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnUbahActionPerformed
        // TODO add your handling code here:
        //masukkan method update();
        update();
    }//GEN-LAST:event_btnUbahActionPerformed

    private void table_dataMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_table_dataMouseClicked
        // TODO add your handling code here:
        //masukkan source code onclick pada tabel
        int tabel = table_data.getSelectedRow(); //0
        nim1 = table_data.getValueAt(tabel, 0).toString();
        kd_mk1 = table_data.getValueAt(tabel, 1).toString();
        np1 = table_data.getValueAt(tabel, 2).toString();
        np2 = table_data.getValueAt(tabel, 3).toString();
        UTS = table_data.getValueAt(tabel, 4).toString();
        np3 = table_data.getValueAt(tabel, 5).toString();
        PRAK = table_data.getValueAt(tabel, 6).toString();
        UAS = table_data.getValueAt(tabel, 7).toString();
        NA = table_data.getValueAt(tabel, 8).toString();
        itempilih();

        
    }//GEN-LAST:event_table_dataMouseClicked

    private void btnHapusActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnHapusActionPerformed
        // TODO add your handling code here:
        //masukkan method delete();
        delete();
    }//GEN-LAST:event_btnHapusActionPerformed

    private void btnCariActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCariActionPerformed
        // TODO add your handling code here:
        //masukkan method cari();
        cari();
    }//GEN-LAST:event_btnCariActionPerformed

    private void btnNimActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnNimActionPerformed
        // TODO add your handling code here:
        new GUI_Mahasiswa().setVisible(true);
    }//GEN-LAST:event_btnNimActionPerformed

    private void btnKdMkActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnKdMkActionPerformed
        // TODO add your handling code here:
        new GUI_DataMatkul().setVisible(true);
    }//GEN-LAST:event_btnKdMkActionPerformed

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
            java.util.logging.Logger.getLogger(Gui_Penilaian.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Gui_Penilaian.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Gui_Penilaian.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Gui_Penilaian.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
       

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Gui_Penilaian().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnBatal;
    private javax.swing.JButton btnCari;
    private javax.swing.JButton btnHapus;
    private javax.swing.JButton btnKdMk;
    private javax.swing.JButton btnNim;
    private javax.swing.JButton btnProses;
    private javax.swing.JButton btnSimpan;
    private javax.swing.JButton btnUbah;
    private javax.swing.JCheckBox cmKeaktifan;
    private javax.swing.JComboBox<String> cmbKodeMk;
    private javax.swing.JComboBox<String> cmbNim;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTable table_data;
    private javax.swing.JTextField txtCari;
    private javax.swing.JTextField txtKeaktifan;
    private javax.swing.JTextField txtNA;
    private javax.swing.JTextField txtNP1;
    private javax.swing.JTextField txtNP2;
    private javax.swing.JTextField txtNP3;
    private javax.swing.JTextField txtPraktikum;
    private javax.swing.JTextField txtUas;
    private javax.swing.JTextField txtUts;
    // End of variables declaration//GEN-END:variables
}
