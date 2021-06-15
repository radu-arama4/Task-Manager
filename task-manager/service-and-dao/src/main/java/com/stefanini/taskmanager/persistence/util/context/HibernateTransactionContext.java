package com.stefanini.taskmanager.persistence.util.context;

import com.stefanini.taskmanager.persistence.util.DataBaseUtil;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

public class HibernateTransactionContext implements TransactionContext {
  private final SessionFactory sessionFactory = DataBaseUtil.connectWithHibernate();
  private Transaction transaction = null;
  private Session session;

  @Override
  public void begin() {
    session = sessionFactory.getCurrentSession();
    transaction = session.beginTransaction();
  }

  @Override
  public void commit() {
    if (transaction.isActive()) {
      transaction.commit();
    }
    session.close();
  }

  @Override
  public void rollback() {
    if (transaction.isActive()) {
      transaction.rollback();
    }
    session.close();
  }
}
