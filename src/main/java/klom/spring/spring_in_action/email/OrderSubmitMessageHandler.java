package klom.spring.spring_in_action.email;

import org.springframework.integration.handler.GenericHandler;
import org.springframework.messaging.MessageHeaders;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
public class OrderSubmitMessageHandler implements GenericHandler<Order> {

  private final RestTemplate rest;
  private final ApiProperties apiProps;

  public OrderSubmitMessageHandler(RestTemplate rest,
      ApiProperties apiProps) {
    this.rest = rest;
    this.apiProps = apiProps;
  }

  @Override
  public Object handle(Order order, MessageHeaders messageHeaders) {
    rest.postForObject(apiProps.getUri(), order, String.class);
    return null;
  }
}
