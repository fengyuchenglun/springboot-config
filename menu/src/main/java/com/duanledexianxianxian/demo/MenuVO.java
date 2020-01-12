package com.duanledexianxianxian.demo;

import lombok.Data;

import java.util.List;

/**
 * 菜单
 *
 * @author duanledexianxianxian
 * @date 2020/1/12 23:49
 * @since 1.0.0
 */
@Data
public class MenuVO {
    private String parentMenuCode;
    private String menuCode;
    private String menuTitle;
    private String menuUrl;
    private List<MenuVO> children;
}
