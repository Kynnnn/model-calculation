package com.ylkj.test;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.ylkj.modelcal.vo.*;
import lombok.extern.log4j.Log4j;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.junit.Test;

import java.io.FileInputStream;
import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Zhudiwei
 * @date 2020/7/9
 */
@Log4j
public class ModelTest {
    @Test
    public void test01() throws Exception {

        FileInputStream input = new FileInputStream("D:\\模型计算.xls");
        HSSFWorkbook wb = new HSSFWorkbook(input);

        //员工表数据注入VO
        List<Employee> employees = setVO(wb, 1, Employee.class, new int[]{2, 4, 5, 6});
        // System.out.println(JSON.toJSONString(employees));

        //人员赋值及权重注入VO
        List<PersonnelAssignmentWeight> personnelAssignmentWeights = setVO(wb, 4, PersonnelAssignmentWeight.class, null);

        //工程量折算系数注入VO
        String json = "[{\"standardInvestment\":3000,\"voltageLevel\":10,\"ConversionQuantities\":0.6},{\"standardInvestment\":5000,\"voltageLevel\":35,\"ConversionQuantities\":0.7},{\"standardInvestment\":5000,\"voltageLevel\":110,\"ConversionQuantities\":0.8},{\"standardInvestment\":5000,\"voltageLevel\":220,\"ConversionQuantities\":0.9},{\"standardInvestment\":5000,\"voltageLevel\":330,\"ConversionQuantities\":1},{\"standardInvestment\":5000,\"voltageLevel\":500,\"ConversionQuantities\":1}]";
        List<ConversionCoefficientQuantities> conversionCoefficientQuantities = JSONArray.parseArray(json, ConversionCoefficientQuantities.class);

        //工程明细表注入VO
        List<ProjectDetails> projectDetails = setVO(wb, 10, ProjectDetails.class, null);

        //企业信息表注入VO
        EnterpriseInformation enterpriseInformation = new EnterpriseInformation();


        //1.计算 人员信息（项目部人员）
        employees = calEmployee(employees, personnelAssignmentWeights);

        //2.计算 工程明细表
        calProjectDetails(projectDetails);
    }

    /**
     * 工程明细表计算 1-12月
     */
    public List<ProjectDetails> calProjectDetails(List<ProjectDetails> projectDetailList) throws Exception {


        for (ProjectDetails projectDetails : projectDetailList) {
            //如果 是 的话计算
            Double CalJanuary = 1 * projectDetails.getQuantityConversion();
            BigDecimal b = new BigDecimal(CalJanuary);

            //循环出每个工程 一月份
            if (projectDetails.getJanuary().equals("是")) {
                //如果百分位是0的话默认为0.0
                if (b.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue() == 0) {
                    //一月
                    projectDetails.setCalJanuary(0.0);
                }
                //四舍五入
                projectDetails.setCalJanuary(b.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue());

            } else {
                projectDetails.setCalJanuary(0 * projectDetails.getQuantityConversion());
            }
            //循环出每个工程 二月份
            if (projectDetails.getFebruary().equals("是")) {
                //如果百分位是0的话默认为0.0
                if (b.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue() == 0) {

                    //二月
                    projectDetails.setCalFebruary(0.0);
                }
                //四舍五入
                projectDetails.setCalFebruary(b.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue());

            } else {
                projectDetails.setCalFebruary(0 * projectDetails.getQuantityConversion());
            }
            //循环出每个工程 三月份
            if (projectDetails.getMarch().equals("是")) {
                //如果百分位是0的话默认为0.0
                if (b.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue() == 0) {

                    //三月
                    projectDetails.setCalMarch(0.0);
                }
                //四舍五入
                projectDetails.setCalMarch(b.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue());

            } else {
                projectDetails.setCalMarch(0 * projectDetails.getQuantityConversion());
            }
            //循环出每个工程 四月份
            if (projectDetails.getApril().equals("是")) {
                //如果百分位是0的话默认为0.0
                if (b.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue() == 0) {

                    //四月
                    projectDetails.setCalApril(0.0);
                }
                //四舍五入
                projectDetails.setCalApril(b.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue());

            } else {
                projectDetails.setCalApril(0 * projectDetails.getQuantityConversion());
            }
            //循环出每个工程 五月份
            if (projectDetails.getMay().equals("是")) {
                //如果百分位是0的话默认为0.0
                if (b.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue() == 0) {

                    //五月
                    projectDetails.setCalMay(0.0);
                }
                //四舍五入
                projectDetails.setCalMay(b.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue());

            } else {
                projectDetails.setCalMay(0 * projectDetails.getQuantityConversion());
            }
            //循环出每个工程 六月份
            if (projectDetails.getJune().equals("是")) {
                //如果百分位是0的话默认为0.0
                if (b.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue() == 0) {

                    //六月
                    projectDetails.setCalJune(0.0);
                }
                //四舍五入
                projectDetails.setCalJune(b.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue());

            } else {
                projectDetails.setCalJune(0 * projectDetails.getQuantityConversion());
            }
            //循环出每个工程 七月份
            if (projectDetails.getJuly().equals("是")) {
                //如果百分位是0的话默认为0.0
                if (b.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue() == 0) {

                    //七月
                    projectDetails.setCalJuly(0.0);
                }
                //四舍五入
                projectDetails.setCalJuly(b.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue());

            } else {
                projectDetails.setCalJuly(0 * projectDetails.getQuantityConversion());
            }
            //循环出每个工程 八月份
            if (projectDetails.getAugus().equals("是")) {
                //如果百分位是0的话默认为0.0
                if (b.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue() == 0) {

                    //八月
                    projectDetails.setCalAugus(0.0);
                }
                //四舍五入
                projectDetails.setCalAugus(b.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue());

            } else {
                projectDetails.setCalAugus(0 * projectDetails.getQuantityConversion());
            }
            //循环出每个工程 九月份
            if (projectDetails.getSeptember().equals("是")) {
                //如果百分位是0的话默认为0.0
                if (b.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue() == 0) {

                    //九月
                    projectDetails.setCalSeptember(0.0);
                }
                //四舍五入
                projectDetails.setCalSeptember(b.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue());

            } else {
                projectDetails.setCalSeptember(0 * projectDetails.getQuantityConversion());
            }

            //循环出每个工程 十月份
            if (projectDetails.getOctober().equals("是")) {
                //如果百分位是0的话默认为0.0
                if (b.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue() == 0) {

                    //十月
                    projectDetails.setCalOctober(0.0);
                }
                //四舍五入
                projectDetails.setCalOctober(b.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue());

            } else {
                projectDetails.setCalOctober(0 * projectDetails.getQuantityConversion());
            }

            //循环出每个工程 十一月份
            if (projectDetails.getNovember().equals("是")) {
                //如果百分位是0的话默认为0.0
                if (b.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue() == 0) {

                    //十一月
                    projectDetails.setCalNovember(0.0);
                }
                //四舍五入
                projectDetails.setCalNovember(b.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue());

            } else {
                projectDetails.setCalNovember(0 * projectDetails.getQuantityConversion());
            }

            //循环出每个工程 十二月份
            if (projectDetails.getDecember().equals("是")) {
                //如果百分位是0的话默认为0.0
                if (b.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue() == 0) {

                    //十二月
                    projectDetails.setCalDecember(0.0);
                }
                //四舍五入
                projectDetails.setCalDecember(b.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue());

            } else {
                projectDetails.setCalDecember(0 * projectDetails.getQuantityConversion());
            }


            log.debug(projectDetails);
        }
        return projectDetailList;
    }

