package Domain.model;

public class Pung {
    private int balance;

    public Pung(){
    }

    public void setBalance(int modifier){
        balance = modifier;
    }

    public void addToBalance(int modifier){
        balance += modifier;
    }

    public int getBalance(){
        return balance;
    }
}
