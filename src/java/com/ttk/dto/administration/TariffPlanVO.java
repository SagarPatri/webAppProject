/**
 * @ (#)  TariffPlanVO.java Oct 3, 2005
 * Project      : TTKPROJECT
 * File         : TariffPlanVO.java
 * Author       : Suresh.M
 * Company      : 
 * Date Created : Oct 3, 2005
 *
 * @author       :  Suresh.M
 * Modified by   : 
 * Modified date : 
 * Reason        : 
 */
package com.ttk.dto.administration;

import com.ttk.dto.BaseVO;

public class TariffPlanVO extends BaseVO{
	
	private Long lTariffPlanID = null;			  //PLAN_SEQ_ID
	private String strTariffPlanName = "";	      //PLAN_NAME
	private String strTariffPlanDescription = ""; //PLAN_DESCRIPTION
    private String strImageName = "HistoryIcon";
    private String strImageTitle = "Revision History"; 
    private String strDefaultPlanYn = "";
    
    private String sSlno="";
	private String sCount="";
	private String sUploadedDate="";
	private String sUploadedBy="";
	private String sAddedby="";

    /**
     * Retrieve the Image Name
     * 
     * @return  strImageName String Image Name
     */
    public String getImageName() {
        return strImageName;
    }//end of getImageName()
    
    /**
     * Sets the Image Name
     * 
     * @param  strImageName String Image Name
     */
    public void setImageName(String strImageName) {
        this.strImageName = strImageName;
    }//end of setImageName(String strImageName)
    
    /**
     * Retrieve the Revision History Image Title
     * 
     * @return  strImageTitle String Revision History Image Title
     */
    public String getImageTitle() {
        return strImageTitle;
    }//end of getImageTitle()
    
    /**
     * Sets the Image Title
     * 
     * @param  strImageTitle String Image Title 
     */
    public void setImageTitle(String strImageTitle) {
        this.strImageTitle = strImageTitle;
    }//end of setImageTitle(String strImageTitle)
    
    /** Retrieve the Tariff Plan ID
	 * @return lTariffPlanID Long Tariff Plan ID
	 */
	public Long getTariffPlanID() {
		return lTariffPlanID;
	}//end of getTariffPlanID()
	
	/**sets the lTariffPlanID
	 * @param lTariffplanID Long Tariff Plan ID
	 */
	public void setTariffPlanID(Long lTariffPlanID) {
		this.lTariffPlanID = lTariffPlanID;
	}//end of setTariffPlanID(Long lTariffPlanID)
	
	/**Retrive the Tariff Plan Description
	 * @return strTariffPlanDescription String Tariff Plan Description
	 */
	public String getTariffPlanDesc() {
		return strTariffPlanDescription;
	}//end of getTariffPlanDesc
	
	/**sets the Tariff Plan Description
	 * @param strTariffPlanDescription String Tariff Plan Description
	 */
	public void setTariffPlanDesc(String strTariffPlanDescription) {
		this.strTariffPlanDescription = strTariffPlanDescription;
	}//end of setTariffPlanDesc(String strTariffPlanDescription)
	
	/**Retrieve the Tariff Plan Name
	 * @return strTariffPlanName String Plan Name
	 */
	public String getTariffPlanName() {
		return strTariffPlanName;
	}//end of getTariffPlanName()
	
	/**sets the Tariff Plan Name
	 * @param strTariffPlanName String Plan Name
	 */
	public void setTariffPlanName(String strTariffPlanName) {
		this.strTariffPlanName = strTariffPlanName;
	}//end of setTariffPlanName(String strTariffPlanName)
    
    /**Retrieve the Default Plan YN
     * @return strDefaultPlanYn String Default Plan YN
     */
    public String getDefaultPlanYn() {
        return strDefaultPlanYn;
    }//end of getDefaultPlanYn()
    
    /**Sets the Default Plan YN
     * @param strDefaultPlanYn String Default Plan YN
     */
    public void setDefaultPlanYn(String strDefaultPlanYn) {
        this.strDefaultPlanYn = strDefaultPlanYn;
    }//end of setDefaultPlanYn(String strDefaultPlanYn)

	public String getsSlno() {
		return sSlno;
	}

	public void setsSlno(String sSlno) {
		this.sSlno = sSlno;
	}

	public String getsCount() {
		return sCount;
	}

	public void setsCount(String sCount) {
		this.sCount = sCount;
	}

	public String getsUploadedDate() {
		return sUploadedDate;
	}

	public void setsUploadedDate(String sUploadedDate) {
		this.sUploadedDate = sUploadedDate;
	}

	public String getsUploadedBy() {
		return sUploadedBy;
	}

	public void setsUploadedBy(String sUploadedBy) {
		this.sUploadedBy = sUploadedBy;
	}

	public String getsAddedby() {
		return sAddedby;
	}

	public void setsAddedby(String sAddedby) {
		this.sAddedby = sAddedby;
	}
    
}//end of class TariffPlanVO
