package edu.java.scrapper.client.stackoverflow;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.github.tomakehurst.wiremock.junit5.WireMockExtension;
import com.github.tomakehurst.wiremock.junit5.WireMockTest;
import edu.java.clients.stackoverflow.StackoverflowClientImpl;
import edu.java.clients.stackoverflow.dto.StackoverflowItem;
import edu.java.clients.stackoverflow.dto.StackoverflowItemOwner;
import edu.java.clients.stackoverflow.dto.StackoverflowResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.web.reactive.function.client.WebClient;
import java.time.Instant;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.util.List;
import static com.github.tomakehurst.wiremock.client.WireMock.aResponse;
import static com.github.tomakehurst.wiremock.client.WireMock.get;
import static com.github.tomakehurst.wiremock.client.WireMock.stubFor;
import static com.github.tomakehurst.wiremock.client.WireMock.urlPathMatching;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@WireMockTest(httpPort = 8080)
@ExtendWith(WireMockExtension.class)
public class StackOverFlowClientTest {

    private StackoverflowClientImpl stackOverflowClient;
    private StackoverflowItemOwner correctOwner;
    private OffsetDateTime expectedCreationDate;
    private List<StackoverflowItem> correctItemsList;

    static final String BASE_URL = "http://localhost:8080/2.3/questions/";
    private static final int SCORE = 0;
    private static final boolean IS_ANSWERED = false;
    private static final int VIEW_COUNT = 3;
    private static final int ANSWER_COUNT = 0;

    private static final long ID = 78052845L;
    private static final String BODY = """
        {
          "items": [
            {
              "tags": [
                "javascript",
                "html",
                "css",
                "angular",
                "favicon"
              ],
              "owner": {
                "account_id": 30245120,
                "reputation": 1,
                "user_id": 23178971,
                "user_type": "registered",
                "profile_image": "https://lh3.googleusercontent.com/a/ACg8ocIsSSlqsDCR_HO21DY_BnCpdbVyLjKeek6AN6EVhK5G=k-s256",
                "display_name": "chandan gowda",
                "link": "https://stackoverflow.com/users/23178971/chandan-gowda"
              },
              "is_answered": false,
              "view_count": 3,
              "answer_count": 0,
              "score": 0,
              "last_activity_date": 1708785746,
              "creation_date": 1708785746,
              "question_id": 78052845,
              "content_license": "CC BY-SA 4.0",
              "link": "https://stackoverflow.com/questions/78052845/favicon-in-angular-17-not-working-i-tried-a-lot",
              "title": "Favicon in angular 17 not working I tried a lot"
            }
          ],
          "has_more": false,
          "quota_max": 300,
          "quota_remaining": 284
        }""";

    private WebClient webClient;

    @BeforeEach
    public void setup() {
        webClient = WebClient.builder().baseUrl(BASE_URL).build();
        stackOverflowClient = new StackoverflowClientImpl(webClient);
        correctOwner = new StackoverflowItemOwner(30245120,
            1, 23178971, "chandan gowda");
        expectedCreationDate = Instant.ofEpochSecond(1708785746).atOffset(ZoneOffset.UTC);
        correctItemsList = List.of(
            new StackoverflowItem(
                correctOwner,
                IS_ANSWERED,
                VIEW_COUNT,
                ANSWER_COUNT,
                SCORE,
                expectedCreationDate,
                ID
            )
        );
        stubFor(
            get(urlPathMatching("/2.3/questions/" + ID))
                .willReturn(aResponse()
                    .withStatus(200)
                    .withHeader("Content-Type", "application/json")
                    .withBody(BODY))
        );
    }

    @Test
    public void testStackOverFlowClient() {
        StackoverflowResponse response = stackOverflowClient.fetch(ID);
        assertThat(response).isNotNull();
        List<StackoverflowItem> actualItems = response.getItems();


        assertThat(actualItems).isEqualTo(correctItemsList);
    }
}
