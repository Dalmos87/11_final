package Domain.Controller;

import Domain.felt.Skød;
import View.GUIController;

import java.util.ArrayList;

public class SystemController {

    private MonopolySpilController gameController;
    private GUIController viewController;
    private boolean gameOver=false;

    public SystemController(){
        this.gameController = new MonopolySpilController();
        this.viewController = new GUIController(gameController.getBoardController().getNames()
                ,gameController.getBoardController().getColors()
                ,gameController.getBoardController().getPrices()
                ,gameController.getBoardController().getTypes());

        initialize();
    }

    public void initialize(){
        //Gets the player names via the viewController
        String[] playerNames = viewController.createPlayers();

        //Creates the players in the model
        gameController.setupPlayerController(playerNames);

        //Gets the player balances and creates the guiPlayers
        int[] playerBalances = gameController.getPlayerController().getPlayerBalances();
        viewController.setupGuiPlayers(playerNames,playerBalances,0);
    }





    public void teleportPlayerCar(int playerId, int dieRoll, boolean toJail){
        //Gets old field id
        int oldFieldId= gameController.getPlayerController().getPlayerFieldId(playerId);
        int numberOfPlayers = gameController.getPlayerController().getNumberOfPlayers();

        //Makes array of playerID's of players on oldField
        ArrayList<Integer> playersOnOldFieldId = new ArrayList<Integer>();
        for (int i=0;i<numberOfPlayers;i++){
            if (gameController.getPlayerController().getPlayerFieldId(i)==oldFieldId){
                playersOnOldFieldId.add(i);
            }
        }
        //Updates the players current fieldID
        int newFieldId = gameController.movePlayerCar(dieRoll,playerId);

        //Removes all players from old field
        viewController.removeAllPlayersFromField(oldFieldId);

        //Puts the remaining cars back again
        for (int i=0;i<playersOnOldFieldId.size();i++){
            if (playerId!=playersOnOldFieldId.get(i)){
                viewController.setPlayerOnField(oldFieldId,playersOnOldFieldId.get(i));
            }
        }

        //Updates the active players point (maybe he passed start)
        if (!toJail){
            int updatedPlayerPoints = gameController.newBalanceAfterRoll(playerId,oldFieldId,dieRoll);
            viewController.updatePlayerBalance(playerId,updatedPlayerPoints);
        }

        //Moves the guiPlayer to the new position
        viewController.setPlayerOnField(newFieldId,playerId);

    }







    public void movePlayerCar(int playerId,int dieRoll, boolean fromJail){
        for(int i=0;i<dieRoll;i++){
            teleportPlayerCar(playerId,1, fromJail);
            try
            {
                Thread.sleep(100);
            }
            catch(InterruptedException ex)
            {
                Thread.currentThread().interrupt();
            }
        }


    }

    public void playGame(){
        int activePlayerId = 0;

        int roll=0;

        int newFieldId;
        String activePlayerName;
        while (!gameOver){

            //Gets the name of the active player
            activePlayerName=gameController.getPlayerController().getPlayers()[activePlayerId].getName();

            //Displays who's turn it is
            viewController.showMessage("Det er "+ activePlayerName + "s tur.");

            if (gameController.getPlayerController().getPlayers()[activePlayerId].isNextTurnVacantField()){
                viewController.showMessage(activePlayerName + " bruger sit specielle chancekort og må vælge et ledigt felt at køre til.");
                moveToVacantProperty(activePlayerId,gameController.getPlayerController().getPlayers()[activePlayerId].getCurrentFieldId());
                gameController.getPlayerController().getPlayers()[activePlayerId].setNextTurnVacantField(false);
            } else {

                String prisonMessage = gameController.executePlayerInPrison(activePlayerId);
                if (!prisonMessage.equals("")) {
                    viewController.showMessage(prisonMessage);
                }

                //////////////////////////////////////////////////////////////////
               String indkomstSkatMassage = gameController.indkomstskatFelt(activePlayerId);
                if (!indkomstSkatMassage.equals("")){
                    viewController.showMessage(indkomstSkatMassage);
                }

                String statsSkatMassage = gameController.statsSkatFelt(activePlayerId);
                if (!statsSkatMassage.equals("")){
                    viewController.showMessage(statsSkatMassage);
                }
               //////////////////////////////////////////////////////////////////



                //rolls the die
                roll = gameController.getRoll();

                viewController.showDie(roll);






                //Updates the position of the active player
                movePlayerCar(activePlayerId, roll, false);
                newFieldId = gameController.getPlayerController().getPlayerFieldId(activePlayerId);
                playTurnOnNewField(activePlayerId, newFieldId);

                //Checks if game is over
                checkIfGameOver();

            }
            //Gives the turn to the next player
            activePlayerId++;
            activePlayerId = activePlayerId % gameController.getNumberOfPlayers();
        }
        viewController.showMessage("Tak for spillet! Luk vinduet ved at trykke på X i hjørnet, eller tryk OK for at spille igen.");
        viewController.closeGui();

    }


