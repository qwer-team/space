package gala

import grails.converters.JSON
class PointTypeController {

    def getPointType(Integer x, Integer y, Integer z) {
        def id = x + (y - 1) * 10**3 + (z - 1) * 10**6
        def point = Point.get(id)
        
        
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
        
        render data as JSON
    }
}
