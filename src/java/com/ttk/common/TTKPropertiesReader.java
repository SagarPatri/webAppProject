/**
 * @ (#) TTKPropertiesReader.java 12th Jul 2005
 * Project      : 
 * File         : TTKPropertiesReader.java
 * Author       : 
 * Company      : 
 * Date Created : 12th Jul 2005
 *
 * @author       :  
 * Modified by   : 
 * Modified date : 
 * Reason        : 
 */

package com.ttk.common;
import java.io.FileInputStream;
import java.util.Properties;

//import com.ttk.common.exception.TTKException;

public class TTKPropertiesReader
{

	
	 private static Properties prop = null; //Properties
	/**
	 * This method takes the property name and returns the value of it in the properties file.
	 * @param strPropertyName String The property name
	 * @return strPropertyValue String The property value
	 */    
	public static String getPropertyValue(String strPropertyName)
	{
		String strPropertyValue="";
		try
		{
		    if (prop == null)
		    {
		        prop=new Properties();
			    prop.load(new FileInputStream("ttk.properties"));
			}
			strPropertyValue  = (String)prop.get(strPropertyName);
		}// end of try
		
		catch(Exception exp){
			exp.printStackTrace();
		}
		return strPropertyValue;
	}//end of getPropertyValue(String strPropertyName)
	
}//end of class TTKPropertiesReader

