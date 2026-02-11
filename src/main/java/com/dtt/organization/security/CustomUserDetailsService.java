package com.dtt.organization.security;


import com.dtt.organization.model.TrustedUsersEntity;
import com.dtt.organization.repository.TrustedUsersRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import java.util.ArrayList;
import java.util.List;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    private final TrustedUsersRepository trustedUsersRepository;

    public CustomUserDetailsService(TrustedUsersRepository trustedUsersRepository) {
        this.trustedUsersRepository = trustedUsersRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username)
            throws UsernameNotFoundException {

        TrustedUsersEntity user = trustedUsersRepository.findByEmail(username);

        if (user == null) {
            throw new UsernameNotFoundException("User not found");
        }
        
        List<SimpleGrantedAuthority> authorities = new ArrayList<>();
        authorities.add(new SimpleGrantedAuthority("ROLE_USER"));

        return new CustomUserDetails(
                user.getEmail(),
                user.getName(),
                user.getPassword(),
                authorities
        );
    }
}
