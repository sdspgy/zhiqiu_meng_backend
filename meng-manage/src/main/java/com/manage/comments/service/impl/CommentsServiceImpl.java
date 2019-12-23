package com.manage.comments.service.impl;

import com.core.entity.comments.Comments;
import com.core.mapper.comments.CommentsMapper;
import com.manage.comments.service.ICommentsService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author zhiqiu
 * @since 2019-12-22
 */
@Service
public class CommentsServiceImpl extends ServiceImpl<CommentsMapper, Comments> implements ICommentsService {

}
