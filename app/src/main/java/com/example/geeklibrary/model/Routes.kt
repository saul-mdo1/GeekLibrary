package com.example.geeklibrary.model

enum class Routes(val route: String) {
    HOME("home/{tabIndex}"),
    DETAILS("details/{id}/{type}"),
    ADD_ELEMENT("add-element/{type}"),
    UPDATE_ELEMENT("update/{id}/{type}");
}