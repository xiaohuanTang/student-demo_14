package com.example.studentdemo.mapper;

import com.example.studentdemo.entity.Attendance;
import org.apache.ibatis.annotations.*;

import java.time.LocalDateTime;
import java.util.List;

@Mapper
public interface AttendanceMapper {

    @Insert("INSERT INTO attendance(student_id, student_name, course_id, check_in_time, seat_row, seat_col, status, ip, remark, create_time) " +
            "VALUES(#{studentId}, #{studentName}, #{courseId}, #{checkInTime}, #{seatRow}, #{seatCol}, #{status}, #{ip}, #{remark}, #{createTime})")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    int insert(Attendance attendance);

    @Select("SELECT * FROM attendance WHERE id = #{id}")
    Attendance selectById(Integer id);

    @Update("UPDATE attendance SET status = #{status} WHERE id = #{id}")
    int update(Attendance attendance);

    @Select("SELECT * FROM attendance WHERE student_id = #{studentId} AND course_id = #{courseId} AND DATE(check_in_time) = CURDATE()")
    Attendance selectTodayByStudentAndCourse(@Param("studentId") String studentId, @Param("courseId") Integer courseId);

    @Select("<script>" +
            "SELECT a.*, c.course_name as courseName " +
            "FROM attendance a " +
            "LEFT JOIN course c ON a.course_id = c.id " +
            "WHERE a.student_id = #{studentId} " +
            "<if test='start != null'> AND a.check_in_time &gt;= #{start} </if>" +
            "<if test='end != null'> AND a.check_in_time &lt; #{end} </if>" +
            "<if test='status != null and status != \"\"'> AND a.status = #{status} </if>" +
            "<if test='courseId != null'> AND a.course_id = #{courseId} </if>" +
            "ORDER BY a.check_in_time DESC" +
            "</script>")
    List<Attendance> selectByCondition(@Param("studentId") String studentId,
                                       @Param("start") LocalDateTime start,
                                       @Param("end") LocalDateTime end,
                                       @Param("status") String status,
                                       @Param("courseId") Integer courseId);
}