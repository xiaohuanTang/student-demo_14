package com.example.studentdemo.entity;

import java.time.LocalTime;

public class Course {
    private Integer id;
    private String courseName;
    private String courseCode;
    private String className;
    private LocalTime startTime;
    private LocalTime endTime;
    private String teacherName;
    private String classroom;
    private Integer weekDay;
    private String semester;

    public Integer getId() { return id; }
    public void setId(Integer id) { this.id = id; }

    public String getCourseName() { return courseName; }
    public void setCourseName(String courseName) { this.courseName = courseName; }

    public String getCourseCode() { return courseCode; }
    public void setCourseCode(String courseCode) { this.courseCode = courseCode; }

    public String getClassName() { return className; }
    public void setClassName(String className) { this.className = className; }

    public LocalTime getStartTime() { return startTime; }
    public void setStartTime(LocalTime startTime) { this.startTime = startTime; }

    public LocalTime getEndTime() { return endTime; }
    public void setEndTime(LocalTime endTime) { this.endTime = endTime; }

    public String getTeacherName() { return teacherName; }
    public void setTeacherName(String teacherName) { this.teacherName = teacherName; }

    public String getClassroom() { return classroom; }
    public void setClassroom(String classroom) { this.classroom = classroom; }

    public Integer getWeekDay() { return weekDay; }
    public void setWeekDay(Integer weekDay) { this.weekDay = weekDay; }

    public String getSemester() { return semester; }
    public void setSemester(String semester) { this.semester = semester; }
}