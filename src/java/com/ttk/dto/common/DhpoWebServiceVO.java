package com.ttk.dto.common;

import java.io.Reader;

import com.ttk.dto.BaseVO;

public class DhpoWebServiceVO extends BaseVO{	
	
	/**
	 *
	 * 
	 */
	
	private static final long serialVersionUID = 3456863988176537498L;
	private String userID;
	private String password;
	private byte[] fileContent;
	private String xmlFileContent;
	private String fileName;
	private Integer transactionResult;
	private String errorMessage;
	private String fileType;
	private byte[] errorReport;
	private String fileID;
	private String downloadStatus;
	private String bifurcationYN;
	private Reader xmlFileReader;
	private String claimFrom;
	private Long fileSeqID;
	private String xmlRecievedData;
	private String userDownloadStatus;
	private String userDownloadDate;
	
	private String dhpoTxDate;
	private String dhpoClaimRecCount;
	private String claimRecCount;
	
	private String preAuthSeqID;
	private String preAuthUploadStatus;
	private String dhpoLicenceNO;
	
	private String providerID;
	private String claimsettlementnumber;
	private long claimSeqId;
	private String flag_YN; 
	private String senderID;
	private String enrollementId;
	
	private String errorText;
	
	public void setSenderID(String senderID) {
		this.senderID = senderID;
	}
	
	public String getSenderID() {
		return senderID;
	}

	public String getFlag_YN() {
		return flag_YN;
	}
	public void setFlag_YN(String flag_YN) {
		this.flag_YN = flag_YN;
	}
	public String getClaimsettlementnumber() {
		return claimsettlementnumber;
	}
	public void setClaimsettlementnumber(String claimsettlementnumber) {
		this.claimsettlementnumber = claimsettlementnumber;
	}
	public long getClaimSeqId() {
		return claimSeqId;
	}
	public void setClaimSeqId(long claimSeqId) {
		this.claimSeqId = claimSeqId;
	}
	
	public void setDhpoLicenceNO(String dhpoLicenceNO) {
		this.dhpoLicenceNO = dhpoLicenceNO;
	}
	public String getDhpoLicenceNO() {
		return dhpoLicenceNO;
	}
	public void setPreAuthUploadStatus(String preAuthUploadStatus) {
		this.preAuthUploadStatus = preAuthUploadStatus;
	}
	public String getPreAuthUploadStatus() {
		return preAuthUploadStatus;
	}
	
	public void setPreAuthSeqID(String preAuthSeqID) {
		this.preAuthSeqID = preAuthSeqID;
	}
	public String getPreAuthSeqID() {
		return preAuthSeqID;
	}
	
	public void setDhpoTxDate(String dhpoTxDate) {
		this.dhpoTxDate = dhpoTxDate;
	}
	public String getDhpoTxDate() {
		return dhpoTxDate;
	}
	public String getDhpoClaimRecCount() {
		return dhpoClaimRecCount;
	}
	public void setDhpoClaimRecCount(String dhpoClaimRecCount) {
		this.dhpoClaimRecCount = dhpoClaimRecCount;
	}
	
	public String getXmlRecievedData() {
		return xmlRecievedData;
	}
	public void setXmlRecievedData(String xmlRecievedData) {
		this.xmlRecievedData = xmlRecievedData;
	}
	public String getUserDownloadStatus() {
		return userDownloadStatus;
	}
	public void setUserDownloadStatus(String userDownloadStatus) {
		this.userDownloadStatus = userDownloadStatus;
	}
	public String getUserDownloadDate() {
		return userDownloadDate;
	}
	public void setUserDownloadDate(String userDownloadDate) {
		this.userDownloadDate = userDownloadDate;
	}
	public void setFileSeqID(Long fileSeqID) {
		this.fileSeqID = fileSeqID;
	}
	public Long getFileSeqID() {
		return fileSeqID;
	}
	public void setClaimFrom(String claimFrom) {
		this.claimFrom = claimFrom;
	}
	public String getClaimFrom() {
		return claimFrom;
	}
	
	public void setXmlFileReader(Reader xmlFileReader) {
		this.xmlFileReader = xmlFileReader;
	}
	public Reader getXmlFileReader() {
		return xmlFileReader;
	}
	public void setBifurcationYN(String bifurcationYN) {
		this.bifurcationYN = bifurcationYN;
	}
	public String getBifurcationYN() {
		return bifurcationYN;
	}
	public String getUserID() {
		return userID;
	}
	public void setUserID(String userID) {
		this.userID = userID;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public byte[] getFileContent() {
		return fileContent;
	}
	public void setFileContent(byte[] fileContent) {
		this.fileContent = fileContent;
	}
	public String getFileName() {
		return fileName;
	}
	public void setFileName(String fileName) {
		this.fileName = fileName;
	}
	public Integer getTransactionResult() {
		return transactionResult;
	}
	public void setTransactionResult(Integer transactionResult) {
		this.transactionResult = transactionResult;
	}
	public String getErrorMessage() {
		return errorMessage;
	}
	public void setErrorMessage(String errorMessage) {
		this.errorMessage = errorMessage;
	}
	public byte[] getErrorReport() {
		return errorReport;
	}
	public void setErrorReport(byte[] errorReport) {
		this.errorReport = errorReport;
	}
	public String getFileType() {
		return fileType;
	}
	public void setFileType(String fileType) {
		this.fileType = fileType;
	}
	public String getFileID() {
		return fileID;
	}
	public void setFileID(String fileID) {
		this.fileID = fileID;
	}
	public String getDownloadStatus() {
		return downloadStatus;
	}
	public void setDownloadStatus(String downloadStatus) {
		this.downloadStatus = downloadStatus;
	}
	public String getXmlFileContent() {
		return xmlFileContent;
	}
	public void setXmlFileContent(String xmlFileContent) {
		this.xmlFileContent = xmlFileContent;
	}
	public String getProviderID() {
		return providerID;
	}
	public void setProviderID(String providerID) {
		this.providerID = providerID;
	}
	public String getClaimRecCount() {
		return claimRecCount;
	}
	public void setClaimRecCount(String claimRecCount) {
		this.claimRecCount = claimRecCount;
	}

	public String getEnrollementId() {
		return enrollementId;
	}

	public void setEnrollementId(String enrollementId) {
		this.enrollementId = enrollementId;
	}

	public String getErrorText() {
		return errorText;
	}

	public void setErrorText(String errorText) {
		this.errorText = errorText;
	}
	
	
	
	
	
}
