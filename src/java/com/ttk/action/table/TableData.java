/**
 * @ (#) TableData.java 12th Jul 2005
 * Project      :
 * File         : TableData.java
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
import java.util.HashMap;
import java.util.Set;
import org.apache.log4j.Logger;
import com.ttk.common.TTKCommon;
import com.ttk.common.exception.TTKException;

/**
 * This is a session scope bean to hold all grid
 * related information.
 */
public class TableData implements Serializable
{
    private static Logger log = Logger.getLogger( TableData.class );
    private ArrayList alTitle  = null;
	private ArrayList alData = null;
	private ArrayList<Object> alSearchData = new ArrayList<Object>();
	private int iPageLinks[] = new int[0];
	private int iCurrentPage = 1;
	public Table obj1 = null;
	private String strSortData = "0000"; // first two chars indicate column number and last two indicate asc or desc order
	private String strSortColumnName = "";
	private String strSortOrder = "ASC";
	private int iSortColumn = 0;
	private int iSortOrder = 0;
	private HashMap<Object,Object> hmCheckbox = new HashMap<Object,Object>();
	private int iRowCount   =  0;
	private int iStartRowCount =  1; // Stores the Current Row Count to be fetched from DataBase
	private int iNoOfRowToFetch =  0; // Stores the No of Rows to be fetched from DataBase

	public boolean bPageNextLink        = false;    // is next link
	public boolean bPagePreviousLink    = false;    // is previous link



    //default constructor
	public TableData()
	{
	}//end of default constructor

	/**
	 * Sets the selected column number for sort.
	 *
	 * @param iSortColumn int column number
	 */
	public void setSortColumnNumber(int iSortColumn)
	{
		this.iSortColumn = iSortColumn;
	}//end of setSortColumnNumber(int iSortColumn)

	/**
	 * Returns the sort column.
	 *
	 * @return iSortColumn  int column number
	 */
	public int getSortColumnNumber()
	{
		return this.iSortColumn;
	}//end of getSortColumnNumber()

	/**
	 * Sets the selected sort order number.
	 *
	 * @param iSortOrder int sort order number
	 */
	public void setSortOrderNumber(int iSortOrder)
	{
		this.iSortOrder = iSortOrder;
	}//end of setSortOrderNumber(int iSortOrder)

	/**
	 * Returns the sort order number.
	 *
	 * @return iSortOrder  int order number
	 */
	public int getSortOrderNumber()
	{
		return this.iSortOrder;
	}//end of getSortOrderNumber()

	/**
	 * Sets the selected sort data.
	 *
	 * @param strSortData String sort data
	 */
	public void setSortData(String strSortData)
	{
		this.strSortData = strSortData;
		try
		{
			int iSortData = Integer.parseInt(strSortData);
			int iSortColumnNum  =  iSortData / 100;
			iSortColumnNum = iSortColumnNum <= 0 ? 0 : iSortColumnNum;
			String strOderTemp  =  iSortData % 2 == 0 ? "ASC" : "DESC";
			setSortOrder(strOderTemp);
			setSortOrderNumber(iSortData % 2);
			iSortColumnNum = iSortColumnNum <= 0 ? 0 : iSortColumnNum;
			setSortColumnNumber(iSortColumnNum);
			setSortColumnName(((Column)alTitle.get(iSortColumnNum)).getDBColumnName());

			//re-set the rowcount and current page information -- added for TTK
			this.setStartRowCount(1);
			this.setCurrentPage(1);
		}//end of try
		catch(Exception exp)
		{
			TTKCommon.logStackTrace(exp);
		}//end of catch(Exception exp)
	}//end of setSortData(String strSortData)

	/**
	 * Sets the selected sort order.
	 *
	 * @param strSortOrder String sort order
	 */
	public void setSortOrder(String strSortOrder)
	{
		this.strSortOrder = strSortOrder;
	}//end of setSortOrder(String strSortOrder)

	/**
	 * Returns the sort order ASC/DESC.
	 *
	 * @return strSortOrder  String sort order
	 */
	public String getSortOrder()
	{
		return this.strSortOrder;
	}//end of getSortOrder()

