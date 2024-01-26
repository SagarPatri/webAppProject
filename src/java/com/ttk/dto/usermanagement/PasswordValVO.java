package com.ttk.dto.usermanagement;

import com.ttk.dto.BaseVO;

public class PasswordValVO extends BaseVO{
	private String lockDays = "";
    private String passwordValidityDays = "";
	private String wrongAttempts = "";
	private String pwdAlert = "";
	private String configSeqIDd = "";
//Changes as 11PP KOC 1257
	private String identifier ="";

	/**
	 * @param identifier the identifier to set
	 */
	public void setIdentifier(String identifier) {
		this.identifier = identifier;
	}

	/**
	 * @return the identifier
	 */
	public String getIdentifier() {
		return identifier;
	}

	//Changes as 11PP KOC 1257	
	/**
	 * @param configSeqIDd the configSeqIDd to set
	 */
	public void setConfigSeqIDd(String configSeqIDd) {
		this.configSeqIDd = configSeqIDd;
	}

	/**
	 * @return the configSeqIDd
	 */
	public String getConfigSeqIDd() {
		return configSeqIDd;
	}

	/**
	 * @param pwdAlert the pwdAlert to set
	 */
	public void setPwdAlert(String pwdAlert) {
		this.pwdAlert = pwdAlert;
	}

	/**
	 * @return the pwdAlert
	 */
	public String getPwdAlert() {
		return pwdAlert;
	}
	/**
	 * @param lockDays the lockDays to set
	 */
	public void setLockDays(String lockDays) {
		this.lockDays = lockDays;
	}

	/**
	 * @return the lockDays
	 */
	public String getLockDays() {
		return lockDays;
	}

		/**
	 * @param passwordValidityDays the passwordValidityDays to set
	 */
	public void setPasswordValidityDays(String passwordValidityDays) {
		this.passwordValidityDays = passwordValidityDays;
	}

	/**
	 * @return the passwordValidityDays
	 */
	public String getPasswordValidityDays() {
		return passwordValidityDays;
	}

		/**
	 * @param wrongAttempts the wrongAttempts to set
	 */
	public void setWrongAttempts(String wrongAttempts) {
		this.wrongAttempts = wrongAttempts;
	}

	/**
	 * @return the wrongAttempts
	 */
	public String getWrongAttempts() {
		return wrongAttempts;
	}

}
