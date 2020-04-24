package matned.java.pl.mrndesign.oneToMany;

import matned.java.pl.mrndesign.oneToMany.entity.Property;
import matned.java.pl.mrndesign.oneToMany.entity.Company;
import matned.java.pl.mrndesign.oneToMany.entity.CompanyDetail;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.hibernate.query.Query;

import java.util.List;

public class AssociationsOneToManyMain {

    public static void main(String[] args) {
        initialize();
    }

    private static void initialize(){
        Configuration conf = new Configuration();
        conf.configure("hibernate.cfg.xml");
        conf.addAnnotatedClass(Company.class);
        conf.addAnnotatedClass(CompanyDetail.class);
        conf.addAnnotatedClass(Property.class);

        methods(conf);



    }

    private static void methods(Configuration conf) {
        SessionFactory factory = conf.buildSessionFactory();

//        getProperty(factory);
//        saveApp(factory);
//        oneToManyDeleteApp(factory);
//        oneToManyDeleteApp2(factory);
//        oneToManyHQL_App1(factory);
//        oneToManyHQL_App2(factory);
        oneToManyHQL_App3(factory);

        factory.close();
    }

    private static void oneToManyHQL_App3(SessionFactory factory) {
        Session session;
        session = factory.getCurrentSession();
        String sel = "select c.name from Company c where size(c.properties) > 3";
        session.beginTransaction();
        Query query = session.createQuery(sel);
        List<String> resultList = query.getResultList();
        System.out.println("Firmy mające więcej niż 3 nieruchomości: ");
        for (String el : resultList) {
            System.out.println(el);
        }
        session.getTransaction().commit();
    }

   private static void oneToManyHQL_App2(SessionFactory factory) {
        Session session;
        session = factory.getCurrentSession();
        String sel = "select c.name from Property p join p.company c join c.companyDetail d where p.city = 'Wrocław' and d.residence = 'Poland'";
        session.beginTransaction();
        Query query = session.createQuery(sel);
        List<String> resultList = query.getResultList();
        System.out.println("nieruchomiści w Wrocku, siedziba główna w PL: ");
        for (String el : resultList) {
            System.out.println(el);
        }
        session.getTransaction().commit();
    }

    private static void oneToManyHQL_App1(SessionFactory factory) {

        Session session;
        session = factory.getCurrentSession();
        String sel = "select c.name from Property p join p.company c where p.city = 'Sevilla'";
        session.beginTransaction();
        Query query = session.createQuery(sel);
        List<String> resultList = query.getResultList();
        System.out.println("Siedziba firmy jest w mieście Sevila, nazwy firm: ");
        for (String el : resultList) {
            System.out.println(el);
        }
        session.getTransaction().commit();
    }

    private static void oneToManyDeleteApp(SessionFactory factory) {
        Session session;
        session = factory.getCurrentSession();

        String getCo = "select c from Company c where c.idCompany = 6";
        session.beginTransaction();
        Query query = session.createQuery(getCo);
        Company company = (Company) query.getSingleResult();

        System.out.println(company);

        for (Property pr: company.getProperties()) {
            if (4 == pr.getIdProperty())
                session.delete(pr);
        }

        session.getTransaction().commit();
    }

    private static void oneToManyDeleteApp2(SessionFactory factory) {
        Session session;
        session = factory.getCurrentSession();

        session.beginTransaction();

        int idToDelete = 3;

        Property prop = session.get(Property.class, idToDelete);
        session.delete(prop);

        session.getTransaction().commit();
    }

    private static void saveApp(SessionFactory factory) {
        Session session;
        Query query;
        session = factory.getCurrentSession();
        String getCompany = "select c from Company c where c.idCompany = 6";
        session.beginTransaction();
        query = session.createQuery(getCompany);


        Company company = (Company) query.getSingleResult();

        Property p1 = new Property("Wrocław", 2);
        Property p2 = new Property("Brzeg", 10);

        company.addProperty(p1);
        company.addProperty(p2);

        session.persist(p1);
        session.persist(p2);

        session.getTransaction().commit();
        System.out.println(company);

    }


    private static void getProperty(SessionFactory factory) {
        Session session;
        session = factory.getCurrentSession();

        String getCo = "select c from Company c where c.idCompany = 6";

        session.beginTransaction();

        Query query = session.createQuery(getCo);

        Company company = (Company) query.getSingleResult();

        System.out.println(company);

        for (Property pr: company.getProperties()) {
            System.out.println(pr);
        }


        session.getTransaction().commit();


    }
}
