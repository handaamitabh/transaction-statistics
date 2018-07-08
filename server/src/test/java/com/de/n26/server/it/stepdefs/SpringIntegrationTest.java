package com.de.n26.server.it.stepdefs;

import com.de.n26.server.Application;
import com.de.n26.server.it.config.IntegrationConfig;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;

@ContextConfiguration(classes = IntegrationConfig.class)
@SpringBootTest(classes = Application.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
abstract class SpringIntegrationTest {
}
