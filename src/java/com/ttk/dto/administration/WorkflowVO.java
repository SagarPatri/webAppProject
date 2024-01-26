/**
 * @ (#)  WorkflowVO.java Dec 20, 2005
 * Project       : TTKPROJECT
 * File          : WorkflowVO.java
 * Author        : Balakrishna.E
 * Company       : Span Systems Corporation
 * Date Created  : Dec 20, 2005
 *
 * @author       : Balakrishna.E
 * Modified by   : 
 * Modified date : 
 * Reason        : 
 */
package com.ttk.dto.administration;

import java.util.ArrayList;

import com.ttk.dto.BaseVO;

public class WorkflowVO extends BaseVO {
	private Long lngWorkflowSeqID=null;		//WORKFLOW_SEQ_ID
	private String strName="";				//WORKFLOW_NAME
	private String strDesc="";				//WORKFLOW_DESCRIPTION
	private ArrayList alEventVO=null;		
	
	/**This menthod retuns the EventVO ArrayList
	 * @return Returns the alEventVO.
	 */
	public ArrayList getEventVO() {
		return alEventVO;
	}//end of getEventVO()
	
	/**This menthod sets the EventVo ArrayList
	 * @param alEventVO The alEventVO to set.
	 */
	public void setEventVO(ArrayList alEventVO) {
		this.alEventVO = alEventVO;
	}//end of setEventVO(ArrayList alEventVO)
	
	/**This method returns the Workflow Sequence ID
	 * @return Returns the lngWorkflowSeqID.
	 */
	public Long getWorkflowSeqID() {
		return lngWorkflowSeqID;
	}//end of getWorkflowSeqID()
	
	/**This method sets the Workflow Sequence ID
	 * @param lngWorkflowSeqID The lngWorkflowSeqID to set.
	 */
	public void setWorkflowSeqID(Long lngWorkflowSeqID) {
		this.lngWorkflowSeqID = lngWorkflowSeqID;
	}//end of setWorkflowSeqID(Long lngWorkflowSeqID)
	
	/**This method returns the Workflow Description
	 * @return Returns the strDesc.
	 */
	public String getWorkflowDesc() {
		return strDesc;
	}//end of getWorkflowDesc()
	
	/**This method sets the Workflow Description
	 * @param strDesc The strDesc to set.
	 */
	public void setWorkflowDesc(String strDesc) {
		this.strDesc = strDesc;
	}//end of setWorkflowDesc(String strDesc)
	
	/**This method returns the Workflow Name
	 * @return Returns the strName.
	 */
	public String getWorkflowName() {
		return strName;
	}//end of getWorkflowName()
	
	/**This method sets the Workflow Name
	 * @param strName The strName to set.
	 */
	public void setWorkflowName(String strName) {
		this.strName = strName;
	}//end of setWorkflowName(String strName)
	
}//end of WorkflowVO
