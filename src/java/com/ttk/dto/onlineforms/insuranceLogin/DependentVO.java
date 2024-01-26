
/**
 * @ (#) DependentVO.java 21 August 2015
 * Author      : KISHOR KUMAR S H
 * Company     : RCS TECHNOLOGIES
 * Date Created: 02 Feb 2015
 *
 * @author 		 : KISHOR KUMAR S H
 * Modified by   : KISHOR KUMAR S H
 * Modified date : 21 August 2015
 * Reason        :
 *
 */

package com.ttk.dto.onlineforms.insuranceLogin;


import com.ttk.dto.BaseVO;

public class DependentVO extends BaseVO{
	

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	String name;
	int age=0;
	String gender;
	String relation;
	public String getRelation() {
		return relation;
	}
	public void setRelation(String relation) {
		this.relation = relation;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public int getAge() {
		return age;
	}
	public void setAge(int age) {
		this.age = age;
	}
	public String getGender() {
		return gender;
	}
	public void setGender(String gender) {
		this.gender = gender;
	}
	
	
	
	
}//end of DependentVO.java
