package com.example.bbk.demo.service.impl;

import ch.qos.logback.core.spi.ScanException;
import com.example.bbk.demo.model.ScannerResponse;
import com.example.bbk.demo.model.WebResourcePageInfo;
import com.example.bbk.demo.service.ScannerService;
import com.example.bbk.demo.util.TextExtractor;
import lombok.extern.slf4j.Slf4j;
import org.glassfish.jersey.internal.guava.Lists;
import org.jsoup.Jsoup;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static com.example.bbk.demo.util.TextExtractor.*;
import static java.util.Optional.*;

@Slf4j
@Service
public class ScannerServiceImpl implements ScannerService {

    @Value("${web.urls}")
    private List<String> urls;

    @Override
    public ScannerResponse scanRemoteWebResource(List<String> urls) {
        final ScannerResponse scannerResponse = new ScannerResponse();
        ofNullable(urls).orElse(this.urls).forEach(url -> {
                    final WebResourcePageInfo webResourcePageInfo = new WebResourcePageInfo();
                    webResourcePageInfo.setUrl(url);
                    try {
                        final Elements allElements = Jsoup.connect(url).get().getAllElements();

                        final Set<String> txtContent = extractText(allElements);
                        webResourcePageInfo.setTextContent(txtContent);
                    } catch (IOException e) {
                        log.debug("Error during working with remote URL: {}, error: {}", url, e.getMessage());
                        webResourcePageInfo.setTextContent(
                                Stream.of(
                                        String.format("Error: %s", e.getMessage())).collect(Collectors.toSet()));
                    }
                    scannerResponse.addPageInfo(webResourcePageInfo);
                }
        );
        return scannerResponse;
    }
}
