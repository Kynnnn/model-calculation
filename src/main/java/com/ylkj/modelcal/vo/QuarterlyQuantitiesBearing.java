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

    private  String JanuaryBearingCapacitySaturation;

    private  String FebruaryBearingCapacitySaturation;

    private  String MarchBearingCapacitySaturation;

    private  String AprilBearingCapacitySaturation;

    private  String MayBearingCapacitySaturation;

    private  String JuneBearingCapacitySaturation;

    private  String JulyBearingCapacitySaturation;

    private  String AugustBearingCapacitySaturation;

    private  String SeptemberBearingCapacitySaturation;

    private  String OctoberBearingCapacitySaturation;

    private  String NovemberBearingCapacitySaturation;

    private  String DecemberBearingCapacitySaturation;

    //季度工程 1-4季度
    private  Double QuarterlyEngineering1;

    private  Double QuarterlyEngineering2;

    private  Double QuarterlyEngineering3;

    private  Double QuarterlyEngineering4;

    //月承载力饱和度1-4
    private  String CalQuarterlyEngineering1;

    private  String CalQuarterlyEngineering2;

    private  String CalQuarterlyEngineering3;

    private  String CalQuarterlyEngineering4;

    //季度承载力饱和度 1-4季度
    private  String QuarterlyBearingCapacitySaturation1;

    private  String QuarterlyBearingCapacitySaturation2;

    private  String QuarterlyBearingCapacitySaturation3;

    private  String QuarterlyBearingCapacitySaturation4;

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
