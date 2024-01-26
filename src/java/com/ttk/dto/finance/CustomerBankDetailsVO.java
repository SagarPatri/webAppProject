package com.ttk.dto.finance;

import java.util.Date;
import org.apache.struts.upload.FormFile;
import com.ttk.dto.BaseVO;
/**
 * This class is added for cr koc 1103
 * added eft
 */
public class CustomerBankDetailsVO extends BaseVO {
	
	private String strPolicyNumeber;  //bankaccount SeqId.
    private String strEnrollNmbr;  //EnrollNmbr No.
    private Long insurenceSeqId;//insurence seq id
    private String policyType;//cheking corporate,indidual,indidual as group.non vorporate
    private String empanalDescription;//emnalment description
    private String countryId;//empanal country id
    private String hospAddresSeqID;
    private String bankState;//bank state
    private int pincode;//pincode
    private String state;//sate for bank
    private String strEnrType;//enroll type
    private Long officeSeqID;//OFFICE TYPE
   private Long lngPolicySeqID;
    private Long lngGroupSeqID;
    private String climentName;
    private String claimNo;
    private String strAddress1="";// Address1
    private String strAddress2="";// Address2
    private String strAddress3="";// Address3
    private String strPinCode="";// Pin Code
    private String clmSetmentno;//claim settelment no
    private String bankAccType;//bank account type
   // private String policyType;//chck wethet corporate or other
  //for logs
	private Date refDate;//reference date
	private String refNmbr;//reference number
	private String remarks;//remarks
	private String modReson;//modification reson
  //end logs
	private String strBankname;//bank name
    private String bankAccno;//account number
    private String lngPhoneno;//phone no
    private String strBankBranch;//branch name
    private String strBankAccName;//bank account name
    private String strMicr;//micr code
    private String strIfsc;//ifsc code
    private String strNeft;//neft code
    private String strBankcity;//Bnak city
    private String bankSeqId;//bank seq id
    private String checkIssuedTo;//check issued to 
    private String bankdistict;//bank distict
    private String hospitalEmnalNumber;//hospital empanal number
	private String hospitalName;//hospital name
	//private String hospitalbankSeqId;//hospital bank seq id
	private String accountnumber;//account number
	private String hospitalAccountINameOf;//account in name of
	private String hospitalEmpnalstatusTypeId;//empanlm status id
    private String hospitalEmpnalDesc;//empanl description
	private String strInsureName; //Account Name
    private String strofficename; //TTK Branch
    private String strStatusDesc;    //Status
    private String strAccountType; //Account Type
    private String strTransactionYN;//Transaction is there or not
  //for hospital
    private Long hospitalBankSeqId;//hospitalBankSeqId 
    private String countryCode;//county code
    private String issueChqToHosp;//issueChqToHosp
    private String hospitalSeqId;//hospitalSeqId
    private String emailID;//for fetching  email id
    
    private Date startDate;//Start date
    private Date endDate;//End datev
    //intX
    private String strReviewedYN;//Account Review 
    private String iBanNo		=	"";
    private String swiftCode	=	"";
    private String managementName	=	"";
    private Long lngHospSeqID;
    private String strDhaId;
    private	FormFile file=null;
	private String FileName	="";
	private String docName = "";
	private String docPath = "";
    private String policyCategory="";
    private Long lngPtnrSeqID;
    private String partnerEmnalNumber;
	private String partnerName;
    private String partnerEmpnalDesc;
    private Long partnerBankSeqId;//hospitalBankSeqId 
    private String ptnrAddresSeqID;

	public Long getPtnrSeqID() {
 		return lngPtnrSeqID;
 	}

