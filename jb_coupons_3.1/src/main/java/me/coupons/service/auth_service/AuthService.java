package me.coupons.service.auth_service;

import me.coupons.auth.models.Principal;
import me.coupons.auth.models.UserDetails;

public interface AuthService {

	/**
	 * Authentication service
	 * @param userDetails
	 * @return principal
	 * @throws UserNotFoundException if can not authenticate.
	 */
	public Principal authenticate(UserDetails userDetails);
}
