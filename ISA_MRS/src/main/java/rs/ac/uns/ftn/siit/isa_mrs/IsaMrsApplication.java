package rs.ac.uns.ftn.siit.isa_mrs;

import org.modelmapper.ModelMapper;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.ui.freemarker.FreeMarkerConfigurationFactoryBean;
import rs.ac.uns.ftn.siit.isa_mrs.service.UserService;


@SpringBootApplication
@ConfigurationPropertiesScan
public class IsaMrsApplication {

    @Bean
    public ModelMapper modelMapper() {
        return new ModelMapper();
    }

    @Bean
    PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    public FreeMarkerConfigurationFactoryBean factoryBean() {
        FreeMarkerConfigurationFactoryBean bean = new FreeMarkerConfigurationFactoryBean();
        bean.setTemplateLoaderPath("classpath:/templates");
        return bean;
    }

    public static void main(String[] args) {
        SpringApplication.run(IsaMrsApplication.class, args);
    }

//    @Bean
//    CommandLineRunner run(UserService userService) {
//        return args -> {
//            userService.encryptUsersPasswords();
//        };
//   }
}
