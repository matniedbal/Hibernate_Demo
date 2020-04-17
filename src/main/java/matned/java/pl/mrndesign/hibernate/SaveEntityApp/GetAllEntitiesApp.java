package matned.java.pl.mrndesign.hibernate.SaveEntityApp;

import matned.java.pl.mrndesign.hibernate.entity.Employee;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import java.util.List;

public class GetAllEntitiesApp {

    public static void main(String[] args) {
        Configuration conf = new Configuration();
        conf.configure("hibernate.cfg.xml");
        conf.addAnnotatedClass(Employee.class);
        SessionFactory factory = conf.buildSessionFactory();
        Session session = factory.getCurrentSession();
        session.beginTransaction();

        List<Employee> employees =session.createQuery("from Employee").getResultList();

        for (Employee el: employees) {
            System.out.println(el);

        }

        session.getTransaction().commit();

        factory.close();
    }
}
