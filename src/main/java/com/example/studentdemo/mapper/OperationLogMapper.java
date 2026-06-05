package com.example.studentdemo.mapper;

import com.example.studentdemo.entity.OperationLog;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface OperationLogMapper {

    @Insert("INSERT INTO operation_log(user_id, user_name, operation, method, params, ip, duration, status, error_msg, create_time) " +
            "VALUES(#{userId}, #{userName}, #{operation}, #{method}, #{params}, #{ip}, #{duration}, #{status}, #{errorMsg}, #{createTime})")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    int insert(OperationLog log);

    @Select("SELECT * FROM operation_log ORDER BY create_time DESC")
    List<OperationLog> selectAll();

    @Select("SELECT * FROM operation_log WHERE user_id = #{userId} ORDER BY create_time DESC")
    List<OperationLog> selectByUserId(String userId);
}