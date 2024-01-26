//on Click of review button
function onReview()
{
    //trimForm(document.forms[1]);
   
	if(!JS_SecondSubmit)
     {
		document.forms[1].mode.value="doReviewInfo";
		document.forms[1].action="/ReviewClaimsAction.do";
		JS_SecondSubmit=true
		document.forms[1].submit();
	}//end of if(!JS_SecondSubmit)

}//end of onReview()

//on Click of promote button
function onPromote()
{
    var message;
	message=confirm('Claim will be moved to the next level and if you do not have privileges, you may not see this Claim.');
	if(message)
	{
		if(!JS_SecondSubmit)
        {
			document.forms[1].mode.value="doReviewInfo";
			document.forms[1].action="/ReviewClaimsAction.do";
			JS_SecondSubmit=true
			document.forms[1].submit();
		}//end of if(!JS_SecondSubmit)
	}//end of if(message)
}//end of onPromote()
