package com.example.demo;

import com.example.demo.domain.Customer;
import com.example.demo.repository.CustomerRepository;
import org.junit.jupiter.api.Test;
import org.mockito.plugins.MockMaker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.RequestBuilder;

import javax.transaction.Transactional;

import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class CustomerControllerTest {

    @Autowired
    MockMvc mvc;

    @Autowired
    CustomerRepository repository;

    @Test @Transactional @Rollback
    public void testCreate() throws Exception{

        RequestBuilder rq = post("/customer/create").
                contentType(MediaType.APPLICATION_JSON).
                content("{\n" +
                        "    \"firstName\" : \"Arjun\",\n" +
                        "    \"lastName\" : \"Viswanathan\"\n" +
                        "}");
        this.mvc.perform(rq).
                andExpect(status().isOk()).
                andExpect(jsonPath("$.id",instanceOf(Number.class)));
    }

    @Test @Transactional @Rollback
    public void testGetCustomer() throws Exception{
        Customer c = new Customer();
        c.setLastName("Ashok");
        repository.save(c);
        RequestBuilder rq = get("/customer/getCustomers").
                            contentType(MediaType.APPLICATION_JSON);
        this.mvc.perform(rq).
                andExpect(status().isOk()).
                andExpect(jsonPath("$[0].lastName",is("Ashok"))).
                andExpect(jsonPath("$[0].id",equalTo(c.getId().intValue())));
    }
}
