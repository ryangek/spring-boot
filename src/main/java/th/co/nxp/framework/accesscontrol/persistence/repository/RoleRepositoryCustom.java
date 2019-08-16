package th.co.nxp.framework.accesscontrol.persistence.repository;

import java.util.List;

import th.co.nxp.framework.accesscontrol.persistence.entity.Role;
import th.co.nxp.framework.accesscontrol.vo.RoleFormVo;

public interface RoleRepositoryCustom {

	public Integer countByCriteria(RoleFormVo role);

	public List<Role> findByCriteria(RoleFormVo role);
}
