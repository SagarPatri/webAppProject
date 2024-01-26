
/**
 * @ (#) TTKMisCommon.java 11th March 2008
 * Project      : TTK Healthcare Services
 * File         : TTKMisCommon.java
 * Author       : Ajay Kumar 
 * Company      : WebEdge Technologies Pvt. Ltd. 
 * Date Created : 11th March 2008
 *
 * @author       :
 * Modified by   :
 * Modified date :
 * Reason        :
 */



package com.ttk.common.misreports;

import java.awt.AlphaComposite;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;

import org.apache.log4j.Logger;
/**
 * This class has the common methods usefull across the project
 *
 */
public class TTKMisCommon {
	private static Logger log = Logger.getLogger( TTKMisCommon.class );
	
	/**
     * Method for resizing images to desired size Using Graphics2d Mechanism. 
     * @param BufferedImage image, new Width, new Height
     * @return Resized iame of type BuffferdImage
     * @exception  
     */
public static BufferedImage resizeImage(BufferedImage img, int newW, int newH) throws Exception{
		log.debug("Inside ResizeImage method for image resizing in TTKMisCommon.");
		BufferedImage bufferImge=null;
		try
		{
			int w = img.getWidth();
			int h = img.getHeight();
			bufferImge = new BufferedImage(newW, newH, img.getType());
			//log.info("image type is-----"+img.getType());
			//log.info("image width is-----"+img.getWidth());
			//log.info("image height is-----"+img.getHeight());
			Graphics2D g = bufferImge.createGraphics();
			g.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
			g.drawImage(img, 0, 0, newW, newH, 0, 0, w, h, null);
			g.dispose();
			
		}//end of try
		catch(Exception Exp)
		{
			Exp.printStackTrace();
		}//end of catch(Exception Exp)
		return bufferImge;
	}//End of BufferedImage img, int newW, int newH)  
	
public static BufferedImage createResizedCopy(Image originalImage,int scaledWidth, int scaledHeight,boolean preserveAlpha)
    {
    	int imageType = preserveAlpha ? BufferedImage.TYPE_INT_RGB : BufferedImage.TYPE_INT_ARGB;
    	BufferedImage scaledBI = new BufferedImage(scaledWidth, scaledHeight, imageType);
    	Graphics2D g = scaledBI.createGraphics();
    	if (preserveAlpha) 
    	{
    		g.setComposite(AlphaComposite.Src);
    	}//end of if (preserveAlpha) 
    	g.setRenderingHint(RenderingHints.KEY_INTERPOLATION,RenderingHints.VALUE_INTERPOLATION_BICUBIC); 
    	g.drawImage(originalImage, 0, 0, scaledWidth, scaledHeight, null); 
    	g.dispose();
    	return scaledBI;

    }//end of  public static BufferedImage createResizedCopy(Image originalImage,int scaledWidth, int scaledHeight,boolean preserveAlpha)

}//end of TTKMisCommon
