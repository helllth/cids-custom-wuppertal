/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package de.cismet.cids.custom.objectrenderer.utils.alkis;

import de.aedsicad.aaaweb.service.alkis.catalog.ALKISCatalogServices;
import de.aedsicad.aaaweb.service.alkis.catalog.ALKISCatalogServicesServiceLocator;
import de.aedsicad.aaaweb.service.alkis.info.ALKISInfoServices;
import de.aedsicad.aaaweb.service.alkis.info.ALKISInfoServicesServiceLocator;
import de.aedsicad.aaaweb.service.alkis.search.ALKISSearchServices;
import de.aedsicad.aaaweb.service.alkis.search.ALKISSearchServicesServiceLocator;
import java.net.URL;

/**
 * TODO: Should be made (lazy) Singleton? - But check about timeouts!
 * @author srichter
 */
public final class SOAPAccessProvider {

    public SOAPAccessProvider(String identityCard, String service, String catalogService, String infoService, String searchService) {
        this.identityCard = identityCard;
        this.service = service;
        try {
            this.alkisCatalogServices = new ALKISCatalogServicesServiceLocator().getALKISCatalogServices(new URL(catalogService));
            this.alkisInfoService = new ALKISInfoServicesServiceLocator().getALKISInfoServices(new URL(infoService));
            this.alkisSearchService = new ALKISSearchServicesServiceLocator().getALKISSearchServices(new URL(searchService));
        } catch (Exception ex) {
            throw new IllegalStateException("Can not create SOAPAccessProvider", ex);
        }
    }

    public SOAPAccessProvider(String identityCard, String service) {
        this(identityCard, service, "http://s102x083:8080/AAAWebService/services/ALKISCatalogServices", "http://s102x083:8080/AAAWebService/services/ALKISInfoServices", "http://s102x083:8080/AAAWebService/services/ALKISSearchServices");
    }

    public SOAPAccessProvider() {
        this("3atest,3atest", "wuppertal");
    }

    private final String identityCard;
    private final String service;
    private final ALKISCatalogServices alkisCatalogServices;
    private final ALKISInfoServices alkisInfoService;
    private final ALKISSearchServices alkisSearchService;

    /**
     * @return the identityCard
     */
    public String getIdentityCard() {
        return identityCard;
    }

    /**
     * @return the service
     */
    public String getService() {
        return service;
    }

    /**
     * @return the alkisCatalogServices
     */
    public ALKISCatalogServices getAlkisCatalogServices() {
        return alkisCatalogServices;
    }

    /**
     * @return the alkisInfoService
     */
    public ALKISInfoServices getAlkisInfoService() {
        return alkisInfoService;
    }

    /**
     * @return the alkisSearchService
     */
    public ALKISSearchServices getAlkisSearchService() {
        return alkisSearchService;
    }

   
}