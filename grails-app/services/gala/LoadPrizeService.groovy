package gala

class LoadPrizeService {

    def fillPrizeService
    def progress = [:]
    
    @grails.events.Listener(namespace='browser') 
    initPrizeLoading(Map data){
        println 'prize loading'
        progress = [:]
        if(fillPrizeService.inWork){
            return
        }
        event([namespace: "browser", topic: "startPrize", data: []])
        PrizeSegment.list().each{
            initSegmentLoading(it)
        }
        progress = [:]
        event([namespace: "browser", topic: "endPrize", data: []])
    }
    @grails.events.Listener(namespace='browser') 
    initPrizeSegment(Map data){
        println 'prize segment'
        initSegmentLoading(PrizeSegment.get(data.segment))
        progress = [:]
        event([namespace: "browser", topic: "endPrizeSegment", data: [id: data.segment]])
    }
    def initSegmentLoading(segment) {
        def response = [id: segment.id, length: segment.length]
        
        progress[segment.id] = [length: segment.length, portion: 0]
        try{
        event([namespace: "browser", topic: "startPrizeLoading", data: response])
        } catch(e){
            println e.message
        }
        fillPrizeService.fillSegment(segment)
        progress[segment.id] = [length: segment.length, portion: segment.length]
        event([namespace: "browser", topic: "endPrizeLoading", data: response])
    }
    
    @grails.events.Listener
    loadingPrizeProgressInfo(Map data){
        progress[data.id].portion += data.portion
        data.portion = progress[data.id].portion
        data.progress =  progress[data.id]
        event([namespace: "browser", topic: "loadingPrizeProgress", data: data])
    }
    
    @grails.events.Listener(namespace='browser') 
    getPrizeLoadingStatus(Map data){
        println "proinfo prize $progress"
        data = [inWork: fillPrizeService.inWork, progress: progress]
        event([namespace: "browser", topic: "loadingPrizeStatus", data: data])
    }
}
