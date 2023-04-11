package com.se.librarymanagesystem.dto;

import com.se.librarymanagesystem.entity.Book;
import com.se.librarymanagesystem.entity.BookTag;
import com.se.librarymanagesystem.entity.Category;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class BookDto extends Book {
    //Dto类包含 Book原有信息，分类名称CategoryName，标签列表List<BookTag>,库存
    private String CategoryName;

    private List<BookTag> tags = new ArrayList<>();

    private Integer counts;
}
