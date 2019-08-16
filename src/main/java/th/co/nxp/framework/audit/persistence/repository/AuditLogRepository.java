package th.co.nxp.framework.audit.persistence.repository;

import th.co.nxp.framework.audit.persistence.entity.AuditLog;
import th.co.nxp.framework.common.persistence.repository.CommonJpaCrudRepository;

public interface AuditLogRepository extends CommonJpaCrudRepository<AuditLog, Long> {

}
