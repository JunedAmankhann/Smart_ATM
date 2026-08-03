package model;

public class Account {
   private long account_no;
    private  String Phone;
    private String name;
    private int pin;
    private double balance;
    public Account(){}


    public long getAccount_no() {
        return account_no;
    }

    public void setAccount_no(long account_no) {
        this.account_no = account_no;
    }

    public double getBalance() {
        return balance;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return Phone;
    }

    public void setPhone(String phone) {
        Phone = phone;
    }

    public int getPin() {
        return pin;
    }

    public void setPin(int pin) {
        this.pin = pin;
    }

    public Account(long account_no, String Phone, String name, int pin, double balance){
        this.account_no=account_no;
        this.Phone=Phone;
        this.name=name;
        this.pin=pin;
        this.balance=balance;

    }
}
