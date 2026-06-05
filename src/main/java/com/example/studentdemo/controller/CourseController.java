package com.example.studentdemo.controller;

import com.example.studentdemo.entity.Course;
import com.example.studentdemo.service.CourseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/course")
public class CourseController {

    @Autowired
    private CourseService courseService;

    @GetMapping("/list")
    public String list(Model model) {
        List<Course> courses = courseService.findAll();
        model.addAttribute("courses", courses);
        return "course-list";
    }

    @GetMapping("/add")
    public String addPage(Model model) {
        model.addAttribute("course", new Course());
        return "course-add";
    }

    @PostMapping("/save")
    public String save(Course course) {
        courseService.save(course);
        return "redirect:/course/list";
    }

    @GetMapping("/edit/{id}")
    public String editPage(@PathVariable Integer id, Model model) {
        Course course = courseService.findById(id);
        model.addAttribute("course", course);
        return "course-edit";
    }

    @PostMapping("/update")
    public String update(Course course) {
        courseService.save(course);
        return "redirect:/course/list";
    }

    @GetMapping("/delete/{id}")
    public String delete(@PathVariable Integer id) {
        courseService.deleteById(id);
        return "redirect:/course/list";
    }
}