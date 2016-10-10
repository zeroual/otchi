package api.stepsDefinition;

import cucumber.api.DataTable;
import cucumber.api.java.en.And;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMultipartHttpServletRequestBuilder;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.ArrayList;
import java.util.List;

import static java.util.stream.Collectors.toList;
import static org.springframework.http.MediaType.MULTIPART_FORM_DATA;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class HttpStepsDefinitions {

    @Autowired
    protected MockMvc mockMvc;

    private ResultActions resultActions;

    private String clientUsername;

    private List<MockMultipartFile> uploadedFiles = new ArrayList<>();

    private String contentType = "application/json;charset=UTF-8";


    @Then("^the response code should be \"([^\"]*)\"$")
    public void checkResponseCode(int statusCode) throws Throwable {
        resultActions.andExpect(status().is(statusCode));
    }

    @When("^client request \"([^\"]*)\" \"([^\"]*)\" with json data:$")
    public void clientRequestWithJsonData(HttpMethod httpMethod, String resourceURI, String payload) throws Throwable {
        UriComponentsBuilder uriComponentsBuilder = UriComponentsBuilder.fromPath(resourceURI);
        String uriString = uriComponentsBuilder.build().toUriString();
        MockHttpServletRequestBuilder request = request(httpMethod, uriString);
        resultActions = mockMvc.perform(request.content(payload)
                .with(user(clientUsername)).with(csrf())
                .contentType(contentType));
    }


    @And("^I sign in as \"([^\"]*)\"$")
    public void iSignInAs(String username) throws Throwable {
        this.clientUsername = username;
    }

    @Given("^client upload those files :$")
    public void clientUploadThoseFiles(DataTable dataTable) throws Throwable {
        this.uploadedFiles = dataTable.asList(CucumberMultipartFile.class)
                .stream()
                .map(CucumberMultipartFile::toMockMultipartFile)
                .collect(toList());
    }

    @And("^upload json file \"([^\"]*)\" with following content$")
    public void uploadJsonFileWithFollowingContent(String fileName, String jsonContent) throws Throwable {
        MockMultipartFile jsonFile = new MockMultipartFile(fileName, "", contentType, jsonContent.getBytes());
        this.uploadedFiles.add(jsonFile);
    }

    @When("^client send uploaded files to \"([^\"]*)\"$")
    public void clientSendUploadedFilesTo(String resourceURI) throws Throwable {
        MockMultipartHttpServletRequestBuilder multipartHttpServletRequestBuilder = fileUpload(resourceURI);
        this.uploadedFiles.stream()
                .forEach(multipartHttpServletRequestBuilder::file);

        this.resultActions = mockMvc.perform(multipartHttpServletRequestBuilder
                .with(user(this.clientUsername)).with(csrf())
                .contentType(MULTIPART_FORM_DATA)).andDo(print());
    }

    @And("^the response body should be$")
    public void theResponseBodyShouldBe(String json) throws Throwable {
        resultActions.andExpect(content().json(json));
    }

    @When("^client send GET request \"([^\"]*)\"$")
    public void clientSendGETRequest(String resourceURI) throws Throwable {
        resultActions = mockMvc.perform(get(resourceURI)
                .with(user(clientUsername)).with(csrf())
                .contentType(contentType));
    }

    private class CucumberMultipartFile {
        private String name;
        private String fileName;
        private String type;

        MockMultipartFile toMockMultipartFile() {
            return new MockMultipartFile(this.name, this.fileName, this.type, new byte[]{});
        }
    }
}
