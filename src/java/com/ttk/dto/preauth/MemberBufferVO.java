/*
 * 
 * Created as per Change request 1216 B (IBM)
 * 
 * 
 * 
 * 
 */

package com.ttk.dto.preauth;

import java.math.BigDecimal;

import com.ttk.dto.BaseVO;

public class MemberBufferVO extends BaseVO {
	
	//Modification as per KOC 1216B Change request
	private BigDecimal bdUtilizedBufferAmtMember = null;//used_buff_amount
	private BigDecimal bdBufferAmtMember = null;//Mem_Buffer_Alloc
	private String strMemberBufferYN="N";	//member_buffer_yn
	//added for hyundai buffer
	
    private BigDecimal bdNorMedicalBufAmt = null;
    private BigDecimal bdUtilizedNorMedicalBufAmt = null;
	private BigDecimal bdCriCorpusBufAmt = null;
    private BigDecimal bdUtilizedCriCorpusBufAmt = null;
    private BigDecimal bdCriMedicalBufAmt = null;
    private BigDecimal bdUtilizedCriMedicalBufAmt = null;
    private BigDecimal bdCriIllnessBufAmt = null;
    private BigDecimal bdUtilizedCriIllnessBufAmt = null;

	
	/**
	 * @param memberBufferYN the memberBufferYN to set
	 */
	public void setMemberBufferYN(String memberBufferYN) {
		strMemberBufferYN = memberBufferYN;
	}

	/**
	 * @return the memberBufferYN
	 */
	public String getMemberBufferYN() {
		return strMemberBufferYN;
	}

	/**
	 * @param bufferAmtMember the bufferAmtMember to set
	 */
	public void setBufferAmtMember(BigDecimal bufferAmtMember) {
		bdBufferAmtMember = bufferAmtMember;
	}

	/**
	 * @return the bufferAmtMember
	 */
	public BigDecimal getBufferAmtMember() {
		return bdBufferAmtMember;
	}

	/**
	 * @param utilizedBufferAmtMember the utilizedBufferAmtMember to set
	 */
	public void setUtilizedBufferAmtMember(BigDecimal utilizedBufferAmtMember) {
		bdUtilizedBufferAmtMember = utilizedBufferAmtMember;
	}

	/**
	 * @return the utilizedBufferAmtMember
	 */
	public BigDecimal getUtilizedBufferAmtMember() {
		return bdUtilizedBufferAmtMember;
	}
	//Modification as per KOC 1216B Change request
	
	
//added for hyundai buffer

	public BigDecimal getNorMedicalBufAmt() {
		return bdNorMedicalBufAmt;
	}

	public void setNorMedicalBufAmt(BigDecimal bdNorMedicalBufAmt) {
		this.bdNorMedicalBufAmt = bdNorMedicalBufAmt;
	}

	public BigDecimal getUtilizedNorMedicalBufAmt() {
		return bdUtilizedNorMedicalBufAmt;
	}

	public void setUtilizedNorMedicalBufAmt(BigDecimal bdUtilizedNorMedicalBufAmt) {
		this.bdUtilizedNorMedicalBufAmt = bdUtilizedNorMedicalBufAmt;
	}

	public BigDecimal getCriCorpusBufAmt() {
		return bdCriCorpusBufAmt;
	}

	public void setCriCorpusBufAmt(BigDecimal bdCriCorpusBufAmt) {
		this.bdCriCorpusBufAmt = bdCriCorpusBufAmt;
	}

	public BigDecimal getUtilizedCriCorpusBufAmt() {
		return bdUtilizedCriCorpusBufAmt;
	}

	public void setUtilizedCriCorpusBufAmt(BigDecimal bdUtilizedCriCorpusBufAmt) {
		this.bdUtilizedCriCorpusBufAmt = bdUtilizedCriCorpusBufAmt;
	}

	public BigDecimal getCriMedicalBufAmt() {
		return bdCriMedicalBufAmt;
	}

	public void setCriMedicalBufAmt(BigDecimal bdCriMedicalBufAmt) {
		this.bdCriMedicalBufAmt = bdCriMedicalBufAmt;
	}

	public BigDecimal getUtilizedCriMedicalBufAmt() {
		return bdUtilizedCriMedicalBufAmt;
	}

	public void setUtilizedCriMedicalBufAmt(BigDecimal bdUtilizedCriMedicalBufAmt) {
		this.bdUtilizedCriMedicalBufAmt = bdUtilizedCriMedicalBufAmt;
	}

	public BigDecimal getCriIllnessBufAmt() {
		return bdCriIllnessBufAmt;
	}

	public void setCriIllnessBufAmt(BigDecimal bdCriIllnessBufAmt) {
		this.bdCriIllnessBufAmt = bdCriIllnessBufAmt;
	}

	public BigDecimal getUtilizedCriIllnessBufAmt() {
		return bdUtilizedCriIllnessBufAmt;
	}

	public void setUtilizedCriIllnessBufAmt(BigDecimal bdUtilizedCriIllnessBufAmt) {
		this.bdUtilizedCriIllnessBufAmt = bdUtilizedCriIllnessBufAmt;
	}
	

}
