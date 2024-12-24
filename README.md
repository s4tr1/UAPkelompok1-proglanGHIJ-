# Betta Fish Management System

## Deskripsi Proyek
Sistem manajemen ikan cupang ini memungkinkan admin untuk mengelola data ikan, seperti menambahkan, mengedit, dan menghapus informasi ikan beserta gambar terkait. Pembeli dapat melihat daftar ikan yang tersedia, membeli ikan menggunakan saldo mereka, serta melihat koleksi ikan yang telah dibeli. Data ikan yang ditambahkan oleh admin akan otomatis tersedia untuk pembeli.

---

## Fitur Utama
### Admin
- *Tambah Ikan:* Admin dapat menambahkan data ikan baru beserta gambar.
- *Hapus Ikan:* Admin dapat menghapus data ikan yang tidak lagi tersedia.
- *Edit Ikan:* Admin dapat mengedit data ikan yang ada.
- *Logout:* Admin dapat keluar dari sistem.

### Buyer
- *Tambah Saldo:* Buyer dapat menambahkan saldo untuk membeli ikan.
- *Beli Ikan:* Buyer dapat membeli ikan yang tersedia jika saldo mencukupi.
- *Lihat Koleksi:* Buyer dapat melihat daftar ikan yang telah dibeli.
- *Logout:* Buyer dapat keluar dari sistem.

---

## Cara Menggunakan

### 1. Login
- *Admin:* Gunakan kredensial berikut untuk login:
  - *Username:* admin
  - *Password:* admin
- *Buyer:* Anda dapat mendaftar sebagai pembeli melalui form registrasi.

### 2. Fungsi Admin
- Setelah login sebagai admin, Anda dapat:
  - Klik tombol Add Fish untuk menambahkan ikan baru.
  - Pilih ikan di tabel dan klik Delete Fish untuk menghapus ikan.
  - Klik tombol Logout untuk keluar dari sistem.

### 3. Fungsi Buyer
- Setelah login sebagai buyer, Anda dapat:
  - Klik tombol Add Balance untuk menambahkan saldo.
  - Pilih ikan di tabel dan klik Buy Fish untuk membeli ikan.
  - Klik tombol View Collection untuk melihat koleksi ikan yang telah dibeli.
  - Klik tombol Logout untuk keluar dari sistem.

---

##📁 Struktur Proyek
```
├── src
│   ├── AdminDashboard.java
│   ├── BuyerDashboard.java
│   ├── BettaFish.java
│   ├── Data.java
│   ├── LoginForm.java
│   ├── MainApp.java
│   ├── RegistrationForm.java
│   └── UserTable.java
├── users.txt (Berisi data user yang terdaftar)
└── README.md (Dokumentasi proyek ini)
```

## [![My Skills](https://skillicons.dev/icons?i=java,kotlin,nodejs,figma&theme=light)](https://skillicons.dev) Teknologi yang Digunakan
- *Java Swing* untuk antarmuka grafis.
- *JTable* untuk menampilkan data ikan.
- *JFileChooser* untuk memilih gambar ikan.
- *BufferedReader/BufferedWriter* untuk membaca dan menulis data pengguna.

---

## Prasyarat
- *Java Development Kit (JDK)* versi 8 atau lebih baru.
- IDE seperti IntelliJ IDEA, Eclipse, atau NetBeans untuk menjalankan proyek ini.

---

## Cara Menjalankan Proyek
1. Clone atau unduh repository proyek ini.
2. Buka proyek di IDE favorit Anda.
3. Jalankan file MainApp.java untuk memulai aplikasi.

---

## Pengembangan Lebih Lanjut
- Integrasi dengan database untuk penyimpanan data yang lebih aman.
- Penambahan autentikasi tingkat lanjut untuk keamanan login.
- Kemampuan untuk mencetak laporan data ikan atau pembelian.

---

## 👥 Kontributor
- *Nawaf SUlaiman Al Hunaiti, Bintang Mars Satria Tuhu*

---

## Lisensi
Proyek ini dilisensikan di bawah [MIT License](https://opensource.org/licenses/MIT).
