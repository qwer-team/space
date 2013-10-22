package gala

class EventService {

    def fillService
    def progress = [:]
    
    @grails.events.Listener(namespace='browser') 
    initLoading(Map data){
        progress = [:]
        if(fillService.inWork){
            return
        }
        event([namespace: "browser", topic: "start", data: []])
        Segment.list().each{
            initSegmentLoading(it)
        }
        progress = [:]
        event([namespace: "browser", topic: "end", data: []])
    }
    @grails.events.Listener(namespace='browser') 
    initSegment(Map data){
        initSegmentLoading(Segment.get(data.segment))
        progress = [:]
        event([namespace: "browser", topic: "endSegment", data: [id: data.segment]])
    }
    def initSegmentLoading(segment) {
        def response = [id: segment.id, length: segment.length]
        println segment
        progress[segment.id] = [length: segment.length, portion: 0]
        event([namespace: "browser", topic: "spaceStartLoading", data: response]);
        event([namespace: "browser", topic: "spaceStartLoading", data: response]);
        fillService.fillSegment(segment)
        progress[segment.id] = [length: segment.length, portion: segment.length]
        event([namespace: "browser", topic: "endLoading", data: response])
    }
    
    @grails.events.Listener
    loadingProgressInfo(Map data){
        progress[data.id].portion += data.portion
        data.portion = progress[data.id].portion
        data.progress =  progress[data.id]
        event([namespace: "browser", topic: "loadingProgress", data: data])
    }
    
    @grails.events.Listener(namespace='browser') 
    getLoadingStatus(Map data){
        println "proinfo $progress"
        data = [inWork: fillService.inWork, progress: progress]
        event([namespace: "browser", topic: "loadingStatus", data: data])
    }
}
