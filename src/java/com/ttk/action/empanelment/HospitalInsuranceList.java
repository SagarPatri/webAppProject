/**
 * @ (#) HospitalInsuranceList.java Sep 24, 2005
 * Project      : TTK HealthCare Services
 * File         : HospitalContactAction.java
 * Author       : Chandrasekaran J
 * Company      : Span Systems Corporation
 * Date Created : Sep 24, 2005
 *
 * @author       :  Chandrasekaran J
 * Modified by   : Arun K N
 * Modified date : 4th Nov 2005
 * Reason        : to refresh data when contact type is changed
 */

package com.ttk.action.empanelment;

import java.util.ArrayList;

import javax.naming.InitialContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.DynaActionForm;

import com.ttk.action.TTKAction;
import com.ttk.action.table.TableData;
import com.ttk.action.tree.TreeData;
import com.ttk.business.empanelment.ContactManager;
import com.ttk.common.TTKCommon;
import com.ttk.common.exception.TTKException;
import com.ttk.dto.empanelment.GroupRegistrationVO;
import com.ttk.dto.empanelment.HospitalDetailVO;
import com.ttk.dto.empanelment.HospitalVO;
import com.ttk.dto.empanelment.InsuranceVO;

/**
 * This class is reusable for adding contacts in Hospital, Insurance, Group Registration and Banks in
 * Empanlement and Finance flows. Adding User in the User Management flow.
 * This class also provides option for reseting the Password.
 */

public class HospitalInsuranceList extends TTKAction
{
    private static Logger log = Logger.getLogger( HospitalInsuranceList.class );
    
    //Modes.
    private static final String strBackward="Backward";
    private static final String strForward="Forward";
    private static final String strClose="Cancel";
    

