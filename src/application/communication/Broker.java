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
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

import application.engine.entities.Bullet;
import application.engine.entities.specializations.Specializations;
import application.engine.factories.EntityFactory;
import application.engine.rendering.ClientMap;
import application.util.LightEvent;

/**
 * Handles all networking that isn't web service based and acts as a middleman between server and client objects, such as ClientMap, that needs to
 * communicate with the server.
 * 
 */
public class Broker
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
    public void sendUpdate(List<GameObjectDAO> posList) // TODO Should possibly
                                                        // know DAO of
                                                        // GameObject instead of
                                                        // ObjectPosition.
    {
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
        _socket.close();
    }

    private void sendPing()
    {
        ByteBuffer buffer = ByteBuffer.allocate(4);

        buffer.order(ByteOrder.LITTLE_ENDIAN);
        buffer.put((byte)1); // ASCII Standard for Start of heading
        buffer.put((byte)Opcodes.PING.getValue());
        buffer.put((byte)2); // ASCII Standard for Start of text
        buffer.put((byte)4); // ASCII Standard for End of transmission
        sendTcp(buffer);
    }

    /**
     * Handles reading of score updates;
     * 
     * @param input
     *            The ByteBuffer that handles reading of data send from the server.
     */
    private void readScoreUpdate(ByteBuffer buffer)
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

    /**
     * Handles reading of health updates
     * 
     * @param input
     *            The ByteBuffer that handles reading of data send from the server.
     */
    private void readHealthUpdate(ByteBuffer buffer)
    {
        List<GameObjectDAO> healths = new ArrayList<>();

        do
        {
            GameObjectDAO objectHealth = new GameObjectDAO();
            objectHealth.objectId = buffer.getInt();
            objectHealth.maxHealth = buffer.getInt();
            objectHealth.healthValue = buffer.getInt();
            healths.add(objectHealth);
        }
        while (buffer.get() == 31); // unit separator

        map.updateHealths(healths);
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
            if (b[i] != sessionId[i])
                throw new Error("Rest in pepperoni m9");
        }

        ByteBuffer buffer = ByteBuffer.allocate(256);
        buffer.order(ByteOrder.LITTLE_ENDIAN);
        buffer.put((byte)1);
        buffer.put((byte)Opcodes.UDP_CONNECT.getValue());
        buffer.put((byte)2);
        buffer.put(sessionId);
        buffer.put((byte)4);
        byte[] buf = buffer.array();
        _socket.send(new DatagramPacket(buf, buf.length, ina, SERVER_UDP_PORT));

        input.read(b);

        for (int i = 0; i < sessionId.length; ++i)
        {
            if (b[i] != sessionId[i])
                throw new Error("Rest in pepperoni m9");
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

    /**
     * Sends a data with UDP, as a byte array, to the server.
     * 
     * @param buf
     *            The byte array to be sent.
     */
    public void sendUdp(byte[] data)
    {
        DatagramPacket packet = new DatagramPacket(data, data.length, ina, SERVER_UDP_PORT); // TODO
                                                                                             // dynamically
                                                                                             // port.
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

    AtomicInteger send = new AtomicInteger(0);
    AtomicInteger received = new AtomicInteger(0);

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
                    case OBJECT_DESTRUCTION:
                    {
                        readDestroyedObject(buffer);
                        break;
                    }
                    default:
                        break;
                }
                buffer.get(); // Consuming end of transmission

            }
        }
        catch (BufferUnderflowException e)
        {

        }
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
        char[] nickname = new char[input.get()];
        for (int i = 0; i < nickname.length; ++i)
        {
            nickname[i] = (char)(input.get());
        }
        data.nickname = new String(nickname);
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
