package com.nscooper.mizuho.components;

import static java.util.concurrent.TimeUnit.HOURS;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.nscooper.mizuho.service.MaintainPricesServiceImpl;

import org.apache.log4j.Logger;

@Component
public class DataMaintenancePoller {
	private static Logger logger = Logger.getLogger(DataMaintenancePoller.class);

	@Autowired
	private MaintainPricesServiceImpl maintainPricesService;

	@Value("${maxAgeOfPricesInDays}")
	private int maxAgeOfPricesInDays;
	
	@Value("${intervalInHoursToCheckForOldPrices}")
	private long intervalInHoursToCheckForOldPrices;

	private ScheduledExecutorService scheduler = null;

	@PostConstruct
	public void contextInitialized() {
		logger.info("DataMaintenancePoller - contextInitialized");
		
		final Runnable poller = new Runnable() {
			public void run() {
				logger.info("Removing data older than "+maxAgeOfPricesInDays+" days old");
				maintainPricesService.removeOldPrices(maxAgeOfPricesInDays);
				logger.info("DataMaintenancePoller removed old stuff!");
			}
		};

		scheduler = Executors.newScheduledThreadPool(1);
		scheduler.scheduleAtFixedRate(poller, 0l, intervalInHoursToCheckForOldPrices, HOURS);
	}

	@PreDestroy
	public void contextDestroyed() {
		if (scheduler != null) {
			scheduler.shutdownNow();
		}
	}

}
