package me.coupons.utils;

import org.springframework.security.crypto.argon2.Argon2PasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class Argon2PasswordEncoderExtension extends Argon2PasswordEncoder implements PasswordEncoder {

	// no-args constructor provides some default values
	public Argon2PasswordEncoderExtension() {
		super(32,64,1,15*1024,2);
	}

	public Argon2PasswordEncoderExtension(int saltLength, int hashLength, int parallelism, int memory, int iterations) {
		super(saltLength, hashLength, parallelism, memory, iterations);
	}
}
