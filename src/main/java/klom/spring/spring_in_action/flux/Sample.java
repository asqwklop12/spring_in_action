package klom.spring.spring_in_action.flux;

import reactor.core.publisher.Mono;

public class Sample {
  public void code() {
    String name = "Craig";
    String capitalName = name.toUpperCase();
    String greeting = "Hello, " + capitalName + "!";
    System.out.println(greeting);
  }

  public void reactive() {
    Mono.just("Craig")
        .map(String::toUpperCase)
        .map(cn -> "Hello, " + cn + "!")
        .subscribe(System.out::println);
  }
}
