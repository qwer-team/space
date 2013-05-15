<%@ page import="gala.Point" %>



<div class="fieldcontain ${hasErrors(bean: pointInstance, field: 'name', 'error')} ">
	<label for="name">
		<g:message code="point.name.label" default="Name" />
		
	</label>
	<g:textField name="name" value="${pointInstance?.name}"/>
</div>

<div class="fieldcontain ${hasErrors(bean: pointInstance, field: 'type', 'error')} required">
	<label for="type">
		<g:message code="point.type.label" default="Type" />
		<span class="required-indicator">*</span>
	</label>
	<g:field name="type" type="number" value="${pointInstance.type}" required=""/>
</div>

