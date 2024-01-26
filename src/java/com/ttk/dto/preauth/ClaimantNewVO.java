/** 
 *  @ (#)ClaimantVO.java June 27, 2008
 *  Project       : TTK HealthCare Services
 *  File          : ClaimantNewVO.java
 *  Author        : Balakrishna Erram
 *  Company       : Span Systems Corporation
 *  Date Created  : June 27, 2008
 * 
 *  @author       :  Balakrishna Erram
 *  Modified by   :  
 *  Modified date :  
 *  Reason        :  
 *   
 */

package com.ttk.dto.preauth;

import java.util.Date;

import com.ttk.common.TTKCommon;
import com.ttk.dto.BaseVO;

public class ClaimantNewVO extends BaseVO{
    
    private String strGroupName = "";
    private String strGenderDescription = "";
    private String strInsScheme =""; //ins_scheme
    private Long lngGroupRegnSeqID = null;
    private Long lngMemberSeqID = null;
    private String strName = "";
	private String strEnrollmentID = "";
	private Integer intAge=null;
	private String strPolicyNbr = "";
	private String strSchemeCertNbr ="";
	private Date dtStartDate = null;
	private Date dtEndDate = null;
	
	
		private String sDate = null;
		private String eDate = null;
		private String sEmiratesID = "";
		private String dob = null;
		private String companyName = "";
		private String memberActive = "";
		private String corpGroupId = "";
		private String cardNo = "";
		private String networkType = "";
		private String dhpoMemberId;
		
		
		
		
		
		
		
		
	
	public String getMemberActive() {
			return memberActive;
		}

		public void setMemberActive(String memberActive) {
			this.memberActive = memberActive;
		}

		public String getCorpGroupId() {
			return corpGroupId;
		}

		public void setCorpGroupId(String corpGroupId) {
			this.corpGroupId = corpGroupId;
		}

		public String getCardNo() {
			return cardNo;
		}

		public void setCardNo(String cardNo) {
			this.cardNo = cardNo;
		}

		public String getNetworkType() {
			return networkType;
		}

		public void setNetworkType(String networkType) {
			this.networkType = networkType;
		}

	/** This method returns the Start Date
	 * @return Returns the dtStartDate.
	 */
	public Date getStartDate() {
		return dtStartDate;
	}//end of getStartDate()

	/** This method returns the Start Date
	 * @return Returns the dtStartDate.
	 */
	public String getListStartDate() {
		return TTKCommon.getFormattedDate(dtStartDate);
	}//end of getStartDate()

	/** This method sets the Start Date
	 * @param dtStartDate The Start Date to set.
	 */
	public void setStartDate(Date dtStartDate) {
		this.dtStartDate = dtStartDate;
	}//end of setStartDate(Date dtStartDate)

	/** This method returns the End Date
	 * @return Returns the dtEndDate.
	 */
	public Date getEndDate() {
		return dtEndDate;
	}//end of getEndDate()

	/** This method returns the End Date
	 * @return Returns the dtEndDate.
	 */
	public String getListEndDate() {
		return TTKCommon.getFormattedDate(dtEndDate);
	}//end of getEndDate()

	/** This method sets the End Date
	 * @param dtEndDate The End Date to set.
	 */
	public void setEndDate(Date dtEndDate) {
		this.dtEndDate = dtEndDate;
	}//end of setEndDate(Date dtEndDate)
	
    /** Retrieve the Corporate Name
    * @return Returns the strGroupName.
    */
   public String getGroupName() {
       return strGroupName;
   }//end of getGroupName()
   
   /** Sets the Corporate Name
    * @param strGroupName The strGroupName to set.
    */
   public void setGroupName(String strGroupName) {
       this.strGroupName = strGroupName;
   }//end of setGroupName(String strGroupName)
   
   /** Retrieve the Gender Description
    * @return Returns the strGenderDescription.
    */
   public String getGenderDescription() {
       return strGenderDescription;
   }//end of getGenderDesc()
   
   /** Sets the Gender Description
    * @param strGenderDescription The strGenderDescription to set.
    */
   public void setGenderDescription(String strGenderDescription) {
       this.strGenderDescription = strGenderDescription;
   }//end of setGenderDesc(String strGenderDescription)
   
   /** Retrive the InsScheme
	 * @return Returns the strInsScheme.
	 */
	public String getInsScheme() {
		return strInsScheme;
	}//end of getInsScheme()

