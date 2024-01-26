/**
 * @ (#) ServiceDetailsVO.java June 21, 2017
 * Project 	     : ProjectX
 * File          : ServiceDetailsVO.java
 * Author        : Kishor kumar S H
 * Company       : RCS Technologies
 * Date Created  : july 24, 2017
 *
 * @author       :  
 * Modified by   :  
 * Modified date :  
 * Reason        :  
 */

package com.ttk.dto.onlineforms.providerLogin;

import com.ttk.dto.BaseVO;




public class ServiceDetailsVO extends BaseVO{

	private static final long serialVersionUID = -5973299746045705015L;
	
	private double roomRent;
	private double roomRentDays;
	private double roomRentCharges;
	private double surgonCharges;
	private double oTCharges;
	private double icuRent;
	private double icuRentDays;
	private double icuCharges;
	private double deliveryCharges;
	private double daycareCharges;
	private double anaesthesia;
	private double anaesthesiaCharges;
	

	private double consumables;
	private double investigations;
	private double otConsumables;
	private double doctorRoundFee;
	private double pharmacy;
	private double packageCharges;
	private double others;
	private double othersCharges;
	
	public double getRoomRent() {
		return roomRent;
	}
	public void setRoomRent(double roomRent) {
		this.roomRent = roomRent;
	}
	public double getRoomRentDays() {
		return roomRentDays;
	}
	public void setRoomRentDays(double roomRentDays) {
		this.roomRentDays = roomRentDays;
	}
	public double getIcuRent() {
		return icuRent;
	}
	public void setIcuRent(double icuRent) {
		this.icuRent = icuRent;
	}
	public double getIcuRentDays() {
		return icuRentDays;
	}
	public void setIcuRentDays(double icuRentDays) {
		this.icuRentDays = icuRentDays;
	}
	public double getRoomRentCharges() {
		return roomRentCharges;
	}
	public void setRoomRentCharges(double roomRentCharges) {
		this.roomRentCharges = roomRentCharges;
	}
	public double getSurgonCharges() {
		return surgonCharges;
	}
	public void setSurgonCharges(double surgonCharges) {
		this.surgonCharges = surgonCharges;
	}
	public double getoTCharges() {
		return oTCharges;
	}
	public void setoTCharges(double oTCharges) {
		this.oTCharges = oTCharges;
	}
	public double getDeliveryCharges() {
		return deliveryCharges;
	}
	public void setDeliveryCharges(double deliveryCharges) {
		this.deliveryCharges = deliveryCharges;
	}
	public double getDaycareCharges() {
		return daycareCharges;
	}
	public void setDaycareCharges(double daycareCharges) {
		this.daycareCharges = daycareCharges;
	}
	public double getAnaesthesia() {
		return anaesthesia;
	}
	public void setAnaesthesia(double anaesthesia) {
		this.anaesthesia = anaesthesia;
	}
	public double getAnaesthesiaCharges() {
		return anaesthesiaCharges;
	}
	public void setAnaesthesiaCharges(double anaesthesiaCharges) {
		this.anaesthesiaCharges = anaesthesiaCharges;
	}
	public double getConsumables() {
		return consumables;
	}
	public void setConsumables(double consumables) {
		this.consumables = consumables;
	}
	public double getInvestigations() {
		return investigations;
	}
	public void setInvestigations(double investigations) {
		this.investigations = investigations;
	}
	public double getDoctorRoundFee() {
		return doctorRoundFee;
	}
	public void setDoctorRoundFee(double doctorRoundFee) {
		this.doctorRoundFee = doctorRoundFee;
	}
	public double getPharmacy() {
		return pharmacy;
	}
	public void setPharmacy(double pharmacy) {
		this.pharmacy = pharmacy;
	}
	public double getPackageCharges() {
		return packageCharges;
	}
	public void setPackageCharges(double packageCharges) {
		this.packageCharges = packageCharges;
	}
	public double getOthers() {
		return others;
	}
	public void setOthers(double others) {
		this.others = others;
	}
	public double getOthersCharges() {
		return othersCharges;
	}
	public void setOthersCharges(double othersCharges) {
		this.othersCharges = othersCharges;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	public double getIcuCharges() {
		return icuCharges;
	}
	public void setIcuCharges(double icuCharges) {
		this.icuCharges = icuCharges;
	}
	public double getOtConsumables() {
		return otConsumables;
	}
	public void setOtConsumables(double otConsumables) {
		this.otConsumables = otConsumables;
	}
	
	
}//end of ClaimantVO.java