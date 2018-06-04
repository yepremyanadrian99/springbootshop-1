package com.shop.domain.entity;

import org.hibernate.annotations.Where;

import java.util.List;
import javax.persistence.*;
@Entity
@Table(name = "categories")
public class Category {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column(name = "parent_id")
    private int parentId;
    private int status;
    @OneToMany(mappedBy = "category",fetch = FetchType.LAZY)
    @Where(clause = "lang_id='0'")
    private List<CategoryDescription> categoryDescriptions;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getParentId() {
        return parentId;
    }

    public void setParentId(int parentId) {
        this.parentId = parentId;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public List<CategoryDescription> getCategoryDescriptions() {
        return categoryDescriptions;
    }

    public void setCategoryDescriptions(List<CategoryDescription> categoryDescriptions) {
        this.categoryDescriptions = categoryDescriptions;
    }
}
