package com.ylkj.modelcal.vo;

import lombok.Data;

/**
 * 输变电工程项目部人员构成指标
 *
 * @author FuHongfei
 */
@Data
public class SubstationEngineering {
    /**
     * 岗位
     */
    private String post;

    /**
     * 理论指标值
     */
    private Integer theoreticalValue;

    /**
     * 实际指标值
     */
    private Double actual;

    /**
     * 人员折算系数（线路工程）
     */
    private Double personnelLineEngineering;

    /**
     * 人员折算系数(变电工程)
     */
    private Double personnelSubstationEngineering;

    /**
     * 理论人员指标（线路工程）
     */
    private Double theoryLineEngineering;

    /**
     * 理论人员指标(变电工程)
     */
    private Double theorySubstationEngineering;

    /**
     * 实际人员指标（线路工程）
     */
    private Double actualLineEngineering;

    /**
     * 实际人员指标(变电工程)
     */
    private Double actualSubstationEngineering;


}
