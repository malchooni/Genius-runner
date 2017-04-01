package name.yalsooni.genius.common.megaware.designtree.support;

/**
 * 메가웨어 트자인 트리 관련 SQL
 * todo : Common 프로젝트와는 성격이 다르다고 판단하여 타 프로젝트로 분리 방안 판단 중.
 * @author yoon-iljoong
 */
public class DesignTreeSQL {

	/**
	 * user id uuid get.
	 * 1: user id
	 */
	public final String USERID_UUID_SQL 	= "SELECT META_ID FROM USER_ WHERE ID = ?";
	
	/**
	 * user name uuid get.
	 * 1: user name
	 */
	public final String USERNAMEUUID_SQL 	= "SELECT ID FROM KEY_ WHERE VALUE = ? AND PID = (SELECT ID FROM KEY_ WHERE TYPE = (SELECT ID FROM KEY_ WHERE VALUE = 'Key') AND VALUE = 'User')";
	
	
	/**
	 * DesignTree Value uuid get.
	 * 1: user uuid
	 * 2: container name
	 */
	public final String DESIGNTREE_VALUEUUID_SQL 	= "SELECT VALUE_ID FROM KEY_ WHERE PID = ? AND KEY_ID = (SELECT ID FROM KEY_ WHERE VALUE = ?)";
	
	/**
	 * ServiceTree Value uuid get.
	 * 1: container name - service group
	 *  ex ) Mediate-test
	 * 2: fixed word. MedidateServiceTree
	 *  ex ) MedidateServiceTree
	 */
	public final String SERVICETREE_VALUEUUID_SQL ="SELECT ID FROM KEY_ WHERE VALUE = ? AND PID = (SELECT ID FROM KEY_ WHERE VALUE = ?)";
	
	public final String DESIGN_TREE_UPDATE 	= "UPDATE FILE_ SET ID = ? WHERE ID = ?";
	public final String DESIGN_TREE_INSERT 	= "INSERT INTO FILE_ (ID, LOB_DATA) VALUES(?,?)";
	public final String DESIGN_INSERT 		= "INSERT INTO FILE_ (ID, LOB_DATA) VALUES(?,?)";
	
	/**
	 * DesignTree history remove.
	 * TABLE : FILE_
	 * 1: DesignTree uuid.
	 * 2: DesignTree uuid.
	 */
	@Deprecated
	public final String FILE_HISTORY_TREE_REMOVE = "DELETE FROM FILE_ WHERE LOB_DATA LIKE '%#TREEUUID#%' and id != '#TREEUUID#'";
	
	
	/**
	 * DesignTree history select.
	 * 1: DesignTree uuid.
	 * 2: DesignTree uuid.
	 */
	public final String FILE_TREE_HISTORY_SELECT = "SELECT * FROM FILE_ WHERE LOB_DATA LIKE '%#TREEUUID#%' and id != '#TREEUUID#'";
	
	/**
	 * DesignTree history remove.
	 * TABLE : FILE_ 
	 * 1: DesignTree history uuid.
	 */
	public final String FILE_TREE_REMOVE = "DELETE FROM FILE_ WHERE ID = ?";
	
	/**
	 * DesignTree history remove.
	 * TABLE : HISTORY_ 
	 * 1: DesignTree history uuid.
	 * 2: user uuid
	 */
	public final String HISTORY_TREE_REMOVE = "DELETE FROM HISTORY_ WHERE ID = ? AND USER_ID = ?";
	
	/**
	 * Diagram history select.
	 * TABLE : HIST_REF
	 * 1: DesignTree uuid.
	 */
	public final String DIAGRAM_HIST_REF_SELECT = "SELECT HIST_ID, REF_ID FROM HIST_REF WHERE TREE_ID = ?";
	
	/**
	 * Diagram history remove.
	 * TABLE : HIST_REF
	 * 1: DesignTree uuid.
	 */
	public final String DIAGRAM_HIST_REF_REMOVE = "DELETE FROM HIST_REF WHERE TREE_ID = ?";
	
	/**
	 * Diagram history_ remove.
	 * TABLE : HISTORY_
	 * 1: HIST_REF table HIST_ID uuid.
	 */
	public final String DIAGRAM_HISTORY_REMOVE = "DELETE FROM HISTORY_ WHERE ID = ?";
	
	/**
	 * Diagram file_ remove.
	 * TABLE : FILE_
	 * 1: FILE_ table REF_ID uuid.
	 */
	public final String DIAGRAM_FILE_REMOVE = "DELETE FROM FILE_ WHERE ID = ?";
	
}
