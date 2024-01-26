/**
 * @ (#) BalanceSIInfoVO.java Sep 14, 2009
 * Project 	     : TTK HealthCare Services
 * File          : BalanceSIInfoVO.java
 * Author        : RamaKrishna K M
 * Company       : Span Systems Corporation
 * Date Created  : Sep 14, 2009
 *
 * @author       :  RamaKrishna K M
 * Modified by   :  
 * Modified date :  
 * Reason        :  
 */

package com.ttk.dto.preauth;

import java.math.BigDecimal;

import com.ttk.dto.BaseVO;

/**This VO Class is used to display the SI Info in Preauth & Claims.
 * @author ramakrishna_km
 *
 */
public class BalanceSIInfoVO extends BaseVO{

	/**Serial Version ID.
	 * 
	 */
	private static final long serialVersionUID = 8210054342661257173L;
	
	private BigDecimal bdTotalSumInsured=null;
	private BigDecimal bdBonus = null;
	private BigDecimal bdBufferAmt = null;
	private BigDecimal bdUtilizedSumInsured=null;
	private BigDecimal bdUtilizedBonus = null;
	private String strPolSubType = "";
	private String strBufferAllocation = "";
	private BigDecimal bdUtilizedBufferAmt = null;
	private BigDecimal bdPolicyDeductableAmt=null; //added for Policy Deductable - KOC-1277
	private BigDecimal bdUtilizedPolicyDedAmt=null;//added for Policy Deductable - KOC-1277
	//added for hyundai buffer
	private BigDecimal bdNorCorpusBufAmt = null;
    private BigDecimal bdUtilizedNorCorpusBufAmt = null;
    private BigDecimal bdNorMedicalBufAmt = null;
    private BigDecimal bdUtilizedNorMedicalBufAmt = null;
	private BigDecimal bdCriCorpusBufAmt = null;
    private BigDecimal bdUtilizedCriCorpusBufAmt = null;
    private BigDecimal bdCriMedicalBufAmt = null;
    private BigDecimal bdUtilizedCriMedicalBufAmt = null;
    private BigDecimal bdCriIllnessBufAmt = null;
    private BigDecimal bdUtilizedCriIllnessBufAmt = null;


	
	
	/** Retrieve the TotalSumInsured.
	 * @return Returns the bdTotalSumInsured.
	 */
	public BigDecimal getTotalSumInsured() {
		return bdTotalSumInsured;
	}//end of getTotalSumInsured()
	
	/** Sets the TotalSumInsured.
	 * @param bdTotalSumInsured The bdTotalSumInsured to set.
	 */
	public void setTotalSumInsured(BigDecimal bdTotalSumInsured) {
		this.bdTotalSumInsured = bdTotalSumInsured;
	}//end of setTotalSumInsured(BigDecimal bdTotalSumInsured)
	
	/** Retrieve the Bonus.
	 * @return Returns the bdBonus.
	 */
	public BigDecimal getBonus() {
		return bdBonus;
	}//end of getBonus()
	
	/** Sets the Bonus.
	 * @param bdBonus The bdBonus to set.
	 */
	public void setBonus(BigDecimal bdBonus) {
		this.bdBonus = bdBonus;
	}//end of setBonus(BigDecimal bdBonus)
	
	/** Retrieve the BufferAmt.
	 * @return Returns the bdBufferAmt.
	 */
	public BigDecimal getBufferAmt() {
		return bdBufferAmt;
	}//end of getBufferAmt()
	
	/** Sets the BufferAmt.
	 * @param bdBufferAmt The bdBufferAmt to set.
	 */
	public void setBufferAmt(BigDecimal bdBufferAmt) {
		this.bdBufferAmt = bdBufferAmt;
	}//end of setBufferAmt(BigDecimal bdBufferAmt)
	
	/** Retrieve the UtilizedSumInsured.
	 * @return Returns the bdUtilizedSumInsured.
	 */
	public BigDecimal getUtilizedSumInsured() {
		return bdUtilizedSumInsured;
	}//end of getUtilizedSumInsured()
	
