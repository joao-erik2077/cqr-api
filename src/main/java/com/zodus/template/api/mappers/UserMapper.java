package com.zodus.template.api.mappers;

import com.zodus.template.api.dtos.responses.UserResponse;
import com.zodus.template.domain.models.User;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class UserMapper {
  public final UserResponse userToUserDto(User user) {
    return new UserResponse(user.getId(), user.getUsername(), user.getCreatedAt(), user.getUpdatedAt());
  }
}