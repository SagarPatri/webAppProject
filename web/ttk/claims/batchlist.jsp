<%
/** @ (#) batchlist.jsp 7th July 2015
 * Project     : ProjectX
 * File        : batchlist.jsp
 * Author      : Nagababu K
 * Company     : RCS Technologies
 * Date Created: 7th July 2015
 *
 * @author 		 : Nagababu K
 * Modified by   :
 * Modified date :
 * Reason        :
 *
 */
%>
<%@ taglib uri="/WEB-INF/tlds/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/tlds/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/tlds/ttk-tags.tld" prefix="ttk" %>
<%@ page import="com.ttk.common.security.Cache,com.ttk.common.TTKCommon,java.util.Date,java.text.SimpleDateFormat" %>
<script type="text/javascript" src="/ttk/scripts/validation.js" type="text/javascript"></script>
<script type="text/javascript" src="/ttk/scripts/calendar/calendar.js"></script>
<script type="text/javascript" src="/ttk/scripts/claims/batchlist.js"></script>
<SCRIPT type="text/javascript">
bAction=false;
var TC_Disabled = true; //to avoid the alert message on change of form elements
</SCRIPT>
<%
	boolean viewmode=true;
	if((TTKCommon.isAuthorized(request,"Add")) || (TTKCommon.isAuthorized(request,"Delete")))
	{
		viewmode=false;
	}
	pageContext.setAttribute("viewmode",new Boolean(viewmode));
	pageContext.setAttribute("ProviderList",Cache.getCacheObject("ProviderList"));
	pageContext.setAttribute("claimType", Cache.getCacheObject("claimType"));
	pageContext.setAttribute("modeOfClaims", Cache.getCacheObject("modeOfClaims"));
	pageContext.setAttribute("claimsSubmissionTypes", Cache.getCacheObject("claimsSubmissionTypes"));
	pageContext.setAttribute("submissionCatagory", Cache.getCacheObject("submissionCatagory"));
	java.util.Date dt = new java.util.Date();
	int iDate=0;
	int iMonth=0;
	int iYear=0;    
	java.text.SimpleDateFormat sdt;
	sdt = new java.text.SimpleDateFormat("dd/MM/yyyy");
	String strDate = sdt.format(dt);
	String str[] = strDate.split("/");
	for(int i=0; i<str.length; i++)
	{
		iDate =Integer.parseInt(str[0]);
		iMonth =Integer.parseInt(str[1]);
		iYear =Integer.parseInt(str[2]);
	}
	dt = new Date(iYear-1900,iMonth-1,iDate-5);	
	String strStartDate = sdt.format(dt);
%>

<!-- E N D : Tab Navigation -->
	<!-- S T A R T : Content/Form Area -->
	<html:form action="/BatchlistAction.do" method="post" >
	<!-- S T A R T : Page Title -->
<table align="center" class="pageTitle" border="0" cellspacing="0" cellpadding="0">
  		<tr>
    		<td width="57%">List of Batch Entries</td>
    		<td width="43%" align="right" class="webBoard">&nbsp;<%@ include file="/ttk/common/toolbar.jsp" %></td>
  		</tr>
	</table>
	<!-- E N D : Page Title -->
