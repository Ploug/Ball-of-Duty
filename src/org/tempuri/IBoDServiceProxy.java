package org.tempuri;

public class IBoDServiceProxy implements org.tempuri.IBoDService {
  private String _endpoint = null;
  private org.tempuri.IBoDService iBoDService = null;
  
  public IBoDServiceProxy() {
    _initIBoDServiceProxy();
  }
  
  public IBoDServiceProxy(String endpoint) {
    _endpoint = endpoint;
    _initIBoDServiceProxy();
  }
  
  private void _initIBoDServiceProxy() {
    try {
      iBoDService = (new org.tempuri.BoDServiceLocator()).getBasicHttpBinding_IBoDService();
      if (iBoDService != null) {
        if (_endpoint != null)
          ((javax.xml.rpc.Stub)iBoDService)._setProperty("javax.xml.rpc.service.endpoint.address", _endpoint);
        else
          _endpoint = (String)((javax.xml.rpc.Stub)iBoDService)._getProperty("javax.xml.rpc.service.endpoint.address");
      }
      
    }
    catch (javax.xml.rpc.ServiceException serviceException) {}
  }
  
  public String getEndpoint() {
    return _endpoint;
  }
  
  public void setEndpoint(String endpoint) {
    _endpoint = endpoint;
    if (iBoDService != null)
      ((javax.xml.rpc.Stub)iBoDService)._setProperty("javax.xml.rpc.service.endpoint.address", _endpoint);
    
  }
  
  public org.tempuri.IBoDService getIBoDService() {
    if (iBoDService == null)
      _initIBoDServiceProxy();
    return iBoDService;
  }
  
  public org.datacontract.schemas._2004._07.Ball_of_Duty_Server_DTO.PlayerDTO newGuest() throws java.rmi.RemoteException{
    if (iBoDService == null)
      _initIBoDServiceProxy();
    return iBoDService.newGuest();
  }
  
  public org.datacontract.schemas._2004._07.Ball_of_Duty_Server_DTO.MapDTO joinGame(java.lang.Integer clientPlayerId) throws java.rmi.RemoteException{
    if (iBoDService == null)
      _initIBoDServiceProxy();
    return iBoDService.joinGame(clientPlayerId);
  }
  
  
}