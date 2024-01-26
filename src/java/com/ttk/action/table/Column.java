/**
 * @ (#) Column.java 12th Jul 2005
 * Project      : 
 * File         : Column.java
 * Author       : 
 * Company      : 
 * Date Created : 12th Jul 2005
 *
 * @author       :  
 * Modified by   : 
 * Modified date : 
 * Reason        : 
 */

package com.ttk.action.table;

import java.io.Serializable;
/**
 * This Classs holds the properties of a cloumn
 */
public class Column implements Serializable
{
	String  strColName = null;                  //Column Heading
	String  strMethodName = null;               //Method name in the value object 
	   
	boolean bVisibility = true;                 //Visible property of the column
	boolean bIsComponent = false;               //Is this column having any component
	String  strComponentType = "";              //Component type
	String  strComponentMethodName = "";        //Method name of value object to set the value for the component
	String  strComponentName = "";              // Name of the component
	boolean bIsLink = false;                    //Is column data contains hyper link or not
	String  strLink = "";                       //Column data link url
	String  strLinkParamName = "";              //Column data link url param name
	String  strLinkParamMethodName = "";        //Column data link url param value
	boolean bIsHeaderLink = false;              //Is header contins link
	String  strHeaderLink = "";                 //Header link url
	String  strHeaderLinkParamName = "";        //Header link url param name
	String  strHeaderLinkParamMethodName = "";  //Header link url param value
	int 	iWidth = 0;                         //width of the column in pixels
    String  strWidth = "";                         //width of the column in %
	String  strLinkTitle = "";                  //Title for data hyperlink 
	String  strHeaderLinkTitle = "";            //Title for header hyper link
	String  strAlign = "left";                  //Column horizantal alignment
	String  strValign = "middle";               //Column vertical alignment
	String  strDBColumnName = "";               //Data base table column name 
	String 	strStringWidth = "";                 //No. of character to be displayed in a line
    boolean bIsImageLink = false;
    boolean bIsImage = false;
    String strImageName = "";
    String strImageTitle = "";
	String strImageName2 = "";
    String strImageTitle2 = "";
    String linkTitleMethodName = "";
	
    
    //default constructor
	public Column()
	{
	}
	
    /**
     * This method sets the boolean value 
     * to indicate that column contains image or not
     *
     * @param bIsImage boolean 
     */
    public void setIsImage(boolean bIsImage)
    {
        this.bIsImage = bIsImage;
    }//end of setIsImageLink method
    
    /**
     * This method return boolean value 
     * to indicate that column contains image or not
     *
     * @return bIsImage boolean true/false
     */
    public boolean isImage()
    {
        return this.bIsImage;
    }//end of isImage method
    
    /**
     * This method sets the boolean value 
     * to indicate that column contains image link or not
     *
     * @param bIsImageLink boolean 
     */
    public void setIsImageLink(boolean bIsImageLink)
    {
        this.bIsImageLink = bIsImageLink;
    }//end of setIsImageLink method
    
    /**
     * This method return boolean value 
     * to indicate that column contains image link or not
     *
     * @return bIsImageLink boolean true/false
     */
    public boolean isImageLink()
    {
        return this.bIsImageLink;
    }//end of isImageLink method
    
    /**
     * This method gets the image title
     *
     * @return strImageTitle String image title
     */
    public String getImageTitle()
    {
        return this.strImageTitle;
    }//end of getImageTitle() method
    
    /**
     * This method sets the image title
     *
     * @param strImageTitle String image name
     */
    public void setImageTitle(String strImageTitle)
    {
        this.strImageTitle = strImageTitle; 
    }//end of setImageTitle(String strImageTitle) method    
	//koc 11 koc11
    public String getImageTitle2()
    {
        return this.strImageTitle2;
    }//end of getImageTitle2() method
    public void setImageTitle2(String strImageTitle2)
    {
        this.strImageTitle2 = strImageTitle2; 
    }//end of setImageTitle2(String strImageTitle) method 
    
