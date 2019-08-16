package th.co.nxp.framework.accesscontrol.persistence.repository;

import th.co.nxp.framework.accesscontrol.persistence.entity.RoleOperation;
import th.co.nxp.framework.common.persistence.repository.CommonJpaCrudRepository;

public interface RoleOperationRepository extends CommonJpaCrudRepository<RoleOperation, Long>,RoleOperationRepositoryCustom {

}
                       