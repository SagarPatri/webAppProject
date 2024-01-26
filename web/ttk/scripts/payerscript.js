#cssmenu ul,
#cssmenu li,
#cssmenu span,
#cssmenu a {
  margin: 0;
  padding: 0;
  position: relative;
}
#cssmenu {
  line-height: 1;
  border-radius: 5px 5px 0 0;
  -moz-border-radius: 5px 5px 0 0;
  -webkit-border-radius: 5px 5px 0 0;
  background: #ffffff;
  background: -moz-linear-gradient(top, #ffffff 0%, #ffffff 100%);
  background: -webkit-gradient(linear, left top, left bottom, color-stop(0%, #ffffff), color-stop(100%, #ffffff));
  background: -webkit-linear-gradient(top, #ffffff 0%, #ffffff 100%);
  background: -o-linear-gradient(top, #ffffff 0%, #ffffff 100%);
  background: -ms-linear-gradient(top, #ffffff 0%, #ffffff 100%);
  background: linear-gradient(to bottom, #ffffff 0%, #ffffff 100%);
  border-bottom: 2px solid #db000b;
  width: auto;
}
#cssmenu:after,
#cssmenu ul:after {
  content: '';
  display: block;
  clear: both;
}
#cssmenu a {
  background: #ffffff;
  background: -moz-linear-gradient(top, #ffffff 0%, #ffffff 100%);
  background: -webkit-gradient(linear, left top, left bottom, color-stop(0%, #ffffff), color-stop(100%, #ffffff));
  background: -webkit-linear-gradient(top, #ffffff 0%, #ffffff 100%);
  background: -o-linear-gradient(top, #ffffff 0%, #ffffff 100%);
  background: -ms-linear-gradient(top, #ffffff 0%, #ffffff 100%);
  background: linear-gradient(to bottom, #ffffff 0%, #ffffff 100%);
  color: #ffffff;
  display: block;
  font-family: Helvetica, Arial, Verdana, sans-serif;
  padding: 19px 20px;
  text-decoration: none;
}
#cssmenu ul {
  list-style: none;
}
#cssmenu > ul > li {
  display: inline-block;
  float: left;
  margin: 0;
}
#cssmenu.align-center {
  text-align: center;
}
#cssmenu.align-center > ul > li {
  float: none;
}
#cssmenu.align-center ul ul {
  text-align: left;
}
#cssmenu.align-right > ul {
  float: right;
}
#cssmenu.align-right ul ul {
  text-align: right;
}
#cssmenu > ul > li > a {
  color: #000000;
  font-size: 12px;
}
#cssmenu > ul > li:hover:after {
  content: '';
  display: block;
  width: 0;
  height: 0;
  position: absolute;
  left: 50%;
  bottom: 0;
  border-left: 10px solid transparent;
  border-right: 10px solid transparent;
  border-bottom: 10px solid #db000b;
  margin-left: -10px;
}
#cssmenu > ul > li:first-child > a {
  border-radius: 5px 0 0 0;
  -moz-border-radius: 5px 0 0 0;
  -webkit-border-radius: 5px 0 0 0;
}
#cssmenu.align-right > ul > li:first-child > a,
#cssmenu.align-center > ul > li:first-child > a {
  border-radius: 0;
  -moz-border-radius: 0;
  -webkit-border-radius: 0;
}
#cssmenu.align-right > ul > li:last-child > a {
  border-radius: 0 5px 0 0;
  -moz-border-radius: 0 5px 0 0;
  -webkit-border-radius: 0 5px 0 0;
}
#cssmenu > ul > li.active > a,
#cssmenu > ul > li:hover > a {
  color: #000000;
  box-shadow: inset 0 0 3px #d9d9d9;
  -moz-box-shadow: inset 0 0 3px #d9d9d9;
  -webkit-box-shadow: inset 0 0 3px #d9d9d9;
  background: #f2f2f2;
  background: -moz-linear-gradient(top, #ffffff 0%, #f2f2f2 100%);
  background: -webkit-gradient(linear, left top, left bottom, color-stop(0%, #ffffff), color-stop(100%, #f2f2f2));
  background: -webkit-linear-gradient(top, #ffffff 0%, #f2f2f2 100%);
  background: -o-linear-gradient(top, #ffffff 0%, #f2f2f2 100%);
  background: -ms-linear-gradient(top, #ffffff 0%, #f2f2f2 100%);
  background: linear-gradient(to bottom, #ffffff 0%, #f2f2f2 100%);
}
#cssmenu .has-sub {
  z-index: 1;
}
#cssmenu .has-sub:hover > ul {
  display: block;
}
#cssmenu .has-sub ul {
  display: none;
  position: absolute;
  width: 200px;
  top: 100%;
  left: 0;
}
#cssmenu.align-right .has-sub ul {
  left: auto;
  right: 0;
}
#cssmenu .has-sub ul li {
  *margin-bottom: -1px;
}
#cssmenu .has-sub ul li a {
  background: #db000b;
  border-bottom: 1px dotted #ff0f1b;
  font-size: 11px;
  filter: none;
  display: block;
  line-height: 120%;
  padding: 10px;
  color: #ffffff;
}
#cssmenu .has-sub ul li:hover a {
  background: #a80008;
}
#cssmenu ul ul li:hover > a {
  color: #ffffff;
}
#cssmenu .has-sub .has-sub:hover > ul {
  display: block;
}
#cssmenu .has-sub .has-sub ul {
  display: none;
  position: absolute;
  left: 100%;
  top: 0;
}
#cssmenu.align-right .has-sub .has-sub ul,
#cssmenu.align-right ul ul ul {
  left: auto;
  right: 100%;
}
#cssmenu .has-sub .has-sub ul li a {
  background: #a80008;
  border-bottom: 1px dotted #ff0f1b;
}
#cssmenu .has-sub .has-sub ul li a:hover {
  background: #8f0007;
}
#cssmenu ul ul li.last > a,
#cssmenu ul ul li:last-child > a,
#cssmenu ul ul ul li.last > a,
#cssmenu ul ul ul li:last-child > a,
#cssmenu .has-sub ul li:last-child > a,
#cssmenu .has-sub ul li.last > a {
  border-bottom: 0;
}
