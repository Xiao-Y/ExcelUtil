package com.xiaoy.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.xiaoy.test.TestLoadExcel;

@WebServlet("/LoadExecl")
public class LoadExecl extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public LoadExecl() {
		super();
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		this.doPost(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException,
			IOException {
		TestLoadExcel t = new TestLoadExcel();
		t.loadExcel(request, response);
	}

}
