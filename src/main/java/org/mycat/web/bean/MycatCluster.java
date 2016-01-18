package org.mycat.web.bean;


public class MycatCluster extends BaseZkNode{
	
	private String zoneName;
	
	public String getZoneName() {
		return zoneName;
	}
	public void setZoneName(String zoneName) {
		this.zoneName = zoneName;
	}

}
