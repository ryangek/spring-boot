package th.co.nxp.framework.accesscontrol.persistence.repository;

import java.util.List;

import th.co.nxp.framework.accesscontrol.vo.UserRoleFormVo;
import th.co.nxp.framework.accesscontrol.vo.UserRoleVo;

public interface UserRoleRepositoryCustom {
	Integer countByCriteria(UserRoleFormVo request);

	List<UserRoleVo> findByCriteria(UserRoleFormVo request);

	List<UserRoleVo> findById(UserRoleFormVo request);
}
