package Domain.model;

public class Raflebæger {
    //opretter 2 objekt reference variabler af typen terning klassen
    private Terning Die1 = new Terning();
    private Terning Die2 = new Terning();

    //opretter en konstruktør der inititialiseres med at de to terninger kastes
    public Raflebæger() {
    }

    public void Roll(){
        Die1.roll();
        Die2.roll();
    }

    //opretter en metoder der retunerer de to terninges samlede faceValues
    public int face_sum(){
        return Die1.getFaceValue() + Die2.getFaceValue();
    }


}