    private static final String strHospitalPath ="hospitalsummarylist";//Insurance_corporate_wise_hosp_network
    private static final String strHospitalCorPath="hospitalcorlist";//Insurance_corporate_wise_hosp_network
    private static final String strHospitalGrpClose="hospitalGrpclose";
    private static final String strHospitalInsClose="hospitalclose";
    //Exception Message Identifier
    private static final String strContactError="contact";

    
   
    
  //Insurance_corporate_wise_hosp_network
    public ActionForward doViewHospitalList(ActionMapping mapping,ActionForm form,
    		HttpServletRequest request,HttpServletResponse response) throws Exception{
    	try{
    		log.debug("Inside the doViewHospitalList method of CompanyDetailAction");
    		setLinks(request);    		
    		String strForwards="";	
			//Long lGroupRegSeqID=(Long)request.getSession().getAttribute("groupSeqId")==null?0:(Long)request.getSession().getAttribute("groupSeqId");
			String caption=(String)request.getSession().getAttribute("caption");			
			DynaActionForm frmHospitalList = (DynaActionForm)request.getSession().getAttribute("frmHospitalList");
			String strCaption = "";
			if(TTKCommon.getActiveSubLink(request).equals("Insurance"))
			{
				strForwards=strHospitalPath;
			}//end of if(TTKCommon.getActiveSubLink(request).equals("Products"))
			else if(TTKCommon.getActiveSubLink(request).equals("Group Registration"))
			{
				strForwards=strHospitalCorPath;
			}//end of else if(TTKCommon.getActiveSubLink(request).equals("Policies"))
	
			TableData tableData=null;
			strCaption = this.buildCaption(request);
			if(request.getSession().getAttribute("tableData")!=null)
				tableData= (TableData)(request.getSession()).getAttribute("tableData");
			tableData = new TableData();
			HospitalDetailVO hospitalDetailVO = new HospitalDetailVO();
			request.getSession().setAttribute("HospitalDetailVO",hospitalDetailVO);			
				tableData.createTableInfo("HospitalListTable",new ArrayList());
				request.getSession().setAttribute("tableData",tableData);
				//System.out.println("strCaption-------->"+strCaption);
				frmHospitalList.set("caption",strCaption); 
				//System.out.println("strForwards ..."+strForwards+"request  "+request+"mapping  "+mapping);
    		return this.getForward(strForwards, mapping, request);
    	}//end of try
    	catch(TTKException expTTK)
		{
			return this.processExceptions(request, mapping, expTTK);
		}//end of catch(TTKException expTTK)
		catch(Exception exp)
		{
			return this.processExceptions(request, mapping, new TTKException(exp,strContactError));
		}//end of catch(Exception exp)
    }//end of doViewDisplayCompanyDetail(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response)
    
    
    
    
    public ActionForward doSelectHospital(ActionMapping mapping,ActionForm form,HttpServletRequest request,
    		HttpServletResponse response) throws Exception{
    	try{
    		log.info("Inside the doSearch method of HospitalContactAction");
    		setLinks(request);
    		InsuranceVO insuranceVO=null;
    		GroupRegistrationVO groupRegistrationVO=new GroupRegistrationVO();
    		TreeData treeData = TTKCommon.getTreeData(request);
    		Long lngInsuranceSeqID = null;
            Long lngHospitalSeqId=null;
            Long lngGroupRegSeqId=null;
            Long lngBankSeqId=null;
            String strCaption = "";
    		String strSubLink=TTKCommon.getActiveSubLink(request);
    		TableData  tableData =TTKCommon.getTableData(request);			
    		DynaActionForm frmHospitalList = (DynaActionForm)request.getSession().getAttribute("frmHospitalList");
    		String strContactPath=this.getForwardPath(request);
    		ContactManager contactObject=this.getContactManagerObject();
    		if(strSubLink.equals("Insurance"))
            {
                //get the insurance sequence id from session
    			insuranceVO = (InsuranceVO)request.getSession().getAttribute("SelectedOffice");
                if(insuranceVO != null)
                {
                	lngInsuranceSeqID = insuranceVO.getInsuranceSeqID();
                }//end of if(insuranceVO != null)
            }//end of if (strSubLink=="Insurance")
            else if(strSubLink.equals("Group Registration"))
            {

            	if(groupRegistrationVO != null)
            	{

            		lngGroupRegSeqId=(Long)request.getSession().getAttribute("groupSeqId");
            	}
            	//lngGroupRegSeqId=TTKCommon.getLong(strGroupSeqId); //groupRegistrationVO.getGroupRegSeqID();
            }// end of else if(strSubLink.equals("Group Registration"))
          
    		String strPageID = TTKCommon.checkNull(request.getParameter("pageId"));
            String strSortID = TTKCommon.checkNull(request.getParameter("sortId"));
            //if the page number or sort id is clicked
            if(!strPageID.equals("") || !strSortID.equals(""))
            {
                if(!strPageID.equals(""))
                {
                    tableData.setCurrentPage(Integer.parseInt(TTKCommon.checkNull(request.getParameter("pageId"))));
                    return (mapping.findForward(strContactPath));
                }///end of if(!strPageID.equals(""))
                else
                {
                    tableData.setSortData(TTKCommon.checkNull(request.getParameter("sortId")));
                    tableData.modifySearchData("sort");//modify the search data
                }//end of else
            }//end of if(!strPageID.equals("") || !strSortID.equals(""))
          
                    tableData.createTableInfo("HospitalListTable",null);
                    strCaption = this.buildCaption(request);  
                tableData.setSearchData(this.populateSearchCriteria((DynaActionForm)form,request,
                		lngInsuranceSeqID,lngGroupRegSeqId));
                tableData.modifySearchData("search");
                ArrayList  alResultList= contactObject.getHospitalList(tableData.getSearchData());
                 
            tableData.setData(alResultList, "search");
            //set the table data object to session
            request.getSession().setAttribute("tableData",tableData);
            frmHospitalList.set("caption",strCaption); 
            //finally return to the grid screen
           //getCaption(frmHospitalList,request);
            return (mapping.findForward(strContactPath));
    	}//end of try
    	catch(TTKException expTTK)
		{
			return this.processExceptions(request, mapping, expTTK);
		}//end of catch(TTKException expTTK)
		catch(Exception exp)
		{
			return this.processExceptions(request, mapping, new TTKException(exp,strContactError));
		}//end of catch(Exception exp)
    }//end of doSearch(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response)
    
    
    
