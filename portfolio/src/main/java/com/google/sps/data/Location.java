package com.google.sps.data;

/* location for markers on the map */
public final class Location {

  private final String city;
  private final double latitude;
  private final double longitude;
  private final String image;

  public Location(String city, double latitude, double longitude, String image) {
    this.city = city;
    this.latitude = latitude;
    this.longitude = longitude;
    this.image = image;
  }
}
