package springBootSecurity.com.example.springBootSecurity.model.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@ToString
@Entity
@JsonInclude(JsonInclude.Include.USE_DEFAULTS)
@Table(name = "role")
public class RoleResponse implements Serializable {

  @Id
  @Schema(example = "101")
  @JsonFormat(shape = JsonFormat.Shape.STRING)
  @JsonProperty("id")
  @Column(name = "id")
  private Integer id;

  @Schema(example = "Editor")
  @JsonFormat(shape = JsonFormat.Shape.STRING)
  @JsonProperty("name")
  @Column(name = "name", nullable = false, unique = true)
  private String name;

  @Schema(example = "Role description")
  @JsonFormat(shape = JsonFormat.Shape.STRING)
  @JsonProperty("description")
  @Column(name = "description")
  private String description;

  @Schema(example = "John Doe")
  @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "John Doe")
  @JsonProperty("created_by")
  @Column(name = "created_by")
  private String createdBy;

  @Schema(example = "John Doe")
  @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "John Doe")
  @JsonProperty("last_modified_by")
  @Column(name = "last_modified_by")
  private String lastModifiedBy;

  @Schema(example = "2020-10-15 20:18:45")
  @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
  @JsonProperty("created_date")
  @Column(name = "created_date")
  private Date createdDate;

  @Schema(example = "2020-10-15 20:18:45")
  @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
  @JsonProperty("last_modified_date")
  @Column(name = "last_modified_date")
  private Date lastModifiedDate;

  @OneToMany(fetch = FetchType.EAGER)
  @Schema()
  @JsonFormat(shape = JsonFormat.Shape.OBJECT)
  @JsonProperty("endpoints")
  @JoinColumn(
    name = "role_id",
    referencedColumnName = "id",
    insertable = false,
    updatable = false)
  @ToString.Exclude
  private List<RoleEndpointsResponse> endpoints;

  @OneToMany(fetch = FetchType.EAGER)
  @Schema()
  @JsonFormat(shape = JsonFormat.Shape.OBJECT)
  @JsonProperty("menus")
  @JoinColumn(
    name = "role_id",
    referencedColumnName = "id",
    insertable = false,
    updatable = false)
  @ToString.Exclude
  private List<RoleMenuResponse> menus;
}
