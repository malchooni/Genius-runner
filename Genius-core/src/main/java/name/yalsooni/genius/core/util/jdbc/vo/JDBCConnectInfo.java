package name.yalsooni.genius.core.util.jdbc.vo;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Map;

/**
 * JDBC 연결 정보를 담는 VO.
 * @author ijyoon
 *
 */
public class JDBCConnectInfo {

	private Connection connection;
		
	private String driverName;
	private String jdbcURL;
	private String userName;
	private String password;

	/**
	 * 커네셕 객체를 반환한다.
	 * @return
	 * @throws SQLException
	 */
	public Connection getConnection() throws SQLException {
		if(connection == null || connection.isClosed()){
			connection = DriverManager.getConnection(this.jdbcURL, this.userName, this.password);
		}
		return connection;
	}
	
	/**
	 * 커네션 객체를 삽입한다.
	 * @param connection
	 */
	public void setConnection(Connection connection) {
		this.connection = connection;
	}
	
	/**
	 * 드라이버 클래스 이름을 반환한다.
	 * @return
	 */
	public String getDriverName() {
		return driverName;
	}
	
	/**
	 * 드라이버 클래스 이름을 삽입한다.
	 * @param driverName
	 * @throws ClassNotFoundException
	 */
	public void setDriverName(String driverName) throws ClassNotFoundException {
		if(driverName != null){
			this.driverName = driverName;
			Class.forName(this.driverName);
		}
	}
	
	/**
	 * JDBC URL 정보를 반환한다.
	 * @return
	 */
	public String getJdbcURL() {
		return jdbcURL;
	}
	
	/**
	 * JDBC URL 정보를 삽입한다.
	 * @param jdbcURL
	 */
	public void setJdbcURL(String jdbcURL) {
		this.jdbcURL = jdbcURL;
	}
	
	/**
	 * 유저이름을 반환한다.
	 * @return
	 */
	public String getUserName() {
		return userName;
	}
	
	/**
	 * 유저이름을 삽입한다.
	 * @param userName
	 */
	public void setUserName(String userName) {
		this.userName = userName;
	}
	
	/**
	 * 패스워드를 반환한다.
	 * @return
	 */
	public String getPassword() {
		return password;
	}
	
	/**
	 * 패스워드를 삽입한다.
	 * @param password
	 */
	public void setPassword(String password) {
		this.password = password;
	}
	
	/**
	 * 오토커밋 설정
	 * @param autoCommit
	 * @throws SQLException
	 */
	public void setAutoCommit(Boolean autoCommit) throws SQLException{
		
		if(connection == null){
			this.getConnection();
		}
		
		if(!connection.isClosed()){
			connection.setAutoCommit(autoCommit);
		}
	}
	
	/**
	 * 해당 트랙잭션을 커밋한다.
	 * @throws SQLException
	 */
	public void commit() throws SQLException{
		if(connection != null || !connection.isClosed()){
			connection.commit();
		}
	}
	
	/**
	 * 해당 트랜잭션을 롤백 한다. 
	 * @throws SQLException
	 */
	public void rollback() throws SQLException{
		if(connection != null || !connection.isClosed()){
			connection.rollback();
		}
	}
	
	/**
	 * 디비 커네션을  닫는다.
	 */
	public void close(){
		try {
			if(this.connection != null){
				this.connection.close();
			}
		} catch (SQLException e) {}
	}
}
