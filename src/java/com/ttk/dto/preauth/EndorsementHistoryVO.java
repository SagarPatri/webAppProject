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
import com.ttk.dto.enrollment.PolicyVO;

public class EndorsementHistoryVO extends PolicyVO{

    private Date dtEffectiveDate=null;
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
     * Retrieve the Effective Date
     * @return  dtEffectiveDate Date
     */
    public String getEffectiveDate() {
        return TTKCommon.getFormattedDate(dtEffectiveDate);
    }//end of getEffectiveDate()

    /**
     * Sets the Effective Date
     * @param  dtStartDate Date
     */
    public void setEffectiveDate(Date dtEffectiveDate) {
        this.dtEffectiveDate = dtEffectiveDate;
    }//end of setEffectiveDate(Date dtEffectiveDate)

}//end of PolicyHistoryVO.java
