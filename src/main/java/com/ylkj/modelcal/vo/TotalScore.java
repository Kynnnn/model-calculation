package com.ylkj.modelcal.vo;

import lombok.Data;


/**
 * 评分分指标
 */
@Data
public class TotalScore {

    //季度承载力
    private Integer QuarterlyBearingCapacity1;

    private Integer QuarterlyBearingCapacity2;

    private Integer QuarterlyBearingCapacity3;

    private Integer QuarterlyBearingCapacity4;

    //权重
    private  Double weight1;

    private  Double weight2;

    private  Double weight3;

    private  Double weight4;


    private  Double PersonnelAndQualificationMatching;

    private  Double EquipmentAndQualificationMatching;



    private Double total;


}
