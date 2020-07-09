package com.ylkj.modelcal.vo;

import lombok.Data;

/**
 * 电压等级折算系数
 */
@Data
public class ConversionCoefficientQuantities {
    /**
     * 电压等级(kV)
     */
    private  Integer VoltageLevel;
    /**
     * 标准工程投资（万元）
     */
    private  Integer StandardInvestment;

    /**
     * 电压等级折算系数
     */
    private  Double ConversionQuantities;


    /**
     * 正常施工或非施工单位原因造成的施工暂停、工程延期
     */
    private  Integer NormalStructionUnit;


    /**
     * 因施工单位原因造成的施工暂停、工程延期
     */
    private  Integer UnitReason;

    /**
     * 地形信息
     */
    private  String TerrainType;

    /**
     * 地形特征
     */
    private  String TopographicalFeatures;


    /**
     * 地形调整系数
     */
    private  Integer TerrainCoefficient;

    /**
     * 气象区域
     */
    private  String MeteorologicalArea;


    /**
     * 气象区域调整系数
     */
    private Integer RegionalMeteorology;

}
