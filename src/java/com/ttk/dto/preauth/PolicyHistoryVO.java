/**
 * @ (#) PolicyHistoryVO.java Apr 20, 2006
 * Project      : TTK HealthCare Services
 * File         : PolicyHistoryVO.java
 * Author       : Arun K N
 * Company      : Span Systems Corporation
 * Date Created : Apr 20, 2006
 *
 * @author       :  Arun K N
 * Modified by   : 
 * Modified date : 
 * Reason        : 
 */

package com.ttk.dto.preauth;

import java.util.Date;

import com.ttk.common.TTKCommon;

public class PolicyHistoryVO extends PreAuthVO{
    
    private Date dtStartDate=null;
    private Date dtEndDate=null;
    
    /**
     * Retrieve the End Date
     * @return  dtEndDate Date
     */
    public String getEndDate() {
        return TTKCommon.getFormattedDate(dtEndDate);
    }//end of getEndDate()
    
    /**
     * Sets the End Date
     * @param  dtEndDate Date  
     */
    public void setEndDate(Date dtEndDate) {
        this.dtEndDate = dtEndDate;
    }//end of setEndDate(Date dtEndDate)
    
    /**
     * Retrieve the Start Date
     * @return  dtStartDate Date
     */
    public String getStartDate() {
        return TTKCommon.getFormattedDate(dtStartDate);
    }//end of getStartDate()
    
    /**
     * Sets the Start Date
     * @param  dtStartDate Date  
     */
    public void setStartDate(Date dtStartDate) {
        this.dtStartDate = dtStartDate;
    }//end of setStartDate(Date dtStartDate)
    
}//end of PolicyHistoryVO.java
