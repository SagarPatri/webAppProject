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

//import com.ttk.dto.BaseVO;
import com.ttk.dto.enrollment.MemberDetailVO;


public class SumInsuredVO extends MemberDetailVO {
    
    private Long lngMemInsuredSeqID  =null; //mem_insured_seq_id
    private String strProdPlanSeqId =""; //prod_plan_seq_id
    private String strProdPlanName =""; //prod_plan_name
    private String strPlanAmount ="";	//plan_amount
    private String strPlanPremium ="";	//plan_premium
    private Integer intFromAge =0;	//from_age
    private Integer intToAge =0;	//to_age
    private String strSelectedYN ="";	//selected_yn
    private String strFlag ="";	//v_flag
    private String strCombineAge =""; //from_age-to_age
     // Changes added for KOC1184
    private String strProRata =""; //strProRata premium
	 // Change added for CR KOC1227C
    private String strPremiumDeductionOption=""; //strPremiumDeductionOption
	
	/** This method returns the Premium Deduction Option Selected
     * @return Returns the strPremiumDeductionOption.
     */
    public String getPremiumDeductionOption() {
		return strPremiumDeductionOption;
	}

    /** This method sets the Premium Deduction Option
     * @param strPremiumDeductionOption The Premium Deduction Option selected.
     */
	public void setPremiumDeductionOption(String strPremiumDeductionOption) {
		this.strPremiumDeductionOption = strPremiumDeductionOption;
	}
	
    
    /** This method returns the Pro rata premium
     * @return Returns the strProRata.
     */
    public String getProRata() {
		return strProRata;
	}
    
    /** This method sets the Pro rata premium
     * @param strProRata The Pro rata premium to set.
     */
	public void setProRata(String strProRata) {
		this.strProRata = strProRata;
	}
	// End changes added for KOC1184
	
	
	
    
    /** This method returns the Member Insured Sequence ID
     * @return Returns the lngMemInsuredSeqID.
     */
    public Long getMemInsuredSeqID() {
        return lngMemInsuredSeqID;
    }//end of getMemberSeqID()
    
    /** This method sets the Member Sequence ID
     * @param lngMemberSeqID The Member Insured Sequence ID to set.
     */
    public void setMemInsuredSeqID(Long lngMemInsuredSeqID) {
        this.lngMemInsuredSeqID = lngMemInsuredSeqID;
    }//end of setMemInsuredSeqID(Long lngMemInsuredSeqID)

	/** Retrive the From Age
	 * @return Returns the strFromAge.
	 */
	public Integer getFromAge() {
		return intFromAge;
	}//end of getFromAge()

	/** Sets the From Age
	 * @param strFromAge The strFromAge to set.
	 */
	public void setFromAge(Integer intFromAge) {
		this.intFromAge = intFromAge;
	}//setFromAge(Integer strFromAge)

	/** Retrive the Plan Amount
	 * @return Returns the strPlanAmount.
	 */
	public String getPlanAmount() {
		return strPlanAmount;
	}//end of getPlanAmount()

	/** Sets the Plan Amount
	 * @param strPlanAmount The strPlanAmount to set.
	 */
	public void setPlanAmount(String strPlanAmount) {
		this.strPlanAmount = strPlanAmount;
	}//end of setPlanAmount(String strPlanAmount)

	/** Retrive the Plan Premium
	 * @return Returns the strPlanPremium.
	 */
	public String getPlanPremium() {
		return strPlanPremium;
	}//end of getPlanPremium()

	/** Sets the Plan Premium 
	 * @param strPlanPremium The strPlanPremium to set.
	 */
	public void setPlanPremium(String strPlanPremium) {
		this.strPlanPremium = strPlanPremium;
	}//end of the setPlanPremium(String strPlanPremium) 

	/** Retrive the Product Plan Name
	 * @return Returns the strProdPlanName.
	 */
	public String getProdPlanName() {
		return strProdPlanName;
	}//end of getProdPlanName() 

	/** Sets the Prod Plan Name
	 * @param strProdPlanName The strProdPlanName to set.
	 */
	public void setProdPlanName(String strProdPlanName) {
		this.strProdPlanName = strProdPlanName;
	}//end of setProdPlanName(String strProdPlanName) 

	/** Retrive the Product plan Seq ID
	 * @return Returns the strProdPlanSeqId.
	 */
	public String getProdPlanSeqId() {
		return strProdPlanSeqId;
	}//end of getProdPlanSeqId() 

	/** Sets the Product Plan Seq ID
	 * @param strProdPlanSeqId The strProdPlanSeqId to set.
	 */
	public void setProdPlanSeqId(String strProdPlanSeqId) {
		this.strProdPlanSeqId = strProdPlanSeqId;
	}//end of setProdPlanSeqId(String strProdPlanSeqId) 

	/** Retrive the Selected YN
	 * @return Returns the strSelectedYN.
	 */
	public String getSelectedYN() {
		return strSelectedYN;
	}//end of getSelectedYN()

	/** Sets the Selected YN 
	 * @param strSelectedYN The strSelectedYN to set.
	 */
	public void setSelectedYN(String strSelectedYN) {
		this.strSelectedYN = strSelectedYN;
	}//end of setSelectedYN(String strSelectedYN) 

	/** Retrive the To Age
	 * @return Returns the strToAge.
	 */
	public Integer getToAge() {
		return intToAge;
	}//end of getToAge() 

	/** Sets the To Age
	 * @param strToAge The strToAge to set.
	 */
	public void setToAge(Integer intToAge) {
		this.intToAge = intToAge;
	}//end of setToAge(String intToAge)   

	/** Retrive the Flag
	 * @return Returns the strFlag.
	 */
	public String getFlag() {
		return strFlag;
	}//end of getFlag() 

	/** Sets the Flag value
	 * @param strFlag The strFlag to set.
	 */
	public void setFlag(String strFlag) {
		this.strFlag = strFlag;
	}//end of setFlag(String strFlag)    

	/** Retrive the From Age - To Age
	 * @return Returns the strAge.
	 */
	public String getCombineAge() {
		return strCombineAge;
	}//end of getCombineAge()

	/** Sets the From Age - To Age
	 * @param strAge The strAge to set.
	 */
	public void setCombineAge(String strCombineAge) {
		this.strCombineAge = strCombineAge;
	}//end of setCombineAge(String strCombineAge)
    
}//end of MemberAddressVO
