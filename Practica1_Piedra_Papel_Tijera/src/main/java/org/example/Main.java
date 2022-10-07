package org.example;


import java.io.*;
import java.util.Locale;
import java.util.Properties;
import java.util.ResourceBundle;


import lombok.extern.slf4j.Slf4j;

@Slf4j

public class Main {

    static FileReader reader;

    static ResourceBundle currentBundle;



    public static void main(String[] args) throws IOException {

        log.trace("Starting " );
        log.error("error");


        //default locale
        ResourceBundle bundle = ResourceBundle.getBundle("ApplicationMessages");
        log.trace("Trace message");
        log.info("Log Test");

        currentBundle = bundle;


        //lets print some messages
        println("CountryName");
        print("CurrencyCode");
        try {
            reader = new FileReader("config/config.properties");
            Properties properties = new Properties();
            properties.load(reader);
            String dificultad = properties.getProperty("difficulty");
            System.out.println(dificultad);

            properties.setProperty("capullo", "melon");
            properties.store(new BufferedWriter(new FileWriter("config/config.properties")), "Ejemplo");

        } catch (FileNotFoundException e) {
            System.out.println("File not found");
            throw new RuntimeException(e);
        } catch (IOException e) {
            System.out.println("Error reading file");
            throw new RuntimeException(e);
        } finally {
            reader.close();
        }
    }

    private static void println(String key) {

        System.out.println(currentBundle.getString(key));

    }
    private static void print(String key) {

        System.out.println(currentBundle.getString(key));

    }

}