package Domain.model;

public class PrøvLykkenKort  {
    private String text;
    private int id;

    public PrøvLykkenKort(String text, int id){
        this.text = text;
        this.id = id;
    }

    public String getText() {
        return text;
    }

    public int getId() {
        return id;
    }
}