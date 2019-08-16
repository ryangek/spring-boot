package th.co.nxp.framework.preferences.persistence.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import th.co.nxp.framework.common.constant.CommonConstants.FLAG;
import th.co.nxp.framework.common.persistence.repository.CommonJpaPagingAndSortingRepository;
import th.co.nxp.framework.preferences.persistence.entity.ParameterInfo;

public interface ParameterInfoRepository extends CommonJpaPagingAndSortingRepository<ParameterInfo, Long> {
	
	@Query("select e from #{#entityName} e where e.isDeleted = '" + FLAG.N_FLAG + "' and e.paramGroupCode = :paramGroupCode order by e.sortingOrder")
	public List<ParameterInfo> findByParamGroupCode(@Param("paramGroupCode") String paramGroupCode);
	
	ParameterInfo findByParamGroupCodeAndParamCode(String paramGroupCode, String paramCode);
}
