package com.example.studentdemo.controller;

import com.example.studentdemo.annotation.Log;
import com.example.studentdemo.entity.Attendance;
import com.example.studentdemo.entity.Course;
import com.example.studentdemo.entity.Student;
import com.example.studentdemo.exception.BusinessException;
import com.example.studentdemo.service.AttendanceService;
import com.example.studentdemo.service.CourseService;
import com.example.studentdemo.service.ExcelExportService;
import com.example.studentdemo.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

@Controller
@RequestMapping("/attendance")
public class AttendanceController {

    @Autowired
    private AttendanceService attendanceService;

    @Autowired
    private CourseService courseService;

    @Autowired
    private StudentService studentService;

    @Autowired
    private ExcelExportService excelExportService;

    private String getCurrentStudentId() {
        return "20240001";
    }

    private String getClientIp(HttpServletRequest request) {
        String ip = request.getHeader("X-Forwarded-For");
        if (ip == null || ip.isEmpty()) {
            ip = request.getRemoteAddr();
        }
        return ip;
    }

    @Log("访问打卡页面")
    @GetMapping("/checkIn")
    public String checkInPage(Model model) {
        String studentId = getCurrentStudentId();
        List<Course> courses = courseService.findByStudentId(studentId);
        model.addAttribute("courses", courses);
        return "attendance-check-in";
    }

    @Log("考勤打卡")
    @PostMapping("/checkIn")
    public String checkIn(@RequestParam Integer courseId,
                          @RequestParam(required = false) Integer seatRow,
                          @RequestParam(required = false) Integer seatCol,
                          @RequestParam(required = false) String remark,
                          HttpServletRequest request,
                          Model model) {
        String studentId = getCurrentStudentId();
        Student student = studentService.findByStudentId(studentId);

        if (student == null) {
            throw new BusinessException("学生不存在，学号：" + studentId);
        }

        if (attendanceService.findTodayByStudentAndCourse(studentId, courseId) != null) {
            model.addAttribute("error", "今日已打卡");
            model.addAttribute("courses", courseService.findByStudentId(studentId));
            return "attendance-check-in";
        }

        Attendance attendance = new Attendance();
        attendance.setStudentId(studentId);
        attendance.setStudentName(student.getRealName());
        attendance.setCourseId(courseId);
        attendance.setCheckInTime(LocalDateTime.now());
        attendance.setIp(getClientIp(request));
        attendance.setRemark(remark);
        if (seatRow != null) attendance.setSeatRow(seatRow.byteValue());
        if (seatCol != null) attendance.setSeatCol(seatCol.byteValue());

        LocalTime now = LocalTime.now();
        LocalTime startTime = courseService.getCourseStartTime(courseId);
        LocalTime endTime = courseService.getCourseEndTime(courseId);

        if (now.isAfter(endTime)) {
            attendance.setStatus("ABSENT");
        } else if (now.isAfter(startTime)) {
            attendance.setStatus("LATE");
        } else {
            attendance.setStatus("NORMAL");
        }

        attendance.setCreateTime(LocalDateTime.now());
        attendanceService.save(attendance);
        return "redirect:/attendance/list";
    }

    @Log("早退")
    @PostMapping("/earlyLeave")
    public String earlyLeave(@RequestParam Integer attendanceId) {
        Attendance attendance = attendanceService.findById(attendanceId);
        if (attendance != null) {
            LocalTime now = LocalTime.now();
            LocalTime endTime = courseService.getCourseEndTime(attendance.getCourseId());
            if (now.isBefore(endTime)) {
                attendance.setStatus("EARLY");
                attendanceService.update(attendance);
            }
        }
        return "redirect:/attendance/list";
    }

    @Log("查看考勤列表")
    @GetMapping("/list")
    public String list(@RequestParam(required = false) String startDate,
                       @RequestParam(required = false) String endDate,
                       @RequestParam(required = false) String status,
                       @RequestParam(required = false) Integer courseId,
                       Model model) {
        String studentId = getCurrentStudentId();

        LocalDateTime start = startDate != null && !startDate.isEmpty() ? LocalDate.parse(startDate).atStartOfDay() : null;
        LocalDateTime end = endDate != null && !endDate.isEmpty() ? LocalDate.parse(endDate).plusDays(1).atStartOfDay() : null;

        List<Attendance> records = attendanceService.findByCondition(studentId, start, end, status, courseId);
        List<Course> courses = courseService.findByStudentId(studentId);

        model.addAttribute("records", records);
        model.addAttribute("courses", courses);
        model.addAttribute("startDate", startDate);
        model.addAttribute("endDate", endDate);
        model.addAttribute("status", status);
        model.addAttribute("courseId", courseId);
        return "attendance-list";
    }

    @Log("导出考勤记录")
    @GetMapping("/export")
    public void export(@RequestParam(required = false) String startDate,
                       @RequestParam(required = false) String endDate,
                       @RequestParam(required = false) String status,
                       @RequestParam(required = false) Integer courseId,
                       HttpServletResponse response) throws IOException {
        String studentId = getCurrentStudentId();

        LocalDateTime start = startDate != null && !startDate.isEmpty() ? LocalDate.parse(startDate).atStartOfDay() : null;
        LocalDateTime end = endDate != null && !endDate.isEmpty() ? LocalDate.parse(endDate).plusDays(1).atStartOfDay() : null;

        List<Attendance> records = attendanceService.findByCondition(studentId, start, end, status, courseId);
        excelExportService.exportAttendance(records, response);
    }

    @GetMapping("/testError")
    public String testError() {
        throw new BusinessException("这是一个测试异常");
    }
}