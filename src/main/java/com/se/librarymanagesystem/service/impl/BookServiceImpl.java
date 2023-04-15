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
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class BookServiceImpl extends ServiceImpl<BookMapper, Book> implements BookService {


    @Autowired
    BookTagService bookTagService;

    @Override
    @Transactional
    public void updateWithTags(BookDto bookDto) {
        LambdaQueryWrapper<Book> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Book::getId,bookDto.getId());
        this.update(bookDto,queryWrapper);
        //构造BookTag相关的条件构造器
        LambdaQueryWrapper<BookTag> tagLambdaQueryWrapper = new LambdaQueryWrapper<>();
        tagLambdaQueryWrapper.eq(BookTag::getBookId,bookDto.getId());
        List<BookTag> tags = bookDto.getTags();
        //等值清除原有的tags
        bookTagService.remove(tagLambdaQueryWrapper);

        tags = tags.stream().map((item) ->{
            item.setBookId(bookDto.getId());
            return item;
        }).collect(Collectors.toList());
        //根据主键来选择，需要谨慎使用，选择先删除BookID对应的tag再重新插入
        //bookTagService.saveOrUpdateBatch(tags);
        bookTagService.saveOrUpdateBatch(tags);
    }

    @Override
    @Transactional
    //设计多表查询写入，开启事务
    public void addWithTags(BookDto bookDto) {
        this.save(bookDto);
        //雪花算法自动生成ID，save过程先调用get生成了id再存入数据库中
        Long bookID = bookDto.getId();
        List<BookTag> bookTags = bookDto.getTags();
        bookTags.stream().map((item) ->{
            item.setBookId(bookID);
            return item;
        }).collect(Collectors.toList());

        bookTagService.saveBatch(bookTags);
    }

    /**
     * 通过ID查询 书籍信息和标签信息，返回BookDto
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
