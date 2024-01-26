

package com.ttk.dto.insurancepricing;

import java.math.BigDecimal;
import java.sql.Blob;
import java.util.ArrayList;
import java.util.Date;

import com.ttk.dto.BaseVO;

public class SwPricingSummaryVO  extends BaseVO {  

 private Long lngGroupProfileSeqID=null;
 private Long cpmSeqID=null;
 private Long finaldataSeqID=null;
 private Date policyEffDate=null;
 private String strpolicyEffDate = "";
 private Date policyExpDate=null;
 private String strpolicyExpDate=null;
 private String clientCode="";
 private String effectiveYear="";
 private Long policyDurationPerMonth = null;
 private Long noOfLives = null;
 private String inPatientCPM = "";
 private String outPatientCPM = "";
 private String maternityCPM = "";
 private String opticalCPM = "";
 private String dentalCPM = "";
 private String allExlMaternity = "";
 private String finalweightage = "";
 private String calCPM_FlagYN = "";
 


private String inpatientcrediblty = "";
 private String outpatientcrediblty = "";
 private String maternitycrediblty = "";
 private String opticalcrediblty = "";
 private String dentalcrediblty = "";
 
 private String fininpatientcrediblty = "";
 private String finoutpatientcrediblty = "";
 private String finmaternitycrediblty = "";
 private String finopticalcrediblty = "";
 private String findentalcrediblty = "";

 
 
//private ArrayList<Object> alPastData = null;

 private String load_DeductType = "";
 private Long load_DeductTypeSeqId = null;
 private Long grp_load_SeqId = null;
 private String load_DeductTypePercentage = "";
 //private ArrayList<Object> alLoadingGrosspremium = null;

 private String slNo = "";
 private String dataType = "";
 private String policyNo = "";
 private Long trend = null;
 private String loadComments = "";
 
 private Long fincpmSeqID=null;
 private Date finpolicyEffDate=null;
 private Date finpolicyExpDate=null;
 private String finclientCode="";
 private String fineffectiveYear="";
 private Long finpolicyDurationPerMonth = null;
 private Long finnoOfLives = null;
 private String fininPatientCPM = "";
 private String finoutPatientCPM = "";
 private String finmaternityCPM = "";
 private String finopticalCPM = "";
 private String findentalCPM = "";
 private String finallExlMaternity = "";
 private String finfinalweightage = "";
 
 private String findataType = "";
 private String finpolicyNo = "";
 
 // govind Gross Premium
 
