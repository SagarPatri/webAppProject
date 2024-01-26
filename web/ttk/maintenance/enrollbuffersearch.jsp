<%
/**      New File :1216B CR
 * @ (#) enrollbuffersearch.jsp 03 JAN 2012
 * Project      : TTK HealthCare Services
 * File         : enrollbuffersearch.jsp
 * Author       : 
 * Company      : 
 * Date Created : 03 JAN 2013
 *
 * @author       :
 * Modified by   :
 * Modified date :
 * Reason        :
 */
%>
<%@ page import="com.ttk.common.TTKCommon,com.ttk.common.security.Cache,com.ttk.action.table.TableData,com.ttk.action.table.Column,java.util.ArrayList" %>
<%@ taglib uri="/WEB-INF/tlds/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/tlds/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/tlds/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/tlds/ttk-tags.tld" prefix="ttk" %>

<SCRIPT LANGUAGE="JavaScript" SRC="/ttk/scripts/validation.js"></SCRIPT>
<script language="javascript" src="/ttk/scripts/calendar/calendar.js"></script>
<script language="javascript" src="/ttk/scripts/maintenance/enrollbuffersearch.js"></script>
<script>
bAction=false;
var TC_Disabled = true;
</script>
<%

	pageContext.setAttribute("policyType",Cache.getCacheObject("enrollTypeCode"));
	pageContext.setAttribute("officeInfo",Cache.getCacheObject("officeInfo"));
	pageContext.setAttribute("listSwitchType",Cache.getCacheObject("accountupdate"));
	
