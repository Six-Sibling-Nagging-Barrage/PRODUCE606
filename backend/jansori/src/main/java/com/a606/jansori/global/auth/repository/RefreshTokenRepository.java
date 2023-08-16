package com.a606.jansori.global.auth.repository;

import com.a606.jansori.global.auth.domain.RefreshToken;
import org.springframework.data.repository.CrudRepository;

public interface RefreshTokenRepository extends CrudRepository<RefreshToken, String> {
}
