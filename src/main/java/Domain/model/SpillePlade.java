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
                case 'S': this.fields[i] = new Skød(names[i],i,colors[i],prices[i],types[i],-1);
                    break;
                case 'P': this.fields[i] = new PrøvLykkenFelt(names[i],i,colors[i],types[i]);
                    break;
                case 'D': this.fields[i] = new DeFængsles(names[i],i,colors[i],types[i]);
                    break;
                case 'B': this.fields[i] = new PåBesøg(names[i],i,colors[i],types[i]);
                    break;
                case 's': this.fields[i] = new Start(names[i],i,colors[i],types[i]);
                    break;
                case 'G': this.fields[i] = new GratisParkering(names[i],i,colors[i],types[i]);
                    break;
                case 'I': this.fields[i] = new Indkomstskat(names[i],i,colors[i],types[i]);
                    break;
                case 'E': this.fields[i] = new StatsSkat(names[i],i,colors[i],types[i]);
            }
                    }
    }

    public Felt[] getFields() {
        return this.fields;
    }


}