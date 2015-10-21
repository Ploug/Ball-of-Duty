/**
 * BoDServerLocator.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package org.tempuri;

public class BoDServerLocator extends org.apache.axis.client.Service implements org.tempuri.BoDServer {

    public BoDServerLocator() {
    }


    public BoDServerLocator(org.apache.axis.EngineConfiguration config) {
        super(config);
    }

    public BoDServerLocator(java.lang.String wsdlLoc, javax.xml.namespace.QName sName) throws javax.xml.rpc.ServiceException {
        super(wsdlLoc, sName);
    }

    // Use to get a proxy class for BasicHttpBinding_IBoDServer
    private java.lang.String BasicHttpBinding_IBoDServer_address = "http://localhost:8733/Ball_of_Duty_Server/Domain/BoDServer/";

    public java.lang.String getBasicHttpBinding_IBoDServerAddress() {
        return BasicHttpBinding_IBoDServer_address;
    }

    // The WSDD service name defaults to the port name.
    private java.lang.String BasicHttpBinding_IBoDServerWSDDServiceName = "BasicHttpBinding_IBoDServer";

    public java.lang.String getBasicHttpBinding_IBoDServerWSDDServiceName() {
        return BasicHttpBinding_IBoDServerWSDDServiceName;
    }

    public void setBasicHttpBinding_IBoDServerWSDDServiceName(java.lang.String name) {
        BasicHttpBinding_IBoDServerWSDDServiceName = name;
    }

    public org.tempuri.IBoDServer getBasicHttpBinding_IBoDServer() throws javax.xml.rpc.ServiceException {
       java.net.URL endpoint;
        try {
            endpoint = new java.net.URL(BasicHttpBinding_IBoDServer_address);
        }
        catch (java.net.MalformedURLException e) {
            throw new javax.xml.rpc.ServiceException(e);
        }
        return getBasicHttpBinding_IBoDServer(endpoint);
    }

    public org.tempuri.IBoDServer getBasicHttpBinding_IBoDServer(java.net.URL portAddress) throws javax.xml.rpc.ServiceException {
        try {
            org.tempuri.BasicHttpBinding_IBoDServerStub _stub = new org.tempuri.BasicHttpBinding_IBoDServerStub(portAddress, this);
            _stub.setPortName(getBasicHttpBinding_IBoDServerWSDDServiceName());
            return _stub;
        }
        catch (org.apache.axis.AxisFault e) {
            return null;
        }
    }

    public void setBasicHttpBinding_IBoDServerEndpointAddress(java.lang.String address) {
        BasicHttpBinding_IBoDServer_address = address;
    }

    /**
     * For the given interface, get the stub implementation.
     * If this service has no port for the given interface,
     * then ServiceException is thrown.
     */
    public java.rmi.Remote getPort(Class serviceEndpointInterface) throws javax.xml.rpc.ServiceException {
        try {
            if (org.tempuri.IBoDServer.class.isAssignableFrom(serviceEndpointInterface)) {
                org.tempuri.BasicHttpBinding_IBoDServerStub _stub = new org.tempuri.BasicHttpBinding_IBoDServerStub(new java.net.URL(BasicHttpBinding_IBoDServer_address), this);
                _stub.setPortName(getBasicHttpBinding_IBoDServerWSDDServiceName());
                return _stub;
            }
        }
        catch (java.lang.Throwable t) {
            throw new javax.xml.rpc.ServiceException(t);
        }
        throw new javax.xml.rpc.ServiceException("There is no stub implementation for the interface:  " + (serviceEndpointInterface == null ? "null" : serviceEndpointInterface.getName()));
    }

    /**
     * For the given interface, get the stub implementation.
     * If this service has no port for the given interface,
     * then ServiceException is thrown.
     */
    public java.rmi.Remote getPort(javax.xml.namespace.QName portName, Class serviceEndpointInterface) throws javax.xml.rpc.ServiceException {
        if (portName == null) {
            return getPort(serviceEndpointInterface);
        }
        java.lang.String inputPortName = portName.getLocalPart();
        if ("BasicHttpBinding_IBoDServer".equals(inputPortName)) {
            return getBasicHttpBinding_IBoDServer();
        }
        else  {
            java.rmi.Remote _stub = getPort(serviceEndpointInterface);
            ((org.apache.axis.client.Stub) _stub).setPortName(portName);
            return _stub;
        }
    }

    public javax.xml.namespace.QName getServiceName() {
        return new javax.xml.namespace.QName("http://tempuri.org/", "BoDServer");
    }

    private java.util.HashSet ports = null;

    public java.util.Iterator getPorts() {
        if (ports == null) {
            ports = new java.util.HashSet();
            ports.add(new javax.xml.namespace.QName("http://tempuri.org/", "BasicHttpBinding_IBoDServer"));
        }
        return ports.iterator();
    }

    /**
    * Set the endpoint address for the specified port name.
    */
    public void setEndpointAddress(java.lang.String portName, java.lang.String address) throws javax.xml.rpc.ServiceException {
        
if ("BasicHttpBinding_IBoDServer".equals(portName)) {
            setBasicHttpBinding_IBoDServerEndpointAddress(address);
        }
        else 
{ // Unknown Port Name
            throw new javax.xml.rpc.ServiceException(" Cannot set Endpoint Address for Unknown Port" + portName);
        }
    }

    /**
    * Set the endpoint address for the specified port name.
    */
    public void setEndpointAddress(javax.xml.namespace.QName portName, java.lang.String address) throws javax.xml.rpc.ServiceException {
        setEndpointAddress(portName.getLocalPart(), address);
    }

}
