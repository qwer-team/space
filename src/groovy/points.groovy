import gala.Point

session = ctx.sessionFactory.currentSession

starts = System.currentTimeMillis()

rand = new Random()
st = System.currentTimeMillis()
pointss = []
insert  = 'insert into point (version, subtype) VALUES '
for ( int i = 0; i < 900000001; i++ ) {
    pointss << '(0, 0)'
    if ( i % 50000 == 0 ) {
        tx = session.beginTransaction()
        queryStr = insert + pointss.join(',')
        query = session.createSQLQuery(queryStr)
        query.executeUpdate()
        en = System.currentTimeMillis()
        println "-------"
        println en - st
        println i
        println "-------"
        pointss = []
        //st = System.currentTimeMillis()
        tx.commit()
    }
}



end = System.currentTimeMillis()
println end - starts
