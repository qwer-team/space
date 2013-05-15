package gala

class PointType {
    
    static hasMany = [subtypes: PointSubtype]
    
    String name
    static {
        grails.converters.JSON.registerObjectMarshaller(PointType) {
            def res = [id: it.id]
            res << it.properties.findAll {k,v -> 
                 return (k != 'class' && k != 'subtypes' )
            }
        }
    }
}

