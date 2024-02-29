package edu.java.clients.stackoverflow;

import edu.java.clients.stackoverflow.dto.StackoverflowResponse;

public interface StackoverflowClient {
    StackoverflowResponse fetch(long id);

}
