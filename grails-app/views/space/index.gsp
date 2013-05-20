<%@ page contentType="text/html;charset=UTF-8" %>
<html>
  <head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>space loading</title>
  <r:require module="grailsEvents"/>
  <r:layoutResources />

</head>
<body>
<g:each in="${segments}" status="i" var="segment">
  <div class="segment_${segment.id}">
    <button class="button" onclick="initLoading(${segment.id})" >Загрузить сегмент № ${segment.id}</button>
  </div>
</g:each>
<r:layoutResources />
<g:javascript src="loading.js" />
</body>
</html>
