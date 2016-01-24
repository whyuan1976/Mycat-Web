package org.mycat.web.test.zookeeper;

import java.util.List;

import com.alibaba.fastjson.JSONObject;

import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mycat.web.bean.MycatCluster;
import org.mycat.web.bean.MycatLB;
import org.mycat.web.bean.MycatZone;
import org.mycat.web.service.MycatService;
import org.mycat.web.service.ZookeeperService;
import org.mycat.web.util.ZookeeperCuratorHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({"classpath:/applicationContext.xml"})
public class ZookeeperDataTest {
	@Autowired
	MycatService mycatService;
	@Autowired
	ZookeeperService zookeeperService;
	
	
	
	@BeforeClass
	public static void init(){
		//ZookeeperCuratorHandler.getInstance().connect("127.0.0.1:2181", ZookeeperService.MYCAT_NAME_SPACE);
		
	}
	
	@Before
	public void configuration(){
		//zookeeperService.configuration(new P);
	}
	
	
	
	
	@Test
	public void testCheckConnect(){
		Assert.assertTrue("Zookeeper can't connect.", zookeeperService.isConnected());
	}
	
	
	@Test
	public void testGetZoneInfo(){
		
		List<MycatZone> map = mycatService.getAllZones();
		Assert.assertTrue("Zone data has no found.", map != null && map.size() > 0);
		for (MycatZone zone: map){
			System.out.println(JSONObject.toJSONString(zone)+zone.getGuid());
		}
	}
	
	@Test
	public void testMycatCluster(){
		
		List<MycatCluster> map = mycatService.getMycatCluster("wh");
		Assert.assertTrue("MycatCluster data has no found.", map != null && map.size() > 0);
		for (MycatCluster obj: map){
			System.out.println(JSONObject.toJSONString(obj));
		}
	}
	
	@Test
	public void testMycatLB(){
		
		List<MycatLB> map = mycatService.getLBS("wh");
		Assert.assertTrue("LB data has no found.", map != null && map.size() > 0);
		for (MycatLB obj: map){
			System.out.println(JSONObject.toJSONString(obj));
		}
	}
	
	
	
}