%>
<!-- S T A R T : Content/Form Area -->
<html:form action="/EnrollBufferSearchAction.do" >
    <table align="center" class="pageTitle" border="0" cellspacing="0" cellpadding="0">
	    <tr>
	    <td><bean:write name="frmEnrollBufferSearch" property="caption"/></td>     
	   <%-- <td align="right" class="webBoard">&nbsp;<%@ include file="/ttk/common/toolbar.jsp" %></td>--%>
	    </tr>
	    </table>
	
	<!-- E N D : Page Title -->
  <div class="contentArea" id="contentArea">
	<!-- S T A R T : Search Box -->
	<html:errors/>
	
	<table align="center" class="searchContainer" border="0" cellspacing="0" cellpadding="0">
  		<tr>
    		   	<td nowrap>Scheme&nbsp;No.:<br>
	     		<html:text property="sPolicyNumber" styleClass="textBox textBoxMedium"  maxlength="60" />
	    	</td>
	    	<td nowrap>Enrollment&nbsp;Id.:<br>
    	  		<html:text property="sEnrollmentId" styleClass="textBox textBoxMedium"  maxlength="60" />
      		</td>
	  		<td nowrap>Member&nbsp;Name:<br>
	     		<html:text property="sMemberName" styleClass="textBox textBoxMedium"  maxlength="60" />
	    	</td>
	    	<td nowrap>Enrollment&nbsp;Number:<br>
	     		<html:text property="sEnrollNumber" styleClass="textBox textBoxMedium"  maxlength="60" />
	    	</td>		 
	    			   	  
	    	</tr>
	    	
	    	
		   	  <tr>
		   	  	<td nowrap>Employee&nbsp;Name:<br>
	     		<html:text property="sEmployeeName" styleClass="textBox textBoxMedium"  maxlength="60" />
	    	</td>		 
		   	  <td nowrap>Scheme Type: <br>
		      <html:select  property="sPolicyType"  styleClass="selectBox selectBoxMedium" styleId="sPolicyType" onchange="javascript:ChangePolicyType();" >
		          	<html:optionsCollection name="policyType" value="cacheId" label="cacheDesc"/>
		      </html:select>
		    </td>
		    <td nowrap>Group Id:<br>
	           <logic:empty name="frmEnrollBufferSearch" property="sPolicyType">
	        	<html:text property="sGroupId"  styleClass="textBox textBoxSmall"  styleId="search7" maxlength="60" style="background-color: #EEEEEE;" readonly="true"/>
	            </logic:empty>
	            <logic:notEmpty name="frmEnrollBufferSearch" property="sPolicyType">
		        <logic:match name="frmEnrollBufferSearch" property="sPolicyType" value="IND">
		            		<html:text property="sGroupId"  styleClass="textBox textBoxSmall"  styleId="search7" maxlength="60" style="background-color: #EEEEEE;" readonly="true"/>
				</logic:match>
				<logic:notMatch name="frmEnrollBufferSearch" property="sPolicyType" value="IND">
					<html:text property="sGroupId"  styleClass="textBox textBoxSmall"  styleId="search7" maxlength="60"/>
					<a href="#" accesskey="g"  onClick="javascript:SelectGroup();" class="search">
								<img src="/ttk/images/EditIcon.gif" alt="Select Group" width="16" height="16" border="0" align="absmiddle">&nbsp;
					</a>
					<a href="#" onClick="ClearCorporate()"><img src="/ttk/images/DeleteIcon.gif" alt="Clear Group" width="16" height="16" border="0" align="absmiddle"></a>
				</logic:notMatch>	
			</logic:notEmpty>		
	        </td>
			  
		  <%--  
	     		<logic:empty name="frmEnrollBufferSearch" property="sPolicyType">
	        	<html:text property="sGroupId"  styleClass="textBox textBoxSmall"  styleId="search7" maxlength="60" style="background-color: #EEEEEE;" disabled="true"/>
	        </logic:empty>
	        <logic:notEmpty name="frmEnrollBufferSearch" property="sPolicyType">
		        <logic:match name="frmEnrollBufferSearch" property="sPolicyType" value="IND">
		            		<html:text property="sGroupId"  styleClass="textBox textBoxSmall"  styleId="search7" maxlength="60" style="background-color: #EEEEEE;" disabled="true"/>
				</logic:match>
				<logic:notMatch name="frmEnrollBufferSearch" property="sPolicyType" value="IND">
					<html:text property="sGroupId"  styleClass="textBox textBoxSmall"  styleId="search7" maxlength="60"/>
				</logic:notMatch>	
			</logic:notEmpty>	
			
				</td>
		    	<td valign="bottom" nowrap>Vidal Health TPA Branch:<br>
	        	<html:select  property="sTtkBranch" styleClass="selectBox selectBoxMedium" >
	        	<html:option  value="">Any</html:option>
				<html:optionsCollection name="officeInfo" label="cacheDesc" value="cacheId"/>
				</html:select>
	        </td>
			
			--%> 
	    
	        <td width="100%" valign="bottom"><a href="#" accesskey="s" onClick="javascript:onSearch()"   class="search"><img src="/ttk/images/SearchIcon.gif" alt="Search" width="16" height="16" border="0" align="absmiddle">&nbsp;<u>S</u>earch</a></td>
	       
	        </tr>
  	
	</table>
	
	 <ttk:HtmlGrid name="enrolltableData"/>
	<!-- E N D : Search Box -->
	<!-- S T A R T : Grid -->
	          
		
    <table  align="right" class="buttonsContainerGrid"  border="0" cellspacing="0" cellpadding="0">
  <tr>
    <td >&nbsp;&nbsp;</td>
        <td nowrap align="right"><button type="button" name="Button" accesskey="c" class="buttons" onMouseout="this.className='buttons'" onMouseover="this.className='buttons buttonsHover'" onClick="javascript:onClose();"><u>C</u>lose</button>
         </td>
  </tr>
  <ttk:PageLinks name="enrolltableData"/>
</table>
	  
  
		
	
  </div>
	<!-- E N D : Buttons and Page Counter -->
	<!-- E N D : Content/Form Area -->
	<INPUT TYPE="hidden" NAME="rownum" VALUE="">
	<INPUT TYPE="hidden" NAME="mode" VALUE="">
	<INPUT TYPE="hidden" NAME="sortId" VALUE="">
	<INPUT TYPE="hidden" NAME="pageId" VALUE="">
	<INPUT TYPE="hidden" NAME="tab" VALUE="">
	<INPUT TYPE="hidden" NAME="sublink" VALUE="<%=TTKCommon.getActiveSubLink(request)%>">
	<html:hidden property="policySeqID"/>
</html:form>
<!-- E N D : Main Container Table -->