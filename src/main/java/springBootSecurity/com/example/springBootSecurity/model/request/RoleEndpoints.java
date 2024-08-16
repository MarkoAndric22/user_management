package springBootSecurity.com.example.springBootSecurity.model.request;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
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
@Table(name = "role_endpoints")
public class RoleEndpoints implements Serializable {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "id")
  @JsonIgnore
  private Integer id;

  @NotNull(message = "Please provide role ID")
  @Schema(example = "1")
  @JsonFormat(shape = JsonFormat.Shape.STRING)
  @JsonProperty("role_id")
  @Column(name = "role_id")
  private Integer roleId;

  @NotNull(message = "Please provide endpoint ID")
  @Schema(example = "1")
  @JsonFormat(shape = JsonFormat.Shape.STRING)
  @JsonProperty("endpoint_id")
  @Column(name = "endpoint_id")
  private String endpointId;
}
