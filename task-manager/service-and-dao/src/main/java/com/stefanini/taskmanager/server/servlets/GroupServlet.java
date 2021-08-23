package com.stefanini.taskmanager.server.servlets;

import com.google.gson.Gson;
import com.stefanini.taskmanager.dto.GroupTO;
import com.stefanini.taskmanager.dto.TaskTO;
import com.stefanini.taskmanager.dto.UserTO;
import com.stefanini.taskmanager.service.GroupService;
import com.stefanini.taskmanager.service.proxy.email.EmailProxy;
import com.stefanini.taskmanager.util.context.ApplicationContextManager;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static com.stefanini.taskmanager.server.servlets.util.ServletUtil.getBodyContent;

@WebServlet(
    name = "com.stefanini.taskmanager.server.servlets.GroupServlet",
    urlPatterns = {"/group"})
public class GroupServlet extends HttpServlet {
  private static final Logger logger = LogManager.getLogger(GroupServlet.class);

  GroupService groupService =
      (GroupService)
          EmailProxy.newInstance(
              ApplicationContextManager.getApplicationContext().getBean(GroupService.class));

  @Override
  protected void doGet(HttpServletRequest req, HttpServletResponse resp)
      throws ServletException, IOException {
    resp.setStatus(HttpServletResponse.SC_OK);
  }

  @Override
  protected void doPost(HttpServletRequest req, HttpServletResponse resp)
      throws ServletException, IOException {
    String body = getBodyContent(req);

    Gson g = new Gson();
    GroupTO group = g.fromJson(body, GroupTO.class);

    GroupTO returnedGroup = groupService.createGroup(group);

    resp.setContentType("json");
    resp.getWriter().write(g.toJson(returnedGroup));
    resp.setStatus(HttpServletResponse.SC_OK);
    resp.getWriter().flush();
    resp.getWriter().close();
  }

  @Override
  protected void doPut(HttpServletRequest req, HttpServletResponse resp)
      throws ServletException, IOException {
    String body = getBodyContent(req);

    Gson g = new Gson();
    GroupTO group = g.fromJson(body, GroupTO.class);

    UserTO user = g.fromJson(body, UserTO.class);
    TaskTO task = g.fromJson(body, TaskTO.class);

    if (user.getUserName() == null) {
      if (groupService.addTaskToGroup(group, task)) {
        resp.getWriter().write("Task " + task.toString() + "added to group " + group.toString());
      } else {
        resp.getWriter().write("Something went wrong!");
      }
    } else {
      if (groupService.addUserToGroup(group, user)) {
        resp.getWriter().write("User " + user + "added to group " + group.toString());
      } else {
        resp.getWriter().write("Something went wrong!");
      }
    }

    resp.setStatus(HttpServletResponse.SC_OK);
    resp.getWriter().flush();
    resp.getWriter().close();
  }
}
