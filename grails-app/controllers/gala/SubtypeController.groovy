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
            restore: params.restore,
            parameter: params.parameter
        ] 
        def subtype = new Subtype(props)
        if(!subtype.save()){
            throw new Exception("subtype wasnt saved!!!");
        } 
        
        def resp = [result: "success"]
        render resp as JSON
    }
    
    def update(Long id){
        def subtype = Subtype.get(id)
        subtype.pointsCount = Integer.parseInt(params.pointsCount)
        subtype.block = params.block != "0"
        subtype.restore = params.restore != "0"
        subtype.parameter = Integer.parseInt(params.parameter ? params.parameter : "0")
        
        if(!subtype.save()){
            throw new Exception("subtype wasnt updated!!!");
        }
        println 'subtype updated'
        def resp = [result: "success"]
        render resp as JSON
    }
    

    def getSubtype(Integer id){
        def subtype = Subtype.get(id)
        render subtype as JSON
        
    }
    

    def remove(Long id){
        Subtype.get(params.id).delete()
        println 'subtype removed'
        def resp = [result: "success"]
        render resp as JSON
    }
    
}
