package com.example.bbk.demo.model;

import lombok.Data;

import java.util.Set;

@Data
public class WebResourcePageInfo {
    private String url;
    private Set<String> textContent;
}
