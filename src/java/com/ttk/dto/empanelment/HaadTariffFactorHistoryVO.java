
/**
 * @ (#)  HaadTariffFactorHistoryVO Oct 22, 2005
 * Project      : TTKPROJECT
 * File         : HaadTariffFactorHistoryVO.java
 * Author : Kishor kumar  S H
 * Company :RCS Technologies
 * Date Created : 02 Dec 2016
 *
 * @author : Kishor kumar  S H
 * Modified by :
 * Modified date :
 * Reason :
 */
package com.ttk.dto.empanelment;

import java.util.Date;

import com.ttk.dto.BaseVO;

public class HaadTariffFactorHistoryVO extends BaseVO{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String service;
	private String factor;
	private String oldValue;
	private String newValue;
	private String newTariffStartDate;
	private String oldTariffEndDate;
	private String updatedByName;
	private String network;
	private Date startDate=null;
	private Date EndDate=null;
	
	
	
	public Date getStartDate() {
		return startDate;
	}
	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}
	public Date getEndDate() {
		return EndDate;
	}
	public void setEndDate(Date endDate) {
		EndDate = endDate;
	}
	public String getService() {
		return service;
	}
	public void setService(String service) {
		this.service = service;
	}
	public String getFactor() {
		return factor;
	}
	public void setFactor(String factor) {
		this.factor = factor;
	}
	public String getOldValue() {
		return oldValue;
	}
	public void setOldValue(String oldValue) {
		this.oldValue = oldValue;
	}
	public String getNewValue() {
		return newValue;
	}
	public void setNewValue(String newValue) {
		this.newValue = newValue;
	}
	public String getNewTariffStartDate() {
		return newTariffStartDate;
	}
	public void setNewTariffStartDate(String newTariffStartDate) {
		this.newTariffStartDate = newTariffStartDate;
	}
	public String getOldTariffEndDate() {
		return oldTariffEndDate;
	}
	public void setOldTariffEndDate(String oldTariffEndDate) {
		this.oldTariffEndDate = oldTariffEndDate;
	}
	public String getUpdatedByName() {
		return updatedByName;
	}
	public void setUpdatedByName(String updatedByName) {
		this.updatedByName = updatedByName;
	}
	public String getNetwork() {
		return network;
	}
	public void setNetwork(String network) {
		this.network = network;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	
	
}//end of class HaadTariffFactorHistoryVO
