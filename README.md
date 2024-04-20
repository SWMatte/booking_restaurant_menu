# Restaurant online

here i'll write the documentation about this project, actually there are some explanation about methods in the follower link
http://localhost:8080/api/v1/swagger-ui/index.html .

Soon i'll write more documentation about every single field in this project
but basically a brief summary could be: " Application to book some specific products and show to the caller how much will spend and which products will be buy on the pdf document"

- maybe in the future i'll develop a front-end application in according to these end-point
- need to develop more features like PDF documentation, several fixes

### Technologies used: 
    - Swagger 3.0
    - SpringBoot (web-hibernate-jpa-sql-jwt)
    - Flyway
    - Acroform

# Main classes
### - User (allow to register a new customer and set a role)
### - Customer (allow to register a customer with generality)
### - Product ( allow to insert the products you want sell)
### - Order (based on products defined, you can specify a order)

Almost all endpoints require a token to complete the request.

- the pdf entity will host the summary about purchase ( i'm still thinking how develop it )


### in the resources file you can find the collection of postman

## Licenza

This project is licensed under the terms of the [MIT License](LICENSE).

## References
 if you want work at this project Master branch is uploaded at the last version

## Author
    - SWMatte