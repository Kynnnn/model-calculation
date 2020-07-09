package com.ylkj.modelcal.vo;

import lombok.Data;

/**
 * 输变电工程项目部人员构成指标
 */
@Data
public class SubstationEngineering {
    /**
     * 岗位
     */
    private  String Post;

    /**
     * 理论指标值
     */
    private  Double TheoreticalValue;

    /**
     * 实际指标值
     */
    private  Double Actual;

    /**
     * 人员折算系数（线路工程）
     */
    private  Double PersonnelLineEngineering;

    /**
     * 人员折算系数(变电工程)
     */
    private  Double PersonnelSubstationEngineering;

    /**
     * 理论人员指标（线路工程）
     */
    private  Double TheoryLineEngineering;

    /**
     * 理论人员指标(变电工程)
     */
    private  Double TheorySubstationEngineering;

    /**
     * 实际人员指标（线路工程）
     */
    private  Double ActualLineEngineering;

    /**
     * 实际人员指标(变电工程)
     */
    private  Double ActualSubstationEngineering;



}
