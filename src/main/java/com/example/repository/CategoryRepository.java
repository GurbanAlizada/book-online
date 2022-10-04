package com.example.repository;

import com.example.model.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.Optional;

public interface CategoryRepository extends JpaRepository<Category , Long> , JpaSpecificationExecutor<Category> {


    Optional<Category> getByCategoryName(String categoryName);

}
