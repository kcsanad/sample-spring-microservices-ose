package hu.takarek.ose.organization.controller;

import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import hu.takarek.ose.organization.client.DepartmentClient;
import hu.takarek.ose.organization.client.EmployeeClient;
import hu.takarek.ose.organization.model.Organization;
import hu.takarek.ose.organization.repository.OrganizationRepository;
import io.micrometer.core.instrument.MeterRegistry;

@RestController
@RequestMapping("/organization")
public class OrganizationController {

	private static final Logger LOGGER = LoggerFactory.getLogger(OrganizationController.class);
	
	@Autowired
	OrganizationRepository repository;
	@Autowired
	DepartmentClient departmentClient;
	@Autowired
	EmployeeClient employeeClient;
	@Autowired
    MeterRegistry registry;
	
	@PostMapping
	public Organization add(@RequestBody Organization organization) {
		registry.counter("organization.add","level", "normal").increment();
		LOGGER.info("Organization add: {}", organization);
		return repository.save(organization);
	}
	
	@GetMapping
	public Iterable<Organization> findAll() {
		registry.counter("organization.find", "level","normal").increment();
		LOGGER.info("Organization find");
		return repository.findAll();
	}
	
	@GetMapping("/{id}")
	public Organization findById(@PathVariable("id") String id) {
		registry.counter("organization.find", "level","normal").increment();
		LOGGER.info("Organization find: id={}", id);
		return repository.findById(id).get();
	}

	@GetMapping("/{id}/with-departments")
	public Organization findByIdWithDepartments(@PathVariable("id") String id) {
		LOGGER.info("Organization find: id={}", id);
		Optional<Organization> organization = repository.findById(id);
		if (organization.isPresent()) {
			Organization o = organization.get();
			o.setDepartments(departmentClient.findByOrganization(o.getId()));
			return o;
		} else {
			return null;
		}
	}
	
	@GetMapping("/{id}/with-departments-and-employees")
	public Organization findByIdWithDepartmentsAndEmployees(@PathVariable("id") String id) {
		LOGGER.info("Organization find: id={}", id);
		Optional<Organization> organization = repository.findById(id);
		if (organization.isPresent()) {
			Organization o = organization.get();
			o.setDepartments(departmentClient.findByOrganizationWithEmployees(o.getId()));
			return o;
		} else {
			return null;
		}
	}
	
	@GetMapping("/{id}/with-employees")
	public Organization findByIdWithEmployees(@PathVariable("id") String id) {
		LOGGER.info("Organization find: id={}", id);
		Optional<Organization> organization = repository.findById(id);
		if (organization.isPresent()) {
			Organization o = organization.get();
			o.setEmployees(employeeClient.findByOrganization(o.getId()));
			return o;
		} else {
			return null;
		}
	}
	
}
