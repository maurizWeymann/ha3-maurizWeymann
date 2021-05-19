package htw.berlin.wi.prog2.data;

import htw.berlin.wi.prog2.domain.Ingredient;

import java.math.BigDecimal;
import java.util.*;

// Eine statische Datenbank-Klasse
public class Menu {
    private Menu() {}

    public static Map<Long, Ingredient> buns = Map.of(
            1L, new Ingredient("Vollkorn", new BigDecimal("0.60"), 120),
            2L, new Ingredient("Ciabatta", new BigDecimal("0.70"), 100));
    public static Map<Long, Ingredient> meat = Map.of(
            3L, new Ingredient("Salami", new BigDecimal("0.90"), 90),
            4L, new Ingredient("Schinken", new BigDecimal("0.90"), 80));
    public static Map<Long, Ingredient> salads = Map.of(
            5L, new Ingredient("Eisbergsalat", new BigDecimal("0.40"), 20),
            6L, new Ingredient("Rucolasalat", new BigDecimal("0.60"), 20));
    public static Map<Long, Ingredient> veggies = Map.of(
            7L, new Ingredient("Tomate", new BigDecimal("0.60"), 40),
            8L, new Ingredient("Gurke", new BigDecimal("0.30"), 20));
    public static Map<Long, Ingredient> sauces = Map.of(
            9L, new Ingredient("Mayo", new BigDecimal("0.30"), 20));

    public static Map<Long, Ingredient> getAllArticles() {
        Map<Long, Ingredient> articles = new HashMap<>();
        articles.putAll(buns);
        articles.putAll(meat);
        articles.putAll(salads);
        articles.putAll(veggies);
        articles.putAll(sauces);
        return articles;
    }

}
