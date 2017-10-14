package com.wangzhixuan.commons.utils;

import java.io.IOException;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

import com.wangzhixuan.model.MapMark;
import com.wangzhixuan.model.PositionMark;
import com.wangzhixuan.model.SendInfo;

public class ExportExcel {
	// 显示的导出表的标题
	private String title;

	// 导出表的列名
	private String[] rowName;

	private List<?> dataList;

	HttpServletResponse response;

	// 构造方法，传入要导出的数据
	public ExportExcel(String title, String[] rowName, List<?> dataList, HttpServletResponse response) {
		this.dataList = dataList;
		this.rowName = rowName;
		this.title = title;

		this.response = response;
	}

	public static String[] sendinfoToString(String[] str) {
		for (int i = 0; i < str.length; i++) {
			if (str[i].equals("")) {

			}
		}

		return null;
	}

	public void exportSendInFo() throws Exception {

		HSSFWorkbook wb = new HSSFWorkbook();
		HSSFSheet sheet = wb.createSheet(title);
		sheet.setDefaultColumnWidth((short) 15);

		HSSFFont f = wb.createFont();
		f.setFontHeightInPoints((short) 11);// 字号
		f.setBold(true);
		// 表格标题一个样式
		HSSFCellStyle style = wb.createCellStyle();

		style.setFillBackgroundColor((short) 13);
		// 表格内容样式
		HSSFCellStyle style1 = wb.createCellStyle();
		HSSFCell cell;
		// 根据选择的字段生成表头
		short size = 0;
		int rownum = 0;
		HSSFRow row = sheet.createRow(rownum++);
		for (String string : rowName) {
			style.setFont(f);
			cell = row.createCell(size);
			cell.setCellValue(string);
			cell.setCellStyle(style);
			size++;
		}
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		// 字段
		for (int i = 0; i < dataList.size(); i++) {
			Object obj = dataList.get(i);
			SendInfo s = (SendInfo) obj;

			row = sheet.createRow(i + 1);

			cell = row.createCell(0);
			cell.setCellStyle(style1);
			cell.setCellValue(s.getSendusername());

			cell = row.createCell(1);
			cell.setCellStyle(style1);
			cell.setCellValue(s.getSendInfoName());

			cell = row.createCell(2);
			cell.setCellStyle(style1);
			cell.setCellValue(sdf.format(s.getReceiveTime()));

			cell = row.createCell(3);
			cell.setCellStyle(style1);
			cell.setCellValue(s.getSendInfo());

			cell = row.createCell(4);
			cell.setCellStyle(style1);
			switch (s.getSendInfoType()) {
			case 0:
				cell.setCellValue("基础设施");
				break;
			case 1:
				cell.setCellValue("涉外事件");
				break;
			case 2:
				cell.setCellValue("走私事件");
				break;
			case 3:
				cell.setCellValue("突发事件");
				break;
			}

			cell = row.createCell(5);
			cell.setCellStyle(style1);
			switch (s.getUrgencyLevel()) {
			case 0:
				cell.setCellValue("一般");
				break;
			case 1:
				cell.setCellValue("紧急");
				break;
			case 2:
				cell.setCellValue("特急");
				break;

			}

			cell = row.createCell(6);
			cell.setCellStyle(style1);
			switch (s.getInfoStatus()) {
			case 0:
				cell.setCellValue("未查看");
				break;
			case 1:
				cell.setCellValue("已查看");
				break;
			case 2:
				cell.setCellValue("已审核");
				break;
			case 3:
				cell.setCellValue("未审核");
				break;
			}

		}
		try {
			response.setContentType("octets/stream");
			response.setCharacterEncoding("utf-8");
			response.setHeader("Content-Disposition",
					"attachment;filename=" + new String(title.getBytes("gb2312"), "ISO-8859-1") + ".xls");
			OutputStream out = response.getOutputStream();
			wb.write(out);
			wb.close();
			out.flush();
			out.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return;
	}

	public void exportmapMark() throws Exception {

		HSSFWorkbook wb = new HSSFWorkbook();
		HSSFSheet sheet = wb.createSheet(title);
		sheet.setDefaultColumnWidth((short) 15);

		HSSFFont f = wb.createFont();
		f.setFontHeightInPoints((short) 11);// 字号
		f.setBold(true);
		// 表格标题一个样式
		HSSFCellStyle style = wb.createCellStyle();

		style.setFillBackgroundColor((short) 13);
		// 表格内容样式
		HSSFCellStyle style1 = wb.createCellStyle();
		HSSFCell cell;
		// 根据选择的字段生成表头
		short size = 0;
		int rownum = 0;
		HSSFRow row = sheet.createRow(rownum++);
		for (String string : rowName) {
			style.setFont(f);
			cell = row.createCell(size);
			cell.setCellValue(string);
			cell.setCellStyle(style);
			size++;
		}
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		// 字段
		for (int i = 0; i < dataList.size(); i++) {
			Object obj = dataList.get(i);
			MapMark p = (MapMark) obj;

			row = sheet.createRow(i + 1);

			cell = row.createCell(0);
			cell.setCellStyle(style1);
			cell.setCellValue(p.getName());

			cell = row.createCell(1);
			cell.setCellStyle(style1);
			switch (p.getType()) {
			case 0:
				cell.setCellValue("界碑");
				break;
			case 1:
				cell.setCellValue("巡防路");
				break;
			case 2:
				cell.setCellValue("邻国边境部署");
				break;

			}

			cell = row.createCell(2);
			cell.setCellStyle(style1);
			cell.setCellValue(p.getLongitude());

			cell = row.createCell(3);
			cell.setCellStyle(style1);
			cell.setCellValue(p.getLatitude());

			cell = row.createCell(4);
			cell.setCellStyle(style1);
			cell.setCellValue(sdf.format(p.getCreateTime()));

		}
		try {
			response.setContentType("octets/stream");
			response.setCharacterEncoding("utf-8");
			response.setHeader("Content-Disposition",
					"attachment;filename=" + new String(title.getBytes("gb2312"), "ISO-8859-1") + ".xls");
			OutputStream out = response.getOutputStream();
			wb.write(out);
			wb.close();
			out.flush();
			out.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return;
	}

	public void exportPositionMark() throws Exception {

		HSSFWorkbook wb = new HSSFWorkbook();
		HSSFSheet sheet = wb.createSheet(title);
		sheet.setDefaultColumnWidth((short) 15);

		HSSFFont f = wb.createFont();
		f.setFontHeightInPoints((short) 11);// 字号
		f.setBold(true);
		// 表格标题一个样式
		HSSFCellStyle style = wb.createCellStyle();

		style.setFillBackgroundColor((short) 13);
		// 表格内容样式
		HSSFCellStyle style1 = wb.createCellStyle();
		HSSFCell cell;
		// 根据选择的字段生成表头
		short size = 0;
		int rownum = 0;
		HSSFRow row = sheet.createRow(rownum++);
		for (String string : rowName) {
			style.setFont(f);
			cell = row.createCell(size);
			cell.setCellValue(string);
			cell.setCellStyle(style);
			size++;
		}
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		// 字段
		for (int i = 0; i < dataList.size(); i++) {
			Object obj = dataList.get(i);
			PositionMark p = (PositionMark) obj;

			row = sheet.createRow(i + 1);

			cell = row.createCell(0);
			cell.setCellStyle(style1);
			cell.setCellValue(p.getName());

			cell = row.createCell(1);
			cell.setCellStyle(style1);
			cell.setCellValue(p.getUsername());

			cell = row.createCell(2);
			cell.setCellStyle(style1);
			switch (p.getType()) {
			case 0:
				cell.setCellValue("界碑");
				break;
			case 1:
				cell.setCellValue("巡防路");
				break;
			case 2:
				cell.setCellValue("邻国边境部署");
				break;

			}

			cell = row.createCell(3);
			cell.setCellStyle(style1);
			cell.setCellValue(p.getLongitude());

			cell = row.createCell(4);
			cell.setCellStyle(style1);
			cell.setCellValue(p.getLatitude());

			cell = row.createCell(5);
			cell.setCellStyle(style1);
			switch (p.getInfoStatus()) {
			case 0:
				cell.setCellValue("未查看");
				break;
			case 1:
				cell.setCellValue("信息已查看");
				break;
			case 2:
				cell.setCellValue("审核已通过");
				break;
			case 3:
				cell.setCellValue("审核未通过");
				break;
			}

			cell = row.createCell(6);
			cell.setCellStyle(style1);
			cell.setCellValue(sdf.format(p.getCreateTime()));

		}
		try {
			response.setContentType("octets/stream");
			response.setCharacterEncoding("utf-8");
			response.setHeader("Content-Disposition",
					"attachment;filename=" + new String(title.getBytes("gb2312"), "ISO-8859-1") + ".xls");
			OutputStream out = response.getOutputStream();
			wb.write(out);
			wb.close();
			out.flush();
			out.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return;
	}
}