<%@page import="com.ttk.dto.empanelment.HospitalDetailVO"%>
<%
/** @ (#) haadFactorsDetails.jsp 17th Aug 2016
 * Project     : TTK Healthcare Services
 * File        : haadFactorsDetails.jsp
 * Author      : Kishor kumar
 * Company     : RCS Technologies
 * Date Created: 
 *
 * @author 		 : Kishor kumar
 * Modified by   :
 * Modified date :
 * Reason        :
 *
 */
%>
<%@ taglib uri="/WEB-INF/tlds/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/tlds/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/tlds/ttk-tags.tld" prefix="ttk" %>
<%@ taglib uri="/WEB-INF/tlds/struts-bean.tld" prefix="bean"%>
<%@ page import="java.util.ArrayList,com.ttk.common.TTKCommon,com.ttk.common.security.Cache,org.apache.struts.action.DynaActionForm" %>
<SCRIPT LANGUAGE="JavaScript" SRC="/ttk/scripts/validation.js"></SCRIPT>
<script language="javascript" src="/ttk/scripts/empanelment/haadFactorsDetails.js"></script>
<script language="javascript" src="/ttk/scripts/calendar/calendar.js"></script>
<!-- <script type="text/javascript">
window.onload = function () {
	
	document.getElementById("anetworkType").onclick = null;
	}
</script> -->
<head>

	<link rel="stylesheet" type="text/css" href="ttk/styles/style.css" />
</head>
<%



ArrayList alHaadCategories=(ArrayList)request.getAttribute("alHaadCategories")==null?(ArrayList)request.getSession().getAttribute("alHaadCategories"):(ArrayList)request.getAttribute("alHaadCategories");
ArrayList alHistoryOfTariffUpdates=(ArrayList)request.getAttribute("alHistoryOfTariffUpdates")==null?(ArrayList)request.getSession().getAttribute("alHistoryOfTariffUpdates"):(ArrayList)request.getAttribute("alHistoryOfTariffUpdates");
ArrayList alColumnHeaders=(ArrayList)request.getSession().getAttribute("alColumnHeaders");
String hosp_seq_id	=	request.getParameter("hosp_seq_id")==null?"0":request.getParameter("hosp_seq_id");

request.getSession().setAttribute("alHaadCategories", alHaadCategories);
pageContext.setAttribute("haadGroup",Cache.getCacheObject("haadGroup"));
pageContext.setAttribute("haadfactor",Cache.getCacheObject("haadfactor"));
ArrayList alEligibleNetworks	=	(ArrayList)request.getSession().getAttribute("alEligibleNetworks");

String editFlag	=	request.getParameter("editFlag");
boolean viewMode	=	false;
String styleClass	=	"textBoxSmallest";
String styleClassDrug="disabledBox";
if(!"Edit".equals(editFlag)){
	viewMode	=	true;
	styleClass	=	"disabledBox";
}
boolean genricDrugFlag=true;
String  genericDrugClass="disabledBox";
/* String primaryNetwork	=	(String) alHaadCategories.get(alHaadCategories.size()-1);
alHaadCategories.remove(alHaadCategories.size()-1);//REMOVING THE LAST ADDE STRING IN IMPL CLASS WHICH IS NOT REQUIRED FOR ITERATING
 */
%>

<!-- S T A R T : Content/Form Area -->
<html:form action="/EditHaadFactorsHospitalAction.do">

	<!-- S T A R T : Page Title -->
	<table align="center" class="pageTitle" border="0" cellspacing="0" cellpadding="0">
		<tr>
	    	<td>&nbsp;&nbsp;HAAD Factors 
		   	</td>
		</tr>
	</table>
	<!-- E N D : Page Title -->
	
	<!-- S T A R T : Form Fields -->
	
	<div class="contentArea" id="contentArea">
			<html:errors/>
			
			<!-- S T A R T : Success Box -->
		<logic:notEmpty name="updated" scope="request">
			<table align="center" class="successContainer" style="display:" border="0" cellspacing="0" cellpadding="0">
				<tr>
			  		<td>
			  			<img src="/ttk/images/SuccessIcon.gif" alt="Success" width="16" height="16" align="absmiddle">&nbsp;
						<bean:message name="updated" scope="request"/>
			  		</td>
				</tr>
			</table>
		</logic:notEmpty>
		<!-- E N D : Success Box -->
		
		<logic:notEqual value="Edit" name="editFlag">
		<fieldset>
