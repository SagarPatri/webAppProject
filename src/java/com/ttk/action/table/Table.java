/**
 * @ (#) Table.java 12th Jul 2005
 * Project      : 
 * File         : Table.java
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
import java.util.ArrayList;

/**
 * This contians set and get methods for the table properties.
 */
public abstract class Table implements Serializable
{
	public int iNoOfRows		        = 0;        // No.of.rows per page
	public int iCurrentPage             = 0;        // current page number
	public int iNoOfPageLinks           = 0;        // no.of.pagelinks to be displayed
	public int iPageLinks[]             = null;     // page links
	private ArrayList alDataObject      = null;     // grid data
	public boolean bPageNextLink        = false;    // is next link 
	public boolean bPagePreviousLink    = false;    // is previous link
	public String strTableTitle         = "";       // title of the table
	
	public ArrayList<Object> alTableHeader = new ArrayList<Object>(1);//table header details
	
	/**
	 * Sets the true/false for next link.
	 * 
	 * bPageNextLink boolean
	 * 
	 * @param bPageNextLink
	 */
	public void setIsPageNextLink(boolean bPageNextLink)
	{
		this.bPageNextLink = bPageNextLink;
	}//end of setIsPageNextLink
	
	/**
	 * Sets the page links.
	 * 
	 * iPageLinks int[] page links
	 * 
	 * @param iPageLinks
	 */
	public void setPageLinks(int[] iPageLinks)
	{
		this.iPageLinks = iPageLinks;
	}//end of setPageLinks
	
	/**
	 * Gets page links.
	 * 
	 * @return iPageLinks int[] page links
	 */
	public int[] getPageLinks()
	{
		return iPageLinks;
	}//end of getPageLinks
	
	/**
	 * Gets page next link.
	 * 
	 * @return bPageNextLink boolean next link ture/false
	 */
	public boolean isPageNextLink()
	{
		return this.bPageNextLink;
	}//end of isPageNextLink
	
	/**
	 * Sets the previous link.
	 * 
	 * bPagePreviousLink boolean previous link true/false
	 * 
	 * @param bPagePreviousLink
	 */
	public void setPagePreviousLink(boolean bPagePreviousLink)
	{
		this.bPagePreviousLink = bPagePreviousLink;
	}//end of setPagePreviousLink
	
	/**
	 * Gets the previous link.
	 * 
	 * bPagePreviousLink boolean previous link true/false
	 */
	public boolean getPagePreviousLink()
	{
		return this.bPagePreviousLink;
	}//end of getPagePreviousLink
	
	/**
	 * Sets the No.of.Page links per page.
	 * 
	 * @param iNoOfPageLinks int No.Of.Page links per page
	 */
	public void setPageLinkCount(int iNoOfPageLinks)
	{
		this.iNoOfPageLinks = iNoOfPageLinks;
	}//end of setPageLinkCount
	
	/**
	 * Gets the No.of.Page links per page.
	 * 
	 * @return iNoOfPageLinks int No.Of.Page links per page.
	 */
	public int getPageLinkCount()
	{
		return this.iNoOfPageLinks;
	}//end of getPageLinkCount
	
	/**
	 * Sets the No.of.Rows per page.
	 * 
	 * @param iNoOfRows int No.Of.Rows per page.
	 */
	public void setRowCount(int iNoOfRows)
	{
		this.iNoOfRows = iNoOfRows;
	}//end of setRowCount
	
	/**
	 * Gets the No.of.Rows per page.
	 * 
	 * @return iNoOfRows int No.Of.Rows per page.
	 */
	public int getRowCount()
	{
		return this.iNoOfRows;
	}//end of getRowCount
	
	/**
	 * Sets the current page number.
	 * 
	 * @param iCurrentPage int current page number.
	 */
	public void setCurrentPage(int iCurrentPage)
	{
		this.iCurrentPage = iCurrentPage;
	}//end of setCurrentPage
	
	/**
	 * Gets the current page number.
	 * 
	 * @return iCurrentPage int current page number.
	 */
	public int getCurrentPage()
	{
		return this.iCurrentPage;
	}//end of getCurrentPage
	
	/**
	 * Adds the column object to the table header list.
	 * 
	 * @param objColumnProperties Column Column Object .
	 */
	public void addColumn(Column objColumnProperties)
	{
		alTableHeader.add(objColumnProperties);
	}//end of addColumn
	
	/**
	 * Sets grid data.
	 * 
	 * @param alTitle ArrayList grid data
	 * @param alDataObject
	 */
	public void setDataObject(ArrayList alDataObject)
	{
		this.alDataObject = alDataObject;
	}//end of setDataObject
	
	/**
	 * Retreive the grid data.
	 * 
	 * @return alData  ArrayList grid data
	 */  
	public ArrayList getDataObject()
	{
		return this.alDataObject;
	}//end of getDataObject
	
	/**
	 * Sets the table header information.
	 * 
	 * @param alTitle ArrayList table header information
	 * @param alTableHeader
	 */
	public void setTableHeader(ArrayList<Object> alTableHeader)
	{
		this.alTableHeader = alTableHeader;
		
	}//end of setTableHeader
	
	/**
	 * Retreive the table header information.
	 * 
	 * @return alTitle  ArrayList table header information.
	 */ 
	public ArrayList getTableHeader()
	{
		return alTableHeader;
	}//end of getTableHeader
	
	/**
	 * Gets the table title.
	 * 
	 * @return strTableTitle String table title.
	 */
	public String getTableTitle()
	{
		return this.strTableTitle;
	}//end of getTableTitle
	
	/**
	 * Sets the table title.
	 * 
	 * @param strTableTitle String table title.
	 */
	public void setTableTitle(String strTableTitle)
	{
		this.strTableTitle = strTableTitle;
	}// end of setTableTitle
	
	/**
	 * This method creates the columnproperties objects for each and 
	 * every column and adds the column object to the table.
	 */
	public abstract void setTableProperties();
	
	
}//end of Table class

