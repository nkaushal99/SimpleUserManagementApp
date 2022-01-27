package com.nikhil.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.nikhil.model.User;

//for database operations
public class UserDB {

	private String jdbcURL = "jdbc:mysql://localhost:3306/demo";
	private String jdbcUsername = "root";
	private String jdbcPassword = "root/mysql";
	
	//prepared statements
	private static final String INSERT_USERS_SQL = "INSERT INTO users"+" (username, email, country) VALUES "
													+ "(?, ?, ?);";
	private static final String SELECT_USER_BY_ID = "SELECT id, username, email, country"
													+ " FROM users WHERE id = ?;";
	private static final String SELECT_ALL_USERS = "SELECT * FROM users;";
	private static final String DELETE_USERS_SQL = "DELETE FROM users WHERE id = ?;";
	private static final String UPDATE_USERS_SQL = "UPDATE users set username = ?,"
																	+" email = ?,"
																	+" country = ?"
																	+" WHERE id = ?;";
	
	
	protected Connection getConnection() {
		Connection connection = null;
		try {
			DriverManager.registerDriver(new com.mysql.cj.jdbc.Driver());
//			Class.forName("com.mysql.cj.jdbc.Driver");
			connection = DriverManager.getConnection(jdbcURL, jdbcUsername, jdbcPassword);
		} catch(SQLException e) {
			e.printStackTrace();
		}
		return connection;
	}
	
	//insert user
	public void insertUser(User user) throws SQLException{
		try(Connection connection = getConnection();
				PreparedStatement statement = connection.prepareStatement(INSERT_USERS_SQL);){
			statement.setString(1, user.getUsername());
			statement.setString(2, user.getEmail());
			statement.setString(3, user.getCountry());
			
			statement.executeUpdate();
		}catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	//update user
	public boolean updateUser(User user) throws SQLException{
		boolean rowUpdated=false;
		try(Connection connection = getConnection();
				PreparedStatement statement = connection.prepareStatement(UPDATE_USERS_SQL);){
			statement.setString(1, user.getUsername());
			statement.setString(2, user.getEmail());
			statement.setString(3, user.getCountry());
			statement.setInt(4, user.getId());
			
			rowUpdated = statement.executeUpdate()>0;
		}catch(Exception e) {
			e.printStackTrace();
		}
		return rowUpdated;
	}
	
	//select user by id
	public User selectUser(int id) throws SQLException{
		User user = null;
		try(Connection connection = getConnection();
				PreparedStatement statement = connection.prepareStatement(SELECT_USER_BY_ID);){
			statement.setInt(1, id);
			System.out.println(statement.toString());
			
			ResultSet rs = statement.executeQuery();
			System.out.println(1223);
			while(rs.next()) {
				String username = rs.getString("username");
				String email = rs.getString("email");
				String country = rs.getString("country");
				user = new User(id, username, email, country);
			}
		}catch(Exception e) {
			e.printStackTrace();
		}
		System.out.println(100000);
		return user;
	}
		
		//select all users
		public List<User> selectALLUsers() throws SQLException{
			List<User> users = new ArrayList<>();
			try(Connection connection = getConnection();
					PreparedStatement statement = connection.prepareStatement(SELECT_ALL_USERS);){
				
				ResultSet rs = statement.executeQuery();
				while(rs.next()) {
					int id = rs.getInt("id");
					String username = rs.getString("username");
					String email = rs.getString("email");
					String country = rs.getString("country");
					users.add(new User(id, username, email, country));
				}
			}catch(Exception e) {
					e.printStackTrace();
			}
			return users;
		}
				
		//delete user
		public boolean deleteUser(int id) throws SQLException{
			boolean rowDeleted = false;
			try(Connection connection = getConnection();
					PreparedStatement statement = connection.prepareStatement(DELETE_USERS_SQL);){
				statement.setInt(1, id);
				
				rowDeleted = statement.executeUpdate()>0;
			}catch(Exception e) {
				e.printStackTrace();
			}
			return rowDeleted;
		}
}
