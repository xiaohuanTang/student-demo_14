package com.example.studentdemo.controller;

import com.example.studentdemo.entity.Course;
import com.example.studentdemo.entity.LeaveRequest;
import com.example.studentdemo.entity.Student;
import com.example.studentdemo.service.CourseService;
import com.example.studentdemo.service.LeaveRequestService;
import com.example.studentdemo.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@Controller
@RequestMapping("/leave")
public class LeaveController {

    @Autowired
    private LeaveRequestService leaveRequestService;

    @Autowired
    private CourseService courseService;

    @Autowired
    private StudentService studentService;

    private String getCurrentStudentId() {
        return "20240001";
    }

    @GetMapping("/apply")
    public String applyPage(Model model) {
        String studentId = getCurrentStudentId();
        List<Course> courses = courseService.findByStudentId(studentId);
        model.addAttribute("courses", courses);
        return "leave-apply";
    }

    @PostMapping("/apply")
    public String apply(@RequestParam Integer courseId,
                        @RequestParam String startTime,
                        @RequestParam String endTime,
                        @RequestParam String reason,
                        Model model) {
        String studentId = getCurrentStudentId();
        Student student = studentService.findByStudentId(studentId);
        Course course = courseService.findById(courseId);

        LeaveRequest leave = new LeaveRequest();
        leave.setStudentId(studentId);
        leave.setStudentName(student.getRealName());
        leave.setCourseId(courseId);
        leave.setCourseName(course.getCourseName());
        leave.setStartTime(LocalDateTime.parse(startTime));
        leave.setEndTime(LocalDateTime.parse(endTime));
        leave.setReason(reason);
        leave.setStatus("PENDING");
        leave.setApplyTime(LocalDateTime.now());

        leaveRequestService.save(leave);
        return "redirect:/leave/list";
    }

    @GetMapping("/list")
    public String list(Model model) {
        String studentId = getCurrentStudentId();
        List<LeaveRequest> leaves = leaveRequestService.findByStudentId(studentId);
        model.addAttribute("leaves", leaves);
        return "leave-list";
    }
}