package com.careclues.app.model;

import java.util.List;

public class DataModel {

    String first_name;
    String last_name;
    String rating;
    List<LinkModel> links;

    public String getFirst_name() {
        return first_name;
    }

    public String getLast_name() {
        return last_name;
    }

    public String getRating() {
        return rating;
    }

    public List<LinkModel> getLinkList() {
        return links;
    }
}
