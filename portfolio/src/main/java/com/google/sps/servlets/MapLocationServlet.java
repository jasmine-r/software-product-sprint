package com.google.sps.servlets;

import com.google.sps.data.Location;

import com.google.gson.Gson;
import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.PreparedQuery;
import com.google.appengine.api.datastore.Query;
import com.google.appengine.api.datastore.Query.SortDirection;

import java.io.IOException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.util.Collection;
import java.util.ArrayList;
import java.util.List;
import java.util.Arrays;

/** Servlet responsible for listing tasks. */
@WebServlet("/travel")
public class MapLocationServlet extends HttpServlet {

    private Collection<Location> locations;

    @Override
    public void init() {
        locations = new ArrayList<>();
        // hardcoded data -- TODO: revisit CSV idea 
        locations.add(new Location("Amsterdam", 52.3791, 4.89943, "<img src='/images/amsterdam.jpg'>"));
        locations.add(new Location("Barcelona", 41.3902, 2.154, "<img src='/images/barcelona.jpg'>"));
        locations.add(new Location("Berlin", 52.52, 13.405, "<img src='/images/berlin.jpg'>"));
        locations.add(new Location("Bruges", 51.2093, 3.2247, "<img src='/images/bruges.jpg'>"));
        locations.add(new Location("Copenhagen", 55.6761, 12.5683, "<img src='/images/copenhagen.jpg'>"));
        locations.add(new Location("Honolulu", 21.3069, -157.8583, "<img src='/images/palmtrees.jpg'>"));
        locations.add(new Location("Paris", 48.8566, 2.3522, ""));
        locations.add(new Location("Rome", 41.9028, 12.4964, "<img src='/images/rome.jpg'>"));
        locations.add(new Location("St. Petersburg", 59.9311, 30.3609, "<img src='/images/stpetersburg.jpg'>"));
        locations.add(new Location("Tallinn", 59.437, 24.7536, "<img src='/images/estonia.jpg'>"));
    }

    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType("application/json");
        Gson gson = new Gson();
        String json = gson.toJson(locations);
        response.getWriter().println(json);
    }

}
