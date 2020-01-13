package Domain.model;

public class Terning {
    private static final int MAX	= 6;
    private int faceValue;

    public Terning()
    {

        roll();
    }

    public void roll()
    {

        faceValue = (int) ( ( Math.random(  ) * MAX ) + 1 );
    }

    public int getFaceValue()
    {

        return faceValue;
    }
}
