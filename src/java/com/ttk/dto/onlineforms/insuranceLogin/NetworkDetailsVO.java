/**
 * @ (#) NetworkDetailsVO.java 27th August 2015
 * Author      : KISHOR KUMAR S H
 * Company     : RCS TECHNOLOGIES
 * Date Created: 27th August 2015
 *
 * @author 		 : KISHOR KUMAR S H
 * Modified by   : KISHOR KUMAR S H
 * Modified date : 27th August 2015
 * Reason        :
 *
 */
package com.ttk.dto.onlineforms.insuranceLogin;


import com.ttk.dto.BaseVO;

public class NetworkDetailsVO extends BaseVO{
	
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int noOfCnNetwork;
	private int noOfGnNetwork;
	private int noOfSnNetwork;
	private int noOfBnNetwork;
	private int noOfWnNetwork;
	private int totalNoOfProviders;
	private int numberOfHospitals;
	private int numberOfMedicalcenter;
	private int numberOfClinics;
	private int numberOfPharmacies;
	private int numberOfDiagCenters;    
	private int totalNoOfProvidersUsed;
	
	
	public int getNoOfCnNetwork() {
		return noOfCnNetwork;
	}
	public void setNoOfCnNetwork(int noOfCnNetwork) {
		this.noOfCnNetwork = noOfCnNetwork;
	}
	public int getNoOfGnNetwork() {
		return noOfGnNetwork;
	}
	public void setNoOfGnNetwork(int noOfGnNetwork) {
		this.noOfGnNetwork = noOfGnNetwork;
	}
	public int getNoOfSnNetwork() {
		return noOfSnNetwork;
	}
	public void setNoOfSnNetwork(int noOfSnNetwork) {
		this.noOfSnNetwork = noOfSnNetwork;
	}
	public int getNoOfBnNetwork() {
		return noOfBnNetwork;
	}
	public void setNoOfBnNetwork(int noOfBnNetwork) {
		this.noOfBnNetwork = noOfBnNetwork;
	}
	public int getNoOfWnNetwork() {
		return noOfWnNetwork;
	}
	public void setNoOfWnNetwork(int noOfWnNetwork) {
		this.noOfWnNetwork = noOfWnNetwork;
	}
	public int getTotalNoOfProviders() {
		return totalNoOfProviders;
	}
	public void setTotalNoOfProviders(int totalNoOfProviders) {
		this.totalNoOfProviders = totalNoOfProviders;
	}
	public int getNumberOfHospitals() {
		return numberOfHospitals;
	}
	public void setNumberOfHospitals(int numberOfHospitals) {
		this.numberOfHospitals = numberOfHospitals;
	}
	public int getNumberOfMedicalcenter() {
		return numberOfMedicalcenter;
	}
	public void setNumberOfMedicalcenter(int numberOfMedicalcenter) {
		this.numberOfMedicalcenter = numberOfMedicalcenter;
	}
	public int getNumberOfClinics() {
		return numberOfClinics;
	}
	public void setNumberOfClinics(int numberOfClinics) {
		this.numberOfClinics = numberOfClinics;
	}
	public int getNumberOfPharmacies() {
		return numberOfPharmacies;
	}
	public void setNumberOfPharmacies(int numberOfPharmacies) {
		this.numberOfPharmacies = numberOfPharmacies;
	}
	public int getNumberOfDiagCenters() {
		return numberOfDiagCenters;
	}
	public void setNumberOfDiagCenters(int numberOfDiagCenters) {
		this.numberOfDiagCenters = numberOfDiagCenters;
	}
	public int getTotalNoOfProvidersUsed() {
		return totalNoOfProvidersUsed;
	}
	public void setTotalNoOfProvidersUsed(int totalNoOfProvidersUsed) {
		this.totalNoOfProvidersUsed = totalNoOfProvidersUsed;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	
}

