package org.mycat.web.test.jrds;

import java.io.FileReader;
import java.util.Collection;
import java.util.Map;
import java.util.Properties;

import jrds.Configuration;
import jrds.Graph;
import jrds.GraphNode;
import jrds.Probe;

import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;
import org.mycat.web.util.JrdsUtils;
public class JrdsTest {
    @BeforeClass
    static public void configure() throws Exception {
    	//org.apache.log4j.LogManager.get
		Properties p = new Properties();
		p.load(new FileReader("D:/tmp/jrds/jrds.properties"));
		Configuration.configure(p);
    }
	
	@Test
	public void testForJdbcProbe(){
		boolean showDetail = true;
		String hostname = "MYSQL_1";
		String probeName = "mysqlstatus";
		//String probeName = "MycatMemory";
		String graphName = "MysqlStatusTest";

		//String graphName = "MycatMemoryGraph";
		
		Probe probe = JrdsUtils.getProbe(hostname, probeName);
        Assert.assertTrue("JDBC Probe Not Found" , probe != null);
		
		
		Collection<GraphNode> allGraphs = JrdsUtils.getProbe(hostname, probeName).getGraphList();
		Assert.assertTrue("JDBC Graph Not Found" , allGraphs != null);
		if (showDetail){
			for (GraphNode gn : allGraphs) {
				System.out.println(gn.getName());
			}
		}
        
        
        
		Map dataMap = JrdsUtils.getProbeData(hostname, probeName);
		Assert.assertTrue("JDBC Data Not Found" , dataMap != null);
		if (showDetail){
			for (Object key: dataMap.keySet()){
				System.out.println(key.toString()+"="+dataMap.get(key));
			}
		}
			

		Graph graph  = JrdsUtils.getGraph(hostname, probeName, graphName);
		Assert.assertTrue("Customer JDBC Graph Not Found" , graph != null);
		if (showDetail)
			System.out.println(graph.getQualifiedName().hashCode());

	}
}
