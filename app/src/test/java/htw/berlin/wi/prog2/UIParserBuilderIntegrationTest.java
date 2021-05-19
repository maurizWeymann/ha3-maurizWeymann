package htw.berlin.wi.prog2;

import htw.berlin.wi.prog2.service.parsing.CountingInputParser;
import htw.berlin.wi.prog2.service.parsing.ExtendableInputParser;
import htw.berlin.wi.prog2.service.SandwichBuilder;
import htw.berlin.wi.prog2.ui.CommandLineUI;
import htw.berlin.wi.prog2.ui.UserInputWrapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.AdditionalMatchers.not;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

public class UIParserBuilderIntegrationTest {

    @Test
    @DisplayName("should state the correct ingredients and price")
    void integrationWithCountingParser() {
        String initialQuestion = "Was möchtest du gerne bestellen?";
        UserInputWrapper input = mock(UserInputWrapper.class);
        when(input.ask(contains(initialQuestion)))
                .thenReturn("Ich hätte gerne ein Vollkorn-Sandwich mit Rucolasalat, Schinken, Schinken und Mayo");
        when(input.ask(not(contains(initialQuestion)))).thenReturn("Auf Wiedersehen");

        SandwichBuilder builder = new SandwichBuilder(SandwichBuilder.CreationStyle.PRECOMPUTED);

        ExtendableInputParser parser = new CountingInputParser();

        CommandLineUI ui = new CommandLineUI(input, builder, parser);

        assertEquals("Auf Wiedersehen", ui.launch());

        String expectedQuestion =
                "In Ordnung. Dein Sandwich mit [Mayo, Rucolasalat, Schinken, Schinken, Vollkorn] kostet 3.30 Euro. Willst du die Bestellung abschliessen?";

        verify(input).ask(expectedQuestion);
    }
}
