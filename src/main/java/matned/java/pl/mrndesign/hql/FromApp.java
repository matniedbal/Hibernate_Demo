package matned.java.pl.mrndesign.hql;

import matned.java.pl.mrndesign.hibernate.entity.Employee;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.hibernate.query.Query;


import java.util.List;

public class FromApp {
    public static void main(String[] args) {
        Configuration conf = new Configuration();
        conf.configure("hibernate.cfg.xml");
        conf.addAnnotatedClass(Employee.class);
        SessionFactory factory = conf.buildSessionFactory();
        subtitle("1");
        from_where_orderBy_Testing(factory);
        subtitle("2");
        selectTest(factory);
        subtitle("3");
        selectOrderByTesting(factory);
        subtitle("4");
        searchBySelect(factory);
        subtitle("5 - update 114");
        updateApp(factory);
        subtitle("6 - delete 90");
        deleteApp(factory);
        subtitle("7 - average salary");
        aggregateFunctionApp(factory);

        factory.close();
    }

    private static void subtitle(String s) {
        System.out.println("\nSession check no.: "+ s);
    }

    private static void selectOrderByTesting(SessionFactory factory) {
        Session session;
        Query query;
        session = factory.getCurrentSession();
        session.beginTransaction();
        String select = "select salary, firstName, lastName from Employee where salary < 2300 order by salary desc, lastName, firstName";
        String select1 = "select e.salary, e.firstName, e.lastName from Employee as e where salary < 2300 order by salary asc, lastName desc, firstName";
        String select2 = "select e.salary, e.firstName, e.lastName from Employee e where salary < 2300 order by salary desc, lastName, firstName"; //bez 'as'
        query = session.createQuery(select1);
        List<Object[]> selectResult = query.getResultList();
        session.getTransaction().commit();
        for (Object[] emps: selectResult) {
            System.out.println(emps[0]+"zł ------> First name: " + emps[1] + ", Last name: " + emps[2]);
        }
    }

    private static void selectTest(SessionFactory factory) {
        Session session;
        Query query;
        session = factory.getCurrentSession();
        session.beginTransaction();
        String select = "select salary, firstName, lastName from Employee where salary < 2300 order by salary desc, lastName, firstName";
        String select1 = "select e.salary, e.firstName, e.lastName from Employee as e where salary < 2300 order by salary asc, lastName desc, firstName";
        String select2 = "select e.salary, e.firstName, e.lastName from Employee e where salary < 2300 order by salary desc, lastName, firstName"; //bez 'as'
        query = session.createQuery(select1);
        List<Object[]> selectResult = query.getResultList();
        session.getTransaction().commit();
        for (Object[] emps: selectResult) {
            System.out.println(emps[0]+"zł ------> First name: " + emps[1] + ", Last name: " + emps[2]);
        }
    }

    private static void from_where_orderBy_Testing(SessionFactory factory) {
        Session session;
        Query query;
        session = factory.getCurrentSession();
        session.beginTransaction();
        String from = "from Employee where salary > 6000 and salary < 7000 and lastName in ('Johnson', 'Lee', 'Banda') order by salary";
        //  String from2 = "from matned.java.pl.mrndesign.hql.entity.Employee";   tu dupa, coś ni styka
        query = session.createQuery(from);
        List<Employee> listOfEmps = query.getResultList();
        session.getTransaction().commit();
        for (Employee emps: listOfEmps) {
            System.out.println(emps);
        }
    }

    private static void searchBySelect(SessionFactory factory) {
        Session session;
        Query query;
        session = factory.getCurrentSession();

        String employeeFirstName = "Steven";
        String employeeLastName = "King";
        session.beginTransaction();

        String namedParametersString = "select e.salary, e.firstName, e.lastName from Employee e where e.firstName =: firstName and e.lastName =: lastName";

        query = session.createQuery(namedParametersString);
        query.setParameter("firstName", employeeFirstName);
        query.setParameter("lastName", employeeLastName);


        List<Object[]> listOfEmps = query.getResultList();
        session.getTransaction().commit();
        for (Object[] emps: listOfEmps) {
            System.out.println(emps[0]+"zł ------> First name: " + emps[1] + ", Last name: " + emps[2]);
        }
    }

    private static void updateApp(SessionFactory factory) {
        Session session;
        Query query;
        session = factory.getCurrentSession();

        int idEmployee = 90;
        int salary = 9000;

        session.beginTransaction();

        String update = "update Employee e set e.salary =: salary where e.idEmployee =: idEmployee ";
        query = session.createQuery(update);
        query.setParameter("salary", salary);
        query.setParameter("idEmployee", idEmployee);
        query.executeUpdate();

        session.getTransaction().commit();
    }

    private static void deleteApp(SessionFactory factory) {
        Session session;
        Query query;
        session = factory.getCurrentSession();

        int salary = 13333;
        int idEmp1 = 90;
        int idEmp2 = 100;

        session.beginTransaction();

        String delete = "update Employee e set e.salary =: salary where e.idEmployee >: idEmp1 and e.idEmployee <: idEmp2";
        query = session.createQuery(delete);
        query.setParameter("idEmp1", idEmp1);
        query.setParameter("idEmp2", idEmp2);
        query.setParameter("salary", salary);
        query.executeUpdate();

        session.getTransaction().commit();
    }

    private static void aggregateFunctionApp(SessionFactory factory) {
        Session session;
        Query query;
        session = factory.getCurrentSession();

        session.beginTransaction();

        String avg = " select avg(e.salary) from Employee e";
        String sum = " select sum(e.salary) from Employee e";
        String min = " select min(e.salary) from Employee e";
        String max = " select max(e.salary) from Employee e";
        String count = " select count(e) from Employee e";

        query = session.createQuery(avg);
        double resultAvg = (Double) query.getSingleResult();
        query = session.createQuery(sum);
        long resultSum = (Long) query.getSingleResult();
        query = session.createQuery(min);
        int resultMin = (Integer) query.getSingleResult();
        query = session.createQuery(max);
        int resultMax = (Integer) query.getSingleResult();
        query = session.createQuery(count);
        long resultCount = (Long) query.getSingleResult();

        session.getTransaction().commit();

        String strDouble = String.format("%.2f", resultAvg);
        System.out.println("Average salary: "+ strDouble +"zł");
        System.out.println("Sum of salary: "+ resultSum +"zł");
        System.out.println("Minimal salary: "+ resultMin +"zł");
        System.out.println("Maximal salary: "+ resultMax +"zł");
        System.out.println("Entities count: "+ resultCount);
    }
}
