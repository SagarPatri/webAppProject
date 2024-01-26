/**
 * @ (#) ClauseDocumentVO.java Aug 26, 2008
 * Project 	     : TTK HealthCare Services
 * File          : ClauseDocumentVO.java
 * Author        : RamaKrishna K M
 * Company       : Span Systems Corporation
 * Date Created  : Aug 26, 2008
 *
 * @author       :  RamaKrishna K M
 * Modified by   :  
 * Modified date :  
 * Reason        :  
 */

package com.ttk.dto.administration;

import com.ttk.dto.BaseVO;

/**
 * @author ramakrishna_km
 *
 */
public class ClauseDocumentVO extends BaseVO{
	
	private Long lngClauseDocSeqID = null;
	private String strDocName = "";
	private String strDocPath = "";
	private Long lngProdPolicySeqID = null;
	
	/** Retrieve the ClauseDocSeqID
	 * @return Returns the lngClauseDocSeqID.
	 */
	public Long getClauseDocSeqID() {
		return lngClauseDocSeqID;
	}//end of getClauseDocSeqID()
	
	/** Sets the ClauseDocSeqID
	 * @param lngClauseDocSeqID The lngClauseDocSeqID to set.
	 */
	public void setClauseDocSeqID(Long lngClauseDocSeqID) {
		this.lngClauseDocSeqID = lngClauseDocSeqID;
	}//end of setClauseDocSeqID(Long lngClauseDocSeqID)
	
	/** Retrieve the ProdPolicySeqID
	 * @return Returns the lngProdPolicySeqID.
	 */
	public Long getProdPolicySeqID() {
		return lngProdPolicySeqID;
	}//end of getProdPolicySeqID()
	
	/** Sets the ProdPolicySeqID
	 * @param lngProdPolicySeqID The lngProdPolicySeqID to set.
	 */
	public void setProdPolicySeqID(Long lngProdPolicySeqID) {
		this.lngProdPolicySeqID = lngProdPolicySeqID;
	}//end of setProdPolicySeqID(Long lngProdPolicySeqID)
	
	/** Retrieve the DocName
	 * @return Returns the strDocName.
	 */
	public String getDocName() {
		return strDocName;
	}//end of getDocName()
	
	/** Sets the DocName
	 * @param strDocName The strDocName to set.
	 */
	public void setDocName(String strDocName) {
		this.strDocName = strDocName;
	}//end of setDocName(String strDocName)
	
	/** Retrieve the DocPath
	 * @return Returns the strDocPath.
	 */
	public String getDocPath() {
		return strDocPath;
	}//end of getDocPath()
	
	/** Sets the DocPath
	 * @param strDocPath The strDocPath to set.
	 */
	public void setDocPath(String strDocPath) {
		this.strDocPath = strDocPath;
	}//end of setDocPath(String strDocPath)

}//end of ClauseDocumentVO
