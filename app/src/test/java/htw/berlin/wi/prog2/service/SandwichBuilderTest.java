package htw.berlin.wi.prog2.service;

import htw.berlin.wi.prog2.data.Menu;
import htw.berlin.wi.prog2.domain.Ingredient;
import htw.berlin.wi.prog2.domain.Sandwich;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;

class SandwichBuilderTest {

    private final SandwichBuilder builder  = new SandwichBuilder();

    private final Ingredient kaese = new Ingredient("Käse", new BigDecimal("0.01"), 2000);
    private final Ingredient brot = new Ingredient("Brot", new BigDecimal("0.02"), 1000);

    @Test
    @DisplayName("can build a precomputed sandwich with two ingredients")
    void buildASandwich() {
        builder.setCreationStyle(SandwichBuilder.CreationStyle.PRECOMPUTED);
        Sandwich sandwich = builder.add(brot).add(kaese).build();

        assertEquals(List.of("Brot", "Käse"), sandwich.getIngredientNames());
        assertEquals(new BigDecimal("0.03"), sandwich.calculatePrice());
        assertEquals(3000, sandwich.calculateCalories());
    }

    @Test
    @DisplayName("can build two precomputed sandwiches after another without mixing things up")
    void buildTwoSandwiches() {
        builder.setCreationStyle(SandwichBuilder.CreationStyle.PRECOMPUTED);
        Sandwich sandwich1 = builder.add(brot).add(kaese).build();
        Sandwich sandwich2 = builder.add(brot).add(brot).build();

        assertEquals(List.of("Brot", "Käse"), sandwich1.getIngredientNames());
        assertEquals(List.of("Brot", "Brot"), sandwich2.getIngredientNames());
    }

    @Test
    @DisplayName("can build a dynamically computed sandwich with two ingredients")
    void buildASandwichDynamically() {
        builder.setCreationStyle(SandwichBuilder.CreationStyle.DYNAMICALLY_COMPUTED);
        Sandwich sandwich = builder.add(brot).add(kaese).build();

        assertEquals(List.of("Brot", "Käse"), sandwich.getIngredientNames());
        assertEquals(new BigDecimal("0.03"), sandwich.calculatePrice());
        assertEquals(3000, sandwich.calculateCalories());
    }

    @Test
    @DisplayName("can build two dynamically computed sandwiches after another without mixing things up")
    void buildTwoSandwichesDynamically() {
        builder.setCreationStyle(SandwichBuilder.CreationStyle.DYNAMICALLY_COMPUTED);
        Sandwich sandwich1 = builder.add(brot).add(kaese).build();
        Sandwich sandwich2 = builder.add(brot).add(brot).build();

        assertEquals(List.of("Brot", "Käse"), sandwich1.getIngredientNames());
        assertEquals(List.of("Brot", "Brot"), sandwich2.getIngredientNames());
    }

    @Test
    @DisplayName("a sandwich should have at least two ingredients")
    void checkNumberOfIngredients() {
        assertThrows(IllegalSandwichException.class, builder::build);
        assertThrows(IllegalSandwichException.class, () -> builder.add(brot).build());
    }
}