/**
 * @ (#) ICDCodeVO.java Feb 9, 2006
 * Project 	     : TTK HealthCare Services
 * File          : ICDCodeVO.java
 * Author        : RamaKrishna K M
 * Company       : Span Systems Corporation
 * Date Created  : Feb 9, 2006
 *
 * @author       :  RamaKrishna K M
 * Modified by   :  
 * Modified date :  
 * Reason        :  
 */

package com.ttk.dto.enrollment;

import com.ttk.dto.BaseVO;


public class ICDCodeVO extends BaseVO {
    
    private String strPercentage = "";
    private String strDRG = "";
    private String strDRG_D = "";
    private String strDescription = "";
    
    /** This method returns the Description
     * @return Returns the strDescription.
     */
    public String getDescription() {
        return strDescription;
    }//end of getDescription()
    
    /** This method sets the Description
     * @param strDescription The strDescription to set.
     */
    public void setDescription(String strDescription) {
        this.strDescription = strDescription;
    }//end of setDescription(String strDescription)
    
    /** This method returns the DRG
     * @return Returns the strDRG.
     */
    public String getDRG() {
        return strDRG;
    }//end of getDRG()
    
    /** This method sets the DRG
     * @param strDRG The strDRG to set.
     */
    public void setDRG(String strDRG) {
        this.strDRG = strDRG;
    }//end of setDRG(String strDRG)
    
    /** This method returns the DRG_D
     * @return Returns the strDRG_D.
     */
    public String getDRG_D() {
        return strDRG_D;
    }//end of getDRG_D()
    
    /** This method sets the DRG_D
     * @param strDRG_D The strDRG_D to set.
     */
    public void setDRG_D(String strDRG_D) {
        this.strDRG_D = strDRG_D;
    }//end of setDRG_D(String strDRG_D)
    
    /** This method returns the Percentage Match
     * @return Returns the strPercentage.
     */
    public String getPercentage() {
        return strPercentage;
    }//end of getPercentage()
    
    /** This method sets the Percentage Match
     * @param strPercentage The strPercentage to set.
     */
    public void setPercentage(String strPercentage) {
        this.strPercentage = strPercentage;
    }//end of setPercentage(String strPercentage)
}//end of ICDCodeVO
