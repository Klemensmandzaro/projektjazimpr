package org.example.service;

import org.example.model.Item;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

import java.util.List;

@Service
public class MyViewService {
    private RestClient restClient;

    public MyViewService(RestClient restClient) {
        this.restClient = restClient;
    }

    public List<Item> findSelectedItems() {
        List<Item> itemList = restClient.get()
                .uri("http://localhost:8082/getselecteditems?start=1434&limit=10")
                .retrieve()
                .body(new ParameterizedTypeReference<>() {});

        return itemList;
    }
}
