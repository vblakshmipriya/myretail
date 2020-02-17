# MyRetail Application

## Requirements
MyRetail is a rapidly growing company with HQ in Richmond, VA and over 200 stores across the east coast. myRetail wants to make its internal data available to any number of client devices, from myRetail.com to native mobile apps. 
The goal for this exercise is to create an end-to-end Proof-of-Concept for a products API, which will aggregate product data from multiple sources and return it as JSON to the caller. 
Your goal is to create a RESTful service that can retrieve product and price details by ID. The URL structure is up to you to define, but try to follow some sort of logical convention.
Build an application that performs the following actions: 
* 	Responds to an HTTP GET request at /products/{id} and delivers product data as JSON (where {id} will be a number. 
* 	Example product IDs: 15117729, 16483589, 16696652, 16752456, 15643793) 
* 	Example response: {"id":13860428,"name":"The Big Lebowski (Blu-ray) (Widescreen)","current_price":{"value": 13.49,"currency_code":"USD"}}
* 	Performs an HTTP GET to retrieve the product name from an external API. (For this exercise the data will come from redsky.target.com, but let’s just pretend this is an internal resource hosted by myRetail)  
* 	Example: http://redsky.target.com/v2/pdp/tcin/13860428?excludes=taxonomy,price,promotion,bulk_ship,rating_and_review_reviews,rating_and_review_statistics,question_answer_statistics
* 	Reads pricing information from a NoSQL data store and combines it with the product id and name from the HTTP request into a single response.  
* 	BONUS: Accepts an HTTP PUT request at the same path (/products/{id}), containing a JSON request body similar to the GET response, and updates the product’s price in the data store.  

## Technology Stack

*  JDK 11
*  Groovy 2.5.9
*  Gradle 5.4
*  Spring Boot 2.2.4
*  REST 
*  Spock 1.3-groovy-2.5
*  Datastax Cassandra 5.1.10

## Getting Started
  Install the following tools if you do not already have them:
  
  * If you don’t have it already, install Java(JDK 11).
  
  * Install GVM (see http://gvmtool.net/) if it is available for your OS, then use it to install Groovy and Gradle. Alternatively, search the web for installation instructions.
  
  * Use git (see https://git-scm.com/) to clone the project.
  
  * Launch cassandra in docker
    -  Navigate to  docker directory 
    -  Execute run.sh
    -  Execute schema.sh (to create keyspace and table)
  
  * Navigate into the project root directory and run the command "gradle build" to build the project.
  
  * Launch the application "gradle bootRun"
  
## Demo Prep

### GET 
 *  Endpoint: http://localhost:8080/v1/product/id/13860428
 *  Sample Response body
        {
          "id": "13860428",
          "name": "The Big Lebowski (Blu-ray)",
          "current_price": {
            "value": 120.0,
            "currency_code": "USD"
          },
          "links": [
            {
              "rel": "self",
              "href": "http://localhost:8080/v1/product/id/13860428"
            }
          ]
        }
        
### PUT - to update product price
 *  Endpoint: http://localhost:8080/v1/product/id/13860427/price
 *  Sample Request body
        {
            "value":123.00,
            "currency_code":"usd"
        }
 *  Sample Response body
        {
          "id": "13860427",
          "current_price": {
            "value": 123.0,
            "currency_code": "usd"
          },
          "links": [
            {
              "rel": "self",
              "href": "http://localhost:8080/v1/product/id/13860427/price"
            }
          ]
        }
       