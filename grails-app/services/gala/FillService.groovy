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
        def step = 4000
        def st = System.currentTimeMillis()
        
        def fill = {
            strt, endd ->
            persistenceInterceptor.init()
            def session = sessionFactory.currentSession
            def tx = session.beginTransaction()
            //def query = session.createSQLQuery("update point set subtype = :subtype where id = :id")
            //def queryStart = "UPDATE point SET subtype = CASE "
            def queryStart = "INSERT INTO point (id, subtype) VALUES "
            //def queryEnd = "END WHERE id in"
            def queryEnd = " ON DUPLICATE KEY UPDATE point.subtype = VALUES(subtype), point.version = point.version"
         //   def query = session.createSQLQuery("UPDATE tbl_country SET price = CASE
//WHEN code = 1 THEN 123;")
            (strt..<endd).step(step){
                def offset = start - 1 + it
                def max = step
                if(offset + step > endd){
                    max = endd - offset
                }
                /*Point.list(max: max, offset: offset).each{
                    it.subtype = subtypes[cnt]
                    cnt++
                }*/
                def queryString = queryStart
                def updates = []
                def ids = []
                ((offset+1)..(offset+max)).each{
                    //def update = " WHEN id = ${it} THEN ${subtypes[cnt++]} "
                    def update = " ( ${it}, ${subtypes[cnt++]}) "
                    ids << it
                    updates << update
                    //query.setParameter("subtype", subtypes[cnt])
                    //query.setParameter("id", it)
                    //query.executeUpdate()
                    //cnt++
                }
                
                def queryStr = queryStart + updates.join(",") + queryEnd //+ " (" + ids.join(",") + ")"
                //println queryStr
                def query = session.createSQLQuery(queryStr)
                query.executeUpdate()
                //session.flush()
                //session.clear()
                def end = System.currentTimeMillis()
                //log.info "$it: ${end-st}"
            }

            tx.commit()
            persistenceInterceptor.flush()
            persistenceInterceptor.destroy()
            
        }
        GParsPool.withPool {
            def diaps = []
            def segCnt = 5
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
	        System.sleep(1000*it[2])
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
