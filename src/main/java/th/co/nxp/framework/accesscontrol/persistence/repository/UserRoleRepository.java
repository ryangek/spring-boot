package th.co.nxp.framework.accesscontrol.persistence.repository;

import th.co.nxp.framework.accesscontrol.persistence.entity.UserRole;
import th.co.nxp.framework.common.persistence.repository.CommonJpaCrudRepository;

public interface UserRoleRepository extends CommonJpaCrudRepository<UserRole, Long>, UserRoleRepositoryCustom {

}
