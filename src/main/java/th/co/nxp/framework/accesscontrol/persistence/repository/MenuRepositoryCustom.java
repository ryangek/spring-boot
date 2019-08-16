package th.co.nxp.framework.accesscontrol.persistence.repository;

import java.util.List;

import th.co.nxp.framework.accesscontrol.vo.MenuVo;

public interface MenuRepositoryCustom {

	//public List<MenuVo> listMenu();
	
	public List<MenuVo> findByRolesAndSubdeptLevel(List<String> roleList, String subdeptLevel);

}
