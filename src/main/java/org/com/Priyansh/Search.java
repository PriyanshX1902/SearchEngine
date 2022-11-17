package org.com.Priyansh;


import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

@WebServlet("/Search")
public class Search extends HttpServlet {



    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String keyword = request.getParameter("keyword");
        ArrayList<SearchResult> results = new ArrayList<SearchResult>();
        try{
            Connection connection = DatabaseConnection.getConnection();
            ResultSet resultSet = connection.createStatement().executeQuery("select name, link, (length(lower(pageData))-length(replace(lower(pageData), '"+keyword+"', '')))/length('"+keyword+"') as countoccurence from search order by countoccurence desc limit 30;");
            while (resultSet.next()){
                SearchResult searchResult = new SearchResult();
                searchResult.setName(resultSet.getString("name"));
                searchResult.setLink(resultSet.getString("link"));
                results.add(searchResult);
            }
            PreparedStatement preparedStatement = connection.prepareStatement("Insert into history values (?, ?, ?)");
            preparedStatement.setString(1, "Search");
            preparedStatement.setString(2, keyword);
            preparedStatement.setString(3, "http://localhost:8080/SimpleSearchEngine/Search?keyword="+keyword);
            preparedStatement.executeUpdate();
        }catch (SQLException sqlException){
            System.out.println(sqlException);
        }


        request.setAttribute("results", results);
        request.getRequestDispatcher("/search.jsp").forward(request, response);
        response.setContentType("text/html");
        PrintWriter out = response.getWriter();

    }
}