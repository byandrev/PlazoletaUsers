package com.pragma.powerup.domain.spi;

public interface IBCryptPasswordEncoderPort {

    String encode(String rawPassword);

    boolean matches(String rawPassword, String encodedPassword);

}
