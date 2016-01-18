package org.mycat.web;

import java.io.IOException;
import java.util.HashSet;
import java.util.Properties;
import java.util.Set;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

import org.mycat.web.service.ZookeeperService;
import org.mycat.web.util.ZookeeperCuratorHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.actuate.health.Health;
import org.springframework.boot.actuate.health.HealthIndicator;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.SecurityProperties;
import org.springframework.boot.autoconfigure.velocity.VelocityProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.web.servlet.view.velocity.VelocityLayoutViewResolver;



/*@Configuration    	//配置控制  
@ComponentScan    //组件扫描  
@EnableAutoConfiguration //启用自动配置  
*/

@SpringBootApplication
public class MainApplication implements HealthIndicator {

	private static final Logger logger = LoggerFactory.getLogger(MainApplication.class);

	private Properties properties = new Properties();;
	
	@PostConstruct
	public void init() throws IOException{
		logger.debug("Start Server......");
		//读取配置
		properties.load(MainApplication.class.getClassLoader().getResourceAsStream("mycat.properties"));
		String zookeeper = properties.getProperty("zookeeper");
		if (zookeeper == null)
			throw new IllegalArgumentException("there is no zookeeper settings in mycat.properties");
		ZookeeperCuratorHandler.getInstance().connect(zookeeper, ZookeeperService.MYCAT_NAME_SPACE);
		
	}
	
	@PreDestroy  
    public void  dostory(){  
		logger.debug("Stop Server......");  
    }  
      
	@Override
	public Health health() {
		return Health.up().withDetail("hello", "world").build();
	}
	
	public static void main(String[] args) {
		//启动Spring Boot项目的唯一入口  
		SpringApplication app = new SpringApplication(MainApplication.class);  
        app.setWebEnvironment(true);  
        app.setShowBanner(false);  
          
        Set<Object> set = new HashSet<Object>();  
        set.add("classpath:applicationContext.xml");  
        app.setSources(set);  
        app.run(args);  
		//SpringApplication.run(MainApplication.class, args);

	}
	@Bean(name = "velocityViewResolver")
	public VelocityLayoutViewResolver velocityViewResolver(VelocityProperties properties) {
	    VelocityLayoutViewResolver resolver = new VelocityLayoutViewResolver();
	    properties.applyToViewResolver(resolver);
	    //properties.setCharset("UTF-8");
	    //properties.setContentType("text/html;charset=UTF-8");
	    resolver.setLayoutUrl("layout/layout.vm");
	    //resolver.setContentType("text/html;charset=UTF-8");
	    return resolver;
	}
	

	
/*	@Bean
	public SecurityProperties securityProperties() {
		SecurityProperties security = new SecurityProperties();
		security.getBasic().setPath(""); // empty so home page is insecured
		return security;
	}*/
}
