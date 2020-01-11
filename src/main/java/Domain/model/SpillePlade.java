package Domain.model;

import Domain.felt.*;


public class SpillePlade {
    protected Felt[] fields;
    private Skød[] properties;

    public SpillePlade(String[] names, char[] types, int[] prices, String[] colors){
        int numberOfFields = names.length;
        this.fields = new Felt[numberOfFields];

        for (int i=0;i<numberOfFields;i++){
            switch (types[i]){
                case 'p': this.fields[i] = new Skød(names[i],i,colors[i],prices[i],types[i],-1);
                    break;
                case 'c': this.fields[i] = new PrøvLykkenFelt(names[i],i,colors[i],types[i]);
                    break;
                case 'j': this.fields[i] = new DeFængsles(names[i],i,colors[i],types[i]);
                    break;
                case 'v': this.fields[i] = new PåBesøg(names[i],i,colors[i],types[i]);
                    break;
                case 's': this.fields[i] = new Start(names[i],i,colors[i],types[i]);
                    break;
                case 'f': this.fields[i] = new GratisParkering(names[i],i,colors[i],types[i]);
                    break;
            }
        }
    }
}