	/**
	 * Sets the selected sort column.
	 *
	 * @param strSortColumnName String sort column name
	 */
	public void setSortColumnName(String strSortColumnName)
	{
		this.strSortColumnName = strSortColumnName;
	}//end of setSortColumnName(String strSortColumnName)

	/**
	 * Returns the sort column name.
	 *
	 * @return strSortColumnName  String sort order column name
	 */
	public String getSortColumnName()
	{
		return this.strSortColumnName;
	}//end of getSortColumnName()

	/**
	 * Returns the sort data.
	 *
	 * @return strSortData  String sort data
	 */
	public String getSortData()
	{
		return this.strSortData;
	}//end of getSortData()

	/**
	 * Sets the table header information.
	 *
	 * @param alTitle ArrayList table header information
	 */
	public void setTitle(ArrayList alTitle)
	{
		this.alTitle = alTitle;
	}//end of setTitle(ArrayList alTitle)

    /**
     * Returns the table header information.
     *
     * @return alTitle  ArrayList table header information
     */
	public ArrayList getTitle()
	{
		return alTitle;
	}//end of getTitle()

    /**
     * Sets grid data.
     *
     * @param alData ArrayList grid data
     */
	public void setData(ArrayList alData)
	{
		this.alData = alData;
	}//end of setData(ArrayList alData)

    /**
     * Returns the grid data.
     *
     * @return alData  ArrayList grid data
     */
	public ArrayList getData()
	{
		return alData;
	}//end of getData()

    /**
     * creates the grid info.
     *
     * @param sOption String identifier
     * @param alObject ArrayList grid data
     */
	public void createTableInfo(String sOption,ArrayList alObject)
	{
		setData(alObject);
        setSearchData(new ArrayList<Object>());
        obj1 = TableObjectFactory.getTableObject(sOption);
        obj1.setTableProperties();
        setTitle(obj1.getTableHeader());
        iCurrentPage   = obj1.getCurrentPage();
        setStartRowCount(1);
        setSortData("0000");
        hmCheckbox = new HashMap<Object,Object>();
        createLinks();
	}//end of createTableInfo(String sOption,ArrayList alObject)

    /**
     * This method generates the page links based on available rowcount and data.
     */
    public void createLinks()
    {
        try
        {

            int iTotalRows = 0;
            int iRowCount  = getRowCount();
            iRowCount = iRowCount < 0 ? getData().size() : iRowCount;
            int iPageLinksGoingToBeDisplayed = 1;
            if(iRowCount==0)
            {
               iRowCount=1;
            }//end of if(iRowCount==0)
            if(getData()!=null)
            {
                iTotalRows = getData().size();

                if(obj1.getCurrentPage() * iRowCount > iTotalRows && iTotalRows <= ( (obj1.getCurrentPage()-1) * iRowCount))
                {
                    obj1.setCurrentPage(obj1.getCurrentPage()-1 <= 0 ? 1 : obj1.getCurrentPage()-1);
				}//end of if(obj1.getCurrentPage() * iRowCount > iTotalRows && iTotalRows <= ( (obj1.getCurrentPage()-1) * iRowCount))
            }//end of if(getData()!=null)

			iPageLinksGoingToBeDisplayed = (iTotalRows / iRowCount)+(iTotalRows % iRowCount > 0 ? 1 : 0);
            int[] iLinks = new int[iPageLinksGoingToBeDisplayed];

            //storing the links into an int array
            for(int i=1;i<=iPageLinksGoingToBeDisplayed;i++)
            {
                iLinks[i-1] = i;
            }//end of for(int i=1;i<=iPageLinksGoingToBeDisplayed;i++)
            iPageLinks = iLinks;
        }//end of try block
        catch(Exception exp)
        {
            log.debug("error in createlinks method");
            iPageLinks = new int[0];
            TTKCommon.logStackTrace(exp);
        }//end of catch
    }//end of createLinks()

