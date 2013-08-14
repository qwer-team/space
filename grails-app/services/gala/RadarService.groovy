package gala

class RadarService {

    def sessionFactory
    def step = 1000
    def find(type, x, y, z, minRadius, maxRadius) {
        def coords = findCoords (minRadius, maxRadius)
        def total = coords.size()
        def ids = []
        def counter = 0
        def point = null
        for(def i = 0; i < total; i++){
            def coord = coords[i]
            ids << getId(x, y, z, coord[0], coord[1], coord[2])
            counter++
            if(counter == 1000 || i == (total - 1)){
                point = check(ids, type)
                if(point){
                    break
                }
                ids = []
                counter = 0
            }
        }
        point
    }
    
    def check(ids, type){
        def points = Point.getAll(ids)
        def res = false
        def prize = false
        def subtypes
        if(type == 4){
            prize = true
        } else {
            subtypes = getSubtypes(type)
        }
        def size = ids.size()
        for(def i = 0; i < size; i++){
           
            def point = points[i]
            if(point != null){
                if(prize){
                    if(point.subelement){
                        res = point
                    }
                } else {
                    if((point.subtype as Long) in subtypes){
                        res = point
                    }
                }
                if(res){
                    break;
                }
            }
        }
        clear()
        res
    }
    
    def subtypesCache = [:]
    def getSubtypes(type){
        def subtypes
        if(!subtypesCache.containsKey(type)){
            def tag
            switch(type){
            case 1: tag = "plus_percent"; break; 
            case 2: tag = "plus_all_period"; break; 
            case 3: tag = "plus_prize_period"; break; 
            }
            def pointType = Type.findByTag(tag)
            def types = Subtype.findAllByType(pointType)
            subtypes = types.collect{ it.id }
            subtypesCache[type] = subtypes
        } else {
            subtypes = subtypesCache.get(type)
        }
        subtypes
    }
    
    def clear(){
        def session = sessionFactory.currentSession
        session.clear() 
    }
    
    def getId(sx, sy, sz, nx, ny, nz){
        def x = sx + nx
        if(x > 1000){
            x = 2000 - x
        } else if(x < 1){
            x = 1000 + x
        }
        def y = sy + ny
        if(y > 1000){
            y = 2000 - y
        } else if(y < 1){
            y = 1000 + y
        }
        def z = sz + nz
        if(z > 1000){
            z = 2000 - z
        } else if(z < 1){
            z = 1000 + z
        }
        x + (y - 1) * (10**3) + (z - 1) * (10**6)
    }
    
