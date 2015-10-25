package monetdbconnector

import groovy.sql.Sql
import java.sql.SQLException
import grails.converters.JSON

class OdbcController{
    def dataSource

    /*def index() {

        def sql = new Sql(dataSource)
        def res = sql.rows('select * from sucursal')
        log.debug res.size()
        res.each{
            log.debug "NUEVO DATO"
            log.debug res.id
            log.debug res.description
        }
    }*/

    /*
    * RETURNS: query execution result set
    */
    def executeQuery(String query){
        def sql = new Sql(dataSource)
        def res = sql.rows(query)
        log.debug res
    }

    def executeInsert(String query, String values){
        def sql = new Sql(dataSource)
        try{
            def jVal = JSON.parse(values)
            //insert into sucursal(id, description) values (:id, :des)
            log.debug query
            def updateCounts = sql.withBatch(jVal.data.size(), query) { ps ->
                jVal.data.each{it->
                    log.debug it
                    ps.addBatch(it)
                }
                //ps.addBatch([id:24, des:'des1'])  // map
                //ps.addBatch(id:25, des:'des2')     // Groovy named args allow outer brackets to be dropped
            }
        }catch(SQLException e){
            while (e) {
                log.error "sql error message: $e.message"

                // This vendor-independent string contains a code.
                log.error "sql state: ${e.getSQLState()}"

                log.error "error code: $e.errorCode"
                e = e.nextException
            }
        }catch(e){
            log.error e.message, e
        }
    }
}
