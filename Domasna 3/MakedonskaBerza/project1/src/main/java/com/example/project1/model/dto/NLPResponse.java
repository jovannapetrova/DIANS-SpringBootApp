package com.example.project1.model.dto;

import lombok.Data;

@Data
public class NLPResponse {
    public Double sentimentScore;
    public String recommendation;
}
