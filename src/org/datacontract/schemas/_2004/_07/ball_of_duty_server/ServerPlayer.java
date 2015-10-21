
package org.datacontract.schemas._2004._07.ball_of_duty_server;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for ServerPlayer complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="ServerPlayer">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="_x003C_Account_x003E_k__BackingField" type="{http://schemas.datacontract.org/2004/07/Ball_of_Duty_Server.Persistence}Account"/>
 *         &lt;element name="_x003C_Id_x003E_k__BackingField" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="_x003C_Nickname_x003E_k__BackingField" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ServerPlayer", namespace = "http://schemas.datacontract.org/2004/07/Ball_of_Duty_Server.Persistence", propOrder = {
    "x003CAccountX003EKBackingField",
    "x003CIdX003EKBackingField",
    "x003CNicknameX003EKBackingField"
})
public class ServerPlayer {

    @XmlElement(name = "_x003C_Account_x003E_k__BackingField", required = true, nillable = true)
    protected Account x003CAccountX003EKBackingField;
    @XmlElement(name = "_x003C_Id_x003E_k__BackingField")
    protected int x003CIdX003EKBackingField;
    @XmlElement(name = "_x003C_Nickname_x003E_k__BackingField", required = true, nillable = true)
    protected String x003CNicknameX003EKBackingField;

    /**
     * Gets the value of the x003CAccountX003EKBackingField property.
     * 
     * @return
     *     possible object is
     *     {@link Account }
     *     
     */
    public Account getX003CAccountX003EKBackingField() {
        return x003CAccountX003EKBackingField;
    }

    /**
     * Sets the value of the x003CAccountX003EKBackingField property.
     * 
     * @param value
     *     allowed object is
     *     {@link Account }
     *     
     */
    public void setX003CAccountX003EKBackingField(Account value) {
        this.x003CAccountX003EKBackingField = value;
    }

    /**
     * Gets the value of the x003CIdX003EKBackingField property.
     * 
     */
    public int getX003CIdX003EKBackingField() {
        return x003CIdX003EKBackingField;
    }

    /**
     * Sets the value of the x003CIdX003EKBackingField property.
     * 
     */
    public void setX003CIdX003EKBackingField(int value) {
        this.x003CIdX003EKBackingField = value;
    }

    /**
     * Gets the value of the x003CNicknameX003EKBackingField property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getX003CNicknameX003EKBackingField() {
        return x003CNicknameX003EKBackingField;
    }

    /**
     * Sets the value of the x003CNicknameX003EKBackingField property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setX003CNicknameX003EKBackingField(String value) {
        this.x003CNicknameX003EKBackingField = value;
    }

}
