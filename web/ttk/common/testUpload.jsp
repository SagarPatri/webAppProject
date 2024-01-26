

<br><br><br><br><br><br><br>
<div style="margin-left: 350px;margin-top: 50%;">

<form action="/PreAuthGeneralAction.do" method="POST" enctype="multipart/form-data">
<table>
 <tr>
  <td>File:</td><td> <input type="file" name="testUploadFile"></td>
 </tr>
 <tr>
  <td>File ID:</td><td><input type="text" name="fileID"></td>
 </tr>
 <tr>
  <td>File Type:</td><td><select name="uploadType"><option value="PAT">PAT</option><option value="CLM">CLM</option></select></td>
 </tr>
 <tr>
  <td></td><td> <input type="submit" value="upload"></td>
 </tr>
</table>
<br>
 

 <br><br>
 <textarea rows="5" cols="80" >
 <%=request.getAttribute("errorDetails") %>
 </textarea>
 <input type="hidden" name="mode" value="testUpload">
</form>
<form action="/PreAuthGeneralAction.do" method="POST" enctype="multipart/form-data">

 <input type="hidden" name="mode" value="testUpload">
  <input type="hidden" name="page" value="r">
  <input type="submit" value="Refresh">
</form>
</div>