<legend>Add Haad Factor</legend>
<table align="center" class="searchContainer" border="0" style="width: 70%">
<tr>
<td align="right" nowrap>Select Network Type:&nbsp;&nbsp;&nbsp;</td>
				<td align="left">
					<b>ALL</b> &nbsp;&nbsp;&nbsp;&nbsp;<html:select property ="networkTypeYN" styleId="networkTypeYN" styleClass="selectBox selectBoxMedium" onchange="changeNetworkTypeYN()"  style="width:62px;height:22px;">
						<html:option value="Y">Yes</html:option>          					               		
						<html:option value="N">No</html:option> 
					</html:select>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
				<b>(or)</b> &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Network Type <span class="mandatorySymbol">*</span>:
		<logic:equal property="networkTypeYN" name="frmAddHaadFactors" value="Y">				
				<html:text property="eligibleNetworks" styleId="eligibleNetworks" styleClass="textBox textBoxMedium" readonly="true"  value="CN|GN|SN|EN|PN|ON|RN|CO"/>
			<a href="#"    style="display: inline;pointer-events: none;cursor: default;"   id="anetworkType"><img src="/ttk/images/EditIcon.gif" alt="Select Networks" width="16" height="16" border="0" align="absmiddle"></a>
		   </logic:equal>
		   <logic:equal property="networkTypeYN" name="frmAddHaadFactors" value="N">
		   <html:text property="eligibleNetworks" styleId="eligibleNetworks" styleClass="textBox textBoxMedium" readonly="true"  />
		   <a href="#" onClick="openListTariffIntX('eligibleNetworks','NETWORKSGEN','providerCode')" style="display: inline;" id="anetworkType"><img src="/ttk/images/EditIcon.gif" alt="Select Networks" width="16" height="16" border="0" align="absmiddle"></a>
		   </logic:equal>
		</td>
</tr>
<tr>

		<td align="right" nowrap>Select Group <span class="mandatorySymbol">*</span>: </td>
		<td align="left" >
		 	<html:text property="haadGroup" styleId="haadGroup" styleClass="textBox textBoxMedium" readonly="true" />
			<a href="#" onClick="openListIntX('haadGroup','HAADGROUP')" style="display: inline;" id="anetworkType"><img src="/ttk/images/EditIcon.gif" alt="Select Networks" width="16" height="16" border="0" align="absmiddle"></a>
		</td>
</tr>
<tr>
	<td align="right" nowrap>Haad Factor: <span class="mandatorySymbol">*</span></td>
	<td align="left">
		<html:select property ="haadfactor" styleClass="selectBox selectBoxMedium">
			<html:option value="">--Select From List--</html:option>          					               		
			<html:options collection="haadfactor" property="cacheId" labelProperty="cacheDesc"/>
		</html:select>
</tr>
<tr>
	<td align="right" nowrap>Tariff Start Date: <span class="mandatorySymbol">*</span></td>
	<td align="left">
			<html:text property="haadTarrifStartDt" styleClass="textBox textDate" maxlength="10" />
			<a name="CalendarObjectempDate11" id="CalendarObjectempDate11" href="#" onClick="javascript:show_calendar('CalendarObjectempDate11','frmAddHaadFactors.haadTarrifStartDt',document.frmAddHaadFactors.haadTarrifStartDt.value,'',event,148,178);return false;" onMouseOver="window.status='Calendar';return true;" onMouseOut="window.status='';return true;">
	<img src="/ttk/images/CalendarIcon.gif" alt="Calendar" name="empDate" width="24" height="17" border="0" align="absmiddle">
			</a>
