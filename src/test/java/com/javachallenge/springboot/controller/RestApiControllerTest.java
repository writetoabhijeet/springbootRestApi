package com.javachallenge.springboot.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.javachallenge.springboot.SpringBootRestApiApp;
import com.javachallenge.springboot.model.Shop;
import com.javachallenge.springboot.service.GoogleService;
import com.javachallenge.springboot.service.ShopService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.hamcrest.CoreMatchers.containsString;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * Created by USER on 29-01-2017.
 */

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {SpringBootRestApiApp.class})
@WebAppConfiguration
public class RestApiControllerTest {

    private MockMvc mockMvc;

    @InjectMocks
    private RestApiController controller;

    @Mock
    private ShopService shopService;

    @Mock
    private GoogleService googleService;

    public static String asJsonString(final Object obj) {
        try {
            final ObjectMapper mapper = new ObjectMapper();
            return mapper.writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Before
    public void init() {
        MockitoAnnotations.initMocks(this);
        mockMvc = MockMvcBuilders
                .standaloneSetup(controller)
                .build();
    }

    @Test
    public void testAddShop() throws Exception {

        Shop shop = new Shop();

        doNothing().when(googleService).setLatLongForShop(shop);
        doNothing().when(shopService).saveShop(shop);

        mockMvc.perform(
                post("/api/shop/")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(shop)))
                .andExpect(status().isCreated())
                .andExpect(header().string("location", containsString("/shop/0")));

        verify(googleService, times(1)).setLatLongForShop(shop);
        verify(shopService, times(1)).saveShop(shop);
        verifyNoMoreInteractions(shopService);

    }

    @Test
    public void testGetAllShop() throws Exception {


    }

    @Test
    public void testGetNearByShop() throws Exception {

    }
}