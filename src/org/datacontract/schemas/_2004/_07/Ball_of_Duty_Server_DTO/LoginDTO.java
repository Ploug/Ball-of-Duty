/**
 * LoginDTO.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package org.datacontract.schemas._2004._07.Ball_of_Duty_Server_DTO;

public class LoginDTO  implements java.io.Serializable {
    private byte[] authenticationChallenge;

    private byte[] IV;

    private byte[] passwordSalt;

    private byte[] sessionSalt;

    public LoginDTO() {
    }

    public LoginDTO(
           byte[] authenticationChallenge,
           byte[] IV,
           byte[] passwordSalt,
           byte[] sessionSalt) {
           this.authenticationChallenge = authenticationChallenge;
           this.IV = IV;
           this.passwordSalt = passwordSalt;
           this.sessionSalt = sessionSalt;
    }


    /**
     * Gets the authenticationChallenge value for this LoginDTO.
     * 
     * @return authenticationChallenge
     */
    public byte[] getAuthenticationChallenge() {
        return authenticationChallenge;
    }


    /**
     * Sets the authenticationChallenge value for this LoginDTO.
     * 
     * @param authenticationChallenge
     */
    public void setAuthenticationChallenge(byte[] authenticationChallenge) {
        this.authenticationChallenge = authenticationChallenge;
    }


    /**
     * Gets the IV value for this LoginDTO.
     * 
     * @return IV
     */
    public byte[] getIV() {
        return IV;
    }


    /**
     * Sets the IV value for this LoginDTO.
     * 
     * @param IV
     */
    public void setIV(byte[] IV) {
        this.IV = IV;
    }


    /**
     * Gets the passwordSalt value for this LoginDTO.
     * 
     * @return passwordSalt
     */
    public byte[] getPasswordSalt() {
        return passwordSalt;
    }


    /**
     * Sets the passwordSalt value for this LoginDTO.
     * 
     * @param passwordSalt
     */
    public void setPasswordSalt(byte[] passwordSalt) {
        this.passwordSalt = passwordSalt;
    }


    /**
     * Gets the sessionSalt value for this LoginDTO.
     * 
     * @return sessionSalt
     */
    public byte[] getSessionSalt() {
        return sessionSalt;
    }


    /**
     * Sets the sessionSalt value for this LoginDTO.
     * 
     * @param sessionSalt
     */
    public void setSessionSalt(byte[] sessionSalt) {
        this.sessionSalt = sessionSalt;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof LoginDTO)) return false;
        LoginDTO other = (LoginDTO) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.authenticationChallenge==null && other.getAuthenticationChallenge()==null) || 
             (this.authenticationChallenge!=null &&
              java.util.Arrays.equals(this.authenticationChallenge, other.getAuthenticationChallenge()))) &&
            ((this.IV==null && other.getIV()==null) || 
             (this.IV!=null &&
              java.util.Arrays.equals(this.IV, other.getIV()))) &&
            ((this.passwordSalt==null && other.getPasswordSalt()==null) || 
             (this.passwordSalt!=null &&
              java.util.Arrays.equals(this.passwordSalt, other.getPasswordSalt()))) &&
            ((this.sessionSalt==null && other.getSessionSalt()==null) || 
             (this.sessionSalt!=null &&
              java.util.Arrays.equals(this.sessionSalt, other.getSessionSalt())));
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
        if (getAuthenticationChallenge() != null) {
            for (int i=0;
                 i<java.lang.reflect.Array.getLength(getAuthenticationChallenge());
                 i++) {
                java.lang.Object obj = java.lang.reflect.Array.get(getAuthenticationChallenge(), i);
                if (obj != null &&
                    !obj.getClass().isArray()) {
                    _hashCode += obj.hashCode();
                }
            }
        }
        if (getIV() != null) {
            for (int i=0;
                 i<java.lang.reflect.Array.getLength(getIV());
                 i++) {
                java.lang.Object obj = java.lang.reflect.Array.get(getIV(), i);
                if (obj != null &&
                    !obj.getClass().isArray()) {
                    _hashCode += obj.hashCode();
                }
            }
        }
        if (getPasswordSalt() != null) {
            for (int i=0;
                 i<java.lang.reflect.Array.getLength(getPasswordSalt());
                 i++) {
                java.lang.Object obj = java.lang.reflect.Array.get(getPasswordSalt(), i);
                if (obj != null &&
                    !obj.getClass().isArray()) {
                    _hashCode += obj.hashCode();
                }
            }
        }
        if (getSessionSalt() != null) {
            for (int i=0;
                 i<java.lang.reflect.Array.getLength(getSessionSalt());
                 i++) {
                java.lang.Object obj = java.lang.reflect.Array.get(getSessionSalt(), i);
                if (obj != null &&
                    !obj.getClass().isArray()) {
                    _hashCode += obj.hashCode();
                }
            }
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(LoginDTO.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://schemas.datacontract.org/2004/07/Ball_of_Duty_Server.DTO", "LoginDTO"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("authenticationChallenge");
        elemField.setXmlName(new javax.xml.namespace.QName("http://schemas.datacontract.org/2004/07/Ball_of_Duty_Server.DTO", "AuthenticationChallenge"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "base64Binary"));
        elemField.setMinOccurs(0);
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("IV");
        elemField.setXmlName(new javax.xml.namespace.QName("http://schemas.datacontract.org/2004/07/Ball_of_Duty_Server.DTO", "IV"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "base64Binary"));
        elemField.setMinOccurs(0);
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("passwordSalt");
        elemField.setXmlName(new javax.xml.namespace.QName("http://schemas.datacontract.org/2004/07/Ball_of_Duty_Server.DTO", "PasswordSalt"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "base64Binary"));
        elemField.setMinOccurs(0);
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("sessionSalt");
        elemField.setXmlName(new javax.xml.namespace.QName("http://schemas.datacontract.org/2004/07/Ball_of_Duty_Server.DTO", "SessionSalt"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "base64Binary"));
        elemField.setMinOccurs(0);
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
