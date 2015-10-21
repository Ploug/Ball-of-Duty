/**
 * IBoDServer.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package org.tempuri;

public interface IBoDServer extends java.rmi.Remote {
    public org.datacontract.schemas._2004._07.Ball_of_Duty_Server_Persistence.ServerPlayer newGuest() throws java.rmi.RemoteException;
    public org.datacontract.schemas._2004._07.Ball_of_Duty_Server_Domain.ServerGameObject[] joinGame(org.datacontract.schemas._2004._07.Ball_of_Duty_Server_Persistence.ServerPlayer clientPlayer) throws java.rmi.RemoteException;
}
