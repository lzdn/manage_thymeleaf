package com.lzdn.manage.utils.web;

import java.io.Serializable;
import java.util.List;

public class Page<E> implements Serializable{

	private static final long serialVersionUID = 318573118442919401L;
	
    private List<E> records;
    private Integer pageSize = 1;
    private Integer currentPage = 0;
    private Long total;
    private Long totalPage;

    public List<E> getRecords() {
        return records;
    }

    public void setRecords(List<E> records) {
        this.records = records;
    }

    public Integer getPageSize() {
        return pageSize;
    }

    /**
     * 每页记录数最小为1
     * @param pageSize 每页记录数
     */
    public void setPageSize(Integer pageSize) {
        if (pageSize != null && pageSize > 1) {
            this.pageSize = pageSize;
        }
    }

    public Integer getCurrentPage() {
        return currentPage;
    }

    /**
     * 页码从0开始
     * @param currentPage 当前页
     */
    public void setCurrentPage(Integer currentPage) {
        if (currentPage != null && currentPage > 0) {
            this.currentPage = currentPage;
        }
    }

    public Long getTotal() {
        return total;
    }

    public void setTotal(Long total) {
        this.total = total;
    }

    public Long getTotalPage() {
        totalPage = (total + pageSize - 1) / pageSize;
        return totalPage;
    }

}
