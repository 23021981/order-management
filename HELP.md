# Pre-Requisite Software:

Apache Maven
JDK 11
Lombok plugin for compilation into IntelliJ IDEA.
Postman.

### In Memory database used H2Database.

*  BOOKDETAILS and INVENTORY tables used to hold book details and stock
* URL to H2 UI console. http://localhost:8071/h2

### List of End-Point

http://localhost:8072/bookOrder/placeBookOrder
http://localhost:8072/bookOrder/getBookOrder/{orderId}
http://localhost:8072/bookOrder/deleteBookDetail/{orderId}
http://localhost:8072/bookOrder/updateBookOrder