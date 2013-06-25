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
    
    def addSingleSubelement(Integer id){
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
    
    def updateSingle(Integer id){
        def subelement = Subelement.get(id)
        def oldPoint = Point.get(subelement.pointId)
        
        def newPoint = Point.get(params.pointId)
        if(newPoint.subelement == null){
            newPoint.subelement = id
            oldPoint.subelement = null
            subelement.pointId = params.pointId
            if(!(oldPoint.save() && newPoint.save() && subelement.save())){
                throw new Exception('single subelement was not updated')
            }
        }
        
        render([result: success] as JSON)
    }
    
    def save(Long id){
        def segment = PrizeSegment.get(id)
        def element = PrizeElement.get(params.prizeElementId)
        println params
        def props = [
            element: element, 
            segment: segment, 
            pointsCount: params.pointsCount,
            block: params.block != '',
            restore: params.restore != ''
        ] 
        def subelement = new Subelement(props)
        if(!subelement.save()){
            throw new Exception("subtype wasnt saved!!!");
        } 
        
        def resp = [result: "success"]
        render resp as JSON
    }
    
    def update(Long id){
        def subtype = Subelement.get(params.id)
        subtype.pointsCount = Integer.parseInt(params.pointsCount)
        subtype.block = params.block
        subtype.restore = params.restore
        
        if(!subtype.save()){
            throw new Exception("subtype wasnt updated!!!");
        }
        println 'subtype updated'
        def resp = [result: "success"]
        render resp as JSON
    }
    
    def getPrizesOnSegment(Long id) {
        def segment = PrizeSegment.get(id)
        def response = [
            types: Prizes.list(), 
            subelements: Subelement.findAllBySegment(segment),
            length: segment.length]
        render response as JSON
        
    }
    
    def remove(Long id){
        Subelement.get(params.id).delete()
        def resp = [result: "success"]
        render resp as JSON
    }
}
