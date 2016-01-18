package org.mycat.web.bean;

import java.util.LinkedList;
import java.util.List;

public class MySqlGroup extends BaseZkNode{
	private int repType;
	private String zone;
	private String[] servers;
	private String cur_write_server;
	private boolean auto_write_switch;
	private String heartbeatSQL;
	public int getRepType() {
		return repType;
	}
	public void setRepType(int repType) {
		this.repType = repType;
	}
	public String getZone() {
		return zone;
	}
	public void setZone(String zone) {
		this.zone = zone;
	}
	public String[] getServers() {
		return servers;
	}
	public void setServers(String[] servers) {
		this.servers = servers;
	}
	public String getCur_write_server() {
		return cur_write_server;
	}
	public void setCur_write_server(String cur_write_server) {
		this.cur_write_server = cur_write_server;
	}
	public boolean isAuto_write_switch() {
		return auto_write_switch;
	}
	public void setAuto_write_switch(boolean auto_write_switch) {
		this.auto_write_switch = auto_write_switch;
	}
	public String getHeartbeatSQL() {
		return heartbeatSQL;
	}
	public void setHeartbeatSQL(String heartbeatSQL) {
		this.heartbeatSQL = heartbeatSQL;
	}
	
}
