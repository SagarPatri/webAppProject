/** 
 *   @ (#) BankDetailVO.java June 07, 2006
 *   Project      : TTK HealthCare Services
 *   File         : BankDetailVO.java
 *   Author       : Balakrishna E
 *   Company      : Span Systems Corporation
 *   Date Created : June 07, 2006
 *  
 *   @author       :  Balakrishna E
 *   Modified by   : 
 *   Modified date : 
 *   Reason        : 
 */

package com.ttk.dto.finance;

/**
 * This VO contains bank detail information.
 */

public class BankDetailVO extends BankVO {
     

     private String strOfficeTypeID;
     private Long lngHeadOfficeTypeID = null;
     private String strRemarks = "";
     private Long lngChequeTemplateID = null;
     private BankAddressVO bankAddressVO;

     private String strFileName;
     private String strIban;
     
     public String getIban() {
          return strIban;
     }

     public void setIban(String strIban) {
          this.strIban = strIban;
     }

     
     public String getFileName() {
          return strFileName;
     }

     public void setFileName(String strFileName) {
          this.strFileName = strFileName;
     }
     
     /** Retrieve the HeadOffice Type ID
      * @return Returns the lngHeadOfficeTypeID.
      */
     public Long getHeadOfficeTypeID() {
          return lngHeadOfficeTypeID;
     }//end of getHeadOfficeTypeID()

     /** Sets the HeadOffice Type ID
      * @param lngHeadOfficeTypeID The lngHeadOfficeTypeID to set.
      */
     public void setHeadOfficeTypeID(Long lngHeadOfficeTypeID) {
          this.lngHeadOfficeTypeID = lngHeadOfficeTypeID;
     }//end of setHeadOfficeTypeID(Long lngHeadOfficeTypeID)

     /** Retrieve the Remarks
      * @return Returns the strRemarks.
      */
     public String getRemarks() {
          return strRemarks;
     }//end of getRemarks()

     /** Sets the Remarks
      * @param strRemarks The strRemarks to set.
      */
     public void setRemarks(String strRemarks) {
          this.strRemarks = strRemarks;
     }//end of setRemarks(String strRemarks)

     /** Retrieve the Bank Address
      * @return Returns the bankAddressVO.
      */
     public BankAddressVO getBankAddressVO() {
          return bankAddressVO;
     }//end of getBankAddressVO()
     
     /** Sets the Bank Address
      * @param bankAddressVO The bankAddressVO to set.
      */
     public void setBankAddressVO(BankAddressVO bankAddressVO) {
          this.bankAddressVO = bankAddressVO;
     }//end of setBankAddressVO(BankAddressVO bankAddressVO)
     
     /** Retrieve the Office Type ID
      * @return Returns the strOfficeTypeID.
      */
     public String getOfficeTypeID() {
          return strOfficeTypeID;
     }//end of getOfficeTypeID()
     
     /** Sets the Office Type ID
      * @param strOfficeTypeID The strOfficeTypeID to set.
      */
     public void setOfficeTypeID(String strOfficeTypeID) {
          this.strOfficeTypeID = strOfficeTypeID;
     }//end of setOfficeTypeID(String strOfficeTypeID)

     /** Retrieve the ChequeTemplateID
      * @return Returns the lngChequeTemplateID.
      */
     public Long getChequeTemplateID() {
          return lngChequeTemplateID;
     }//end of getChequeTemplateID()

     /** Sets the ChequeTemplateID
      * @param lngChequeTemplateID The lngChequeTemplateID to set.
      */
     public void setChequeTemplateID(Long lngChequeTemplateID) {
          this.lngChequeTemplateID = lngChequeTemplateID;
     }//end of setChequeTemplateID(Long lngChequeTemplateID)
     
}//end of BankDetailVO
