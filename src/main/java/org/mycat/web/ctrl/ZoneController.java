package org.mycat.web.ctrl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.mycat.web.bean.Menu;
import org.mycat.web.bean.MySqlGroup;
import org.mycat.web.bean.MycatCluster;
import org.mycat.web.bean.MycatLB;
import org.mycat.web.bean.MycatNode;
import org.mycat.web.bean.MycatZone;
import org.mycat.web.service.CommonService;
import org.mycat.web.service.MycatService;
import org.mycat.web.util.Constant;
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
public class ZoneController {

	
	
	
	@Value("${application.message:Hello World}")
	private String message = "Hello World";

	@Autowired
	CommonService commonService;
	
	@Autowired
	MycatService mycatService;
	
	@Autowired
	CounterService counterService;
	
	@Autowired
	GaugeService gaugeService;
	
	
	@RequestMapping("/zones")
	public List<MycatZone> getZones() {
		return mycatService.getAllZones(); 
	}
	
	
	@RequestMapping("/zones/{zoneId}")
	public String getZone(@PathVariable String zoneId,Map<String, Object> model) {
		System.out.println("sssss:"+zoneId);
		model.put("time", new Date());
		model.put("message", this.message);
		List<MycatZone> zones = mycatService.getAllZones(); 
		model.put("zones", zones);
		if (zoneId == null)
			zoneId = zones.get(0).getGuid();
		model.put("myzone", zoneId);
	    model.put("menus", getZoneMenu(zoneId));
	    model.put("content", "metrics");
	    counterService.increment("login.count");			//登陆计数器
	    gaugeService.submit("user.get.db.time", 13.0);
/*		ClientInfo clientInfo = new ClientInfo();
		
	    clientInfo.realname="12Name";
	    clientInfo.signature="signature";
	    clientInfo.password="123";
		UserSession session =  userHandlerPrx.login(clientInfo);
		
		return "welcome "+session.status+" "+session.token;*/
	    
	    
	    
	    
	    
		return "main";
		
		//return "goods/create";
	}

    
	private List<Menu> getZoneMenu(String zoneId) {
		
		List<Menu> menus = new ArrayList<Menu>();
		int i=1,j= 1;		//Menu ID
		
		//Cluster信息
		List<MycatCluster> clusters = mycatService.getMycatCluster(zoneId);
		Collections.sort(clusters);
		for (MycatCluster cluster: clusters){
			Menu menuCluster= new Menu(Integer.toString(i),cluster.getGuid(),"",Constant.MENU_TYPE_CLUSTER_GROUP);
			List<MycatNode> mycats = mycatService.getAllMycatServer();
			Collections.sort(mycats);
			for(MycatNode server: mycats){
				Menu menuServer= new Menu(i+"-"+j,server.getName(),"page/manger/mycat_v2.html",Constant.MENU_TYPE_CLUSTER_NODE);
				menuCluster.addMenu(menuServer);
				j++;
			}
			i++;
			j=1;
			menus.add(menuCluster);
		}
		
		//LB信息
		Menu menu= new Menu(Integer.toString(i),"Load Balance Group","page/manger/mycat.html",Constant.MENU_TYPE_PROJECT_GROUP);
		menus.add(menu);
		List<MycatLB> lbs = mycatService.getLBS(zoneId);
		Collections.sort(lbs);
		for (MycatLB lb: lbs){
			Menu menuLB= new Menu(i+"-"+j,lb.getName(),"page/monitor/jrdsmysql.html",Constant.MENU_TYPE_NODE);
			menu.addMenu(menuLB);
			j++;					
		}
		i++;
		j=1;
		
		//MySQL Group信息
		menu= new Menu(Integer.toString(i),"MySQL Group","",Constant.MENU_TYPE_PROJECT_GROUP);
		menus.add(menu);
		List<MySqlGroup> groups = mycatService.getMysqlGroup(zoneId);
		for (MySqlGroup group: groups){			
			menu.addMenu(new Menu(i+"-"+j,group.getName(),"page/manger/mysql_v2.html",Constant.MENU_TYPE_PROJECT_GROUP));
			j++;
					
		}
		i++;
		j=1;
				
		
		Menu menu1 = new Menu("1","商品管理","url","R");
		menus.add(menu1);
		menu1.addMenu(new Menu("2","商品录入","url","D"));
		menu1.addMenu(new Menu("3","商品查询","url","D"));
		
		menu1 = new Menu("1","用户管理","url","R");
		menus.add(menu1);
		menu1.addMenu(new Menu("2","用户查询","url","D"));
		Menu menu3 = new Menu("1","用户维护","url","D");
		menu1.addMenu(menu3);
		Menu menu2 = new Menu("1","用户新增","url","D");
		menu3.addMenu(menu2);
		
		return menus;
	}
	
	
	
    
	@RequestMapping("/test")
	public String test(Model model) {
		//2015111722005101380013830631 
		//31
	
	
	    return "user";
	}
	
	
}