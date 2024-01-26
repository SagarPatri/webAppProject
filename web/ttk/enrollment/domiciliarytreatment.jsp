<%
/** @ (#) domiciliarytreatment.jsp Mar 8, 2006
 * Project      : TTK Healthcare Services
 * File         : domiciliarytreatment.jsp
 * Author       : Pradeep R
 * Company       : Span Systems Corporation
 * Date Created  : Mar 8, 2006
 * 
 * @author 		 : pPradeep R
 * Modified by   : 
 * Modifiedv date : 
 * Reason        : 
 *
 */
%>

<%@ taglib uri="/WEB-INF/tlds/ttk-tags.tld" prefix="ttk" %>
<%@ taglib uri="/WEB-INF/tlds/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/tlds/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/tlds/struts-logic.tld" prefix="logic" %>
<%@ page import="com.ttk.common.TTKCommon,com.ttk.common.security.Cache,org.apache.struts.action.DynaActionForm"%>

<SCRIPT LANGUAGE="JavaScript" SRC="/ttk/scripts/validation.js"></SCRIPT>
<script language="javascript" src="/ttk/scripts/enrollment/domiciliarytreatment.js"></script>
<script type="text/Javascript">
function checkDec(el){
 var ex = /^[0-9]+\.?[0-9]*$/;
 if(ex.test(el.value)==false){
   el.value = el.value.substring(0,el.value.length - 1);
  }
}
</script>
<%
	//added for Policy Deductable - KOC-1277
	DynaActionForm frmDomiciliary = (DynaActionForm)request.getSession().getAttribute("frmDomiciliary");
	String PolicyDeductableTypeId = frmDomiciliary.getString("policyDeductableTypeId");
	
	boolean viewmode=true;boolean checkmode=true;
	if(TTKCommon.isAuthorized(request,"Edit"))
	{
		viewmode=false;
	}//end of if(TTKCommon.isAuthorized(request,"Edit"))
	if(PolicyDeductableTypeId.equals("PNF"))
	{
		checkmode=false;
	}
	//ended
	pageContext.setAttribute("viewmode",new Boolean(viewmode));
%>

<html:form action="/DomiciliaryAction.do" > 
	<!-- S T A R T : Page Title -->
	<logic:match name="frmPolicyList" property="switchType" value="ENM">
	<table align="center" class="pageTitle" border="0" cellspacing="0" cellpadding="0">
	</logic:match>
	<logic:match name="frmPolicyList" property="switchType" value="END">
	<table align="center" class="pageTitleHilite" border="0" cellspacing="0" cellpadding="0">
	</logic:match>
	    <tr>
    		<td width="100%"><bean:write name="frmDomiciliary" property="caption" /></td>
    		<td align="right">&nbsp;&nbsp;&nbsp;</td>    
   		</tr>
</table>
<!-- E N D : Page Title --> 



