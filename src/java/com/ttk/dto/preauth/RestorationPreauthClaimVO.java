package com.ttk.dto.preauth;

import java.math.BigDecimal;

import com.ttk.dto.BaseVO;

public class RestorationPreauthClaimVO extends BaseVO {
	
	private String strRestorationYN="";//RESTORATION_YN
	private BigDecimal bdRestSumInsured=null;
	
	
	public String getRestorationYN() {
		return strRestorationYN;
	}//end of getStopPatClmYN() 

	/** Sets the strStopPatClmYN
	 * @param strStopPatClmYN the strStopPatClmYN to set
	 */
	public void setRestorationYN(String strRestorationYN) {
		this.strRestorationYN = strRestorationYN;
	}//end of setStopPatClmYN(String strStopPatClmYN) 

	
	public BigDecimal getRestSumInsured() {
		return bdRestSumInsured;
	}
	public void setRestSumInsured(BigDecimal bdRestSumInsured) {
		this.bdRestSumInsured = bdRestSumInsured;
	}

	

}
