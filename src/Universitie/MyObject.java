package Universitie;

import java.util.List;

public class MyObject {

	
	//private String state_province;
	private List<String> domain;
	private String country;
	private List<String> web_pages;
	private String name;
	private String alpha_two_code;
	
	
//	public String getState_province() {
//		return state_province;
//	}
//	public void setState_province(String state_province) {
//		this.state_province = state_province;
//	}

	public MyObject(List<String> domain, String country, List<String> web_pages, String name, String alpha_two_code) {
		super();
		this.domain = domain;
		this.country = country;
		this.web_pages = web_pages;
		this.name = name;
		this.alpha_two_code = alpha_two_code;
	}
	
	public List<String> getDomain() {
		return domain;
	}
	public void setDomain(List<String> domain) {
		this.domain = domain;
	}
	public String getCountry() {
		return country;
	}
	public void setCountry(String country) {
		this.country = country;
	}
	public List<String> getWeb_pages() {
		return web_pages;
	}
	public void setWeb_pages(List<String> web_pages) {
		this.web_pages = web_pages;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getAlpha_two_code() {
		return alpha_two_code;
	}
	public void setAlpha_two_code(String alpha_two_code) {
		this.alpha_two_code = alpha_two_code;
	}
	

	
}
