package com.stefanini.taskmanager.server.servlets;

import com.google.gson.Gson;
import com.stefanini.taskmanager.dto.TaskTO;
import com.stefanini.taskmanager.dto.UserTO;
import com.stefanini.taskmanager.service.TaskService;
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
import java.util.stream.Stream;

import static com.stefanini.taskmanager.server.servlets.util.ServletUtil.getBodyContent;

@WebServlet(
    name = "com.stefanini.taskmanager.server.servlets.TaskServlet",
    urlPatterns = {"/task"})
public class TaskServlet extends HttpServlet {
  private static final Logger logger = LogManager.getLogger(TaskServlet.class);

  TaskService taskService =
      (TaskService)
          EmailProxy.newInstance(
              ApplicationContextManager.getApplicationContext().getBean(TaskService.class));

  @Override
  protected void doGet(HttpServletRequest req, HttpServletResponse resp)
      throws ServletException, IOException {
    String body = getBodyContent(req);

    Gson g = new Gson();
    UserTO user = g.fromJson(body, UserTO.class);

    Stream<TaskTO> tasks = taskService.getTasksOfUser(user);

    resp.setContentType("json");

    tasks.forEach(
        returnedTask -> {
          try {
            resp.getWriter().write(g.toJson(returnedTask));
          } catch (IOException e) {
            logger.error(e);
          }
        });

    resp.setStatus(HttpServletResponse.SC_OK);
    resp.getWriter().flush();
    resp.getWriter().close();
  }

  @Override
  protected void doPost(HttpServletRequest req, HttpServletResponse resp)
      throws ServletException, IOException {
    String body = getBodyContent(req);
    Gson g = new Gson();
    UserTO user = g.fromJson(body, UserTO.class);
    TaskTO task = g.fromJson(body, TaskTO.class);

    TaskTO returnedTask = taskService.addTask(task, user);

    resp.setContentType("json");
    resp.getWriter().write(g.toJson(returnedTask));
    resp.setStatus(HttpServletResponse.SC_OK);
    resp.getWriter().flush();
    resp.getWriter().close();
  }
}
