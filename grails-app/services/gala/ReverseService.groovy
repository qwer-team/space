package gala

import groovyx.gpars.GParsPool

class ReverseService {
    static transactional = false
    def sessionFactory
    def cnt = 960000
    def step = 1000
    def persistenceInterceptor
    def execute(){
        def startt = System.currentTimeMillis()
        def session = sessionFactory.currentSession
        //def cnt = Point.executeQuery("select count(p) from Point p")[0]
        log.info("cnt: " + cnt)
        
        def middle = cnt/2
        def tx = session.beginTransaction()
        for(def i = 0; i < middle; i += step){
            println i
            def start = Point.list(max: step, offset: i)
            def endOffset = cnt - step - i
            def ends = Point.list(max: step, offset: endOffset)
            for(def k = 0; k < step; k++){
                def element = start[k]
                def startType = element.subtype
                //log.info startType
                def end = ends[step - 1 - k]
                element.subtype = end.subtype
                end.subtype = startType
            }
            session.flush()
            session.clear()
        }

        tx.commit()
        def end = System.currentTimeMillis()
        log.info end - startt
    }
    
    def executeParallel(){
        def startt = System.currentTimeMillis()
        
        //def cnt = Point.executeQuery("select count(p) from Point p")[0]
        log.info("cnt: " + cnt)
        
        GParsPool.withPool {
           def etps = 3 
           def part = cnt / (etps *2)
            (1..etps).eachParallel{
                System.sleep(1000 * (it - 1))
                def pos = part * (it - 1)
                mix(pos, part)
            }
        }
        def end = System.currentTimeMillis()
        log.info end - startt
    }
    
    def mix(pos, part){
        persistenceInterceptor.init()
        def session = sessionFactory.currentSession
        def middle = pos + part
        def tx = session.beginTransaction()
        def multi = 20
        def selectStep = step * multi
        for(def i = pos; i < middle; i += selectStep){
            def start = Point.list(max: selectStep, offset: i)
            def endOffset = cnt - selectStep - i
            def ends = Point.list(max: selectStep, offset: endOffset)
            log.info "${i} start ${endOffset} end"
            (0..(multi - 1)).each{
                for(def k = 0; k < step; k++){
                    def ind = it * 1000 + k
                    def element = start[ind]
                    def startType = element.subtype
                    //log.info startType
                    def end = ends[selectStep - 1 - ind]
                    element.subtype = end.subtype
                    end.subtype = startType
                }
                session.flush()
            }
            session.clear()
        }

        tx.commit()
        persistenceInterceptor.flush()
        persistenceInterceptor.destroy()
    }
}
