/**
 * BjazWebServiceAll_PortType.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.ttk.common.externalwebservices.BjazWebServiceAll_wsdl;

import java.util.Map;

public interface BjazWebServiceAll_PortType extends java.rmi.Remote {
    public Map bjazSingleWsForAll(java.lang.String pUserId, java.lang.String pPassKey, java.lang.String pProcessFlag, java.lang.String pTieupName, javax.xml.rpc.holders.StringHolder pInputDataXmlStr_inout, javax.xml.rpc.holders.BigDecimalHolder pErrorCode_out) throws java.rmi.RemoteException;
}
