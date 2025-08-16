package com.zodus.template.application.services;

import com.zodus.template.domain.models.User;
import com.zodus.template.domain.repositories.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;
import java.util.UUID;

@Service
@AllArgsConstructor
public class UserService implements UserDetailsService {
  private final UserRepository repository;

  public Optional<User> findByUsername(String username) {
    return repository.findByUsername(username);
  }

  public User findById(UUID id) throws ResponseStatusException {
    return repository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found for id"));
  }

  public User save(User user) {
    return repository.save(user);
  }

  public Page<User> findAll(Pageable pageable, Specification<User> specification) {
    return repository.findAll(specification, pageable);
  }

  @Override
  public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
    return findByUsername(username).orElseThrow(() -> new UsernameNotFoundException("User not found for username " + username));
  }
}