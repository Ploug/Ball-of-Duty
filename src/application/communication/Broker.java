package application.communication;

import java.io.DataInputStream;
import java.io.DataOutputStream;
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
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.tempuri.IBoDService;

import application.engine.entities.Bullet;
import application.engine.factories.EntityFactory;
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
    private DataOutputStream output = null;

    /**
     * Creates a broker communication with a default server ip.
     */
    public Broker()
    {
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
        try
        {
            tcpSocket = new Socket(ina, SERVER_TCP_PORT);
            tcpSocket.setTcpNoDelay(true);
            output = new DataOutputStream(tcpSocket.getOutputStream());
        }
        catch (IOException e)
        {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
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
     * @param bullets
     *            the bullets that needs to have its position updated to the server.
     * @throws IOException
     */
    public void sendUpdate(List<GameObjectDAO> posList) // TODO Should possibly know DAO of GameObject instead of ObjectPosition.
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
        buffer.put((byte)Opcodes.POSITION_UPDATE.getValue());
        buffer.put((byte)2); // ASCII Standard for Start of text
        for (int i = 0; i < posList.size(); ++i)
        {
            GameObjectDAO data = posList.get(i);
            buffer.putInt(data.objectId);
            buffer.putDouble(data.x);
            buffer.putDouble(data.y);

            if (i < posList.size() - 1)
            {
                buffer.put((byte)31);
            }
        }
        buffer.put((byte)4); // ASCII Standard for End of transmission

        sendUdp(buffer.array());
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
                    byte value = buffer.get();
                    Opcodes opcode = Opcodes.fromInteger(value);

                    buffer.get(); // start of text

                    switch (opcode)
                    {
                        case BROADCAST_POSITION_UPDATE:
                        {
                            readPositionUpdate(buffer);
                            break;
                        }
                        case BROADCAST_SCORE_UPDATE:
                        {
                            readScoreUpdate(buffer);
                            break;
                        }
                        case BROADCAST_HEALTH_UPDATE:
                        {
                            readHealthUpdate(buffer);
                            break;
                        }
                        default:
                            break;
                    }
                }
                catch (IOException e)
                {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }
        }).start();
    }

    public void readScoreUpdate(ByteBuffer buffer)
    {
        HashMap<Integer, Double> scoreMap = new HashMap<>();

        do
        {
            int ID = buffer.getInt();
            double score = buffer.getDouble();

            scoreMap.put(ID, score);
        }
        while (buffer.get() == 31); // unit separator

        map.updateScores(scoreMap);

    }

    public void readHealthUpdate(ByteBuffer buffer)
    {

    }

    public void readPositionUpdate(ByteBuffer buffer)
    {
        List<GameObjectDAO> positions = new ArrayList<>();

        do
        {
            int id = buffer.getInt();
            double x = buffer.getDouble();
            double y = buffer.getDouble();
            GameObjectDAO objectPos = new GameObjectDAO();
            objectPos.objectId = id;
            objectPos.x = x;
            objectPos.y = y;
            positions.add(objectPos);
        }
        while (buffer.get() == 31); // unit separator

        map.updatePositions(positions);
    }

    /**
     * Sends a data with UDP, as a byte array, to the server.
     * 
     * @param buf
     *            The byte array to be sent.
     */
    public void sendUdp(byte[] data)
    {
        DatagramPacket packet = new DatagramPacket(data, data.length, ina, 15001); // TODO dynamically port.
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
     * Sends a data with Tcp, as a byte array, to the server.
     * 
     * @param buf
     *            The byte array to be sent.
     */
    public void sendTcp(byte[] data)
    {
        try
        {
            output.write(data);
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
    }

    /**
     * Creates a bullet and returns it Id.
     * 
     * @return The id of the bullet created.
     */
    public void requestBulletCreation(GameObjectDAO data)
    {
        ByteBuffer buffer = ByteBuffer.allocate(256);

        buffer.order(ByteOrder.LITTLE_ENDIAN);
        buffer.put((byte)1); // ASCII Standard for Start of heading
        buffer.put((byte)Opcodes.REQUEST_BULLET.getValue());
        buffer.put((byte)2); // ASCII Standard for Start of text

        buffer.putDouble(data.x);
        buffer.putDouble(data.y);
        buffer.putDouble(data.height);
        buffer.putDouble(data.velocityX);
        buffer.putDouble(data.velocityY);
        buffer.putInt(data.bulletType.ordinal());
        buffer.putInt(data.damage);
        buffer.putInt(data.ownerId);
        buffer.putInt(data.entityType.ordinal());

        buffer.put((byte)4); // ASCII Standard for End of transmission

        sendTcp(buffer.array());

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
                    byte[] buf = new byte[512];
                    input.read(buf);
                    if (buf.length == 0)
                    {
                        break;
                    }
                    receiveTCP(buf);
                }
            }
            catch (IOException e)
            {
                e.printStackTrace();
            }
        }).start();

    }

    private void receiveTCP(byte[] data)
    {
        ByteBuffer buffer = ByteBuffer.allocate(data.length);
        buffer.order(ByteOrder.LITTLE_ENDIAN);
        buffer.put(data);
        buffer.rewind();

        if (buffer.get() != 1) // start of heading
        {
            return;
        }
        byte value = buffer.get();
        Opcodes opcode = Opcodes.fromInteger(value);

        buffer.get(); // start of text

        switch (opcode)
        {
            case REQUEST_BULLET:
            {
                readBulletCreation(buffer);
                break;
            }
            case NEW_PLAYER:
            {
                readNewPlayer(buffer);
                break;
            }
            case DISCONNECTED_PLAYER:
            {
                readDisconnectedPlayer(buffer);
                break;
            }
            case KILL_NOTIFICATION:
            {
                readKillNotification(buffer);
                break;
            }
            default:
                break;

        }

    }

    /**
     * Handles new players created by the server.
     * 
     * @param input
     *            The ByteBuffer that handles reading of data send from the server.
     */
    private void readDisconnectedPlayer(ByteBuffer input) // Should probably tell GameClient about the new player instead
    {
        int playerId = input.getInt();
        int objectId = input.getInt();
        map.destroyGameObject(objectId);

    }

    /**
     * Handles new players created by the server.
     * 
     * @param input
     *            The ByteBuffer that handles reading of data send from the server.
     */
    private void readNewPlayer(ByteBuffer input) // Should probably tell GameClient about the new player instead
    {
        GameObjectDAO data = new GameObjectDAO();
        int playerId = input.getInt();
        data.objectId = input.getInt();
        data.x = input.getDouble();
        data.y = input.getDouble();
        data.width = input.getDouble();
        data.height = input.getDouble();
        int value = input.getInt();
        data.entityType = EntityFactory.EntityType.fromInteger(value);
        map.addGameObject(data);
    }

    /**
     * Handles new bullets created by the server.
     * 
     * @param input
     *            The ByteBuffer that handles reading of data send from the server.
     */
    private void readBulletCreation(ByteBuffer input)
    {
        GameObjectDAO data = new GameObjectDAO();
        data.x = input.getDouble();
        data.y = input.getDouble();
        data.height = input.getDouble();
        data.velocityX = input.getDouble();
        data.velocityY = input.getDouble();
        data.bulletType = Bullet.Type.fromInteger(input.getInt());
        data.damage = input.getInt();
        data.ownerId = input.getInt();
        data.objectId = input.getInt();
        data.entityType = EntityFactory.EntityType.fromInteger(input.getInt());
        map.addGameObject(data);
    }

    /**
     * Handles reading of kill notifications
     * 
     * @param input
     *            The ByteBuffer that handles reading of data send from the server.
     */
    private void readKillNotification(ByteBuffer input) // Should probably tell GameClient about the new player instead
    {
        int victimId = input.getInt();
        int killerId = input.getInt();
        map.killNotification(victimId, killerId);
    }
}
