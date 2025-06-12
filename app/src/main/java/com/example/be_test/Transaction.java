package com.example.be_test;

public class Transaction {
    private int amount;
    private String description;
    private String date;

    private int type;

    // Constructor
    public Transaction(int amount, String description, String date, int type) {
        this.amount = amount;
        this.description = description;
        this.date = date;
        this.type = type;
    }
    // Getter untuk mengambil data
    public int getAmount() {
        return amount;
    }
    public String getDescription() {
        return description;
    }
    public String getDate() {
        return date;
    }
    public int getType() {
        return type;
    }
    @Override
    public String toString() {
        return "Jumlah: " + amount + "\nDeskripsi: " + description + "\nTanggal: " + date;
    }
}