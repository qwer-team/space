package gala

import groovyx.gpars.GParsPool
import space.ArrayMix
class FillService {
    static transactional = false
    def inWork = false 
    def sessionFactory 
    def persistenceInterceptor
    def segmentId
    private setInWork(inWork){
        this.inWork = inWork
    }
    def fillSegment(Segment segment){
        println 'prize seg'
        if(inWork){
            println 'inWork!';
            return
        } else {
            println 'fill prize'
            inWork = true
        }
        def types = getTypes(segment.subtypes)
        segmentId = segment.id
        println types
        fill(segment.start, segment.length, types)
        inWork = false
    }
    def fill(start, length, types) {
        println "fill $start, $length, $types"
        def subtypes = mix(length, types)
        def cnt = 0
        def step = 1000
        def st = System.currentTimeMillis()
        def fillit = {
            strt, endd ->
            persistenceInterceptor.init()
            def session = sessionFactory.currentSession
            def tx = session.beginTransaction()
            def queryStart = "INSERT INTO point (id, subtype) VALUES "
            def queryEnd = " ON DUPLICATE KEY UPDATE point.subtype = VALUES(subtype)"
            
            def portion = step;
            (strt..<endd).step(step){
                def offset = start - 1 + it
                def max = step
                if(offset + step > start + endd){
                    max = start + endd - offset
                }
                def queryString = queryStart
                def updates = []
                def ids = []
                ((offset+1)..(offset+max)).each{
                    def update = " ( ${it}, ${subtypes[cnt++]}) "
                    ids << it
                    updates << update
                }
                
                def queryStr = queryStart + updates.join(",") + queryEnd 
                def query = session.createSQLQuery(queryStr)
                query.executeUpdate()
                portion += step
                if(portion >= 10000){
                    def data = [id: segmentId, portion: portion]
                    event([topic: "loadingProgressInfo", data: data])
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
            println "diaps: $diaps"
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
