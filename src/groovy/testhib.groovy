sessionFactory = ctx.sessionFactory
session = sessionFactory.currentSession
query = session.createSQLQuery("select p.subtype, p.id from point p where p.id in(1,2)")

result = query.list()
println result