package htw.berlin.wi.prog2.ui;

import htw.berlin.wi.prog2.data.Menu;
import htw.berlin.wi.prog2.data.MenuUtils;
import htw.berlin.wi.prog2.domain.Ingredient;
import htw.berlin.wi.prog2.domain.Sandwich;
import htw.berlin.wi.prog2.service.parsing.ExtendableInputParser;
import htw.berlin.wi.prog2.service.SandwichBuilder;

import java.util.*;
import java.util.stream.Collectors;

public class CommandLineUI {
    private final UserInputWrapper input;
    private final SandwichBuilder builder;
    private final ExtendableInputParser parser;

    public CommandLineUI(UserInputWrapper uiw, SandwichBuilder sb, ExtendableInputParser ip) {
        input = uiw;
        builder = sb;
        parser = ip;
    }

    public String launch() {
        String inputLine = input.ask("Willkommen beim Sandwichbot! Was möchtest du gerne bestellen?");
        Map<Long, Ingredient> articles = Menu.getAllArticles();
        while (!(inputLine.equals("Bestellung abschliessen") || inputLine.equals("Auf Wiedersehen"))) {
            Map<String, Long> keywordsToIds = MenuUtils.focusOnNameAndInvert(articles);
            Map<Long, Integer> ingredientsCount = parser.idsAndCountFromInput(inputLine, keywordsToIds);
            List<Ingredient> ingredients = MenuUtils.ingredientsFromIdAndCount(ingredientsCount, articles);
            if(ingredients.isEmpty()) {
                inputLine = input.ask("Entschuldigung, ich habe dich nicht verstanden. Wähle aus folgenden Zutaten: "
                        + MenuUtils.focusOnNames(articles));
            } else {
                for (Ingredient ing : ingredients) builder.add(ing);
                Sandwich sandwich = builder.build();
                List<String> ingrNames = sandwich.getIngredientNames().stream().sorted().collect(Collectors.toList());
                inputLine = input.ask("In Ordnung. Dein Sandwich mit " + ingrNames +
                        " kostet " + sandwich.calculatePrice() + " Euro. Willst du die Bestellung abschliessen?");
            }
        }
        return inputLine;
    }

}
