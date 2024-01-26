/**
 * @ (#) TDSTaxAttributesAction.java July 29th, 2009
 * Project       : TTK HealthCare Services
 * File          : TDSTaxAttributesAction.java
 * Author        : Balakrishna Erram
 * Company       : Span Systems Corporation
 * Date Created  : July 29th, 2009
 *
 * @author       : Balakrishna Erram
 * Modified by   :
 * Modified date :
 * Reason        :
 */

package com.ttk.action.administration;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;

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
import com.ttk.business.administration.ConfigurationManager;
import com.ttk.common.TTKCommon;
import com.ttk.common.exception.TTKException;
import com.ttk.dto.administration.TDSCateRateDetailVO;
import com.ttk.dto.administration.TDSCategoryRateVO;

import formdef.plugin.util.FormUtils;

/**
 * This class is used for displaying the details of Configure Properties.
 */
public class TDSTaxAttributesAction extends TTKAction{

	private static final Logger log = Logger.getLogger( TDSTaxAttributesAction.class );

	// Forward Path
	private static final String strClose="close";
	private static final String strTDSAttributes="tdsattributes";
	private static final String strTDSAttrRateexp="tdsattrrateexp";

	/**
	 * This method is used to navigate to detail screen to edit selected record.
	 * Finally it forwards to the appropriate view based on the specified forward mappings
	 *
	 * @param mapping The ActionMapping used to select this instance
	 * @param form The optional ActionForm bean for this request (if any)
	 * @param request The HTTP request we are processing
	 * @param response The HTTP response we are creating
	 * @return ActionForward Where the control will be forwarded, after this request is processed
	 * @throws Exception if any error occurs
	 */
	public ActionForward doView(ActionMapping mapping,ActionForm form,HttpServletRequest request,
															HttpServletResponse response) throws TTKException{
		try{
			setLinks(request);
			ConfigurationManager tdsConfManager=this.getConfManager();    //get the business object to call DAO
			DynaActionForm frmtdsRateList=(DynaActionForm)request.getSession().getAttribute("frmtdsRateList");
			String strSubCatTypeID = frmtdsRateList.getString("tdsCatTypeID");
			String strTDSSubCatName = frmtdsRateList.getString("tdsSubCatName");
			StringBuffer sbfCaption=new StringBuffer();
			sbfCaption.append("TDS Attributes - ");
			log.info("Sub Cat Type ID is :"+strSubCatTypeID);
			TableData tableData =(TableData) request.getSession().getAttribute("tableData");
			TDSCategoryRateVO tdsCatRateVO1= new TDSCategoryRateVO();
			if(!(TTKCommon.checkNull(request.getParameter("rownum")).equals("")))
            {
				log.info("Row number :"+request.getParameter("rownum"));
				tdsCatRateVO1=(TDSCategoryRateVO)tableData.getRowInfo(Integer.parseInt((String)
                		request.getParameter("rownum")));
            }//end of if(!(TTKCommon.checkNull(request.getParameter("rownum")).equals("")))
			DynaActionForm frmTDSAttrDetails=(DynaActionForm)form;
			String strFromDate = TTKCommon.getFormattedDate(tdsCatRateVO1.getRevDateFrom());
			String strToDate = TTKCommon.getFormattedDate(tdsCatRateVO1.getRevDateTo());
			ArrayList<Object> alRevisionList  = new ArrayList<Object>();
			alRevisionList.add(strSubCatTypeID);
			alRevisionList.add(strFromDate);
			alRevisionList.add(strToDate);

			TDSCategoryRateVO tdsCatRateVO=new TDSCategoryRateVO();
			tdsCatRateVO= tdsConfManager.getSelCatRateList(alRevisionList);
			sbfCaption.append(" [ ").append(strTDSSubCatName).append(" ]");
			float fTdsTotalAmt = 0;
			if(tdsCatRateVO==null)
			{
				tdsCatRateVO=new TDSCategoryRateVO();
			}//end of if(preAuthTariffVO==null)
			else
			{
				ArrayList<Object> alTDSAttriDetailInfo =tdsCatRateVO.getTDSCateRateDetailVOList();
				if(alTDSAttriDetailInfo!=null && alTDSAttriDetailInfo.size()>0)
				{
					for(int i=0; i<alTDSAttriDetailInfo.size(); i++)
					{
						TDSCateRateDetailVO  tdsCateRateDetVO= (TDSCateRateDetailVO)alTDSAttriDetailInfo.get(i);
						if(tdsCateRateDetVO.getApplRatePerc()!=null)
						{
							fTdsTotalAmt =fTdsTotalAmt+tdsCateRateDetVO.getApplRatePerc().floatValue();
						}//end of if(tdsCateRateDetVO.getApplRatePerc()!=null)
					}//end of for(int i=0; i<alTDSAttriDetailInfo.size(); i++)
					frmTDSAttrDetails = (DynaActionForm)FormUtils.setFormValues("frmTDSAttrDetails",
							tdsCatRateVO,this,mapping,request);

					frmTDSAttrDetails.set("tdscatratelist",(TDSCateRateDetailVO[])alTDSAttriDetailInfo.toArray(
							new TDSCateRateDetailVO[0]));
				}//end of  if(alTariffDetailInfo!=null && alTariffDetailInfo.size()>0)
			}//end of else
			frmTDSAttrDetails.set("totTDSAmount",Float.toString(fTdsTotalAmt));
			frmTDSAttrDetails.set("revDateFrom",strFromDate);
			frmTDSAttrDetails.set("revDateTo",strToDate);
			frmTDSAttrDetails.set("caption",sbfCaption.toString());
			frmTDSAttrDetails.set("tdsCatTypeID",strSubCatTypeID);
			request.getSession().setAttribute("frmTDSAttrDetails",frmTDSAttrDetails);
			return this.getForward(strTDSAttributes,mapping,request);
		}//end of try
		catch(TTKException expTTK)
		{
			return this.processExceptions(request, mapping, expTTK);
		}//end of catch(TTKException expTTK)
		catch(Exception exp)
		{
			return this.processExceptions(request, mapping, new TTKException(exp, strTDSAttrRateexp));
		}//end of catch(Exception exp)
	}//end of doView(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response)

