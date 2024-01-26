/**
 * @ (#) RetailVO.java 26th August 2015
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
import java.util.Date;

import com.ttk.dto.BaseVO;
import com.ttk.dto.usermanagement.PersonalInfoVO;

public class RetailVO extends BaseVO{
	
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private PersonalInfoVO personalInfoVO	=	null;
	private PolicyDetailVO policyDetailVO	=	null;
	private ArrayList<ClaimsVO> alClaimDetails		=	null;
	private ArrayList<AuthorizationVO> alAuthorizationDetails		=	null;
	private ArrayList<DependentVO> dependendents		= 	null;
	
	
	
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
	public ArrayList<ClaimsVO> getClaimDetails() {
		return alClaimDetails;
	}
	public void setClaimDetails(ArrayList<ClaimsVO> alClaimDetails) {
		this.alClaimDetails = alClaimDetails;
	}
	public ArrayList<AuthorizationVO> getAuthorizationDetails() {
		return alAuthorizationDetails;
	}
	public void setAuthorizationDetails(
			ArrayList<AuthorizationVO> alAuthorizationDetails) {
		this.alAuthorizationDetails = alAuthorizationDetails;
	}
	public ArrayList<DependentVO> getDependendents() {
		return dependendents;
	}
	public void setDependendents(ArrayList<DependentVO> dependendents) {
		this.dependendents = dependendents;
	}
	
		//for Retail Search getters - retail search grid
		private int productId;
		private String productName;
		private Date dateFrom;
		private Date DateTo;
		private String dateAsString;

		public int getProductId() {
			return productId;
		}
		public void setProductId(int productId) {
			this.productId = productId;
		}
		public String getProductName() {
			return productName;
		}
		public void setProductName(String productName) {
			this.productName = productName;
		}
		public Date getDateFrom() {
			return dateFrom;
		}
		public void setDateFrom(Date dateFrom) {
			this.dateFrom = dateFrom;
		}
		public Date getDateTo() {
			return DateTo;
		}
		public void setDateTo(Date dateTo) {
			DateTo = dateTo;
		}
		public String getDateAsString() {
			return dateAsString;
		}
		public void setDateAsString(String dateAsString) {
			this.dateAsString = dateAsString;
		}
	
}//end of RetailVO.java
