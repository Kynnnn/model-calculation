package com.ylkj.modelcal.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 工程类型（佚名）
 *
 * @author 付鸿飞
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProjectType {

    /**
     * 工程类型名称
     */
    private String projectTypeName;
    /**
     * 投资占比
     */
    private String proportionOfInvestment;
    /**
     * 工程量占比
     */
    private String proportionOfWorkQuantity;
    /**
     * 工程调整系数
     */
    private Double engineeringAdjustmentCoefficient;
}
