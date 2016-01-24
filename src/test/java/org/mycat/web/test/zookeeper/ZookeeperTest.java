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

/**
 * 测试Zookeeper基础功能
 * 
 * 
 * @author whyuan
 *
 */




//@RunWith(SpringJUnit4ClassRunner.class)
public class ZookeeperTest {

	
	
	
	@BeforeClass
	public static void init(){
		
		
	}
	
	@Before
	public void configuration(){
		//zookeeperService.configuration(new P);
	}
	
	
	
	
	@Test
	public void testCheckConnect(){

		ZookeeperCuratorHandler zk = ZookeeperCuratorHandler.getInstance();
		//单节点登陆
		zk.connect("127.0.0.1:2181", ZookeeperService.MYCAT_NAME_SPACE);
		Assert.assertTrue("Zookeeper can't connect.", zk.isConnected());
		zk.disconnect();
		Assert.assertFalse("Zookeeper can't disconnect.", zk.isConnected());
		
		//多节点登陆
		zk.connect("127.0.0.1:2181,127.0.0.1:2182,127.0.0.1:2183", ZookeeperService.MYCAT_NAME_SPACE);
		Assert.assertTrue("Zookeeper can't connect.", zk.isConnected());
		zk.disconnect();
		
		//异步连接 待实现
		//重试异步连接 待实现   重试n次 无限重试
	}
	
	
		
	
}
