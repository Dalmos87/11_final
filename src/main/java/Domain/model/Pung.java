package Domain.model;

public class Pung {
    private int balance;

    public Pung(int balance) {
        this.balance = balance;
    }

    public void withdraw(int amount) {
        balance = balance - amount;
    }

    public void deposit(int amount) {
        balance = balance + amount;
    }

    public int getBalance() {
        return balance;
    }

    public void setBalance(int balance) {
        this.balance = balance;
    }
}
