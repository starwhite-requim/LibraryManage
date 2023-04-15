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
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
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

    /**
     * 传入ID ，返回相应id的DTO类
     * @param id
     * @return
     */
    @GetMapping("/{id}")
    public R<BookDto> getBookInfo(@PathVariable Long id){
        BookDto bookDto = bookService.queryBookAndTag(id);
        return R.success(bookDto);
    }

    /**
     * 添加书本和标签信息
     * @param bookDto
     * @return
     */
    @PostMapping
    public R addBook(@RequestBody BookDto bookDto){
        bookService.addWithTags(bookDto);
        return R.success("添加成功");
    }


    /**
     * 根据传回的Dto，调用 update方法，进行修改
     * @param bookDto
     * @return
     */
    @PutMapping
    public R updateBook(@RequestBody BookDto bookDto){
        log.info(bookDto.toString());
        bookService.updateWithTags(bookDto);
        return R.success("修改成功");
    }

    /**
     * 书籍收回
     * @param ids
     * @return
     */
    @PostMapping("/status/1")
    public R<String> statusOn(String ids){

        List<Long> idArray = Arrays.asList(ids.split(",")).stream().map((item) ->{
            Long id = Long.valueOf(item);
            return id;
        }).collect(Collectors.toList());
        for (Long id : idArray) {
            Book book = bookService.getById(id);
            book.setStatus(1);
            bookService.updateById(book);
        }
        return R.success("收回成功");
    }

    /**
     * 书籍借出
     * @param ids
     * @return
     */
    @PostMapping("/status/0")
    public R<String> statusOff(String ids){

        //ids用逗号分隔开，得到String数组，转成List后，使用stream，把String转化成Long，再封装为又给Long的List
        List<Long> idArray = Arrays.asList(ids.split(",")).stream().map((item) ->{
            Long id =  Long.valueOf(item);
            return id;
        }).collect(Collectors.toList());


        //逐个修改id对应的菜品的状态
        for (Long id : idArray) {
            Book book = bookService.getById(id);
            book.setStatus(0);
            bookService.updateById(book);

        }
        return R.success("借出成功");
    }


}
