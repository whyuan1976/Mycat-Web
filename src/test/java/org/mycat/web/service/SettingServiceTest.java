package org.mycat.web.service;

import java.util.List;

import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.retry.ExponentialBackoffRetry;
import org.junit.BeforeClass;
import org.junit.Ignore;
import org.junit.Test;
import org.mycat.web.model.MySqlGroup;
import org.mycat.web.model.MycatCluster;
import org.mycat.web.model.MycatLB;
import org.mycat.web.model.MycatNode;
import org.mycat.web.model.MycatServer;
import org.mycat.web.model.MycatZone;
import org.mycat.web.service.setting.ISettingService;
import org.mycat.web.service.setting.SettingService;
import org.mycat.web.util.MycatPathConstant;
import org.mycat.web.util.ZookeeperCuratorHandler;

import com.alibaba.fastjson.JSONObject;

public class SettingServiceTest {
	
	static ISettingService service;
	
	@BeforeClass
	public static void init(){
		ZookeeperService tmp = ZookeeperService.getInstance();
		tmp.Connected();
		service = new SettingService();
	}
	
	@Test
	//@Ignore
	public void testGetAllZones(){
		
		List<MycatZone> zones = service.getAllZones();
		System.out.println("---------Zone size:"+zones.size());
		for (MycatZone zone: zones){
			System.out.println("ID:"+zone.getGuid());
			System.out.println("Zone Name:"+zone.getName());
		}
	}

	@Test
	@Ignore
	public void testGetAllLBS(){
		List<MycatLB> lbs = service.getAllLBS();
		System.out.println("---------LBS size:"+lbs.size());
		for (MycatLB lb: lbs){
			System.out.println("LB data:"+JSONObject.toJSONString(lb));	
		}
	}	

	
	@Test
	@Ignore
	public void testGetAllServers(){
		List<MycatNode> servers = service.getAllMycatServer();
		System.out.println("---------Server size:"+servers.size());
		for (MycatNode server: servers){
			System.out.println("server data:"+JSONObject.toJSONString(server));	
		}
	}	
	@Test
	@Ignore
	public void testGetAllCluster(){
		
		List<MycatCluster> clusters = service.getAllMycatCluster();
		System.out.println("---------Cluster size:"+clusters.size());
		for (MycatCluster cluster: clusters){
			System.out.println("ID:"+cluster.getGuid());
			System.out.println("cluster Name:"+cluster.getClusterName());
		}
	}
	

	@Test
	@Ignore
	public void testGetBS(){
		List<MycatLB> lbs = service.getLBS("wh");
		System.out.println("---------LBS size:"+lbs.size());
		for (MycatLB lb: lbs){
			System.out.println("LB data:"+JSONObject.toJSONString(lb));
		}
	}
	
	
	@Test
	//@Ignore
	public void testGetAllMysqlGroups(){
		
		List<MySqlGroup> groups = service.getAllMysqlGroup();
		System.out.println("---------Groups size:"+groups.size());
		for (MySqlGroup group: groups){
			System.out.println("Group data:"+JSONObject.toJSONString(group));			
		}
	}
}

