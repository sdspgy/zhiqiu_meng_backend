package com.core.common.druid;

import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;

/**
 * @author zhiqiu
 * @since 2019-10-26
 */
public class DynamicDataSource extends AbstractRoutingDataSource {

	/*根据Key返回targetDataSources*/
	@Override
	protected Object determineCurrentLookupKey() {
		return DynamicDataSourceHolder.getDataSource();
	}
}
