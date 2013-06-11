package gala

import grails.converters.JSON
class JumpController {

    def proceed(Integer x, Integer y, Integer z) { 
        def id = x + (y - 1) * 10**3 + (z - 1) * 10**6
        def point = Point.get(id)
        
        def eventData = [
            point: point,
            x: x, 
            y:y, 
            z:z,
        ]
        event([topic: "coordinatesChange", data: eventData])
        def subtype = Subtype.get(point.subtype)
        def type = subtype.type
        def data = [subtype: subtype, type: type]
        render data as JSON
    }
}