 private String riskSeqId = "";
 private String pricSeqId = "";
 private String generalTypeId = "";
 private String riscDescription = "";
 private float riskLoad;
 private float grossRiskLoad;


private String detail = "";
 private String riscDescriptionType = "";
 private String benefit = "";
 private String riskPremAED = "";
 private String comments = "";
 private String showMsg = "";
 


private ArrayList cursor1 = null;
 private ArrayList cursor2 = null;
 private ArrayList cursor3 = null;
 private ArrayList cursor4 = null;
 
 
 public String getShowMsg() {
	return showMsg;
}

public void setShowMsg(String showMsg) {
	this.showMsg = showMsg;
}
public float getGrossRiskLoad() {
	return grossRiskLoad;
}

public void setGrossRiskLoad(float grossRiskLoad) {
	this.grossRiskLoad = grossRiskLoad;
}


 
public ArrayList getCursor1() {
	return cursor1;
}

public void setCursor1(ArrayList cursor1) {
	this.cursor1 = cursor1;
}

public ArrayList getCursor2() {
	return cursor2;
}

public void setCursor2(ArrayList cursor2) {
	this.cursor2 = cursor2;
}

public ArrayList getCursor3() {
	return cursor3;
}

public void setCursor3(ArrayList cursor3) {
	this.cursor3 = cursor3;
}

public ArrayList getCursor4() {
	return cursor4;
}

public void setCursor4(ArrayList cursor4) {
	this.cursor4 = cursor4;
}

public String getComments() {
	return comments;
}

public void setComments(String comments) {
	this.comments = comments;
}

public String getRiskSeqId() {
	return riskSeqId;
}

public void setRiskSeqId(String riskSeqId) {
	this.riskSeqId = riskSeqId;
}

public String getPricSeqId() {
	return pricSeqId;
}

public void setPricSeqId(String pricSeqId) {
	this.pricSeqId = pricSeqId;
}

public String getGeneralTypeId() {
	return generalTypeId;
}

public void setGeneralTypeId(String generalTypeId) {
	this.generalTypeId = generalTypeId;
}

public String getRiscDescription() {
	return riscDescription;
}

public void setRiscDescription(String riscDescription) {
	this.riscDescription = riscDescription;
}


public float getRiskLoad() {
	return riskLoad;
}

public void setRiskLoad(float riskLoad) {
	this.riskLoad = riskLoad;
}


public String getDetail() {
	return detail;
}

public void setDetail(String detail) {
	this.detail = detail;
}

public String getRiscDescriptionType() {
	return riscDescriptionType;
}

public void setRiscDescriptionType(String riscDescriptionType) {
	this.riscDescriptionType = riscDescriptionType;
}

public String getBenefit() {
	return benefit;
}

public void setBenefit(String benefit) {
	this.benefit = benefit;
}

public String getRiskPremAED() {
	return riskPremAED;
}

public void setRiskPremAED(String riskPremAED) {
	this.riskPremAED = riskPremAED;
}

public String getFindataType() {
	return findataType;
}

public void setFindataType(String findataType) {
	this.findataType = findataType;
}

public String getFinpolicyNo() {
	return finpolicyNo;
}

public void setFinpolicyNo(String finpolicyNo) {
	this.finpolicyNo = finpolicyNo;
}


 
 public Long getFincpmSeqID() {
	return fincpmSeqID;
}

public void setFincpmSeqID(Long fincpmSeqID) {
	this.fincpmSeqID = fincpmSeqID;
}

public Date getFinpolicyEffDate() {
	return finpolicyEffDate;
}

public void setFinpolicyEffDate(Date finpolicyEffDate) {
	this.finpolicyEffDate = finpolicyEffDate;
}



public Date getFinpolicyExpDate() {
	return finpolicyExpDate;
}

public void setFinpolicyExpDate(Date finpolicyExpDate) {
	this.finpolicyExpDate = finpolicyExpDate;
}

public String getFinclientCode() {
	return finclientCode;
}

public void setFinclientCode(String finclientCode) {
	this.finclientCode = finclientCode;
}

public String getFineffectiveYear() {
	return fineffectiveYear;
}

public void setFineffectiveYear(String fineffectiveYear) {
	this.fineffectiveYear = fineffectiveYear;
}

public Long getFinpolicyDurationPerMonth() {
	return finpolicyDurationPerMonth;
}

public void setFinpolicyDurationPerMonth(Long finpolicyDurationPerMonth) {
	this.finpolicyDurationPerMonth = finpolicyDurationPerMonth;
}

public Long getFinnoOfLives() {
	return finnoOfLives;
}

public void setFinnoOfLives(Long finnoOfLives) {
	this.finnoOfLives = finnoOfLives;
}

public String getFininPatientCPM() {
	return fininPatientCPM;
}

public void setFininPatientCPM(String fininPatientCPM) {
	this.fininPatientCPM = fininPatientCPM;
}

public String getFinoutPatientCPM() {
	return finoutPatientCPM;
}

public void setFinoutPatientCPM(String finoutPatientCPM) {
	this.finoutPatientCPM = finoutPatientCPM;
}

public String getFinmaternityCPM() {
	return finmaternityCPM;
}

public void setFinmaternityCPM(String finmaternityCPM) {
	this.finmaternityCPM = finmaternityCPM;
}

public String getFinopticalCPM() {
	return finopticalCPM;
}

public void setFinopticalCPM(String finopticalCPM) {
	this.finopticalCPM = finopticalCPM;
}

public String getFindentalCPM() {
	return findentalCPM;
}

public void setFindentalCPM(String findentalCPM) {
	this.findentalCPM = findentalCPM;
}

public String getFinallExlMaternity() {
	return finallExlMaternity;
}

public void setFinallExlMaternity(String finallExlMaternity) {
	this.finallExlMaternity = finallExlMaternity;
}

public String getFinfinalweightage() {
	return finfinalweightage;
}

public void setFinfinalweightage(String finfinalweightage) {
	this.finfinalweightage = finfinalweightage;
}


 
 

