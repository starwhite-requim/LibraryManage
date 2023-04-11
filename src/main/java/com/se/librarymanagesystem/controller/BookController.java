package com.se.librarymanagesystem.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.se.librarymanagesystem.common.R;
import com.se.librarymanagesystem.dto.BookDto;
import com.se.librarymanagesystem.entity.Book;
import com.se.librarymanagesystem.service.BookService;
import com.se.librarymanagesystem.service.BookTagService;
import com.se.librarymanagesystem.service.CategoryService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@Slf4j
@RequestMapping("/book")
public class BookController {
    @Autowired
    BookService bookService;
    //返回结果需要包含标签，书本分类名称，使用dto类来完成
    @Autowired
    BookTagService bookTagService;
    @Autowired
    CategoryService categoryService;

    /**
     * 分页查询
     * @param page 起始位置
     * @param pageSize 分页大小
     * @return
     */
    @GetMapping("/page")
    public R<Page> bookList(int page,int pageSize,String name){
        Page<Book> bookPage = new Page<>(page,pageSize);
        LambdaQueryWrapper<Book> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.like(StringUtils.hasText(name),Book::getName,name);
        queryWrapper.orderByDesc(Book::getUpdateTime);
        bookService.page(bookPage,queryWrapper);
        //得到了原有分页，把分页Copy到Page<BookDto>中，向下转型
        Page<BookDto> pageInfo = new Page<>(page,pageSize);
        //复制除 records记录外的分页信息
        BeanUtils.copyProperties(bookPage,pageInfo,"records");
        List<Book> bookList = bookPage.getRecords();
        List<BookDto> dtoList = bookList.stream().map((item) ->{
            //得到categoryId,通过id得到分类名称
            Long categoryId = item.getCategoryId();
            String categoryName = categoryService.getById(categoryId).getName();
            BookDto bookDto = new BookDto();
            //新建一个BookDto接收属性
            BeanUtils.copyProperties(item,bookDto);
            //设置分类名
            bookDto.setCategoryName(categoryName);
            //返回dto类,collect为 列表
            return bookDto;
        }).collect(Collectors.toList());
        pageInfo.setRecords(dtoList);
        return R.success(pageInfo);
    }

    @GetMapping("#{id}")
    public R<BookDto> getBookInfo(@PathVariable Long id){
        return null;
    }
}
