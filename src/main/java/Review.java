// import java.util.Date;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import org.sql2o.*;

public class Review {
  private String reviewer;
  private LocalDateTime createdAt;
  // private Date createdAt;
  private boolean apple;
  private String content;
  private int id;
  private int movieId;

  public Review(String reviewer, String content, int movieId) {
    this.reviewer = reviewer;
    this.content = content;
    this.movieId = movieId;
    createdAt = LocalDateTime.now();
    this.movieId = movieId;
  }

  public String getReviewer() {
    return reviewer;
  }

  public String getContent() {
    return content;
  }

  public boolean isApple() {
    return apple;
  }

  public int getMovieId() {
    return movieId;
  }

  public int getId() {
    return id;
  }

  // private Date getCreatedAt() {
  //   return createdAt;
  // }

  public LocalDateTime getCreatedAt() {
    return createdAt;
  }

  public static List<Review> all() {
    String sql = "SELECT * FROM review;";
    try(Connection con = DB.sql2o.open()) {
      return con.createQuery(sql).executeAndFetch(Review.class);
    }
  }

  public void save() {
    try(Connection con = DB.sql2o.open()) {
      String sql = "INSERT INTO review(reviewer, content, movieId) VALUES (:reviewer, :content, :movieId);";
      this.id = (int) con.createQuery(sql, true)
        .addParameter("reviewer", this.reviewer)
        .addParameter("content", this.content)
        .addParameter("movieId", this.movieId)
        .executeUpdate()
        .getKey();
    }
  }

  public static Review find(int id) {
    try(Connection con = DB.sql2o.open()) {
      String sql = "SELECT * FROM review WHERE id=:id;";
      Review review = con.createQuery(sql)
        .addParameter("id", id)
        .executeAndFetchFirst(Review.class);
      return review;
    }
  }

  public void delete() {
    try(Connection con = DB.sql2o.open()) {
      String sql = "DELETE FROM review WERE id=:id;";
      con.createQuery(sql)
        .addParameter("id", id)
        .executeUpdate();
    }
  }

  public void update(String content) {
    try(Connection con = DB.sql2o.open()) {
      String sql = "UPDATE review SET content = :content WHERE id=:id;";
      con.createQuery(sql)
        .addParameter("content", content)
        .addParameter("id", id)
        .executeUpdate();
    }
  }

  @Override
  public boolean equals(Object otherReview) {
    if(!(otherReview instanceof Review)) {
      return false;
    } else {
      Review newReview = (Review) otherReview;
      return this.getReviewer().equals(newReview.getReviewer()) &&
        this.getContent().equals(newReview.getContent()) &&
        this.getId() == newReview.getId() &&
        this.getMovieId() == newReview.getId();
    }
  }
}
