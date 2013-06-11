package gala

class PointService {
    static transactional = true
    @grails.events.Listener
    def coordinatesChange(data) {
        def point = data.point
        def x = data.x
        def y = data.y
        def z = data.z
        
        
        def subtype = Subtype.get(point.subtype)
       
        
        if(subtype.restore){
            def type = subtype.type
            def a = type.a
            def b = type.b
            def c = type.c
            
            def delta = type.delta1
            
            
            def newX = findNewCoord(x, delta, a)
            def newY = findNewCoord(y, delta, b)
            def newZ = findNewCoord(z, delta, c)
            println ([newX, newY, newZ])
        }
    }
    
    def findNewCoord(old, delta, add){
        def random = new Random()
        random = new Random(random.nextInt(100))
        def newCoor = -1
        def cnt = 0
        for(def i in 1..1000){
            newCoor = random.nextInt(delta * 2 + 1) - delta + old + add
            if(newCoor >= 1 && newCoor <= 1000){
                break;
            }
        }
        newCoor
    }
}
