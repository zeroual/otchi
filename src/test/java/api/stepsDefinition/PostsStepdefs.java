package api.stepsDefinition;

import com.otchi.application.PublicationsService;
import com.otchi.domain.social.models.Post;
import com.otchi.domain.social.models.Story;
import com.otchi.domain.social.repositories.PostRepository;
import cucumber.api.DataTable;
import cucumber.api.java.en.And;
import cucumber.api.java.en.Given;
import org.springframework.beans.factory.annotation.Autowired;

import static java.util.Collections.emptyList;
import static org.assertj.core.api.Assertions.assertThat;

public class PostsStepdefs {

    @Autowired
    private PostRepository postRepository;

    @Autowired
    private PublicationsService publicationsService;

    //FIXME Abdellah : we don't need to give post such information about user
    @Given("^those stories$")
    public void thoseStories(DataTable dataTable) throws Throwable {

        dataTable.asList(StoryCucumber.class).forEach(
                storyCucumber -> {
                    String author = storyCucumber.getAuthor();
                    Story story = new Story(storyCucumber.getContent());
                    publicationsService.publishStory(story, emptyList(), author);
                }
        );
    }

    @And("^the comment of \"([^\"]*)\" should be attached to the post with id \"([^\"]*)\"$")
    public void verifyThatUserCommentedOnPost(String username, Long postId) throws Throwable {
        Post post = postRepository.findOne(postId);
        boolean thisUserCommentOnThisPost = post.getComments().stream()
                .anyMatch(comment -> comment.getAuthor().getUsername().equals(username));
        assertThat(thisUserCommentOnThisPost).isTrue();
    }

    @And("^the post with id (\\d+) is removed$")
    public void verifyThatPostIsNotFound(Long postId) throws Throwable {
        Post post = postRepository.findOne(postId);
        assertThat(post).isNull();
    }

    class StoryCucumber {

        String author;
        String content;

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }

        public String getAuthor() {
            return author;
        }

        public void setAuthor(String author) {
            this.author = author;
        }

        public Story getStory() {
            return new Story(content);
        }
    }

}
