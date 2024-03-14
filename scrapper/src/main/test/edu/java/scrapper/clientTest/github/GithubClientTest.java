package edu.java.scrapper.clientTest.github;

import com.github.tomakehurst.wiremock.junit5.WireMockExtension;
import com.github.tomakehurst.wiremock.junit5.WireMockTest;
import edu.java.clients.github.GithubClientImpl;
import edu.java.clients.github.dto.GithubOwner;
import edu.java.clients.github.dto.GithubResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.web.reactive.function.client.WebClient;
import java.time.OffsetDateTime;
import static com.github.tomakehurst.wiremock.client.WireMock.aResponse;
import static com.github.tomakehurst.wiremock.client.WireMock.stubFor;
import static com.github.tomakehurst.wiremock.client.WireMock.urlPathMatching;
import static com.github.tomakehurst.wiremock.client.WireMock.get;
import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;

@WireMockTest(httpPort = 8080)
@ExtendWith(WireMockExtension.class)
public class GithubClientTest {

    static final String BASE_URL = "http://localhost:8080/repos/";
    private static final String OWNER_NAME = "chernookd";
    private static final String REPOS = "link__tracker";
    private GithubOwner correctOwner;
    private OffsetDateTime correctUpdatedAtTime;
    private long correctId;
    private String correctName;
    private GithubClientImpl githubClient;
    private static final String BODY = """
        {
           "id": 755153967,
           "node_id": "R_kgDOLQK8Lw",
           "name": "link__tracker",
           "full_name": "chernookd/link__tracker",
           "private": false,
           "owner": {
             "login": "chernookd",
             "id": 130858532,
             "node_id": "U_kgDOB8y-JA",
             "avatar_url": "https://avatars.githubusercontent.com/u/130858532?v=4",
             "gravatar_id": "",
             "url": "https://api.github.com/users/chernookd",
             "html_url": "https://github.com/chernookd",
             "followers_url": "https://api.github.com/users/chernookd/followers",
             "following_url": "https://api.github.com/users/chernookd/following{/other_user}",
             "gists_url": "https://api.github.com/users/chernookd/gists{/gist_id}",
             "starred_url": "https://api.github.com/users/chernookd/starred{/owner}{/repo}",
             "subscriptions_url": "https://api.github.com/users/chernookd/subscriptions",
             "organizations_url": "https://api.github.com/users/chernookd/orgs",
             "repos_url": "https://api.github.com/users/chernookd/repos",
             "events_url": "https://api.github.com/users/chernookd/events{/privacy}",
             "received_events_url": "https://api.github.com/users/chernookd/received_events",
             "type": "User",
             "site_admin": false
           },
           "html_url": "https://github.com/chernookd/link__tracker",
           "description": null,
           "fork": false,
           "url": "https://api.github.com/repos/chernookd/link__tracker",
           "forks_url": "https://api.github.com/repos/chernookd/link__tracker/forks",
           "keys_url": "https://api.github.com/repos/chernookd/link__tracker/keys{/key_id}",
           "collaborators_url": "https://api.github.com/repos/chernookd/link__tracker/collaborators{/collaborator}",
           "teams_url": "https://api.github.com/repos/chernookd/link__tracker/teams",
           "hooks_url": "https://api.github.com/repos/chernookd/link__tracker/hooks",
           "issue_events_url": "https://api.github.com/repos/chernookd/link__tracker/issues/events{/number}",
           "events_url": "https://api.github.com/repos/chernookd/link__tracker/events",
           "assignees_url": "https://api.github.com/repos/chernookd/link__tracker/assignees{/user}",
           "branches_url": "https://api.github.com/repos/chernookd/link__tracker/branches{/branch}",
           "tags_url": "https://api.github.com/repos/chernookd/link__tracker/tags",
           "blobs_url": "https://api.github.com/repos/chernookd/link__tracker/git/blobs{/sha}",
           "git_tags_url": "https://api.github.com/repos/chernookd/link__tracker/git/tags{/sha}",
           "git_refs_url": "https://api.github.com/repos/chernookd/link__tracker/git/refs{/sha}",
           "trees_url": "https://api.github.com/repos/chernookd/link__tracker/git/trees{/sha}",
           "statuses_url": "https://api.github.com/repos/chernookd/link__tracker/statuses/{sha}",
           "languages_url": "https://api.github.com/repos/chernookd/link__tracker/languages",
           "stargazers_url": "https://api.github.com/repos/chernookd/link__tracker/stargazers",
           "contributors_url": "https://api.github.com/repos/chernookd/link__tracker/contributors",
           "subscribers_url": "https://api.github.com/repos/chernookd/link__tracker/subscribers",
           "subscription_url": "https://api.github.com/repos/chernookd/link__tracker/subscription",
           "commits_url": "https://api.github.com/repos/chernookd/link__tracker/commits{/sha}",
           "git_commits_url": "https://api.github.com/repos/chernookd/link__tracker/git/commits{/sha}",
           "comments_url": "https://api.github.com/repos/chernookd/link__tracker/comments{/number}",
           "issue_comment_url": "https://api.github.com/repos/chernookd/link__tracker/issues/comments{/number}",
           "contents_url": "https://api.github.com/repos/chernookd/link__tracker/contents/{+path}",
           "compare_url": "https://api.github.com/repos/chernookd/link__tracker/compare/{base}...{head}",
           "merges_url": "https://api.github.com/repos/chernookd/link__tracker/merges",
           "archive_url": "https://api.github.com/repos/chernookd/link__tracker/{archive_format}{/ref}",
           "downloads_url": "https://api.github.com/repos/chernookd/link__tracker/downloads",
           "issues_url": "https://api.github.com/repos/chernookd/link__tracker/issues{/number}",
           "pulls_url": "https://api.github.com/repos/chernookd/link__tracker/pulls{/number}",
           "milestones_url": "https://api.github.com/repos/chernookd/link__tracker/milestones{/number}",
           "notifications_url": "https://api.github.com/repos/chernookd/link__tracker/notifications{?since,all,participating}",
           "labels_url": "https://api.github.com/repos/chernookd/link__tracker/labels{/name}",
           "releases_url": "https://api.github.com/repos/chernookd/link__tracker/releases{/id}",
           "deployments_url": "https://api.github.com/repos/chernookd/link__tracker/deployments",
           "created_at": "2024-02-09T14:35:22Z",
           "updated_at": "2024-02-09T14:35:30Z",
           "pushed_at": "2024-02-19T20:10:34Z",
           "git_url": "git://github.com/chernookd/link__tracker.git",
           "ssh_url": "git@github.com:chernookd/link__tracker.git",
           "clone_url": "https://github.com/chernookd/link__tracker.git",
           "svn_url": "https://github.com/chernookd/link__tracker",
           "homepage": null,
           "size": 92,
           "stargazers_count": 0,
           "watchers_count": 0,
           "language": "Java",
           "has_issues": true,
           "has_projects": true,
           "has_downloads": true,
           "has_wiki": true,
           "has_pages": false,
           "has_discussions": false,
           "forks_count": 0,
           "mirror_url": null,
           "archived": false,
           "disabled": false,
           "open_issues_count": 1,
           "license": null,
           "allow_forking": true,
           "is_template": false,
           "web_commit_signoff_required": false,
           "topics": [

           ],
           "visibility": "public",
           "forks": 0,
           "open_issues": 1,
           "watchers": 0,
           "default_branch": "master",
           "temp_clone_token": null,
           "network_count": 0,
           "subscribers_count": 1
         }""";




    private WebClient webClient;

    @BeforeEach
    public void setup() {
        webClient = WebClient.builder().baseUrl(BASE_URL).build();
        correctOwner = new GithubOwner(OWNER_NAME, 130858532);
        correctUpdatedAtTime = OffsetDateTime.parse("2024-02-09T14:35:30Z");
        correctId = 755153967;
        correctName = "link__tracker";
        githubClient = new GithubClientImpl(webClient);

        stubFor(
            get(urlPathMatching("/repos/" + OWNER_NAME + "/" + REPOS))
                .willReturn(aResponse()
                    .withStatus(200)
                    .withHeader("Content-Type", "application/json")
                    .withBody(BODY))
        );
    }


    @Test
    public void testGitHubClient() {
        GithubResponse response = githubClient.fetch(OWNER_NAME, REPOS);


        assertThat(response).isNotNull();
        assertThat(response.getId()).isEqualTo(correctId);
        assertThat(response.getName()).isEqualTo(correctName);
        assertThat(response.getUpdatedAt()).isEqualTo(correctUpdatedAtTime);
        assertThat(response.getOwner()).isEqualTo(correctOwner);

    }
}
