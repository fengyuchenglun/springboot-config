package com.duanledexianxianxian.demo;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.collect.Lists;
import org.springframework.beans.BeanUtils;
import org.springframework.util.CollectionUtils;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author duanledexianxianxian
 * @date 2020/1/12 23:51
 * @since 1.0.0
 */
public class Application {
    private static String[][] menus = {
            {null, "0001", "用户管理", "/urlMgr"},
            {"0001", "00010001", "用户管理-添加用户", "/urlMgr/add"},
            {"0001", "00010002", "用户管理-修改用户", "/urlMgr/edit"},
            {"0001", "00010003", "用户管理-删除用户", "/urlMgr/delete"},
            {"0001", "00010004", "用户管理-查询用户", "/urlMgr/query"},
            {null, "0002", "系统管理", "/systemMgr"},
            {"0002", "00020001", "系统管理-系统管理0001", "/systemMgr/0001"},
            {"00020001", "000200010001", "系统管理-系统管理0001-系统管理0001", "/systemMgr/0001/0001"},
            {"000200010001", "0002000100010001", "系统管理-系统管理0001-系统管理0001-系统管理0001", "/systemMgr/0001/0001/0001"},
    };

    public static void main(String[] args) {
        List<Menu> menuList = Lists.newArrayList();
        Arrays.asList(menus).forEach(x -> {
            Menu menu = new Menu();
            menu.setParentMenuCode(x[0]);
            menu.setMenuCode(x[1]);
            menu.setMenuTitle(x[2]);
            menu.setMenuUrl(x[3]);
            menuList.add(menu);
        });

        menuList.forEach(x -> System.out.println(x.toString()));
        MenuVO menuVO = getTreeMenuVO(menuList);
        ObjectMapper mapper = new ObjectMapper();
        try {
            System.out.println(mapper.writeValueAsString(menuVO));
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
    }

    /**
     * 平级列表转成树结构
     * @param menuList
     * @return
     */
    private static MenuVO getTreeMenuVO(List<Menu> menuList) {
        if (CollectionUtils.isEmpty(menuList)) {
            return null;
        }
        List<MenuVO> menuVOList = Lists.newArrayList();
        // 类型转换 一层循环
        menuList.forEach(x -> {
            MenuVO menuVO = new MenuVO();
            BeanUtils.copyProperties(x, menuVO);
            menuVOList.add(menuVO);
        });
        // 转成map  二层循环  一层与二层可以合并
        Map<String, MenuVO> menuMap = menuVOList.stream().collect(Collectors.toMap(MenuVO::getMenuCode, x -> x));
        // 构建根节点
        MenuVO menuVO = new MenuVO();
        menuVO.setParentMenuCode(null);
        menuVO.setMenuCode("root");
        menuVO.setMenuTitle("根节点");

        // 转换逻辑  三层循环
        for (MenuVO menu : menuVOList) {
            if (menuMap.containsKey(menu.getParentMenuCode())) {
                if (menuMap.get(menu.getParentMenuCode()).getChildren() == null) {
                    menuMap.get(menu.getParentMenuCode()).setChildren(Lists.newArrayList());
                }
                menuMap.get(menu.getParentMenuCode()).getChildren().add(menu);

            } else {
                if (menuVO.getChildren() == null) {
                    menuVO.setChildren(Lists.newArrayList());
                }
                menuVO.getChildren().add(menu);
            }
        }
        return menuVO;
    }
}
