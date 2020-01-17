package Domain.model;

public class PrøvLykkenKort  {
    private String text; //erklærer et objekt reference variable af typen String
    private int id; //erklærer en primitiv variable af typen int der gemmer kortets index

    //opretter en konstruktør der initialiserer prøvLykkenKorts oplysninger
    public PrøvLykkenKort(String text, int id){
        this.text = text;
        this.id = id;
    }

    //opretter en metode der retunere kortets indhold
    public String getText() {
        return text;
    }

    //opretter en metoder der retunerer kortets index
    public int getId() {
        return id;
    }
}