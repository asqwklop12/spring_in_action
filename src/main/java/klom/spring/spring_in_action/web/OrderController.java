package klom.spring.spring_in_action.web;

import javax.validation.Valid;
import klom.spring.spring_in_action.Order;
import klom.spring.spring_in_action.User;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Slf4j
@Controller
@RequestMapping("/orders")
public class OrderController {

  @GetMapping("/current")
  public String orderForm(@AuthenticationPrincipal User user,
      @ModelAttribute Order order) {
//    model.addAttribute("order", new Order());
    if (order.getDeliveryName() == null) {
      order.setDeliveryName(user.getFullname());
    }
    if (order.getDeliveryState() == null) {
      order.setDeliveryStreet(user.getStreet());
    }
    if (order.getDeliveryCity() == null) {
      order.setDeliveryCity(user.getCity());
    }
    if (order.getDeliveryZip() == null) {
      order.setDeliveryZip(user.getZip());
    }

    return "orderForm";
  }

  @PostMapping
  public String processOrder(@Valid Order order, Errors errors,
      @AuthenticationPrincipal User user) {
    if (errors.hasErrors()) {
      return "orderForm";
    }
    order.setUser(user);
    log.info("Order submitted: " + order);
    return "redirect:/";
  }
}