</tr>
<tr>
	<td align="right" nowrap>Tariff End Date: <span class="mandatorySymbol"></span></td>
	<td align="left">
			<html:text property="haadTarrifEndDt" styleClass="textBox textDate" maxlength="10" />
			<a name="CalendarObjectempDate11" id="CalendarObjectempDate11" href="#" onClick="javascript:show_calendar('CalendarObjectempDate11','frmAddHaadFactors.haadTarrifEndDt',document.frmAddHaadFactors.haadTarrifEndDt.value,'',event,148,178);return false;" onMouseOver="window.status='Calendar';return true;" onMouseOut="window.status='';return true;">
	<img src="/ttk/images/CalendarIcon.gif" alt="Calendar" name="empDate" width="24" height="17" border="0" align="absmiddle">
			</a>
</tr>
<tr>	
	<td align="right" nowrap>Enter Value: <span class="mandatorySymbol">*</span></td>
	<td align="left">
	<html:text property="factorVal" styleClass="textBox textBoxMedium" styleId="search1" maxlength="60" onkeyup="isNumeric(this);"/></td>
</tr>
<tr>
<td align="left"></td>
	<td align="center" colspan="2"><button type="button" name="Button" accesskey="s" class="buttons" onMouseout="this.className='buttons'" onMouseover="this.className='buttons buttonsHover'" onClick="javascript:onUpdateHaadFactors();"><u>S</u>ave</button>&nbsp;
	</td>
</tr>   
</table></fieldset>

