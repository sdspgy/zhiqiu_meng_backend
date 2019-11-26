package com.core.common.utils;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.metadata.OrderItem;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.core.entity.sys.PageVo;

import java.util.ArrayList;
import java.util.List;

/**
 * @author zhiqiu
 * @since 2019-11-26
 */
public class PageUtil {

	/**
	 * Mybatis-Plus分页封装
	 *
	 * @param page
	 * @return
	 */
	public static Page initMpPage(PageVo page) {

		Page p = null;
		int pageNumber = page.getPageNumber();
		int pageSize = page.getPageSize();
		String sort = StrUtil.toUnderlineCase(page.getSort());
		String order = StrUtil.toUnderlineCase(page.getOrder());

		if (pageNumber < 1) {
			pageNumber = 1;
		}
		if (pageSize < 1) {
			pageSize = 10;
		}
		p = new Page(pageNumber, pageSize);
		if (StrUtil.isNotBlank(sort)) {
			boolean isAsc = "asc".equalsIgnoreCase(order);
			p.addOrder(isAsc ? OrderItem.asc(sort) : OrderItem.desc(sort));
		}

		return p;
	}

	/**
	 * List 分页
	 *
	 * @param page
	 * @param list
	 * @return
	 */
	public static List listToPage(PageVo page, List list) {

		int pageNumber = page.getPageNumber() - 1;
		int pageSize = page.getPageSize();

		if (pageNumber < 0) {
			pageNumber = 0;
		}
		if (pageSize < 1) {
			pageSize = 10;
		}

		int fromIndex = pageNumber * pageSize;
		int toIndex = pageNumber * pageSize + pageSize;

		if (fromIndex > list.size()) {
			return new ArrayList();
		} else if (toIndex >= list.size()) {
			return list.subList(fromIndex, list.size());
		} else {
			return list.subList(fromIndex, toIndex);
		}
	}
}
