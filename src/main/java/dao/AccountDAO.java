package dao;

import model.Account;
import db.DBConnection;
import model.Transaction;

import java.sql.ResultSet;
import java.sql.*;
import java.time.LocalDateTime;

public class AccountDAO {
    public void createAccount(Account account) {
        Connection con = DBConnection.getConnection();

        try {
            String sql = "insert into accounts(account_no ,name,pin,balance,phone) values(?,?,?,?,?) ";
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setLong(1, account.getAccount_no());
            ps.setString(2, account.getName());
            ps.setInt(3, account.getPin());
            ps.setDouble(4, account.getBalance());
            ps.setString(5, account.getPhone());
            ps.addBatch();
            int rows[]=ps.executeBatch();
            if(rows.length>0)
            {
                System.out.println("Account create successfully");
            }
            else{
                System.out.println("Account creation failed");
            }
            ps.close();
            con.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public boolean login(long account_no,int pin){
    Connection con = DBConnection.getConnection();
    try{
        String sql="select * from accounts where account_no=? and pin=?";
        PreparedStatement ps=con.prepareStatement(sql);
        ps.setLong(1,account_no);
        ps.setInt(2,pin);
        ResultSet rs= ps.executeQuery();
        if(rs.next()){
            System.out.println("Login successful");
            return true;
        }
        else{
            System.out.println("Invalid accountNo and pin");
        }
        ps.close();
        rs.close();
        con.close();
    } catch (Exception e) {
        e.printStackTrace();
    }
    return false;
}
public void checkbalance(long account_no){
    Connection con = DBConnection.getConnection();

    try{
        String sql="select balance from accounts where account_no=?";
        PreparedStatement ps=con.prepareStatement(sql);
        ps.setLong(1,account_no);
        ResultSet rs= ps.executeQuery();
        if(rs.next()) {
            System.out.println("current Balance="+rs.getDouble(1));
        }
        else{
            System.out.println("Account not found");
        }
        ps.close();
        rs.close();
        con.close();
    } catch (Exception e) {
        e.printStackTrace();
    }
}
    public void deposite(Connection con,long account_no,double amount){

        try{
            String sql="update accounts set balance=balance + ? where account_no=?";
            PreparedStatement ps=con.prepareStatement(sql);
            ps.setDouble(1,amount);
            ps.setLong(2,account_no);
            ps.addBatch();
            int rows[]=ps.executeBatch();
            if(rows.length>0){
                System.out.println("Amount Deposited successfully");
                Transaction transaction=new Transaction();
                transaction.setAccount_no(account_no);
                transaction.setType("Deposite");
                transaction.setAmount((int)amount);
transaction.setDatetime(LocalDateTime.now());
                TransactionDAO transactionDAO=new TransactionDAO();
                transactionDAO.addTransaction(transaction);
            }
            else{
                System.out.println("Deposite Failed");
            }
            ps.close();


        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void withdraw(Connection con,long account_no,double amount) {

        try {
            String sql = "select balance from accounts where account_no=?";

            PreparedStatement ps = con.prepareStatement(sql);
            ps.setLong(1, account_no);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                double balance = rs.getDouble("balance");
                if (balance >= amount) {
                    String updatesql = "update accounts set balance=balance-? where account_no=?";
                    PreparedStatement ps2 = con.prepareStatement(updatesql);
                    ps2.setDouble(1, amount);
                    ps2.setLong(2, account_no);
                    int row = ps2.executeUpdate();
                    if (row > 0) {
                        System.out.println("Withdraw successfully");
                        Transaction transaction=new Transaction();
                        transaction.setAccount_no(account_no);
                        transaction.setType("Deposite");
                        transaction.setAmount((int)amount);
                        transaction.setDatetime(LocalDateTime.now());
                        TransactionDAO transactionDAO=new TransactionDAO();
                        transactionDAO.addTransaction(transaction);
                    } else {
                        System.out.println("Withdraw Failed");
                    }
                    ps2.close();
                } else {
                    System.out.println("Insufficient balance !!!");
                }
            } else {
                System.out.println("Account Not Found");
            }
            rs.close();
            ps.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
public void transfer(Connection con,long account_no,long Reciever_account_no,double amount){

      try {
          String sql = "select balance from accounts where account_no=?";
          PreparedStatement ps = con.prepareStatement(sql);
          ps.setLong(1,account_no);
          ResultSet rs= ps.executeQuery();
          if(rs.next()) {
              double balance = rs.getDouble("balance");
              if (balance >= amount) {
                  String sql2 = "select *from accounts where account_no=?";
                  PreparedStatement ps2 = con.prepareStatement(sql2);
                  ps2.setLong(1, Reciever_account_no);
                  ResultSet rs2 = ps2.executeQuery();
                  if (rs2.next()) {
                      String sql3 = "update accounts set balance=balance-?where account_no=?";
                      PreparedStatement ps3 = con.prepareStatement(sql3);
                      ps3.setDouble(1, amount);
                      ps3.setLong(2, account_no);
                      ps3.executeUpdate();
                      String sql4 = "update accounts set balance=balance+?where account_no=?";
                      PreparedStatement ps4 = con.prepareStatement(sql4);
                      ps4.setDouble(1, amount);
                      ps4.setLong(2, Reciever_account_no);
                      ps4.executeUpdate();
                      Transaction transaction=new Transaction();
                      transaction.setAccount_no(account_no);
                      transaction.setType("Transfer");
                      transaction.setAmount((int)amount);
                      transaction.setDatetime(LocalDateTime.now());
                      TransactionDAO transactionDAO=new TransactionDAO();
                      transactionDAO.addTransaction(transaction);
                      //REciever transaction
                      Transaction transaction2=new Transaction();
                      transaction2.setAccount_no(Reciever_account_no);
                      transaction2.setType("Recieved");
                      transaction2.setAmount((int)amount);
                      transaction2.setDatetime(LocalDateTime.now());

                      transactionDAO.addTransaction(transaction2);
                      System.out.println("Transfer succeessfull");

                      ps4.close();
                      ps3.close();
                  } else {
                      System.out.println("Reciever Account not Found");

                  }
                  rs2.close();
                  ps2.close();

              } else {
                  System.out.println("Insufficient Balance");
              }
          }
          else{
              System.out.println("Sender not found");
          }
          ps.close();
          rs.close();

      } catch (SQLException e) {
          e.printStackTrace();
      }
}
public void transactionHistory(long account_no){
        Connection con=DBConnection.getConnection();
        try {
            String sql = "select * from transactions where account_no=? order by transaction_id desc";
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setLong(1,account_no);
            ResultSet rs=ps.executeQuery();
            if(!rs.next()) {
                System.out.println("No Transaction Found");
            }
            else {
                do {
                    System.out.println("_______");
                    System.out.println("TransactionId :" + rs.getInt("transaction_id"));
                    System.out.println("Type :" + rs.getString("type"));
                    System.out.println("Amount :" + rs.getInt("amount"));
                    System.out.println("Date & Time :" + rs.getTimestamp("date_time"));
                } while (rs.next());
            }
            rs.close();
            ps.close();
            con.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
}
public void deleteAccount(long account_no,int pin){
    Connection con=  DBConnection.getConnection();
    try{
        String sql="Select balance from accounts where account_no=? and pin=?";

        PreparedStatement ps=con.prepareStatement(sql)   ;
        ps.setLong(1,account_no);
        ps.setInt(2,pin);
        ResultSet rs=ps.executeQuery();
        if(rs.next()){
            double balance=rs.getDouble("balance");
            if(balance>0){
                System.out.println("Plz Withdraw all balance");
            }
            else{
                String sql2="delete from accounts where account_no=?";
                PreparedStatement ps2=con.prepareStatement(sql2);
                ps2.setLong(1,account_no);
                int row=ps2.executeUpdate();
                if(row>0){
                    System.out.println("Account Deleted successfully");
                }
                else{
                    System.out.println("Account Deletion Failed ");
                }
                ps2.close();
            }

        }
        else {
            System.out.println("Invalid AccountNo or Pin");
        }
        ps.close();
        rs.close();
        con.close();
} catch (Exception e) {
        e.printStackTrace();
    }
}
public  void changePin(long account_no,int Current_pin,int new_pin){
        Connection con=DBConnection.getConnection();
        try {
            String sql="update accounts set pin=? where account_no=? and pin=? ";
            PreparedStatement ps=con.prepareStatement(sql);
            ps.setInt(1,new_pin);
            ps.setLong(2,account_no);
            ps.setInt(3,Current_pin);
            int row=ps.executeUpdate();
            if(row>0){
                System.out.println("Pin Changed successfully");
            }
            else{
                System.out.println("Pin change failed");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
}



}
//update accounts set balance=balance-? where account_no=?