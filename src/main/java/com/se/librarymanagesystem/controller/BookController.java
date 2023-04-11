package com.se.librarymanagesystem.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.se.librarymanagesystem.common.R;
import com.se.librarymanagesystem.entity.Book;
import com.se.librarymanagesystem.service.BookService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
@RequestMapping("/book")
public class BookController {
    @Autowired
    BookService bookService;

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
        return R.success(bookPage);
    }
}
