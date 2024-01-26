/**
 * BjazWebServiceAll_ServiceLocator.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.ttk.common.externalwebservices.BjazWebServiceAll_wsdl;

public class BjazWebServiceAll_ServiceLocator extends org.apache.axis.client.Service implements com.ttk.common.externalwebservices.BjazWebServiceAll_wsdl.BjazWebServiceAll_Service {

    public BjazWebServiceAll_ServiceLocator() {
    }


    public BjazWebServiceAll_ServiceLocator(org.apache.axis.EngineConfiguration config) {
        super(config);
    }

    public BjazWebServiceAll_ServiceLocator(java.lang.String wsdlLoc, javax.xml.namespace.QName sName) throws javax.xml.rpc.ServiceException {
        super(wsdlLoc, sName);
    }

    // Use to get a proxy class for bjazWebServiceAllPort
    private java.lang.String bjazWebServiceAllPort_address = "http://webservices.bajajallianz.com:80/bjazWebServiceAll/bjazWebServiceAllPort";

    public java.lang.String getbjazWebServiceAllPortAddress() {
        return bjazWebServiceAllPort_address;
    }

    // The WSDD service name defaults to the port name.
    private java.lang.String bjazWebServiceAllPortWSDDServiceName = "bjazWebServiceAllPort";

    public java.lang.String getbjazWebServiceAllPortWSDDServiceName() {
        return bjazWebServiceAllPortWSDDServiceName;
    }

    public void setbjazWebServiceAllPortWSDDServiceName(java.lang.String name) {
        bjazWebServiceAllPortWSDDServiceName = name;
    }

    public com.ttk.common.externalwebservices.BjazWebServiceAll_wsdl.BjazWebServiceAll_PortType getbjazWebServiceAllPort() throws javax.xml.rpc.ServiceException {
       java.net.URL endpoint;
        try {
            endpoint = new java.net.URL(bjazWebServiceAllPort_address);
        }
        catch (java.net.MalformedURLException e) {
            throw new javax.xml.rpc.ServiceException(e);
        }
        return getbjazWebServiceAllPort(endpoint);
    }

    public com.ttk.common.externalwebservices.BjazWebServiceAll_wsdl.BjazWebServiceAll_PortType getbjazWebServiceAllPort(java.net.URL portAddress) throws javax.xml.rpc.ServiceException {
        try {
        	com.ttk.common.externalwebservices.BjazWebServiceAll_wsdl.BjazWebServiceAll_BindingStub _stub = new com.ttk.common.externalwebservices.BjazWebServiceAll_wsdl.BjazWebServiceAll_BindingStub(portAddress, this);
            _stub.setPortName(getbjazWebServiceAllPortWSDDServiceName());
            return _stub;
        }
        catch (org.apache.axis.AxisFault e) {
            return null;
        }
    }

    public void setbjazWebServiceAllPortEndpointAddress(java.lang.String address) {
        bjazWebServiceAllPort_address = address;
    }

    /**
     * For the given interface, get the stub implementation.
     * If this service has no port for the given interface,
     * then ServiceException is thrown.
     */
    public java.rmi.Remote getPort(Class serviceEndpointInterface) throws javax.xml.rpc.ServiceException {
        try {
            if (com.ttk.common.externalwebservices.BjazWebServiceAll_wsdl.BjazWebServiceAll_PortType.class.isAssignableFrom(serviceEndpointInterface)) {
            	com.ttk.common.externalwebservices.BjazWebServiceAll_wsdl.BjazWebServiceAll_BindingStub _stub = new com.ttk.common.externalwebservices.BjazWebServiceAll_wsdl.BjazWebServiceAll_BindingStub(new java.net.URL(bjazWebServiceAllPort_address), this);
                _stub.setPortName(getbjazWebServiceAllPortWSDDServiceName());
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
        if ("bjazWebServiceAllPort".equals(inputPortName)) {
            return getbjazWebServiceAllPort();
        }
        else  {
            java.rmi.Remote _stub = getPort(serviceEndpointInterface);
            ((org.apache.axis.client.Stub) _stub).setPortName(portName);
            return _stub;
        }
    }

    public javax.xml.namespace.QName getServiceName() {
        return new javax.xml.namespace.QName("http://com/bajajallianz/BjazWebServiceAll.wsdl", "bjazWebServiceAll");
    }

    private java.util.HashSet ports = null;

    public java.util.Iterator getPorts() {
        if (ports == null) {
            ports = new java.util.HashSet();
            ports.add(new javax.xml.namespace.QName("http://com/bajajallianz/BjazWebServiceAll.wsdl", "bjazWebServiceAllPort"));
        }
        return ports.iterator();
    }

    /**
    * Set the endpoint address for the specified port name.
    */
    public void setEndpointAddress(java.lang.String portName, java.lang.String address) throws javax.xml.rpc.ServiceException {
        
if ("bjazWebServiceAllPort".equals(portName)) {
            setbjazWebServiceAllPortEndpointAddress(address);
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