    public ActionForward doAssociate(ActionMapping mapping,ActionForm form,HttpServletRequest request,
			   HttpServletResponse response) throws Exception{
    			try{
    					log.debug("Inside the doAssociate method of AdminHospitalAction");
    					setLinks(request);
    					String strForwards="";
    					String strAssociateCode="";
    					String strCaption = "";
    					Long lngInsuranceSeqID=null;
    					Long lngGroupRegSeqId=null;
    					ContactManager contactObject=this.getContactManagerObject();
    					GroupRegistrationVO groupRegistrationVO=new GroupRegistrationVO();
    					String strSubLink=TTKCommon.getActiveSubLink(request);
    					TableData tableData=null;
						InsuranceVO insuranceVO = (InsuranceVO)request.getSession().getAttribute("SelectedOffice");
						String userID=TTKCommon.getUserID(request);
						if(strSubLink.equals("Insurance"))
			            {
			                //get the insurance sequence id from session
			    			insuranceVO = (InsuranceVO)request.getSession().getAttribute("SelectedOffice");
			                if(insuranceVO != null)
			                {
			                	lngInsuranceSeqID = insuranceVO.getInsuranceSeqID();
			                }//end of if(insuranceVO != null)
			            }//end of if (strSubLink=="Insurance")
			            else if(strSubLink.equals("Group Registration"))
			            {

			            	if(groupRegistrationVO != null)
			            	{

			            		lngGroupRegSeqId=(Long)request.getSession().getAttribute("groupSeqId");
			            	}
			            	//lngGroupRegSeqId=TTKCommon.getLong(strGroupSeqId); //groupRegistrationVO.getGroupRegSeqID();
			            }// end of else if(strSubLink.equals("Group Registration"))
						
						HospitalDetailVO hospitalDetailVO=new HospitalDetailVO();
						Long hospSeqId=hospitalDetailVO.getHospSeqId();
    					if(request.getSession().getAttribute("tableData")!=null)
    						tableData= (TableData)(request.getSession()).getAttribute("tableData");
    					DynaActionForm frmHospitalList = (DynaActionForm)request.getSession().getAttribute("frmHospitalList");
    					strAssociateCode=(String)frmHospitalList.get("associateCode");
    					DynaActionForm frmHospitals=(DynaActionForm)form;
    					ArrayList alAssoicateHospList=this.getHospList(request);
    					strAssociateCode="ASL";
    					if(strSubLink.equals("Insurance"))
			            {
    						
    					int iResult=contactObject.associateHospitalList(alAssoicateHospList,lngInsuranceSeqID,strAssociateCode);
    							request.setAttribute("updated","message.associate");
			            }
    					else if(strSubLink.equals("Group Registration"))
			            {
    						
    						int iResult=contactObject.associateHospitalGroupList(alAssoicateHospList,lngGroupRegSeqId,strAssociateCode);
    						request.setAttribute("updated","message.associate");
			            }
			            
    					//log.debug("iResult value is :"+iResult);
    					strCaption = this.buildCaption(request);

    					 ArrayList alContact= contactObject.getHospitalList(tableData.getSearchData());
    			            tableData.setData(alContact, "search");
    					//set the table data object to session
    					request.getSession().setAttribute("tableData",tableData);
    					 frmHospitalList.set("caption",strCaption); 
    					return this.getForward(strHospitalCorPath, mapping, request);
    			}//end of try
    			catch(TTKException expTTK)
    			{
    				return this.processExceptions(request, mapping, expTTK);
    			}//end of catch(TTKException expTTK)
    			catch(Exception exp)
    			{
    				return this.processExceptions(request, mapping, new TTKException(exp,strContactError));
    			}//end of catch(Exception exp)
    }//end of doAssociate(ActionMapping mapping,ActionForm form,HttpServletRequest request,
//HttpServletResponse response)
    
    
    
