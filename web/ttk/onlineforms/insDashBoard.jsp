<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<style>

.image {
      position: absolute;
    left: 144px;
    width: 400px;
    top: 186px;
    
}
.image .text {
    position:absolute;
    top:40px;
    left:20px;
    width:300px;
}
.image1 {
      position: absolute;
    left: 298px;
    width: 400px;
    top: 185px;
}
.image1 .text1 {
    position:absolute;
    top:40px;
    left:20px;
    width:300px;
}
.image3 {
    position: absolute;
    left: 67px;
    width: 400px;
    top: 320px;
}
.image3 .text3 {
    position:absolute;
    top:40px;
    left:20px;
    width:300px;
}
.image4 {
    position: absolute;
    left: 221px;
    width: 400px;
    top: 319px;
}
.image4 .text4 {
    position:absolute;
    top:40px;
    left:20px;
    width:300px;
}

.image5 {
    position: absolute;
    left: 375px;
    width: 400px;
    top: 319px;
}
.image5 .text5 {
    position:absolute;
    top:40px;
    left:20px;
    width:300px;
}
.image6 {
    position: absolute;
    left: 145px;
    width: 400px;
    top: 453px;
}
.image6 .text6 {
    position:absolute;
    top:40px;
    left:20px;
    width:300px;
}
.image7 {
    position: absolute;
    left: 299px;
    width: 400px;
    top: 452px;
}
.image7 .text7 {
    position:absolute;
    top:40px;
    left:20px;
    width:300px;
}
</style>
<script>
function bigImg(x) {
    x.style.width = "180px";
}

function normalImg(x) {
    x.style.width = "150px";
}
</script>
</head>
<body>
<div id="sidebackground"> 
<table>
<tr>
<td>
<div class="image">
  <img alt="" src="/ttk/images/big_hex.png" style="width: 150px" >
  <div class="text">
    <p>This is some demo</p>
    <p>This is some more</p>
  </div>
</div>
</td>
<td>
<div class="image1">
  <img alt="" src="/ttk/images/big_hex.png" style="width: 150px	"/>
  <div class="text1">
    <p>This is some demo2</p>
    <p>This is some more</p>
  </div>
</div>
</td>
</tr>

<tr>
<td>
<div class="image3">
  <img alt="" src="/ttk/images/big_hex.png" style="width: 150px	"/>
  <div class="text3">
    <p>This is some demo3</p>
    <p>This is some more</p>
  </div>
</div>
</td>
<td>
<!--onmouseover="bigImg(this)" onmouseout="normalImg(this)"/-->
<div class="image4">
  <img alt="" src="/ttk/images/big_hex.png" style="width: 150px"/>
  <div class="text4">
    <p>This is some demo4</p>
    <p>This is some more</p>
  </div>
</div>
</td>
<td>
<div class="image5">
  <img alt="" src="/ttk/images/big_hex.png" style="width: 150px	"/>
  <div class="text5">
    <p>This is some demo5</p>
    <p>This is some more</p>
  </div>
</div>
</td>
</tr>
<tr>
<td>
<div class="image6">
  <img alt="" src="/ttk/images/big_hex.png" style="width: 150px	"/>
  <div class="text6">
    <p>This is some demo6</p>
    <p>This is some more</p>
  </div>
</div>
</td>

<td>
<div class="image7">
  <img alt="" src="/ttk/images/big_hex.png" style="width: 150px	"/>
  <div class="text7">
    <p>This is some demo7</p>
    <p>This is some more</p>
  </div>
</div>
</td>

</tr>
</table>

</div>

</body>
</html>
