package application;

import java.awt.geom.Point2D;
import java.io.ByteArrayInputStream;
import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.MulticastSocket;
import java.net.UnknownHostException;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Observable;

public class Broker extends Observable
{

    private ClientMap map;
    private DatagramSocket _sender;
    private InetAddress ina;
    private MulticastSocket _socket;

    public Broker(ClientMap map)
    {
        this.map = map;
        InetAddress group = null;
        try
        {
            ina = InetAddress.getByName("127.0.0.1");
            group = InetAddress.getByName("235.1.2.87");
        }
        catch (UnknownHostException e1)
        {
            // TODO Auto-generated catch block
            e1.printStackTrace();
        }
        try
        {
            // _sender = new DatagramSocket(15001, ina);
            _sender = new DatagramSocket();
            _socket = new MulticastSocket(15000);
            new Thread(() ->
            {
                receive();
            }).start();
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

    public void sendPositionUpdate(Point2D.Double position, int id) throws IOException
    {
        ByteBuffer buffer = ByteBuffer.allocate(256); // more bytes pls

        buffer.order(ByteOrder.LITTLE_ENDIAN);
        buffer.put((byte)1); // ASCII Standard for Start of heading
        buffer.put((byte)Opcodes.POSITION_UPDATE.getCode());
        buffer.put((byte)2); // ASCII Standard for Start of text
        buffer.putInt(id);
        buffer.putDouble(position.getX());
        buffer.putDouble(position.getY());
        buffer.put((byte)4); // ASCII Standard for End of transmission

        send(buffer.array());
    }

    public void receive()
    {
        while (true)
        {
            DatagramPacket packet;

            byte[] buf = new byte[256]; // more bytes pls
            packet = new DatagramPacket(buf, buf.length);
            try
            {
                _socket.receive(packet);
                byte[] data = Arrays.copyOf(packet.getData(), packet.getLength());

                ByteBuffer buffer = ByteBuffer.allocate(256); // more bytes pls
                buffer.order(ByteOrder.LITTLE_ENDIAN);
                buffer.put(data);
                buffer.rewind();

                InputStream s = new ByteArrayInputStream(data);
                DataInputStream in = new DataInputStream(s);
                if (buffer.get() != 1) // start of heading
                {
                    return;
                }
                byte opcode = buffer.get();

                buffer.get(); // start of text

                List<ObjectPosition> positions = new ArrayList<>();
                do
                {
                    int id = buffer.getInt();
                    double x = buffer.getDouble();
                    double y = buffer.getDouble();
                    Point2D.Double position = new Point2D.Double(x, y);
                    positions.add(new ObjectPosition(id, position));
                }
                while (buffer.get() == 31); // unit separator

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
        packet = new DatagramPacket(buf, buf.length, ina, 15001);
        try
        {
            _sender.send(packet);
        }
        catch (IOException e)
        {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

}
