package com.example.starter;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.MultiMap;
import io.vertx.core.Promise;
import io.vertx.core.Vertx;
import io.vertx.core.http.HttpMethod;
import io.vertx.core.http.HttpServer;
import io.vertx.core.http.HttpServerRequest;
import io.vertx.core.http.HttpServerResponse;
import io.vertx.core.json.JsonArray;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.jdbc.JDBCClient;
import io.vertx.ext.sql.ResultSet;
import io.vertx.ext.sql.SQLConnection;
import io.vertx.ext.sql.UpdateResult;
import io.vertx.mysqlclient.MySQLConnectOptions;
import io.vertx.mysqlclient.MySQLPool;
import io.vertx.sqlclient.*;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class MainVerticle extends AbstractVerticle {

  private JDBCClient jdbcClient;
  private HttpServer server;


  @Override
  public void start(Promise<Void> startPromise) throws Exception {
    // create JDBC client
    JsonObject config = new JsonObject()
      .put("url", "jdbc:mysql://localhost:5001/mydb")
      .put("user", "root")
      .put("password", "pass123")
      .put("driver_class", "com.mysql.cj.jdbc.Driver")
      .put("max_pool_size", 30);
    jdbcClient = JDBCClient.createShared(vertx, config);

    server = vertx.createHttpServer();

    server.requestHandler(req -> {
      HttpServerResponse response = req.response();
      response.putHeader("Access-Control-Allow-Origin", "*");
      response.putHeader("Access-Control-Allow-Headers", "Content-Type");

      if (req.method() == HttpMethod.OPTIONS && "/data".equals(req.path())) {  // handle preflight request
        response.setStatusCode(200).end();
      } else if (req.method() == HttpMethod.POST && "/data".equals(req.path())) {
        System.out.println("Received data");
        handleStoreDataRequest(req);
      } else if (req.method() == HttpMethod.GET && "/data".equals(req.path())) {
        MultiMap params = req.params();
        String username = params.get("username");
        System.out.println("Retrieve data");
        handleGetDataRequest(req, username);
      } else {
        req.response().setStatusCode(404).end();
      }

    });

    // Now bind the server:
    server.listen(8080, res -> {
      if (res.succeeded()) {
        startPromise.complete();
      } else {
        startPromise.fail(res.cause());
      }
    });
  }

  /**
   * Handles a request to store data in the database.
   *
   * @param request the request to handle
   */
  private void handleStoreDataRequest(HttpServerRequest request) {
    request.bodyHandler(buffer -> {
      JsonObject json = buffer.toJsonObject();
      String value = json.getString("value");

      System.out.println("value: " + value);

      JsonArray batch = new JsonArray();
      batch.add(value);

      // store data in database
      jdbcClient.getConnection(ar -> {
        if (ar.succeeded()) {
          SQLConnection connection = ar.result();
          connection.updateWithParams("INSERT INTO data (value) VALUES (?)", batch, ar2 -> {
            if (ar2.succeeded()) {
              // The insert statement was executed successfully
              UpdateResult result = ar2.result();
              System.out.println("Data stored in database");
              request.response().setStatusCode(200).end();
            } else {
              // The insert statement failed
              Throwable cause = ar2.cause();
              System.out.println("Data not stored in database");
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


  private void handleGetDataRequest(HttpServerRequest request, String username) {
    JsonArray params = new JsonArray();
    params.add(username);
    jdbcClient.getConnection(ar -> {
      if (ar.succeeded()) {
        SQLConnection connection = ar.result();

        // retrieve amount for specific user
        connection.queryWithParams("SELECT * FROM data WHERE value = ?", params, result -> {
          if (result.succeeded()) {
            ResultSet rs = result.result();
            int amount = rs.getNumRows();
            System.out.println("numRows: " + amount);

            connection.query("SELECT * FROM data", res -> {
              if (res.succeeded()) {
                ResultSet rs2 = res.result();
                int total = rs2.getNumRows();
                System.out.println("numRows: " + total);

                // create response object
                JsonObject response = new JsonObject()
                  .put("amount", amount)
                  .put("totalAmount", total);

                // send response
                request.response()
                  .putHeader("Content-Type", "application/json")
                  .end(response.encode());
              } else {
                Throwable cause = res.cause();
                request.response().setStatusCode(500).end();
              }
            });
          } else {
            request.response().setStatusCode(500).end();
          }
        });
      } else {
        request.response().setStatusCode(500).end();
      }
    });
  }
}

