package matned.java.pl.mrndesign.manyToMany;

import matned.java.pl.mrndesign.manyToMany.entity.*;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.hibernate.query.Query;

import java.util.List;

public class ManyToManyMain {
    public static void main(String[] args) {
        initialize();
    }

    private static void initialize(){
        Configuration conf = new Configuration();
        conf.configure("hibernate.cfg.xml");
        conf.addAnnotatedClass(Company.class);
        conf.addAnnotatedClass(CompanyDetail.class);
        conf.addAnnotatedClass(Property.class);
        conf.addAnnotatedClass(Employee.class);
        conf.addAnnotatedClass(Training.class);
        SessionFactory factory = conf.buildSessionFactory();

        methods(factory);

        factory.close();

    }

    private static void methods(SessionFactory factory) {
//        saveApp(factory);
//        getApp(factory);
//        getApp2(factory);
//        deleteApp(factory);
//        deleteApp2(factory);
//        select1(factory);
//        select2(factory);
        select3(factory);
    }

    private static void select3(SessionFactory factory) {
        Session session;
        session = factory.getCurrentSession();
        int training = 1;
        int maxSalary = 12000;

        String getEmployee = "select e from Employee e where size(e.trainings) =:training and e.salary < :maxSalary";
        session.beginTransaction();
        Query q = session.createQuery(getEmployee);
        q.setParameter("training", training);
        q.setParameter("maxSalary", maxSalary);

        List<Employee> trainings = q.getResultList();

        for (Employee el: trainings ) {
            System.out.println(el);
        }
        session.getTransaction().commit();
    }

    private static void select2(SessionFactory factory) {
        Session session;
        session = factory.getCurrentSession();
        int minEmployeeNum = 2;

        String course = "javascript";
        String getEmployee = "select e from Employee e join e.trainings t where t.name =:course";
        session.beginTransaction();
        Query q = session.createQuery(getEmployee);
        q.setParameter("course", course);

        List<Employee> trainings = q.getResultList();

        for (Employee el: trainings ) {
            System.out.println(el);
        }
        session.getTransaction().commit();
    }

    private static void select1(SessionFactory factory) {
        Session session;
        session = factory.getCurrentSession();
        int minEmployeeNum = 2;
        String getTraining = "select t from Training t where size(t.employees) >= :minEmployeeNum";
        session.beginTransaction();
        Query q = session.createQuery(getTraining);
        q.setParameter("minEmployeeNum", minEmployeeNum);

        List<Training> trainings = q.getResultList();

        for (Training el: trainings ) {
            System.out.println(el);
        }

        session.getTransaction().commit();

    }

    private static void deleteApp2(SessionFactory factory) {
        Session session;
        session = factory.getCurrentSession();
        int id = 1;
        session.beginTransaction();
        Training t = session.get(Training.class, id);

        session.delete(t);

        session.getTransaction().commit();
    }

    private static void deleteApp(SessionFactory factory){
        Session session;
        session = factory.getCurrentSession();
        int id = 113;
        session.beginTransaction();
        Employee e = session.get(Employee.class, id);

        session.delete(e);

        session.getTransaction().commit();
    }

    private static void getApp2(SessionFactory factory) {
        Session session;
        session = factory.getCurrentSession();
        session.beginTransaction();
        List<Training> t = (List<Training>) session.createQuery("from Training").getResultList();
        for (Training tr: t ) {
            System.out.println(tr);
            for (Employee e: tr.getEmployees() ) {
                System.out.println("- " + e);
            }
        }
        session.getTransaction().commit();
    }

    private static void getApp(SessionFactory factory) {
        Session session;
        session = factory.getCurrentSession();
        int id = 1;
        session.beginTransaction();
        Training t = session.get(Training.class, id);
        System.out.println(t);
        for (Employee e: t.getEmployees() ) {
            System.out.println("- "+e);

        }
        session.getTransaction().commit();
    }

    private static void saveApp(SessionFactory factory) {
        Session session;
        session = factory.getCurrentSession();
        session.beginTransaction();
        Training training = new Training("C++");
        Employee e1 = session.get(Employee.class,113);
        Employee e2 = session.get(Employee.class,112);
        training.addEmployee(e1);
        training.addEmployee(e2);
        session.persist(training);
        session.getTransaction().commit();
    }
}
