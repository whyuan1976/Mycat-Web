package org.mycat.web.util;

import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import jrds.Configuration;
import jrds.GraphNode;
import jrds.HostInfo;
import jrds.HostsList;
import jrds.Probe;
import jrds.Graph;

/**
 * JRDS工具类
 * 
 * 
 * @author 袁文华
 *
 */
public class JrdsUtils {
	

    private static final Logger log = LoggerFactory.getLogger(JrdsUtils.class);
	
	/**
	 * 取得JRDS的Graph
	 * 
	 * @param hostName Host名称
	 * @param probeClass Probe类
	 * @param graphName Graph名称
	 * @return
	 */
	public static Graph getGraph(String hostName,String probeName,String graphName){
		if (hostName == null || graphName == null || probeName == null)
			throw new IllegalArgumentException("hostname , probe name or graph name is null.");

		Probe curProbe = getProbe(hostName, probeName);
		if (curProbe == null)
			return null;
		log.debug("Found proble {}",curProbe.getName());
		
		Collection<GraphNode> allGraphs = curProbe.getGraphList();
		for (GraphNode gn : allGraphs) {
			if (graphName.equals(gn.getGraphDesc().getName())){
				log.debug("Found graph {}",gn.getQualifiedName());
				return gn.getGraph();
			}
		}
		return null;
	}
	
	public static Probe getProbe(String hostName,String probeName){
		return Configuration.get().getHostsList().getProbeByPath(hostName, probeName);
	}
	
	
	
	/**
	 * 获取Probe的最新数据
	 * 
	 * @param hostName 主机名
	 * @param probeName	Probe名
	 * @return
	 */
	public static Map getProbeData(String hostName,String probeName){
		if (hostName == null || probeName == null)
			throw new IllegalArgumentException("hostname or probe name is null.");

		Probe curProbe = getProbe(hostName, probeName);
		if (curProbe != null)
			return curProbe.getLastValues();
		
		return null;
	}
}
