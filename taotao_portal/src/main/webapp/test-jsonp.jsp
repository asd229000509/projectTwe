<%--
  Created by IntelliJ IDEA.
  User: 22900
  Date: 2018-03-13
  Time: 19:36
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
    <script type="text/javascript" src="js/jquery-1.2.6.min.js"></script>
    <script type="text/javascript">
        $.ajax({
            url:"http://manage.taotao.com/json.jsp",
            dataType:"jsonp",
            success:function (msg) {
                alert(msg.test);
            }
        })
        /*function itast(msg){
            alert(msg.test);
        }*/
    </script>
   <%-- <script type="text/javascript" src="http://manage.taotao.com/json.jsp?callback=itast"></script>--%>
</head>
<body>

</body>
</html>
