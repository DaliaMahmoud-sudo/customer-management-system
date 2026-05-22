package com.example.service;


import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.example.model.Customer;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.List;

public class CustomerService {

    private static final String BASE_URL = "http://localhost:8080/customers";

    private final OkHttpClient client = new OkHttpClient();

    private final Gson gson = new Gson();

      // =========================
    // GET ALL CUSTOMERS
    // =========================
    public List<Customer> getAllCustomers() throws IOException {

        Request request = new Request.Builder()
                .url(BASE_URL)
                .get()
                .build();

        try (Response response = client.newCall(request).execute()) {

            String json = response.body().string();

            Type listType = new TypeToken<List<Customer>>() {}.getType();

            return gson.fromJson(json, listType);
        }
    }

    // =========================
    // ADD CUSTOMER (POST)
    // =========================
    public void addCustomer(Customer customer) throws IOException {

        String json = gson.toJson(customer);

        RequestBody body = RequestBody.create(
                json,
                MediaType.get("application/json")
        );

        Request request = new Request.Builder()
                .url(BASE_URL)
                .post(body)
                .build();

        try (Response response = client.newCall(request).execute()) {

            if (!response.isSuccessful()) {
                throw new IOException("Failed to add customer: " + response.message());
            }
        }
    }

    // =========================
    // UPDATE CUSTOMER (PUT)
    // =========================
    public void updateCustomer(int id, Customer customer) throws IOException {

        String json = gson.toJson(customer);

        RequestBody body = RequestBody.create(
                json,
                MediaType.get("application/json")
        );

        Request request = new Request.Builder()
                .url(BASE_URL + "/" + id)
                .put(body)
                .build();

        try (Response response = client.newCall(request).execute()) {

            if (!response.isSuccessful()) {
                throw new IOException("Failed to update customer: " + response.message());
            }
        }
    }

    // =========================
    // DELETE CUSTOMER
    // =========================
    public void deleteCustomer(int id) throws IOException {

        Request request = new Request.Builder()
                .url(BASE_URL + "/" + id)
                .delete()
                .build();

        try (Response response = client.newCall(request).execute()) {

            if (!response.isSuccessful()) {
                throw new IOException("Failed to delete customer: " + response.message());
            }
        }
    }
}
