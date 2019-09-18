
Run project :

	run com.shoppingcart.demo.Application file
	
log file :

	log file will be find in same directory in which application resides
	
project url :

	http://localhost:8080/shopping/swagger-ui.html#/
	
Db script : 
	
	please find db script in resource directory

Authentication :

    http://localhost:9090/shopping/login for retrieving token by post params as ApplicationUser 

    If you want to call secured rest then 
        
        apply Authorization Bearer 'token generated from above request' as header

	
Api description :

	1 ) AdminController : will be accessed by admin only
	
		A ) admin/get-all-productstransaction : get all products detail and its order detail as sub list
		B ) admin/get-product-transaction/{productId} : get single product detail and its order detail as sub list
		C ) admin/get-all-orders-detail : get all orders of customer and items detail under that order
		D ) admin/update-order-status : update perticular order's status. like (order from Requested to Confirmed,Confirmed to In Process and etc.)
	
	2 ) CartController : will be accessible by customer
		
		A ) cart/add-product : add product to cart/add-product
		B ) cart/remove-product/{cartItemId} : remove product from cart
		C ) cart/get-customer-cart/{customerId} : get cart detail of customer. load all item available in cart
		
	3 ) OrderController : 
		
		A ) order/get-customer-orders/{customerId} : get all order of customer. consists order detail and all items in order as sub list
		B ) order/get-order/{orderId} : get single order. consists order detail and all items in order as sub list
		C ) order/create/{customerId} : place order of customer. place order by getting all active item in cart of customer
		D ) order/cancel/{customerId} : cancel order by customer.
		
	4 ) ProductController : 
		
		A ) product/get-all-products : get all product detail
		B ) product/get-product/{productId} : get single product detail


