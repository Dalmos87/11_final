package Domain.model;

public class Felt {

    private String navn;
    private boolean owned = false;
    private int owner = 0;
    private int soesterFelt;
    private int pris;

    public Felt(String navn, int soesterFelt, int pris) {
        this.navn = navn;
        this.soesterFelt = soesterFelt;
        this.pris = pris;
    }

    public String getName() {
        return navn;
    }

    public boolean getOwned(){
        return owned;
    }

    public void setOwner(int player){
        owner = player;
        owned = true;
    }

    public int getOwner(){
        return owner;
    }

    public int getSoesterFelt(){
        return soesterFelt;
    }

    public int getPris(){
        return pris;
    }
}
