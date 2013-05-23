dataSource {
    pooled = true
    driverClassName = "org.h2.Driver"
    username = "sa"
    password = ""
}
hibernate {
    cache.use_second_level_cache = true
    cache.use_query_cache = false
    cache.region.factory_class = 'net.sf.ehcache.hibernate.EhCacheRegionFactory'
    max_fetch_depth = 3
}
// environment specific settings
environments {
    development {
        dataSource {
            username = 'root'
            password = '123'
            dbCreate = "update" // one of 'create', 'create-drop', 'update', 'validate', ''
            url = "jdbc:mysql://localhost:3306/gala?useUnicode=yes&characterEncoding=UTF-8"
            driverClassName = "org.gjt.mm.mysql.Driver"
            logSql = false
        }
    }
    test {
        dataSource {
            dbCreate = "update"
            url = "jdbc:h2:mem:testDb;MVCC=TRUE;LOCK_TIMEOUT=10000"
        }
    }
    production {
        dataSource {
            username = 'root'
            password = '123'
            dbCreate = "update" // one of 'create', 'create-drop', 'update', 'validate', ''
            url = "jdbc:mysql://localhost:3306/gala"
            driverClassName = "org.gjt.mm.mysql.Driver"
            logSql = false
        }
    }
}
