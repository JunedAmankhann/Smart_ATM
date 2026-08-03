package main;
import dao.AccountDAO;
import db.DBConnection;
import model.Account;

import java.sql.Connection;
import java.sql.Savepoint;
import java.util.Scanner;

public class main {
    public static void main(String[] args) {
        Connection con = DBConnection.getConnection();
        Scanner sc = new Scanner(System.in);

        System.out.println("1.Register");
        System.out.println("2.Login");
        System.out.println("3.Exit");
        int choice = sc.nextInt();
        AccountDAO accountDAO = new AccountDAO();
        switch (choice) {
            case 1:
                registerAccount(sc, accountDAO);
                break;
            case 2:
                login(sc,accountDAO);
                break;
        }
    }
            public static void registerAccount(Scanner sc,AccountDAO accountDAO){
                System.out.println("Enter Your AccountNo");
                Long account_no = sc.nextLong();
                System.out.println("Enter your Name");
                String name = sc.next();
                System.out.println("Enter Pin");
                int pin = sc.nextInt();
                System.out.println("Enter Balance");
                Double balance = sc.nextDouble();
                System.out.println("Enter Phone_No");
                String phone = sc.next();

                Account account = new Account();
                account.setAccount_no(account_no);
                account.setName(name);
                account.setBalance(balance);
                account.setPin(pin);
                account.setPhone(phone);



                accountDAO.createAccount(account);

}
    public static void login(Scanner sc,AccountDAO accountDAO) {
        System.out.println("Enter your AccountNo:");
        long account_no = sc.nextLong();
        System.out.println("Enter Pin");
        int pin = sc.nextInt();

        if(accountDAO.login(account_no,pin)){
            Atm_menu(sc,accountDAO,account_no);
        }
        else {
            System.out.println("Invalid details");
        }
    }
    public static void Atm_menu(Scanner sc,AccountDAO accountDAO,long account_no){
        System.out.println("Enter 1 Checkbalance");
        System.out.println("Enter 2 Deposite");
        System.out.println("Enter 3  Withdraw");
        System.out.println("Enter 4 Transfer");
        System.out.println("Enter 5 TransactionHistory");
        System.out.println("Enter 6 Delete_Account");
        System.out.println("Enter 7 ChangePin");

        int choice=sc.nextInt();
        switch (choice){
            case 1:
               accountDAO.checkbalance(account_no);
               break;
               case 2:
                   deposite(sc,accountDAO,account_no);
                   break;
            case 3:
                withdraw(sc,accountDAO,account_no);
                break;
            case 4:
                transfer(sc,accountDAO,account_no);
                break;
            case 5:
                transactionHistory(accountDAO,account_no);
                break;
            case 6:
                System.out.println("Enter the Pin:");
                int pin=sc.nextInt();

                deleteAccount(accountDAO,account_no,pin);
                break;
            case 7:
                System.out.println("Enter current pin");
                int Current_pin=sc.nextInt();
                System.out.println("Enter New pin");
                int new_pin=sc.nextInt();

                changePin(accountDAO,account_no,Current_pin,new_pin);
                break;
            case 8:

            default:
                System.out.println("Invalid Choice");
        }

    }
    public static void deposite(Scanner sc,AccountDAO accountDAO,long account_no) {
        try {

            Connection con = DBConnection.getConnection();
            con.setAutoCommit(false);
            System.out.println("Enter Deposite Amount");
            double amount = sc.nextDouble();
            accountDAO.deposite(con,account_no, amount);
            System.out.println("Enter 1 COMMIT");
            System.out.println("Enter 2 ROLLBACK");
            int choice = sc.nextInt();
            if (choice == 1) {
                con.commit();
                System.out.println("Transaction commit successfully");
            }
            else{
                con.rollback();
                System.out.println("Transaction rollback successfully");
            }
            con.close();
        } catch(Exception e) {
            e.printStackTrace();

        }
    }
    public static void withdraw(Scanner sc,AccountDAO accountDAO,long account_no) {
        try {

            Connection con=DBConnection.getConnection();
            con.setAutoCommit(false);
            System.out.println("Enter Withdraw Amount");
            double amount = sc.nextDouble();
            accountDAO.withdraw(con,account_no, amount);
            System.out.println("Enter 1 COMMIT");
            System.out.println("Enter 2 ROLLBACK");
            int choice = sc.nextInt();
            if (choice == 1) {
                con.commit();
                System.out.println("Transaction commit successfully");
            }
            else{
                con.rollback();
                System.out.println("Transaction rollback successfully");
            }
            con.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public static void transfer(Scanner sc,AccountDAO accountDAO,long account_no) {
        try {
            Connection con=DBConnection.getConnection();
            con.setAutoCommit(false);
            System.out.println("Enter Reciever accountNO");
            long Reciever_account_no = sc.nextLong();
            System.out.println("Enter THe amount");
            double amount = sc.nextDouble();
            accountDAO.transfer(con,account_no, Reciever_account_no, amount);
            Savepoint sp=con.setSavepoint("Transfer");
            System.out.println("Enter 1. COMMIT");
            System.out.println("Enter 2. ROLLBACK");
            System.out.println("Enter 3. Savepoint");

            int choice = sc.nextInt();
            switch (choice){
                case 1:
                con.commit();
                    System.out.println("Transaction committed successfully");
                    break;
                case 2:
                    con.rollback();
                    System.out.println("Transaction Rollbacked successfully");
                    break;
                case 3:
                    con.rollback(sp);
                    con.commit();
                    System.out.println("Transaction Rollbacked to Savepoint");
            break;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public static void transactionHistory(AccountDAO accountDAO,long account_no){
        System.out.println("History");
        accountDAO.transactionHistory(account_no);
    }

    public static void deleteAccount(AccountDAO accountDAO,long account_no,int pin){

        accountDAO.deleteAccount(account_no,pin);
    }
    public  static void changePin(AccountDAO accountDAO,long account_no,int Current_pin,int new_pin){
        accountDAO.changePin(account_no,Current_pin,new_pin);
    }


    }