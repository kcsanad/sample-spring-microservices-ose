package hu.takarek.ose.department.client;

import java.util.List;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import hu.takarek.ose.department.model.Employee;

@FeignClient(name = "employee", url = "${microservices.employee.url}")
public interface EmployeeClient {

	@GetMapping("/employee/department/{departmentId}")
	List<Employee> findByDepartment(@PathVariable("departmentId") String departmentId);
	
}
