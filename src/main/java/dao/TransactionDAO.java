package dao;

import db.DBConnection;
import model.Transaction;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.Timestamp;
import java.time.LocalDateTime;

public class TransactionDAO {
public void addTransaction(Transaction transaction){
Connection con=DBConnection.getConnection();
try{
    String sql="insert into transactions(account_no,type,amount,date_time)values(?,?,?,?)";
    PreparedStatement ps=con.prepareStatement(sql);
    ps.setLong(1,transaction.getAccount_no());
    ps.setString(2, transaction.getType());
    ps.setInt(3,transaction.getAmount());
ps.setTimestamp(4,Timestamp.valueOf(transaction.getDatetime()));
    int row=ps.executeUpdate();
    if(row>0){
        System.out.println("Transaction saved Successfully");
    }
    else{
        System.out.println("Transaction Failed");
        //kdfdgouiwvrfhjo

    }
    ps.close();
    con.close();
} catch (Exception e) {
    e.printStackTrace();
}
}
}
