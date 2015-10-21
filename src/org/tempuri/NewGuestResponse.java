
package org.tempuri;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElementRef;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;
import org.datacontract.schemas._2004._07.ball_of_duty_server.ServerPlayer;


/**
 * <p>Java class for anonymous complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType>
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="newGuestResult" type="{http://schemas.datacontract.org/2004/07/Ball_of_Duty_Server.Persistence}ServerPlayer" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
    "newGuestResult"
})
@XmlRootElement(name = "newGuestResponse")
public class NewGuestResponse {

    @XmlElementRef(name = "newGuestResult", namespace = "http://tempuri.org/", type = JAXBElement.class, required = false)
    protected JAXBElement<ServerPlayer> newGuestResult;

    /**
     * Gets the value of the newGuestResult property.
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link ServerPlayer }{@code >}
     *     
     */
    public JAXBElement<ServerPlayer> getNewGuestResult() {
        return newGuestResult;
    }

    /**
     * Sets the value of the newGuestResult property.
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link ServerPlayer }{@code >}
     *     
     */
    public void setNewGuestResult(JAXBElement<ServerPlayer> value) {
        this.newGuestResult = value;
    }

}
