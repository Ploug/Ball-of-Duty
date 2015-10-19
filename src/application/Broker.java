import java.awt.geom.Point2D;
import java.awt.geom.Point2D.Double;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.MulticastSocket;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Observable;

public class Broker extends Observable
{

    private ClientMap map;
    private String _serverIP;
    private DatagramSocket _sender;
    private MulticastSocket _socket;

    public Broker(String serverIP)
    {
        this._serverIP = serverIP;
        InetAddress group = null;
        try
        {
            group = InetAddress.getByName("127.0.0.1");
        }
        catch (UnknownHostException e1)
        {
            // TODO Auto-generated catch block
            e1.printStackTrace();
        }
        try
        {
            _socket = new MulticastSocket(15000);
        }
        catch (IOException e)
        {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        try
        {
            _socket.joinGroup(group);
        }
        catch (IOException e)
        {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    public ClientMap getMap()
    {
        return map;
    }

    public void setMap(ClientMap map)
    {
        this.map = map;
    }

    public void sendPositionUpdate(Point2D.Double position, int id) throws IOException
    {
        ByteArrayOutputStream s = new ByteArrayOutputStream();
        DataOutputStream out = new DataOutputStream(s);

        out.writeByte(1); // ASCII Standard for Start of heading
        out.writeByte(Opcodes.POSITION_UPDATE.getCode());
        out.writeByte(2); // ASCII Standard for Start of text
        out.write(id);
        out.writeDouble(position.getX());
        out.writeDouble(position.getY());
        out.writeByte(4); // ASCII Standard for End of transmission

        byte[] buf = s.toByteArray();
        send(buf);
    }

    public void receive()
    {
        while (true)
        {
            DatagramPacket packet;
            byte[] buf = new byte[256];
            packet = new DatagramPacket(buf, buf.length);
            try
            {
                _socket.receive(packet);
                byte[] data = Arrays.copyOf(packet.getData(), packet.getLength());
                InputStream s = new ByteArrayInputStream(data);
                DataInputStream in = new DataInputStream(s);
                if (in.readByte() != 1)
                {
                    return;
                }
                byte opcode = in.readByte();
                in.readByte();
                List<ObjectPosition> positions = new ArrayList<>();
                do
                {
                    int id = in.readInt();
                    double x = in.readDouble();
                    double y = in.readDouble();
                    Point2D.Double position = new Point2D.Double(x, y);
                    positions.add(new ObjectPosition(id, position));
                }
                while (in.readByte() != 31);
                map.updatePositions(positions);

            }
            catch (IOException e)
            {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }

    }

    public void send(byte[] buf)
    {
        DatagramPacket packet;
        packet = new DatagramPacket(buf, buf.length);
        try
        {
            _socket.send(packet);
        }
        catch (IOException e)
        {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

}
