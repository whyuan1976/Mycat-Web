package jrds.webapp;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.util.Enumeration;
import java.util.Properties;

import javax.servlet.ServletContext;

import org.apache.log4j.Logger;

public class WebStartListener extends StartListener {

    static private final Logger logger = Logger.getLogger(StartListener.class);
    
	@Override
    public Properties readProperties(ServletContext ctxt) {
		
        /*
         Properties p = super.readProperties(ctxt);
         
        if (p  != null)
        	return p;*/
		Properties p = new Properties();
		
		
		
		
        InputStream propStream = ctxt.getResourceAsStream("/jrds/jrds.properties");
        if(propStream != null) {
            try {
                p.load(propStream);
            } catch (IOException ex) {
                logger.warn("Invalid properties stream " + propStream + ": " + ex);
            }
        }

		//String webroot = System.getProperty("webapp.root");
        String webroot = WebStartListener.class.getResource("/").getPath();
		//System.out.println(webroot);
		p.setProperty("configdir", webroot + "jrds/config");
		p.setProperty("rrddir", webroot + "jrds/rrddir");
        Enumeration<String> params = ctxt.getInitParameterNames();
        for(String attr: jrds.Util.iterate(params)) {
            String value = ctxt.getInitParameter(attr);
            if(value != null)
                p.setProperty(attr, value);
        }

       
        return p;
    }
	
	
}
