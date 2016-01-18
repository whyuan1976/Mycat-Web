package org.mycat.web.service;

import java.util.List;
import java.util.Map;

import org.mycat.web.bean.MySqlGroup;
import org.mycat.web.bean.MycatCluster;
import org.mycat.web.bean.MycatLB;
import org.mycat.web.bean.MycatNode;
import org.mycat.web.bean.MycatZone;

public interface MycatService {
	List<MycatZone> getAllZones();
	List<MycatCluster> getAllMycatCluster();
	List<MycatLB> getAllLBS();
	List<MycatNode> getAllMycatServer();

	List<MycatLB> getMycatLBS(String zoneId);
	List<MycatCluster> getMycatCluster(String zoneId);
	List<MycatLB> getLBS(String zoneId);
	List<MySqlGroup> getMysqlGroup(String zoneId);
	List<MySqlGroup> getAllMysqlGroup();
	
}
