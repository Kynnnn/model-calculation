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
    private  String post;

    /**
     * 理论指标值
     */
    private  Integer theoreticalValue;

    /**
     * 实际指标值
     */
    private  Double actual;

    /**
     * 人员折算系数
     */
    private  Double personnel;

    /**
     * 理论人员指标
     */
    private  Double theory;

    /**
     * 实际人员指标
     */
    private  Double  actualPersonnel;

}
