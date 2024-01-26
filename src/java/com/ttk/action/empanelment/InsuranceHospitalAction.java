/*
 * Created for Associate Hospitals to a Insurance Company
 * 
 */


package com.ttk.action.empanelment;
import java.util.ArrayList;

import com.ttk.action.TTKAction;
import com.ttk.business.empanelment.GroupRegistrationManager;
import com.ttk.business.empanelment.InsuranceManager;
import com.ttk.common.TTKCommon;
import com.ttk.common.exception.TTKException;
import com.ttk.dto.empanelment.InsuranceDetailVO;
import com.ttk.dto.empanelment.InsuranceVO;
import com.ttk.dto.empanelment.NotificationInfoVO;
import com.ttk.dto.empanelment.NotifyDetailVO;

import formdef.plugin.util.FormUtils;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;

import javax.naming.InitialContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.DynaActionForm;



public class InsuranceHospitalAction extends TTKAction{
	
	//Forward paths
	private static final String strMailNoticationDetails = "mailnotificationdetails"; 
	private static final String strMailNotifiError = "mailnotification";
	private static final String strSaveMailNotificationDetails="savemailnotificationdetails";
	private static final String strCloseMailNotification = "close";
	
	private static final String strSearchHosp	=	"searchHospital"; 
	
	public ActionForward doView(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response) throws Exception
	{
		try{
			log.info("Inside doView method of InsuranceHospitalAction");
			setLinks(request);
			DynaActionForm frmNotifiDetails = (DynaActionForm)form;
			InsuranceDetailVO insDetailVO = new InsuranceDetailVO();
			ArrayList alAssocNotifyList = new ArrayList();
			ArrayList alUnAssocNotifyList = new ArrayList();
			NotificationInfoVO notificationInfoVO=null;
			notificationInfoVO = new NotificationInfoVO();
			//initialize the form bean
			frmNotifiDetails.initialize(mapping);
            InsuranceManager insuranceObject=(InsuranceManager)this.getInsuranceManagerObject();
          //get the head off seq id from webboard
            /*Long lngHeadOffInsSeqId=null;
            lngHeadOffInsSeqId=new Long(TTKCommon.getWebBoardId(request));//get the web board id
          */
            //get the minimum info from the session to get all the details of company to edit
            // get the minimum info of the selected office to edited
            InsuranceVO  insuranceVO = (InsuranceVO)request.getSession().getAttribute("SelectedOffice");
           
            //call the DAO to get all details from the minimum info                                                                                                                                                                      
            notificationInfoVO = insuranceObject.getMailNotificationList(insuranceVO.getInsuranceSeqID());
            frmNotifiDetails = (DynaActionForm)FormUtils.setFormValues("frmNotifiDetails",notificationInfoVO, this, mapping, request);
			alAssocNotifyList = notificationInfoVO.getAssocNotifyList();
			alUnAssocNotifyList =	notificationInfoVO.getUnAssocNotifyList();
			//set the ArrayList data to new instance when listofroles fetch's null value
			if(alUnAssocNotifyList==null)
			{
				alUnAssocNotifyList=new ArrayList();
			}//end of if(alUnAssocNotifyList==null)
			if(alAssocNotifyList==null)
			{
				alAssocNotifyList=new ArrayList();
			}//end of if(alAssocNotifyList==null)
			frmNotifiDetails.set("associateNotifyList",alAssocNotifyList);
			frmNotifiDetails.set("unAssociateNotifyList",alUnAssocNotifyList);
			request.getSession().setAttribute("frmNotifiDetails",frmNotifiDetails);
            return this.getForward(strMailNoticationDetails, mapping, request);
		}
		catch(TTKException expTTK)
		{
			return this.processExceptions(request, mapping, expTTK);
		}
		catch(Exception exp)
		{
			return this.processExceptions(request, mapping, new TTKException(exp,strMailNotifiError));
		}
	}
	
	
	/**
	 * This method is used to add/update the record.
	 * Finally it forwards to the appropriate view based on the specified forward mappings
	 *
	 * @param mapping The ActionMapping used to select this instance
	 * @param form The optional ActionForm bean for this request (if any)
	 * @param request The HTTP request we are processing
	 * @param response The HTTP response we are creating
	 * @return ActionForward Where the control will be forwarded, after this request is processed
	 * @throws Exception if any error occurs
	 */
	public ActionForward doSave(ActionMapping mapping,ActionForm form,HttpServletRequest request,
			HttpServletResponse response) throws Exception{
		
		try{
			log.debug("Inside the doSave method of InsuranceHospitalAction");
			setLinks(request);
			InsuranceManager insuranceObject=(InsuranceManager)this.getInsuranceManagerObject();
			InsuranceVO  insuranceVO = (InsuranceVO)request.getSession().getAttribute("SelectedOffice");
			NotificationInfoVO notificationInfoVO=null;
			
			DynaActionForm frmNotifiDetails=(DynaActionForm)form;
			ArrayList alAssocNotifyList = new ArrayList();
			ArrayList alUnAssocNotifyList = new ArrayList();
			notificationInfoVO=new NotificationInfoVO();
			
			//TreeData treeData =(TreeData)request.getSession().getAttribute("treeData") ;
			//int iSelectedRoot= TTKCommon.getInt(request.getParameter("selectedroot"));
			//String strSelectedRoot = TTKCommon.checkNull(request.getParameter("selectedroot"));
			notificationInfoVO=(NotificationInfoVO)FormUtils.getFormValues(frmNotifiDetails, "frmNotifiDetails",this, mapping, request);
			//fetching roleID's from form to String array
			String strAssociatedNotifyList[] =(String[])frmNotifiDetails.get("selectedNotifyList");
			if(strAssociatedNotifyList != null)
			{
				for(int i=0; i < strAssociatedNotifyList.length; i++)
				{
					frmNotifiDetails.set("selectedNotifyList",strAssociatedNotifyList);
				}//end of for(int i=0; i < strAssociatedNotifyList.length; i++)
			}//end of if(strAssociatedRoles != null)
			
			notificationInfoVO.setInsuranceSeqID(insuranceVO.getInsuranceSeqID());
			notificationInfoVO.setAssocNotifyList(populateRoleObjects(strAssociatedNotifyList));
			// User ID from session
			notificationInfoVO.setUpdatedBy(TTKCommon.getUserSeqId(request));
			int iResult = insuranceObject.saveMailNotificationInfo(notificationInfoVO);
				
			if(iResult!=0) {
				request.setAttribute("updated","message.saved");
				notificationInfoVO = insuranceObject.getMailNotificationList(insuranceVO.getInsuranceSeqID());
				frmNotifiDetails = (DynaActionForm)FormUtils.setFormValues("frmNotifiDetails",
						notificationInfoVO, this, mapping, request);
				
				alAssocNotifyList = notificationInfoVO.getAssocNotifyList();
				alUnAssocNotifyList =	notificationInfoVO.getUnAssocNotifyList();
				//set the ArrayList data to new instance when listofroles fetch's null value
				if(alUnAssocNotifyList==null)
				{
					alUnAssocNotifyList=new ArrayList();
				}//end of if(alUnAssocNotifyList==null)
				if(alAssocNotifyList==null)
				{
					alAssocNotifyList=new ArrayList();
				}//end of if(alAssocNotifyList==null)
			}//end of if(iResult!=0)
			
			frmNotifiDetails.set("associateNotifyList",alAssocNotifyList);
			frmNotifiDetails.set("unAssociateNotifyList",alUnAssocNotifyList);
			request.getSession().setAttribute("frmNotifiDetails",frmNotifiDetails);			
		}
		catch(TTKException expTTK)
		{
			return this.processExceptions(request, mapping, expTTK);
		}//end of catch(TTKException expTTK)
		catch(Exception exp)
		{
			return this.processExceptions(request, mapping, new TTKException(exp,strMailNotifiError));
		}//end of catch(Exception exp)
		return this.getForward(strSaveMailNotificationDetails, mapping, request);
	}
		
	
	  /**
     * This method is used to navigate to previous screen when closed button is clicked.
     * Finally it forwards to the appropriate view based on the specified forward mappings
     *
     * @param mapping The ActionMapping used to select this instance
     * @param form The optional ActionForm bean for this request (if any)
     * @param request The HTTP request we are processing
     * @param response The HTTP response we are creating
     * @return ActionForward Where the control will be forwarded, after this request is processed
     * @throws Exception if any error occurs
     */
    public ActionForward doClose(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response) throws TTKException
    {
    	try
    	{
    		log.debug("Inside the doClose method of InsuranceHospitalAction");
    		setLinks(request);
    		return this.getForward(strCloseMailNotification, mapping, request);
    	}
    	catch(TTKException expTTK)
		{
			return this.processExceptions(request, mapping, expTTK);
		}//end of catch(TTKException expTTK)
		catch(Exception exp)
		{
			return this.processExceptions(request, mapping, new TTKException(exp,strMailNotifiError));
		}//end of catch(Exception exp)    	
    }
	
	
	/**
	 * Returns the ArrayList roleVO object for invoking methods on it.
	 * @return ArrayList roleVO  object which can be used for method invokation
	 * @exception throws TTKException
	 */
	private ArrayList populateRoleObjects(String strAssociatedNotifyList[]) throws TTKException
	{
		ArrayList<Object> alAssocNotifyList = new ArrayList<Object>();
		try
		{
			for(int i=0; i<strAssociatedNotifyList.length; i++)
			{
				NotifyDetailVO notifyDetailVO = new NotifyDetailVO();
				notifyDetailVO.setMsgID(strAssociatedNotifyList[i]);
				alAssocNotifyList.add(notifyDetailVO);
			}//end of for 
		}//end of try
		catch(Exception exp)
		{
			throw new TTKException(exp, strMailNotifiError);
		}//end of catch
		return (alAssocNotifyList);
	}//end of populateRoleObjects(String strAssociatedRoles[])
	
	
	
