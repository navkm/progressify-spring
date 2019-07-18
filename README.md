# progressify-spring Release 0.1

*   [Introduction](#intro)
*   [How is works](#how)
*   [Usage](#usage)
*   [API](#api)
*   [API Documentation](#apidoc)

## <a id="#intro">Introduction</a>

**progressify-spring** is a build tool that can dynamically generate a [javascript service worker](https://developers.google.com/web/fundamentals/primers/service-workers/) for a [Spring Web application](https://spring.io/guides/gs/serving-web-content/). It can be integrated with any existing java build process and doesn't bring in any new dependencies to the Java code.

progressify-spring uses [Google's Workbox library](https://developers.google.com/web/tools/workbox/) to implement the service worker.

## <a id="#how">How it works</a>

progressify-spring provides an annotation processor and a set of annotations that you can use in conjunction with Spring WebMVC annotations to generate a [Service Worker](https://developers.google.com/web/fundamentals/primers/service-workers/). The annotation processor runs during the compilation phase and inspects the Spring code for annotations that it understands to generate the service worker.

Since all annotations defined by this tool are marked with RetentionPolicy.SOURCE, they are discarded by the compiler. Hence it introduces no impact to the rest of the Spring Application.

You can now use your generated serviceworker javascript in your web pages.

## <a id="#usage">Usage</a>

The following steps describes how to integrate the library with a Maven based build system.

### Step 1

Add a dependency to the library in your pom.xml

    <dependency> 
        <groupId>org.progressify</groupId> 
        <artifactId>progressify-spring</artifactId> 
        <version>0.1.0</version> 
    </dependency>
    
### Step 2

Configure the compiler plugin to use the annotation processor

    <plugin> 
        <artifactId>maven-compiler-plugin</artifactId> 
        <configuration> 
            <annotationProcessorPaths> 
                <path> 
                    <groupId>org.progressify</groupId> 
                    <artifactId>progressify-spring</artifactId> 
                    <version>0.1.0</version> 
                </path>
            </annotationProcessorPaths>
        </configuration> 
    </plugin>
    
### Step 3

Annotate your spring code. All annotations closely match existing [Workbox caching strategies](https://developers.google.com/web/tools/workbox/reference-docs/latest/workbox.strategies). Check the <a target="_blank" href="">API doc</a> for the list of available annotations.

For example, let us say you have an about page mapped to the path "/about". You would typically map this path to a java method in a Spring Web Conrtoller like this:

    @GetMapping(value="/about")
    public String about(){
        //
    } 
    
 If you want your browser clients to always cache the about page, just add an @CacheFirst annotation to the same method. 
 
     @CacheFirst    
     @GetMapping(value="/about")
     public String about(){
         //
     } 
     
This will ensure that your about page is always picked from the cache first. If the page is not available in the cache, the service worker would reach out to the server to retrieve the about page. 

<img src="https://developers.google.com/web/tools/workbox/images/modules/workbox-strategies/cache-first.png" width='300' />

Here is how the generated javascript code for this annotation would look like:

    workbox.routing.registerRoute(
        new RegExp('/about'),
        new workbox.strategies.StaleWhileRevalidate({
        })
    );

 ### Step 4
 
 Use the generated service worker in your Web Pages
 
 You can register the serviceworker in your Web Pages:
 
    <script>
        if ('serviceWorker' in navigator) {
            window.addEventListener('load', () => {
                navigator.serviceWorker.register('/sw.js');
            });
        }
    </script>
    
## <a id="#api">The API</a>

Currently the following annotations are supported:

*   @PWA
*   @CacheOnly
*   @CacheFirst
*   @NetworkOnly
*   @NetworkFirst
*   @StaleWhileRevalidate

 
 ## <a id="#apidoc">API Documentation</a>

 
 