</logic:notEqual>
<fieldset>

			<legend>Modify Haad Factors</legend>
			
			<table align="center" class="searchContainer" border="0" style="width: 70%">
			<tr>
				<td align="right" nowrap>Select Network Type</td>
				<td align="left">
					<html:select property ="networkType" styleClass="selectBox selectBoxMedium" onchange="changeNetworkType(this)">
						<html:option value="">--Select From List--</html:option>          					               		
						<html:options collection="alEligibleNetworks" property="cacheId" labelProperty="cacheDesc"/>
					</html:select>
					</td>
			</tr>
			</table>
			<table align="center" border="1" cellspacing="0" cellpadding="1" style="width: 70%">
				<tr bgcolor="#04B4AE">
    				<th width="20%" style="padding: 5px;">GROUP </th>
    				<%
					for(int j=0;j<alColumnHeaders.size();j++){
					%>
						<th width="20%" style="padding: 5px;"><%=alColumnHeaders.get(j) %></th>
					<%
					}
					%>
    				
	    		</tr>
	    		<%-- <logic:equal value="Edit" name="editFlag"> --%>
				<%-- <%for(int k=0;k<alHaadCategories.size();k++){
					String cache[]	=	(String[])alHaadCategories.get(k); 
					%>
					<tr <%=(k%2)==0? "gridOddRow":"gridEvenRow"%>">	
					<th> <%=cache[1]%> <input type="hidden" name="groupName" value="<%=cache[0]%>"> </th>
					<td> <input type="text" name="factor" class="textBoxSmallest" onkeyup="isNumeric(this);"> 	</td>	
					<td> <input type="text" name="baseRate" class="textBoxSmallest" onkeyup="isNumeric(this);"></td>	
					<td> <input type="text" name="gap" class="textBoxSmallest" onkeyup="isNumeric(this);">		</td>	
					<td> <input type="text" name="margin" class="textBoxSmallest" onkeyup="isNumeric(this);">	</td>
					</tr>
					<%
				}%> --%>
				<%-- </logic:equal> --%>
				<%-- <logic:notEqual value="Edit" name="editFlag"> --%>
				<logic:iterate id="HospitalDetailVO" name="alHaadCategories" indexId="i">
				<tr class="<%=(i.intValue()%2)==0? "gridOddRow":"gridEvenRow"%>">	
	    			<th> <bean:write property="groupName" name="HospitalDetailVO"  /> <html:hidden property="groupName" name="HospitalDetailVO"/> </th>
	    			<%boolean foctFlag=true;String foctClass="disabledBox"; %>
	    			<logic:equal value="Edit" name="editFlag">
					<bean:define id="foctID" name="HospitalDetailVO" property="categoryID"/>
					<%
					 foctFlag=false;
					 foctClass="";
					 genricDrugFlag=true;						
				    genericDrugClass="disabledBox";
					
					if("6".equals(foctID)||"4".equals(foctID)||"3".equals(foctID) ||"7".equals(foctID)){
						foctFlag=true;						
						foctClass="disabledBox";
					}
					
					if("7".equals(foctID)||"3".equals(foctID)){
						genricDrugFlag=false;
						
						genericDrugClass="textBoxSmallest";
					}
					%>
					</logic:equal>
					<%-- <%
					boolean baseRateFlag=false;
					String baseRateClass="";
					if(!("6".equals(foctID))){
						baseRateFlag=true;
						
						foctClass="disabledBox";
					}
					%>
					 --%>
					
					
					<%-- <td> <html:text property="factor" name="HospitalDetailVO" styleClass="<%=styleClass %>" onkeyup="isNumeric(this);" readonly="<%=viewMode %>"/> 	</td> 	 --%>
					
					<td> <html:text property="factor"   styleClass="<%=foctClass %>"  name="HospitalDetailVO"  onkeyup="numZeroValidation(this);" readonly="<%=foctFlag %>"/> 	</td>
					
					  	
				<%-- 	<td> <html:text property="baseRate" styleClass="<%=foctClass %>"  name="HospitalDetailVO"  onkeyup="isNumeric(this);" readonly="<%=baseRateFlag %>"/> 	</td> --%>			
				 <logic:equal value="IR-DRG" property="groupName" name="HospitalDetailVO">
					<td> <html:text property="baseRate" name="HospitalDetailVO" styleClass="<%=styleClass %>" onkeyup="isNumeric(this);" readonly="<%=viewMode %>"/> 	</td>
				</logic:equal>
				<logic:notEqual value="IR-DRG" property="groupName" name="HospitalDetailVO">
					<td> <html:text property="baseRate" name="HospitalDetailVO" styleClass="<%=styleClassDrug %>" onkeyup="isNumeric(this);" readonly="true"/> 	</td>
				</logic:notEqual>
					
				
				<!-- gap -->
				<logic:equal value="IR-DRG" property="groupName" name="HospitalDetailVO">
					<td> <html:text property="gap" name="HospitalDetailVO" styleClass="<%=styleClass %>" onkeyup="isNumeric(this);" readonly="<%=viewMode %>"/> 		</td>
				</logic:equal>
				<logic:notEqual value="IR-DRG" property="groupName" name="HospitalDetailVO">
					<%-- <td> <html:text property="baseRate" name="HospitalDetailVO" styleClass="<%=styleClassDrug %>" onkeyup="isNumeric(this);" readonly="true"/> 	</td> --%>
					<td> <html:text property="gap" name="HospitalDetailVO" styleClass="<%=styleClassDrug %>" onkeyup="isNumeric(this);" readonly="<%=true %>"/> 		</td>
				</logic:notEqual>	
					
					
					<!-- MArgin -->
				<logic:equal value="IR-DRG" property="groupName" name="HospitalDetailVO">
					<td> <html:text property="margin" name="HospitalDetailVO" styleClass="<%=styleClass %>" onkeyup="isNumeric(this);" readonly="<%=viewMode %>"/> 	</td>
				</logic:equal>
				<logic:notEqual value="IR-DRG" property="groupName" name="HospitalDetailVO">
					<td> <html:text property="margin" name="HospitalDetailVO" styleClass="<%=styleClassDrug %>" onkeyup="isNumeric(this);" readonly="<%=true %>"/> 	</td>
				</logic:notEqual>		
					
					
					
					<%-- 	<td> <html:text property="baseRate" name="HospitalDetailVO" styleClass="<%=styleClassDrug %>" onkeyup="isNumeric(this);" readonly="true"/> 	</td> --%>
					<%-- 	<td> <html:text property="gap" name="HospitalDetailVO" styleClass="<%=styleClass %>" onkeyup="isNumeric(this);" readonly="<%=viewMode %>"/> 		</td> --%>	
					<%-- 	<td> <html:text property="margin" name="HospitalDetailVO" styleClass="<%=styleClass %>" onkeyup="isNumeric(this);" readonly="<%=viewMode %>"/> 	</td> --%>
					
					
					<td> <html:text property="discount" name="HospitalDetailVO" styleClass="<%=genericDrugClass%>" onkeyup="isNumeric(this);" readonly="<%=genricDrugFlag %>"/> 	</td>
					
					<%-- <!-- Generic drug -->
					<logic:equal value="Generic drug" property="groupName" name="HospitalDetailVO">
					<td> <html:text property="discount" name="HospitalDetailVO" styleClass="<%=styleClass %>" onkeyup="isNumeric(this);" readonly="<%=viewMode %>"/> 	</td>
					</logic:equal>
					<logic:notEqual value="Generic drug" property="groupName" name="HospitalDetailVO">
					<td> <html:text property="discount" name="HospitalDetailVO" styleClass="<%=styleClassDrug %>" onkeyup="isNumeric(this);" readonly="true"/> 	</td>
					</logic:notEqual> --%>
				
				
				</tr>	
    			</logic:iterate>
				<logic:equal value="Edit" name="editFlag">
				<tr>
					<td align="right" nowrap colspan="2">Tariff Start Date: <span class="mandatorySymbol">*</span></td>
					<td align="left" colspan="3">
							<html:text property="haadTarrifStartDt" styleClass="textBox textDate" maxlength="10" />
							<a name="CalendarObjectempDate11" id="CalendarObjectempDate11" href="#" onClick="javascript:show_calendar('CalendarObjectempDate11','frmAddHaadFactors.haadTarrifStartDt',document.frmAddHaadFactors.haadTarrifStartDt.value,'',event,148,178);return false;" onMouseOver="window.status='Calendar';return true;" onMouseOut="window.status='';return true;">
					<img src="/ttk/images/CalendarIcon.gif" alt="Calendar" name="empDate" width="24" height="17" border="0" align="absmiddle">
							</a>
				</tr>
				<tr>
					<td align="right" nowrap colspan="2">Tariff End Date: <span class="mandatorySymbol"></span></td>
					<td align="left" colspan="3">
							<html:text property="haadTarrifEndDt" styleClass="textBox textDate" maxlength="10" />
							<a name="CalendarObjectempDate11" id="CalendarObjectempDate11" href="#" onClick="javascript:show_calendar('CalendarObjectempDate11','frmAddHaadFactors.haadTarrifEndDt',document.frmAddHaadFactors.haadTarrifEndDt.value,'',event,148,178);return false;" onMouseOver="window.status='Calendar';return true;" onMouseOut="window.status='';return true;">
					<img src="/ttk/images/CalendarIcon.gif" alt="Calendar" name="empDate" width="24" height="17" border="0" align="absmiddle">
							</a>
				</tr>
				</logic:equal>
    			<%-- </logic:notEqual> --%>
				
	    	</table>
	    	
	    	<!-- S T A R T : Buttons -->
  		<table align="center" class="buttonsContainer"  border="0" cellspacing="0" cellpadding="0">
  			<tr>
    			<td width="100%" align="center">
    			<logic:equal value="Edit" name="editFlag">
    			<button type="button" name="Button" accesskey="s" class="buttons" onMouseout="this.className='buttons'" onMouseover="this.className='buttons buttonsHover'" onClick="javascript:onSaveHaadFactors();"><u>S</u>ave</button>&nbsp;
    			&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
    			</logic:equal>
    			<logic:notEqual value="Edit" name="editFlag">
	    			<button type="button" name="Button" accesskey="e" class="buttons" onMouseout="this.className='buttons'" onMouseover="this.className='buttons buttonsHover'" onClick="javascript:onEditValues();"><u>E</u>dit</button>&nbsp;
	    			&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
    			</logic:notEqual>
  				<button type="button" name="Button" accesskey="b" class="buttons" onMouseout="this.className='buttons'" onMouseover="this.className='buttons buttonsHover'" onClick="javascript:onClose();"><u>B</u>ack</button>&nbsp;
 				</td>
  			</tr>  		
		</table>
		
</fieldset>	   

<fieldset>
<legend>History of Tariff Updates</legend>
<div class="scrollableGrid" style="height:290px;">
			<ttk:HistoryOfTariffUpdates/>
</div>
</fieldset>			
		</div>
	
<input type="hidden" name="mode" value="">
<input type="hidden" id="hosp_seq_id" name="hosp_seq_id" value="<%=hosp_seq_id%>">
<input type="hidden" name="providerCode" value="<%= (String)request.getSession().getAttribute("AuthLicenseNo") %>">
<html:hidden property="networkTypeYN" name="frmAddHaadFactors"/>

</html:form>
