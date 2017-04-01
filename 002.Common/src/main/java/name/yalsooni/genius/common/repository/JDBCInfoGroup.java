package name.yalsooni.genius.common.repository;

import genius.rulebreaker.util.jdbc.vo.JDBCConnectInfo;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * todo: 사용여부 판단 중..
 */
public class JDBCInfoGroup {

    private Map<String, JDBCConnectInfo> map;

    public JDBCInfoGroup() {
        map = new HashMap<String, JDBCConnectInfo>();
    }

    public void setJdbcInfo(String key, JDBCConnectInfo info) {
        this.map.put(key, info);
    }

    public void getJdbcInfo(String key) {
        this.map.get(key);
    }
    
    public int size(){
    	return this.map.size();
    }
    
    public void close(){
    	
    	Iterator<String> key = map.keySet().iterator();
    	JDBCConnectInfo info = null;
    	
    	while(key.hasNext()){
    		info = map.get(key);
    		if(info != null){
    			info.close();
    		}
    	}
    }
}
