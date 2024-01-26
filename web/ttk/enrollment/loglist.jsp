<%
/** @ (#) loglist.jsp 26th Sep 2005
 * Project     : TTK Healthcare Services
 * File        : loglist.jsp
 * Author      : Arun K N
 * Company     : Span Systems Corporation
 * Date Created: 26th Sep 2005
 *
 * @author 		 : Arun K N
 * Modified by   :
 * Modified date :
 * Reason        :
 *
 */
%>
<%@ page import="com.ttk.common.TTKCommon,com.ttk.common.security.Cache,com.ttk.common.PreAuthWebBoardHelper" %>
<%@ taglib uri="/WEB-INF/tlds/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/tlds/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/tlds/ttk-tags.tld" prefix="ttk" %>
<%@ taglib uri="/WEB-INF/tlds/struts-html.tld" prefix="html" %>
<%
	String strSubLink=TTKCommon.getActiveSubLink(request);
	String strLink=TTKCommon.getActiveLink(request);
	pageContext.setAttribute("logTypeCode",Cache.getCacheObject("logTypeCode"));
	pageContext.setAttribute("listLogType",Cache.getCacheObject("logType"));
	pageContext.setAttribute("listLogInfo",Cache.getCacheObject("logInfo"));
	pageContext.setAttribute("dataEntrylistLogType",Cache.getCacheObject("dataEntrylogType"));
	pageContext.setAttribute("dataEntrylistLogInfo",Cache.getCacheObject("dataEntrylogInfo"));
	boolean viewmode=true;
    if(TTKCommon.isAuthorized(request,"Edit"))
    {
   		viewmode=false;
    }//end of if(TTKCommon.isAuthorized(request,"Edit"))

