package com.duanledexianxianxian.demo;

import lombok.Data;

/**
 * 菜单
 *
 * @author duanledexianxianxian
 * @date 2020/1/12 23:49
 * @since 1.0.0
 */
@Data
public class Menu {
    private String parentMenuCode;
    private String menuCode;
    private String menuTitle;
    private String menuUrl;
}
