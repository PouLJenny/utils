package top.poul.utils.excel;

import org.apache.commons.lang3.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.*;
import java.util.*;

public class ExcelUtil {

    private ExcelUtil() {
    }


//    /**
//     * 获取Excel头部上的列名
//     * @param index
//     * @return
//     */
//    public static String getColumnName(int index) {
//        return String.valueOf((char)(65 + index));
//    }


    /**
     *
     * @param is
     * @param sheetNum      1 - base
     * @param colNumStart   1 - base
     * @param colNumEnd     1 - base
     * @return
     */
    public static List<List<String>> readExcel(InputStream is,int sheetNum,int colNumStart,int colNumEnd) throws IOException {
        if (colNumStart > colNumEnd) {
            throw new IllegalArgumentException("colNumStart can not grete than colNumEnd");
        }
        if (colNumStart < 1) {
            throw new IllegalArgumentException("colNumStart can not less 1");
        }
        if (sheetNum < 1) {
            throw new IllegalArgumentException("sheetNum can not less 1");
        }
        if (colNumEnd < 1) {
            throw new IllegalArgumentException("colNumEnd can not less 1");
        }
        sheetNum--;
        colNumStart--;
        colNumEnd--;
        Workbook workbook = null;
        try {
            workbook = WorkbookFactory.create(is);
            Sheet sheet = workbook.getSheetAt(sheetNum);
            if (sheet == null) {
                throw new IllegalArgumentException("sheet不存在");
            }
            int firstRowNum = sheet.getFirstRowNum();
            int lastRowNum = sheet.getLastRowNum();
            int width = colNumEnd - colNumStart + 1;
            List<List<String>> result = new ArrayList<List<String>>(lastRowNum  + 1);
            for (int ri = firstRowNum ;ri <= lastRowNum; ri++) {
                Row row = sheet.getRow(ri);
                ArrayList<String> rowData = new ArrayList<>(width);
                for (int ci = colNumStart;ci <= colNumEnd; ci++) {
                    Cell cell = row.getCell(ci);
                    String stringCellValue = cell == null ? StringUtils.EMPTY : cell.getStringCellValue();
                    rowData.add(ci,stringCellValue);
                }
                result.add(ri,rowData);
            }
            return result;
        } catch (IOException e) {
            // TODO 如果POI更换非4.0.0版本之后，请检查此处是否可用
            if (Objects.equals("Your InputStream was neither an OLE2 stream, nor an OOXML stream",e.getMessage())) {
                throw new IllegalArgumentException("请上传excel文件");
            } else {
                throw e;
            }
        } finally {
            if (workbook != null) {
                workbook.close();
            }
        }
    }

    /**
     * 读取excel为二维的String list
     * @param sheetName
     * @param is
     * @return
     * @throws Exception
     */
    public static List<List<String>> readExcel(String sheetName, InputStream is) throws Exception{
        Workbook workbook = null;
        try {
            workbook = WorkbookFactory.create(is);
            Sheet sheet = workbook.getSheet(sheetName);
            if (sheet == null) {
                throw new IllegalArgumentException("sheet"+sheetName+"不存在");
            }

            int firstRowNum = sheet.getFirstRowNum();
            int lastRowNum = sheet.getLastRowNum();
            List<List<String>> result = new ArrayList<List<String>>(lastRowNum  + 1);
            for (int ri = firstRowNum ;ri <= lastRowNum; ri++) {
                Row row = sheet.getRow(ri);
                short firstCellNum = row.getFirstCellNum();
                short lastCellNum = row.getLastCellNum();
                ArrayList<String> rowData = new ArrayList<>(lastCellNum  +  1);
                for (int ci = firstCellNum;ci < lastCellNum; ci++) {
                    Cell cell = row.getCell(ci);
                    String stringCellValue = cell == null ? StringUtils.EMPTY : cell.getStringCellValue();
                    rowData.add(ci,stringCellValue);
                }

                result.add(ri,rowData);
            }
            return result;
        } catch (IOException e) {
            // TODO 如果POI更换非4.0.0版本之后，请检查此处是否可用
            if (Objects.equals("Your InputStream was neither an OLE2 stream, nor an OOXML stream",e.getMessage())) {
                throw new IllegalArgumentException("请上传excel文件");
            } else {
                throw e;
            }
        } finally {
//			if (is != null) {
//				is.close();
//			}
            if (workbook != null) {
                workbook.close();
            }
        }
    }


    /**
     * 生成一个简单的excel
     * @return
     */
    public static Excel generateSimpleExcel (ExcelType type,String sheetName,List<List<Object>> data) throws IOException{
        Workbook workbook = null;
        ByteArrayOutputStream byteArrayOutputStream = null;
        try {
            workbook = getWorkbook(type);
            Sheet sheet = workbook.createSheet(sheetName);

            if (data != null && !data.isEmpty()) {
                int sheetRowIndex = 0;
                for (List<Object> rowData : data) {
                    if (rowData != null && !rowData.isEmpty()) {
                        Row row = sheet.createRow(sheetRowIndex);
                        int cellIndex = 0;
                        for (Object obj : rowData) {
                            row.createCell(cellIndex).setCellValue(obj == null ? "" : obj.toString());
                            cellIndex++;
                        }
                        sheetRowIndex++;
                    }
                }
            }

            byteArrayOutputStream = new ByteArrayOutputStream();
            workbook.write(byteArrayOutputStream);
            Excel excel = new Excel();
            excel.setExcelType(type);
            excel.setOutputStream(byteArrayOutputStream);
            return excel;
        } finally {
            if (workbook != null) {
                workbook.close();
            }
            if (byteArrayOutputStream != null) {
                byteArrayOutputStream.close();
            }
        }


    }


    public static Workbook getWorkbook(ExcelType type) {
        if (type == null) {
            throw new NullPointerException();
        }
        Workbook workbook = null;
        if (ExcelType.EXCEL_03.equals(type)) {
            workbook = new HSSFWorkbook();
        } else if (ExcelType.EXCEL_07.equals(type)){
            workbook = new XSSFWorkbook();
        } else {
            throw new IllegalArgumentException("不支持的类型" + type.getCode());
        }
        return workbook;
    }

    /**
     * EXCEL
     */
    private static char[] COL_BASE = {'A','B','C','D','E','F','G','H','I','J','K','L','M','N','O','P','Q','R','S','T','U','V','W','X','Y','Z'};
    /**
     * 获取excel中列的名字 A B C
     * @param colIndex 列序列 begin from zero
     * @return
     */
    public static String getColumnName(int colIndex) {
        if (colIndex < 0) {
            throw new IllegalArgumentException("colIndex can not < 0");
        }

        Stack<Character> characters = new Stack<>();
        int i = (colIndex) % (COL_BASE.length);
        characters.push(COL_BASE[i]);
        colIndex += 1;
        while (colIndex > COL_BASE.length) {
            colIndex = colIndex / COL_BASE.length;
            if (colIndex > COL_BASE.length) {
                characters.push(COL_BASE[COL_BASE.length - 1]);
            } else {
                characters.push(COL_BASE[colIndex - 1]);
            }
        }
        StringBuilder sb = new StringBuilder();
        while (!characters.empty()) {
            sb.append(characters.pop());
        }
        return sb.toString();
    }



}
