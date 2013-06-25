package gala

import grails.converters.JSON
class SubelementController {

    def singleList(){
        def list = Subelement.findAllBySingle(true)
        
        render list as JSON
    }
    
    def list(){
        def list = Subelement.findAllBySingle(false)
        render list as JSON
    }
    
    def addSingleSubelement(Integer id)
    {
        def element = PrizeElement.get(id)
        def data = [
            element: element, 
            single: true, 
            pointId: params.pointId,
            restore: false,
            prizeCount: 1,
        ]
        def subelement = new Subelement(data)
        
        if(subelement.save()){
            def point = Point.get(params.pointId)
            point.subelement = subelement.id
        }
        render([result: success] as JSON)
    }
}
