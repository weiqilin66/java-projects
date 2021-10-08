package org.wayne.common.utils;

import com.google.gson.Gson;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.web.multipart.MultipartFile;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * @Description:
 * @author: LinWeiQi
 */
@Slf4j
public class PoiUtilQ {

    //2003- 版本的excel
    final static String EXCEL_2003_L = ".xls";
    //2007+ 版本的excel
    final static String EXCEL_2007_U = ".xlsx";

    static Gson gson = new Gson();

    public static void main(String[] args) {

        excel2ddl("C://Users//1//Desktop//帅奇的excel2mysql.xlsx",null);

    }

    /**
     * excels
     * @param excelPath
     */
    public static void excel2formatCol(String excelPath){
        Workbook workBook;
        try {
            workBook = getWorkBook(excelPath);
        } catch (Exception e) {
            log.error("生成workBook异常", e);
            return;
        }
        final List<Map<Integer, Object>> maps = readExcel(workBook, 1);
        log.info(gson.toJson(maps));

        for (Map<Integer, Object> map : maps) {
            System.out.println(StringUtilQ.toSqlStyle(map.get(0).toString()));
        }
    }

    /**
     * excel转建表语句
     * @param excelPath
     * @return
     */
    public static String excel2ddl(String excelPath,Object sheetOpt) {
        Workbook workBook;
        try {
            workBook = getWorkBook(excelPath);
        } catch (Exception e) {
            log.error("生成workBook异常", e);
            return "生成workBook异常";
        }
        final StringBuffer sb = getDDL(workBook,sheetOpt);
        return sb.toString();
    }
    /**
     * excel转建表语句
     * @param
     * @return
     */
    public static String excel2ddl(MultipartFile file,Object sheetOpt) {
        Workbook workBook;
        try {
            workBook = getWorkBook(file);
        } catch (Exception e) {
            log.error("生成workBook异常", e);
            return "生成workBook异常";
        }
        final StringBuffer sb = getDDL(workBook,sheetOpt);
        return sb.toString();
    }

    private static StringBuffer getDDL(Workbook workBook,Object sheetOpt) {
        List<Map<Integer, Object>> maps;
        if (sheetOpt==null) {
          maps = readExcel(workBook, 0);
        }else if(sheetOpt instanceof String) {
            maps = readExcel(workBook, (String) sheetOpt);

        }else {
            maps = readExcel(workBook, (Integer) sheetOpt);
        }
        log.info(gson.toJson(maps));
        String tableName = maps.get(0).get(1).toString();
        String tableComment = maps.get(0).get(4).toString();
        tableName=StringUtilQ.toSqlStyle(tableName);
        log.info("表名:{},备注:{}", tableName, tableComment);
        final StringBuffer sb = new StringBuffer();
        sb.append("\nDROP TABLE IF EXISTS ").append(tableName).append(";");
        sb.append("\nCREATE TABLE ").append(tableName).append(" (\n");
        final String str1 = "',\n";

        for (int i = 2; i < maps.size(); i++) {
            final Map<Integer, Object> rowMap = maps.get(i);
            String colName = String.valueOf(rowMap.get(0));
            if ("".equals(colName)) {
                continue;
            }
            colName = StringUtilQ.toSqlStyle(colName);
            String colType = String.valueOf(rowMap.get(1));
            final String colLength = String.valueOf(rowMap.get(2));
            final String colComment = String.valueOf(rowMap.get(3));
            final String colNull = String.valueOf(rowMap.get(4));
            final String colIndex = String.valueOf(rowMap.get(5));
            final String primary = String.valueOf(rowMap.get(6));
            log.info("字段名:{},字段类型:{},字段长度:{},备注:{},是否可空:{},是否所以索引:{},是否组件:{}",
                    colName, colType,colLength, colComment, colNull, colIndex, primary);
            //字段类型转换
            colType = changeJavaType(colType);
            // 拼接字段
            if (colLength.contains(",")) {
                final String[] split = colLength.split(",");
                sb.append("\t").append(colName).append(" ").append(colType).append(String.format("(%s,%s)",
                        split[0],split[1])).append(" ");
            }else {
                sb.append("\t").append(colName).append(" ").append(colType).append(String.format("(%s)",
                        Math.round(Double.parseDouble(colLength)))).append(" ");
            }
            //是否可空
            if ("Y".equals(colNull)||"是".equals(colNull)) {
                sb.append("DEFAULT NULL ");
            } else {
                sb.append("NOT NULL ");
            }
            sb.append("COMMENT '").append(colComment).append(str1);
        }
        int flag = 0;
        for (int i = 2; i < maps.size(); i++) {
            final Map<Integer, Object> rowMap = maps.get(i);
            String colName = String.valueOf(rowMap.get(0));
            if ("".equals(colName)) {
                continue;
            }
            colName = StringUtilQ.toSqlStyle(colName);
            final String colIndex = String.valueOf(rowMap.get(5));
            final String primary = String.valueOf(rowMap.get(6));
            if ("Y".equals(primary) ||"是".equals(primary)) {
                sb.append("\tPRIMARY KEY (").append(colName).append("),\n");
                flag = 1;
            }
            if ("Y".equals(colIndex)||"是".equals(colIndex)) {
                sb.append("\tKEY ").append(tableName).append("_").append("0").append(i).append("(")
                        .append(colName).append(") USING BTREE,\n");
                flag = 2;
            }

        }

        if (flag == 1) {

            sb.delete(sb.lastIndexOf(","), ",".length() + sb.lastIndexOf(","));
            sb.append(")");
        } else if (flag == 2) {
            sb.delete(sb.lastIndexOf(",\n"), ",\n".length() + sb.lastIndexOf(",\n"));
            sb.append("\n)");
        } else {
            sb.delete(sb.lastIndexOf(str1), str1.length() + sb.lastIndexOf(str1));
            sb.append("'\n)");
        }
        sb.append("DEFAULT CHARSET=utf8 COMMENT='").append(tableComment).append("';");
        return sb;
    }
    private static List<String> javaType;
    static {
        javaType = Arrays.asList("varchar","int","decimal");

    }
    //java类型转成mysql
    private static String changeJavaType(String colType) {
        if (javaType.contains(colType)) {
            return colType;
        }
        switch (colType) {
            case "String":
                return "varchar";
            case "string":
                return "varchar";
            case "BigDecimal":
                return "decimal";
            case "Integer":
                return "int";
            default:
                return "undefind";
        }
    }

