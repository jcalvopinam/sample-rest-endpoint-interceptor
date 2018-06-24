Rest Endpoints Interceptor Sample
---

This is a sample for **intercept all requests**. Inspects all headers and checks if exist an specific header.

* I used the following technologies:
   ```
   Spring Boot 2.0.2.RELEASE
   Mockito 1.9.5
   ```

How to run?
---

1. To compile and run the tests you can use the following command:
   ```mvn clean install```

   Or if you prefer just compile, use this command:
   ```mvn clean compile```

2. The project is a Spring Boot Application, so you can run inside of your IDE or from terminal with the following command:

   ```mvn spring-boot:run```

3. Rest endpoints

* **Dummy GET Request:** Checks if exist 'custom-header' in the request and return something like this:  "The URL: `url` has header the header?"

  ```
  url    : http://localhost:8080/sample-rest-endpoint-interceptor/api
  method : GET
  command: curl -H "custom-header: new-custom-header" -X GET http://localhost:8080/sample-rest-endpoint-interceptor/api?url=www.github.com/juanca87

  ```
  
* **Dummy POST Request:** Checks if exist 'custom-header' in the request and return something like this:  "The Body: `RequestBody` has header the header?"
    
    ```
    url    : http://localhost:8080/sample-rest-endpoint-interceptor/api
    method : POST * Body required 
    command: curl -H "custom-header: new-custom-header" --data "my body" -X POST http://localhost:8080/sample-rest-endpoint-interceptor/api
    ```
