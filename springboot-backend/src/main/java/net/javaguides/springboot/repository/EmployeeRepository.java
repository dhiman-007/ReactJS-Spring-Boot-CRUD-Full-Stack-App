package net.javaguides.springboot.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import net.javaguides.springboot.model.Employee;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee, Long>{

    @Query(value = "select Count(*) from employee e where e.EMAIL_ID =:emailId", nativeQuery = true)
    int findByEmailId(String emailId);
}
