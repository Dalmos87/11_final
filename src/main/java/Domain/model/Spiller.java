package Domain.model;

public class Spiller {

    private String name;
    private Pung pung = new Pung();
    private Brik brik = new Brik();
    private boolean freeOutJail = false;
    private boolean inJail = false;

    public Spiller(String name){
        this.name = name;
    }

    public void setBalance(int modifier){
        pung.setBalance(modifier);
    }

    public void setLocation(int location){
        brik.setLocation(location);
    }

    public void addToBalance(int modifier){
        pung.addToBalance(modifier);
    }

    public int getBalance(){
        return pung.getBalance();
    }

    public String getName(){
        return name;
    }

    public void setBrikLocation(int modifier) {
        brik.setBrikLocation(modifier);
    }

    public int getBrikLocation(){
        return brik.getBrikLocation();
    }

    public boolean getPassedStart(){
        return  brik.getPassedStart();
    }

    public boolean getFreeOutJail(){
        return freeOutJail;
    }

    public  void setFreeOutJail(Boolean modify){
        freeOutJail = modify;
    }

    public void setInJail(boolean modifier){
        inJail = modifier;
    }

    public boolean getInJail(){
        return inJail;
    }
}