    /**
     * 读取数据
     */
    public static List<Map<Integer, Object>> readExcel(Workbook work, Integer numOfSheet) {

        List<Map<Integer, Object>> list = new ArrayList<>();

        //遍历Excel中所有的sheet
        final int numberOfSheets = work.getNumberOfSheets();
        log.info("共有sheet {}页", numberOfSheets);
        if (numOfSheet != null) {
            list = blSheet(work, numOfSheet);
        } else {
            for (int i = 0; i < numberOfSheets; i++) {
                list = blSheet(work, i);
            }
        }

        return list;
    }
    /**
     * 读取数据
     */
    public static List<Map<Integer, Object>> readExcel(Workbook work, String sheetName) {

        final Sheet sheet = work.getSheet(sheetName);
        List<Map<Integer, Object>> list = new ArrayList<>();
        final int firstRowNum = sheet.getFirstRowNum();
        final int lastRowNum = sheet.getLastRowNum();
        log.info("当前遍历sheet:{},首行:{},末行:{}", sheetName, firstRowNum, lastRowNum);
        for (int j = firstRowNum; j <= lastRowNum; j++) {
            Row row = sheet.getRow(j);
            if (row == null) {
                continue;
            }
            final Map<Integer, Object> dataMap = new HashMap<>();
            for (int k = row.getFirstCellNum(); k < row.getLastCellNum(); k++) {
                Cell cell = row.getCell(k);
                if (cell == null) {
                    dataMap.put(k, "");
                } else {
                    dataMap.put(k, getCellValue(cell));
                }
            }
            log.info("第{}行对象数据:{}", j, dataMap);
            list.add(dataMap);

        }
        return list;
    }

    static List<Map<Integer, Object>> blSheet(Workbook workbook, Integer numOfSheet) {
        List<Map<Integer, Object>> list = new ArrayList<>();

        Sheet sheet = workbook.getSheetAt(numOfSheet);
        final int firstRowNum = sheet.getFirstRowNum();
        final int lastRowNum = sheet.getLastRowNum();
        log.info("当前遍历第{}页sheet,首行:{},末行:{}", numOfSheet, firstRowNum, lastRowNum);
        for (int j = firstRowNum; j <= lastRowNum; j++) {
            Row row = sheet.getRow(j);
            if (row == null) {
                continue;
            }
            final Map<Integer, Object> dataMap = new HashMap<>();
            for (int k = row.getFirstCellNum(); k < row.getLastCellNum(); k++) {
                Cell cell = row.getCell(k);
                if (cell == null) {
                    dataMap.put(k, "");
                } else {
                    dataMap.put(k, getCellValue(cell));
                }
            }
            log.info("第{}行对象数据:{}", j, dataMap);
            list.add(dataMap);

        }
        return list;
    }

