package Domain.model;

public class Brik {
    private int location;
    private boolean passedStart;

    public Brik() {
        location = 1;
    }

    public void setBrikLocation(int modifier){
        //hvis spilleren passerer start
        if ((location + modifier) > 40){
            location += modifier - 40;
            passedStart = true;
        }

        //hvis spilleren ikke passerer start
        else {
            location += modifier;
        }
    }

    public void setLocation(int location){
        this.location = location;
    }

    public boolean getPassedStart(){
        //hvis spilleren har passeret start
        if (passedStart) {
            passedStart = false;
            return true;
        }
        //hvis spilleren ikke har passeret start
        else {
            return passedStart;
        }
    }

    public int getBrikLocation(){
        return location;
    }
}
