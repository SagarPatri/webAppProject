package com.ttk.action.finance;



import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name="batch")
public class Batch {
private FileDetail fileDetail;

private List<Policy> policy;

@XmlElement
public List<Policy> getPolicy() {
	return policy;
}

public void setPolicy(List<Policy> policy) {
	this.policy = policy;
}

private List<MemberDetails> memberDetails;

@XmlElement
public List<MemberDetails> getMemberDetails() {
	return memberDetails;
}

public void setMemberDetails(List<MemberDetails> memberDetails) {
	this.memberDetails = memberDetails;
}

@XmlElement(name="filedetail")
public FileDetail getFileDetail() {
	return fileDetail;
}

public void setFileDetail(FileDetail fileDetail) {
	this.fileDetail = fileDetail;
}
}
