package org.mycat.web.ctrl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.mycat.web.bean.Menu;
import org.mycat.web.bean.MycatZone;
import org.mycat.web.bean.ZkInfo;
import org.mycat.web.service.CommonService;
import org.mycat.web.service.MycatService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.actuate.metrics.CounterService;
import org.springframework.boot.actuate.metrics.GaugeService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ZookeeperController {

	@Autowired
	CommonService commonService;
	
	@Autowired
	MycatService mycatService;
	
	@Autowired
	CounterService counterService;
	
	@Autowired
	GaugeService gaugeService;
	
	@RequestMapping("/")
	public String init(Map<String, Object> model) {
		//Menu

	    model.put("menus", getZookeeperMenu());
		//右侧内容
		return "main";
	}

	@RequestMapping("/menus")	
	public  List<Menu> menu(Map<String, Object> model) {
		return getZookeeperMenu();
	}
	
	@RequestMapping("/zks")	
	public  List<ZkInfo> getZkInfos() {
		List<ZkInfo> list = new ArrayList<ZkInfo> ();
		ZkInfo zk = new ZkInfo();
		zk.setHost("192.168.9.1");
		zk.setName("test");
		zk.setPort(2181);
		list.add(zk);
		list.add(zk);
		list.add(zk);
		list.add(zk);
		list.add(zk);
		list.add(zk);
		list.add(zk);
		list.add(zk);
		list.add(zk);
		
		return list;
	}
	
	

	
	
	
	private List<Menu> getZookeeperMenu() {

		List<Menu> menus = new ArrayList<Menu>();
		Menu menu1 = new Menu("1","注册中心","url","R");
		menus.add(menu1);
		menu1.addMenu(new Menu("2","配置","url","D"));
		//menu1.addMenu(new Menu("3","商品查询","url","D"));
		return menus;
	}
    

	
	
}