%>
<SCRIPT LANGUAGE="JavaScript" SRC="/ttk/scripts/validation.js"></SCRIPT>
<script language="javascript" src="/ttk/scripts/enrollment/log.js"></script>
<script language="javascript" src="/ttk/scripts/calendar/calendar.js"></script>
<html:errors/>
<!-- S T A R T : Content/Form Area -->
	<html:form action="/LogAction.do" >
	<!-- S T A R T : Page Title -->
	<%
	  	//if(strSubLink.equals("Hospital"))
	  	if((strLink.equals("Empanelment")&& strSubLink.equals("Hospital"))||
	  		(strLink.equals("Pre-Authorization")&& strSubLink.equals("Processing"))||
	  		(strLink.equals("Claims"))||(strLink.equals("DataEntryClaims"))||(strLink.equals("Coding")&& strSubLink.equals("PreAuth"))||
	  		((strLink.equals("Coding") || strLink.equals("DataEntryCoding"))&& strSubLink.equals("Claims"))||(strLink.equals("Code CleanUp")&& 
	  		strSubLink.equals("PreAuth"))||(strLink.equals("Code CleanUp")&& strSubLink.equals("Claims")))
		{
	%>
			<table align="center" class="<%=TTKCommon.checkNull(PreAuthWebBoardHelper.getShowBandYN(request)).equals("Y") ? "pageTitleHilite" :"pageTitle" %>" border="0" cellspacing="0" cellpadding="0">
	<%
		}// end of if(strLink.equals("Empanelment")&& strSubLink.equals("Hospital"))
		else
		{
    %>
			<logic:match name="frmPolicyList" property="switchType" value="ENM">
			<table align="center" class="pageTitle" border="0" cellspacing="0" cellpadding="0">
			</logic:match>
			<logic:match name="frmPolicyList" property="switchType" value="END">
			<table align="center" class="pageTitleHilite" border="0" cellspacing="0" cellpadding="0">
			</logic:match>
	<%
		}// end of else
	%>
	  <tr>
	    <td><bean:write name="frmHospitalLog" property="caption"/></td>
	    <td align="right" class="webBoard">&nbsp;<%@ include file="/ttk/common/toolbar.jsp" %></td>
	  </tr>
	</table>
	<!-- E N D : Page Title -->

    <div class="contentArea" id="contentArea">
	<!-- S T A R T : Success Box -->
		<logic:notEmpty name="updated" scope="request">
			<table align="center" class="successContainer" style="display:" border="0" cellspacing="0" cellpadding="0">
				<tr>
					<td><img src="/ttk/images/SuccessIcon.gif" alt="Success" width="16" height="16" align="absmiddle">&nbsp;
						<bean:message name="updated" scope="request"/>
					</td>
				</tr>
			</table>
		</logic:notEmpty>
	<!-- E N D : Success Box -->
	<!-- S T A R T : Search Box -->
	<table align="center" class="searchContainer" border="0" cellspacing="0" cellpadding="0">
	  <tr>
	<%
	  	if(strLink.equals("Empanelment")&& strSubLink.equals("Hospital"))
		{
	%>
		    <td align="left" nowrap>Log Type:<br>
		      <html:select property="logTypeCode" styleClass="selectBox selectBoxMedium" >
		  	 	  		<html:option value="">Any</html:option>
		          		<html:optionsCollection name="logTypeCode" label="cacheDesc" value="cacheId" />
            	</html:select>
	        </td>
    <%
		}// end of if(strLink.equals("Empanelment")&& strSubLink.equals("Hospital"))
		if((strLink.equals("Pre-Authorization")&& strSubLink.equals("Processing"))||
			(strLink.equals("Claims"))||(strLink.equals("Coding")&& strSubLink.equals("PreAuth"))||
			(strLink.equals("Coding")&& strSubLink.equals("Claims"))||
			(strLink.equals("Code CleanUp")&& strSubLink.equals("PreAuth"))||
			(strLink.equals("Code CleanUp")&& strSubLink.equals("Claims")))
		{
    %>
    		<td align="left" nowrap>Log Type:<br>
    			<html:select property="sLogType" styleClass="selectBox selectBoxMedium" >
		  	 	  		<html:option value="">Any</html:option>
		          		<html:optionsCollection name="listLogType" label="cacheDesc" value="cacheId" />
            	</html:select>
    		</td>
    <%
    	}// end of if(strLink.equals("Pre-Authorization")&& strSubLink.equals("Processing"))
    	if((strLink.equals("DataEntryClaims")) || (strLink.equals("DataEntryCoding")&& strSubLink.equals("Claims")))
    	{
    %>
    		<td align="left" nowrap>Log Type:<br>
    			<html:select property="sLogType" styleClass="selectBox selectBoxMedium" >
		  	 	  		<html:option value="">Any</html:option>
		          		<html:optionsCollection name="dataEntrylistLogType" label="cacheDesc" value="cacheId" />
            	</html:select>
    		</td>
    <%
    	}
    			
    %>
	    <td align="left" nowrap>Start Date:<br>
	  	    <html:text property="sStartDate"  styleClass="textBox textDate" maxlength="10" /><A NAME="CalendarObjectStartDate" ID="CalendarObjectStartDate" HREF="#" onClick="javascript:show_calendar('CalendarObjectStartDate','frmHospitalLog.sStartDate',document.frmHospitalLog.sStartDate.value,'',event,148,178);return false;" onMouseOver="window.status='Calendar';return true;" onMouseOut="window.status='';return true;"><img src="/ttk/images/CalendarIcon.gif" alt="Calendar" name="empDate" width="24" height="17" border="0" align="absmiddle"></a></td>
	    <td align="left" nowrap>End Date:<br>
		  <html:text property="sEndDate"  styleClass="textBox textDate" maxlength="10" /><A NAME="CalendarObjectEndDate" ID="CalendarObjectEndDate" HREF="#" onClick="javascript:show_calendar('CalendarObjectEndDate','frmHospitalLog.sEndDate',document.frmHospitalLog.sEndDate.value,'',event,148,178);return false;" onMouseOver="window.status='Calendar';return true;" onMouseOut="window.status='';return true;"><img src="/ttk/images/CalendarIcon.gif" alt="Calendar" name="empDate" width="24" height="17" border="0" align="absmiddle"></a></td>
		  <td width="100%" align="left" valign="bottom">
			  <a href="#" accesskey="s" onClick="javascript:onSearch()"   class="search"><img src="/ttk/images/SearchIcon.gif" alt="Search" width="16" height="16" border="0" align="absmiddle">&nbsp;<u>S</u>earch</a>
		  </td>
	  </tr>
	</table>
	<!-- E N D : Search Box --><br>

	<!-- S T A R T : Form Fields -->
	 <ttk:HtmlGrid name="tableDataLog"/>
	<br>	
    <fieldset>
    <legend>Add Log Information</legend>
    
    <!--
    <%
    	if((strLink.equals("Coding")&& strSubLink.equals("PreAuth"))||(strLink.equals("Coding")&& strSubLink.equals("Claims")))
    	{
    %>
    		<legend>Add Log Information [Coding]</legend>
    <%	
    	}//end of if((strLink.equals("Coding")&& strSubLink.equals("PreAuth"))||(strLink.equals("Coding")&& strSubLink.equals("Claims")))
    	else
    	{
    %>
    		<legend>Add Log Information</legend>
    <%	
    	}//end of else
    %> -->
    
    <table class="formContainer"  border="0" cellspacing="0" cellpadding="0">
      
      <%
      	if((strLink.equals("Pre-Authorization")&& strSubLink.equals("Processing"))||
			(strLink.equals("Claims"))||(strLink.equals("Coding")&& strSubLink.equals("PreAuth"))||
			(strLink.equals("Coding")&& strSubLink.equals("Claims"))||
			(strLink.equals("Code CleanUp")&& strSubLink.equals("PreAuth"))||
			(strLink.equals("Code CleanUp")&& strSubLink.equals("Claims")))
		{
      %>
	      	<tr>
	      	<td nowrap class="formLabel indentedLabels">Log Information:</td>	
	        <td nowrap class="formLabel indentedLabels">
	        <html:select property="logType" styleClass="selectBox selectBoxMedium" >
		 	  		<html:optionsCollection name="listLogInfo" label="cacheDesc" value="cacheId" />
	        </html:select>
	        </td>
	        </tr>
	 <%
		}
        if((strLink.equals("DataEntryClaims")) || (strLink.equals("DataEntryCoding")&& strSubLink.equals("Claims")))
        {
	 %>   
		<tr>
	      	<td nowrap class="formLabel indentedLabels">Log Information:</td>	
	        <td nowrap class="formLabel indentedLabels">
	        <html:select property="logType" styleClass="selectBox selectBoxMedium" >
		 	  		<html:optionsCollection name="dataEntrylistLogInfo" label="cacheDesc" value="cacheId" />
	        </html:select>
	        </td>
	        </tr>   
	  <%
		}
      %>  
        <tr>
        <td nowrap class="formLabel indentedLabels">Remarks:</td>
        <td nowrap class="formLabel indentedLabels">
        <html:textarea  property="logDesc" styleClass="textBox textAreaLong" disabled="<%= viewmode %>"/>
        </td>
        <td valign="bottom" nowrap class="formLabel indentedLabels">&nbsp;</td>
      </tr>
    </table>
    <table align="center" class="buttonsContainer"  border="0" cellspacing="0" cellpadding="0">
  		<tr>
  			<td width="100%" align="center">
  			<%
		    	if(TTKCommon.isAuthorized(request,"Edit"))
    			{
    		%>	
    				<button type="button" name="Button" accesskey="s" class="buttons" onMouseout="this.className='buttons'" onMouseover="this.className='buttons buttonsHover'" onClick="javascript:onSave()"><u>S</u>ave to List</button>&nbsp;	
  			<%
  				}//end of if(TTKCommon.isAuthorized(request,"Edit"))
  			%>		
  			</td>
  		</tr>
  	</table>
    </fieldset>
    </div>
    <INPUT TYPE="hidden" NAME="mode" VALUE="">
	<INPUT TYPE="hidden" NAME="sortId" VALUE="">
	<!-- E N D : Form Fields -->
    </html:form>
	<!-- E N D : Content/Form Area -->
