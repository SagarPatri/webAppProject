package com.ttk.dto.maintenance;

import com.ttk.dto.BaseVO;

public class ICDCodeVO extends BaseVO {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1731500874626535968L;
		private Long pedCode;
		private String icdCode;
		private String description;
		private String mostCommon;
		private String masterIcdCode;
		private Long masterPedCode;
		/**
		 * 
		 * @return
		 */
		public String getIcdCode() {
			return icdCode;
		}
		/**
		 * 
		 * @param icdCode
		 */
		public void setIcdCode(String icdCode) {
			this.icdCode = icdCode;
		}
		/**
		 * 
		 * @return
		 */
		public String getDescription() {
			return description;
		}
		/**
		 * 
		 * @param description
		 */
		public void setDescription(String description) {
			this.description = description;
		}
		/**
		 * 
		 * @return
		 */
		public String getMostCommon() {
			return mostCommon;
		}
		/**
		 * 
		 * @param mostCommon
		 */
		public void setMostCommon(String mostCommon) {
			this.mostCommon = mostCommon;
		}
		/**
		 * 
		 * @return
		 */
		public String getMasterIcdCode() {
			return masterIcdCode;
		}
		/**
		 * 
		 * @param masterIcdCode
		 */
		public void setMasterIcdCode(String masterIcdCode) {
			this.masterIcdCode = masterIcdCode;
		}
		/***
		 * 
		 * @return
		 */
		public Long getPedCode() {
			return pedCode;
		}
		/**
		 * 
		 * @param pedCode
		 */
		public void setPedCode(Long pedCode) {
			this.pedCode = pedCode;
		}
		/**
		 * 
		 * @return
		 */
		public static long getSerialVersionUID() {
			return serialVersionUID;
		}
		/**
		 * 
		 * @return
		 */
		public Long getMasterPedCode() {
			return masterPedCode;
		}
		/**
		 * 
		 * @param masterPedCode
		 */
		public void setMasterPedCode(Long masterPedCode) {
			this.masterPedCode = masterPedCode;
		}
}