    public void playTurnOnNewField(int playerId, int newFieldId){
        //Gets name of active player
        String activePlayerName = gameController.getPlayerController().getPlayers()[playerId].getName();
        switch (gameController.getBoardController().getCurrentField(newFieldId).getType()){
            case 'S':
                landedOnProperty(playerId,newFieldId,false);
                break;
            case 'P':
                landedOnChanceCardField(playerId,newFieldId);
                break;
            case 'D':
                landedOnJail(playerId);
                break;
            case 'B':
                viewController.showMessage( activePlayerName+ " er på besøg i fængsel.");
                break;
            case 's':
                viewController.displayLandedOnNewField(activePlayerName,"start");
                break;
            case 'G':
                viewController.displayLandedOnNewField(activePlayerName,"gratis parkering");
                break;
            case 'I':
                landPåIndkomstSkatFelt(playerId);///////////////////////////////////////
                break;
            case 'E':
                landPåStatsSkatFelt(playerId);//////////////////////////////////////////

        }

    }


    //////////////////////////////////////////////////////////////////////
    public void landPåIndkomstSkatFelt(int playerId){
        String activePlayerName = gameController.getPlayerController().getPlayers()[playerId].getName();
        viewController.showMessage(activePlayerName + " Du skal betaler 2000Kr.");
        gameOver = !gameController.getPlayerController().safeTransferToBank(playerId,2000);
        gameController.setIndkomstSkatFelt(playerId,true);
        updatePlayerBalances();
    }

    public void landPåStatsSkatFelt(int playerId){
        String activePlayerName = gameController.getPlayerController().getPlayers()[playerId].getName();
        viewController.showMessage(activePlayerName + "Du skal betaler 2000Kr.");
        gameOver = !gameController.getPlayerController().safeTransferToBank(playerId,1000);
        gameController.setStatsSkatFelt(playerId,true);
        updatePlayerBalances();
    }
    ////////////////////////////////////////////////////////////////////////


    public void landedOnJail(int playerId){
        viewController.displayLandedOnNewField(gameController.getPlayerController().getPlayers()[playerId].getName(),"Fængslet");
        movePlayerCar(playerId,6,true);
        gameController.setPlayerInPrison(playerId,true);
        updatePlayerBalances();
    }

    public void updatePlayerBalances(){
        int newBalance;
        for (int i=0; i<gameController.getNumberOfPlayers();i++){
            newBalance = gameController.getPlayerController().getPlayerBalances()[i];
            viewController.updatePlayerBalance(i,newBalance);
        }
    }


