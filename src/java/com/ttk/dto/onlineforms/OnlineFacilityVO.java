
/**
 * @ (#) OnlineFacilityVO.java 02 Feb 2015
 * Author      : KISHOR KUMAR S H
 * Company     : RCS TECHNOLOGIES
 * Date Created: 02 Feb 2015
 *
 * @author 		 : KISHOR KUMAR S H
 * Modified by   : KISHOR KUMAR S H
 * Modified date : 02 Feb 2015
 * Reason        :
 *
 */

package com.ttk.dto.onlineforms;


import com.ttk.dto.BaseVO;

public class OnlineFacilityVO extends BaseVO{
	
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	String strFacilityHeader	=	null;
	String strMedicalDesc		=	null;
	String strMedicalAbbr		=	null;
	
	
	public String getFacilityHeader() {
		return strFacilityHeader;
	}
	public void setFacilityHeader(String strFacilityHeader) {
		this.strFacilityHeader = strFacilityHeader;
	}
	
	public String getMedicalDesc() {
		return strMedicalDesc;
	}
	public void setMedicalDesc(String strMedicalDesc) {
		this.strMedicalDesc = strMedicalDesc;
	}
	
	public String getMedicalAbbr() {
		return strMedicalAbbr;
	}
	public void setMedicalAbbr(String strMedicalAbbr) {
		this.strMedicalAbbr = strMedicalAbbr;
	}
}//end of OnlineFacilityVO
