package com.ylkj.modelcal.vo;

import lombok.Data;

/**
 * 人员信息（项目部人员）
 *
 * @author Zhudiwei
 * @date 2020/7/9
 */
@Data
public class Employee {

    /**
     * 岗位
     */
    private String post;

    /**
     * 从业年限
     */
    private Integer workingAge;

    /**
     * 职业资格
     */
    private String proQualification;

    /**
     * 专业工作业绩
     */
    private String workExperience;

    /**
     * 职业资格得分
     */
    private Double proQualificationScore;

    /**
     * 从业年限得分
     */
    private Double workingAgeScore;

    /**
     * 专业工作业绩得分
     */
    private Double workExperienceScore;

    /**
     * 综合指标值
     */
    private Double totalScore;

}
