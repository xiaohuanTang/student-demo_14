package com.example.studentdemo.service;

import com.example.studentdemo.entity.Attendance;
import com.example.studentdemo.mapper.AttendanceMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class AttendanceService {

    @Autowired
    private AttendanceMapper attendanceMapper;

    public void save(Attendance attendance) {
        attendanceMapper.insert(attendance);
    }

    public Attendance findById(Integer id) {
        return attendanceMapper.selectById(id);
    }

    public void update(Attendance attendance) {
        attendanceMapper.update(attendance);
    }

    public Attendance findTodayByStudentAndCourse(String studentId, Integer courseId) {
        return attendanceMapper.selectTodayByStudentAndCourse(studentId, courseId);
    }

    public List<Attendance> findByCondition(String studentId, LocalDateTime start, LocalDateTime end, String status, Integer courseId) {
        return attendanceMapper.selectByCondition(studentId, start, end, status, courseId);
    }
}