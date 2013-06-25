package gala

class SegmentService {
    static transactional = true
    def space = 10000000
    def domainClassname = "gala.Segmenet";
    def reset(number) {
        getDomainClass().list().each{
            it.subtypes.each{
                it.segment = null
            }
            it.delete()
        }
        
        def len = (int) space / number
        def modulo = space % number
        def start = 1
        (1..number).each{
            def length = len
            if(it == number){
                length += modulo
            }
            def end = start + length - 1
            new Segment(start: start, end: end, length: length).save()
            start = end + 1
        }
    }
    
    def correctLength(){
        def length = 0
        getDomainClass().list().each{
            length += it.length
        }
        length == space 
    }
    
    def updateStart(){
        def start = 1
        getDomainClass().list().each{
            it.start = start
            def end = start + it.length - 1
            it.end = end
            it.save()
            start = end + 1
        }
    }
    
    def getDomainClass(){
        grailsApplication.getArtefact("Domain", domainClassname)?.getClazz()
    }
}
