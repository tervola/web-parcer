package com.example.bbk.demo.model;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.Value;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;


@NoArgsConstructor
@Getter
public class ScannerResponse {
    private List<WebResourcePageInfo> response;

    public void addPageInfo(WebResourcePageInfo pageInfo) {
        if (response == null) {
            response = new ArrayList<>();
        }
        response.add(pageInfo);
    }
}
