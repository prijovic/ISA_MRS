package rs.ac.uns.ftn.siit.isa_mrs;

import org.modelmapper.ModelMapper;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import rs.ac.uns.ftn.siit.isa_mrs.service.UserService;
import rs.ac.uns.ftn.siit.isa_mrs.service.VacationRentalService;

import static rs.ac.uns.ftn.siit.isa_mrs.util.Paths.CROSS_ORIGIN;


@SpringBootApplication
public class IsaMrsApplication {

    @Bean
    public ModelMapper modelMapper() {
        return new ModelMapper();
    }

    @Bean
    PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public WebMvcConfigurer corsConfigurer() {
        return new WebMvcConfigurer() {
            @Override
            public void addCorsMappings(CorsRegistry registry) {
                registry.addMapping("/**").allowedOrigins(CROSS_ORIGIN);
            }
        };
    }

    @Bean
    CommandLineRunner run(UserService userService, VacationRentalService vrc) {
        return args -> {
            userService.updateUserPassword(1L, "a");
            userService.updateUserPassword(2L, "123");
            userService.updateUserPassword(3L, "1");
            userService.updateUserPassword(4L, "s1");
//            vrc.getVacationRental(1L);
//            vrc.getVacationRentals();
        };
    }

    public static void main(String[] args) {
        SpringApplication.run(IsaMrsApplication.class, args);
    }


}
