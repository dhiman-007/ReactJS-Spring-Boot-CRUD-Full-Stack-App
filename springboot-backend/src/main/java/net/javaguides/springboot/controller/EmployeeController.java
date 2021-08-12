package net.javaguides.springboot.controller;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import net.javaguides.springboot.Exceptions.ErrorWhileMakingRequestException;
import net.javaguides.springboot.exception.ResourceNotFoundException;
import net.javaguides.springboot.model.Employee;
import net.javaguides.springboot.repository.EmployeeRepository;
import org.apache.http.HttpEntity;
import org.apache.http.HttpRequest;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import utility.Util;

import javax.json.Json;
import javax.json.JsonObject;
import javax.ws.rs.Consumes;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.URI;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


// Have not created an Service Layer Since Business Implementation is of Less Importance

@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("/api/v1/")
public class EmployeeController {

   // Logger log = Logger.getLogger(EmployeeController.class);

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

    @GetMapping("/getWeather")
    @Produces("application/json")
    @Consumes("application/json")
    public StringBuffer getTemperatureOfCity(@RequestParam("cityName") String cityName) throws IOException {
        String OpenWeatherStackApiURL = new Util().getBaseURL() + "&query=" + cityName;
        StringBuffer result = new StringBuffer();
        try {
            HttpGet request = new HttpGet(OpenWeatherStackApiURL);
            CloseableHttpClient client = HttpClients.createDefault();
            CloseableHttpResponse response = client.execute(request);
            try (BufferedReader buffer = new BufferedReader(
                    new InputStreamReader(response.getEntity().getContent(), StandardCharsets.UTF_8))) {
                String readResponse;
                while ((readResponse = buffer.readLine()) != null) {
                    result.append(readResponse);
                }
            }
        }
        catch (ErrorWhileMakingRequestException e){
            new ErrorWhileMakingRequestException("Error Making Request");
        }
        return result;
    }
}
