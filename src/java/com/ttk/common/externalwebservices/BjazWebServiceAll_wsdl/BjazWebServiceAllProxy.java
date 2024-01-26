package com.ttk.common.externalwebservices.BjazWebServiceAll_wsdl;

import java.util.HashMap;
import org.apache.log4j.Logger;
public class BjazWebServiceAllProxy implements com.ttk.common.externalwebservices.BjazWebServiceAll_wsdl.BjazWebServiceAll_PortType {
	private static final Logger log = Logger.getLogger(BjazWebServiceAllProxy.class);
	private String _endpoint = null;
  private com.ttk.common.externalwebservices.BjazWebServiceAll_wsdl.BjazWebServiceAll_PortType bjazWebServiceAll_PortType = null;
  
  public BjazWebServiceAllProxy() {
    _initBjazWebServiceAllProxy();
  }
  
  public BjazWebServiceAllProxy(String endpoint) {
    _endpoint = endpoint;
    _initBjazWebServiceAllProxy();
  }
  
  private void _initBjazWebServiceAllProxy() {
    try {
      bjazWebServiceAll_PortType = (new com.ttk.common.externalwebservices.BjazWebServiceAll_wsdl.BjazWebServiceAll_ServiceLocator()).getbjazWebServiceAllPort();
      if (bjazWebServiceAll_PortType != null) {
        if (_endpoint != null)
          ((javax.xml.rpc.Stub)bjazWebServiceAll_PortType)._setProperty("javax.xml.rpc.service.endpoint.address", _endpoint);
        else
          _endpoint = (String)((javax.xml.rpc.Stub)bjazWebServiceAll_PortType)._getProperty("javax.xml.rpc.service.endpoint.address");
      }
      
    }
    catch (javax.xml.rpc.ServiceException serviceException) {}
  }
  
  public String getEndpoint() {
    return _endpoint;
  }
  
  public void setEndpoint(String endpoint) {
    _endpoint = endpoint;
    if (bjazWebServiceAll_PortType != null)
      ((javax.xml.rpc.Stub)bjazWebServiceAll_PortType)._setProperty("javax.xml.rpc.service.endpoint.address", _endpoint);
    
  }
  
  public com.ttk.common.externalwebservices.BjazWebServiceAll_wsdl.BjazWebServiceAll_PortType getBjazWebServiceAll_PortType() {
    if (bjazWebServiceAll_PortType == null)
      _initBjazWebServiceAllProxy();
    return bjazWebServiceAll_PortType;
  }
  
  public java.util.Map bjazSingleWsForAll(java.lang.String pUserId, java.lang.String pPassKey, java.lang.String pProcessFlag, java.lang.String pTieupName, javax.xml.rpc.holders.StringHolder pInputDataXmlStr_inout, javax.xml.rpc.holders.BigDecimalHolder pErrorCode_out) throws java.rmi.RemoteException{
    if (bjazWebServiceAll_PortType == null)
      _initBjazWebServiceAllProxy();
    HashMap outparams =(HashMap)bjazWebServiceAll_PortType.bjazSingleWsForAll(pUserId, pPassKey, pProcessFlag, pTieupName, pInputDataXmlStr_inout, pErrorCode_out);
	return outparams;
  }
  
  
}