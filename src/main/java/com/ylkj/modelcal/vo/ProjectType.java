package com.ylkj.modelcal.vo;

import lombok.Data;

/**
 * 工程类型（佚名）
 */
@Data
public class ProjectType {

    /**
     * 工程类型名称
     */
    private  String ProjectTypeName;
    /**
     * 投资占比
     */
    private  String ProportionOfInvestment;
    /**
     *工程量占比
     */
    private  String ProportionOfWorkQuantity;
    /**
     * 工程调整系数
     */
    private  Double EngineeringAdjustmentCoefficient;
}
