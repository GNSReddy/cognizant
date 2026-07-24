package com.cognizant.springlearn;

import com.cognizant.springlearn.model.Country;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Main entry point – Handbooks 1, 2 & 3 cumulative project.
 *
 * Handbook 3 adds:
 *   Employee, Department, Skill models
 *   EmployeeDao, DepartmentDao (static lists from employee.xml)
 *   EmployeeService (@Service, @Transactional)
 *   DepartmentService
 *   EmployeeController – GET /employees
 *   DepartmentController – GET /departments
 *
 * On startup, the DAO constructors fire automatically (Spring creates them as beans)
 * and log: "Loaded 4 employees from employee.xml" and "Loaded 3 departments from employee.xml"
 */
@SpringBootApplication
public class SpringLearnApplication {

    private static final Logger LOGGER = LoggerFactory.getLogger(SpringLearnApplication.class);

    public static void main(String[] args) {
        LOGGER.info("START - SpringLearnApplication main()");
        SpringApplication.run(SpringLearnApplication.class, args);
        displayDate();
        displayCountry();
        displayCountries();
        LOGGER.info("END - SpringLearnApplication main()");
    }

    public static void displayDate() {
        LOGGER.info("Start");
        try {
            ApplicationContext context = new ClassPathXmlApplicationContext("date-format.xml");
            SimpleDateFormat format = context.getBean("dateFormat", SimpleDateFormat.class);
            Date date = format.parse("31/12/2018");
            LOGGER.debug("Parsed date: {}", date);
        } catch (ParseException e) {
            LOGGER.error("Date parsing error: {}", e.getMessage());
        }
        LOGGER.info("End");
    }

    public static void displayCountry() {
        LOGGER.info("Start");
        ApplicationContext context = new ClassPathXmlApplicationContext("country.xml");
        Country country = context.getBean("country", Country.class);
        LOGGER.debug("Country : {}", country);
        Country anotherCountry = context.getBean("country", Country.class);
        LOGGER.debug("Another Country : {}", anotherCountry);
        LOGGER.debug("Same instance? {}", (country == anotherCountry));
        LOGGER.info("End");
    }

    @SuppressWarnings("unchecked")
    public static void displayCountries() {
        LOGGER.info("Start");
        ApplicationContext context = new ClassPathXmlApplicationContext("country.xml");
        List<Country> countries = (ArrayList<Country>) context.getBean("countryList", ArrayList.class);
        for (Country c : countries) {
            LOGGER.debug("Country: {}", c);
        }
        LOGGER.info("End");
    }
}
