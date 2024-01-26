

package com.ttk.dto.insurancepricing;

import java.io.InputStream;
import java.math.BigDecimal;
import java.sql.Blob;
import java.util.ArrayList;
import java.util.Date;



import org.apache.struts.upload.FormFile;

import com.ttk.dto.BaseVO;

public class SwFinalQuoteVO  extends BaseVO {  

 private Long lngGroupProfileSeqID=null;

 private String companyName = "";
 private String officeCode = "";
 private Long productSeqID=null;


private Long enrollbatchSeqid=null;
 private Long policySeqid=null;
 private String batchNumber = "";
 private String policyNumber = "";
 private String trendfactorYN = "";
 private String trendFactorYNValue = "";
 private Long polcopyseqid=null;
 private String policydocYN = "";
 private String finalpolicydocYN = "";
 private String filedocimageYN = "";
 private String filedoctitle = "";
 private String groupId = "";
 private Long finalPolcopyseqid = null;
 private String finalQuotationNo = "";
 private String quoGeneratedDate = "";
 private String quotationNo = "";

 private byte[] finalQuotationdocs ;
 
 
 private FormFile sourceAttchments1; 
 private InputStream inputstreamdoc1 = null;
 private String attachmentname1="";

 
 public Long getFinalPolcopyseqid() {
	return finalPolcopyseqid;
}

public void setFinalPolcopyseqid(Long finalPolcopyseqid) {
	this.finalPolcopyseqid = finalPolcopyseqid;
}

public String getFinalQuotationNo() {
	return finalQuotationNo;
}

public void setFinalQuotationNo(String finalQuotationNo) {
	this.finalQuotationNo = finalQuotationNo;
}

 	public Long getLngGroupProfileSeqID() {
		return lngGroupProfileSeqID;
	}

	public void setLngGroupProfileSeqID(Long lngGroupProfileSeqID) {
		this.lngGroupProfileSeqID = lngGroupProfileSeqID;
	}

	

	public String getCompanyName() {
		return companyName;
	}

	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}

	public String getOfficeCode() {
		return officeCode;
	}

	public void setOfficeCode(String officeCode) {
		this.officeCode = officeCode;
	}

	public Long getProductSeqID() {
		return productSeqID;
	}

	public void setProductSeqID(Long productSeqID) {
		this.productSeqID = productSeqID;
	} 
 
	
	 public Long getEnrollbatchSeqid() {
			return enrollbatchSeqid;
		}

		public void setEnrollbatchSeqid(Long enrollbatchSeqid) {
			this.enrollbatchSeqid = enrollbatchSeqid;
		}

		public Long getPolicySeqid() {
			return policySeqid;
		}

		public void setPolicySeqid(Long policySeqid) {
			this.policySeqid = policySeqid;
		}

		public String getBatchNumber() {
			return batchNumber;
		}

		public void setBatchNumber(String batchNumber) {
			this.batchNumber = batchNumber;
		}

		public String getPolicyNumber() {
			return policyNumber;
		}

		public void setPolicyNumber(String policyNumber) {
			this.policyNumber = policyNumber;
		}

		public String getTrendfactorYN() {
			return trendfactorYN;
		}

		public void setTrendfactorYN(String trendfactorYN) {
			this.trendfactorYN = trendfactorYN;
		}

		public Long getPolcopyseqid() {
			return polcopyseqid;
		}

		public void setPolcopyseqid(Long polcopyseqid) {
			this.polcopyseqid = polcopyseqid;
		}

		public String getPolicydocYN() {
			return policydocYN;
		}

		public void setPolicydocYN(String policydocYN) {
			this.policydocYN = policydocYN;
		}

		public String getFinalpolicydocYN() {
			return finalpolicydocYN;
		}

		public void setFinalpolicydocYN(String finalpolicydocYN) {
			this.finalpolicydocYN = finalpolicydocYN;
		}

		public String getFiledocimageYN() {
			return filedocimageYN;
		}

		public void setFiledocimageYN(String filedocimageYN) {
			this.filedocimageYN = filedocimageYN;
		}

		public String getFiledoctitle() {
			return filedoctitle;
		}

		public void setFiledoctitle(String filedoctitle) {
			this.filedoctitle = filedoctitle;
		}

		public String getGroupId() {
			return groupId;
		}

		public void setGroupId(String groupId) {
			this.groupId = groupId;
		}

		public byte[] getFinalQuotationdocs() {
			return finalQuotationdocs;
		}

		public void setFinalQuotationdocs(byte[] finalQuotationdocs) {
			this.finalQuotationdocs = finalQuotationdocs;
		}

		public String getTrendFactorYNValue() {
			return trendFactorYNValue;
		}

		public void setTrendFactorYNValue(String trendFactorYNValue) {
			this.trendFactorYNValue = trendFactorYNValue;
		}

		public String getQuoGeneratedDate() {
			return quoGeneratedDate;
		}

		public void setQuoGeneratedDate(String quoGeneratedDate) {
			this.quoGeneratedDate = quoGeneratedDate;
		}

		public String getQuotationNo() {
			return quotationNo;
		}

		public void setQuotationNo(String quotationNo) {
			this.quotationNo = quotationNo;
		}

		public FormFile getSourceAttchments1() {
			return sourceAttchments1;
		}

		public void setSourceAttchments1(FormFile sourceAttchments1) {
			this.sourceAttchments1 = sourceAttchments1;
		}

		public InputStream getInputstreamdoc1() {
			return inputstreamdoc1;
		}

		public void setInputstreamdoc1(InputStream inputstreamdoc1) {
			this.inputstreamdoc1 = inputstreamdoc1;
		}

		public String getAttachmentname1() {
			return attachmentname1;
		}

		public void setAttachmentname1(String attachmentname1) {
			this.attachmentname1 = attachmentname1;
		}

		
	

}
	
	
	


