package gala

class EventService {

    def fillService
    @grails.events.Listener(namespace='browser') 
    initLoading(Map data) {
        
        def segment = Segment.get(data.segment)
        def response = [id: segment.id, length: segment.length]
        event([namespace: "browser", topic: "startLoading", data: response])
        fillService.fillSegment(segment)
        event([namespace: "browser", topic: "endLoading", data: response])
    }
}
