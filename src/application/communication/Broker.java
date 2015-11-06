package application.communication;

import java.io.DataInputStream;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.tempuri.IBoDService;

import application.engine.rendering.ClientMap;
import javafx.geometry.Point2D;

/**
 * Handles all networking that isn't web service based and acts as a middleman between server and client objects, such as ClientMap, that
 * needs to communicate with the server.
 * 
 */
public class Broker
{

    private ClientMap map;
    private InetAddress ina;
    private DatagramSocket _socket;
    private Socket tcpSocket;
    private boolean isActive = false;
    private static final String SERVER_IP = "127.0.0.1";
    private static final int SERVER_TCP_PORT = 15010;
    private IBoDService webservice;

    /**
     * Creates a broker communication with a default server ip.
     */
    public Broker(IBoDService webservice)
    {
        this.webservice = webservice;
        try
        {
            ina = InetAddress.getByName(SERVER_IP);
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
            tcpSocket = new Socket(ina, SERVER_TCP_PORT);
        }
        catch (IOException e)
        {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    /**
     * Activates the brokers listening loops.
     * 
     * @param map
     *            he map the broker is communicating with.
     */
    public void activate(ClientMap map)
    {
        this.map = map;

        isActive = true;
        startListeningTCP();
        receive();

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
     * @param bullets the bullets that needs to have its position updated to the server.
     * @throws IOException
     */
    public void sendUpdate(List<ObjectPosition> posList ) // TODO Should possibly know DAO of GameObject instead of ObjectPosition.
    {
        try
        {
            Thread.sleep(20);
        }
        catch (InterruptedException e)
        {
            e.printStackTrace();
        }
        ByteBuffer buffer = ByteBuffer.allocate(1024);

        buffer.order(ByteOrder.LITTLE_ENDIAN);
        buffer.put((byte)1); // ASCII Standard for Start of heading
        buffer.put((byte)Opcodes.POSITION_UPDATE.getCode());
        buffer.put((byte)2); // ASCII Standard for Start of text
        for(ObjectPosition pos : posList)
        {
            buffer.putInt(pos.Id);
            buffer.putDouble(pos.Position.getX());
            buffer.putDouble(pos.Position.getY());
            buffer.put((byte)31); // Unit   

        }
        buffer.put((byte)4); // ASCII Standard for End of transmission
       

        send(buffer.array());
    }

    /**
     * Stops the brokers listening loop.
     */
    public void stop()
    {
        isActive = false;
    }

    /**
     * Starts listening for updates.
     */
    private void receive()
    {
        new Thread(() ->
        {

            while (isActive)
            {
                DatagramPacket packet;

                byte[] buf = new byte[1024]; 
                packet = new DatagramPacket(buf, buf.length);
                try
                {
                    _socket.receive(packet);
                    byte[] data = Arrays.copyOf(packet.getData(), packet.getLength());

                    ByteBuffer buffer = ByteBuffer.allocate(1024); 
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

                    map.updatePositions(positions);

                }
                catch (IOException e)
                {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }
        }).start();
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
            e.printStackTrace();
        }
    }

    /**
     * Creates a bullet and returns it Id.
     * @return The id of the bullet created.
     */
    public int requestBulletCreation(double x, double y, double radius, double damage, int ownerId, int gameId)
    {
        try
        {
            return webservice.requestBulletCreation(x, y,radius,damage,ownerId,gameId);
        }
        catch (RemoteException e)
        {
            e.printStackTrace();
            return -1;
        }
        
    }
    /**
     * Listens for messages on the server
     */
    private void startListeningTCP()
    {

        new Thread(() ->
        {
            DataInputStream input = null;
            try
            {
                input = new DataInputStream(tcpSocket.getInputStream());

                while (isActive)
                {
                    int value = input.readInt();
                    if (true) //FIXME if its an opcode
                    {
                        do
                        {
                            value = input.read();
                            System.out.println("data: "+value);
                           //Do something while value isnt end of transmission.
                        }
                        while(value != -1); // TODO end of transmission value is?
                            
                    }
                }
            }
            catch (IOException e)
            {
                e.printStackTrace();
            }
        }).start();

    }
}
