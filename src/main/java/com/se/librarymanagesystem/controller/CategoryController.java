package com.se.librarymanagesystem.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.se.librarymanagesystem.common.R;
import com.se.librarymanagesystem.entity.Category;
import com.se.librarymanagesystem.service.CategoryService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * 分类管理的controller业务逻辑
 */
@RestController()
@RequestMapping("/category")
@Slf4j
public class CategoryController {
    //自动注入 service实现
    @Autowired
    private CategoryService categoryService;

    /**
     *
     * @param request servletRequest
     * @param page 当前页面
     * @param pageSize 页面大小
     * @param name 传入的查询参数
     * @return
     */
    @GetMapping("/page")
    public R<Page> page(HttpServletRequest request, int page, int pageSize, String name){
        //由MybatisPlus管理分页

        //page为分页构造器 泛型类，泛型参数为页面中存放的数据类型,构造函数中传入当前页数和页面大小
        Page<Category> pageE = new Page<>(page,pageSize);

        //条件构造器
        LambdaQueryWrapper<Category> queryWrapper = new LambdaQueryWrapper<>();
        //模糊查询，第一个参数为 查询生效条件，第二个为查询的列名，第三个为传入的参数值
        queryWrapper.like(StringUtils.hasText(name),Category::getName,name);

        //page方法参数为Page，QueryWrapper，根据传入的分页构造器和查询构造器进行分页，分页结果会覆盖再传入的Page中
        categoryService.page(pageE,queryWrapper);
        return R.success(pageE);

    }


    /**
     * 新增分类信息
     * @param request
     * @param categoryIn
     * @return
     */
    @PostMapping
    public R<String> add(HttpServletRequest request, @RequestBody Category categoryIn){
        log.info("添加分类");
        categoryService.save(categoryIn);
        return R.success("添加成功");
    }


    /**
     * 修改分类信息
     * @param request
     * @param categoryIn
     * @return
     */
    @PutMapping
    public R<String> update(HttpServletRequest request, @RequestBody Category categoryIn){
        log.info("修改分类");
        Long idIn = categoryIn.getId();
        //Category category = categoryService.getById(idIn);
        LambdaQueryWrapper<Category> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Category::getId,idIn);
        Category category = categoryService.getOne(queryWrapper);
        category.setName(categoryIn.getName());
        category.setSort(categoryIn.getSort());
        //categoryService.updateById(category);
        categoryService.update(category,queryWrapper);
        return R.success("修改成功");
    }

    @DeleteMapping
    public R<String> delete(Long ids){
        log.warn("删除分类"+ids);
        //Long id = Long.valueOf(idIn);
        categoryService.deleteCategory(ids);
        return R.success("删除成功");
    }

    /**
     *给前端返回套餐列表用于选择
     * @param type 前端传来套餐的分类，菜品分类或套餐分类
     * @param request
     * @return
     */
    @GetMapping("list")
    public R<List<Category>> list(Integer type, HttpServletRequest request){

        LambdaQueryWrapper<Category> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(type!=null,Category::getType,type);
        List<Category> listResponse = categoryService.list(queryWrapper);
        return R.success(listResponse);
    }
}
