package com.ylkj.modelcal.vo;

import lombok.Data;


/**
 * 综合评分
 */
@Data
public class TotalScore {

    /**
     * 季度承载力
     */
    private double quarterlyBearingCapacity1;

    private double quarterlyBearingCapacity2;

    private double quarterlyBearingCapacity3;

    private double quarterlyBearingCapacity4;

    /**
     * 权重
     */
    private  String weight1;

    private  String weight2;

    private  String weight3;

    private  String weight4;


    /**
     * 人员与资质匹配评分
     */
    private  Double personnelAndQualificationMatching;

    /**
     * 设备与资质匹配评分
     */
    private  Double equipmentAndQualificationMatching;


    private Double total;


}
