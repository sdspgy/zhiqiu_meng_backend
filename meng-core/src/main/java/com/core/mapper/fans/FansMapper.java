package com.core.mapper.fans;

import com.core.entity.fans.Fans;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author zhiqiu
 * @since 2019-12-22
 */
public interface FansMapper extends BaseMapper<Fans> {

    @Select("select * from fans")
    List<Fans> querys();
}
