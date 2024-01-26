<%@page import="com.ttk.dto.empanelment.HospitalDetailVO"%>
<%@page import="com.ttk.dto.common.CacheObject"%>
<%@page import="com.ttk.dao.impl.common.CacheDAOImpl"%>
<%@page import="java.util.ArrayList"%>
<%
/** @ (#) ProfessionalDetails.jsp 08th Jan 2015
 * Project     : TTK Healthcare Services
 * File        : ProfessionalDetails.jsp
 * Author      : Kishor kumar
 * Company     : RCS Technologies
 * Date Created: 08th Jan 2015
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
<%@ page import=" com.ttk.common.TTKCommon,com.ttk.common.security.Cache,org.apache.struts.action.DynaActionForm" %>
<SCRIPT LANGUAGE="JavaScript" SRC="/ttk/scripts/validation.js"></SCRIPT>
<script language="javascript" src="/ttk/scripts/calendar/calendar.js"></script>
<script language="javascript" src="/ttk/scripts/usermanagement/usercontact.js"></script>


<head>
	<link rel="stylesheet" type="text/css" href="css/style.css" />
	<link href="http://ajax.googleapis.com/ajax/libs/jqueryui/1.8/themes/base/jquery-ui.css" rel="stylesheet" type="text/css" />
	<script src="http://ajax.googleapis.com/ajax/libs/jquery/1.4/jquery.min.js"></script>

	<script src="jquery-1.11.1.min.js"></script>
	
    <script src="http://ajax.googleapis.com/ajax/libs/jqueryui/1.8/jquery-ui.min.js"></script>
    <script src="http://code.jquery.com/jquery-1.8.2.js"></script>
	<script src="js/jquery.autocomplete.js"></script>
      
<SCRIPT>
  $(document).ready(function() {
    $("#professionalId").autocomplete("auto.jsp?mode=profession");
	});  
  
  $(document).ready(function() {
   // $("#name").autocomplete("/AsynchronousAction.do?mode=getAutoCompleteMethod&getType=professionsName");
	  $("#name").autocomplete("auto.jsp?mode=professionsName");
	/* var availableTags = document.forms[1].alProfessionalsNames.value;
	alert("availableTags::"+availableTags);
    $( "#name" ).autocomplete({
        source: availableTags
      }); */
    
});
  
  function getClinicainId(obj)
  {
	  var hospSeqId	=	document.getElementById("hospSeqId").value;
	  $(document).ready(function() {
	  $("#clinicianID").blur(function(){
	    	var ID	=	obj.value;
	        $.ajax({
	        		url: "/AsynchronousAction.do?mode=getCommonMethod&id="+ID+"&getType=clinicianId&hospSeqId="+hospSeqId, 
	        		success: function(result){
	      				var res	=	result.split("@");
						document.getElementById("clinician").value	=	res[0];
						document.getElementById("speciality").value		=	res[1]; 
						
	        		}}); 
	   		 });
	  });
  }
  
</SCRIPT>

</head>

<%
/* pageContext.setAttribute("ProfessionalsNames",Cache.getCacheObject("ProfessionalsNames"));

ArrayList alProfessionalsNames	=	(ArrayList)pageContext.getAttribute("ProfessionalsNames");

ArrayList alProfessionalsDescs	=	new ArrayList();;
//out.print("alProfessionalsNames::"+alProfessionalsNames);
CacheObject cacheObject	=	new CacheObject();
for(int i=0;i<alProfessionalsNames.size();i++)
{
	cacheObject	=	(CacheObject)alProfessionalsNames.get(i);
	//out.print("getCacheDesc::"+cacheObject.getCacheDesc());
	//out.print("getCacheId::"+cacheObject.getCacheId());
	alProfessionalsDescs.add(i, cacheObject.getCacheDesc());
} */
DynaActionForm frmProfessionalContact=(DynaActionForm)request.getSession().getAttribute("frmProfessionalContact");
    boolean viewmode=true;
    if(TTKCommon.isAuthorized(request,"Edit"))
    {
        viewmode=false;
    }//end of if(TTKCommon.isAuthorized(request,"Edit"))
    pageContext.setAttribute("viewmode",new Boolean(viewmode));

	pageContext.setAttribute("consultTypeCode", Cache.getCacheObject("consultTypeCode"));
	pageContext.setAttribute("nationalityTypeCode", Cache.getCacheObject("nationalityTypeCode"));
	pageContext.setAttribute("gender", Cache.getCacheObject("gender"));
	pageContext.setAttribute("specialityTypeCode", Cache.getCacheObject("specialityType"));
	pageContext.setAttribute("regAuthority",Cache.getCacheObject("regAuthority"));
	
