package com.atguigu.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.atguigu.constant.MessageConstant;
import com.atguigu.entity.Result;
import com.atguigu.service.MemberService;
import com.atguigu.service.ReportService;
import com.atguigu.service.SetMealService;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.FileInputStream;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.*;

@RestController
@RequestMapping("/report")
public class ReportController {

    @Reference
    MemberService memberService;

    @Reference
    SetMealService setMealService;

    @Reference
    ReportService reportService;

    @GetMapping("/exportBusinessReport")
    public Result exportBusinessReport(HttpSession session, HttpServletResponse response){
        try {
            //调用getBusinessReportData方法，获取需要导出的数据
            Map<String, Object> result = reportService.getBusinessReportData();
            //将数据取出，准备将数据写入到Excel文件中
            String reportDate = (String) result.get("reportDate");
            Integer todayNewMember = (Integer) result.get("todayNewMember");
            Integer totalMember = (Integer) result.get("totalMember");
            Integer thisWeekNewMember = (Integer) result.get("thisWeekNewMember");
            Integer thisMonthNewMember = (Integer) result.get("thisMonthNewMember");
            Integer todayOrderNumber = (Integer) result.get("todayOrderNumber");
            Integer thisWeekOrderNumber = (Integer) result.get("thisWeekOrderNumber");
            Integer thisMonthOrderNumber = (Integer) result.get("thisMonthOrderNumber");
            Integer todayVisitsNumber = (Integer) result.get("todayVisitsNumber");
            Integer thisWeekVisitsNumber = (Integer) result.get("thisWeekVisitsNumber");
            Integer thisMonthVisitsNumber = (Integer) result.get("thisMonthVisitsNumber");
            List<Map> hotSetmeal = (List<Map>) result.get("hotSetmeal");
            //获取模板文件的绝对路径
            String templateRealPath = session.getServletContext().getRealPath("template") + File.separator +
                    "report_template.xlsx";
            //读取模板文件创建Excel表格对象
            XSSFWorkbook workbook = new XSSFWorkbook(new FileInputStream(new File(templateRealPath)));
            //获取表格文件中的第一张表格
            XSSFSheet sheet = workbook.getSheetAt(0);
            //将数据逐个写入表格中
            //日期
            sheet.getRow(2).getCell(5).setCellValue(reportDate);
            //新增会员数
            XSSFRow row = sheet.getRow(4);
            row.getCell(5).setCellValue(todayNewMember);
            row.getCell(7).setCellValue(totalMember);//总会员数

            row = sheet.getRow(5);
            row.getCell(5).setCellValue(thisWeekNewMember);//本周新增会员数
            row.getCell(7).setCellValue(thisMonthNewMember);//本月新增会员数

            row = sheet.getRow(7);
            row.getCell(5).setCellValue(todayOrderNumber);//今日预约数
            row.getCell(7).setCellValue(todayVisitsNumber);//今日出游数

            row = sheet.getRow(8);
            row.getCell(5).setCellValue(thisWeekOrderNumber);//本周预约数
            row.getCell(7).setCellValue(thisWeekVisitsNumber);//本周出游数

            row = sheet.getRow(9);
            row.getCell(5).setCellValue(thisMonthOrderNumber);//本月预约数
            row.getCell(7).setCellValue(thisMonthVisitsNumber);//本月出游数

            int rowNum = 12;
            for (Map map : hotSetmeal) {//热门套餐
                //获取集合中数据
                String name = (String) map.get("name");
                Long setmeal_count = (Long) map.get("setmeal_count");
                BigDecimal proportion = (BigDecimal) map.get("proportion");
                XSSFRow row1 = sheet.getRow(rowNum++);
                //将数据写入表格中
                row1.getCell(4).setCellValue(name);//套餐名称
                row1.getCell(5).setCellValue(setmeal_count);//预约数量
                row1.getCell(6).setCellValue(proportion.doubleValue());//占比
            }

            //获取输出流
            ServletOutputStream os = response.getOutputStream();
            //下载的数据类型（Excel类型）
            response.setContentType("application/vnd.ms-excel");
            //设置下载形式（通过附件形式下载）
            response.setHeader("content-Disposition","attachment;filename=report.xlsx");
            workbook.write(os);
            os.flush();
            os.close();
            workbook.close();
            return null;
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false, MessageConstant.GET_BUSINESS_REPORT_FAIL,null);
        }
    }

    @GetMapping("/getBusinessReportData")
    public Result getBusinessReportData(){
        try {
            Map map = reportService.getBusinessReportData();
            return new Result(true,MessageConstant.GET_BUSINESS_REPORT_SUCCESS, map);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false,MessageConstant.GET_BUSINESS_REPORT_FAIL);
        }
    }

    @GetMapping("/getSetmealReport")
    public Result getSetmealReport(){
        try {
            List<String> setmealNames = new ArrayList<>();
            List<Map> setmealCount = setMealService.getSetmealReport();

            for (Map map : setmealCount) {
                String setmealName = (String) map.get("name");
                setmealNames.add(setmealName);
            }

            Map map = new HashMap();
            map.put("setmealNames", setmealNames);
            map.put("setmealCount", setmealCount);
            return new Result(true,MessageConstant.GET_SETMEAL_COUNT_REPORT_SUCCESS, map);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false, MessageConstant.GET_SETMEAL_COUNT_REPORT_FAIL);
        }
    }

    @GetMapping("/getMemberReport")
    public Result getMemberReport(){
        try {
            List<String> months = new ArrayList<>();
            List<Integer> monthCounts = null;

            Calendar calendar = Calendar.getInstance();
            calendar.add(Calendar.MONTH,-12);
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM");
            for(int i = 0; i < 12; i++){
                calendar.add(Calendar.MONTH,1);
                Date time = calendar.getTime();
                String dateStr = simpleDateFormat.format(time);
                months.add(dateStr);
            }

            monthCounts = memberService.findMemberCountByMonth(months);

            Map map = new HashMap<>();
            map.put("months", months);
            map.put("memberCount", monthCounts);
            return new Result(true, MessageConstant.GET_MEMBER_NUMBER_REPORT_SUCCESS, map);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false,MessageConstant.GET_MEMBER_NUMBER_REPORT_FAIL);
        }
    }

}
