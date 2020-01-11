package Domain.Controller;

import Domain.felt.Felt;
import Domain.felt.Skød;
import Domain.model.SpillePlade;

public class SpillePladeController {

    private SpillePlade gameBoard;
    private String[] names = {"START","Rødovrevej","Prøv Lykken", "Hvidovrevej", "Betal inkomstskat",
            "Øresund", "Roskildeevej", "Prøv Lykken", "Valby Langgade",
            "Allegadé", "Fængsel", "Frederiksberg Allé", "Tuborg", "Bülowsvej", "Gl. Kongevej",
            "D.F.D.S", "Bernstorffsvej", "Prøv Lykken", "Helleruovej", "Strandvej", "Helle", "Trianglen",
            "Prøv Lykken", "Østerbrogade", "Grønningen", "Ø.S.", "Bredgade",
            "Kgs. Nytorv", "Carlsberg", "Østergade", "Gå i fængsel", "Amagertorv", "Vimmelskaftet",
            "Prøv Lykken", "Nygade", "Bornholm", "Prøv Lykken", "Frederiksberggade", "Ekstraordinær statsskat", "Rådhuspladse"};

    private char[] types = {'s','S','P','S','S','S','v','p','p','c','p','p','f','p','p','c','p','p','j','p','p','c','p','p'};
    private int[] prices = {0,1,1,0,1,1,0,2,2,0,2,2,0,3,3,0,3,3,0,4,4,0,5,5};
    private String[] colors = {"WHITE","BROWN","BROWN","WHITE","CYAN","CYAN","WHITE","MAGENTA","MAGENTA","WHITE",
            "ORANGE","ORANGE","WHITE","RED","RED","WHITE","YELLOW","YELLOW","WHITE","GREEN","GREEN","WHITE","BLUE","BLUE"};



    public SpillePladeController(){
        this.gameBoard=new SpillePlade(this.names,this.types,this.prices,this.colors);
    }

    public SpillePlade getGameBoard() {
        return gameBoard;
    }

    public String[] getNames() {
        return names;
    }

    public char[] getTypes() {
        return types;
    }

    public int[] getPrices() {
        return prices;
    }

    public String[] getColors() {
        return colors;
    }

    public Felt getCurrentField(int fieldId){
        return getGameBoard().getFields()[fieldId];
    }

    public int[] getPropertiesOwnedByIds(){
        int[] list = new int[24];
        for (int i=0;i<24;i++){
            if(gameBoard.getFields()[i].getType()!='p'){
                list[i]=-2;
            } else {
                list[i]=((Skød)gameBoard.getFields()[i]).getOwnedByPlayerId();
            }
        }
        return list;
    }
}