    public void landedOnProperty(int playerId, int fieldId,boolean free){
        //Input playerId, fieldId and free-boolean.
        //if free==true, the player will get the property for free. Otherwise, the balances and ownership will be
        //updated and displayed on the board correctly.

        //Makes the gameController update balances and ownership. Returns a message describing what happened.
        String statusMessage = gameController.landedOnProperty(playerId,fieldId,free);

        //If the player now owns the field, the ownership is displayed on the board
        if (statusMessage.contains("og ejer nu") || statusMessage.contains(" gratis på grund af sit chancekort!")){
            //A bit of a problem if the player names contains these
            int fieldPrice =gameController.getBoardController().getGameBoard().getFields()[fieldId].getPrice();
            String playerName = gameController.getPlayerController().getPlayers()[playerId].getName();
            viewController.setNewPropertyOwner(fieldId,fieldPrice,playerName);
        }
        viewController.showMessage(statusMessage);
        updatePlayerBalances();
        checkIfGameOver();
    }

    public void landedOnChanceCardField(int playerId,int fieldId){
        String activePlayerName = gameController.getPlayerController().getPlayers()[playerId].getName();

        //Displays the chancecard-text
        String ccText = gameController.getCcd_controller().getChanceCardDeck().getChanceCards()[0].getText();
        viewController.displayChanceCard(ccText);
        //Displays message
        viewController.showMessage(activePlayerName + " er landet på et chancekortfelt! Læs kortet i midten af brættet.");


        //Gets the id of the chanceCard
        int ccId = gameController.getCcd_controller().getChanceCardDeck().getChanceCards()[0].getId();

        //Puts the card in the bottom of the deck
        gameController.getCcd_controller().getChanceCardDeck().draw();


        String selection="";
        switch (ccId){
            case 0://Ryk frem til start
                movePlayerCar(playerId,40-fieldId,false);
                break;
            case 1://Ryk frem til Rådhuspladsen
                movePlayerCar(playerId,39-fieldId,false);
                break;
            case 2://Ryk op til 5 felter frem
                selection = viewController.getUserSelection("Hvor mange felter vil du rykke frem?","1","2","3","4","5");
                int chosenDieRoll = Integer.parseInt(selection);
                movePlayerCar(playerId,chosenDieRoll,false);
                break;
            case 3: //Ryk 1 felt frem eller tag et nyt kort
                selection = viewController.getUserButtonPressed("Hvad vælger du?","Ryk et felt frem.","Tag et chancekort mere.");
                if ("Tag et chancekort mere.".equals(selection)){
                    landedOnChanceCardField(playerId,fieldId);
                } else if("Ryk et felt frem.".equals(selection)){
                    movePlayerCar(playerId,1,false);//..................................
                }

                break;
            case 4:
                freePropertyChanceCard(playerId,fieldId,10,11);
                break;
            case 5:
                freePropertyChanceCard(playerId,fieldId,10,11,19,20);
                break;
            case 6:
                freePropertyChanceCard(playerId,fieldId,4,5);
                break;
            case 7:
                freePropertyChanceCard(playerId,fieldId,10);
                break;
            case 8:
                freePropertyChanceCard(playerId,fieldId,13,14);
                break;
            case 9:
                freePropertyChanceCard(playerId,fieldId,7,8,22,23);
                break;
            case 10:
                freePropertyChanceCard(playerId,fieldId,4,5,13,14);
                break;
            case 11:
                freePropertyChanceCard(playerId,fieldId,1,2,16,17);
                break;
            case 12://Betal 2 til banken
                viewController.showMessage(activePlayerName + " betaler bøden på Kr.");
                gameOver = !gameController.getPlayerController().safeTransferToBank(playerId,3000);
                break;
            case 13: //Modtag 2
                viewController.showMessage(activePlayerName + " modtager gevinsten på Kr.");
                gameController.getPlayerController().safeTransferToBank(playerId,-500);
                break;
            case 14: //Fødseldsag
                for (int i =0; i<gameController.getNumberOfPlayers();i++){
                    if (i!=playerId) {
                        if(!gameController.getPlayerController().safeTransferToPlayer(i, 300, playerId)){
                            gameOver=true;
                        }
                    }
                }
                break;
            case 15:
                viewController.showMessage(activePlayerName + " har modtager løsladelseskortet!");
                gameController.getPlayerController().getPlayers()[playerId].setHasPrisonCard(true);
                break;
            case 16:
                gameController.getPlayerController().getPlayers()[0].setNextTurnVacantField(true);
                landedOnChanceCardField(playerId,fieldId);
                break;
            case 17:
                gameController.getPlayerController().getPlayers()[1].setNextTurnVacantField(true);
                landedOnChanceCardField(playerId,fieldId);
                break;
            case 18:
                gameController.getPlayerController().getPlayers()[2].setNextTurnVacantField(true);
                landedOnChanceCardField(playerId,fieldId);
                break;
            case 19:
                gameController.getPlayerController().getPlayers()[3].setNextTurnVacantField(true);
                landedOnChanceCardField(playerId,fieldId);
                break;

        }
        updatePlayerBalances();


    }

