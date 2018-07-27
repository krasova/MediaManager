package com.ostrovskiy.media.domain;

import java.io.Serializable;
import java.util.Objects;
import javax.validation.constraints.NotNull;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

/**
 * A Picture.
 */
@Document(collection = "picture")
@org.springframework.data.elasticsearch.annotations.Document(indexName = "picture")
public class Picture implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    private String id;

    @NotNull
    @Field("name")
    private String name;

    @NotNull
    @Field("path")
    private String path;

    @NotNull
    @Field("size")
    private String size;

    @NotNull
    @Field("md5")
    private String md5;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public Picture name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPath() {
        return path;
    }

    public Picture path(String path) {
        this.path = path;
        return this;
    }

    public void setPath(String path) {
        this.path = path;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not
    // remove

    public String getMd5() {
        return md5;
    }

    public Picture setMd5(String md5) {
        this.md5 = md5;
        return this;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Picture picture = (Picture) o;
        return picture.getId() != null && getId() != null && Objects
            .equals(getId(), picture.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    public String getSize() {
        return size;
    }

    public Picture setSize(String size) {
        this.size = size;
        return this;
    }

    @Override
    public String toString() {
        return "Picture{" +
            "id='" + id + '\'' +
            ", name='" + name + '\'' +
            ", path='" + path + '\'' +
            ", size='" + size + '\'' +
            '}';
    }
}
