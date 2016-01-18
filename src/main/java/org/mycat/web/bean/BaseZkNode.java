package org.mycat.web.bean;

public class BaseZkNode implements Comparable<BaseZkNode>{
	private String guid;
	private String name;
	public String getGuid() {
		return guid;
	}

	public void setGuid(String guid) {
		this.guid = guid;
	}
	

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Override
	public int compareTo(BaseZkNode o) {
		return this.name.compareTo(o.name);
	}
	
	
}
