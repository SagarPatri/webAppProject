/**
 * @ (#) ErrorLogVO.java Sep 07, 2006
 * Project 	     : TTK HealthCare Services
 * File          : ErrorLogVO.java
 * Author        : Krishna K H
 * Company       : Span Systems Corporation
 * Date Created  : Sep 07, 2006
 *
 * @author       :  Krishna K H
 * Modified by   :
 * Modified date :
 * Reason        :
 */

package com.ttk.dto.webservice;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Errors{
//	@JsonProperty("Index")
	private String index ="";
//	@JsonProperty("Error")
	private String error ="";
	
	public String getError() {
		return error;
	}
	public void setError(String error) {
		this.error = error;
	}
	public String getIndex() {
		return index;
	}
	public void setIndex(String index) {
		this.index = index;
	}
	@Override
	public String toString() {
		return "Errors [index=" + index + ", error=" + error + "]";
	}
}//end of MemberVO
