
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
  <script type="text/javascript" src="https://www.gstatic.com/charts/loader.js"></script>
  <script language="JavaScript" type="text/javascript" src="script/jquery-1.2.6.min.js"></script>
  <script type="text/javascript">
      google.charts.load('current', {'packages': ['corechart']});
      google.charts.setOnLoadCallback(drawChart);

      function drawChart() {
          var screenName = getUrlVars()["screenname"];
          var jsonUrl = "json/" + screenName + ".json";

          var jsonData = $.ajax({
              url: jsonUrl,
              dataType: "json",
              async: false
          }).responseText;


          var data = new google.visualization.DataTable(jsonData);

          var options = {
              title: 'Average likes per day for @'+screenName,
              curveType: 'function',
              legend: {position: 'bottom'}
          };

          var chart = new google.visualization.LineChart(document.getElementById('curve_chart'));

          chart.draw(data, options);
      }

      function getUrlVars() {
          var vars = {};
          var parts = window.location.href.replace(/[?&]+([^=&]+)=([^&]*)/gi, function(m,key,value) {
              vars[key] = value;
          });
          return vars;
      }
  </script>
  <title>Results</title>
</head>
<body>
<div id="curve_chart" style="width: 2000px; height: 800px"></div>

<form action="index.jsp">
  <input type="submit" value="Go back" />
</form>
</body>
</html>
