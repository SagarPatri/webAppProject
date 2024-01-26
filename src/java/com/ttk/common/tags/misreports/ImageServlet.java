/** @ (#) ImageServlet.java May 28, 2007
 * Project     : TTK Healthcare Services
 * File        : ImageServlet.java
 * Author      : Ajay Kumar
 * Company     : WebEdge Technologies Pvt.Ltd.
 * Date Created: May 28, 2007
 *
 * @author 		 : Ajay Kumar
 * Modified by   :
 * Modified date :
 * Reason        :
 *
 */

package com.ttk.common.tags.misreports;

import java.awt.image.BufferedImage;
import java.io.BufferedOutputStream;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.sql.Blob;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.imageio.ImageIO;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.log4j.Logger;

import com.ttk.common.misreports.TTKMisCommon;
import com.ttk.dto.misreports.ImageEnquiryVO;
 
public class ImageServlet extends HttpServlet {
	
	private static Logger log = Logger.getLogger( ImageServlet.class );
	
	ImageEnquiryVO imageEnquiryVO=new ImageEnquiryVO();
	
	/**
     * This method taken request and forward the response 
     * @param write the image in jsp page through iframe
     * @return 
     * @exception throws Exception
     */
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException,
                                                                                          IOException
    {
	    log.debug("Inside the protected void doGet(HttpServletRequest request, HttpServletResponse response)");
        
	    byte buffer[] = null;
        String strChoiceid = new String("");
        ArrayList alChoices = null;
        //BufferedOutputStream out=null;
        BufferedImage buffImage=null;
        BufferedImage buffImageOut=null;
		
        try
        {            
 
        	strChoiceid = request.getParameter("choiceid");
        	alChoices = (ArrayList) request.getSession().getAttribute("alImageEnquiryList");
        }//end of try 
        catch(Exception exp)
        {
 
           exp.printStackTrace();
        } //end of catch(Exception exp)     
        if(!strChoiceid.equals("") && alChoices != null)
        {
        	int index;
        	String[] strch =strChoiceid.split(",");
        	index = Integer.parseInt(strch[0]);
        	/*String strPolicyNbr = ((ImageEnquiryVO)choices.get(index)).getPolicyNbr();
        	String strEnrollNbr = ((ImageEnquiryVO)choices.get(index)).getEnrollmentNbr();
        	String strEnrollmentID = ((ImageEnquiryVO)choices.get(index)).getEnrollmentID();
        	String strMemberName = ((ImageEnquiryVO)choices.get(index)).getMemberName();
        	String strCorporateName = ((ImageEnquiryVO)choices.get(index)).getCorporateName();
        	String strGroupID = ((ImageEnquiryVO)choices.get(index)).getGroupId();*/
        	Blob imagedata = ((ImageEnquiryVO) alChoices.get(index)).getChoice_image();
        
        	try {
        		if(imagedata != null)
        		{
        		buffer = imagedata.getBytes(1,(int)imagedata.length());
        		buffImage=ImageIO.read(new ByteArrayInputStream(buffer));
    			try {
        			//buffImageOut=TTKMisCommon.createResizedCopy(buffImage,60,76,true);
        			buffImageOut = TTKMisCommon.resizeImage(buffImage, 60, 76);
        		} //end of try 
        		catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}//end of catch (Exception e) 
        		}//end of if(imagedata !=null)
        		else
        		{
        			buffer = new byte[0];
        		}//end of else
        	}//end of try 
        	catch (SQLException e) {
        		e.printStackTrace();
        	} //end of catch (SQLException e)                 
        	
        	ImageIO.write(buffImageOut,"jpg",response.getOutputStream());
        	index= index+1;
        }//end of if(!choiceid.equals("") && choices != null) 
        else
        {
 
        	response.setContentType("text/html");
        	response.getWriter().println("Image Not Found");
        } //end of  else                
    }//end of protected void doGet(HttpServletRequest request, HttpServletResponse response)
}//end of ImageServlet 
