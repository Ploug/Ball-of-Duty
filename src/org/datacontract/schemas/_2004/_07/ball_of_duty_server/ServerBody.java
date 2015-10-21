
package org.datacontract.schemas._2004._07.ball_of_duty_server;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;
import org.datacontract.schemas._2004._07.system.Point;


/**
 * <p>Java class for ServerBody complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="ServerBody">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="gameObject" type="{http://schemas.datacontract.org/2004/07/Ball_of_Duty_Server.Domain}ServerGameObject"/>
 *         &lt;element name="position" type="{http://schemas.datacontract.org/2004/07/System.Windows}Point"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ServerBody", propOrder = {
    "gameObject",
    "position"
})
public class ServerBody {

    @XmlElement(required = true, nillable = true)
    protected ServerGameObject gameObject;
    @XmlElement(required = true)
    protected Point position;

    /**
     * Gets the value of the gameObject property.
     * 
     * @return
     *     possible object is
     *     {@link ServerGameObject }
     *     
     */
    public ServerGameObject getGameObject() {
        return gameObject;
    }

    /**
     * Sets the value of the gameObject property.
     * 
     * @param value
     *     allowed object is
     *     {@link ServerGameObject }
     *     
     */
    public void setGameObject(ServerGameObject value) {
        this.gameObject = value;
    }

    /**
     * Gets the value of the position property.
     * 
     * @return
     *     possible object is
     *     {@link Point }
     *     
     */
    public Point getPosition() {
        return position;
    }

    /**
     * Sets the value of the position property.
     * 
     * @param value
     *     allowed object is
     *     {@link Point }
     *     
     */
    public void setPosition(Point value) {
        this.position = value;
    }

}
