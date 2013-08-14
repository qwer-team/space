package gala

import grails.converters.JSON
class RadarController {

    def pointService
    def prizeService
    def radarService 
    def find() { 
        def type = Integer.parseInt(params.type)
        def x = Integer.parseInt(params.x)
        def y = Integer.parseInt(params.y)
        def z = Integer.parseInt(params.z)
        def point = 
            radarService.find(type, x, y, z, 
                            Integer.parseInt(params.r1), 
                            Integer.parseInt(params.r2)
                        );
        def result
        if(point){
            def subelement, subtype
            def eventData = [point: point, x: x, y: y, z: z]
            if(type == 4){
                subelement = Subelement.get(point.subelement)
                event([topic: "changeSubelementListener", data: eventData])
            } else {
                subtype =  Subtype.get(point.subtype)
                event([topic: "changeSubtypeListener", data: eventData])
            }

            result = [result: "success", point: point, subelement: subelement, subtype: subtype]

        } else {
            result = [result: "fail"]
        }
        render result as JSON
    }
    
}
