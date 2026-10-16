package zadanie1;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api")
public class HelloController {

    // Эндпоинт 1: возвращает текст
    @GetMapping("/hello")
    public String hello() {
        return "Привет! Это задание 1, Spring Boot работает.";
    }

    // Эндпоинт 2: возвращает числа
    @GetMapping("/numbers")
    public List<Integer> numbers() {
        return List.of(1, 2, 3, 4, 5);
    }
}