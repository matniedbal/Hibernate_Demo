package matned.java.pl.mrndesign.hibernate.SaveEntityApp;


import matned.java.pl.mrndesign.hibernate.entity.Employee;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

public class Main {
    public static void main(String[] args) {

        //tworz\ę obiekt configuration
        Configuration conf = new Configuration();

        // wczytanie pliku konfiguracyjnego
        conf.configure("hibernate.cfg.xml");

        // wczystanie adnotacji
        conf.addAnnotatedClass(Employee.class);
        //stworzenie obiektu sesion factory
        SessionFactory factory = conf.buildSessionFactory();
        //pobieranie sesji
        Session session = factory.getCurrentSession();

        // stworzenie obiektu
        Employee employee = new Employee();
        employee.setIdEmployee(1);
        employee.setFirstName("Janusz");
        employee.setLastName("Kowalczyk");
        employee.setSalary(2600);

        //rozpoczęcie transakcji
        session.beginTransaction();

        //zapis pracownika
        session.save(employee);

        //zakończ transakcji
        session.getTransaction().commit();

        //zamknięcie obirektu SessionFactory
        factory.close();

    }
}
