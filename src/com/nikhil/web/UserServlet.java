package com.nikhil.web;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.nikhil.db.UserDB;
import com.nikhil.model.User;


@WebServlet("/")
public class UserServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private UserDB userDB;
	
	public UserServlet() {
		this.userDB = new UserDB();
	}
	
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		
		String action = request.getServletPath();
		
		try {
			switch (action) {
				case "/new":
					showNewForm(request, response);
					break;
				case "/insert":
					insertUser(request, response);
					break;
				case "/delete":
					deleteUser(request, response);
					break;
				case "/edit":
					showEditForm(request, response);
					break;
				case "/update":
					updateUser(request, response);
					break;
				default:
					listUser(request, response);
					break;
				}
			} catch (SQLException ex) {
				throw new ServletException(ex);
			}
	}
	
	private void listUser(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException, SQLException{
		
		List<User> listUser = userDB.selectALLUsers();
		request.setAttribute("listUser", listUser);
		RequestDispatcher rd = request.getRequestDispatcher("user-list.jsp");
		rd.forward(request, response);
	}
		
	
	private void updateUser(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException, SQLException{
		
		int id = Integer.parseInt(request.getParameter("id"));
		String username = request.getParameter("username");
		String email = request.getParameter("email");
		String country = request.getParameter("country");
		
		User user = new User(id, username, email, country);
		userDB.updateUser(user);
		response.sendRedirect("list");
	}
	
	private void showEditForm(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException, SQLException{
				
		int id = Integer.parseInt(request.getParameter("id"));
		User user = userDB.selectUser(id);
		
		request.setAttribute("user", user);
		RequestDispatcher rd = request.getRequestDispatcher("user-form.jsp");
		rd.forward(request, response);
	}

	
	private void deleteUser(HttpServletRequest request, HttpServletResponse response)
		throws ServletException, IOException, SQLException{
			
		int id = Integer.parseInt(request.getParameter("id"));
		userDB.deleteUser(id);	
		response.sendRedirect("list");
	}

	private void showNewForm(HttpServletRequest request, HttpServletResponse response)
		throws ServletException, IOException{
		
		RequestDispatcher rd = request.getRequestDispatcher("user-form.jsp");
		rd.forward(request, response);
	}
	
	private void insertUser(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException, SQLException{
			
		String username = request.getParameter("username");
		String email = request.getParameter("email");
		String country = request.getParameter("country");
		
		User user = new User(username, email, country);
		userDB.insertUser(user);
		response.sendRedirect("list");
	}
	
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		
		doGet(request, response);
	}

}
