package org.tempuri;

public class IBoDServerProxy implements org.tempuri.IBoDServer {
  private String _endpoint = null;
  private org.tempuri.IBoDServer iBoDServer = null;
  
  public IBoDServerProxy() {
    _initIBoDServerProxy();
  }
  
  public IBoDServerProxy(String endpoint) {
    _endpoint = endpoint;
    _initIBoDServerProxy();
  }
  
  private void _initIBoDServerProxy() {
    try {
      iBoDServer = (new org.tempuri.BoDServerLocator()).getBasicHttpBinding_IBoDServer();
      if (iBoDServer != null) {
        if (_endpoint != null)
          ((javax.xml.rpc.Stub)iBoDServer)._setProperty("javax.xml.rpc.service.endpoint.address", _endpoint);
        else
          _endpoint = (String)((javax.xml.rpc.Stub)iBoDServer)._getProperty("javax.xml.rpc.service.endpoint.address");
      }
      
    }
    catch (javax.xml.rpc.ServiceException serviceException) {}
  }
  
  public String getEndpoint() {
    return _endpoint;
  }
  
  public void setEndpoint(String endpoint) {
    _endpoint = endpoint;
    if (iBoDServer != null)
      ((javax.xml.rpc.Stub)iBoDServer)._setProperty("javax.xml.rpc.service.endpoint.address", _endpoint);
    
  }
  
  public org.tempuri.IBoDServer getIBoDServer() {
    if (iBoDServer == null)
      _initIBoDServerProxy();
    return iBoDServer;
  }
  
  public org.datacontract.schemas._2004._07.Ball_of_Duty_Server_Persistence.ServerPlayer newGuest() throws java.rmi.RemoteException{
    if (iBoDServer == null)
      _initIBoDServerProxy();
    return iBoDServer.newGuest();
  }
  
  public org.datacontract.schemas._2004._07.Ball_of_Duty_Server_Domain.ServerGameObject[] joinGame(org.datacontract.schemas._2004._07.Ball_of_Duty_Server_Persistence.ServerPlayer clientPlayer) throws java.rmi.RemoteException{
    if (iBoDServer == null)
      _initIBoDServerProxy();
    return iBoDServer.joinGame(clientPlayer);
  }
  
  
}