package gala

import grails.converters.JSON
class PrizeSegmentController {

    def segmentService
    def reset(Integer number) {
        def response 
        try{
            segmentService.domainClassname = "gala.PrizeSegment"
            segmentService.reset(number)
            response = [result: "success"]
        } catch (Exception e) {
            println e.getMessage()
            response = [result: "fail"]
        }
        render response as JSON
    }
    
    def list(){
        render PrizeSegment.list() as JSON
    }
    
    def update(Long id){
        def segment = PrizeSegment.get(id)
        segment.properties = params
        segment.save()
        
        log.info segment
        
        segmentService.domainClassname = "gala.PrizeSegment"
        if(segmentService.correctLength()){
            segmentService.updateStart()
        }
        
        def response = [result: "success"]
        render response as JSON
    }
}
