<%@ page language="java" contentType="text/html; utf-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>Zero Footprint Components Demo</title>
<link href="css/style.css" rel="stylesheet" type="text/css" />
<link href="css/top_menu.css" rel="stylesheet" type="text/css" />
<link href="css/jquery-ui-1.9.0.css" rel="stylesheet" type="text/css" />

<script type="text/javascript" language="javascript" src="js/base64.js"></script>
<script type="text/javascript" language="javascript" src="js/jquery-1.8.2.js"></script>
<script type="text/javascript" language="javascript" src="js/errors.js"></script>
<script type="text/javascript" language="javascript" src="js/zfcomponent.js"></script>
<script type="text/javascript" language="javascript" src="js/jquery-ui-1.9.0.js"></script>

<script type="text/javascript">
function doReadPublicData() {
    var Ret = Initialize();
    if(Ret == false)
        return;
    
    Ret = ReadPublicData(true, true, true, true, true, true);
    if(Ret == false)
        return;
    
    $("#ef_idn_cn").val(GetEF_IDN_CN());
    $("#ef_non_mod_data").val(GetEF_NonModifiableData());
    $("#ef_mod_data").val(GetEF_ModifiableData());
    $("#ef_sign_image").val(GetEF_HolderSignatureImage());
    $("#ef_photo").val(GetEF_Photography());
    $("#ef_root_cert").val(GetEF_RootCertificate());
    $("#ef_home_address").val(GetEF_HomeAddressData());
    $("#ef_work_address").val(GetEF_WorkAddressData());
    
    $("#btnVerifyPubData").removeAttr("disabled");
    $("#msg p:last").html("Public data read successfully");
    $("#msg").show("fade", {}, 500);
    verifyPublicData();
    return true;
}

/* function verifyPublicData() {
    $("#form1").attr("action", "ParsePublicData");
    $('#form1').submit();
}
 */
function doSignData() {
    var Ret = Initialize();
    if(Ret == false)
        return;
    
    var pin = $("#txtPin").val();
    var data = $("#txtSignData").val();
    
    if(pin == null || pin == "" || pin.length != 4) {
        alert("Empty or invalid PIN size!");
        return;
    }
    
    if(data == null || data == "") {
        alert("Data field is empty!");
        return;
    }
    
    data = window.btoa(data);
    
    Ret = SignData(pin, data);
    if(Ret == "")
        return;
    
    $("#certificate").val(ExportSignCertificate());
    $("#signature").val(Ret);
    $("#btnVerifySignature").removeAttr("disabled");
    
    $("#btnVerifyPubData").removeAttr("disabled");
    $("#msg p:last").html("Data Signed successfully");
    $("#msg").show("fade", {}, 500);
}

function doSignChallenge() {
    var Ret = Initialize();
    if(Ret == false)
        return;
    
    var pin = $("#txtPin").val();
    if(pin == null || pin == "" || pin.length != 4) {
    	alert("Empty or invalid PIN size!");
    	return;
    }
    	
    
    var challenge = "";
    $.ajax({
        url: "GenerateChallenge",
        type: "POST",
        async: false,
        dataType: 'text',
        context: document.body,
        success: function(data){
            challenge = data;
        }
      });
    
    Ret = SignChallenge(pin, challenge);
    if(Ret == "")
        return;
    
    $("#certificate").val(ExportAuthCertificate());
    $("#signature").val(Ret);
    $("#btnVerifySignature").removeAttr("disabled");
    
    $("#msg p:last").html("Server Challenge Signed successfully");
    $("#msg").show("fade", {}, 500);
}

function verifySignature() {
    $("#form1").attr("action", "VerifySignature");
    $('#form1').submit();
}

function onSignChange(rd) {
    if(rd.value == "Auth")
        $("#TR_Data").hide();
    else
        $("#TR_Data").show();
}

function doSign() {
    if($("#rdSign").is(':checked'))
        doSignData();
    else
        doSignChallenge();
}
</script>

</head>

<body>

<div class="container">
  <!-- <div id="header"> -->
  <!-- <a href="index.jsp">
    <img class="logo-uae" width="142" height="121" alt="United Arab Emirates" src="images/logo-uae-new.gif" /><img class="flash-banner" width="683" height="119" alt="" src="images/eida-national-day-en.gif" /><img width="142" height="121" alt="Emirates Identity Authority" src="images/logo-eida-new.gif" />
  </a> -->
  <!-- <div>
  <ul class="menu black">
    <li class="current"><a href="index.jsp" title="">Applet Home</a></li>
    <li><a href="index_activex.jsp" title="">ActiveX Home</a></li>
    <li><a href="#" title="" >About EIDA</a></li>
    <li><a href="#" title="">Contact</a></li>
