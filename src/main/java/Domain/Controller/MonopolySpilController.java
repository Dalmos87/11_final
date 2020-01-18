package Domain.Controller;

import Domain.felt.Skød;
import Domain.model.Raflebæger;
import View.GUIController;

import java.util.Random;

public class MonopolySpilController {

        private Raflebæger raflebæger;
        private boolean gameOver = false;
        private int startBonus=4000;
        private SpillePladeController boardController;
        private GUIController guiController;
        private PLKB_Controller ccd_controller;


        private SpillerController playerController;

        public MonopolySpilController() {
            this.boardController = new SpillePladeController();
            this.raflebæger = new Raflebæger();
        }

        public SpillePladeController getBoardController() {
            return this.boardController;
        }

        public PLKB_Controller getCcd_controller() {
            return ccd_controller;
        }



        public void setupPlayerController(String[] playerNames){
            this.playerController = new SpillerController(playerNames);
            ccd_controller = new PLKB_Controller(playerNames.length);
        }

        public void setPlayerController(SpillerController playerController) {
            this.playerController = playerController;
        }

        public SpillerController getPlayerController() {
            return playerController;
        }

        public int movePlayerCar(int dieRoll, int playerId){
            //Gets the number of fields on the board
            int numberOfFields = boardController.getGameBoard().getFields().length;

            //Gets old fieldID and calculates the new one
            int oldFieldId = playerController.getPlayerFieldId(playerId);
            int newFieldId = (oldFieldId + dieRoll)%numberOfFields;


            //sets player on the new field
            playerController.setPlayerFieldId(playerId,newFieldId);

            return newFieldId;
        }

        public int newBalanceAfterRoll(int playerId, int oldFieldId, int dieRoll){
            //If player passes start, adds 4000kr. to players balance
            if (oldFieldId+dieRoll>boardController.getNames().length-1){
                this.playerController.addPointsToPlayer(playerId,startBonus);
            }

            return this.playerController.getPlayerBalances()[playerId];
        }

        public int getRoll(){
            //Rolls the die
            raflebæger.Roll();
            int roll = raflebæger.face_sum();

            return roll;


        }

        public int getNumberOfPlayers(){
            return playerController.getNumberOfPlayers();
        }

        //////////////////////////////////////////////////////////////////////////////
        public boolean isIndkomstSkatFelt(int playerID){
            return playerController.isIndkomstSkatFelt(playerID);
        }

        public void setIndkomstSkatFelt(int playerID, boolean indkomstSkat){
            playerController.setIndkomstSkatFelt(playerID,indkomstSkat);
        }

        public boolean isStatsSkatFelt(int playerID){
            return playerController.isStatsSkatFelt(playerID);
        }

        public void setStatsSkatFelt(int playerID, boolean statsSkatFelt){
            playerController.setStatsSkatFelt(playerID,statsSkatFelt);
        }

        ////////////////////////////////////////////////////////////////////////////




        public boolean isPlayerInPrison(int playerID){
            return playerController.isPlayerInPrison(playerID);

        }

        public void setPlayerInPrison(int playerID,boolean inPrison){
            playerController.setPlayerInPrison(playerID,inPrison);
        }



        /////////////////////////////////////////////////////////////////////////
        public String indkomstskatFelt(int activePlayerID){
            String msg1="";
            String activePlayerName1 =playerController.getPlayers()[activePlayerID].getName();
            if (isIndkomstSkatFelt(activePlayerID)) {
                boolean landPå =playerController.safeTransferToBank(activePlayerID,2000);
                if (landPå){

                    msg1 = activePlayerName1 + "Man Skal betal 2000kr.\n";}
                setIndkomstSkatFelt(activePlayerID,false);
            }
            return msg1;
        }

        public String statsSkatFelt(int activePlayerID){
            String msg1="";
            String activePlayerName1 =playerController.getPlayers()[activePlayerID].getName();
            if (isStatsSkatFelt(activePlayerID)) {
                boolean landPå =playerController.safeTransferToBank(activePlayerID,1000);
                if (landPå){

                    msg1 = activePlayerName1 + "Man Skal betal 1000kr.\n";}
                setIndkomstSkatFelt(activePlayerID,false);
            }
            return msg1;
        }
        ////////////////////////////////////////////////////////////////////////////////

