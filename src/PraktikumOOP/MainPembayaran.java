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
public class MainPembayaran {
    public static void main(String[] args) {
       
 Pembayaran pmb = new Pembayaran();
    System.out.println("Metode Pembayaran Bahan Bangunan ");
    System.out.println("------------------------------------");
    System.out.println("Nama Pembeli: " + pmb.cetaknamapembeli()+"\n"); 
    System.out.println("Nama Kasir : "+ pmb.cetaknamakasir()+"\n"); 
    System.out.println("Diskon: "+ pmb.getDiskon()+"\n"); 
    System.out.println("Total Harga: "+ pmb.cetakTotal()+"\n"); 
}

}