    public ActionForward doExclude(ActionMapping mapping,ActionForm form,HttpServletRequest request,
		     HttpServletResponse response) throws Exception{
    	try{
    		log.debug("Inside the doExclude method of AdminHospitalAction");
    		setLinks(request);
    		String strForwards="";
    		String strAssociateCode="";
    		String strCaption = "";
			Long lngInsuranceSeqID=null;
			Long lngGroupRegSeqId=null;
    		ContactManager contactObject=this.getContactManagerObject();
    		GroupRegistrationVO groupRegistrationVO=new GroupRegistrationVO();
    		InsuranceVO insuranceVO = (InsuranceVO)request.getSession().getAttribute("SelectedOffice");
    		TableData tableData=null;
    		if(request.getSession().getAttribute("tableData")!=null)
    			tableData= (TableData)(request.getSession()).getAttribute("tableData");
    		DynaActionForm frmHospitalList = (DynaActionForm)request.getSession().getAttribute("frmHospitalList");
    		strAssociateCode=(String)frmHospitalList.get("associateCode");
    		ArrayList alAssoicateHospList=this.getHospList(request);
    		String strSubLink=TTKCommon.getActiveSubLink(request);
    		if(TTKCommon.getActiveSubLink(request).equals("Insurance"))
			{
				strForwards=strHospitalPath;
			}//end of if(TTKCommon.getActiveSubLink(request).equals("Products"))
			else if(TTKCommon.getActiveSubLink(request).equals("Group Registration"))
			{
				strForwards=strHospitalCorPath;
			}//end of else if(TTKCommon.getActiveSubLink(request).equals("Policies"))
	
    		if(strSubLink.equals("Insurance"))
            {
                //get the insurance sequence id from session
    			insuranceVO = (InsuranceVO)request.getSession().getAttribute("SelectedOffice");
                if(insuranceVO != null)
                {
                	lngInsuranceSeqID = insuranceVO.getInsuranceSeqID();
                }//end of if(insuranceVO != null)
            }//end of if (strSubLink=="Insurance")
            else if(strSubLink.equals("Group Registration"))
            {

            	if(groupRegistrationVO != null)
            	{

            		lngGroupRegSeqId=(Long)request.getSession().getAttribute("groupSeqId");
            	}
            	//lngGroupRegSeqId=TTKCommon.getLong(strGroupSeqId); //groupRegistrationVO.getGroupRegSeqID();
            }// end of else if(strSubLink.equals("Group Registration"))
    		strAssociateCode="ASL";
    		
    		if(strSubLink.equals("Insurance"))
            {
    		int iResult=contactObject.associateHospitalExcInsList(alAssoicateHospList);
            }
    		else if(strSubLink.equals("Group Registration"))
            {
    			int iResult=contactObject.associateHospitalExcGroupList(alAssoicateHospList);	
            }
    		//log.debug("iResult value is :"+iResult);
    		//fetch the records from the criteria found in session
    		ArrayList alExucdeHospitalList=null;
    		int iCount=TTKCommon.deleteStringLength((String)alAssoicateHospList.get(0), "|");
    		
    		if(iCount == tableData.getData().size())
    		{
    			tableData.modifySearchData("Delete");//modify the search data
    			int iStartRowCount = Integer.parseInt((String)tableData.getSearchData().get(tableData.
									getSearchData().size()-2));
    		
    				
    			//end of if(iStartRowCount > 0)
    		}//end if(alHospitalList.size() == 0 || iCount == tableData.getData().size())
    		alExucdeHospitalList=contactObject.getHospitalList(tableData.getSearchData());
    		tableData.setData(alExucdeHospitalList, "Delete");
    		//set the table data object to session
    		request.getSession().setAttribute("tableData",tableData);
    		return this.getForward(strHospitalCorPath, mapping, request);
    	}//end of try
    	catch(TTKException expTTK)
    	{
    		return this.processExceptions(request, mapping, expTTK);
    	}//end of catch(TTKException expTTK)
    	catch(Exception exp)
    	{
    		return this.processExceptions(request, mapping, new TTKException(exp,strContactError));
    	}//end of catch(Exception exp)
    }//end of doExclude(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response)

    
    
    
	public ActionForward doHospitalClose(ActionMapping mapping,ActionForm form,HttpServletRequest request,
			HttpServletResponse response) throws Exception{
		try{
			log.debug("Inside doClose method of AccInfoMemberAction");
			setLinks(request);
			String strForwards="";
			String strSubLink=TTKCommon.getActiveSubLink(request);
			if(strSubLink.equals("Insurance"))
            {
				strForwards=strHospitalInsClose;
            }//end of if (strSubLink=="Insurance")
            else if(strSubLink.equals("Group Registration"))
            {
            	strForwards=strHospitalGrpClose;
            	
            }// end of else if(strSubLink.equals("Group Registration"))
			
			return this.getForward(strForwards, mapping, request);
		}//end of try
		catch(TTKException expTTK)
		{
			return this.processExceptions(request, mapping, expTTK);
		}//end of catch(TTKException expTTK)
		catch(Exception exp)
		{
			return this.processExceptions(request, mapping, new TTKException(exp,strContactError));
		}//end of catch(Exception exp)
	}//end of doClose method
    
