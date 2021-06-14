package com.stefanini.taskmanager.persistence.util.context;

import com.stefanini.taskmanager.persistence.util.DataBaseUtil;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

public class HibernateTransactionContext implements TransactionContext {
  private final SessionFactory sessionFactory = DataBaseUtil.connectWithHibernate();
  private Transaction transaction = null;

  @Override
  public void begin() {
    Session session = sessionFactory.getCurrentSession();
    transaction = session.beginTransaction();
  }

  @Override
  public void commit() {
    if (transaction != null) {
      transaction.commit();
    }
  }

  @Override
  public void rollback() {
    if (transaction != null) {
      transaction.rollback();
    }
  }
}
