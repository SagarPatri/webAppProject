<%
/** @ (#) filelist.jsp 
 * Project     : TTK Healthcare Services
 * File        : filelist.jsp
 * Author      : Balaji C R B
 * Company     : Span Systems Corporation
 * Date Created: January 24,2008
 *
 * @author 		 : Balaji C R B
 * Modified by   :
 * Modified date :
 * Reason        :
 *
 */
 %>
<%@ taglib uri="/WEB-INF/tlds/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/tlds/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<script language="javascript" src="/ttk/scripts/administration/filelist.js"></script>
<%
	pageContext.setAttribute("alFileList",request.getAttribute("alFileList"));
	
%>

<html:form action="/LinkDetailsAction.do">
<!-- S T A R T : Page Title -->
	<table align="center" class="pageTitle" border="0" cellspacing="0" cellpadding="0">
  	<tr>
    	<td width="90%">File List</td>
  	</tr>
	</table>
	<!-- E N D : Page Title -->
&nbsp;&nbsp;

<fieldset>
	<legend>File List</legend>
	<div class="scrollableGrid" style="height:350px;">
	  <table class="gridWithCheckBox zeroMargin" border="0" cellspacing="0" cellpadding="0" width="80%" align="center">
	  <logic:notEmpty name="alFileList">
	   <tr>
          <td width="98%" nowrap class="gridHeader">File Name</td>          
          <td width="2%" nowrap class="gridHeader">&nbsp;</td>
       </tr>
	  </logic:notEmpty>
	  <logic:empty name="alFileList">
	  	<tr>
          <td> No files found </td>
        </tr>
	  </logic:empty>	
	  <% int i=0; %>
	  <logic:iterate id="file" name="alFileList">
	  		<%
				String strClass=i%2==0 ? "gridOddRow" : "gridEvenRow" ;
				i++;
			%>
	 		 <tr class=<%=strClass%>>
	  			<td ><bean:write name="file"/></td>	
	  			<td ><input type="radio" name="chkopt"  value="radiobutton" onclick="javascript:toRadioButton(this,this.checked,document.forms[1],'<bean:write name="file"/>')"></td>	
	  		</tr>	  
	  </logic:iterate>
	 </table>
	 </div>
</fieldset>

<!-- S T A R T : Buttons -->
<table align="center" class="buttonsContainer"  border="0" cellspacing="0" cellpadding="0">
  <tr>
    <td width="100%"  align="center" nowrap class="formLabel">
        <button type="button" name="selectButton" accesskey="s" class="buttons" onMouseout="this.className='buttons'" onMouseover="this.className='buttons buttonsHover'" style="display:none" onClick="javascript:onSelect()"><u>S</u>elect</button>&nbsp;
		<button type="button" name="closeButton" accesskey="c" class="buttons" onMouseout="this.className='buttons'" onMouseover="this.className='buttons buttonsHover'" onClick="javascript:onClose()"><u>C</u>lose</button>&nbsp;        
    </td>
  </tr>
</table>
<!-- E N D : Buttons -->
<INPUT TYPE="hidden" NAME="mode" VALUE="">
<html:hidden property="path"/>
</html:form>

