<%@page import="java.util.ArrayList"%>
<%@page import="java.util.List"%>
<%@page import="org.com.Priyansh.HistoryResult"%>
<html>
<body>
    <a href="http://localhost:8080/SimpleSearchEngine/">Back to home page</a>
    <table border = 2>
        <tr>
            <td>Type</td>
            <td>Name</td>
            <td>Link</td>
        </tr>
        <%
            ArrayList<HistoryResult> results = (ArrayList<HistoryResult>)request.getAttribute("results");
            for(HistoryResult result: results){
        %>
                <tr>
                    <td><%out.println(result.getType());%></td>
                    <td><%out.println(result.getName());%></td>
                    <td><a href = "<%out.println(result.getLink());%>"><%out.println(result.getLink());%></a></td>
                </tr>
        <%
            }
        %>
    </table>


</body>
</html>