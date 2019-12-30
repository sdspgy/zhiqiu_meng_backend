package com.core.mapper.work;

import com.core.entity.work.Work;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

/**
 * <p>
 * Mapper 接口
 * </p>
 *
 * @author zhiqiu
 * @since 2019-12-22
 */
public interface WorkMapper extends BaseMapper<Work> {

    void addWorkLookByWorkid(String workId);

    @Select("select workSupportUsers from work where work_id = #{workId}")
    String queryWorkSupportUsers(String workId);

    void upWorkSupport(String workSupportUsers);

    @Update("update work set status = #{checkType} where work_id = #{workId}")
    void checkWorkStatus(String workId, int checkType);
}
