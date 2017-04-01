package name.yalsooni.genius.common.megaware.vo;

import genius.rulebreaker.definition.ErrCode;

import java.util.ArrayList;
import java.util.List;

/**
 * 메가웨어 디자인트리 관련 VO
 * todo : Common 프로젝트와는 성격이 다르다고 판단하여 타 프로젝트로 분리 방안 판단 중.
 */
public class TreeInfo {

	public final String ROUTE_DT = "RouteDesignTree";
	public final String MEDIATE_DT = "MediateDesignTree";
	public final String ORCHESTRATE_DT = "OrchestrateDesignTree";
	public final String TRANSFORM_DT = "TransformDesignTree";
	
	public final String ROUTE_ST = "RouteServiceTree";
	public final String MEDIATE_ST = "MedidateServiceTree";
	public final String ORCHESTRATE_ST = "OrchestrateServiceTree";
	public final String TRANSFORM_ST = "TransformServiceTree";
	
	private List<String> dTargetContainerList = new ArrayList<String>();
	private List<String> sTargetContainerList = new ArrayList<String>();
	
	private String userID;
	private String serviceGroup;
	private String containerName;

	/**
	 * 상관성이 없음 제거 예정.
	 */
	private String geniusPropertyPath;

	@Deprecated
	public String getGeniusPropertyPath() {
		return geniusPropertyPath;
	}

	@Deprecated
	public void setGeniusPropertyPath(String geniusPropertyPath) {
		this.geniusPropertyPath = geniusPropertyPath;
	}
	
	public List<String> getDTargetContainerList() {
		return dTargetContainerList;
	}
	
	public List<String> getSTargetContainerList() {
		return sTargetContainerList;
	}
	
	public void setDTargetContainerName(String containerName) throws IllegalArgumentException{
		
		containerName = containerName.toUpperCase();
		
		String[] target = containerName.split(",");
		
		for(int i=0; i < target.length; i++){
			if(target[i].equals("MEDIATE")){
				dTargetContainerList.add(this.MEDIATE_DT);
			}else if(target[i].equals("ROUTE")){
				dTargetContainerList.add(this.ROUTE_DT);
			}else if(target[i].equals("ORCHESTRATE")){
				dTargetContainerList.add(this.ORCHESTRATE_DT);
			}else if(target[i].equals("TRANSFORM")){
				dTargetContainerList.add(this.TRANSFORM_DT);
			}else{
				throw new IllegalArgumentException(ErrCode.G_002_0003 + " input : "+ containerName);
			}
		}
	}
	
	public void setSTargetContainerName(String containerName) throws IllegalArgumentException{
		
		if(containerName == null) return;
		
		containerName = containerName.toUpperCase();
		
		String[] target = containerName.split(",");
		
		for(int i=0; i < target.length; i++){
			if(target[i].equals("MEDIATE")){
				sTargetContainerList.add(this.MEDIATE_ST);
			}else if(target[i].equals("ROUTE")){
				sTargetContainerList.add(this.ROUTE_ST);
			}else if(target[i].equals("ORCHESTRATE")){
				sTargetContainerList.add(this.ORCHESTRATE_ST);
			}else if(target[i].equals("TRANSFORM")){
				sTargetContainerList.add(this.TRANSFORM_ST);
			}else{
				throw new IllegalArgumentException(ErrCode.G_002_0003 + " input : "+ containerName);
			}
		}
	}
	
	public String getUserID() {
		return userID;
	}
	
	public void setUserID(String userID) throws NullPointerException{
		if(userID == null){
			throw new NullPointerException(ErrCode.G_002_0004);
		}
		this.userID = userID;
	}

	public String getServiceGroup(String type) throws IllegalArgumentException {
		
		String prefix = "";
		
		if(type.equals(this.MEDIATE_ST)){
			prefix = "Mediate-"; 
		}else if(type.equals(this.ROUTE_ST)){
			prefix = "Route-";
		}else if(type.equals(this.ORCHESTRATE_ST)){
			prefix = "Orchestrate-";
		}else if(type.equals(this.TRANSFORM_ST)){
			prefix = "Transform-";
		}else{
			throw new IllegalArgumentException(ErrCode.G_002_0003); 
		}

		return prefix + getServiceGroup();
	}
	
	public String getServiceGroup() {
		return serviceGroup;
	}

	public void setServiceGroup(String serviceGroup) {
		if(serviceGroup == null){
			throw new NullPointerException(ErrCode.G_002_0005);
		}
		this.serviceGroup = serviceGroup;
	}
	
	public String getContainerName() {
		return containerName;
	}
	
	public void setDesignTreeContainerName(String containerName) {
		
		containerName = containerName.toUpperCase();
		
		if(containerName.equals("MEDIATE")){
			this.containerName = this.MEDIATE_DT;
		}else if(containerName.equals("ROUTE")){
			this.containerName = this.ROUTE_DT;
		}else if(containerName.equals("ORCHESTRATE")){
			this.containerName = this.ORCHESTRATE_DT;
		}else if(containerName.equals("TRANSFORM")){
			this.containerName = this.TRANSFORM_DT;
		}else{
			throw new IllegalArgumentException(ErrCode.G_002_0003 + " input : "+ containerName);
		}
		
	}

}
