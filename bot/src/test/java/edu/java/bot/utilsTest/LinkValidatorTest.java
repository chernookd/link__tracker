package edu.java.bot.utilsTest;

import edu.java.bot.utils.LinkValidator;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import java.util.stream.Stream;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class LinkValidatorTest {

    static Stream<Arguments> source() {
        return Stream.of(
            Arguments.of("https://spring-projects.ru/projects/", true),
            Arguments.of("https://core.telegram.org/bots/api#menubutton", true),
            Arguments.of("https://habr.com/ru/companies/otus/articles/780090/", true),
            Arguments.of("уацуагрцуарцуа", false),
            Arguments.of("rfewfwefewfwefefwwef", false),
            Arguments.of("https://", false)
        );
    }

    @MethodSource("source")
    @ParameterizedTest
    void linkValidationTest(String linkStr, Boolean correctAnswer) {
        Boolean currentAnswer = LinkValidator.isValidLink(linkStr);
        assertThat(currentAnswer.equals(correctAnswer)).isTrue();
    }
}
