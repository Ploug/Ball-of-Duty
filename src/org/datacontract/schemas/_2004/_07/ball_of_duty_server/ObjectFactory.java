
package org.datacontract.schemas._2004._07.ball_of_duty_server;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.namespace.QName;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the org.datacontract.schemas._2004._07.ball_of_duty_server package. 
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

    private final static QName _ServerPlayer_QNAME = new QName("http://schemas.datacontract.org/2004/07/Ball_of_Duty_Server.Persistence", "ServerPlayer");
    private final static QName _ArrayOfServerGameObject_QNAME = new QName("http://schemas.datacontract.org/2004/07/Ball_of_Duty_Server.Domain", "ArrayOfServerGameObject");
    private final static QName _ServerBody_QNAME = new QName("http://schemas.datacontract.org/2004/07/Ball_of_Duty_Server.Domain", "ServerBody");
    private final static QName _Account_QNAME = new QName("http://schemas.datacontract.org/2004/07/Ball_of_Duty_Server.Persistence", "Account");
    private final static QName _ServerGameObject_QNAME = new QName("http://schemas.datacontract.org/2004/07/Ball_of_Duty_Server.Domain", "ServerGameObject");
    private final static QName _AccountUsername_QNAME = new QName("http://schemas.datacontract.org/2004/07/Ball_of_Duty_Server.Persistence", "Username");
    private final static QName _AccountSalt_QNAME = new QName("http://schemas.datacontract.org/2004/07/Ball_of_Duty_Server.Persistence", "Salt");
    private final static QName _AccountHash_QNAME = new QName("http://schemas.datacontract.org/2004/07/Ball_of_Duty_Server.Persistence", "Hash");

    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: org.datacontract.schemas._2004._07.ball_of_duty_server
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link ServerPlayer }
     * 
     */
    public ServerPlayer createServerPlayer() {
        return new ServerPlayer();
    }

    /**
     * Create an instance of {@link ArrayOfServerGameObject }
     * 
     */
    public ArrayOfServerGameObject createArrayOfServerGameObject() {
        return new ArrayOfServerGameObject();
    }

    /**
     * Create an instance of {@link Account }
     * 
     */
    public Account createAccount() {
        return new Account();
    }

    /**
     * Create an instance of {@link ServerBody }
     * 
     */
    public ServerBody createServerBody() {
        return new ServerBody();
    }

    /**
     * Create an instance of {@link ServerGameObject }
     * 
     */
    public ServerGameObject createServerGameObject() {
        return new ServerGameObject();
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link ServerPlayer }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://schemas.datacontract.org/2004/07/Ball_of_Duty_Server.Persistence", name = "ServerPlayer")
    public JAXBElement<ServerPlayer> createServerPlayer(ServerPlayer value) {
        return new JAXBElement<ServerPlayer>(_ServerPlayer_QNAME, ServerPlayer.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link ArrayOfServerGameObject }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://schemas.datacontract.org/2004/07/Ball_of_Duty_Server.Domain", name = "ArrayOfServerGameObject")
    public JAXBElement<ArrayOfServerGameObject> createArrayOfServerGameObject(ArrayOfServerGameObject value) {
        return new JAXBElement<ArrayOfServerGameObject>(_ArrayOfServerGameObject_QNAME, ArrayOfServerGameObject.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link ServerBody }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://schemas.datacontract.org/2004/07/Ball_of_Duty_Server.Domain", name = "ServerBody")
    public JAXBElement<ServerBody> createServerBody(ServerBody value) {
        return new JAXBElement<ServerBody>(_ServerBody_QNAME, ServerBody.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link Account }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://schemas.datacontract.org/2004/07/Ball_of_Duty_Server.Persistence", name = "Account")
    public JAXBElement<Account> createAccount(Account value) {
        return new JAXBElement<Account>(_Account_QNAME, Account.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link ServerGameObject }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://schemas.datacontract.org/2004/07/Ball_of_Duty_Server.Domain", name = "ServerGameObject")
    public JAXBElement<ServerGameObject> createServerGameObject(ServerGameObject value) {
        return new JAXBElement<ServerGameObject>(_ServerGameObject_QNAME, ServerGameObject.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://schemas.datacontract.org/2004/07/Ball_of_Duty_Server.Persistence", name = "Username", scope = Account.class)
    public JAXBElement<String> createAccountUsername(String value) {
        return new JAXBElement<String>(_AccountUsername_QNAME, String.class, Account.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link ServerPlayer }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://schemas.datacontract.org/2004/07/Ball_of_Duty_Server.Persistence", name = "ServerPlayer", scope = Account.class)
    public JAXBElement<ServerPlayer> createAccountServerPlayer(ServerPlayer value) {
        return new JAXBElement<ServerPlayer>(_ServerPlayer_QNAME, ServerPlayer.class, Account.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://schemas.datacontract.org/2004/07/Ball_of_Duty_Server.Persistence", name = "Salt", scope = Account.class)
    public JAXBElement<String> createAccountSalt(String value) {
        return new JAXBElement<String>(_AccountSalt_QNAME, String.class, Account.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://schemas.datacontract.org/2004/07/Ball_of_Duty_Server.Persistence", name = "Hash", scope = Account.class)
    public JAXBElement<String> createAccountHash(String value) {
        return new JAXBElement<String>(_AccountHash_QNAME, String.class, Account.class, value);
    }

}
