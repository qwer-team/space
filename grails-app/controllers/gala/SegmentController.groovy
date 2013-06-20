package gala

import grails.converters.JSON
class SegmentController {

    def segmentService
    def reset(Integer number) {
        log.info number
        def response 
        try{
            segmentService.reset(number)
            response = [result: "success"]
        } catch (Exception e) {
            response = [result: "fail"]
        }
        render response as JSON
    }
    
    def list(){
        render Segment.list() as JSON
    }
    
    def update(Long id){
        def segment = Segment.get(id)
        segment.properties = params
        segment.save()
        
        log.info segment
        
        if(segmentService.correctLength()){
            segmentService.updateStart()
        }
        
        def response = [result: "success"]
        render response as JSON
    }
    
}
