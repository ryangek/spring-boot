package th.co.nxp.framework.accesscontrol.persistence.repository;

import java.util.List;

import th.co.nxp.framework.accesscontrol.vo.RoleOperationFormVo;
import th.co.nxp.framework.accesscontrol.vo.RoleOperationVo;

public interface RoleOperationRepositoryCustom {

	Integer countByCriteria(RoleOperationFormVo request);

	List<RoleOperationVo> findByCriteria(RoleOperationFormVo request);

	List<RoleOperationVo> findById(RoleOperationFormVo request);

}
