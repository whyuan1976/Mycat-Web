package org.mycat.web.util;


/**
 * 常量类
 * 
 */
public final class Constant {
    private Constant() {

    }

    /** * 统一的编码 */
    public static final String CHARSET = "UTF-8";

    /** * zk的命名空间 */
    public static final String LOCAL_ZK_NS_NAME = "";
    public static final String LOCAL_ZK_URL_NAME = "mycat";

    /*****************zookeeper配置节点路径*******************/
    
    /** 特定中心  */
    public static final String MYCAT_ZONES = "/mycat-zones";
    public static final String MYCAT_LBSES = "/mycat-lbs";
    public static final String MYCAT_ZONE= MYCAT_ZONES + "/zone-";
    public static final String MYCAT_CLUSTERS = "/mycat-cluster";
    public static final String MYCAT_NODES = "/clusters";
    public static final String MYCAT_NODE = MYCAT_NODES + "/cluster-";
    public static final String MYCAT_SERVERS ="/mycat-nodes";
    public static final String MYCAT_MYSQL_GROUPS ="/mycat-mysqlgroup";
    public static final String MYCAT_SERVER = MYCAT_SERVERS + "/server-";
    public static final String MYCAT_HOSTS = "/hosts";
    public static final String MYCAT_HOST = MYCAT_HOSTS + "/host-";
    /** mycat-server负载  */
    public static final String MYCAT_LBS ="/lbs-";
    /** mycat-server负载组  */
    public static final String MYCAT_LBS_GROUP="/lbs_group-";
    /** mysql数据库节点  */
    public static final String MYCAT_MYSQLS="/mysqls-";
    /** mysql数据库组 */
    public static final String MYCAT_MYSQLGROUP="/mysqlgroup-";
    
    

    public static final String MENU_TYPE_ZONE = "1";
    public static final String MENU_TYPE_CLUSTER_GROUP = "2";
    public static final String MENU_TYPE_CLUSTER_NODE = "3";
    public static final String MENU_TYPE_HOST_GROUP = "4";	
    public static final String MENU_TYPE_HOST_NODE = "5";
    public static final String MENU_TYPE_PROJECT_GROUP = "6";
	public static final String MENU_TYPE_PROJECT_NODE = "7";
	public static final String MENU_TYPE_NODE = "8";	
	public static final String MENU_TYPE_LBS = "9";	
	
    
}