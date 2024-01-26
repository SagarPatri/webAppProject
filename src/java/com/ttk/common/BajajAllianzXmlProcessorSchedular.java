package com.ttk.common;
import org.apache.log4j.Logger;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import com.ttk.common.BajajSchedularHelper;
public class BajajAllianzXmlProcessorSchedular implements Job {
	private static final Logger log = Logger.getLogger( BajajAllianzXmlProcessorSchedular.class);

	BajajSchedularHelper bajajSchedularHelper=null;
	public void execute(JobExecutionContext arg0) throws JobExecutionException  {
		
		try {
			
			
			log.info("Inside run method in BajajAllianzXmlProcessorSchedular");
			bajajSchedularHelper=new BajajSchedularHelper();
			bajajSchedularHelper.processBajajXml();
			log.info("Inside run method in BajajAllianzXmlProcessorSchedular");
			
					
		} catch(Exception e){
			//Thread.sleep(10000);
			TTKCommon.logStackTrace(e);
		}//end of catch(Exception e)
	}//end of execute
		
		
		
		
	}//end of BajajAllianzXmlProcessorSchedular
