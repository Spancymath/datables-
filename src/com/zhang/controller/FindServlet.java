package com.zhang.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.zhang.daoImpl.FindStudent;
import com.zhang.domain.Student;

/**
 * Servlet implementation class FindServlet
 */
@WebServlet("/FindServlet")
public class FindServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    public FindServlet() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		FindStudent fs = new FindStudent();

		request.setCharacterEncoding("utf-8");
		String grade = request.getParameter("pdataId");
		List<Student> list = new ArrayList<Student>();
		JSONArray jarray = new JSONArray();
		if (grade != null) {
			list = fs.findByGrade(grade);
			for (Student s : list) {
				JSONObject jo = new JSONObject();
				try {
				jo.put("class", s.getClas());
				jo.put("man", String.valueOf(s.getMan()));
				jo.put("woman", String.valueOf(s.getWoman()));
				jarray.put(jo);
				} catch (JSONException e) {
					e.printStackTrace();
				}
			}
		} else {
			list = fs.findAll();
		
			for (Student s : list) {
				JSONObject jo = new JSONObject();
				try {
				jo.put("grade", s.getGrade());
				jo.put("man", String.valueOf(s.getMan()));
				jo.put("woman", String.valueOf(s.getWoman()));
				jarray.put(jo);
				} catch (JSONException e) {
					e.printStackTrace();
				}
			}
		}
		
		response.setCharacterEncoding("UTF-8");
		response.getWriter().write(jarray.toString());
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
