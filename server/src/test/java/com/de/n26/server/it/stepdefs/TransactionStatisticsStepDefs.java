package com.de.n26.server.it.stepdefs;

import com.de.n26.dto.StatisticsDto;
import com.de.n26.dto.TransactionDto;
import com.de.n26.server.it.data.State;
import com.de.n26.server.it.utils.GherkinHelper;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import cucumber.api.DataTable;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.context.annotation.Scope;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import static org.junit.Assert.assertEquals;

@Scope("cucumber-glue")
public class TransactionStatisticsStepDefs extends SpringIntegrationTest {

    private final TestRestTemplate restTemplate;
    private final State state;
    private final ObjectMapper objectMapper;

    @Autowired
    public TransactionStatisticsStepDefs(TestRestTemplate restTemplate, State state, ObjectMapper objectMapper) {
        this.restTemplate = restTemplate;
        this.state = state;
        this.objectMapper = objectMapper;
    }

    @When("^a request is made to process a transaction with input as$")
    public void callTransactionEndpoint(DataTable dataTable) {
        Map<String, String> request = dataTable.asMaps(String.class, String.class).get(0);
        TransactionDto transactionDto = new TransactionDto(Double.parseDouble(request.get("amount")), GherkinHelper.millsFromString(request.get("timestamp")));
        state.setLastResponse(restTemplate.postForEntity("/transactions", transactionDto, null));
    }

    @Then("^response code is (\\d+)$")
    public void responseCodeIs(int responseCode) throws Throwable {
        assertEquals((long) responseCode, (long) this.state.getLastResponse().getStatusCodeValue());
    }

    @Given("^Below are the transactions$")
    public void listOfTransactions(DataTable dataTable) {
        List<Map<String, String>> requests = dataTable.asMaps(String.class, String.class);
        requests.forEach(request -> {
            TransactionDto transactionDto = new TransactionDto(Double.parseDouble(request.get("amount")), GherkinHelper.millsFromString(request.get("timestamp")));
            restTemplate.postForEntity("/transactions", transactionDto, null);
        });
    }

    @When("^a request is made to get the transaction statistics$")
    public void callStatisticsEndpoint() {
        state.setLastResponse(restTemplate.getForEntity("/statistics", String.class));
    }

    @Then("^the valid transaction statistics are$")
    public void assertTransactionResponse(DataTable dataTable) throws IOException {
        Map<String, String> request = dataTable.asMaps(String.class, String.class).get(0);
        StatisticsDto actualStatisticsDto = objectMapper.readValue(state.getLastResponse().getBody(),
                new TypeReference<StatisticsDto>() {
                });

        StatisticsDto expectedStatisticsDto = new StatisticsDto(Double.parseDouble(request.get("sum")),
                Double.parseDouble(request.get("average")),
                Double.parseDouble(request.get("maximum")),
                Double.parseDouble(request.get("minimum")),
                Long.parseLong(request.get("count")));

        assertEquals(expectedStatisticsDto, actualStatisticsDto);
    }
}
