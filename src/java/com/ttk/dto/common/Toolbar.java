/**
 * @ (#) Toolbar.java Oct 10, 2005
 * Project      :
 * File         : Toolbar.java
 * Author       : Nagaraj D V
 * Company      :
 * Date Created : Oct 10, 2005
 *
 * @author       : Nagaraj D V
 * Modified by   :
 * Modified date :
 * Reason        :
 */
package com.ttk.dto.common;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;

//import org.apache.log4j.Logger;

import com.ttk.common.TTKPropertiesReader;

public class Toolbar implements Serializable{
//    private static Logger log = Logger.getLogger( Toolbar.class );
    IconObject conflictIcon = new IconObject();
    IconObject docViewIcon = new IconObject();
    HashMap<Object, Object> hm = null;
    WebBoard webBoard = null;
    ArrayList<String> alConflictVisibility = null;
    ArrayList<String> alDocViewVisibility = null;
    ArrayList<String> alWebBoardVisibility = null;
    private ArrayList<String> alDocViewParams=null;
    private String strUrl = "";
    private String strHost = "";

    /**
     * @return Returns the alDocViewParams.
     */
    public ArrayList<String> getDocViewParams()
    {
        return alDocViewParams;
    }//end of ArrayList<String> getDocViewParams()

    /**
     * @param alDocViewParams The alDocViewParams to set.
     */
    public void setDocViewParams(ArrayList<String> alDocViewParams)
    {
        this.alDocViewParams = alDocViewParams;
    }//end of setDocViewParams(ArrayList<String> alDocViewParams)

    /**
     * @return Returns the strUrl.
     */
    public String getUrl()
    {
        return strUrl;
    }//end of getUrl()
    /**
     * @param strUrl The strUrl to set.
     */
    public void setUrl(String strUrl)
    {
        this.strUrl = strUrl;
    }//end of setUrl(String strUrl)

    /**
     * @return Returns the strHost.
     */
    public String getHost()
    {
        return strHost;
    }//end of getHost()
    /**
     * @param strHost The strHost to set.
     */
    public void setHost(String strHost)
    {
        this.strHost = strHost;
    }//end of setHost(String strHost)

    public String getQueryString()
    {
        String strQueryString="";
        if(alDocViewParams!=null && alDocViewParams.size()>0)
        {
            for(int i=0;i<alDocViewParams.size();i++)
            {
                if(i<alDocViewParams.size()-1)
                {
                    strQueryString=strQueryString+alDocViewParams.get(i)+"&amp;";
                }//end of if(i<alDocViewParams.size()-1)
                else
                {
                    strQueryString=strQueryString+alDocViewParams.get(i);
                }//end of else
            }//end of for(int i=0;i<alDocViewParams.size();i++)
        }//end of if(alDocViewParams!=null && alDocViewParams.size()>0)
        return strQueryString;
    }//end of getQueryString()
    public Toolbar()
    {

    }
    public Toolbar(IconObject conflictIcon, IconObject docViewIcon, String strLink)
    {
        this.conflictIcon = conflictIcon;
        this.docViewIcon  = docViewIcon;
        this.updateVisibility(strLink);
    }
    /**
     * @return Returns the conflictIcon object.
     */
    public IconObject getConflictIcon()
    {
        return this.conflictIcon;
    }//end of getConflictIcon()

    /**
     * @return Returns the docViewIcon object.
     */
    public IconObject getDocViewIcon()
    {
        return this.docViewIcon;
    }//end of getDocViewIcon()

    /**
     * @return Returns the webBoard object.
     */
    public WebBoard getWebBoard()
    {
        return this.webBoard;
    }//end of getDocViewIcon()

    /**
     * updates the visibility links
     */
    public void setLinks(String strLink)
    {
        this.updateVisibility(strLink);
    }//end of setLinks(String strLink)

    /**
     * @return Returns the webBoard object based on the appropriate identifier
     */
    public WebBoard currentWebBoard(String strId)
    {
        if(hm == null)
            this.loadWebBoard();
        webBoard = (WebBoard)hm.get(strId);

        if(webBoard == null)
            webBoard = new WebBoard();

        return webBoard;
    }//end of currentWebBoard(String strId)

