
package com.ttk.business.webservice.dhpo;

import java.net.MalformedURLException;
import java.net.URL;
import javax.xml.namespace.QName;
import javax.xml.ws.Service;
import javax.xml.ws.WebEndpoint;
import javax.xml.ws.WebServiceClient;
import javax.xml.ws.WebServiceException;
import javax.xml.ws.WebServiceFeature;


/**
 * This class was generated by the JAX-WS RI.
 * JAX-WS RI 2.2.4-b01
 * Generated source version: 2.2
 * 
 */
@WebServiceClient(name = "ValidateTransactions", targetNamespace = "http://www.eClaimLink.ae/", wsdlLocation = "https://dhpo.eclaimlink.ae/ValidateTransactions.asmx?wsdl")
public class ValidateTransactions
    extends Service
{

    private final static URL VALIDATETRANSACTIONS_WSDL_LOCATION;
    private final static WebServiceException VALIDATETRANSACTIONS_EXCEPTION;
    private final static QName VALIDATETRANSACTIONS_QNAME = new QName("http://www.eClaimLink.ae/", "ValidateTransactions");

    static {
        URL url = null;
        WebServiceException e = null;
        try {
            url = new URL("https://dhpo.eclaimlink.ae/ValidateTransactions.asmx?wsdl");
        } catch (MalformedURLException ex) {
            e = new WebServiceException(ex);
        }
        VALIDATETRANSACTIONS_WSDL_LOCATION = url;
        VALIDATETRANSACTIONS_EXCEPTION = e;
    }

    public ValidateTransactions() {
        super(__getWsdlLocation(), VALIDATETRANSACTIONS_QNAME);
    }

    public ValidateTransactions(WebServiceFeature... features) {
        super(__getWsdlLocation(), VALIDATETRANSACTIONS_QNAME, features);
    }

    public ValidateTransactions(URL wsdlLocation) {
        super(wsdlLocation, VALIDATETRANSACTIONS_QNAME);
    }

    public ValidateTransactions(URL wsdlLocation, WebServiceFeature... features) {
        super(wsdlLocation, VALIDATETRANSACTIONS_QNAME, features);
    }

    public ValidateTransactions(URL wsdlLocation, QName serviceName) {
        super(wsdlLocation, serviceName);
    }

    public ValidateTransactions(URL wsdlLocation, QName serviceName, WebServiceFeature... features) {
        super(wsdlLocation, serviceName, features);
    }

    /**
     * 
     * @return
     *     returns ValidateTransactionsSoap
     */
    @WebEndpoint(name = "ValidateTransactionsSoap")
    public ValidateTransactionsSoap getValidateTransactionsSoap() {
        return super.getPort(new QName("http://www.eClaimLink.ae/", "ValidateTransactionsSoap"), ValidateTransactionsSoap.class);
    }

    /**
     * 
     * @param features
     *     A list of {@link javax.xml.ws.WebServiceFeature} to configure on the proxy.  Supported features not in the <code>features</code> parameter will have their default values.
     * @return
     *     returns ValidateTransactionsSoap
     */
    @WebEndpoint(name = "ValidateTransactionsSoap")
    public ValidateTransactionsSoap getValidateTransactionsSoap(WebServiceFeature... features) {
        return super.getPort(new QName("http://www.eClaimLink.ae/", "ValidateTransactionsSoap"), ValidateTransactionsSoap.class, features);
    }

    private static URL __getWsdlLocation() {
        if (VALIDATETRANSACTIONS_EXCEPTION!= null) {
            throw VALIDATETRANSACTIONS_EXCEPTION;
        }
        return VALIDATETRANSACTIONS_WSDL_LOCATION;
    }

}
