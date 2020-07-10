package com.ylkj.test;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.ylkj.modelcal.vo.ConversionCoefficientQuantities;
import com.ylkj.modelcal.vo.Employee;
import com.ylkj.modelcal.vo.PersonnelAssignmentWeight;
import com.ylkj.modelcal.vo.ProjectDetails;
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
        FileInputStream input = new FileInputStream("D:\\模型计算.xls");
        HSSFWorkbook wb = new HSSFWorkbook(input);

        //员工表数据注入VO
        List<Employee> employees = setVO(wb, 1, Employee.class, new int[]{2, 4, 5, 6});

        //人员赋值及权重注入VO
        List<PersonnelAssignmentWeight> personnelAssignmentWeights = setVO(wb, 4, PersonnelAssignmentWeight.class, null);

        //工程量折算系数注入VO
        String json = "[{\"standardInvestment\":3000,\"voltageLevel\":10,\"ConversionQuantities\":0.6},{\"standardInvestment\":5000,\"voltageLevel\":35,\"ConversionQuantities\":0.7},{\"standardInvestment\":5000,\"voltageLevel\":110,\"ConversionQuantities\":0.8},{\"standardInvestment\":5000,\"voltageLevel\":220,\"ConversionQuantities\":0.9},{\"standardInvestment\":5000,\"voltageLevel\":330,\"ConversionQuantities\":1},{\"standardInvestment\":5000,\"voltageLevel\":500,\"ConversionQuantities\":1}]";
        List<ConversionCoefficientQuantities> conversionCoefficientQuantities = JSONArray.parseArray(json, ConversionCoefficientQuantities.class);

        //工程明细表注入VO
        List<ProjectDetails> projectDetails = setVO(wb, 10, ProjectDetails.class, null);

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
                if (cellStr != null) {
                    for (int j = 0; j < cellStr.length; j++) {
                        HSSFCell cell = row.getCell(cellStr[j]);
                        cell.setCellType(Cell.CELL_TYPE_STRING);
                        String[] split = declaredFields[j].getType().getName().split("\\.");
                        String type = split[split.length - 1];
                        if ("".equals(cell.getStringCellValue())) {
                            continue;
                        }
                        if ("Integer".equals(type) || "Double".equals(type)) {
                            try {
                                object.put(declaredFields[j].getName(), Double.valueOf(cell.getStringCellValue()));
                            } catch (Exception e) {
                                object.put(declaredFields[j].getName(), null);
                            }
                        } else if ("String".equals(type)) {
                            object.put(declaredFields[j].getName(), cell.getStringCellValue());
                        }
                    }
                    T t = JSON.toJavaObject(object, clazz);
                    voList.add(t);
                } else {
                    int cellNum = row.getPhysicalNumberOfCells();
                    for (int a = 0; a < cellNum; a++) {
                        HSSFCell cell = row.getCell(a);
                        cell.setCellType(Cell.CELL_TYPE_STRING);
                        String[] split = declaredFields[a].getType().getName().split("\\.");
                        String type = split[split.length - 1];
                        if ("Integer".equals(type) || "Double".equals(type)) {
                            try {
                                object.put(declaredFields[a].getName(), Double.valueOf(cell.getStringCellValue()));
                            } catch (Exception e) {
                                object.put(declaredFields[a].getName(), -9999);
                            }
                        } else if ("String".equals(type)) {
                            if (cell.getStringCellValue().equals("-")) {
                                object.put(declaredFields[a].getName(), "-9999");
                            } else {
                                object.put(declaredFields[a].getName(), cell.getStringCellValue());
                            }
                        }
                    }
                    T t = JSON.toJavaObject(object, clazz);
                    voList.add(t);
                }
            }
        }
        return voList;
    }

}
