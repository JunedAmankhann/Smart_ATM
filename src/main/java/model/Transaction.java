package model;




import java.time.LocalDateTime;

public class Transaction {
    private int transactionId;
    private long account_no;
    private String type;
    private  int amount;
private LocalDateTime datetime;

    public Transaction(){

    }
    public Transaction(int transactionId,long account_no,String type,int amount){
        this.account_no=account_no;
        this.transactionId=transactionId;
        this.amount=amount;
        this.type=type;
this.datetime=datetime;

    }

    public long getAccount_no() {
        return account_no;
    }

    public void setAccount_no(long account_no) {
        this.account_no = account_no;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public LocalDateTime getDatetime() {
        return datetime;
    }

    public void setDatetime(LocalDateTime datetime) {
        this.datetime = datetime;
    }

    public int getTransactionId() {
        return transactionId;
    }

    public void setTransactionId(int transactionId) {
        this.transactionId = transactionId;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}