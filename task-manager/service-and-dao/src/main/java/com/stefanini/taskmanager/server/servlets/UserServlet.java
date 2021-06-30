package com.stefanini.taskmanager.server.servlets;

import com.google.gson.Gson;
import com.stefanini.taskmanager.dto.UserTO;
import com.stefanini.taskmanager.service.UserService;
import com.stefanini.taskmanager.service.proxy.email.EmailProxy;
import com.stefanini.taskmanager.util.context.ApplicationContextManager;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.stream.Stream;

import static com.stefanini.taskmanager.server.servlets.util.ServletUtil.getBody;

@WebServlet(
    name = "com.stefanini.taskmanager.server.servlets.UserServlet",
    urlPatterns = {"/user"})
public class UserServlet extends HttpServlet {

  UserService userService =
      (UserService)
          EmailProxy.newInstance(
              ApplicationContextManager.getApplicationContext().getBean(UserService.class));

  @Override
  protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
    resp.setStatus(HttpServletResponse.SC_OK);

    Gson g = new Gson();
    Stream<UserTO> users = userService.getAllUsers();

    resp.setContentType("json");
    users.forEach(
        returnedUser -> {
          try {
            resp.getWriter().write(g.toJson(returnedUser));
          } catch (IOException e) {
            e.printStackTrace();
          }
        });

    resp.getWriter().flush();
    resp.getWriter().close();
  }

  @Override
  protected void doPost(HttpServletRequest req, HttpServletResponse resp)
      throws ServletException, IOException {
    resp.setStatus(HttpServletResponse.SC_OK);

    String body = getBody(req);

    Gson g = new Gson();
    UserTO user = g.fromJson(body, UserTO.class);

    UserTO returnedUser = userService.createUser(user);

    resp.setContentType("json");
    resp.getWriter().write(g.toJson(returnedUser));
    resp.getWriter().flush();
    resp.getWriter().close();
  }
}
