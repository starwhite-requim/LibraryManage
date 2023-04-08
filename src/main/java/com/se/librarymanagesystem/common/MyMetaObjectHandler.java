package com.se.librarymanagesystem.common;

import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.reflection.MetaObject;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
@Slf4j
public class MyMetaObjectHandler implements MetaObjectHandler {
    @Override
    public void insertFill(MetaObject metaObject) {
        log.info("insert fill"+metaObject.toString());
        metaObject.setValue("createTime", LocalDateTime.now());
        metaObject.setValue("updateTime", LocalDateTime.now());
        //想办法拿到当前用户的id ， 考虑使用session,但session需要获得连接，使用ThreadLocal
        metaObject.setValue("createUser", CurrentId.GetId());
        metaObject.setValue("updateUser", CurrentId.GetId());
    }

    @Override
    public void updateFill(MetaObject metaObject) {
        log.info("update fill"+metaObject.toString());
        metaObject.setValue("updateTime", LocalDateTime.now());
        //想办法拿到当前用户的id ， 考虑使用session,但session需要获得连接，使用ThreadLocal
        metaObject.setValue("updateUser", CurrentId.GetId());

    }
}