 public Long getLngGroupProfileSeqID() {
		return lngGroupProfileSeqID;
	}

	public void setLngGroupProfileSeqID(Long lngGroupProfileSeqID) {
		this.lngGroupProfileSeqID = lngGroupProfileSeqID;
	} 
 
	
	public Long getCpmSeqID() {
		return cpmSeqID;
	}

	public void setCpmSeqID(Long cpmSeqID) {
		this.cpmSeqID = cpmSeqID;
	}


	 public Date getPolicyEffDate() {
		return policyEffDate;
	}

	public void setPolicyEffDate(Date policyEffDate) {
		this.policyEffDate = policyEffDate;
	}

	public Date getPolicyExpDate() {
		return policyExpDate;
	}

	public void setPolicyExpDate(Date policyExpDate) {
		this.policyExpDate = policyExpDate;
	}

	public String getClientCode() {
		return clientCode;
	}

	public void setClientCode(String clientCode) {
		this.clientCode = clientCode;
	}

	public String getEffectiveYear() {
		return effectiveYear;
	}

	public void setEffectiveYear(String effectiveYear) {
		this.effectiveYear = effectiveYear;
	}

	public Long getPolicyDurationPerMonth() {
		return policyDurationPerMonth;
	}

	public void setPolicyDurationPerMonth(Long policyDurationPerMonth) {
		this.policyDurationPerMonth = policyDurationPerMonth;
	}

	public Long getNoOfLives() {
		return noOfLives;
	}

	public void setNoOfLives(Long noOfLives) {
		this.noOfLives = noOfLives;
	}

	
	
/*
	public BigDecimal getFinalweightage() {
		return finalweightage;
	}

	public void setFinalweightage(BigDecimal finalweightage) {
		this.finalweightage = finalweightage;
	}*/

	public String getSlNo() {
		return slNo;
	}

	public void setSlNo(String slNo) {
		this.slNo = slNo;
	}

	public String getDataType() {
		return dataType;
	}

	public void setDataType(String dataType) {
		this.dataType = dataType;
	}

	public String getPolicyNo() {
		return policyNo;
	}

	public void setPolicyNo(String policyNo) {
		this.policyNo = policyNo;
	}

	public Long getTrend() {
		return trend;
	}

	public void setTrend(Long trend) {
		this.trend = trend;
	}

	/*public ArrayList<Object> getAlPastData() {
		return alPastData;
	}

	public void setAlPastData(ArrayList<Object> alPastData) {
		this.alPastData = alPastData;
	}*/

	
	
	 public String getLoad_DeductType() {
			return load_DeductType;
		}

		public void setLoad_DeductType(String load_DeductType) {
			this.load_DeductType = load_DeductType;
		}

		public Long getLoad_DeductTypeSeqId() {
			return load_DeductTypeSeqId;
		}

		public void setLoad_DeductTypeSeqId(Long load_DeductTypeSeqId) {
			this.load_DeductTypeSeqId = load_DeductTypeSeqId;
		}

		


		public Long getGrp_load_SeqId() {
			return grp_load_SeqId;
		}

		public void setGrp_load_SeqId(Long grp_load_SeqId) {
			this.grp_load_SeqId = grp_load_SeqId;
		}

	

		public String getFinalweightage() {
			return finalweightage;
		}

		public void setFinalweightage(String finalweightage) {
			this.finalweightage = finalweightage;
		}
		
		public String getInPatientCPM() {
			return inPatientCPM;
		}

		public void setInPatientCPM(String inPatientCPM) {
			this.inPatientCPM = inPatientCPM;
		}

		public String getOutPatientCPM() {
			return outPatientCPM;
		}

		public void setOutPatientCPM(String outPatientCPM) {
			this.outPatientCPM = outPatientCPM;
		}

		public String getMaternityCPM() {
			return maternityCPM;
		}

		public void setMaternityCPM(String maternityCPM) {
			this.maternityCPM = maternityCPM;
		}

		public String getOpticalCPM() {
			return opticalCPM;
		}

		public void setOpticalCPM(String opticalCPM) {
			this.opticalCPM = opticalCPM;
		}

		public String getDentalCPM() {
			return dentalCPM;
		}

		public void setDentalCPM(String dentalCPM) {
			this.dentalCPM = dentalCPM;
		}

