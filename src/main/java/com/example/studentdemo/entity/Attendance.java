package com.example.studentdemo.entity;

import java.time.LocalDateTime;

public class Attendance {
    private Integer id;
    private String studentId;
    private String studentName;
    private Integer courseId;
    private String courseName;  // 新增字段
    private LocalDateTime checkInTime;
    private Byte seatRow;
    private Byte seatCol;
    private String status;
    private String ip;
    private String remark;
    private LocalDateTime createTime;

    // getter/setter
    public Integer getId() { return id; }
    public void setId(Integer id) { this.id = id; }

    public String getStudentId() { return studentId; }
    public void setStudentId(String studentId) { this.studentId = studentId; }

    public String getStudentName() { return studentName; }
    public void setStudentName(String studentName) { this.studentName = studentName; }

    public Integer getCourseId() { return courseId; }
    public void setCourseId(Integer courseId) { this.courseId = courseId; }

    public String getCourseName() { return courseName; }
    public void setCourseName(String courseName) { this.courseName = courseName; }

    public LocalDateTime getCheckInTime() { return checkInTime; }
    public void setCheckInTime(LocalDateTime checkInTime) { this.checkInTime = checkInTime; }

    public Byte getSeatRow() { return seatRow; }
    public void setSeatRow(Byte seatRow) { this.seatRow = seatRow; }

    public Byte getSeatCol() { return seatCol; }
    public void setSeatCol(Byte seatCol) { this.seatCol = seatCol; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }

    public String getIp() { return ip; }
    public void setIp(String ip) { this.ip = ip; }

    public String getRemark() { return remark; }
    public void setRemark(String remark) { this.remark = remark; }

    public LocalDateTime getCreateTime() { return createTime; }
    public void setCreateTime(LocalDateTime createTime) { this.createTime = createTime; }
}