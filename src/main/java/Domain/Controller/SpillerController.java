package Domain.Controller;

import Domain.model.Spiller;

public class SpillerController {
    private Spiller[] players; //opretter et reference objekt af typen Array af klassen spiller
    private int numberOfPlayers;//opretter en primitiv variable af typen int der gemmer antal spiller

    //oprettet en konstruktør initialisere en spillers oplysninger
    public SpillerController(String[] playerNames){
        this.numberOfPlayers = playerNames.length;
        int startPoint=30000;
        players = new Spiller[numberOfPlayers];
        for (int i=0;i<numberOfPlayers;i++){
            players[i] = new Spiller(playerNames[i],startPoint,i);
        }
        }

    //opretter en metode af typen array der tildeler hver spiller en accountBalance
    public int[] getPlayerBalances(){
        int[] playerBalances = new int[numberOfPlayers];
        for (int i=0;i<numberOfPlayers;i++){
            playerBalances[i] = players[i].getAccountBalance();
        }
        return playerBalances;
    }
    //opretter en metode der retunere den nuværende spiller
    public Spiller[] getPlayers(){
        return this.players;
    }
    //opretter en metode der retunere en nuværende spiller, den nuværende felt spilleren står på og spillerens id
    public int getPlayerFieldId(int playerId){
        return this.players[playerId].getCurrentFieldId();
    }
    //opretter en metode der gemmer den nuværende spillers placering og id
    public void setPlayerFieldId(int playerId,int newPlayerFieldId){
        this.players[playerId].setCurrentFieldId(newPlayerFieldId);
    }
    //opretter en metode der retunere antal spillere
    public int getNumberOfPlayers() {
        return numberOfPlayers;
    }
    //opretter en metoder der tilføjer penge til en spiller
    public void addPointsToPlayer(int playerId, int amount){
        players[playerId].deposit(amount);
    }
    //opretter en metode der trækker penge fra en spiller
    public void takePointsFromPlayer(int playerid, int amount){
        players[playerid].withdraw(amount);
    }
    //opretter en metode der retunere om en spiller er i fængsel
    public boolean isPlayerInPrison(int playerID){
        return players[playerID].isInPrison();
    }
    //opretter en metode der gemmer om en spiller er i fængsel
    public void setPlayerInPrison(int playerID,boolean inPrison){
        players[playerID].setInPrison(inPrison);
    }
    //Returns true if transfer is sucessfull. Otherwise returns false and ends game.
    public boolean safeTransferToBank(int playerId,int amount){
        //Returns true if transfer is sucessfull. Otherwise returns false and ends game.
        boolean succes;
        if(amount<=players[playerId].getAccountBalance()){
            succes=true;
            takePointsFromPlayer(playerId,amount);
        } else {
            succes=false;
            takePointsFromPlayer(playerId,players[playerId].getAccountBalance());
        }
        return succes;
    }

    public boolean safeTransferToPlayer(int fromPlayerId, int amount, int toPlayerId){
        //Returns true if transfer is successful. Otherwise returns false and ends game.

        boolean succes;
        if(amount<=players[fromPlayerId].getAccountBalance()){
            succes=true;
            takePointsFromPlayer(fromPlayerId,amount);
            addPointsToPlayer(toPlayerId,amount);
        } else {
            succes=false;
            int lastAmount = players[fromPlayerId].getAccountBalance();
            takePointsFromPlayer(fromPlayerId,lastAmount);
            addPointsToPlayer(toPlayerId,lastAmount);
        }
        return succes;
    }

    //opretter en metode der retunere om en spiller står på isIndkomstSkatFeltet
    public boolean isIndkomstSkatFelt (int playerID){
        return players[playerID].isIndkomstSkatFelt();
    }
    //opretter en metoder der gemmer om en spiller står på indkomstSkatsFelt
    public void setIndkomstSkatFelt(int playerID, boolean indkomstSkatFelt){
        players[playerID].setIndkomstSkatFelt(indkomstSkatFelt);
    }
    //opretter en metode der retunere om en spiller står på StatsSkatsFeltet
    public boolean isStatsSkatFelt (int playerID){
        return players[playerID].isStatsSkatFelt();
    }
    //opretter en metoder der gemmer om en spiller står på StatsSkatFeltet
    public void setStatsSkatFelt(int playerID, boolean statsSkatFelt){
        players[playerID].setStatsSkatFelt(statsSkatFelt);
    }

}
