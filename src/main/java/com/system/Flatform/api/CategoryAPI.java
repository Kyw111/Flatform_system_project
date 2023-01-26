package com.system.Flatform.api;

import com.system.Flatform.category.dto.CategorySaveDTO;
import com.system.Flatform.category.dto.CategoryUpdateDTO;
import com.system.Flatform.category.service.CategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static com.system.Flatform.utils.Constants.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/category")
public class CategoryAPI {

    private final CategoryService categoryService;

    @PostMapping
    public ResponseEntity saveCategory(@RequestBody CategorySaveDTO categorySaveDTO) {
        categoryService.saveCategory(categorySaveDTO);
        return ResponseEntity.ok().body(CREATE_SUCCESS);
    }

    @PutMapping
    public ResponseEntity updateCategory(@RequestBody CategoryUpdateDTO categoryUpdateDTO) {
        categoryService.updateCategory(categoryUpdateDTO);
        return ResponseEntity.ok().body(UPDATE_SUCCESS);
    }



}