	/** Sets the UtilizedSumInsured.
	 * @param bdUtilizedSumInsured The bdUtilizedSumInsured to set.
	 */
	public void setUtilizedSumInsured(BigDecimal bdUtilizedSumInsured) {
		this.bdUtilizedSumInsured = bdUtilizedSumInsured;
	}//end of setUtilizedSumInsured(BigDecimal bdUtilizedSumInsured)
	
	/** Retrieve the UtilizedBonus.
	 * @return Returns the bdUtilizedBonus.
	 */
	public BigDecimal getUtilizedBonus() {
		return bdUtilizedBonus;
	}//end of getUtilizedBonus()
	
	/** Sets the UtilizedBonus.
	 * @param bdUtilizedBonus The bdUtilizedBonus to set.
	 */
	public void setUtilizedBonus(BigDecimal bdUtilizedBonus) {
		this.bdUtilizedBonus = bdUtilizedBonus;
	}//end of setUtilizedBonus(BigDecimal bdUtilizedBonus)
	
	/** Retrieve the PolSubType.
	 * @return Returns the strPolSubType.
	 */
	public String getPolSubType() {
		return strPolSubType;
	}//end of getPolSubType()
	
	/** Sets the PolSubType.
	 * @param strPolSubType The strPolSubType to set.
	 */
	public void setPolSubType(String strPolSubType) {
		this.strPolSubType = strPolSubType;
	}//end of setPolSubType(String strPolSubType)
	
	/** Retrieve the BufferAllocation.
	 * @return Returns the strBufferAllocation.
	 */
	public String getBufferAllocation() {
		return strBufferAllocation;
	}//end of getBufferAllocation()
	
	/** Sets the BufferAllocation.
	 * @param strBufferAllocation The strBufferAllocation to set.
	 */
	public void setBufferAllocation(String strBufferAllocation) {
		this.strBufferAllocation = strBufferAllocation;
	}//end of setBufferAllocation(String strBufferAllocation)
	
	/** Retrieve the UtilizedBufferAmt.
	 * @return Returns the bdUtilizedBufferAmt.
	 */
	public BigDecimal getUtilizedBufferAmt() {
		return bdUtilizedBufferAmt;
	}//end of getUtilizedBufferAmt()
	
	/** Sets the UtilizedBufferAmt.
	 * @param bdUtilizedBufferAmt The bdUtilizedBufferAmt to set.
	 */
	public void setUtilizedBufferAmt(BigDecimal bdUtilizedBufferAmt) {
		this.bdUtilizedBufferAmt = bdUtilizedBufferAmt;
	}//end of setUtilizedBufferAmt(BigDecimal bdUtilizedBufferAmt)
		public void setPolicyDeductableAmt(BigDecimal bdPolicyDeductableAmt) {
		this.bdPolicyDeductableAmt = bdPolicyDeductableAmt;
	}

	public BigDecimal getPolicyDeductableAmt() {
		return bdPolicyDeductableAmt;
	}

	public void setUtilizedPolicyDedAmt(BigDecimal bdUtilizedPolicyDedAmt) {
		this.bdUtilizedPolicyDedAmt = bdUtilizedPolicyDedAmt;
	}

	public BigDecimal getUtilizedPolicyDedAmt() {
		return bdUtilizedPolicyDedAmt;
	}
	//added for hyundai buffer
	public BigDecimal getNorCorpusBufAmt() {
		return bdNorCorpusBufAmt;
	}

	public void setNorCorpusBufAmt(BigDecimal bdNorCorpusBufAmt) {
		this.bdNorCorpusBufAmt = bdNorCorpusBufAmt;
	}

	public BigDecimal getUtilizedNorCorpusBufAmt() {
		return bdUtilizedNorCorpusBufAmt;
	}

	public void setUtilizedNorCorpusBufAmt(BigDecimal bdUtilizedNorCorpusBufAmt) {
		this.bdUtilizedNorCorpusBufAmt = bdUtilizedNorCorpusBufAmt;
	}
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
		


}//end of BalanceSIInfoVO