    def findCoords (minRadius, maxRadius){
        def coords = []
        def count = 0
        for(def x = 1; x <= maxRadius; x++){
            if(x > minRadius && x <= maxRadius){
                count += 6
                coords << [x, 0, 0]
                coords << [-x, 0, 0]
                coords << [0, x, 0]
                coords << [0, -x, 0]
                coords << [0, 0, x]
                coords << [0, 0, -x]
            }
            for(def y = x; y <= maxRadius; y++){
                def xyLen = y**2 + x**2
                if(xyLen <= maxRadius**2){
                    def xy = x == y
                    if(xyLen <= maxRadius**2 && xyLen > minRadius**2){
                        if(xy){
                            coords << [x, x, 0]
                            coords << [-x, -x, 0]
                            coords << [-x, x, 0]
                            coords << [x, -x, 0]
                            coords << [0, x, x]
                            coords << [0, x, -x]
                            coords << [0, -x, -x]
                            coords << [0, -x, x]
                            coords << [x, 0, x]
                            coords << [-x, 0, x]
                            coords << [-x, 0, -x]
                            coords << [x, 0, -x]
                            count += 12
                        } else{
                            coords << [x, y, 0]
                            coords << [y, x, 0]
                            coords << [-x, -y, 0]
                            coords << [-y, -x, 0]
                            coords << [-x, y, 0]
                            coords << [-y, x, 0]
                            coords << [x, -y, 0]
                            coords << [y, -x, 0]
                            coords << [0, x, y]
                            coords << [0, y, x]
                            coords << [0, x, -y]
                            coords << [0, y, -x]
                            coords << [0, -x, -y]
                            coords << [0, -y, -x]
                            coords << [0, -x, y]
                            coords << [0, -y, x]
                            coords << [x, 0, y]
                            coords << [y, 0, x]
                            coords << [-x, 0, y]
                            coords << [-y, 0, x]
                            coords << [-x, 0, -y]
                            coords << [-y, 0, -x]
                            coords << [x, 0, -y]
                            coords << [y, 0, -x]
                            count += 24
                        }
                    }
                    for(def z = y; z <= maxRadius; z++){
                        def xyzLen = y**2 + x**2 + z**2
                        if(xyzLen <= maxRadius**2 && xyzLen > minRadius**2){
                            def yz = y == z
                            if(xy && yz){
                                coords << [x, x, x]
                                coords << [-x, x, -x]
                                coords << [x, -x, x]
                                coords << [-x, x, x]
                                coords << [x, x, -x]
                                coords << [x, -x, -x]
                                coords << [-x, -x, x]
                                coords << [-x, -x, -x]
                                count += 8
                            } else if(xy){
                                coords << [x, x, z]
                                coords << [-x, -x, z]
                                coords << [-x, x, z]
                                coords << [x, -x, z]
                                coords << [z, x, x]
                                coords << [z, x, -x]
                                coords << [z, -x, -x]
                                coords << [z, -x, x]
                                coords << [x, z, x]
                                coords << [-x, z, x]
                                coords << [-x, z, -x]
                                coords << [x, z, -x]
                                coords << [x, x, -z]
                                coords << [-x, -x, -z]
                                coords << [-x, x, -z]
                                coords << [x, -x, -z]
                                coords << [-z, x, x]
                                coords << [-z, x, -x]
                                coords << [-z, -x, -x]
                                coords << [-z, -x, x]
                                coords << [x, -z, x]
                                coords << [-x, -z, x]
                                coords << [-x, -z, -x]
                                coords << [x, -z, -x]
                                count += 24
                            } else if(yz){
                                coords << [y, y, x]
                                coords << [-y, -y, x]
                                coords << [-y, y, x]
                                coords << [y, -y, x]
                                coords << [x, y, y]
                                coords << [x, y, -y]
                                coords << [x, -y, -y]
                                coords << [x, -y, y]
                                coords << [y, x, y]
                                coords << [-y, x, y]
                                coords << [-y, x, -y]
                                coords << [y, x, -y]
                                coords << [y, y, -x]
                                coords << [-y, -y, -x]
                                coords << [-y, y, -x]
                                coords << [y, -y, -x]
                                coords << [-x, y, y]
                                coords << [-x, y, -y]
                                coords << [-x, -y, -y]
                                coords << [-x, -y, y]
                                coords << [y, -x, y]
                                coords << [-y, -x, y]
                                coords << [-y, -x, -y]
                                coords << [y, -x, -y]
                                count += 24
                            } else{ 
                                coords << [z, y, x]
                                coords << [-z, -y, x]
                                coords << [-z, y, x]
                                coords << [z, -y, x]
                                coords << [x, z, y]
                                coords << [x, z, -y]
                                coords << [x, -z, -y]
                                coords << [x, -z, y]
                                coords << [z, x, y]
                                coords << [-z, x, y]
                                coords << [-z, x, -y]
                                coords << [z, x, -y]
                                coords << [z, y, -x]
                                coords << [-z, -y, -x]
                                coords << [-z, y, -x]
                                coords << [z, -y, -x]
                                coords << [-x, z, y]
                                coords << [-x, z, -y]
                                coords << [-x, -z, -y]
                                coords << [-x, -z, y]
                                coords << [z, -x, y]
                                coords << [-z, -x, y]
                                coords << [-z, -x, -y]
                                coords << [z, -x, -y]
                                
                                coords << [y, z, x]
                                coords << [-y, -z, x]
                                coords << [-y, z, x]
                                coords << [y, -z, x]
                                coords << [x, y, z]
                                coords << [x, y, -z]
                                coords << [x, -y, -z]
                                coords << [x, -y, z]
                                coords << [y, x, z]
                                coords << [-y, x, z]
                                coords << [-y, x, -z]
                                coords << [y, x, -z]
                                coords << [y, z, -x]
                                coords << [-y, -z, -x]
                                coords << [-y, z, -x]
                                coords << [y, -z, -x]
                                coords << [-x, y, z]
                                coords << [-x, y, -z]
                                coords << [-x, -y, -z]
                                coords << [-x, -y, z]
                                coords << [y, -x, z]
                                coords << [-y, -x, z]
                                coords << [-y, -x, -z]
                                coords << [y, -x, -z]
                                count += 48
                            }
                        }
                    }
                }
            }
        }
        println "count $count"
        coords
    }
}
