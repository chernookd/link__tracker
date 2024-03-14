package edu.java.clients.stackoverflow.dto;

import java.util.List;
import lombok.Data;

@Data
public class StackoverflowResponse {
    List<StackoverflowItem> items;
}
