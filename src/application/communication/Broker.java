package application.communication;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import java.nio.BufferUnderflowException;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import application.engine.entities.Bullet;
import application.engine.entities.specializations.Specializations;
import application.engine.factories.EntityFactory;
import application.engine.rendering.ClientMap;
import application.util.LightEvent;
import application.util.Observable;
import application.util.Observation;
import application.util.Timer;

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
    private Socket tcpSocket;
    private boolean isActive = false;
    private static final String SERVER_IP = "85.218.183.174";
    private static final int SERVER_UDP_PORT = 15001;
    private static final int SERVER_TCP_PORT = 15010;
    private DataOutputStream output = null;
    private long ping = 0; // in ms
    private long lastPing;
    private LightEvent _pingEvent = new LightEvent(1000, () ->
    {

        sendPing();
    });

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
            _socket = new DatagramSocket();
        }
        catch (IOException e)
        {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
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
        receiveUdp();
    }

    public void update(long deltaTime)
    {
        _pingEvent.update(deltaTime);
    }

    /**
     * Returns the udp port the broker is listening on.
     * 
     * @return The udp port the broker is listening on.
     */
    public int getUdpPort()
    {
        return _socket.getLocalPort();
    }

    /**
     * Returns the tcp port the broker is listening on.
     * 
     * @return The tcp port the broker is listening on.
     */
    public int getTcpPort()
    {
        return tcpSocket.getLocalPort();
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
    public void sendUpdate(List<GameObjectDAO> posList)
    {
        ByteBuffer buffer = ByteBuffer.allocate(1024);
        buffer.order(ByteOrder.LITTLE_ENDIAN);
        buffer.put((byte)1); // ASCII Standard for Start of heading
        buffer.putInt(Opcodes.POSITION_UPDATE.getValue());
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
    public void deactivate()
    {
        isActive = false;
        _socket.close();
        try
        {
            tcpSocket.close();
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
    }

    public long getPing()
    {
        return ping;
    }

    private void sendPing()
    {
        ByteBuffer buffer = ByteBuffer.allocate(20);

        buffer.order(ByteOrder.LITTLE_ENDIAN);
        buffer.put((byte)1); // ASCII Standard for Start of heading
        buffer.putInt(Opcodes.PING.getValue());
        buffer.put((byte)2); // ASCII Standard for Start of text
        buffer.put((byte)4); // ASCII Standard for End of transmission
        
        lastPing = System.currentTimeMillis();
        sendTcp(buffer);
        
    }

    /**
     * Handles reading of health updates
     * 
     * @param input
     *            The ByteBuffer that handles reading of data send from the server.
     */
    private void readCharacterStatUpdate(ByteBuffer buffer)
    {
        List<GameObjectDAO> characterStats = new ArrayList<>();

        do
        {
            GameObjectDAO characterStat = new GameObjectDAO();
            characterStat.objectId = buffer.getInt();
            characterStat.score = buffer.getDouble();
            characterStat.maxHealth = buffer.getInt();
            characterStat.healthValue = buffer.getInt();
            characterStats.add(characterStat);
        }
        while (buffer.get() == 31); // unit separator

        map.updateCharacterStats(characterStats);
    }

    public void readPositionUpdate(ByteBuffer buffer)
    {
        List<GameObjectDAO> positions = new ArrayList<>();

        do
        {
            GameObjectDAO objectPos = new GameObjectDAO();
            objectPos.objectId = buffer.getInt();
            objectPos.x = buffer.getDouble();
            objectPos.y = buffer.getDouble();
            positions.add(objectPos);
        }
        while (buffer.get() == 31); // unit separator

        map.updatePositions(positions);
    }

    // TODO: improve this thingy.
    public void readSessionId(byte[] sessionId) throws IOException
    {
        output.write(sessionId);

        DataInputStream input = new DataInputStream(tcpSocket.getInputStream());
        byte[] b = new byte[32];
        input.read(b);

        for (int i = 0; i < sessionId.length; ++i)
        {
            if (b[i] != sessionId[i]) throw new Error("Rest in pepperoni m9");
        }

        ByteBuffer buffer = ByteBuffer.allocate(256);
        buffer.order(ByteOrder.LITTLE_ENDIAN);
        buffer.put((byte)1);
        buffer.putInt(Opcodes.UDP_CONNECT.getValue());
        buffer.put((byte)2);
        buffer.put(sessionId);
        buffer.put((byte)4);
        byte[] buf = buffer.array();
        _socket.send(new DatagramPacket(buf, buf.length, ina, SERVER_UDP_PORT));

        input.read(b);

        for (int i = 0; i < sessionId.length; ++i)
        {
            if (b[i] != sessionId[i]) throw new Error("Rest in pepperoni m9");
        }
    }

    private void receiveUdp()
    {
        new Thread(() ->
        {
            while (isActive)
            {
                DatagramPacket packet = null;

                byte[] buf = new byte[1024];

                packet = new DatagramPacket(buf, buf.length);

                try
                {
                    _socket.receive(packet);
                }
                catch (IOException e)
                {

                    notifyObservers(Observation.SERVER_OFFLINE);
                }

                byte[] data = Arrays.copyOf(packet.getData(), packet.getLength());

                ByteBuffer buffer = ByteBuffer.allocate(1024);
                buffer.order(ByteOrder.LITTLE_ENDIAN);
                buffer.put(data);
                buffer.rewind();

                try
                {
                    while (buffer.get() == 1) // start of heading
                    {
                        Opcodes opcode = Opcodes.fromInteger(buffer.getInt());

                        buffer.get(); // start of text

                        switch (opcode)
                        {
                            case BROADCAST_POSITION_UPDATE:
                            {
                                readPositionUpdate(buffer);
                                break;
                            }
                            case BROADCAST_CHARACTER_STAT_UPDATE:
                            {
                                readCharacterStatUpdate(buffer);
                                break;
                            }
                            default:
                                break;
                        }

                        if (buffer.position() == data.length)
                        {
                            break;
                        }
                    }
                }
                catch (BufferUnderflowException ex)
                {
                    ex.printStackTrace();
                }
            }
        }).start();
    }

    /**
     * Sends a data with UDP, as a byte array, to the server.
     * 
     * @param buf
     *            The byte array to be sent.
     */
    public void sendUdp(byte[] data)
    {
        DatagramPacket packet = new DatagramPacket(data, data.length, ina, SERVER_UDP_PORT); // TODO dynamically port.
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
    public void sendTcp(ByteBuffer buffer)
    {
        // Remove trash data from the buffer before sending.
        // Otherwise bytesRead will be useless on the receiving side.
        byte[] data = Arrays.copyOf(buffer.array(), buffer.position());
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
        buffer.putInt(Opcodes.REQUEST_BULLET.getValue());
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
        sendTcp(buffer);
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
                    byte[] buf = new byte[1024];
                    int bytesRead = input.read(buf);
                    if (bytesRead <= 0)
                    {
                        break;
                    }
                    receiveTCP(Arrays.copyOf(buf, bytesRead));
                }
            }
            catch (IOException e)
            {
                this.notifyObservers(Observation.SERVER_OFFLINE);
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

        try
        {
            while (buffer.get() == 1) // start of heading
            {
                Opcodes opcode = Opcodes.fromInteger(buffer.getInt());

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
                    case OBJECT_DESTRUCTION:
                    {
                        readDestroyedObject(buffer);
                        break;
                    }
                    case SERVER_MESSAGE:
                    {
                        readServerMessage(buffer);
                        break;
                    }
                    case PING:
                    {
                        ping = System.currentTimeMillis()-lastPing;
                        break;
                    }
                    default:
                        break;
                }
                buffer.get(); // Consuming end of transmission

                if (buffer.position() == data.length)
                {
                    break;
                }
            }
        }
        catch (BufferUnderflowException e)
        {
            e.printStackTrace();
        }
    }

    private void readServerMessage(ByteBuffer input)
    {

        map.writeServerMessage(readString(input));
    }

    /**
     * Handles players disconnected from the server
     * 
     * @param input
     *            The ByteBuffer that handles reading of data send from the server.
     */
    private void readDisconnectedPlayer(ByteBuffer input)
    {
        int playerId = input.getInt();
        int objectId = input.getInt();
        map.destroyGameObject(objectId);
    }

    /**
     * Handles destroyed objects
     * 
     * @param input
     *            The ByteBuffer that handles reading of data send from the server.
     */
    private void readDestroyedObject(ByteBuffer input)
    {
        int objectId = input.getInt();
        map.destroyGameObject(objectId);
    }

    private String readString(ByteBuffer input)
    {
        int stringLength = (int)input.get();
        if (stringLength > 0)
        {
            char[] string = new char[stringLength];
            for (int i = 0; i < string.length; ++i)
            {
                string[i] = (char)(input.get());
            }
            return new String(string);
        }
        else
        {
            return new String();
        }
    }

    /**
     * Handles new players created by the server.
     * 
     * @param input
     *            The ByteBuffer that handles reading of data send from the server.
     */
    private void readNewPlayer(ByteBuffer input) // Should probably tell
                                                 // GameClient about the new
                                                 // player instead
    {
        GameObjectDAO data = new GameObjectDAO();

        data.nickname = readString(input);

        data.objectId = input.getInt();
        data.x = input.getDouble();
        data.y = input.getDouble();
        data.width = input.getDouble();
        data.height = input.getDouble();
        data.specialization = Specializations.fromInteger(input.getInt());
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
    private void readKillNotification(ByteBuffer input)
    {
        int victimId = input.getInt();
        int killerId = input.getInt();
        map.killNotification(victimId, killerId);
    }
}
