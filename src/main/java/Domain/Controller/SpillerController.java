package Domain.Controller;

import Domain.model.Spiller;

public class SpillerController {
    private Spiller[] players;
    private int numberOfPlayers;

    public SpillerController(String[] playerNames){
        this.numberOfPlayers = playerNames.length;
        int startPoint=30000;
        players = new Spiller[numberOfPlayers];
        for (int i=0;i<numberOfPlayers;i++){
            players[i] = new Spiller(playerNames[i],startPoint,i);
        }
        }


    public int[] getPlayerBalances(){
        int[] playerBalances = new int[numberOfPlayers];
        for (int i=0;i<numberOfPlayers;i++){
            playerBalances[i] = players[i].getAccountBalance();
        }
        return playerBalances;
    }

    public Spiller[] getPlayers(){
        return this.players;
    }

    public int getPlayerFieldId(int playerId){
        return this.players[playerId].getCurrentFieldId();
    }

    public void setPlayerFieldId(int playerId,int newPlayerFieldId){
        this.players[playerId].setCurrentFieldId(newPlayerFieldId);
    }

    public int getNumberOfPlayers() {
        return numberOfPlayers;
    }

    public void addPointsToPlayer(int playerId, int amount){
        players[playerId].deposit(amount);
    }

    public void takePointsFromPlayer(int playerid, int amount){
        players[playerid].withdraw(amount);
    }

    public boolean isPlayerInPrison(int playerID){
        return players[playerID].isInPrison();
    }

    public void setPlayerInPrison(int playerID,boolean inPrison){
        players[playerID].setInPrison(inPrison);
    }

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

    /////////////////////////////////////////////////////////
    public boolean isIndkomstSkatFelt (int playerID){
        return players[playerID].isIndkomstSkatFelt();
    }

    public void setIndkomstSkatFelt(int playerID, boolean indkomstSkatFelt){
        players[playerID].setIndkomstSkatFelt(indkomstSkatFelt);
    }

    public boolean isStatsSkatFelt (int playerID){
        return players[playerID].isStatsSkatFelt();
    }

    public void setStatsSkatFelt(int playerID, boolean statsSkatFelt){
        players[playerID].setStatsSkatFelt(statsSkatFelt);
    }
    //////////////////////////////////////////////////////////////

}
