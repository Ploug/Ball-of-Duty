package application;

public class MousePosition
{
    private double mouseX;
    private double mouseY;

    public MousePosition()
    {
        setMouseX(0);
        setMouseY(0);
    }

    public double getMouseX()
    {
        return mouseX;
    }

    public void setMouseX(double mouseX)
    {
        this.mouseX = mouseX;
    }

    public double getMouseY()
    {
        return mouseY;
    }

    public void setMouseY(double mouseY)
    {
        this.mouseY = mouseY;
    }
}
