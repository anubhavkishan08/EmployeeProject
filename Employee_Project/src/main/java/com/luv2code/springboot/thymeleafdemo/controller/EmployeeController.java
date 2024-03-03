package com.luv2code.springboot.thymeleafdemo.controller;

import com.luv2code.springboot.thymeleafdemo.entity.Employee;
import com.luv2code.springboot.thymeleafdemo.service.EmployeeService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/employees")
public class EmployeeController {

    private EmployeeService employeeService;


    //Contructor Injection, since this class has one constructor so auto wired is optional
    public EmployeeController(EmployeeService theEmployeeService){
        employeeService=theEmployeeService;
    }

    //add mapping for /list
    @GetMapping("/list")
    public String listEmployees(Model themodel){
        //get the employees list from db
        List<Employee> theEmployees=employeeService.findAll();

        //add the list to Spring model
        themodel.addAttribute("employees",theEmployees);
        return "employees/list-employees";
    }

    @GetMapping("/showFormForAdd")
    public String showFormForAdd(Model themodel){
        //create model attribute to bind form data
        Employee employees=new Employee();

        //
        themodel.addAttribute("employee",employees);

        return "employees/employee-form";
    }

    @GetMapping("/showFormForUpdate")
    public String showFormForUpdate(@RequestParam("employeeId") int theId,Model themodel){
        //get the Employee from the Service
        Employee theEmployee=employeeService.findById(theId);

        //set the Employee in the model to prepoulate the form
        themodel.addAttribute("employee",theEmployee);

        //send over the form

        return "employees/employee-form";
    }
    @PostMapping("/save")
    public String saveEmployee(@ModelAttribute("employees") Employee theEmployee){
        //save the Employee
        employeeService.save(theEmployee);
        //use a redirect to prevent duplicate submission
        return "redirect:/employees/list";
    }

    @GetMapping("/delete")
    public String deleteEmployee(@RequestParam("employeeId") int theId){

        //delete the Employee
        employeeService.deleteById(theId);
        //use a redirect to prevent duplicate submission
        return "redirect:/employees/list";

    }
}