    /**
     * loads the hash map object with web board objects
     */
    private void loadWebBoard()
    {
        /*
        ArrayList alCacheObjects = new ArrayList();
        CacheObject cacheObject = new CacheObject();
        cacheObject.setCacheDesc("User Management.Users");
        alCacheObjects.add(cacheObject);
        WebBoard webBoard = new WebBoard();
        webBoard.setWebboardList(alCacheObjects);
        */
        hm = new HashMap<Object, Object>();
        hm.put("User Management.Users", new WebBoard());
        hm.put("User Management.Roles and Previleges", new WebBoard());
        hm.put("Empanelment.Insurance", new WebBoard());
        hm.put("Empanelment.Broker", new WebBoard());
        hm.put("Empanelment.Hospital", new WebBoard());
        hm.put("Empanelment.Partner", new WebBoard());
        hm.put("Administration.Tariff Plans", new WebBoard());
        hm.put("Administration.Products", new WebBoard());
        hm.put("Administration.Policies", new WebBoard());
        hm.put("Administration.MOU", new WebBoard());
        hm.put("Enrollment.Individual Policy.ENM",new WebBoard());
        hm.put("Enrollment.Individual Policy.END",new WebBoard());
        hm.put("Enrollment.Ind. Policy as Group.ENM",new WebBoard());
        hm.put("Enrollment.Ind. Policy as Group.END",new WebBoard());
        hm.put("Enrollment.Corporate Policy.ENM",new WebBoard());
        hm.put("Enrollment.Corporate Policy.END",new WebBoard());
        hm.put("Enrollment.Non-Corporate Policy.ENM",new WebBoard());
        hm.put("Enrollment.Non-Corporate Policy.END",new WebBoard());

        hm.put("Enrollment.Individual Policy.Members.ENM",new WebBoard());
        hm.put("Enrollment.Individual Policy.Members.END",new WebBoard());
        hm.put("Enrollment.Ind. Policy as Group.Members.ENM",new WebBoard());
        hm.put("Enrollment.Ind. Policy as Group.Members.END",new WebBoard());
        hm.put("Enrollment.Corporate Policy.Members.ENM",new WebBoard());
        hm.put("Enrollment.Corporate Policy.Members.END",new WebBoard());
        hm.put("Enrollment.Non-Corporate Policy.Members.ENM",new WebBoard());
        hm.put("Enrollment.Non-Corporate Policy.Members.END",new WebBoard());

        hm.put("Pre-Authorization.Processing", new WebBoard());
        hm.put("Pre-Authorization.Enhancement.Search", new WebBoard());
        hm.put("Pre-Authorization.Shortfalls",new WebBoard());
        hm.put("Pre-Authorization.Referral Letter", new WebBoard());
        hm.put("Claims.Processing", new WebBoard());
        hm.put("Claims.BatchEntry",new WebBoard());
		//added for CR - KOC-Decoupling
        hm.put("DataEntryClaims.Processing",new WebBoard());
        hm.put("Finance.Bank Account", new WebBoard());
        hm.put("Finance.Float Account", new WebBoard());

        hm.put("Account Info.Enrollment", new WebBoard());
        hm.put("Coding.PreAuth",new WebBoard());
        hm.put("Coding.Claims",new WebBoard());
        hm.put("Code CleanUp.PreAuth",new WebBoard());
        hm.put("Code CleanUp.Claims",new WebBoard());
		//added for CR KOC-Decoupling
        hm.put("DataEntryCoding.Claims",new WebBoard());
        /*
		 *End koc 1103
		 * End eft
		 */	
       //hm.put("Finance.Cust. Bank Details.Search",  new WebBoard());
        //hm.put("Finance.Cust. Bank Details", new WebBoard());
       // hm.put("Finance.Cust. Bank Details.POLC", new WebBoard());
        hm.put("Finance.Cust. Bank Details.POLCMem", new WebBoard());
        hm.put("Finance.Cust. Bank Details.POLCPolc", new WebBoard());
        hm.put("Finance.Cust. Bank Details.CLAMMem", new WebBoard());
        hm.put("Finance.Cust. Bank Details.CLAMPolc", new WebBoard());
        //hm.put("Finance.Cust. Bank Details.CLAM", new WebBoard());
        hm.put("Finance.Cust. Bank Details.HOSP", new WebBoard());
		//koc 11 koc 11
        hm.put("Support.Investigation.PreAuth", new WebBoard());
        hm.put("Support.Investigation.Claim", new WebBoard());
		//  hm.put("Support.Investigation", new WebBoard());

        //added as per Hospital login        
        hm.put("Hospital Information.Cashless Status",new WebBoard());
        //alWebBoardVisibility.add("Hospital Information.Pre-Auth.General"); 
        hm.put("Hospital Information.Claims Status",new WebBoard());
        hm.put("Hospital Information.Cashless",new WebBoard());
        

        hm.put("Administration.Tariff", new WebBoard());
        
        //bikki
        hm.put("Software Insurance Pricing.Profile creation", new WebBoard());
        
      //added as per Hospital login
       // alWebBoardVisibility.add("Hospital Information.Claims.General");

        
        /*
		 * This code is added for cr koc 1103
		 * added eft
		 */
    }//end of loadWebBoard()

