package th.co.nxp.framework.accesscontrol.persistence.repository;

import th.co.nxp.framework.accesscontrol.persistence.entity.Menu;
import th.co.nxp.framework.common.persistence.repository.CommonJpaCrudRepository;

public interface MenuRepository extends CommonJpaCrudRepository<Menu, Long>, MenuRepositoryCustom {

}
