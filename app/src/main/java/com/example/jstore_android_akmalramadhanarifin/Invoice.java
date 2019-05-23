package com.example.jstore_android_akmalramadhanarifin;

public class Invoice {
    private int id;
    private int totalPrice;
    private String date;
    private boolean isActive;
    private Customer customer;
    private String item;
    private String invoiceStatus;
    private String invoiceType;
    private int installmentPeriod;
    private String dueDate;
    private int installmentPrice;

    //Invoice installment
    public Invoice(int id, int totalPrice, String date, boolean isActive, Customer customer, String item, String invoiceStatus, String invoiceType, int installmentPeriod, int installmentPrice) {
        this.id = id;
        this.totalPrice = totalPrice;
        this.date = date;
        this.isActive = isActive;
        this.customer = customer;
        this.item = item;
        this.invoiceStatus = invoiceStatus;
        this.invoiceType = invoiceType;
        this.installmentPeriod = installmentPeriod;
        this.installmentPrice = installmentPrice;
    }

    //Invoice paid
    public Invoice(int id, int totalPrice, String date, boolean isActive, Customer customer, String item, String invoiceStatus, String invoiceType) {
        this.id = id;
        this.totalPrice = totalPrice;
        this.date = date;
        this.isActive = isActive;
        this.customer = customer;
        this.item = item;
        this.invoiceStatus = invoiceStatus;
        this.invoiceType = invoiceType;
    }

    //Invoice unpaid
    public Invoice(int id, int totalPrice, String date, boolean isActive, Customer customer, String item, String invoiceStatus, String invoiceType, String dueDate) {
        this.id = id;
        this.totalPrice = totalPrice;
        this.date = date;
        this.isActive = isActive;
        this.customer = customer;
        this.item = item;
        this.invoiceStatus = invoiceStatus;
        this.invoiceType = invoiceType;
        this.dueDate = dueDate;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(int totalPrice) {
        this.totalPrice = totalPrice;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public boolean isActive() {
        return isActive;
    }

    public void setActive(boolean active) {
        isActive = active;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public String getItem() {
        return item;
    }

    public void setItem(String item) {
        this.item = item;
    }

    public String getInvoiceStatus() {
        return invoiceStatus;
    }

    public void setInvoiceStatus(String invoiceStatus) {
        this.invoiceStatus = invoiceStatus;
    }

    public String getInvoiceType() {
        return invoiceType;
    }

    public void setInvoiceType(String invoiceType) {
        this.invoiceType = invoiceType;
    }

    public int getInstallmentPeriod() {
        return installmentPeriod;
    }

    public void setInstallmentPeriod(int installmentPeriod) {
        this.installmentPeriod = installmentPeriod;
    }

    public String getDueDate() {
        return dueDate;
    }

    public void setDueDate(String dueDate) {
        this.dueDate = dueDate;
    }

    public int getInstallmentPrice() {
        return installmentPrice;
    }

    public void setInstallmentPrice(int installmentPrice) {
        this.installmentPrice = installmentPrice;
    }
}
