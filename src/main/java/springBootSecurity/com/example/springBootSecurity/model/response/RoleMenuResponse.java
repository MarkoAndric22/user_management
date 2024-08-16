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

@Getter
@Setter
@NoArgsConstructor
@ToString
@Entity
@JsonInclude(JsonInclude.Include.USE_DEFAULTS)
@Table(name = "role_menu")
public class RoleMenuResponse implements Serializable {

  @Id
  @Schema(example = "1")
  @Column(name = "id")
  @JsonProperty("id")
  private Integer id;

  @Schema(example = "3")
  @JsonFormat(shape = JsonFormat.Shape.NUMBER)
  @JsonProperty("role_id")
  @Column(name = "role_id", nullable = false)
  private Integer roleId;

  @Schema(example = "3")
  @JsonFormat(shape = JsonFormat.Shape.NUMBER)
  @JsonProperty("menu_id")
  @Column(name = "menu_id", nullable = false)
  private Integer menuId;

  @OneToOne(cascade = CascadeType.ALL)
  @Schema()
  @JsonProperty("menu")
  @JoinColumn(name = "menu_id", insertable = false, updatable = false)
  private MenuResponse menu;
}
