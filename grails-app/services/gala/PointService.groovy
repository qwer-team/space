package gala

class PointService {
    static transactional = true
    //TODO
    def zLimit = 1
    @grails.events.Listener
    def coordinatesChange(data) {
        def point = data.point
        def x = data.x
        def y = data.y
        def z = data.z
        
        changeSubtype(x,y,z, point)
        changeSubelement(x,y,z, point)
        
    }
    
    @grails.events.Listener
    def restoreSubelement(data){
        def x = data.x
        def y = data.y
        def z = data.z
        def id = getPointId(x, y, z)
        def point = Point.get(id)
        
        point.subelement = data.subelement
        def newPoint = changeSubelement(x, y, z, point, true)
        def subelement = Subelement.get(newPoint.subelement)
        if(subelement.single){
            subelement.pointId = newPoint.id
            println "new pointid ${newPoint.id}"
            subelement.save()
        }
    }
    
    @grails.events.Listener
    def changeSubelementListener(data){
        changeSubelement(data.x,data.y,data.z,data.point)
    }
    
    def changeSubelement(x,y,z,point, restore = false){
        if(point.subelement == null){
            return
        }
        def subelement = Subelement.get(point.subelement)
        
        if(subelement.element.blocked && !restore){
            return
        }
        if(subelement.restore || restore){
            def element = subelement.element
            def movVariant = element.movingVariant
            def a = element["a$movVariant"]
            def b = element["b$movVariant"]
            def c = element["c$movVariant"]
            def delta = element["delta$movVariant"]
            
            def newX = findNewCoord(x, delta, a)
            def newY = findNewCoord(y, delta, b)
            
            def newZ = findNewCoord(z, delta, c, zLimit)
            
            def newPointId = getPointId(newX, newY ,newZ)
            def newPoint = Point.get(newPointId)
            def newSubelement = newPoint.subelement
            newPoint.subelement = point.subelement
            point.subelement = newSubelement
            point.save()
            newPoint.save()
            return newPoint
        } else {
            point.subelement = null
            point.save()
        }
    }
    @grails.events.Listener
    def changeSubtypeListener(data){
        changeSubtype(data.x,data.y,data.z,data.point)
    }
    
    def changeSubtype(x,y,z,point){
        def subtype = Subtype.get(point.subtype)
        
        if(subtype.restore){
            def type = subtype.type
            def a = type.a
            def b = type.b
            def c = type.c
            
            def delta = type.delta1
            
            def newX = findNewCoord(x, delta, a)
            def newY = findNewCoord(y, delta, b)
            def newZ = findNewCoord(z, delta, c, zLimit)
            def newPointId = getPointId(newX, newY ,newZ)
            def newPoint = Point.get(newPointId)
            def newSubtype = newPoint.subtype
            newPoint.subtype = point.subtype
            point.subtype = newSubtype
            point.save()
            newPoint.save()
            
        } else {
            point.subtype = 1
            point.save()
        }
    }
    
    def findNewCoord(old, delta, add, limit = 1000){
        def random = new Random()
        random = new Random(random.nextInt(100))
        def newCoor = -1
        def cnt = 0
        for(def i in 1..1000){
            newCoor = random.nextInt(delta * 2 + 1) - delta + old + add
            if(newCoor >= 1 && newCoor <= limit){
                break;
            }
        }
        newCoor
    }
    
    def getPointId(x, y, z){
        x + (y - 1) * 10**3 + (z - 1) * 10**6
    }
}
