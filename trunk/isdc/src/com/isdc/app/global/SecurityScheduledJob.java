package com.isdc.app.global;

import java.util.Date;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.springframework.scheduling.annotation.Scheduled;

import com.isdc.app.service.SettingsService;

@SuppressWarnings("restriction")
public class SecurityScheduledJob {
	
	protected static Logger logger = Logger.getLogger("service");
	
	HttpSession session;
	
	@Resource(name="SettingsService")
	private SettingsService settingsservice;
	
    @Scheduled(fixedDelay = 5000)
    //@Scheduled(fixedRate = 5000)
    public void demoServiceMethod() {
    	logger.info("Method executed at every 5 seconds. Current time is :: "+ new Date());
    	logger.info("Method executed at every 5 seconds. Current time is :: "+session.getId());
    }
}
