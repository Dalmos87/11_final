package Domain.model;

public class Terning {
    private static final int MAX = 6; //erklærer en konstant primitiv variable af typen int der angiver antal terningsider og initialisere den til 6
    private int faceValue; //erlærer en primitiv variable af typen der gemmer facevalue

    //opretter en konstruktør som initialiseres med en roll metode
    public Terning()
    {
        roll();
    }

    //opretter en metoder der generer et tilfældigt tal meleem 1-6 og gemmer den i faceValue variablen
    public void roll()
    {
        faceValue = (int) ( ( Math.random(  ) * MAX ) + 1 );
    }

    //opretter en metode der retunere facevalue
    public int getFaceValue()
    {
        return faceValue;
    }
}
