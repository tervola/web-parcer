package com.example.bbk.demo.controller;

import com.example.bbk.demo.model.ScannerResponse;
import com.example.bbk.demo.model.UrlRequest;
import com.example.bbk.demo.service.ScannerService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ScannerController {

    private final ScannerService scannerService;

    public ScannerController(ScannerService scannerService) {
        this.scannerService = scannerService;
    }

    @GetMapping
    public ResponseEntity<String> hello() {
        return ResponseEntity.ok("Hello world!");
    }

    @GetMapping("proceed-static-url")
    public ResponseEntity<ScannerResponse> proceedStaticUrls() {
        return ResponseEntity.ok(scannerService.scanRemoteWebResource(null));
    }

    @GetMapping("proceed-custom-url")
    public ResponseEntity<ScannerResponse> proceedCustomUrl(@RequestBody UrlRequest urlRequest) {
        if (urlRequest == null) {
            throw new RuntimeException("Wrong request!");
        }
        return ResponseEntity.ok(scannerService.scanRemoteWebResource(urlRequest.getUrls()));
    }
}
