package com.ylkj.modelcal.vo;

import lombok.Data;

/**
 * 配网工程项目部人员构成指标
 */
@Data
public class DistributionNetwork {


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
     * 人员折算系数
     */
    private  Double Personnel;

    /**
     * 理论人员指标
     */
    private  Double Theory;

    /**
     * 实际人员指标
     */
    private  Double  ActualPersonnel;



}
