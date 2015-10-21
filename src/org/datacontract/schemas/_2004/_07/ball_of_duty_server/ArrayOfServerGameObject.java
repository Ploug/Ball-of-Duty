
package org.datacontract.schemas._2004._07.ball_of_duty_server;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for ArrayOfServerGameObject complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="ArrayOfServerGameObject">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="ServerGameObject" type="{http://schemas.datacontract.org/2004/07/Ball_of_Duty_Server.Domain}ServerGameObject" maxOccurs="unbounded" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ArrayOfServerGameObject", propOrder = {
    "serverGameObject"
})
public class ArrayOfServerGameObject {

    @XmlElement(name = "ServerGameObject", nillable = true)
    protected List<ServerGameObject> serverGameObject;

    /**
     * Gets the value of the serverGameObject property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the serverGameObject property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getServerGameObject().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link ServerGameObject }
     * 
     * 
     */
    public List<ServerGameObject> getServerGameObject() {
        if (serverGameObject == null) {
            serverGameObject = new ArrayList<ServerGameObject>();
        }
        return this.serverGameObject;
    }

}
