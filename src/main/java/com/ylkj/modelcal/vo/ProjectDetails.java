package com.ylkj.modelcal.vo;

import lombok.Data;

/**
 *工程明细
 */
@Data
public class ProjectDetails {

    // 1-12 月 (是或否)
    private  String january;

    private  String February;

    private  String March;

    private  String April;

    private  String May;

    private  String June;

    private  String July;

    private  String August;

    private  String September;

    private  String October;

    private  String November;

    private  String December;

    /**
     * 电压等级（kV）
     */
    private Integer voltageLevel;

    /**
     * 工程类别
     */
    private String projectType;

    /**
     * 工程合同额(万元）
     */
    private Double projectContractAmount;

    /**
     * 工程量折算(计算)
     */
    private Double quantityConversion;

    /**
     * 1-12月金额
     */
    private  Double calJanuary;

    private  Double calFebruary;

    private  Double calMarch;

    private  Double calApril;

    private  Double calMay;

    private  Double calJune;

    private  Double calJuly;

    private  Double calAugust;

    private  Double calSeptember;

    private  Double calOctober;

    private  Double calNovember;

    private  Double calDecember;
}
