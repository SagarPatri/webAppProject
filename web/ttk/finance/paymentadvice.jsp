<%
/**
 * @ (#) chequeseries.jsp 9th June 2006
 * Project      : TTK HealthCare Services
 * File         : paymentadvice.jsp
 * Author       : Arun K.M
 * Company      : Span Systems Corporation
 * Date Created : 27th oct 2006
 *
 * @author       :Arun K.M
 * Modified by   :
 * Modified date :
 * Reason        :
 */
%>
<%@ taglib uri="/WEB-INF/tlds/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/tlds/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/tlds/ttk-tags.tld" prefix="ttk" %>
<%@ taglib uri="/WEB-INF/tlds/struts-bean.tld" prefix="bean"%>
<%@ page import=" com.ttk.common.TTKCommon,com.ttk.common.security.Cache" %>

<SCRIPT LANGUAGE="JavaScript" SRC="/ttk/scripts/validation.js"></SCRIPT>
<script language="javascript" src="/ttk/scripts/finance/paymentadvice.js"></script>
<script language="javascript" src="/ttk/scripts/calendar/calendar.js"></script>
<SCRIPT LANGUAGE="JavaScript">
	var TC_Disabled = true;
</SCRIPT>
<%
	pageContext.setAttribute("bankName",Cache.getCacheObject("bankName"));
	pageContext.setAttribute("listofficeInfo",Cache.getCacheObject("officeInfo"));
	//start cr koc 1103 and 1105
	pageContext.setAttribute("paymentMethod", Cache.getCacheObject("paymentMethod1"));
	pageContext.setAttribute("curencyTypeList", Cache.getCacheObject("allCurencyCode"));
	//end cr koc 1103 and 1105
%>

