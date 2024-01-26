package com.ttk.dto.preauth;

import java.util.Date;
import java.math.BigDecimal;

public class InvestigationHistoryVO extends InvestigationVO{
	//private Date HistoryInvestDate = null;
	//private BigDecimal HistoryClaimAmt = null;
	private String HistoryInvestDate = ""; //koc11 koc 11
	private String HistoryClaimAmt = "";
	public String getHistoryInvestDate() {
		return HistoryInvestDate;
	}
	public void setHistoryInvestDate(String historyInvestDate) {
		HistoryInvestDate = historyInvestDate;
	}
	public String getHistoryClaimAmt() {
		return HistoryClaimAmt;
	}
	public void setHistoryClaimAmt(String historyClaimAmt) {
		HistoryClaimAmt = historyClaimAmt;
	}
	

}