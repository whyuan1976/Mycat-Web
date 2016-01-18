package org.mycat.web.bean;

import java.util.ArrayList;
import java.util.List;

public class Menu {
	private String id;
	private String name;
	private String url;
	private String type;
	private List<Menu> subs = new ArrayList<Menu>();
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public List<Menu> getSubs() {
		return subs;
	}
	public void setSubs(List<Menu> subs) {
		this.subs = subs;
	}
	
	public void addMenu(Menu menu){
		subs.add(menu);
	}
	
	
	public Menu(){
		
	}
	
	public Menu(String id,String name,String url,String type){
		this.id = id;
		this.name = name;
		this.url = url;
		this.type = type;
	}
	
}
