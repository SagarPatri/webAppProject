<%
/** @ (#) preauthlist.jsp April 21, 2006
 * Project     : TTK Healthcare Services
 * File        : preauthlist.jsp
 * Author      : Chandrasekaran J
 * Company     : Span Systems Corporation
 * Date Created: April 21, 2006
 *
 * @author 		 : Chandrasekaran J
 * Modified by   :
 * Modified date :
 * Reason        :
 *
 */
%>

<%@ taglib uri="/WEB-INF/tlds/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/tlds/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/tlds/ttk-tags.tld" prefix="ttk" %>
<%@ taglib uri="/WEB-INF/tlds/struts-bean.tld" prefix="bean" %>

<%@ page import="com.ttk.common.TTKCommon,com.ttk.common.security.Cache,java.util.HashMap,java.util.ArrayList,com.ttk.common.PreAuthWebBoardHelper"%>
<%@ page import="com.ttk.dto.usermanagement.UserSecurityProfile,com.ttk.dto.administration.WorkflowVO"%>
<SCRIPT LANGUAGE="JavaScript" SRC="/ttk/scripts/validation.js"></SCRIPT>
<script language="javascript" src="/ttk/scripts/calendar/calendar.js"></script>
<script language="javascript" src="/ttk/scripts/preauth/preauthlist.js"></script>
<script>
bAction=false;
var TC_Disabled = true;
</script>

<%
	pageContext.setAttribute("sAmount", Cache.getCacheObject("amount"));
	pageContext.setAttribute("sSource", Cache.getCacheObject("source"));
	pageContext.setAttribute("sStatus", Cache.getCacheObject("preauthStatus"));
	pageContext.setAttribute("sTtkBranch", Cache.getCacheObject("officeInfo"));
	pageContext.setAttribute("sAssignedTo", Cache.getCacheObject("assignedTo"));
	pageContext.setAttribute("sPreAuthType", Cache.getCacheObject("preauthType"));

	HashMap hmWorkflow= ((UserSecurityProfile)request.getSession().getAttribute("UserSecurityProfile")).getWorkFlowMap();
	ArrayList alWorkFlow=null;

	if(hmWorkflow!=null && hmWorkflow.containsKey(new Long(3)))	//to get the workflow of Pre-Auth
	{
	    alWorkFlow=((WorkflowVO)hmWorkflow.get(new Long(3))).getEventVO();
	}

    pageContext.setAttribute("listWorkFlow",alWorkFlow);

