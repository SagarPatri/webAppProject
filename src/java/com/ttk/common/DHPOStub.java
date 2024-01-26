package com.ttk.common;

import com.ttk.business.webservice.dhpo.ValidateTransactions;
import com.ttk.business.webservice.dhpo.ValidateTransactionsSoap;

public class DHPOStub {

	public static ValidateTransactionsSoap getStub() {
		
		ValidateTransactions transactions  = new ValidateTransactions();
		ValidateTransactionsSoap soapDHPOStub = transactions.getValidateTransactionsSoap();		
      return  soapDHPOStub;
	}
public static com.ttk.business.webservice.haad.WebservicesSoap getHaadStub() {
		
	com.ttk.business.webservice.haad.Webservices webservices=new com.ttk.business.webservice.haad.Webservices();
	com.ttk.business.webservice.haad.WebservicesSoap soapDHPOStub=webservices.getWebservicesSoap();		
      return  soapDHPOStub;
	}//Webservices webservices=new Webservices();
}
