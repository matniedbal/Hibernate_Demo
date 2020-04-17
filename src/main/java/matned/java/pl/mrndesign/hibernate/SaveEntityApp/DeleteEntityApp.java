package matned.java.pl.mrndesign.hibernate.SaveEntityApp;

import matned.java.pl.mrndesign.hibernate.entity.Employee;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

public class DeleteEntityApp {
    public static void main(String[] args) {
        Configuration conf = new Configuration();

        conf.configure("hibernate.cfg.xml");
        conf.addAnnotatedClass(Employee.class);
        SessionFactory factory = conf.buildSessionFactory();
        Session session = factory.getCurrentSession();
        session.beginTransaction();

        Employee employee = session.get(Employee.class, 13);
        session.delete(employee);

        session.getTransaction().commit();

        factory.close();
    }
}
