package Domain.model;

public class Pung {
    private int balance; //Indholder spillerens konto balance

    public Pung(int balance) {
        this.balance = balance;
    } //Initialisere spillernes balancer

    public void withdraw(int amount) {
        balance = balance - amount;
    } // Trækker fra spillernes balancer

    public void deposit(int amount) {
        balance = balance + amount;
    } // Tilfører til spillernes balancer

    public int getBalance() {
        return balance;
    } // Viser spillernes balancer

    public void setBalance(int balance) {
        this.balance = balance;
    } // Modtager ændringer på spillernes balancer
}
