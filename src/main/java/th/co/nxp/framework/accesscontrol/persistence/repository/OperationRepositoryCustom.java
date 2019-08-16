package th.co.nxp.framework.accesscontrol.persistence.repository;

import java.util.List;

import th.co.nxp.framework.accesscontrol.persistence.entity.Operation;
import th.co.nxp.framework.accesscontrol.vo.OperationFormVo;

public interface OperationRepositoryCustom {
	
	
	
	public Integer countByCriteria(OperationFormVo operationFormVo);
	
	public List<Operation>  findByCriteria(OperationFormVo operationFormVo);

	
}
