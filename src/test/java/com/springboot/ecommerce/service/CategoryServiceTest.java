package com.springboot.ecommerce.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

import java.util.Optional;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import com.springboot.ecommerce.model.Category;
import com.springboot.ecommerce.repo.CategoryRepository;

@SpringBootTest
class CategoryServiceTest {

	@InjectMocks
	private CategoryService categoryService;

	@Mock
	private CategoryRepository categoryRepository;

	private Category category;
	
	@BeforeEach
    void init() {
        category = new Category();
        category.setId(1);
        category.setCategoryName("Electronics");
    }
	
	@Test
    void testAddCategory() {
        when(categoryRepository.save(category)).thenReturn(category);
        Category result = categoryService.addCategory(category);
        assertEquals("Electronics", result.getCategoryName());
    }
	
	@Test
    void testGetCategory() {
        when(categoryRepository.findById(1)).thenReturn(Optional.of(category));
        Category result = categoryService.getCategory(1);
        assertEquals(1, result.getId());
    }
	@AfterEach
	void end() {
		category=null;
	}

}
