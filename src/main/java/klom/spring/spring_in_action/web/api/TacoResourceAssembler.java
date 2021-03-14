package klom.spring.spring_in_action.web.api;

import klom.spring.spring_in_action.Taco;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;

public class TacoResourceAssembler extends RepresentationModelAssemblerSupport<Taco, TacoModel> {


  public TacoResourceAssembler() {
    super(DesignTacoApiController.class, TacoModel.class);
  }

  @Override
  protected TacoModel instantiateModel(Taco taco) {
    return new TacoModel(taco);
  }

  @Override
  public TacoModel toModel(Taco taco) {
    return createModelWithId(taco.getId(), taco);
  }
}