        public String executePlayerInPrison(int activePlayerId){
            //Checks if player is in prison. If true it checks if the player has a "Prison chancecard", else it withdraw 1 points from playercard
            String msg="";
            String activePlayerName =playerController.getPlayers()[activePlayerId].getName();
            if(isPlayerInPrison(activePlayerId)){
                msg = activePlayerName + " er i fængsel.\n";
                if(getPlayerController().getPlayers()[activePlayerId].isHasPrisonCard()){
                    getPlayerController().getPlayers()[activePlayerId].setHasPrisonCard(false);
                    msg += activePlayerName +" bruger sit løsladelseskort til at forlade fængslet uden bøde.";
                }
                else{     //He doesnt have the card
                    boolean canAfford =playerController.safeTransferToBank(activePlayerId,1000);
                    if (canAfford){
                        msg += activePlayerName + " betaler 1000kr i bøde og forlader fængslet.";
                    } else{
                        msg+= activePlayerName + " har ikke råd til at betale bøden.";
                        gameOver=true;
                    }


                }
                setPlayerInPrison(activePlayerId,false);

            }
            return msg;
        }

    public String landedOnProperty(int playerId, int fieldId,boolean free){
        //Input: playerId, fieldId and "free"-boolean.
        //free is only true, if the player lands on the property with a special chancecard and get it for free
        //Returns a message that displays what happens, and updates the status of balances and so on automatically

        //Checks who, if anyone, owns the field
        int propertyOwnerId = boardController.getPropertiesOwnedByIds()[fieldId];
        int activePlayerBalance = playerController.getPlayerBalances()[playerId];
        int propertyPrice = boardController.getGameBoard().getFields()[fieldId].getPrice();
        String propertyOwnerName="Ingen";
        if(propertyOwnerId>=0){
            propertyOwnerName = playerController.getPlayers()[propertyOwnerId].getName();
        }
        String propertyName = boardController.getGameBoard().getFields()[fieldId].getName();
        String activePlayerName = playerController.getPlayers()[playerId].getName();
        String msg="";
        boolean canAfford;

        if (playerId==propertyOwnerId){ //If the player owns it himself
            msg+=activePlayerName+" ejer selv " + propertyName +".";
        }
        else if(propertyOwnerId>=0){ //If it is owned by someone else
            msg+= propertyOwnerName + " ejer " + propertyName + ".\n";
            canAfford = playerController.safeTransferToPlayer(playerId,propertyPrice,propertyOwnerId);
            if (canAfford){
                msg+= activePlayerName +" betaler " + propertyPrice +"Kr. "+ " til " +propertyOwnerName +".\n";
            } else {
                msg+= activePlayerName +" skal betale " + propertyPrice + "Kr. "+" til " +propertyOwnerName +", men har ikke råd.\n";
                gameOver=true;
            }

        } else if (free){ //If it is not owned and the player gets it for free
            msg+= propertyOwnerName + " ejer " + propertyName + ".\n";
            msg+= activePlayerName +" får " + propertyName + " gratis på grund af sit chancekort!";
            ((Skød)boardController.getGameBoard().getFields()[fieldId]).setOwnedByPlayerId(playerId);

        }else if (propertyOwnerId==-1){//If it is not owned and it is not free
            msg+= propertyOwnerName + " ejer " + propertyName + ".\n";
            canAfford = playerController.safeTransferToBank(playerId,propertyPrice);
            if (canAfford){
                msg+= activePlayerName +" betaler " + propertyPrice + "Kr. "+" til Banken og ejer nu "+propertyName+".\n";
                ((Skød)boardController.getGameBoard().getFields()[fieldId]).setOwnedByPlayerId(playerId);//Skal rette

            } else {
                msg+= activePlayerName +" skal betale " + propertyPrice + "Kr. "+" til banken, men har ikke råd.\n";
                gameOver=true;
            }
        }
        return msg;
    }




        public boolean isGameOver() {
            return gameOver;
        }

        public String returnWinnerMessage(){
            String winnerMessage="";
            int currentHighest=0;
            int[] playerBalances = playerController.getPlayerBalances();
            for (int i=0;i<getNumberOfPlayers();i++){
                if(playerBalances[i]>currentHighest){
                    currentHighest=playerBalances[i];
                    winnerMessage=playerController.getPlayers()[i].getName();
                } else if (playerBalances[i]==currentHighest){
                    winnerMessage+="og " + playerController.getPlayers()[i].getName();
                }
            }

            winnerMessage+= " har vundet spillet med en endelig score på " + currentHighest +"Kr. " + ".";



            return winnerMessage;
        }

}
