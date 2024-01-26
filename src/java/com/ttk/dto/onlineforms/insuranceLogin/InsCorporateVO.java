/**
 * @ (#) InsCorporateVO.java 26th August 2015
 * Author      : KISHOR KUMAR S H
 * Company     : RCS TECHNOLOGIES
 * Date Created: 26th August 2015
 *
 * @author 		 : KISHOR KUMAR S H
 * Modified by   : KISHOR KUMAR S H
 * Modified date : 26th August 2015
 * Reason        :
 *
 */

package com.ttk.dto.onlineforms.insuranceLogin;


import java.util.ArrayList;

import com.ttk.dto.BaseVO;
import com.ttk.dto.usermanagement.PersonalInfoVO;

public class InsCorporateVO extends BaseVO{
	
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private PersonalInfoVO personalInfoVO	=	null;
	private PolicyDetailVO policyDetailVO	=	null;
	private ArrayList<ClaimsVO> alClaimDetails		=	null;
	private ArrayList<AuthorizationVO> alAuthorizationDetails		=	null;
	private ArrayList<DependentVO> dependendents		= 	null;
	
	
	public ArrayList<DependentVO> getDependendents() {
		return dependendents;
	}
	public void setDependendents(ArrayList<DependentVO> dependendents) {
		this.dependendents = dependendents;
	}
	
	public ArrayList<ClaimsVO> getClaimDetails() {
		return alClaimDetails;
	}
	public void setClaimDetails(ArrayList<ClaimsVO> alClaimDetails) {
		this.alClaimDetails = alClaimDetails;
	}
	
	
	public ArrayList<AuthorizationVO> getAuthorizationDetails() {
		return alAuthorizationDetails;
	}
	public void setAuthorizationDetails(ArrayList<AuthorizationVO> alAuthorizationDetails) {
		this.alAuthorizationDetails = alAuthorizationDetails;
	}
	
	
	String EnrollmentId;
	
	
	public String getEnrollmentId() {
		return EnrollmentId;
	}
	public void setEnrollmentId(String EnrollmentId) {
		this.EnrollmentId = EnrollmentId;
	}
	public PersonalInfoVO getPersonalInfoVO() {
		return personalInfoVO;
	}

	public void setPersonalInfoVO(PersonalInfoVO personalInfoVO) {
		this.personalInfoVO = personalInfoVO;
	}
	

	public PolicyDetailVO getPolicyDetailVO() {
		return policyDetailVO;
	}
	public void setPolicyDetailVO(PolicyDetailVO policyDetailVO) {
		this.policyDetailVO = policyDetailVO;
	}
	
}//end of InsCorporateVO.java
