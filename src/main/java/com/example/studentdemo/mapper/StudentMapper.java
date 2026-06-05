package com.example.studentdemo.mapper;

import com.example.studentdemo.entity.Student;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface StudentMapper {

    @Select("SELECT * FROM student WHERE student_id = #{studentId}")
    Student selectByStudentId(String studentId);

    @Select("SELECT * FROM student WHERE id = #{id}")
    Student selectById(Integer id);

    @Select("SELECT * FROM student")
    List<Student> selectAll();

    @Insert("INSERT INTO student(student_id, real_name, class_name, phone, email) " +
            "VALUES(#{studentId}, #{realName}, #{className}, #{phone}, #{email})")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    int insert(Student student);

    @Update("UPDATE student SET student_id = #{studentId}, real_name = #{realName}, " +
            "class_name = #{className}, phone = #{phone}, email = #{email} WHERE id = #{id}")
    int update(Student student);

    @Delete("DELETE FROM student WHERE id = #{id}")
    int deleteById(Integer id);
}