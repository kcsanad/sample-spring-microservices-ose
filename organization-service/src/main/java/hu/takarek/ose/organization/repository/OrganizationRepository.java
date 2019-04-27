package hu.takarek.ose.organization.repository;

import org.springframework.data.repository.CrudRepository;

import hu.takarek.ose.organization.model.Organization;

public interface OrganizationRepository extends CrudRepository<Organization, String> {
	
}
