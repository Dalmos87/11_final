package Domain.model;

public class PrøvLykkenKortBunke {

    //erklærer en objekt reference array af typen PrøvLykkenKort der gemmer kortenes oplysninger
    private PrøvLykkenKort[] prøvLykkenKorts;

    //opretter en konstruktør der initialiserer kortenes oplysninger og antal spillere
    public PrøvLykkenKortBunke(String[] chanceCardTexts,int numberOfPlayers){
        int numberOfRelevantChanceCards = chanceCardTexts.length-6+numberOfPlayers;
        this.prøvLykkenKorts = new PrøvLykkenKort[numberOfRelevantChanceCards];

        for (int i=0;i<numberOfRelevantChanceCards;i++){
            prøvLykkenKorts[i] = new PrøvLykkenKort(chanceCardTexts[i],i);
        }
    }

    //opretter en metode der modtager tilfældige kort og blander dem, derefter gemmes de i array objekter
    public void swap(int a, int b){
        PrøvLykkenKort cardA = prøvLykkenKorts[a];
        PrøvLykkenKort cardB = prøvLykkenKorts[b];
        prøvLykkenKorts[a] = cardB;
        prøvLykkenKorts[b] = cardA;
    }


    public void shuffle(){
        for (int i=0; i<1000; i++ ){
            int a= (int) (Math.random()*prøvLykkenKorts.length);
            int b= (int) (Math.random()*prøvLykkenKorts.length);
            swap(a,b);
        }
    }

    public PrøvLykkenKort draw(){
        PrøvLykkenKort upper= prøvLykkenKorts[0];
        for (int i=0; i<prøvLykkenKorts.length-1;i++){
            prøvLykkenKorts[i] =prøvLykkenKorts [i+1];
        }
        prøvLykkenKorts[prøvLykkenKorts.length-1]=upper;
        return upper;
    }

    public PrøvLykkenKort[] getChanceCards() {
        return prøvLykkenKorts;
    }
}
