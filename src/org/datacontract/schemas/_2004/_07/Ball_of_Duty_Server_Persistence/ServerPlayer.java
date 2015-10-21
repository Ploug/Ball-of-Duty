/**
 * ServerPlayer.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package org.datacontract.schemas._2004._07.Ball_of_Duty_Server_Persistence;

public class ServerPlayer  implements java.io.Serializable {
    private org.datacontract.schemas._2004._07.Ball_of_Duty_Server_Persistence.Account _x003C_Account_x003E_k__BackingField;

    private int _x003C_Id_x003E_k__BackingField;

    private java.lang.String _x003C_Nickname_x003E_k__BackingField;

    public ServerPlayer() {
    }

    public ServerPlayer(
           org.datacontract.schemas._2004._07.Ball_of_Duty_Server_Persistence.Account _x003C_Account_x003E_k__BackingField,
           int _x003C_Id_x003E_k__BackingField,
           java.lang.String _x003C_Nickname_x003E_k__BackingField) {
           this._x003C_Account_x003E_k__BackingField = _x003C_Account_x003E_k__BackingField;
           this._x003C_Id_x003E_k__BackingField = _x003C_Id_x003E_k__BackingField;
           this._x003C_Nickname_x003E_k__BackingField = _x003C_Nickname_x003E_k__BackingField;
    }


    /**
     * Gets the _x003C_Account_x003E_k__BackingField value for this ServerPlayer.
     * 
     * @return _x003C_Account_x003E_k__BackingField
     */
    public org.datacontract.schemas._2004._07.Ball_of_Duty_Server_Persistence.Account get_x003C_Account_x003E_k__BackingField() {
        return _x003C_Account_x003E_k__BackingField;
    }


    /**
     * Sets the _x003C_Account_x003E_k__BackingField value for this ServerPlayer.
     * 
     * @param _x003C_Account_x003E_k__BackingField
     */
    public void set_x003C_Account_x003E_k__BackingField(org.datacontract.schemas._2004._07.Ball_of_Duty_Server_Persistence.Account _x003C_Account_x003E_k__BackingField) {
        this._x003C_Account_x003E_k__BackingField = _x003C_Account_x003E_k__BackingField;
    }


    /**
     * Gets the _x003C_Id_x003E_k__BackingField value for this ServerPlayer.
     * 
     * @return _x003C_Id_x003E_k__BackingField
     */
    public int get_x003C_Id_x003E_k__BackingField() {
        return _x003C_Id_x003E_k__BackingField;
    }


    /**
     * Sets the _x003C_Id_x003E_k__BackingField value for this ServerPlayer.
     * 
     * @param _x003C_Id_x003E_k__BackingField
     */
    public void set_x003C_Id_x003E_k__BackingField(int _x003C_Id_x003E_k__BackingField) {
        this._x003C_Id_x003E_k__BackingField = _x003C_Id_x003E_k__BackingField;
    }


    /**
     * Gets the _x003C_Nickname_x003E_k__BackingField value for this ServerPlayer.
     * 
     * @return _x003C_Nickname_x003E_k__BackingField
     */
    public java.lang.String get_x003C_Nickname_x003E_k__BackingField() {
        return _x003C_Nickname_x003E_k__BackingField;
    }


    /**
     * Sets the _x003C_Nickname_x003E_k__BackingField value for this ServerPlayer.
     * 
     * @param _x003C_Nickname_x003E_k__BackingField
     */
    public void set_x003C_Nickname_x003E_k__BackingField(java.lang.String _x003C_Nickname_x003E_k__BackingField) {
        this._x003C_Nickname_x003E_k__BackingField = _x003C_Nickname_x003E_k__BackingField;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof ServerPlayer)) return false;
        ServerPlayer other = (ServerPlayer) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this._x003C_Account_x003E_k__BackingField==null && other.get_x003C_Account_x003E_k__BackingField()==null) || 
             (this._x003C_Account_x003E_k__BackingField!=null &&
              this._x003C_Account_x003E_k__BackingField.equals(other.get_x003C_Account_x003E_k__BackingField()))) &&
            this._x003C_Id_x003E_k__BackingField == other.get_x003C_Id_x003E_k__BackingField() &&
            ((this._x003C_Nickname_x003E_k__BackingField==null && other.get_x003C_Nickname_x003E_k__BackingField()==null) || 
             (this._x003C_Nickname_x003E_k__BackingField!=null &&
              this._x003C_Nickname_x003E_k__BackingField.equals(other.get_x003C_Nickname_x003E_k__BackingField())));
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
        if (get_x003C_Account_x003E_k__BackingField() != null) {
            _hashCode += get_x003C_Account_x003E_k__BackingField().hashCode();
        }
        _hashCode += get_x003C_Id_x003E_k__BackingField();
        if (get_x003C_Nickname_x003E_k__BackingField() != null) {
            _hashCode += get_x003C_Nickname_x003E_k__BackingField().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(ServerPlayer.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://schemas.datacontract.org/2004/07/Ball_of_Duty_Server.Persistence", "ServerPlayer"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("_x003C_Account_x003E_k__BackingField");
        elemField.setXmlName(new javax.xml.namespace.QName("http://schemas.datacontract.org/2004/07/Ball_of_Duty_Server.Persistence", "_x003C_Account_x003E_k__BackingField"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://schemas.datacontract.org/2004/07/Ball_of_Duty_Server.Persistence", "Account"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("_x003C_Id_x003E_k__BackingField");
        elemField.setXmlName(new javax.xml.namespace.QName("http://schemas.datacontract.org/2004/07/Ball_of_Duty_Server.Persistence", "_x003C_Id_x003E_k__BackingField"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "int"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("_x003C_Nickname_x003E_k__BackingField");
        elemField.setXmlName(new javax.xml.namespace.QName("http://schemas.datacontract.org/2004/07/Ball_of_Duty_Server.Persistence", "_x003C_Nickname_x003E_k__BackingField"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(true);
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
