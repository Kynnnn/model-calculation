package com.ylkj.modelcal.vo;

import lombok.Data;

/**
 *工程明细
 */
@Data
public class ProjectDetails {

    // 1-12 月 (是或否)
    private  String January;

    private  String February;

    private  String March;

    private  String April;

    private  String May;

    private  String June;

    private  String July;

    private  String Augus;

    private  String September;

    private  String October;

    private  String November;

    private  String December;
    /**
     * 电压等级（kV）
     */
    private Integer VoltageLevel;
    /**
     * 工程合同额(万元）
     */
    private Double ProjectContractAmount;

    /**
     * 工程量折算(计算)
     */
    private Double QuantityConversion;


}
