package com.zhang.daoImpl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import com.zhang.domain.Student;
import com.zhang.util.DBUtil;

public class FindStudent {
	
	public List<Student> findAll() {
		List<Student> students = new ArrayList<>();
		
		Connection con = null;
		PreparedStatement stat = null;
		ResultSet rs = null;
		
		try {
			con = DBUtil.getConnection();
			//String sql = "select * from student group by grade;";
			StringBuilder sql = new StringBuilder();
			sql.append("SELECT a.grade, man, woman FROM ");
			sql.append("(select grade, count(*) man from student ");
			sql.append("where sex='男' group by grade) ");
			sql.append("as a LEFT JOIN ");
			sql.append("(select grade, count(*) woman from student ");
			sql.append("where sex='女' group by grade) ");
			sql.append("as b ON a.grade=b.grade;");
			String ssql = sql.toString();
			
			stat = con.prepareStatement(ssql);
			stat.executeQuery();
			
			rs = stat.getResultSet();
			while (rs.next()) {
				Student student = new Student();
				student.setGrade(rs.getString("grade"));
//				student.setClas(rs.getString("class"));
//				student.setName(rs.getString("name"));
//				student.setSex(rs.getString("sex"));
				student.setMan(Integer.parseInt(rs.getString("man")));
				student.setWoman(Integer.parseInt(rs.getString("woman")));
				
				students.add(student);
			}
			
		} catch(Exception e) {
			throw new RuntimeException();
		} finally {
			DBUtil.release(con, stat, rs);
		}
		
		return students;
	}
	
	public List<Student> findByGrade(String grade) {
		List<Student> students = new ArrayList<Student>();
		
		Connection con = null;
		PreparedStatement stat = null;
		ResultSet rs = null;
		
		try {
			con = DBUtil.getConnection();
			//String sql = "select * from student group by grade;";
			StringBuilder sql = new StringBuilder();
			sql.append("SELECT a.class, man, woman FROM ");
			sql.append("(select class, count(*) man from student ");
			sql.append("where sex='男' and grade = ? group by class) ");
			sql.append("as a LEFT JOIN ");
			sql.append("(select class, count(*) woman from student ");
			sql.append("where sex='女' and grade = ? group by class) ");
			sql.append("as b ON a.class=b.class;");
			String ssql = sql.toString();
			
			stat = con.prepareStatement(ssql);
			stat.setString(1, grade);
			stat.setString(2, grade);
			stat.executeQuery();
			
			rs = stat.getResultSet();
			while (rs.next()) {
				Student student = new Student();
//				student.setGrade(rs.getString("grade"));
				student.setClas(rs.getString("class"));
//				student.setName(rs.getString("name"));
//				student.setSex(rs.getString("sex"));
				student.setMan(Integer.parseInt(rs.getString("man") == null ? "0" : rs.getString("man")));
				student.setWoman(Integer.parseInt(rs.getString("woman") == null ? "0" : rs.getString("man")));
				
				students.add(student);
			}
			
		} catch(Exception e) {
			throw new RuntimeException();
		} finally {
			DBUtil.release(con, stat, rs);
		}
		
		return students;
	}

}
