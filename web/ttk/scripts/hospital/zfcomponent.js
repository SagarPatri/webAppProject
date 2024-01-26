var ZFComponentName = "ZFComponent";
var ZFComponent = null;
/*alert("calling method");*/
function Initialize() 
{
	try
	{
		ZFComponent = document.getElementById(ZFComponentName);
		
		var Ret = ZFComponent.Initialize();

		if(Ret.startsWith("-") || Ret != "")
		{
			ProcessError(Ret);
			return false;
		}
		
		return true;
	}
	catch(e)
	{
		alert("Webcomponent Initialization Failed, Details: "+e);
		return false;
	}
}

function ReadPublicData(readPhotography, readNonModifiableData,
		readModifiableData, readSingatureImage, readAddress, readRootCertificate)
{
	try {
		if(ZFComponent == null)
		{
			alert("The Webcomponent is not initialized.");
			return false;
		}
		
		var Ret = ZFComponent.ReadPublicData(readPhotography, readNonModifiableData,
				readModifiableData, readSingatureImage, readAddress, readRootCertificate);

		if(Ret.startsWith("-") || Ret != "")
		{
			ProcessError(Ret);
			return false;
		}
		
		return true;
	} catch(e) {
		alert("Read public data failed, Details: " + e);
	}
	
}

function SignData(PIN, Data) 
{
	try
	{
		if(ZFComponent == null)
		{
			alert("The Webcomponent is not initialized.");
			return "";
		}
		
		var Ret = ZFComponent.SignData(PIN, Data);
		if(Ret.startsWith("-"))
		{
			ProcessError(Ret);
			return "";
		}
		
		return Ret;
	}
	catch(e)
	{
		alert("Signing Data without PIN caching (Sign KeyPair) Failed, Details: " + e);
		return "";
	}
}


function SignChallenge(PIN, Data) 
{
	try
	{
		if(ZFComponent == null)
		{
			alert("The Webcomponent is not initialized.");
			return "";
		}
		
		var Ret = ZFComponent.SignChallenge(PIN, Data);
		if(Ret.startsWith("-"))
		{
			ProcessError(Ret);
			return "";
		}
		
		return Ret;
	}
	catch(e)
	{
		alert( "Signing Data without PIN caching (Auth KeyPair) Failed, Details: "+e);
		return "";
	}
}


function ExportSignCertificate() 
{

	try
	{
		if(ZFComponent == null)
		{
			alert("The Webcomponent is not initialized.");
			return "";
		}
		
		var Ret = ZFComponent.GetSignCertificate();
		if(Ret.startsWith("-"))
		{
			ProcessError(Ret);
			return "";
		}
		
		return Ret;
	}
	catch(e)
	{
		alert( "Export Signing Certificate Failed, Details: "+e);
		return "";
	}
}

function ExportAuthCertificate() 
{
	try
	{
		if(ZFComponent == null)
		{
			alert("The Webcomponent is not initialized.");
			return "";
		}
		
		var Ret = ZFComponent.GetAuthCertificate();
		if(Ret.startsWith("-"))
		{
			ProcessError(Ret);
			return "";
		}
		
		return Ret;
	}
	catch(e)
	{
		alert( "Export Auth Certificate Failed, Details: "+e);
		return "";
	}
}

function GetEF_IDN_CN(){return ZFComponent.GetEF_IDN_CN();}

function GetEF_HolderSignatureImage(){return ZFComponent.GetEF_HolderSignatureImage();}

function GetEF_Photography(){return ZFComponent.GetEF_Photography();}

function GetEF_ModifiableData(){return ZFComponent.GetEF_ModifiableData();}

function GetEF_NonModifiableData(){return ZFComponent.GetEF_NonModifiableData();}

function GetEF_RootCertificate(){return ZFComponent.GetEF_RootCertificate();}

function GetEF_HomeAddressData(){return ZFComponent.GetEF_HomeAddressData();}

function GetEF_WorkAddressData(){return ZFComponent.GetEF_WorkAddressData();}

function GetCardVersion(){return ZFComponent.GetCardVersion();}

function GetCardSerialNumber(){return ZFComponent.GetCardSerialNumber();}
function GetChipSerialNumber(){return ZFComponent.GetChipSerialNumber();}
function GetIssuerSerialNumber(){return ZFComponent.GetIssuerSerialNumber();}
function GetIssuerReferenceNumber(){return ZFComponent.GetIssuerReferenceNumber();}
function GetCPLC0101(){return ZFComponent.GetCPLC0101();}
function GetCPLC9F7F(){return ZFComponent.GetCPLC9F7F();}
