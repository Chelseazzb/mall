package com.imooc.mall.service;

import com.imooc.mall.model.pojo.Category;
import com.imooc.mall.model.request.AddCategoryReq;
import com.imooc.mall.model.request.UpdateCategoryReq;
import org.springframework.stereotype.Service;

public interface CategoryService {

    void add(AddCategoryReq addCategoryReq);

    void update(Category category);

    void delete(Integer id);
}
