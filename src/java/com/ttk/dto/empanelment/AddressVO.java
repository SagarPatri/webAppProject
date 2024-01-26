/**
 * @ (#) AddressVO.java Sep 19, 2005
 * Project      : TTK HealthCare Services
 * File         : AddressVO.java
 * Author       : Arun K N
 * Company      : Span Systems Corporation
 * Date Created : Sep 19, 2005
 *
 * @author       :  Arun K N
 * Modified by   : 
 * Modified date : 
 * Reason        : 
 */

package com.ttk.dto.empanelment;

import com.ttk.dto.BaseVO;

/**
 * This VO contains all details of any address. 
 * The corresponding DB Table is TPA_HOSP_ADDRESS.
 */
public class AddressVO extends BaseVO{
    
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	//  declaration of the fields as private

	private Long lAddrSeqId=null;// Address Id
    private String strAddress1="";// Address1
    private String strAddress2="";// Address2
    private String strAddress3="";// Address3
    private String strCityCode="";// City code
    private String strStateCode="";// State code
    private String strCountryCode="";// Country Code
    private String strPinCode="";// Pin Code
    private String strCityDesc = "";
    private String strStateName = "";
    private String strCountryName = "";
    private String districtCode="";
    
    private String off1StdCode="";
    private String off1IsdCode="";
    private String off2StdCode="";
    private String off2IsdCode="";
    private String homeStdCode="";
    private String homeIsdCode="";
    private String mobileIsdCode="";
   
    
    //intX
    
    private int iIsdCode			= 	0;
    private int iStdCode			= 	0;
    private String strPhoneNo1 		= 	null;
    private String strPhoneNo2 		= 	null;
    
    public int getIsdCode() {
		return iIsdCode;
	}

	public void setIsdCode(int iIsdCode) {
		this.iIsdCode = iIsdCode;
	}

	public int getStdCode() {
		return iStdCode;
	}

	public void setStdCode(int iStdCode) {
		this.iStdCode = iStdCode;
	}

	public String getPhoneNo1() {
		return strPhoneNo1;
	}

	public void setPhoneNo1(String strPhoneNo1) {
		this.strPhoneNo1 = strPhoneNo1;
	}

	public String getPhoneNo2() {
		return strPhoneNo2;
	}

	public void setPhoneNo2(String strPhoneNo2) {
		this.strPhoneNo2 = strPhoneNo2;
	}
    /**
     * Retrieve the Country Code
     * 
     * @return  strCountryCode String Country Code
     */
    public String getCountryCode() {
        return strCountryCode;
    }//end of getCountryCode()
    
    /**
     * Sets the Country Code
     * 
     * @param  strCountryCode String Country Code 
     */
    public void setCountryCode(String strCountryCode) {
       this.strCountryCode = strCountryCode;
    }//end of setCountryCode(String strCountryCode)
    
    /**
     * Retrieve the State Code
     * 
     * @return  strStateCode String State Code
     */
    public String getStateCode() {
        return strStateCode;
    }//end of getStateCode()
    /**
     * Sets the State Code
     * 
     * @param  strStateCode String State Code 
     */
    public void setStateCode(String strStateCode) {
        this.strStateCode = strStateCode;
    }//end of setStateCode(String strStateCode)
    
    /**
     * Retrieve the Address Sequence Id
     * 
     * @return  lAddrSeqId Long Address Sequence Id
     */
    public Long getAddrSeqId() {
        return lAddrSeqId;
    }//end of getAddrSeqId()
    
    /**
     * Sets the Address Sequence Id
     * 
     * @param  lAddrSeqId Long Address Sequence Id 
     */
    public void setAddrSeqId(Long lAddrSeqId) {
        this.lAddrSeqId = lAddrSeqId;
    }//end of setAddrSeqId(Long lAddrSeqId)
    
    /**
     * Retrieve the Address1
     * 
     * @return  strAddress1 String Address1
     */
    public String getAddress1() {
        return strAddress1;
    }//end of getAddress1()
    
    /**
     * Sets the Address1
     * 
     * @param  strAddress1 String Address1
     */
    public void setAddress1(String strAddress1) {
        this.strAddress1 = strAddress1;
    }//end of setAddress1(String strAddress1)
    