    /**
     * This method sets the image name
     *
     * @param strImageName String image name
     */
    public void setImageName(String strImageName)
    {
        this.strImageName = strImageName; 
    }//end of setImageName method    
    
    /**
     * This method gets the image name
     *
     * @return strImageName String image name
     */
    public String getImageName()
    {
        return this.strImageName;
    }//end of getImageName method
	 //koc 11 koc11
    public void setImageName2(String strImageName2)
    {
        this.strImageName2 = strImageName2; 
    }//end of setImageName method  
	/**
     * This method gets the image name
     *
     * @return strImageName String image name
     */
    public String getImageName2()
    {
        return this.strImageName2;
    }//end of getImageName method
    
    //constructor accepts column name as a parameter
	public Column(String strColName)
	{
		setColumnName(strColName);
	}//end of Column constructor
    
    //constructor accepts column name and width of the columns as  parameter 
	public Column(String strColName,int iWidth)
	{
		setColumnName(strColName);
		setVisibility(bVisibility);
		setWidth(iWidth);
	}//end of Column constructor
    
    /**
     * This method sets the data base table column name
     *
     * @param strDBColumnName String DB Column name
     */
    public void setDBColumnName(String strDBColumnName)
    {
            this.strDBColumnName = strDBColumnName; 
    }//end of setDBColumnName method    
    
    /**
     * This method gets the data base table column name
     *
     * @return strDBColumnName String DB Column name
     */
    public String getDBColumnName()
    {
            return this.strDBColumnName;
    }//end of getDBColumnName method
    
    /**
     * This method sets the title for column header link
     *
     * @param strHeaderLinkTitle String title for header link
     */
	public void setHeaderLinkTitle(String strHeaderLinkTitle)
	{
		this.strHeaderLinkTitle = strHeaderLinkTitle;
	}//end of setHeaderLinkTitle method
	
	/**
     * This method gets the title of column header link
     *
     * @return strHeaderLinkTitle String title for header link
     */
	public String getHeaderLinkTitle()
	{
		return this.strHeaderLinkTitle;
	}//end of getHeaderLinkTitle method

    /**
     * This method sets the title for url
     *
     * @param strLinkTitle String title for url
     */
	public void setLinkTitle(String strLinkTitle)
	{
		this.strLinkTitle = strLinkTitle;
	}//end of setLinkTitle method

    /**
     * This method gets the title for url
     *
     * @return strLinkTitle String title for url
     */
	public String getLinkTitle()
	{
		return this.strLinkTitle;
	}//end of getLinkTitle method
    
    /**
     * This method sets the column heading
     *
     * @param strColName String column heading
     */
	public void setColumnName(String strColName)
	{
		this.strColName = strColName;
	}//end of setColumnName method
    
    /**
     * This method gets the column heading
     *
     * @return strLinkTitle String column heading
     */
	public String getColumnName()
	{
		return this.strColName;
	}//end of getColumnName method
    
    /**
     * This method sets the method name defined in the value object
     * to display the value in the grid column
     *
     * @param strMethodName String method name
     */
	public void setMethodName(String strMethodName)
	{
		this.strMethodName = strMethodName;
	}//end of setMethodName method

    /**
     * This method gets the method name defined in the value object
     * to display the value in the grid column
     *
     * @return strColName String method name
     */
	public String getMethodName()
	{
		return this.strMethodName;
	}//end of getMethodName method
    
    /**
     * This method sets the boolean value 
     * to indicate that column contains component or not
     *
     * @param bIsComponent boolean 
     */
	public void setIsComponent(boolean bIsComponent)
	{
		this.bIsComponent = bIsComponent;
	}//end of setIsComponent method
    
    /**
     * This method return boolean value 
     * to indicate that column contains component or not
     *
     * @return bIsComponent boolean true/false
     */
	public boolean isComponent()
	{
		return this.bIsComponent;
	}//end of isComponent method

