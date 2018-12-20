package top.poul.utils.excel;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import top.poul.utils.FileUtil;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class ExcelUtil {


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
                    String stringCellValue = row.getCell(ci).getStringCellValue();
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


    private static Workbook getWorkbook(ExcelType type) {
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




}