    public void freePropertyChanceCard(int playerId, int oldFieldId,int...fieldIdsToChooseFrom){
        //Gets the names of the fields that player can choose
        String[] fieldNames = new String[fieldIdsToChooseFrom.length];
        for (int i=0;i<fieldIdsToChooseFrom.length;i++){
            fieldNames[i] = gameController.getBoardController().getGameBoard().getFields()[fieldIdsToChooseFrom[i]].getName();
        }

        String selection="";
        int chosenFieldId=0;

        if(fieldNames.length==1){ //If there is only one option
            viewController.showMessage("Du rykker til "+ fieldNames[0]+".");
            chosenFieldId=fieldIdsToChooseFrom[0];
        } else { //else if there are multiple fields to choose from
            selection = viewController.getUserSelection("Hvilket felt vælger du?",fieldNames);
        }

        for (int i=0;i<fieldNames.length;i++){
            if (selection.equals(fieldNames[i])){
                chosenFieldId=fieldIdsToChooseFrom[i];
            }
        }

        //Moves to the chosen field
        int simulatedRoll= chosenFieldId-oldFieldId;
        if(simulatedRoll<0){
            simulatedRoll+=40;
        }
        movePlayerCar(playerId,simulatedRoll,false);
        landedOnProperty(playerId,chosenFieldId, false);
    }

    public void moveToVacantProperty(int playerId, int oldFieldId){
        ArrayList<Integer> vacantPropertyIds = new ArrayList<Integer>();
        for (int i =0; i<40;i++){
            if(gameController.getBoardController().getGameBoard().getFields()[i].getType()=='S'){
                if (((Skød)gameController.getBoardController().getGameBoard().getFields()[i]).getOwnedByPlayerId()==-1){
                    vacantPropertyIds.add(i);
                }
            }

        }

        String[] vacantProperties = new String[vacantPropertyIds.size()];
        for (int i=0;i<vacantPropertyIds.size();i++) { //Gets the names of the vacant properties
            vacantProperties[i] =gameController.getBoardController().getGameBoard().getFields()[vacantPropertyIds.get(i)].getName();
        }

        //Asks the player which property he wants to go to
        String selection = viewController.getUserSelection("Vælg den ejendom, du vil gå til.",vacantProperties);

        int selectedFieldId=0;
        for (int i =0;i<vacantProperties.length;i++){
            if (selection.equals(vacantProperties[i])){
                selectedFieldId=vacantPropertyIds.get(i);
            }
        }

        //Moves the player to the correct field
        int simulatedRoll = (40+selectedFieldId-oldFieldId)%40;
        movePlayerCar(playerId,simulatedRoll,false);
        landedOnProperty(playerId,selectedFieldId,false);

    }







    public void checkIfGameOver(){
        if(gameController.isGameOver()){
            String winningMessage = gameController.returnWinnerMessage();
            viewController.showMessage(winningMessage);
            gameOver=true;
        }
    }

}
