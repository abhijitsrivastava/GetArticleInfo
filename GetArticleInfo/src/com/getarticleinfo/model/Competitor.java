package com.getarticleinfo.model;

public class Competitor {

	private String competitorName;
	private String competitorPrice;

	public String getCompetitorName() {
		return competitorName;
	}

	public void setCompetitorName(String competitorName) {
		this.competitorName = competitorName;
	}

	public String getCompetitorPrice() {
		return competitorPrice;
	}

	public void setCompetitorPrice(String competitorPrice) {
		this.competitorPrice = competitorPrice;
	}

	@Override
	public String toString() {
		return "Competitor [competitorName=" + competitorName
				+ ", competitorPrice=" + competitorPrice + "]";
	}
	
	

}
