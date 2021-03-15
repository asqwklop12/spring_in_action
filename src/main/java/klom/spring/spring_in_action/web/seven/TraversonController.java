package klom.spring.spring_in_action.web.seven;

import java.net.URI;
import java.util.HashMap;
import java.util.Map;
import klom.spring.spring_in_action.Ingredient;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.MediaTypes;
import org.springframework.hateoas.client.Traverson;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

@RestController
@Slf4j
public class TraversonController {

  private Traverson traverson = new Traverson(
      URI.create("http://localhost:8080/api"), MediaTypes.HAL_JSON
  );


}
