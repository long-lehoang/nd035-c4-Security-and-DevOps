package com.example.demo.controllers;

import com.example.demo.model.persistence.Item;
import com.example.demo.model.persistence.repositories.ItemRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;

@SpringBootTest
class ItemControllerTest {
    @Autowired
    private ItemController itemController;

    @MockBean
    private ItemRepository itemRepository;

    @BeforeEach
    void setUp() {
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void testGetItems_ShouldReturn200() {
        List<Item> items = new ArrayList<>();
        given(itemRepository.findAll()).willReturn(items);

        assertEquals(itemController.getItems().getBody(), items);
    }

    @Test
    void testGetItemById_WhenGivenCorrectId_ShouldReturn200() {
        Item item = new Item();
        given(itemRepository.findById(any())).willReturn(Optional.of(item));

        assertEquals(HttpStatus.OK, itemController.getItemById(1L).getStatusCode());
    }

    @Test
    void testGetItemById_WhenGivenWrongId_ShouldReturn404() {
        Item item = new Item();
        given(itemRepository.findById(1L)).willReturn(Optional.of(item));

        assertEquals(HttpStatus.NOT_FOUND, itemController.getItemById(2L).getStatusCode());
    }

    @Test
    void testGetItemsByName_WhenGivenCorrectName_ShouldReturn200() {
        List<Item> items = new ArrayList<>();
        items.add(new Item());
        given(itemRepository.findByName(any())).willReturn(items);

        assertEquals(HttpStatus.OK, itemController.getItemsByName("").getStatusCode());

    }

    @Test
    void testGetItemsByName_WhenGivenWrongName_ShouldReturn404() {
        List<Item> items = new ArrayList<>();
        items.add(new Item());
        given(itemRepository.findByName("test")).willReturn(items);

        assertEquals(HttpStatus.NOT_FOUND, itemController.getItemsByName("").getStatusCode());
    }
}