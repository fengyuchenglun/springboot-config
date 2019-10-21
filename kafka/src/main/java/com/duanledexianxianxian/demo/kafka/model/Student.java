package com.duanledexianxianxian.demo.kafka.model;

import lombok.Data;

import java.io.Serializable;

/**
 * 学生实体类
 *
 * @author duanledexianxianxian
 * @date 2019/10/19 0:32
 * @since 1.0.0
 */
@Data
public class Student implements Serializable {
    private static final long serialVersionUID = -4572819276161640638L;
    /**
     * 学生学号
     */
    private String studentId;
    /**
     * 学生姓名
     */
    private String name;
    /**
     * 学生年龄
     */
    private Integer age;
    /**
     * 学生性别
     * 0-男
     * 1-女
     */
    private Byte sex;
}