<html:form action="/PaymentAdviceAction.do" method="post"   enctype="multipart/form-data">
	
	<html:errors/>
	
		<logic:notEmpty name="fileName" scope="request">
			<SCRIPT LANGUAGE="JavaScript">
				var w = screen.availWidth - 10;
				var h = screen.availHeight - 82;
				var features = "scrollbars=0,status=1,toolbar=1,top=0,left=0,resizable=0,menubar=yes,width="+w+",height="+h;
				window.open("/PaymentAdviceAction.do?mode=doPaymentAdviceXL&displayFile=<bean:write name="fileName"/>&alternateFileName=<bean:write name="alternateFileName"/>",'PaymentAdvice',features);
			</SCRIPT>
		</logic:notEmpty>
	<!-- S T A R T : Page Title -->
	
	<table align="center" class="pageTitle" border="0" cellspacing="0" cellpadding="0">
	
  		<tr>
    		<td width="57%">List of Claims</td>
    		<td align="right" class="webBoard">&nbsp;<%@ include file="/ttk/common/toolbar.jsp" %></td>
 		</tr>
	</table>

	<!-- E N D : Page Title -->
	<div class="contentArea" id="contentArea">
	<!-- S T A R T : Search Box -->
   <logic:notEmpty name="notify" scope="session">
	
		<table align="center" class="errorContainer" style="display:" border="0" cellspacing="0" cellpadding="0">
	      <tr>
	        <td><img src="/ttk/images/ErrorIcon.gif" alt="Alert" title="Alert" width="16" height="16" align="absmiddle">&nbsp;
	    
	        <%=request.getSession().getAttribute("notify")%>
	        </td>
	      </tr>
   	 </table>
   	 </logic:notEmpty>
   	 	 <%  session.removeAttribute("notify");%>
	<table align="center" class="searchContainer" border="0" cellspacing="0" cellpadding="0">
      <tr>
        <td nowrap>Float Name :<br>
            <html:text property="sFloatName"  styleClass="textBox textBoxMedium" maxlength="10"/>
        </td>
        <td nowrap>Branch Location:<br>
            <html:select property="sTTKBranch" styleClass="selectBox selectBoxMedium" >
				<html:option value="">Any</html:option>
				<html:optionsCollection name="listofficeInfo" label="cacheDesc" value="cacheId"/>
				</html:select>
		</td>
        <td nowrap>Bank Account No.:<br>
          <html:text property="sbankaccountNbr"  styleClass="textBox textBoxMedium" maxlength="10"/>
        </td>
        <!-- start changes for cr koc 1103 and 1105 -->
        <td nowrap> Payment Method:<br>
        	<html:select property="paymethod" styleClass="selectBox selectBoxMedium" onchange="enableField(this)" >
				 <html:optionsCollection name="paymentMethod" label="cacheDesc" value="cacheId"/>
			</html:select>
		</td>
		<td nowrap>Incurred Currency Format:<br>
        	<html:select property="incuredCurencyFormat" styleClass="selectBox selectBoxMedium" onchange="enableField(this)" >
				 <html:optionsCollection name="curencyTypeList" label="cacheDesc" value="cacheId" />
			</html:select>
		</td>
		<!-- end changes for cr koc 1103 and 1105-->
        <td width="100%" valign="bottom" nowrap>
	        <a href="#" accesskey="s" onClick="javascript:onSearch()"   class="search"><img src="/ttk/images/SearchIcon.gif" alt="Search" width="16" height="16" border="0" align="absmiddle">&nbsp;<u>S</u>earch</a>
        </td>
        </tr>

    </table>
	<!-- E N D : Search Box -->
    <!-- S T A R T : Grid -->
	<ttk:HtmlGrid name="tableData"/>
    <!-- E N D : Grid -->
    <!-- S T A R T : Buttons and Page Counter -->

	<table align="center" class="buttonsContainerGrid"  border="0" cellspacing="0" cellpadding="0">
	      <tr>
	        <td width="61%">&nbsp;</td>
	        <td width="39%" algn="right">
	         <%
		   		if(TTKCommon.isDataFound(request,"tableData")&& TTKCommon.isAuthorized(request,"Add"))
		    		{
		    	%>
		    	&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;		    	
				Report Format :<br>
				&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;				
				<html:select property="sreportFormat" styleClass="selectBox selectBoxMedium" >
					<logic:match name="frmFloatAccounts" property="sBankDesc" value="CITI">
						<%-- <html:option value="Old Format"/>
						<html:option value="New Format"/> 
						These 2 formats we are not using for Dubai--%>
						<html:option value="ENBD Format"/>
						<logic:match name="frmFloatAccounts" property="sAbbrCode" value="OI">
							<html:option value="OIC Report"/>
						</logic:match>
					</logic:match>
					<logic:match name="frmFloatAccounts" property="sBankDesc" value="UTI">
						<html:option value="Default Format"/>
						<html:option value="UTI Mumbai New Format"/>
						<html:option value="Multi Location Format"/>
						<!-- Change added for Axis Bank CR KOC1212 -->
						<html:option value="Axis Pay Pro Advice"/>
						<logic:match name="frmFloatAccounts" property="sAbbrCode" value="OI">
							<html:option value="OIC Report"/>
						</logic:match>
					</logic:match>
					<logic:match name="frmFloatAccounts" property="sBankDesc" value="ICICI">
						<html:option value="Default Format"/>
						<html:option value="Multi Location Format"/>
						<logic:match name="frmFloatAccounts" property="sAbbrCode" value="OI">
							<html:option value="OIC Report"/>
						</logic:match>
					</logic:match>
					<logic:match name="frmFloatAccounts" property="sBankDesc" value="HDFC">
						<html:option value="Default Format"/>
						<logic:match name="frmFloatAccounts" property="sAbbrCode" value="OI">
							<html:option value="OIC Report"/>
						</logic:match>						
					</logic:match>
					<!-- Change added for BOA Bank CR KOC1220 -->
					<logic:match name="frmFloatAccounts" property="sBankDesc" value="BOA">
						<html:option value="Default Format"/>
					</logic:match>
				</html:select>
				<%
		    		}//end of if(TTKCommon.isDataFound(request,"tableData")&& TTKCommon.isAuthorized(request,"Add"))
		    	%>
				
	        <%
		   		if(TTKCommon.isDataFound(request,"tableData")&& TTKCommon.isAuthorized(request,"Add"))
		    		{
		    	%>
		    	&nbsp;
		    	<logic:match name="frmFloatAccounts" property="sBankDesc" value="UTI">		    		
		    		   <button type="button" name="Button2" accesskey="g" class="buttons" onMouseout="this.className='buttons'" onMouseover="this.className='buttons buttonsHover'" onClick="onGenerateUTIXL()"><u>G</u>enerateXL</button>&nbsp;
		    	</logic:match>
				<logic:notMatch name="frmFloatAccounts" property="sBankDesc" value="UTI">		    		
				   		<button type="button" name="Button2" accesskey="g" class="buttons" onMouseout="this.className='buttons'" onMouseover="this.className='buttons buttonsHover'" onClick="onGenerateXL()"><u>G</u>enerateXL</button>&nbsp;
				</logic:notMatch>
		    	<%
		    		}//end of if(TTKCommon.isDataFound(request,"tableData")&& TTKCommon.isAuthorized(request,"Add"))
		    	%>
	        </td>
	      </tr>	  
	      <tr>
	          <td>
	          <ttk:PageLinks name="tableData"/>    
	          </td>
		  </tr>		  
		           	
    </table>
   <table align="center;"   border="0" cellspacing="0" cellpadding="0" style="padding-left:17px;border-left-width:29px;" >
        <tr>
          
           <td width="60%" height="28">File Name :
          <html:file  name="frmFloatAccounts" property="stmFile"/>            
           </td>
           <td> </td>
           <td> </td>   
            <td width="40%" height="28" nowrap class="fieldGroupHead-r"> 
               No. of Claims :<br>
            <html:text property="noofclaimssettlementnum"  styleClass="textBox textBoxMedium textBoxDisabled" disabled="true"  maxlength="10"/>
            </td>   
        </tr>
        <tr>
          <td width="100%" height="28" nowrap class="fieldGroupHead-r">
          <button type="button" name="uploadButton" accesskey="u" class="buttons" onMouseout="this.className='buttons'" onMouseover="this.className='buttons buttonsHover'" onClick="javascript:onUploadBatchdetail()"><u>U</u>pload File</button>
          </td>
             <td> </td>
           <td> </td>   
            <td></td>   
        </tr>
   </table>
    <br>
    
     <fieldset style="width:40%"><legend>Log Details</legend>
          <table align="left" width="85%"  border="0" cellspacing="0" cellpadding="0" style="padding-left:17px;border-left-width:29px;" >
        <tr>
          
           <td width="50%" height="28">
         
           Start Date:<br/>
           <html:text property="startDate" styleClass="textBox textDate" maxlength="10"/>
				<A NAME="calStartDate" ID="calStartDate" HREF="#" onClick="javascript:show_calendar('calStartDate','frmFloatAccounts.startDate',document.frmFloatAccounts.startDate.value,'',event,148,178);return false;" onMouseOver="window.status='Calendar';return true;" onMouseOut="window.status='';return true;"><img src="/ttk/images/CalendarIcon.gif" alt="Calendar"  width="24" height="17" border="0" align="absmiddle"></a>
           </td>
           <td width="50%" height="28">
           End Date:<br/>
           <html:text property="endDate" styleClass="textBox textDate" maxlength="10"/>
	        	<A NAME="calEndDate" ID="calEndDate" HREF="#" onClick="javascript:show_calendar('calEndDate','frmFloatAccounts.endDate',document.frmFloatAccounts.endDate.value,'',event,148,178);return false;" onMouseOver="window.status='Calendar';return true;" onMouseOut="window.status='';return true;"><img src="/ttk/images/CalendarIcon.gif" alt="Calendar"  width="24" height="17" border="0" align="absmiddle"></a>
         
             <a href="#" accesskey="s" onClick="javascript:onPaymentAdviceLogSearch()"   class="search"><img src="/ttk/images/SearchIcon.gif" alt="Search" width="16" height="16" border="0" align="absmiddle">&nbsp;<u>S</u>earch</a>
          
            </td>
              
        </tr>
             </table>
        </fieldset> 
    </div>
    <!-- E N D : Buttons and Page Counter -->
	<INPUT TYPE="hidden" NAME="rownum" VALUE="">
	<INPUT TYPE="hidden" NAME="mode" VALUE="">
	<INPUT TYPE="hidden" NAME="sortId" VALUE="">
	<INPUT TYPE="hidden" NAME="pageId" VALUE="">
	</html:form>

	<!-- E N D : Content/Form Area -->