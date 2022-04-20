package rs.ac.uns.ftn.siit.isa_mrs.security;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import rs.ac.uns.ftn.siit.isa_mrs.model.User;
import rs.ac.uns.ftn.siit.isa_mrs.repository.UserRepo;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Optional;

/** Class that specifies which user data will be used for the authentication. */
@Slf4j
@Service
@RequiredArgsConstructor
public class CustomUserDetailsServiceImpl implements UserDetailsService {
    private final UserRepo userRepo;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Optional<User> userResult = userRepo.findByEmail(email);
        if (userResult.isEmpty()) {
            throw new UsernameNotFoundException("User with email " + email + " was not found in the database.");
        }
        return userResult.get();
    }
}
