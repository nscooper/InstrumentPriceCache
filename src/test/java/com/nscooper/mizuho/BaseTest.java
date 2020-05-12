package com.nscooper.mizuho;

import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.IntegrationTest;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.junit4.AbstractTransactionalJUnit4SpringContextTests;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;
import org.springframework.test.context.support.DirtiesContextTestExecutionListener;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.transaction.annotation.Transactional;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)   // use the SpringBoot app class
@IntegrationTest("server.port:0")   // find a spare port for testing
//mock Servlet API support not needed as running a SpringBoot integration test with embedded Tomcat svr
@TestExecutionListeners(inheritListeners = false, listeners = {
       DependencyInjectionTestExecutionListener.class,
       DirtiesContextTestExecutionListener.class })  
@WebAppConfiguration  //  we need a web context for the tests
@Transactional
public abstract class BaseTest extends AbstractTransactionalJUnit4SpringContextTests {
	
    @Value("${local.server.port}")   // Obtain the port chosen
    private int port;

	public int getPort() {
		return port;
	}
}