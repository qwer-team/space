package gala

import grails.converters.JSON

class PointSubtypeController {

    def getPointsOnSegment(Long id) {
        def thisSegment = Segment.get(id)
        def response = [types: PointType.list(), 
            subtypes: PointSubtype.findAllBySegment(thisSegment),
            length: thisSegment.length]
        render response as JSON
        
    }
    
    def create(Long typeId){
        def response 
        response = [result: "success"]
        render response as JSON
    }
    
    def update(Long id){
        def response 
        response = [result: "success"]
        render response as JSON
    }
    
}