	/**
	 * This method is used to navigate to detail screen to add a record.
	 * Finally it forwards to the appropriate view based on the specified forward mappings
	 *
	 * @param mapping The ActionMapping used to select this instance
	 * @param form The optional ActionForm bean for this request (if any)
	 * @param request The HTTP request we are processing
	 * @param response The HTTP response we are creating
	 * @return ActionForward Where the control will be forwarded, after this request is processed
	 * @throws Exception if any error occurs
	 */
	public ActionForward doAdd(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response) throws TTKException
	{
		try
		{
			setLinks(request);
			log.info("Inside TDSTaxConfiguration doAdd");
			ConfigurationManager tdsConfManager=this.getConfManager();    //get the business object to call DAO
			DynaActionForm frmtdsRateList=(DynaActionForm)request.getSession().getAttribute("frmtdsRateList");
			String strSubCatTypeID = frmtdsRateList.getString("tdsCatTypeID");
			String strTDSSubCatName = frmtdsRateList.getString("tdsSubCatName");
			StringBuffer sbfCaption=new StringBuffer();
			sbfCaption.append("TDS Attributes - ");
			log.info("Sub Cat Type ID is :"+strSubCatTypeID);
			DynaActionForm frmTDSAttrDetails=(DynaActionForm)form;
			log.info("dyna action form info "+frmTDSAttrDetails);
			TDSCategoryRateVO tdsCatRateVO=new TDSCategoryRateVO();
			ArrayList<Object> alRevisionList  = new ArrayList<Object>();
			alRevisionList.add("");
			alRevisionList.add("");
			alRevisionList.add("");
			tdsCatRateVO= tdsConfManager.getSelCatRateList(alRevisionList);
			sbfCaption.append(" [ ").append(strTDSSubCatName).append(" ]");
			if(tdsCatRateVO==null)
			{
				tdsCatRateVO=new TDSCategoryRateVO();
			}//end of if(preAuthTariffVO==null)
			else
			{
				ArrayList<Object> alTDSAttriDetailInfo =tdsCatRateVO.getTDSCateRateDetailVOList();

				frmTDSAttrDetails = (DynaActionForm)FormUtils.setFormValues("frmTDSAttrDetails",
						tdsCatRateVO,this,mapping,request);
				if(alTDSAttriDetailInfo!=null && alTDSAttriDetailInfo.size()>0)
				{
					frmTDSAttrDetails.set("tdscatratelist",(TDSCateRateDetailVO[])alTDSAttriDetailInfo.toArray(
																							new TDSCateRateDetailVO[0]));
					frmTDSAttrDetails.set("TDSCateRateDetailVOList",(TDSCateRateDetailVO[])alTDSAttriDetailInfo.toArray(
							new TDSCateRateDetailVO[0]));
				}//end of  if(alTariffDetailInfo!=null && alTariffDetailInfo.size()>0)

			}//end of else
			frmTDSAttrDetails.set("caption",sbfCaption.toString());
			frmTDSAttrDetails.set("tdsCatTypeID",strSubCatTypeID);
			request.getSession().setAttribute("frmTDSAttrDetails",frmTDSAttrDetails);
			return this.getForward(strTDSAttributes, mapping, request);
		}//end of try
		catch(TTKException expTTK)
		{
			return this.processExceptions(request, mapping, expTTK);
		}//end of catch(TTKException expTTK)
		catch(Exception exp)
		{
			return this.processExceptions(request, mapping, new TTKException(exp,strTDSAttrRateexp));
		}//end of catch(Exception exp)
	}//end of doAdd(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response)

