package springBootSecurity.com.example.springBootSecurity.model.request;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
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
@Table(name = "menu")
public class MenuUpdate implements Serializable {

  @Id
  @Schema(example = "1")
  @NotNull(message = "Please provide a menu ID")
  @Min(value = 1, message = "role ID must be greater or minimum 1")
  @Column(name = "id")
  @JsonProperty("id")
  private Integer id;

  @NotNull(message = "Please provide menu number")
  @Schema(example = "3")
  @JsonFormat(shape = JsonFormat.Shape.NUMBER)
  @JsonProperty("menu_number")
  @Column(name = "menu_number", nullable = false)
  private Integer menuNumber;

  @Schema(example = "Menu description")
  @JsonFormat(shape = JsonFormat.Shape.STRING)
  @JsonProperty("description")
  @Column(name = "description")
  private String description;
}
