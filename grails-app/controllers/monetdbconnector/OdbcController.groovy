package monetdbconnector

import groovy.sql.Sql
import java.sql.SQLException
import grails.converters.*

class OdbcController{
    def dataSource

    /*def index() { 
    	
        def sql = new Sql(dataSource)
        def res = sql.rows('select * from sucursal')
        println res.size()
        res.each{
            println "NUEVO DATO"
            println res.id
            println res.description
        }
    }*/

    /*
    * RETURNS: query execution result set
    */
    def executeQuery(String query){
            def sql = new Sql(dataSource)
            def res = sql.rows(query)
            println res
    }

    def executeInsert(String query,String values){
        def sql = new Sql(dataSource)
        try{
            def jVal = JSON.parse(values)
            //insert into sucursal(id, description) values (:id, :des)
            println query
            def updateCounts = sql.withBatch(jVal.data.size(), query) { ps ->
                    jVal.data.each{it->
                        println it
                        ps.addBatch(it)
                    }
                    //ps.addBatch([id:24, des:'des1'])  // map
                    //ps.addBatch(id:25, des:'des2')     // Groovy named args allow outer brackets to be dropped
            }
        }catch(SQLException e){
            while (e != null) {
                String errorMessage = e.getMessage();
                println("sql error message:" + errorMessage);

                // This vendor-independent string contains a code.
                String sqlState = e.getSQLState();
                println("sql state:" + sqlState);

                int errorCode = e.getErrorCode();
                println("error code:" + errorCode);
                e = e.getNextException();
            }
        }catch(Exception ex2){
            println ex2.message
        }
    }

}
