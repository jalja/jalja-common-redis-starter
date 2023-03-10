package com.jalja.common.model.param;

import java.io.Serializable;

/**
 * @author XL@doublefs.com
 * @date 2/1/23
 */
public class PageParam implements Serializable {

  private static final long serialVersionUID = -1117081890821498022L;
  private Integer currentPage = 1;
  private Integer pageSize = 20;


  public PageParam() {
  }

  public PageParam(Integer currentPage, Integer pageSize) {
    this.currentPage = currentPage;
    this.pageSize = pageSize;
  }

  public Integer getCurrentPage() {
    return this.currentPage;
  }

  public Integer getPageSize() {
    return this.pageSize;
  }


  public void setCurrentPage(final Integer currentPage) {
    this.currentPage = currentPage;
  }

  public void setPageSize(final Integer pageSize) {
    this.pageSize = pageSize;
  }

}