    /**
     * 根据文件路径中文件名判断excel版本
     */
    public static Workbook getWorkBook(String excelPath) throws Exception {
        InputStream in;
        Workbook workbook;
        try {
            in = new FileInputStream(excelPath);
        } catch (FileNotFoundException e) {
            log.error("excel文件不存在", e);
            throw new Exception();
        }
        if (excelPath.contains(EXCEL_2007_U)) {
            log.info("生成2007版本excel,使用XSSF");
            workbook = new XSSFWorkbook(in);

        } else if (excelPath.contains(EXCEL_2003_L)) {
            log.info("生成2003版本excel,使用HSSF");
            workbook = new HSSFWorkbook(in);

        } else {
            throw new Exception("解析的文件格式有误！");
        }
        return workbook;
    }
    /**
     * 根据文件路径中文件名判断excel版本
     */
    public static Workbook getWorkBook(MultipartFile file) throws Exception {
        InputStream in;
        Workbook workbook;
        try {
            in = file.getInputStream();
        } catch (FileNotFoundException e) {
            log.error("excel文件不存在", e);
            throw new Exception();
        }
        String excelPath = file.getOriginalFilename();
        if (excelPath.contains(EXCEL_2007_U)) {
            log.info("生成2007版本excel,使用XSSF");
            workbook = new XSSFWorkbook(in);

        } else if (excelPath.contains(EXCEL_2003_L)) {
            log.info("生成2003版本excel,使用HSSF");
            workbook = new HSSFWorkbook(in);

        } else {
            throw new Exception("解析的文件格式有误！");
        }
        return workbook;
    }

    /**
     * @Description 统一格式化 表格中 某列 的数值小位数,时间格式  --未完善
     * @date 2020/2/16
     */
    public static Object getCellValue(Cell cell) {
        Object value = null;
        DecimalFormat df = new DecimalFormat("0");
        SimpleDateFormat sdf = new SimpleDateFormat("yyy-MM-dd");
        DecimalFormat df2 = new DecimalFormat("0.00");

        if (cell == null || cell.getCellType() == CellType.BLANK) {
            value = "";
        } else {
            //判断数据类型
            switch (cell.getCellType()) {
                case FORMULA:
                    value = "" + cell.getCellFormula();
                    break;
                case NUMERIC:
                    value = "" + cell.getNumericCellValue();
                    break;
                case STRING:
                    value = cell.getStringCellValue();
                    break;
                default:
                    break;
            }
        }

        return value;
    }

    /**
     * @params [lineStyleMap行样式, columnWidthMap列宽, headLists表头内容行集合, dataList数据内容行集合]
     * @Description excel2003导出  废弃 作为代码参考 poi应该定制化util接口 参数只传入一个数据data
     * @date 2020/2/16
     */
    public static Workbook MyExportExcel2003(HashMap<Integer, CellStyle> lineStyleMap, HashMap<Integer, Integer> columnWidthMap, List<List<String>> headLists, List<List<?>> dataList) {
        // 表头数
        int headLineSize = headLists.size();
        // 数据列数
        int columnSize = dataList.size();
        // 1. 建立表格
        Workbook workbook = new HSSFWorkbook();
        // 2. 新建sheet页
        Sheet sheet = workbook.createSheet();

        // 3. 表头处理
        for (int i = 0; i < headLineSize; i++) {
            List<String> headStrList = headLists.get(i);
            // 新建行
            Row row = sheet.createRow(i);
            // 行样式
            CellStyle cellStyle = lineStyleMap.get(i);
            // 新建列
            for (int j = 0; j < headStrList.size(); j++) {

                Cell cell = row.createCell(j);
                cell.setCellValue(headStrList.get(j));
                cell.setCellStyle(cellStyle);
            }

        }
        // 列宽处理
        for (int i = 0; i < columnSize; i++) {
            sheet.setColumnWidth(i, columnWidthMap.get(i));
        }

        // 4. 遍历表格数据
        CellStyle dataStyle = lineStyleMap.get(headLineSize);
        for (int i = headLineSize; i < dataList.size(); i++) {
            Row row = sheet.createRow(i);
            List<?> rowData = dataList.get(i);
            for (int j = 0; j < rowData.size(); j++) {
                Cell cell = row.createCell(j);
                // 数据格式转换
                if (rowData.get(j) instanceof String) {
                    String value = (String) rowData.get(j);
                    cell.setCellValue(value);
                } else if (rowData.get(j) instanceof Integer) {
                    Integer value = ((Integer) rowData.get(j));
                    cell.setCellValue(value);
                } else if (rowData.get(j) instanceof Date) {
                    String value = (String) rowData.get(j);
                    cell.setCellValue(value);
                }
                cell.setCellStyle(dataStyle);
            }

        }

        return workbook;
    }

}
