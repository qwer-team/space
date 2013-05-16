package gala

class Type {
    
    static hasMany = [subtypes: Subtype]
    
    String name
    /*static {
        grails.converters.JSON.registerObjectMarshaller(new CustomDomainMarshaller()) {
            def res = [id: it.id]
            res << it.properties.findAll {k,v -> return (k != 'class' && k != 'subtypes' )}
        }
    }*/
}