    /**
     * Sets page links.
     *
     * @param iPageLinks int[] page links
     */
    public void setLinks(int[] iPageLinks)
    {
        this.iPageLinks = iPageLinks;
    }//end of setLinks(int[] iPageLinks)

    /**
     * Gets page links.
     *
     * @return iPageLinks int[] page links
     */
    public int[] getLinks()
    {
        createLinks();
        //checking whether iPageLinks array is null or not
        if(iPageLinks==null)
        {
            iPageLinks = new int[1];
            iPageLinks[0] = 1;
        }//end of if(iPageLinks==null)
        return iPageLinks;
    }//end of getLinks()


    /**
     * Sets current page number.
     *
     * @param iCurrentPage int current page number
     */
	public void setCurrentPage(int iCurrentPage)
	{
	    //checking whether table object is null or not
	    if(obj1!=null)
	    {
		    obj1.setCurrentPage(iCurrentPage);
		}//end of if(obj1!=null)
		this.iCurrentPage = iCurrentPage;
	}//end of setCurrentPage(int iCurrentPage)

    /**
     * Gets current page number.
     *
     * @return iCurrentPage int current page number
     */
	public int getCurrentPage()
	{
		try
		{
			iCurrentPage = obj1.getCurrentPage();
		}//end of try
		catch(Exception exp)
		{
		}//end of catch
		return iCurrentPage;
	}//end of getCurrentPage()

	/**
	 * This method store the selected checkbox's information.
	 *
	 * @param iPageNumber int page number
	 * @param strSelectedCheckBox String[]  String array of selected check box values
	 */
	public void setSelectedCheckBoxInfo(int iPageNumber,String[] strSelectedCheckBox)
	{
		String[] strCheckBox = {};
		if(strSelectedCheckBox!=null)
		{
			strCheckBox = strSelectedCheckBox;
		}//end of if(strSelectedCheckBox!=null)
		hmCheckbox.put(String.valueOf(iPageNumber),strCheckBox);
	}//end of setSelectedCheckBoxInfo(int iPageNumber,String[] strSelectedCheckBox)

	/**
	 * This method all the selected checkbox's information.
	 *
	 * @param iPageNumber
	 * @return strSelectedCheckBox    String[]  String array of selected check box values
	 */
	public String[] getSelectedCheckBoxInfo(int iPageNumber)
	{
	    return (String[])hmCheckbox.get(String.valueOf(iPageNumber));
	}//end of getSelectedCheckBoxInfo(int iPageNumber)

	/**
	 * This method returns all selected checkbox's information based on the provided page number.
	 *
	 * @param iPageNumber int page number
	 * @return strSelectedCheckBox    String[]  String array of selected check box values
	 */
	public String[] getAllSelectedCheckBoxInfo()
	{
	    String[] strAllCheckBoxInfo = {};
	    ArrayList<Object> alSelectedCheckBox = new ArrayList<Object>();
	    Set setPgNum = hmCheckbox.keySet();
        Object[] strPgNum = setPgNum.toArray();
        String[] strChk = null;
        //looping through hashmap keys to merge all the values in the hashmap.
        for( int i = 0; i < strPgNum.length; i++)
        {
            strChk = (String[])hmCheckbox.get(strPgNum[i].toString());
            //looping through all the selected checkbox values
            for( int j = 0 ; j < strChk.length ; j++ )
            {
                alSelectedCheckBox.add(strChk[j]);
            }//end of for( int j = 0 ; j < strChk.length ; j++ )
        }//end of for( int i = 0; i < strPgNum.length; i++)
	    return ((String[])alSelectedCheckBox.toArray(strAllCheckBoxInfo));
	}//end of getAllSelectedCheckBoxInfo()

