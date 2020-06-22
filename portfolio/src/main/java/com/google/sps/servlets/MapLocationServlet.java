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
        locations.add(new Location("Paris", 48.8566, 2.3522, ""));
    }

    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType("application/json");
        Gson gson = new Gson();
        String json = gson.toJson(locations);
        response.getWriter().println(json);
    }

}
