package gala

import grails.converters.JSON

class SubtypeController {

    def getPointsOnSegment(Long id) {
        def thisSegment = Segment.get(id)
        def response = [
            types: Type.list(), 
            subtypes: Subtype.findAllBySegment(thisSegment),
            length: thisSegment.length]
        render response as JSON
        
    }
    
    def save(Long id){
        def segment = Segment.get(id)
        def type = Type.get(params.typeId)
        println params
        def props = [
            type: type, 
            segment: segment, 
            pointsCount: params.pointsCount,
            block: params.block,
            restore: params.restore] 
        def subtype = new Subtype(props)
        if(!subtype.save()){
            throw new Exception("subtype wasnt saved!!!");
        }
        
        def resp = [result: "success"]
        render resp as JSON
    }
    
}
