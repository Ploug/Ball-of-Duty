/**
 * BodyDTO.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package org.datacontract.schemas._2004._07.Ball_of_Duty_Server_DTO;

public class BodyDTO  implements java.io.Serializable {
    private double height;

    private org.datacontract.schemas._2004._07.Ball_of_Duty_Server_DTO.PointDTO position;

    private int type;

    private double width;

    public BodyDTO() {
    }

    public BodyDTO(
           double height,
           org.datacontract.schemas._2004._07.Ball_of_Duty_Server_DTO.PointDTO position,
           int type,
           double width) {
           this.height = height;
           this.position = position;
           this.type = type;
           this.width = width;
    }


    /**
     * Gets the height value for this BodyDTO.
     * 
     * @return height
     */
    public double getHeight() {
        return height;
    }


    /**
     * Sets the height value for this BodyDTO.
     * 
     * @param height
     */
    public void setHeight(double height) {
        this.height = height;
    }


    /**
     * Gets the position value for this BodyDTO.
     * 
     * @return position
     */
    public org.datacontract.schemas._2004._07.Ball_of_Duty_Server_DTO.PointDTO getPosition() {
        return position;
    }


    /**
     * Sets the position value for this BodyDTO.
     * 
     * @param position
     */
    public void setPosition(org.datacontract.schemas._2004._07.Ball_of_Duty_Server_DTO.PointDTO position) {
        this.position = position;
    }


    /**
     * Gets the type value for this BodyDTO.
     * 
     * @return type
     */
    public int getType() {
        return type;
    }


    /**
     * Sets the type value for this BodyDTO.
     * 
     * @param type
     */
    public void setType(int type) {
        this.type = type;
    }


    /**
     * Gets the width value for this BodyDTO.
     * 
     * @return width
     */
    public double getWidth() {
        return width;
    }


    /**
     * Sets the width value for this BodyDTO.
     * 
     * @param width
     */
    public void setWidth(double width) {
        this.width = width;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof BodyDTO)) return false;
        BodyDTO other = (BodyDTO) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            this.height == other.getHeight() &&
            ((this.position==null && other.getPosition()==null) || 
             (this.position!=null &&
              this.position.equals(other.getPosition()))) &&
            this.type == other.getType() &&
            this.width == other.getWidth();
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
        _hashCode += new Double(getHeight()).hashCode();
        if (getPosition() != null) {
            _hashCode += getPosition().hashCode();
        }
        _hashCode += getType();
        _hashCode += new Double(getWidth()).hashCode();
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(BodyDTO.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://schemas.datacontract.org/2004/07/Ball_of_Duty_Server.DTO", "BodyDTO"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("height");
        elemField.setXmlName(new javax.xml.namespace.QName("http://schemas.datacontract.org/2004/07/Ball_of_Duty_Server.DTO", "Height"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "double"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("position");
        elemField.setXmlName(new javax.xml.namespace.QName("http://schemas.datacontract.org/2004/07/Ball_of_Duty_Server.DTO", "Position"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://schemas.datacontract.org/2004/07/Ball_of_Duty_Server.DTO", "PointDTO"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("type");
        elemField.setXmlName(new javax.xml.namespace.QName("http://schemas.datacontract.org/2004/07/Ball_of_Duty_Server.DTO", "Type"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "int"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("width");
        elemField.setXmlName(new javax.xml.namespace.QName("http://schemas.datacontract.org/2004/07/Ball_of_Duty_Server.DTO", "Width"));
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
