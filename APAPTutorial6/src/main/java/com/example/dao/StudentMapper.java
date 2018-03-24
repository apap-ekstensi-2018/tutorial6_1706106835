package com.example.dao;

import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import com.example.model.StudentModel;

@Mapper
public interface StudentMapper
{
    @Select("select npm, name, gpa from student where npm = #{npm}")
    StudentModel selectStudent (@Param("npm") String npm);

    @Select("select npm, name, gpa, IF(gpa < 3.5, 'Sangat Memuaskan','Cum Laude') as predikat from student LIMIT #{start}, #{leng}")
    List<StudentModel> selectAllStudents (@Param("start") int start,@Param("leng") int leng);

    @Insert("INSERT INTO student (npm, name, gpa) VALUES (#{npm}, #{name}, #{gpa})")
    boolean addStudent (StudentModel student);
    
    @Delete("DELETE FROM student where npm = #{npm}")
    boolean deleteStudent (String npm);
    
    @Update("UPDATE student set name = #{student.name}, gpa = #{student.gpa} where npm = #{student.npm}")
    boolean updateStudent (@Param("student") StudentModel student);
}
