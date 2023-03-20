package com.elykp.coreservice.users;

import com.elykp.coreservice.users.domain.KcUserRS;
import com.elykp.coreservice.users.domain.UpdateUserAttributeRQ;
import java.util.Optional;

public interface UserService {

  Optional<String> requestAccessTokenFromIS();

  KcUserRS getKcUserById(String userId);

  void updateUserAttributes(String userId, UpdateUserAttributeRQ updateUserAttributeRQ,
      String accessToken);
}
