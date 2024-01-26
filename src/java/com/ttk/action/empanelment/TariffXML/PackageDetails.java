package com.ttk.action.empanelment.TariffXML;

import java.util.List;

import javax.xml.bind.annotation.XmlElement;


public class PackageDetails {

	private List<TariffXMLTag> tariffVO;
	
	public PackageDetails() {
		// TODO Auto-generated constructor stub
	}
	
	
	
	public PackageDetails(List<TariffXMLTag> tariffVO) {
		super();
		this.tariffVO = tariffVO;
	}



	public List<TariffXMLTag> getTariffVO() {
		return tariffVO;
	}
	
	
	
	@XmlElement(name="tariff")
	public void setTariffVO(List<TariffXMLTag> tariffVO) {
		this.tariffVO = tariffVO;
	}


}
