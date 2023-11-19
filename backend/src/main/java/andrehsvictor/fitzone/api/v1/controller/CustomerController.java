package andrehsvictor.fitzone.api.v1.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.auth0.jwt.interfaces.DecodedJWT;

import andrehsvictor.fitzone.api.v1.dto.CustomerDTO;
import andrehsvictor.fitzone.api.v1.dto.TokenDTO;
import andrehsvictor.fitzone.api.v1.model.Customer;
import andrehsvictor.fitzone.api.v1.service.CustomerService;
import andrehsvictor.fitzone.api.v1.service.TokenService;
import andrehsvictor.fitzone.api.v1.util.Claims;
import andrehsvictor.fitzone.api.v1.util.FitzoneMapper;

@RestController
@RequestMapping("/api/v1")
@CrossOrigin
public class CustomerController {
    
    @Autowired
    private CustomerService service;

    @Autowired
    private TokenService tokenService;

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody String accessCode) {
        Customer customer = (Customer) service.loadByAccessCode(accessCode);
        TokenDTO tokenResponse = new TokenDTO(tokenService.generateToken(customer));
        return ResponseEntity.ok(tokenResponse);
    }

    @GetMapping("/customer")
    public ResponseEntity<?> getCustomer(@RequestHeader(value = HttpHeaders.AUTHORIZATION) String bearerToken) {
        String token = tokenService.formatToken(bearerToken);
        DecodedJWT decodedToken = tokenService.decodedToken(token);
        Customer customer = (Customer) service.loadByAccessCode(decodedToken.getClaim(Claims.ACCESS_CODE).asString());
        CustomerDTO customerDTO = FitzoneMapper.convert(customer, CustomerDTO.class);
        return ResponseEntity.ok(customerDTO);
    }

    @GetMapping("/customers")
    public ResponseEntity<?> getCustomers(@RequestParam(value = "onlyUsers", defaultValue = "false", required = false) String onlyUsers) {
        if(Boolean.parseBoolean(onlyUsers)) {
            List<CustomerDTO> customerDTOs = FitzoneMapper.convertList(service.findAllUsers(),CustomerDTO.class);
            return ResponseEntity.ok(customerDTOs);
        }
        List<Customer> customerList = service.findAll();
        List<CustomerDTO> customerDTOs = FitzoneMapper.convertList(customerList, CustomerDTO.class);
        return ResponseEntity.ok(customerDTOs);
    }

}
