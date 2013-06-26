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
    
    def addSingle(){
        def point = Point.get(params.pointId)
        if(point.subelement != null){
            println 'fail'
            return render([result: 'fail'] as JSON)
        }
        def element = PrizeElement.get(params.element)
        def data = [
            element: element, 
            single: true, 
            pointId: params.pointId,
            restore: false,
            prizeCount: 1,
        ]
        def subelement = new Subelement(data)
        
        if(subelement.save()){
            
            point.subelement = subelement.id
        } else {
            subelement.errors.each{
                println it
            } 
        }
        render([result: 'success'] as JSON)
    }
    
    def updateSingle(Integer id){
        def subelement = Subelement.get(id)
        def oldPoint = Point.get(subelement.pointId)
        
        def newPoint = Point.get(params.pointId)
        if(newPoint.subelement == null){
            newPoint.subelement = id
            oldPoint.subelement = null
            subelement.pointId = newPoint.id
            if(!(oldPoint.save() && newPoint.save() && subelement.save())){
                throw new Exception('single subelement was not updated')
            }
        } 
        
        render([result: 'success'] as JSON)
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
            throw new Exception("subelement wasnt saved!!!");
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
            throw new Exception("subelement wasnt updated!!!");
        }
        println 'subelement updated'
        def resp = [result: "success"]
        render resp as JSON
    }
    
    def getPrizesOnSegment(Long id) {
        def segment = PrizeSegment.get(id)
        def response = [
            types: Prize.list(), 
            subelements: Subelement.findAllBySegment(segment),
            length: segment.length]
        render response as JSON
        
    }
    
    def remove(Long id){
        Subelement.get(params.id).delete()
        def resp = [result: "success"]
        render resp as JSON
    }
    
    def restore(){
        def data = [
            subelement: Integer.parseInt(params.subelement),
            x:  Integer.parseInt(params.x),
            y:  Integer.parseInt(params.y),
            z:  Integer.parseInt(params.z),
        ]
        
        event([topic: "restoreSubelement", data: data])
        render ([result: 'success'] as JSON)
    }
}
