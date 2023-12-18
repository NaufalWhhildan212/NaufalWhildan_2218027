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
public class DataPembeli extends CLI_Bahan{
    public String namapembeli,Alamat,jenis;
    public int jumlah,stok;
    public String namabahan;
    private int total,notelepon;
    
 public void datanamapembeli(String namapembeli){
 this.namapembeli = namapembeli;
    }
 public void dataAlamat(String Alamat){
 this.Alamat = Alamat;
    }
  public void datajumlah(int jumlah){
 this.jumlah = jumlah;
    }
  public void datanamabahan(String namabahan){
 this.namabahan = namabahan;
    }
  public void datajenisbahan(String jenisbahan){
 this.jenis = jenisbahan;
    }
  public String cetaknamapembeli(){
 return namapembeli;
 }
  public String cetakAlamat(){
 return Alamat;
 }
  public String cetaknamabahan(){
 return namabahan;
 }
  public String cetakjenisbahan(){
 return jenis;
 }
 int cetakjumlah(){
 return jumlah;
 }
 
 //Seter and getter nya
    public int getTotal() {
        return total;
    }
    public void setTotal(int total) {
        this.total = total;
    }
    public int getNotelepon() {
        return notelepon;
    }
    public void setNotelepon(int notelepon) {
        this.notelepon = notelepon;
    }
    
    //abstrack method
    //method yang digunakan untuk menghitung ketersediaan stok
    @Override
    public int CekStok() {
     int Cstok;
        Cstok = getStok()-getSatuan();
        return Cstok;
    }
    @Override
    public double HitTOtal(){
        return 0;
    }
}


