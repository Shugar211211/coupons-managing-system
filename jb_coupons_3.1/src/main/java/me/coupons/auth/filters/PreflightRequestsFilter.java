package me.coupons.auth.filters;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

/**
 * Preflight request filter: this filter is used to handle CORS issues 
 * by adding necessary headers to preflight requests. 
 * It should be disabled in production environment. 
 * @author Eugeny Korobka
 * // https://stackoverflow.com/questions/29954037/why-is-an-options-request-sent-and-can-i-disable-it
 * // https://developer.mozilla.org/en-US/docs/Glossary/Preflight_request
 *
 */
@Component
@Order(0)
public class PreflightRequestsFilter extends OncePerRequestFilter{

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		
		final String requestMethod = request.getMethod();
		
		if( requestMethod.equalsIgnoreCase("OPTIONS") ) {
			response.addHeader("Access-Control-Allow-Origin", "*");
			response.addHeader("Access-Control-Allow-Methods", "GET,POST,PUT,DELETE,OPTIONS");
			response.addHeader("Access-Control-Allow-Headers", "Content-Type, "
																+ "Access-Control-Allow-Headers, "
																+ "Authorization, X-Requested-With");
			response.addHeader("Access-Control-Max-Age", "86400");
			response.setStatus(HttpStatus.OK.value());
//			filterChain.doFilter(request, response);
		}
		else {
			filterChain.doFilter(request, response);
		}
	}
}
