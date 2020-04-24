package matned.java.pl.mrndesign.oneToManyOneWay;

import matned.java.pl.mrndesign.oneToManyOneWay.entity.Company;
import matned.java.pl.mrndesign.oneToManyOneWay.entity.CompanyDetail;
import matned.java.pl.mrndesign.oneToManyOneWay.entity.Property;
import matned.java.pl.mrndesign.oneToManyOneWay.entity.Department;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.hibernate.query.Query;

import javax.sound.midi.MidiChannel;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

public class OneToManyOneWayAppMain {
    public static void main(String[] args) {
        initialize();
    }

    private static void initialize(){
        Configuration conf = new Configuration();
        conf.configure("hibernate.cfg.xml");
        conf.addAnnotatedClass(Company.class);
        conf.addAnnotatedClass(CompanyDetail.class);
        conf.addAnnotatedClass(Property.class);
        conf.addAnnotatedClass(Department.class);

        methods(conf);



    }

    private static void methods(Configuration conf) {
        SessionFactory factory = conf.buildSessionFactory();

//        oneToManyHQL_App1(factory);
//        oneToManyHQL_GetApp(factory);
        oneToManyHQL_DeleteApp(factory);

        factory.close();
    }

    private static void oneToManyHQL_App1(SessionFactory factory) {
        Session session;
        session = factory.getCurrentSession();
        int id = 30;
        session.beginTransaction();

        Company c = session.get(Company.class, id);
        System.out.println(c);

        Department d1 = new Department("Sales");
        Department d2 = new Department("Production");
        Department d3 = new Department("HR");

        c.addDepartment(d1);
        c.addDepartment(d2);
        c.addDepartment(d3);

        session.persist(c);

        session.getTransaction().commit();
    }


    private static void oneToManyHQL_GetApp(SessionFactory factory) {

        // NIE DA SIĘ PRZEJSC Z DEP DO COMP -> POL JEDNOKIERUNKOWE
        Session session;
        session = factory.getCurrentSession();
        int id = 30;
        session.beginTransaction();

//        Company c = session.get(Company.class, id);
//        System.out.println(c);
//
//        Set<Department> departments = c.getDepartments();
//
//        for(Department dep : departments){
//            System.out.println(dep);
//        }

        Department department = session.get(Department.class, 1);

        System.out.println(department);

        session.getTransaction().commit();
    }

     private static void oneToManyHQL_DeleteApp(SessionFactory factory) {

        Session session;
        session = factory.getCurrentSession();
        int id = 2;
        int compId = 30;
         String delete = "delete Department d where d.idDepartment =: idDepartment";

         session.beginTransaction();

         Query query = session.createQuery(delete);
         query.setParameter("idDepartment", 1);
         int deleteRows = query.executeUpdate();

         System.out.println("Usuniete rekordy: "+deleteRows);
//        222222222222222222222222222222222
//         Company c = session.get(Company.class, compId);
//
//        Department d;
//        for (int i = 0 ; i < c.getDepartments().size(); i++){
//            d = c.getDepartments().get(i);
//            if (d.getIdDepartment() > 1) {
//                c.getDepartments().remove(d);
//                session.delete(d);
//                i--;
//            }
//        }
//        11111111111111111111111111111111
//        Department object = session.get(Department.class, id);
//        session.delete(object);

        session.getTransaction().commit();
    }

}