    /**
     * This method gets the value by calling appropriate method defined in the value object using reflection.
     *
     * @param obj Object  value object
     * @param strProperty String  name of the method
     * @return value         String
     * @exception TTKException
     */
   	public String setObjectProperty(Object obj, String strProperty)
	throws TTKException
	{
	    try
	    {
//		    String[] s = null;
		    Class[] paramTypes = null;
		    StringBuffer sPropertyName = new StringBuffer("");
			sPropertyName = (StringBuffer)sPropertyName.append(strProperty);
		    java.lang.reflect.Method method = obj.getClass().getMethod(sPropertyName.toString(),paramTypes);
		    return String.valueOf(method.invoke(obj,(Object[])paramTypes));
		}//end of try
		catch(Exception exp)
		{
			throw new TTKException(exp, "error.table");
		}//end of catch
	}//end of setObjectProperty(Object obj, String strProperty)

	/**
     * This method gets the value by calling appropriate method defined in the value object using reflection.
     *
     * @param obj Object  value object
     * @param strProperty String  name of the method
     * @return value         String
     * @exception TTKException
     */
   	public String setObjectProperty(Object obj, String strProperty,String strMethodSize)
	throws TTKException
	{
	    try
	    {
		    String strValue = "";
		    String strFinalString = "";
//		    String[] s = null;
		    Class[] paramTypes = null;
		    StringBuffer sPropertyName = new StringBuffer("");
		    sPropertyName = (StringBuffer)sPropertyName.append(strProperty);
		    java.lang.reflect.Method method = null;
		    method = obj.getClass().getMethod(sPropertyName.toString(),paramTypes);
		    strValue =  String.valueOf(method.invoke(obj,(Object[])paramTypes));
		    strFinalString = strValue;
		    if(!strMethodSize.equals(""))
		    {
		            int iSize = strValue.length();
		            int iOPStringSize = iSize;
		            int iReqSize = (new Integer(strMethodSize)).intValue();
		            strFinalString = "";
		            int iCount = 1;
		            int iStart = 0;
		            while((iSize+iReqSize)>iReqSize)
		            {
		                int iFinal = iCount * iReqSize;
		                if(iFinal>iOPStringSize)
		                {
		                    iFinal = iOPStringSize;
		                }//end of if(iFinal>iOPStringSize)
		                if(iCount!=1)
		                {
		                    strFinalString = strFinalString + "-<BR>";
		                }//end of if(iCount!=1) 
		                strFinalString = strFinalString + strValue.substring(iStart,iFinal);
		                iCount++;
		                iStart = iFinal;
		                iSize = iSize - iFinal;
		            }//end of if(iSize>iReqSize)
		    }//end of if(!strSize.equals(""))
		    return strFinalString;
		}//end of try
		catch(Exception exp)
		{
			throw new TTKException(exp, "error.table");
		}//end of catch
	}//end of setObjectProperty(Object obj, String strProperty)


    /**
     * Returns link URL.
     * This method will concatinate the param names and values to url.
     *
     * @param strLink String
     * @param strParam String param name
     * @param strParamValue String param value
     * @return url String
     */
	public String getLinkURL(String strLink,String strParam,String strParamValue)
	{
	    if(strParam.equals(":"))
	    {
	        return strLink+strParam+strParamValue;
	    }//end of if(strParam.equals(":"))
	    else
	    {
		    return strLink+strParam+"="+strParamValue;
	    }//end of else
	}//end of getLinkURL(String strLink,String strParam,String strParamValue)

    /**
     * Returns requested value object based on provided row number
     *
     * @param iRowNumer int row number
     * @return Object Returns the appropriate value object based on the specified row number
     */
	public Object getRowInfo(int iRowNumer)
	{
		return getData().get(iRowNumer);
	}//end of getRowInfo(int iRowNumer)

	/**
	 * Returns search data.
	 *
	 * @return alSearchData ArrayList Search data
	 */
	public ArrayList<Object> getSearchData()
	{
		return this.alSearchData;
	}//end of getSearchData()

    /**
     * Sets search data.
     *
     * @param alSearchData ArrayList Search data
     */
	public void setSearchData(ArrayList<Object> alSearchData)
	{
		this.alSearchData = alSearchData;
	}//end of setSearchData(ArrayList alSearchData)

