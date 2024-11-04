package com.mercaline.service;

import com.mercaline.model.CategoryEntity;
import com.mercaline.repository.CategoryRepository;
import com.mercaline.service.base.BaseService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CategoryService extends BaseService<CategoryEntity, Long, CategoryRepository> {
}
