package com.example.toyskingdom.model;

import com.google.gson.annotations.SerializedName;

public class DataProduct {
    @SerializedName("id")
    private String id;

    @SerializedName("nama_produk")
    private String nama_produk;

    @SerializedName("status")
    private String status;

    @SerializedName("harga_jual")
    private String harga_jual;

    @SerializedName("stok")
    private String stok;

    @SerializedName("kategori")
    private String kategori;

    @SerializedName("deskripsi")
    private String deskripsi;

    @SerializedName("foto")
    private String foto;

    public String getId() {
        return id;
    }

    public String getNama_produk() {
        return nama_produk;
    }

    public String getStatus() {
        return status;
    }

    public String getHarga_jual() {
        return harga_jual;
    }

    public String getStok() {
        return stok;
    }

    public String getKategori() {
        return kategori;
    }

    public String getDeskripsi() {
        return deskripsi;
    }

    public String getFoto() {
        return foto;
    }
}
