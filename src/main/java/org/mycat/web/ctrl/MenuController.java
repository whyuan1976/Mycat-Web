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
public class MenuController {
	
	@Autowired
	MycatService mycatService;

	
	@RequestMapping("/menus/zones/{zoneId}")
	public List<Menu> getMenus(@PathVariable String zoneId) {
		return getZoneMenu(zoneId); 
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
		Menu menu= new Menu(Integer.toString(i),"Load Balance Group","",Constant.MENU_TYPE_LBS);
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
			menu.addMenu(new Menu(i+"-"+j,group.getName(),"/mysql_info",Constant.MENU_TYPE_PROJECT_GROUP));
			j++;
					
		}
		i++;
		j=1;
				
		
		Menu menu1 = new Menu("1","ZK管理","url","R");
		menus.add(menu1);
		menu1.addMenu(new Menu("2","ZK配置","/zkconfig","R"));
		menu1.addMenu(new Menu("3","ZK信息","/zkread","R"));
		
		menu1 = new Menu("1","用户管理","url","R");
		menus.add(menu1);
		menu1.addMenu(new Menu("2","用户查询","url","D"));
		Menu menu3 = new Menu("1","用户维护","url","D");
		menu1.addMenu(menu3);
		Menu menu2 = new Menu("1","用户新增","url","D");
		menu3.addMenu(menu2);
		
		return menus;
	}
	
	
}