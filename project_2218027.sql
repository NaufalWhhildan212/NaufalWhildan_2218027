-- phpMyAdmin SQL Dump
-- version 5.2.0
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1
-- Waktu pembuatan: 18 Des 2023 pada 07.46
-- Versi server: 10.4.24-MariaDB
-- Versi PHP: 8.1.6

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `project_2218027`
--

-- --------------------------------------------------------

--
-- Struktur dari tabel `tb_datapembeli`
--

CREATE TABLE `tb_datapembeli` (
  `Id` int(11) NOT NULL,
  `Nama_Pembeli` varchar(15) NOT NULL,
  `Alamat` varchar(15) NOT NULL,
  `No_telepon` varchar(20) NOT NULL,
  `Jenis_bahan` varchar(20) NOT NULL,
  `Nama_bahan` varchar(20) NOT NULL,
  `Jumlah_pembelian` int(20) NOT NULL,
  `Total_pembelian` int(20) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data untuk tabel `tb_datapembeli`
--

INSERT INTO `tb_datapembeli` (`Id`, `Nama_Pembeli`, `Alamat`, `No_telepon`, `Jenis_bahan`, `Nama_bahan`, `Jumlah_pembelian`, `Total_pembelian`) VALUES
(1, 'Naufal', 'Pasuruan', '97593', 'Pelapis', 'Asbes', 6, 10000);

-- --------------------------------------------------------

--
-- Struktur dari tabel `tb_pembayaran`
--

CREATE TABLE `tb_pembayaran` (
  `Id` int(255) NOT NULL,
  `Nama_Pembeli` varchar(15) NOT NULL,
  `Nama_Kasir` varchar(15) NOT NULL,
  `Metode_Pembayaran` varchar(15) NOT NULL,
  `Diskon` int(20) NOT NULL,
  `Harga_Barang` int(20) NOT NULL,
  `Total` int(20) NOT NULL,
  `Pajak` int(20) NOT NULL,
  `Laba` int(20) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data untuk tabel `tb_pembayaran`
--

INSERT INTO `tb_pembayaran` (`Id`, `Nama_Pembeli`, `Nama_Kasir`, `Metode_Pembayaran`, `Diskon`, `Harga_Barang`, `Total`, `Pajak`, `Laba`) VALUES
(3, 'Naufal', 'Candra', 'transfer', 500, 30000, 30000, 3000, 6000);

--
-- Indexes for dumped tables
--

--
-- Indeks untuk tabel `tb_datapembeli`
--
ALTER TABLE `tb_datapembeli`
  ADD PRIMARY KEY (`Id`);

--
-- Indeks untuk tabel `tb_pembayaran`
--
ALTER TABLE `tb_pembayaran`
  ADD PRIMARY KEY (`Id`);

--
-- AUTO_INCREMENT untuk tabel yang dibuang
--

--
-- AUTO_INCREMENT untuk tabel `tb_datapembeli`
--
ALTER TABLE `tb_datapembeli`
  MODIFY `Id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=2;

--
-- AUTO_INCREMENT untuk tabel `tb_pembayaran`
--
ALTER TABLE `tb_pembayaran`
  MODIFY `Id` int(255) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=4;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
