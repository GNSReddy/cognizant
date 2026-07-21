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
 * Main entry point for the Spring Learn application.
 *
 * <p>@SpringBootApplication is a convenience annotation that combines:
 * - @Configuration: tags the class as a source of bean definitions
 * - @EnableAutoConfiguration: tells Spring Boot to configure beans it thinks you need
 * - @ComponentScan: tells Spring to look for components in com.cognizant.springlearn</p>
 *
 * <p>Hands-on coverage:
 * - Hands-on 1: Basic Spring Boot application setup
 * - Hands-on 2: displayDate() - Load SimpleDateFormat from date-format.xml
 * - Hands-on 3: Logging integration with SLF4J/Logback
 * - Hands-on 4: displayCountry() - Load Country bean from country.xml (singleton scope)
 * - Hands-on 5: Singleton vs Prototype scope demonstrated in displayCountry()
 * - Hands-on 6: displayCountries() - Load list of countries from country.xml</p>
 */
@SpringBootApplication
public class SpringLearnApplication {

    /**
     * SLF4J Logger for this class.
     * LoggerFactory.getLogger() returns a logger bound to this specific class.
     * Log levels used: LOGGER.info() for method boundaries, LOGGER.debug() for data.
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(SpringLearnApplication.class);

    /**
     * Application entry point.
     * SpringApplication.run() bootstraps the Spring application context,
     * starts the embedded Tomcat server, and triggers component scanning.
     *
     * @param args Command-line arguments
     */
    public static void main(String[] args) {
        LOGGER.info("START - SpringLearnApplication main()");
        SpringApplication.run(SpringLearnApplication.class, args);
        LOGGER.info("END - SpringLearnApplication main()");

        // Demonstrate Spring Core bean loading from XML (Hands-on 2, 4, 6)
        displayDate();
        displayCountry();
        displayCountries();
    }

    /**
     * Hands-on 2 & 3: Loads SimpleDateFormat bean from date-format.xml
     * and parses a date string.
     *
     * <p>Demonstrates:
     * - ClassPathXmlApplicationContext for loading XML config
     * - context.getBean() to retrieve a configured bean
     * - Bean reuse: avoids creating SimpleDateFormat in multiple places</p>
     */
    public static void displayDate() {
        LOGGER.info("START");
        try {
            // Load Spring application context from date-format.xml on the classpath
            ApplicationContext context = new ClassPathXmlApplicationContext("date-format.xml");

            // Retrieve the dateFormat bean configured with pattern 'dd/MM/yyyy'
            SimpleDateFormat format = context.getBean("dateFormat", SimpleDateFormat.class);

            // Parse the date string using the configured format
            Date date = format.parse("31/12/2018");

            // Debug log: display the parsed date (using LOGGER, not System.out.println)
            LOGGER.debug("Parsed Date: {}", date);
        } catch (ParseException e) {
            LOGGER.error("Error parsing date: {}", e.getMessage());
        }
        LOGGER.info("END");
    }

    /**
     * Hands-on 4 & 5: Loads Country bean from country.xml.
     *
     * <p>Demonstrates:
     * - Singleton Scope (default): constructor called ONCE even though getBean() is called twice
     * - Prototype Scope (when scope="prototype"): constructor called TWICE, one per getBean()
     * - Property injection via XML: <property name="code" value="IN"/>
     * - context.getBean() method and how Spring creates beans</p>
     *
     * <p>To switch scopes, change scope attribute in country.xml:
     * Singleton: <bean id="country" class="..." scope="singleton">
     * Prototype: <bean id="country" class="..." scope="prototype"></p>
     */
    public static void displayCountry() {
        LOGGER.info("START");
        ApplicationContext context = new ClassPathXmlApplicationContext("country.xml");

        // First getBean() call
        Country country = context.getBean("country", Country.class);
        LOGGER.debug("Country : {}", country.toString());

        // Second getBean() call - demonstrates scope behavior:
        // Singleton: same instance returned, constructor NOT called again
        // Prototype: new instance created, constructor IS called again
        Country anotherCountry = context.getBean("country", Country.class);
        LOGGER.debug("Another Country : {}", anotherCountry.toString());

        // Check if both references point to the same object (singleton) or different (prototype)
        LOGGER.debug("Are both references same instance? {}", (country == anotherCountry));
        LOGGER.info("END");
    }

    /**
     * Hands-on 6: Loads the list of all four countries from country.xml.
     *
     * <p>Demonstrates:
     * - Loading an ArrayList bean from Spring XML configuration
     * - Using <list> and <ref bean="..."> tags in XML
     * - Iterating and displaying a collection loaded from Spring context</p>
     */
    @SuppressWarnings("unchecked")
    public static void displayCountries() {
        LOGGER.info("START");
        ApplicationContext context = new ClassPathXmlApplicationContext("country.xml");

        // Load the ArrayList of Country beans configured with <ref> elements
        List<Country> countries = (ArrayList<Country>) context.getBean("countryList", ArrayList.class);

        // Display each country using debug log
        for (Country country : countries) {
            LOGGER.debug("Country: {}", country.toString());
        }
        LOGGER.info("END");
    }
}
