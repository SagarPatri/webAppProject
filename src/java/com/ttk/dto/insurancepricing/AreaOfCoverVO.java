/**
 * @ (#) AreaOfCoverVO.java Aug 22, 2018
 * Project      : TTK HealthCare Services
 * File         : AreaOfCoverVO.java
 * Author       : Hare Govind
 * Company      : Span Systems Corporation
 * Date Created : Aug 22, 2018
 *
 * @author       : Hare Govind
 * Modified by   : 
 * Modified date : 
 * Reason        : 
 */

package com.ttk.dto.insurancepricing;
// import java.util.Date;



import java.util.Date;

import com.ttk.dto.BaseVO;

/**
 * This VO contains all details of any address. 
 * The corresponding DB Table is TPA_HOSP_ADDRESS.
 */
public class AreaOfCoverVO extends BaseVO{
    
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	//  declaration of the fields as private

	private String factId= "";
    private String description="";
    private String network="";
    
	public String getFactId() {
		return factId;
	}
	public void setFactId(String factId) {
		this.factId = factId;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getNetwork() {
		return network;
	}
	public void setNetwork(String network) {
		this.network = network;
	}
 
	   

	
	
}//end of AddressVO.java
