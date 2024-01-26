<%
/**
 * @ (#) bankaccountlist.jsp 10th June 2006
 * Project      : TTK HealthCare Services
 * File         : bankaccountlist.jsp
 * Author       : Harsha Vardhan B N
 * Company      : Span Systems Corporation
 * Date Created : 10th June 2006
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
<script language="javascript" src="/ttk/scripts/finance/customerbankdetails.js"></script>
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
<html:form action="/CustomerBankDetails.do" >
    <table align="center" class="pageTitle" border="0" cellspacing="0" cellpadding="0">
	    <tr>
	    <td><bean:write name="frmCustomerBankDetailsSearch" property="caption"/></td>     
	    <td align="right" class="webBoard">&nbsp;<%@ include file="/ttk/common/toolbar.jsp" %></td>
	    </tr>
	    </table>
	
	<!-- E N D : Page Title -->
  <div class="contentArea" id="contentArea">
	<!-- S T A R T : Search Box -->
	<html:errors/>
	<table align="center" class="tablePad"  border="0" cellspacing="0" cellpadding="0">
      <tr>
        <td width="10%" nowrap class="textLabelBold">Switch to:</td>
        <td width="90%">
	        <html:select property="switchType" styleClass="specialDropDown" styleId="switchType" onchange="javascript:onSwitch()">
	        <html:options collection="listSwitchType" property="cacheId" labelProperty="cacheDesc"/>
			</html:select>
		</td>
      </tr>   
    </table>
	<table align="center" class="searchContainer" border="0" cellspacing="0" cellpadding="0">
  		<logic:match name="frmCustomerBankDetailsSearch" property="switchType" value="POLC">
  		<tr>
    		
	    	 
	    	<td nowrap>Scheme&nbsp;No.:<br>
	     		<html:text property="sPolicyNumber" styleClass="textBox textBoxMedium"  maxlength="60" />
	    	</td>
	    	<td nowrap>Enrollment&nbsp;No.:<br>
    	  		<html:text property="sEnrollNumber" styleClass="textBox textBoxMedium"  maxlength="60" />
      		</td>
	  		<td nowrap>Beneficiary&nbsp;Name:<br>
	     		<html:text property="sInsuredName" styleClass="textBox textBoxMedium"  maxlength="60" />
	    	</td>		   	  
	    	</tr>
		   	  <tr>
		   	  <td nowrap>Scheme Type: <br>
		      <html:select  property="sPolicyType"  styleClass="selectBox selectBoxMedium" styleId="search11" onchange="javascript:changePolicyType();" >
		          	<html:optionsCollection name="policyType" value="cacheId" label="cacheDesc"/>
		      </html:select>
		    </td>
		    <td nowrap>Group Id:<br>
	     		<logic:empty name="frmCustomerBankDetailsSearch" property="sPolicyType">
	        	<html:text property="sGroupId"  styleClass="textBox textBoxSmall"  styleId="search7" maxlength="60" style="background-color: #EEEEEE;" readonly="true"/>
	        </logic:empty>
	        <logic:notEmpty name="frmCustomerBankDetailsSearch" property="sPolicyType">
		        <logic:match name="frmCustomerBankDetailsSearch" property="sPolicyType" value="IND">
		            		<html:text property="sGroupId"  styleClass="textBox textBoxSmall"  styleId="search7" maxlength="60" style="background-color: #EEEEEE;" readonly="true"/>
				</logic:match>
				<logic:notMatch name="frmCustomerBankDetailsSearch" property="sPolicyType" value="IND">
					<html:text property="sGroupId"  styleClass="textBox textBoxSmall"  styleId="search7" maxlength="60"/>
				</logic:notMatch>	
			</logic:notEmpty>	
	    	</td>
		    	<td valign="bottom" nowrap>Branch Location:<br>
	        	<html:select  property="sTtkBranch" styleClass="selectBox selectBoxMedium" >
	        	<html:option  value="">Any</html:option>
				<html:optionsCollection name="officeInfo" label="cacheDesc" value="cacheId"/>
				</html:select>
	        </td>
	        <td width="100%" valign="bottom"><a href="#" accesskey="s" onClick="javascript:onSearch()"   class="search"><img src="/ttk/images/SearchIcon.gif" alt="Search" width="16" height="16" border="0" align="absmiddle">&nbsp;<u>S</u>earch</a></td>
	        </tr>
  		</logic:match>
	</table>
	<table align="center" class="searchContainer" border="0" cellspacing="0" cellpadding="0">
  		<tr>
    		
	    	 <logic:match name="frmCustomerBankDetailsSearch" property="switchType" value="CLAM">
	    	<td nowrap>Claim&nbsp;No:<br>
	     		<html:text property="sClaimNumber" styleClass="textBox textBoxMedium"  maxlength="60" />
	    	</td>
	    	<td nowrap>Claim Settlement&nbsp;No.:<br>
    	  		<html:text  property="sClaimSettmentNumber" styleClass="textBox textBoxMedium"  maxlength="60" />
      		</td>
	  		<td nowrap>Member&nbsp;Name:<br>
	     		<html:text property="sClaimentName" styleClass="textBox textBoxMedium"  maxlength="60" />
	    	</td>
	    	
	    	
	  		<td valign="bottom" nowrap>Vidal Health Branch:<br>
	        	<html:select  property="sTtkBranch" styleClass="selectBox selectBoxMedium" >
				<html:option  value="">Any</html:option>
				<html:optionsCollection name="officeInfo" label="cacheDesc" value="cacheId"/>
				</html:select>
	        </td>
	       
	        
	    	<td width="100%" valign="bottom"><a href="#" accesskey="s" onClick="javascript:onSearch()"   class="search"><img src="/ttk/images/SearchIcon.gif" alt="Search" width="16" height="16" border="0" align="absmiddle">&nbsp;<u>S</u>earch</a></td>
	        </logic:match>
	        </td>
	        
  		</tr>
	</table>
	
	<table align="center" class="searchContainer" border="0" cellspacing="0" cellpadding="0">
  		<tr>
  		<logic:match name="frmCustomerBankDetailsSearch" property="switchType" value="HOSP">
    		<td nowrap>Empanelment&nbsp;No.:<br>
    	  		<html:text property="sEmpanelmentNo" styleClass="textBox textBoxMedium"  maxlength="60" />
      		</td>
	  		<td nowrap>Hospital&nbsp;Name:<br>
	     		<html:text property="sHospitalName" styleClass="textBox textBoxMedium"  maxlength="60" />
	    	</td>
	        <td valign="bottom" nowrap>Vidal Health Branch:<br>
	        	<html:select  property="sTtkBranch" styleClass="selectBox selectBoxMedium" >
				<html:option  value="">Any</html:option>
				<html:optionsCollection name="officeInfo" label="cacheDesc" value="cacheId"/>
				</html:select>
	        </td>
	        <td width="100%" valign="bottom"><a href="#" accesskey="s" onClick="javascript:onSearch()"   class="search"><img src="/ttk/images/SearchIcon.gif" alt="Search" width="16" height="16" border="0" align="absmiddle">&nbsp;<u>S</u>earch</a></td>
	        </logic:match>
  		</tr>
	</table>
	
	
	<!-- E N D : Search Box -->
	<!-- S T A R T : Grid -->
	          
		    
   <logic:match name="frmCustomerBankDetailsSearch" property="switchType" value="CLAM">
	<ttk:HtmlGrid name="tableData"/>
	</logic:match>
	<logic:match name="frmCustomerBankDetailsSearch" property="switchType" value="POLC">
	<ttk:HtmlGrid name="tableData"/>
	</logic:match>
    <logic:match name="frmCustomerBankDetailsSearch" property="switchType" value="HOSP">
	<ttk:HtmlGrid name="tableData"/>
	</logic:match>
	
	<table align="center" class="buttonsContainerGrid"  border="0" cellspacing="0" cellpadding="0">
      <tr>
      <td width="27%">&nbsp;</td>
        <td width="73%" align="right">
      <%
        	if(TTKCommon.isDataFound(request,"tableData"))
	    	{
        		//TableData tableData = (TableData)request.getSession().getAttribute("tableData");
        		//if(((Column)((ArrayList)tableData.getTitle()).get(1)).isLink())
        		//{
	    %>
	    	<button type="button" name="Button" accesskey="c" class="buttonsCopyWB" onMouseout="this.className='buttonsCopyWB'" onMouseover="this.className='buttonsCopyWB buttonsCopyWBHover'" onClick="javascript:copyToWebBoard()"><u>C</u>opy to Web Board</button>&nbsp;
        <%
        	//}
      }
	%>
	</td>
	
	</tr>
	  <ttk:PageLinks name="tableData"/>
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
	<INPUT TYPE="hidden" NAME="from" VALUE="">
</html:form>
<!-- E N D : Main Container Table -->