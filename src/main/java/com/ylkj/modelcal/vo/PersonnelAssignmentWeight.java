package com.ylkj.modelcal.vo;

import lombok.Data;

/**
 * 人员赋值及权重
 *
 * @author Zhudiwei
 * @date 2020/7/9
 */
@Data
public class PersonnelAssignmentWeight {
    /**
     * 岗位
     */
    private String post;
    /**
     * 职业资格
     */
    private String proQualification;

    /**
     * 岗位权重
     */
    private String postWeight;

    /**
     * 岗位赋值
     */
    private Double postAssignment;

    /**
     * 岗位等效值
     */
    private Double postValue;

    /**
     * 从业年限
     */
    private String workingAge;

    /**
     * 从业年限权重
     */
    private String workWeight;

    /**
     * 从业年限赋值
     */
    private Double workAssignment;

    /**
     * 从业年限等效值
     */
    private Double workValue;

    /**
     * 专业工作业绩
     */
    private String achievement;

    /**
     * 专业工作业绩权重
     */
    private String achievementWeight;

    /**
     * 专业工作业绩赋值
     */
    private Double achievementAssignment;

    /**
     * 从专业工作业绩等效值
     */
    private Double achievementValue;

}
