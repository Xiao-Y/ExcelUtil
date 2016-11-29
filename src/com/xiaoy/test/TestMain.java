package com.xiaoy.test;


import java.sql.SQLException;
import java.util.List;

import com.xiaoy.crud.Crud;
import com.xiaoy.model.GuProposalMain;

public class TestMain {

	public static void main(String[] args) {
		long start = System.currentTimeMillis();
		Crud c = new Crud();
		try {
			List<GuProposalMain> guProposalMainList = c.getGuProposalMainList();
			System.out.println(guProposalMainList.size());
		} catch (SQLException e) {
			e.printStackTrace();
		}
		long end = System.currentTimeMillis();
		System.out.println("运行：" + (end - start) + "ms");
	}
}
