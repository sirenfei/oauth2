package com.faithbj.oauth.as.config;

import java.util.Arrays;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.http.MediaType;
import org.springframework.web.accept.ContentNegotiationManagerFactoryBean;
import org.springframework.web.servlet.View;
import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.config.annotation.DefaultServletHandlerConfigurer;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.springframework.web.servlet.view.ContentNegotiatingViewResolver;
import org.springframework.web.servlet.view.InternalResourceViewResolver;
import org.springframework.web.servlet.view.json.MappingJackson2JsonView;

@Configuration
@ComponentScan(basePackages = {"com.faithbj.oauth.as"})
@EnableWebMvc
@EnableAspectJAutoProxy
public class WebMvcConfig extends WebMvcConfigurerAdapter {

    @Bean
    public static PropertySourcesPlaceholderConfigurer propertySourcesPlaceholderConfigurer() {
        return new PropertySourcesPlaceholderConfigurer();
    }
    @Override
    public void configureDefaultServletHandling(DefaultServletHandlerConfigurer configurer) {
        configurer.enable();
    }
    
    

    @Bean
    public ContentNegotiatingViewResolver contentViewResolver() throws Exception {
        ContentNegotiationManagerFactoryBean contentNegotiationManager = new ContentNegotiationManagerFactoryBean();
        contentNegotiationManager.addMediaType("json", MediaType.APPLICATION_JSON);

        InternalResourceViewResolver viewResolver = new InternalResourceViewResolver();
        viewResolver.setPrefix("/WEB-INF/views/");
        viewResolver.setSuffix(".jsp");

        MappingJackson2JsonView defaultView = new MappingJackson2JsonView();
        defaultView.setExtractValueFromSingleKeyModel(true);

        ContentNegotiatingViewResolver contentViewResolver = new ContentNegotiatingViewResolver();
        contentViewResolver.setContentNegotiationManager(contentNegotiationManager.getObject());
        contentViewResolver.setViewResolvers(Arrays.<ViewResolver> asList(viewResolver));
        contentViewResolver.setDefaultViews(Arrays.<View> asList(defaultView));
        return contentViewResolver;
    }
    
    
/*    @Bean
    public FreeMarkerConfigurer freeMarkerConfigurer() {
        FreeMarkerConfigurer configurer = new FreeMarkerConfigurer();
        
        freemarker.template.Configuration configuration = new freemarker.template.Configuration(freemarker.template.Configuration.VERSION_2_3_22);
        configuration.setEncoding(Locale.SIMPLIFIED_CHINESE, "UTF-8");
        configuration.setTemplateUpdateDelay(1800);
        configurer.setConfiguration(configuration);
        return configurer;
    }   */
    
    /*
     * @Bean public PhotoServiceUserController photoServiceUserController(PhotoService photoService) {
     * PhotoServiceUserController photoServiceUserController = new PhotoServiceUserController(); return
     * photoServiceUserController; }
     */

    /*
     * @Bean public PhotoController photoController(PhotoService photoService) { PhotoController photoController = new
     * PhotoController(); photoController.setPhotoService(photoService); return photoController; }
     */

    /*
     * @Bean public AccessConfirmationController accessConfirmationController(ClientDetailsService clientDetailsService,
     * ApprovalStore approvalStore) { AccessConfirmationController accessConfirmationController = new
     * AccessConfirmationController(); accessConfirmationController.setClientDetailsService(clientDetailsService);
     * accessConfirmationController.setApprovalStore(approvalStore); return accessConfirmationController; }
     */

    /*
     * @Bean public PhotoServiceImpl photoServices() { List<PhotoInfo> photos = new ArrayList<PhotoInfo>();
     * photos.add(createPhoto("1", "marissa")); photos.add(createPhoto("2", "paul")); photos.add(createPhoto("3",
     * "marissa")); photos.add(createPhoto("4", "paul")); photos.add(createPhoto("5", "marissa"));
     * photos.add(createPhoto("6", "paul"));
     * 
     * PhotoServiceImpl photoServices = new PhotoServiceImpl(); photoServices.setPhotos(photos); return photoServices; }
     * 
     * // N.B. the @Qualifier here should not be necessary (gh-298) but lots of users report needing it.
     * 
     * @Bean public AdminController adminController(TokenStore tokenStore,
     * 
     * @Qualifier("consumerTokenServices") ConsumerTokenServices tokenServices, SparklrUserApprovalHandler
     * userApprovalHandler) { AdminController adminController = new AdminController();
     * adminController.setTokenStore(tokenStore); adminController.setTokenServices(tokenServices);
     * adminController.setUserApprovalHandler(userApprovalHandler); return adminController; }
     * 
     * private PhotoInfo createPhoto(String id, String userId) { PhotoInfo photo = new PhotoInfo(); photo.setId(id);
     * photo.setName("photo" + id + ".jpg"); photo.setUserId(userId);
     * photo.setResourceURL("/org/springframework/security/oauth/examples/sparklr/impl/resources/" + photo.getName());
     * return photo; }
     */



}
