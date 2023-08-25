package com.mkl.framework.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import java.io.Serializable;
import java.util.Date;
import java.util.Map;

public class BaseEntity implements Serializable {
   private static final long serialVersionUID = 1L;
   private String searchValue;
   private String createBy;
   @JsonFormat(
      pattern = "yyyy-MM-dd HH:mm:ss"
   )
   private Date createTime;
   private String updateBy;
   @JsonFormat(
      pattern = "yyyy-MM-dd HH:mm:ss"
   )
   private Date updateTime;
   private String remark;
   private Map<String, Object> params;

   public String getSearchValue() {
      return this.searchValue;
   }

   public void setSearchValue(String searchValue) {
      this.searchValue = searchValue;
   }

   public String getCreateBy() {
      return this.createBy;
   }

   public void setCreateBy(String createBy) {
      this.createBy = createBy;
   }

   public Date getCreateTime() {
      return this.createTime;
   }

   public void setCreateTime(Date createTime) {
      this.createTime = createTime;
   }

   public String getUpdateBy() {
      return this.updateBy;
   }

   public void setUpdateBy(String updateBy) {
      this.updateBy = updateBy;
   }

   public Date getUpdateTime() {
      return this.updateTime;
   }

   public void setUpdateTime(Date updateTime) {
      this.updateTime = updateTime;
   }

   public String getRemark() {
      return this.remark;
   }

   public void setRemark(String remark) {
      this.remark = remark;
   }

   public Map<String, Object> getParams() {
      return this.params;
   }

   public void setParams(Map<String, Object> params) {
      this.params = params;
   }
}
