package rs.ac.uns.ftn.siit.isa_mrs.controller;

import org.junit.Before;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.nio.charset.Charset;

import static org.apache.http.HttpHeaders.AUTHORIZATION;
import static org.hamcrest.Matchers.hasItem;
import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
class ReviewControllerTest {
    private static final String URL_PREFIX = "/Reviews";

    private MediaType contentType = new MediaType(MediaType.APPLICATION_JSON.getType(),
            MediaType.APPLICATION_JSON.getSubtype());

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private WebApplicationContext webApplicationContext;

    @Before
    public void setup() {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
    }

    @Test
    void getReviews() throws Exception {
        mockMvc.perform(get(URL_PREFIX + "/getReviewsPage?page=0&pageSize=10").header(AUTHORIZATION, "Bearer eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJwcmlqb3ZpYy51cm9zMTNAZ21haWwuY29tIiwicm9sZXMiOlsiU3VwZXJBZG1pbiJdLCJpc3MiOiJodHRwOi8vbG9jYWxob3N0OjgwODAvbG9naW4iLCJleHAiOjE2NTcwNTg0MDB9.lAtwjfUrRTzpby9lStfHivCnMC9xQQpLdsSj0QasMJU")).andExpect(status().isOk())
                .andExpect(content().contentType(contentType)).andExpect(jsonPath("$.content", hasSize(1)))
                .andExpect(jsonPath("$.content[0].id").value(32))
                .andExpect(jsonPath("$.content[0].comment").value("Experienced expert"))
                .andExpect(jsonPath("$.content[0].grade").value(5))
                .andExpect(jsonPath("$.pageSize").value(10))
                .andExpect(jsonPath("$.pages").value(1))
                .andExpect(jsonPath("$.currentPage").value(0));
    }

}