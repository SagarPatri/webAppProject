function onCustomisedItem(listname)
{
  document.forms[1].mode.value="doCustomisedItem";
  document.forms[1].caption.value=listname;
  document.forms[1].action="/CardBatchAction.do";
  document.forms[1].submit();
}//end of onCustomisedItem(listname)







