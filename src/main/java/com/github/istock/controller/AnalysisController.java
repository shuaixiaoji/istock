package com.github.istock.controller;

import com.github.istock.service.AnalysisService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * AnalysisController
 *
 * @author mac-huanglc
 * @since 2022/6/22
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/anlysis")
@Slf4j
public class AnalysisController {
    
    @Autowired
    private AnalysisService analysisService;
    
    @GetMapping("/exportData")
    public void exportAnalysisData() {
        analysisService.exportAnalysisData();
    }
}
