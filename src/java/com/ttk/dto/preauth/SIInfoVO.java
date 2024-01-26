/**
 * @ (#) SIInfoVO.java Sep 14, 2009
 * Project 	     : TTK HealthCare Services
 * File          : SIInfoVO.java
 * Author        : RamaKrishna K M
 * Company       : Span Systems Corporation
 * Date Created  : Sep 14, 2009
 *
 * @author       :  RamaKrishna K M
 * Modified by   :  
 * Modified date :  
 * Reason        :  
 */

package com.ttk.dto.preauth;

import java.util.ArrayList;

import com.ttk.dto.BaseVO;
import com.ttk.dto.preauth.RestorationPreauthClaimVO;

/**This VO Class is used to display the SI Info in Preauth & Claims.
 * @author ramakrishna_km
 *
 */
public class SIInfoVO extends BaseVO{
	
	/** Serial Version ID.
	 * 
	 */
	private static final long serialVersionUID = 9114000444840668968L;
	
	private ArrayList<Object> alSIBreakupList = null;
	private BalanceSIInfoVO balSIInfoVO = null;
	private StopPreauthClaimVO stopPreClmVO = null;
	//	<!-- added for koc1289_1272  -->
	private RestorationPreauthClaimVO restorationPreauthClaimVO = null;
	
//	/	<!--End added for koc1289_1272  -->
	//Changes as per koc 1216B change request 
	private MemberBufferVO memberBufferVO=null;
	// Start Changes As per KOC1142 (Copay restriction)
	private BalanceCopayDeductionVO balCopayDeducVO=null; 
	
	
	/**
	 * @param balCopayDeducVO the balCopayDeducVO to set
	 */
	public void setBalCopayDeducVO(BalanceCopayDeductionVO balCopayDeducVO) {
		this.balCopayDeducVO = balCopayDeducVO;
	}

	/**
	 * @return the balCopayDeducVO
	 */
	public BalanceCopayDeductionVO getBalCopayDeducVO() {
		return balCopayDeducVO;
	}
	//end Changes As per KOC1142 (Copay restriction)
	/**
	 * @param memberBufferVO the memberBufferVO to set
	 */
	public void setMemberBufferVO(MemberBufferVO memberBufferVO) {
		this.memberBufferVO = memberBufferVO;
	}

	/**
	 * @return the memberBufferVO
	 */
	public MemberBufferVO getMemberBufferVO() {
		return memberBufferVO;
	}
	
	//Changes as per koc 1216B change request 
	
	
	/** Retrieve the SIBreakupList.
	 * @return Returns the alSIBreakupList.
	 */
	public ArrayList<Object> getSIBreakupList() {
		return alSIBreakupList;
	}//end of getSIBreakupList()
	
	/** Sets the SIBreakupList.
	 * @param alSIBreakupList The alSIBreakupList to set.
	 */
	public void setSIBreakupList(ArrayList<Object> alSIBreakupList) {
		this.alSIBreakupList = alSIBreakupList;
	}//end of setSIBreakupList(ArrayList<Object> alSIBreakupList)
	
	/** Retrieve the Balance SI InfoVO.
	 * @return Returns the balSIInfoVO.
	 */
	public BalanceSIInfoVO getBalSIInfoVO() {
		return balSIInfoVO;
	}//end of getBalSIInfoVO()
	
	/** Sets the Balance SI InfoVO.
	 * @param balSIInfoVO The balSIInfoVO to set.
	 */
	public void setBalSIInfoVO(BalanceSIInfoVO balSIInfoVO) {
		this.balSIInfoVO = balSIInfoVO;
	}//end of setBalSIInfoVO(BalanceSIInfoVO balSIInfoVO)
	
	/** Retrieve the Balance SI InfoVO.
	 * @return Returns the stopPreClmVO.
	 */
	public StopPreauthClaimVO getStopPreClmVO() {
		return stopPreClmVO;
	}//end of getBalSIInfoVO()
	
	/** Sets the StopPreauthClaimVO.
	 * @param StopPreauthClaimVO The StopPreauthClaimVO to set.
	 */
	public void setStopPreClmVO(StopPreauthClaimVO stopPreClmVO) {
		this.stopPreClmVO = stopPreClmVO;
	}//end of setStopPreClmVO(StopPreauthClaimVO stopPreauthClaimVO)
	
	//	<!-- added for koc1289_1272  -->
	
	public RestorationPreauthClaimVO getRestorationPreauthClaimVO() {
		return restorationPreauthClaimVO;
	}//end of getBalSIInfoVO()
	
	/** Sets the StopPreauthClaimVO.
	 * @param StopPreauthClaimVO The StopPreauthClaimVO to set.
	 */
	public void setRestorationPreauthClaimVO(RestorationPreauthClaimVO restorationPreauthClaimVO) {
		this.restorationPreauthClaimVO = restorationPreauthClaimVO;
	}//end of setStopPreClmVO(StopPreauthClaimVO stopPreauthClaimVO)
	
	//	<!-- added for koc1289_1272  -->
}//end of SIInfoVO
