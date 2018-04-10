package com.genius.znzx.common.mapper;

import tk.mybatis.mapper.common.Mapper;
import tk.mybatis.mapper.common.MySqlMapper;

/**
 * 被继承的Mapper，一般业务Mapper继承它
 * @author fangxing.peng
 *
 * @param <T>
 */
public interface TkMapper <T> extends Mapper<T>, MySqlMapper<T>{

}
