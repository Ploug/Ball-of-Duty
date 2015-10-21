
package org.datacontract.schemas._2004._07.ball_of_duty_server;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementRef;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for Account complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="Account">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="Hash" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="Id" type="{http://www.w3.org/2001/XMLSchema}int" minOccurs="0"/>
 *         &lt;element name="Salt" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="ServerPlayer" type="{http://schemas.datacontract.org/2004/07/Ball_of_Duty_Server.Persistence}ServerPlayer" minOccurs="0"/>
 *         &lt;element name="Username" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "Account", namespace = "http://schemas.datacontract.org/2004/07/Ball_of_Duty_Server.Persistence", propOrder = {
    "hash",
    "id",
    "salt",
    "serverPlayer",
    "username"
})
public class Account {

    @XmlElementRef(name = "Hash", namespace = "http://schemas.datacontract.org/2004/07/Ball_of_Duty_Server.Persistence", type = JAXBElement.class, required = false)
    protected JAXBElement<String> hash;
    @XmlElement(name = "Id")
    protected Integer id;
    @XmlElementRef(name = "Salt", namespace = "http://schemas.datacontract.org/2004/07/Ball_of_Duty_Server.Persistence", type = JAXBElement.class, required = false)
    protected JAXBElement<String> salt;
    @XmlElementRef(name = "ServerPlayer", namespace = "http://schemas.datacontract.org/2004/07/Ball_of_Duty_Server.Persistence", type = JAXBElement.class, required = false)
    protected JAXBElement<ServerPlayer> serverPlayer;
    @XmlElementRef(name = "Username", namespace = "http://schemas.datacontract.org/2004/07/Ball_of_Duty_Server.Persistence", type = JAXBElement.class, required = false)
    protected JAXBElement<String> username;

    /**
     * Gets the value of the hash property.
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public JAXBElement<String> getHash() {
        return hash;
    }

    /**
     * Sets the value of the hash property.
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public void setHash(JAXBElement<String> value) {
        this.hash = value;
    }

    /**
     * Gets the value of the id property.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getId() {
        return id;
    }

    /**
     * Sets the value of the id property.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setId(Integer value) {
        this.id = value;
    }

    /**
     * Gets the value of the salt property.
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public JAXBElement<String> getSalt() {
        return salt;
    }

    /**
     * Sets the value of the salt property.
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public void setSalt(JAXBElement<String> value) {
        this.salt = value;
    }

    /**
     * Gets the value of the serverPlayer property.
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link ServerPlayer }{@code >}
     *     
     */
    public JAXBElement<ServerPlayer> getServerPlayer() {
        return serverPlayer;
    }

    /**
     * Sets the value of the serverPlayer property.
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link ServerPlayer }{@code >}
     *     
     */
    public void setServerPlayer(JAXBElement<ServerPlayer> value) {
        this.serverPlayer = value;
    }

    /**
     * Gets the value of the username property.
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public JAXBElement<String> getUsername() {
        return username;
    }

    /**
     * Sets the value of the username property.
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public void setUsername(JAXBElement<String> value) {
        this.username = value;
    }

}
