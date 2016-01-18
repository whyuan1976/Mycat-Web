package org.mycat.web.service.impl;

import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.retry.ExponentialBackoffRetry;
import org.mycat.web.service.ZookeeperService;
import org.mycat.web.util.ZookeeperCuratorHandler;
import org.springframework.stereotype.Service;
@Service
public class ZookeeperServiceImpl implements ZookeeperService {
	
	//private String url;
	 
	ZookeeperCuratorHandler zkHandler = ZookeeperCuratorHandler.getInstance();
	
	
	private static CuratorFramework framework;
	

	@Override
	public boolean isConnected() {
		return zkHandler.isConnected();
	}

	@Override
	public void updateZkConfig() {
		// TODO Auto-generated method stub

	}

	@Override
	public String getUrl() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean createPath(Map<String, String> paths) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean isExists(String path) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public Map<String, Object> getNode(String path) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean insertMycat(String guid, Object innerMap) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public Map<String, Object> getMycatNode(String mycatGuid) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Map<String, Object>> getMycats() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Map<String, Object>> getMycats(String field, String dbname) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean delMycat(String guid) {
		// TODO Auto-generated method stub
		return false;
	}

	
}
