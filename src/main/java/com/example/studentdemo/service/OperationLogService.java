package com.example.studentdemo.service;

import com.example.studentdemo.entity.OperationLog;
import com.example.studentdemo.mapper.OperationLogMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OperationLogService {

    @Autowired
    private OperationLogMapper operationLogMapper;

    public void save(OperationLog log) {
        operationLogMapper.insert(log);
    }

    public List<OperationLog> findAll() {
        return operationLogMapper.selectAll();
    }

    public List<OperationLog> findByUserId(String userId) {
        return operationLogMapper.selectByUserId(userId);
    }
}