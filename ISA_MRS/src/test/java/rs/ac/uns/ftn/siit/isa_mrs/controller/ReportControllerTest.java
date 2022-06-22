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

import static org.apache.http.HttpHeaders.AUTHORIZATION;
import static org.hamcrest.Matchers.hasSize;
import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
class ReportControllerTest {
    private static final String URL_PREFIX = "/Reports";

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
    void getReports() throws Exception {
        mockMvc.perform(get(URL_PREFIX + "/getReports?page=0&pageSize=10").header(AUTHORIZATION, "Bearer eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJwcmlqb3ZpYy51cm9zMTNAZ21haWwuY29tIiwicm9sZXMiOlsiU3VwZXJBZG1pbiJdLCJpc3MiOiJodHRwOi8vbG9jYWxob3N0OjgwODAvbG9naW4iLCJleHAiOjE2NTcwNTg0MDB9.lAtwjfUrRTzpby9lStfHivCnMC9xQQpLdsSj0QasMJU")).andExpect(status().isOk())
                .andExpect(content().contentType(contentType)).andExpect(jsonPath("$.content", hasSize(2)))
                .andExpect(jsonPath("$.pageSize").value(10))
                .andExpect(jsonPath("$.pages").value(1))
                .andExpect(jsonPath("$.currentPage").value(0));
    }

    @Test
    void getReportsFilter() throws Exception {
        mockMvc.perform(get(URL_PREFIX + "/getReportsFilter?page=0&pageSize=10&userType=Client").header(AUTHORIZATION, "Bearer eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJwcmlqb3ZpYy51cm9zMTNAZ21haWwuY29tIiwicm9sZXMiOlsiU3VwZXJBZG1pbiJdLCJpc3MiOiJodHRwOi8vbG9jYWxob3N0OjgwODAvbG9naW4iLCJleHAiOjE2NTcwNTg0MDB9.lAtwjfUrRTzpby9lStfHivCnMC9xQQpLdsSj0QasMJU")).andExpect(status().isOk())
                .andExpect(content().contentType(contentType)).andExpect(jsonPath("$.content", hasSize(1)))
                .andExpect(jsonPath("$.pageSize").value(10))
                .andExpect(jsonPath("$.pages").value(1))
                .andExpect(jsonPath("$.currentPage").value(0));
    }
}