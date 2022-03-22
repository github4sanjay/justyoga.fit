package com.justyoga.util.dto.cache;

import java.io.Serializable;
import java.util.Set;

public class GenericCacheDTO<I, D, C, P> implements Serializable {
    private I identifier;
    private D data;
    private Set<C> children;
    private P parent;

    public GenericCacheDTO() {}

    public GenericCacheDTO(I identifier, D data, Set<C> children, P parent) {
        this.identifier = identifier;
        this.data = data;
        this.children = children;
        this.parent = parent;
    }

    public I getIdentifier() {
        return identifier;
    }

    public void setIdentifier(I identifier) {
        this.identifier = identifier;
    }

    public D getData() {
        return data;
    }

    public void setData(D data) {
        this.data = data;
    }

    public Set<C> getChildren() {
        return children;
    }

    public void setChildren(Set<C> children) {
        this.children = children;
    }

    public P getParent() {
        return parent;
    }

    public void setParent(P parent) {
        this.parent = parent;
    }
}
