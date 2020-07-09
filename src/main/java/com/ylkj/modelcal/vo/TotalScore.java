package com.ylkj.modelcal.vo;

import lombok.Data;


/**
 * 综合评分
 */
@Data
public class TotalScore {

    //季度承载力
    private Integer QuarterlyBearingCapacity1;

    private Integer QuarterlyBearingCapacity2;

    private Integer QuarterlyBearingCapacity3;

    private Integer QuarterlyBearingCapacity4;

    //权重
    private  String weight1;

    private  String weight2;

    private  String weight3;

    private  String weight4;


    private  Double PersonnelAndQualificationMatching;

    private  Double EquipmentAndQualificationMatching;



    private Double total;


}