    /**
     * 计算 人员信息（项目部人员）
     *
     * @param employeeList 员工的集合
     * @param weightList   权重的集合
     * @return 员工的集合  {@link List<Employee>}
     * @throws Exception
     * @author 朱迪炜
     */
    public List<Employee> calEmployee(List<Employee> employeeList,
                                      List<PersonnelAssignmentWeight> weightList) throws Exception {
        if (employeeList != null) {
            for (PersonnelAssignmentWeight weight : weightList) {
                for (Employee employee : employeeList) {

                    //一、计算人员信息的 职业（等效）资格赋值
                    if (employee.getPost().equals(weight.getPost()) &&
                            employee.getProQualification().equals(weight.getProQualification())) {
                        employee.setProQualificationScore(weight.getPostValue());
                    }

                    //二、计算从业年限赋值
                    //项目经理，并且工作年限大于两年，给0.4分
                    if ("项目经理".equals(employee.getPost())) {
                        if (employee.getWorkingAge() > 2) {
                            employee.setWorkingAgeScore(0.4);
                        }
                        //项目质检员，并且工作年限大于两年，给0.6分
                    } else if ("项目质检员".equals(employee.getPost())) {
                        if (employee.getWorkingAge() > 2) {
                            employee.setWorkingAgeScore(0.6);
                        }
                        //项目安全员，并且工作年限大于两年，给0.6分
                    } else if ("项目安全员".equals(employee.getPost())) {
                        if (employee.getWorkingAge() > 2) {
                            employee.setWorkingAgeScore(0.6);
                        }
                        //作业副班长,大于4年 0.4分，否则0.32
                    } else if ("作业副班长".equals(employee.getPost())) {
                        if (employee.getWorkingAge() > 4) {
                            employee.setWorkingAgeScore(0.4);
                        } else {
                            employee.setWorkingAgeScore(0.32);
                        }
                    }

                    //三、计算 专业工作业绩
                    if (employee.getPost().equals(weight.getPost()) &&
                            employee.getWorkExperience().equals((weight.getWorkExperience()))) {
                        employee.setWorkExperienceScore(weight.getWorkExperienceValue());
                    }

                    //四、计算 综合指标
                    //有的值超过了两位小数，比如  0.940000000001，需要进行截取
                    BigDecimal bg = new BigDecimal(employee.getProQualificationScore()
                            + employee.getWorkingAgeScore()
                            + employee.getWorkExperienceScore());
                    double num = bg.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
                    employee.setTotalScore(num);
                }
            }
        }
        return employeeList;
    }

    /**
     * 为VO赋值
     *
     * @param wb      工作簿对象，每个EXCEL有且仅有一个工作簿对象 {@link HSSFWorkbook}
     * @param index   sheet页的索引，从0开始 Sheet页对象{@link HSSFSheet}
     * @param clazz   需要set值的VO.class
     * @param cellStr 需要取的cell的索引，传int类型数组或null，传null表示所有cell都要
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
                                // System.out.println("实际值：" + cell.getStringCellValue());
                                // System.out.println("存入值：" + Double.valueOf(cell.getStringCellValue()));
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
