package com.example.studentdemo.service;

import com.example.studentdemo.entity.LeaveRequest;
import com.example.studentdemo.mapper.LeaveRequestMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LeaveRequestService {

    @Autowired
    private LeaveRequestMapper leaveRequestMapper;

    public void save(LeaveRequest leaveRequest) {
        leaveRequestMapper.insert(leaveRequest);
    }

    public List<LeaveRequest> findByStudentId(String studentId) {
        return leaveRequestMapper.selectByStudentId(studentId);
    }

    public LeaveRequest findById(Integer id) {  // Long → Integer
        return leaveRequestMapper.selectById(id);
    }
}