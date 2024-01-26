
/**
 * @ (#) EnrollMemberVO.java 21 August 2015
 * Author      : KISHOR KUMAR S H
 * Company     : RCS TECHNOLOGIES
 * Date Created: 02 Feb 2015
 *
 * @author 		 : KISHOR KUMAR S H
 * Modified by   : KISHOR KUMAR S H
 * Modified date : 21 August 2015
 * Reason        :
 *
 */

package com.ttk.dto.onlineforms.insuranceLogin;


import java.math.BigDecimal;
import java.util.Date;

import com.ttk.common.TTKCommon;
import com.ttk.dto.BaseVO;

public class EnrollMemberVO extends BaseVO{
	

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	String EnrollmentId;
	String MemberName;
	String EmployeeName;
	String EmployeeNo;
	
	public String getEmployeeNo() {
		return EmployeeNo;
	}
	public void setEmployeeNo(String EmployeeNo) {
		this.EmployeeNo = EmployeeNo;
	}
	public String getEnrollmentId() {
		return EnrollmentId;
	}
	public void setEnrollmentId(String EnrollmentId) {
		this.EnrollmentId = EnrollmentId;
	}
	
	public String getMemberName() {
		return MemberName;
	}
	public void setMemberName(String MemberName) {
		this.MemberName = MemberName;
	}
	
	public String getEmployeeName() {
		return EmployeeName;
	}
	public void setEmployeeName(String EmployeeName) {
		this.EmployeeName = EmployeeName;
	}
	
	
	
	/*
	 * for Corporate Grid
	 */
	
	private String groupId;
	private String corpName;
	private String policyNumb;
	private Date effectiveToDate;


	public String getGroupId() {
		return groupId;
	}
	public void setGroupId(String groupId) {
		this.groupId = groupId;
	}
	public String getCorpName() {
		return corpName;
	}
	public void setCorpName(String corpName) {
		this.corpName = corpName;
	}
	public String getPolicyNumb() {
		return policyNumb;
	}
	public void setPolicyNumb(String policyNumb) {
		this.policyNumb = policyNumb;
	}
	public String getEffectiveToDate() {
		return TTKCommon.getFormattedDate(effectiveToDate);
	}
	public void setEffectiveToDate(Date effectiveToDate) {
		this.effectiveToDate = effectiveToDate;
	}
	
	
	//global view enroll data
	private int numberOfEmployees;
	private int numberOfDependents;
	private int noOfEmpCurMonAdded;
	private int noOfDependantsCurMonAdded;
	private int noOfEmpCurMonDeleted;
	private int noOfDepndCurMonDeleted;
	private int noOfEmployeesAdded;
	private int noOfDependantsAdded;
	private int noOfEmployeesDeleted;
	private int noOfDependantsDeleted;
	private BigDecimal ttlPremiumAtPolicy;
	private BigDecimal ttlEarnedPremium;
	
	public int getNumberOfEmployees() {
		return numberOfEmployees;
	}
	public void setNumberOfEmployees(int numberOfEmployees) {
		this.numberOfEmployees = numberOfEmployees;
	}
	public int getNumberOfDependents() {
		return numberOfDependents;
	}
	public void setNumberOfDependents(int numberOfDependents) {
		this.numberOfDependents = numberOfDependents;
	}
	public int getNoOfEmpCurMonAdded() {
		return noOfEmpCurMonAdded;
	}
	public void setNoOfEmpCurMonAdded(int noOfEmpCurMonAdded) {
		this.noOfEmpCurMonAdded = noOfEmpCurMonAdded;
	}
	public int getNoOfDependantsCurMonAdded() {
		return noOfDependantsCurMonAdded;
	}
	public void setNoOfDependantsCurMonAdded(int noOfDependantsCurMonAdded) {
		this.noOfDependantsCurMonAdded = noOfDependantsCurMonAdded;
	}
	public int getNoOfEmpCurMonDeleted() {
		return noOfEmpCurMonDeleted;
	}
	public void setNoOfEmpCurMonDeleted(int noOfEmpCurMonDeleted) {
		this.noOfEmpCurMonDeleted = noOfEmpCurMonDeleted;
	}
	public int getNoOfDepndCurMonDeleted() {
		return noOfDepndCurMonDeleted;
	}
	public void setNoOfDepndCurMonDeleted(int noOfDepndCurMonDeleted) {
		this.noOfDepndCurMonDeleted = noOfDepndCurMonDeleted;
	}
	public int getNoOfEmployeesAdded() {
		return noOfEmployeesAdded;
	}
	public void setNoOfEmployeesAdded(int noOfEmployeesAdded) {
		this.noOfEmployeesAdded = noOfEmployeesAdded;
	}
	public int getNoOfDependantsAdded() {
		return noOfDependantsAdded;
	}
	public void setNoOfDependantsAdded(int noOfDependantsAdded) {
		this.noOfDependantsAdded = noOfDependantsAdded;
	}
	public int getNoOfEmployeesDeleted() {
		return noOfEmployeesDeleted;
	}
	public void setNoOfEmployeesDeleted(int noOfEmployeesDeleted) {
		this.noOfEmployeesDeleted = noOfEmployeesDeleted;
	}
	public int getNoOfDependantsDeleted() {
		return noOfDependantsDeleted;
	}
	public void setNoOfDependantsDeleted(int noOfDependantsDeleted) {
		this.noOfDependantsDeleted = noOfDependantsDeleted;
	}
	
	
	//RETAIL GLOBAL VIEW
	private int numberOfPolicies;
	private int numberOfLives;
	private BigDecimal totalGrossPremium;
	private BigDecimal totalEarnedPremium;

	public int getNumberOfPolicies() {
		return numberOfPolicies;
	}
	public void setNumberOfPolicies(int numberOfPolicies) {
		this.numberOfPolicies = numberOfPolicies;
	}
	public int getNumberOfLives() {
		return numberOfLives;
	}
	public void setNumberOfLives(int numberOfLives) {
		this.numberOfLives = numberOfLives;
	}
	public BigDecimal getTotalGrossPremium() {
		return totalGrossPremium;
	}
	public void setTotalGrossPremium(BigDecimal totalGrossPremium) {
		this.totalGrossPremium = totalGrossPremium;
	}
	public BigDecimal getTotalEarnedPremium() {
		return totalEarnedPremium;
	}
	public void setTotalEarnedPremium(BigDecimal totalEarnedPremium) {
		this.totalEarnedPremium = totalEarnedPremium;
	}
	public BigDecimal getTtlPremiumAtPolicy() {
		return ttlPremiumAtPolicy;
	}
	public void setTtlPremiumAtPolicy(BigDecimal ttlPremiumAtPolicy) {
		this.ttlPremiumAtPolicy = ttlPremiumAtPolicy;
	}
	public BigDecimal getTtlEarnedPremium() {
		return ttlEarnedPremium;
	}
	public void setTtlEarnedPremium(BigDecimal ttlEarnedPremium) {
		this.ttlEarnedPremium = ttlEarnedPremium;
	}
	
	
	
}//end of EnrollMemberVO.java
