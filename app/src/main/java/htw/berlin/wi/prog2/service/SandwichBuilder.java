package htw.berlin.wi.prog2.service;

import htw.berlin.wi.prog2.domain.DynamicallyComputedSandwich;
import htw.berlin.wi.prog2.domain.Ingredient;
import htw.berlin.wi.prog2.domain.PrecomputedSandwich;
import htw.berlin.wi.prog2.domain.Sandwich;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class SandwichBuilder {

    private List<Ingredient> ingredients = new ArrayList<>();

    public enum CreationStyle {
        PRECOMPUTED,
        DYNAMICALLY_COMPUTED
    }

    private CreationStyle creationStyle;

    public void setCreationStyle(CreationStyle creationStyle) {
        this.creationStyle = creationStyle;
    }

    public SandwichBuilder add(Ingredient ingredient) {
        this.ingredients.add(ingredient);
        return this;
    }

    public Sandwich build() {
        if(ingredients.size() < 2) throw new IllegalSandwichException("Nicht genügend Zutaten");
        return creationStyle == CreationStyle.PRECOMPUTED ? buildPrecomputed() : buildDynamicallyComputed();
    }

    private Sandwich buildPrecomputed() {
        BigDecimal price = BigDecimal.ZERO;
        int calories = 0;
        List<String> ingredientNames = new ArrayList<>();

        for (Ingredient ing : ingredients) {
            price = price.add(ing.getPrice());
            calories += ing.getCalories();
            ingredientNames.add(ing.getName());
        }
        ingredients.clear();

        return new PrecomputedSandwich(price, calories, ingredientNames);
    }

    private Sandwich buildDynamicallyComputed() {
        List<Ingredient> ingsToPass = List.copyOf(ingredients);
        ingredients.clear();
        return new DynamicallyComputedSandwich(ingsToPass);
    }

    public SandwichBuilder(CreationStyle creationStyle) {
        this.creationStyle = creationStyle;
    }

    public SandwichBuilder() {
        this(CreationStyle.PRECOMPUTED);
    }
}
