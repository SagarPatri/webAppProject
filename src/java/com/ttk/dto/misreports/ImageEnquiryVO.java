
/** @ (#) ImageEnquiryVO.java May 28, 2007
 * Project     : TTK Healthcare Services
 * File        : ImageEnquiryVO.java
 * Author      : Ajay Kumar
 * Company     : WebEdge Technologies Pvt.Ltd.
 * Date Created: May 28, 2007
 *
 * @author 		 : Ajay Kumar
 * Modified by   :
 * Modified date :
 * Reason        :
 *
 */

package com.ttk.dto.misreports;

import java.sql.Blob;
import java.util.ArrayList;
import com.ttk.dto.BaseVO;

public class ImageEnquiryVO extends BaseVO {
	
	private String policyNbr="";
    private String enrollmentNbr = "";
    private String enrollmentID="";
    private String memberName = "";
    private String strGroupName = "";
    private String groupId = "";
    private String corporateName = "";
    private String employeeNbr = "";
    private ArrayList alImageEnquiryList = null;
    private Blob choice_image;
    private String policyType= "";
   
    /** This method returns the Policy Number
     * @return Returns the strPolicyNbr.
     */
    public String getPolicyNbr() {
        return policyNbr;
    }//end of getPolicyNbr()
    
    /** This method sets the Policy Number
     * @param strPolicyNbr The strPolicyNbr to set.
     */
    public void setPolicyNbr(String policyNbr) {
        this.policyNbr = policyNbr;
    }//end of setPolicyNbr(String strPolicyNbr)

    /** This method returns the Enrollment Number
     * @return Returns the strEnrollmentNbr.
     */
    public String getEnrollmentNbr() {
        return enrollmentNbr;
    }//end of getEnrollmentNbr()
    
    /** This method sets the Enrollment Number
     * @param strEnrollmentNbr The strEnrollmentNbr to set.
     */
    public void setEnrollmentNbr(String enrollmentNbr) {
        this.enrollmentNbr = enrollmentNbr;
    }//end of setEnrollmentNbr(String strEnrollmentNbr)
    
    /** Retrieve the EnrollmentID
	 * @return Returns the strEnrollmentID.
	 */
	public String getEnrollmentID() {
		return enrollmentID;
	}//end of getEnrollmentID()
	
	/** Sets the EnrollmentID
	 * @param strEnrollmentID The strEnrollmentID to set.
	 */
	public void setEnrollmentID(String enrollmentID) {
		this.enrollmentID = enrollmentID;
	}//end of setEnrollmentID(String strEnrollmentID)
    /** This method returns the Member Name
     * @return Returns the strMemberName.
     */
    public String getMemberName() {
        return memberName;
    }//end of getMemberName()
    
    /** This method sets the Member Name
     * @param strMemberName The strMemberName to set.
     */
    public void setMemberName(String memberName) {
        this.memberName = memberName;
    }//end of setMemberName(String strMemberName)
    
    /** This method returns the Group Name
     * @return Returns the strGroupName.
     */
    public String getGroupName() {
        return strGroupName;
    }//end of getGroupName()
    
    /** This method sets the Group Name
     * @param strGroupName The strGroupName to set.
     */
    public void setGroupName(String strGroupName) {
        this.strGroupName = strGroupName;
    }//end of setGroupName(String strGroupName)
    
   /** This method returns the Corporate Name
     * @return Returns the strCorporateName.
     */
    public String getCorporateName() {
        return corporateName;
    }//end of getCorporateName()
    
    /** This method sets the Corporate Name
     * @param strCorporateName The strCorporateName to set.
     */
    public void setCorporateName(String corporateName) {
        this.corporateName = corporateName;
    }//end of setCorporateName(String strCorporateName)
    
    /** Retrieve the EmployeeNbr
	 * @return Returns the strEmployeeNbr.
	 */
	public String getEmployeeNbr() {
		return employeeNbr;
	}//end of getEmployeeNbr()

	/** Sets the EmployeeNbr
	 * @param strEmployeeNbr The strEmployeeNbr to set.
	 */
	public void setEmployeeNbr(String employeeNbr) {
		this.employeeNbr = employeeNbr;
	}//end of setEmployeeNbr(String strEmployeeNbr)
	
	/**Retrieve the ImageEnquiryList
	 * @return Returns the alImageEnquiryList.
	 */
	public ArrayList getImageEnquiryList() {
		return alImageEnquiryList;
	}//end of getImageEnquiryList()
	
	/**Sets the ImageEnquiryList
	 * @param alImageEnquiryList The alImageEnquiryList to set.
	 */
	public void setImageEnquiryList(ArrayList alImageEnquiryList) {
		this.alImageEnquiryList = alImageEnquiryList;
	}//end of setImageEnquiryList(ArrayList alImageEnquiryList)

	/**Retrieve the choice_image
	 * @return Returns the choice_image.
	 */
	public Blob getChoice_image() {
		return choice_image;
	}//end of getChoice_image()

	/**Sets the choice_image
	 * @param choice_image The choice_image to set.
	 */
	public void setChoice_image(Blob choice_image) {
		this.choice_image = choice_image;
	}//end of setChoice_image(Blob choice_image)

	/**Retrieve the groupId
	 * @return Returns the groupId.
	 */
	public String getGroupId() {
		return groupId;
	}//end of getGroupId()

	/**Sets the groupId
	 * @param groupId The groupId to set.
	 */
	public void setGroupId(String groupId) {
		this.groupId = groupId;
	}//end of setGroupId(String groupId)
	
	/**Retrieve the policyType
	 * @return Returns the policyType.
	 */
	public String getPolicyType() {
		return policyType;
	}//end of getPolicyType()

	/**Sets the policyType
	 * @param groupId The policyType to set.
	 */
	public void setPolicyType(String policyType) {
		this.policyType = policyType;
	}//end of setPolicyType(String policyType)
}//end of ImageEnquiryVO
