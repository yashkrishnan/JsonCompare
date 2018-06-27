package com.example.jsoncompare;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Author : Yashwanth Krishnan
 */
public class JsonCompare {

    /**
     * Compares two different json array of objects regarding a matchable key in each array.
     * In the given example key1 in array A is comparable towards val2 of array B
     *
     * @param args unused in program logic
     */
    public static void main(String[] args) {
        String A = "[{\"key1\":1234,\"key2\":100,\"key3\":\"S1\"},{\"key1\":4321,\"key2\":187,\"key3\":\"S2\"},{\"key1\":8212,\"key2\":841,\"key3\":\"S9\"}]";
        String B = "[{\"val1\":\"OR89879\",\"val2\":4321,\"val3\":182,\"val4\":\"S2\"},{\"val1\":\"OR89879\",\"val2\":8482,\"val3\":840,\"val4\":\"S5\"}]";

        // Java reflection type token of type JsonObject which can be used to seamlessly parse Json array of objects to
        // list of JsonObject
        Type typeJsonObject = new TypeToken<ArrayList<JsonObject>>() {
        }.getType();

        // Instance of basic Gson object to perform json operations
        Gson gson = new Gson();

        // Parsing each json array strings to respective lists of JsonObject
        List<JsonObject> jsonDataAList = gson.fromJson(A, typeJsonObject);
        List<JsonObject> jsonDataBList = gson.fromJson(B, typeJsonObject);

        // Instance of JsonCompare class to access other non-static methods of the class
        JsonCompare jsonCompare = new JsonCompare();

        // Getting excess of json objects in each list compared towards the other one
        List<JsonObject> excessInA = jsonCompare.getExcessElements(jsonDataAList, jsonDataBList, "key1", "val2");
        List<JsonObject> excessInB = jsonCompare.getExcessElements(jsonDataBList, jsonDataAList, "val2", "key1");

        // Prints the outputs to console screen
        jsonCompare.printOutputs(jsonDataAList, jsonDataBList, excessInA, excessInB);

    }

    /**
     * Compares two json object arrays according to comparable keys in each object of both array and filters
     *
     * @param primaryList   Primary json list
     * @param secondaryList Secondary json list
     * @param primaryKey    Comparable key in primary json array
     * @param secondaryKey  Comparable key in secondary json array
     * @return excessInPrimary Excess of items in primaryList compared to secondaryList
     */
    private List<JsonObject> getExcessElements(List<JsonObject> primaryList, List<JsonObject> secondaryList, String primaryKey, String secondaryKey) {
        List<JsonObject> excessInPrimary = primaryList.stream()
                .filter(e -> (secondaryList.stream()
                        .filter(d -> d.get(secondaryKey).equals(e.get(primaryKey)))
                        .count()) < 1)
                .collect(Collectors.toList());
        return excessInPrimary;
    }

    /**
     * Prints the outputs to console screen
     *
     * @param jsonDataAList Object of first json array A
     * @param jsonDataBList Object of second json array B
     * @param excessInA     List of items excess in array A compared to array B
     * @param excessInB     List of items excess in array B compared to array A
     */
    private void printOutputs(List<JsonObject> jsonDataAList, List<JsonObject> jsonDataBList, List<JsonObject> excessInA, List<JsonObject> excessInB) {
        Gson gson = new GsonBuilder().setPrettyPrinting().create();

        // Prints existing Array A
        System.out.println("Array A : \n");
        System.out.println(gson.toJson(jsonDataAList));
        System.out.println();

        // Prints existing Array B
        System.out.println("Array B : \n");
        System.out.println(gson.toJson(jsonDataBList));
        System.out.println();

        // Prints excess items in Array A compared to Array B
        System.out.println("Excess items in Array A : \n");
        System.out.println(gson.toJson(excessInA));
        System.out.println();

        // Prints excess items in Array B compared to Array A
        System.out.println("Excess items in Array B : \n");
        System.out.println(gson.toJson(excessInB));
        System.out.println();
    }
}
