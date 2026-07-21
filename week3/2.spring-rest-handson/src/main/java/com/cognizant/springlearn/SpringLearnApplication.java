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
 * Main entry point – Handbooks 1 & 2 cumulative project.
 *
 * @SpringBootApplication is a convenience annotation combining:
 *   @Configuration       – this class is a source of Spring bean definitions
 *   @EnableAutoConfiguration – Spring Boot auto-configures beans (Tomcat, Jackson, etc.)
 *   @ComponentScan       – scans com.cognizant.springlearn for @Component/@Service/@Repository
 *
 * SpringApplication.run() bootstraps the application context, starts the
 * embedded Tomcat on port 8083, and registers all REST controllers.
 *
 * Hands-on 1  : main() with log statements
 * Hands-on 2  : displayDate() – load SimpleDateFormat from Spring XML
 * Hands-on 3  : SLF4J Logger used throughout (no System.out.println)
 * Hands-on 4  : displayCountry() – load Country bean from Spring XML
 * Hands-on 5  : two getBean() calls → demonstrates singleton vs prototype scope
 * Hands-on 6  : displayCountries() – load ArrayList of countries from Spring XML
 */
@SpringBootApplication
public class SpringLearnApplication {

    private static final Logger LOGGER = LoggerFactory.getLogger(SpringLearnApplication.class);

    public static void main(String[] args) {
        LOGGER.info("START - SpringLearnApplication main()");
        SpringApplication.run(SpringLearnApplication.class, args);

        // Demonstrate Spring Core bean loading (Hands-on 2, 4, 6)
        displayDate();
        displayCountry();
        displayCountries();

        LOGGER.info("END - SpringLearnApplication main()");
    }

    /**
     * Hands-on 2 & 3 – Load SimpleDateFormat from date-format.xml.
     *
     * ClassPathXmlApplicationContext searches the classpath (src/main/resources)
     * for date-format.xml and creates the Spring container from it.
     * context.getBean("dateFormat") retrieves the pre-configured SimpleDateFormat bean.
     * The bean is a shared, reusable instance – no need to create it with new().
     */
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

    /**
     * Hands-on 4 & 5 – Load Country bean and demonstrate scope.
     *
     * country.xml has scope="prototype" on the "country" bean.
     * Each call to context.getBean("country") creates a FRESH instance,
     * so "Inside Country Constructor." appears TWICE in the logs.
     *
     * To demonstrate Singleton scope:
     *   Remove scope="prototype" (or set scope="singleton") in country.xml.
     *   Constructor log appears only ONCE even with two getBean() calls.
     */
    public static void displayCountry() {
        LOGGER.info("Start");
        ApplicationContext context = new ClassPathXmlApplicationContext("country.xml");

        Country country = context.getBean("country", Country.class);
        LOGGER.debug("Country : {}", country);

        // Second getBean() – constructor called again (prototype) or not (singleton)
        Country anotherCountry = context.getBean("country", Country.class);
        LOGGER.debug("Another Country : {}", anotherCountry);
        LOGGER.debug("Same instance? {}", (country == anotherCountry));

        LOGGER.info("End");
    }

    /**
     * Hands-on 6 – Load list of all four countries.
     *
     * country.xml defines an ArrayList<Country> bean named "countryList"
     * using <list> and <ref bean="..."/> tags.
     * Retrieved as a plain ArrayList because Spring's XML config stores it that way.
     */
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
