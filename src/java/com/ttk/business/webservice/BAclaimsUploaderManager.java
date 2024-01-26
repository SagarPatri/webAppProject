
/**
 * @ (#) WebServiceManager.java Jun 14, 2006
 * Project       : TTK HealthCare Services
 * File          : WebServiceManager.java
 * Author        :
 * Company       : Span Systems Corporation
 * Date Created  : Jun 14, 2006
 * @author       : Krishna K. H
 * Modified by   :
 * Modified date :
 * Reason        :
 */

package com.ttk.business.webservice;


import java.math.BigDecimal;
//import java.rmi.RemoteException;

//import java.util.Map;

import javax.ejb.Local;
//import javax.xml.rpc.holders.BigDecimalHolder;
//import javax.xml.rpc.holders.StringHolder;


import com.ttk.common.exception.TTKException;
@Local

public interface BAclaimsUploaderManager {

    public String saveXmlFromBajaj(String xmlString,String filename,BigDecimal errorCode) throws TTKException;
   
   //public Map getResponseFromBajaj(String pUserId,String pPassKey,String pProcessFlag,String pTieupName,StringHolder pInputDataXmlStr_inout,BigDecimalHolder pErrorCode_out) throws TTKException, RemoteException;
   
    

    }//End of WebServiceManager
