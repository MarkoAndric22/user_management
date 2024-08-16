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
@Table(name = "role_endpoints")
public class RoleEndpointsResponse implements Serializable {

  @Id
  @Schema(example = "101")
  @Column(name = "id")
  @JsonProperty("id")
  private Integer id;

  @Schema(example = "1")
  @JsonFormat(shape = JsonFormat.Shape.STRING)
  @JsonProperty("role_id")
  @Column(name = "role_id")
  private Integer roleId;

  @Schema(example = "1")
  @JsonFormat(shape = JsonFormat.Shape.STRING)
  @JsonProperty("endpoint_id")
  @Column(name = "endpoint_id")
  private String endpointId;

  @OneToOne(cascade = CascadeType.ALL)
  @Schema()
  @JsonProperty("endpoint")
  @JoinColumn(name = "endpoint_id", insertable = false, updatable = false)
  private EndpointResponse endpoint;
}
