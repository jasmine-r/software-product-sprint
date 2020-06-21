package com.google.sps.data;

/** A comment on the portfolio */
public final class Comment {

  private final String name;
  private final String comment;
  private final long timestamp;

  public Comment(String name, String comment, long timestamp) {
    this.name = name;
    this.comment = comment;
    this.timestamp = timestamp;
  }
}
