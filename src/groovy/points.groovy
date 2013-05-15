import gala.Point

session = ctx.sessionFactory.currentSession

starts = System.currentTimeMillis()
tx = session.beginTransaction()
rand = new Random()
st = System.currentTimeMillis()
for ( int i = 0; i < 1000000; i++ ) {
    point = new Point(subtype : rand.nextInt(100))
    session.save(point)
    if ( i % 1000 == 0 ) {
        session.flush()
        session.clear()
        en = System.currentTimeMillis()
        println "-------"
        println en - st
        println i
        println "-------"
        st = System.currentTimeMillis()
    }
}

tx.commit()

end = System.currentTimeMillis()
println end - starts