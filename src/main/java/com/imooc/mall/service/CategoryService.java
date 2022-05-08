package com.imooc.mall.service;

import com.imooc.mall.model.request.AddCategoryReq;
import org.springframework.stereotype.Service;

@Service
public interface CategoryService {

    void add(AddCategoryReq addCategoryReq);
}