<div class="contentArea" id="contentArea">
<html:errors/>
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
<!-- S T A R T : Form Fields -->
	<fieldset>
	
 <legend>Domiciliary Treatment(OPD) / Hospitalization Limits/High Deductable</legend>
	<logic:notEmpty name="frmDomiciliary" property="domiciliaryInfo">
		<table align="center" class="formContainer" border="0" cellspacing="0" cellpadding="0">
		  <tr>
		    <td width="17%" align="left" nowrap class="formLabel">Domiciliary Type (OPD):</td>
		    <td width="83%" align="left" nowrap class="textLabelBold">
		    	<bean:write name="frmDomiciliary" property="domiciliaryTypeDesc"/>
		    </td>
		  </tr>
		  <tr><td height="10" colspan="2" align="left" nowrap class="formLabel"></td></tr> 
		</table>
	</logic:notEmpty>

	<table align="center" class="gridWithCheckBox zeroMargin" border="0" cellspacing="0" cellpadding="0">
      <tr>
        <td width="20%" nowrap class="gridHeader">Member Name</td>
       <td width="20%" nowrap class="gridHeader">Domiciliary Hospitalization Amount</td><!-- added as per KOC 1285 Change Request -->
        <td width="20%" nowrap class="gridHeader">OPD Type</td>
        <td width="20%" nowrap class="gridHeader">OPD Limit </td>
        <!-- new Header Names added for Policy Deductable - KOC-1277-->
        <td width="20%" nowrap class="gridHeader">Member Deduct(Y/N)</td>
        <td width="25%" nowrap class="gridHeader">Ded.Amount</td>
  
        </tr>
      
        <logic:empty name="frmDomiciliary" property="domiciliaryInfo">
			<tr>
				<td class="generalcontent" colspan="3">&nbsp;&nbsp;No Data Found</td>
			</tr>
       </logic:empty>
      <logic:notEmpty name="frmDomiciliary" property="domiciliaryInfo">
      <%
      	int i=0;
      	String strGridStyle="";
      %>
	      <logic:iterate id="domiciliaryInfo" name="frmDomiciliary" property="domiciliaryInfo" >
	       <html:hidden name="domiciliaryInfo" property="memberSeqID" />
	      <%
	      	if(i%2==0)
				strGridStyle="gridOddRow";
			else
				strGridStyle="gridEvenRow";
		  %>    		
	      <tr class="<%=strGridStyle%>">
      		<td>
      			<bean:write name="domiciliaryInfo" property="name"/>
      			<html:hidden name="domiciliaryInfo" property="name" />
			</td>
			
			<logic:empty name="frmDomiciliary" property="domiciliaryTypeID">
		 		<td>
					<!--	<input type="text" name="limitName" Class="textBoxDisabled textBoxSmall" disabled="true" onfocus="this.blur()"  maxlength="" value="<bean:write name="domiciliaryInfo" property="hospAmt"/>">
					<html:hidden name="domiciliaryInfo" property="domHospAmt"/> -->
					<html:text name="domiciliaryInfo" property="domHospAmt" styleClass="textBox textBoxSmall" maxlength="13" disabled="<%=viewmode%>" /></td>
				</td>
				<td>
		 			<html:select name="domiciliaryInfo" property="domiciliaryTypeID" styleClass="selectBox selectBoxMedium" >
						<html:option value="">Select from list</html:option>
					</html:select>
				</td>
				<td>
					<input type="text" name="limitName" Class="textBoxDisabled textBoxSmall" disabled="true" onfocus="this.blur()"  maxlength="" value="<bean:write name="domiciliaryInfo" property="limit"/>">
					<html:hidden name="domiciliaryInfo" property="domiciliaryLimit"/>
				</td>
		    </logic:empty>
			
      		<logic:match name="frmDomiciliary" property="domiciliaryTypeID" value="PFL">
				<%
					pageContext.setAttribute("listDomiciliary",Cache.getCacheObject("floater"));
				%>	
						<td>
						<!--	<input type="text" name="limitName" Class="textBoxDisabled textBoxSmall" disabled="true" onfocus="this.blur()"  maxlength="" value="<bean:write name="domiciliaryInfo" property="hospAmt"/>">
							<html:hidden name="domiciliaryInfo" property="domHospAmt"/> -->
							<html:text name="domiciliaryInfo" property="domHospAmt" styleClass="textBox textBoxSmall" maxlength="13" disabled="<%=viewmode%>" /></td>
						</td>
						<td>
							<html:select name="domiciliaryInfo" property="domiciliaryTypeID" styleClass="selectBox selectBoxMedium" >
								<html:options collection="listDomiciliary" property="cacheId" labelProperty="cacheDesc"/>
							</html:select>
						</td>						
						<td>
							<input type="text" name="limitName" Class="textBoxDisabled textBoxSmall" disabled="true" onfocus="this.blur()"  maxlength="" value="<bean:write name="domiciliaryInfo" property="limit"/>">
							<html:hidden name="domiciliaryInfo" property="domiciliaryLimit"/>
						</td>
			</logic:match>
       
      		<logic:match name="frmDomiciliary" property="domiciliaryTypeID" value="PNF">
	      	<%
	      		pageContext.setAttribute("listDomiciliary",Cache.getCacheObject("nonFloater"));
	      	%>
	      		<td>
						<html:text name="domiciliaryInfo" property="domHospAmt" styleClass="textBox textBoxSmall" maxlength="13" disabled="<%=viewmode%>" /></td>
				</td>
		      	<td>
						<html:select name="domiciliaryInfo" property="domiciliaryTypeID" styleClass="selectBox selectBoxMedium" >
							<html:options collection="listDomiciliary" property="cacheId" labelProperty="cacheDesc"/>
						</html:select>
				</td>
				<td>
						<html:text name="domiciliaryInfo" property="domiciliaryLimit" styleClass="textBox textBoxSmall" maxlength="13" disabled="<%=viewmode%>" onblur="javascript:addOverAllFamily()"/></td>
				</td>
			</logic:match>
			<!--added for Policy Deductable No Blur - KOC-1277-->
			<logic:match name="frmDomiciliary" property="policyDeductableTypeId" value="PFL">
			
				<td>
					<input type="checkbox" name="checkbox" disabled="true" onclick="javascript:onCheck()"/>
					<html:hidden name="domiciliaryInfo" property="memberDeductableYN"/>				
				</td>
				<td>
					<input type="text" name="dedFLlimit"  Class="textBoxDisabled textBoxSmall" disabled="true" onfocus="this.blur()" maxlength="13" disabled="<%=checkmode%>"/> 
					<html:hidden name="domiciliaryInfo" property="policyDeductableLimit"/>
				</td>
			</logic:match>
			
			<!-- Blur - KOC-1277-->
			<logic:match name="frmDomiciliary" property="policyDeductableTypeId" value="PNF">
			
				<td>
					<input type="checkbox" name="checkbox"  onclick="javascript:onCheck()"/>				
					<html:hidden name="domiciliaryInfo" property="memberDeductableYN"/>
				</td>
				<td>
					<!-- <input type="text" name="dedNFLlimit"  Class="textBox textBoXSmall" maxlength="13"  onblur="javascript:addOverallDeductableAmount()"/> -->
					<html:text name="domiciliaryInfo" property="policyDeductableLimit" styleClass="textBox textBoxSmall" maxlength="13" disabled="<%=viewmode%>"  onkeyup="checkDec(this)"  onblur="javascript:addOverallDeductableAmount()"/>
				</td>
				
			</logic:match>
			
			
			
			<!--ended for Policy Deductable-->
	 </tr>
		    <%
		    	i++;	// increment the record counter
		    %>
	      </logic:iterate>
      </logic:notEmpty>
    </table>
         

		<table class="gridTotal" border="0" cellspacing="0" cellpadding="0">
	      <tr>
			<logic:match name="frmDomiciliary" property="domiciliaryTypeID" value="PNF">
    		<logic:notEmpty name="frmDomiciliary" property="domiciliaryInfo">
	        <td nowrap width="30%">&nbsp;</td>
	        <td width="42%" align="right" nowrap class="formLabel">Overall Family Limit: </td>
	        <td width="42%" nowrap>	       
       <html:text property="totalFlyLimit" styleClass="textBox textBoxSmall" maxlength="13" readonly="true" disabled="<%=viewmode%>"/></td>
	    	</logic:notEmpty>
			</logic:match>
 
	   	<logic:match name="frmDomiciliary" property="domiciliaryTypeID" value="PFL">
	   <logic:notEmpty name="frmDomiciliary" property="domiciliaryInfo">
		   <td nowrap width="30%">&nbsp;</td>
		   <td width="42%" align="right" nowrap class="formLabel">Overall Family Limit: </td>
		   <td width="42%" nowrap>
		  <html:text property="totalFlyLimit" styleClass="textBox textBoxSmall" maxlength="13" disabled="<%=viewmode%>"/></td>
	  </logic:notEmpty>
	</logic:match>

	
   	<logic:match name="frmDomiciliary" property="domiciliaryTypeID" value="">
	   <logic:notEmpty name="frmDomiciliary" property="domiciliaryInfo">
		   <td nowrap width="30%">&nbsp;</td>
		   <td width="42%" align="right" nowrap class="formLabel"></td>
		   <td width="48%" nowrap>
		   </td>
	  </logic:notEmpty>
	</logic:match>
	<!--Added for Policy Deductable - KOC-1277  -->
		<logic:match name="frmDomiciliary" property="policyDeductableTypeId" value="PFL">
	       <!-- added for Policy Deductable-->
	      	<td>
	      		<html:checkbox name="domiciliaryInfo" property="overallFamilyCheckYN" onclick="javascript:onCheck()" value="Y" disabled = "false"/>	
	      		<!--<input type="checkbox" name="checkbox"  onclick="javascript:onCheck()"/>-->				
					<html:hidden name="domiciliaryInfo" property="overallFamilyCheckYN" value="N"/>			
			</td>
	       <!-- added for Policy Deductable-->
	        <td width="38%" align="right" nowrap class="formLabel">Overall Deductable Limit: </td>
	       <td nowrap width="20%">&nbsp;</td>
	       <!--<td width="30%" align="right" nowrap class="formLabel">Overall Deductable Limit: </td> -->
	        <td width="28%" nowrap>
	       <html:text property="totalFlyDeductableLimit" styleClass="textBox textBoxSmall" maxlength="13" readonly="false"  onkeyup="checkDec(this)"  disabled="<%=viewmode%>"/></td>
	   	   <!-- ended-->
	   </logic:match>
	
	    <logic:match name="frmDomiciliary" property="policyDeductableTypeId" value="PNF">
	       <!-- added for Policy Deductable-->
	      	<td>
	      		<html:checkbox name="domiciliaryInfo" property="overallFamilyCheckYN" disabled = "true"/>				
			</td>
	       <!-- added for Policy Deductable-->
	       <td width="38%" align="right" nowrap class="formLabel">Overall Deductable Limit: </td>
	       <td nowrap width="20%">&nbsp;</td>
	       <!--<td width="30%" align="right" nowrap class="formLabel">Overall Deductable Limit: </td> -->
	        <td width="28%" nowrap>
	    <html:text property="totalFlyDeductableLimit" styleClass="textBox textBoxSmall" maxlength="13" readonly="true" disabled="<%=viewmode%>"/></td>
		</logic:match>
		 </tr>
	    </table>
	
	</fieldset>
