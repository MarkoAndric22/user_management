package springBootSecurity.com.example.springBootSecurity.component;

import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import springBootSecurity.com.example.springBootSecurity.service.service_api.EndpointService;

@Component
@RequiredArgsConstructor
public class ScheduledTasks {

  private final EndpointService endpointService;

  /**
   * Populate endpoint table.
   */
  @Scheduled(initialDelay = 10000, fixedDelay = 300000) // 5 min
  public void doPopulateEndpoints() {
    endpointService.populate();
  }
}
