package hu.takarek.ose.department.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import hu.takarek.ose.department.model.Department;

public interface DepartmentRepository extends CrudRepository<Department, String> {

	List<Department> findByOrganizationId(String organizationId);
	
}