 	public void setPtnrSeqID(Long lngPtnrSeqID) {
 		this.lngPtnrSeqID = lngPtnrSeqID;
 	}
 	
 	
	public String getPartnerEmnalNumber() {
	return partnerEmnalNumber;
}


public void setPartnerEmnalNumber(String partnerEmnalNumber) {
	this.partnerEmnalNumber = partnerEmnalNumber;
}

public String getPartnerName() {
	return partnerName;
}

public void setPartnerName(String partnerName) {
	this.partnerName = partnerName;
}


public String getPartnerEmpnalDesc() {
	return partnerEmpnalDesc;
}


public void setPartnerEmpnalDesc(String partnerEmpnalDesc) {
	this.partnerEmpnalDesc = partnerEmpnalDesc;
}

public Long getPartnerBankSeqId() {
	return partnerBankSeqId;
}


public void setPartnerBankSeqId(Long partnerBankSeqId) {
	this.partnerBankSeqId = partnerBankSeqId;
}


public String getPtnrAddresSeqID() {
	return ptnrAddresSeqID;
}


public void setPtnrAddresSeqID(String ptnrAddresSeqID) {
	this.ptnrAddresSeqID = ptnrAddresSeqID;
}




    
    public String getDocName() {
		return docName;
	}

	public void setDocName(String docName) {
		this.docName = docName;
	}

	public String getDocPath() {
		return docPath;
	}

	public void setDocPath(String docPath) {
		this.docPath = docPath;
	}


	public String getFileName() {
		return FileName;
	}

	public void setFileName(String fileName) {
		FileName = fileName;
	}


    public FormFile getFile() {
		return file;
	}

	public void setFile(FormFile file) {
		this.file = file;
	}

    public String getPolicyCategory() {
		return policyCategory;
	}

	public void setPolicyCategory(String policyCategory) {
		this.policyCategory = policyCategory;
	}

    public String getDhaId() {
		return strDhaId;
	}

	public void setDhaId(String strDhaId) {
		this.strDhaId = strDhaId;
	}

	public Long getHospSeqID() {
		return lngHospSeqID;
	}

	public void setHospSeqID(Long lngHospSeqID) {
		this.lngHospSeqID = lngHospSeqID;
	}

	public String getManagementName() {
		return managementName;
	}

	public void setManagementName(String managementName) {
		this.managementName = managementName;
	}

	public String getiBanNo() {
		return iBanNo;
	}

	public void setiBanNo(String iBanNo) {
		this.iBanNo = iBanNo;
	}

	public String getSwiftCode() {
		return swiftCode;
	}

	public void setSwiftCode(String swiftCode) {
		this.swiftCode = swiftCode;
	}

	public String getReviewedYN() {
		return strReviewedYN;
	}

	public void setReviewedYN(String strReviewedYN) {
		this.strReviewedYN = strReviewedYN;
	}
    
    /**Retrieve the startDate.
     * @return Returns the startDate.
     */	
	public Date getStartDate() {
		return startDate;
	}
	
