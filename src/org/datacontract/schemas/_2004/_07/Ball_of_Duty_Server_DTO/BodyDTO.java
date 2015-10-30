/**
 * BodyDTO.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package org.datacontract.schemas._2004._07.Ball_of_Duty_Server_DTO;

public class BodyDTO  implements java.io.Serializable {
    private int CIRCLE;

    private int RECTANGLE;

    private int _height;

    private org.datacontract.schemas._2004._07.Ball_of_Duty_Server_DTO.PointDTO _point;

    private int _type;

    private int _width;

    public BodyDTO() {
    }

    public BodyDTO(
           int CIRCLE,
           int RECTANGLE,
           int _height,
           org.datacontract.schemas._2004._07.Ball_of_Duty_Server_DTO.PointDTO _point,
           int _type,
           int _width) {
           this.CIRCLE = CIRCLE;
           this.RECTANGLE = RECTANGLE;
           this._height = _height;
           this._point = _point;
           this._type = _type;
           this._width = _width;
    }


    /**
     * Gets the CIRCLE value for this BodyDTO.
     * 
     * @return CIRCLE
     */
    public int getCIRCLE() {
        return CIRCLE;
    }


    /**
     * Sets the CIRCLE value for this BodyDTO.
     * 
     * @param CIRCLE
     */
    public void setCIRCLE(int CIRCLE) {
        this.CIRCLE = CIRCLE;
    }


    /**
     * Gets the RECTANGLE value for this BodyDTO.
     * 
     * @return RECTANGLE
     */
    public int getRECTANGLE() {
        return RECTANGLE;
    }


    /**
     * Sets the RECTANGLE value for this BodyDTO.
     * 
     * @param RECTANGLE
     */
    public void setRECTANGLE(int RECTANGLE) {
        this.RECTANGLE = RECTANGLE;
    }


    /**
     * Gets the _height value for this BodyDTO.
     * 
     * @return _height
     */
    public int get_height() {
        return _height;
    }


    /**
     * Sets the _height value for this BodyDTO.
     * 
     * @param _height
     */
    public void set_height(int _height) {
        this._height = _height;
    }


    /**
     * Gets the _point value for this BodyDTO.
     * 
     * @return _point
     */
    public org.datacontract.schemas._2004._07.Ball_of_Duty_Server_DTO.PointDTO get_point() {
        return _point;
    }


    /**
     * Sets the _point value for this BodyDTO.
     * 
     * @param _point
     */
    public void set_point(org.datacontract.schemas._2004._07.Ball_of_Duty_Server_DTO.PointDTO _point) {
        this._point = _point;
    }


    /**
     * Gets the _type value for this BodyDTO.
     * 
     * @return _type
     */
    public int get_type() {
        return _type;
    }


    /**
     * Sets the _type value for this BodyDTO.
     * 
     * @param _type
     */
    public void set_type(int _type) {
        this._type = _type;
    }


    /**
     * Gets the _width value for this BodyDTO.
     * 
     * @return _width
     */
    public int get_width() {
        return _width;
    }


    /**
     * Sets the _width value for this BodyDTO.
     * 
     * @param _width
     */
    public void set_width(int _width) {
        this._width = _width;
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
            this.CIRCLE == other.getCIRCLE() &&
            this.RECTANGLE == other.getRECTANGLE() &&
            this._height == other.get_height() &&
            ((this._point==null && other.get_point()==null) || 
             (this._point!=null &&
              this._point.equals(other.get_point()))) &&
            this._type == other.get_type() &&
            this._width == other.get_width();
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
        _hashCode += getCIRCLE();
        _hashCode += getRECTANGLE();
        _hashCode += get_height();
        if (get_point() != null) {
            _hashCode += get_point().hashCode();
        }
        _hashCode += get_type();
        _hashCode += get_width();
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(BodyDTO.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://schemas.datacontract.org/2004/07/Ball_of_Duty_Server.DTO", "BodyDTO"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("CIRCLE");
        elemField.setXmlName(new javax.xml.namespace.QName("http://schemas.datacontract.org/2004/07/Ball_of_Duty_Server.DTO", "CIRCLE"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "int"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("RECTANGLE");
        elemField.setXmlName(new javax.xml.namespace.QName("http://schemas.datacontract.org/2004/07/Ball_of_Duty_Server.DTO", "RECTANGLE"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "int"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("_height");
        elemField.setXmlName(new javax.xml.namespace.QName("http://schemas.datacontract.org/2004/07/Ball_of_Duty_Server.DTO", "_height"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "int"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("_point");
        elemField.setXmlName(new javax.xml.namespace.QName("http://schemas.datacontract.org/2004/07/Ball_of_Duty_Server.DTO", "_point"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://schemas.datacontract.org/2004/07/Ball_of_Duty_Server.DTO", "PointDTO"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("_type");
        elemField.setXmlName(new javax.xml.namespace.QName("http://schemas.datacontract.org/2004/07/Ball_of_Duty_Server.DTO", "_type"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "int"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("_width");
        elemField.setXmlName(new javax.xml.namespace.QName("http://schemas.datacontract.org/2004/07/Ball_of_Duty_Server.DTO", "_width"));
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
