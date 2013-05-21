package gala

class SegmentService {
    static transactional = true
    def space = 10000000
    def reset(number) {
        Segment.list().each{
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
        Segment.list().each{
            length += it.length
        }
        length == space 
    }
    
    def updateStart(){
        def start = 1
        Segment.list().each{
            it.start = start
            def end = start + it.length - 1
            it.end = end
            it.save()
            start = end + 1
        }
    }
}
