package com.spring.projects.MVCRest.services;

import com.spring.projects.MVCRest.api.v1.model.CategoryDTO;

import java.util.List;

public interface CategoryService {

    List<CategoryDTO> getAllCategories();

    CategoryDTO getCategoryByName(String name);
}