    /**
     * loads the arraylist object's with the links
     */
    private void loadVisibilityInfo()
    {
        //construct the links where conflict icon's has to be displayed
        alConflictVisibility = new ArrayList<String>();
        alConflictVisibility.add("User Management.Users");
        alConflictVisibility.add("Empanelment.Hospital.Contacts");
        alConflictVisibility.add("Empanelment.Partner.Contacts");
        /*alConflictVisibility.add("Empanelment.Insurance.Search");
        alConflictVisibility.add("Empanelment.Insurance.Company Details");
        alConflictVisibility.add("Empanelment.Hospital.General");
        alConflictVisibility.add("Empanelment.Hospital.MOU");
        alConflictVisibility.add("Empanelment.Hospital.Contacts");
        alConflictVisibility.add("Empanelment.Hospital.Accounts");
        alConflictVisibility.add("Empanelment.Hospital.Grading");
        alConflictVisibility.add("Empanelment.Hospital.Tariff");
        alConflictVisibility.add("Empanelment.Hospital.Validation");
        alConflictVisibility.add("Empanelment.Hospital.Status");
        alConflictVisibility.add("Empanelment.Hospital.Feedback");
        alConflictVisibility.add("Empanelment.Hospital.Log");
*/      alConflictVisibility.add("Pre-Authorization.Processing.System Preauth Approval");
        alConflictVisibility.add("Pre-Authorization.Processing.Authorization");
        alConflictVisibility.add("Claims.Processing.General");
        alConflictVisibility.add("Claims.Processing.Settlement");

//      construct the links where document viewer icon's has to be displayed
        alDocViewVisibility = new ArrayList<String>();
        alDocViewVisibility.add("Administration.Products.General");
        alDocViewVisibility.add("Administration.Products.Product Rules");
        alDocViewVisibility.add("Administration.Policies.General");
        alDocViewVisibility.add("Administration.Policies.Policy Rules");
        alDocViewVisibility.add("Administration.Policies.Buffer");
        alDocViewVisibility.add("Administration.Products.Associate Product");
        alDocViewVisibility.add("User Management.Roles and Previleges");
        alDocViewVisibility.add("Empanelment.Hospital.General");
        alDocViewVisibility.add("Empanelment.Hospital.MOU");
        alDocViewVisibility.add("Empanelment.Hospital.Grading");
        alDocViewVisibility.add("Empanelment.Hospital.Tariff");
        alDocViewVisibility.add("Empanelment.Hospital.Validation");
        alDocViewVisibility.add("Empanelment.Hospital.Feedback");
        alDocViewVisibility.add("Empanelment.Partner.General");
        alDocViewVisibility.add("Empanelment.Partner.MOU");
        alDocViewVisibility.add("Empanelment.Partner.Grading");
        alDocViewVisibility.add("Empanelment.Partner.Tariff");
        alDocViewVisibility.add("Empanelment.Partner.Validation");
        alDocViewVisibility.add("Empanelment.Partner.Feedback");
        alDocViewVisibility.add("Inward Entry.Enrollment.Batch Policies");

        alDocViewVisibility.add("Enrollment.Individual Policy.END.Endorsement");
        alDocViewVisibility.add("Enrollment.Ind. Policy as Group.END.Endorsement");
        alDocViewVisibility.add("Enrollment.Corporate Policy.END.Endorsement");
        alDocViewVisibility.add("Enrollment.Non-Corporate Policy.END.Endorsement");

        alDocViewVisibility.add("Enrollment.Individual Policy.ENM.Policy Details");
        alDocViewVisibility.add("Enrollment.Individual Policy.END.Policy Details");
        alDocViewVisibility.add("Enrollment.Individual Policy.ENM.Members");
        alDocViewVisibility.add("Enrollment.Individual Policy.END.Members");
        alDocViewVisibility.add("Enrollment.Individual Policy.ENM.Alert");
        alDocViewVisibility.add("Enrollment.Ind. Policy as Group.ENM.Policy Details");
        alDocViewVisibility.add("Enrollment.Ind. Policy as Group.END.Policy Details");
        alDocViewVisibility.add("Enrollment.Ind. Policy as Group.ENM.Members");
        alDocViewVisibility.add("Enrollment.Ind. Policy as Group.END.Members");
        alDocViewVisibility.add("Enrollment.Ind. Policy as Group.ENM.Alert");
        alDocViewVisibility.add("Enrollment.Corporate Policy.ENM.Policy Details");
        alDocViewVisibility.add("Enrollment.Corporate Policy.END.Policy Details");
        alDocViewVisibility.add("Enrollment.Corporate Policy.ENM.Members");
        alDocViewVisibility.add("Enrollment.Corporate Policy.END.Members");
        alDocViewVisibility.add("Enrollment.Corporate Policy.ENM.Alert");
        alDocViewVisibility.add("Enrollment.Non-Corporate Policy.ENM.Policy Details");
        alDocViewVisibility.add("Enrollment.Non-Corporate Policy.END.Policy Details");
        alDocViewVisibility.add("Enrollment.Non-Corporate Policy.ENM.Members");
        alDocViewVisibility.add("Enrollment.Non-Corporate Policy.END.Members");

        alDocViewVisibility.add("Enrollment.Corporate Policy.ENM.Rule Data");
        alDocViewVisibility.add("Enrollment.Corporate Policy.END.Rule Data");
        alDocViewVisibility.add("Enrollment.Individual Policy.ENM.Rule Data");
        alDocViewVisibility.add("Enrollment.Individual Policy.END.Rule Data");

        alDocViewVisibility.add("Pre-Authorization.Processing.System Preauth Approval");
        alDocViewVisibility.add("Pre-Authorization.Processing.Medical");
        alDocViewVisibility.add("Pre-Authorization.Processing.Tariff");
        alDocViewVisibility.add("Pre-Authorization.Processing.Authorization");
        alDocViewVisibility.add("Pre-Authorization.Processing.History");
        alDocViewVisibility.add("Pre-Authorization.Processing.Support Doc");
        alDocViewVisibility.add("Pre-Authorization.Processing.General.Discrepancy");
        alDocViewVisibility.add("Pre-Authorization.Processing.Alert");
        alDocViewVisibility.add("Claims.Processing.General");
        alDocViewVisibility.add("Claims.Processing.Medical");
        alDocViewVisibility.add("Claims.Processing.Bills");
        alDocViewVisibility.add("Claims.Processing.Support Doc");
        alDocViewVisibility.add("Claims.Processing.Settlement");
        alDocViewVisibility.add("Claims.Processing.History");
        alDocViewVisibility.add("Claims.Processing.Alert");
        alDocViewVisibility.add("Coding.PreAuth.General");
        alDocViewVisibility.add("Coding.PreAuth.Medical");
        alDocViewVisibility.add("Coding.PreAuth.Alert");
        alDocViewVisibility.add("Coding.Claims.General");
        alDocViewVisibility.add("Coding.Claims.Medical");
        alDocViewVisibility.add("Coding.Claims.Alert");
        alDocViewVisibility.add("Code CleanUp.PreAuth.Medical");
        alDocViewVisibility.add("Code CleanUp.PreAuth.Alert");
        alDocViewVisibility.add("Code CleanUp.PreAuth.General");
        alDocViewVisibility.add("Code CleanUp.Claims.Medical");
        alDocViewVisibility.add("Code CleanUp.Claims.Alert");
        alDocViewVisibility.add("Code CleanUp.Claims.General");
		alDocViewVisibility.add("Online Information.ApproveReject.Claims");//added as per Bajaj 
		alDocViewVisibility.add("DataEntryCoding.Claims.General");//ParallelAlert
        alDocViewVisibility.add("DataEntryCoding.Claims.Medical");//ParallelAlert
        alDocViewVisibility.add("DataEntryCoding.Claims.Alert");//ParallelAlert
        alDocViewVisibility.add("DataEntryClaims.Processing.General");//ParallelAlert
        alDocViewVisibility.add("DataEntryClaims.Processing.Medical");//ParallelAlert
        alDocViewVisibility.add("DataEntryClaims.Processing.Bills");//ParallelAlert
        alDocViewVisibility.add("DataEntryClaims.Processing.Alert");//ParallelAlert


        //construct the links where webboard icon's has to be displayed
        
        alWebBoardVisibility = new ArrayList<String>();
        alWebBoardVisibility.add("Administration.Tariff.Search");
        alWebBoardVisibility.add("Administration.Tariff.General");
        
        alWebBoardVisibility.add("User Management.Users");
        alWebBoardVisibility.add("Empanelment.Insurance.Search");
        alWebBoardVisibility.add("Empanelment.Insurance.Company Details");
        alWebBoardVisibility.add("Empanelment.Hospital.Search");
        alWebBoardVisibility.add("Empanelment.Hospital.General");
        alWebBoardVisibility.add("Empanelment.Hospital.MOU");
        alWebBoardVisibility.add("Empanelment.Hospital.Contacts");
        alWebBoardVisibility.add("Empanelment.Hospital.Accounts");
        alWebBoardVisibility.add("Empanelment.Hospital.Grading");
        alWebBoardVisibility.add("Empanelment.Hospital.Tariff");
        alWebBoardVisibility.add("Empanelment.Hospital.Validation");
        alWebBoardVisibility.add("Empanelment.Hospital.Status");
        alWebBoardVisibility.add("Empanelment.Hospital.Feedback");
        alWebBoardVisibility.add("Empanelment.Hospital.Alert");

        //links related to administration Mou
        //alWebBoardVisibility.add("Administration.MOU.Search");
        //alWebBoardVisibility.add("Administration.MOU.General");
        //alWebBoardVisibility.add("Administration.MOU.Clauses");
        alWebBoardVisibility.add("Empanelment.Partner.Search");
        alWebBoardVisibility.add("Empanelment.Partner.General");
        alWebBoardVisibility.add("Empanelment.Partner.Contacts");
        alWebBoardVisibility.add("Empanelment.Partner.Accounts");
        alWebBoardVisibility.add("Empanelment.Partner.Validation");
        alWebBoardVisibility.add("Empanelment.Partner.Status");
        alWebBoardVisibility.add("Empanelment.Partner.Feedback");
        alWebBoardVisibility.add("Empanelment.Partner.Alert");
        //links related to administration products
        alWebBoardVisibility.add("Administration.Products.Search");
        alWebBoardVisibility.add("Administration.Products.General");
        alWebBoardVisibility.add("Administration.Products.Product Rules");
        alWebBoardVisibility.add("Administration.Products.Card Rules");
        alWebBoardVisibility.add("Administration.Products.Hospitals");
        alWebBoardVisibility.add("Administration.Products.Circulars");
        alWebBoardVisibility.add("Administration.Products.Associate Product");
        //alWebBoardVisibility.add("Administration.Products.Honouring");
        alWebBoardVisibility.add("Administration.Products.User Group");
        alWebBoardVisibility.add("Administration.Policies.Buffer");

        // links related to administration policies
        alWebBoardVisibility.add("Administration.Policies.Search");
        alWebBoardVisibility.add("Administration.Policies.General");
        alWebBoardVisibility.add("Administration.Policies.Policy Rules");
        alWebBoardVisibility.add("Administration.Policies.Card Rules");
        alWebBoardVisibility.add("Administration.Policies.Hospitals");
        alWebBoardVisibility.add("Administration.Policies.Circulars");
        alWebBoardVisibility.add("Administration.Policies.Web Config");
        alWebBoardVisibility.add("Administration.Policies.Miscellaneous");
        
        //links related to Enrollment module
        alWebBoardVisibility.add("Enrollment.Individual Policy.ENM.Search");
        alWebBoardVisibility.add("Enrollment.Individual Policy.END.Search");
        alWebBoardVisibility.add("Enrollment.Ind. Policy as Group.ENM.Search");
        alWebBoardVisibility.add("Enrollment.Ind. Policy as Group.END.Search");
        alWebBoardVisibility.add("Enrollment.Corporate Policy.ENM.Search");
        alWebBoardVisibility.add("Enrollment.Corporate Policy.END.Search");
        alWebBoardVisibility.add("Enrollment.Non-Corporate Policy.ENM.Search");
        alWebBoardVisibility.add("Enrollment.Non-Corporate Policy.END.Search");
        alWebBoardVisibility.add("Enrollment.Individual Policy.ENM.Policy Details");
        alWebBoardVisibility.add("Enrollment.Individual Policy.END.Policy Details");
        alWebBoardVisibility.add("Enrollment.Ind. Policy as Group.ENM.Policy Details");
        alWebBoardVisibility.add("Enrollment.Ind. Policy as Group.END.Policy Details");
        alWebBoardVisibility.add("Enrollment.Corporate Policy.ENM.Policy Details");
        alWebBoardVisibility.add("Enrollment.Corporate Policy.END.Policy Details");
        alWebBoardVisibility.add("Enrollment.Non-Corporate Policy.ENM.Policy Details");
        alWebBoardVisibility.add("Enrollment.Non-Corporate Policy.END.Policy Details");

        alWebBoardVisibility.add("Enrollment.Individual Policy.END.Endorsement");
        alWebBoardVisibility.add("Enrollment.Ind. Policy as Group.END.Endorsement");
        alWebBoardVisibility.add("Enrollment.Corporate Policy.END.Endorsement");
        alWebBoardVisibility.add("Enrollment.Non-Corporate Policy.END.Endorsement");

        alWebBoardVisibility.add("Enrollment.Individual Policy.ENM.Members");
        alWebBoardVisibility.add("Enrollment.Individual Policy.END.Members");
        alWebBoardVisibility.add("Enrollment.Ind. Policy as Group.ENM.Members");
        alWebBoardVisibility.add("Enrollment.Ind. Policy as Group.END.Members");
        alWebBoardVisibility.add("Enrollment.Corporate Policy.ENM.Members");
        alWebBoardVisibility.add("Enrollment.Corporate Policy.END.Members");
        alWebBoardVisibility.add("Enrollment.Non-Corporate Policy.ENM.Members");
        alWebBoardVisibility.add("Enrollment.Non-Corporate Policy.END.Members");

        alWebBoardVisibility.add("Enrollment.Individual Policy.ENM.Alert");
        alWebBoardVisibility.add("Enrollment.Individual Policy.END.Alert");
        alWebBoardVisibility.add("Enrollment.Ind. Policy as Group.ENM.Alert");
        alWebBoardVisibility.add("Enrollment.Ind. Policy as Group.END.Alert");
        alWebBoardVisibility.add("Enrollment.Corporate Policy.ENM.Alert");
        alWebBoardVisibility.add("Enrollment.Corporate Policy.END.Alert");
        alWebBoardVisibility.add("Enrollment.Non-Corporate Policy.ENM.Alert");
        alWebBoardVisibility.add("Enrollment.Non-Corporate Policy.END.Alert");

        alWebBoardVisibility.add("Enrollment.Individual Policy.ENM.Enrollment Rules");
        alWebBoardVisibility.add("Enrollment.Individual Policy.END.Enrollment Rules");
        alWebBoardVisibility.add("Enrollment.Ind. Policy as Group.ENM.Enrollment Rules");
        alWebBoardVisibility.add("Enrollment.Ind. Policy as Group.END.Enrollment Rules");
        alWebBoardVisibility.add("Enrollment.Corporate Policy.ENM.Enrollment Rules");
        alWebBoardVisibility.add("Enrollment.Corporate Policy.END.Enrollment Rules");
        alWebBoardVisibility.add("Enrollment.Non-Corporate Policy.ENM.Enrollment Rules");
        alWebBoardVisibility.add("Enrollment.Non-Corporate Policy.END.Enrollment Rules");


        //links related to Pre-Authorization module
        alWebBoardVisibility.add("Pre-Authorization.Processing.Search");
        alWebBoardVisibility.add("Pre-Authorization.Processing.System Preauth Approval");
        alWebBoardVisibility.add("Pre-Authorization.Processing.Medical");
        alWebBoardVisibility.add("Pre-Authorization.Processing.Tariff");
        alWebBoardVisibility.add("Pre-Authorization.Processing.Rule Data");
        alWebBoardVisibility.add("Pre-Authorization.Processing.Authorization");
        alWebBoardVisibility.add("Pre-Authorization.Processing.Alert");
        alWebBoardVisibility.add("Pre-Authorization.Processing.History");
        alWebBoardVisibility.add("Pre-Authorization.Processing.Support Doc");
        alWebBoardVisibility.add("Pre-Authorization.Shortfalls.Search");
        alWebBoardVisibility.add("Pre-Authorization.Shortfalls.General");
        alWebBoardVisibility.add("Pre-Authorization.Enhancement.Search");
        alWebBoardVisibility.add("Pre-Authorization.Enhancement.General");
        alWebBoardVisibility.add("Pre-Authorization.Referral Letter.Search");
        alWebBoardVisibility.add("Pre-Authorization.Referral Letter.Generate Letter");
        
        //links related to Claims module
        
        alWebBoardVisibility.add("Claims.BatchEntry.Search");
        alWebBoardVisibility.add("Claims.BatchEntry.General");
        alWebBoardVisibility.add("Claims.Processing.Search");
        alWebBoardVisibility.add("Claims.Processing.General");
        alWebBoardVisibility.add("Claims.Processing.Medical");
        alWebBoardVisibility.add("Claims.Processing.Bills");
        alWebBoardVisibility.add("Claims.Processing.Rule Data");
        alWebBoardVisibility.add("Claims.Processing.Support Doc");
        alWebBoardVisibility.add("Claims.Processing.Settlement");
        alWebBoardVisibility.add("Claims.Processing.History");
        alWebBoardVisibility.add("Claims.Processing.Alert");
        alWebBoardVisibility.add("Claims.Shortfalls.Search");
        alWebBoardVisibility.add("Claims.Shortfalls.General");
        
		//added for CR KOC-Decoupling
        //links related to DataEntryClaims module
	    alWebBoardVisibility.add("DataEntryClaims.Processing.Search");
	    alWebBoardVisibility.add("DataEntryClaims.Processing.General");
	    alWebBoardVisibility.add("DataEntryClaims.Processing.Medical");
	    alWebBoardVisibility.add("DataEntryClaims.Processing.Bills");
	    alWebBoardVisibility.add("DataEntryClaims.Processing.Rule Data");
	    alWebBoardVisibility.add("DataEntryClaims.Processing.Support Doc");
	    alWebBoardVisibility.add("DataEntryClaims.Processing.Settlement");
	    alWebBoardVisibility.add("DataEntryClaims.Processing.History");
	    alWebBoardVisibility.add("DataEntryClaims.Processing.Alert");

        //links related to Finance module
        alWebBoardVisibility.add("Finance.Bank Account.Search");
        alWebBoardVisibility.add("Finance.Bank Account.General");
        alWebBoardVisibility.add("Finance.Bank Account.Guarantee Details");
        alWebBoardVisibility.add("Finance.Bank Account.Authorised Signatories");
        alWebBoardVisibility.add("Finance.Bank Account.Cheque Series");
        alWebBoardVisibility.add("Finance.Bank Account.Transactions");
        alWebBoardVisibility.add("Finance.Float Account.Search");
        alWebBoardVisibility.add("Finance.Float Account.General");
        alWebBoardVisibility.add("Finance.Float Account.Transactions");
        alWebBoardVisibility.add("Finance.Float Account.Payments");
        alWebBoardVisibility.add("Finance.Float Account.Address Label");
        alWebBoardVisibility.add("Finance.Float Account.Payment Advice");
        alWebBoardVisibility.add("Finance.Float Account.View Payment Advice");
        
        /*
		 * This code is added for cr koc 1103
		 * added eft
		 */
          alWebBoardVisibility.add("Finance.Cust. Bank Details.Search");
          alWebBoardVisibility.add("Finance.Cust. Bank Details.POLCPolc.Search");
          alWebBoardVisibility.add("Finance.Cust. Bank Details.POLCMem.Search");
          alWebBoardVisibility.add("Finance.Cust. Bank Details.CLAMPolc.Search");
          alWebBoardVisibility.add("Finance.Cust. Bank Details.CLAMMem.Search");
          alWebBoardVisibility.add("Finance.Cust. Bank Details.HOSP.Search");
          alWebBoardVisibility.add("Finance.Cust. Bank Details.POLCPolc.Policy Bank Details");
          alWebBoardVisibility.add("Finance.Cust. Bank Details.POLCMem.Member Bank Details");
          alWebBoardVisibility.add("Finance.Cust. Bank Details.CLAMPolc.Policy Bank Details");
          alWebBoardVisibility.add("Finance.Cust. Bank Details.CLAMMem.Member Bank Details");
          alWebBoardVisibility.add("Finance.Cust. Bank Details.HOSP.Hospital Bank Details");
          /*
  		 *End koc 1103
  		 * End eft
  		 */

        //Web board visibality for Account Info screens.
        alWebBoardVisibility.add("Account Info.Enrollment.Search");
        alWebBoardVisibility.add("Account Info.Enrollment.Policy Details");
        alWebBoardVisibility.add("Account Info.Enrollment.Members");

        //Web board visibility for Coding Screens
        alWebBoardVisibility.add("Coding.PreAuth.Search");
        alWebBoardVisibility.add("Coding.PreAuth.General");
        alWebBoardVisibility.add("Coding.PreAuth.Medical");
        alWebBoardVisibility.add("Coding.PreAuth.ICD");
        alWebBoardVisibility.add("Coding.Claims.ICD");
        alWebBoardVisibility.add("Coding.PreAuth.Alert");
        alWebBoardVisibility.add("Coding.Claims.Search");
        alWebBoardVisibility.add("Coding.Claims.General");
        alWebBoardVisibility.add("Coding.Claims.Medical");
        alWebBoardVisibility.add("Coding.Claims.Alert");
		
		//Web board visibility for DataEntryCoding Screens KOC-Decoupling
        alWebBoardVisibility.add("DataEntryCoding.Claims.Search");
        alWebBoardVisibility.add("DataEntryCoding.Claims.General");
        alWebBoardVisibility.add("DataEntryCoding.Claims.Medical");
        alWebBoardVisibility.add("DataEntryCoding.Claims.Alert");
        
        //Web board visibility for CodeCleanup Screens
        alWebBoardVisibility.add("Code CleanUp.PreAuth.Search");
        alWebBoardVisibility.add("Code CleanUp.PreAuth.General");
        alWebBoardVisibility.add("Code CleanUp.PreAuth.Medical");
        alWebBoardVisibility.add("Code CleanUp.PreAuth.ICD");
        alWebBoardVisibility.add("Code CleanUp.PreAuth.Alert");
        alWebBoardVisibility.add("Code CleanUp.Claims.Search");
        alWebBoardVisibility.add("Code CleanUp.Claims.General");
        alWebBoardVisibility.add("Code CleanUp.Claims.Medical");
        alWebBoardVisibility.add("Code CleanUp.Claims.ICD");
        alWebBoardVisibility.add("Code CleanUp.Claims.Alert");
		
		//Web board visibility for Support
        alWebBoardVisibility.add("Support.Investigation.General1");
        alWebBoardVisibility.add("Support.Investigation.General2");
        alWebBoardVisibility.add("Support.Investigation.Claim.General1");
        alWebBoardVisibility.add("Support.Investigation.Claim.General2");
        alWebBoardVisibility.add("Support.Investigation.PreAuth.General1");
        alWebBoardVisibility.add("Support.Investigation.PreAuth.General2");
        //added as per Hospital Login 
        alWebBoardVisibility.add("Hospital Information.Cashless Status.Search");
        //alWebBoardVisibility.add("Hospital Information.Pre-Auth.General");
        //alWebBoardVisibility.add("Hospital Information.Pre-Auth.General");
        alWebBoardVisibility.add("Hospital Information.Cashless Status.Details");
        alWebBoardVisibility.add("Hospital Information.Claims Status.Search");
        // alWebBoardVisibility.add("Hospital Information.Claims.General");
        alWebBoardVisibility.add("Hospital Information.Claims Status.Details");
        alWebBoardVisibility.add("Hospital Information.Home.Cashless DashBoard");
        alWebBoardVisibility.add("Hospital Information.Cashless.Search");
        alWebBoardVisibility.add("Hospital Information.Cashless.General");
          
		//Web board visibility for CodeCleanup Screens Broker
        alWebBoardVisibility.add("Empanelment.Broker.Search");
        alWebBoardVisibility.add("Empanelment.Broker.Broker Details");
        alWebBoardVisibility.add("Empanelment.Broker.Search.Add");
        alWebBoardVisibility.add("Empanelment.Broker.Search.Delete");
        alWebBoardVisibility.add("Empanelment.Broker.Broker Details.Add/Edit Broker");
        alWebBoardVisibility.add("Empanelment.Broker.Broker Details.Contacts");
        alWebBoardVisibility.add("Empanelment.Broker.Broker Details.ContactDetails");
        alWebBoardVisibility.add("Empanelment.Broker.Broker Details.Validity");
        alWebBoardVisibility.add("Empanelment.Broker.Broker Details.Feedback");
        alWebBoardVisibility.add("Empanelment.Broker.Broker Details.FeedbackDetail");
        alWebBoardVisibility.add("Empanelment.Broker.Broker Details.InsuranceProduct");
        alWebBoardVisibility.add("Empanelment.Broker.Broker Details.EditInsuranceProduct");
        alWebBoardVisibility.add("Empanelment.Broker.Broker Details.Add");
        alWebBoardVisibility.add("Empanelment.Broker.Broker Details.Edit");
        alWebBoardVisibility.add("Empanelment.Broker.Broker Details.Delete");
        
        alDocViewVisibility.add("Empanelment.Broker.Search");
        alDocViewVisibility.add("Empanelment.Broker.Broker Details");
        alDocViewVisibility.add("Empanelment.Broker.Search.Add");
        alDocViewVisibility.add("Empanelment.Broker.Search.Delete");
        alDocViewVisibility.add("Empanelment.Broker.Broker Details.Add/Edit Broker");
        alDocViewVisibility.add("Empanelment.Broker.Broker Details.Contacts");
        alDocViewVisibility.add("Empanelment.Broker.Broker Details.ContactDetails");
        alDocViewVisibility.add("Empanelment.Broker.Broker Details.Validity");
        alDocViewVisibility.add("Empanelment.Broker.Broker Details.Feedback");
        alDocViewVisibility.add("Empanelment.Broker.Broker Details.FeedbackDetail");
        alDocViewVisibility.add("Empanelment.Broker.Broker Details.InsuranceProduct");
        alDocViewVisibility.add("Empanelment.Broker.Broker Details.EditInsuranceProduct");
        alDocViewVisibility.add("Empanelment.Broker.Broker Details.Add");
        alDocViewVisibility.add("Empanelment.Broker.Broker Details.Edit");
        alDocViewVisibility.add("Empanelment.Broker.Broker Details.Delete");
        
        //bikki
        alWebBoardVisibility.add("Software Insurance Pricing.Profile creation.Home");
        alWebBoardVisibility.add("Software Insurance Pricing.Profile creation.Group Profile");
        
  // govind
        
        alWebBoardVisibility.add("Software Insurance Pricing.Master.FactorMaster");
        
        
    }//end of loadWebBoard()

    public void updateVisibility(String strLink)
    {
        if(webBoard == null)
            this.currentWebBoard(strLink);

        if(alConflictVisibility == null || alDocViewVisibility == null || alWebBoardVisibility == null)
            this.loadVisibilityInfo();

        this.getConflictIcon().setVisible(alConflictVisibility.contains(strLink));
        this.getDocViewIcon().setVisible(alDocViewVisibility.contains(strLink));
        this.webBoard = this.currentWebBoard(strLink.substring(0,strLink.lastIndexOf(".")));//main link.sub link value
       
        this.webBoard.setVisible(alWebBoardVisibility.contains(strLink));

    }//end of updateVisibility(String strLink)

    public String getDocUrl(){
        String strURL = TTKPropertiesReader.getPropertyValue("DocUrl");
        return strURL;
    }//end of getDocUrl()
    
    public String getWebDocUrl(){
        String strURL = TTKPropertiesReader.getPropertyValue("WebDocUrl");
        return strURL;
    }//end of getDocUrl()

}//end of Toolbar