    /**
	 * This method is used to navigate to  detail screen to edit selected record.
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
															HttpServletResponse response) throws TTKException{
		try{
			log.info("Inside the doViewTaxPerc method of TDSTaxConfigurationAction");
			setLinks(request);
			ConfigurationManager tdsConfManager=this.getConfManager();    //get the business object to call DAO
			DynaActionForm frmtdsRateList=(DynaActionForm)request.getSession().getAttribute("frmtdsRateList");
			String strSubCatTypeID = frmtdsRateList.getString("tdsCatTypeID");

			DynaActionForm frmTDSAttrDetails=(DynaActionForm)request.getSession().getAttribute("frmTDSAttrDetails");
			String strCaption =(String) frmTDSAttrDetails.get("caption");
			TDSCategoryRateVO tdsCatRateVO= (TDSCategoryRateVO)FormUtils.getFormValues(frmTDSAttrDetails,
            														"frmTDSAttrDetails",this, mapping, request);
            Long[] lngTdsCatRateSeqID= (Long[])frmTDSAttrDetails.get("selTdsCatRateSeqIDs");
            String strRevDateFrom = (String)frmTDSAttrDetails.get("revDateFrom");
            String strRevDateTo = (String)frmTDSAttrDetails.get("revDateTo");
            //Date dtRevDateTo = (Date)frmTDSAttrDetails.get("revDateTo");
            Date dtRevDateTo = TTKCommon.getUtilDate(strRevDateTo);

            //Tariff values
            String[] strTdsAtrTypeID = request.getParameterValues("tdsAtrTypeID");
            String[] strApplRatePerc = request.getParameterValues("applRatePerc");
            //String strtotTDSAmount = request.getParameter("totTDSAmount");

            ArrayList<Object> alTariffDetail = new ArrayList<Object>();
            if(strApplRatePerc !=null && strApplRatePerc.length>0)
            {
            	for(int i=0;i<strApplRatePerc.length;i++)
                {
                	TDSCateRateDetailVO tdsCatRateDetVO = new TDSCateRateDetailVO();
                	tdsCatRateDetVO.setTdsCatRateSeqID(lngTdsCatRateSeqID[i]);
                	tdsCatRateDetVO.setTdsAtrTypeID(strTdsAtrTypeID[i]);
                	if(TTKCommon.checkNull(strApplRatePerc[i])!="")
                	{
                		tdsCatRateDetVO.setApplRatePerc(new BigDecimal(TTKCommon.checkNull(strApplRatePerc[i])));
                	}//end of if(TTKCommon.checkNull(strApplRatePerc[i])!="")
                	else
                	{
                		tdsCatRateDetVO.setApplRatePerc(new BigDecimal(0.00));
                	}//end of else
                	alTariffDetail.add(tdsCatRateDetVO);
                }//end of for(int i=0;i<lngTdsCatRateSeqID.length;i++)
            }//end of if(lngTdsCatRateSeqID !=null && lngTdsCatRateSeqID.length>0)
            tdsCatRateVO.setTDSCateRateDetailVOList(alTariffDetail);
            tdsCatRateVO.setReviseDateFrom(strRevDateFrom);
            tdsCatRateVO.setRevDateTo(dtRevDateTo);
            tdsCatRateVO.setTdsSubCatTypeID(strSubCatTypeID);
            tdsCatRateVO.setUpdatedBy(TTKCommon.getUserSeqId(request));
            int iResult=tdsConfManager.saveCatRate(tdsCatRateVO);
            if(iResult>0){
            	if(frmTDSAttrDetails.get("revDateFrom")!=null && !frmTDSAttrDetails.get("revDateFrom").equals(""))
                {
                    request.setAttribute("updated","message.savedSuccessfully");
                }//end of if(frmTDSAttrDetails.get("tdsCatRateSeqID")!=null && !frmTDSAttrDetails.get("tdsCatRateSeqID").equals(""))
                else
                {
                    request.setAttribute("updated","message.addedSuccessfully");
                }//end of else
            }//end of if(iResult>0)

            ArrayList<Object> alRevisionList  = new ArrayList<Object>();
			alRevisionList.add(strSubCatTypeID);
			alRevisionList.add(strRevDateFrom);
			alRevisionList.add(strRevDateTo);
			tdsCatRateVO= tdsConfManager.getSelCatRateList(alRevisionList);
			float fTdsTotalAmt=0.0f;
			float fBaseAmt=0.0f;
			if(tdsCatRateVO==null)
			{
				tdsCatRateVO=new TDSCategoryRateVO();
			}//end of if(preAuthTariffVO==null)
			else
			{
				ArrayList<Object> alTDSAttriDetailInfo =tdsCatRateVO.getTDSCateRateDetailVOList();
				if(alTDSAttriDetailInfo!=null && alTDSAttriDetailInfo.size()>0)
				{
					for(int i=0; i<alTDSAttriDetailInfo.size(); i++)
					{
						TDSCateRateDetailVO  tdsCateRateDetVO= (TDSCateRateDetailVO)alTDSAttriDetailInfo.get(i);
						/*if(i==0)
						{
							if(tdsCateRateDetVO.getApplRatePerc().intValue()==0)
							{
								TTKException expTTK = new TTKException();
								expTTK.setMessage("error.administration.basetax.required");
								throw expTTK;
							}//end of if(PreAuthWebBoardHelper.checkWebBoardId(request)==null)

							fBaseAmt = tdsCateRateDetVO.getApplRatePerc().floatValue();
						}//end of if(i==0)
*/						if(tdsCateRateDetVO.getApplRatePerc()!=null)
						{
							fBaseAmt = tdsCateRateDetVO.getApplRatePerc().floatValue();
							fTdsTotalAmt =fTdsTotalAmt+tdsCateRateDetVO.getApplRatePerc().floatValue();
						}//end of if(TTKCommon.checkNull(tdsCateRateDetVO.getApplRatePerc())!="")
					}//end of for(int i=0; i<alTDSAttriDetailInfo.size(); i++)
					frmTDSAttrDetails = (DynaActionForm)FormUtils.setFormValues("frmTDSAttrDetails",
							tdsCatRateVO,this,mapping,request);

					frmTDSAttrDetails.set("tdscatratelist",(TDSCateRateDetailVO[])alTDSAttriDetailInfo.toArray(
							new TDSCateRateDetailVO[0]));
				}//end of  if(alTariffDetailInfo!=null && alTariffDetailInfo.size()>0)
			}//end of else
			frmTDSAttrDetails.set("totTDSAmount",Float.toString(fTdsTotalAmt));
			frmTDSAttrDetails.set("baseTaxAmt",Float.toString(fBaseAmt));

			frmTDSAttrDetails.set("caption",strCaption);
			frmTDSAttrDetails.set("tdsCatTypeID",strSubCatTypeID);
			request.getSession().setAttribute("frmTDSAttrDetails",frmTDSAttrDetails);
            return this.getForward(strTDSAttributes,mapping,request);
		}//end of try
		catch(TTKException expTTK)
		{
			return this.processExceptions(request, mapping, expTTK);
		}//end of catch(TTKException expTTK)
		catch(Exception exp)
		{
			return this.processExceptions(request, mapping, new TTKException(exp,strTDSAttrRateexp));
		}//end of catch(Exception exp)
	}//end of doSave(ActionMapping mapping,ActionForm form,HttpServletRequest request,
		//HttpServletResponse response)


	/**
	 * This method is used to navigate to detail screen to add a record.
	 * Finally it forwards to the appropriate view based on the specified forward mappings
	 *
	 * @param mapping The ActionMapping used to select this instance
	 * @param form The optional ActionForm bean for this request (if any)
	 * @param request The HTTP request we are processing
	 * @param response The HTTP response we are creating
	 * @return ActionForward Where the control will be forwarded, after this request is processed
	 * @throws Exception if any error occurs
	 */
	public ActionForward doReset(ActionMapping mapping,ActionForm form,HttpServletRequest request,
																HttpServletResponse response) throws TTKException
	{
		try
		{
			log.info("Inside the doReset method of TDSTaxAttributesAction");
			setLinks(request);
			ConfigurationManager tdsConfManager=this.getConfManager();    //get the business object to call DAO
			DynaActionForm frmtdsRateList=(DynaActionForm)request.getSession().getAttribute("frmtdsRateList");
			String strSubCatTypeID = frmtdsRateList.getString("tdsCatTypeID");
			String strTDSSubCatName = frmtdsRateList.getString("tdsSubCatName");
			StringBuffer sbfCaption=new StringBuffer();
			sbfCaption.append("TDS Attributes - ");
			log.info("Sub Cat Type ID is :"+strSubCatTypeID);
			DynaActionForm frmTDSAttrDetails=(DynaActionForm)form;
			String strRevDateFrom = (String)frmTDSAttrDetails.get("revDateFrom");
            String strRevDateTo = (String)frmTDSAttrDetails.get("revDateTo");
            //Date dtRevDateTo = (Date)frmTDSAttrDetails.get("revDateTo");
            float fTdsTotalAmt = 0;

			ArrayList<Object> alRevisionList  = new ArrayList<Object>();
			alRevisionList.add(strSubCatTypeID);
			alRevisionList.add(strRevDateFrom);
			alRevisionList.add(strRevDateTo);
			sbfCaption.append(" [ ").append(strTDSSubCatName).append(" ]");
			TDSCategoryRateVO tdsCatRateVO=new TDSCategoryRateVO();
			if(frmTDSAttrDetails.get("revDateFrom")!=null && !((String)frmTDSAttrDetails.get("revDateFrom")).equals(""))
			{
				tdsCatRateVO= tdsConfManager.getSelCatRateList(alRevisionList);

				ArrayList<Object> alTDSAttriDetailInfo =tdsCatRateVO.getTDSCateRateDetailVOList();
				if(alTDSAttriDetailInfo!=null && alTDSAttriDetailInfo.size()>0)
				{
					for(int i=0; i<alTDSAttriDetailInfo.size(); i++)
					{
						TDSCateRateDetailVO  tdsCateRateDetVO= (TDSCateRateDetailVO)alTDSAttriDetailInfo.get(i);
						if(tdsCateRateDetVO.getApplRatePerc()!=null)
						{
							fTdsTotalAmt =fTdsTotalAmt+tdsCateRateDetVO.getApplRatePerc().floatValue();
						}//end of if(tdsCateRateDetVO.getApplRatePerc()!=null)
					}//end of for(int i=0; i<alTDSAttriDetailInfo.size(); i++)
					frmTDSAttrDetails = (DynaActionForm)FormUtils.setFormValues("frmTDSAttrDetails",
							tdsCatRateVO,this,mapping,request);

					frmTDSAttrDetails.set("tdscatratelist",(TDSCateRateDetailVO[])alTDSAttriDetailInfo.toArray(
							new TDSCateRateDetailVO[0]));
				}//end of  if(alTariffDetailInfo!=null && alTariffDetailInfo.size()>0)
			}//end of if(frmAddtdsConfiguration.get("tdsSubCatTypeID")!=null && !((String)frmAddtdsConfiguration.
					//get("tdsSubCatTypeID")).equals(""))
			frmTDSAttrDetails.set("totTDSAmount",Float.toString(fTdsTotalAmt));
			frmTDSAttrDetails.set("revDateFrom",strRevDateFrom);
			frmTDSAttrDetails.set("revDateTo",strRevDateTo);
			frmTDSAttrDetails.set("caption",sbfCaption.toString());
			frmTDSAttrDetails.set("tdsCatTypeID",strSubCatTypeID);
			request.getSession().setAttribute("frmTDSAttrDetails",frmTDSAttrDetails);
			return this.getForward(strTDSAttributes,mapping,request);
		}//end of try
		catch(TTKException expTTK)
		{
			return this.processExceptions(request, mapping, expTTK);
		}//end of catch(TTKException expTTK)
		catch(Exception exp)
		{
			return this.processExceptions(request, mapping, new TTKException(exp,strTDSAttrRateexp));
		}//end of catch(Exception exp)
	}//end of doReset(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response)

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
	public ActionForward doClose(ActionMapping mapping,ActionForm form,HttpServletRequest request,
												   HttpServletResponse response) throws TTKException{
		try{
			log.info("Inside the doClose method of ConfPropertiesAction");
			setLinks(request);
			ConfigurationManager tdsConfManager=this.getConfManager();
			DynaActionForm frmtdsRateList=(DynaActionForm)request.getSession().getAttribute("frmtdsRateList");
			String strTDSSubCatTypeID = frmtdsRateList.getString("tdsCatTypeID");
			TableData tableData =(TableData) request.getSession().getAttribute("tableData");
			ArrayList<Object> altdsCatRateList= tdsConfManager.getSelectRevisionList(strTDSSubCatTypeID);
			tableData.setData(altdsCatRateList,"search");
			request.getSession().setAttribute("tableData",tableData);
			frmtdsRateList.set("tdsCatTypeID", strTDSSubCatTypeID);
			request.getSession().setAttribute("frmtdsRateList",frmtdsRateList);
			return this.getForward(strClose, mapping, request);
		}//end of try
		catch(TTKException expTTK)
		{
			return this.processExceptions(request, mapping, expTTK);
		}//end of catch(TTKException expTTK)
		catch(Exception exp)
		{
			return this.processExceptions(request, mapping, new TTKException(exp,strTDSAttrRateexp));
		}//end of catch(Exception exp)
	}//end of doClose(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response)

    /**
	 * Returns the TDSConfigurationManager session object for invoking methods on it.
	 * @return TDSConfigurationManager session object which can be used for method invokation
	 * @exception throws TTKException
	 */
	private ConfigurationManager getConfManager() throws TTKException
	{
		ConfigurationManager ConfManager = null;
		try
		{
			if(ConfManager == null)
			{
				InitialContext ctx = new InitialContext();
				ConfManager = (ConfigurationManager) ctx.lookup("java:global/TTKServices/business.ejb3/ConfigurationManagerBean!com.ttk.business.administration.ConfigurationManager");
			}//end if
		}//end of try
		catch(Exception exp)
		{
			throw new TTKException(exp, strTDSAttrRateexp);
		}//end of catch
		return ConfManager;
	}//end getTDSConfManager()

}//end of TDSTaxAttributesAction class