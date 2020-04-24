package matned.java.pl.mrndesign.fetchTypes;

import matned.java.pl.mrndesign.fetchTypes.entity.Company;
import matned.java.pl.mrndesign.fetchTypes.entity.CompanyDetail;
import matned.java.pl.mrndesign.fetchTypes.entity.Property;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;


public class fetchTypesMain {
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

        System.out.println("\nEAGER:");
        fetchType1_EAGER(factory);
        System.out.println("\nLAZY:");
        fetchType2_LAZY(factory);

        factory.close();
    }

    private static void fetchType2_LAZY(SessionFactory factory) {
        Session session;
        session = factory.getCurrentSession();

        int id = 25;

        session.beginTransaction();
        System.out.println("Pobieranie obiektu company");
        Company comp = session.get(Company.class, id);
        System.out.println("Pobrano obiekt company");
        System.out.println(comp);

        session.getTransaction().commit();
// zaczynamy kolejną sesję
        session = factory.getCurrentSession();
        session.beginTransaction();
        Company mergedCompany = (Company) session.merge(comp);


        System.out.println("...");

        System.out.println("Nieruchomości:");
        for (Property p : mergedCompany.getProperties()) {
            System.out.println(p);
        }

        session.getTransaction().commit();

    }

    private static void fetchType2(SessionFactory factory) {
        Session session;
        session = factory.getCurrentSession();



        session.beginTransaction();

        session.getTransaction().commit();
    }



    private static void fetchType1_EAGER(SessionFactory factory) {
        Session session;
        session = factory.getCurrentSession();

        int id = 25;

        session.beginTransaction();
        System.out.println("Pobieranie obiektu company");
        Company comp = session.get(Company.class, id);
        System.out.println("Pobrano obiekt company");
        System.out.println(comp);



        System.out.println("...");

        System.out.println("Nieruchomości:");
        for (Property p : comp.getProperties()) {
            System.out.println(p);
        }
        session.getTransaction().commit();

    }
}
