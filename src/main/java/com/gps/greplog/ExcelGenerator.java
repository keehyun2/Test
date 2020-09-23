package com.gps.greplog;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import javax.servlet.http.HttpServletRequest;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.List;


public class ExcelGenerator {

	private ExcelGenerator() {}

	public static ByteArrayInputStream excelByteStream(List<List<Object>> excelData) throws IOException {

		Workbook workbook = new XSSFWorkbook();
		ByteArrayOutputStream out = new ByteArrayOutputStream();

		Sheet sheet = workbook.createSheet("result");
		
//		sheet.setColumnWidth(0, Math.min(255 * 256, sheet.getColumnWidth(0) + 1500));

		Font headerFont = workbook.createFont();
		headerFont.setBold(true);
		headerFont.setColor(IndexedColors.WHITE.getIndex());

		CellStyle headerCellStyle = workbook.createCellStyle();
		headerCellStyle.setFont(headerFont);
		headerCellStyle.setAlignment(HorizontalAlignment.CENTER);
		headerCellStyle.setFillForegroundColor(IndexedColors.BLUE_GREY.getIndex());
		headerCellStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);

		CellStyle contentsCellStyle = workbook.createCellStyle();
		contentsCellStyle.setWrapText(true);

		int rowIdx = 0;

		// Row for Header
		Row headerRow = sheet.createRow(rowIdx++);
		List<Object> headerTitle = excelData.get(0);
		for (int col = 0; col < headerTitle.size(); col++) {
			Cell cell = headerRow.createCell(col);
			cell.setCellValue((String)headerTitle.get(col));
			cell.setCellStyle(headerCellStyle);
		}

		// Row for Data
		for (int i = 1; i < excelData.size(); i++) {
			Row dataRow = sheet.createRow(rowIdx++);
			List<Object> rowList = excelData.get(i);
			for (int col = 0; col < rowList.size(); col++) {
				Cell cell = dataRow.createCell(col);
				Object data = rowList.get(col);
				if(data instanceof Double) {
					cell.setCellValue((Double) data);
				}else if(data instanceof Long) {
					cell.setCellValue((Long) data);
				}else if(data instanceof Integer) {
					cell.setCellValue((Integer) data);
				}else if(data instanceof String) {
					cell.setCellValue((String) data);
				}else if(data instanceof List){
					List<Object> childDataList = (List<Object>) data;
					StringBuilder mData = new StringBuilder();
					for (Object o : childDataList) {
						if(mData.length() > 0) mData.append("\n");
						mData.append(o);
					}
					cell.setCellValue(mData.toString());
					cell.setCellStyle(contentsCellStyle);
				}
			}
		}
		for(int colNum = 0; colNum < workbook.getSheetAt(0).getRow(0).getLastCellNum(); colNum++){
			workbook.getSheetAt(0).autoSizeColumn(colNum);
//			workbook.getSheetAt(0).setColumnWidth(colNum, (workbook.getSheetAt(0).getColumnWidth(colNum)) + 1500);
			workbook.getSheetAt(0).setColumnWidth(colNum, Math.min(255 * 256, workbook.getSheetAt(0).getColumnWidth(colNum) + 1500));
		}

		workbook.write(out);
		return new ByteArrayInputStream(out.toByteArray());
	}


}