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
import java.text.NumberFormat;
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

        //5.计算 配网工程项目部人员构成指标
        List<DistributionNetwork> distributionNetworkList = calDistributionNetwork(employees);
        // System.out.println("===================");
        // System.out.println(JSON.toJSONString(distributionNetworkList));

        //6.计算 施工承载力
        List<ConstructionBearingCapacity> bearingCapacities = calConstructionBearingCapacity(projectTypeList, substationEngineeringList, distributionNetworkList);
        // System.out.println("=============");
        // System.out.println(JSON.toJSONString(bearingCapacities));

        //7.计算 季度工程量及承载力
        QuarterlyQuantitiesBearing QuarterlyQuantitiesBearing = calQuarterlyQuantitiesBearing(projectDetails, bearingCapacities);

        //8.计算 综合评分
        TotalScore totalScore = calTotalScore(QuarterlyQuantitiesBearing);
        System.out.println(totalScore.getTotal());
    }

    /**
     * 工程明细表计算 1-12月
     */
    public List<ProjectDetails> calProjectDetails(List<ProjectDetails> projectDetailList) throws Exception {


        for (ProjectDetails projectDetails : projectDetailList) {
            //如果 是 的话计算
            Double CalJanuary = 1 * projectDetails.getQuantityConversion();
            //循环出每个工程 一月份
            if (projectDetails.getJanuary().equals("是")) {
                //如果百分位是0的话默认为0.0
                if (Double.parseDouble(String.format("%.2f", CalJanuary)) == 0) {
                    //一月
                    projectDetails.setCalJanuary(0.0);
                }
                //四舍五入
                projectDetails.setCalJanuary(Double.parseDouble(String.format("%.3f", CalJanuary)));

            } else {
                projectDetails.setCalJanuary(0 * projectDetails.getQuantityConversion());
            }

            //循环出每个工程 二月份
            if (projectDetails.getFebruary().equals("是")) {
                //如果百分位是0的话默认为0.0
                if (Double.parseDouble(String.format("%.2f", CalJanuary)) == 0) {

                    //二月
                    projectDetails.setCalFebruary(0.0);
                }
                //四舍五入
                projectDetails.setCalFebruary(Double.parseDouble(String.format("%.4f", CalJanuary)));


            } else {
                projectDetails.setCalFebruary(0 * projectDetails.getQuantityConversion());
            }
            //循环出每个工程 三月份
            if (projectDetails.getMarch().equals("是")) {
                //如果百分位是0的话默认为0.0
                if (Double.parseDouble(String.format("%.2f", CalJanuary)) == 0) {

                    //三月
                    projectDetails.setCalMarch(0.0);
                }
                //四舍五入
                projectDetails.setCalMarch(Double.parseDouble(String.format("%.4f", CalJanuary)));
            } else {
                projectDetails.setCalMarch(0 * projectDetails.getQuantityConversion());
            }
            //循环出每个工程 四月份
            if (projectDetails.getApril().equals("是")) {
                //如果百分位是0的话默认为0.0
                if (Double.parseDouble(String.format("%.3f", CalJanuary)) == 0) {

                    //四月
                    projectDetails.setCalApril(0.0);
                }
                //四舍五入
                projectDetails.setCalApril(Double.parseDouble(String.format("%.4f", CalJanuary)));
            } else {
                projectDetails.setCalApril(0 * projectDetails.getQuantityConversion());
            }
            //循环出每个工程 五月份
            if (projectDetails.getMay().equals("是")) {
                //如果百分位是0的话默认为0.0
                if (Double.parseDouble(String.format("%.2f", CalJanuary)) == 0) {

                    //五月
                    projectDetails.setCalMay(0.0);
                }
                //四舍五入
                projectDetails.setCalMay(Double.parseDouble(String.format("%.4f", CalJanuary)));

            } else {
                projectDetails.setCalMay(0 * projectDetails.getQuantityConversion());
            }
            //循环出每个工程 六月份
            if (projectDetails.getJune().equals("是")) {
                //如果百分位是0的话默认为0.0
                if (Double.parseDouble(String.format("%.2f", CalJanuary)) == 0) {

                    //六月
                    projectDetails.setCalJune(0.0);
                }
                //四舍五入
                projectDetails.setCalJune(Double.parseDouble(String.format("%.4f", CalJanuary)));

            } else {
                projectDetails.setCalJune(0 * projectDetails.getQuantityConversion());
            }
            //循环出每个工程 七月份
            if (projectDetails.getJuly().equals("是")) {
                //如果百分位是0的话默认为0.0
                if (Double.parseDouble(String.format("%.2f", CalJanuary)) == 0) {

                    //七月
                    projectDetails.setCalJuly(0.0);
                }
                //四舍五入
                projectDetails.setCalJuly(Double.parseDouble(String.format("%.4f", CalJanuary)));


            } else {
                projectDetails.setCalJuly(0 * projectDetails.getQuantityConversion());
            }
            //循环出每个工程 八月份
            if (projectDetails.getAugust().equals("是")) {
                //如果百分位是0的话默认为0.0
                if (Double.parseDouble(String.format("%.2f", CalJanuary)) == 0) {

                    //八月
                    projectDetails.setCalAugust(0.0);
                }
                //四舍五入
                projectDetails.setCalAugust(Double.parseDouble(String.format("%.4f", CalJanuary)));

            } else {
                projectDetails.setCalAugust(0 * projectDetails.getQuantityConversion());
            }
            //循环出每个工程 九月份
            if (projectDetails.getSeptember().equals("是")) {
                //如果百分位是0的话默认为0.0
                if (Double.parseDouble(String.format("%.2f", CalJanuary)) == 0) {

                    //九月
                    projectDetails.setCalSeptember(0.0);
                }
                //四舍五入
                projectDetails.setCalSeptember(Double.parseDouble(String.format("%.4f", CalJanuary)));

            } else {
                projectDetails.setCalSeptember(0 * projectDetails.getQuantityConversion());
            }

            //循环出每个工程 十月份
            if (projectDetails.getOctober().equals("是")) {
                //如果百分位是0的话默认为0.0
                if (Double.parseDouble(String.format("%.3f", CalJanuary)) == 0) {

                    //十月
                    projectDetails.setCalOctober(0.0);
                }
                //四舍五入
                projectDetails.setCalOctober(Double.parseDouble(String.format("%.5f", CalJanuary)));

            } else {
                projectDetails.setCalOctober(0 * projectDetails.getQuantityConversion());
            }

            //循环出每个工程 十一月份
            if (projectDetails.getNovember().equals("是")) {
                //如果百分位是0的话默认为0.0
                if (Double.parseDouble(String.format("%.3f", CalJanuary)) == 0) {

                    //十一月
                    projectDetails.setCalNovember(0.0);
                }
                //四舍五入
                projectDetails.setCalNovember(Double.parseDouble(String.format("%.5f", CalJanuary)));

            } else {
                projectDetails.setCalNovember(0 * projectDetails.getQuantityConversion());
            }

            //循环出每个工程 十二月份
            if (projectDetails.getDecember().equals("是")) {
                //如果百分位是0的话默认为0.0
                if (Double.parseDouble(String.format("%.3f", CalJanuary)) == 0) {

                    //十二月
                    projectDetails.setCalDecember(0.0);
                }
                //四舍五入
                projectDetails.setCalDecember(Double.parseDouble(String.format("%.5f", CalJanuary)));

            } else {
                projectDetails.setCalDecember(0 * projectDetails.getQuantityConversion());
            }


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
     * 计算l工程量及承载力
     *
     * @return
     */
    public QuarterlyQuantitiesBearing calQuarterlyQuantitiesBearing(List<ProjectDetails> projectDetails
            , List<ConstructionBearingCapacity> bearingCapacities
    ) throws Exception {
        QuarterlyQuantitiesBearing quarterlyQuantitiesBearing = new QuarterlyQuantitiesBearing();
        //取出工程信息数据
        Double Sum1 = 0.0;
        Double Sum2 = 0.0;
        Double Sum3 = 0.0;
        Double Sum4 = 0.0;
        Double Sum5 = 0.0;
        Double Sum6 = 0.0;
        Double Sum7 = 0.0;
        Double Sum8 = 0.0;
        Double Sum9 = 0.0;
        Double Sum10 = 0.0;
        Double Sum11 = 0.0;
        Double Sum12 = 0.0;
        for (ProjectDetails projectDetail : projectDetails) {
            //一月
            Double SumJanuary = projectDetail.getCalJanuary();
            //二月
            Double SumFebruary = projectDetail.getCalFebruary();
            //三月
            Double SumMarch = projectDetail.getCalMarch();
            //四月
            Double SumApril = projectDetail.getCalApril();
            //五月
            Double SumMay = projectDetail.getCalMay();
            //六月
            Double SumJune = projectDetail.getCalJune();
            //七月
            Double SumJuly = projectDetail.getCalJuly();
            //八月
            Double SumAugust = projectDetail.getCalAugust();
            //九月
            Double SumSeptember = projectDetail.getCalSeptember();
            //十月
            Double SumOctober = projectDetail.getCalOctober();
            //十一月
            Double SumNovember = projectDetail.getCalNovember();
            //十二月
            Double SumDecember = projectDetail.getCalDecember();


            Sum1 += SumJanuary;
            Sum2 += SumFebruary;
            Sum3 += SumMarch;
            Sum4 += SumApril;
            Sum5 += SumMay;
            Sum6 += SumJune;
            Sum7 += SumJuly;
            Sum8 += SumAugust;
            Sum9 += SumSeptember;
            Sum10 += SumOctober;
            Sum11 += SumNovember;
            Sum12 += SumDecember;
        }
        //月工程量
        quarterlyQuantitiesBearing.setJanuaryEngineering(Double.parseDouble(String.format("%.2f", Sum1)));
        quarterlyQuantitiesBearing.setFebruaryEngineering(Double.parseDouble(String.format("%.2f", Sum2)));
        quarterlyQuantitiesBearing.setMarchEngineering(Double.parseDouble(String.format("%.2f", Sum3)));
        quarterlyQuantitiesBearing.setAprilEngineering(Double.parseDouble(String.format("%.2f", Sum4)));
        quarterlyQuantitiesBearing.setMayEngineering(Double.parseDouble(String.format("%.2f", Sum5)));
        quarterlyQuantitiesBearing.setJuneEngineering(Double.parseDouble(String.format("%.2f", Sum6)));
        quarterlyQuantitiesBearing.setJulyEngineering(Double.parseDouble(String.format("%.2f", Sum7)));
        quarterlyQuantitiesBearing.setAugustEngineering(Double.parseDouble(String.format("%.2f", Sum8)));
        quarterlyQuantitiesBearing.setSeptemberEngineering(Double.parseDouble(String.format("%.2f", Sum9)));
        quarterlyQuantitiesBearing.setOctoberEngineering(Double.parseDouble(String.format("%.2f", Sum10)));
        quarterlyQuantitiesBearing.setNovemberEngineering(Double.parseDouble(String.format("%.2f", Sum11)));
        quarterlyQuantitiesBearing.setDecemberEngineering(Double.parseDouble(String.format("%.2f", Sum12)));


        //季度工程量 1-3月
        quarterlyQuantitiesBearing.setQuarterlyEngineering1(Double.parseDouble(String.format("%.2f", (Sum1 + Sum2 + Sum3) / 3)));
        //4-6月
        quarterlyQuantitiesBearing.setQuarterlyEngineering2(Double.parseDouble(String.format("%.2f", (Sum4 + Sum5 + Sum6) / 3)));
        //7-9月
        quarterlyQuantitiesBearing.setQuarterlyEngineering3(Double.parseDouble(String.format("%.2f", (Sum7 + Sum8 + Sum9) / 3)));
        //10-12
        quarterlyQuantitiesBearing.setQuarterlyEngineering4(Double.parseDouble(String.format("%.2f", (Sum10 + Sum11 + Sum12) / 3)));
        //实际承载力
        NumberFormat nf = NumberFormat.getNumberInstance();
        nf.setMaximumFractionDigits(1);
        double CalActualBearingCapacity4 = 0;
        for (ConstructionBearingCapacity bearingCapacity : bearingCapacities) {
            if (bearingCapacity.getIndicatorName().equals("线路标准工程承载力")) {
                quarterlyQuantitiesBearing.setActualBearingCapacity1(Double.parseDouble(String.format("%.3f", bearingCapacity.getActual())));
            }
            if (bearingCapacity.getIndicatorName().equals("变电标准工程承载力")) {
                quarterlyQuantitiesBearing.setActualBearingCapacity2(Double.parseDouble(String.format("%.3f", bearingCapacity.getActual())));
            }
            if (bearingCapacity.getIndicatorName().equals("配网标准工程承载力")) {
                quarterlyQuantitiesBearing.setActualBearingCapacity3(Double.parseDouble(String.format("%.3f", bearingCapacity.getActual())));
            }
            if (bearingCapacity.getIndicatorName().equals("企业施工承载力")) {
                quarterlyQuantitiesBearing.setActualBearingCapacity4(Double.parseDouble(String.format("%.3f", bearingCapacity.getActual())));
                CalActualBearingCapacity4 = bearingCapacity.getActual();
            }

        }

        //月承载力饱和度
        //一月份
        double a = quarterlyQuantitiesBearing.getJanuaryEngineering();
        double b = CalActualBearingCapacity4;
        double c = a / b;
        String d = (String.format("%.2f", c * 100) + "%");
        quarterlyQuantitiesBearing.setJanuaryBearingCapacitySaturation(d);

        //二月份
        a = quarterlyQuantitiesBearing.getFebruaryEngineering();
        c = a / b;
        d = (String.format("%.2f", c * 100) + "%");
        quarterlyQuantitiesBearing.setFebruaryBearingCapacitySaturation(d);

        //三月份
        a = quarterlyQuantitiesBearing.getMarchEngineering();
        c = a / b;
        d = (String.format("%.2f", c * 100) + "%");
        quarterlyQuantitiesBearing.setMarchBearingCapacitySaturation(d);

        //四月份
        a = quarterlyQuantitiesBearing.getAprilEngineering();
        c = a / b;
        d = (String.format("%.2f", c * 100) + "%");
        quarterlyQuantitiesBearing.setAprilBearingCapacitySaturation(d);

        //五月份
        a = quarterlyQuantitiesBearing.getMayEngineering();
        c = a / b;
        d = (String.format("%.2f", c * 100) + "%");
        quarterlyQuantitiesBearing.setMayBearingCapacitySaturation(d);

        //六月份
        a = quarterlyQuantitiesBearing.getJuneEngineering();
        c = a / b;
        d = (String.format("%.2f", c * 100) + "%");
        quarterlyQuantitiesBearing.setJuneBearingCapacitySaturation(d);

        //七月份
        a = quarterlyQuantitiesBearing.getJulyEngineering();
        c = a / b;
        d = (String.format("%.2f", c * 100) + "%");
        quarterlyQuantitiesBearing.setJulyBearingCapacitySaturation(d);

        //八月份
        a = quarterlyQuantitiesBearing.getAugustEngineering();
        c = a / b;
        d = (String.format("%.2f", c * 100) + "%");
        quarterlyQuantitiesBearing.setAugustBearingCapacitySaturation(d);

        //九月份
        a = quarterlyQuantitiesBearing.getSeptemberEngineering();
        c = a / b;
        d = (String.format("%.2f", c * 100) + "%");
        quarterlyQuantitiesBearing.setSeptemberBearingCapacitySaturation(d);

        //十月份
        a = quarterlyQuantitiesBearing.getOctoberEngineering();
        c = a / b;
        d = (String.format("%.2f", c * 100) + "%");
        quarterlyQuantitiesBearing.setOctoberBearingCapacitySaturation(d);

        //十一月份
        a = quarterlyQuantitiesBearing.getNovemberEngineering();
        c = a / b;
        d = (String.format("%.2f", c * 100) + "%");
        quarterlyQuantitiesBearing.setNovemberBearingCapacitySaturation(d);

        //十二月份
        a = quarterlyQuantitiesBearing.getDecemberEngineering();
        c = a / b;
        d = (String.format("%.2f", c * 100) + "%");
        quarterlyQuantitiesBearing.setDecemberBearingCapacitySaturation(d);

        //季度承载力饱和度  1季度
//        double max=0;
//        double  aa=Double.parseDouble(quarterlyQuantitiesBearing.getJanuaryBearingCapacitySaturation());
//        //
//        double  bb=Double.parseDouble(quarterlyQuantitiesBearing.getFebruaryBearingCapacitySaturation());
//
//        double cc=Double.parseDouble(quarterlyQuantitiesBearing.getMarchBearingCapacitySaturation());
//        max=aa>bb?aa:bb;
//        max=cc>max?cc:max;
//        quarterlyQuantitiesBearing.setQuarterlyEngineering1(Double.parseDouble(String.format("%.2f",quarterlyQuantitiesBearing.getMarchBearingCapacitySaturation() * 100) + "%"));
//        quarterlyQuantitiesBearing.setQuarterlyEngineering2(Double.parseDouble(String.format("%.2f",quarterlyQuantitiesBearing.getJuneBearingCapacitySaturation() * 100) + "%"));
//        quarterlyQuantitiesBearing.setQuarterlyEngineering3(Double.parseDouble(String.format("%.2f",quarterlyQuantitiesBearing.getJulyBearingCapacitySaturation() * 100) + "%"));
//        quarterlyQuantitiesBearing.setQuarterlyEngineering4( Double.parseDouble(String.format("%.2f",quarterlyQuantitiesBearing.getOctoberBearingCapacitySaturation() * 100) + "%"));
//        quarterlyQuantitiesBearing.setQuarterlyEngineering1(Double.parseDouble(quarterlyQuantitiesBearing.getMarchBearingCapacitySaturation()));
        System.out.println(quarterlyQuantitiesBearing.getMarchBearingCapacitySaturation()+"wwwwwwwww");
//        quarterlyQuantitiesBearing.setQuarterlyEngineering2(Double.parseDouble(quarterlyQuantitiesBearing.getJuneBearingCapacitySaturation()));
//        quarterlyQuantitiesBearing.setQuarterlyEngineering3(Double.parseDouble(quarterlyQuantitiesBearing.getJulyBearingCapacitySaturation()));
//        quarterlyQuantitiesBearing.setQuarterlyEngineering4( Double.parseDouble(quarterlyQuantitiesBearing.getOctoberBearingCapacitySaturation()));
        System.out.println(quarterlyQuantitiesBearing.getQuarterlyEngineering1());
        //承载力饱和度

        quarterlyQuantitiesBearing.setBearingCapacitySaturation1(quarterlyQuantitiesBearing.getQuarterlyEngineering1()/CalActualBearingCapacity4);
        quarterlyQuantitiesBearing.setBearingCapacitySaturation2(quarterlyQuantitiesBearing.getQuarterlyEngineering2()/CalActualBearingCapacity4);
        quarterlyQuantitiesBearing.setBearingCapacitySaturation3(quarterlyQuantitiesBearing.getQuarterlyEngineering3()/CalActualBearingCapacity4);
        quarterlyQuantitiesBearing.setBearingCapacitySaturation4(quarterlyQuantitiesBearing.getQuarterlyEngineering4()/CalActualBearingCapacity4);

        quarterlyQuantitiesBearing.setSingleQuarterRating1(Double.parseDouble(String.valueOf(100-(0.6-quarterlyQuantitiesBearing.getBearingCapacitySaturation1()*0))));
        quarterlyQuantitiesBearing.setSingleQuarterRating2(Double.parseDouble(String.valueOf(100-(0.6-quarterlyQuantitiesBearing.getBearingCapacitySaturation2()*0))));
        quarterlyQuantitiesBearing.setSingleQuarterRating3(Double.parseDouble(String.valueOf(100-(0.6-quarterlyQuantitiesBearing.getBearingCapacitySaturation3()*0))));
        quarterlyQuantitiesBearing.setSingleQuarterRating4(Double.parseDouble(String.valueOf(100-(0.6-quarterlyQuantitiesBearing.getBearingCapacitySaturation4()*0))));
        System.out.println(  quarterlyQuantitiesBearing.getSingleQuarterRating1()+"aaa1111");

    //单季度评分



        return quarterlyQuantitiesBearing;
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
         * 作业副班长
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
        substationEngineering = loadSubstationEngineeringVO("项目经理", theoreticalValueManager, 1D, actualManager);
        substationEngineeringList.add(substationEngineering);
        //  项目总工
        substationEngineering = loadSubstationEngineeringVO("项目总工", theoreticalValueEngineer, 1D, actualEngineer);
        substationEngineeringList.add(substationEngineering);
        //  项目质检员
        substationEngineering = loadSubstationEngineeringVO("项目质检员", theoreticalValueProQualityInspector, 1D, actualProQualityInspector);
        substationEngineeringList.add(substationEngineering);
        //  项目安全员
        substationEngineering = loadSubstationEngineeringVO("项目安全员", theoreticalValueProSafetyOfficer, 1D, actualProSafetyOfficer);
        substationEngineeringList.add(substationEngineering);
        //  班长兼指挥
        substationEngineering = loadSubstationEngineeringVO("班长兼指挥", theoreticalValueMonitorAndCommander, 0.6, actualMonitorAndCommander);
        substationEngineeringList.add(substationEngineering);
        //  安全员
        substationEngineering = loadSubstationEngineeringVO("安全员", theoreticalValueSafetyOfficer, 0.6, actualSafetyOfficer);
        substationEngineeringList.add(substationEngineering);
        //  技术兼质检员
        substationEngineering = loadSubstationEngineeringVO("技术兼质检员", theoreticalValueTechAndQualityInspector, 0.6, actualTechAndQualityInspector);
        substationEngineeringList.add(substationEngineering);
        //  作业副班长
        substationEngineering = loadSubstationEngineeringVO("作业副班长", theoreticalValueDeputyShiftLeader, 0.6, actualDeputyShiftLeader);
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
    public SubstationEngineering loadSubstationEngineeringVO(String post, Integer theoreticalValue, Double personnelLineEngineering, Double actual) {
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

    /**
     * 计算 配网工程项目部人员构成指标
     *
     * @param employees 人员信息的集合
     * @return {@link List<DistributionNetwork>}
     */
    public List<DistributionNetwork> calDistributionNetwork(List<Employee> employees) {

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
         * 班长兼指挥
         */
        Integer theoreticalValueMonitorAndCommander = 0;
        Double actualMonitorAndCommander = 0D;

        /**
         * 作业副班长
         */
        Integer theoreticalValueDeputyShiftLeader = 0;
        Double actualDeputyShiftLeader = 0D;

        for (Employee employee : employees) {

            if ("项目经理".equals(employee.getPost())) {
                theoreticalValueManager++;
                actualManager += employee.getTotalScore();
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
            if ("安全员".equals(employee.getPost())) {
                theoreticalValueSafetyOfficer++;
                actualSafetyOfficer += employee.getTotalScore();
            }
            if ("技术兼质检员".equals(employee.getPost())) {
                theoreticalValueTechAndQualityInspector++;
                actualTechAndQualityInspector += employee.getTotalScore();
            }
            if ("班长兼指挥".equals(employee.getPost())) {
                theoreticalValueMonitorAndCommander++;
                actualMonitorAndCommander += employee.getTotalScore();
            }
            if ("作业副班长".equals(employee.getPost())) {
                theoreticalValueDeputyShiftLeader++;
                actualDeputyShiftLeader += employee.getTotalScore();
            }

        }
        List<DistributionNetwork> substationEngineeringList = new ArrayList<DistributionNetwork>();

        DistributionNetwork distributionNetwork = new DistributionNetwork();
        //  装载VO类
        //  项目经理
        distributionNetwork = loadDistributionNetworkVO("项目经理", theoreticalValueManager, actualManager, 1D);
        substationEngineeringList.add(distributionNetwork);
        //  项目总工
        distributionNetwork = loadDistributionNetworkVO("项目总工", theoreticalValueEngineer, actualEngineer, 1D);
        substationEngineeringList.add(distributionNetwork);
        //  项目质检员
        distributionNetwork = loadDistributionNetworkVO("项目质检员", theoreticalValueProQualityInspector, actualProQualityInspector, 1D);
        substationEngineeringList.add(distributionNetwork);
        //  项目安全员
        distributionNetwork = loadDistributionNetworkVO("项目安全员", theoreticalValueProSafetyOfficer, actualProSafetyOfficer, 1D);
        substationEngineeringList.add(distributionNetwork);
        //  安全员
        distributionNetwork = loadDistributionNetworkVO("安全员", theoreticalValueSafetyOfficer, actualSafetyOfficer, 0.7);
        substationEngineeringList.add(distributionNetwork);
        //  技术兼质检员
        distributionNetwork = loadDistributionNetworkVO("技术兼质检员", theoreticalValueTechAndQualityInspector, actualTechAndQualityInspector, 0.8);
        substationEngineeringList.add(distributionNetwork);
        //  班长兼指挥
        distributionNetwork = loadDistributionNetworkVO("班长兼指挥", theoreticalValueMonitorAndCommander, actualMonitorAndCommander, 0.8);
        substationEngineeringList.add(distributionNetwork);
        //  作业副班长
        distributionNetwork = loadDistributionNetworkVO("作业副班长", theoreticalValueDeputyShiftLeader, actualDeputyShiftLeader, 0.8);
        substationEngineeringList.add(distributionNetwork);

        return substationEngineeringList;

    }

    /**
     * 装载 配网工程项目部人员构成指标 VO类
     *
     * @param post             岗位
     * @param theoreticalValue 理论值
     * @param actual           实际值
     * @param personnel        人员折算系数
     * @return
     */
    public DistributionNetwork loadDistributionNetworkVO(String post, Integer theoreticalValue, Double actual, Double personnel) {
        DistributionNetwork distributionNetwork = new DistributionNetwork();
        //岗位
        distributionNetwork.setPost(post);
        //理论指标值
        distributionNetwork.setTheoreticalValue(theoreticalValue);
        // 实际指标值
        distributionNetwork.setActual(Double.valueOf(String.format("%.2f", actual)));
        // 人员折算系数
        distributionNetwork.setPersonnel(personnel);
        // 理论人员指标
        distributionNetwork.setTheory(Math.floor(distributionNetwork.getTheoreticalValue() * distributionNetwork.getPersonnel()));
        // 实际人员指标
        distributionNetwork.setActualPersonnel(Double.valueOf(String.format("%.2f", distributionNetwork.getActual() * distributionNetwork.getPersonnel())));

        // Double.valueOf(String.format("%.2f", distributionNetwork.getTheoreticalValue() * distributionNetwork.getPersonnel()));

        return distributionNetwork;
    }

    /**
     * 计算 施工承载力
     *
     * @param projectTypeList           工程类型
     * @param substationEngineeringList 输变电工程项目部人员构成指标
     * @param distributionNetworkList   配网工程项目部人员构成指标
     * @return {@link List<ConstructionBearingCapacity>}
     */
    public List<ConstructionBearingCapacity> calConstructionBearingCapacity(List<ProjectType> projectTypeList,
                                                                            List<SubstationEngineering> substationEngineeringList,
                                                                            List<DistributionNetwork> distributionNetworkList) {

        /**
         * 线路标准工程承载力
         */
        Double theoreticalValueLine;
        Double actualLine;

        /**
         * 变电标准工程承载力
         */
        Double theoreticalValueVoltage;
        Double actualVoltage;

        /**
         * 配网标准工程承载力
         */
        Double theoreticalValueNetwork;
        Double actualNetwork;

        /**
         * 企业施工承载力
         */
        Double theoreticalValueConstruction;
        Double actualConstruction;

        /**
         * 理论人员指标-线路工程 最小值
         */
        Double theoryLineEngineeringMin = 9999.0;

        /**
         * 实际人员指标-线路工程 最小值
         */
        Double actualLineEngineeringMin = 9999.0;

        /**
         * 变电标准工程承载力-理论指标值 最小值
         */
        Double theoreticalValueVoltageMin = 9999.0;

        /**
         * 变电标准工程承载力-实际指标值 最小值
         */
        Double actualVoltageMin = 9999.0;

        /**
         * 配网标准工程承载力-理论指标值 最小值
         */
        Double theoreticalValueNetworkMin = 9999.0;


        /**
         * 配网标准工程承载力-实际指标值 最小值
         */
        Double actualNetworkMin = 9999.0;

        for (SubstationEngineering substationEngineering : substationEngineeringList) {
            //取当前值与最小值作比较，循环取小
            Double lineEngineering = substationEngineering.getTheoryLineEngineering();
            if (lineEngineering < theoryLineEngineeringMin) {
                theoryLineEngineeringMin = lineEngineering;
            }
            Double actualLineEngineering = substationEngineering.getActualLineEngineering();
            if (actualLineEngineering < actualLineEngineeringMin) {
                actualLineEngineeringMin = actualLineEngineering;
            }
            Double theorySubstationEngineering = substationEngineering.getTheorySubstationEngineering();
            if (theorySubstationEngineering < theoreticalValueVoltageMin) {
                theoreticalValueVoltageMin = theorySubstationEngineering;
            }
            Double actualSubstationEngineering = substationEngineering.getActualSubstationEngineering();
            if (actualSubstationEngineering < actualVoltageMin) {
                actualVoltageMin = actualSubstationEngineering;
            }
        }

        for (DistributionNetwork distributionNetwork : distributionNetworkList) {
            Double theory = distributionNetwork.getTheory();
            if (theory < theoreticalValueNetworkMin) {
                theoreticalValueNetworkMin = theory;
            }
            Double actualPersonnel = distributionNetwork.getActualPersonnel();
            if (actualPersonnel < actualNetworkMin) {
                actualNetworkMin = actualPersonnel;
            }
        }

        //线路标准工程承载力-理论指标值
        theoreticalValueLine = projectTypeList.get(0).getEngineeringAdjustmentCoefficient() * theoryLineEngineeringMin;
        //线路标准工程承载力-实际指标值
        actualLine = projectTypeList.get(0).getEngineeringAdjustmentCoefficient() * actualLineEngineeringMin;

        //变电标准工程承载力-理论指标值
        theoreticalValueVoltage = projectTypeList.get(1).getEngineeringAdjustmentCoefficient() * theoreticalValueVoltageMin;
        //变电标准工程承载力-实际指标值
        actualVoltage = projectTypeList.get(1).getEngineeringAdjustmentCoefficient() * actualVoltageMin;

        //配网标准工程承载力--理论指标值
        theoreticalValueNetwork = projectTypeList.get(2).getEngineeringAdjustmentCoefficient() * theoreticalValueNetworkMin;
        //配网标准工程承载力-实际指标值
        actualNetwork = projectTypeList.get(2).getEngineeringAdjustmentCoefficient() * actualNetworkMin;

        //企业施工承载力-理论指标值
        theoreticalValueConstruction = theoreticalValueLine + theoreticalValueVoltage + theoreticalValueNetwork;
        //企业施工承载力-实际指标值
        actualConstruction = actualLine + actualVoltage + actualNetwork;

        List<ConstructionBearingCapacity> capacityList = new ArrayList<ConstructionBearingCapacity>();
        ConstructionBearingCapacity capacity = new ConstructionBearingCapacity();

        //线路标准工程承载力
        capacity = loadConstructionBearingCapacityVO("线路标准工程承载力", theoreticalValueLine, actualLine);
        capacityList.add(capacity);

        //变电标准工程承载力
        capacity = loadConstructionBearingCapacityVO("变电标准工程承载力", theoreticalValueVoltage, actualVoltage);
        capacityList.add(capacity);

        //配网标准工程承载力
        capacity = loadConstructionBearingCapacityVO("配网标准工程承载力", theoreticalValueNetwork, actualNetwork);
        capacityList.add(capacity);

        //企业施工承载力
        capacity = loadConstructionBearingCapacityVO("企业施工承载力", theoreticalValueConstruction, actualConstruction);
        capacityList.add(capacity);

        return capacityList;
    }

    /**
     * 装载 施工承载力 VO的方法
     *
     * @param indicatorName    指标名称
     * @param theoreticalValue 理论值
     * @param actual           实际值
     * @return
     */
    public ConstructionBearingCapacity loadConstructionBearingCapacityVO(String indicatorName, Double theoreticalValue, Double actual) {
        ConstructionBearingCapacity capacity = new ConstructionBearingCapacity();
        capacity.setIndicatorName(indicatorName);
        capacity.setTheoreticalValue(theoreticalValue);
        capacity.setActual(actual);

        return capacity;
    }

    /**
     * 计算 综合评分
     *
     * @param QuarterlyQuantitiesBearing 季度承载力
     * @return
     */
    public TotalScore calTotalScore(QuarterlyQuantitiesBearing QuarterlyQuantitiesBearing) {
        /**
         * 权重
         */
        Double weight1;
        Double weight2;
        Double weight3;
        Double weight4;

        //合计
        Double total;


        EnterpriseInformation enterpriseInformation = new EnterpriseInformation();
        TotalScore totalScore = new TotalScore();
        //季度承载力评分
        totalScore.setQuarterlyBearingCapacity1(QuarterlyQuantitiesBearing.getSingleQuarterRating1());
        totalScore.setQuarterlyBearingCapacity2(QuarterlyQuantitiesBearing.getSingleQuarterRating2());
        totalScore.setQuarterlyBearingCapacity3(QuarterlyQuantitiesBearing.getSingleQuarterRating3());
        totalScore.setQuarterlyBearingCapacity4(QuarterlyQuantitiesBearing.getSingleQuarterRating4());
        //权重
        totalScore.setWeight1("25%");
        totalScore.setWeight2("25%");
        totalScore.setWeight3("25%");
        totalScore.setWeight4("25%");


        //人员与资质匹配评分
        if ("否".equals(enterpriseInformation.getProjectTypeEnterpriseQualification1())) {
            totalScore.setPersonnelAndQualificationMatching(-25D);
        } else {
            totalScore.setPersonnelAndQualificationMatching(0D);
        }

        //设备与资质匹配评分
        if ("否".equals(enterpriseInformation.getProjectTypeEnterpriseQualification2()) ||
                "否".equals(enterpriseInformation.getProjectTypeEnterpriseQualification3()) ||
                "否".equals(enterpriseInformation.getProjectTypeEnterpriseQualification4())
        ) {
            totalScore.setEquipmentAndQualificationMatching(-25D);
        } else {
            totalScore.setEquipmentAndQualificationMatching(0D);
        }

        //合计
        weight1 = Double.valueOf(totalScore.getWeight1().split("%")[0])/100;
        weight2 = Double.valueOf(totalScore.getWeight2().split("%")[0])/100;
//        System.out.println(totalScore.getWeight3());
        weight3 = Double.valueOf(totalScore.getWeight3().split("%")[0])/100;
        weight4 = Double.valueOf(totalScore.getWeight4().split("%")[0])/100;
        System.out.println(totalScore.getQuarterlyBearingCapacity1()+"a");
        System.out.println(totalScore.getQuarterlyBearingCapacity2());
        System.out.println(totalScore.getQuarterlyBearingCapacity3());
        System.out.println(totalScore.getQuarterlyBearingCapacity4());
        System.out.println( totalScore.getPersonnelAndQualificationMatching());
        System.out.println( totalScore.getEquipmentAndQualificationMatching()+"aa");
        total = weight1 * totalScore.getQuarterlyBearingCapacity1() +
                weight2 * totalScore.getQuarterlyBearingCapacity2() +
                weight3 * totalScore.getQuarterlyBearingCapacity3() +
                weight4 * totalScore.getQuarterlyBearingCapacity4() +
                totalScore.getPersonnelAndQualificationMatching() +
                totalScore.getEquipmentAndQualificationMatching();

        totalScore.setTotal(total);

        return totalScore;
    }
}
