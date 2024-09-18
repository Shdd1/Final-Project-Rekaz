package com.example.rekazfinalproject;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.example.rekazfinalproject.Api.ApiResponse;
import com.example.rekazfinalproject.Controller.BidController;
import com.example.rekazfinalproject.Model.Bid;
import com.example.rekazfinalproject.Model.User;
import com.example.rekazfinalproject.Service.BidService;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(SpringExtension.class)
@WebMvcTest(value = BidController.class, excludeAutoConfiguration = {SecurityAutoConfiguration.class})
public class BidControllerTest {

    @MockBean
    private BidService bidService;

    @Autowired
    private MockMvc mockMvc;

    private Bid bid1, bid2;
    private List<Bid> bids;

    @BeforeEach
    void setUp() {
        bid1 = new Bid();
        bid1.setId(1);
        bid1.setDescription("Bid Description 1");
        bid1.setDeadline(LocalDate.now().plusDays(5));
        bid1.setBudget(1000.0);
        bid1.setStatus(Bid.BidStatus.PENDING);
        bid1.setComment("Bid Comment 1");

        bid2 = new Bid();
        bid2.setId(2);
        bid2.setDescription("Bid Description 2");
        bid2.setDeadline(LocalDate.now().plusDays(10));
        bid2.setBudget(2000.0);
        bid2.setStatus(Bid.BidStatus.APPROVED);
        bid2.setComment("Bid Comment 2");

        bids = Arrays.asList(bid1, bid2);
    }

    @Test
    public void testGetAllBids() throws Exception {
        Mockito.when(bidService.findAllBids()).thenReturn(bids);

        mockMvc.perform(get("/api/v1/bid/get-all-bids"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", Matchers.hasSize(2)))
                .andExpect(jsonPath("$[0].description").value("Bid Description 1"))
                .andExpect(jsonPath("$[1].description").value("Bid Description 2"));
    }
}
