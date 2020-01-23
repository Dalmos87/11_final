package Domain.felt;

public class Felt {
    private String name; //opretter et reference objekt af typen String(Feltets navn)
    private int index;//oprettet en primitiv variable af typen int
    private String color;//oprettet et referenceobjekt af typen string(feltets farve)
    private char type;//oprettet en primitiv variable af typen char(id)

    //oprettet en konstruktor til at initialisere felternes oplysninger
    public Felt(String name, int index,String color, char type){
        this.name=name;
        this.index=index;
        this.color=color;
        this.type=type;
    }

    public String getColor() {
        return color;
    }

    public char getType() {
        return type;
    }

    public String getName(){
        return this.name;
    }

    public int getPrice() {
        return -2;
    }

}
