<!DOCTYPE HTML>
<%@ taglib uri="/WEB-INF/tlds/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/tlds/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/tlds/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/tlds/ttk-tags.tld" prefix="ttk"%>
<%@ page import="com.ttk.common.TTKCommon,com.ttk.common.security.Cache,java.util.ArrayList"%>
<script language="JavaScript" src="/ttk/scripts/validation.js"></script>
<script language="javascript" src="/ttk/scripts/onlineforms/insuranceLogin/doctorDailyreport.js"></script>
<script language="javascript" src="/ttk/scripts/calendar/calendar.js"></script>
<head>
    <link href="/ttk/scripts/bootstrap/css/bootstrap.min.css" rel="stylesheet">
    <link href="/ttk/scripts/bootstrap/css/bootstrap-responsive.min.css" rel="stylesheet">
    <link href="/ttk/scripts/bootstrap/css/custom.css" rel="stylesheet" type="text/css" />
</head>
<%
	//pageContext.setAttribute("allPolicyListInd", Cache.getCacheObject("alPolicyListInd"));
%>
<html:form action="/InsuranceLoginReportAction.do" >
<body id="pageBody">
<div class="contentArea" id="contentArea">
<div style="background-image:url('/ttk/images/Insurance/content.png');background-repeat: repeat-x; ">

<div class="container"  style="background:#fff;">

    <div class="divPanel page-content">
        <!--Edit Main Content Area here-->
        <div class="row-fluid" >

                <div class="span12">
                   			<div id="contentOuterSeparator"></div>
								<h5 class="sub_heading"><bean:write name="frmInsReports" property="reportName"/> </h5>
			                <div id="contentOuterSeparator"></div>
                </div>
                <html:errors/>
           <div class="span12">
           
               <logic:notMatch name="frmInsReports" property="reportName" value="TAT Enrollment INDIVIDUAL">
               <div class="span4"> 
                    <label>Corporate Name:</label>
                <html:select property ="corpName" styleClass="selectBox selectBoxMedium" onchange="javascript:onChangeCorp(this)">		
				<html:option value="">All</html:option>
      			<html:optionsCollection  property="alCorpList" value="cacheId" label="cacheDesc"/>
				</html:select>
                </div>
                <div class="span4"> 
                    <label>Policy Number:</label>
                 <html:select property ="policyNo" styleClass="selectBox selectBoxMedium" onchange="javascript:onChangePolicy(this)">		
				<html:option value="">--All--</html:option>
				<html:optionsCollection  property="alPolicyList" value="cacheId" label="cacheDesc"/>
				</html:select>
                </div>
                <div class="span4"> 
                    <label>Policy Period:</label>
                 <html:select property ="policyPeriod" styleClass="selectBox selectBoxMedium">		
				<html:option value="">--All--</html:option>
				<html:optionsCollection  property="alPolicyPeriod" value="cacheId" label="cacheDesc"/>
				</html:select>
                </div>
                </logic:notMatch>
                 <logic:match name="frmInsReports" property="reportName" value="TAT Enrollment INDIVIDUAL">
                <div class="span4"> 
                  <label>Policy Number:</label>
                 <html:select property ="policyNo" styleClass="selectBox selectBoxMedium" onchange="javascript:onChangePolicy(this)">		
				<html:option value="">--All--</html:option>
				<html:optionsCollection  name="alINDPolicyList" value="cacheId" label="cacheDesc"/>
				</html:select>
                </div>
                <div class="span4"> 
                    <label>Policy Period:</label>
                 <html:select property ="policyPeriod" styleClass="selectBox selectBoxMedium">		
				<html:option value="">--All--</html:option>
				<html:optionsCollection  property="alPolicyPeriod" value="cacheId" label="cacheDesc"/>
				</html:select>
                </div>
                </logic:match>
                
                </div>
                <br>
			 <div class="span12">
			 <logic:equal name="frmInsReports" property="reportName" value="Technical Result Report">
			 <div class="span4"> 
                    <label>IBNR Days:</label>
                    
                     <%-- <logic:notMatch name="frmInsReports" property="reportName" value="Technical Result Report">
                    <label>Member Id:</label>
                    </logic:notMatch>--%>
                	<html:text property="memberId"></html:text> 
                </div>
             </logic:equal>
				<div class="span4"> 
                   	<label>From Date : <span class="mandatorySymbol">*</span></label>
                   	<html:text property="fromDate" styleClass="textBox textBoxMedium" />
					<a name="CalendarObjectempDate11" id="CalendarObjectempDate11" href="#" onClick="javascript:show_calendar('CalendarObjectempDate11','frmInsReports.fromDate',document.frmInsReports.fromDate.value,'',event,148,178);return false;" onMouseOver="window.status='Calendar';return true;" onMouseOut="window.status='';return true;">
						<img src="/ttk/images/CalendarIcon.gif" alt="Calendar" name="empDate" width="24" height="17" border="0" align="absmiddle">
					</a>
				</div> 
			
				<div class="span4"> 
                   	<label>To Date : <span class="mandatorySymbol">*</span></label>
                   	<html:text property="toDate" styleClass="textBox textBoxMedium" />
					<a name="CalendarObjectempDate11" id="CalendarObjectempDate11" href="#" onClick="javascript:show_calendar('CalendarObjectempDate11','frmInsReports.toDate',document.frmInsReports.toDate.value,'',event,148,178);return false;" onMouseOver="window.status='Calendar';return true;" onMouseOut="window.status='';return true;">
						<img src="/ttk/images/CalendarIcon.gif" alt="Calendar" name="toDate" width="24" height="17" border="0" align="absmiddle">
					</a>
				</div> 
				
				
			</div>	
            </div>
			<!--End Main Content Area here-->



  	<div class="row-fluid" align="center">
<p>&nbsp;</p>


<div style="" align="center">
<p style="margin-bottom:0px;margin-left:12%"><img class="hexagon_small" src="ttk/images/Insurance/small_hexagon_1.png" alt="" align="middle"> &nbsp;&nbsp;&nbsp;
<a href="#" onclick="javascript:onGenerateInsReports()" accesskey="g"><u>G</u>enerate Report</a> </p>
<p style="margin-bottom:0px;margin-left:12%"><img class="hexagon_small" src="/ttk/images/Insurance/small_hexagon_1.png" alt="" align="middle"> &nbsp;&nbsp;&nbsp; 
<a href="#" onclick="javascript:onBack()" accesskey="b"> <u>B</u>ack </a></p>
<br>
</div>


                 </div>
    </div>

</div>
</div>
</div>
<script src="/ttk/scripts/bootstrap/css/jquery.min.js" type="text/javascript"></script> 
<script src="/ttk/scripts/bootstrap/js/bootstrap.min.js" type="text/javascript"></script>
<input type="hidden" name="mode" value="">
<html:hidden property="reportName" name="frmInsReports"/>
<html:hidden property="xmlId"  name="frmInsReports"/>
<html:hidden property="jrxmlName" name="frmInsReports"/>

</body>
</html:form>
