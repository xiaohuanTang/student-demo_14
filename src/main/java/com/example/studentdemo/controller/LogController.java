package com.example.studentdemo.controller;

import com.example.studentdemo.entity.OperationLog;
import com.example.studentdemo.service.OperationLogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/log")
public class LogController {

    @Autowired
    private OperationLogService operationLogService;

    @GetMapping("/list")
    public String list(Model model) {
        List<OperationLog> logs = operationLogService.findAll();
        model.addAttribute("logs", logs);
        return "log-list";
    }
}