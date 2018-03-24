package com.example.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.validation.BindingResult;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.*;

import com.example.model.StudentModel;
import com.example.service.StudentService;

@Controller
public class StudentController
{
    @Autowired
    StudentService studentDAO;
    String start = "0";
    String leng = "5";

    @RequestMapping("/")
    public String index ()
    {
        return "index";
    }


    @RequestMapping("/student/add")
    public String add ()
    {
        return "form-add";
    }


    @RequestMapping("/student/add/submit")
    public String addSubmit (
            @RequestParam(value = "npm", required = false) String npm,
            @RequestParam(value = "name", required = false) String name,
            @RequestParam(value = "gpa", required = false) double gpa)
    {
        StudentModel student = new StudentModel (npm, name, gpa);
        studentDAO.addStudent (student);

        return "success-add";
    }


    @RequestMapping("/student/view")
    public String view (Model model,
            @RequestParam(value = "npm", required = false) String npm)
    {
        StudentModel student = studentDAO.selectStudent (npm);

        if (student != null) {
            model.addAttribute ("student", student);
            return "view";
        } else {
            model.addAttribute ("npm", npm);
            return "not-found";
        }
    }


    @RequestMapping("/student/view/{npm}")
    public String viewPath (Model model,
            @PathVariable(value = "npm") String npm)
    {
        StudentModel student = studentDAO.selectStudent (npm);

        if (student != null) {
            model.addAttribute ("student", student);
            return "view";
        } else {
            model.addAttribute ("npm", npm);
            return "not-found";
        }
    }


    @RequestMapping("/student/viewall")
    public String view (HttpServletRequest request, HttpServletResponse response,Model model)
    {
//        String start = request.getParameter("start");
//        String leng = request.getParameter("length");
//        List<StudentModel> students = studentDAO.selectAllStudents(start,leng);
//        model.addAttribute ("students", students);
        return "viewall";
    }
    
    @RequestMapping(value = "/student/data",method = RequestMethod.GET,produces = "application/json")
    @ResponseBody
    public String studentData(HttpServletRequest request, HttpServletResponse response,Model model)
    {
    		if(!request.getParameter("start").isEmpty()) {
    			start = request.getParameter("start");
    			leng = request.getParameter("length");
    		}
        List<StudentModel> students = studentDAO.selectAllStudents(Integer.parseInt(start),Integer.parseInt(leng));
        model.addAttribute ("students", students);
        String js = new Gson().toJson(students);
        return js;
    }
    


    @RequestMapping("/student/delete/{npm}")
    public String delete (Model model, @PathVariable(value = "npm") String npm)
    {
        StudentModel student = studentDAO.selectStudent(npm);
    		
        if (student != null)
    			studentDAO.deleteStudent (npm);
        else {
        		model.addAttribute("npm", npm);
        		return "not-found";
        }

        return "delete";
    }
    
    @RequestMapping("/student/update/{npm}")
    public String update (Model model, @PathVariable(value = "npm") String npm)
    {
    		StudentModel student = studentDAO.selectStudent(npm);
    		
            if (student == null){
            		model.addAttribute("npm", npm);
            		return "not-found";
            }

            model.addAttribute ("student", student);
            return "form-update";
    }
    
//    @RequestMapping(value = "/student/update/submit", method = RequestMethod.POST)
//    public String updateSubmit(
//    		@RequestParam(value = "npm", required = false) String npm,
//    		@RequestParam(value = "name", required = false) String name,
//    		@RequestParam(value = "gpa", required = false) double gpa) 
//    {
//    		studentDAO.updateStudent(npm,name,gpa);
//    		return "success-update";
//    }

    @PostMapping("/student/update/submit/")
    public String updateSubmit(@Valid @ModelAttribute("student") StudentModel student,
    		BindingResult result) {

    		if(result.hasErrors())
    			return "not-valid-input";
    		else
    			studentDAO.updateStudent(student);
    		
    		return "success-update";
    }
}
