package gala

import groovyx.gpars.GParsPool
import space.ArrayMix
class FillPrizeService {
    static transactional = false
    def inWork = false 
    def sessionFactory 
    def persistenceInterceptor
    def segmentId
    private setInWork(inWork){
        this.inWork = inWork
    }
    def fillSegment(PrizeSegment segment){
        if(inWork){
            println 'inWork!';
            return
        } else {
            inWork = true
        }
        def types = getTypes(segment.subelements)
        segmentId = segment.id
        println types
        fill(segment.start, segment.length, types)
        inWork = false
    }
    def fill(start, length, types) {
        println "fill $start, $length, ${types}"
        def subtypes = mix(length, types)
        def cnt = 0
        def step = 1000
        def st = System.currentTimeMillis()
        def fillit = {
            strt, endd ->
            persistenceInterceptor.init()
            def session = sessionFactory.currentSession
            def tx = session.beginTransaction()
            def queryStart = "INSERT INTO point (id, subelement, subtype) VALUES "
            def queryEnd = " ON DUPLICATE KEY UPDATE point.subelement = VALUES(subelement)"
            
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
                    if(cnt + 1 < length){
                        def stype = subtypes[cnt++];
                        if(stype == -1){
                            stype = 'null'
                        }
                        def update = " ( ${it}, ${stype}, 10) "
                        ids << it
                        updates << update
                    }
                }
                
                def queryStr = queryStart + updates.join(",") + queryEnd
                def query = session.createSQLQuery(queryStr)
                query.executeUpdate()
                portion += step
                if(portion >= 10000){
                    def data = [id: segmentId, portion: portion]
                    event([topic: "loadingPrizeProgressInfo", data: data])
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
            diaps.each{
	        System.sleep(800*it[2])
                fillit(it[0], it[1])
            }
        }
        def end = System.currentTimeMillis()
    }
    
    def mix(length, types) {
        def res = space.ArrayMix.mix(length, types, -1)
        res
    }
    
    def getTypes(subelements){
        def types = [:]
        subelements.each{
            if(!it.single){
                println "it ${it.id} ${it.prizeCount}"
                types[it.id as int] = it.prizeCount
            }
        }
        types
    }
}
