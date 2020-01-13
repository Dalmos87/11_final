package Domain.model;

public class Raflebæger {
    private Terning Die1 = new Terning();
    private Terning Die2 = new Terning();

    public Raflebæger() {
    }

    public void Roll(){
        Die1.roll();
        Die2.roll();
    }


    public int face_sum(){
        return Die1.getFaceValue() + Die2.getFaceValue();
    }


}
