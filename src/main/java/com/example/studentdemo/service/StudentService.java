package com.example.studentdemo.service;

import com.example.studentdemo.entity.Student;
import com.example.studentdemo.mapper.StudentMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StudentService {

    @Autowired
    private StudentMapper studentMapper;

    // 根据学号查询
    public Student findByStudentId(String studentId) {
        return studentMapper.selectByStudentId(studentId);
    }

    // 根据ID查询
    public Student findById(Integer id) {
        return studentMapper.selectById(id);
    }

    // 查询所有学生
    public List<Student> findAll() {
        return studentMapper.selectAll();
    }

    // 保存学生（新增或更新）
    public void save(Student student) {
        if (student.getId() == null) {
            studentMapper.insert(student);
        } else {
            studentMapper.update(student);
        }
    }

    // 根据ID删除
    public void deleteById(Integer id) {
        studentMapper.deleteById(id);
    }
}