package org.mycat.web.service;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.framework.imps.CuratorFrameworkState;
import org.apache.curator.retry.ExponentialBackoffRetry;
import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.data.Stat;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

public interface ZookeeperService {

	static final String MYCAT_NAME_SPACE= "mycat";
	
	
	static final String MYCAT_EYE= "/mycat-eye";
	
	static final String MYSQL_GROUP=MYCAT_NAME_SPACE+"/mycat-mysqlgroup";
		
	//boolean connect();	
	
	//boolean reconnect();
	
	boolean isConnected();
	
	void updateZkConfig();
	
	String getUrl();
	
	boolean createPath(Map<String,String> paths);
	
	boolean isExists(String path);
	
	Map<String, Object> getNode(String path);
	
	boolean insertMycat(String guid,Object innerMap);
	
	//获取节点
	Map<String, Object> getMycatNode(String mycatGuid);
	
	//获取全部节点
	List<Map<String, Object>> getMycats();
	
	//获取指定节点
	List<Map<String, Object>> getMycats(String field,String dbname);	
	
	//删除节点
	boolean delMycat(String guid);	    
}
