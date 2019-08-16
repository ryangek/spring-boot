package th.co.nxp.framework.accesscontrol.persistence.repository;

import java.util.List;

import th.co.nxp.framework.accesscontrol.persistence.entity.User;
import th.co.nxp.framework.accesscontrol.vo.UserFormVo;

public interface UserRepositoryCustom {

	public Integer countByCriteria(UserFormVo userFormVo);

	public List<User> findByCriteria(UserFormVo userFormVo);

}
