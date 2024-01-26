<%
/**
 * @ (#)  tdstaxrateconf.jsp July 28, 2009
 * Project      : TTKPROJECT
 * File         : tdstaxrateconf.jsp
 * Author       : Balakrishna Erram
 * Company      : Span Systems Corporation
 * Date Created : July 28, 2009
 *
 * @author       :  Balakrishna Erram
 * Modified by   :  
 * Modified date :  
 * Reason        :  
 *
 * Modified by   :  
 * Modified date :  
 * Reason        :  
 */
 %>
 
<%@ taglib uri="/WEB-INF/tlds/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/tlds/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/tlds/ttk-tags.tld" prefix="ttk" %>
<%@ page import=" com.ttk.common.TTKCommon" %>

<script language="javascript" src="/ttk/scripts/administration/tdstaxrateconf.js"></script>

<!-- S T A R T : Content/Form Area -->	
	<html:form action="/TDSTaxConfigurationAction.do"> 	
	<!-- S T A R T : Page Title -->
	<table align="center" class="pageTitle" border="0" cellspacing="0" cellpadding="0">
	  <tr>
	    <td><bean:write name="frmtdsRateList" property="caption"/></td>
	  </tr>
	</table>
	<!-- E N D : Page Title --> 
	<div class="contentArea" id="contentArea">
	<!-- S T A R T : Grid -->
	<ttk:HtmlGrid name="tableData"/>
	<!-- E N D : Grid -->
    
    <!-- S T A R T : Buttons and Page Counter -->
	<table align="center" class="buttonsContainerGrid"  border="0" cellspacing="0" cellpadding="0">
	  <tr>
	    <td width="73%" nowrap>&nbsp;</td>
	    <td align="right" nowrap>
	    <%
			    if(TTKCommon.isAuthorized(request,"Edit"))
				{
			%>
            <button type="button" name="Button" accesskey="a" class="buttons" onMouseout="this.className='buttons'" onMouseover="this.className='buttons buttonsHover'" onClick="javascript:onAdd();"><u>A</u>dd</button>&nbsp;
            <%
            	}//end of if(TTKCommon.isAuthorized(request,"Edit"))
            %>		
            <button type="button" name="Button" accesskey="c" class="buttons" onMouseout="this.className='buttons'" onMouseover="this.className='buttons buttonsHover'" onClick="javascript:onClose();"><u>C</u>lose</button>&nbsp;
	    </td>
	  </tr>
 	</table>
	</div>
    <!-- E N D : Buttons and Page Counter -->

    <INPUT TYPE="hidden" NAME="sortId" VALUE="">
    <INPUT TYPE="hidden" NAME="mode" VALUE="">
   	<INPUT TYPE="hidden" NAME="rownum" VALUE=''>
   	<INPUT TYPE="hidden" NAME="caption" VALUE='<bean:write name="frmtdsRateList" property="caption"/>'>
   	<INPUT TYPE="hidden" NAME="tab" VALUE="">
   	<INPUT TYPE="hidden" NAME="tdsCatTypeID" VALUE='<bean:write name="frmtdsRateList" property="tdsCatTypeID"/>'>
   	</html:form>
<!-- E N D : Content/Form Area -->