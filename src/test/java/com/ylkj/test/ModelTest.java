package com.ylkj.test;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.ylkj.modelcal.vo.Employee;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.junit.Test;

import java.io.FileInputStream;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Zhudiwei
 * @date 2020/7/9
 */
public class ModelTest {
    @Test
    public void test01() throws Exception {
        FileInputStream input = new FileInputStream("C:\\Users\\朱迪炜\\Desktop\\模型计算.xls");
        HSSFWorkbook wb = new HSSFWorkbook(input);

        //员工表数据注入VO
        List<Employee> employees = setVO(wb, 1, Employee.class, new int[]{2, 4, 5, 6});



    }

    /**
     * 为VO赋值
     *
     * @param wb      工作簿对象，每个EXCEL有且仅有一个工作簿对象 {@link HSSFWorkbook}
     * @param index   sheet页的索引，从0开始 Sheet页对象{@link HSSFSheet}
     * @param clazz   需要set值的VO.class
     * @param cellStr 需要取的cell的索引
     */
    public <T> List<T> setVO(HSSFWorkbook wb, int index, Class<T> clazz, int[] cellStr) {
        List<T> voList = new ArrayList<T>();
        JSONObject object;
        Field[] declaredFields = clazz.getDeclaredFields();
        //根据索引获得sheet页面，索引从0开始
        HSSFSheet sheet = wb.getSheetAt(index);
        //获得该sheet页的行数
        int rowNum = sheet.getLastRowNum();
        if (rowNum > 0) {
            for (int i = 1; i <= rowNum; i++) {
                //定位到某一行
                HSSFRow row = sheet.getRow(i);
                object = new JSONObject();
                if (cellStr != null && cellStr.length > 0) {
                    for (int j = 0; j < cellStr.length; j++) {
                        HSSFCell cell = row.getCell(cellStr[j]);
                        cell.setCellType(Cell.CELL_TYPE_STRING);
                        String[] split = declaredFields[j].getType().getName().split("\\.");
                        String type = split[split.length - 1];
                        if ("Integer".equals(type)) {
                            object.put(declaredFields[j].getName(), Double.valueOf(cell.getStringCellValue()));
                        } else if ("String".equals(type)) {
                            object.put(declaredFields[j].getName(), cell.getStringCellValue());
                        }
                    }
                    T t = JSON.toJavaObject(object, clazz);
                    voList.add(t);
                }
            }
        }
        System.out.println(JSON.toJSONString(voList));
        return voList;
    }

}
