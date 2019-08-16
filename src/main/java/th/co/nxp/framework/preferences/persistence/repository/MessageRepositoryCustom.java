package th.co.nxp.framework.preferences.persistence.repository;

import java.util.List;

import org.springframework.data.domain.Pageable;

import th.co.nxp.framework.preferences.persistence.entity.Message;
import th.co.nxp.framework.preferences.vo.MessageCriteriaVo;

public interface MessageRepositoryCustom {
	
	public Integer countByCriteria(MessageCriteriaVo criteria);
	
	public List<Message> findByCriteria(MessageCriteriaVo criteria, Pageable pageable);
	
}
