
/**
 * @ (#) InsGlobalView.java 26th August 2015
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
import com.ttk.dto.BaseVO;

public class InsGlobalViewVO extends BaseVO{
	
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private AuthorizationVO authorizationVO		=	null;
	private EnrollMemberVO  enrollMemberVO		=	null;
	private ClaimsVO	   	claimsVO			=	null;
	private CallCenterDetailsVO	   	callCenterDetailsVO			=	null;
	private NetworkDetailsVO networkDetailsVO	=	null;
	

	public AuthorizationVO getAuthorizationVO() {
		return authorizationVO;
	}

	public void setAuthorizationVO(AuthorizationVO authorizationVO) {
		this.authorizationVO = authorizationVO;
	}

	public EnrollMemberVO getEnrollMemberVO() {
		return enrollMemberVO;
	}

	public void setEnrollMemberVO(EnrollMemberVO enrollMemberVO) {
		this.enrollMemberVO = enrollMemberVO;
	}

	public ClaimsVO getClaimsVO() {
		return claimsVO;
	}

	public void setClaimsVO(ClaimsVO claimsVO) {
		this.claimsVO = claimsVO;
	}

	public CallCenterDetailsVO getCallCenterDetailsVO() {
		return callCenterDetailsVO;
	}

	public void setCallCenterDetailsVO(CallCenterDetailsVO callCenterDetailsVO) {
		this.callCenterDetailsVO = callCenterDetailsVO;
	}

	public NetworkDetailsVO getNetworkDetailsVO() {
		return networkDetailsVO;
	}

	public void setNetworkDetailsVO(NetworkDetailsVO networkDetailsVO) {
		this.networkDetailsVO = networkDetailsVO;
	}


	
	
}//end of InsGlobalView.java
