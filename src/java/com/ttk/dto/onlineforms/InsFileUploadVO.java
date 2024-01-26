 /**
  * @ (#) InsFileUploadVO.java January 23, 2014
  * Project      : Vidal Health TPA
  * File         : InsFileUploadVO.java
  * Author       : Thirumalai K P
  * Company      : Span Systems Corporation
  * Date Created : January 23, 2014
  *
  * @author       :Thirumalai K P
  * Modified by   :
  * Modified date :
  * Reason        : File upload in insurance company login -- ins file upload
  */

package com.ttk.dto.onlineforms;

import com.ttk.dto.BaseVO;
import org.apache.struts.upload.FormFile;


public class InsFileUploadVO extends BaseVO {

	private FormFile file = null;
	private String strFileName = "";
	private String strInsCompCode = "";
	private String strLoginUserId = "";
	private String strActiveYN = "";
	private Long strUserSeqId = null;
	private String strUploadFileType = "";
	//as per Hospital Login
	private String strEmpanelCode = "";
	private String strSubFileType = "";
	private String strVidalId="";
	private String strPolicyNumber="";
	private String strRemarks="";
	private String strPolicyType="";
	public void setPolicyType(String policyType) {
		strPolicyType = policyType;
	}
	public String getPolicyType() {
		return strPolicyType;
	}
	public void setRemarks(String remarks) {
		strRemarks = remarks;
	}
	public String getRemarks() {
		return strRemarks;
	}
	public void setPolicyNumber(String policyNumber) {
		strPolicyNumber = policyNumber;
	}
	public String getPolicyNumber() {
		return strPolicyNumber;
	}
	public void setVidalId(String vidalId) {
		strVidalId = vidalId;
	}
	public String getVidalId() {
		return strVidalId;
	}
	public void setEmpanelCode(String empanelCode) {
		strEmpanelCode = empanelCode;
	}
	public String getEmpanelCode() {
		return strEmpanelCode;
	}
	/**
	 * @param strSubFileType the strSubFileType to set
	 */
	public void setSubFileType(String subFileType) {
		strSubFileType = subFileType;
	}
	/**
	 * @return the strSubFileType
	 */
	public String getSubFileType() {
		return strSubFileType;
	}
	//as per Hospital Login
	/**
	 * @return the strUploadFileType
	 */
	public String getUploadFileType() {
		return strUploadFileType;
	}
	/**
	 * @param strUploadFileType the strUploadFileType to set
	 */
	public void setUploadFileType(String strUploadFileType) {
		this.strUploadFileType = strUploadFileType;
	}
	/**
	 * @return the strUserSeqId
	 */
	public Long getUserSeqId() {
		return strUserSeqId;
	}
	/**
	 * @param strUserSeqId the strUserSeqId to set
	 */
	public void setUserSeqId(Long strUserSeqId) {
		this.strUserSeqId = strUserSeqId;
	}

	/**
	 * @return the strActiveYN
	 */
	public String getActiveYN() {
		return strActiveYN;
	}
	/**
	 * @param strActiveYN the strActiveYN to set
	 */
	public void setActiveYN(String strActiveYN) {
		this.strActiveYN = strActiveYN;
	}
	/**
	 * @return the strLoginUserId
	 */
	public String getLoginUserId() {
		return strLoginUserId;
	}
	/**
	 * @param strLoginUserId the strLoginUserId to set
	 */
	public void setLoginUserId(String strLoginUserId) {
		this.strLoginUserId = strLoginUserId;
	}
	/**
	 * @return the strInsCompCode
	 */
	public String getInsCompCode() {
		return strInsCompCode;
	}
	/**
	 * @param strInsCompCode the strInsCompCode to set
	 */
	public void setInsCompCode(String strInsCompCode) {
		this.strInsCompCode = strInsCompCode;
	}
	/**
	 * @param fileName the fileName to set
	 */
	public void setFileName(String fileName) {
		strFileName = fileName;
	}
	/**
	 * @return the fileName
	 */
	public String getFileName() {
		return strFileName;
	}

	/**
	 * @param file the file to set
	 */
	public void setFile(FormFile file) {
		this.file = file;
	}
	/**
	 * @return the file
	 */
	public FormFile getFile() {
		return file;
	}
	
}