	/**
     * Sets row count data.
     *
     * @param iRowCount int row count
     */
	public void setRowCount(int iRowCount)
	{
	  this.iRowCount = iRowCount;
	  if(obj1!=null)
	  {
	    obj1.setRowCount(iRowCount);
	  }//end of if(obj1!=null)

	}//end of  setRowCount(int iRowCount)

	/**
     * Returns row count data.
     *
     * @return int row count
     */
	public int getRowCount()
	{
	    if(obj1!=null)
	    {
	       iRowCount= obj1.getRowCount();
	    }//end of if(obj1!=null)
	    return iRowCount;
	}//end of getRowCount()

	//--
	/**
     * Sets Start row count .
     *
     * @param iStartRowCount int row count
     */
	public void setStartRowCount(int iStartRowCount)
	{
	  this.iStartRowCount = iStartRowCount;
	}//end of  setStartRowCount(int iStartRowCount)

	/**
     * Retunrs Start row count .
     *
     * @return int row count
     */
	public int getStartRowCount()
	{
	    return iStartRowCount ;
	}//end of getStartRowCount()



	/**
     * Retunrs No of Rows To Fetch from DB.
     *
     * @return int row count
     */
	public int getNoOfRowToFetch()
	{
	    iNoOfRowToFetch = 0;
	    if(obj1!=null)
	    {
	       iNoOfRowToFetch = obj1.getRowCount() * obj1.getPageLinkCount();
	    }//end of if(obj1!=null)
	    return iNoOfRowToFetch;
	}//end of getNoOfRowToFetch()


	/**
     * Retunrs Next row count data This is should be called from Forward and Backward button
     * during .
     *
     * @return int row count
     */
	public int getNextRowCount()
	{
	    return iStartRowCount + iNoOfRowToFetch + iNoOfRowToFetch ;
	}//end of getNextRowCount()

	/**
     * Retunrs Next row count This should be called from other than Forward
     * and backward button.
     *
     * @return int row count
     */
	public int getCurrentNextRowCount()
	{
		return iStartRowCount + getNoOfRowToFetch() ;
	}//end of getCurrentNextRowCount()

	/**
     * Returns Previous row count data. This should be called when Backward
     * button is pressed.
     *
     * @return int row count
     */
	public int getPreviousRowCount()
	{
	    return iStartRowCount - iNoOfRowToFetch;
	}//end of getPreviousRowCount()

	/**
	 * Sets the true/false for next link.
	 * This should be called when Forward button is pressed.
	 *
	 * bPageNextLink boolean
	 *
	 * @param bPageNextLink
	 */
	public void setPageNextLink(boolean bPageNextLink)
	{
		this.bPageNextLink = bPageNextLink;
	}//end of setPageNextLink(boolean bPageNextLink)

