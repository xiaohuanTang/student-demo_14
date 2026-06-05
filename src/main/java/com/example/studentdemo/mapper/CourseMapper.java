package com.example.studentdemo.mapper;

import com.example.studentdemo.entity.Course;
import org.apache.ibatis.annotations.*;

import java.time.LocalTime;
import java.util.List;

@Mapper
public interface CourseMapper {

    @Select("SELECT * FROM course ORDER BY id")
    List<Course> selectAll();

    @Select("SELECT * FROM course WHERE id = #{id}")
    Course selectById(Integer id);

    @Select("SELECT c.* FROM course c " +
            "LEFT JOIN student_course sc ON c.id = sc.course_id " +
            "WHERE sc.student_id = #{studentId}")
    List<Course> selectByStudentId(String studentId);

    @Select("SELECT start_time FROM course WHERE id = #{courseId}")
    LocalTime selectStartTimeById(Integer courseId);

    @Select("SELECT end_time FROM course WHERE id = #{courseId}")
    LocalTime selectEndTimeById(Integer courseId);

    @Insert("INSERT INTO course(course_name, course_code, class_name, start_time, end_time, teacher_name, classroom, week_day, semester) " +
            "VALUES(#{courseName}, #{courseCode}, #{className}, #{startTime}, #{endTime}, #{teacherName}, #{classroom}, #{weekDay}, #{semester})")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    int insert(Course course);

    @Update("UPDATE course SET course_name = #{courseName}, course_code = #{courseCode}, " +
            "class_name = #{className}, start_time = #{startTime}, end_time = #{endTime}, " +
            "teacher_name = #{teacherName}, classroom = #{classroom}, week_day = #{weekDay}, semester = #{semester} " +
            "WHERE id = #{id}")
    int update(Course course);

    @Delete("DELETE FROM course WHERE id = #{id}")
    int deleteById(Integer id);
}