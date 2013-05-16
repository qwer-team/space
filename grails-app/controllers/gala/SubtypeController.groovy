package gala

import grails.converters.JSON

class SubtypeController {

    def getPointsOnSegment(Long id) {
        def thisSegment = Segment.get(id)
        def response = [types: Type.list(), 
            subtypes: Subtype.findAllBySegment(thisSegment),
            length: thisSegment.length]
        render response as JSON
        
    }
    
    def save(Long typeId){
        def response 
        response = [result: "success"]
        render response as JSON
    }
    
}
