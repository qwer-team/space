package gala

class Segment {
    static hasMany = [subtype: Subtype]
    Integer start
    Integer end
    Integer length
   /* static {
        grails.converters.JSON.registerObjectMarshaller(Segment) {
            def res = [id: it.id]
            res << it.properties.findAll {k,v -> k != 'class'}
        }
    }*/
}
