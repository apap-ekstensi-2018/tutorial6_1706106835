package com.example.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.dao.StudentMapper;
import com.example.model.StudentModel;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class StudentServiceDatabase implements StudentService
{
    @Autowired
    private StudentMapper studentMapper;

    public StudentServiceDatabase(){}
    
    public StudentServiceDatabase(StudentMapper studentMapper){
    		this.studentMapper = studentMapper;
    }
    
    @Override
    public StudentModel selectStudent (String npm)
    {
        log.info ("select student with npm {}", npm);
        return studentMapper.selectStudent (npm);
    }


    @Override
    public List<StudentModel>selectAllStudents(int start, int leng)
    {
        log.info ("select all students");
        return studentMapper.selectAllStudents(start, leng);
    }


    @Override
    public boolean addStudent (StudentModel student)
    {
        return studentMapper.addStudent (student);
    }


    @Override
    public boolean deleteStudent (String npm)
    {
    		return studentMapper.deleteStudent(npm);
    		//log.info("student "+ npm + " deleted");
    }
    
//    public void updateStudent (String npm, String name, double gpa)
//    {
//    		studentMapper.updateStudent(npm, name,gpa);
//    		log.info("student "+ npm + " update");
//    }
    
    public boolean updateStudent (StudentModel student)
    {
    		return studentMapper.updateStudent(student);
    		//log.info("student "+ student.getNpm() + " update");
    }

}
