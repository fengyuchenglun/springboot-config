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
    private String studentId;
    private String name;
    private String age;
}
