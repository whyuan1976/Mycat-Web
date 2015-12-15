package org.mycat.web.service.setting;

import java.util.List;

import org.mycat.web.model.MySqlGroup;
import org.mycat.web.model.MycatCluster;
import org.mycat.web.model.MycatLB;
import org.mycat.web.model.MycatNode;
import org.mycat.web.model.MycatServer;
import org.mycat.web.model.MycatZone;

public interface ISettingService {
	List<MycatZone> getAllZones();
	List<MycatLB> getAllLBS();
	List<MycatLB> getLBS(String zoneId);			//查找Zone下LB
	List<MycatCluster> getAllMycatCluster();
	List<MycatCluster> getMycatCluster(String zoneId);
	List<MycatNode> getAllMycatServer();
	List<MySqlGroup> getMysqlGroup(String zoneId);
	List<MySqlGroup> getAllMysqlGroup();
	
}
