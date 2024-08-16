package springBootSecurity.com.example.springBootSecurity.model.modelTools;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.util.LinkedList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@ToString
@JsonInclude(JsonInclude.Include.USE_DEFAULTS)
public class MappingServicesWrapper {

  @Schema()
  @JsonProperty("services")
  List<String> services = new LinkedList<>();

  @Schema()
  @JsonProperty("endpoints")
  List<Mapping> mappings = new LinkedList<>();
}
