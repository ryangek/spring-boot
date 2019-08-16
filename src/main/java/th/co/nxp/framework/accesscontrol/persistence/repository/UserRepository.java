package th.co.nxp.framework.accesscontrol.persistence.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import th.co.nxp.framework.accesscontrol.persistence.entity.User;
import th.co.nxp.framework.common.constant.CommonConstants.FLAG;
import th.co.nxp.framework.common.persistence.repository.CommonJpaCrudRepository;

public interface UserRepository extends CommonJpaCrudRepository<User, Long>, UserRepositoryCustom {

	@Query("select e from #{#entityName} e where e.isDeleted = '" + FLAG.N_FLAG + "' and e.username = :username")
	public User findByUsername(@Param("username") String username);

}
