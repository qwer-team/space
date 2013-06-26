package gala

import grails.converters.JSON
class JumpController {

    def proceed(Integer x, Integer y, Integer z) { 
        def id = x + (y - 1) * 10**3 + (z - 1) * 10**6
        def point = Point.get(id)
        
        def eventData = [
            point: point,
            x: x, 
            y: y, 
            z: z,
        ]
        def subtype = Subtype.get(point.subtype)
        def type = subtype.type
        def subelement = null
        def element = null
        if(point.subelement){
             subelement = Subelement.get(point.subelement)
             element = subelement.element
        }
        def data = [
            subtype: subtype, 
            type: type,
            subelement: subelement,
            element: element,
        ]
        event([topic: "coordinatesChange", data: eventData])
        render data as JSON
    }
}
