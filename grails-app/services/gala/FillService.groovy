package gala

import groovyx.gpars.GParsPool
import space.ArrayMix
class FillService {
    static transactional = false
    def sessionFactory 
    def persistenceInterceptor
    def segmentId
    def fillSegment(Segment segment){
        def types = getTypes(segment.subtypes)
        segmentId = segment.id
        fill(segment.start, segment.length, types)
    }
    def fill(start, length, types) {
        def subtypes = mix(length, types)
        def cnt = 0
        def step = 1000
        def st = System.currentTimeMillis()
        def fillit = {
            strt, endd ->
            persistenceInterceptor.init()
            def session = sessionFactory.currentSession
            def tx = session.beginTransaction()
            def queryStart = "INSERT INTO point (id, subtype, version) VALUES "
            def queryEnd = " ON DUPLICATE KEY UPDATE point.subtype = VALUES(subtype)"
            
            def portion = step;
            (strt..<endd).step(step){
                def offset = start - 1 + it
                def max = step
                if(offset + step > endd){
                    max = endd - offset
                }
                def queryString = queryStart
                def updates = []
                def ids = []
                ((offset+1)..(offset+max)).each{
                    def update = " ( ${it}, ${subtypes[cnt++]}, 0) "
                    ids << it
                    updates << update
                }
                
                def queryStr = queryStart + updates.join(",") + queryEnd 
                def query = session.createSQLQuery(queryStr)
                query.executeUpdate()
                portion += step
                if(portion >= 10000){
                    def data = [id: segmentId, portion: portion]
                    event([namespace: "browser", topic: "loadingProgress", data: data])
                    portion = 0
                }
            }

            tx.commit()
            persistenceInterceptor.flush()
            persistenceInterceptor.destroy()
            
        }
        GParsPool.withPool {
            def diaps = []
            def segCnt = 3
            def len = (int)length / segCnt
            def frst = 0
	    def sl = 0 
            (1..segCnt).each{
                def end = frst + len
                if(it == segCnt){
                    def mod = length % segCnt
                    end += mod
                }
                diaps << [frst, end, sl]
		sl++
                frst = end
            }
            
            diaps.eachParallel{
	        System.sleep(800*it[2])
                fillit(it[0], it[1])
            }
        }
        def end = System.currentTimeMillis()
    }
    
    def mix(length, types) {
        space.ArrayMix.mix(length, types)
    }
    
    def getTypes(subtypes){
        def types = [:]
        subtypes.each{
            types[it.id as int] = it.pointsCount
        }
        types
    }
}
