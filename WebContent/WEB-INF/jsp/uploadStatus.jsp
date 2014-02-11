<html>
<head>
<title>Upload Status</title>
<link rel="stylesheet" type="text/css" href="css/style.css" />
</head>
<%@ page import="java.io.*" %>
<table border="1">
<tr><th colspan="3">Current files in upload directory</th></tr>
<%
String filePath = getServletContext().getInitParameter("file-upload-dir");
File dir = new File(filePath);
File fs[] = dir.listFiles();
for (int i=0; i<fs.length; i++) {
   if (fs[i].isFile()) {
      if (fs[i].getName().endsWith(".xml")) {
         out.print("<tr><td>");
         out.print(fs[i].getName());
         out.println("</td>");
         out.println("<form action=\"ProcessFile\">");
         out.println("<td>Floor Level<input type=\"text\" name=\"floorname\" value=\"Floor1\" /></td>");
         out.println("<td><input type=\"hidden\" name=\"file\" value=\""+fs[i].getName()+"\" />");
         out.println("   <input type=\"hidden\" name=\"action\" value=\"dbload\" />");
         out.println("   <input type=\"submit\" value=\"DB load "+fs[i].getName()+"\">");
         out.println("</td></form></tr>");
      } else {
         out.print("<tr><td colspan=\"3\">");
         out.print(fs[i].getName());
         out.println("</td></tr>");          
      } 
  }
}
out.println("<tr>");
out.println("<form action=\"ProcessFile\">");
out.println("<td colspan=\"2\">Floor Level<input type=\"text\" name=\"floorname\" value=\"Floor1\" /></td>");
out.println("<td><input type=\"hidden\" name=\"action\" value=\"imgcreate\" />");
out.println("<input type=\"submit\" value=\"Image create from DB\"></td>");
out.println("</form>");
out.println("</tr>");
out.println("<tr><td colspan=\"3\"><form action=\"ProcessFile\">");
out.println("   <input type=\"hidden\" name=\"action\" value=\"randname\" />");
out.println("   <input type=\"submit\" value=\"Randomize DB names\">");
out.println("</form></td>");
out.println("</tr>");
%>
</table>
</html>