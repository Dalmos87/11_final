package Domain.Controller;

import Domain.felt.Felt;
import Domain.felt.Skød;
import Domain.model.SpillePlade;

public class SpillePladeController {

    private SpillePlade spillePlade;
    private String[] names = {"START","Rødovrevej","Prøv Lykken", "Hvidovrevej", "Betal inkomstskat",
            "Øresund", "Roskildeevej", "Prøv Lykken", "Valby Langgade",
            "Allegadé", "Fængsel", "Frederiksberg Allé", "Tuborg", "Bülowsvej", "Gl. Kongevej",
            "D.F.D.S", "Bernstorffsvej", "Prøv Lykken", "Helleruovej", "Strandvej", "GratisParkering", "Trianglen",
            "Prøv Lykken", "Østerbrogade", "Grønningen", "Ø.S.", "Bredgade",
            "Kgs. Nytorv", "Carlsberg", "Østergade", "Gå i fængsel", "Amagertorv", "Vimmelskaftet",
            "Prøv Lykken", "Nygade", "Bornholm", "Prøv Lykken", "Frederiksberggade", "Ekstraordinær statsskat", "Rådhuspladse"};

    private char[] types = {'s','S','P','S','I','S','S','P','S','S','B','S','S',
            'S','S','S','S','P','S','S','G','S','P','S',
            'S','S','S','S','S','S','D','S','S','P','S','S','P','S','E','S' };

    private int[] prices = {0,600,0,600,2000,200,100,0,100,120,0,1400,150,1400,1400,
            200,1800,0,1800,1800,0,2200,0,2200,2400,
            200,2600,2600,150,2800,0,3000,3000,0,3200,200,0,3500,1000,4000};

    private String[] colors = {"WHITE","CYAN","WHITE","CYAN","WHITE","WHITE","PINK","WHITE","PINK","PINK",
            "WHITE","ORANGE","WHITE","ORANGE","ORANGE","WHITE","GRAY","WHITE","GRAY","GRAY",
            "WHITE","RED","WHITE","RED","RED","WHITE","YELLOW","YELLOW","WHITE",
            "YELLOW","WHITE","GREEN","GREEN","WHITE","GREEN","WHITE","WHITE","BLUE","WHITE","BLUE"};



    public SpillePladeController(){
        this.spillePlade=new SpillePlade(this.names,this.types,this.prices,this.colors);
    }

    public SpillePlade getGameBoard() {
        return spillePlade;
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
        int[] list = new int[40];
        for (int i=0;i<40;i++){
            if(spillePlade.getFields()[i].getType()!='S'){
                list[i]=-2;
            } else {
                list[i]=((Skød)spillePlade.getFields()[i]).getOwnedByPlayerId();
            }
        }
        return list;
    }
}
