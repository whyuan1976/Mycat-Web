package org.mycat.web.model;

import com.alibaba.fastjson.JSON;

public class MycatNode extends BaseZkNode{
	

	private String name;

	private String hostname;

	private String zone;

	private String cluster;

	private String weigth;

	private String leader;

	private String state;

	private String systemParams;

	private String defaultsqlparser;
	
	private int port;
	
	private int sequncehandlertype;

	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getHostname() {
		return hostname;
	}
	public void setHostname(String hostname) {
		this.hostname = hostname;
	}
	public String getZone() {
		return zone;
	}




	public void setZone(String zone) {
		this.zone = zone;
	}




	public String getCluster() {
		return cluster;
	}




	public void setCluster(String cluster) {
		this.cluster = cluster;
	}




	public String getWeigth() {
		return weigth;
	}




	public void setWeigth(String weigth) {
		this.weigth = weigth;
	}




	public String getLeader() {
		return leader;
	}




	public void setLeader(String leader) {
		this.leader = leader;
	}




	public String getState() {
		return state;
	}




	public void setState(String state) {
		this.state = state;
	}




	public String getSystemParams() {
		return systemParams;
	}




	public void setSystemParams(String systemParams) {
		this.systemParams = systemParams;
	}




	public String getDefaultsqlparser() {
		return defaultsqlparser;
	}




	public void setDefaultsqlparser(String defaultsqlparser) {
		this.defaultsqlparser = defaultsqlparser;
	}




	public int getPort() {
		return port;
	}




	public void setPort(int port) {
		this.port = port;
	}




	public int getSequncehandlertype() {
		return sequncehandlertype;
	}




	public void setSequncehandlertype(int sequncehandlertype) {
		this.sequncehandlertype = sequncehandlertype;
	}




	public static void main(String[] args) {
		MycatNode node = new MycatNode();
		System.out.println(JSON.toJSONString(node));
	}
}