		public String getAllExlMaternity() {
			return allExlMaternity;
		}

		public void setAllExlMaternity(String allExlMaternity) {
			this.allExlMaternity = allExlMaternity;
		}

		public String getLoad_DeductTypePercentage() {
			return load_DeductTypePercentage;
		}

		public void setLoad_DeductTypePercentage(String load_DeductTypePercentage) {
			this.load_DeductTypePercentage = load_DeductTypePercentage;
		}

		public String getLoadComments() {
			return loadComments;
		}

		public void setLoadComments(String loadComments) {
			this.loadComments = loadComments;
		}

		public String getStrpolicyEffDate() {
			return strpolicyEffDate;
		}

		public void setStrpolicyEffDate(String strpolicyEffDate) {
			this.strpolicyEffDate = strpolicyEffDate;
		}

		public String getStrpolicyExpDate() {
			return strpolicyExpDate;
		}

		public void setStrpolicyExpDate(String strpolicyExpDate) {
			this.strpolicyExpDate = strpolicyExpDate;
		}

		public Long getFinaldataSeqID() {
			return finaldataSeqID;
		}

		public void setFinaldataSeqID(Long finaldataSeqID) {
			this.finaldataSeqID = finaldataSeqID;
		}

		public String getCalCPM_FlagYN() {
			return calCPM_FlagYN;
		}

		public void setCalCPM_FlagYN(String calCPM_FlagYN) {
			this.calCPM_FlagYN = calCPM_FlagYN;
		}

		
		 public String getInpatientcrediblty() {
				return inpatientcrediblty;
			}

			public void setInpatientcrediblty(String inpatientcrediblty) {
				this.inpatientcrediblty = inpatientcrediblty;
			}

			public String getOutpatientcrediblty() {
				return outpatientcrediblty;
			}

			public void setOutpatientcrediblty(String outpatientcrediblty) {
				this.outpatientcrediblty = outpatientcrediblty;
			}

			public String getMaternitycrediblty() {
				return maternitycrediblty;
			}

			public void setMaternitycrediblty(String maternitycrediblty) {
				this.maternitycrediblty = maternitycrediblty;
			}

			public String getOpticalcrediblty() {
				return opticalcrediblty;
			}

			public void setOpticalcrediblty(String opticalcrediblty) {
				this.opticalcrediblty = opticalcrediblty;
			}

			public String getDentalcrediblty() {
				return dentalcrediblty;
			}

			public void setDentalcrediblty(String dentalcrediblty) {
				this.dentalcrediblty = dentalcrediblty;
			}

			public String getFininpatientcrediblty() {
				return fininpatientcrediblty;
			}

			public void setFininpatientcrediblty(String fininpatientcrediblty) {
				this.fininpatientcrediblty = fininpatientcrediblty;
			}

			public String getFinoutpatientcrediblty() {
				return finoutpatientcrediblty;
			}

			public void setFinoutpatientcrediblty(String finoutpatientcrediblty) {
				this.finoutpatientcrediblty = finoutpatientcrediblty;
			}

			public String getFinmaternitycrediblty() {
				return finmaternitycrediblty;
			}

			public void setFinmaternitycrediblty(String finmaternitycrediblty) {
				this.finmaternitycrediblty = finmaternitycrediblty;
			}

			public String getFinopticalcrediblty() {
				return finopticalcrediblty;
			}

			public void setFinopticalcrediblty(String finopticalcrediblty) {
				this.finopticalcrediblty = finopticalcrediblty;
			}

			public String getFindentalcrediblty() {
				return findentalcrediblty;
			}

			public void setFindentalcrediblty(String findentalcrediblty) {
				this.findentalcrediblty = findentalcrediblty;
			}

			/*public ArrayList<Object> getAlLoadingData() {
				return alLoadingData;
			}

			public void setAlLoadingData(ArrayList<Object> alLoadingData) {
				this.alLoadingData = alLoadingData;
			}*/

			/*public ArrayList<Object> getAlLoadingGrosspremium() {
				return alLoadingGrosspremium;
			}

			public void setAlLoadingGrosspremium(
					ArrayList<Object> alLoadingGrosspremium) {
				this.alLoadingGrosspremium = alLoadingGrosspremium;
			}*/

		

}
	
	
	