	/**
	 * Gets page next link.
	 *
	 * @return bPageNextLink boolean next link ture/false
	 */
	public boolean isPageNextLink()
	{
		return this.bPageNextLink;
	}//end of isPageNextLink()

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
	}//end of setPagePreviousLink(boolean bPagePreviousLink)

	/**
	 * Gets the previous link.
	 *
	 * bPagePreviousLink boolean previous link true/false
	 */
	public boolean getPagePreviousLink()
	{
		return this.bPagePreviousLink;
	}//end of getPagePreviousLink()

	/**
	 * Sets the Next link.
	 * This should be called when Backward button is pressed.
	 *
	 * bPageNextLink sets to TRUE if we need to display '>>'
	 *
	 * @param
	 */
	public void setBackWardNextLink()
	{
		if(obj1!=null)
	    {
	        if(alData.size()>getNoOfRowToFetch())
	        {
			    alData.remove(alData.size()-1);
			    setPageNextLink(true);
			}//end of if(alData.size()>getNoOfRowToFetch())
		    else
		    {
			    setPageNextLink(false);
		    }//end of else
	    }//end of if(obj1!=null)
	}//end of setBackWardNextLink()

	/**
	 * Sets the previous link.
	 * This should be called when BackWard button is pressed.
	 *
	 * bPagePreviousLink sets to TRUE if we need to display '<<'
	 *
	 * @param
	 */
	public void setBackWardPreviousLink()
	{
		if(obj1!=null)
		{
			if(getStartRowCount() <= getNoOfRowToFetch())
			{
				setPagePreviousLink(false);
			}//end of if(getStartRowCount() <= getNoOfRowToFetch())
			else
			{
				setPagePreviousLink(true);
			}//end of else
		}//end of if(obj1!=null)
	}//end of setBackWardPreviousLink()

	/**
	 * Sets the Next link.
	 * This should be called when Forward button is pressed.
	 *
	 * bPageNextLink sets to TRUE if we need to display '>>'
	 *
	 * @param bPagePreviousLink
	 */
	public void setForwardNextLink()
	{
		if(obj1!=null)
	    {
	        if(alData != null && alData.size()>getNoOfRowToFetch())
		    {
				alData.remove(alData.size()-1);
				setPageNextLink(true);
			}//end of if(alData != null && alData.size()>getNoOfRowToFetch())
			else
			{
				setPageNextLink(false);
			}//end of else
	    }//end of if(obj1!=null)
	}//end of setForwardNextLink()

	/**
	 * Sets the previous link.
	 * This should be called when Forward button is pressed.
	 *
	 * bPagePreviousLink sets to TRUE if we need to display '<<'
	 *
	 * @param bPagePreviousLink
	 */
	public void setForwardPreviousLink()
	{
		if(obj1!=null)
		{
			if(getStartRowCount() != 1)
			{
				setPagePreviousLink(true);
			}//end of if(getStartRowCount() != 1)
			else
			{
				setPagePreviousLink(false);
			}//end of else
		}//end of if(obj1!=null)
	}//end of setForwardPreviousLink()

	/**
	 * This is used to Increment the Start Row Count during forward traversal
	 *
	 * @param
	 */
	public void setForwardStartRowCount()
	{
		if(obj1!=null)
	    {
	        setStartRowCount(iStartRowCount + getNoOfRowToFetch());
	    }//end of if(obj1!=null)
	}//end of setForwardStartRowCount()

	/**
	 * This is used to decrement the Start Row Count during backward traversal
	 *
	 * @param
	 */
	public void setBackWardStartRowCount()
	{
		if(obj1!=null)
	    {
	        setStartRowCount(iStartRowCount - getNoOfRowToFetch());
	    }//end of if(obj1!=null)
	}//end of setBackWardStartRowCount()

	/**
	 * This returns the No of pages to be displayed
	 *
	 * @param
	 */
	public int getPageLinkCount()
	{
	    int iNoOfPages = 0;
	    if(obj1!=null)
	    {
	       iNoOfPages = obj1.getPageLinkCount();
	    }//end of if(obj1!=null)
	    return iNoOfPages;
	}//end of getPageLinkCount()

	/**
	 * This is used to set the no of pages to be displayed -- added for TTK
	 *
	 * @param iNoOfPages int the number of pages to be displayed
	 */
	public void setPageLinkCount(int iNoOfPages)
	{
		if(obj1!=null)
		{
       		obj1.setPageLinkCount(iNoOfPages);
		}//end of if(obj1!=null)
	}//end of setPageLinkCount(int iNoOfPages)

    //Method added to reduce the code in action classes
    public void modifySearchData(String strAction)
    {
        if(strAction.equals("DeleteList") || strAction.equals("Delete"))
        {
            int iEndRowCnt = (Integer.parseInt((String)this.getSearchData().get(this.getSearchData().size()-2)));
            int iStartRowCount = (Integer.parseInt((String)this.getSearchData().get(this.getSearchData().size()-2)) - (this.getRowCount() * this.getPageLinkCount()));
            if(iStartRowCount<0)
            {
                iStartRowCount=1;
            }//end of if(iStartRowCount<0)
            this.getSearchData().set(this.getSearchData().size()-2, String.valueOf(iStartRowCount));
            this.getSearchData().set(this.getSearchData().size()-1, String.valueOf(iEndRowCnt));
            this.setStartRowCount(iStartRowCount);
            this.setBackWardNextLink();
            this.setBackWardPreviousLink();
        }//end of if(strAction.equals("DeleteList") || strAction.equals("Delete"))
        else if(strAction.equals("sort"))
        {
            this.getSearchData().set(this.getSearchData().size()-4, this.getSortColumnName());
            this.getSearchData().set(this.getSearchData().size()-3, this.getSortOrder());
            this.getSearchData().set(this.getSearchData().size()-2, String.valueOf(this.getStartRowCount()));
            this.getSearchData().set(this.getSearchData().size()-1, String.valueOf(this.getCurrentNextRowCount()));
        }//end of else if(strAction.equals("sort"))
        else if(strAction.equals("search"))
        {
            this.getSearchData().add(this.getSortColumnName());
            this.getSearchData().add(this.getSortOrder());
            this.getSearchData().add(String.valueOf(this.getStartRowCount()));
            this.getSearchData().add(String.valueOf(this.getCurrentNextRowCount()));
        }//end of else if(strAction.equals("search"))
        //kocbroker
        else if(strAction.equals("Policies"))
        {
            this.getSearchData().add(this.getSortColumnName());
            this.getSearchData().add(this.getSortOrder());
            this.getSearchData().add(String.valueOf(this.getStartRowCount()));
            this.getSearchData().add(String.valueOf(this.getCurrentNextRowCount()));
        }//end of else if(strAction.equals("Policies"))
        //homebroker
        else if(strAction.equals("Home"))
        {
            this.getSearchData().add(this.getSortColumnName());
            this.getSearchData().add(this.getSortOrder());
            this.getSearchData().add(String.valueOf(this.getStartRowCount()));
            this.getSearchData().add(String.valueOf(this.getCurrentNextRowCount()));
        }//end of else if(strAction.equals("Home"))
        else if(strAction.equals("Forward"))
        {
            //set the new row numbers
            this.getSearchData().set(this.getSearchData().size()-2, String.valueOf(this.getStartRowCount() + this.getNoOfRowToFetch()));
            this.getSearchData().set(this.getSearchData().size()-1, String.valueOf(this.getNextRowCount()));
        }//end of else if(strAction.equals("Forward"))
        else if(strAction.equals("Backward"))
        {
            //set the new row numbers
            this.getSearchData().set(this.getSearchData().size()-2, String.valueOf(this.getPreviousRowCount()));
            this.getSearchData().set(this.getSearchData().size()-1, String.valueOf(this.getStartRowCount()));
        }//end of else if(strAction.equals("Backward"))

    }//end of modifySearchData(String strAction)

    /**
     * Sets grid data.
     *
     * @param alData ArrayList grid data
     */
    public void setData(ArrayList alData, String strAction)
    {
        this.alData = alData;
        if(strAction.equals("search"))
        {
            this.setPagePreviousLink(false);
            this.setForwardNextLink();
        }//end of if(strAction.equals("search"))
        else if(strAction.equals("Forward"))
        {
            //set the links
            this.setForwardStartRowCount();
            this.setForwardNextLink();
            this.setForwardPreviousLink();
            this.setCurrentPage(1);
        }//end of else if(strAction.equals("Forward"))
        else if(strAction.equals("Backward"))
        {
            //set the links
            this.setBackWardStartRowCount();
            this.setBackWardNextLink();
            this.setBackWardPreviousLink();
            this.setCurrentPage(1);
        }//end of else if(strAction.equals("Backward"))
        else if(strAction.equals("DeleteList") || strAction.equals("Delete") || strAction.equalsIgnoreCase("close") || strAction.equalsIgnoreCase("cancel"))
        {
            this.setForwardNextLink();
        }//end of else if(strAction.equals("DeleteList") || strAction.equals("Delete") || strAction.equalsIgnoreCase("close") || strAction.equalsIgnoreCase("cancel"))

    }//end of setData(ArrayList alData)

    public int getStartRow()
    {
        return (Integer.parseInt((String)this.getSearchData().get(-2)) - (this.getRowCount() * this.getPageLinkCount()));
    }//end of getStartRow()
}//end of class TableData

