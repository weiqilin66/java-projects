package org.wayne.base.controller.config;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.wayne.base.entity.Menu;
import org.wayne.base.service.MenuService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

/**
 * @Description:
 * @author: LinWeiQi
 */
@RestController
@RequestMapping("/sysConfig")
public class SysConfigController {

    @Resource
    MenuService menuService;

    @GetMapping("/menu")
    public List<Menu> getMenuById() {
        return menuService.getMenuById();

    }
    @DeleteMapping("/")
    public void del(){
        menuService.deleteMenu(0);
    }
}
