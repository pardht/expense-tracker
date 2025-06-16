package com.example.be_test;

public class Transaction {
    private int amount, id, type;
    private String description, date;

    // Constructor
    public Transaction(int id, int amount, String description, String date, int type) {
        this.id = id;
        this.amount = amount;
        this.description = description;
        this.date = date;
        this.type = type;
    }
    // Getter untuk mengambil data
    public int getId() {
        return id;

    }public int getAmount() {
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
        return "Jumlah: " + amount + "\nDeskripsi: "
                + description + "\nTanggal: " + date + "\ntipe: " + type
                + "\nid: " + id;
    }
}