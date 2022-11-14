<%@page import="java.util.ArrayList"%>
<%@page import="java.util.List"%>
<%@page import="org.com.Priyansh.SearchResult"%>
<html>
<body>
    <form action = "Search">
        <input type = "text" name = "keyword">
        <button type = "submit"> Search </button>
    </form>
    <a href="http://localhost:8080/SimpleSearchEngine/History">History</a>
    <table border = 2>
        <tr>
            <td>Name</td>
            <td>Link</td>
        </tr>
        <%
            ArrayList<SearchResult> results = (ArrayList<SearchResult>)request.getAttribute("results");
            for(SearchResult result: results){
        %>
                <tr>
                    <td><%out.println(result.getName());%></td>
                    <td><a href = "<%out.println(result.getLink());%>"><%out.println(result.getLink());%></a></td>
                </tr>
        <%
            }
        %>
    </table>


</body>
</html>