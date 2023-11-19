package andrehsvictor.fitzone.api.v1.filter;

import java.io.IOException;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import andrehsvictor.fitzone.api.v1.model.Customer;
import andrehsvictor.fitzone.api.v1.service.CustomerService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class AccountExpiredFilter extends OncePerRequestFilter {

    @Autowired
    private CustomerService customerService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if(auth.getPrincipal() instanceof Customer) {
            Customer customer = (Customer) auth.getPrincipal();
            if(customerIsExpired(customer)) {
                customer.setAccountNonExpired(false);
                customerService.save(customer);
                response.sendError(HttpServletResponse.SC_UNAUTHORIZED);
                return;
            }

        }
        filterChain.doFilter(request, response);
    }

    private boolean customerIsExpired(Customer customer) {
        Date customerExpirationDate = customer.getExpiration();
        if(customerExpirationDate != null)
            return customerExpirationDate.before(new Date());
        return false;
    }

}
