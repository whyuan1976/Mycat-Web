package org.mycat.web.model;


public class MycatCluster extends BaseZkNode{
	
	private String zoneName;
	private String clusterName;
	
	public String getZoneName() {
		return zoneName;
	}
	public void setZoneName(String zoneName) {
		this.zoneName = zoneName;
	}
	public String getClusterName() {
		return clusterName;
	}
	public void setClusterName(String clusterName) {
		this.clusterName = clusterName;
	}

}