	 /**
     * Returns the InsuranceManager session object for invoking methods on it.
     * @return InsuranceManager session object which can be used for method invokation
     * @exception throws TTKException
     */
    private InsuranceManager getInsuranceManagerObject() throws TTKException
	{
		InsuranceManager insuremanager = null;
		try
		{
			if(insuremanager == null)
			{
				InitialContext ctx = new InitialContext();
				insuremanager = (InsuranceManager) ctx.lookup("java:global/TTKServices/business.ejb3/InsuranceManagerBean!com.ttk.business.empanelment.InsuranceManager");
			}//end if
		}//end of try
		catch(Exception exp)
		{
			throw new TTKException(exp, strMailNotifiError);
		}//end of catch
		return insuremanager;
	} // end of private InsuranceManager getInsManagerObject() throws TTKException

    
    
    //intX starts Here
    
    public ActionForward doViewSearchHospital(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response) throws TTKException
    {
    	try
    	{
    		log.debug("Inside the doViewSearchHospital method of InsuranceHospitalAction");
    		setLinks(request);
    		return this.getForward(strSearchHosp, mapping, request);
    	}
    	catch(TTKException expTTK)
		{
			return this.processExceptions(request, mapping, expTTK);
		}//end of catch(TTKException expTTK)
		catch(Exception exp)
		{
			return this.processExceptions(request, mapping, new TTKException(exp,strMailNotifiError));
		}//end of catch(Exception exp)    	
    }
	
	

}
