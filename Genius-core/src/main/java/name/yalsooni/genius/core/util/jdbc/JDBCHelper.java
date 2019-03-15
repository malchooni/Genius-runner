package name.yalsooni.genius.core.util.jdbc;



import name.yalsooni.genius.core.util.jdbc.vo.JDBCConnectInfo;

import java.sql.*;
import java.util.*;

/**
 * JDBC 관련 유틸
 * @author ijyoon
 *
 */
public class JDBCHelper {
	
	/**
	 * 파라미터 SQL로 쿼리를 조회하여 결과 값을 반환한다.
	 * 오직 하나의 결과 값만 반환한다. 
	 * 
	 * @param jdbcInfo
	 * @param sql
	 * @param parameters
	 * @return
	 * @throws SQLException
	 */
	public String getResultOnlyOneValueReturn(JDBCConnectInfo jdbcInfo, String sql, String[] parameters) throws SQLException{
		
		Connection connection = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		String reuslt = null;
		
		try {
			connection = jdbcInfo.getConnection();			
			ps = connection.prepareStatement(sql);
			
			for(int i=0; parameters != null && i < parameters.length; i++){
				ps.setString((i+1), parameters[i]);
			}
			
			rs = ps.executeQuery();
			
			if(rs.next()){
				reuslt = rs.getString(1);
			}
			
		} catch (SQLException e) {
			throw e;
		} finally {
			if(rs != null) try {rs.close();} catch(SQLException e){}
			if(ps != null) try {ps.close();} catch(SQLException e){}
		}
		
		return reuslt;
	}
	
	public List<String> getResultList(JDBCConnectInfo jdbcInfo, String sql, String[] parameters) throws Exception {
		
		
		Connection connection = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		List<String> reuslt = new ArrayList<String>();
		
		try {
			connection = jdbcInfo.getConnection();			
			ps = connection.prepareStatement(sql);
			
			for(int i=0; parameters != null && i < parameters.length; i++){
				ps.setString((i+1), parameters[i]);
			}
			
			rs = ps.executeQuery();
			
			while(rs.next()){
				reuslt.add(rs.getString(1));
			}
			
		} catch (SQLException e) {
			throw e;
		} catch (NullPointerException e){
			throw e;
		} finally {
			if(rs != null) try {rs.close();} catch(SQLException e){}
			if(ps != null) try {ps.close();} catch(SQLException e){}
		}
		
		return reuslt;
	}
	
	public List<Map<String, String>> getResultMap(JDBCConnectInfo jdbcInfo, String sql, String[] parameters) throws SQLException{
		
		Connection connection = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		List<Map<String, String>> reuslt = new ArrayList<Map<String, String>>();
		
		try {
			connection = jdbcInfo.getConnection();			
			ps = connection.prepareStatement(sql);
			
			for(int i=0; parameters != null && i < parameters.length; i++){
				ps.setString((i+1), parameters[i]);
			}
			
			rs = ps.executeQuery();
			
			while(rs.next()){
				
				ResultSetMetaData rsd = rs.getMetaData();
				Map<String, String> row = new HashMap<String, String>();
				
				for(int i=1; i<= rsd.getColumnCount(); i++){
					row.put(rsd.getColumnName(i), rs.getString(i));
				}
				
				reuslt.add(row);
			}
			
		} catch (SQLException e) {
			throw e;
		} finally {
			if(rs != null) try {rs.close();} catch(SQLException e){}
			if(ps != null) try {ps.close();} catch(SQLException e){}
		}
		
		return reuslt;
	}
	
	/**
	 * Insert, Update, Delete 쿼리문을 실행하여 그 결과값을 반환한다. 
	 * @param jdbcInfo
	 * @param sql
	 * @param parameters
	 * @return
	 * @throws SQLException
	 */
	public int executeUpdate(JDBCConnectInfo jdbcInfo, String sql, String[] parameters) throws SQLException{
		
		Connection connection = null;
		PreparedStatement ps = null;
		
		int result = 0;
		
		try {
			connection = jdbcInfo.getConnection();
			ps = connection.prepareStatement(sql);
			
			for(int i=0;  parameters != null && i < parameters.length; i++){
				ps.setString((i+1), parameters[i]);
			}
			
			result = ps.executeUpdate();
			
		} catch (SQLException e) {
			throw e;
		} finally {
			if(ps != null) try {ps.close();} catch(SQLException e){}
		}
		
		return result;
	}
	
	/**
	 * 테이블의 컬럼정보를 반환한다.
	 * @param conn
	 * @param tableName
	 * @return
	 * @throws SQLException
	 */
	public Map<String, Map<String,String>> getColumnInfo(Connection conn, String tableName) throws SQLException{
		
		DatabaseMetaData data = conn.getMetaData();
		ResultSet rs = data.getColumns(null, null, tableName, "%");
		
		Map<String, Map<String,String>> result = new LinkedHashMap<String, Map<String,String>>();
				
		String columnName = null;
		
		while(rs.next()){
			Map<String,String> columnInfo = new HashMap<String, String>();
			columnName = rs.getString(4);
			
			columnInfo.put("columnType", rs.getString(6));
			columnInfo.put("columnSize", String.valueOf(rs.getInt(7)));
			columnInfo.put("columnNullable", rs.getInt(11) == DatabaseMetaData.columnNoNulls ? "NotNull" : "Null"  );
			
			result.put(columnName, columnInfo);
		}
		
		return result;
	}
	
}
