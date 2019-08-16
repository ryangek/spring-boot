package th.co.nxp.framework.accesscontrol.persistence.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import th.co.nxp.framework.accesscontrol.persistence.entity.Operation;
import th.co.nxp.framework.common.constant.CommonConstants.FLAG;
import th.co.nxp.framework.common.persistence.repository.CommonJpaCrudRepository;

public interface OperationRepository extends CommonJpaCrudRepository<Operation, Long>, OperationRepositoryCustom{
	
	@Query("select e from #{#entityName} e where e.isDeleted = '" + FLAG.N_FLAG + "' and e.operationCode = :operationCode")
	public Operation findByOperationCode(@Param("operationCode") String operationCode);
	
}