%>
<html:form action="/EditProfessionContact.do"  method="post" enctype="multipart/form-data">
	<!-- S T A R T : Page Title -->
	<table align="center" class="pageTitle" border="0" cellspacing="0" cellpadding="0">
		<tr>
    		<td><bean:write name="frmProfessionalContact" property="caption" /></td>
			<td width="43%" align="right" class="webBoard">&nbsp;</td>
  		</tr>
	</table>
	<!-- E N D : Page Title -->
	<div class="contentArea" id="contentArea">
	<html:errors/>
	<!-- E N D : Page Title --> 
	
	
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
	
	<logic:notEmpty name="notify" scope="request">
		<table align="center" class="errorContainer" style="display:" border="0" cellspacing="0" cellpadding="0">
	      <tr>
	        <td><img src="/ttk/images/ErrorIcon.gif" alt="Alert" title="Alert" width="16" height="16" align="absmiddle">&nbsp;
	          <bean:write name="notify" scope="request"/>
	        </td>
	      </tr>
   	 </table>
   	 </logic:notEmpty>
	
	<br>
	<fieldset>
		<legend>Personal Information</legend>
		<table align="center" class="formContainer"  border="0" cellspacing="0" cellpadding="0">
			
			<tr>
				<logic:equal name="regType" value="DHA">
	        		<td class="formLabel">Professional ID.: <span class="mandatorySymbol">*</span></td>
	        		<td>
	        			<html:text property="personalInfoVO.professionalId" styleId="professionalId" styleClass="textBox textBoxMedium" maxlength="30" onblur="getProfessionalDetails(this)"/>
	        		</td>
	        		<td class="formLabel">Name: <span class="mandatorySymbol">*</span></td>
	        		<td>
	        			<html:text property="personalInfoVO.name"  styleId="name" styleClass="textBox textBoxLarge" maxlength="250" onblur="getProfessionalsName(this)"/>
	        		</td>
		        </logic:equal>
	        
		        <logic:notEqual name="regType" value="DHA">
	        		<td class="formLabel">Professional ID.: <span class="mandatorySymbol">*</span></td>
	        		<td>
	        			<html:text property="personalInfoVO.professionalId" styleClass="textBox textBoxMedium" maxlength="30"/>
	        		</td>
	        		<td class="formLabel">Name: <span class="mandatorySymbol">*</span></td>
	        		<td>
	        			<html:text property="personalInfoVO.name" styleClass="textBox textBoxLarge" maxlength="250"/>
	        		</td>
		        </logic:notEqual>
	       </tr>
	       
	       <tr>
	        		<td class="formLabel">Gender: </td>
	        		<td>
	        			<html:select property="personalInfoVO.gender" styleClass="selectBox" disabled="<%=(viewmode)%>">
							<html:option value="">Select from list</html:option>
							<html:options collection="gender" property="cacheId" labelProperty="cacheDesc" />
						</html:select>
	        		</td>
	        		<td class="formLabel">Age: </td>
	        		<td>
	        			<html:text property="personalInfoVO.age" size="3" styleClass="textBox textBoxMedium" maxlength="3" />
	        		</td>
	       </tr>
	       
	       
	       <tr>
	        		<td class="formLabel">Authority Standard:</td>
	        		<td>
	        			<%-- <html:text property="personalInfoVO.professionalAuthority" styleId="professionalAuthority" styleClass="textBox textBoxMedium" maxlength="30" /> --%>
	        			<logic:equal name="regType" value="DHA">
	        				<html:select property ="personalInfoVO.professionalAuthority" styleClass="selectBox selectBoxSmall" styleId="professionalAuthority">
                 				<html:option value="DHA">DHA</html:option>
          					</html:select>
	        			</logic:equal>
	        			
	        			<logic:notEqual name="regType" value="DHA">
		        			<html:select property ="personalInfoVO.professionalAuthority" styleClass="selectBox selectBoxSmall" styleId="professionalAuthority">
	                 			<html:options collection="regAuthority" property="cacheId" labelProperty="cacheDesc"/>
	          				</html:select>
	          			</logic:notEqual>
	          				
	        		</td>
	        		<td class="formLabel">Consultation Type: <span class="mandatorySymbol">*</span></td>
	        		<td>
	        			<html:select property ="personalInfoVO.consultTypeCode" styleClass="selectBox selectBoxMedium">
                 			<html:option value="">Select from list</html:option>
                 			<html:options collection="consultTypeCode" property="cacheId" labelProperty="cacheDesc"/>
          				</html:select>
          				
	        		</td>
	       </tr>
	       <tr>
	        		<td class="formLabel">Start Date:</td>
	        		<td>
	        		<html:text property="startDate" styleClass="textBox textDate" maxlength="10"/>
	        	
	        	
	        	<a name="CalendarObjectempDate11" id="CalendarObjectempDate11" href="#" onClick="javascript:show_calendar('CalendarObjectempDate11','frmProfessionalContact.startDate',document.frmProfessionalContact.startDate.value,'',event,148,178);return false;" onMouseOver="window.status='Calendar';return true;" onMouseOut="window.status='';return true;">
	    						<img src="/ttk/images/CalendarIcon.gif" alt="Calendar" name="empDate" width="24" height="17" border="0" align="absmiddle">
	    					</a>
	    		
	    				
	        		</td>
	        		<td class="formLabel">End Date:</td>
	        		<td>
	        		<html:text property="endDate" styleClass="textBox textDate" maxlength="10"/>
	        		
	        		
	        			<a name="CalendarObjectToDate" id="CalendarObjectToDate" href="#" onClick="javascript:show_calendar('CalendarObjectToDate','frmProfessionalContact.endDate',document.frmProfessionalContact.endDate.value,'',event,148,178);return false;" onMouseOver="window.status='Calendar';return true;" onMouseOut="window.status='';return true;">
        						<img src="/ttk/images/CalendarIcon.gif" alt="Calendar" name="toDate" width="24" height="17" border="0" align="absmiddle">
        			</a>
	        		</td>
	       </tr>
	       <tr>
	        		<td class="formLabel">Primary Email ID:</td>
	        		<td>
        				<html:text property="personalInfoVO.primaryEmailID" styleClass="textBox textBoxLarge"/>
        			</td>
        			<td class="formLabel">Office Phone 1: <!-- <span class="mandatorySymbol">*</span> --></td>
	        		<td>

						<html:text property="personalInfoVO.isdCode" styleId="isdCode" styleClass="disabledfieldType" size="3" maxlength="3" onclick="changeMe(this)" onblur="checkMe('ISD')" disabled="true"/>&nbsp;
	        			<html:text property="personalInfoVO.stdCode" styleId="stdCode" styleClass="disabledfieldType" size="4" maxlength="4" onclick="changeMe(this)" onblur="checkMe('STD')" disabled="true"/>&nbsp;
	        			
	        		<logic:empty name="frmProfessionalContact" property="personalInfoVO.phoneNbr1">
	        			<html:text property="personalInfoVO.phoneNbr1" styleId="phoneNbr1" styleClass="disabledfieldType"  maxlength="15" value="Phone No1" onclick="changeMe(this)" onblur="checkMe('Phone No1')" /> 
	        		</logic:empty>
	        			
	        		<logic:notEmpty name="frmProfessionalContact" property="personalInfoVO.phoneNbr1">
	        			<html:text property="personalInfoVO.phoneNbr1" styleId="phoneNbr1" styleClass="disabledfieldType"  maxlength="15" onclick="changeMe(this)" onblur="checkMe('Phone No1')" /> 
	        		</logic:notEmpty>	
					

	        		</td>
	       </tr>
	       <tr>
	        		<td class="formLabel">Office Phone 2:</td>
	        		<td>
	        		
	        		<html:text property="personalInfoVO.isdCode" styleId="isdCode" styleClass="disabledfieldType" size="3" maxlength="3" onclick="changeMe(this)" onblur="checkMe('ISD')" disabled="true"/>&nbsp;
	        		<html:text property="personalInfoVO.stdCode" styleId="stdCode" styleClass="disabledfieldType" size="4" maxlength="4" onclick="changeMe(this)" onblur="checkMe('STD')" disabled="true"/>&nbsp;
	        		
	        		<logic:empty name="frmProfessionalContact" property="personalInfoVO.phoneNbr2">
	        			<html:text property="personalInfoVO.phoneNbr2" styleId="phoneNbr2" styleClass="disabledfieldType"  maxlength="15" value="Phone No2" onclick="changeMe(this)" onblur="checkMe('Phone No2')" /> 
	        		</logic:empty>
	        			
	        		<logic:notEmpty name="frmProfessionalContact" property="personalInfoVO.phoneNbr2">
	        			<html:text property="personalInfoVO.phoneNbr2" styleId="phoneNbr2" styleClass="disabledfieldType"  maxlength="15" onclick="changeMe(this)" onblur="checkMe('Phone No2')" /> 
	        		</logic:notEmpty>
	        		
	        		
        				<!-- html:text property="personalInfoVO.phoneNbr2" styleClass="textBox textBoxLarge"/-->
        			</td>
        			<td class="formLabel">Mobile:</td>
	        		<td>
	        			<html:text property="personalInfoVO.isdCode" styleId="isdCode" styleClass="disabledfieldType" size="3" maxlength="3" onclick="changeMe(this)" onblur="checkMe('ISD')"/>&nbsp;&nbsp;
	        			<html:text property="personalInfoVO.mobileNbr" styleClass="textBox textBoxMedium" maxlength="30" />
	        		</td>
	       </tr>
	       <tr>
	        		<td class="formLabel">Fax:</td>
	        		<td>
        				<html:text property="personalInfoVO.faxNbr" styleClass="textBox textBoxLarge"/>
        			</td>
        			<td class="formLabel">Nationality:</td>
	        		<td>
        				<html:select property ="personalInfoVO.nationalityTypeCode" styleClass="selectBox selectBoxMedium">
                 			<html:option value="">Select from list</html:option>
                 			<html:options collection="nationalityTypeCode" property="cacheId" labelProperty="cacheDesc"/>
          				</html:select>
        			</td>
	       </tr>
	       
	       <tr>
	       <td class="formLabel">Speciality: <span class="mandatorySymbol">*</span></td>
        		<td>
       				<html:select property ="personalInfoVO.speciality" styleClass="selectBox selectBoxMedium">
                			<html:option value="">Select from list</html:option>
                			<html:options collection="specialityTypeCode" property="cacheId" labelProperty="cacheDesc"/>
         				</html:select>
   			</td>
   			<td class="formLabel">Status:</td>
       		<td>
      				<html:select property ="personalInfoVO.activeYN" styleClass="selectBox selectBoxMedium">
               			<html:option value="Y">Active</html:option>
               			<html:option value="N">In-Active</html:option>
        				</html:select>
   			</td>
	       </tr>
	       
	       <tr>
          		
          		<td align="left">Upload License : </td>
			<td>
				<html:file property="file" styleId="file" />
			</td>
			<td>
        <a href="#" onClick="javascript:showProfessionDocs('<bean:write name="frmProfessionalContact" property="personalInfoVO.fileName"/>');"><bean:write name="frmProfessionalContact" property="personalInfoVO.fileName"/> </a>
	        		<!-- 	<html:text property="personalInfoVO.fileName" styleClass="textBox textBoxMedium" maxlength="30" /> -->
			</td>
			</tr>
			
			<!-- <tr>
			<td colspan="2">
			<font color=blue><b>Note :</b>  Please Upload DOC,XLS OR PDF files.</font> 
			</td>
     	</tr> -->
			
		</table>
		
		
		<table align="center" class="buttonsContainer"  border="0" cellspacing="0" cellpadding="0">
  			<tr>
    			<td width="100%" align="center">
						<button type="button" name="Button" accesskey="s" class="buttons" onMouseout="this.className='buttons'" onMouseover="this.className='buttons buttonsHover'" onClick="javascript:onSaveProfessions()"><u>S</u>ave</button>
						&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
						<button type="button" name="Button" accesskey="c" class="buttons" onMouseout="this.className='buttons'" onMouseover="this.className='buttons buttonsHover'" onClick="javascript:onCloseProfessions()"><u>C</u>lose</button>	
 				</td>
  			</tr>  		
		</table>

	</fieldset>
	
		

	
	<html:hidden property="showLinkYN" value="Y" />
	<html:hidden property="caption"/>
	<INPUT TYPE="hidden" NAME="rownum" VALUE="">
	<INPUT TYPE="hidden" NAME="mode" VALUE="">
	<INPUT TYPE="hidden" NAME="sortId" VALUE="">
	<INPUT TYPE="hidden" NAME="pageId" VALUE="">
	
	<script language="javascript">
	//onLoadLinks();
	</script>
</html:form>