</ul>
  </div> -->
    <!-- end .header --><!-- </div> -->
  <div class="content">
  <applet id="ZFComponent" alt="This browser does not support Applets."
    code="emiratesid.jio.webcomponents.ZFApplet" archive="ZFApplet.jar" width="0" height="0">
    This browser does not support Applets.
    </applet>
    
    <form action="ParsePublicData" method="post" id="form1">
    <input type="hidden" id="ef_idn_cn" name="ef_idn_cn" value="" />
    <input type="hidden" id="ef_non_mod_data" name="ef_non_mod_data" value="" />
    <input type="hidden" id="ef_mod_data" name="ef_mod_data" value="" />
    <input type="hidden" id="ef_sign_image" name="ef_sign_image" value="" />
    <input type="hidden" id="ef_photo" name="ef_photo" value="" />
    <input type="hidden" id="ef_root_cert" name="ef_root_cert" value="" />
    <input type="hidden" id="ef_home_address" name="ef_home_address" value="" />
    <input type="hidden" id="ef_work_address" name="ef_work_address" value="" />
    
    <input type="hidden" id="certificate" name="certificate" value="" />
    <input type="hidden" id="signature" name="signature" value="" />
    
    <!-- <h3>Emirates identity Authority</h3>
      <p style="margin:5px;">Emirates Identity Authority (EIDA) is an independent federal authority established by virtue of the federal decree No. (2) of 2004. The decree has empowered the Authority with ultimate powers required for the execution of the Population Register and the ID card program.</p>
      <p style="margin:5px;">EIDA was established on the 29th September 2004 in accordance with the constitution and the federal laws and decrees related to the competencies of ministries and the powers of ministers, naturalization, passports and entry and residence of expatriates, the organization of births and deaths, the organization of labor affairs and the amending laws thereof, in addition to decrees related to the General Authority of Information and civil service at the federal government.</p>
    <br/>
    
    <div id="left_column">
        <h3>Public Data Reader</h3>
        <p>This function reads public data raw files (EFs) from ID applet, then these files are sent to server for parsing and signature verification</p>
        <br/>
        <input type="button" class="submitButton" value="Read Public Data" onclick="doReadPublicData();" />
        <input type="button" class="submitButton" value="Verify and Parse" onclick="verifyPublicData();" disabled="disabled" id="btnVerifyPubData" />
    </div>
    <div id="right_column">
        <h3>Signature &amp; Verification</h3>
        <table align="center">
            <tr>
                <td>PIN</td>
                <td><input type="password" id="txtPin" name="PIN" value="" style="width: 200px;"/></td>
            </tr>
            <tr>
                <td>Sign</td>
                <td align="left">
                    <input type="radio" value="Sign" name="signType" id="rdSign" checked="checked" onchange="onSignChange(this);"/>Sign Data
                    <br/>
                    <input type="radio" value="Auth" name="signType" id="rdAuth" onchange="onSignChange(this);" />Sign Challenge
                </td>
            </tr>
            <tr id="TR_Data">
                <td>Data</td>
                <td>
                    <textarea rows="4" cols="5" id="txtSignData" name="SignData" style="width: 200px;"></textarea>
                </td>
            </tr>
            
            <tr>
                <td colspan="2" align="center">
                    <input type="button" value="Sign" onclick="doSign();" class="submitButton" /> &nbsp;
                    <input type="button" value="Verify Signature" onclick="verifySignature();" disabled="disabled" id="btnVerifySignature" class="submitButton" />
                </td>
            </tr> -->
<!--         </table> -->
   <!--  </div> -->
  
    </form>
    <!-- end .content -->
  </div>
    
   <!--  <div class="ui-widget" id="msg" style="display: none; width: 350px;">
       <div class="ui-state-highlight ui-corner-all" style="padding: 0 .7em;"> 
           <span class="ui-icon ui-icon-alert" style="float: left; margin-right: .3em;"></span>
           <p style="font-weight: bold;"></p>
       </div>
   </div>
 -->
  <!-- <div class="footer">
    <p>&nbsp;EIDA &copy; 2012</p> -->
    <!-- end .footer --><!-- </div> -->
  <!-- end .container --></div>
</body>
</html>
