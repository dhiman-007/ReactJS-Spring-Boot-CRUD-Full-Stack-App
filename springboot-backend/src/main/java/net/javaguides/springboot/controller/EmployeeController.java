package net.javaguides.springboot.controller;

import com.sun.istack.internal.logging.Logger;
import net.javaguides.springboot.exception.ResourceNotFoundException;
import net.javaguides.springboot.model.Employee;
import net.javaguides.springboot.repository.EmployeeRepository;
import org.hibernate.mapping.Collection;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import utility.Util;

import javax.ws.rs.core.Response;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


// Have not created an Service Layer Since Business Implementation is of Less Importance

@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("/api/v1/")
public class EmployeeController {

    Logger log = Logger.getLogger(EmployeeController.class);

    @Autowired
    private EmployeeRepository employeeRepository;

    private List<String> quotes = new Util().quotes();

    @GetMapping("/employees")
    public List<Employee> getAllEmployees() {
        return employeeRepository.findAll();
    }


    @PostMapping("/employees")
    public Response createEmployee(@RequestBody Employee employee) {
//        log.info("Email of the user is " + employee.getEmailId());
//        int count = employeeRepository.findByEmailId(employee.getEmailId());
//        log.info("Email" + count);

//        if (emailId.equals(employee.getEmailId())) {
//            return Response
//                    .status(Response.Status.OK)
//                    .entity("")
//                    .build();
//        }
       employeeRepository.save(employee);
        return Response
                .status(Response.Status.CREATED)
                .entity(employee)
                .build();
    }

    @GetMapping("/employees/{id}")
    public ResponseEntity<Employee> getEmployeeById(@PathVariable Long id) {
        Employee employee = employeeRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Employee not exist with id :" + id));
        return ResponseEntity.ok(employee);
    }

    @PutMapping("/employees/{id}")
    public ResponseEntity<Employee> updateEmployee(@PathVariable Long id, @RequestBody Employee employeeDetails) {
        Employee employee = employeeRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Employee not exist with id :" + id));

        employee.setFirstName(employeeDetails.getFirstName());
        employee.setLastName(employeeDetails.getLastName());
        employee.setEmailId(employeeDetails.getEmailId());

        Employee updatedEmployee = employeeRepository.save(employee);
        return ResponseEntity.ok(updatedEmployee);
    }

    @DeleteMapping("/employees/{id}")
    public ResponseEntity<Map<String, Boolean>> deleteEmployee(@PathVariable Long id) {
        Employee employee = employeeRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Employee not exist with id :" + id));

        employeeRepository.delete(employee);
        Map<String, Boolean> response = new HashMap<>();
        response.put("deleted", Boolean.TRUE);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/get-a-quote")
    public String returnAQuote() {
        int randomQuoteNumber = (int) Math.floor(Math.random() * (quotes.size() - 0) + 0);
        return quotes.get(randomQuoteNumber);
    }
}
