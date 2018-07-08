package com.ostrovskiy.media.domain;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Field;
import org.springframework.data.mongodb.core.mapping.Document;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A Movie.
 */
@Document(collection = "movie")
@org.springframework.data.elasticsearch.annotations.Document(indexName = "movie")
public class Movie implements Serializable {

  private static final long serialVersionUID = 1L;

  @Id
  private String id;

  @NotNull
  @Field("name")
  private String name;

  @NotNull
  @Field("path")
  private String path;

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

  public Movie name(String name) {
    this.name = name;
    return this;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getPath() {
    return path;
  }

  public Movie path(String path) {
    this.path = path;
    return this;
  }

  public void setPath(String path) {
    this.path = path;
  }
  // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not
  // remove

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    Movie movie = (Movie) o;
    if (movie.getId() == null || getId() == null) {
      return false;
    }
    return Objects.equals(getId(), movie.getId());
  }

  @Override
  public int hashCode() {
    return Objects.hashCode(getId());
  }

  @Override
  public String toString() {
    return "Movie{" + "id=" + getId() + ", name='" + getName() + "'" + ", path='" + getPath() + "'"
        + "}";
  }
}
