package com.de.n26.server.it.data;

import org.springframework.boot.test.context.TestComponent;
import org.springframework.context.annotation.Scope;
import org.springframework.http.ResponseEntity;

@TestComponent
@Scope("cucumber-glue")
public class State {

    private ResponseEntity<String> lastResponse;

    public ResponseEntity<String> getLastResponse() {
        return lastResponse;
    }

    public void setLastResponse(ResponseEntity<String> lastResponse) {
        this.lastResponse = lastResponse;
    }
}
