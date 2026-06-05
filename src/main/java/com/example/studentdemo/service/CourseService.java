package com.example.studentdemo.service;

import com.example.studentdemo.entity.Course;
import com.example.studentdemo.mapper.CourseMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalTime;
import java.util.List;

@Service
public class CourseService {

    @Autowired
    private CourseMapper courseMapper;

    public List<Course> findAll() {
        return courseMapper.selectAll();
    }

    public List<Course> findByStudentId(String studentId) {
        return courseMapper.selectByStudentId(studentId);
    }

    public Course findById(Integer id) {
        return courseMapper.selectById(id);
    }

    public LocalTime getCourseStartTime(Integer courseId) {
        return courseMapper.selectStartTimeById(courseId);
    }

    public LocalTime getCourseEndTime(Integer courseId) {
        return courseMapper.selectEndTimeById(courseId);
    }

    public void save(Course course) {
        if (course.getId() == null) {
            courseMapper.insert(course);
        } else {
            courseMapper.update(course);
        }
    }

    public void deleteById(Integer id) {
        courseMapper.deleteById(id);
    }
}