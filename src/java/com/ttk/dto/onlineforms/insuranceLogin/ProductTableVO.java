/**
 * @ (#) ProductTableVO.java 26th August 2015
 * Author      : KISHOR KUMAR S H
 * Company     : RCS TECHNOLOGIES
 * Date Created: 26th August 2015
 *
 * @author 		 : KISHOR KUMAR S H
 * Modified by   : KISHOR KUMAR S H
 * Modified date : 26th August 2015
 * Reason        :
 *
 */

package com.ttk.dto.onlineforms.insuranceLogin;


import com.ttk.dto.BaseVO;

public class ProductTableVO extends BaseVO{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private int productSeqId;
	private String productName;
	private String description;
	private String insProductCode;
	private String productCatTypeId;
	private String authorityType;
	public int getProductSeqId() {
		return productSeqId;
	}
	public void setProductSeqId(int productSeqId) {
		this.productSeqId = productSeqId;
	}
	public String getProductName() {
		return productName;
	}
	public void setProductName(String productName) {
		this.productName = productName;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getInsProductCode() {
		return insProductCode;
	}
	public void setInsProductCode(String insProductCode) {
		this.insProductCode = insProductCode;
	}
	public String getProductCatTypeId() {
		return productCatTypeId;
	}
	public void setProductCatTypeId(String productCatTypeId) {
		this.productCatTypeId = productCatTypeId;
	}
	public String getAuthorityType() {
		return authorityType;
	}
	public void setAuthorityType(String authorityType) {
		this.authorityType = authorityType;
	}
	
	
	
}//end of ProductTableVO.java

