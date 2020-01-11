package Domain.felt;

public class Felt {
    private String name;
    private int index;
    private String color;
    private char type;

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
