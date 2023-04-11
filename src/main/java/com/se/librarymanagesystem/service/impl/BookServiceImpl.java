package com.se.librarymanagesystem.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.se.librarymanagesystem.dto.BookDto;
import com.se.librarymanagesystem.entity.Book;
import com.se.librarymanagesystem.entity.BookTag;
import com.se.librarymanagesystem.mapper.BookMapper;
import com.se.librarymanagesystem.service.BookService;
import com.se.librarymanagesystem.service.BookTagService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BookServiceImpl extends ServiceImpl<BookMapper, Book> implements BookService {


    @Autowired
    BookTagService bookTagService;
    /**
     * 通过ID查询 菜品信息和口味信息，返回BookDto
     * @param id
     * @return
     */
    @Override
    public BookDto queryBookAndTag(Long id) {
        BookDto bookDto = new BookDto();
        BeanUtils.copyProperties(this.getById(id),bookDto);

        LambdaQueryWrapper<BookTag> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(BookTag::getBookId,id);
        List<BookTag> tags = bookTagService.list(queryWrapper);
        bookDto.setTags(tags);
        return bookDto;
    }
}
