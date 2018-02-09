package com.lzdn.manage.utils.sql;

import org.apache.ibatis.jdbc.SQL;

public class SqlProvide {

	
	public String getUserAll() {
		return new SQL() {
			{
				this.SELECT("*").FROM("user");
			}
		}.toString();
	}
	
	public String getProductAll() {
		return new SQL() {
			{
				this.SELECT("id, product_name as productName").FROM("product");
			}
		}.toString();
	}
}
