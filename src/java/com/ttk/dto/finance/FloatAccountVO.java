/**
 * @ (#) FloatAccountVO.java June 07, 2006
 * Project       : TTK HealthCare Services
 * File          : FloatAccountVO.java
 * Author        : Srikanth H M
 * Company       : Span Systems Corporation
 * Date Created  : June 07, 2006
 *
 * @author       : Srikanth H M
 * Modified by   :
 * Modified date :
 * Reason        :
 */

package com.ttk.dto.finance;

/**
 * This VO contains float account information.
 */

public class FloatAccountVO extends BankAccountVO {
    
	private Long lngFloatAcctSeqID;  //float account seqID
	private String strFloatNo;     //Float No.
	private String strInsComp;   //Insurance Company.
	private String strInsCompCode;//Company Code
	private String strStatusDesc = "";//Status Description
    private Long lngInsSeqID;      //Insurance Seq ID
    private String strFloatAcctName = "";//Float name
    private String strProductAuthority;//Add By AK
    
    public String getStrProductAuthority() {
		return strProductAuthority;
	}

	public void setStrProductAuthority(String strProductAuthority) {
		this.strProductAuthority = strProductAuthority;
	}

	/** Retrieve the Float Account Name
	 * @return Returns the strFloatAcctName.
	 */
	public String getFloatAcctName() {
		return strFloatAcctName;
	}//end of getFloatAcctName()

	/** Sets the Float Name
	 * @param strFloatName The strFloatAcctName to set.
	 */
	public void setFloatAcctName(String strFloatAcctName) {
		this.strFloatAcctName = strFloatAcctName;
	}//end of setFloatAcctName(String strFloatAcctName)

	/** Retrieve the Status Description
	 * @return Returns the strStatusDesc.
	 */
	public String getStatusDesc() {
		return strStatusDesc;
	}//end of getStatusDesc()

	/** Sets the Status Description
	 * @param strStatusDesc The strStatusDesc to set.
	 */
	public void setStatusDesc(String strStatusDesc) {
		this.strStatusDesc = strStatusDesc;
	}//end of setStatusDesc(String strStatusDesc)

	/**Retrieve the Insurance SeqID.
     * @return Returns the lInsSeqID.
     */
    public Long getInsSeqID() {
        return lngInsSeqID;
    }//end of getInsSeqID()
    
    /**Sets the Insurance SeqID.
     * @param insSeqID The lInsSeqID to set.
     */
    public void setInsSeqID(Long lngInsSeqID) {
        this.lngInsSeqID = lngInsSeqID;
    }//end of setInsSeqID(Long lngInsSeqID)
    
    /**Retrieve the Float Account SeqID.
     * @return Returns the lngFloatAcctSeqID.
     */
    public Long getFloatAcctSeqID() {
        return lngFloatAcctSeqID;
    }//end of getFloatAcctSeqID()
    
    /**Sets the Float Account SeqID.
     * @param lngFloatAcctSeqID The lngFloatAcctSeqID to set.
     */
    public void setFloatAcctSeqID(Long lngFloatAcctSeqID) {
        this.lngFloatAcctSeqID = lngFloatAcctSeqID;
    }//end of setFloatAcctSeqID(Long lngFloatAcctSeqID)
    
    /**Retrieve the Float No.
     * @return Returns the lFloatNo.
     */
    public String getFloatNo() {
        return strFloatNo;
    }//end of getFloatNo()
    
    /**Sets the Float No.
     * @param floatNo The lFloatNo to set.
     */
    public void setFloatNo(String strFloatNo) {
        this.strFloatNo = strFloatNo;
    }//end of setFloatNo(String floatNo)
    
    /**Retrieve the Insurance Company.
     * @return Returns the strInsComp.
     */
    public String getInsComp() {
        return strInsComp;
    }//end of getInsComp()
    
    /**Sets the Insurance Company.
     * @param strInsComp The strInsComp to set.
     */
    public void setInsComp(String strInsComp) {
        this.strInsComp = strInsComp;
    }//end of setInsComp(String strInsComp)
    
    /**Retrieve the Insurance Company Code.
     * @return Returns the strInsCompCode.
     */
    public String getInsCompCode() {
        return strInsCompCode;
    }//end of getInsCompCode()
    
    /**Sets the Insurance Company Code.
     * @param strInsCompCode The strInsCompCode to set.
     */
    public void setInsCompCode(String strInsCompCode) {
        this.strInsCompCode = strInsCompCode;
    }//end of setInsCompCode(String strInsCompCode) 
}//end of FloatAccountVO