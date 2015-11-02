package application.communication;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Observable;
import java.util.Set;

import application.engine.entities.Bullet;
import application.engine.rendering.ClientMap;
import javafx.geometry.Point2D;

/**
 * Handles all networking that isn't web service based and acts as a middleman between server and client objects, such as ClientMap, that
 * needs to communicate with the server.
 * 
 */
public class Broker extends Observable
{

    private ClientMap map;
    private InetAddress ina;
    private DatagramSocket _socket;
    private Thread receiveThread;
    private boolean isActive = false;

    /**
     * Creates a broker communication with a default server ip.
     */
    public Broker()
    {
        try
        {
            ina = InetAddress.getByName("127.0.0.1"); // TODO dynamically change ip.
        }
        catch (UnknownHostException e1)
        {
            e1.printStackTrace();
        }
        try
        {
            _socket = new DatagramSocket(null); // force _socket not to bind to
                                                // an address.
            _socket.bind(null); // force _socket to pick up a free port and an
                                // address determined by the operating system.
        }
        catch (IOException e)
        {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    /**
     * Activates the brokers listening loop.
     * 
     * @param map
     */
    public void activate(ClientMap map)
    {
        this.map = map;

        isActive = true;
        receiveThread = new Thread(() ->
        {
            receive();
        });
        receiveThread.start();
    }

    /**
     * Returns the port the broker is listening on.
     * 
     * @return The port the broker is listening on.
     */
    public int getPort()
    {
        return _socket.getLocalPort();
    }

    /**
     * Returns the map the broker is communicating with.
     * 
     * @return The map the broker is communicating with.
     */
    public ClientMap getMap()
    {
        return map;
    }

    /**
     * Sends a packet to the server with updated information from the clients game.
     * 
     * @param position
     *            The position of the clients character.
     * @param id
     *            The id of the clients character.
     * @param bullets
     *            The currently active bullets shot by the clients characters weapon.
     * @throws IOException
     */
    public void sendUpdate(Point2D position, int id, Set<Bullet> bullets)
    {
        try
        {
            Thread.sleep(20);
        }
        catch (InterruptedException e)
        {
            e.printStackTrace();
        }
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

    /**
     * Stops the brokers listening loop.
     */
    public void stop()
    {
        receiveThread.interrupt();
        isActive = false;
    }

    /**
     * Starts listening for updates.
     */
    private void receive()
    {
        while (isActive)
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

                if (buffer.get() != 1) // start of heading
                {
                    return;
                }
                byte opcode = buffer.get();

                buffer.get(); // start of text

                List<ObjectPosition> positions = new ArrayList<>();
                int count = 0;
                do
                {
                    count++;
                    int id = buffer.getInt();
                    double x = buffer.getDouble();
                    double y = buffer.getDouble();
                    Point2D position = new Point2D(x, y);

                    positions.add(new ObjectPosition(id, position));
                }
                while (buffer.get() == 31); // unit separator
                // System.out.println(count);

                map.updatePositions(positions);

            }
            catch (IOException e)
            {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
    }

    /**
     * Sends a data, as a byte array, to the server.
     * 
     * @param buf
     *            The byte array to be sent.
     */
    public void send(byte[] buf)
    {
        DatagramPacket packet = new DatagramPacket(buf, buf.length, ina, 15001); // TODO dynamically port.
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
