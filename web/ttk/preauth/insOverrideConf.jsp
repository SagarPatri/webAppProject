<%
/*1274A*/
 %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="/WEB-INF/tlds/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/tlds/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/tlds/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/tlds/ttk-tags.tld" prefix="ttk"%>

<script language="javascript" src="/ttk/scripts/calendar/calendar.js"></script>
<script type="text/javascript" src="/ttk/scripts/preauth/insOverrideConf.js" ></script>
<script language="JavaScript" src="/ttk/scripts/validation.js"></script>
<%@ page import=" com.ttk.common.TTKCommon,java.util.ArrayList,com.ttk.common.security.Cache" %>
<%
String strActiveTab=TTKCommon.getActiveTab(request);
pageContext.setAttribute("insReqType",Cache.getCacheObject("insReqType"));
%>

<html:form action="/FileInsOverrideConf.do" method="post" enctype="multipart/form-data">

<!--  -->
 <table align="center" class="pageTitle" border="0" cellspacing="0" cellpadding="0">
      <tr>
      	<td><bean:write name="frmInsOverrideConf" property="caption"/> </td>
      	<td align="right"></td>
        <td align="right" ></td>
      </tr>
    </table>
    	<html:errors/>
    	 <logic:notEmpty name="notify" scope="request">
		<table align="center" class="errorContainer" style="display:" border="0" cellspacing="0" cellpadding="0">
	      <tr>
	        <td><img src="/ttk/images/ErrorIcon.gif" alt="Alert" title="Alert" width="16" height="16" align="absmiddle">&nbsp;
	          <bean:write name="notify" scope="request"/>
	        </td>
	      </tr>
   	 </table>
	   </logic:notEmpty>
    	<logic:notEmpty name="updated" scope="request">
	   	<table align="center" class="successContainer" style="display:" border="0" cellspacing="0" cellpadding="0">
			<tr>
			 	<td><img src="/ttk/images/SuccessIcon.gif" alt="Success" width="16" height="16" align="absmiddle">&nbsp;
		    		<bean:write name="updated" scope="request"/>
		    	</td>
		 	</tr>
		</table>
	</logic:notEmpty>
		  <fieldset>
      <legend>General</legend>
		 <table align="center" class="formContainer"  border="0" cellspacing="0" cellpadding="0">
	                    <tr >
				         <td width="10%" height="20" class="formLabel">Override configuration:</td>
				          <td width="10%"  class="textLabelBold">
	   	                   <html:select name="frmInsOverrideConf" property="insReqType"  styleClass="selectBox selectBoxLarge" >
	   		               <html:options collection="insReqType"  property="cacheId" labelProperty="cacheDesc"/>
		                    </html:select>
		                    </td>
		                    <td></td>
		                    <td></td>
		                    </tr>
	    			      <tr>
			              <td width="10%" height="20" class="formLabel">Overridden Date:<br></td>
	                      <td width="10%" class="textLabelBold"> <html:text disabled="true" property="overriddenDate"  styleClass="textBox textDate" /></td>
				     <td></td>
				     <td></td>
				      </tr>
		             <tr>
			         <td width="10%" height="20" class="formLabel" >Remarks:</td>
			          <td width="10%" class="textLabelBold" nowrap> 		
                       <html:textarea name="frmInsOverrideConf" property="remarks" styleId="remarks" styleClass="textBox textAreaLongHt"/>
        	          </td>
        	          <td></td>
        	          <td></td>
        	     </tr>
		</table>
	  </fieldset>
	<table align="center" class="buttonsContainer"  border="0" cellspacing="0" cellpadding="0">
	  <tr>
	    <td width="100%" align="center">  
		<button type="button" name="Button"  accesskey="s" class="buttons" onMouseout="this.className='buttons'" onMouseover="this.className='buttons buttonsHover'" onClick="onSave();"><u>S</u>ave</button>&nbsp;
	    
	    <button type="button" name="Button2" accesskey="r" class="buttons" onMouseout="this.className='buttons'" onMouseover="this.className='buttons buttonsHover'" onClick="javascript:onReset();"><u>R</u>eset</button>&nbsp;
	    <button type="button" name="Button2" accesskey="c" class="buttons" onMouseout="this.className='buttons'" onMouseover="this.className='buttons buttonsHover'" onClick="onClose();"><u>C</u>lose</button>&nbsp;
	   
	    <%-- <logic:equal name="frmInsOverrideConf" property="overrideYN" value="Y"> --%>
		    
		<%-- </logic:equal> --%>
	</td>
	  </tr>
	</table>
	
	
<INPUT TYPE="hidden" NAME="mode" VALUE="">
<INPUT TYPE="hidden" NAME="tab" VALUE="<%=strActiveTab%>">
 <html:hidden property="insDec" /> 
<html:hidden name="frmInsOverrideConf" property="overrideYN"/>
</html:form>