%>
<!-- S T A R T : Content/Form Area -->
<html:form action="/PreAuthAction.do">
	<!-- S T A R T : Page Title -->
	<table align="center" class="pageTitle" border="0" cellspacing="0" cellpadding="0">
  		<tr>
    		<td width="57%">List of Cashless</td>
    		<td width="43%" align="right" class="webBoard">&nbsp;<%@ include file="/ttk/common/toolbar.jsp" %></td>
  		</tr>
	</table>
	<!-- E N D : Page Title -->
	<html:errors/>
	<div class="contentArea" id="contentArea">
	<!-- S T A R T : Search Box -->
	<table align="center" class="searchContainer" border="0" cellspacing="0" cellpadding="0">
		<tr class="searchContainerWithTab">
		    <td nowrap>Cashless No.:<br>
		    	<html:text property="sPreAuthNumber"  styleClass="textBox textBoxMedium" maxlength="60"/>
		    </td>
		    <td nowrap> Hospital Name:<br>
		    	<html:text property="sHospitalName"  styleClass="textBox textBoxMedium" maxlength="250"/>
		    </td>
		    <td nowrap> Enrollment Id:<br>
		    	<html:text property="sEnrollmentId"  styleClass="textBox textBoxMedium" maxlength="60" onkeypress="ConvertToUpperCase(event.srcElement)"/>
		    </td>
		    <td nowrap>Member Name:<br>
		    	<html:text property="sClaimantName"  styleClass="textBox textBoxMedium" maxlength="250"/>
		    </td>
		    <td nowrap>Received Date:<br>
	            <html:text property="sRecievedDate"  styleClass="textBox textDate" maxlength="10"/><A NAME="CalendarObjectMarkDate" ID="CalendarObjectMarkDate" HREF="#" onClick="javascript:show_calendar('CalendarObjectMarkDate','forms[1].sRecievedDate',document.forms[1].sRecievedDate.value,'',event,148,178);return false;" onMouseOver="window.status='Calendar';return true;" onMouseOut="window.status='';return true;"><img src="/ttk/images/CalendarIcon.gif" alt="Calendar" name="mrkDate" width="24" height="17" border="0" align="absmiddle"></a>
            </td>
    	</tr>
    	<tr>
        	<td nowrap>Vidal Healthcare Branch:<br>
	            <html:select property="sTtkBranch" styleClass="selectBox selectBoxMedium">
		  	 		<html:option value="">Any</html:option>
		        	<html:optionsCollection name="sTtkBranch" label="cacheDesc" value="cacheId" />
            	</html:select>
          	</td>
        	<td nowrap>Assigned To:<br>
	            <html:select property="sAssignedTo" styleClass="selectBox selectBoxMedium">
		        	<html:optionsCollection name="sAssignedTo" label="cacheDesc" value="cacheId" />
            	</html:select>
          	</td>
        	<td nowrap>If Others, specify Name:<br>
            	<html:text property="sSpecifyName"  styleClass="textBox textBoxMedium" maxlength="250"/>
            </td>
        	<td nowrap>Amount (Rs):<br>
              	<html:select property="sAmount" styleClass="selectBox selectBoxMedium">
		  	 		<html:option value="">Any</html:option>
		        	<html:optionsCollection name="sAmount" label="cacheDesc" value="cacheId" />
            	</html:select>
            </td>
            <td nowrap>Source:<br>
	            <html:select property="sSource" styleClass="selectBox selectBoxSmall" style="width:130px;">
		  	 		<html:option value="">Any</html:option>
		        	<html:optionsCollection name="sSource" label="cacheDesc" value="cacheId" />
            	</html:select>
        	</td>
		</tr>
      	<tr>
        	<td nowrap>Status:<br>
	            <html:select property="sStatus" styleClass="selectBox selectBoxMedium">
		  	 		<html:option value="">Any</html:option>
		        	<html:optionsCollection name="sStatus" label="cacheDesc" value="cacheId" />
            	</html:select>
          	</td>
        	<td nowrap>Workflow:<br>
	            <html:select property="sWorkFlow" styleClass="selectBox selectBoxMedium">
		  	 		<html:option value="">Any</html:option>
		        	<logic:notEmpty name="listWorkFlow">
		        		<html:optionsCollection name="listWorkFlow" label="eventName" value="eventSeqID" />
		        	</logic:notEmpty>
            	</html:select>
	        </td>
        	<td nowrap>Cashless Type:<br>
            	<html:select property="sPreAuthType" styleClass="selectBox selectBoxMedium">
		  	 		<html:option value="">Any</html:option>
		        	<html:optionsCollection name="sPreAuthType" label="cacheDesc" value="cacheId" />
            	</html:select>
            </td>
            <td nowrap>Scheme No.:<br>
            	<html:text property="sPolicyNumber"  styleClass="textBox textBoxMedium" maxlength="60"/>
        	</td>
        	<td nowrap>Corporate Name:<br>
            	<html:text property="sCorporateName"  styleClass="textBox textBoxMedium" maxlength="60"/>
        	</td>
        </tr>
        <tr>
			<td nowrap>Employee No.:<br>
            	<html:text property="sEmployeeNumber"  styleClass="textBox textBoxMedium" maxlength="60"/>
            </td>
        	<td nowrap>Scheme Name:<br>
            	<html:text property="sSchemeName"  styleClass="textBox textBoxMedium" maxlength="60" onkeypress="ConvertToUpperCase(event.srcElement);"/>
            </td>
				<%--added as per bajaj  --%>
            <td nowrap>Insurer Status.:<br>
            	<html:select  property="sInsurerAppStatus" styleClass="selectBox selectBoxMedium">
            					 <html:option value="">Any</html:option>
				                 <html:option value="INP">In-Progress</html:option>
				                  <html:option value="APR">Approved</html:option>
					              <html:option value="REJ">Rejected</html:option>
					              <html:option value="REQ">Required Information</html:option>
				  </html:select>     
			</td>
            <td nowrap>Certificate No.:<br>
            	<html:text property="sCertificateNumber"  styleClass="textBox textBoxMedium" maxlength="60" onkeypress="ConvertToUpperCase(event.srcElement);"/>
            </td>
        	<td valign="bottom"><a href="#" accesskey="s" onClick="javascript:onSearch()"   class="search"><img src="/ttk/images/SearchIcon.gif" alt="Search" width="16" height="16" border="0" align="absmiddle">&nbsp;<u>S</u>earch</a></td>
        	<td valign="bottom" nowrap="nowrap">&nbsp;</td>
      	</tr>
	</table>
	<!-- E N D : Search Box -->
	<!-- S T A R T : Grid -->
		<ttk:HtmlGrid name="tableData"/>
	<!-- E N D : Grid -->
	<!-- S T A R T : Buttons and Page Counter -->
	<table align="center" class="buttonsContainerGrid"  border="0" cellspacing="0" cellpadding="0">
     	<tr>
     		<td width="27%"></td>
     		<td width="73%" align="right">
     		<%
	     		if(TTKCommon.isDataFound(request,"tableData"))
		    	{
	    	%>
 				    <button type="button" name="Button" accesskey="c" class="buttonsCopyWB" onMouseout="this.className='buttonsCopyWB'" onMouseover="this.className='buttonsCopyWB buttonsCopyWBHover'" onClick="javascript:copyToWebBoard()"><u>C</u>opy to Web Board</button>&nbsp;
     		<%
	        	}
            	if(TTKCommon.isAuthorized(request,"Add"))
		    	{
	    	%>
     				<button type="button" name="Button2" accesskey="a" class="buttons" onMouseout="this.className='buttons'" onMouseover="this.className='buttons buttonsHover'" onClick="addPreAuth()"><u>A</u>dd</button>&nbsp;
     		<%
     			}
        		if(TTKCommon.isDataFound(request,"tableData") && TTKCommon.isAuthorized(request,"Delete"))
	    		{
	    	%>
     			<button type="button" name="Button" accesskey="d" class="buttons" onMouseout="this.className='buttons'" onMouseover="this.className='buttons buttonsHover'" onClick="javascript:onDelete()"><u>D</u>elete</button>
     		<%
        		}
        	%>
     		</td>
     	</tr>
     	<ttk:PageLinks name="tableData"/>
      	<tr>
        	<td height="4" colspan="2"></td>
      	</tr>
      	<tr>
        	<td colspan="2"><span class="textLabelBold">Legend: </span><img src="/ttk/images/HighPriorityIcon.gif" alt="High Priority" width="16" height="16" align="absmiddle">- High Priority&nbsp;&nbsp;&nbsp;<img src="/ttk/images/LowPriorityIcon.gif" alt="Low Priority" width="16" height="16" align="absmiddle">&nbsp;- Low Priority</td>
        </tr>
	</table>
	</div>
	<!-- E N D : Buttons and Page Counter -->
	<INPUT TYPE="hidden" NAME="rownum" VALUE=''>
	<input type="hidden" name="child" value="">
	<INPUT TYPE="hidden" NAME="mode" VALUE="">
	<INPUT TYPE="hidden" NAME="sortId" VALUE="">
	<INPUT TYPE="hidden" NAME="pageId" VALUE="">
	<INPUT TYPE="hidden" NAME="tab" VALUE="">
</html:form>
<!-- E N D : Content/Form Area -->
