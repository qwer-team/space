package gala

class PointSubtype {
    
    PointType type
    Segment segment
    
    Integer pointsCount
    Boolean block
    Boolean restore
    
    
    static {
        grails.converters.JSON.registerObjectMarshaller(PointSubtype) {
            def res = [id: it.id]
            res << it.properties.findAll {k,v -> 
                return (k != 'class' && k != 'segment' )
            }
        }
    }
}
