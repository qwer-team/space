<%@ page contentType="text/html;charset=UTF-8" %>
<html>
  <head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <r:require modules="bootstrap"/>
    <title>space loading</title>
  <r:require module="grailsEvents"/>
  <r:layoutResources />

</head>
<body>

<g:each in="${segments}" status="i" var="segment">
<div class="row-fluid">
  <div class="segment_${segment.id} span2">
    <button class="button btn" data-id="${segment.id}">Cегмент № ${i+1}</button>
  </div>
</div>
  
</g:each>
<div class="row-fluid">
    <button class="all btn" >Все</button>
  </div>
<r:layoutResources />
<g:javascript src="loading.js" />
</body>
</html>
