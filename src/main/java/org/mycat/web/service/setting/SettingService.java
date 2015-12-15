package org.mycat.web.service.setting;

import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.mycat.web.model.MySqlGroup;
import org.mycat.web.model.MycatCluster;
import org.mycat.web.model.MycatLB;
import org.mycat.web.model.MycatNode;
import org.mycat.web.model.MycatServer;
import org.mycat.web.model.MycatZone;
import org.mycat.web.service.ZookeeperService;
import org.mycat.web.util.Constant;
import org.mycat.web.util.ZookeeperCuratorHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SettingService implements ISettingService {

	//@Autowired
	ZookeeperService zookeeperService = ZookeeperService.getInstance();
	
	

	
	
	@Override
	public List<MycatZone> getAllZones() {		
		return zookeeperService.getChildNode(Constant.MYCAT_ZONES, MycatZone.class);
	}

	@Override
	public List<MycatCluster> getAllMycatCluster() {
		return zookeeperService.getChildNode(Constant.MYCAT_CLUSTERS, MycatCluster.class);
	}

	@Override
	public List<MycatLB> getAllLBS() {
		return zookeeperService.getChildNode(Constant.MYCAT_LBSES, MycatLB.class);
	}

	@Override
	public List<MycatNode> getAllMycatServer() {
		return zookeeperService.getChildNode(Constant.MYCAT_SERVERS, MycatNode.class);
	}


	@Override
	public List<MycatCluster> getMycatCluster(String zoneId) {
		List<MycatCluster> list = getAllMycatCluster();
		Iterator<MycatCluster> iterator = list.iterator();
		MycatCluster tmp = null; 
		
		while (iterator.hasNext()){
			tmp = iterator.next();
			if (zoneId.equals(tmp.getZoneName()))
				list.remove(tmp);
		}
		return list;
	}

	@Override
	public List<MycatLB> getLBS(String zoneId) {
		if (zoneId == null)
			return null;
		List<MycatLB> lbs = zookeeperService.getChildNode(Constant.MYCAT_LBSES, MycatLB.class) ;
		Iterator<MycatLB> iterator = lbs.iterator();
		MycatLB lb = null;
		while(iterator.hasNext()){
			lb = iterator.next();
			if (!zoneId.equals(lb.getZone()))
				iterator.remove();
		}
		return lbs;
	}

	@Override
	public List<MySqlGroup> getMysqlGroup(String zoneId) {
		if (zoneId == null)
			return null;
		List<MySqlGroup> groups = getAllMysqlGroup() ;
		Iterator<MySqlGroup> iterator = groups.iterator();
		MySqlGroup group = null;
		while(iterator.hasNext()){
			group = iterator.next();
			if (!zoneId.equals(group.getZone()))
				iterator.remove();
		}
		return groups;
	}

	@Override
	public List<MySqlGroup> getAllMysqlGroup() {
		return zookeeperService.getChildNode(Constant.MYCAT_MYSQL_GROUPS, MySqlGroup.class);
	}

}


