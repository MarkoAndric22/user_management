package springBootSecurity.com.example.springBootSecurity.model.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@ToString
@JsonInclude(JsonInclude.Include.USE_DEFAULTS)
public class RoleEndpointsDto implements Serializable {

  @NotNull(message = "Please provide role ID")
  @Schema(example = "1")
  @JsonFormat(shape = JsonFormat.Shape.STRING)
  @JsonProperty("role_id")
  private Integer roleId;

  @NotNull(message = "Please provide endpoint ID")
  @Schema()
  @JsonFormat(shape = JsonFormat.Shape.OBJECT)
  @JsonProperty("endpoint_ids")
  private List<String> endpointId;
}
