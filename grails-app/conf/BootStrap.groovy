import grails.converters.JSON
import gala.Segment
import gala.Subtype
import gala.Type
import gala.Prize
import gala.PrizeElement
import gala.Subelement

class BootStrap {

    def filter( domain, exclude = []){
        exclude << "class"
        exclude << "securityInfo"
        exclude << "securityIdentity"
        def res = [id: domain.id]
        res << domain.properties.findAll {k,v ->  !(k in exclude) }
    }
    def init = { servletContext ->
        JSON.registerObjectMarshaller(Segment) {
            def res = [id: it.id, end: it.end, start: it.start, length: it.length]
        }
        JSON.registerObjectMarshaller(Subtype) {
            filter(it, ['segment', 'type'])
        }
        JSON.registerObjectMarshaller(Type) {
            filter(it, ['subtypes', 'securityIdentity', 'securityInfo'])
        }
        JSON.registerObjectMarshaller(Prize) {
            filter(it, [])
        }
        JSON.registerObjectMarshaller(PrizeElement) {
            filter(it, ['prize'])
        }
        JSON.registerObjectMarshaller(Subelement) {
            filter(it, ['element', 'segment'])
        }
    }
    def destroy = {
    }
}
