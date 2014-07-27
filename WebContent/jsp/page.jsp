<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"  trimDirectiveWhitespaces="true"%>
<%
int t=Integer.valueOf(request.getParameter("count"));
int p=Integer.valueOf(request.getParameter("page"));
String path = request.getParameter("path");
String first = request.getParameter("first");
int s=6;
int hs=s/2;
int pre = p-hs>2?p-hs:2;
int end = p+hs>t-1?t-1:p+hs;
%>
<div id="pagination" class="pages_bg"><div class="pages color_66 englist_one">
<%if(p>1){ %><a href="<%=p-1 %>" class="first"></a><%} %>
<a href="<%=first %>" <%if(1==p){ %>class="hover"<%} %>>1</a>
<%if(pre-1>1){ %><a>...</a><%} %>
<%for(int i =pre;i<=end;i++){ %>
<a href="<%=path+'/'+i %>" <%if(i==p){ %>class="hover"<%} %>><%=i %></a>
<%} %>
<%if(t-end>1){ %><a>...</a><%} %>
<a href="<%=path+'/'+t %>" <%if(t==p){ %>class="hover"<%} %>><%=t %></a></div>
<%if(p<t){ %><div class="pages_next"><a href="<%=path+'/'+(p+1) %>" class="pages_next"></a></div><%} %>
</div>