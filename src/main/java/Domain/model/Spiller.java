package Domain.model;

public class Spiller {

    private Pung account = new Pung(0);  //Opretter objekt reference variable til at skalbe et objekt fra klassen Pung og initialisere det
    private String name; // erklære et objekt reference variable som skaber et objekt fra String klassen, for at navnegive en spiller
    private int currentFieldId; //erlære en primitiv variable af typen int, til at angive spillerens nuværende position
    private boolean hasPrisonCard=false; //erklærer en primitiv variable af typen boolean som gemmer om spilleren har et ud af fængsel kort
    private boolean inPrison=false; // erlærer en primitiv variable af typen boolean, her gemmes om spilleren er i fængsel eller ej
    private boolean nextTurnVacantField = false; //erklærer en primitiv variable af typen boolean som gemmer om feltet ved spillerens nuværende position er ledigt
    ////////////////////////////////////////
    private boolean indkomstSkatFelt= false;
    private boolean StatsSkatFelt= false;
    ///////////////////////////////////////




    // oprettet en konstruktør der initialisere en spillers oplysninger
    public Spiller(String name, int pointStart, int id){
        this.account.setBalance(pointStart);
        this.name = name;
        this.currentFieldId=0;
    }
    //opretter en metode der retunere spillerens navn
    public String getName() {
        return name;
    }

    //opretter en metode der modtager spillerens navn og gemmer den i klassen attribut navn
    public void setName(String name) {
        this.name = name;
    }
    // opretter en metode der retunerer spillerens balance
    public int getAccountBalance(){
        return this.account.getBalance();
    }

    //opretter en metode der retunerer spillerens nuværende placerings id
    public int getCurrentFieldId() {
        return currentFieldId;
    }

    //opretter en metode der modtager og gemmer spilleren nuværende placerings id og gemmer den i den pågældende objekt(currentFieldId)
    public void setCurrentFieldId(int currentFieldId){
        this.currentFieldId=currentFieldId;
    }

    //opretter en metode der tilføjer spillerens en mængde til spillerens balance og gemmer den i account objektet
    public void deposit(int amount){
        this.account.deposit(amount);
    }

    //opretter en metode der trækker en mængde fra spillerens balance og gemmer den i account objektet
    public void withdraw(int amount){
        this.account.withdraw(amount);
    }

    //opretter en metoder der retunerer om spilleren har fængselkortet
    public boolean isHasPrisonCard() {
        return hasPrisonCard;
    }

    //opretter en metode der modtager om spilleren har fængselkortet og gemmer det i hasPrisonCard variablen
    public void setHasPrisonCard(boolean hasPrisonCard) {
        this.hasPrisonCard = hasPrisonCard;
    }

    //opretter en metode der retunerer om man er i fængsel
    public boolean isInPrison() {
        return inPrison;
    }

    //opretter en metode der modtager om spilleren er i fængsel og gemmer det i inPrison variablen
    public void setInPrison(boolean inPrison) {
        this.inPrison = inPrison;
    }

    // opretter en metode der retunerer om feltet ved spillerens nuværende placering er ejet
    public boolean isNextTurnVacantField() {
        return nextTurnVacantField;
    }

    //opretter en metode der modtager om feltet ved spillerens nuværende placering er ejet og gemmer det i nextTurnVacantField variablen
    public void setNextTurnVacantField(boolean nextTurnVacantField) {
        this.nextTurnVacantField = nextTurnVacantField;
    }

    //opretter en metode der retunerer spillerens acount objekt
    public Pung getAccount() {
        return this.account;
    }

   ///////////////////////////////////////////////////////////////
    public boolean isIndkomstSkatFelt() {
        return indkomstSkatFelt;
    }

    public void setIndkomstSkatFelt(boolean indkomstSkatFelt) {
        this.indkomstSkatFelt = indkomstSkatFelt;
    }

    public boolean isStatsSkatFelt() {
        return StatsSkatFelt;
    }

    public void setStatsSkatFelt(boolean statsSkatFelt) {
        StatsSkatFelt = statsSkatFelt;
    }
    /////////////////////////////////////////////////////////
}