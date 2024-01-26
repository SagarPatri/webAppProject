package com.ttk.dto.finance;

import com.ttk.dto.BaseVO;

public class TDSHospitalInfoVO extends BaseVO{
	
	/**
	 * default serial versionID
	 */
	private static final long serialVersionUID = 1L;
	private String strFinancialYear = null;
	private String strHospitalName = null;
	private String strEmplNumber="";
	private String strTtkBranch="";
	/**
	 * @return the strFinancialYear
	 */
	public String getFinancialYear() {
		return strFinancialYear;
	}
	/**
	 * @param strFinancialYear the strFinancialYear to set
	 */
	public void setFinancialYear(String strFinancialYear) {
		this.strFinancialYear = strFinancialYear;
	}
	/**
	 * @return the strHospitalName
	 */
	public String getHospitalName() {
		return strHospitalName;
	}
	/**
	 * @param strHospitalName the strHospitalName to set
	 */
	public void setHospitalName(String strHospitalName) {
		this.strHospitalName = strHospitalName;
	}
	/**
	 * @return the strEmplNumber
	 */
	public String getEmplNumber() {
		return strEmplNumber;
	}
	/**
	 * @param strEmplNumber the strEmplNumber to set
	 */
	public void setEmplNumber(String strEmplNumber) {
		this.strEmplNumber = strEmplNumber;
	}
	/**
	 * @return the strTtkBranch
	 */
	public String getTtkBranch() {
		return strTtkBranch;
	}
	/**
	 * @param strTtkBranch the strTtkBranch to set
	 */
	public void setTtkBranch(String strTtkBranch) {
		this.strTtkBranch = strTtkBranch;
	}

}
