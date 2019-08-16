package th.co.nxp.framework.security.persistence.repository;

import org.springframework.data.repository.CrudRepository;

import th.co.nxp.framework.security.persistence.entity.UserAttempt;

public interface UserAttemptRepository extends CrudRepository<UserAttempt, Long> {

	public UserAttempt findByUsername(String username);

}
