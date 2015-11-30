package application.engine.rendering;

public class TranslatedPoint
{
    private static double translateX = 0;
    private static double translateY = 0;

    private double x;
    private double y;

    public TranslatedPoint(double x, double y)
    {
        this.x = x;
        this.y = y;

    }

    public static void setTranslate(double x, double y)
    {
        translateX = x;
        translateY = y;
    }

    public void add(double x, double y)
    {
        this.x += x;
        this.y += y;
    }

    public double getY()
    {
        return y;
    }

    public double getX()
    {
        return x;
    }

    public double getTranslatedX()
    {
        return x + translateX;
    }

    public double getTranslatedY()
    {
        return y + translateY;
    }
}
