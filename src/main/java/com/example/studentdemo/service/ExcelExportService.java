package com.example.studentdemo.service;

import com.example.studentdemo.entity.Attendance;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.URLEncoder;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Service
public class ExcelExportService {

    public void exportAttendance(List<Attendance> records, HttpServletResponse response) throws IOException {
        // 创建工作簿
        Workbook workbook = new XSSFWorkbook();
        Sheet sheet = workbook.createSheet("考勤记录");

        // 创建标题行
        Row headerRow = sheet.createRow(0);
        String[] headers = {"ID", "学号", "姓名", "课程ID", "打卡时间", "状态", "IP", "备注"};

        CellStyle headerStyle = workbook.createCellStyle();
        Font headerFont = workbook.createFont();
        headerFont.setBold(true);
        headerStyle.setFont(headerFont);

        for (int i = 0; i < headers.length; i++) {
            Cell cell = headerRow.createCell(i);
            cell.setCellValue(headers[i]);
            cell.setCellStyle(headerStyle);
        }

        // 填充数据
        int rowNum = 1;
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

        for (Attendance record : records) {
            Row row = sheet.createRow(rowNum++);
            row.createCell(0).setCellValue(record.getId());
            row.createCell(1).setCellValue(record.getStudentId());
            row.createCell(2).setCellValue(record.getStudentName());
            row.createCell(3).setCellValue(record.getCourseId());
            row.createCell(4).setCellValue(record.getCheckInTime() != null ? record.getCheckInTime().format(formatter) : "");
            row.createCell(5).setCellValue(getStatusText(record.getStatus()));
            row.createCell(6).setCellValue(record.getIp() != null ? record.getIp() : "");
            row.createCell(7).setCellValue(record.getRemark() != null ? record.getRemark() : "");
        }

        // 设置列宽
        for (int i = 0; i < headers.length; i++) {
            sheet.autoSizeColumn(i);
        }

        // 输出到响应
        response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
        response.setHeader("Content-Disposition", "attachment; filename=" + URLEncoder.encode("考勤记录.xlsx", "UTF-8"));
        workbook.write(response.getOutputStream());
        workbook.close();
    }

    private String getStatusText(String status) {
        if (status == null) return "未知";
        switch (status) {
            case "NORMAL": return "正常";
            case "LATE": return "迟到";
            case "EARLY": return "早退";
            case "ABSENT": return "缺勤";
            default: return status;
        }
    }
}