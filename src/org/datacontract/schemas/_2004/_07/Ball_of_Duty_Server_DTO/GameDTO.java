/**
 * GameDTO.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package org.datacontract.schemas._2004._07.Ball_of_Duty_Server_DTO;

public class GameDTO  implements java.io.Serializable {
    private java.lang.Integer characterId;

    private java.lang.Integer gameId;

    private org.datacontract.schemas._2004._07.Ball_of_Duty_Server_DTO.GameObjectDTO[] gameObjects;

    private java.lang.String IPAddress;

    private java.lang.Integer mapHeight;

    private java.lang.Integer mapWidth;

    private org.datacontract.schemas._2004._07.Ball_of_Duty_Server_DTO.PlayerDTO[] players;

    public GameDTO() {
    }

    public GameDTO(
           java.lang.Integer characterId,
           java.lang.Integer gameId,
           org.datacontract.schemas._2004._07.Ball_of_Duty_Server_DTO.GameObjectDTO[] gameObjects,
           java.lang.String IPAddress,
           java.lang.Integer mapHeight,
           java.lang.Integer mapWidth,
           org.datacontract.schemas._2004._07.Ball_of_Duty_Server_DTO.PlayerDTO[] players) {
           this.characterId = characterId;
           this.gameId = gameId;
           this.gameObjects = gameObjects;
           this.IPAddress = IPAddress;
           this.mapHeight = mapHeight;
           this.mapWidth = mapWidth;
           this.players = players;
    }


    /**
     * Gets the characterId value for this GameDTO.
     * 
     * @return characterId
     */
    public java.lang.Integer getCharacterId() {
        return characterId;
    }


    /**
     * Sets the characterId value for this GameDTO.
     * 
     * @param characterId
     */
    public void setCharacterId(java.lang.Integer characterId) {
        this.characterId = characterId;
    }


    /**
     * Gets the gameId value for this GameDTO.
     * 
     * @return gameId
     */
    public java.lang.Integer getGameId() {
        return gameId;
    }


    /**
     * Sets the gameId value for this GameDTO.
     * 
     * @param gameId
     */
    public void setGameId(java.lang.Integer gameId) {
        this.gameId = gameId;
    }


    /**
     * Gets the gameObjects value for this GameDTO.
     * 
     * @return gameObjects
     */
    public org.datacontract.schemas._2004._07.Ball_of_Duty_Server_DTO.GameObjectDTO[] getGameObjects() {
        return gameObjects;
    }


    /**
     * Sets the gameObjects value for this GameDTO.
     * 
     * @param gameObjects
     */
    public void setGameObjects(org.datacontract.schemas._2004._07.Ball_of_Duty_Server_DTO.GameObjectDTO[] gameObjects) {
        this.gameObjects = gameObjects;
    }


    /**
     * Gets the IPAddress value for this GameDTO.
     * 
     * @return IPAddress
     */
    public java.lang.String getIPAddress() {
        return IPAddress;
    }


    /**
     * Sets the IPAddress value for this GameDTO.
     * 
     * @param IPAddress
     */
    public void setIPAddress(java.lang.String IPAddress) {
        this.IPAddress = IPAddress;
    }


    /**
     * Gets the mapHeight value for this GameDTO.
     * 
     * @return mapHeight
     */
    public java.lang.Integer getMapHeight() {
        return mapHeight;
    }


    /**
     * Sets the mapHeight value for this GameDTO.
     * 
     * @param mapHeight
     */
    public void setMapHeight(java.lang.Integer mapHeight) {
        this.mapHeight = mapHeight;
    }


    /**
     * Gets the mapWidth value for this GameDTO.
     * 
     * @return mapWidth
     */
    public java.lang.Integer getMapWidth() {
        return mapWidth;
    }


    /**
     * Sets the mapWidth value for this GameDTO.
     * 
     * @param mapWidth
     */
    public void setMapWidth(java.lang.Integer mapWidth) {
        this.mapWidth = mapWidth;
    }


    /**
     * Gets the players value for this GameDTO.
     * 
     * @return players
     */
    public org.datacontract.schemas._2004._07.Ball_of_Duty_Server_DTO.PlayerDTO[] getPlayers() {
        return players;
    }


    /**
     * Sets the players value for this GameDTO.
     * 
     * @param players
     */
    public void setPlayers(org.datacontract.schemas._2004._07.Ball_of_Duty_Server_DTO.PlayerDTO[] players) {
        this.players = players;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof GameDTO)) return false;
        GameDTO other = (GameDTO) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.characterId==null && other.getCharacterId()==null) || 
             (this.characterId!=null &&
              this.characterId.equals(other.getCharacterId()))) &&
            ((this.gameId==null && other.getGameId()==null) || 
             (this.gameId!=null &&
              this.gameId.equals(other.getGameId()))) &&
            ((this.gameObjects==null && other.getGameObjects()==null) || 
             (this.gameObjects!=null &&
              java.util.Arrays.equals(this.gameObjects, other.getGameObjects()))) &&
            ((this.IPAddress==null && other.getIPAddress()==null) || 
             (this.IPAddress!=null &&
              this.IPAddress.equals(other.getIPAddress()))) &&
            ((this.mapHeight==null && other.getMapHeight()==null) || 
             (this.mapHeight!=null &&
              this.mapHeight.equals(other.getMapHeight()))) &&
            ((this.mapWidth==null && other.getMapWidth()==null) || 
             (this.mapWidth!=null &&
              this.mapWidth.equals(other.getMapWidth()))) &&
            ((this.players==null && other.getPlayers()==null) || 
             (this.players!=null &&
              java.util.Arrays.equals(this.players, other.getPlayers())));
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
        if (getCharacterId() != null) {
            _hashCode += getCharacterId().hashCode();
        }
        if (getGameId() != null) {
            _hashCode += getGameId().hashCode();
        }
        if (getGameObjects() != null) {
            for (int i=0;
                 i<java.lang.reflect.Array.getLength(getGameObjects());
                 i++) {
                java.lang.Object obj = java.lang.reflect.Array.get(getGameObjects(), i);
                if (obj != null &&
                    !obj.getClass().isArray()) {
                    _hashCode += obj.hashCode();
                }
            }
        }
        if (getIPAddress() != null) {
            _hashCode += getIPAddress().hashCode();
        }
        if (getMapHeight() != null) {
            _hashCode += getMapHeight().hashCode();
        }
        if (getMapWidth() != null) {
            _hashCode += getMapWidth().hashCode();
        }
        if (getPlayers() != null) {
            for (int i=0;
                 i<java.lang.reflect.Array.getLength(getPlayers());
                 i++) {
                java.lang.Object obj = java.lang.reflect.Array.get(getPlayers(), i);
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
        new org.apache.axis.description.TypeDesc(GameDTO.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://schemas.datacontract.org/2004/07/Ball_of_Duty_Server.DTO", "GameDTO"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("characterId");
        elemField.setXmlName(new javax.xml.namespace.QName("http://schemas.datacontract.org/2004/07/Ball_of_Duty_Server.DTO", "CharacterId"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "int"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("gameId");
        elemField.setXmlName(new javax.xml.namespace.QName("http://schemas.datacontract.org/2004/07/Ball_of_Duty_Server.DTO", "GameId"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "int"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("gameObjects");
        elemField.setXmlName(new javax.xml.namespace.QName("http://schemas.datacontract.org/2004/07/Ball_of_Duty_Server.DTO", "GameObjects"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://schemas.datacontract.org/2004/07/Ball_of_Duty_Server.DTO", "GameObjectDTO"));
        elemField.setMinOccurs(0);
        elemField.setNillable(true);
        elemField.setItemQName(new javax.xml.namespace.QName("http://schemas.datacontract.org/2004/07/Ball_of_Duty_Server.DTO", "GameObjectDTO"));
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("IPAddress");
        elemField.setXmlName(new javax.xml.namespace.QName("http://schemas.datacontract.org/2004/07/Ball_of_Duty_Server.DTO", "IPAddress"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("mapHeight");
        elemField.setXmlName(new javax.xml.namespace.QName("http://schemas.datacontract.org/2004/07/Ball_of_Duty_Server.DTO", "MapHeight"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "int"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("mapWidth");
        elemField.setXmlName(new javax.xml.namespace.QName("http://schemas.datacontract.org/2004/07/Ball_of_Duty_Server.DTO", "MapWidth"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "int"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("players");
        elemField.setXmlName(new javax.xml.namespace.QName("http://schemas.datacontract.org/2004/07/Ball_of_Duty_Server.DTO", "Players"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://schemas.datacontract.org/2004/07/Ball_of_Duty_Server.DTO", "PlayerDTO"));
        elemField.setMinOccurs(0);
        elemField.setNillable(true);
        elemField.setItemQName(new javax.xml.namespace.QName("http://schemas.datacontract.org/2004/07/Ball_of_Duty_Server.DTO", "PlayerDTO"));
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
