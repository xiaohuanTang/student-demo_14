package com.example.studentdemo.mapper;

import com.example.studentdemo.entity.LeaveRequest;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface LeaveRequestMapper {

    @Insert("INSERT INTO leave_request(student_id, student_name, course_id, course_name, start_time, end_time, reason, status, apply_time) " +
            "VALUES(#{studentId}, #{studentName}, #{courseId}, #{courseName}, #{startTime}, #{endTime}, #{reason}, #{status}, #{applyTime})")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    int insert(LeaveRequest leaveRequest);

    @Select("SELECT * FROM leave_request WHERE student_id = #{studentId} ORDER BY apply_time DESC")
    List<LeaveRequest> selectByStudentId(String studentId);

    @Select("SELECT * FROM leave_request WHERE id = #{id}")
    LeaveRequest selectById(Integer id);  // Long → Integer

    @Update("UPDATE leave_request SET status = #{status}, approval_time = #{approvalTime}, approver_remark = #{approverRemark} WHERE id = #{id}")
    int update(LeaveRequest leaveRequest);

    @Select("SELECT * FROM leave_request WHERE status = 'PENDING' ORDER BY apply_time ASC")
    List<LeaveRequest> selectPending();
}