<html:errors/>
	<!-- S T A R T : Search Box -->
	
	<div class="contentArea" id="contentArea">
	<table align="center" class="searchContainer" border="0" cellspacing="0" cellpadding="0">
      <tr>
      <td nowrap>Batch No.:<br>
            <html:text property="sBatchNumber"  styleClass="textBox textBoxMedLarge" maxlength="60" name="frmClaimBatchList"/>
        </td>
       <td nowrap>Provider Name:<br>
		    <html:select property="sProviderName" name="frmClaimBatchList"  styleClass="selectBox selectBoxLarge">
		    	 <html:option value="">Any</html:option>
  				<html:options collection="ProviderList"  property="cacheId" labelProperty="cacheDesc"/>
		    </html:select>
        </td>
       <td nowrap>Claim Type:<br>
	            <html:select property="sClaimType" styleClass="selectBox selectBoxMedium" name="frmClaimBatchList">
		  	 		<html:option value="">Any</html:option>
		        	<html:optionsCollection name="claimType" label="cacheDesc" value="cacheId" />
            	</html:select>
          	</td>
          	 <td nowrap>Mode Of Claim:<br>
	            <html:select property="sModeOfClaim" styleClass="selectBox selectBoxMedium" name="frmClaimBatchList">
		  	 		<html:option value="">Any</html:option>
		        	<html:optionsCollection name="modeOfClaims" label="cacheDesc" value="cacheId" />
            	</html:select>
          	</td>
      </tr>
      <tr>
        <td nowrap>Batch Status:<br>
	        <html:select property="sBatchStatusID"  styleClass="selectBox selectBoxMedium" name="frmClaimBatchList">
  				<html:option value="">Any</html:option>
  				<html:option value="INP">In Progress</html:option>
  				<html:option value="COMP">Complete</html:option>
	    	</html:select>
		</td>
		<td nowrap>
		<table><tr><td>
		Claims Submission Type:<br>
	
				      <html:select name="frmClaimBatchList" property="sSubmissionType" styleClass="selectBox selectBoxMedium">
				            <html:option value="">Select From List</html:option>
							<html:optionsCollection name="claimsSubmissionTypes" label="cacheDesc" value="cacheId" />
					  </html:select>
			     </td>
			     <td>
			     <div style="margin-left: 50px;">
			  Batch Received From:<br><html:text property="sBatchReceivedFrom" name="frmClaimBatchList" styleClass="textBox textDate" maxlength="10"/><A NAME="sBatchReceivedFrom" ID="sBatchReceivedFrom" HREF="#" onClick="javascript:show_calendar('sBatchReceivedFrom','forms[1].sBatchReceivedFrom',document.forms[1].sBatchReceivedFrom.value,'',event,148,178);return false;" onMouseOver="window.status='Calendar';return true;" onMouseOut="window.status='';return true;"><img src="/ttk/images/CalendarIcon.gif" alt="Calendar"  width="24" height="17" border="0" align="absmiddle"></a>
      	</div>
      	</td>
      	</tr></table>
		</td>
		<td nowrap>Batch Received To:<br>
      			<html:text property="sBatchReceivedTo" name="frmClaimBatchList" styleClass="textBox textDate" maxlength="10"/><A NAME="sBatchReceivedTo" ID="sBatchReceivedTo" HREF="#" onClick="javascript:show_calendar('sBatchReceivedTo','forms[1].sBatchReceivedTo',document.forms[1].sBatchReceivedTo.value,'',event,148,178);return false;" onMouseOver="window.status='Calendar';return true;" onMouseOut="window.status='';return true;"><img src="/ttk/images/CalendarIcon.gif" alt="Calendar"  width="24" height="17" border="0" align="absmiddle"></a>
      	 &nbsp;&nbsp;&nbsp; <!-- <a href="#" accesskey="s" onClick="javascript:onSearch()"   class="search"><img src="/ttk/images/SearchIcon.gif" alt="Search" width="16" height="16" border="0" align="absmiddle">&nbsp;<u>S</u>earch</a> -->
      	</td>
      	
      	
      	
      	
     <!--  </tr>
      
      
      
      
       <tr> -->
      <td>
		 Submission Category:<br>
	
				      <html:select name="frmClaimBatchList" property="sProcessType" styleClass="selectBox selectBoxMedium">
				            <html:option value="">Any</html:option>
							<html:optionsCollection name="submissionCatagory" label="cacheDesc" value="cacheId" />
					  </html:select>
					  <a href="#" accesskey="s" onClick="javascript:onSearch()"   class="search"><img src="/ttk/images/SearchIcon.gif" alt="Search" width="16" height="16" border="0" align="absmiddle">&nbsp;<u>S</u>earch</a>
					  
			     </td>
      
      </tr>
      
      
      
      
      
      
      
     </table>
	<!-- E N D : Search Box -->
	<!-- S T A R T : Grid -->
	
	<ttk:HtmlGrid name="tableData" />
	<!-- E N D : Grid -->
<!-- E N D : Grid -->
	<!-- S T A R T : Buttons and Page Counter -->
	<table align="center" class="buttonsContainerGrid"  border="0" cellspacing="0" cellpadding="0">
  <tr>
   <!--  <td width="27%"><a href="#"><img src="/ttk/images/First.gif" alt="First Page" width="5" height="8" border="0"></a>&nbsp;<a href="#"><img src="/ttk/images/Prev.gif" alt="Previous Page" width="4" height="8" border="0"></a>&nbsp;&nbsp;Page 5 of 25&nbsp;&nbsp;<a href="#"><img src="/ttk/images/Next.gif" alt="Next Page" width="4" height="8" border="0"></a>&nbsp;<a href="#"><img src="/ttk/images/Last.gif" alt="Last Page" width="5" height="8" border="0"></a> </td> -->
    <td width="73%" nowrap align="right">
    <td width="73%" nowrap align="right">
    <%
    if(TTKCommon.isAuthorized(request,"Add")) {
    %>
    <button type="button" name="Button" accesskey="a" class="buttons" onMouseout="this.className='buttons'" onMouseover="this.className='buttons buttonsHover'" onClick="javascript:onAdd()"><u>A</u>dd</button>&nbsp;
     <%
     }//end of if(TTKCommon.isAuthorized(request,"Add"))
     if(TTKCommon.isDataFound(request,"tableData")){
    %>
    	  <button type=R"button" name="Button" accesskey="c" class="buttonsCopyWB" onMouseout="this.className='buttonsCopyWB'" onMouseover="this.className='buttonsCopyWB buttonsCopyWBHover'" onClick="javascript:copyToWebBoard()"><u>C</u>opy to Web Board</button>&nbsp;
     <%if(TTKCommon.isAuthorized(request,"Delete"))
     {
    %>
<!--     <button type="button" name="Button" accesskey="d" class="buttons" onMouseout="this.className='buttons'" onMouseover="this.className='buttons buttonsHover'" onClick="javascript:onDelete()"><u>D</u>elete</button>&nbsp; -->
    <%}//end of if(TTKCommon.isDataFound(request,"tableData"))
     }//end of if(TTKCommon.isAuthorized(request,"Delete"))
    %>
    </td>
  </tr>
  <ttk:PageLinks name="tableData"/>
</table>
</div>
	<!-- E N D : Buttons and Page Counter -->

	<!-- E N D : Content/Form Area -->
	<INPUT TYPE="hidden" NAME="rownum" VALUE=''>
	<INPUT TYPE="hidden" NAME="mode" VALUE="">
	<INPUT TYPE="hidden" NAME="sortId" VALUE="">
	<INPUT TYPE="hidden" NAME="pageId" VALUE="">
	<INPUT TYPE="hidden" NAME="tab" VALUE="">
	</td>
  </tr>
</table>
</html:form>
<!-- E N D : Main Container Table -->