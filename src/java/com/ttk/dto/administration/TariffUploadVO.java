package com.ttk.dto.administration;

/**
 * @ (#) TariffUploadVO.java Sep 30, 2005
 * Project       : Vidal Health
 * File          : TariffUploadVO.java
 * Author        : Kishor kumar S H
 * Company       : RCS Technologies
 * Date Created  : 09/04/2015
 *
 * @author       : Kishor kumar S H
 * Modified by   : 
 * Modified date : 
 * Reason        : 
 */


import java.util.Date;

import com.ttk.dto.BaseVO;

/**
 * This VO contains all details of any tariff item
 * The corresponding DB Table is TPA_HOSP_TARIFF_ITEM.
 * 
 */

public class TariffUploadVO extends BaseVO{
	
	
public String getNetworkType1() {
		return strNetworkType1;
	}
	public void setNetworkType1(String strNetworkType1) {
		this.strNetworkType1 = strNetworkType1;
	}
public String getDiscAt() {
		return strDiscAt;
	}
	public void setDiscAt(String strDiscAt) {
		this.strDiscAt = strDiscAt;
	}
public String getGrpNetworkType() {
		return strGrpNetworkType;
	}
	public void setGrpNetworkType(String strGrpNetworkType) {
		this.strGrpNetworkType = strGrpNetworkType;
	}
public String getGrpProviderCode() {
		return strGrpProviderCode;
	}
	public void setGrpProviderCode(String strGrpProviderCode) {
		this.strGrpProviderCode = strGrpProviderCode;
	}
public String getIndOrGrp() {
		return strIndOrGrp;
	}
	public void setIndOrGrp(String strIndOrGrp) {
		this.strIndOrGrp = strIndOrGrp;
	}
	public String getGrpProviderName() {
		return strGrpProviderName;
	}
	public void setGrpProviderName(String strGrpProviderName) {
		this.strGrpProviderName = strGrpProviderName;
	}
public String getDiscPercentage() {
		return strDiscPercentage;
	}
	public void setDiscPercentage(String strDiscPercentage) {
		this.strDiscPercentage = strDiscPercentage;
	}
public String getNewFileName() {
		return strNewFileName;
	}
	public void setNewFileName(String strNewFileName) {
		this.strNewFileName = strNewFileName;
	}
public String getActivityDesc() {
		return strActivityDesc;
	}
	public void setActivityDesc(String strActivityDesc) {
		this.strActivityDesc = strActivityDesc;
	}
public Long getPriceRefSeqId() {
		return lngPriceRefSeqId;
	}
	public void setPriceRefSeqId(Long lngPriceRefSeqId) {
		this.lngPriceRefSeqId = lngPriceRefSeqId;
	}
public String getRemarks() {
		return strRemarks;
	}
	public void setRemarks(String strRemarks) {
		this.strRemarks = strRemarks;
	}
public String getInternalCode() {
		return strInternalCode;
	}
	public void setInternalCode(String strInternalCode) {
		this.strInternalCode = strInternalCode;
	}
public Date getStartDate() {
		return dtStartDate;
	}
	public void setStartDate(Date dtStartDate) {
		this.dtStartDate = dtStartDate;
	}
	public Date getEndDate() {
		return dtEndDate;
	}
	public void setEndDate(Date dtEndDate) {
		this.dtEndDate = dtEndDate;
	}
public String getGroupId() {
		return StrGroupId;
	}
	public void setGroupId(String StrGroupId) {
		this.StrGroupId = StrGroupId;
	}
	public Long getGrpRegSeqId() {
		return lngGrpRegSeqId;
	}
	public void setGrpRegSeqId(Long lngGrpRegSeqId) {
		this.lngGrpRegSeqId = lngGrpRegSeqId;
	}
	public String getGrpName() {
		return strGrpName;
	}
	public void setGrpName(String strGrpName) {
		this.strGrpName = strGrpName;
	}
//	String fields declared private	
private String strPayerCode			=	null;
private String strProviderCode		=	null;
private String strNetworkType		=	null;
private String strCorpCode			=	null;
private String strTariffType		=	null;
private String strPayerCodeSearch	=	null;
private String strProviderCodeSearch=	null;
private String strNetworkTypeSearch	=	null;
private String strCorpCodeSearch	=	null;
private String strTariffTypeSearch	=	null;
private String strNetworkType1		=	null;
private String strNetworkTypeYN     =   null;

private String strPriceRefNo		=	null;
private Long   lngPriceRefSeqId		=	null;
private String strActivityCode		=	null;
private String strGrossAmount		=	null;
private String strDiscAmount		=	null;
private String strDiscPercentage	=	null;
private Long   lngTariffSeqId		=	null;

private Long   lngHospSeqId			=	null;
private Long   lngInsSeqId			=	null;
private String strHospName			=	null;
private String strInsCompName		=	null;
private Long   lngActivitySeqId		=	null;
private Long   lngActivityTypeSeqId	=	null;
private Long   lngServiceSeqId		=	null;
private String strServiceName		=	null;
private String strPackageId			=	null;
private String strBundleId			=	null;

private String   StrGroupId			=	null;
private Long   lngGrpRegSeqId		=	null;
private String strGrpName			=	null;
private Date dtStartDate			=	null;
private Date dtEndDate				=	null;
private String strInternalCode		=	null;
private String strRemarks			=	null;
private String strActivityDesc		=	null;
private String strNewFileName		=	null;
private String strIndOrGrp			=	null;
private String strGrpProviderName	=	null;
private String strGrpProviderCode	=	null;
private String strGrpNetworkType	=	null;
private String  strDiscAt			=	null;
private String strend_date			=	null;
private String enddate              =   null;
private String hospLicenceNo        =   null;

public String getNetworkTypeYN() {
	return strNetworkTypeYN;
}
public void setNetworkTypeYN(String strNetworkTypeYN) {
	strNetworkTypeYN = strNetworkTypeYN;
}
 
public String getHospName() {
	return strHospName;
}
public void setHospName(String strHospName) {
	this.strHospName = strHospName;
}
public String getInsCompName() {
	return strInsCompName;
}
public void setInsCompName(String strInsCompName) {
	this.strInsCompName = strInsCompName;
}
public String getServiceName() {
	return strServiceName;
}
public void setServiceName(String strServiceName) {
	this.strServiceName = strServiceName;
}
public Long getHospSeqId() {
	return lngHospSeqId;
}
public void setHospSeqId(Long lngHospSeqId) {
	this.lngHospSeqId = lngHospSeqId;
}
public Long getInsSeqId() {
	return lngInsSeqId;
}
public void setInsSeqId(Long lngInsSeqId) {
	this.lngInsSeqId = lngInsSeqId;
}
public Long getActivitySeqId() {
	return lngActivitySeqId;
}
public void setActivitySeqId(Long lngActivitySeqId) {
	this.lngActivitySeqId = lngActivitySeqId;
}
public Long getActivityTypeSeqId() {
	return lngActivityTypeSeqId;
}
public void setActivityTypeSeqId(Long lngActivityTypeSeqId) {
	this.lngActivityTypeSeqId = lngActivityTypeSeqId;
}
public Long getServiceSeqId() {
	return lngServiceSeqId;
}
public void setServiceSeqId(Long lngServiceSeqId) {
	this.lngServiceSeqId = lngServiceSeqId;
}
public String getPackageId() {
	return strPackageId;
}
public void setPackageId(String strPackageId) {
	this.strPackageId = strPackageId;
}
public String getBundleId() {
	return strBundleId;
}
public void setBundleId(String strBundleId) {
	this.strBundleId = strBundleId;
}
public Long getTariffSeqId() {
	return lngTariffSeqId;
}
public void setTariffSeqId(Long lngTariffSeqId) {
	this.lngTariffSeqId = lngTariffSeqId;
}
public String getActivityCode() {
	return strActivityCode;
}
public void setActivityCode(String strActivityCode) {
	this.strActivityCode = strActivityCode;
}
public String getGrossAmount() {
	return strGrossAmount;
}
public void setGrossAmount(String strGrossAmount) {
	this.strGrossAmount = strGrossAmount;
}
public String getDiscAmount() {
	return strDiscAmount;
}
public void setDiscAmount(String strDiscAmount) {
	this.strDiscAmount = strDiscAmount;
}