    /**
     * This method sets the visible property of the column
     * It accepts boolean value true/false
     *
     * @param bVisibility boolean 
     */
	public void setVisibility(boolean bVisibility)
	{
	  this.bVisibility = bVisibility;
	}//end of setVisibility method
    
    /**
     * This method gets the visible property of the column
     * 
     * @return bVisibility boolean 
     */
	public boolean isVisible()
	{
		return this.bVisibility;
	}//end of isVisible method
    
    /**
     * This method sets component name
     *
     * @param strComponentName String name of the component
     */
	public void setComponentName(String strComponentName)
	{
		setIsComponent(true);
		this.strComponentName = strComponentName;
	}//end of setComponentName method
    
    /**
     * This method gets component name
     *
     * @return strComponentName String name of the component
     */
	public String getComponentName()
	{
		return this.strComponentName;
	}//end of getComponentName method
    
    /**
     * This method sets method name defined in the value object
     * for component 
     *
     * @param strComponentMethodName String 
     */
	public void setComponentMethodName(String strComponentMethodName)
	{
		this.strComponentMethodName = strComponentMethodName;
	}//end of setComponentMethodName method
    
    /**
     * This method gets method name to get the value for component 
     *
     * @return strComponentMethodName String 
     */
	public String getComponentMethodName()
	{
		return this.strComponentMethodName;
	}//end of getComponentMethodName method
    
    /**
     * This method sets type of the component
     *
     * @param strComponentType String 
     */
	public void setComponentType(String strComponentType)
	{
		this.strComponentType = strComponentType;
	}//end of setComponentType method
    
    /**
     * This method gets type of the component
     *
     * @param strComponentType String 
     */
	public String getComponentType()
	{
		return this.strComponentType;
	}//end of getComponentType method
    
    /**
     * This method sets either true or false to indicate that column data contains link or not
     *
     * @param bIsLink boolean 
     */
	public void setIsLink(boolean bIsLink)
	{
		this.bIsLink  = bIsLink;
	}//end of setIsLink method
    
    /**
     * This method returns either true or false to indicate column data contains link or not
     *
     * @return bIsLink boolean 
     */
	public boolean isLink()
	{
		return this.bIsLink;
	}//end of isLink method
    
    /**
     * This method sets url for column data
     *
     * @param strLink String 
     */
	public void setLink(String strLink)
	{
		setIsLink(true);
		this.strLink = strLink;
	}//end of setLink method
    
     /**
     * This method gets the url for column data
     *
     * @return strLink String 
     */
	public String getLink()
	{
		return this.strLink;
	}//end of getLink method
    
    /**
     * This method sets param name for column data url
     *
     * @param strLinkParamName String 
     */
	public void setLinkParamName(String strLinkParamName)
	{
		this.strLinkParamName = strLinkParamName;
	}//end of setLinkParamName method
    
    /**
     * This method gets param name for column data url
     *
     * @return strLinkParamName String 
     */
	public String getLinkParamName()
	{
		return this.strLinkParamName;
	}//end of getLinkParamName method
    
    /**
     * This method sets method name of param value for column data url
     *
     * @param strLinkParamName String 
     */
	public void setLinkParamMethodName(String strLinkParamMethodName)
	{
		this.strLinkParamMethodName = strLinkParamMethodName;
	}//end of setLinkParamMethodName method
    
    /**
     * This method gets method name of param value for column data url
     *
     * @param strLinkParamName String 
     */
	public String getLinkParamMethodName()
	{
		return this.strLinkParamMethodName;
	}//end of getLinkParamMethodName method
    
    /**
     * This method sets width of the column
     *
     * @param iWidth int 
     */
	public void setWidth(int iWidth)
	{
		this.iWidth = iWidth;
	}//end of setWidth method
    
    public void setColumnWidth(String strWidth)
    {
        this.strWidth = strWidth;
    }//end of setColumnWidth(String strWidth) method
    
