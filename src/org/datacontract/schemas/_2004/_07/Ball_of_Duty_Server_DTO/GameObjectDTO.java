/**
 * GameObjectDTO.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package org.datacontract.schemas._2004._07.Ball_of_Duty_Server_DTO;

public class GameObjectDTO  implements java.io.Serializable {
    private org.datacontract.schemas._2004._07.Ball_of_Duty_Server_DTO.BodyDTO body;

    private int id;

    private int specialization;

    public GameObjectDTO() {
    }

    public GameObjectDTO(
           org.datacontract.schemas._2004._07.Ball_of_Duty_Server_DTO.BodyDTO body,
           int id,
           int specialization) {
           this.body = body;
           this.id = id;
           this.specialization = specialization;
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
            this.id == other.getId() &&
            this.specialization == other.getSpecialization();
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
        _hashCode += getId();
        _hashCode += getSpecialization();
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
