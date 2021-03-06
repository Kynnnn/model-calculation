package com.ylkj.modelcal.vo;

import lombok.Data;

/**
 * 施工承载力
 */
@Data
public class ConstructionBearingCapacity {
    /**
     * 指标名称
     */
    private  String indicatorName;

    /**
     * 理论指标值
     */
    private  Double theoreticalValue;

    /**
     * 实际指标值
     */
    private  Double actual;







}
