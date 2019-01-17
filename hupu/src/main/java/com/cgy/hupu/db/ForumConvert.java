package com.cgy.hupu.db;

import com.alibaba.fastjson.JSON;

import org.greenrobot.greendao.converter.PropertyConverter;

/**
 * Created by cgy on 2019/1/17 下午 6:41.
 */
public class ForumConvert implements PropertyConverter<Forum, String> {
    @Override
    public Forum convertToEntityProperty(String databaseValue) {
        return JSON.parseObject(databaseValue, Forum.class);
    }

    @Override
    public String convertToDatabaseValue(Forum entityProperty) {
        return JSON.toJSONString(entityProperty);
    }
}