    public String getColumnWidth()
    {
        return this.strWidth; 
    }//end of setColumnWidth(String strWidth) method
    /**
     * This method gets width of the column
     *
     * @return iWidth int 
     */
	public int getWidth()
	{
		return this.iWidth;
	}//end of getWidth method
    
    /**
     * This method sets boolean value for the column header link is there or not
     *
     * @param bIsHeaderLink boolean 
     */
	public void setIsHeaderLink(boolean bIsHeaderLink)
	{
		this.bIsHeaderLink  = bIsHeaderLink;
	}//end of setIsHeaderLink method
    
    /**
     * This method return boolean value for the column header link is there or not
     *
     * @return bIsHeaderLink boolean 
     */
	public boolean isHeaderLink()
	{
		return this.bIsHeaderLink;
	}//end of isHeaderLink method
	
    /**
     * This method sets url for column header
     *
     * @param strHeaderLink String 
     */
	public void setHeaderLink(String strHeaderLink)
	{
		setIsHeaderLink(true);
		this.strHeaderLink = strHeaderLink;
	}//end of setHeaderLink method
    
    /**
     * This method gets url for column header
     *
     * @param strHeaderLink String 
     */
	public String getHeaderLink()
	{
		return this.strHeaderLink;
	}//end of getHeaderLink method
    
    /**
     * This method sets param name of url for column header
     *
     * @param strHeaderLinkParamName String 
     */
	public void setHeaderLinkParamName(String strHeaderLinkParamName)
	{
		this.strHeaderLinkParamName = strHeaderLinkParamName;
	}//end of setHeaderLinkParamName method
    
    /**
     * This method gets param name of url for column header
     *
     * @return strHeaderLinkParamName String 
     */
	public String getHeaderLinkParamName()
	{
		return this.strHeaderLinkParamName;
	}//end of getHeaderLinkParamName method
    
    /**
     * This method sets method name defined in the value object 
     * to get param value of url for column header link
     *
     * @param strHeaderLinkParamMethodName String 
     */
	public void setHeaderLinkParamMethodName(String strHeaderLinkParamMethodName)
	{
		this.strHeaderLinkParamMethodName = strHeaderLinkParamMethodName;
	}//end of setHeaderLinkParamMethodName method
    
    /**
     * This method gets method name defined in the value object 
     * to get param value of url for column header link
     *
     * @return strHeaderLinkParamMethodName String 
     */
	public String getHeaderLinkParamMethodName()
	{
		return this.strHeaderLinkParamMethodName;
	}//end of getHeaderLinkParamMethodName method
    
    /**
     * This method sets horizontal alignment
     *
     * @param strAlign String 
     */
    public void setAlign(String strAlign)
	{
		this.strAlign = strAlign;
	}//end of setAlign method
    
    /**
     * This method gets horizontal alignment
     *
     * @return strAlign String 
     */
	public String getAlign()
	{
		return this.strAlign;
	}//end of getAlign method
    
    /**
     * This method sets vertical alignment
     *
     * @param strAlign String 
     */
	public void setValign(String strValign)
	{
		this.strValign = strValign;
	}//end of setValign method
    
    /**
     * This method gets vertical alignment
     *
     * @param strAlign String 
     */
	public String getValign(String strValign)
	{
		return this.strValign;
	}//end of getValign method
	
	/**
     * This method sets No. of character to be displayed in a line for the given column
     *
     * @param strStringWidth String 
     */
	public void setStringWidth(String strStringWidth)
	{
		this.strStringWidth = strStringWidth;
	}//end of setStringWidth method
    
    /**
     * This method gets No. of character to be displayed in a line for the given column
     *
     * @return strStringWidth String 
     */
	public String getStringWidth()
	{
		return this.strStringWidth;
	}//end of getStringWidth method
	
	public void setLinkTitleMethodName(String linkTitleMethodName) {
		this.linkTitleMethodName = linkTitleMethodName;
	}
    public String getLinkTitleMethodName() {
		return linkTitleMethodName;
	}
}//end of Column Class

