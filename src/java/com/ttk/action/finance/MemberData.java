package com.ttk.action.finance;



import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlType;

@XmlType
public class MemberData {
	private String member_Name;
	private String gender;
	private String dob;
	private String age;
	private String matCovered;
	private String relationship;
	private String salary;
	private String country_code;
//	private String policy_number;//not required
//	private String member_id;//not required
	





public MemberData() {
	// TODO Auto-generated constructor stub
}
//public Payments(String chequeno, String chequedate, String payeename, String settlementnumber,String issueddate)
public MemberData(String member_Name, String gender, String dob,String age,String matCovered,String relationship,String salary,String country_code) {
	super();
	
	this.member_Name = member_Name;
	this.gender = gender;
	this.dob = dob;
	this.age = age;
	this.matCovered = matCovered;
	this.salary = salary;
	this.country_code = country_code;
	this.relationship = relationship;
}

@XmlAttribute
public String getMember_Name() {
	return member_Name;
}



public void setMember_Name(String member_Name) {
	this.member_Name = member_Name;
}


@XmlAttribute
public String getGender() {
	return gender;
}



public void setGender(String gender) {
	this.gender = gender;
}


@XmlAttribute
public String getDob() {
	return dob;
}



public void setDob(String dob) {
	this.dob = dob;
}


@XmlAttribute
public String getAge() {
	return age;
}



public void setAge(String age) {
	this.age = age;
}


@XmlAttribute
public String getMatCovered() {
	return matCovered;
}



public void setMatCovered(String matCovered) {
	this.matCovered = matCovered;
}


@XmlAttribute
public String getSalary() {
	return salary;
}



public void setSalary(String salary) {
	this.salary = salary;
}





@XmlAttribute
public String getCountry_code() {
	return country_code;
}



public void setCountry_code(String country_code) {
	this.country_code = country_code;
}




@XmlAttribute
public String getRelationship() {
	return relationship;
}



public void setRelationship(String relationship) {
	this.relationship = relationship;
}


}

