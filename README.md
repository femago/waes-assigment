# Java - Assignment Scalable Web	

## Assigment
* Provide 2 http endpoints that accepts JSON base64 encoded binary data on both endpoints 
`<host>/v1/diff/<ID>/left` and `<host>/v1/diff/<ID>/right`
* The provided data needs to be diff-ed and the results shall be available on a third end 
point `<host>/v1/diff/<ID>`
* The results shall provide the following info in JSON format 
    * If equal return that 
    * If not of equal size just return that 
    * If of same size provide insight in where the diffs are, actual diffs are not needed (offsets + length) 
* Make assumptions in the implementation explicit, choices are good but need to be 
communicated	

## Assumptions
* **left** and **right** endpoints are implemented using http **PUT** operation since they are implemented as a create/update
* **left** or **right** endpoints can be called many times to update the operators values for a given **ID** as 
long as no diff operation have been called for the given **ID**
    * when **left** or **right** endpoints are called for an already calculated diff following error response will be returned
        ```
        HTTP/1.1 400 
        Content-Type: text/plain;charset=UTF-8
        Content-Length: 68
        Connection: close
    
        Operators cannot be changed for a previously processed Id: "example"   
        ```

## Implementation
### Running the application
1. clone the repository
2. run `./gradlew bootRun`
3. Application starts on port 8080

### Test
#### Unit Tests
Implemented using junit, located under folder `.\src\test`

#### Integration test
Implemented using cucumber, located under folder `.\src\test-integration`
