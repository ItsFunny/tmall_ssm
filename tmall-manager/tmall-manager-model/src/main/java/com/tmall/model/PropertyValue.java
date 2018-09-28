package com.tmall.model;

import java.util.Date;

public class PropertyValue {
    private Integer productValueId;

    private String productId;

    private Integer propertyId;

    private String propertyValue;

    private Date createDate;

    private Date updateDate;

    public Integer getProductValueId() {
        return productValueId;
    }

    public void setProductValueId(Integer productValueId) {
        this.productValueId = productValueId;
    }

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId == null ? null : productId.trim();
    }

    public Integer getPropertyId() {
        return propertyId;
    }

    public void setPropertyId(Integer propertyId) {
        this.propertyId = propertyId;
    }

    public String getPropertyValue() {
        return propertyValue;
    }

    public void setPropertyValue(String propertyValue) {
        this.propertyValue = propertyValue == null ? null : propertyValue.trim();
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public Date getUpdateDate() {
        return updateDate;
    }

    public void setUpdateDate(Date updateDate) {
        this.updateDate = updateDate;
    }
}