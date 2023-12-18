/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package PraktikumOOP;

import javax.swing.JTextField;

/**
 *
 * @author Naufal whildan
 */
public class Pembayaran extends CLI_Bahan implements ModalBalik {
    //Atribut Pembayaran
public String namaP,namaK,metode;
private int Diskon; 
    //Setter dan Getter
        public void setDiskon(int Diskon) {
        this.Diskon = Diskon;
    }
        public int getDiskon() {
        return Diskon;
    }
    //Constructornya
        public void datanamapembeli(String namap){
        this.namaP = namap ;
    }
        public void datanamakasir(String namak){
        this.namaK = namak;
    }
        public String cetaknamapembeli(){
        return namaP;
        }
        public String cetaknamakasir(){
        return namaK;
    }
        public void datametode(String metode){
        this.metode = metode;
    }
    public String cetakmetode(){
        return metode;
    }
    //Overriding    

    @Override
    public double HitTOtal() {
        double pajak = 0;
        pajak = Harga*0.1;
        return pajak;
    }
    //Overloading
    public int cetakTotal(){
        return Harga;
    }
    public int cetakTotal(int Harga,int Diskon){
        int total;
        total = Harga-Diskon;
        return total;
    }
    @Override
    public int CekStok(){
        return 0;
    }
    //Method Dari Interface Berupa Keuntungan
    @Override
    public double Keuntungan() {
        double Untung;
        Untung = Harga*0.2;
        return Untung;
    }
}