<!-- S T A R T : Buttons -->

<table align="center" class="buttonsContainer"  border="0" cellspacing="0" cellpadding="0">
  <tr>
    <td width="100%" align="center">
    <logic:notEmpty name="frmDomiciliary" property="domiciliaryInfo">
    	 <logic:match name="viewmode" value="false">
    	 	<button type="button" name="Button" accesskey="s" class="buttons" onMouseout="this.className='buttons'" onMouseover="this.className='buttons buttonsHover'" onClick="javascript:onSave()"><u>S</u>ave</button>&nbsp;
			<button type="button" name="Button" accesskey="r" class="buttons" onMouseout="this.className='buttons'" onMouseover="this.className='buttons buttonsHover'" onClick="javascript:onReset()"><u>R</u>eset</button>&nbsp;
    	 </logic:match>
  </logic:notEmpty>
		    <button type="button" name="Button" accesskey="c" class="buttons" onMouseout="this.className='buttons'" onMouseover="this.className='buttons buttonsHover'" onClick="javascript:onClose()"><u>C</u>lose</button>&nbsp;
    </td>
    
  </tr>
</table>
</div>
<!-- E N D : Buttons -->
<!-- E N D : Form Fields -->
<script language="javascript">
		onDocumentLoad();
</script>
<INPUT TYPE="hidden" NAME="mode" value="">
<!-- Added for Policy Deductable - KOC-1277  -->
<html:hidden name="frmDomiciliary" property="policyDeductableTypeId"/>
 <logic:notEmpty name="domiciliaryInfo">
<html:hidden name="domiciliaryInfo" property="overallFamilyCheckYN"/>
</logic:notEmpty>
 <logic:empty name="domiciliaryInfo">
 <input type="hidden" name="overallFamilyCheckYN">
</logic:empty>
</html:form>
	<!-- E N D : Content/Form Area -->