	/** Sets the InsScheme
	 * @param strInsScheme The strInsScheme to set.
	 */
	public void setInsScheme(String strInsScheme) {
		this.strInsScheme = strInsScheme;
	}//end of setInsScheme(String strInsScheme)
	
	/** Retrieve the Group Registration Seq ID
	 * @return Returns the lngGroupRegSeqID.
	 */
	public Long getGroupRegnSeqID() {
		return lngGroupRegnSeqID;
	}//end of getGroupRegSeqID()

	/** Sets the Group Registration Seq ID
	 * @param lngGroupRegnSeqID The lngGroupRegnSeqID to set.
	 */
	public void setGroupRegnSeqID(Long lngGroupRegnSeqID) {
		this.lngGroupRegnSeqID = lngGroupRegnSeqID;
	}//end of setGroupRegSeqID(Long lngGroupRegnSeqID)
	
	/** This method returns the Member Sequence ID
	 * @return Returns the lngMemberSeqID.
	 */
	public Long getMemberSeqID() {
		return lngMemberSeqID;
	}//end of getMemberSeqID()

	/** This method sets the Member Sequence ID
	 * @param lngMemberSeqID The Member Sequence ID to set.
	 */
	public void setMemberSeqID(Long lngMemberSeqID) {
		this.lngMemberSeqID = lngMemberSeqID;
	}//end of setMemberSeqID(Long lngMemberSeqID)
	
	/** This method returns the Member Name
	 * @return Returns the strName.
	 */
	public String getName() {
		return strName;
	}//end of getName()

	/** This method sets the Member Name
	 * @param strName The Member Name to set.
	 */
	public void setName(String strName) {
		this.strName = strName;
	}//end of setName(String strName)

	/** This method returns the Enrollment ID
	 * @return Returns the strEnrollmentID.
	 */
	public String getEnrollmentID() {
		return strEnrollmentID;
	}//end of getEnrollmentID()

	/** This method sets the Enrollment ID
	 * @param strEnrollmentID The Enrollment ID to set.
	 */
	public void setEnrollmentID(String strEnrollmentID) {
		this.strEnrollmentID = strEnrollmentID;
	}//end of setEnrollmentID(String strEnrollmentID)

	/** This method returns the Age
	 * @return Returns the intAge.
	 */
	public String getFormattedAge() {
		return intAge==null ? "":intAge.toString();
	}//end of getAge()

	/** This method returns the Age
	 * @return Returns the intAge.
	 */
	public Integer getAge() {
		return intAge;
	}//end of getAge()
	
	/** This method sets the Age
	 * @param intAge The Age to set.
	 */
	public void setAge(Integer intAge) {
		this.intAge = intAge;
	}//end of setAge(Integer intAge)
	
	/** This method sets the Policy Number
     * @param strPolicyNbr The strPolicyNbr to set.
     */
    public void setPolicyNbr(String strPolicyNbr) {
        this.strPolicyNbr = strPolicyNbr;
    }// End of setPolicyNbr(String strPolicyNbr)
    /** This method returns the Policy Number
     * @return Returns the strPolicyNbr.
     */
    public String getPolicyNbr() {
        return strPolicyNbr;
    }// End of getPolicyNbr()
    
    /** Retrieve the Scheme or Certificate Number
	 * @return Returns the strSchemeCertNbr.
	 */
	public String getSchemeCertNbr() {
		return strSchemeCertNbr;
	}//end of getSchemeCertNbr()

	/** Sets the Scheme or Certificate Number
	 * @param strSchemeCertNbr The strSchemeCertNbr to set.
	 */
	public void setSchemeCertNbr(String strSchemeCertNbr) {
		this.strSchemeCertNbr = strSchemeCertNbr;
	}//end of setSchemeCertNbr(String strSchemeCertNbr)

	public String getsDate() {
		return sDate;
	}

	public void setsDate(String sDate) {
		this.sDate = sDate;
	}

	public String geteDate() {
		return eDate;
	}

	public void seteDate(String eDate) {
		this.eDate = eDate;
	}

	public String getsEmiratesID() {
		return sEmiratesID;
	}

	public void setsEmiratesID(String sEmiratesID) {
		this.sEmiratesID = sEmiratesID;
	}

	public String getDob() {
		return dob;
	}

	public void setDob(String dob) {
		this.dob = dob;
	}

	public String getCompanyName() {
		return companyName;
	}

	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}

	public String getDhpoMemberId() {
		return dhpoMemberId;
	}

	public void setDhpoMemberId(String dhpoMemberId) {
		this.dhpoMemberId = dhpoMemberId;
	}
    
}//end of ClaimantVO.java