# progressify-spring Release 0.1

## <a id="#intro">Introduction</a>

**progressify-spring** is a build tool that can dynamically generate a [javascript service worker](https://developers.google.com/web/fundamentals/primers/service-workers/) for a [Spring Web application](https://spring.io/guides/gs/serving-web-content/). It can be integrated with any existing java build process and doesn't bring in any new dependencies to the Java code.

progressify-spring uses [Google's Workbox library](https://developers.google.com/web/tools/workbox/) to implement the service worker.

## <a id="#intro">How it works</a>

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
