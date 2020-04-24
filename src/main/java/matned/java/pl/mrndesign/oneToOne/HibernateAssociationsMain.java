package matned.java.pl.mrndesign.oneToOne;

import matned.java.pl.mrndesign.hibernate.entity.Employee;
import matned.java.pl.mrndesign.oneToOne.entity.Company;
import matned.java.pl.mrndesign.oneToOne.entity.CompanyDetail;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.hibernate.query.Query;

import java.util.List;

public class HibernateAssociationsMain {
    public static void main(String[] args) {
        initialize();
    }

    private static void initialize(){
        Configuration conf = new Configuration();
        conf.configure("hibernate.cfg.xml");
        conf.addAnnotatedClass(Company.class);
        conf.addAnnotatedClass(CompanyDetail.class);
        SessionFactory factory = conf.buildSessionFactory();

        methods(factory);

        factory.close();

    }

    private static void methods(SessionFactory factory) {
//        oneToOne(factory);
//        cascadeApp(factory);
//        cascadeRemoveApp(factory);
//        biDirectional_NewCompany_App(factory);
//        biDirectional_ReadCompany_App(factory);
//        biDirectional_RemoveCompany_App(factory);
        oneToOneHQL_App(factory);
    }

    private static void oneToOneHQL_App(SessionFactory factory) {
        getAllEntitiesFromDataBase(factory);
    }

    private static void getAllEntitiesFromDataBase(SessionFactory factory) {
        Session session;
        session = factory.getCurrentSession();

        String select = "select c from Company c join c.companyDetail cd where  cd.residence = 'Italy'";
        String allPolishCompaniesValue = "select sum(c.value) from Company c join c.companyDetail cd where cd.residence = 'Poland'";
        String sortSmaller = "select c.name from CompanyDetail cd join cd.company c where cd.employeeNumber < 35000 order by c.value";

        session.beginTransaction();


        Query query = session.createQuery(allPolishCompaniesValue);
        Long result = (Long) query.getSingleResult();
        query = session.createQuery(sortSmaller);

        List<String> resultList = query.getResultList();

        session.getTransaction().commit();

        System.out.println("Wartość polskich firm w bazie: " + result +"zł");

        for (String c : resultList) {
            System.out.println(c);
        }

    }

    private static void biDirectional_ReadCompany_App(SessionFactory factory) {
        Session session;
        session = factory.getCurrentSession();
        session.beginTransaction();
        CompanyDetail det = session.get(CompanyDetail.class, 10);

        session.getTransaction().commit();

        System.out.println(det.getCompany());
    }

    private static void biDirectional_RemoveCompany_App(SessionFactory factory) {
        Session session;
        session = factory.getCurrentSession();
        session.beginTransaction();
        CompanyDetail det = session.get(CompanyDetail.class, 10);

        session.remove(det);

        session.getTransaction().commit();
    }

    private static void biDirectional_NewCompany_App(SessionFactory factory) {
        Session session;
        session = factory.getCurrentSession();

        int id = 5;

        Company company = new Company("PZU", 100000);
        CompanyDetail companyDetail = new CompanyDetail("Poland", 100000);
        companyDetail.setCompany(company);
        company.setCompanyDetail(companyDetail);



        session.beginTransaction();

        session.persist(company);

        session.getTransaction().commit();
    }

    private static void cascadeRemoveApp(SessionFactory factory) {
        Session session;
        session = factory.getCurrentSession();

        int idToRemove = 9;

        session.beginTransaction();

        Company company = session.get(Company.class, idToRemove);
        session.remove(company);

        session.getTransaction().commit();
    }

    private static void cascadeApp(SessionFactory factory) {
        Session session;
        session = factory.getCurrentSession();

        Company company = new Company("MRN sp. z o.o.", 8000);
        CompanyDetail companyDetail = new CompanyDetail("Poland", 8);
        company.setCompanyDetail(companyDetail);

        session.beginTransaction();

        session.persist(company);

        session.getTransaction().commit();
    }

    private static void oneToOne(SessionFactory factory) {
        Session session;
        session = factory.getCurrentSession();

        Company company = new Company("MRN sp. z o.o.", 8000);
        CompanyDetail companyDetail = new CompanyDetail("Poland", 8);
        company.setCompanyDetail(companyDetail);

        session.beginTransaction();

        session.save(companyDetail);
        session.save(company);

        session.getTransaction().commit();
    }



}
