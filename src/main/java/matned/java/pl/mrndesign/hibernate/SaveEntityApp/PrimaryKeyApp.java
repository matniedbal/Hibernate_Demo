package matned.java.pl.mrndesign.hibernate.SaveEntityApp;

import matned.java.pl.mrndesign.hibernate.entity.Employee;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

public class PrimaryKeyApp {
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

        // stworzenie 3 obiektów

        Employee employee1 = new Employee();
        employee1.setFirstName("Janusz");
        employee1.setLastName("Kowalczyk");
        employee1.setSalary(2600);

        Employee employee2 = new Employee();
        employee2.setFirstName("Marian");
        employee2.setLastName("Walczak");
        employee2.setSalary(2600);

        Employee employee3 = new Employee();
        employee3.setFirstName("Grażyna");
        employee3.setLastName("Pawłowicz");
        employee3.setSalary(2600);

        Employee employee4 = new Employee();
        employee4.setFirstName("Lech");
        employee4.setLastName("Piekarz");
        employee4.setSalary(2600);

        //rozpoczęcie transakcji
        session.beginTransaction();

        //zapis 3 pracownikó
        session.save(employee1);
        session.save(employee2);
        session.save(employee3);
        session.save(employee4);

        //zakończ transakcji
        session.getTransaction().commit();

        //zamknięcie obirektu SessionFactory
        factory.close();
    }
}
