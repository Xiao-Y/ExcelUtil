package com.xiaoy.crud;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.xiaoy.db.MySQLConnection;
import com.xiaoy.model.ZipModel;

/**
 * 全国省市的操作
 * 
 * @author XiaoY
 * @date: 2015年9月5日 下午7:08:39
 */
public class ZipCRUD {

	/**
	 * 插入数据
	 * 
	 * @param zips
	 * @throws SQLException
	 */
	public void saveListZip(List<ZipModel> zips) throws SQLException {
		MySQLConnection mc = new MySQLConnection();
		Connection connection = null;
		PreparedStatement ps = null;
		String sql = "insert into zip(id,name,parent_Id,short_name,level_Type,city_code,zip_code,merger_name,lng,lat,pinyin) values (?,?,?,?,?,?,?,?,?,?,?)";
		try {
			connection = mc.getConnection();
			ps = connection.prepareStatement(sql);
			System.out.println("begin insert into...");
			for (ZipModel z : zips) {
				ps.setString(1, z.getId());
				ps.setString(2, z.getName());
				ps.setString(3, z.getParentId());
				ps.setString(4, z.getShortName());
				ps.setString(5, z.getLevelType());
				ps.setString(6, z.getCityCode());
				ps.setString(7, z.getZipCode());
				ps.setString(8, z.getMergerName());
				ps.setString(9, z.getLng());
				ps.setString(10, z.getLat());
				ps.setString(11, z.getPinyin());
				ps.addBatch();
			}
			ps.executeBatch();
			System.out.println("end insert into...");
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (ps != null) {
				ps.close();
			}
			if (connection != null) {
				connection.close();
			}
		}
	}

	/**
	 * 查询所有的数据
	 * 
	 * @return
	 * @throws SQLException
	 */
	public List<ZipModel> getZipList() {
		MySQLConnection mc = new MySQLConnection();
		Connection connection = null;
		PreparedStatement ps = null;
		String sql = "select id,name,parent_Id,short_name,level_Type,city_code,zip_code,merger_name,lng,lat,pinyin from zip";
		try {
			connection = mc.getConnection();
			ps = connection.prepareStatement(sql);
			ResultSet rs = ps.executeQuery();
			List<ZipModel> list = new ArrayList<ZipModel>();
			while (rs.next()) {
				ZipModel z = new ZipModel();
				z.setId(rs.getString(1));
				z.setName(rs.getString(2));
				z.setParentId(rs.getString(3));
				z.setShortName(rs.getString(4));
				z.setLevelType(rs.getString(5));
				z.setCityCode(rs.getString(6));
				z.setZipCode(rs.getString(7));
				z.setMergerName(rs.getString(8));
				z.setLng(rs.getString(9));
				z.setLat(rs.getString(10));
				z.setPinyin(rs.getString(11));

				list.add(z);
			}
			return list;
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (ps != null) {
				try {
					ps.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
			if (connection != null) {
				try {
					connection.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}
		return null;
	}
}
