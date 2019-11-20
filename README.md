Run project :

    mvn spring-boot:run to start application

log file :

    log file will be find in same directory in which application resides

Db script :
please find db script in resource directory

Authentication :

    Make POST request at http://localhost:9090/oauth/token to generate tokens.In the headers we have selected basic auth and provided username and password as 'ecartdemoclient' and 'hakunamatata'. This will result access_token, token_type, expiry, userId etc

    - Pass accesstoken in each request to access secure resource

Api description :

    1 ) ProductController :

    	A ) /products : get all product detail
    	B ) /products/{productId} : get single product detail

    2 ) AdminController : will be accessed by admin only

    	A ) admin/get-all-productstransaction : get all products detail and its order detail as sub list
    	B ) admin/get-product-transaction/{productId} : get single product detail and its order detail as sub list
    	C ) admin/get-all-orders-detail : get all orders of customer and items detail under that order
    	D ) admin/update-order-status : update perticular order's status. like (order from Requested to Confirmed,Confirmed to In Process and etc.)

    3 ) CartController : will be accessible by customer

    	A ) cart/add-product : add product to cart/add-product
    	B ) cart/remove-product/{cartItemId} : remove product from cart
    	C ) cart/get-customer-cart/{customerId} : get cart detail of customer. load all item available in cart

    4 ) OrderController :

    	A ) order/get-customer-orders/{customerId} : get all order of customer. consists order detail and all items in order as sub list
    	B ) order/get-order/{orderId} : get single order. consists order detail and all items in order as sub list
    	C ) order/create/{customerId} : place order of customer. place order by getting all active item in cart of customer
    	D ) order/cancel/{customerId} : cancel order by customer.


Improvement: 
	- Add API access by role 
	- Change API path as RestFul standards - Integrate authentication in swagger
