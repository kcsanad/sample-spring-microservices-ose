package hu.takarek.ose.department.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import hu.takarek.ose.department.client.EmployeeClient;
import hu.takarek.ose.department.model.Department;
import hu.takarek.ose.department.model.Employee;
import hu.takarek.ose.department.repository.DepartmentRepository;
import io.micrometer.core.instrument.MeterRegistry;

import java.util.List;

@RestController
@RequestMapping("/department")
public class DepartmentController {

	private static final Logger LOGGER = LoggerFactory.getLogger(DepartmentController.class);
	
	@Autowired
	DepartmentRepository repository;
	@Autowired
	EmployeeClient employeeClient;
	@Autowired
    MeterRegistry registry;
	
	@GetMapping("/feign")
	public List<Employee> listRest() {
		return employeeClient.findByDepartment("1");
	}
	
	@PostMapping("/")
	public Department add(@RequestBody Department department) {
		registry.counter("department.add","level", "normal").increment();
		LOGGER.info("Department add: {}", department);
		return repository.save(department);
	}
	
	@GetMapping("/{id}")
	public Department findById(@PathVariable("id") String id) {
		registry.counter("department.find", "level","normal").increment();
		LOGGER.info("Department find: id={}", id);
		return repository.findById(id).get();
	}
	
	@GetMapping("/")
	public Iterable<Department> findAll() {
		registry.counter("department.find", "level","normal").increment();
		LOGGER.info("Department find");
		return repository.findAll();
	}
	
	@GetMapping("/organization/{organizationId}")
	public List<Department> findByOrganization(@PathVariable("organizationId") String organizationId) {
		LOGGER.info("Department find: organizationId={}", organizationId);
		return repository.findByOrganizationId(organizationId);
	}
	
	@GetMapping("/organization/{organizationId}/with-employees")
	public List<Department> findByOrganizationWithEmployees(@PathVariable("organizationId") String organizationId) {
		LOGGER.info("Department find: organizationId={}", organizationId);
		List<Department> departments = repository.findByOrganizationId(organizationId);
		departments.forEach(d -> d.setEmployees(employeeClient.findByDepartment(d.getId())));
		return departments;
	}
	
}
