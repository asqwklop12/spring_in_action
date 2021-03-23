package klom.spring.spring_in_action.flux;

import java.time.Duration;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;
import org.junit.jupiter.api.Test;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;
import reactor.util.function.Tuple2;

class SampleTest {

  @Test
  public void createAFlux_just() {
    Flux<String> fruitFlux = Flux
        .just("Apple", "Orange", "Grape", "Banana", "Strawberry");
    fruitFlux.subscribe(
        f -> System.out.println("Here's some fruit: " + f)
    );
    StepVerifier.create(fruitFlux)
        .expectNext("Apple")
        .expectNext("Orange")
        .expectNext("Grape")
        .expectNext("Banana")
        .expectNext("Strawberry")
        .verifyComplete();
  }

  @Test
  public void createFlux_fromArray() {
    String[] fruits = {"Apple", "Orange", "Grape", "Banana", "Strawberry"};
    Flux<String> fruitFlux = Flux.fromArray(fruits);
    StepVerifier.create(fruitFlux)
        .expectNext("Apple")
        .expectNext("Orange")
        .expectNext("Grape")
        .expectNext("Banana")
        .expectNext("Strawberry")
        .verifyComplete();
  }

  @Test
  public void createFlux_fromIterable() {
    List<String> fruitList = Arrays.asList("Apple", "Orange", "Grape", "Banana", "Strawberry");
    Flux<String> fruitFlux = Flux.fromIterable(fruitList);
    StepVerifier.create(fruitFlux)
        .expectNext("Apple")
        .expectNext("Orange")
        .expectNext("Grape")
        .expectNext("Banana")
        .expectNext("Strawberry")
        .verifyComplete();
  }

  @Test
  public void createFlux_fromStream() {
    Stream<String> fruitStream = Stream.of("Apple", "Orange", "Grape", "Banana", "Strawberry");
    Flux<String> fruitFlux = Flux.fromStream(fruitStream);
    StepVerifier.create(fruitFlux)
        .expectNext("Apple")
        .expectNext("Orange")
        .expectNext("Grape")
        .expectNext("Banana")
        .expectNext("Strawberry")
        .verifyComplete();
  }

  @Test
  public void createAFlux_range() {
    Flux<Integer> integerFlux = Flux.range(1, 5);
    StepVerifier.create(integerFlux)
        .expectNext(1)
        .expectNext(2)
        .expectNext(3)
        .expectNext(4)
        .expectNext(5)
        .verifyComplete();
  }

  @Test
  public void createAFlux_interval() {
    Flux<Long> integerFlux = Flux.interval(Duration.ofSeconds(1)).take(5);
    StepVerifier.create(integerFlux)
        .expectNext(0L)
        .expectNext(1L)
        .expectNext(2L)
        .expectNext(3L)
        .expectNext(4L)
        .verifyComplete();
  }

  @Test
  public void mergeFluxes() {
    Flux<String> characterFlux = Flux
        .just("Carried", "kojak", "Barbosa")
        .delayElements(Duration.ofMillis(500));
    Flux<String> foodFlux = Flux
        .just("Kaaba", "Lollipops", "Apples")
        .delaySubscription(Duration.ofMillis(250))
        .delayElements(Duration.ofMillis(500));
    Flux<String> mergeWith = characterFlux.mergeWith(foodFlux);
    StepVerifier.create(mergeWith)
        .expectNext("Carried").expectNext("Kaaba").expectNext("kojak")
        .expectNext("Lollipops").expectNext("Barbosa").expectNext("Apples")
        .verifyComplete();
  }

  @Test
  public void zipFluxes() {
    Flux<String> characterFlux = Flux
        .just("Carried", "kojak", "Barbosa")
        .delayElements(Duration.ofMillis(500));
    Flux<String> foodFlux = Flux
        .just("Kaaba", "Lollipops", "Apples")
        .delaySubscription(Duration.ofMillis(250))
        .delayElements(Duration.ofMillis(500));
    Flux<Tuple2<String, String>> tuple2Flux = Flux.zip(characterFlux, foodFlux);
    StepVerifier.create(tuple2Flux)
        .expectNextMatches(p ->
            p.getT1().equals("Carried") &&
                p.getT2().equals("Kaaba"))
        .expectNextMatches(p ->
            p.getT1().equals("kojak") &&
                p.getT2().equals("Lollipops"))
        .expectNextMatches(p ->
            p.getT1().equals("Barbosa") &&
                p.getT2().equals("Apples"))
        .verifyComplete();
  }

