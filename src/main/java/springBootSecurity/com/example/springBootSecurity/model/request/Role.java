package springBootSecurity.com.example.springBootSecurity.model.request;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import springBootSecurity.com.example.springBootSecurity.model.modelTools.Auditable;

import java.io.Serializable;

@Getter
@Setter
@NoArgsConstructor
@ToString
@Entity
@JsonInclude(JsonInclude.Include.USE_DEFAULTS)
@Table(name = "role")
public class Role extends Auditable implements Serializable {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @JsonIgnore
  @Column(name = "id")
  private Integer id;

  @NotBlank(message = "Please provide name")
  @NotEmpty(message = "Please provide name")
  @Size(min = 1, max = 255, message = "name must be between 1 and 255")
  @Schema(example = "Editor")
  @JsonFormat(shape = JsonFormat.Shape.STRING)
  @JsonProperty("name")
  @Column(name = "name", nullable = false, unique = true)
  private String name;


  @Size(max = 3000, message = "description can be maximum 3000 characters")
  @Schema(example = "Role description")
  @JsonFormat(shape = JsonFormat.Shape.STRING)
  @JsonProperty("description")
  @Column(name = "description")
  private String description;
}
