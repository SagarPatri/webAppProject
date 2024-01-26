/**
 * @ (#) BankAccountVO.java June 07, 2006
 * Project       : TTK HealthCare Services
 * File          : BankAccountVO.java
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
 * This VO contains bank account information.
 */

public class BankAccountVO extends BankVO {

    private Long lngAccountSeqID;  //bankaccount SeqId.
    private String strAccountNO;  //Account No.
    private String strAccountName; //Account Name
    private String strTtkBranch; //TTK Branch
    private String strStatusDesc;    //Status
    private String strAccountType; //Account Type
    private String strTransactionYN;//Transaction is there or not

    /**Retrieve the Transaction is there or not.
     * @return Returns the strTransactionYN.
     */
    public String getTransactionYN() {
        return strTransactionYN;
    }//end of getTransactionYN()

    /**Sets the Transaction is there or not.
     * @param strTransactionYN The strTransactionYN to set.
     */
    public void setTransactionYN(String strTransactionYN) {
        this.strTransactionYN = strTransactionYN;
    }//end of setTransactionYN(String strTransactionYN)

    /**Retrieve the Account SeqID.
     * @return Returns the lAccountSeqID.
     */
    public Long getAccountSeqID() {
        return lngAccountSeqID;
    }//end of getAccountSeqID()

    /**Sets the Account SeqID.
     * @param lngAccountSeqID The lngAccountSeqID to set.
     */
    public void setAccountSeqID(Long lngAccountSeqID) {
        this.lngAccountSeqID = lngAccountSeqID;
    }//end of setAccountSeqID(Long lngAccountSeqID)

    /**Retrieve the Account Name.
     * @return Returns the strAccountName.
     */
    public String getAccountName() {
        return strAccountName;
    }//end of getAccountName()

    /**Sets the Account Name.
     * @param strAccountName The strAccountName to set.
     */
    public void setAccountName(String strAccountName) {
        this.strAccountName = strAccountName;
    }//end of setAccountName(String strAccountName)

    /**Retrieve the Account NO.
     * @return Returns the strAccountNO.
     */
    public String getAccountNO() {
        return strAccountNO;
    }//end of getAccountNO()

    /**Sets the Account NO.
     * @param strAccountNO The strAccountNO to set.
     */
    public void setAccountNO(String strAccountNO) {
        this.strAccountNO = strAccountNO;
    }//end of setAccountNO(String strAccountNO)

    /**Retrieve the Account Type.
     * @return Returns the strAccountType.
     */
    public String getAccountType() {
        return strAccountType;
    }//end of getAccountType()

    /**Sets the AccountType.
     * @param strAccountType The strAccountType to set.
     */
    public void setAccountType(String strAccountType) {
        this.strAccountType = strAccountType;
    }//end of setAccountType(String strAccountType)

    /**Retrieve the Status Description.
     * @return Returns the strStatusDesc.
     */
    public String getStatusDesc() {
        return strStatusDesc;
    }//end of getStatusDesc()

    /**Sets the Status Description.
     * @param strStatusDesc The strStatusDesc to set.
     */
    public void setStatusDesc(String strStatusDesc) {
        this.strStatusDesc = strStatusDesc;
    }//end of setStatusDesc(String strStatusDesc)

    /**Retrieve the TtkBranch.
     * @return Returns the strTtkBranch.
     */
    public String getTtkBranch() {
        return strTtkBranch;
    }//end of getTtkBranch()

    /**Sets the Ttk Branch.
     * @param strTtkBranch The strTtkBranch to set.
     */
    public void setTtkBranch(String strTtkBranch) {
        this.strTtkBranch = strTtkBranch;
    }//end of setTtkBranch
}//end of BankAccountVO

