package View;

import gui_fields.*;
import gui_main.GUI;
import java.awt.*;
import java.util.ArrayList;


public class GUIController {

        private GUI gui;
        private GUI_Field[] guiFields;
        private GUI_Player[] guiPlayers;
        private GUI_Car[] guiCars = {new GUI_Car(Color.BLUE, Color.BLUE, GUI_Car.Type.CAR, GUI_Car.Pattern.FILL),
                new GUI_Car(Color.RED, Color.RED, GUI_Car.Type.CAR, GUI_Car.Pattern.FILL),
                new GUI_Car(Color.GREEN, Color.GREEN, GUI_Car.Type.CAR, GUI_Car.Pattern.FILL),
                new GUI_Car(Color.YELLOW, Color.YELLOW, GUI_Car.Type.CAR, GUI_Car.Pattern.FILL),new GUI_Car(Color.BLACK, Color.BLACK, GUI_Car.Type.CAR, GUI_Car.Pattern.FILL),new GUI_Car(Color.ORANGE, Color.ORANGE, GUI_Car.Type.CAR, GUI_Car.Pattern.FILL)};




        //This is the logic for the ViewController, that translates the models colors to awt-colors
        private Color[] colors = {Color.WHITE,Color.CYAN,Color.PINK,Color.ORANGE,Color.GRAY,Color.RED,Color.YELLOW,Color.GREEN,Color.BLUE};
        private String[] colorNames= {"WHITE","CYAN","PINK","ORANGE",
                "GRAY","RED","YELLOW","GREEN","BLUE"};


//{"WHITE","Dark Purple","WHITE","Dark Purple","WHITE","WHITE","Light Blue","WHITE","Light Blue","Light Blue",
//            "WHITE","Pinks","WHITE","Pinks","Pinks","WHITE","Orange","WHITE","Orange","Orange",
//            "WHITE","Reds","WHITE","Reds","Reds","WHITE","Yellows","Yellows","WHITE",
//            "Yellows","WHITE","Greens","Greens","WHITE","Greens","WHITE","WHITE","Dark Blues","WHITE","Dark Blues"};



        public GUIController(String[] names, String[] colorNames, int[] prices, char[] types){
            boardSetup(names,colorNames,prices,types);
            gui.showMessage("Velkommen til Matador-spillet udviklet af gruppe 11.");
        }


        //Sets the board with the information given
        public void boardSetup(String[] names, String[] colorNames, int[] prices, char[] types){
            GUI_Field[] fields = new GUI_Field[names.length];
            Color newColor=Color.WHITE;
            for (int i=0;i<names.length;i++){
                switch (types[i]){
                    case 'S': //Property
                        fields[i] = new GUI_Street();
                        fields[i].setSubText(+prices[i]+ "Kr."); //SubText er under "Overskriften. Hvis ingen SubText metode vil der stå "SubText" på Boardet
                        fields[i].setDescription("");
                        break;
                    case 's': //Start
                        fields[i] = new GUI_Start();
                        fields[i].setSubText("");
                        fields[i].setDescription("");
                        break;
                    case 'P': //ChancecardField
                        fields[i] = new GUI_Chance();
                        fields[i].setSubText("");
                        fields[i].setDescription("");
                        break;
                    case 'D': //Jail
                        fields[i] = new GUI_Jail();
                        fields[i].setSubText("Gå i fængsel");
                        fields[i].setDescription("");
                        break;
                    case 'B'://VisitJail
                        fields[i] = new GUI_Jail();
                        fields[i].setSubText("På besøg");
                        fields[i].setDescription("");
                        break;
                    case 'G': //FreeParking
                        fields[i] = new GUI_Refuge();
                        fields[i].setSubText("Parkering");
                        fields[i].setDescription("");
                        break;

                    case 'I':
                        fields[i] = new GUI_Tax();
                        fields[i].setSubText(+prices[i]+ "Kr.");
                        fields[i].setDescription("");
                        break;
                    case 'E':
                        fields[i] = new GUI_Tax();
                        fields[i].setSubText(prices[i]+ "Kr.");
                        fields[i].setDescription("");


                }
                for (int j=0; j<this.colors.length;j++){
                    if (colorNames[i].equals(this.colorNames[j])){
                        newColor = this.colors[j];
                    }
                }

                fields[i].setDescription(names[i]);
                fields[i].setBackGroundColor(newColor);
                fields[i].setTitle(names[i]);
            }
            this.guiFields = fields;
            this.gui = new GUI(fields,Color.GREEN);
            this.gui.setChanceCard("Prøv lykken");
        }

        public String[] createPlayers(){
            int playerSelection = 2;
            playerSelection = Integer.parseInt(gui.getUserButtonPressed("Vælg antal spillere","3","4","5","6"));
            String[] playerNames = new String[playerSelection];
            String input;
            for (int i=0;i<playerSelection;){
                input = gui.getUserString("Spiller "+(i+1) + " skriv dit navn");
                if(input != null ) {
                    if (!checkPlayerexsistance(playerNames, input) && !(input.length()==0)) {
                        playerNames[i] = input;
                        i++;
                    } else {
                        gui.showMessage("To spillere må ikke have samme navn, og navnet må ikke være tomt.");
                    }
                }
                else if(input == null ){
                    gui.showMessage("Skriv venligst et navn!");
                }
            }

            return playerNames;
        }





        public void setupGuiPlayers(String[] names, int[] startPoint, int startFieldId){
            guiPlayers = new GUI_Player[names.length];
            for (int i=0;i<names.length;i++){
                guiPlayers[i] = new GUI_Player(names[i],startPoint[i],guiCars[i]);
                gui.addPlayer(guiPlayers[i]);
                setPlayerOnField(startFieldId,i);
            }

        }

        public void setPlayerOnField(int fieldId, int playerId){
            this.guiFields[fieldId].setCar(guiPlayers[playerId],true);

        }

        public void removeAllPlayersFromField(int fieldId){
            this.guiFields[fieldId].removeAllCars();
        }

        public void updatePlayerBalance(int playerId, int newBalance){
            guiPlayers[playerId].setBalance(newBalance);
        }

        public void displayLandedOnNewField(String name, String fieldName){
            gui.showMessage(name + " er landet på " + fieldName +".");
        }

        public void showDie(int die){
            gui.setDie(die);

        }
        /*
        gui.showDice(cup.getD1().getValue(), cup.getD2().getValue());
         */
        public void showMessage(String msg){
            gui.showMessage(msg);
        }

    public static boolean checkPlayerexsistance(String[] playerName,String typedName){
        //boolean playerexsistance = Arrays.stream(playerName).anyMatch(typedName::equals);
        //return playerexsistance;
        for( int i = 0; i < playerName.length; i++){

            if(playerName[i] != null && playerName[i].equals(typedName))
                return true;
        }
        return false;
    }

        public void displayChanceCard(String text){
            gui.displayChanceCard(text);
        }

        public String getUserSelection(String msg, String ... options){
            String selection = gui.getUserSelection(msg,options);
            return selection;
        }

        public String getUserButtonPressed(String msg, String ... options){
            return  gui.getUserButtonPressed(msg,options);
        }

        public void setNewPropertyOwner(int fieldId, int price, String ownerName){
        guiFields[fieldId].setSubText("Pris: "+price + "Kr." +"     Ejes af "+ownerName+".");
         }

      public void closeGui(){
        gui.close();
    }
        }


