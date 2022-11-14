package org.com.Priyansh;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;
import javax.servlet.annotation.WebServlet;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.HashSet;

//@WebListener
public class Crawler implements ServletContextListener {

    private HashSet<String> urlLink;
    public int MAX_DEPTH = 2;
    public Connection connection;
    public Crawler()  {
        try {
            connection = DatabaseConnection.getConnection();
            connection.createStatement().executeUpdate("Truncate table search");
        }catch (SQLException sqlException){
            System.out.println(sqlException);
        }
        urlLink = new HashSet<String>();
    }
    public void getPageTextAndLinks(String URL, int depth){
        if(!urlLink.contains(URL)&&depth<MAX_DEPTH){
            try {
                if(urlLink.add(URL)){
                    System.out.println(URL);
                }
                Document document = Jsoup.connect(URL).timeout(5000).get();

                String text = document.text().substring(0, 500);
                try {
                    PreparedStatement preparedStatement = connection.prepareStatement("Insert into search values(?,?,?)");
                    preparedStatement.setString(1, document.title());
                    preparedStatement.setString(2, URL);
                    preparedStatement.setString(3, text);
                    preparedStatement.executeUpdate();
                }catch (SQLException sqlException){
                    System.out.println(sqlException);
                }


                depth++;
                Elements availableLinksOnPage = document.select("a[href]");
                for (Element element:availableLinksOnPage){
                    getPageTextAndLinks(element.attr("abs:href"), depth);
                }

            }catch (Exception exception){
                System.out.println(exception);
            }
        }
    }
    @Override
    public void contextInitialized(ServletContextEvent event) {
        Crawler crawler = new Crawler();

        long start = System.currentTimeMillis();
        long end = start + 30 * 1000;
        crawler.getPageTextAndLinks("https://www.javatpoint.com/", 0);

    }
    @Override
    public void contextDestroyed(ServletContextEvent event) {
        // Webapp shutdown.
    }
}
