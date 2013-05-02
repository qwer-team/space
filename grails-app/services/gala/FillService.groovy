package gala

import groovyx.gpars.GParsPool

class FillService {
    static transactional = false
    def sessionFactory 
    def persistenceInterceptor
    
    def fill(start, length, types) {
        def first = start - 1
        
        def subtypes = mix(length, types)
        def cnt = 0
        def step = 10000
        def st = System.currentTimeMillis()
        
        def fill = {
            strt, endd ->
            persistenceInterceptor.init()
            def session = sessionFactory.currentSession
            def tx = session.beginTransaction()
            (strt..<endd).step(step){
                def offset = start - 1 + it
                def max = step
                if(offset + step > endd){
                    max = endd - offset
                }
                Point.list(max: max, offset: offset).each{
                    it.subtype = subtypes[cnt]
                    cnt++
                }
                session.flush()
                session.clear()
                def end = System.currentTimeMillis()
                println "$it: ${end-st}"
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
            (1..segCnt).each{
                def end = frst + len
                if(it == segCnt){
                    def mod = length % segCnt
                    end += mod
                }
                diaps << [frst, end]
                frst = end
            }
            
            diaps.eachParallel{
                fill(it[0], it[1])
            }
        }
        def end = System.currentTimeMillis()
        println "fenita: ${end-st}"
    }
    
    def mix(length, types) {
        space.ArrayMix.mix(length, types)
    }
}
