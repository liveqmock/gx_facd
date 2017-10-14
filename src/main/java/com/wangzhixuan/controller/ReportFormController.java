package com.wangzhixuan.controller;

import java.io.IOException;
import java.io.OutputStream;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.wangzhixuan.commons.base.BaseController;
import com.wangzhixuan.service.IDailyPatrolService;

/**
 * 报表统计
 * 
 * @author sunbq
 *
 */
@Controller
@RequestMapping("/reportform")
public class ReportFormController extends BaseController {

	@Autowired
	private IDailyPatrolService idpService;
	private String[] header = new String[]{"巡防员","jan","feb","mar","apr","may","jun","jul","aug","sep","octb","nov","decb"};
	@RequestMapping("/report")
	@ResponseBody
	public Object report(String token,Integer year){
		year = year==null?2017:year;
		List<Map<String, Object>> rplist = idpService.selectFormList(year);
		return renderSuccess(rplist);
	}
	@SuppressWarnings("resource")
	@RequestMapping("/exportExcel")
	public void exportExcel(HttpServletRequest request,HttpServletResponse response,Integer year ){
		year = year==null?2017:year;
		List<Map<String, Object>> rplist = idpService.selectFormList(year);
		HSSFWorkbook wb = new HSSFWorkbook();
		HSSFSheet sheet = wb.createSheet("报表");
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
		for (String string : header) {
			style.setFont(f);
			cell = row.createCell(size);
			cell.setCellValue(string);
			cell.setCellStyle(style);
			size++;
		}
		// 字段
		for (Map<String,Object> map : rplist) {
			row = sheet.createRow(rownum++);
			int zdCell = 0;
			for (String string : header) {
				if (string.equals("巡防员")) {
					string="userName";
				}
				cell = row.createCell(zdCell);
				cell.setCellStyle(style1);
				cell.setCellValue(map.get(string).toString());
				zdCell++;
			}
		}
		try {
			response.setContentType("octets/stream");
			response.setCharacterEncoding("utf-8");
			response.setHeader("Content-Disposition", "attachment;filename=" +year+new String("报表".getBytes("gb2312"), "ISO-8859-1") + ".xls");
			OutputStream out = response.getOutputStream();
			wb.write(out);
			out.flush();
			out.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return;
	}
}