		public String getPriceRefNo() {
			return strPriceRefNo;
		}
		public void setPriceRefNo(String strPriceRefNo) {
			this.strPriceRefNo = strPriceRefNo;
		}

		public String getPayerCode() {
			return strPayerCode;
		}
		public void setPayerCode(String strPayerCode) {
			this.strPayerCode = strPayerCode;
		}
		public String getProviderCode() {
			return strProviderCode;
		}
		public void setProviderCode(String strProviderCode) {
			this.strProviderCode = strProviderCode;
		}
		public String getNetworkType() {
			return strNetworkType;
		}
		public void setNetworkType(String strNetworkType) {
			this.strNetworkType = strNetworkType;
		}
		public String getCorpCode() {
			return strCorpCode;
		}
		public void setCorpCode(String strCorpCode) {
			this.strCorpCode = strCorpCode;
		}
		public String getTariffType() {
			return strTariffType;
		}
		public void setTariffType(String strTariffType) {
			this.strTariffType = strTariffType;
		}
		public String getPayerCodeSearch() {
			return strPayerCodeSearch;
		}
		public void setPayerCodeSearch(String strPayerCodeSearch) {
			this.strPayerCodeSearch = strPayerCodeSearch;
		}
		public String getProviderCodeSearch() {
			return strProviderCodeSearch;
		}
		public void setProviderCodeSearch(String strProviderCodeSearch) {
			this.strProviderCodeSearch = strProviderCodeSearch;
		}
		public String getNetworkTypeSearch() {
			return strNetworkTypeSearch;
		}
		public void setNetworkTypeSearch(String strNetworkTypeSearch) {
			this.strNetworkTypeSearch = strNetworkTypeSearch;
		}
		public String getCorpCodeSearch() {
			return strCorpCodeSearch;
		}
		public void setCorpCodeSearch(String strCorpCodeSearch) {
			this.strCorpCodeSearch = strCorpCodeSearch;
		}
		public String getTariffTypeSearch() {
			return strTariffTypeSearch;
		}
		public void setTariffTypeSearch(String strTariffTypeSearch) {
			this.strTariffTypeSearch = strTariffTypeSearch;
		}
		public String getStrend_date() {
			return strend_date;
		}
		public void setStrend_date(String strend_date) {
			this.strend_date = strend_date;
		}
		public String getEnddate() {
			return enddate;
		}
		public void setEnddate(String enddate) {
			this.enddate = enddate;
		}
		public String getHospLicenceNo() {
			return hospLicenceNo;
		}
		public void setHospLicenceNo(String hospLicenceNo) {
			this.hospLicenceNo = hospLicenceNo;
		}
		
}// End of TariffUploadVO
