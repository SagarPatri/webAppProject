<%
/** @ (#) companysummary.jsp 10th Nov 2005
 * Project     : TTK Healthcare Services
 * File        : brokersummary.jsp
 * Author      : Nagaraj D V
 * Company     : Span Systems Corporation
 * Date Created: 10th Nov 2005
 *
 * @author 	 : Nagaraj D V
 * Modified by   :
 * Modified date :
 * Reason        :
 *
 */
 %>
 <%@ taglib uri="/WEB-INF/tlds/ttk-tags.tld" prefix="ttk" %>
 <%@ taglib uri="/WEB-INF/tlds/struts-logic.tld" prefix="logic" %>
 <%@ taglib uri="/WEB-INF/tlds/struts-html.tld" prefix="html" %>
 <%@ page import="java.util.ArrayList, com.ttk.dto.empanelment.BrokerVO,com.ttk.common.TTKCommon" %>
 <%
 	BrokerVO headOffice = (BrokerVO)request.getSession().getAttribute("HeadOfficeBro");
  
 	ArrayList alRegionalOffice = null;
 	if(headOffice!=null)
 		alRegionalOffice=headOffice.getBranchList();
 	
 %>
<script language="javascript" src="/ttk/scripts/empanelment/brokersummary.js"></script>

<!-- S T A R T : Content/Form Area -->
	<html:form action="/BrokerDetailAction.do">

	<!-- S T A R T : Page Title -->
	<logic:empty name="ErrorDisplay">
		<table align="center" class="pageTitle" border="0" cellspacing="0" cellpadding="0">
		  <tr>
		    <td>Broker Details </td>
		    <td align="right" class="webBoard">&nbsp;<%@ include file="/ttk/common/toolbar.jsp" %></td>
		  </tr>
		</table>
	<!-- E N D : Page Title -->

	<div class="contentArea" id="contentArea">
	<html:errors/>
	<!-- S T A R T : Form Fields -->
	<table align="center" class="formContainer rcContainer" border="0" cellspacing="1" cellpadding="0">
		<tr><td class="fieldGroupHeader">Head Office: </td></tr>
	  	<tr><td>
			<div class="rcDiv">
				<table width="97%"  align="left" border="0" cellpadding="0" cellspacing="0" class="rcBorder">
			        <logic:empty name="HeadOfficeBro">
			        <!-- changebroker -->
			        
			        </logic:empty>
			        <logic:notEmpty name="HeadOfficeBro">
			        	<tr>
						  <td width="100%" align="left" class="rcText"> <%=headOffice.getBranchName()%> </td>
						  <td align="right" class="rcIcons">&nbsp;&nbsp;
						  <%
						  	if(TTKCommon.isAuthorized(request,"Add"))
						  	{
						  %>
						  		<a href="javascript:companyDetails('<%= headOffice.getInsuranceSeqID() %>', 'addcompany')"><img src="/ttk/images/AddIcon.gif" alt="Add new Regional Office" width="16" height="16" border="0" align="absmiddle"></a>&nbsp;&nbsp;<img src="/ttk/images/IconSeparator.gif" width="1" height="18" align="absmiddle">&nbsp;&nbsp;
						  <%
						  	}
						  %>
						  		<a href="javascript:companyDetails('<%= headOffice.getInsuranceSeqID() %>', 'editcompany')"><img src="/ttk/images/EditIcon.gif" alt="Edit Office" width="16" height="16" border="0" align="absmiddle"></a>&nbsp;&nbsp;<img src="/ttk/images/IconSeparator.gif" width="1" height="18" align="absmiddle">&nbsp;&nbsp;
						  	<!-- 	<a href="javascript:companyDetails('<%= headOffice.getInsuranceSeqID() %>', 'insproductlist')"><img src="/ttk/images/ProductIcon.gif" alt="Associate Products" width="16" height="16" border="0" align="absmiddle"></a>&nbsp;&nbsp;<img src="/ttk/images/IconSeparator.gif" width="1" height="18" align="absmiddle">&nbsp;&nbsp;  -->
						  		<a href="javascript:companyDetails('<%= headOffice.getInsuranceSeqID() %>', 'insurancecontactlist')"><img src="/ttk/images/ContactsIcon.gif" alt="Manage Contacts" width="16" height="16" border="0" align="absmiddle"></a>&nbsp;&nbsp;<img src="/ttk/images/IconSeparator.gif" width="1" height="18" align="absmiddle">&nbsp;&nbsp;
						  		<a href="javascript:companyDetails('<%= headOffice.getInsuranceSeqID() %>', 'feedback')"><img src="/ttk/images/FeedbackIcon.gif" alt="Manage Feedback" border="0" align="absmiddle"></a>&nbsp;&nbsp;
						  <%
						  	if(TTKCommon.isAuthorized(request,"Delete"))
						  	{
						  %>
						  		<img src="/ttk/images/IconSeparator.gif" width="1" height="18" align="absmiddle">&nbsp;&nbsp;<a href="javascript:companyDetails('<%= headOffice.getInsuranceSeqID() %>', 'deletecompany')"><img src="/ttk/images/DeleteIcon.gif" alt="Delete Office" width="16" height="16"  border="0" align="absmiddle"></a>
						  <%
						  	}
						  %>
						  </td>
			        	</tr>
			        </logic:notEmpty>
			    </table>
			</div>
		</td></tr>
	  	<tr><td>&nbsp;</td></tr>
	  	<%
	  		if(alRegionalOffice != null && alRegionalOffice.size() > 0)
	  		{
	  	%>
	  	<tr>
	    	<td class="fieldGroupHeader">Regional Office: </td>
	  	</tr>
	  	<tr><td valign="middle">
				<div class="rcDiv">
					<table align="left" width="97%" border="0" cellpadding="0" cellspacing="0" class="rcBorder">
				        <%
				        BrokerVO brokerVO = null;
				        	if(alRegionalOffice != null && alRegionalOffice.size() > 0)
				        	{
				        		brokerVO = (BrokerVO)alRegionalOffice.get(0);
				        %>
					        <tr> 
					          <td width="100%" align="left" class="rcText" ><%= brokerVO.getBranchName() %> </td>
					          <td align="right" class="rcIcons">&nbsp;&nbsp;
					          <%
							  	if(TTKCommon.isAuthorized(request,"Add")){
							  %>
					          		<a href="javascript:companyDetails('<%= brokerVO.getInsuranceSeqID() %>', 'addcompany')"><img src="/ttk/images/AddIcon.gif" alt="Add new Divisional Office" width="16" height="16" border="0" align="absmiddle"></a>&nbsp;&nbsp;<img src="/ttk/images/IconSeparator.gif" width="1" height="18" align="absmiddle">&nbsp;&nbsp;
					          <%
							  	}
							  %>
					          		<a href="javascript:companyDetails('<%= brokerVO.getInsuranceSeqID() %>', 'editcompany')"><img src="/ttk/images/EditIcon.gif" alt="Edit Office" width="16" height="16" border="0" align="absmiddle"></a>&nbsp;&nbsp;<img src="/ttk/images/IconSeparator.gif" width="1" height="18" align="absmiddle">&nbsp;&nbsp;
					          	<!--	<a href="javascript:companyDetails('<%= brokerVO.getInsuranceSeqID() %>', 'insproductlist')"><img src="/ttk/images/ProductIcon.gif" alt="Associate Products" width="16" height="16" border="0" align="absmiddle"></a>&nbsp;&nbsp;<img src="/ttk/images/IconSeparator.gif" width="1" height="18" align="absmiddle">&nbsp;&nbsp; -->
					          		<a href="javascript:companyDetails('<%= brokerVO.getInsuranceSeqID() %>', 'insurancecontactlist')"><img src="/ttk/images/ContactsIcon.gif" alt="Manage Contacts" width="16" height="16" border="0" align="absmiddle"></a>&nbsp;&nbsp;<img src="/ttk/images/IconSeparator.gif" width="1" height="18" align="absmiddle">&nbsp;&nbsp;<a href="javascript:companyDetails('<%= brokerVO.getInsuranceSeqID() %>', 'feedback')"><img src="/ttk/images/FeedbackIcon.gif" alt="Manage Feedback" border="0" align="absmiddle"></a>&nbsp;&nbsp;
							  <%
						  		if(TTKCommon.isAuthorized(request,"Delete")){
							  %>
							  		<img src="/ttk/images/IconSeparator.gif" width="1" height="18" align="absmiddle">&nbsp;&nbsp;<a href="javascript:companyDetails('<%= brokerVO.getInsuranceSeqID() %>', 'deletecompany')"><img src="/ttk/images/DeleteIcon.gif" alt="Delete Office" width="16" height="16"  border="0" align="absmiddle"></a>
							  <%
							  	}
							  %>
							  </td>
					        </tr>
				      	<%
				      		}
				      	%>
				      </table><img src="/ttk/images/DropDown.gif" id="drpArrow" width="17" height="26" align="absmiddle" onClick="dropDownMenu('idRO')" class="dropimge">
				</div>
				<div id="idRO" class="rcDropDownDiv" style="display:none">
				    <table width="100%" border="0" cellspacing="0" cellpadding="0">
				    <%
				    	if(alRegionalOffice != null && alRegionalOffice.size() > 0)
				    	{
				    		for(int i=1; i < alRegionalOffice.size(); i ++)
				    		{
				    			brokerVO = (BrokerVO)alRegionalOffice.get(i);
				    %>
				    			<tr>
							    	<td width="100%" id="r8" align="left"  nowrap class="rcDropDownText" onMouseout="mouseOut(this)" onMouseover="mouseOver(this)" onClick="javascript:companyDetails('<%= brokerVO.getInsuranceSeqID() %>', 'RegionalOffice')"><%= brokerVO.getBranchName() %> </td>
							    </tr>
				    <%
				    		}
				    	}
				    %>
				    </table>
				 </div>
		 </td></tr>
		 <tr><td>&nbsp;</td></tr>

	     <logic:notEmpty name="treeData" property="rootData">
		     <tr>
		         <td class="fieldGroupHeader" colspan="2">Divisional/Branch Offices: </td>
		     </tr>
		     <tr>
			     <td colspan="2"><ttk:TreeComponent name="treeData"/></td>
			</tr>
			<tr><td colspan="2">&nbsp;</td></tr>
			<tr><td colspan="2" class="buttonsContainerGrid">&nbsp;</td></tr>
				<ttk:TreePageLinks name="treeData"/>
	  	</logic:notEmpty>
	  	<%
	     	}
        %>
	</table>
	<!-- E N D : Form Fields -->
    <p>&nbsp;</p>
	</div>
	</logic:empty>
	<logic:notEmpty name="ErrorDisplay">
		<html:errors/>
	</logic:notEmpty>
	<input type="hidden" name="selectedroot" value="">
    <input type="hidden" name="selectednode" value="">
	<INPUT TYPE="hidden" NAME="mode" VALUE="">
	<INPUT TYPE="hidden" NAME="flow" VALUE="">
	<INPUT TYPE="hidden" NAME="offSeqId" VALUE="">
	<input type="hidden" name="pageId" value="">
	<input type="hidden" name="child" value="">
	</html:form>
	<!-- E N D : Content/Form Area -->