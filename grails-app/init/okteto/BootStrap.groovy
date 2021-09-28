package okteto

import org.hibernate.SessionFactory
import org.springframework.core.io.Resource

import java.text.SimpleDateFormat

class BootStrap {

    def assetResourceLocator
    SessionFactory sessionFactory

    def init = { servletContext ->
        Resource originalData = assetResourceLocator.findAssetForURI('original-data.csv')
        originalData.inputStream.toCsvReader([skipLines:1]).eachLine { tokens ->
            AdvertInfo.withNewTransaction {
                AdvertInfo adInfo = new AdvertInfo(datasource: tokens[0],
                        campaign: tokens[1],
                        day: new SimpleDateFormat('MM/dd/yy').parse(tokens[2]),
                        clicks: tokens[3],
                        impressions: tokens[4])
                adInfo.save(failOnError: true, flush: true)
            }
            sessionFactory.currentSession.clear()
        }
        println "Number of rows: ${AdvertInfo.count()}"
    }
    def destroy = {
    }
}
