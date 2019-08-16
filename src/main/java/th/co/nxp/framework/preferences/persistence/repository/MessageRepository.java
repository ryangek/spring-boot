package th.co.nxp.framework.preferences.persistence.repository;

import th.co.nxp.framework.common.persistence.repository.CommonJpaPagingAndSortingRepository;
import th.co.nxp.framework.preferences.persistence.entity.Message;

public interface MessageRepository extends CommonJpaPagingAndSortingRepository<Message, Long>, MessageRepositoryCustom {

}
