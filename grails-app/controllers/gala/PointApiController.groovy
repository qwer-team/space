package gala

class PointApiController {

    def jump(x,y,z) { 
        def id = x + (y - 1) * 10**3 + (z - 1) * 10**6
        def point = Point.get(id)
    }
}
