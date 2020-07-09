package com.ylkj.modelcal.vo;


import lombok.Data;

/**
 * 季度工程量及承载力
 */
@Data
public class QuarterlyQuantitiesBearing {

    //月工程量 1-12月
    private  Double JanuaryEngineering;

    private  Double FebruaryEngineering;

    private  Double MarchEngineering;

    private  Double AprilEngineering;

    private  Double MayEngineering;

    private  Double JuneEngineering;

    private  Double JulyEngineering;

    private  Double AugustEngineering;

    private  Double SeptemberEngineering;

    private  Double OctoberEngineering;

    private  Double NovemberEngineering;

    private  Double DecemberEngineering;

    //月承载力饱和度1-12月

    private  Double JanuaryBearingCapacitySaturation;

    private  Double FebruaryBearingCapacitySaturation;

    private  Double MarchBearingCapacitySaturation;

    private  Double AprilBearingCapacitySaturation;

    private  Double MayBearingCapacitySaturation;

    private  Double JuneBearingCapacitySaturation;

    private  Double JulyBearingCapacitySaturation;

    private  Double AugustBearingCapacitySaturation;

    private  Double SeptemberBearingCapacitySaturation;

    private  Double OctoberBearingCapacitySaturation;

    private  Double NovemberBearingCapacitySaturation;

    private  Double DecemberBearingCapacitySaturation;

    //季度工程 1-4季度
    private  Double QuarterlyEngineering1;

    private  Double QuarterlyEngineering2;

    private  Double QuarterlyEngineering3;

    private  Double QuarterlyEngineering4;

    //季度承载力饱和度 1-4季度
    private  Double QuarterlyBearingCapacitySaturation1;

    private  Double QuarterlyBearingCapacitySaturation2;

    private  Double QuarterlyBearingCapacitySaturation3;

    private  Double QuarterlyBearingCapacitySaturation4;

    //实际承载力 1-4季度
    private  Double ActualBearingCapacity1;

    private  Double ActualBearingCapacity2;

    private  Double ActualBearingCapacity3;

    private  Double ActualBearingCapacity4;

    //承载力饱和度 1-4季度
    private  Double BearingCapacitySaturation1;

    private  Double BearingCapacitySaturation2;

    private  Double BearingCapacitySaturation3;

    private  Double BearingCapacitySaturation4;

    //单季度评分 1-4季度
    private  Integer SingleQuarterRating1;

    private  Integer SingleQuarterRating2;

    private  Integer SingleQuarterRating3;

    private  Integer SingleQuarterRating4;






}
