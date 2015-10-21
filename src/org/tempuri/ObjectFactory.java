
package org.tempuri;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.namespace.QName;
import org.datacontract.schemas._2004._07.ball_of_duty_server.ArrayOfServerGameObject;
import org.datacontract.schemas._2004._07.ball_of_duty_server.ServerPlayer;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the org.tempuri package. 
 * <p>An ObjectFactory allows you to programatically 
 * construct new instances of the Java representation 
 * for XML content. The Java representation of XML 
 * content can consist of schema derived interfaces 
 * and classes representing the binding of schema 
 * type definitions, element declarations and model 
 * groups.  Factory methods for each of these are 
 * provided in this class.
 * 
 */
@XmlRegistry
public class ObjectFactory {

    private final static QName _JoinGameClientPlayer_QNAME = new QName("http://tempuri.org/", "clientPlayer");
    private final static QName _NewGuestResponseNewGuestResult_QNAME = new QName("http://tempuri.org/", "newGuestResult");
    private final static QName _JoinGameResponseJoinGameResult_QNAME = new QName("http://tempuri.org/", "joinGameResult");

    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: org.tempuri
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link JoinGame }
     * 
     */
    public JoinGame createJoinGame() {
        return new JoinGame();
    }

    /**
     * Create an instance of {@link NewGuestResponse }
     * 
     */
    public NewGuestResponse createNewGuestResponse() {
        return new NewGuestResponse();
    }

    /**
     * Create an instance of {@link JoinGameResponse }
     * 
     */
    public JoinGameResponse createJoinGameResponse() {
        return new JoinGameResponse();
    }

    /**
     * Create an instance of {@link NewGuest }
     * 
     */
    public NewGuest createNewGuest() {
        return new NewGuest();
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link ServerPlayer }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://tempuri.org/", name = "clientPlayer", scope = JoinGame.class)
    public JAXBElement<ServerPlayer> createJoinGameClientPlayer(ServerPlayer value) {
        return new JAXBElement<ServerPlayer>(_JoinGameClientPlayer_QNAME, ServerPlayer.class, JoinGame.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link ServerPlayer }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://tempuri.org/", name = "newGuestResult", scope = NewGuestResponse.class)
    public JAXBElement<ServerPlayer> createNewGuestResponseNewGuestResult(ServerPlayer value) {
        return new JAXBElement<ServerPlayer>(_NewGuestResponseNewGuestResult_QNAME, ServerPlayer.class, NewGuestResponse.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link ArrayOfServerGameObject }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://tempuri.org/", name = "joinGameResult", scope = JoinGameResponse.class)
    public JAXBElement<ArrayOfServerGameObject> createJoinGameResponseJoinGameResult(ArrayOfServerGameObject value) {
        return new JAXBElement<ArrayOfServerGameObject>(_JoinGameResponseJoinGameResult_QNAME, ArrayOfServerGameObject.class, JoinGameResponse.class, value);
    }

}
