package th.co.nxp.framework.accesscontrol.vo;

import th.co.nxp.framework.common.bean.DataTableRequest;

public class OperationFormVo extends DataTableRequest {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 6947879063136050077L;
	private String operationCode;
	private String operationDesc;
	
	public String getOperationCode() {
		return operationCode;
	}
	public void setOperationCode(String operationCode) {
		this.operationCode = operationCode;
	}
	public String getOperationDesc() {
		return operationDesc;
	}
	public void setOperationDesc(String operationDesc) {
		this.operationDesc = operationDesc;
	}
	
}
