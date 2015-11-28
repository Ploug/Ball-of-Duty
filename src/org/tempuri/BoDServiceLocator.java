/**
 * BoDServiceLocator.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package org.tempuri;

public class BoDServiceLocator extends org.apache.axis.client.Service implements org.tempuri.BoDService {

    public BoDServiceLocator() {
    }


    public BoDServiceLocator(org.apache.axis.EngineConfiguration config) {
        super(config);
    }

    public BoDServiceLocator(java.lang.String wsdlLoc, javax.xml.namespace.QName sName) throws javax.xml.rpc.ServiceException {
        super(wsdlLoc, sName);
    }

    // Use to get a proxy class for BasicHttpBinding_IBoDService
    private java.lang.String BasicHttpBinding_IBoDService_address = "http://localhost/Ball_of_Duty_Server/Services/BoDService/";

    public java.lang.String getBasicHttpBinding_IBoDServiceAddress() {
        return BasicHttpBinding_IBoDService_address;
    }

    // The WSDD service name defaults to the port name.
    private java.lang.String BasicHttpBinding_IBoDServiceWSDDServiceName = "BasicHttpBinding_IBoDService";

    public java.lang.String getBasicHttpBinding_IBoDServiceWSDDServiceName() {
        return BasicHttpBinding_IBoDServiceWSDDServiceName;
    }

    public void setBasicHttpBinding_IBoDServiceWSDDServiceName(java.lang.String name) {
        BasicHttpBinding_IBoDServiceWSDDServiceName = name;
    }

    public org.tempuri.IBoDService getBasicHttpBinding_IBoDService() throws javax.xml.rpc.ServiceException {
       java.net.URL endpoint;
        try {
            endpoint = new java.net.URL(BasicHttpBinding_IBoDService_address);
        }
        catch (java.net.MalformedURLException e) {
            throw new javax.xml.rpc.ServiceException(e);
        }
        return getBasicHttpBinding_IBoDService(endpoint);
    }

    public org.tempuri.IBoDService getBasicHttpBinding_IBoDService(java.net.URL portAddress) throws javax.xml.rpc.ServiceException {
        try {
            org.tempuri.BasicHttpBinding_IBoDServiceStub _stub = new org.tempuri.BasicHttpBinding_IBoDServiceStub(portAddress, this);
            _stub.setPortName(getBasicHttpBinding_IBoDServiceWSDDServiceName());
            return _stub;
        }
        catch (org.apache.axis.AxisFault e) {
            return null;
        }
    }

    public void setBasicHttpBinding_IBoDServiceEndpointAddress(java.lang.String address) {
        BasicHttpBinding_IBoDService_address = address;
    }

    /**
     * For the given interface, get the stub implementation.
     * If this service has no port for the given interface,
     * then ServiceException is thrown.
     */
    public java.rmi.Remote getPort(Class serviceEndpointInterface) throws javax.xml.rpc.ServiceException {
        try {
            if (org.tempuri.IBoDService.class.isAssignableFrom(serviceEndpointInterface)) {
                org.tempuri.BasicHttpBinding_IBoDServiceStub _stub = new org.tempuri.BasicHttpBinding_IBoDServiceStub(new java.net.URL(BasicHttpBinding_IBoDService_address), this);
                _stub.setPortName(getBasicHttpBinding_IBoDServiceWSDDServiceName());
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
        if ("BasicHttpBinding_IBoDService".equals(inputPortName)) {
            return getBasicHttpBinding_IBoDService();
        }
        else  {
            java.rmi.Remote _stub = getPort(serviceEndpointInterface);
            ((org.apache.axis.client.Stub) _stub).setPortName(portName);
            return _stub;
        }
    }

    public javax.xml.namespace.QName getServiceName() {
        return new javax.xml.namespace.QName("http://tempuri.org/", "BoDService");
    }

    private java.util.HashSet ports = null;

    public java.util.Iterator getPorts() {
        if (ports == null) {
            ports = new java.util.HashSet();
            ports.add(new javax.xml.namespace.QName("http://tempuri.org/", "BasicHttpBinding_IBoDService"));
        }
        return ports.iterator();
    }

    /**
    * Set the endpoint address for the specified port name.
    */
    public void setEndpointAddress(java.lang.String portName, java.lang.String address) throws javax.xml.rpc.ServiceException {
        
if ("BasicHttpBinding_IBoDService".equals(portName)) {
            setBasicHttpBinding_IBoDServiceEndpointAddress(address);
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
