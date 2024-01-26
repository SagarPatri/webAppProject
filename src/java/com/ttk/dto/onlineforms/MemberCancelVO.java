/**
 * @ (#) SumInsuredVO.java Jan 16, 2007
 * Project 	     : TTK HealthCare Services
 * File          : SumInsuredVO.java
 * Author        : Balakrishna E
 * Company       : Span Systems Corporation
 * Date Created  : Jan 16, 2007
 *
 * @author       :  Balakrishna E
 * Modified by   :  
 * Modified date :  
 * Reason        :  
 */

package com.ttk.dto.onlineforms;

//import java.util.Date;

import com.ttk.dto.enrollment.MemberDetailVO;


public class MemberCancelVO extends MemberDetailVO  {
    
//    private Date dtDateOfExit  =null; //date_of_exit
    private String strStopPatClmProcessYN =""; //stop_pat_clm_process_yn
//    private Date dtRecievedAfter =null; //recieved_after
   // private Date dtEffectiveFromDate =null;	//effective_from_date
    //effective_to_date
    private String strFlag ="";//v_flag
    private String strAllowEditYN ="";//allow_edit_yn
	
    
   /* *//** Retrive the Date of EXIT
	 * @return Returns the dtDateOfExit.
	 *//*
	public Date getDateOfExit() {
		return dtDateOfExit;
	}//end of getDateOfExit() 
	*//** Sets the Date of EXIT
	 * @param dtDateOfExit The dtDateOfExit to set.
	 *//*
	public void setDateOfExit(Date dtDateOfExit) {
		this.dtDateOfExit = dtDateOfExit;
	}//end of setDateOfExit(Date dtDateOfExit) 
	*/
	/** Retrive teh Recieved After
	 * @return Returns the dtRecievedAfter.
	 */
	/*public Date getRecievedAfter() {
		return dtRecievedAfter;
	}//end of getRecievedAfter() 
	
	*//** Sets the Recieved After
	 * @param dtRecievedAfter The dtRecievedAfter to set.
	 *//*
	public void setRecievedAfter(Date dtRecievedAfter) {
		this.dtRecievedAfter = dtRecievedAfter;
	}//end of setRecievedAfter(Date dtRecievedAfter)
*/	
	/** Retrive the Allowed Edit YN
	 * @return Returns the strAllowEditYN.
	 */
	public String getAllowEditYN() {
		return strAllowEditYN;
	}//end of getAllowEditYN()
	
	/** Sets the Allowed Edit YN
	 * @param strAllowEditYN The strAllowEditYN to set.
	 */
	public void setAllowEditYN(String strAllowEditYN) {
		this.strAllowEditYN = strAllowEditYN;
	}//end of setAllowEditYN(String strAllowEditYN) 
	
	/** Retrive the Flag
	 * @return Returns the strFlag.
	 */
	public String getFlag() {
		return strFlag;
	}//end of getFlag()
	
	/** Sets the Flag
	 * @param strFlag The strFlag to set.
	 */
	public void setFlag(String strFlag) {
		this.strFlag = strFlag;
	}//end of setFlag(String strFlag) 
	
	/** Retrive the Stop Claim Process YN
	 * @return Returns the strStopPatClmProcessYN.
	 */
	public String getStopPatClmProcessYN() {
		return strStopPatClmProcessYN;
	}//end of getStopPatClmProcessYN() 
	
	/** Sets the Claim Process YN
	 * @param strStopPatClmProcessYN The strStopPatClmProcessYN to set.
	 */
	public void setStopPatClmProcessYN(String strStopPatClmProcessYN) {
		this.strStopPatClmProcessYN = strStopPatClmProcessYN;
	}//end of setStopPatClmProcessYN(String strStopPatClmProcessYN)
        
}//end of MemberAddressVO
