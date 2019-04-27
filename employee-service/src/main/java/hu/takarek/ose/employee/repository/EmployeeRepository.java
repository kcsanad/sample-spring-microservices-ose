package hu.takarek.ose.employee.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import hu.takarek.ose.employee.model.Employee;

public interface EmployeeRepository extends CrudRepository<Employee, String> {
	
	List<Employee> findByDepartmentId(String departmentId);
	List<Employee> findByOrganizationId(String organizationId);
	
}
