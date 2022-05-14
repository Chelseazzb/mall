package com.imooc.mall.controller;

import com.imooc.mall.common.ApiRestResponse;
import com.imooc.mall.common.Constant;
import com.imooc.mall.exception.ImoocMallExceptionEnum;
import com.imooc.mall.model.pojo.Category;
import com.imooc.mall.model.pojo.User;
import com.imooc.mall.model.request.AddCategoryReq;
import com.imooc.mall.model.request.UpdateCategoryReq;
import com.imooc.mall.service.CategoryService;
import com.imooc.mall.service.UserService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

/**
 * 目录Controller
 */
@Controller
public class CategoryController {

    @Autowired
    UserService userService;

    @Autowired
    CategoryService categoryService;

    @ApiOperation("后台添加目录")
    @PostMapping("/admin/category/add")
    @ResponseBody
    public ApiRestResponse addCategory(HttpSession session, @Valid @RequestBody AddCategoryReq categoryReq) {

//        if (categoryReq.getName() == null || categoryReq.getType() == null || categoryReq.getParentId() == null
//                || categoryReq.getOrderNum() == null) {
//            return ApiRestResponse.error(ImoocMallExceptionEnum.CATEGORY_NOT_NULL);
//        }

        User user = (User) session.getAttribute(Constant.IMOOC_MALL_USER);
        if (user == null)
            return ApiRestResponse.error(ImoocMallExceptionEnum.NEED_LOGIN);

        //校验是否为管理员
        boolean checkAdmin = userService.checkAdmin(user);
        if (checkAdmin){
            categoryService.add(categoryReq);
            return ApiRestResponse.success();
        } else
            return ApiRestResponse.error(ImoocMallExceptionEnum.NEED_ADMIN);

    }

    @ApiOperation("后台更新目录")
    @PutMapping("/admin/category/update")
    @ResponseBody
    public ApiRestResponse updateCategory(@Valid @RequestBody UpdateCategoryReq updateCategoryReq, HttpSession session){
        User user = (User) session.getAttribute(Constant.IMOOC_MALL_USER);
        if (user == null)
            return ApiRestResponse.error(ImoocMallExceptionEnum.NEED_LOGIN);

        //校验是否为管理员
        boolean checkAdmin = userService.checkAdmin(user);
        if (checkAdmin){
            Category category = new Category();
            BeanUtils.copyProperties(updateCategoryReq,category);
            categoryService.update(category);
            return ApiRestResponse.success();
        } else
            return ApiRestResponse.error(ImoocMallExceptionEnum.NEED_ADMIN);
    }
}
