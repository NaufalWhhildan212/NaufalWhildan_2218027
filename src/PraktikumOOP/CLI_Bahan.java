/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package PraktikumOOP;

/**
 *
 * @author Naufal whildan
 */
abstract public class CLI_Bahan {
    public String  Jenis,Bahan,Ukuran;
    public int Harga;
    private int Satuan ,Stok;
    //constructor 
 public void dataJenis(String jenis){
 this.Jenis = jenis;
    }
 public void dataBahan(String bahan){
 this.Bahan = bahan;
    }
  public void dataUkuran(String ukuran){
 this.Ukuran = ukuran;
    }
  public int dataHarga(int harga){
 this.Harga = harga;
 return harga;
    }
 public String cetakJenis(){
 return Jenis;
 }
 public String cetakBahan(){
 return Bahan;
 }
 public String cetakUkuran(){
 return Ukuran;
 }
 public int cetakHarga(){
 return Harga;
 }
 //method Hitung Total
public double HitTotal(){
   double total;
   total = Harga*Satuan;
        return total;
 }
//method mengecek ketersedian atau stok
abstract public int CekStok();
//method abstract Untuk Menghitung total
abstract public double HitTOtal();
    //Set dan Get
    public int getSatuan() {
        return Satuan;
    }

    public int setSatuan(int Satuan) {
        this.Satuan = Satuan;
        return Satuan;
    }

    public int getStok() {
        return Stok;
    }

    public void  setStok(int Stok) {
        this.Stok = Stok;
    }
}