  @Test
  public void zipFluxesToObject() {
    Flux<String> characterFlux = Flux
        .just("Carried", "kojak", "Barbosa");
    Flux<String> foodFlux = Flux
        .just("Kaaba", "Lollipops", "Apples");
    Flux<String> stringFlux = Flux
        .zip(characterFlux, foodFlux, (c, f) -> c + " eats " + f);
    StepVerifier.create(stringFlux)
        .expectNext("Carried eats Kaaba")
        .expectNext("kojak eats Lollipops")
        .expectNext("Barbosa eats Apples")
        .verifyComplete();
  }

  @Test
  public void firstFlux() {
    Flux<String> characterFlux = Flux
        .just("Carried", "kojak", "Barbosa")
        .delaySubscription(Duration.ofMillis(500));
    Flux<String> foodFlux = Flux
        .just("Kaaba", "Lollipops", "Apples");
    Flux<String> stringFlux = Flux
        .first(characterFlux, foodFlux);
    StepVerifier.create(stringFlux)
        .expectNext("Kaaba")
        .expectNext("Lollipops")
        .expectNext("Apples")
        .verifyComplete();
  }

  @Test
  public void skipAFew() {
    Flux<String> skipFlux = Flux
        .just("one", "two", "skip a few", "ninety nine", "one hundred")
        .skip(3);
    StepVerifier.create(skipFlux)
        .expectNext("ninety nine", "one hundred")
        .verifyComplete();
  }

  @Test
  public void skipAFewSeconds() {
    Flux<String> skipFlux = Flux
        .just("one", "two", "skip a few", "ninety nine", "one hundred")
        .delayElements(Duration.ofSeconds(1))
        .skip(Duration.ofSeconds(4));
    StepVerifier.create(skipFlux)
        .expectNext("ninety nine", "one hundred")
        .verifyComplete();
  }

  @Test
  public void take() {
    Flux<String> skipFlux = Flux
        .just("one", "two", "skip a few", "ninety nine", "one hundred")
        .take(3);
    StepVerifier.create(skipFlux)
        .expectNext("one", "two", "skip a few")
        .verifyComplete();
  }

  @Test
  void filter() {
    Flux<String> nationalParkFlux = Flux.just(
        "Yellowstone", "Yosemite", "Grand Canyon",
        "Zion", "Grand Teuton"
    ).filter(np -> !np.contains(" "));

    StepVerifier.create(nationalParkFlux)
        .expectNext("Yellowstone", "Yosemite", "Zion")
        .verifyComplete();
  }

  @Test
  void distinct() {
    Flux<String> animalFlux = Flux.just(
        "dog", "cat", "bird", "dog", "bird", "anteater"
    ).distinct();
    StepVerifier.create(animalFlux)
        .expectNext("dog", "cat", "bird", "anteater")
        .verifyComplete();
  }

  @Test
  void collectList() {
    Flux<String> fruitFlux = Flux.just("apple", "orange", "banana", "kiwi", "strawberry");
    Mono<List<String>> fruitListMono = fruitFlux.collectList();
    StepVerifier.create(fruitListMono)
        .expectNext(Arrays.asList("apple", "orange", "banana", "kiwi", "strawberry"))
        .verifyComplete();
  }

  @Test
  void collectMap() {
    Flux<String> animalFlux = Flux.just("aardvark", "elephant", "koala", "eagle", "kangaroo");
    Mono<Map<Object, String>> animalMapMono = animalFlux.collectMap(a -> a.charAt(0));
    StepVerifier
        .create(animalMapMono)
        .expectNextMatches(map -> map.size() == 3 &&
            map.get('a').equals("aardvark") &&
            map.get('e').equals("eagle") &&
            map.get('k').equals("kangaroo"))
        .verifyComplete();
  }

  @Test
  void all() {
    Flux<String> animalFlux = Flux.just("aardvark", "elephant", "koala", "eagle", "kangaroo");
    Mono<Boolean> hasAMono = animalFlux.all(a -> a.contains("a"));
    StepVerifier
        .create(hasAMono)
        .expectNext(true)
        .verifyComplete();

    Mono<Boolean> hasAMono2 = animalFlux.all(a -> a.contains("k"));
    StepVerifier
        .create(hasAMono2)
        .expectNext(false)
        .verifyComplete();
  }
  @Test
  void any() {
    Flux<String> animalFlux = Flux.just("aardvark", "elephant", "koala", "eagle", "kangaroo");
    Mono<Boolean> hasAMono = animalFlux.any(a -> a.contains("t"));
    StepVerifier
        .create(hasAMono)
        .expectNext(true)
        .verifyComplete();

    Mono<Boolean> hasAMono2 = animalFlux.any(a -> a.contains("z"));
    StepVerifier
        .create(hasAMono2)
        .expectNext(false)
        .verifyComplete();
  }


}
