import grails.converters.JSON
import gala.Segment
import gala.PointSubtype
import gala.PointType
class BootStrap {

    def filter( domain, exclude = []){
        exclude << "class"
        def res = [id: domain.id]
        res << domain.properties.findAll {k,v ->  !(k in exclude) }
    }
    def init = { servletContext ->
        JSON.registerObjectMarshaller(Segment) {
            def res = [id: it.id, end: it.end, start: it.start, length: it.length]
        }
        JSON.registerObjectMarshaller(PointSubtype) {
            filter(it, ['segment'])
        }
        JSON.registerObjectMarshaller(PointType) {
            filter(it, ['subtypes'])
        }
    }
    def destroy = {
    }
}
