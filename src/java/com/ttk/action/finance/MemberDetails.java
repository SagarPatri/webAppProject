package com.ttk.action.finance;



import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;
@XmlType
public class MemberDetails {
	

private MemberData memberData;

private String uploadstatus;

public MemberDetails() {}

public MemberDetails(String uploadstatus, MemberData payments) {
	super();	
	this.uploadstatus=uploadstatus;
	this.memberData=memberData;	
	}


@XmlElement
public MemberData getMemberData() {
	return memberData;
}

public void setMemberData(MemberData memberData) {
	this.memberData = memberData;
}

@XmlAttribute
public String getUploadstatus() {
	return uploadstatus;
}
public void setUploadstatus(String uploadstatus) {
	this.uploadstatus = uploadstatus;
}
}

