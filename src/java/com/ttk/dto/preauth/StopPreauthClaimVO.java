package com.ttk.dto.preauth;

import java.util.Date;

import com.ttk.dto.BaseVO;

public class StopPreauthClaimVO extends BaseVO {
	
	private String strStopPatClmYN="";//stop_pat_clm_process_yn
	private Date dtPatClmRcvdAftr=null;//pat_clm_recieved_after
	
	/** Retrieve the StopPatClmYN
	 * @return the strStopPatClmYN
	 */
	public String getStopPatClmYN() {
		return strStopPatClmYN;
	}//end of getStopPatClmYN() 

	/** Sets the strStopPatClmYN
	 * @param strStopPatClmYN the strStopPatClmYN to set
	 */
	public void setStopPatClmYN(String strStopPatClmYN) {
		this.strStopPatClmYN = strStopPatClmYN;
	}//end of setStopPatClmYN(String strStopPatClmYN) 

	/** Retrieve the dtPatClmRcvdAftr
	 * @return the dtPatClmRcvdAftr
	 */
	public Date getPatClmRcvdAftr() {
		return dtPatClmRcvdAftr;
	}//end of getPatClmRcvdAftr() 

	/** Retrieve the dtPatClmRcvdAftr
	 * @param dtPatClmRcvdAftr the dtPatClmRcvdAftr to set
	 */
	public void setPatClmRcvdAftr(Date dtPatClmRcvdAftr) {
		this.dtPatClmRcvdAftr = dtPatClmRcvdAftr;
	}//end of setPatClmRcvdAftr(Date dtPatClmRcvdAftr)

}
