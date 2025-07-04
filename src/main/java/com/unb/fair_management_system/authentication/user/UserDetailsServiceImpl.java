package com.unb.fair_management_system.authentication.user;

import com.unb.fair_management_system.authentication.role.Role;
import java.util.Collection;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {

  private final UserRepository userRepository;

  @Override
  public UserDetails loadUserByUsername(final String email) throws UsernameNotFoundException {
    final User user = userRepository.findByEmail(email)
                    .orElseThrow(() -> new UsernameNotFoundException("User not found with email: " + email));

    return new org.springframework.security.core.userdetails.User(
        user.getEmail(),
        user.getPassword(),
        mapRolesToAuthorities(user.getRoles())
    );
  }

  private Collection<? extends GrantedAuthority> mapRolesToAuthorities(final Collection<Role> roles) {
    return roles.stream()
               .map(role -> new SimpleGrantedAuthority("ROLE_" + role.getName()))
               .toList();
  }
}