package com.example.starter;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.http.HttpMethod;
import io.vertx.core.http.HttpServer;
import io.vertx.core.http.HttpServerRequest;
import io.vertx.core.json.JsonArray;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.jdbc.JDBCClient;
import io.vertx.ext.sql.ResultSet;
import io.vertx.ext.sql.SQLConnection;

public class DataStreamService extends AbstractVerticle {
  private JDBCClient jdbcClient;

  @Override
  public void start() {
    // create JDBC client
    JsonObject config = new JsonObject()
      .put("url", "jdbc:mysql://localhost:5001/mydb")
      .put("driver_class", "com.mysql.cj.jdbc.Driver")
      .put("max_pool_size", 30);
    jdbcClient = JDBCClient.createShared(vertx, config);

    // create HTTP server
    HttpServer server = vertx.createHttpServer();

    // handle requests to store data
    server.requestHandler(request -> {
      if (request.method() == HttpMethod.POST && "/data".equals(request.path())) {
        handleStoreDataRequest(request);
      } else {
        request.response().setStatusCode(404).end();
      }
    });

    // start server
    server.listen(8080);
  }

  private void handleStoreDataRequest(HttpServerRequest request) {
    request.bodyHandler(buffer -> {
      // parse data from request body
      JsonArray data = buffer.toJsonArray();

      // store data in database
      jdbcClient.getConnection(ar -> {
        if (ar.succeeded()) {
          SQLConnection connection = ar.result();
          connection.batchWithParams("INSERT INTO data (value) VALUES (?)", data.getList(), result -> {
            if (result.succeeded()) {
              request.response().setStatusCode(200).end();
            } else {
              request.response().setStatusCode(500).end();
            }
            connection.close();
          });
        } else {
          request.response().setStatusCode(500).end();
        }
      });
    });
  }
}
