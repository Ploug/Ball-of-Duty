/**
 * GameObjectDTO.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package org.datacontract.schemas._2004._07.Ball_of_Duty_Server_DTO;

public class GameObjectDTO  implements java.io.Serializable {
    private org.datacontract.schemas._2004._07.Ball_of_Duty_Server_DTO.BodyDTO body;

    private int bulletType;

    private int id;

    private int specialization;

    private int type;

    private double velX;

    private double velY;

    public GameObjectDTO() {
    }

    public GameObjectDTO(
           org.datacontract.schemas._2004._07.Ball_of_Duty_Server_DTO.BodyDTO body,
           int bulletType,
           int id,
           int specialization,
           int type,
           double velX,
           double velY) {
           this.body = body;
           this.bulletType = bulletType;
           this.id = id;
           this.specialization = specialization;
           this.type = type;
           this.velX = velX;
           this.velY = velY;
    }


    /**
     * Gets the body value for this GameObjectDTO.
     * 
     * @return body
     */
    public org.datacontract.schemas._2004._07.Ball_of_Duty_Server_DTO.BodyDTO getBody() {
        return body;
    }


    /**
     * Sets the body value for this GameObjectDTO.
     * 
     * @param body
     */
    public void setBody(org.datacontract.schemas._2004._07.Ball_of_Duty_Server_DTO.BodyDTO body) {
        this.body = body;
    }


    /**
     * Gets the bulletType value for this GameObjectDTO.
     * 
     * @return bulletType
     */
    public int getBulletType() {
        return bulletType;
    }


    /**
     * Sets the bulletType value for this GameObjectDTO.
     * 
     * @param bulletType
     */
    public void setBulletType(int bulletType) {
        this.bulletType = bulletType;
    }


    /**
     * Gets the id value for this GameObjectDTO.
     * 
     * @return id
     */
    public int getId() {
        return id;
    }


    /**
     * Sets the id value for this GameObjectDTO.
     * 
     * @param id
     */
    public void setId(int id) {
        this.id = id;
    }


    /**
     * Gets the specialization value for this GameObjectDTO.
     * 
     * @return specialization
     */
    public int getSpecialization() {
        return specialization;
    }


    /**
     * Sets the specialization value for this GameObjectDTO.
     * 
     * @param specialization
     */
    public void setSpecialization(int specialization) {
        this.specialization = specialization;
    }


    /**
     * Gets the type value for this GameObjectDTO.
     * 
     * @return type
     */
    public int getType() {
        return type;
    }


    /**
     * Sets the type value for this GameObjectDTO.
     * 
     * @param type
     */
    public void setType(int type) {
        this.type = type;
    }


    /**
     * Gets the velX value for this GameObjectDTO.
     * 
     * @return velX
     */
    public double getVelX() {
        return velX;
    }


    /**
     * Sets the velX value for this GameObjectDTO.
     * 
     * @param velX
     */
    public void setVelX(double velX) {
        this.velX = velX;
    }


    /**
     * Gets the velY value for this GameObjectDTO.
     * 
     * @return velY
     */
    public double getVelY() {
        return velY;
    }


    /**
     * Sets the velY value for this GameObjectDTO.
     * 
     * @param velY
     */
    public void setVelY(double velY) {
        this.velY = velY;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof GameObjectDTO)) return false;
        GameObjectDTO other = (GameObjectDTO) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.body==null && other.getBody()==null) || 
             (this.body!=null &&
              this.body.equals(other.getBody()))) &&
            this.bulletType == other.getBulletType() &&
            this.id == other.getId() &&
            this.specialization == other.getSpecialization() &&
            this.type == other.getType() &&
            this.velX == other.getVelX() &&
            this.velY == other.getVelY();
        __equalsCalc = null;
        return _equals;
    }

    private boolean __hashCodeCalc = false;
    public synchronized int hashCode() {
        if (__hashCodeCalc) {
            return 0;
        }
        __hashCodeCalc = true;
        int _hashCode = 1;
        if (getBody() != null) {
            _hashCode += getBody().hashCode();
        }
        _hashCode += getBulletType();
        _hashCode += getId();
        _hashCode += getSpecialization();
        _hashCode += getType();
        _hashCode += new Double(getVelX()).hashCode();
        _hashCode += new Double(getVelY()).hashCode();
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(GameObjectDTO.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://schemas.datacontract.org/2004/07/Ball_of_Duty_Server.DTO", "GameObjectDTO"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("body");
        elemField.setXmlName(new javax.xml.namespace.QName("http://schemas.datacontract.org/2004/07/Ball_of_Duty_Server.DTO", "Body"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://schemas.datacontract.org/2004/07/Ball_of_Duty_Server.DTO", "BodyDTO"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("bulletType");
        elemField.setXmlName(new javax.xml.namespace.QName("http://schemas.datacontract.org/2004/07/Ball_of_Duty_Server.DTO", "BulletType"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "int"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("id");
        elemField.setXmlName(new javax.xml.namespace.QName("http://schemas.datacontract.org/2004/07/Ball_of_Duty_Server.DTO", "Id"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "int"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("specialization");
        elemField.setXmlName(new javax.xml.namespace.QName("http://schemas.datacontract.org/2004/07/Ball_of_Duty_Server.DTO", "Specialization"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "int"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("type");
        elemField.setXmlName(new javax.xml.namespace.QName("http://schemas.datacontract.org/2004/07/Ball_of_Duty_Server.DTO", "Type"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "int"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("velX");
        elemField.setXmlName(new javax.xml.namespace.QName("http://schemas.datacontract.org/2004/07/Ball_of_Duty_Server.DTO", "VelX"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "double"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("velY");
        elemField.setXmlName(new javax.xml.namespace.QName("http://schemas.datacontract.org/2004/07/Ball_of_Duty_Server.DTO", "VelY"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "double"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
    }

    /**
     * Return type metadata object
     */
    public static org.apache.axis.description.TypeDesc getTypeDesc() {
        return typeDesc;
    }

    /**
     * Get Custom Serializer
     */
    public static org.apache.axis.encoding.Serializer getSerializer(
           java.lang.String mechType, 
           java.lang.Class _javaType,  
           javax.xml.namespace.QName _xmlType) {
        return 
          new  org.apache.axis.encoding.ser.BeanSerializer(
            _javaType, _xmlType, typeDesc);
    }

    /**
     * Get Custom Deserializer
     */
    public static org.apache.axis.encoding.Deserializer getDeserializer(
           java.lang.String mechType, 
           java.lang.Class _javaType,  
           javax.xml.namespace.QName _xmlType) {
        return 
          new  org.apache.axis.encoding.ser.BeanDeserializer(
            _javaType, _xmlType, typeDesc);
    }

}
