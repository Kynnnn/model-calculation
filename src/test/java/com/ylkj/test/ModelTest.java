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
        projectDetails = calProjectDetails(projectDetails);

        //3.计算 工程类型
        List<ProjectType> projectTypeList = calProjectType(projectDetails);

        //4.计算 输变电工程项目部人员构成指标
        List<SubstationEngineering> substationEngineeringList = calSubstationEngineering(employees);

        //7.计算季度工程量及承载力
        List<QuarterlyQuantitiesBearing>  QuarterlyQuantitiesBearing =calQuarterlyQuantitiesBearing(projectDetails);

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
                if (Double.parseDouble(String.format("%.2f",CalJanuary)) == 0) {
                    //一月
                    projectDetails.setCalJanuary(0.0);
                }
                //四舍五入
                projectDetails.setCalJanuary(Double.parseDouble(String.format("%.3f",CalJanuary)));

            } else {
                projectDetails.setCalJanuary(0 * projectDetails.getQuantityConversion());
            }
            System.out.println(projectDetails.getCalJanuary());
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
            if (projectDetails.getAugust().equals("是")) {
                //如果百分位是0的话默认为0.0
                if (b.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue() == 0) {

                    //八月
                    projectDetails.setCalAugust(0.0);
                }
                //四舍五入
                projectDetails.setCalAugust(b.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue());

            } else {
                projectDetails.setCalAugust(0 * projectDetails.getQuantityConversion());
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


//            System.out.println(projectDetails.getCalJanuary());
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
     * 计算季度工程量及承载力
     *
     * @return
     */
    public List<QuarterlyQuantitiesBearing> calQuarterlyQuantitiesBearing(List<ProjectDetails> projectDetails) throws Exception {
        QuarterlyQuantitiesBearing quarterlyQuantitiesBearing =new QuarterlyQuantitiesBearing();
        //取出工程信息数据
        Double Sum1=0.0;
        Double Sum2=0.0;
        Double Sum3=0.0;
        Double Sum4=0.0;
        Double Sum5=0.0;
        Double Sum6=0.0;
        Double Sum7=0.0;
        Double Sum8=0.0;
        Double Sum9=0.0;
        Double Sum10=0.0;
        Double Sum11=0.0;
        Double Sum12=0.0;
        for (ProjectDetails projectDetail : projectDetails) {
            //一月
            Double SumJanuary=projectDetail.getCalJanuary();
            //二月
            Double SumFebruary=projectDetail.getCalFebruary();
            //三月
            Double SumMarch=projectDetail.getCalMarch();
            //四月
            Double SumApril=projectDetail.getCalApril();
            //五月
            Double SumMay=projectDetail.getCalMay();
            //六月
            Double SumJune=projectDetail.getCalJune();
            //七月
            Double SumJuly=projectDetail.getCalJuly();
            //八月
            Double SumAugust=projectDetail.getCalAugust();
            //九月
            Double SumSeptember=projectDetail.getCalSeptember();
            //十月
            Double SumOctober=projectDetail.getCalOctober();
            //十一月
            Double SumNovember=projectDetail.getCalNovember();
            //十二月
            Double SumDecember=projectDetail.getCalDecember();



            Sum1+=SumJanuary;
            Sum2+=SumFebruary;
            Sum3+=SumMarch;
            Sum4+=SumApril;
            Sum5+=SumMay;
            Sum6+=SumJune;
            Sum7+=SumJuly;
            Sum8+=SumAugust;
            Sum9+=SumSeptember;
            Sum10+=SumOctober;
            Sum11+=SumNovember;
            Sum12+=SumDecember;
        }
        //月工程量
        quarterlyQuantitiesBearing.setJanuaryEngineering(Double.parseDouble(String.format("%.2f",Sum1)));

        quarterlyQuantitiesBearing.setFebruaryEngineering(Double.parseDouble(String.format("%.2f",Sum2)));
        quarterlyQuantitiesBearing.setMarchEngineering(Double.parseDouble(String.format("%.2f",Sum3)));
        quarterlyQuantitiesBearing.setAprilEngineering(Double.parseDouble(String.format("%.2f",Sum4)));
        quarterlyQuantitiesBearing.setMayEngineering(Double.parseDouble(String.format("%.2f",Sum5)));
        quarterlyQuantitiesBearing.setJuneEngineering(Double.parseDouble(String.format("%.2f",Sum6)));
        quarterlyQuantitiesBearing.setJulyEngineering(Double.parseDouble(String.format("%.2f",Sum7)));
        quarterlyQuantitiesBearing.setAugustEngineering(Double.parseDouble(String.format("%.2f",Sum8)));
        quarterlyQuantitiesBearing.setSeptemberEngineering(Double.parseDouble(String.format("%.2f",Sum9)));
        quarterlyQuantitiesBearing.setOctoberEngineering(Double.parseDouble(String.format("%.2f",Sum10)));
        quarterlyQuantitiesBearing.setNovemberEngineering(Double.parseDouble(String.format("%.2f",Sum11)));
        quarterlyQuantitiesBearing.setDecemberEngineering(Double.parseDouble(String.format("%.2f",Sum12)));

        //季度工程量 1-3月
        quarterlyQuantitiesBearing.setQuarterlyEngineering1(Double.parseDouble(String.format("%.2f",(Sum1+Sum2+Sum3)/3)));
        //4-6月
        quarterlyQuantitiesBearing.setQuarterlyEngineering2(Double.parseDouble(String.format("%.2f",(Sum4+Sum5+Sum6)/3)));
        //7-9月
        quarterlyQuantitiesBearing.setQuarterlyEngineering3(Double.parseDouble(String.format("%.2f",(Sum7+Sum8+Sum9)/3)));
        //10-12
        quarterlyQuantitiesBearing.setQuarterlyEngineering4(Double.parseDouble(String.format("%.2f",(Sum10+Sum12+Sum12)/3)));

        //取出每个季度最大的值


        System.out.println(quarterlyQuantitiesBearing.getJanuaryEngineering());

        System.out.println(Sum1);

//    log.debug(quarterlyQuantitiesBearing);
//

        return null;
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

    /**
     * 计算 工程类型
     *
     * @param projectDetails
     * @return
     * @author 朱迪炜
     */
    public List<ProjectType> calProjectType(List<ProjectDetails> projectDetails) {

        /**
         * 工程合同款综合
         */
        Double totalAmount = 0D;

        /**
         * 高压线路工程 工程合同款总和
         */
        Double highLineAmount = 0D;

        /**
         * 高压变电工程 工程合同款总和
         */
        Double highVoltageAmount = 0D;
        /**
         * 中低压配网工程 工程合同款总和
         */
        Double lowVoltageAmount = 0D;

        /**
         * 总工程量
         */
        Double totalQuantities = 0D;

        /**
         * 高压线路工程 工程量
         */
        Double highLineQuantities = 0D;

        /**
         * 高压变电工程 工程量
         */
        Double highVoltageQuantities = 0D;

        /**
         * 中低压配网工程 工程量
         */
        Double lowVoltageQuantities = 0D;


        List<ProjectType> projectTypeList = new ArrayList<ProjectType>();

        //根据工程类型的不同，将工程合同款分别叠加
        for (ProjectDetails projectDetail : projectDetails) {
            totalAmount += projectDetail.getProjectContractAmount();
            totalQuantities += projectDetail.getQuantityConversion();
            if ("高压线路工程".equals(projectDetail.getProjectType())) {
                highLineAmount += projectDetail.getProjectContractAmount();
                highLineQuantities += projectDetail.getQuantityConversion();
            }
            if ("高压变电工程".equals(projectDetail.getProjectType())) {
                highVoltageAmount += projectDetail.getProjectContractAmount();
                highVoltageQuantities += projectDetail.getQuantityConversion();
            }
            if ("中低压配网工程".equals(projectDetail.getProjectType())) {
                lowVoltageAmount += projectDetail.getProjectContractAmount();
                lowVoltageQuantities += projectDetail.getQuantityConversion();
            }
        }

        //给每种工程类型赋值
        ProjectType projectType = new ProjectType();
        projectType.setProjectTypeName("高压线路工程");
        projectType.setProportionOfInvestment(String.format("%.2f", (highLineAmount / totalAmount) * 100) + "%");
        projectType.setProportionOfWorkQuantity(String.format("%.2f", (highLineQuantities / totalQuantities) * 100) + "%");
        projectType.setEngineeringAdjustmentCoefficient(Double.valueOf(String.format("%.2f", (highLineAmount / totalAmount + highLineQuantities / totalQuantities) / 2)));
        projectTypeList.add(projectType);

        projectType = new ProjectType();
        projectType.setProjectTypeName("高压变电工程");
        projectType.setProportionOfInvestment(String.format("%.2f", (highVoltageAmount / totalAmount) * 100) + "%");
        projectType.setProportionOfWorkQuantity(String.format("%.2f", (highVoltageQuantities / totalQuantities) * 100) + "%");
        projectType.setEngineeringAdjustmentCoefficient(Double.valueOf(String.format("%.2f", (highVoltageAmount / totalAmount + highVoltageQuantities / totalQuantities) / 2)));
        projectTypeList.add(projectType);

        projectType = new ProjectType();
        projectType.setProjectTypeName("中低压配网工程");
        projectType.setProportionOfInvestment(String.format("%.2f", (lowVoltageAmount / totalAmount) * 100) + "%");
        projectType.setProportionOfWorkQuantity(String.format("%.2f", (lowVoltageQuantities / totalQuantities) * 100) + "%");
        projectType.setEngineeringAdjustmentCoefficient(Double.valueOf(String.format("%.2f", (lowVoltageAmount / totalAmount + lowVoltageQuantities / totalQuantities) / 2)));
        projectTypeList.add(projectType);

        return projectTypeList;
    }

    /**
     * 计算 输变电工程项目部人员构成指标
     *
     * @param employees 人员信息的集合
     * @return
     */
    public List<SubstationEngineering> calSubstationEngineering(List<Employee> employees) {

        /**
         * 项目经理
         */
        Integer theoreticalValueManager = 0;
        Double actualManager = 0D;

        /**
         * 项目总工
         */
        Integer theoreticalValueEngineer = 0;
        Double actualEngineer = 0D;

        /**
         * 项目质检员
         */
        Integer theoreticalValueProQualityInspector = 0;
        Double actualProQualityInspector = 0D;

        /**
         * 项目安全员
         */
        Integer theoreticalValueProSafetyOfficer = 0;
        Double actualProSafetyOfficer = 0D;

        /**
         * 班长兼指挥
         */
        Integer theoreticalValueMonitorAndCommander = 0;
        Double actualMonitorAndCommander = 0D;

        /**
         * 安全员
         */
        Integer theoreticalValueSafetyOfficer = 0;
        Double actualSafetyOfficer = 0D;


        /**
         * 技术兼质检员
         */
        Integer theoreticalValueTechAndQualityInspector = 0;
        Double actualTechAndQualityInspector = 0D;

        /**
         * 技术兼质检员
         */
        Integer theoreticalValueDeputyShiftLeader = 0;
        Double actualDeputyShiftLeader = 0D;

        for (Employee employee : employees) {

            if ("项目经理".equals(employee.getPost())) {
                theoreticalValueManager++;
                if ("一级建造师".equals(employee.getProQualification()) || "二级建造师".equals(employee.getProQualification())) {
                    actualManager += employee.getTotalScore();
                }
            }
            if ("项目总工".equals(employee.getPost())) {
                theoreticalValueEngineer++;
                actualEngineer += employee.getTotalScore();
            }
            if ("项目质检员".equals(employee.getPost())) {
                theoreticalValueProQualityInspector++;
                actualProQualityInspector += employee.getTotalScore();
            }
            if ("项目安全员".equals(employee.getPost())) {
                theoreticalValueProSafetyOfficer++;
                actualProSafetyOfficer += employee.getTotalScore();
            }
            if ("班长兼指挥".equals(employee.getPost())) {
                theoreticalValueMonitorAndCommander++;
                actualMonitorAndCommander += employee.getTotalScore();
            }
            if ("安全员".equals(employee.getPost())) {
                theoreticalValueSafetyOfficer++;
                actualSafetyOfficer += employee.getTotalScore();
            }
            if ("技术兼质检员".equals(employee.getPost())) {
                theoreticalValueTechAndQualityInspector++;
                actualTechAndQualityInspector += employee.getTotalScore();
            }
            if ("作业副班长".equals(employee.getPost())) {
                theoreticalValueDeputyShiftLeader++;
                actualDeputyShiftLeader += employee.getTotalScore();
            }

        }
        List<SubstationEngineering> substationEngineeringList = new ArrayList<SubstationEngineering>();

        SubstationEngineering substationEngineering = new SubstationEngineering();
        //  装载VO类
        //  项目经理
        substationEngineering = loadVO("项目经理", theoreticalValueManager, 1D, actualManager);
        substationEngineeringList.add(substationEngineering);
        //  项目总工
        substationEngineering = loadVO("项目总工", theoreticalValueEngineer, 1D, actualEngineer);
        substationEngineeringList.add(substationEngineering);
        //  项目质检员
        substationEngineering = loadVO("项目质检员", theoreticalValueProQualityInspector, 1D, actualProQualityInspector);
        substationEngineeringList.add(substationEngineering);
        //  项目安全员
        substationEngineering = loadVO("项目安全员", theoreticalValueProSafetyOfficer, 1D, actualProSafetyOfficer);
        substationEngineeringList.add(substationEngineering);
        //  班长兼指挥
        substationEngineering = loadVO("班长兼指挥", theoreticalValueMonitorAndCommander, 0.6, actualMonitorAndCommander);
        substationEngineeringList.add(substationEngineering);
        //  安全员
        substationEngineering = loadVO("安全员", theoreticalValueSafetyOfficer, 0.6, actualSafetyOfficer);
        substationEngineeringList.add(substationEngineering);
        //  技术兼质检员
        substationEngineering = loadVO("技术兼质检员", theoreticalValueTechAndQualityInspector, 0.6, actualTechAndQualityInspector);
        substationEngineeringList.add(substationEngineering);
        //  作业副班长
        substationEngineering = loadVO("作业副班长", theoreticalValueDeputyShiftLeader, 0.6, actualDeputyShiftLeader);
        substationEngineeringList.add(substationEngineering);

        return substationEngineeringList;
    }


    /**
     * 装载 输变电工程项目部人员构成指标 VO类的方法
     *
     * @param post             职位
     * @param theoreticalValue 理论值
     * @param actual           实际值
     * @return
     */
    public SubstationEngineering loadVO(String post, Integer theoreticalValue, Double personnelLineEngineering, Double actual) {
        SubstationEngineering substationEngineering = new SubstationEngineering();
        //岗位
        substationEngineering.setPost(post);
        //理论指标值
        substationEngineering.setTheoreticalValue(theoreticalValue);
        // 实际指标值
        substationEngineering.setActual(Double.valueOf(String.format("%.2f", actual)));
        //人员折算系数-线路工程
        substationEngineering.setPersonnelLineEngineering(personnelLineEngineering);
        //人员折算系数-变电工程
        substationEngineering.setPersonnelSubstationEngineering(1D);
        //理论人员指标-线路工程
        substationEngineering.setTheoryLineEngineering(substationEngineering.getTheoreticalValue() * substationEngineering.getPersonnelLineEngineering());
        //理论人员指标-变电工程
        substationEngineering.setTheorySubstationEngineering(substationEngineering.getTheoreticalValue() * substationEngineering.getPersonnelSubstationEngineering());
        //实际人员指标-线路工程
        substationEngineering.setActualLineEngineering(Double.valueOf(String.format("%.2f", substationEngineering.getActual() * substationEngineering.getPersonnelLineEngineering())));
        //实际人员指标-变电工程
        substationEngineering.setActualSubstationEngineering(Double.valueOf(String.format("%.2f", substationEngineering.getActual() * substationEngineering.getPersonnelSubstationEngineering())));

        return substationEngineering;

    }
}
