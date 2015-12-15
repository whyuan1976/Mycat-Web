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

import org.apache.commons.lang.StringUtils;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.framework.imps.CuratorFrameworkState;
import org.apache.curator.retry.ExponentialBackoffRetry;
import org.apache.zookeeper.data.Stat;
import org.apache.zookeeper.CreateMode;
import org.hx.rainbow.common.context.RainbowContext;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.google.common.base.Preconditions;

import org.mycat.web.model.BaseZkNode;
import org.mycat.web.task.common.Constant;
import org.mycat.web.util.JavaBeanToMapUtil;
import org.mycat.web.util.JsonUtils;
import org.mycat.web.util.MycatPathConstant;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ZookeeperService {
	 private static final Logger LOGGER = LoggerFactory.getLogger(ZookeeperService.class);
	private  static  ZookeeperService  zookeeperService;
	private final String zooKey="zookeeper";
	
	private final String mycat_eye=MycatPathConstant.MYCAT_EYE;
	private final String mycats=mycat_eye+"/mycat";
	private final String mycat_jmx=mycat_eye+"/mycat_jmx";
	private final String mycat_snmp=mycat_eye+"/mycat_snmp";
	private final String mycat_processor=mycat_eye+"/mycat_processor";	
	
	private  String zookeeper;
	private static CuratorFramework framework;
	private String errorWithNullClient = "zookeeper CuratorFramework is null, please invoke connect method first";

	private ZookeeperService(){
		Properties properties = new Properties();
		try {
			properties.load(ZookeeperService.class.getClassLoader().getResourceAsStream("mycat.properties"));
			zookeeper=properties.getProperty(zooKey);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public static ZookeeperService getInstance(){ 
		if(zookeeperService == null){
			synchronized (ZookeeperService.class) {
				if(zookeeperService == null){
					zookeeperService = new ZookeeperService();
				}
			}
		}
		return zookeeperService;	   
	}
	
    private static CuratorFramework createConnection(String url) {
        CuratorFramework curatorFramework = CuratorFrameworkFactory
                .newClient(url, new ExponentialBackoffRetry(100, 6));
        //start connection
        curatorFramework.start();
        //wait 3 second to establish connect
        try {
            curatorFramework.blockUntilConnected(3, TimeUnit.SECONDS);
            if (curatorFramework.getZookeeperClient().isConnected()) {
                return curatorFramework;
            }
        } catch (InterruptedException e) {
        }
   
        //fail situation
        curatorFramework.close();
        //throw new RuntimeException("failed to connect to zookeeper service : " + url);
       System.out.println("failed to connect to zookeeper service : " + url);
       return null;
    }
    
	public boolean Connected(){
		return Connected(zookeeper);
	}
	
	public boolean Connected(String value){
	  try {	
		framework = createConnection(value).usingNamespace(MycatPathConstant.MYCAT_NAME_SPACE);
		if (framework!=null){
			zookeeper=value;
			createMainPath();
			return true;
		}
		else {
			return false;
		}
	  } catch (Exception e) {
		// TODO Auto-generated catch block
		  return false;
	  }	  
	}	
	public boolean reConnected() {
	  boolean isConnect	=isConnected();
	  if (!isConnect){
		  isConnect=Connected();  
	  }
	  return isConnect;
	}
	public boolean isConnected() {
		return framework != null
				&& framework.getState() == CuratorFrameworkState.STARTED;
	}
	
	public void UpdateZkConfig(){
		Properties properties = new Properties();
		try {
			properties.load(ZookeeperService.class.getClassLoader().getResourceAsStream("mycat.properties"));
			properties.setProperty(zooKey, zookeeper);
			
			String realPath = ZookeeperService.class.getClassLoader().getResource("mycat.properties").getPath();
			OutputStream out = new FileOutputStream(realPath);
			System.out.println("realPath : " + realPath);
			properties.store(out, "###ZK CONFIG");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
	public String getZookeeper(){
		return zookeeper;
	}
	public boolean createMainPath() {
		if (!isExitPath(mycat_eye)) {
			createPath(mycat_eye,"mycat eye");
			createPath(mycats,"mycat node");
			createPath(mycat_jmx,"jmx");
			createPath(mycat_snmp,"snmp");
			createPath(mycat_processor,"processor");
		}
		return true;
	}
	
	private boolean isExitPath(String mainPath){
    	if (!reConnected()){
    		return false;
    	}		
        Stat nodeStat= null;
		try {
			nodeStat = framework.checkExists().forPath(mainPath);
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		return nodeStat!=null;
	}
	private boolean createPath(String mainPath,String value) {		
		boolean isCreate=true;
      //  if (!isExitPath(mainPath)) {
        	try {
				framework.create().creatingParentsIfNeeded().withMode(CreateMode.PERSISTENT).forPath(mainPath,value.getBytes());				
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				isCreate=false;
			}
     //   }
        return isCreate;
	}
	
	private int getChildNum(String aPath) {		        
		try {
			List<String> children=null;
			children = framework.getChildren().forPath(aPath);
			if (children!=null) {
			  return children.size();
			}
			else {
				return 0;
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return 0;
		}       
	} 
	
	private boolean processLeafNode(String childPath,Object innerMap) {
        try {
            Stat restNodeStat = framework.checkExists().forPath(childPath);
            if (restNodeStat == null) {
                framework.create().creatingParentsIfNeeded().forPath(childPath);
            }
              framework.setData().forPath(childPath, JSON.toJSONString(innerMap).getBytes());
               return true;
        } catch (Exception e) {
          //  LOGGER.error("create node error: {} ", e.getMessage(), e);
          //  throw new RuntimeException(e);
        	return false;
        }
    }	
	
	private boolean insert(String ParentPath,String guid,Object innerMap) {
		 return processLeafNode(ParentPath+"/"+guid,innerMap);
	}
	
	private boolean insert(String ParentPath,Object innerMap) {
		 int index=getChildNum(ParentPath)+1;
		 return insert(ParentPath+"/"+String.valueOf(index),innerMap);
	}
	

	//删除
	private boolean del(String ParentPath,String guid) {
		try {
			framework.delete().guaranteed().deletingChildrenIfNeeded().withVersion(-1).forPath(ParentPath+"/"+guid);
			return true;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}
	}
	
    public Map<String, Object> readNode(String aPath) {
        //读取节点
        Stat stat = new Stat();
        byte[] nodeData;
		try {
			nodeData = framework.getData().storingStatIn(stat).forPath(aPath);
			String dataNode = new String(nodeData);//.replace("[", "").replace("]", "").trim();
			return JsonUtils.json2Map(dataNode);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
        
    }   
    //获取所有子节点下的数据
    private List<Map<String, Object>> getPath(String ParentPath){
		List<Map<String, Object>> rows = new ArrayList<Map<String, Object>>();
    	if (!reConnected()){
    		return rows;
    	}
		try {
			List<String> children=null;
			children = framework.getChildren().forPath(ParentPath);
			if (children!=null) {
		          for(int i = 0; i < children.size(); i++)  {
		        	  rows.add( readNode(ParentPath+"/"+children.get(i)));
		            }
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			LOGGER.error("Zk.getPath:"+ParentPath+" error:"+e.toString());
		}     		
		return rows;
	}
	//增加修改节点
	public boolean insertMycat(String guid,Object innerMap) {
		return insert(mycats,guid,innerMap);
	}	
	//获取节点
	public Map<String, Object> getMycatNode(String Key) {
		return readNode(mycats+"/"+Key);
	}	
	
	//获取全部节点
	public List<Map<String, Object>> getMycat() {
		return getPath(mycats);
	}	
	//获取指定节点
	public List<Map<String, Object>> getMycat(String field,String dbname) {
		if(dbname ==  null || dbname.isEmpty()){
			return getMycat();
		}		
		else {
		  List<Map<String, Object>> mycatlist= getMycat();
		  for (int i=0;i<mycatlist.size();i++){	
			  Map<String, Object> dbinfo= mycatlist.get(i);
			  String db=(String)dbinfo.get(field);
			  if (db.equals(dbname)){
				  mycatlist.clear();
				  mycatlist.add(dbinfo);
				  break;
			  }
		  }
		  return mycatlist;
		}
	}	
	
	//删除节点
	public boolean delMycat(String guid){
		return del(mycats,guid);
	}	
	
	//增加修改节点
	public boolean insertJmx(String guid,Object innerMap) {
		return insert(mycat_jmx,guid,innerMap);
	}	
	//获取节点
	public Map<String, Object> getJmxNode(String Key) {
		return readNode(mycat_jmx+"/"+Key);
	}		
	//获取全部节点
	public List<Map<String, Object>> getJmx() {
		return getPath(mycat_jmx);
	}	
	//删除节点
	public boolean delJmx(String guid){
		return del(mycat_jmx,guid);
	}		
	
	//增加修改节点
	public boolean insertSnmp(String guid,Object innerMap) {
		return insert(mycat_snmp,guid,innerMap);
	}	
	//获取节点
	public Map<String, Object> getSnmpNode(String Key) {
		return readNode(mycat_snmp+"/"+Key);
	}		
	//获取全部节点
	public List<Map<String, Object>> getSnmp() {
		return getPath(mycat_snmp);
	}	
	//删除节点
	public boolean delSnmp(String guid){
		return del(mycat_snmp,guid);
	}		
///////////////////////////////mycat集群使用
	public List<String> getChilds(String aPath) {		
		List<String> children=null;
		try {			
			children = framework.getChildren().forPath(aPath);
			if (children!=null)
			  return children;	
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}   
		return children;
	} 
	//获取节点的子节点数据，如果没子节点直接获取节点的数据
	public List<Map<String, Object>> getNodeOrChildNodes(String ParentPath,String configKey,String childKey) throws Exception{
	
		if (configKey.equals("datanode-config")){
			return getDatanodeConfig(ParentPath,configKey);
		}
		else if (configKey.equals("schema-config")){
			return getSchemaConfig(ParentPath,configKey);	
			}
		else if (configKey.equals("server-config")){
			return getServerNodeConfig(ParentPath,configKey);	
			}			
		else if (configKey.equals("datahost-config")){
			return getDataHostNodeConfig(ParentPath,configKey);	
			}			
		else {
			String nodePath = ParentPath + "/" + configKey+childKey;
			List<Map<String, Object>> rows = new ArrayList<Map<String, Object>>();
			try {
				List<String> children=null;
				children = framework.getChildren().forPath(nodePath);
				if ((children!=null)  && children.size()>0) {
			          for(int i = 0; i < children.size(); i++)  {
			        	  rows.add( readNode(nodePath+"/"+children.get(i)));
			            }
				}
				else {
					rows.add(readNode(nodePath));
				}
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();		
			}     		
			return rows;	
	
		}
		
	}
    //Datanode Config
    public  List<Map<String,Object>> getDatanodeConfig(String PARENT_PATH ,String configKey)throws Exception {
        String nodePath = PARENT_PATH + "/" + configKey;
        LOGGER.trace("child path is {}", nodePath);
        List<Map<String,Object>> listServer = new ArrayList<>();
        listServer = getDatanodeConfig(listServer, nodePath);
        return listServer;
    }

    //Datanode Child Config
    private  List<Map<String,Object>> getDatanodeConfig(List<Map<String,Object>> listServer,String childPath) throws Exception {

        List list = new ArrayList<>();
        list = framework.getChildren().forPath(childPath);
        Iterator<String> iterator = list.iterator();
        int nodeSize = list.size();
        if (nodeSize==0 ){
            //处理叶子节点数据
            listServer=getConfigData(listServer, childPath);
        }else  {
            for (int i = 0; i < nodeSize; i++) {
                String leaf = iterator.next();
                if (leaf.endsWith("config")){
                    listServer=getConfigData(listServer, childPath);
                }else {
                    String childPath1 = childPath + "/" + leaf;
                    getServerChildConfig(listServer, childPath1);
                }
            }
        }
        return listServer;
    }	
    //DataHost Config
    public  List<Map<String,Object>> getDataHostNodeConfig(String PARENT_PATH ,String configKey)throws Exception {
        String nodePath = PARENT_PATH + "/" + configKey;
        LOGGER.trace("child path is {}", nodePath);
        List<Map<String,Object>> listServer = new ArrayList<>();
        listServer = getDataHostChildConfig(listServer, nodePath);
        return listServer;
    }

    //DataHost Child Config
    private  List<Map<String,Object>> getDataHostChildConfig(List<Map<String,Object>> listServer,String childPath) throws Exception {
        List list = new ArrayList<>();
        list = framework.getChildren().forPath(childPath);
        Iterator<String> iterator = list.iterator();
        int nodeSize = list.size();
        for (int i = 0; i < nodeSize; i++) {
            String leaf = iterator.next();
            String childPath1 = childPath + "/" + leaf;
            listServer=getConfigData(listServer, childPath1);
            getDataHostChildConfig(listServer, childPath1);
        }
        return listServer;
    }	
    //Schema Config
    public  List<Map<String,Object>> getSchemaConfig(String PARENT_PATH ,String configKey)throws Exception {
        String nodePath = PARENT_PATH + "/" + configKey;
        LOGGER.trace("child path is {}", nodePath);
        List<Map<String,Object>> listServer = new ArrayList<>();
        listServer = getSchemaChildConfig(listServer, nodePath);
        return listServer;
    }

    //Schema Child Config
    private  List<Map<String,Object>> getSchemaChildConfig(List<Map<String,Object>> listServer,String childPath) throws Exception {
        List list = new ArrayList<>();
        list = framework.getChildren().forPath(childPath);
        Iterator<String> iterator = list.iterator();
        int nodeSize = list.size();
        for (int i = 0; i < nodeSize; i++) {
            String leaf = iterator.next();
            String childPath1 = childPath + "/" + leaf;
            listServer=getConfigData(listServer, childPath1);
            getSchemaChildConfig(listServer, childPath1);
        }
        return listServer;
    }	
    //Server Config
    public  List<Map<String,Object>> getServerNodeConfig(String PARENT_PATH,String configKey)throws Exception {
        String nodePath = PARENT_PATH + "/" + configKey;
        LOGGER.trace("child path is {}", nodePath);
        List<Map<String,Object>> listServer = new ArrayList<>();
        listServer = getServerChildConfig(listServer, nodePath);
        return listServer;
    }

    //Server Child Config
    private  List<Map<String,Object>> getServerChildConfig(List<Map<String,Object>> listServer,String childPath) throws Exception {

        List list = new ArrayList<>();
        list = framework.getChildren().forPath(childPath);
        Iterator<String> iterator = list.iterator();
        int nodeSize = list.size();
        if (nodeSize==0 ){
            //处理叶子节点数据
            listServer=getConfigData(listServer, childPath);
        }else  {
            for (int i = 0; i < nodeSize; i++) {
                String leaf = iterator.next();
                if (leaf.endsWith("config")){
                    listServer=getConfigData(listServer, childPath);
                }else {
                    String childPath1 = childPath + "/" + leaf;
                    getServerChildConfig(listServer, childPath1);
                }
            }
        }
        return listServer;
    }    
    private  List<Map<String,Object>> getConfigData(List<Map<String,Object>> list,String childPath) throws IOException {

        Map<String,Object> map = new HashMap<>();
        String data= null;
        try {
            data = new String(framework.getData().forPath(childPath),"utf8");
            //list.add(JsonUtils.json2Map(new String(data)));  
            
            if (data.startsWith("[")&&data.endsWith("]")){ //JsonArray
                JSONArray jsonArray = JSONArray.parseArray(data);
//                System.out.println("----------------------JSONARRAY------------------------");
//                System.out.println("---------------------"+childPath+"-------------------------");
//                System.out.println(jsonArray);
                for (int i=0;i<jsonArray.size();i++){
                    map.put(childPath,(JSONObject)jsonArray.get(i));
                    list.add(map);
                }
                return list;
            }else {  //JsonObject

                JSONObject jsonObject = JSONObject.parseObject(data);
//                System.out.println("----------------------jsonObject------------------------");
//                System.out.println("---------------------" + childPath + "-------------------------");
//                System.out.println(jsonObject);
                map.put(childPath,jsonObject);
                list.add(map);
                return list;
                //write json to xml
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }    
	public <T> Map<String, Object> getChildNodeData(String path,Class<T> entity){
		try {
			return getChildNodeData(path, entity, 0,null,null);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	public List<String> getChildNode(String path){
		Preconditions.checkNotNull(framework, errorWithNullClient);
		Map<String, Object> reMap=new HashMap<String, Object>();
		Stat stat=null;
		try {
			stat = framework.checkExists().forPath(path);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if(stat==null)
			return null;
		List<String> forPath=new ArrayList<String>();
		try {
			forPath = framework.getChildren().forPath(path);
			return forPath;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	public <T> Map<String, Object> getChildNodeData(String path,Class<T> entity,Integer begin,Integer size,Map<String, Object> attr){
		Preconditions.checkNotNull(framework, errorWithNullClient);
		Map<String, Object> reMap=new HashMap<String, Object>();
		Stat stat=null;
		try {
			stat = framework.checkExists().forPath(path);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if(stat==null)
			return null;
		List<String> forPath=new ArrayList<String>();
		
		
		try {
			forPath = framework.getChildren().forPath(path);
			List<String> remove=new ArrayList<String>();
			if (attr!=null&&attr.size()>=1&&attr.get("name")!=null) {
				String name=String.valueOf(attr.get("name"));
				if(StringUtils.isNotEmpty(name)){
					for (String s : forPath) {
						if(s.indexOf(name)<0)
							remove.add(s);
					}
				}
			}
			forPath.removeAll(remove);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if(size==null){
			size=0;
		}
		int offsize=begin*size;
		if(size==0||offsize>forPath.size()){
			offsize=forPath.size();
		}
		List<Map<String, Object>> rows=new ArrayList<Map<String, Object>>();
		for (int i = (begin-1)*size; i < offsize; i++) {
			String s=forPath.get(i);
			String nodeData = getNodeData(path+"/"+s);
			if(StringUtils.isEmpty(nodeData))
				continue;
			T t = JSONArray.parseObject(nodeData, entity);
			rows.add(JavaBeanToMapUtil.beanToMap(t));
		}
		reMap.put("rows", rows);
		reMap.put("total", forPath.size());
		return reMap;
	}
	
	public String getNodeData(String path) {
		Preconditions.checkNotNull(framework, errorWithNullClient);
		String rep=null;
		try {
			byte[] byteData = framework.getData().forPath(path);
			rep=new String(byteData, Constant.CHARSET);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return rep;
	}
	public <T> List<T> getChildNode(String path,Class<T> entity){
		Preconditions.checkNotNull(framework, errorWithNullClient);
		//Map<String, Object> reMap=new HashMap<String, Object>();
		Stat stat=null;
		try {
			stat = framework.checkExists().forPath(path);
		} catch (Exception e) {
			//log.error("Path is not exist.",e);
		}
		if(stat==null)
			return null;
		List<String> forPath = null;
		try {
			forPath = framework.getChildren().forPath(path);
		} catch (Exception e) {
			//LOG.error("getChildNode error :",e);
		}
		List<T> list = new ArrayList<T>();		
		for (String s: forPath) {			
			String nodeData = getNodeData(path+"/"+s);
			if(StringUtils.isEmpty(nodeData))
				nodeData = "{}";
			//	continue;			
			T t = JSONArray.parseObject(nodeData, entity);
			if (t instanceof BaseZkNode){
				((BaseZkNode)t).setGuid(s);
			}
			list.add(t);
		}
		//reMap.put("rows", rows);
		//reMap.put("total", forPath.size());
		return list;
	}

}
