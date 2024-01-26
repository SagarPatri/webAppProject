/**
 * @ (#) SearchCriteria.java Sep 20, 2005
 * Project      : 
 * File         : SearchCriteria.java
 * Author       : Nagaraj D V
 * Company      : 
 * Date Created : Sep 20, 2005
 *
 * @author       :  Nagaraj D V
 * Modified by   : 
 * Modified date : 
 * Reason        : 
 */
package com.ttk.dto.common;

//import com.ttk.common.TTKCommon;
import com.ttk.dto.BaseVO;

public class SearchCriteria extends BaseVO{
    
    private String strName="";
    private String strValue="";
    private String strOperator="";
    
    
    /** 
     * Initialize the name and value
     * @param strName
     * @param strValue
     */
    public SearchCriteria(String strName, String strValue) {
        super();
        this.strName = strName;
        this.strValue = strValue;
    }//end of constructor
    
    /** 
     * Initialize the name and value
     * @param strName
     * @param strValue
     * @param strOperator
     */
    public SearchCriteria(String strName, String strValue,String strOperator) {
        super();
        this.strName = strName;
        this.strValue = strValue;
        this.strOperator = strOperator;
    }//end of constructor
    
    /**
     * Store the criteria name to be searched
     * 
     * @param strName String criteria name
     */
    public void setName(String strName)
    {
        this.strName = strName;
    }//end of setName(String strName)
    
    /**
     * Retreive the criteria name to be searched
     * 
     * @return strName String criteria name to be searched
     */
    public String getName()
    {
        return strName;
    }//end of getName()
    
    /**
     * Store the value to be searched
     * 
     * @param strValue String the value to be searched
     */
    public void setValue(String strValue)
    {
        this.strValue = strValue;
    }//end of setValue(String strValue)
    
    /**
     * Retreive the value to be searched
     * 
     * @return strValue String the value to be searched
     */
    public String getValue()
    {
        return strValue;
    }//end of getValue()

	/** This method returns the strOperator
	 * @return Returns the strOperator.
	 */
	public String getOperator() {
		return strOperator;
	}// End of getOperator()
	
	/** this method sets the strOperator
	 * @param strOperator The strOperator to set.
	 */
	public void setOperator(String strOperator) {
		this.strOperator = strOperator;
	}// End of setOperator(String strOperator)
	
}//end of class SearchCriteria
