package klom.spring.spring_in_action.web.api;

import java.util.List;
import java.util.Optional;
import klom.spring.spring_in_action.Taco;
import klom.spring.spring_in_action.data.OrderRepository;
import klom.spring.spring_in_action.data.TacoRepository;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/design", produces = MediaType.APPLICATION_JSON_VALUE)
@CrossOrigin(origins = "*")
public class DesignTacoApiController {
//  @Autowired
//  EntityLinks entityLinks;

  private final TacoRepository tacoRepo;
  private final OrderRepository orderRepo;

  public DesignTacoApiController(TacoRepository tacoRepo, OrderRepository orderRepo) {
    this.tacoRepo = tacoRepo;
    this.orderRepo = orderRepo;
  }

  @GetMapping("/recent")
  public ResponseEntity<CollectionModel<TacoModel>> recentTacos() {
    PageRequest page = PageRequest.of(1, 12, Sort.by("createdAt").descending());

    List<Taco> tacos = tacoRepo.findAll(page).getContent();

    CollectionModel<TacoModel> tacoResources = new TacoResourceAssembler().toCollectionModel(tacos);

    CollectionModel<TacoModel> recentResources = new CollectionModel<>(tacoResources);

    recentResources.add(
        WebMvcLinkBuilder
            .linkTo(WebMvcLinkBuilder.methodOn(DesignTacoApiController.class).recentTacos())
            .withRel("recents"));
    return new ResponseEntity<>(recentResources, HttpStatus.OK);
  }

  @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
  @ResponseStatus(HttpStatus.CREATED)
  public Taco postTaco(@RequestBody Taco taco) {
    return tacoRepo.save(taco);
  }


  @GetMapping("/{id}")
  public ResponseEntity<Taco> tacoById(@PathVariable("id") Long id) {
    Optional<Taco> optTaco = tacoRepo.findById(id);
    if (optTaco.isPresent()) {
      return new ResponseEntity<>(optTaco.get(), HttpStatus.OK);
    }
    return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
  }

}
