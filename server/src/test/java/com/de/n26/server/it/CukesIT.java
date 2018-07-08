package com.de.n26.server.it;

import cucumber.api.CucumberOptions;
import cucumber.api.junit.Cucumber;
import org.junit.runner.RunWith;

@RunWith(Cucumber.class)
@CucumberOptions(
        features = "classpath:features",
        tags = "~@WIP",
        plugin = {"pretty","html:target/cucumber","json:target/cucumber.json"},
        glue = {"com.de.n26.server.it.stepdefs"}
)
public class CukesIT {
}
