package com.zhang.test;

import static org.junit.Assert.*;

import java.util.List;

import org.junit.Test;

import com.zhang.daoImpl.FindStudent;
import com.zhang.domain.Student;

public class UnitTest {

	@Test
	public void test() {
		FindStudent find = new FindStudent();
//		List<Student> list = find.findAll();
		List<Student> list = find.findByGrade("五年级");
		System.out.println(list);
	}

}