	/**Sets the startDate.
     * @param startDate The startDate to set.
     */
	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}
	
	/**Retrieve the startDate.
     * @return Returns the startDate.
     */	
	public Date getEndDate() {
		return endDate;
	}
	
	/**Sets the startDate.
     * @param startDate The startDate to set.
     */
	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}
	
	
	/**Retrieve the PolicyType.
     * @return Returns the policyType.
     */
    public String getPolicyType() {
		return policyType;
	}

    /**Sets the PolicyType.
         * @param policyType The policyType to set.
         */
	public void setPolicyType(String policyType) {
		this.policyType = policyType;
	}

	/**Retrieve the CountryCode.
     * @return Returns the countryCode.
     */
    public String getCountryCode() {
		return countryCode;
	}
    
    /**Sets the CountryCode.
     * @param countryCode The countryCode to set.
     */
	public void setCountryCode(String countryCode) {
		this.countryCode = countryCode;
	}

	/**Retrieve the IssueChqToHosp.
     * @return Returns the issueChqToHosp.
     */
	public String getIssueChqToHosp() {
		return issueChqToHosp;
	}
	
	/**Sets the IssueChqToHosp.
     * @param issueChqToHosp The issueChqToHosp to set.
     */
	public void setIssueChqToHosp(String issueChqToHosp) {
		this.issueChqToHosp = issueChqToHosp;
	}

	/**Retrieve the HospitalBankSeqId.
     * @return Returns the hospitalBankSeqId.
     */
    public Long getHospitalBankSeqId() {
		return hospitalBankSeqId;
	}
   
    /**Sets the HospitalBankSeqId.
     * @param hospitalBankSeqId The hospitalBankSeqId to set.
     */
	public void setHospitalBankSeqId(Long hospitalBankSeqId) {
		this.hospitalBankSeqId = hospitalBankSeqId;
	}
	
	/**Retrieve the EmpanalDescription.
     * @return Returns the empanalDescription.
     */
	public String getEmpanalDescription() {
		return empanalDescription;
	}

	/**Sets the EmpanalDescription.
     * @param empanalDescription The empanalDescription to set.
     */
	public void setEmpanalDescription(String empanalDescription) {
		this.empanalDescription = empanalDescription;
	}

	/**Retrieve the CountryId.
     * @return Returns the countryId.
     */
	public String getCountryId() {
		return countryId;
	}
	
	/**Sets the CountryId.
     * @param countryId The countryId to set.
     */
	public void setCountryId(String countryId) {
		this.countryId = countryId;
	}
	
	/**Retrieve the Pincode.
     * @return Returns the pincode.
     */
	public int getPincode() {
		return pincode;
	}

	/**Sets the Pincode.
     * @param pincode The pincode to set.
     */
	public void setPincode(int pincode) {
		this.pincode = pincode;
	}
	/**Retrieve the State.
     * @return Returns the state.
     */
	public String getState() {
		return state;
	}
	/**Sets the State.
     * @param state The state to set.
     */
	public void setState(String state) {
		this.state = state;
	}

	/**Retrieve the HospAddresSeqID.
     * @return Returns the hospAddresSeqID.
     */
    public String getHospAddresSeqID() {
		return hospAddresSeqID;
	}

    /**Sets the HospAddresSeqID.
     * @param hospAddresSeqID The hospAddresSeqID to set.
     */
	public void setHospAddresSeqID(String hospAddresSeqID) {
		this.hospAddresSeqID = hospAddresSeqID;
	}

	/**Retrieve the InsurenceSeqId.
     * @return Returns the insurenceSeqId.
     */
    public Long getInsurenceSeqId() {
		return insurenceSeqId;
	}

    /**Sets the InsurenceSeqId.
     * @param insurenceSeqId The insurenceSeqId to set.
     */
	public void setInsurenceSeqId(Long insurenceSeqId) {
		this.insurenceSeqId = insurenceSeqId;
	}

	/**Retrieve the BankState.
     * @return Returns the bankState.
     */
	public String getBankState() {
		return bankState;
	}
	
	/**Sets the BankState.
     * @param bankState The bankState to set.
     */
	public void setBankState(String bankState) {
		this.bankState = bankState;
	}

	/**Retrieve the ClaimNo.
     * @return Returns the claimNo.
     */
	
    public String getClaimNo() {
		return claimNo;
	}
    
    /**Sets the ClaimNo.
     * @param claimNo The claimNo to set.
     */
    public void setClaimNo(String claimNo) {
		this.claimNo = claimNo;
	}
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
    public String getAddress1() {
        return strAddress1;
    }//end of getAddress2()
    
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
    }//end of setAddress3(String strAddress3

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
    
	
    /**Retrieve the Status ClimentName.
     * @return Returns the climentName.
     */
	public String getClimentName() {
		return climentName;
	}
	
	/**Sets the ClimentName.
     * @param climentName The climentName to set.
     */
	public void setClimentName(String climentName) {
		this.climentName = climentName;
	}
	
	/**Retrieve the Status ClmSetmentno.
     * @return Returns the clmSetmentno.
     */
	public String getClmSetmentno() {
		return clmSetmentno;
	}
	/**Sets the ClmSetmentno.
     * @param clmSetmentno The clmSetmentno to set.
     */
	public void setClmSetmentno(String clmSetmentno) {
		this.clmSetmentno = clmSetmentno;
	}


	
	/**Retrieve the Status GroupSeqID.
     * @return Returns the lngGroupSeqID.
     */
    public Long getGroupSeqID() {
		return lngGroupSeqID;
	}

    /**Sets the GroupSeqID.
     * @param lngGroupSeqID The lngGroupSeqID to set.
     */
	public void setGroupSeqID(Long lngGroupSeqID) {
		this.lngGroupSeqID = lngGroupSeqID;
	}

	/**Retrieve the Status OfficeSeqID.
     * @return Returns the officeSeqID.
     */
	
    public Long getOfficeSeqID() {
		return officeSeqID;
	}
    
    /**Sets the OfficeSeqID.
     * @param officeSeqID The officeSeqID to set.
     */
	public void setOfficeSeqID(Long officeSeqID) {
		this.officeSeqID = officeSeqID;
	}

	/**Retrieve the Status BankAccType.
     * @return Returns the bankAccType.
     */
	
	public String getBankAccType() {
		return bankAccType;
	}

	/**Sets the BankAccType.
     * @param bankAccType The bankAccType to set.
     */
	public void setBankAccType(String bankAccType) {
		this.bankAccType = bankAccType;
	}


	/**Retrieve the Status EnrType.
     * @return Returns the strEnrType.
     */
	
    public String getEnrType() {
		return strEnrType;
	}
  
    /**Sets the EnrType.
     * @param strEnrType The strEnrType to set.
     */
	public void setEnrType(String strEnrType) {
		this.strEnrType = strEnrType;
	}

	/**Retrieve the Status Bankname.
     * @return Returns the strBankname.
     */
	public String getBankname() {
		return strBankname;
	}

	/**Sets the Bankname.
     * @param strBankname The strBankname to set.
     */
	public void setBankname(String strBankname) {
		this.strBankname = strBankname;
	}

	/**Retrieve the Status BankAccno.
     * @return Returns the lngBankAccno.
     */
	public String getBankAccno() {
		return bankAccno;
	}

	/**Sets the BankAccno.
     * @param strBankname The strBankname to set.
     */
	public void setBankAccno(String bankAccno) {
		this.bankAccno = bankAccno;
	}
	/**Retrieve the BankPhoneno.
     * @return Returns the lngPhoneno.
     */
	public String getBankPhoneno() {
		return lngPhoneno;
	}
	/**Sets the BankPhoneno.
     * @param lngPhoneno The lngPhoneno to set.
     */
	public void setBankPhoneno(String string) {
		this.lngPhoneno = string;
	}
	/**Retrieve the BankBranch.
     * @return Returns the strBankBranch.
     */
	public String getBankBranch() {
		return strBankBranch;
	}
	/**Sets the BankBranch.
     * @param strBankBranch The strBankBranch to set.
     */
	public void setBankBranch(String strBankBranch) {
		this.strBankBranch = strBankBranch;
	}
	/**Retrieve the BankAccName.
     * @return Returns the strBankAccName.
     */
	public String getBankAccName() {
		return strBankAccName;
	}
	/**Sets the BankAccName.
     * @param strBankAccName The strBankAccName to set.
     */
	public void setBankAccName(String strBankAccName) {
		this.strBankAccName = strBankAccName;
	}
	
	/**Retrieve the Micr.
     * @return Returns the strMicr.
     */
	public String getMicr() {
		return strMicr;
	}
	/**Sets the Micr.
     * @param strMicr The strMicr to set.
     */
	public void setMicr(String strMicr) {
		this.strMicr = strMicr;
	}
	/**Retrieve the Ifsc.
     * @return Returns the strIfsc.
     */
	public String getIfsc() {
		return strIfsc;
	}
	/**Sets the Ifsc.
     * @param strIfsc The strIfsc to set.
     */
	public void setIfsc(String strIfsc) {
		this.strIfsc = strIfsc;
	}
	/**Retrieve the Neft.
     * @return Returns the strNeft.
     */
	public String getNeft() {
		return strNeft;
	}
	/**Sets the Neft.
     * @param strNeft The strNeft to set.
     */
	public void setNeft(String strNeft) {
		this.strNeft = strNeft;
	}

	/**Retrieve the ModReson.
     * @return Returns the modReson.
     */
	public String getModReson() {
		return modReson;
	}
	/**Sets the ModReson.
     * @param modReson The modReson to set.
     */
	public void setModReson(String modReson) {
		this.modReson = modReson;
	}
	/**Retrieve the RefDate.
     * @return Returns the refDate.
     */	
	public Date getRefDate() {
		return refDate;
	}
	
	/**Sets the RefDate.
     * @param refDate The refDate to set.
     */
	public void setRefDate(Date refDate) {
		this.refDate = refDate;
	}
	
	/**Retrieve the RefNmbr.
     * @return Returns the refNmbr.
     */	
	public String getRefNmbr() {
		return refNmbr;
	}
	/**Sets the RefNmbr.
     * @param refNmbr The refNmbr to set.
     */
	public void setRefNmbr(String refNmbr) {
		this.refNmbr = refNmbr;
	}
	/**Retrieve the Remarks.
     * @return Returns the remarks.
     */	
	public String getRemarks() {
		return remarks;
	}

	/**Sets the Remarks.
     * @param remarks The remarks to set.
     */
	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}


	
   
	/**Retrieve the Bankdistict.
     * @return Returns the bankdistict.
     */	
	
    public String getBankdistict() {
		return bankdistict;
	}
    /**Sets the Bankdistict.
     * @param bankdistict The bankdistict to set.
     */
	public void setBankdistict(String bankdistict) {
		this.bankdistict = bankdistict;
	}
	/**Retrieve the CheckIssuedTo.
     * @return Returns the checkIssuedTo.
     */
	public String getCheckIssuedTo() {
		return checkIssuedTo;
	}
	/**Sets the CheckIssuedTo.
     * @param checkIssuedTo The checkIssuedTo to set.
     */
	public void setCheckIssuedTo(String checkIssuedTo) {
		this.checkIssuedTo = checkIssuedTo;
	}
	/**Retrieve the BankSeqId.
     * @return Returns the bankSeqId.
     */
	public String getBankSeqId() {
		return bankSeqId;
	}
	/**Sets the BankSeqId.
     * @param bankSeqId The bankSeqId to set.
     */
	public void setBankSeqId(String bankSeqId) {
		this.bankSeqId = bankSeqId;
	}

	/**Retrieve the Bankcity.
     * @return Returns the strBankcity.
     */
	public String getBankcity() {
		return strBankcity;
	}
	/**Sets the Bankcity.
     * @param strBankcity The strBankcity to set.
     */
	public void setBankcity(String strBankcity) {
		this.strBankcity = strBankcity;
	}
	
	/**Retrieve the PolicySeqID.
     * @return Returns the lngPolicySeqID.
     */
	public Long getPolicySeqID() {
		return lngPolicySeqID;
	}
	/**Sets the PolicySeqID.
     * @param lngPolicySeqID The lngPolicySeqID to set.
     */
	public void setPolicySeqID(Long lngPolicySeqID) {
		this.lngPolicySeqID = lngPolicySeqID;
	}
	/**Retrieve the PolicyGroupSeqID.
     * @return Returns the lngGroupSeqID.
     */
	public Long getPolicyGroupSeqID() {
		return lngGroupSeqID;
	}
	/**Sets the PolicyGroupSeqID.
     * @param lngGroupSeqID The lngGroupSeqID to set.
     */
	public void setPolicyGroupSeqID(Long lngGroupSeqID) {
		this.lngGroupSeqID = lngGroupSeqID;
	}

	
	
	/**Retrieve the HospitalSeqId.
     * @return Returns the hospitalSeqId.
     */
	public String getHospitalSeqId() {
		return hospitalSeqId;
	}

	/**Sets the HospitalSeqId.
     * @param hospitalSeqId The hospitalSeqId to set.
     */
	public void setHospitalSeqId(String hospitalSeqId) {
		this.hospitalSeqId = hospitalSeqId;
	}

	/**Retrieve the HospitalEmnalNumber.
     * @return Returns the hospitalEmnalNumber.
     */
	public String getHospitalEmnalNumber() {
		return hospitalEmnalNumber;
	}

	/**Sets the HospitalEmnalNumber.
     * @param hospitalEmnalNumber The hospitalEmnalNumber to set.
     */
	public void setHospitalEmnalNumber(String hospitalEmnalNumber) {
		this.hospitalEmnalNumber = hospitalEmnalNumber;
	}

	/**Retrieve the HospitalName.
     * @return Returns the hospitalName.
     */
	public String getHospitalName() {
		return hospitalName;
	}

	/**Sets the HospitalName.
     * @param hospitalName The hospitalName to set.
     */
	public void setHospitalName(String hospitalName) {
		this.hospitalName = hospitalName;
	}

	/*public String getHospitalbankSeqId() {
		return hospitalbankSeqId;
	}

	public void setHospitalbankSeqId(String hospitalbankSeqId) {
		this.hospitalbankSeqId = hospitalbankSeqId;
	}*/

	/**Retrieve the Accountnumber.
     * @return Returns the accountnumber.
     */
	public String getAccountnumber() {
		return accountnumber;
	}
	/**Sets the accountnumber.
     * @param accountnumber The accountnumber to set.
     */
	public void setAccountnumber(String accountnumber) {
		this.accountnumber = accountnumber;
	}

	/**Retrieve the HospitalAccountINameOf.
     * @return Returns the hospitalAccountINameOf.
     */
	public String getHospitalAccountINameOf() {
		return hospitalAccountINameOf;
	}

	/**Sets the HospitalAccountINameOf.
     * @param hospitalAccountINameOf The hospitalAccountINameOf to set.
     */
	public void setHospitalAccountINameOf(String hospitalAccountINameOf) {
		this.hospitalAccountINameOf = hospitalAccountINameOf;
	}


	/**Retrieve the HospitalEmpnalstatusTypeId.
     * @return Returns the hospitalEmpnalstatusTypeId.
     */
	public String getHospitalEmpnalstatusTypeId() {
		return hospitalEmpnalstatusTypeId;
	}
	
	/**Sets the HospitalEmpnalstatusTypeId.
     * @param hospitalEmpnalstatusTypeId The hospitalEmpnalstatusTypeId to set.
     */
	
	public void setHospitalEmpnalstatusTypeId(String hospitalEmpnalstatusTypeId) {
		this.hospitalEmpnalstatusTypeId = hospitalEmpnalstatusTypeId;
	}

	/**Retrieve the HospitalEmpnalDesc.
     * @return Returns the hospitalEmpnalDesc.
     */
	public String getHospitalEmpnalDesc() {
		return hospitalEmpnalDesc;
	}

	 /**Sets the HospitalEmpnalDesc.
     * @param hospitalEmpnalDesc The hospitalEmpnalDesc to set.
     */
	public void setHospitalEmpnalDesc(String hospitalEmpnalDesc) {
		this.hospitalEmpnalDesc = hospitalEmpnalDesc;
	}


	
	/**Retrieve the EnrollNmbr.
     * @return Returns the strEnrollNmbr.
     */
    public String getEnrollNmbr() {
		return strEnrollNmbr;
	}

    /**Sets the EnrollNmbr.
     * @param strEnrollNmbr The strEnrollNmbr to set.
     */
	public void setEnrollNmbr(String strEnrollNmbr) {
		this.strEnrollNmbr = strEnrollNmbr;
	}

	/**Retrieve the InsureName.
     * @return Returns the strInsureName.
     */
	public String getInsureName() {
		return strInsureName;
	}

	/**Sets the InsureName.
     * @param strInsureName The strInsureName to set.
     */
	public void setInsureName(String strInsureName) {
		this.strInsureName = strInsureName;
	}

	/**Retrieve the Officename.
     * @return Returns the strofficename.
     */
	public String getOfficename() {
		return strofficename;
	}

	/**Sets the Officename.
     * @param strofficename The strofficename to set.
     */
	public void setOfficename(String strofficename) {
		this.strofficename = strofficename;
	}
   
	/**Retrieve the PolicyNumber.
     * @return Returns the strPolicyNumeber.
     */ 
	
    public String getPolicyNumber() {
        return strPolicyNumeber;
    }//end of getTransactionYN()

    /**Sets the PolicyNumber is there or not.
     * @param strPolicyNumeber The strPolicyNumeber to set.
     */
    public void setPolicyNumber(String strPolicyNumeber) {
        this.strPolicyNumeber = strPolicyNumeber;
    }//end of setTransactionYN(String strTransactionYN)
    
    /**Retrieve the Transaction is there or not.
     * @return Returns the strTransactionYN.
     */
    public String getTransactionYN() {
        return strTransactionYN;
    }//end of getTransactionYN()

    /**Sets the Transaction is there or not.
     * @param strTransactionYN The strTransactionYN to set.
     */
    public void setTransactionYN(String strTransactionYN) {
        this.strTransactionYN = strTransactionYN;
    }//end of setTransactionYN(String strTransactionYN)

    /**Retrieve the Account SeqID.
     * @return Returns the lAccountSeqID.
     */
   /* public Long getAccountSeqID() {
        return lngAccountSeqID;
    }//end of getAccountSeqID()

    *//**Sets the Account SeqID.
     * @param lngAccountSeqID The lngAccountSeqID to set.
     *//*
    public void setAccountSeqID(Long lngAccountSeqID) {
        this.lngAccountSeqID = lngAccountSeqID;
    }//end of setAccountSeqID(Long lngAccountSeqID)
*/
    /**Retrieve the Account Name.
     * @return Returns the strAccountName.
     */
  /*  public String getAccountName() {
        return strAccountName;
    }//end of getAccountName()

    *//**Sets the Account Name.
     * @param strAccountName The strAccountName to set.
     *//*
    public void setAccountName(String strAccountName) {
        this.strAccountName = strAccountName;
    }//end of setAccountName(String strAccountName)

    *//**Retrieve the Account NO.
     * @return Returns the strAccountNO.
     *//*
    public String getAccountNO() {
        return strAccountNO;
    }//end of getAccountNO()

    *//**Sets the Account NO.
     * @param strAccountNO The strAccountNO to set.
     *//*
    public void setAccountNO(String strAccountNO) {
        this.strAccountNO = strAccountNO;
    }//end of setAccountNO(String strAccountNO)
*/
    /**Retrieve the Account Type.
     * @return Returns the strAccountType.
     */
    public String getAccountType() {
        return strAccountType;
    }//end of getAccountType()

    /**Sets the AccountType.
     * @param strAccountType The strAccountType to set.
     */
    public void setAccountType(String strAccountType) {
        this.strAccountType = strAccountType;
    }//end of setAccountType(String strAccountType)

    /**Retrieve the Status Description.
     * @return Returns the strStatusDesc.
     */
    public String getStatusDesc() {
        return strStatusDesc;
    }//end of getStatusDesc()

    /**Sets the Status Description.
     * @param strStatusDesc The strStatusDesc to set.
     */
    public void setStatusDesc(String strStatusDesc) {
        this.strStatusDesc = strStatusDesc;
    }//end of setStatusDesc(String strStatusDesc)

	/**
	 * @param emailID the emailID to set
	 */
	public void setEmailID(String emailID) {
		this.emailID = emailID;
	}

	/**
	 * @return the emailID
	 */
	public String getEmailID() {
		return emailID;
	}

    /**Retrieve the TtkBranch.
     * @return Returns the strTtkBranch.
     */
   /* public String getTtkBranch() {
        return strTtkBranch;
    }//end of getTtkBranch()

    *//**Sets the Ttk Branch.
     * @param strTtkBranch The strTtkBranch to set.
     *//*
    public void setTtkBranch(String strTtkBranch) {
        this.strTtkBranch = strTtkBranch;*/
}
