package com.imooc.mall.service.impl;

import com.imooc.mall.exception.ImoocMallException;
import com.imooc.mall.exception.ImoocMallExceptionEnum;
import com.imooc.mall.model.dao.CategoryMapper;
import com.imooc.mall.model.pojo.Category;
import com.imooc.mall.model.request.AddCategoryReq;
import com.imooc.mall.model.request.UpdateCategoryReq;
import com.imooc.mall.service.CategoryService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CategoryServiceImpl implements CategoryService {

    @Autowired
    CategoryMapper categoryMapper;

    @Override
    public void add(AddCategoryReq addCategoryReq){
        Category category = new Category();
        BeanUtils.copyProperties(addCategoryReq,category);
        Category old = categoryMapper.selectByName(addCategoryReq.getName());
        if (old != null){
            throw new ImoocMallException(ImoocMallExceptionEnum.NAME_EXISTED);
        }

        int count = categoryMapper.insertSelective(category);
        if (count == 0){
            throw new ImoocMallException(ImoocMallExceptionEnum.INSERT_FAILED);
        }

    }

    @Override
    public void update(Category category) {
        if (category.getName() != null){
            Category old = categoryMapper.selectByName(category.getName());
            if (old != null && !old.getId().equals(category.getId())){
                throw new ImoocMallException(ImoocMallExceptionEnum.NAME_EXISTED);
            }
        }

        int count = categoryMapper.updateByPrimaryKeySelective(category);
        if (count == 0){
            throw new ImoocMallException(ImoocMallExceptionEnum.UPDATE_FAILED);
        }
    }
}
