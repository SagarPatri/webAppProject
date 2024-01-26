package com.ttk.action.enrollment;

import java.util.ArrayList;

import javax.naming.InitialContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.DynaActionForm;

import com.ttk.action.TTKAction;
import com.ttk.action.table.TableData;
import com.ttk.business.enrollment.MemberManager;
import com.ttk.common.TTKCommon;
import com.ttk.common.exception.TTKException;
import com.ttk.dto.enrollment.ReciverMailIdVO;
import com.ttk.dto.enrollment.ReciverMailList;

import formdef.plugin.util.FormUtils;

public class ConfigureReciverMailID extends TTKAction
{
	private static final String strReciverDetail="addReciever";
	private static final String stremailsList="emailsList";
	
	
public ActionForward doAdd(ActionMapping mapping, ActionForm form,HttpServletRequest request, HttpServletResponse response)
		throws Exception {
	 DynaActionForm reciverFrm=(DynaActionForm) form;
	request.setAttribute("reciverFrm", reciverFrm);
	return mapping.findForward(strReciverDetail);
}

public ActionForward doEditReciverMailData(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response) throws Exception {
	try
	{
		//setLinks(request);
		log.info("Inside MemberUploadAction doEditReciverMailData");
		 MemberManager memberManager=this.getMemberManager();
		 DynaActionForm reciverFrm=(DynaActionForm) form;
		
		TableData  tableData =TTKCommon.getTableData(request);
		
		ReciverMailList reciverMailList=null;
		
		if(!(TTKCommon.checkNull(request.getParameter("rownum")).equals("")))
		{
			reciverMailList=(ReciverMailList)tableData.getRowInfo(Integer.parseInt((String)request.getParameter("rownum")));
			ReciverMailIdVO recMailIdVO=memberManager.getReciverMailData(reciverMailList.getReciverSeqID());
			    reciverFrm=(DynaActionForm) FormUtils.setFormValues("reciverFrm", recMailIdVO,this , mapping, request);
			    request.setAttribute("reciverFrm", reciverFrm);
				
		}			
		return mapping.findForward(strReciverDetail);
	}//end of try
	catch(TTKException expTTK)
	{
		return this.processExceptions(request,mapping,expTTK);
	}//end of catch(ETTKException expTTK)
	catch(Exception exp)
	{
		return this.processExceptions(request,mapping,new TTKException(exp,"product"));
	}//end of catch(Exception exp)
	
}

public ActionForward doSaveReciverMails(ActionMapping mapping, ActionForm form,
		HttpServletRequest request, HttpServletResponse response)
		throws Exception {
	try
	{
	String responceMsg = null;
	 DynaActionForm reciverFrm=(DynaActionForm) form;
	 MemberManager memberManager=this.getMemberManager();
	ReciverMailIdVO recMailIdVO=(ReciverMailIdVO) FormUtils.getFormValues(reciverFrm, this, mapping, request);
	recMailIdVO.setAddedBY(TTKCommon.getUserSeqId(request));
	Long updateFlag=recMailIdVO.getReciverSeqID();
	Long seqId=memberManager.saveReciverMailData(recMailIdVO);
	if(updateFlag!=null) responceMsg="Receiver Details Updated Succesfully";
	else {if(seqId!=0) responceMsg="Receiver Details Saved Succesfully";}
    recMailIdVO=memberManager.getReciverMailData(seqId);
    reciverFrm=(DynaActionForm) FormUtils.setFormValues("reciverFrm", recMailIdVO,this , mapping, request);
    request.setAttribute("reciverFrm", reciverFrm);
	request.setAttribute("successMsg", responceMsg);
	return mapping.findForward(strReciverDetail);
	}
	catch(TTKException expTTK)
	{
		return this.processExceptions(request,mapping,expTTK);
	}//end of catch(ETTKException expTTK)
	catch(Exception exp)
	{
		return this.processExceptions(request,mapping,new TTKException(exp,"product"));
	}//end of catch(Exception exp)
	
}

public ActionForward doReset(ActionMapping mapping, ActionForm form,
		HttpServletRequest request, HttpServletResponse response)
		throws Exception {
	try{
	 DynaActionForm reciverFrm=(DynaActionForm) form;
	 reciverFrm.initialize(mapping);
	request.setAttribute("reciverFrm", reciverFrm);
	return mapping.findForward(strReciverDetail);
	}
	catch(Exception exp)
	{
		return this.processExceptions(request,mapping,new TTKException(exp,"strReciverDetail"));
	}//end of catch(Exception exp)
	
}



public ActionForward doClose(ActionMapping mapping, ActionForm form,
		HttpServletRequest request, HttpServletResponse response)
		throws Exception {
	request.setAttribute("reciverFrm", form);
	return mapping.findForward(stremailsList);
}

private MemberManager getMemberManager() throws TTKException
{
    MemberManager memberManager = null;
    try
    {
        if(memberManager == null)
        {
            InitialContext ctx = new InitialContext();
            memberManager = (MemberManager) ctx.lookup("java:global/TTKServices/business.ejb3/MemberManagerBean!com.ttk.business.enrollment.MemberManager");
        }//end if
    }//end of try
    catch(Exception exp)
    {
        throw new TTKException(exp, "memberdetail");
    }//end of catch
    return memberManager;
}//end getMemberManager()

}
