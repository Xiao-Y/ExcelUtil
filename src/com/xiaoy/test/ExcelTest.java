package com.xiaoy.test;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

import com.xiaoy.crud.ZipCRUD;
import com.xiaoy.model.ZipModel;
import com.xiaoy.util.DBExportToExcelFile;
import com.xiaoy.util.ExcelFileImportToDB;

/**
 * 测试类
 * 
 * @author XiaoY
 * @date: 2015年9月5日 下午10:06:26
 */
public class ExcelTest {

	/**
	 * 导出Excel报表
	 * 
	 * @return
	 * 
	 * @author XiaoY
	 * @date: 2014年12月30日 下午8:56:40
	 */
	public String export() {
		// 设置表头
		ArrayList<Object> fieldName = this.getExcelFieldNameList();
		// 装载要导出数据
		ArrayList<Object> fieldData = this.getExcelFieldDataList();
		try {
			// 获得输出流
			// OutputStream out = response.getOutputStream();
			OutputStream out = new FileOutputStream("d:\\workbook.xls");
			// 重置输出流
			// response.reset();
			// // 设置导出的格式
			// response.setContentType("appliction/vnd.ms-excel");
			// 设定输出文件头
			// response.setHeader("Content-disposition", "attachment; filename="+ new String(fileName.getBytes("GB2312"),"ISO8859-1"));
			DBExportToExcelFile excelFileGenerator = new DBExportToExcelFile(fieldName, fieldData);
			excelFileGenerator.expordExcel(out);
			// 设置输出形式
			// System.setOut(new PrintStream(out));
			// 刷新输出流
			out.flush();
			// 关闭输出流
			if (out != null) {
				out.close();
			}
			System.out.println("导出数据完成...");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 设置表头
	 * 
	 * @return
	 */
	private ArrayList<Object> getExcelFieldNameList() {
		String[] str = { "id", "Name", "ParentId", "ShortName", "LevelType", "CityCode", "ZipCode", "MergerName", "lng", "Lat", "Pinyin" };
		ArrayList<Object> list = new ArrayList<Object>();
		for (int i = 0; i < str.length; i++) {
			list.add(str[i]);
		}
		System.out.println("表头设置完成...");
		return list;
	}

	/**
	 * 装载要导出数据
	 * 
	 * @param elecUserForm
	 * @return
	 */
	public ArrayList<Object> getExcelFieldDataList() {
		ZipCRUD z = new ZipCRUD();
		List<ZipModel> zips = z.getZipList();
		ArrayList<Object> fieldData = null;
		if (zips != null && zips.size() > 0) {
			fieldData = new ArrayList<Object>();
			for (ZipModel zip : zips) {
				ArrayList<String> dataList = new ArrayList<String>();
				// id,name,parent_Id,short_name,level_Type,city_code,zip_code,merger_name,lng,lat,pinyin
				dataList.add(zip.getId());
				dataList.add(zip.getName());
				dataList.add(zip.getParentId());
				dataList.add(zip.getShortName());
				dataList.add(zip.getLevelType());
				dataList.add(zip.getCityCode());
				dataList.add(zip.getZipCode());
				dataList.add(zip.getMergerName());
				dataList.add(zip.getLng());
				dataList.add(zip.getLat());
				dataList.add(zip.getPinyin());

				fieldData.add(dataList);
			}
		}
		System.out.println("获取数据集完成...");
		return fieldData;
	}

	/**
	 * 导入Excel到数据库
	 */
	public void saveZipWithExcel() {
		File file = new File("d:\\2015.xls");
		try {
			// 获取excel中的数据集
			ArrayList<String[]> arragrnerate = ExcelFileImportToDB.parseExcelData(file);
			if (arragrnerate != null && arragrnerate.size() > 0) {
				ArrayList<ZipModel> zips = new ArrayList<ZipModel>();
				for (String[] str : arragrnerate) {
					ZipModel form = new ZipModel();
					form.setId(str[0]);
					form.setName(str[1]);
					form.setParentId(str[2]);
					form.setShortName(str[3]);
					form.setLevelType(str[4]);
					form.setCityCode(str[5]);
					form.setZipCode(str[6]);
					form.setMergerName(str[7]);
					form.setLng(str[8]);
					form.setLat(str[9]);
					form.setPinyin(str[10]);

					zips.add(form);
				}
				ZipCRUD zc = new ZipCRUD();
				// 执行Save方法
				zc.saveListZip(zips);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {
		ExcelTest e = new ExcelTest();
		e.saveZipWithExcel();
		// e.export();
	}
}
