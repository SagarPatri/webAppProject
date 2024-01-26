/**
 * @ (#) ReportVO.java Oct 21, 2005
 * Project      : TTK HealthCare Services
 * File         : ReportVO
 * Author       : Ramakrishna K M
 * Company      : Span Systems Corporation
 * Date Created : Oct 21, 2005
 *
 * @author       :  Ramakrishna K M
 * Modified by   : 
 * Modified date : 
 * Reason        : 
 */
package com.ttk.dto.administration;

import java.math.BigDecimal;
import java.util.ArrayList;

import com.ttk.dto.BaseVO;


public class ReportVO extends BaseVO{
	
   

	private ArrayList alColumns	=	null;
	
	private String exchangeratesDate;
	
	private String countryCode;
	
	private String currencyCode;
	private String currencyName;
	//private int unitsperAED;
	//private int aEDperUnit;
	private String conversionDate;
	private String uploadedDate;
	
	
	 private BigDecimal unitsperAED = null;
	 private BigDecimal aEDperUnit = null;
	
	
	
	
	
	
	
	 public String getExchangeratesDate() {
		return exchangeratesDate;
	}

	public void setExchangeratesDate(String exchangeratesDate) {
		this.exchangeratesDate = exchangeratesDate;
	}

	public String getCountryCode() {
		return countryCode;
	}

	public void setCountryCode(String countryCode) {
		this.countryCode = countryCode;
	}

	public String getCurrencyCode() {
		return currencyCode;
	}

	public void setCurrencyCode(String currencyCode) {
		this.currencyCode = currencyCode;
	}

	public String getCurrencyName() {
		return currencyName;
	}

	public void setCurrencyName(String currencyName) {
		this.currencyName = currencyName;
	}

	
	public String getConversionDate() {
		return conversionDate;
	}

	public void setConversionDate(String conversionDate) {
		this.conversionDate = conversionDate;
	}

	public String getUploadedDate() {
		return uploadedDate;
	}

	public void setUploadedDate(String uploadedDate) {
		this.uploadedDate = uploadedDate;
	}

	public ArrayList getAlColumns() {
			return alColumns;
		}

		public void setAlColumns(ArrayList alColumns) {
			this.alColumns = alColumns;
		}

		public BigDecimal getUnitsperAED() {
			return unitsperAED;
		}

		public void setUnitsperAED(BigDecimal unitsperAED) {
			this.unitsperAED = unitsperAED;
		}

		public BigDecimal getaEDperUnit() {
			return aEDperUnit;
		}

		public void setaEDperUnit(BigDecimal aEDperUnit) {
			this.aEDperUnit = aEDperUnit;
		}

		
	
	
	
	
	
	
    
}//end of ReportVO
