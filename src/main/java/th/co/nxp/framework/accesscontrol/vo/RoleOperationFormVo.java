package th.co.nxp.framework.accesscontrol.vo;

import th.co.nxp.framework.common.bean.DataTableRequest;

public class RoleOperationFormVo extends DataTableRequest {
	/**
	 * 
	 */
	private static final long serialVersionUID = -6884853110297388133L;
	private Long roleId;

	public Long getRoleId() {
		return roleId;
	}

	public void setRoleId(Long roleId) {
		this.roleId = roleId;
	}
}
