package rs.ac.uns.ftn.siit.isa_mrs.security.filter;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import rs.ac.uns.ftn.siit.isa_mrs.model.User;
import rs.ac.uns.ftn.siit.isa_mrs.security.JwtGenerator;

import javax.servlet.FilterChain;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@Slf4j
@RequiredArgsConstructor
public class CustomAuthenticationFilter extends UsernamePasswordAuthenticationFilter {
    private final AuthenticationManager authenticationManager;
    private final JwtGenerator jwtGenerator;

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
        LoginRequest loginRequest = parseLoginRequest(request);
        String email = loginRequest != null ? loginRequest.getEmail() : null;
        String password = loginRequest != null ? loginRequest.getPassword() : null;
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(email, password);
        return authenticationManager.authenticate(authenticationToken);
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authentication) throws IOException{
        User user = (User) authentication.getPrincipal();
        String access_token = jwtGenerator.generateJwt(user.getUsername(),
                request.getRequestURL().toString(),
                user.getAuthorities().stream().map(GrantedAuthority::getAuthority).collect(Collectors.toList()),
                JwtGenerator.TokenPeriod.TwoDays);
        Map<String, String> tokens = new HashMap<>();
        tokens.put("access_token", access_token);
        tokens.put("user_type", user.getAuthorities().stream().map(GrantedAuthority::getAuthority).collect(Collectors.toList()).get(0));
        tokens.put("email", user.getUsername());
        tokens.put("first_login", user.getFirstLogin()?"true":"");
        response.setContentType(APPLICATION_JSON_VALUE);
        new ObjectMapper().writeValue(response.getOutputStream(), tokens);
    }

    /** Method parses through HttpServlet Login Request and returns LoginRequest*/
    private LoginRequest parseLoginRequest(HttpServletRequest request) {
        StringBuilder sb = new StringBuilder();
        BufferedReader bufferedReader;
        try {
            bufferedReader = request.getReader();
            String content;
            LoginRequest sr;

            char[] charBuffer = new char[128];
            int bytesRead;
            while ( (bytesRead = bufferedReader.read(charBuffer)) != -1 ) {
                sb.append(charBuffer, 0, bytesRead);
            }
            content = sb.toString();
            ObjectMapper objectMapper = new ObjectMapper();
            sr = objectMapper.readValue(content, LoginRequest.class);
            return sr;
        } catch (IOException e) {
            log.info(e.getMessage());
            return null;
        }
    }

    @Data
    static class LoginRequest {
        private String email;
        private String password;
    }
}
