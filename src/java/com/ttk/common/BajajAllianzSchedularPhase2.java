package com.ttk.common;
import org.apache.log4j.Logger;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

public class BajajAllianzSchedularPhase2 implements Job {
	private static final Logger log = Logger.getLogger( BajajAllianzSchedularPhase2.class);

	BajajSchedularHelperPhase2 bajajSchedularHelperPhase2=null;
	public void execute(JobExecutionContext arg0) throws JobExecutionException  {
		
		try {		
			
			log.info("Inside run method in BajajSchedularHelperPhase2");
			bajajSchedularHelperPhase2=new BajajSchedularHelperPhase2();
			bajajSchedularHelperPhase2.mergeApproveForm();
			log.info("Inside run method in BajajSchedularHelperPhase2");
			
					
		} catch(Exception e){
			//Thread.sleep(10000);
			TTKCommon.logStackTrace(e);
		}//end of catch(Exception e)
	}//end of execute
		
		
		
		
	}//end of BajajAllianzXmlProcessorSchedular
