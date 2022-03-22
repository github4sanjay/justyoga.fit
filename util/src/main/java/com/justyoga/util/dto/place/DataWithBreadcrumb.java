package com.justyoga.util.dto.place;

import java.util.List;

public class DataWithBreadcrumb<D> {

    private D data;
    private List<BreadcrumbDTO> breadcrumbs;

    public D getData() {
        return data;
    }

    public void setData(D data) {
        this.data = data;
    }

    public List<BreadcrumbDTO> getBreadcrumbs() {
        return breadcrumbs;
    }

    public void setBreadcrumbs(List<BreadcrumbDTO> breadcrumbs) {
        this.breadcrumbs = breadcrumbs;
    }
}