  //Insurance_corporate_wise_hosp_network


    public ActionForward doBackward(ActionMapping mapping,ActionForm form,HttpServletRequest request,
    		HttpServletResponse response) throws Exception{
    	try{
    		log.debug("Inside the doBackward method of HospitalContactAction");
    		setLinks(request);
    		ContactManager contactObject=this.getContactManagerObject();
    		String strContactPath=this.getForwardPath(request);
    		TableData  tableData =TTKCommon.getTableData(request);
    		tableData.modifySearchData(strBackward);//modify the search data
    		ArrayList  alResultList= contactObject.getHospitalList(tableData.getSearchData());
            tableData.setData(alResultList, strBackward);//set the table data
            request.getSession().setAttribute("tableData",tableData);//set the table data object to session
            //finally return to the grid screen
            return (mapping.findForward(strContactPath));
    	}//end of try
    	catch(TTKException expTTK)
		{
			return this.processExceptions(request, mapping, expTTK);
		}//end of catch(TTKException expTTK)
		catch(Exception exp)
		{
			return this.processExceptions(request, mapping, new TTKException(exp,strContactError));
		}//end of catch(Exception exp)
    }//end of doBackward(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response)

    
    public ActionForward doForward(ActionMapping mapping,ActionForm form,HttpServletRequest request,
    		HttpServletResponse response) throws Exception{
    	try{
    		log.debug("Inside the doForward method of HospitalContactAction");
    		setLinks(request);
    		ContactManager contactObject=this.getContactManagerObject();
    		String strContactPath=this.getForwardPath(request);
    		TableData  tableData =TTKCommon.getTableData(request);
    		tableData.modifySearchData(strForward);//modify the search data
    		ArrayList  alResultList= contactObject.getHospitalList(tableData.getSearchData());
            tableData.setData(alResultList, strForward);//set the table data
            request.getSession().setAttribute("tableData",tableData);//set the table data object to session
            //finally return to the grid screen
            return (mapping.findForward(strContactPath));
    	}//end of try
    	catch(TTKException expTTK)
		{
			return this.processExceptions(request, mapping, expTTK);
		}//end of catch(TTKException expTTK)
		catch(Exception exp)
		{
			return this.processExceptions(request, mapping, new TTKException(exp,strContactError));
		}//end of catch(Exception exp)
    }//end of doForward(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response)
    

    

    private String getForwardPath(HttpServletRequest request) throws TTKException{
    	String strSubLink=TTKCommon.getActiveSubLink(request);
    	String strContactPath="";
    	try{
    	
    		if(strSubLink.equals("Insurance")){
    			strContactPath=strHospitalPath;
    		}//end of if(strSubLink.equals("Insurance"))
    		if(strSubLink.equals("Group Registration")){
    			strContactPath=strHospitalCorPath;
    		}//end of if(strSubLink.equals("Group Registration"))
    	}//end of try
    	catch(Exception exp)
        {
            throw new TTKException(exp, strContactError);
        }//end of catch
        return strContactPath;
    }//end of getForwardPath(HttpServletRequest request)
    

