<%@ taglib uri="/WEB-INF/tlds/ttk-tags.tld" prefix="ttk" %>
<%
/** @ (#) menuLinks.jsp 18 Dec 2015
 * Project     : 
 * File        : menuLinks.jsp
 * Author      : Nagababu K
 * Company     : RCS Technologies
 * Date Created: 18 Dec 2015
 *
 * Modified by   : 
 * Modified date : 
 * Reason        : 
 * 
 */
 %> 
 <div style="margin-right: -4px;">
<ttk:AccessMenues/>

  <form name="insleftlinks" METHOD="POST" style="display: none;">
  <input type="hidden" name="mode">
  <INPUT TYPE="hidden" NAME="leftlink" VALUE="">
  <INPUT TYPE="hidden" NAME="sublink" VALUE="">
  <INPUT TYPE="hidden" NAME="tab" VALUE="">
  <INPUT TYPE="hidden" NAME="loginType" VALUE="OBR">
  </form>
  </div>