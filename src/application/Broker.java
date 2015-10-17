package application;
import java.awt.Point;
import java.util.Observable;

public class Broker extends Observable
{
    private String serverIP;
    public Broker(String serverIP)
    {
        this.serverIP = serverIP;
    }
    public void sendPositionUpdate(Point coord)
    {
        
    }


}