    /**
     * Retrieve the Address2
     * 
     * @return  strAddress2 String Address2
     */
    public String getAddress2() {
        return strAddress2;
    }//end of getAddress2()
    
    /**
     * Sets the Address2
     * 
     * @param  strAddress2 String Address2 
     */
    public void setAddress2(String strAddress2) {
        this.strAddress2 = strAddress2;
    }//end of setAddress2(String strAddress2)
    
    /**
     * Retrieve the Address3
     * 
     * @return  strAddress3 String Address3
     */
    public String getAddress3() {
        return strAddress3;
    }//end of getAddress3()
    
    /**
     * Sets the Address3
     * 
     * @param  strAddress3 String Address3 
     */
    public void setAddress3(String strAddress3) {
        this.strAddress3 = strAddress3;
    }//end of setAddress3(String strAddress3)
    
    /**
     * Retrieve the City Code
     * 
     * @return  strCityCode String City Code
     */
    public String getCityCode() {
        return strCityCode;
    }// end of getCityCode() 
    
    /**
     * Sets the City Code
     * 
     * @param  strCityCode String City Code 
     */
    public void setCityCode(String strCityCode) {
        this.strCityCode = strCityCode;
    }//end of setCityCode(String strCityCode)
    
    /**
     * Retrieve the Pin Code
     * 
     * @return  strPinCode String Pin Code
     */
    public String getPinCode() {
        return strPinCode;
    }//end of  getPinCode()
    
    /**
     * Sets the Pin Code
     * 
     * @param  strPinCode String Pin Code 
     */
    public void setPinCode(String strPinCode) {
        this.strPinCode = strPinCode;
    }//end of setPinCode(String strPinCode) 
    
    /** This method returns the City Description
     * @return Returns the strCityDesc.
     */
    public String getCityDesc() {
        return strCityDesc;
    }//end of getCityDesc()
    
    /** This method sets the City Description
     * @param strCityDesc The strCityDesc to set.
     */
    public void setCityDesc(String strCityDesc) {
        this.strCityDesc = strCityDesc;
    }//end of setCityDesc(String strCityDesc)
    
    /** This method returns the Country Name
     * @return Returns the strCountryName.
     */
    public String getCountryName() {
        return strCountryName;
    }//end of getCountryName()
    
    /** This method sets the Country Name
     * @param strCountryName The strCountryName to set.
     */
    public void setCountryName(String strCountryName) {
        this.strCountryName = strCountryName;
    }//end of setCountryName(String strCountryName)
    
    /** This method returns the State Name
     * @return Returns the strStateName.
     */
    public String getStateName() {
        return strStateName;
    }//end of getStateName()
    
    /** This method sets the State Name
     * @param strStateName The strStateName to set.
     */
    public void setStateName(String strStateName) {
        this.strStateName = strStateName;
    }//end of setStateName(String strStateName)

	public String getOff1StdCode() {
		return off1StdCode;
	}

	public void setOff1StdCode(String off1StdCode) {
		this.off1StdCode = off1StdCode;
	}

	public String getOff1IsdCode() {
		return off1IsdCode;
	}

	public void setOff1IsdCode(String off1IsdCode) {
		this.off1IsdCode = off1IsdCode;
	}

	public String getOff2StdCode() {
		return off2StdCode;
	}

	public void setOff2StdCode(String off2StdCode) {
		this.off2StdCode = off2StdCode;
	}

	public String getHomeStdCode() {
		return homeStdCode;
	}

	public void setHomeStdCode(String homeStdCode) {
		this.homeStdCode = homeStdCode;
	}

	public String getOff2IsdCode() {
		return off2IsdCode;
	}

	public void setOff2IsdCode(String off2IsdCode) {
		this.off2IsdCode = off2IsdCode;
	}

	public String getHomeIsdCode() {
		return homeIsdCode;
	}

	public void setHomeIsdCode(String homeIsdCode) {
		this.homeIsdCode = homeIsdCode;
	}

	public String getMobileIsdCode() {
		return mobileIsdCode;
	}

	public void setMobileIsdCode(String mobileIsdCode) {
		this.mobileIsdCode = mobileIsdCode;
	}

	public String getDistrictCode() {
		return districtCode;
	}

	public void setDistrictCode(String districtCode) {
		this.districtCode = districtCode;
	}


	
}//end of AddressVO.java
