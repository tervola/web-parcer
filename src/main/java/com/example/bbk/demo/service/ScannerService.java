package com.example.bbk.demo.service;


import com.example.bbk.demo.model.ScannerResponse;

import java.util.List;

public interface ScannerService {
    ScannerResponse scanRemoteWebResource(List<String> urls);

}