    public String buildCaption(HttpServletRequest request) throws TTKException{
    	String strSubLink=TTKCommon.getActiveSubLink(request);
    	String strCaption= "";
    	InsuranceVO  insuranceVO=null;
    	String strCompanyName = "";
    	if(strSubLink.equals("Insurance"))
        {
    		insuranceVO = (InsuranceVO)request.getSession().getAttribute("SelectedOffice");
            if(insuranceVO != null){
            	strCompanyName="[";
                strCompanyName+=insuranceVO.getBranchName()+"]";
            }//end of if(insuranceVO != null)
            strCaption = strCompanyName;
    	}//end of if(strSubLink.equals("Insurance"))
    	else{
    		strCaption=(String)request.getSession().getAttribute("caption"); //Getting the caption value from grouplist action
    	}//end of else
    	return strCaption;
    }//end of buildCaption(HttpServletRequest request)
    
    
    private ArrayList getHospList(HttpServletRequest request) {
		ArrayList <Object>alHospitalList=new ArrayList<Object>();
		StringBuffer sbfId=new StringBuffer();
		String strChOpt[]=request.getParameterValues("chkopt");
		TableData tableData=(TableData)request.getSession().getAttribute("tableData");
		if((strChOpt!=null)&&strChOpt.length!=0)
		{
			if(request.getParameter("mode").equals("doRemove"))
			{
				for(int intCounter=0;intCounter<strChOpt.length;intCounter++)
				{
					if(strChOpt[intCounter]!=null)
					{
						if(intCounter==0){
							sbfId.append(String.valueOf(((HospitalVO)tableData.getRowInfo(Integer.parseInt
									(strChOpt[intCounter]))).getProdHospSeqId()));
						}//end of if(intCounter==0)
						else{
							sbfId=sbfId.append("|").append(String.valueOf(((HospitalVO)tableData.getRowInfo
									(Integer.parseInt(strChOpt[intCounter]))).getProdHospSeqId()));
						}//end of else
					} // end of if(strChOpt[intCounter]!=null)
					
				} //end of for loop
			} //if(request.getParameter("mode").equals(strDisassociate))
			else
			{
				for(int intCounter=0;intCounter<strChOpt.length;intCounter++)
				{
					if(strChOpt[intCounter]!=null)
					{
						if(intCounter==0){
							sbfId.append(String.valueOf(((HospitalVO)tableData.getRowInfo(Integer.parseInt
									(strChOpt[intCounter]))).getHospSeqId()));
						}//end of if(intCounter==0)
						else{
							sbfId=sbfId.append("|").append(String.valueOf(((HospitalVO)tableData.getRowInfo(
									Integer.parseInt(strChOpt[intCounter]))).getHospSeqId()));
						}//end of else
					} // end of if(strChOpt[intCounter]!=null)
				} // end of for loop
			} // end of else
		}//end of if((strChOpt!=null)&&strChOpt.length!=0)		
		alHospitalList.add("|"+sbfId+"|");
		alHospitalList.add(TTKCommon.getUserSeqId(request));
		return alHospitalList;
	}//end of getHospList(HttpServletRequest request)

  
    private ArrayList<Object> populateSearchCriteria(DynaActionForm frmHospitalList,HttpServletRequest request,  Long lInsuranceSeqID, Long lGroupRegSeqId) throws TTKException
    {
        String strSubLink=TTKCommon.getActiveSubLink(request);
        ArrayList<Object> alSearchParams = new ArrayList<Object>();
        alSearchParams.add(TTKCommon.replaceSingleQots((String)frmHospitalList.get("empanelmentNO")));
		alSearchParams.add(TTKCommon.replaceSingleQots((String)frmHospitalList.get("hospName")));
		alSearchParams.add(TTKCommon.replaceSingleQots((String)frmHospitalList.get("officeInfo")));
		alSearchParams.add((String)frmHospitalList.get("cityCode"));
		alSearchParams.add((String)frmHospitalList.get("associateCode"));
       if(strSubLink.equals("Insurance"))
        {
    	    alSearchParams.add("INS");
            alSearchParams.add(lInsuranceSeqID);
        }//end of else if(strSubLink=="Insurance")
        else if(strSubLink.equals("Group Registration"))
        {
         	alSearchParams.add("COR");
            alSearchParams.add(lGroupRegSeqId);
        }// end of else if(strSubLink.equals("Group Registration"))

        //request.getSession().setAttribute("searchparam",alSearchParams);
        return alSearchParams;
    }//end of populateSearchCriteria(DynaActionForm searchContactForm,HttpServletRequest request, Long lHospitalSeqId, Long lInsuranceSeqID) throws TTKException

  
  
    private ContactManager getContactManagerObject() throws TTKException
    {
        ContactManager contactManager = null;
        try
        {
            if(contactManager == null)
            {
                InitialContext ctx = new InitialContext();
                contactManager = (ContactManager) ctx.lookup("java:global/TTKServices/business.ejb3/ContactManagerBean!com.ttk.business.empanelment.ContactManager");
            }//end of if(contactManager == null)
        }//end of try
        catch(Exception exp)
        {
            throw new TTKException(exp, "contact");
        }//end of catch
        return contactManager;
    }//end getContactManagerObject()
}// end of HospitalContactAction class
